<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Fluffy">
    <meta name="description" content="Корисні статті із фрагментами коду, що допоможуть під час розробки Ваших проектів.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/static/img/common/favicon.ico"/>">
    <title>Створення статті</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.0/font/bootstrap-icons.css">

    <link rel="stylesheet" href="<c:url value="/resources/static/css/style.css"/>">
</head>
<body>
    <!-- header -->
    <jsp:include page="/WEB-INF/components/header.jsp"/>

    <div class="container-fluid">
        <div class="row justify-content-center">
            <form:form modelAttribute="articleForm" class="col-9 border border-primary p-4 rounded" action="${pageContext.request.contextPath}/articles/create" method="post">
                <h1>Створення статті</h1>
                <div class="mb-3">
                    <label class="form-label" for="input-name">Назва</label>
                    <form:input path="name" class="form-control" type="text" name="name" id="input-name" required="required"/>
                    <form:errors path="name"/>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="input-category">Категорія</label>
                    <form:input path="category" class="form-control" type="text" name="category" id="input-category" required="required"/>
                    <form:errors path="category"/>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="input-content">Контент</label>
                    <form:textarea path="content" class="form-control" id="input-content" rows="10" required="required"/>
                    <form:errors path="content"/>
                </div>

                <!-- кнопки -->
                <div class="float-end mb-3">
                    <button class="btn btn-primary" type="submit">Створити</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/">Повернутися</a>
                </div>
            </form:form>
        </div>
    </div>


    <!-- footer -->
    <jsp:include page="/WEB-INF/components/footer.jsp"/>

    <!-- fixed controls -->
    <jsp:include page="/WEB-INF/components/fixed-controls.jsp"/>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>
