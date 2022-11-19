<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${contextPath}/resources/bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial;
            font-size: 17px;
        }

        #myVideo {
            position: fixed;
            right: 0;
            bottom: 0;
            min-width: 100%;
            min-height: 100%;
        }

        .content {
            position: fixed;
            bottom: 0;
            background: rgba(0, 0, 0, 0.5);
            color: #f1f1f1;
            width: 100%;
            padding: 20px;
        }

        #myBtn {
            width: 200px;
            font-size: 18px;
            padding: 10px;
            border: none;
            background: #000;
            color: #fff;
            cursor: pointer;
        }

        #myBtn:hover {
            background: #ddd;
            color: black;
        }
    </style>
</head>
<body>
<jsp:useBean id="text" scope="session" type="java.lang.String"/>

<video autoplay muted loop id="myVideo">
    <source src="${pageContext.request.contextPath}/resources/Trophee_Zidler.mp4" type="video/mp4">
    Your browser does not support HTML5 video.
</video>

<div class="content">
    <p align="center">${text}</p>
    <h1 align="center">Congratulations!</h1>
</div>

<div class="d-grid gap-2 d-md-flex justify-content-md-end"  style="position:fixed;bottom:5px;right:5px;margin:0;padding:5px 3px;">
<button onclick="window.location='/quest'" type="button" class="btn btn-primary">Try another quest</button>
</div>

</body>
</html>
