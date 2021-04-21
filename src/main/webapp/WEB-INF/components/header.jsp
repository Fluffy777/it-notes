<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<header class="header mb-4">
    <div class="container-fluid d-flex">
        <div class="navbar navbar-expand-lg">
            <a class="navbar-brand" href="<c:url value="/"/>">
                <img class="header__logo" src="<c:url value="/resources/static/img/common/logo.png"/>" alt="Логотип">
            </a>
            <a class="header__title" href="<c:url value="/"/>">
                Нотатки програміста
            </a>
        </div>
        <ul class="navbar-nav ms-auto flex-row">
            <security:authorize access="hasRole('USER') and hasRole('ADMIN')">
                <li class="nav-item align-self-center me-4">
                    <a class="nav-link p-1" href="#" data-bs-toggle="dropdown" style="border-radius: 7px;">
                        <i class="header__nav-item-content bi bi-menu-button-wide-fill"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li> <a class="dropdown-item" href="#">Користувачі &raquo;</a>
                            <ul class="submenu submenu-left dropdown-menu">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/users/delete">Видалити</a></li>
                            </ul>
                        </li>
                        <li> <a class="dropdown-item" href="#">Категорії &raquo;</a>
                            <ul class="submenu submenu-left dropdown-menu">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/categories/create">Створити</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/categories/edit">Редагувати</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/categories/delete">Видалити</a></li>
                            </ul>
                        </li>
                        <li><a class="dropdown-item" href="#">Статті &raquo;</a>
                            <ul class="submenu submenu-left dropdown-menu">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/articles/create">Створити</a></li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </security:authorize>
            <li class="nav-item align-self-center me-4">
                <a class="header__nav-item col" data-bs-toggle="modal" data-bs-target="#modal">
                    <i class="header__nav-item-content bi bi-info-circle float-end"></i>
                </a>
            </li>
            <li class="nav-item align-self-center btn-group">
                <div class="header__nav-item col-auto" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="header__nav-item-content bi bi-person-circle float-end"></i>
                </div>
                <ul class="dropdown-menu dropdown-menu-end">
                    <security:authorize access="!isAnonymous()">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Профіль</a></li>
                    </security:authorize>
                    <security:authorize access="isAnonymous()">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/login">Авторизація</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/signup">Реєстрація</a></li>
                    </security:authorize>
                    <security:authorize access="!isAnonymous()">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Вихід</a></li>
                    </security:authorize>
                </ul>
            </li>
        </ul>
    </div>
</header>

<jsp:include page="/WEB-INF/components/about-modal.jsp"/>