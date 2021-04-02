<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header mb-4">
    <nav class="header__nav navbar row p-2">
        <a class="header__logo-col col-auto" href="<c:url value="/"/>">
            <img class="header__logo float-start" src="<c:url value="/resources/img/common/logo.png"/>" alt="Логотип">
        </a>
        <a class="header__title col-auto align-self-center" href="<c:url value="/"/>">
            Нотатки програміста
        </a>
        <div class="header__nav-item col">
            <i class="header__nav-item-content bi bi-info-circle float-end"></i>
        </div>
        <div class="header__nav-item col-auto">
            <i class="header__nav-item-content bi bi-person-circle float-end"></i>
        </div>
    </nav>
</header>