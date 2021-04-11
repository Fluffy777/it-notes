<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html lang="uk">
    <meta charset="UTF-8">
    <meta name="author" content="Fluffy">
    <meta name="description" content="Корисні статті із фрагментами коду, що допоможуть під час розробки Ваших проектів.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/static/img/common/favicon.ico"/>">
    <title>Нотатки програміста</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.0/font/bootstrap-icons.css">

    <link rel="stylesheet" href="<c:url value="/resources/static/css/style.css"/>">
<body>
    <!-- header -->
    <jsp:include page="/WEB-INF/components/header.jsp"/>

    <div class="container-fluid">
        <div class="row mb-4">
            <div class="col-8 px-5">
                <!-- panel -->

                <div class="row mb-2" style="padding-left: 12px;">
                    <security:authorize access="hasRole('USER') and hasRole('ADMIN')">
                        <a class="col-auto control p-0" href="${pageContext.request.contextPath}/articles/create">
                            <i class="bi bi-plus-square-fill"></i>
                        </a>
                    </security:authorize>
                    <div class="col-auto control p-0" onclick="getAllArticles()">
                        <i class="bi bi-arrow-clockwise"></i>
                    </div>
                </div>

                <!-- articles -->
                <div class="row">
                    <div id="articles" class="col">
                    </div>
                </div>
            </div>

            <aside class="col-4 pe-5">
                <div class="row mb-4">
                    <div class="aside-col col">
                        <h2 class="aside-col__header">Популярне</h2>
                        <nav class="aside-col__nav">
                            <ul class="aside-col__list-group list-group">
                                <c:forEach var="article" items="${mostPopularArticles}">
                                    <li class="aside-col__list-group-item list-group-item d-flex justify-content-between align-items-center">
                                        <a class="aside-col__list-group-item-link" href="#">
                                            ${article.name}
                                            <span class="float-end">10<i class="bi bi-eye ms-2"></i></span>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
                    </div>
                </div>

                <div class="row">
                    <div class="aside-col col">
                        <h2 class="aside-col__header">Розділи</h2>
                        <nav class="aside-col__nav">
                            <ul class="aside-col__list-group list-group">
                                <c:forEach var="category" items="${categories}">
                                    <li class="aside-col__list-group-item list-group-item d-flex justify-content-between align-items-center">
                                        <a class="aside-col__list-group-item-link" href="#">
                                            ${category.name}
                                            <span class="badge bg-primary float-end">14</span>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
                    </div>
                </div>
            </aside>
        </div>

        <!-- pagination -->
        <jsp:include page="/WEB-INF/components/pagination.jsp"/>
    </div>

    <!-- footer -->
    <jsp:include page="/WEB-INF/components/footer.jsp"/>

    <!-- fixed controls -->
    <jsp:include page="/WEB-INF/components/fixed-controls.jsp"/>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

    <!-- JQuery -->
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    -->
    <script src="<c:url value="/resources/static/js/jquery-3.6.0.min.js"/>"></script>

    <script type="text/javascript">
        const baseUrl = location.protocol + "//" + location.host + "${pageContext.request.contextPath}";
        $(document).ready(function() {
            getAllArticles();
        });

        function getAllArticles() {
            let articles = "";
            $.ajax({
                url: baseUrl + "/api/articles",
                method: "GET",
                dataType: "json",
                contentType: "application/json",

                success: function(data) {
                    if (data.length === 0) {
                        articles = getAlertMessage("alert-warning", "Попередження:", "жодної статті не знайдено!");
                    }

                    $.each(data, function() {
                        articles = appendArticle(articles, this.category.name, this.name, this.content, this.id);
                    });

                    $("#articles").html(articles);
                },

                error: function () {
                    articles = getAlertMessage("alert-danger", "Помилка:", "сталася помилка під час отримання даних про статті!");
                    $("#articles").html(articles);
                }
            });
        }

        function appendArticle(articles, categoryName, name, content, id) {
            return articles + '<article class="article card mb-5">'
                            + '<h5 class="article__header card-header">' + categoryName + '</h5>'
                            + '<section class="article__body card-body">'
                            + '<h5 class="article__title card-title">' + name + '</h5>'
                            + '<p class="article__text card-text">' + content + '</p>'
                            + '<a class="btn btn-primary" href="${pageContext.request.contextPath}/articles/' + id + '">Читати далі</a>'
                            <security:authorize access="hasRole('USER') and hasRole('ADMIN')">
                            + '<a class="btn btn-secondary ms-2" href="${pageContext.request.contextPath}/articles/' + id + '">Редагувати</a>'
                            </security:authorize>
                            + '</section>'
                            + '</article>';
        }

        function getAlertMessage(type, title, content) {
            return '<div class="alert ' + type +' alert-dismissible fade show" role="alert">'
                    + '<strong>' + title + '</strong> ' + content
                    + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>'
                    + '</div>';
        }
    </script>
</body>
</html>
