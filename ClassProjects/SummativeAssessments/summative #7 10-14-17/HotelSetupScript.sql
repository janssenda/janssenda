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
    ResSvcID INT NOT NULL,
    FOREIGN KEY (ResID) REFERENCES Reservations(ResID),
    FOREIGN KEY (RoomNumber) REFERENCES Rooms(RoomNumber),
    FOREIGN KEY (ResSvcID) REFERENCES ResServices(ResSvcID),
    PRIMARY KEY (SvcPromoID)  
);

CREATE TABLE Invoices (
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
    FOREIGN KEY (ResID) REFERENCES Reservations(ResID),
    FOREIGN KEY (RoomNumber) REFERENCES Rooms(RoomNumber),
	PRIMARY KEY (InvoiceID)
);
