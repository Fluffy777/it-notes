<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<header class="header mb-4">
    <nav class="header__nav navbar row p-2">
        <a class="header__logo-col col-auto" href="<c:url value="/"/>">
            <img class="header__logo" src="<c:url value="/resources/static/img/common/logo.png"/>" alt="Логотип">
        </a>
        <a class="header__title col-auto align-self-center" href="<c:url value="/"/>">
            Нотатки програміста
        </a>
        <a class="header__nav-item col" data-bs-toggle="modal" data-bs-target="#modal">
            <i class="header__nav-item-content bi bi-info-circle float-end"></i>
        </a>
        <div class="btn-group col-auto">
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
        </div>
    </nav>
</header>

<jsp:include page="/WEB-INF/components/about-modal.jsp"/>