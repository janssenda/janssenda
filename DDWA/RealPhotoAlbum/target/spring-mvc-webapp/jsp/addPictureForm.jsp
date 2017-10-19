<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Spring MVC Application from Archetype</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/home">Home</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/addPictureForm">Add a Picture!</a></li>
                </ul>    
            </div>
            <h2>Home Page</h2>
            <h2>${errorMsg}</h2>
            <p>Please upload an image:</p>
            <form role="form" method="POST" 
                  action="addPicture" 
                  enctype="multipart/form-data">
                <div class="form-group">
                    <label for="displayTitle">Display Title:</label>
                    <input type="text" 
                           id="displayTitle" 
                           name="displayTitle"/>
                </div>
                <div class="form-group">
                    <label for="picture">Upload File:</label> 
                    <input type="file" 
                           id="picture" 
                           name="picture"/>
                </div>
                <input type="submit" value="Upload Picture"/>
            </form>

        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

