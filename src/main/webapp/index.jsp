<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">

    <title>Нотатки програміста</title>
<body>
    <div class="container-fluid">
        <!-- header -->
        <jsp:include page="/WEB-INF/components/header.jsp" />

        <div class="row mb-4">
            <div class="col-8 px-5">
                <!-- article -->
                <article class="card mb-5">
                    <h5 class="card-header">Featured</h5>
                    <section class="card-body">
                        <h5 class="card-title">Special title treatment</h5>
                        <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </section>
                </article>

                <article class="card">
                    <h5 class="card-header">Featured</h5>
                    <section class="card-body">
                        <h5 class="card-title">Special title treatment</h5>
                        <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </section>
                </article>

                <article class="card">
                    <h5 class="card-header">Featured</h5>
                    <section class="card-body">
                        <h5 class="card-title">Special title treatment</h5>
                        <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </section>
                </article>

                <article class="card">
                    <h5 class="card-header">Featured</h5>
                    <section class="card-body">
                        <h5 class="card-title">Special title treatment</h5>
                        <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </section>
                </article>
            </div>

            <aside class="col-4 pe-5">
                <div class="row mb-4">
                    <div class="col">
                        <h2>Популярне</h2>

                        <ul class="list-group">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Cras justo odio
                                <span class="float-end">10<i class="bi bi-eye ms-2"></i></span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Dapibus ac facilisis in
                                <span class="float-end">2<i class="bi bi-eye ms-2"></i></span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Morbi leo risus
                                <span class="float-end">1<i class="bi bi-eye ms-2"></i></span>
                            </li>
                        </ul>
                    </div>

                </div>

                <div class="row">
                    <div class="col">
                        <h2>Розділи</h2>
                        <ul class="list-group">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Cras justo odio
                                <span class="badge bg-primary">14</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Dapibus ac facilisis in
                                <span class="badge bg-primary">2</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Morbi leo risus
                                <span class="badge bg-primary">1</span>
                            </li>
                        </ul>
                    </div>
                </div>

            </aside>
        </div>


        <!-- pagination -->
        <nav aria-label="page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1" aria-disabled="true">«</a>
                </li>
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item">
                    <a class="page-link" href="#">»</a>
                </li>
            </ul>
        </nav>
    </div>

    <!-- footer -->
    <jsp:include page="/WEB-INF/components/footer.jsp" />

    <!-- nav -->
    <div style="position: fixed; bottom: 1em; right: 1em;">
        <i class="bi bi-arrow-up-square-fill" style="font-size: 200%;"></i>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>
