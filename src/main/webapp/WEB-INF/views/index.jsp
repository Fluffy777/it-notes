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
                <!-- admin panel -->
                <security:authorize access="hasRole('USER') and hasRole('ADMIN')">
                    <div class="row mb-2" style="padding-left: 12px;">
                        <a class="col-auto control p-0" href="${pageContext.request.contextPath}/articles/create">
                            <i class="bi bi-plus-square-fill"></i>
                        </a>
                    </div>
                </security:authorize>

                <!-- articles -->
                <div class="row">
                    <div class="col">
                        <c:forEach var="article" items="${articles}">
                            <article class="article card mb-5">
                                <h5 class="article__header card-header">${article.category.name}</h5>
                                <section class="article__body card-body">
                                    <h5 class="article__title card-title">${article.name}</h5>
                                    <p class="article__text card-text">${article.content}</p>
                                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/articles/${article.id}">Читати далі</a>
                                </section>
                            </article>
                        </c:forEach>
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
</body>
</html>
