<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<head>
    <title>Title</title>
    <link href="${contextPath}/resources/bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h1 align="center">Quests</h1>

    <jsp:useBean id="quests" scope="request" type="java.util.List"/>
    <c:forEach items="${quests}" var="quest" varStatus="status">
        <a onclick="go_to_quest(${quest.id})" href="#" class="${status.index % 2 == 0? 'list-group-item list-group-item-action active' : 'list-group-item list-group-item-action'}" aria-current=true>
            <div class="d-flex w-100 justify-content-between">
                <h5 class="mb-1">${quest.name}</h5>
                <small>${quest.id}</small>
            </div>
            <p class="mb-1">${quest.description}</p>
            <small>And some small print.</small>
        </a>
    </c:forEach>
<script>

    function go_to_quest(id) {
        $.ajax({
            url: 'http://localhost:8080/quest?id=1',
            type: 'GET',
            contentType: 'application/json;charset=UTF-8',
            async: false,
            success: function () {
                window.location='/quest/' + id;
            }
        });
    }

</script>

</body>
</html>
