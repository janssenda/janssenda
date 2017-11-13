DROP SCHEMA IF EXISTS vendingdatabase;
CREATE DATABASE vendingdatabase;
USE vendingdatabase;

CREATE TABLE prices (
ProductID INT PRIMARY KEY AUTO_INCREMENT,
ProductName VARCHAR(255) NOT NULL,
ProductPrice DECIMAL(10,2) NOT NULL);

CREATE TABLE inventory (
InventoryID INT PRIMARY KEY AUTO_INCREMENT,
ProductName VARCHAR(255) NOT NULL,
ProductDate DATE NOT NULL,
ProductMessage VARCHAR(255) NULL,
ProductInformation VARCHAR(255) NULL);

CREATE TABLE serverlog (
LogID INT PRIMARY KEY AUTO_INCREMENT,
Entry TEXT);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/DDWA/VendingAPI_JDBC/src/main/resources/priceData.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/DDWA/VendingAPI_JDBC/src/main/resources/priceData.csv'
REPLACE
INTO TABLE prices
fields terminated BY ','
lines terminated BY '\n'
(ProductName, ProductPrice);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/DDWA/VendingAPI_JDBC/src/main/resources/inventoryData.csv'
-- LOAD DATA infile '/run/media/danimaetrix/Storage/SWG/thenewcarag/DDWA/VendingAPI_JDBC/src/main/resources/inventoryData.csv'
REPLACE
INTO TABLE inventory
fields terminated BY ','
lines terminated BY '\n'
(ProductName, @StringDate, ProductMessage, ProductInformation)
SET ProductDate = STR_TO_DATE(@StringDate,'%m/%d/%Y');


