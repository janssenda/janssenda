USE OldHotelForLostSouls;
SET @NewStartDate = '2017-10-17';
SET @RoomType = 5;
-- Find rooms open on a single date --
SELECT DISTINCT rooms.RoomNumber FROM rooms
WHERE rooms.RoomNumber NOT IN (SELECT reservations.RoomNumber FROM reservations
        WHERE @NewStartDate BETWEEN reservations.StartDate AND reservations.EndDate)
        AND rooms.RoomTypeID = @RoomType;
        
-- Find rooms open on a range of dates --
SET @NewStartDate = '2017-10-17';
SET @NewEndDate = '2017-10-17';
SET @RoomType = 5;
SELECT DISTINCT rooms.RoomNumber FROM rooms
WHERE rooms.RoomNumber NOT IN (SELECT reservations.RoomNumber FROM reservations
WHERE reservations.StartDate BETWEEN @NewStartDate  AND @NewEndDate
OR reservations.EndDate BETWEEN @NewStartDate  AND @NewEndDate
OR @NewStartDate BETWEEN reservations.StartDate AND reservations.EndDate)
AND rooms.RoomTypeID = @RoomType;

-- All rooms reserved for customer -- 
SET @CustomerID = 515;
SELECT * FROM customers 
INNER JOIN rescustomers
ON customers.CustID = rescustomers.CustID
INNER JOIN reservations 
ON rescustomers.ResID = reservations.ResID
WHERE rescustomers.CustID = @CustomerId;

-- Based on Promo Code -- 
SET @PromoID = 1;
SELECT * FROM reservations
INNER JOIN roompromotionsreservations
ON reservations.ResID = roompromotionsreservations.ResID
INNER JOIN promotions
ON roompromotionsreservations.PromoID = promotions.PromoID
WHERE roompromotionsreservations.PromoID = @PromoID;

-- Query room comfiguration --
SET @AmenityID = 5; 
SET @NewStartDate = '2017-10-17';
SET @NewEndDate = '2017-10-17';
SELECT rooms.RoomNumber, rooms.RoomTypeID, rooms.FeaturePkgId, roomtypes.TypeName, amenities.AmenityID, roombaseprices.BasePrice, featurepkgs.Price as FtPrice, 
IFNULL(featurepkgs.Price,0)+roombaseprices.BasePrice as Total, amenities.Couch, amenities.Refrigerator, amenities.CoffeeMaker, amenities.TV, amenities.MiniBar,
amenities.Desk, amenities.HotTub
FROM rooms
INNER JOIN roomtypes
ON rooms.RoomTypeID = roomtypes.RoomTypeID
INNER JOIN amenities
ON roomtypes.AmenityID = amenities.AmenityID
INNER JOIN roombaseprices
On roomtypes.RoomTypeID = roombaseprices.RoomTypeID
LEFT JOIN featurepkgs 
ON featurepkgs.FeaturePkgID = rooms.FeaturePkgID
WHERE roomtypes.RoomTypeID = @AmenityID
AND rooms.RoomNumber NOT IN (SELECT reservations.RoomNumber FROM reservations
WHERE reservations.StartDate BETWEEN @NewStartDate  AND @NewEndDate
OR reservations.EndDate BETWEEN @NewStartDate  AND @NewEndDate
OR @NewStartDate BETWEEN reservations.StartDate AND reservations.EndDate)
ORDER BY (SELECT Total) asc;

-- Create total promotion codes / times used --
CREATE VIEW AllPromos as 
SELECT * FROM servicepromotionsreservations UNION 
SELECT * FROM roompromotionsreservations;

-- Query total times used -- 
SELECT AllPromos.PromoID, promotions.PromoDesc, COUNT(AllPromos.PromoID) as TimesUsed FROM AllPromos
INNER JOIN promotions on AllPromos.PromoID = promotions.PromoID
GROUP BY AllPromos.PromoID;

-- 10 Greatest Bills --
SELECT invoices.ResID, customers.FirstName, customers.LastName, reservations.StartDate as ResDate, SUM(invoices.Total) FROM invoices
INNER JOIN customers
ON invoices.CustID = customers.CustID
INNER JOIN reservations 
ON invoices.ResID = reservations.ResID
GROUP BY invoices.ResID
ORDER BY invoices.Total desc
LIMIT 10;

-- 10 Greatest Bills --
SET @CustID = 14;
SELECT invoices.ResID, customers.FirstName, customers.LastName, reservations.StartDate as ResDate, SUM(invoices.Total) As GrandTotal FROM invoices
INNER JOIN customers
ON invoices.CustID = customers.CustID
INNER JOIN reservations 
ON invoices.ResID = reservations.ResID
WHERE invoices.CustID = @CustID
GROUP BY invoices.ResID
ORDER BY invoices.Total desc
LIMIT 10;

-- Reservations that end today --
SET @EndDate = '2017-10-21';
SELECT * FROM reservations
WHERE reservations.EndDate = @EndDate;

-- Invoice for one reservation (Line Items)
SET @ResID = 19;
SELECT * FROM invoices
WHERE invoices.ResID = @ResID
ORDER BY invoices.ChargeDate asc;


