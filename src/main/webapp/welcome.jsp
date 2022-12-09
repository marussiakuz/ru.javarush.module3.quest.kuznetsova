<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Optional" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <title>WelcomePage</title>
    <link href="${contextPath}/resources/bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="//code.jquery.com/jquery.min.js"></script>
    <script>
        document.getElementById('userName').value = getCookie("userName");
    </script>
    <%
        String userName = null;
        Optional<Cookie> userNameCookieOptional = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("userName"))
                .findFirst();
        if(userNameCookieOptional.isPresent()) userName = userNameCookieOptional.get().getValue();
    %>
</head>
<body>

<!-- Section: Design Block -->
<section class="text-center">
    <!-- Background image -->
    <div class="p-5 bg-image"  style="
        background-image: url('/resources/abduction.gif');
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
                    <form  id="form-inline" action="user" method="POST">
                        <div class="form-group align-content-center mx-sm-3 mb-2">
                            <span style="vertical-align: inherit">
                                <span style="vertical-align: inherit">
                            <label for="userName" class="form-label">What's your name? </label>
                                </span>
                            </span>
                            <input value="<% if(userName != null) out.print(userName); %>" name="userName" type="text" class="form-label align-items-center w-50" id="userName" minLength="3" maxlength="20" placeholder="Please enter your username">
                        </div>
                        <button type="submit" class="btn btn-primary mb-2">Start Quest</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Section: Design Block -->
</body>
<script>

    const input = document.querySelector("#inputUserName");

    function checkInput() {
        let value = document.getElementById("inputUserName").value;

        if (value.length < 2 || value.length > 15) {
            input.classList.remove("is-valid");
            input.classList.add("is-invalid");
        } else {
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
        }
    }

    function getCookie(c_name) {
        let c_start;
        let c_end;
        if (document.cookie.length > 0) {
            c_start = document.cookie.indexOf(c_name + "=");
            if (c_start !== -1) {
                c_start = c_start + c_name.length + 1;
                c_end = document.cookie.indexOf(";", c_start);
                if (c_end === -1) {
                    c_end = document.cookie.length;
                }
                return decodeURI(document.cookie.substring(c_start, c_end));
            }
        }
        return "";
    }

</script>
</html>