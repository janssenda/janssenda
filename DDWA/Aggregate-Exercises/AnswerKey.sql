USE Northwind;

-- 1
/*
	Find the average freight paid for orders 
	placed by companies in the USA
*/
SELECT AVG(Freight) AS FreightAverage
FROM Orders o	
	INNER JOIN Customers c ON o.CustomerID = c.CustomerID
WHERE c.Country = 'USA';

-- 2
/*
	Find the gross total (sum of quantity * unit price) for 
	all orders placed by B's Beverages and Chop-suey Chinese.
*/

SELECT SUM(Quantity * UnitPrice) AS GrossTotal
FROM Orders o	
	INNER JOIN order_details od ON o.OrderID = od.OrderID
	INNER JOIN Customers c ON o.CustomerID = c.CustomerID
WHERE c.CompanyName IN ('B''s Beverages', 'Chop-suey Chinese');

-- 3
/*
	Find the gross total of all orders (sum of quantity * unit price) 
	for each customer, order it in descending order by the total.
*/

SELECT SUM(Quantity * UnitPrice) AS GrossTotal, CompanyName
FROM Orders o	
	INNER JOIN order_details od ON o.OrderID = od.OrderID
	INNER JOIN Customers c ON o.CustomerID = c.CustomerID
GROUP BY CompanyName
ORDER BY GrossTotal DESC;

-- 4
/*
	Get the count of how many employees work for the 
	company
*/

SELECT Count(*) as TotalEmployees
FROM Employees;

-- 5
/*
	Get the count of how many employees 
	report to someone else in the company 
	without using a WHERE clause.
*/
SELECT Count(*) as ManagedEmployees
FROM Employees e1 
	INNER JOIN Employees e2 ON e1.EmployeeID = e2.ReportsTo;

-- 6
/*
	Get the count of how many unique countries
	are represented by our suppliers.
*/

SELECT COUNT(DISTINCT(Country)) as UniqueCountries
FROM Suppliers;

-- 7
/*
	Find the total sales by supplier 
	ordered from most to least.
*/

SELECT SUM(od.Quantity * od.UnitPrice) AS TotalSales, s.CompanyName
FROM Suppliers s
	INNER JOIN Products p ON s.SupplierID = p.ProductID
	INNER JOIN order_details od ON od.ProductID = p.ProductID
GROUP BY CompanyName
ORDER BY TotalSales DESC;	

-- 8 
/*
	Show the number of orders placed by customers 
	from fewest to most provided the customer has 
	a minimum of 4 orders.
*/

SELECT COUNT(OrderID) as TotalOrders, CompanyName
FROM Orders o 
	INNER JOIN Customers c ON o.CustomerID = c.CustomerID
GROUP BY CompanyName
HAVING COUNT(OrderID) >= 4
ORDER BY TotalOrders;

-- 9
/*
	Challenge: 
	Show the total amount of orders by
	year and country.  Data should be ordered 
	by year ascending and total descending.
	
	TotalSales    Year     Country
	41907.80      1996     USA
	37804.60      1996     Germany
	etc...
	
	Hint: Research the DatePart() function
*/

SELECT SUM(od.UnitPrice * od.Quantity) as TotalSales, c.Country, 
	YEAR(o.OrderDate) as `Year`
FROM Orders o
	INNER JOIN order_details od ON o.OrderID = od.OrderID
	INNER JOIN Customers c ON o.CustomerID = c.CustomerID
GROUP BY c.Country, YEAR(o.OrderDate)
ORDER BY `Year`, TotalSales DESC;