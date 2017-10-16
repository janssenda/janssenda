use hotelforlostsouls; 

CREATE VIEW serviceinvoices AS
SELECT reservations.ResID, resservices.RoomNumber, rescustomers.CustID, resservices.ChargeTypeID, resservices.OrderTime as StartDate, null as EndDate,
null as Duration, null as RoomTypeID, services.SvcDesc, null as Features, null as PriceMod, services.BasePrice,
 
(SELECT seasonalservices.SeasonalPrice FROM seasonalservices 
WHERE resservices.SvcID = seasonalservices.SvcID
AND CAST(resservices.OrderTime AS DATE) BETWEEN seasonalservices.StartDate AND seasonalservices.EndDate) as SeasonalPrice,

(SELECT SeasonalPrice) as SubTotal,

(SELECT sum(promotions.PromoAmtDisc) FROM servicepromotionsreservations INNER JOIN promotions ON servicepromotionsreservations.PromoID = promotions.PromoID
WHERE resservices.ResSvcId = servicepromotionsreservations.ResSvcID
GROUP BY reservations.ResID) as Discount,

(SELECT sum(promotions.PromoPercDisc) FROM servicepromotionsreservations INNER JOIN promotions ON servicepromotionsreservations.PromoID = promotions.PromoID
WHERE resservices.ResSvcId = servicepromotionsreservations.ResSvcID
GROUP BY reservations.ResID) as PercOff,

IFNULL(
IF(IF((SELECT PercOff) >= 100,0,((SELECT SubTotal) - (SELECT Discount))*(1 - (SELECT PercOff/100))) <= 0,0, 
IF((SELECT PercOff) >= 100,0,((SELECT SubTotal) - (SELECT Discount))*(1 - (SELECT PercOff/100))) ), (SELECT SubTotal)
) as Total


FROM resservices
INNER JOIN reservations
ON resservices.ResID = reservations.ResID
INNER JOIN services
ON resservices.SvcID = services.SvcID
INNER JOIN rescustomers
ON resservices.ResID = rescustomers.ResID;



CREATE VIEW combinedinvoices AS 
SELECT * FROM roominvoices UNION SELECT * FROM serviceinvoices;