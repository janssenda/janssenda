DROP SCHEMA IF EXISTS HotelForLostSouls;

CREATE DATABASE HotelForLostSouls;
USE HotelForLostSouls;

-- CREATE THE ROOMS AND ARRANGEMENTS --

CREATE TABLE Arrangements (
    ArrangementID INT AUTO_INCREMENT NOT NULL,
    KingBeds INT,
    QueenBeds INT,
    DoubleBeds INT,
    PRIMARY KEY (ArrangementID)    
);

CREATE TABLE Amenities (
    AmenityID INT AUTO_INCREMENT NOT NULL,
    Couch INT,
    Refrigerator INT,
    CoffeeMaker INT,
    TV INT,
    MiniBar INT,
    Desk INT,
    HotTub INT,
    PRIMARY KEY (AmenityID)
);

CREATE TABLE FeaturePkgs (
    FeaturePkgID INT AUTO_INCREMENT NOT NULL,
    Description VARCHAR(255) NOT NULL,
    Price DECIMAL NOT NULL,
    PRIMARY KEY (FeaturePkgID)    
);

CREATE TABLE RoomTypes (
    RoomTypeID INT AUTO_INCREMENT NOT NULL,
    TypeName VARCHAR(255) NOT NULL,
    ArrangementID INT NOT NULL,
    AmenityID INT NOT NULL,
    PRIMARY KEY (RoomTypeID),
    FOREIGN KEY (ArrangementID) REFERENCES Arrangements(ArrangementID),
    FOREIGN KEY (AmenityID) REFERENCES Amenities(AmenityID)
);

CREATE TABLE Rooms (
    RoomNumber INT NOT NULL,
    RoomTypeID INT NOT NULL,
    FeaturePkgId INT NULL,
    PRIMARY KEY (RoomNumber),    
    FOREIGN KEY (RoomTypeID) REFERENCES RoomTypes(RoomTypeID),
    FOREIGN KEY (FeaturePkgID) REFERENCES FeaturePkgs(FeaturePkgID)
);

CREATE TABLE RoomBasePrices (
    RoomTypeID INT NOT NULL,
    BasePrice DECIMAL NOT NULL,
    PRIMARY KEY (RoomTypeID),
    FOREIGN KEY (RoomTypeID) REFERENCES RoomTypes(RoomTypeID)
);

CREATE TABLE RoomSeasonalPrices (
	SeasonalPriceID INT AUTO_INCREMENT NOT NULL,
    RoomTypeID INT NOT NULL,
    Description VARCHAR(255) NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    SeasonalPrice DECIMAL NOT NULL,
    PRIMARY KEY (SeasonalPriceID),
    FOREIGN KEY (RoomTypeID) REFERENCES RoomTypes (RoomTypeID)
);

CREATE TABLE Services (
    SvcID INT AUTO_INCREMENT NOT NULL,
    SvcDesc VARCHAR(255) NOT NULL,
    BasePrice DECIMAL NOT NULL,
    PRIMARY KEY (SvcID)    
);

CREATE TABLE SeasonalServices (
	SeasonalPriceID INT AUTO_INCREMENT NOT NULL,
    SvcID INT NOT NULL,
    Description VARCHAR(255) NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    SeasonalPrice DECIMAL NOT NULL,
    FOREIGN KEY (SvcID) REFERENCES Services(SvcID),
    PRIMARY KEY (SeasonalPriceID)    
);

CREATE TABLE ChargeTypes (
    ChargeTypeID INT AUTO_INCREMENT NOT NULL,
    ChargeDesc VARCHAR(255) NOT NULL,
    PRIMARY KEY (ChargeTypeID)    
);

CREATE TABLE Promotions (
    PromoID INT AUTO_INCREMENT NOT NULL,
    PromoPercDisc DECIMAL NOT NULL,
    PromoAmtDisc DECIMAL NOT NULL,
    PromoDesc VARCHAR(255) NOT NULL,
    PRIMARY KEY (PromoID)    
);

-- CREATE THE RESERVATION SYSTEM -- 

CREATE TABLE Customers (
    CustID INT AUTO_INCREMENT,
    FirstName VARCHAR(255),
    LastName VARCHAR(255) NOT NULL,
    Age INT,
    Phone VARCHAR(255),
    Email VARCHAR(255),
    Address TEXT,
    BillingInfo TEXT NOT NULL,    
    PRIMARY KEY (CustID)    
);

CREATE TABLE Guests (
    GuestID INT AUTO_INCREMENT NOT NULL,
    FirstName VARCHAR(255),
    LastName VARCHAR(255) NOT NULL,
    Age INT,
    PRIMARY KEY (GuestID)    
);

CREATE TABLE CustomersGuests (
	CustGuestID INT AUTO_INCREMENT NOT NULL,
    CustID INT NOT NULL,
    GuestID INT NOT NULL,
    FOREIGN KEY (CustID) REFERENCES Customers(CustID),
    FOREIGN KEY (GuestID) REFERENCES Guests(GuestID),
    PRIMARY KEY (CustGuestID)
);

CREATE TABLE Reservations (
    ResID INT AUTO_INCREMENT NOT NULL,
    RoomNumber INT NOT NULL, 
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    ChargeTypeID INT NOT NULL,
    FOREIGN KEY (RoomNumber) REFERENCES Rooms(RoomNumber),
    PRIMARY KEY (ResID, RoomNumber)    
);


CREATE TABLE ResCustomers (
	ResID INT NOT NULL,
    CustID Int NOT NULL,
    FOREIGN KEY (ResID) REFERENCES Reservations(ResID),
    FOREIGN KEY (CustID) REFERENCES Customers(CustID),
    PRIMARY KEY (ResID, CustID)    
);

-- MAP RESERVATIONS TO SERVICES FOR EACH ORDERED SERVICE -- 
-- ONLY THE DATETIME IS UNCONSTRAINED HERE --

CREATE TABLE ResServices (
	ResSvcID INT AUTO_INCREMENT NOT NULL,
    ResID INT NOT NULL,
    RoomNumber INT NOT NULL,
    SvcID INT NOT NULL,
    OrderTime DATETIME NOT NULL,
    ChargeTypeID INT NOT NULL,
    FOREIGN KEY (ResID) REFERENCES Reservations(ResID),
    FOREIGN KEY (RoomNumber) REFERENCES Rooms(RoomNumber),
    FOREIGN KEY (SvcID) REFERENCES Services(SvcID),
    PRIMARY KEY (ResSvcID)
);


CREATE TABLE RoomPromotionsReservations (
	RoomPromoID INT AUTO_INCREMENT NOT NULL,
    ResID INT NOT NULL,
    RoomNumber INT NOT NULL,
    PromoID INT NOT NULL,
    FOREIGN KEY (ResID) REFERENCES Reservations(ResID),
    FOREIGN KEY (RoomNumber) REFERENCES Rooms(RoomNumber),
    PRIMARY KEY (RoomPromoID)  
);

CREATE TABLE ServicePromotionsReservations (
	SvcPromoID INT AUTO_INCREMENT NOT NULL,
    ResID INT NOT NULL,
    RoomNumber INT NOT NULL,
    PromoID INT NOT NULL,
    FOREIGN KEY (ResID) REFERENCES Reservations(ResID),
    FOREIGN KEY (RoomNumber) REFERENCES Rooms(RoomNumber),
    PRIMARY KEY (SvcPromoID)  
);

CREATE TABLE Invoices (
	InvoiceID INT AUTO_INCREMENT NOT NULL,
    ResID INT NOT NULL,
    RoomNumber INT NOT NULL,
    ChargeTypeID INT NOT NULL,
    ChargeDate DATETIME NOT NULL,
    Duration INT,
    RoomTypeID INT,
    TypeName VARCHAR (255) NOT NULL,
    Features VARCHAR (255),
    FtPrice DECIMAL,
    BasePrice DECIMAL NOT NULL,
    SeasonalPrice DECIMAL,
    SubTotal DECIMAL NOT NULL,
    DiscountAmt DECIMAL,
    DiscountPer DECIMAL,
    Total DECIMAL NOT NULL,
    Notes VARCHAR(255),
    FOREIGN KEY (ResID) REFERENCES Reservations(ResID),
    FOREIGN KEY (RoomNumber) REFERENCES Rooms(RoomNumber),
	PRIMARY KEY (InvoiceID)
);


USE hotelforlostsouls;


-- Bed arrangements for each room type --

INSERT INTO arrangements (Kingbeds, Queenbeds, Doublebeds)
VALUES 
(0, 0, 2), 
(0, 2, 0), 
(0, 2, 0), 
(1, 0, 0), 
(2, 0, 0); 

-- Amenities for each room type (Roomenities???) --

INSERT INTO amenities (Couch, Refrigerator, CoffeeMaker, TV, MiniBar, Desk, HotTub)
VALUES 
(0, 1, 1, 1, 0, 1, 0),
(1, 1, 1, 1, 1, 1, 0),
(1, 1, 2, 1, 1, 2, 0),
(1, 1, 1, 2, 2, 2, 1),
(2, 1, 2, 2, 2, 2, 1);

-- Feature Packages --

INSERT INTO featurepkgs (Description, Price)
VALUES 
('Poolside', 10),
('Top floor', 30),
('Lounge level', 10),
('Beachside', 50),
('Villa', 100);


INSERT INTO roomtypes (TypeName, ArrangementID, AmenityID)
VALUES
('Standard', 1, 1),
('Premium', 2, 2),
('Comfort Plus', 2, 3),
('Queen Suite', 3, 4),
('King Suite', 4, 4),
('Soul''s Keep', 5, 5);

INSERT INTO roombaseprices (RoomTypeID, BasePrice)
VALUES
(1, 100),
(2, 120),
(3, 140),
(4, 180),
(5, 220),
(6, 250);

INSERT INTO services (SvcDesc, BasePrice)
VALUES
('Food Service', 0),
('Drink Service', 0),
('Wake-up Call', 0),
('Massage (30 Min)', 45),
('Massage (90 Min)', 90);

INSERT INTO chargetypes (ChargeDesc)
VALUES
('Daily Rate'),
('Service Charge');

INSERT INTO promotions (PromoPercDisc, PromoAmtDisc, PromoDesc)
VALUES
(0, 10, '$10 off one night'),
(100, 0, 'Complimentary'),
(20, 0, '%20 off massage'),
(0, 30, '$25 off dinner');





USE hotelforlostsouls;

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/Rooms.csv'
REPLACE
INTO TABLE rooms
fields terminated BY ','
IGNORE 1 LINES
(RoomNumber,RoomTypeID,@FeaturePkgID)
SET FeaturePkgID = IF(@FeaturePkgID = 0, NULL , @FeaturePkgID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/Customers.csv'
REPLACE
INTO TABLE customers
fields terminated BY ','
lines terminated BY '\n'
IGNORE 1 LINES
(CustID, FirstName, LastName, Age, Phone, Email, Address, BillingInfo);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/Guests.csv'
REPLACE
INTO TABLE guests
fields terminated BY ','
lines terminated BY '\r\n'
IGNORE 1 LINES
(GuestID, FirstName, LastName, Age);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/CustomersGuests.csv'
REPLACE
INTO TABLE customersguests
fields terminated BY ','
lines terminated BY '\r\n'
IGNORE 1 LINES
(CustGuestID, CustID, GuestID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/Reservations.csv'
REPLACE
INTO TABLE reservations
fields terminated BY ','
lines terminated BY '\r\n'
IGNORE 1 LINES
(ResID, RoomNumber, StartDate, EndDate, ChargeTypeID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/ReservationsCustomers.csv'
REPLACE
INTO TABLE rescustomers
fields terminated BY ','
lines terminated BY '\n'
IGNORE 1 LINES
(ResID, CustID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/ReservationServices.csv'
REPLACE
INTO TABLE resservices
fields terminated BY ','
lines terminated BY '\r\n'
IGNORE 1 LINES
(ResSvcID, ResID, RoomNumber, SvcID, OrderTime, ChargeTypeID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/SeasonalRoomPrices.csv'
REPLACE
INTO TABLE roomseasonalprices
fields terminated BY ','
lines terminated BY '\r\n'
IGNORE 1 LINES
(SeasonalPriceID, RoomTypeID, Description, StartDate, EndDate, SeasonalPrice);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/SeasonalSvcPrices.csv'
REPLACE
INTO TABLE seasonalservices
fields terminated BY ','
lines terminated BY '\r\n'
IGNORE 1 LINES
(SeasonalPriceID, SvcID, Description, StartDate, EndDate, SeasonalPrice);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/RoomPromotions.csv'
REPLACE
INTO TABLE roompromotionsreservations
fields terminated BY ','
lines terminated BY '\r\n'
IGNORE 1 LINES
(RoomPromoID, ResID, RoomNumber, PromoID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/ServicePromotions.csv'
REPLACE
INTO TABLE servicepromotionsreservations
fields terminated BY ','
lines terminated BY '\r\n'
IGNORE 1 LINES
(SvcPromoID, ResID, RoomNumber, PromoID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/Invoices.csv'
REPLACE
INTO TABLE invoices
fields terminated BY ','
lines terminated BY '\n'
IGNORE 0 LINES
(InvoiceID, ResID, RoomNumber, ChargeTypeID, ChargeDate, Duration, RoomTypeID, TypeName, Features, FtPrice, BasePrice, SeasonalPrice, SubTotal, DiscountAmt, DiscountPer, Total, Notes);

CREATE VIEW RoomInvoices AS
SELECT reservations.ResID, rooms.RoomNumber, reservations.ChargeTypeID, reservations.StartDate, reservations.EndDate, 
IF(TO_DAYS(reservations.EndDate) - TO_DAYS(reservations.StartDate) > 0, TO_DAYS(reservations.EndDate) - TO_DAYS(reservations.StartDate), 1) as Duration,
rooms.RoomTypeID, roomtypes.TypeName, featurepkgs.Description as Features, featurepkgs.Price as PriceMod, roombaseprices.BasePrice,  

(SELECT roomseasonalprices.SeasonalPrice FROM roomseasonalprices 
WHERE rooms.RoomTypeID = roomseasonalprices.RoomTypeID
AND reservations.StartDate BETWEEN roomseasonalprices.StartDate AND roomseasonalprices.EndDate) as SeasonalPrice,

(IFNULL(featurepkgs.Price,0)+(SELECT SeasonalPrice))*(SELECT Duration) as SubTotal,
(SELECT sum(Promotions.PromoAmtDisc) FROM roompromotionsreservations INNER JOIN promotions ON roompromotionsreservations.PromoID = Promotions.PromoID
WHERE reservations.ResID = roompromotionsreservations.ResID
GROUP BY Reservations.ResID) as Discount,

(SELECT sum(Promotions.PromoPercDisc) FROM roompromotionsreservations INNER JOIN promotions ON roompromotionsreservations.PromoID = Promotions.PromoID
WHERE reservations.ResID = roompromotionsreservations.ResID
GROUP BY Reservations.ResID) as PercOff,

IFNULL(
IF(IF((SELECT PercOff) >= 100,0,((SELECT SubTotal) - (SELECT Discount))*(1 - (SELECT PercOff/100))) <= 0,0, 
IF((SELECT PercOff) >= 100,0,((SELECT SubTotal) - (SELECT Discount))*(1 - (SELECT PercOff/100))) ), (SELECT SubTotal)
)

as Total

FROM
rescustomers
INNER JOIN reservations
ON rescustomers.ResID = Reservations.ResID
INNER JOIN rooms 
ON reservations.RoomNumber = rooms.RoomNumber
INNER JOIN roomtypes
ON rooms.RoomTypeID = roomtypes.RoomTypeID
LEFT JOIN featurepkgs
ON rooms.FeaturePkgID = featurepkgs.FeaturePkgID
INNER JOIN roombaseprices
ON rooms.RoomTypeID = roombaseprices.RoomTypeID;

CREATE VIEW ServiceInvoices AS
SELECT reservations.ResID, resservices.RoomNumber, resservices.ChargeTypeID, resservices.OrderTime as StartDate, null as EndDate,
null as Duration, null as RoomTypeID, services.SvcDesc, null as Features, null as PriceMod, services.BasePrice,
 
(SELECT seasonalservices.SeasonalPrice FROM seasonalservices 
WHERE resservices.SvcID = seasonalservices.SvcID
AND CAST(resservices.OrderTime AS DATE) BETWEEN seasonalservices.StartDate AND seasonalservices.EndDate) as SeasonalPrice,

(SELECT SeasonalPrice) as SubTotal,

(SELECT sum(Promotions.PromoAmtDisc) FROM servicepromotionsreservations INNER JOIN promotions ON servicepromotionsreservations.PromoID = Promotions.PromoID
WHERE reservations.ResID = servicepromotionsreservations.ResID
GROUP BY Reservations.ResID) as Discount,

(SELECT sum(Promotions.PromoPercDisc) FROM servicepromotionsreservations INNER JOIN promotions ON servicepromotionsreservations.PromoID = Promotions.PromoID
WHERE reservations.ResID = servicepromotionsreservations.ResID
GROUP BY Reservations.ResID) as PercOff,

IFNULL(
IF(IF((SELECT PercOff) >= 100,0,((SELECT SubTotal) - (SELECT Discount))*(1 - (SELECT PercOff/100))) <= 0,0, 
IF((SELECT PercOff) >= 100,0,((SELECT SubTotal) - (SELECT Discount))*(1 - (SELECT PercOff/100))) ), (SELECT SubTotal)
) as Total


FROM resservices
INNER JOIN reservations
ON resservices.ResID = reservations.ResID
INNER JOIN services
ON resservices.SvcID = services.SvcID;



CREATE VIEW combinedinvoices AS 
SELECT * FROM roominvoices UNION SELECT * FROM serviceinvoices;