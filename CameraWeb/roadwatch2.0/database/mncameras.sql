DROP SCHEMA IF EXISTS mncameras;
CREATE DATABASE mncameras;
USE mncameras;


CREATE TABLE cameras (
	SurrogateID INT NOT NULL AUTO_INCREMENT,
	CameraID INT NOT NULL,
	CameraNumber INT NULL,    
    Location VARCHAR(512) NULL,
    Description VARCHAR(512) NULL,
	Link VARCHAR(5000) NULL,
    Latitude DOUBLE PRECISION NULL, 
    Longitude DOUBLE PRECISION NULL, 
    EnableState BIT NOT NULL DEFAULT 1, 
    PRIMARY KEY (SurrogateID),
    UNIQUE (CameraID)
);


CREATE TABLE geolocations (
	LocID INT NOT NULL AUTO_INCREMENT,
    Description VARCHAR(512) NULL,
    PRIMARY KEY (LocID)
);

CREATE TABLE cameralocations (
	CameraID INT NOT NULL,
	LocID INT NOT NULL,
	FOREIGN KEY (CameraID) REFERENCES cameras(CameraID),
    FOREIGN KEY (LocID) REFERENCES geolocations(LocID),
    PRIMARY KEY (CameraID,LocID)
);