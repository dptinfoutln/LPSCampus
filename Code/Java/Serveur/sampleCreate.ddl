CREATE TABLE UTILISATEUR (ID BIGINT NOT NULL, DTYPE VARCHAR(31), CARACTERISTIQUESMACHINE VARCHAR(255), FORM_ID BIGINT, LASTSCAN_ID BIGINT, EMAIL VARCHAR(255), PASSWORDHASH LONGBLOB, RANDOM LONGBLOB, SALT LONGBLOB, PRIMARY KEY (ID))
CREATE TABLE FORMDEVENIRSUPER (ID BIGINT NOT NULL, EMAIL VARCHAR(255), PASSWORDHASH LONGBLOB, RANDOM LONGBLOB, SALT LONGBLOB, PRIMARY KEY (ID))
CREATE TABLE BUGREPORT (ID BIGINT NOT NULL, CONTENT VARCHAR(255), CARACTERISTIQUESMACHINE VARCHAR(255), CATEGORY VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE BATIMENT (ID BIGINT NOT NULL, NAME VARCHAR(255), POSITION_X INTEGER, POSITION_Y INTEGER, CAMPUS_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE CAMPUS (ID BIGINT NOT NULL, NAME VARCHAR(255), PLAN VARCHAR(255), ADMINISTRATEUR_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE ETAGE (ID BIGINT NOT NULL, NAME VARCHAR(255), PLAN VARCHAR(255), BATIMENT_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE PIECE (ID BIGINT NOT NULL, NAME VARCHAR(255), POSITION_X INTEGER, POSITION_Y INTEGER, ETAGE_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE WIFIDATA (ID BIGINT AUTO_INCREMENT NOT NULL, BSSID VARCHAR(255), CAPABILITIES VARCHAR(255), SSID VARCHAR(255), CENTERFREQ0 INTEGER, CENTERFREQ1 INTEGER, CHANNELWIDTH INTEGER, FREQUENCY INTEGER, LEVEL INTEGER, OPERATORFRIENDLYNAME VARCHAR(255), TIMESTAMP BIGINT, VENUENAME VARCHAR(255), SCANDATA_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE SCANDATA (ID BIGINT AUTO_INCREMENT NOT NULL, INFOSCAN VARCHAR(255), PIECE_ID BIGINT, SUPERVISEUR_ID BIGINT, PRIMARY KEY (ID))
ALTER TABLE UTILISATEUR ADD CONSTRAINT FK_UTILISATEUR_FORM_ID FOREIGN KEY (FORM_ID) REFERENCES FORMDEVENIRSUPER (ID)
ALTER TABLE UTILISATEUR ADD CONSTRAINT FK_UTILISATEUR_LASTSCAN_ID FOREIGN KEY (LASTSCAN_ID) REFERENCES SCANDATA (ID)
ALTER TABLE BATIMENT ADD CONSTRAINT FK_BATIMENT_CAMPUS_ID FOREIGN KEY (CAMPUS_ID) REFERENCES CAMPUS (ID)
ALTER TABLE CAMPUS ADD CONSTRAINT FK_CAMPUS_ADMINISTRATEUR_ID FOREIGN KEY (ADMINISTRATEUR_ID) REFERENCES UTILISATEUR (ID)
ALTER TABLE ETAGE ADD CONSTRAINT FK_ETAGE_BATIMENT_ID FOREIGN KEY (BATIMENT_ID) REFERENCES BATIMENT (ID)
ALTER TABLE PIECE ADD CONSTRAINT FK_PIECE_ETAGE_ID FOREIGN KEY (ETAGE_ID) REFERENCES ETAGE (ID)
ALTER TABLE WIFIDATA ADD CONSTRAINT FK_WIFIDATA_SCANDATA_ID FOREIGN KEY (SCANDATA_ID) REFERENCES SCANDATA (ID)
ALTER TABLE SCANDATA ADD CONSTRAINT FK_SCANDATA_PIECE_ID FOREIGN KEY (PIECE_ID) REFERENCES PIECE (ID)
ALTER TABLE SCANDATA ADD CONSTRAINT FK_SCANDATA_SUPERVISEUR_ID FOREIGN KEY (SUPERVISEUR_ID) REFERENCES UTILISATEUR (ID)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN_TABLE', 0)
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)
