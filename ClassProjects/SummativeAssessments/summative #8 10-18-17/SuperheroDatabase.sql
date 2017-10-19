DROP DATABASE IF EXISTS superheroes;
CREATE DATABASE superheroes;
USE superheroes;

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
    OrgDesc TEXT NULL,
    PRIMARY KEY (OrgID)
);

CREATE TABLE heroes (
	HeroID INT NOT NULL AUTO_INCREMENT,
    HeroName VARCHAR(255) NOT NULL,
    HeroType VARCHAR(255) NOT NULL,
    Description TEXT NULL,
    PRIMARY KEY (HeroID)
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

CREATE TABLE orgslocations (
	OrgLocID INT NOT NULL AUTO_INCREMENT,
	OrgID INT NOT NULL,
    LocID INT NOT NULL,
    PRIMARY KEY (OrgLocID),
    FOREIGN KEY (LocID) REFERENCES locations(LocID),
    FOREIGN KEY (OrgID) REFERENCES organizations(OrgID)
);

CREATE TABLE superpowers (
	PowerID INT NOT NULL AUTO_INCREMENT,
    HeroID INT NOT NULL,
    PRIMARY KEY (PowerID, HeroID),
    FOREIGN KEY (HeroID) REFERENCES heroes(HeroID)
);

CREATE TABLE orgmembers (
	OrgID INT NOT NULL,
    HeroID INT NOT NULL,
    PRIMARY KEY (OrgID, HeroID),
    FOREIGN KEY (OrgID) REFERENCES organizations(OrgID),
    FOREIGN KEY (HeroID) REFERENCES heroes(HeroID)
);

CREATE TABLE orgemail(
	OrgLocID INT NOT NULL,
    Email VARCHAR(255),
    PRIMARY KEY (OrgLocID),
    FOREIGN KEY (OrgLocID) REFERENCES orgslocations(OrgLocID)
);

CREATE TABLE orgphone(
	OrgLocID INT NOT NULL,
    Phone VARCHAR(255),
	PRIMARY KEY (OrgLocID),
    FOREIGN KEY (OrgLocID) REFERENCES orgslocations(OrgLocID)
);



