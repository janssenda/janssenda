USE superherodb;


-- LOAD DATA SET -- 
-- LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/heroes.csv'
LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/heroes.csv'
INTO TABLE heroes
fields terminated BY ','
lines terminated BY '\n'
IGNORE 1 LINES
(HeroID, HeroName, HeroType, Description);

-- LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/organizations.csv'
LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/organizations.csv'
INTO TABLE organizations
fields terminated BY ','
lines terminated BY '\n'
IGNORE 1 LINES
(OrgID, OrgName, Description);

-- LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/headquarters.csv'
LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/headquarters.csv'
INTO TABLE headquarters
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(HeadQID, HeadQName, Address, Description);

-- LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/headquarters.csv'
LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/superpowers.csv'
INTO TABLE superpowers
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(PowerID, PowerName, Description);

-- LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/headquarters.csv'
LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/contacts.csv'
INTO TABLE contacts
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(HeadQID, Email);

-- LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/headquarters.csv'
LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/orgsheroes.csv'
INTO TABLE orgsheroes
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(OrgID, HeroID);

-- LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/headquarters.csv'
LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/orgsheadquarters.csv'
INTO TABLE orgsheadquarters
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(OrgID, HeadQID);

-- LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/headquarters.csv'
LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/heroespowers.csv'
INTO TABLE superpowersheroes
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(HeroID, PowerID);

-- LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/headquarters.csv'
LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/locations.csv'
INTO TABLE locations
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(LocID, LocName, Country, City, State, StAddress, ZipCode, Latitude, Longitude, Description);

LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/sightings.csv'
INTO TABLE sightings
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(SightingID, SightTime, LocID);

LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/sightingsheroes.csv'
INTO TABLE tempsightingsheroes
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(SightingID, HeroID);

INSERT INTO sightingsheroes
SELECT DISTINCT * FROM tempsightingsheroes;

UPDATE superpowers SET Description = NULL WHERE Description = '<none>';
UPDATE heroes SET Description = NULL WHERE Description = '<none>';
UPDATE organizations SET Description = NULL WHERE Description = '<none>';
UPDATE headquarters SET Description = NULL WHERE Description = '<none>';
UPDATE headquarters  SET Address = NULL WHERE Address = '<none>';
UPDATE locations SET Description = NULL WHERE Description = '<none>';
UPDATE locations  SET LocName = NULL WHERE LocName = '<none>';

DROP TABLE tempsightingsheroes;