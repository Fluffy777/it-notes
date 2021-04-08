<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Fluffy">
    <meta name="description" content="Корисні статті із фрагментами коду, що допоможуть під час розробки Ваших проектів.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/static/img/common/favicon.ico"/>">
    <title>Авторизація</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.0/font/bootstrap-icons.css">

    <link rel="stylesheet" href="<c:url value="/resources/static/css/style.css"/>">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-auto">
                <form:form modelAttribute="logInForm" class="border border-primary p-4 rounded" action="${pageContext.request.contextPath}/login/processing" method="post">
                    <h1>Вхід до облікового запису</h1>
                    <c:if test='${param.get("error") == true}'>
                        <div class="text-danger mb-5">
                            <small>Електронна пошта або пароль вказані неправильно.</small>
                        </div>
                    </c:if>
                    <div class="mb-3">
                        <label class="form-label" for="input-email">Електронна пошта</label>
                        <form:input path="email" class="form-control" type="text" name="email" id="input-email" required="required"/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="input-password">Пароль</label>
                        <form:input path="password" class="form-control" type="password" name="password" id="input-password" required="required"/>
                    </div>
                    <button class="btn btn-success w-100 mb-3" type="submit">Авторизуватися</button>

                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/">На головну</a>
                        ·
                        <a href="${pageContext.request.contextPath}/signup">Реєстрація</a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>
