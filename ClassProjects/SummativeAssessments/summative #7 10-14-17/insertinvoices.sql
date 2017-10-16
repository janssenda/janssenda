use hotelforlostsouls; 
INSERT INTO invoices
(ResID, RoomNumber, CustID, ChargeTypeID, ChargeDate, Duration, RoomTypeID, TypeName, Features, FtPrice, BasePrice, SeasonalPrice,
SubTotal, DiscountAmt, DiscountPer, Total)

SELECT ResID, RoomNumber, CustID, ChargeTypeID, StartDate, Duration, RoomTypeID, TypeName, Features, PriceMod, BasePrice, SeasonalPrice,
SubTotal, Discount, PercOff, Total FROM combinedinvoices;