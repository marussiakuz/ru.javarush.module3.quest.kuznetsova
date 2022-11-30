<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <title>WinPage</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${contextPath}/resources/bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, emoji;
            font-size: 17px;
        }

        #winVideo {
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

        p, h1 {
            text-align: center;
        }
    </style>
</head>
<body>
<jsp:useBean id="text" scope="session" type="java.lang.String"/>

<video loop autoplay muted id="winVideo">
    <source src="${pageContext.request.contextPath}/resources/win.mp4" type="video/mp4">
    Your browser does not support HTML5 video.
</video>

<div class="content">
    <p>${text}</p>
    <h1>Congratulations!</h1>
</div>

<div class="d-grid gap-2 d-md-flex justify-content-md-end"  style="position:fixed;bottom:5px;right:5px;margin:0;padding:5px 3px;">
<button onclick="window.location='/quest'" type="button" class="btn btn-primary">Try another quest</button>
</div>

</body>
</html>
