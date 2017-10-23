DROP DATABASE IF EXISTS `superherodb-test`;
CREATE DATABASE `superherodb-test`;
USE `superherodb-test`;

CREATE TABLE locations (
	LocID INT NOT NULL AUTO_INCREMENT,
    LocName VARCHAR(255) NULL,
    Country VARCHAR(255) NULL,    
    City VARCHAR(255) NULL,
    State VARCHAR(255) NULL,    
    StAddress VARCHAR(255) NULL,
    Zipcode VARCHAR(255) NULL,    
    Latitude DECIMAL(12,6) NULL,
    Longitude DECIMAL(12,6) NULL,
    Description TEXT NULL,
    PRIMARY KEY (LocID)
);

CREATE TABLE organizations (
	OrgID INT NOT NULL AUTO_INCREMENT,
    OrgName VARCHAR(255) NOT NULL,
    Description TEXT NULL,
    PRIMARY KEY (OrgID)
);

CREATE TABLE heroes (
	HeroID INT NOT NULL AUTO_INCREMENT,
    HeroName VARCHAR(255) NOT NULL,
    HeroType VARCHAR(255) NOT NULL,
    Description TEXT NULL,
    PRIMARY KEY (HeroID)
);
    
CREATE TABLE superpowers (
	PowerID INT NOT NULL AUTO_INCREMENT,
    PowerName VARCHAR(255) NOT NULL,
    Description TEXT NULL,
    PRIMARY KEY(PowerID)
);

CREATE TABLE headquarters (
	HeadQID INT NOT NULL AUTO_INCREMENT,
    HeadQName VARCHAR(255) NOT NULL,
    Address VARCHAR(255) NULL,
    Description TEXT NULL,
    PRIMARY KEY(HeadQID)
);

CREATE TABLE contacts(
	HeadQID INT NOT NULL,
    Email VARCHAR(255),
    PRIMARY KEY (HeadQID, Email),
    FOREIGN KEY (HeadQID) REFERENCES headquarters(HeadQID)
);

CREATE TABLE sightings (
	SightingID INT NOT NULL AUTO_INCREMENT,
    SightTime DATETIME NOT NULL,
    LocID INT NOT NULL,
    PRIMARY KEY (SightingID),
    FOREIGN KEY (LocID) REFERENCES locations(LocID)
);

CREATE TABLE sightingsheroes (
    SightingID INT NOT NULL,
    HeroID INT NOT NULL,
  --  PRIMARY KEY (SightingID, HeroID),
    FOREIGN KEY (SightingID) REFERENCES sightings(SightingID),
    FOREIGN KEY (HeroID) REFERENCES heroes(HeroID)
);

CREATE TABLE tempsightingsheroes (
    SightingID INT NOT NULL,
    HeroID INT NOT NULL,
    FOREIGN KEY (SightingID) REFERENCES sightings(SightingID),
    FOREIGN KEY (HeroID) REFERENCES heroes(HeroID)
);

CREATE TABLE orgsheadquarters (
	OrgID INT NOT NULL,
    HeadQID INT NOT NULL,
    PRIMARY KEY (OrgID, HeadQID),
    FOREIGN KEY (HeadQID) REFERENCES headquarters(HeadQID),
    FOREIGN KEY (OrgID) REFERENCES organizations(OrgID)
);

CREATE TABLE superpowersheroes (
	PowerID INT NOT NULL,
    HeroID INT NOT NULL,
    PRIMARY KEY (PowerID, HeroID),
    FOREIGN KEY (HeroID) REFERENCES heroes(HeroID),
    FOREIGN KEY (PowerID) REFERENCES superpowers(PowerID)
);

CREATE TABLE orgsheroes (
	OrgID INT NOT NULL,
    HeroID INT NOT NULL,
    PRIMARY KEY (OrgID, HeroID),
    FOREIGN KEY (OrgID) REFERENCES organizations(OrgID),
    FOREIGN KEY (HeroID) REFERENCES heroes(HeroID)
);


-- LOAD DATA SET -- 
LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/heroes.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/heroes.csv'
INTO TABLE heroes
fields terminated BY ','
lines terminated BY '\n'
IGNORE 1 LINES
(HeroID, HeroName, HeroType, Description);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/organizations.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/organizations.csv'
INTO TABLE organizations
fields terminated BY ','
lines terminated BY '\n'
IGNORE 1 LINES
(OrgID, OrgName, Description);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/headquarters.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/headquarters.csv'
INTO TABLE headquarters
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(HeadQID, HeadQName, Address, Description);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/superpowers.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/superpowers.csv'
INTO TABLE superpowers
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(PowerID, PowerName, Description);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/contacts.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/contacts.csv'
INTO TABLE contacts
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(HeadQID, Email);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/orgsheroes.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/orgsheroes.csv'
INTO TABLE orgsheroes
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(OrgID, HeroID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/orgsheadquarters.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/orgsheadquarters.csv'
INTO TABLE orgsheadquarters
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(OrgID, HeadQID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/heroespowers.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/heroespowers.csv'
INTO TABLE superpowersheroes
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(HeroID, PowerID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/locations.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/locations.csv'
INTO TABLE locations
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(LocID, LocName, Country, City, State, StAddress, ZipCode, Latitude, Longitude, Description);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/sightings.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/sightings.csv'
INTO TABLE sightings
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(SightingID, SightTime, LocID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/sightingsheroes.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/sightingsheroes.csv'
INTO TABLE tempsightingsheroes
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(SightingID, HeroID);

INSERT INTO sightingsheroes
SELECT DISTINCT * FROM tempsightingsheroes;

UPDATE superpowers SET Description = NULL WHERE Description LIKE '%<none>%';
UPDATE heroes SET Description = NULL WHERE Description LIKE '%<none>%';
UPDATE organizations SET Description = NULL WHERE Description LIKE '%<none>%';
UPDATE headquarters SET Description = NULL WHERE Description LIKE '%<none>%';
UPDATE headquarters  SET Address = NULL WHERE Address LIKE '%<none>%';
UPDATE locations SET Description = NULL WHERE Description LIKE'%<none>%';
UPDATE locations  SET LocName = NULL WHERE LocName LIKE '%<none>%';

DROP TABLE tempsightingsheroes;


CREATE TABLE heroesb LIKE heroes;
CREATE TABLE organizationsb LIKE organizations;
CREATE TABLE headquartersb LIKE headquarters;
CREATE TABLE superpowersb LIKE superpowers;
CREATE TABLE contactsb LIKE contacts;
CREATE TABLE orgsheroesb LIKE orgsheroes;
CREATE TABLE orgsheadquartersb LIKE orgsheadquarters;
CREATE TABLE superpowersheroesb LIKE superpowersheroes;
CREATE TABLE locationsb LIKE locations;
CREATE TABLE sightingsb LIKE sightings;
CREATE TABLE sightingsheroesb LIKE sightingsheroes;

INSERT heroesb SELECT * FROM heroes;
INSERT organizationsb SELECT * FROM organizations;
INSERT headquartersb SELECT * FROM headquarters;
INSERT superpowersb SELECT * FROM superpowers;
INSERT contactsb SELECT * FROM contacts;
INSERT orgsheroesb SELECT * FROM orgsheroes;
INSERT orgsheadquartersb SELECT * FROM orgsheadquarters;
INSERT superpowersheroesb SELECT * FROM superpowersheroes;
INSERT locationsb SELECT * FROM locations;
INSERT sightingsb SELECT * FROM sightings;
INSERT sightingsheroesb SELECT * FROM sightingsheroes;


DELIMITER //
CREATE PROCEDURE refreshdata()
BEGIN
	SET FOREIGN_KEY_CHECKS = 0;
	TRUNCATE sightingsheroes;
    TRUNCATE sightings;
    TRUNCATE locations;
    TRUNCATE superpowersheroes;
    TRUNCATE orgsheadquarters;
    TRUNCATE orgsheroes;
    TRUNCATE contacts;
    TRUNCATE superpowers;
    TRUNCATE headquarters;
    TRUNCATE organizations;
    TRUNCATE heroes;
    
	INSERT heroes SELECT * FROM heroesb;
	INSERT organizations SELECT * FROM organizationsb;
	INSERT headquarters SELECT * FROM headquartersb;
	INSERT superpowers SELECT * FROM superpowersb;
	INSERT contacts SELECT * FROM contactsb;
	INSERT orgsheroes SELECT * FROM orgsheroesb;
	INSERT orgsheadquarters SELECT * FROM orgsheadquartersb;
	INSERT superpowersheroes SELECT * FROM superpowersheroesb;
	INSERT locations SELECT * FROM locationsb;
	INSERT sightings SELECT * FROM sightingsb;
	INSERT sightingsheroes SELECT * FROM sightingsheroesb;		
    SET FOREIGN_KEY_CHECKS = 1;
END //
DELIMITER ;

CALL refreshdata();


