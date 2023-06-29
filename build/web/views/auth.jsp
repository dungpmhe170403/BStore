<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>MYW</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" href="./css/auth.css">
</head>
<body>
<section class="ftco-section">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6 text-center mb-5">
                <h2 class="heading-section">
                    ${data['isLogin'] ? "Login" : "Register"}
                </h2>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-5">
                <div class="login-wrap p-4 p-md-5">
                    <div class="icon d-flex align-items-center justify-content-center">
                        <span class="fa fa-user-o"></span>
                    </div>
                    <h3 class="text-center mb-4"><a
                            href="${data['isLogin'] ? "./signup" : "./login"}">${data['isLogin'] ? "Not having Account?" : "Already Have Account"}</a>
                    </h3>
                    <form action="${data['isLogin'] ? "./login" : "./signup"}" class="login-form" method="post">
                        <c:if test="${not data['isLogin']}">
                            <div class="form-group">
                                <input type="text" class="form-control rounded-left" placeholder="Username"
                                       name="username" required>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <input type="text" class="form-control rounded-left" placeholder="Email" name="email"
                                   required>
                        </div>
                        <div class="form-group d-flex">
                            <input type="password" name="password" class="form-control rounded-left" placeholder="Password" required>
                        </div>
                        <c:if test="${not data['isLogin']}">
                            <div class="form-group d-flex">
                                <input type="password" class="form-control rounded-left"
                                       placeholder="Password Confirmation" name="password_confirmation" required>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary rounded submit p-3 px-5">Get Started</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="./js/jquery.min.js"></script>
<script src="./js/popper.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/main.js"></script>

</body>
</html>
