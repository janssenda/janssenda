<%--
  Created by IntelliJ IDEA.
  User: Danimaetrix
  Date: 10/2/2017
  Time: 9:08 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Factorizer</title>
</head>
<body>
    <h1>Factorizer</h1>

    <p>Please enter then number that you want to factor: </p>

    <p><form method="POST" action="FactorizerServlet">

        <input type="text" name="numberToFactor">
        <input type="submit" value="Find Factors">

    </form>
    </p>
</body>
</html>
