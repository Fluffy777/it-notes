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
    <title>Редагування категорії</title>

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
            <form id="update-category-form" class="col-auto border border-primary p-4 rounded my-5">
                <h1>Оновлення категорії</h1>
                <div class="mb-3">
                    <label class="form-label" for="select-category">Категорія</label>
                    <select class="form-select" name="category_id" id="select-category" required="required">
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="input-name">Нова назва</label>
                    <input class="form-control" type="text" name="name" id="input-name" required="required">
                </div>

                <!-- кнопки -->
                <div class="float-end mb-3">
                    <button class="btn btn-info" type="submit">Оновити</button>
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
            loadCategories();
            $("#select-category").change(function() {
                $("#input-name").val($("#select-category option:selected").text());
            });
            $("#update-category-form").submit(function(event) {
                event.preventDefault();
                doUpdate();
            });
        });

        function loadCategories() {
            let categories = '';
            $.ajax({
                url: baseUrl + "/api/categories",
                type: "GET",
                contentType: "application/json",

                success: function(result) {
                    $.each(result, function() {
                        categories += '<option value="' + this.id + '">' + this.name + '</option>';
                    });
                    $("#select-category").html(categories);
                    $("#input-name").val($("#select-category option:selected").text());
                },

                error: function(result) {

                }
            });
        }

        function doUpdate() {
            let category = {
                id: $("#select-category").val(),
                name: $("#input-name").val()
            };

            $.ajax({
                url: baseUrl + "/api/categories/" + category.id,
                type: "PUT",
                data: JSON.stringify(category),
                dataType: "json",
                contentType: "application/json",

                success: function(result) {
                    customAlert("success", "Успіх", 'Категорія "' + $("#select-category option:selected").text() + '" була оновлена');
                    $("#update-category-form").find("input, select, textarea, button").prop('disabled', true);
                    setTimeout(function() { window.location.href = "${pageContext.request.contextPath}/"; }, 1000);
                },

                error: function(result) {

                }
            });
        }
    </script>
</body>
</html>
