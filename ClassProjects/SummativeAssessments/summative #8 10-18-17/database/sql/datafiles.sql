USE superherodb;


-- LOAD DATA SET -- 
LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/heroes.csv'
INTO TABLE heroes
fields terminated BY ','
lines terminated BY '\n'
IGNORE 1 LINES
(HeroID, HeroName, HeroType, Description);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/organizations.csv'
INTO TABLE organizations
fields terminated BY ','
lines terminated BY '\n'
IGNORE 1 LINES
(OrgID, OrgName, Description);



LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #8 10-18-17/database/data/csv/headquarters.csv'
INTO TABLE organizations
fields terminated BY ','
optionally enclosed by '"'
lines terminated BY '\n'
IGNORE 1 LINES
(HeadQID, HeadQName, Address, Description);
