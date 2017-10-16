INSERT INTO invoices(ResID, RoomNumber, CustID, ChargeTypeID, ChargeDate, Duration, RoomTypeID, TypeName, Features, FtPrice, BasePrice, SeasonalPrice, SubTotal, DiscountAmt, DiscountPer, Total)

SELECT ResID, RoomNumber, CustID, ChargeTypeID, StartDate, Duration, RoomTypeID, TypeName, Features, PriceMod, BasePrice, SeasonalPrice, SubTotal, Discount, PercOff, Total
FROM combinedinvoices ;

SELECT combinedinvoices.RoomNumber, combinedinvoices.ResID, customers.FirstName, customers.LastName, reservations.EndDate as InvDate, SUM(Total) as GrandTotal FROM combinedinvoices
INNER JOIN rescustomers 
ON combinedinvoices.ResID = rescustomers.ResID
INNER JOIN customers
ON rescustomers.CustID = customers.CustID
INNER JOIN reservations
ON rescustomers.ResID = reservations.ResID
GROUP BY combinedinvoices.ResID