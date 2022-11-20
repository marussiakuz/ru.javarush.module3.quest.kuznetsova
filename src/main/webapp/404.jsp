<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>404</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="${contextPath}/resources/bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: none;
            padding-left: 15px;
            padding-right: 15px;
        }

        html {
            background: url(/resources/404.webp) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }
    </style>
</head>


<br>
<br>
<h2 align="center" class="h5 text-center">Here may have been such a page, but it's no longer here..</h2>
<div align="center">
    <a href="./welcome.jsp">Go back home</a>
</div>


</body>
</html>
