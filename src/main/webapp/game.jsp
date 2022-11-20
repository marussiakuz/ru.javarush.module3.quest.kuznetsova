<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<head>
    <title>ProcessQuest</title>
    <link href="${contextPath}/resources/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<jsp:useBean id="userInfo" scope="session" type="ru.javarush.quest.model.dto.UserShortDto"/>
<jsp:useBean id="currentStep" scope="session" type="ru.javarush.quest.model.dto.StepOutDto"/>
<jsp:useBean id="countOfStep" scope="session" type="java.lang.Integer"/>
<jsp:useBean id="failureCount" scope="session" type="java.lang.Integer"/>
<jsp:useBean id="winCount" scope="session" type="java.lang.Integer"/>
<jsp:useBean id="currentQuest" scope="session" type="ru.javarush.quest.model.dto.QuestOutDto"/>
<div class="container-fluid bg-info">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3><span class="label label-warning" id="qid">${countOfStep}</span> ${currentStep.question}</h3>
            </div>
            <div class="modal-body">
                <div class="col-xs-3 col-xs-offset-5">
                    <div id="loadbar" style="display: none;">
                        <div class="blockG" id="rotateG_01"></div>
                        <div class="blockG" id="rotateG_02"></div>
                        <div class="blockG" id="rotateG_03"></div>
                        <div class="blockG" id="rotateG_04"></div>
                        <div class="blockG" id="rotateG_05"></div>
                        <div class="blockG" id="rotateG_06"></div>
                        <div class="blockG" id="rotateG_07"></div>
                        <div class="blockG" id="rotateG_08"></div>
                    </div>
                </div>

                <div class="quiz" id="quiz" data-toggle="buttons">
                    <c:forEach items="${currentStep.choices}" var="choice" varStatus="status">
                    <label onclick="window.location='/quest/${currentQuest.id}?choice=${choice.id}'" class="element-animation${status.count} btn btn-lg btn-primary btn-block"><span class="btn-label"><i class="glyphicon glyphicon-chevron-right"></i></span> <input type="radio" name="q_answer" value=${choice.id}> ${choice.answer}</label>
                    </c:forEach>
                </div>
            </div>
            <div class="modal-footer text-muted">
                <span id="answer"></span>
            </div>
        </div>
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
                <p><b>Quests finished:</b> ${failureCount + winCount}</p>
                <p><b>Won:</b> ${winCount}</p>
                <p><b>Lost:</b> ${failureCount}</p>
                <p><b>Current Quest:</b> ${currentQuest.name} </p>
                <p><b>Current Step:</b> ${countOfStep}</p>
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

    #qid {
        padding: 10px 15px;
        -moz-border-radius: 50px;
        -webkit-border-radius: 50px;
        border-radius: 20px;
    }
    label.btn {
        padding: 18px 60px;
        white-space: normal;
        -webkit-transform: scale(1.0);
        -moz-transform: scale(1.0);
        -o-transform: scale(1.0);
        -webkit-transition-duration: .3s;
        -moz-transition-duration: .3s;
        -o-transition-duration: .3s
    }
    label.btn:hover {
        text-shadow: 0 3px 2px rgba(0,0,0,0.4);
        -webkit-transform: scale(1.1);
        -moz-transform: scale(1.1);
        -o-transform: scale(1.1)
    }
    label.btn-block {
        text-align: left;
        position: relative
    }
    label .btn-label {
        position: absolute;
        left: 0;
        top: 0;
        display: inline-block;
        padding: 0 10px;
        background: rgba(0,0,0,.15);
        height: 100%
    }
    label .glyphicon {
        top: 34%
    }
    .element-animation1 {
        animation: animationFrames ease .8s;
        animation-iteration-count: 1;
        transform-origin: 50% 50%;
        -webkit-animation: animationFrames ease .8s;
        -webkit-animation-iteration-count: 1;
        -webkit-transform-origin: 50% 50%;
        -ms-animation: animationFrames ease .8s;
        -ms-animation-iteration-count: 1;
        -ms-transform-origin: 50% 50%
    }
    .element-animation2 {
        animation: animationFrames ease 1s;
        animation-iteration-count: 1;
        transform-origin: 50% 50%;
        -webkit-animation: animationFrames ease 1s;
        -webkit-animation-iteration-count: 1;
        -webkit-transform-origin: 50% 50%;
        -ms-animation: animationFrames ease 1s;
        -ms-animation-iteration-count: 1;
        -ms-transform-origin: 50% 50%
    }
    .element-animation3 {
        animation: animationFrames ease 1.2s;
        animation-iteration-count: 1;
        transform-origin: 50% 50%;
        -webkit-animation: animationFrames ease 1.2s;
        -webkit-animation-iteration-count: 1;
        -webkit-transform-origin: 50% 50%;
        -ms-animation: animationFrames ease 1.2s;
        -ms-animation-iteration-count: 1;
        -ms-transform-origin: 50% 50%
    }
    @keyframes animationFrames {
        0% {
            opacity: 0;
            transform: translate(-1500px,0px)
        }
        60% {
            opacity: 1;
            transform: translate(30px,0px)
        }
        80% {
            transform: translate(-10px,0px)
        }
        100% {
            opacity: 1;
            transform: translate(0px,0px)
        }
    }
    @-webkit-keyframes animationFrames {
        0% {
            opacity: 0;
            -webkit-transform: translate(-1500px,0px)
        }
        60% {
            opacity: 1;
            -webkit-transform: translate(30px,0px)
        }
        80% {
            -webkit-transform: translate(-10px,0px)
        }
        100% {
            opacity: 1;
            -webkit-transform: translate(0px,0px)
        }
    }
    @-ms-keyframes animationFrames {
        0% {
            opacity: 0;
            -ms-transform: translate(-1500px,0px)
        }
        60% {
            opacity: 1;
            -ms-transform: translate(30px,0px)
        }
        80% {
            -ms-transform: translate(-10px,0px)
        }
        100% {
            opacity: 1;
            -ms-transform: translate(0px,0px)
        }
    }
    .modal-header {
        background-color: transparent;
        color: inherit
    }
    .modal-body {
        min-height: 205px
    }
    #loadbar {
        position: absolute;
        width: 62px;
        height: 77px;
        top: 2em
    }
    .blockG {
        position: absolute;
        background-color: #FFF;
        width: 10px;
        height: 24px;
        -moz-border-radius: 8px 8px 0 0;
        -moz-transform: scale(0.4);
        -moz-animation-name: fadeG;
        -moz-animation-duration: .8800000000000001s;
        -moz-animation-iteration-count: infinite;
        -moz-animation-direction: initial;
        -webkit-border-radius: 8px 8px 0 0;
        -webkit-transform: scale(0.4);
        -webkit-animation-name: fadeG;
        -webkit-animation-duration: .8800000000000001s;
        -webkit-animation-iteration-count: infinite;
        -webkit-animation-direction: initial;
        -ms-border-radius: 8px 8px 0 0;
        -ms-transform: scale(0.4);
        -ms-animation-name: fadeG;
        -ms-animation-duration: .8800000000000001s;
        -ms-animation-iteration-count: infinite;
        -ms-animation-direction: linear;
        -o-border-radius: 8px 8px 0 0;
        -o-transform: scale(0.4);
        -o-animation-name: fadeG;
        -o-animation-duration: .8800000000000001s;
        -o-animation-iteration-count: infinite;
        -o-animation-direction: initial;
        border-radius: 8px 8px 0 0;
        transform: scale(0.4);
        animation-name: fadeG;
        animation-duration: .8800000000000001s;
        animation-iteration-count: infinite;
        animation-direction: initial;
    }
    #rotateG_01 {
        left: 0;
        top: 28px;
        -moz-animation-delay: .33s;
        -moz-transform: rotate(-90deg);
        -webkit-animation-delay: .33s;
        -webkit-transform: rotate(-90deg);
        -ms-animation-delay: .33s;
        -ms-transform: rotate(-90deg);
        -o-animation-delay: .33s;
        -o-transform: rotate(-90deg);
        animation-delay: .33s;
        transform: rotate(-90deg)
    }
    #rotateG_02 {
        left: 8px;
        top: 10px;
        -moz-animation-delay: .44000000000000006s;
        -moz-transform: rotate(-45deg);
        -webkit-animation-delay: .44000000000000006s;
        -webkit-transform: rotate(-45deg);
        -ms-animation-delay: .44000000000000006s;
        -ms-transform: rotate(-45deg);
        -o-animation-delay: .44000000000000006s;
        -o-transform: rotate(-45deg);
        animation-delay: .44000000000000006s;
        transform: rotate(-45deg)
    }
    #rotateG_03 {
        left: 26px;
        top: 3px;
        -moz-animation-delay: .55s;
        -moz-transform: rotate(0deg);
        -webkit-animation-delay: .55s;
        -webkit-transform: rotate(0deg);
        -ms-animation-delay: .55s;
        -ms-transform: rotate(0deg);
        -o-animation-delay: .55s;
        -o-transform: rotate(0deg);
        animation-delay: .55s;
        transform: rotate(0deg)
    }
    #rotateG_04 {
        right: 8px;
        top: 10px;
        -moz-animation-delay: .66s;
        -moz-transform: rotate(45deg);
        -webkit-animation-delay: .66s;
        -webkit-transform: rotate(45deg);
        -ms-animation-delay: .66s;
        -ms-transform: rotate(45deg);
        -o-animation-delay: .66s;
        -o-transform: rotate(45deg);
        animation-delay: .66s;
        transform: rotate(45deg)
    }
    #rotateG_05 {
        right: 0;
        top: 28px;
        -moz-animation-delay: .7700000000000001s;
        -moz-transform: rotate(90deg);
        -webkit-animation-delay: .7700000000000001s;
        -webkit-transform: rotate(90deg);
        -ms-animation-delay: .7700000000000001s;
        -ms-transform: rotate(90deg);
        -o-animation-delay: .7700000000000001s;
        -o-transform: rotate(90deg);
        animation-delay: .7700000000000001s;
        transform: rotate(90deg)
    }
    #rotateG_06 {
        right: 8px;
        bottom: 7px;
        -moz-animation-delay: .8800000000000001s;
        -moz-transform: rotate(135deg);
        -webkit-animation-delay: .8800000000000001s;
        -webkit-transform: rotate(135deg);
        -ms-animation-delay: .8800000000000001s;
        -ms-transform: rotate(135deg);
        -o-animation-delay: .8800000000000001s;
        -o-transform: rotate(135deg);
        animation-delay: .8800000000000001s;
        transform: rotate(135deg)
    }
    #rotateG_07 {
        bottom: 0;
        left: 26px;
        -moz-animation-delay: .99s;
        -moz-transform: rotate(180deg);
        -webkit-animation-delay: .99s;
        -webkit-transform: rotate(180deg);
        -ms-animation-delay: .99s;
        -ms-transform: rotate(180deg);
        -o-animation-delay: .99s;
        -o-transform: rotate(180deg);
        animation-delay: .99s;
        transform: rotate(180deg)
    }
    #rotateG_08 {
        left: 8px;
        bottom: 7px;
        -moz-animation-delay: 1.1s;
        -moz-transform: rotate(-135deg);
        -webkit-animation-delay: 1.1s;
        -webkit-transform: rotate(-135deg);
        -ms-animation-delay: 1.1s;
        -ms-transform: rotate(-135deg);
        -o-animation-delay: 1.1s;
        -o-transform: rotate(-135deg);
        animation-delay: 1.1s;
        transform: rotate(-135deg)
    }
    @-moz-keyframes fadeG {
        0% {
            background-color: #000
        }
        100% {
            background-color: #FFF
        }
    }
    @-webkit-keyframes fadeG {
        0% {
            background-color: #000
        }
        100% {
            background-color: #FFF
        }
    }
    @-ms-keyframes fadeG {
        0% {
            background-color: #000
        }
        100% {
            background-color: #FFF
        }
    }
    @-o-keyframes fadeG {
        0% {
            background-color: #000
        }
        100% {
            background-color: #FFF
        }
    }
    @keyframes fadeG {
        0% {
            background-color: #000
        }
        100% {
            background-color: #FFF
        }
    }
</style>
<script>
</script>
</body>