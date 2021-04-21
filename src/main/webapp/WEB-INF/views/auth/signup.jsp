<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
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
                <form:form modelAttribute="signUpForm" class="border border-primary p-4 rounded" action="${pageContext.request.contextPath}/signup" method="post">
                    <h1>Створення облікового запису</h1>
                    <div class="row mb-3">
                        <div class="col">
                            <label class="form-label" for="input-first-name">Ім'я</label>
                            <form:input path="firstName" class="form-control" type="text" name="first-name" id="input-first-name" placeholder="John" maxlength="50" required="required"/>
                            <form:errors path="firstName"/>
                        </div>
                        <div class="col">
                            <label class="form-label" for="input-last-name">Прізвище</label>
                            <form:input path="lastName" class="form-control" type="text" name="last-name" id="input-last-name" placeholder="Doe" maxlength="50" required="required"/>
                            <form:errors path="lastName"/>
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
                            <form:errors path="gender"/>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="input-email">Електронна пошта</label>
                        <form:input path="email" class="form-control" type="text" name="email" id="input-email" placeholder="john_doe@example.com" maxlength="320" required="required"/>
                        <form:errors path="email"/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="input-password">Пароль</label>
                        <form:input path="password" class="form-control" type="password" name="password" id="input-password" placeholder="8+ символів (букви обох регістрів, цифри)" maxlength="60" required="required"/>
                        <form:errors path="password"/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="input-bday">Дата народження</label>
                        <form:input path="bday" class="form-control" type="text" name="bday" id="input-bday" placeholder="dd/mm/yyyy" maxlength="10"/>
                        <form:errors path="bday"/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="input-address">Адреса</label>
                        <form:input path="address" class="form-control" type="text" name="address" id="input-address" placeholder="Місто та країна"/>
                        <form:errors path="address"/>
                    </div>
                    <button class="btn btn-success w-100 mb-3" type="submit">Зареєструватися</button>

                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/">На головну</a>
                        ·
                        <a href="${pageContext.request.contextPath}/login">Авторизація</a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

    <script src="<c:url value="/resources/static/js/signup-form-helper.js"/>"></script>

    <!-- JQuery -->
    <script src="<c:url value="/resources/static/js/jquery-3.6.0.min.js"/>"></script>

    <!-- alert -->
    <jsp:include page="/WEB-INF/components/alert.jsp"/>
    <script src="<c:url value="/resources/static/js/alert.js"/>"></script>

    <script>
        $(document).ready(function() {
            initValidation();
        });

        function initValidation() {
            $("form").submit(function (event) {
                let message = validate();
                if (message !== "") {
                    customAlert("danger", "Помилка", message);
                    event.preventDefault();
                }
            });
        }

        function validate() {
            let firstName = $("#input-first-name").val();
            let lastName = $("#input-last-name").val();
            let email = $("#input-email").val();
            let password = $("#input-password").val();
            let bday = $("#input-bday").val();
            let message = "";

            if (firstName.match(/^[A-ZА-Я][a-zа-яA-ZА-Я-]{1,48}[a-zа-яA-ZА-Я]$/) == null) {
                message += "Ім'я не відповідає формату.";
            }

            if (lastName.match(/^[A-ZА-Я][a-zа-яA-ZА-Я-]{1,48}[a-zа-яA-ZА-Я]$/ == null)) {
                if (message !== "") {
                    message += "<br>";
                }
                message += "Прізвище не відповідає формату.";
            }

            if (email.match(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/) == null) {
                if (message !== "") {
                    message += "<br>";
                }
                message += "Електронна пошта не відповідає формату.";
            }

            if (password.match(/(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/) == null) {
                if (message !== "") {
                    message += "<br>";
                }
                message += "Пароль не відповідає формату.";
            }

            if (bday.match(/^(?:(?:(?:0[1-9]|1\d|2[0-8])\/(?:0[1-9]|1[0-2])|(?:29|30)\/(?:0[13-9]|1[0-2])|31\/(?:0[13578]|1[02]))\/[1-9]\d{3}|29\/02(?:\/[1-9]\d(?:0[48]|[2468][048]|[13579][26])|(?:[2468][048]|[13579][26])00))$/) == null) {
                if (message !== "") {
                    message += "<br>";
                }
                message += "Дата народження не відповідає формату.";
            }
            return message;
        }
    </script>
</body>
</html>
