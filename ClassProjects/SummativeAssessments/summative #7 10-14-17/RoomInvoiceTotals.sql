CREATE VIEW RoomInvoices AS
SELECT reservations.ResID, rooms.RoomNumber, rescustomers.CustID, reservations.ChargeTypeID, reservations.StartDate, reservations.EndDate, 
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