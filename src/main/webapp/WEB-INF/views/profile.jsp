<%@ page import="com.fluffy.spring.domain.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.springframework.core.env.Environment" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%
    User userData = (User) request.getAttribute("user");
    User.Gender gender = userData.getGender();

    Date bday = userData.getBday();
    String bdayValue = bday != null ? new SimpleDateFormat("dd/MM/yyyy").format(bday) : "";
%>

<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Fluffy">
    <meta name="description" content="Корисні статті із фрагментами коду, що допоможуть під час розробки Ваших проектів.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/static/img/common/favicon.ico"/>">
    <title><%=userData.getFirstName() + " " + userData.getLastName()%></title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.0/font/bootstrap-icons.css">

    <link rel="stylesheet" href="<c:url value="/resources/static/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/static/css/multi-level-dropdown.css"/>">
</head>
<body>
    <!-- header -->
    <jsp:include page="/WEB-INF/components/header.jsp"/>

    <div class="container">
        <div class="profile row">
            <div class="col-auto">
                <form:form modelAttribute="userDataForm" class="border border-primary p-4 rounded" action="${pageContext.request.contextPath}/profile" method="post" enctype="multipart/form-data">
                    <div class="col-auto mb-3">
                        <% if (gender.equals(User.Gender.MALE)) { %>
                            <img class="profile__logo mb-2" id="thumbnail" src="<%="icons/" + userData.getId() + "/" + userData.getIcon()%>" alt="Фото" onerror="this.onerror = null; this.src = `<c:url value="/resources/static/img/profile/male.png"/>`;">
                        <% } else { %>
                            <img class="profile__logo mb-2" id="thumbnail" src="<%="icons/" + userData.getId() + "/" + userData.getIcon()%>" alt="Фото" onerror="this.onerror = null; this.src = `<c:url value="/resources/static/img/profile/female.png"/>`;">
                        <% }%>
                        <form:input path="icon" class="form-control" type="file" name="icon" id="input-icon" accept="image/png, image/jpeg"/>
                        <form:errors path="icon"/>
                    </div>
                    <div class="col-auto">
                        <div class="row mb-3">
                            <div class="col">
                                <label class="form-label" for="input-first-name">Ім'я</label>
                                <form:input path="firstName" class="form-control" type="text" name="first-name" id="input-first-name" value="<%=userData.getFirstName()%>" maxlength="50" required="required"/>
                                <form:errors path="firstName"/>
                            </div>
                            <div class="col">
                                <label class="form-label" for="input-last-name">Прізвище</label>
                                <form:input path="lastName" class="form-control" type="text" name="last-name" id="input-last-name" value="<%=userData.getLastName()%>" maxlength="50" required="required"/>
                                <form:errors path="lastName"/>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col">
                                <p class="mb-2">Стать</p>
                                <% if(gender == User.Gender.MALE) { %>
                                <div class="form-check form-check-inline">
                                    <form:radiobutton path="gender" class="form-check-input" name="gender" id="male" value="male" checked="checked" required="required"/>
                                    <label class="form-check-label" for="male">Чоловік</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <form:radiobutton path="gender" class="form-check-input" name="gender" id="female" value="female" required="required"/>
                                    <label class="form-check-label" for="female">Жінка</label>
                                </div>
                                <% }else{ %>
                                <div class="form-check form-check-inline">
                                    <form:radiobutton path="gender" class="form-check-input" name="gender" id="male" value="male" required="required"/>
                                    <label class="form-check-label" for="male">Чоловік</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <form:radiobutton path="gender" class="form-check-input" name="gender" id="female" value="female" checked="checked" required="required"/>
                                    <label class="form-check-label" for="female">Жінка</label>
                                </div>
                                <%} %>
                                <form:errors path="gender"/>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="input-email">Електронна пошта</label>
                            <form:input path="email" class="form-control" type="text" name="email" id="input-email" value="<%=userData.getEmail()%>" maxlength="320" required="required"/>
                            <form:errors path="email"/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="input-password">Пароль</label>
                            <form:input path="password" class="form-control" type="password" name="password" id="input-password" maxlength="60"/>
                            <form:errors path="password"/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="input-new-password">Новий пароль</label>
                            <form:input path="newPassword" class="form-control" type="password" name="new-password" id="input-new-password" maxlength="60"/>
                            <form:errors path="newPassword"/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="input-new-password-again">Новий пароль (ще раз)</label>
                            <form:input path="newPasswordAgain" class="form-control" type="password" name="new-password-again" id="input-new-password-again" maxlength="60"/>
                            <form:errors path="newPasswordAgain"/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="input-bday">Дата народження</label>
                            <form:input path="bday" class="form-control" type="text" name="bday" id="input-bday" value='<%=bdayValue%>' placeholder="dd/mm/yyyy" maxlength="10"/>
                            <form:errors path="bday"/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="input-address">Адреса</label>
                            <form:input path="address" class="form-control" type="text" name="address" value="<%=userData.getAddress()%>" id="input-address" maxlength="128"/>
                            <form:errors path="address"/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="input-description">Опис</label>
                            <form:input path="description" class="form-control" type="text" name="description" value="<%=userData.getDescription()%>" id="input-description" maxlength="512"/>
                            <form:errors path="description"/>
                        </div>

                        <button class="btn btn-success w-100 mb-3" type="submit">Оновити дані</button>
                    </div>
                </form:form>
                <div class="text-center mb-4">
                    <a href="${pageContext.request.contextPath}/">На головну</a>
                </div>
            </div>
        </div>
    </div>

    <!-- footer -->
    <jsp:include page="/WEB-INF/components/footer.jsp"/>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

    <!-- JQuery -->
    <script src="<c:url value="/resources/static/js/jquery-3.6.0.min.js"/>"></script>

    <!-- alert -->
    <jsp:include page="/WEB-INF/components/alert.jsp"/>
    <script src="<c:url value="/resources/static/js/alert.js"/>"></script>

    <script>
        $(document).ready(function() {
            $('#input-icon').change(function() {
                showImageThumbnail(this);
            });

            function showImageThumbnail(fileInput) {
                file = fileInput.files[0];
                reader = new FileReader();
                reader.onload = function(e) {
                    $('#thumbnail').attr('src', e.target.result);
                }

                reader.readAsDataURL(file);
            }

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

            $("#input-bday").on("input", function() {
                let length = this.value.length;
                if (length === 2 || length === 5) {
                    this.value = this.value + "/";
                }
            });
        }

        function validate() {
            let firstName = $("#input-first-name").val();
            let lastName = $("#input-last-name").val();
            let email = $("#input-email").val();
            let password = $("#input-password").val();
            let newPassword = $("#input-new-password").val();
            let newPasswordAgain = $("#input-new-password-again").val();
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

            if (password !== "") {
                // користувач вирішив вказати пароль, що є необов'язковим для
                // звичайних змін, та обов'язковим під час зміни пароля

                if (newPassword === "" && newPasswordAgain === "") {
                    // користувач тоді просто ввів пароль без наміру його зміни
                    // можемо пароль ще раз перевірити введений пароль
                    if (password.match(/(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/) == null) {
                        message += "Пароль не відповідає формату.";
                    }
                } else if (newPassword === newPasswordAgain) {
                    // випадок, коли користувач вказує три паролі для зміни
                    // початкового (де password - початковий) - тоді треба
                    // перевірити один із них
                    if (newPassword.match(/(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/) == null) {
                        message += "Новий пароль не відповідає формату.";
                    }
                } else {
                    // newPassword та newPasswordAgain різні
                    message += "Паролі не співпадають";
                }
            } else {
                // користувач пароль не вказував
                if ((newPassword !== "") || (newPasswordAgain !== "")) {
                    // новий пароль не є порожнім - користувач намагається змінити
                    // пароль без вказання попереднього, що не можна (перевірка
                    // формату не є необхідною)
                    message += "Для зміни пароля треба вказати попередній.";
                }
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
