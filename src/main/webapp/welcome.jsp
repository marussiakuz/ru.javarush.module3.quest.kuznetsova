<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>Insert title here</title>
    <script src="//code.jquery.com/jquery.min.js"></script>
    <link href="${contextPath}/resources/bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<!-- Section: Design Block -->
<section class="text-center">
    <!-- Background image -->
    <div class="p-5 bg-image" style="
        background-image: url('https://mdbootstrap.com/img/new/textures/full/171.jpg');
        height: 300px;
        "></div>
    <!-- Background image -->

    <div class="card mx-4 mx-md-5 shadow-5-strong" style="
        margin-top: -100px;
        background: hsla(0, 0%, 100%, 0.8);
        backdrop-filter: blur(30px);
        ">
        <div class="card-body py-5 px-md-5">
            <div class="row d-flex justify-content-center">
                <div class="col-lg-8">
                    <h2 class="fw-bold mb-5">Are you ready to plunge into the abyss of adventure?</h2>
                    <form  id="form_create" action="user" method="GET">
                        <!-- UserName input -->
                        <div class="form-outline mb-2">
                            <label class="form-label" for="create_name">What's your name</label>
                            <input type="text" name="userName" id="create_name" minLength="1" maxlength="12" class="form-control" placeholder="Please enter your username"/>
                        </div>
                        <!-- Go to the quest button -->
                        <button type="submit" class="btn btn-primary btn-block mb-2">Start Quest</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Section: Design Block -->
</body>
<script>

</script>
</html>