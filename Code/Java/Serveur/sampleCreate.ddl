CREATE TABLE UTILISATEUR (ID BIGINT AUTO_INCREMENT NOT NULL, DTYPE VARCHAR(31), CARACTERISTIQUESMACHINE VARCHAR(255), LASTSCAN_ID BIGINT, EMAIL VARCHAR(255), PASSWORDHASH VARCHAR(255), RANDOM LONGBLOB, SALT LONGBLOB, CAMPUS_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE BATIMENT (ID BIGINT NOT NULL, NAME VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE CAMPUS (ID BIGINT NOT NULL, PLAN VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE ETAGE (ID BIGINT NOT NULL, NAME VARCHAR(255), PLAN VARCHAR(255), BATIMENT_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE PIECE (ID BIGINT NOT NULL, NAME VARCHAR(255), POSITION_X INTEGER, POSITION_Y INTEGER, ETAGE_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE WIFIDATA (ID BIGINT AUTO_INCREMENT NOT NULL, BSSID VARCHAR(255), CAPABILITIES VARCHAR(255), SSID VARCHAR(255), CENTERFREQ0 INTEGER, CENTERFREQ1 INTEGER, CHANNELWIDTH INTEGER, FREQUENCY INTEGER, LEVEL INTEGER, OPERATORFRIENDLYNAME VARCHAR(255), TIMESTAMP BIGINT, VENUENAME VARCHAR(255), SCANDATA_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE SCANDATA (ID BIGINT AUTO_INCREMENT NOT NULL, INFOSCAN VARCHAR(255), PIECE_ID BIGINT, SUPERVISEUR_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE CAMPUS_BATIMENT (Campus_ID BIGINT NOT NULL, batimentList_ID BIGINT NOT NULL, PRIMARY KEY (Campus_ID, batimentList_ID))
ALTER TABLE UTILISATEUR ADD CONSTRAINT FK_UTILISATEUR_CAMPUS_ID FOREIGN KEY (CAMPUS_ID) REFERENCES CAMPUS (ID)
ALTER TABLE UTILISATEUR ADD CONSTRAINT FK_UTILISATEUR_LASTSCAN_ID FOREIGN KEY (LASTSCAN_ID) REFERENCES SCANDATA (ID)
ALTER TABLE ETAGE ADD CONSTRAINT FK_ETAGE_BATIMENT_ID FOREIGN KEY (BATIMENT_ID) REFERENCES BATIMENT (ID)
ALTER TABLE PIECE ADD CONSTRAINT FK_PIECE_ETAGE_ID FOREIGN KEY (ETAGE_ID) REFERENCES ETAGE (ID)
ALTER TABLE WIFIDATA ADD CONSTRAINT FK_WIFIDATA_SCANDATA_ID FOREIGN KEY (SCANDATA_ID) REFERENCES SCANDATA (ID)
ALTER TABLE SCANDATA ADD CONSTRAINT FK_SCANDATA_PIECE_ID FOREIGN KEY (PIECE_ID) REFERENCES PIECE (ID)
ALTER TABLE SCANDATA ADD CONSTRAINT FK_SCANDATA_SUPERVISEUR_ID FOREIGN KEY (SUPERVISEUR_ID) REFERENCES UTILISATEUR (ID)
ALTER TABLE CAMPUS_BATIMENT ADD CONSTRAINT FK_CAMPUS_BATIMENT_Campus_ID FOREIGN KEY (Campus_ID) REFERENCES CAMPUS (ID)
ALTER TABLE CAMPUS_BATIMENT ADD CONSTRAINT FK_CAMPUS_BATIMENT_batimentList_ID FOREIGN KEY (batimentList_ID) REFERENCES BATIMENT (ID)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)
