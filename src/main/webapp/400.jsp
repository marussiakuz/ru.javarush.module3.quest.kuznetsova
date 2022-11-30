<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="message" scope="request" type="java.lang.String"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>400</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
    <style>
        body {
            background: none;
            padding-left: 15px;
            padding-right: 15px;
        }
        html {
            background: url(/resources/400.webp) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }
    </style>

<body>

<h1>${message}</h1>

</body>
</html>
