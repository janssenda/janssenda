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
lines terminated BY '\n'
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
(SvcPromoID, ResID, RoomNumber, PromoID, ResSvcID);

LOAD DATA infile 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/Invoices.csv'
REPLACE
INTO TABLE invoices
fields terminated BY ','
lines terminated BY '\n'
IGNORE 0 LINES
(InvoiceID, ResID, RoomNumber, CustID, ChargeTypeID, ChargeDate, Duration, RoomTypeID, TypeName, Features, FtPrice, BasePrice, SeasonalPrice, SubTotal, DiscountAmt, DiscountPer, Total, Notes);