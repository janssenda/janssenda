DROP SCHEMA IF EXISTS cabirdb_test;
CREATE DATABASE cabirdb_test;
USE cabirdb_test;

CREATE TABLE speakers (
	SpeakerID INT NOT NULL AUTO_INCREMENT,
    SpeakerModel VARCHAR(255) NOT NULL,
    SpeakerBrand VARCHAR(255) NULL,
    SpeakerName VARCHAR(255) NULL, 
    PRIMARY KEY (SpeakerID),
    UNIQUE(SpeakerModel,SpeakerBrand,SpeakerName)
);

CREATE TABLE cabs (
	CabID INT NOT NULL AUTO_INCREMENT,
    Brand VARCHAR(255) NOT NULL,
    CabName VARCHAR(255) NOT NULL,
    Size VARCHAR(25) NOT NULL,
    PRIMARY KEY (CabID),
    UNIQUE(Brand,CabName,Size)
);

CREATE TABLE impulses (
	ImpulseID INT NOT NULL AUTO_INCREMENT,
    ImpulseName VARCHAR(255) NOT NULL,
    Creator VARCHAR(255) NULL,
    PackName VARCHAR(255) NULL,
    CabID INT NULL,
    SpeakerID INT NULL,
    Description VARCHAR(1000) NULL,
    PRIMARY KEY (ImpulseID),
    FOREIGN KEY (CabID) REFERENCES cabs(CabID),
    FOREIGN KEY (SpeakerID) REFERENCES speakers(SpeakerID)
);

CREATE TABLE mics (
	MicID INT NOT NULL AUTO_INCREMENT,
    MicModel VARCHAR(255) NOT NULL,
    MicBrand VARCHAR(255) NOT NULL,
    PRIMARY KEY (MicID)
);

CREATE TABLE impulsesmics (
	ImpulseID INT NOT NULL,
    MicID INT NOT NULL,
	FOREIGN KEY (ImpulseID) REFERENCES impulses(ImpulseID),
    FOREIGN KEY (MicID) REFERENCES mics(MicID),
    PRIMARY KEY (ImpulseID, MicID)
);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ImpulseReactor/Database/speakers.csv'
REPLACE
INTO TABLE speakers
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(SpeakerModel, SpeakerBrand, SpeakerName);
UPDATE speakers SET SpeakerName = TRIM(REPLACE(REPLACE(REPLACE(SpeakerName, '\n', ' '), '\r', ' '), '\t', ' '));

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ImpulseReactor/Database/cabs.csv'
REPLACE
INTO TABLE cabs
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(Brand, CabName, Size);
UPDATE cabs SET Size = TRIM(REPLACE(REPLACE(REPLACE(Size, '\n', ' '), '\r', ' '), '\t', ' '));

DELETE FROM cabs WHERE CabID = 6;
DELETE FROM cabs WHERE CabID = 40;

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ImpulseReactor/Database/mics.csv'
REPLACE
INTO TABLE mics
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(MicModel, MicBrand);
UPDATE mics SET MicBrand = TRIM(REPLACE(REPLACE(REPLACE(MicBrand, '\n', ' '), '\r', ' '), '\t', ' '));

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ImpulseReactor/Database/impulses.csv'
REPLACE
INTO TABLE impulses
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\r'
IGNORE 1 LINES
(ImpulseName, Creator, PackName, CabID, SpeakerID, Description);
UPDATE impulses SET Description = TRIM(REPLACE(REPLACE(REPLACE(Description, '\n', ' '), '\r', ' '), '\t', ' '));

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ImpulseReactor/Database/impulsesmics.csv'
REPLACE
INTO TABLE impulsesmics
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\r\n'
IGNORE 1 LINES
(ImpulseID, MicID);
UPDATE mics SET MicID = TRIM(REPLACE(REPLACE(REPLACE(MicID, '\n', ' '), '\r', ' '), '\t', ' '));

CREATE TABLE cabsb LIKE cabs;
CREATE TABLE impulsesb LIKE impulses;
CREATE TABLE impulsesmicsb LIKE impulsesmics;
CREATE TABLE micsb LIKE mics;
CREATE TABLE speakersb LIKE speakers;

INSERT cabsb SELECT * FROM cabs;
INSERT impulsesb SELECT * FROM impulses;
INSERT impulsesmicsb SELECT * FROM impulsesmics;
INSERT micsb SELECT * FROM mics;
INSERT speakersb SELECT * FROM speakers;

DELIMITER //
CREATE PROCEDURE resetdata()
BEGIN
	SET FOREIGN_KEY_CHECKS = 0;
    TRUNCATE impulsesmics;
    TRUNCATE impulses;
    TRUNCATE speakers;
    TRUNCATE mics;    
    TRUNCATE cabs;
    
	INSERT cabs SELECT * FROM cabsb;
    INSERT speakers SELECT * FROM speakersb;
	INSERT mics SELECT * FROM micsb;
	INSERT impulses SELECT * FROM impulsesb;
	INSERT impulsesmics SELECT * FROM impulsesmicsb;

    SET FOREIGN_KEY_CHECKS = 1;
END //
DELIMITER ;

#CALL resetdata();




