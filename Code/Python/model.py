import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

from sklearn.neighbors import KNeighborsClassifier
from sklearn.ensemble import RandomForestClassifier

from sklearn.model_selection import train_test_split


import requests
from requests.exceptions import HTTPError
import json


ip = "10.21.74.229"


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
        
        url = "http://" + ip + ":9998/LPS/LaGarde/" + "scans"
        response = requests.get(url)
        response.raise_for_status()
        
        jSonResponse = response.json()
        
        
        i = 0
        j = 0
        # Separation des scans
        for scanData in jSonResponse :
            scanData = jSonResponse[scanData]
            
            scanInfo = scanData["infoScan"]
            scanDate = scanData["dateScan"]
            scanWifis = scanData["wifiList"]
            
            piece = scanData["piece"]["name"]
            if piece not in self.pieceOrder:
                self.pieceOrder[piece] = i
                self.pieceInverse[i] = piece
                i += 1


            scanWifisObj = []
            # Separation des wifis detectes sur le scan            
            for wifi in scanWifis:
            
                BSSID = wifi["BSSID"]
                if BSSID not in self.BSSIDOrder:
                    self.BSSIDOrder[BSSID] = j
                    j += 1
                    
                wifiBSSID = self.BSSIDOrder[BSSID]
                cap = wifi["Capabilities"]
                freq0 = int( wifi["centerFreq0"] )
                freq1 = int(wifi["centerFreq1"])
                cWidth = int(wifi["channelWidth"])
                freq = int(wifi["frequency"])
                level = int(wifi["level"])
                op_name = wifi["operatorFriendlyName"]
                ssid = wifi["SSID"]
                ts = int(wifi["timestamp"])
                vn = wifi["venueName"]                         
                    
                wifiObj = Wifi(wifiBSSID, cap, freq0, freq1, cWidth, freq, level, op_name, ssid,ts, vn)
                scanWifisObj.append(wifiObj)
                
            scan = Scan(scanDate, scanInfo, self.pieceOrder[piece], scanWifisObj)
            self.lScan.append(scan)


def get_data_csv(scanMan, set_mod = True, predict = False) :
    dico = {}
    dico["BSSID"] = []
    dico["centerFreq0"] = []
    dico["centerFreq1"] = []
    dico["channelWidth"] = []
    dico["frequency"] = []
    dico["level"] = []
    dico["operatorFriendlyName"] = []
    dico["SSID"] = []
    dico["timestamp"] = []
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

            dico["centerFreq0"].append(wifi.centerFreq0)
            dico["centerFreq1"].append(wifi.centerFreq1)
            dico["channelWidth"].append(wifi.channelWidth)
            dico["frequency"].append(wifi.frequency)
            dico["level"].append(wifi.level)
            dico["operatorFriendlyName"].append(wifi.operatorFriendlyName)
            dico["SSID"].append(wifi.SSID)
            dico["timestamp"].append(wifi.timestamp)

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


# Separation des donnees
def data_separator(model_data_csv):
    # Shuffle  les lignes
    model_data_csv.reindex(np.random.permutation(model_data_csv.index))
    
    x, y = [], []

    for piece in model_data_csv:
        room_name = int(piece.split(':')[0])
        y.append(room_name)
        x.append(model_data_csv[piece])
        
    return x, y


# Creation d'un modèle KNeighborsClassifier
def create_KNeighborsClassifier(x_train, y_train):
    model = KNeighborsClassifier()
    model.fit(x_train, y_train)
    return model

# Creation d'un modèle RandomForestClassifier
def create_RandomForestClassifier(x_train, y_train):
    model = RandomForestClassifier()
    model.fit(x_train, y_train)
    return model


# Mesure de l'efficacite du modele
def get_efficiency(model):
    somme = 0
    nb = 20

    for i in range(nb):
        somme += model.score(x_test, y_test)

    precision = "{:.2f}".format(somme / nb)
    print("Précisions sur " + str(nb) + " essais pour le modèle = " + str(precision + "\n"))
    return precision
    

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
x, y = [], []
def init():
    global scanMan, x, y
    scanMan = ScanManager()
    scanMan.read()

    
    model_data_csv = data_organizer(scanMan)['level']
    x, y = data_separator(model_data_csv)
    

    modelKnn = create_KNeighborsClassifier(x, y)
    print("Creation Knn done")
    
    
    modelRF = create_RandomForestClassifier(x, y)
    print("Creation random forest done")
    

if __name__ == "__main__" :
    init()
