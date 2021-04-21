<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Fluffy">
    <meta name="description" content="Корисні статті із фрагментами коду, що допоможуть під час розробки Ваших проектів.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/static/img/common/favicon.ico"/>">
    <title>Редагування статті</title>

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
            <form id="edit-article-form" class="col-9 border border-primary p-4 rounded">
                <h1>Редагування статті</h1>
                <div class="mb-3">
                    <label class="form-label" for="input-name">Назва</label>
                    <input class="form-control" type="text" name="name" id="input-name" placeholder="Назва" required="required"/>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="select-category">Категорія</label>
                    <div class="d-flex">
                        <select class="form-select" name="category" id="select-category">
                        </select>
                        <div class="col-auto control p-0" onclick="updateCategories()">
                            <i class="bi bi-arrow-clockwise"></i>
                        </div>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="input-content">Контент</label>
                    <textarea class="form-control" id="input-content" rows="10" placeholder="Уміст статті" required="required"></textarea>
                </div>

                <!-- кнопки -->
                <div class="float-end mb-3">
                    <button class="btn btn-primary" type="submit">Оновити</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/">Повернутися</a>
                </div>
            </form>
        </div>
    </div>

    <!-- footer -->
    <jsp:include page="/WEB-INF/components/footer.jsp"/>

    <!-- fixed controls -->
    <jsp:include page="/WEB-INF/components/fixed-controls.jsp"/>

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
        let persistent_id;
        let persistent_author;
        let persistent_views;

        let currentCategory;

        $(document).ready(function() {
            updateInputs(updateCategories());
            $("#edit-article-form").submit(function(event) {
                event.preventDefault();
                doPut();
            });
        });

        function doPut() {
            let article = {
                id: persistent_id,
                category: {
                    id: $("#select-category").val(),
                    name: $("#select-category option:selected").text()
                },
                user: persistent_author,
                name: $("#input-name").val(),
                content: $("#input-content").val(),
                views: persistent_views
            };

            $.ajax({
                url: baseUrl + "/api/articles/${articleId}",
                type: "PUT",
                data: JSON.stringify(article),
                contentType: "application/json",
                dataType: 'json',

                success: function (result) {
                    customAlert("success", "Успіх", 'Стаття "' + article.name + '" була оновлена');
                    $("#create-article-form").find("input, select, textarea, button").prop('disabled', true);
                    setTimeout(function() { window.location.href = "${pageContext.request.contextPath}/"; }, 1000);
                },

                error: function (result) {
                    customAlert("danger", "Помилка", 'Не вдалося оновити статтю "' + article.name + '"');
                }
            });
        }

        function updateInputs(categoriesRequest) {
            $.ajax({
                url: baseUrl + "/api/articles/${articleId}",
                type: "GET",
                contentType: "application/json",

                success: function(result) {
                    $("#input-name").val(result.name);
                    $("#input-content").val(result.content);

                    persistent_id = result.id;
                    persistent_author = result.user;
                    persistent_views = result.views;

                    $.when(categoriesRequest).done(function() {
                        currentCategory = result.category.id;
                        $("#select-category").val(result.category.id);
                    });
                },

                error: function(result) {
                    customAlert("danger", "Помилка", "Не вдалося отримати дані про статтю");
                }
            });
        }

        function updateCategories() {
            currentCategory = $("#select-category").val();
            let categories = '<option value="">Без категорії</option>';
            return $.ajax({
                url: baseUrl + "/api/categories",
                type: "GET",
                contentType: "application/json",

                success: function (result) {
                    if (result.length !== 0) {
                        $.each(result, function() {
                            categories = appendCategory(categories, this);
                        });
                    }
                    $("#select-category").html(categories);
                    $("#select-category").val(currentCategory);
                },

                error: function (result) {
                    customAlert("danger", "Помилка", "Не вдалося оновити список категорій");
                }
            });
        }

        function appendCategory(categories, category) {
            return categories + '<option value="' + category.id + '">' + category.name + '</option>';
        }
    </script>
</body>
</html>
