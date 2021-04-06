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
    <title>Реєстрація</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.0/font/bootstrap-icons.css">

    <link rel="stylesheet" href="<c:url value="/resources/static/css/style.css"/>">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-auto">
                <form:form modelAttribute="signUpForm" class="border border-primary p-4 rounded" action="signup/request" method="POST">
                    <h1>Створення облікового запису</h1>
                    <div class="row mb-3">
                        <div class="col">
                            <label class="form-label" for="input-first-name">Ім'я</label>
                            <form:input path="firstName" class="form-control" type="text" name="first-name" id="input-first-name" required="required"/>
                        </div>
                        <div class="col">
                            <label class="form-label" for="input-last-name">Прізвище</label>
                            <form:input path="lastName" class="form-control" type="text" name="last-name" id="input-last-name" required="required"/>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col">
                            <p class="mb-2">Стать</p>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="gender" class="form-check-input" name="gender" id="male" value="male" required="required"/>
                                <label class="form-check-label" for="male">Чоловік</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="gender" class="form-check-input" name="gender" id="female" value="female" required="required"/>
                                <label class="form-check-label" for="female">Жінка</label>
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="input-email">Електронна пошта</label>
                        <form:input path="email" class="form-control" type="text" name="email" id="input-email" required="required"/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="input-password">Пароль</label>
                        <form:input path="password" class="form-control" type="password" name="password" id="input-password" required="required"/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="input-bday">Дата народження</label>
                        <form:input path="bday" class="form-control" type="text" name="bday" id="input-bday" placeholder="dd/mm/yyyy"/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="input-address">Адреса</label>
                        <form:input path="address" class="form-control" type="text" name="address" id="input-address"/>
                    </div>
                    <button class="btn btn-success w-100" type="submit">Зареєструватися</button>
                </form:form>
            </div>
        </div>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>
