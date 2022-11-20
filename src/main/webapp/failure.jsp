<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ru">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Failure</title>
    <link href="${contextPath}/resources/bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: none;
            padding-left: 15px;
            padding-right: 15px;
        }
        .content {
            position: fixed;
            bottom: 0;
            background: rgba(0, 0, 0, 0.5);
            color: #f1f1f1;
            width: 100%;
            padding: 20px;
        }
        html {
            background: url(/resources/failure.webp) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }
    </style>
</head>

<body>
<jsp:useBean id="text" scope="session" type="java.lang.String"/>
<jsp:useBean id="questId" scope="request" type="java.lang.Long"/>
<div class="content">
    <h1 align="center" class="h4 text-center mt-0 mb-4">${text}</h1>
    <h2 align="center" class="h5 text-center">You are looser!</h2>
</div>
<div class="d-grid gap-2 d-md-flex justify-content-md-end"  style="position:fixed;bottom:5px;right:5px;margin:0;padding:5px 3px;">
    <button onclick="window.location='/quest/${questId}'" type="button" class="btn btn-primary">Try again</button>
    <button onclick="window.location='/quest'" type="button" class="btn btn-secondary">Try another quest</button>
</div>
</body>
</html>
