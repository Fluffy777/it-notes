<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="uk">
    <meta charset="UTF-8">
    <meta name="author" content="Fluffy">
    <meta name="description" content="Корисні статті із фрагментами коду, що допоможуть під час розробки Ваших проектів.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Нотатки програміста</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">
<body>
    <!-- header -->
    <jsp:include page="/WEB-INF/components/header.jsp"/>

    <div class="container-fluid">
        <div class="row mb-4">
            <div class="col-8 px-5">
                <!-- article -->
                <article class="article card mb-5">
                    <h5 class="article__header card-header">Featured</h5>
                    <section class="article__body card-body">
                        <h5 class="article__title card-title">Special title treatment</h5>
                        <p class="article__text card-text">With supporting text below as a natural lead-in to additional content.</p>
                        <a class="btn btn-primary" href="#">Go somewhere</a>
                    </section>
                </article>

                <article class="article card mb-5">
                    <h5 class="article__header card-header">Featured</h5>
                    <section class="article__body card-body">
                        <h5 class="article__title card-title">Special title treatment</h5>
                        <p class="article__text card-text">With supporting text below as a natural lead-in to additional content.</p>
                        <a class="btn btn-primary" href="#">Go somewhere</a>
                    </section>
                </article>

                <article class="article card mb-5">
                    <h5 class="article__header card-header">Featured</h5>
                    <section class="article__body card-body">
                        <h5 class="article__title card-title">Special title treatment</h5>
                        <p class="article__text card-text">With supporting text below as a natural lead-in to additional content.</p>
                        <a class="btn btn-primary" href="#">Go somewhere</a>
                    </section>
                </article>

                <article class="article card mb-5">
                    <h5 class="article__header card-header">Featured</h5>
                    <section class="article__body card-body">
                        <h5 class="article__title card-title">Special title treatment</h5>
                        <p class="article__text card-text">With supporting text below as a natural lead-in to additional content.</p>
                        <a class="btn btn-primary" href="#">Go somewhere</a>
                    </section>
                </article>
            </div>

            <aside class="col-4 pe-5">
                <div class="row mb-4">
                    <div class="aside-col col">
                        <h2 class="aside-col__header">Популярне</h2>
                        <nav class="aside-col__nav">
                            <ul class="aside-col__list-group list-group">
                                <li class="aside-col__list-group-item list-group-item d-flex justify-content-between align-items-center">
                                    <a class="aside-col__list-group-item-link" href="#">
                                        Cras justo odio
                                        <span class="float-end">10<i class="bi bi-eye ms-2"></i></span>
                                    </a>
                                </li>
                                <li class="aside-col__list-group-item list-group-item d-flex justify-content-between align-items-center">
                                    <a class="aside-col__list-group-item-link" href="#">
                                        Dapibus ac facilisis in
                                        <span class="float-end">2<i class="bi bi-eye ms-2"></i></span>
                                    </a>
                                </li>
                                <li class="aside-col__list-group-item list-group-item d-flex justify-content-between align-items-center">
                                    <a class="aside-col__list-group-item-link" href="#">
                                        Morbi leo risus
                                        <span class="float-end">1<i class="bi bi-eye ms-2"></i></span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>

                <div class="row">
                    <div class="aside-col col">
                        <h2 class="aside-col__header">Розділи</h2>
                        <nav class="aside-col__nav">
                            <ul class="aside-col__list-group list-group">
                                <li class="aside-col__list-group-item list-group-item d-flex justify-content-between align-items-center">
                                    <a class="aside-col__list-group-item-link" href="#">
                                        Cras justo odio
                                        <span class="badge bg-primary float-end">14</span>
                                    </a>
                                </li>
                                <li class="aside-col__list-group-item list-group-item d-flex justify-content-between align-items-center">
                                    <a class="aside-col__list-group-item-link" href="#">
                                        Dapibus ac facilisis in
                                        <span class="badge bg-primary float-end">2</span>
                                    </a>
                                </li>
                                <li class="aside-col__list-group-item list-group-item d-flex justify-content-between align-items-center">
                                    <a class="aside-col__list-group-item-link" href="#">
                                        Morbi leo risus
                                        <span class="badge bg-primary float-end">1</span>
                                    </a>
                                </li>
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

    <!-- Auth modal -->
    <div class="modal fade" id="authModal" tabindex="-1" aria-labelledby="authModalLabel" aria-hidden="true" style="z-index: 10000;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="authModalLabel">Авторизація</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form class="p-1" action="auth.php" method="POST">
                        <div class="mb-3">
                            <label class="form-label" for="input-auth-name">Ім'я</label>
                            <input class="form-control" type="text" name="name" id="input-auth-name" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="input-auth-password">Пароль</label>
                            <input class="form-control" type="password" name="password" id="input-auth-password" required>
                        </div>

                        <button class="btn btn-primary" id="auth-submit-btn" type="submit" disabled>Авторизуватися</button>
                        <button class="btn btn-secondary" id="auth-reset-btn" type="reset">Очистити</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Reg modal -->
    <div class="modal fade" id="regModal" tabindex="-1" aria-labelledby="regModalLabel" aria-hidden="true" style="z-index: 10000;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="regModalLabel">Створення облікового запису</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form class="p-1" action="reg.php" method="POST">
                        <div class="mb-3">
                            <label class="form-label" for="input-reg-name">Ім'я</label>
                            <input class="form-control" type="text" name="name" id="input-reg-name" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="input-reg-password">Пароль</label>
                            <input class="form-control" type="password" name="password" id="input-reg-password" required>
                        </div>

                        <button class="btn btn-primary" id="reg-submit-btn" type="submit" disabled>Зареєструватися</button>
                        <button class="btn btn-secondary" id="reg-reset-btn" type="reset">Очистити</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>
