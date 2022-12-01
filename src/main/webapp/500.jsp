<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
  <title>500</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link href="${contextPath}/resources/bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background: none;
      padding-left: 15px;
      padding-right: 15px;
    }

    html {
      background: url(/resources/500_error.png) no-repeat center center fixed;
      -webkit-background-size: cover;
      -moz-background-size: cover;
      -o-background-size: cover;
      background-size: cover;
    }
    div {
      text-align: center;
      vertical-align: center;
    }
    p::before {
      text-align: center;
      color: #262626;
      float: right;
      content: '';
    }
  </style>
</head>
<body>
<div>
<br>
<p class="h1 text-center">We already know about the problem, and will fix it soon</p>
<br>
<div>
  <a href="http://localhost:8080/welcome.jsp">Go back home</a>
</div>
</div>
</body>
</html>
