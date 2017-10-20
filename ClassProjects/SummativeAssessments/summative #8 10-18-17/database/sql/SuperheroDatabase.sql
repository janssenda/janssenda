DROP DATABASE IF EXISTS superherodb;
CREATE DATABASE superherodb;
USE superherodb;

CREATE TABLE locations (
	LocID INT NOT NULL AUTO_INCREMENT,
    LocName VARCHAR(255) NOT NULL,
    City VARCHAR(255) NULL,
    StAddress VARCHAR(255) NULL,
    Latitude DECIMAL NULL,
    Longitude DECIMAL NULL,
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
    PRIMARY KEY (HeadQID),
    FOREIGN KEY (HeadQID) REFERENCES headquarters(HeadQID)
);

CREATE TABLE sightings (
	SightingID INT NOT NULL AUTO_INCREMENT,
    LocID INT NOT NULL,
    HeroID INT NOT NULL,
    SightTime DATETIME NOT NULL,
    PRIMARY KEY (SightingID),
    FOREIGN KEY (LocID) REFERENCES locations(LocID),
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




