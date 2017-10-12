USE Northwind;

-- Select * from Products;

-- Select * from Products where ProductName = 'Queso Cabrales';

/*
   Write a single query to display only the name and number of 
   units in stock for the products Laughing Lumberjack Lager, 
   Outback Lager, and Ravioli Angelo
*/

-- SELECT ProductName, UnitsInStock FROM Products
-- WHERE ProductName = 'Laughing Lumberjack Lager' 
-- OR ProductName = 'Outback Lager' 
-- OR ProductName = 'Ravioli Angelo';

/*
   From the Orders table select all the order information for the 
   customer with id of QUEDE
*/
-- SELECT * FROM Orders where CustomerID = 'QUEDE';

/*
   Select the orders whose freight is more than $100.00
*/

-- SELECT * FROM Orders where Freight > 100;
/*
   Select the orders shipping to the USA whose freight is 
   between $10 and $20
*/
-- SELECT * FROM Orders WHERE ShipCountry = 'USA' AND Freight BETWEEN 10 AND 20;

/*
   Select the Suppliers whose contact has a title that starts with the word 
   Sales
*/

-- SELECT * FROM Suppliers WHERE ContactTitle LIKE 'Sales%';

