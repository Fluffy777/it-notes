<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Fluffy">
    <meta name="description" content="Корисні статті із фрагментами коду, що допоможуть під час розробки Ваших проектів.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/static/img/common/favicon.ico"/>">
    <title>Видалення користувача</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.0/font/bootstrap-icons.css">

    <link rel="stylesheet" href="<c:url value="/resources/static/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/static/css/multi-level-dropdown.css"/>">
</head>
<body>
    <!-- header -->
    <jsp:include page="/WEB-INF/components/header.jsp"/>

    <div class="container-fluid">
        <div class="row justify-content-center">
            <form id="delete-user-form" class="col-auto border border-primary p-4 rounded">
                <h1>Видалення користувача</h1>
                <div class="mb-3">
                    <label class="form-label" for="select-user">Користувач</label>
                    <select class="form-select" name="user_id" id="select-user" required="required">
                    </select>
                </div>

                <!-- кнопки -->
                <div class="float-end mb-3">
                    <button class="btn btn-warning" type="submit">Видалити</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/">Повернутися</a>
                </div>
            </form>
        </div>
    </div>

    <!-- footer -->
    <jsp:include page="/WEB-INF/components/footer.jsp"/>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

    <!-- JQuery -->
    <script src="<c:url value="/resources/static/js/jquery-3.6.0.min.js"/>"></script>

    <script src="<c:url value="/resources/static/js/multi-level-dropdown.js"/>"></script>

    <!-- alert -->
    <jsp:include page="/WEB-INF/components/alert.jsp"/>
    <script src="<c:url value="/resources/static/js/alert.js"/>"></script>

    <script>
        const baseUrl = location.protocol + "//" + location.host + "${pageContext.request.contextPath}";

        $(document).ready(function() {
            loadUsers();
            $("#delete-user-form").submit(function(event) {
                event.preventDefault();
                doDelete();
            });
        });

        function loadUsers() {
            let users = '';
            $.ajax({
                url: baseUrl + "/api/users",
                type: "GET",
                contentType: "application/json",

                success: function(result) {
                    $.each(result, function() {
                        users += '<option value="' + this.id + '">' + this.firstName + ' ' + this.lastName + ' (id = ' + this.id + ')' + '</option>';
                    });
                    $("#select-user").html(users);
                },

                error: function(result) {

                }
            });
        }

        function doDelete() {
            let user = {
                id: $("#select-user").val(),
                name: $("#select-user option:selected").text()
            };
            $.ajax({
                url: baseUrl + "/api/users/" + user.id,
                type: "DELETE",

                success: function(result) {
                    customAlert("success", "Успіх", 'Користувач "' + user.name + '" був видалений');
                    $("#delete-user-form").find("input, select, textarea, button").prop('disabled', true);
                    setTimeout(function() { window.location.href = "${pageContext.request.contextPath}/"; }, 1000);
                },

                error: function(result) {

                }
            });
        }
    </script>
</body>
</html>
