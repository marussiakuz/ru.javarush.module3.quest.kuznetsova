<%@ page contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>

<head>
    <title>QuestList</title>
    <link href="${contextPath}/resources/bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
</head>

<body>
<jsp:useBean id="quests" scope="request" type="java.util.List"/>
<jsp:useBean id="userInfo" scope="session" type="ru.javarush.quest.model.dto.UserShortDto"/>
<jsp:useBean id="isStart"  scope="session" type="java.lang.Boolean"/>
<% if (!isStart) { %>
<jsp:useBean id="countOfStep" scope="session" type="java.lang.Integer"/>
<jsp:useBean id="failureCount" scope="session" type="java.lang.Integer"/>
<jsp:useBean id="winCount" scope="session" type="java.lang.Integer"/>
<% if (countOfStep != 0) { %>
<jsp:useBean id="currentQuest" scope="session" type="ru.javarush.quest.model.dto.QuestOutDto"/>
<% }
%>
<% }
%>

<div class="row d-flex justify-content-center">
    <div class="col-md-6">
        <h3 class="text-muted" align="center">Quests</h3>
        <ul class="list-group">
            <c:forEach items="${quests}" var="quest" varStatus="status">
                <a onclick="go_to_quest(${quest.id})" href="#" class="${status.index % 2 == 0? 'list-group-item list-group-item-action active' : 'list-group-item list-group-item-action'}" aria-current=true>
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">${quest.name}</h5>
                        <small>${quest.id}</small>
                    </div>
                    <p class="mb-1">${quest.description}</p>
                    <small class="image-parent">
                        <img src="${quest.img}" class="img-fluid" alt="quixote">
                    </small>
                </a>
            </c:forEach>
        </ul>
    </div>
</div>

<a class="float-sm-right" data-toggle="modal" data-target="#exampleModal" style="position:fixed;bottom:5px;right:5px;margin:0;padding:5px 3px;" href="#">STATISTIC</a>

<!-- Modal -->
<div class="modal" tabindex="-1" role="dialog" id="exampleModal" data-bs-target="#staticBackdrop">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Statistic</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p><b>Name:</b> ${userInfo.name}</p>
                <p><b>IP address:</b> ${userInfo.ip}</p>
                <p><b>Quests finished:</b> ${isStart ? 0 : failureCount + winCount}</p>
                <p><b>Won:</b> ${isStart ? 0 : winCount}</p>
                <p><b>Lost:</b> ${isStart ? 0 : failureCount}</p>
                <p><b>Current Quest:</b> ${isStart || countOfStep == 0 ? 'any quest hasn\'t been selected yet' : currentQuest.name} </p>
                <p><b>Current Step:</b> ${isStart || countOfStep == 0 ? 'no quest has been started yet' : countOfStep}</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<style>
    .modal {
        background-color: white;
    }
</style>

<script>

    function go_to_quest(id) {
        console.log('http://localhost:8080/quest/'+ id)
        $.ajax({
            url: 'http://localhost:8080/quest/'+ id,
            type: 'GET',
            contentType: 'application/json;charset=UTF-8',
            async: false,
            success: function () {
                window.location='/quest/'+ id;
            }
        });
    }

</script>

</body>