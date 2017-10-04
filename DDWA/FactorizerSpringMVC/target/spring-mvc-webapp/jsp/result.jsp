<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: danimaetrix
  Date: 10/3/17
  Time: 2:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Factor Results</title>
</head>
<body>
<h1>Result</h1>
<p>
    You asked to factor ${numberToFactor}
</p>
<p>
    Factors are:
    <c:forEach var="currentFactor" items="${factors}">
        <c:out value="${currentFactor} "/>
    </c:forEach>
</p>
<p>
    <c:choose>
        <c:when test="${isPerfect}">
            <c:out value="The number is perfect."/>
        </c:when>
        <c:otherwise>
            <c:out value="The number is not perfect."/>
        </c:otherwise>
    </c:choose>
</p>
<p>
    <c:choose>
        <c:when test="${isPrime}">
            <c:out value="The number is prime."/>
        </c:when>
        <c:otherwise>
            <c:out value="The number is not prime."/>
        </c:otherwise>
    </c:choose>
</p>
<p>
    <a href="${pageContext.request.contextPath}">Factor Another One!</a>
</p>

</body>
</html>
