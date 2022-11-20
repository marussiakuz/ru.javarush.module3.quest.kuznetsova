<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="message" scope="request" type="java.lang.String"/>
<html>
<head>
    <title>400</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

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
            background: url(/resources/400.webp) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }
    </style>
</head>
<body>

<h1>${message}</h1>

</body>
</html>
