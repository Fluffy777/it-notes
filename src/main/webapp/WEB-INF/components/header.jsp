<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header mb-4">
    <nav class="header__nav navbar row p-2">
        <a class="header__logo-col col-auto" href="<c:url value="/"/>">
            <img class="header__logo float-start" src="<c:url value="/resources/static/img/common/logo.png"/>" alt="Логотип">
        </a>
        <a class="header__title col-auto align-self-center" href="<c:url value="/"/>">
            Нотатки програміста
        </a>
        <div class="header__nav-item col">
            <i class="header__nav-item-content bi bi-info-circle float-end"></i>
        </div>
        <div class="btn-group col-auto">
            <div class="header__nav-item col-auto" data-bs-toggle="dropdown" aria-expanded="false">
                <i class="header__nav-item-content bi bi-person-circle float-end"></i>
            </div>
            <ul class="dropdown-menu dropdown-menu-end">
                <li><button class="dropdown-item" type="button" data-bs-toggle="modal" data-bs-target="#authModal">Авторизація</button></li>
                <li><button class="dropdown-item" type="button" data-bs-toggle="modal" data-bs-target="#regModal">Реєстрація</button></li>
            </ul>
        </div>
    </nav>
</header>