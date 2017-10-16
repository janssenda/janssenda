DROP SCHEMA IF EXISTS hotelforlostsouls;

CREATE DATABASE hotelforlostsouls;
USE hotelforlostsouls;

-- CREATE THE ROOMS AND ARRANGEMENTS --

CREATE TABLE arrangements (
    ArrangementID INT AUTO_INCREMENT NOT NULL,
    KingBeds INT,
    QueenBeds INT,
    DoubleBeds INT,
    PRIMARY KEY (ArrangementID)    
);

CREATE TABLE amenities (
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

CREATE TABLE featurepkgs (
    FeaturePkgID INT AUTO_INCREMENT NOT NULL,
    Description VARCHAR(255) NOT NULL,
    Price DECIMAL NOT NULL,
    PRIMARY KEY (FeaturePkgID)    
);

CREATE TABLE roomtypes (
    RoomTypeID INT AUTO_INCREMENT NOT NULL,
    TypeName VARCHAR(255) NOT NULL,
    ArrangementID INT NOT NULL,
    AmenityID INT NOT NULL,
    PRIMARY KEY (RoomTypeID),
    FOREIGN KEY (ArrangementID) REFERENCES arrangements(ArrangementID),
    FOREIGN KEY (AmenityID) REFERENCES amenities(AmenityID)
);

CREATE TABLE rooms (
    RoomNumber INT NOT NULL,
    RoomTypeID INT NOT NULL,
    FeaturePkgId INT NULL,
    PRIMARY KEY (RoomNumber),    
    FOREIGN KEY (RoomTypeID) REFERENCES roomtypes(RoomTypeID),
    FOREIGN KEY (FeaturePkgID) REFERENCES featurepkgs(FeaturePkgID)
);

CREATE TABLE roombaseprices (
    RoomTypeID INT NOT NULL,
    BasePrice DECIMAL NOT NULL,
    PRIMARY KEY (RoomTypeID),
    FOREIGN KEY (RoomTypeID) REFERENCES roomtypes(RoomTypeID)
);

CREATE TABLE roomseasonalprices (
	SeasonalPriceID INT AUTO_INCREMENT NOT NULL,
    RoomTypeID INT NOT NULL,
    Description VARCHAR(255) NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    SeasonalPrice DECIMAL NOT NULL,
    PRIMARY KEY (SeasonalPriceID),
    FOREIGN KEY (RoomTypeID) REFERENCES roomtypes (RoomTypeID)
);

CREATE TABLE services (
    SvcID INT AUTO_INCREMENT NOT NULL,
    SvcDesc VARCHAR(255) NOT NULL,
    BasePrice DECIMAL NOT NULL,
    PRIMARY KEY (SvcID)    
);

CREATE TABLE seasonalservices (
	SeasonalPriceID INT AUTO_INCREMENT NOT NULL,
    SvcID INT NOT NULL,
    Description VARCHAR(255) NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    SeasonalPrice DECIMAL NOT NULL,
    FOREIGN KEY (SvcID) REFERENCES services(SvcID),
    PRIMARY KEY (SeasonalPriceID)    
);

CREATE TABLE chargetypes (
    ChargeTypeID INT AUTO_INCREMENT NOT NULL,
    ChargeDesc VARCHAR(255) NOT NULL,
    PRIMARY KEY (ChargeTypeID)    
);

CREATE TABLE promotions (
    PromoID INT AUTO_INCREMENT NOT NULL,
    PromoPercDisc DECIMAL NOT NULL,
    PromoAmtDisc DECIMAL NOT NULL,
    PromoDesc VARCHAR(255) NOT NULL,
    PRIMARY KEY (PromoID)    
);

-- CREATE THE RESERVATION SYSTEM -- 

CREATE TABLE customers (
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

CREATE TABLE guests (
    GuestID INT AUTO_INCREMENT NOT NULL,
    FirstName VARCHAR(255),
    LastName VARCHAR(255) NOT NULL,
    Age INT,
    PRIMARY KEY (GuestID)    
);

CREATE TABLE customersguests (
	CustGuestID INT AUTO_INCREMENT NOT NULL,
    CustID INT NOT NULL,
    GuestID INT NOT NULL,
    FOREIGN KEY (CustID) REFERENCES customers(CustID),
    FOREIGN KEY (GuestID) REFERENCES guests(GuestID),
    PRIMARY KEY (CustGuestID)
);

CREATE TABLE reservations (
    ResID INT AUTO_INCREMENT NOT NULL,
    RoomNumber INT NOT NULL, 
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    ChargeTypeID INT NOT NULL,
    FOREIGN KEY (RoomNumber) REFERENCES rooms(RoomNumber),
    PRIMARY KEY (ResID, RoomNumber)    
);


CREATE TABLE rescustomers (
	ResID INT NOT NULL,
    CustID Int NOT NULL,
    FOREIGN KEY (ResID) REFERENCES reservations(ResID),
    FOREIGN KEY (CustID) REFERENCES customers(CustID),
    PRIMARY KEY (ResID, CustID)    
);

-- MAP RESERVATIONS TO SERVICES FOR EACH ORDERED SERVICE -- 
-- ONLY THE DATETIME IS UNCONSTRAINED HERE --

CREATE TABLE resservices (
	ResSvcID INT AUTO_INCREMENT NOT NULL,
    ResID INT NOT NULL,
    RoomNumber INT NOT NULL,
    SvcID INT NOT NULL,
    OrderTime DATETIME NOT NULL,
    ChargeTypeID INT NOT NULL,
    FOREIGN KEY (ResID) REFERENCES reservations(ResID),
    FOREIGN KEY (RoomNumber) REFERENCES rooms(RoomNumber),
    FOREIGN KEY (SvcID) REFERENCES services(SvcID),
    PRIMARY KEY (ResSvcID)
);


CREATE TABLE roompromotionsreservations (
	RoomPromoID INT AUTO_INCREMENT NOT NULL,
    ResID INT NOT NULL,
    RoomNumber INT NOT NULL,
    PromoID INT NOT NULL,
    FOREIGN KEY (ResID) REFERENCES reservations(ResID),
    FOREIGN KEY (RoomNumber) REFERENCES rooms(RoomNumber),
    PRIMARY KEY (RoomPromoID)  
);

CREATE TABLE servicepromotionsreservations (
	SvcPromoID INT AUTO_INCREMENT NOT NULL,
    ResID INT NOT NULL,
    RoomNumber INT NOT NULL,
    PromoID INT NOT NULL,
    ResSvcID INT NOT NULL,
    FOREIGN KEY (ResID) REFERENCES reservations(ResID),
    FOREIGN KEY (ResSvcID) REFERENCES resservices(ResSvcID),
    FOREIGN KEY (RoomNumber) REFERENCES rooms(RoomNumber),
    PRIMARY KEY (SvcPromoID)  
);

CREATE TABLE invoices (
	InvoiceID INT AUTO_INCREMENT NOT NULL,
    ResID INT NOT NULL,
    RoomNumber INT NOT NULL,
    CustID INT NOT NULL,
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
    FOREIGN KEY (ResID) REFERENCES reservations(ResID),
    FOREIGN KEY (RoomNumber) REFERENCES rooms(RoomNumber),
	PRIMARY KEY (InvoiceID)
);
