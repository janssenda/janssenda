use hotelforlostsouls; 
SELECT rescustomers.ResID, rescustomers.CustID FROM reservations
INNER JOIN rescustomers
ON reservations.ResID = rescustomers.ResID
ORDER BY reservations.ResID ASC
INTO OUTFILE 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/ResNew.csv'
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

SELECT * FROM rooms
ORDER BY rooms.RoomNumber ASC
INTO OUTFILE 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/RoomsNew.csv'
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

SELECT * FROM roomseasonalprices
ORDER BY SeasonalPriceID ASC
INTO OUTFILE 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/RoomSeasNew.csv'
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

SELECT * FROM seasonalservices
ORDER BY SeasonalPriceID ASC
INTO OUTFILE 'G:/SoftwareGuild/SWGProjects/Repositories/Private/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/SvcSeasNew.csv'
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

SELECT * FROM invoices
ORDER BY invoiceID ASC
INTO OUTFILE '/run/media/danimaetrix/Storage/SWG/thenewcarag/ClassProjects/SummativeAssessments/summative #7 10-14-17/Invoices.csv'
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

