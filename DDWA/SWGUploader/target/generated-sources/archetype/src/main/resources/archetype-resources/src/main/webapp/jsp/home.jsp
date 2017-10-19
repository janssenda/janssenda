#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${symbol_dollar}{pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Spring MVC Application from Archetype</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                	<li role="presentation" class="active"><a href="${symbol_dollar}{pageContext.request.contextPath}/home">Home</a></li>
                	<li role="presentation"><a href="${symbol_dollar}{pageContext.request.contextPath}/addPictureForm">Add a Picture!</a></li>
                </ul>    
            </div>
            <h2>Home Page</h2>
            
            <c:forEach var="currentPicture" items="${symbol_dollar}{pictureList}">
                <hr>
                ${symbol_dollar}{currentPicture.title}<br>
                <img src="${symbol_dollar}{pageContext.request.contextPath}/${symbol_dollar}{currentPicture.filename}"
                     class="img-thumbnail" alt="${symbol_dollar}{currentPicture.title}" width="300" height="300"><br> 
            </c:forEach>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${symbol_dollar}{pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${symbol_dollar}{pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

