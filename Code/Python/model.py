import os

import seaborn as sns
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

from sklearn.neighbors import KNeighborsClassifier
from sklearn.ensemble import RandomForestClassifier

from sklearn.model_selection import train_test_split

import data.join_files as jf


### COMMON ###

def enlever_extension(nom):
    return ".".join(nom.split(".")[:-1])
    

class Wifi:
    def __init__(self, BSSID, capabilities, centerFreq0, centerFreq1,
                 channelWidth, frequency, level, operatorFriendlyName, SSID,
                 timestamp, venueName):
        self.BSSID = BSSID
        self.capabilities = capabilities
        self.centerFreq0 = centerFreq0
        self.centerFreq1 = centerFreq1
        self.channelWidth = channelWidth
        self.frequency = frequency
        self.level = level
        self.operatorFriendlyName = operatorFriendlyName
        self.SSID = SSID
        self.timestamp = timestamp
        self.venueName = venueName

class Scan:
    def __init__(self, date, info, piece, lWifi):
        self.date = date
        self.info = info
        self.piece = piece
        self.lWifi = lWifi

class ScanManager:
    def __init__(self):
        self.lScan = []
        self.pieceOrder = {}
        self.pieceInverse = {}
        self.BSSIDOrder = {}
    
    def read(self, directory="./data/res/"):
        # pour chaque fichier dans le dossier
        i = 0
        j = 0
        for fname in os.listdir(directory):
            with open(directory + fname) as fichier:
                # la piece est donnée par le nom du fichier
                piece = enlever_extension(fname)
                tmp = self.pieceOrder[piece] = i
                
                self.pieceInverse[tmp] = piece
                
                piece = tmp
                i += 1
                
                # Separation des scans
                for scanData in fichier.read().split("scan")[1:]:
                    
                    scan_lines = scanData.splitlines()
                    scanDate = scan_lines[0].split(":")[1]
                    scanInfo = scan_lines[1].split(":")[1]
                    scanWifis = []

                    wifis_content = scan_lines[2:]

                    # Separation des wifis detectes sur le scan
                    wifis_list = [wifis_content[x:x+11] for x in range(0, len(wifis_content), 12)][:-1]
                    
                    for wifi_car_l in (wifis_list) :
                        BSSID = wifi_car_l[0].split("BSSID:")[1]
                        if BSSID not in self.BSSIDOrder:
                            self.BSSIDOrder[BSSID] = j
                            j += 1
                            
                        wifiBSSID = self.BSSIDOrder[BSSID]
                        cap = wifi_car_l[1].split(":")[1]
                        freq0 = int(wifi_car_l[2].split(":")[1])
                        freq1 = int(wifi_car_l[3].split(":")[1])
                        cWidth = int(wifi_car_l[4].split(":")[1])
                        freq = int(wifi_car_l[5].split(":")[1])
                        level = int(wifi_car_l[6].split(":")[1])
                        op_name = wifi_car_l[7].split(":")[1]
                        ssid = wifi_car_l[8].split(":")[1]
                        ts = int(wifi_car_l[9].split(":")[1])
                        vn = wifi_car_l[10].split(":")[1]                            
                            
                        wifi = Wifi(wifiBSSID, cap, freq0, freq1, cWidth, freq, level, op_name, ssid,ts, vn)
                        scanWifis.append(wifi)
                    scan = Scan(scanDate, scanInfo, piece, scanWifis)
                    self.lScan.append(scan)


def get_data_csv(scanMan, set_mod = True, predict = False) :
    dico = {}
    dico["BSSID"] = []
    # dico["capabilities"] = []
    dico["centerFreq0"] = []
    dico["centerFreq1"] = []
    dico["channelWidth"] = []
    dico["frequency"] = []
    dico["level"] = []
    dico["operatorFriendlyName"] = []
    dico["SSID"] = []
    dico["timestamp"] = []
    # dico["venueName"] = []
    dico["piece"] = []
    dico["zone_info"] = []
    dico["date"] = []
    
    i = 0
    prev_piece = scanMan.lScan[0].piece
    for scan in scanMan.lScan :
        for wifi in scan.lWifi:
            if prev_piece != scan.piece:
                prev_piece = scan.piece
                i = 0
            if set_mod == True :
                dico["piece"].append(str(scan.piece) + ":" + str(i))
            else :
                dico["piece"].append(str(scan.piece))
            dico["zone_info"].append(scan.info)
            dico["date"].append(scan.date)
            
            dico["BSSID"].append(wifi.BSSID)
            # dico["capabilities"].append(wifi.capabilities)
            dico["centerFreq0"].append(wifi.centerFreq0)
            dico["centerFreq1"].append(wifi.centerFreq1)
            dico["channelWidth"].append(wifi.channelWidth)
            dico["frequency"].append(wifi.frequency)
            dico["level"].append(wifi.level)
            dico["operatorFriendlyName"].append(wifi.operatorFriendlyName)
            dico["SSID"].append(wifi.SSID)
            dico["timestamp"].append(wifi.timestamp)
            # dico["venueName"].append(wifi.venueName)
        i += 1
    
    data_csv = pd.DataFrame.from_dict(dico)
    
    return data_csv
    
def data_organizer(scanMan, car = 'level', set_mod = True):
    
    all_data_csv = get_data_csv(scanMan, set_mod)

    data_csv = all_data_csv[ ['BSSID', 'piece', car] ]
    data_csv = data_csv.pivot_table(data_csv,
                                    index=['BSSID'],
                                    columns = ['piece'],
                                    aggfunc={car: np.mean},
                                    fill_value=0)

      
    return data_csv.astype(int)

def all_data_organizer(scanMan):
    
    all_data_csv = get_data_csv(scanMan, False)

    data_csv = all_data_csv.pivot_table(all_data_csv,
                                    index=['BSSID'],
                                    columns = ['piece'], 
                                    aggfunc={'level': np.mean,
                                             'frequency': np.mean},
                                    fill_value=0)

    return data_csv.astype(int)

def data_organizer2(scanMan, car = 'level'):
    
    all_data_csv = get_data_csv(scanMan, set_mod=False)

    data_csv = all_data_csv[ ['BSSID', 'piece', car] ]
    data_csv = data_csv.set_index(['BSSID'])
    return data_csv.astype(int)


def model_data_analyzer(scanMan):
    model_data_csv = get_data_csv(scanMan, set_mod=True)

    return model_data_csv[ ['BSSID', 'level', 'piece'] ]

def calcul_correlation(scanMan):
    """
    Renvoie la corrélation de variables récupérées
    """
    lvl_data = all_data_organizer(scanMan)
    return lvl_data.corr()

# Separation des donnees
def data_separator(model_data_csv):
    # Shuffle  les lignes
    # model_data_csv = model_data_csv.sample(frac=1)
    model_data_csv.reindex(np.random.permutation(model_data_csv.index))
    
    x1, y1, x2, y2 = [], [], [], []
    x_train2, x_test2, y_train2, y_test2 = [], [], [], []
    for piece in model_data_csv:
        room_name = int(piece.split(':')[0])
        # On met de cote dans x1, y1 1 donnee de chaque piece
        if room_name not in y1 :
            y1.append(room_name)
            x1.append(model_data_csv[piece])
        else:
            y2.append(room_name)
            x2.append(model_data_csv[piece])
    
    
    # On split aleatoirement en test et entrainement les data des salles
    x_train2, x_test2, y_train2, y_test2 = train_test_split(x2,
                                                    y2,
                                                    test_size=0.3,
                                                    random_state=42)
    
    return x_train2+x1, x_test2, y_train2+y1, y_test2

# Creation d'un modèle KNeighborsClassifier
def create_KNeighborsClassifier(x_train, x_test, y_train, y_test):
    model = KNeighborsClassifier()
    model.fit(x_train, y_train)
    return model

# Creation d'un modèle RandomForestClassifier
def create_RandomForestClassifier(x_train, x_test, y_train, y_test):
    model = RandomForestClassifier()
    model.fit(x_train, y_train)
    return model



# Mesure de l'efficacite du modele

def get_efficiency(model):
    somme = 0
    nb = 1

    for i in range(nb):
        somme += model.score(x_test, y_test)

    precision = "{:.2f}".format(somme / nb)
    print("Précisions sur " + str(nb) + " essais pour le modèle = " + str(precision + "\n"))
    return precision

def update_data():
    jf.join(os.getcwd() + "/data/")



def get_predict(dico) :
    global scanMan, model
    x = [0]*len(scanMan.BSSIDOrder)
    for wifi in dico:
        wifi = dico[wifi]
        bssid = wifi["BSSID"]
        if bssid in scanMan.BSSIDOrder:
            bssid = scanMan.BSSIDOrder[bssid]
            x[bssid] = wifi["level"]

    prediction = scanMan.pieceInverse[model.predict([x])[0]]
    predict_proba = model.predict_proba([x])
    return prediction, predict_proba
    
    

scanMan = None
model = None
x_train, x_test, y_train, y_test = [], [], [], []
def init():
    global scanMan, x_train, x_test, y_train, y_test, model
    update_data()
    scanMan = ScanManager()
    scanMan.read()

    
    model_data_csv = data_organizer(scanMan)['level']
    x_train, x_test, y_train, y_test = data_separator(model_data_csv)
    print("Test Modele RandomForest :")
    model = create_RandomForestClassifier(x_train, x_test, y_train, y_test)
    get_efficiency(model)
    

if __name__ == "__main__" :
    init()



