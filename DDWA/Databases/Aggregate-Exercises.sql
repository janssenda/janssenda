/*
	Find the average freight paid for orders 
	placed by companies in the USA
*/
USE Northwind;

-- SELECT Customers.CompanyName, AVG(Orders.Freight), Customers.Country  
-- FROM Customers
-- INNER JOIN Orders ON Customers.CustomerID = Orders.CustomerID
-- WHERE Customers.Country = 'USA'
-- GROUP BY Customers.CompanyName;

/*
	Find the gross total (sum of quantity * unit price) for 
	all orders placed by B's Beverages and Chop-suey Chinese.
*/
-- SELECT Customers.CompanyName, SUM(Order_Details.Quantity*Order_Details.UnitPrice)
-- FROM Customers
-- INNER JOIN Orders ON Customers.CustomerID = Orders.CustomerID
-- INNER JOIN Order_Details ON Orders.OrderID = Order_Details.OrderID
-- WHERE Customers.CustomerID = 'BSBEV' OR Customers.CustomerID = 'CHOPS'
-- GROUP BY Customers.CompanyName;

/*
	Find the gross total of all orders (sum of quantity * unit price) 
	for each customer, order it in descending order by the total.
*/
-- SELECT Customers.CompanyName, Customers.CustomerID, SUM(Order_Details.Quantity*Order_Details.UnitPrice)
-- FROM Customers
-- INNER JOIN Orders ON Customers.CustomerID = Orders.CustomerID
-- INNER JOIN Order_Details ON Orders.OrderID = Order_Details.OrderID
-- GROUP BY Customers.CompanyName
-- ORDER BY SUM(Order_Details.Quantity*Order_Details.UnitPrice) asc

/*
	Get the count of how many employees work for the 
	company
-- */
-- SELECT SUM(EmployeeID) FROM Employees;

/*
	Get the count of how many employees 
	report to someone else in the company 
	without using a WHERE clause.
*/
-- SELECT count(Employees.ReportsTo) FROM Employees;

/*
	Get the count of how many unique countries
	are represented by our suppliers.
*/
-- SELECT count(distinct Suppliers.Country) FROM Suppliers;

/*
	Find the total sales by supplier 
	ordered from most to least.
*/
-- SELECT Suppliers.SupplierID, sum(Order_Details.Quantity*Order_Details.UnitPrice) FROM Suppliers
-- INNER JOIN Products
-- ON Suppliers.SupplierID = Products.SupplierID
-- INNER JOIN Order_Details
-- ON Products.ProductID = Order_Details.ProductID
-- GROUP BY Suppliers.SupplierID



