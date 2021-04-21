<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html lang="uk">
<head>
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
    <link rel="stylesheet" href="<c:url value="/resources/static/css/multi-level-dropdown.css"/>">
</head>
<body>
    <!-- header -->
    <jsp:include page="/WEB-INF/components/header.jsp"/>

    <div class="container-fluid">
        <div class="row mb-4">
            <div class="col-8 px-5">
                <!-- panel -->
                <div class="row mb-2" style="padding-left: 12px;">
                    <div class="col d-flex">
                        <i id="refresh-button" class="control bi bi-arrow-clockwise border border-2 rounded-circle p-2"></i>
                    </div>

                    <div class="col ms-auto">
                        <div class="row">
                            <div class="col d-flex">
                                <label class="form-label ms-auto me-2" for="select-page-size">Розмір сторінки:</label>
                                <select class="form-select w-auto" name="page-size" id="select-page-size">
                                    <option value="3" selected>3</option>
                                    <option value="5">5</option>
                                    <option value="10">10</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row my-4 border border-2 px-2 py-3">
                    <div class="col">
                        <h5 class="text-center mb-3"><i class="control bi bi-patch-question me-2" data-order-direction="" data-order-by="category_id"></i>Категорія</h5>
                        <select class="form-select w-auto mb-1" name="category-operation" id="select-category-operation">
                            <option value="" selected></option>
                            <option value="eq">=</option>
                            <option value="ne">!=</option>
                        </select>
                        <select class="form-control" name="category" id="select-category" disabled>
                        </select>
                    </div>

                    <div class="col">
                        <h5 class="text-center mb-3"><i class="control bi bi-patch-question me-2" data-order-direction="" data-order-by="user_id"></i>Користувач</h5>
                        <select class="form-select w-auto mb-1" name="user-operation" id="select-user-operation">
                            <option value="" selected></option>
                            <option value="eq">=</option>
                            <option value="ne">!=</option>
                        </select>
                        <select class="form-control" name="user" id="select-user" disabled>
                        </select>
                    </div>

                    <div class="col">
                        <h5 class="text-center mb-3"><i class="control bi bi-patch-question me-2" data-order-direction="" data-order-by="name"></i>Назва</h5>
                        <select class="form-select w-auto mb-1" name="name-operation" id="select-name-operation">
                            <option value="" selected></option>
                            <option value="eq">=</option>
                            <option value="ne">!=</option>
                            <option value="lt">&lt;</option>
                            <option value="gt">&gt;</option>
                            <option value="starts">починається з</option>
                            <option value="contains">містить</option>
                            <option value="ends">завершується на</option>
                        </select>
                        <input class="form-control" type="text" name="name" id="input-name" maxlength="100" disabled>
                    </div>

                    <div class="col">
                        <h5 class="text-center mb-3"><i class="control bi bi-patch-question me-2" data-order-direction="" data-order-by="content"></i>Контент</h5>
                        <select class="form-select w-auto mb-1" name="content-operation" id="select-content-operation">
                            <option value="" selected></option>
                            <option value="eq">=</option>
                            <option value="ne">!=</option>
                            <option value="lt">&lt;</option>
                            <option value="gt">&gt;</option>
                            <option value="starts">починається з</option>
                            <option value="contains">містить</option>
                            <option value="ends">завершується на</option>
                        </select>
                        <input class="form-control" type="text" name="content" id="input-content" maxlength="2000" disabled>
                    </div>

                    <div class="col">
                        <h5 class="text-center mb-3"><i class="control bi bi-patch-question me-2" data-order-direction="" data-order-by="modification_date"></i>Дата</h5>
                        <select class="form-select w-auto mb-1" name="modification-date-operation" id="select-modification-date-operation">
                            <option value="" selected></option>
                            <option value="eq">=</option>
                            <option value="ne">!=</option>
                            <option value="lt">&lt;</option>
                            <option value="gt">&gt;</option>
                        </select>
                        <input class="form-control" type="text" name="modification-date" id="input-modification-date" placeholder="dd/mm/yyyy" maxlength="10" disabled>
                    </div>

                    <div class="col">
                        <h5 class="text-center mb-3"><i class="control bi bi-patch-question me-2" data-order-direction="" data-order-by="views"></i>Перегляди</h5>
                        <select class="form-select w-auto mb-1" name="views-operation" id="select-views-operation">
                            <option value="" selected></option>
                            <option value="eq">=</option>
                            <option value="ne">!=</option>
                            <option value="lt">&lt;</option>
                            <option value="gt">&gt;</option>
                        </select>
                        <input class="form-control" type="text" name="views" id="input-views" maxlength="10" disabled>
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
                            <ul id="topArticles" class="aside-col__list-group list-group">
                            </ul>
                        </nav>
                    </div>
                </div>

                <div class="row">
                    <div class="aside-col col">
                        <h2 class="aside-col__header">Розділи</h2>
                        <nav class="aside-col__nav">
                            <ul id="categories" class="aside-col__list-group list-group">
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
    <script src="<c:url value="/resources/static/js/jquery-3.6.0.min.js"/>"></script>

    <script src="<c:url value="/resources/static/js/multi-level-dropdown.js"/>"></script>
    <script src="<c:url value="/resources/static/js/data-manipulator.js"/>"></script>

    <!-- alert -->
    <jsp:include page="/WEB-INF/components/alert.jsp"/>
    <script src="<c:url value="/resources/static/js/alert.js"/>"></script>

    <script>
        const baseUrl = location.protocol + "//" + location.host + "${pageContext.request.contextPath}";
        let currentPage = 1;
        let currentPageSize = 3;
        let allAmount = 0;

        $(document).ready(function() {
            refresh();

            initOrdering();
            loadFilteringValues();
            initPagination();
            blockingMechanism();
            initValidation();
        });

        function refresh() {
            getAllArticles();
            getAllCategories();
            getMostPopular(3);
        }

        function getAllArticles() {
            let articles = "";
            let filter = {};

            let category = $("#select-category").val();
            let categoryOperation = $("#select-category-operation").val();
            if (category !== "" && categoryOperation !== "") {
                filter.category = {
                    id: category
                };
                filter.categoryOperation = categoryOperation;
            }

            let user = $("#select-user").val();
            let userOperation = $("#select-user-operation").val();
            if (user !== "" && userOperation !== "") {
                filter.user = {
                    id: user
                };
                filter.userOperation = userOperation;
            }

            let name = $("#input-name").val()
            let nameOperation = $("#select-name-operation").val();
            if (name !== "" && nameOperation !== "") {
                filter.name = name;
                filter.nameOperation = nameOperation;
            }

            let content = $("#input-content").val();
            let contentOperation = $("#select-content-operation").val();
            if (content !== "" && contentOperation !== "") {
                filter.content = content;
                filter.contentOperation = contentOperation;
            }

            let modificationDate = $("#input-modification-date").val();
            let modificationDateOperation = $("#select-modification-date-operation").val();
            if (modificationDate !== "" && modificationDateOperation !== "") {
                filter.modificationDate = modificationDate;
                filter.modificationDateOperation = modificationDateOperation;
            }

            let views = $("#input-views").val();
            let viewsOperation = $("#select-views-operation").val();
            if (views !== "" && viewsOperation !== "") {
                filter.views = views;
                filter.viewsOperation = viewsOperation;
            }

            let orderingButtons = $("[data-order-by]");
            $.each(orderingButtons, function() {
                let currentButton = $(this);
                if (currentButton.attr("data-order-direction") !== "") {
                    filter.orderBy = currentButton.attr("data-order-by");
                    filter.orderDirection = currentButton.attr("data-order-direction");
                    return false;
                }
            });

            // налаштування пагінації
            filter.page = currentPage;
            filter.pageSize = currentPageSize;

            $.ajax({
                url: baseUrl + "/api/articles/filter",
                type: "POST",
                data: JSON.stringify(filter),
                dataType: "json",
                contentType: "application/json",

                success: function(result) {
                    if (result.length === 0) {
                        articles = getAlertMessage("alert-warning", "Попередження:", "жодної статті не знайдено!");
                    } else {
                        $.each(result, function() {
                            articles = appendArticle(articles, this);
                        });
                        lastSeenArticleId = result[result.length - 1].id;
                    }
                    $("#articles").html(articles);

                    let buttons = $('[data-delete-article-with-id]');
                    if (buttons.length > 0) {
                        buttons.on('click', function (event) {
                            event.preventDefault();
                            $.ajax({
                                url: baseUrl + "/api/articles/" + this.getAttribute("data-delete-article-with-id"),
                                type: 'DELETE',

                                success: function() {
                                    customAlert("success", "Успіх", "Стаття була видалена");

                                    refresh();
                                },

                                error: function (result) {
                                    customAlert("danger", "Помилка", "Не вдалося видалити цю статтю");
                                }
                            });
                        });
                    }
                },

                error: function (result) {
                    articles = getAlertMessage("alert-danger", "Помилка:", "не вдалося отримати дані про статті!");
                    $("#articles").html(articles);
                }
            });

            // отримання кількості усіх статей, відібраних за такими фільтрами
            $.ajax({
                url: baseUrl + "/api/articles/filter-amount",
                type: "POST",
                data: JSON.stringify(filter),
                dataType: "json",
                contentType: "application/json",

                success: function(result) {
                    allAmount = +result;
                    updatePaginationButtons();
                },

                error: function() {

                }
            });
        }

        function getAllCategories() {
            let categories = "";
            $.ajax({
                url: baseUrl + "/api/categories",
                type: "GET",
                contentType: "application/json",

                success: function(result) {
                    if (result.length === 0) {
                        categories = getAlertMessage("alert-warning", "Попередження:", "жодної категорії не знайдено");
                    } else {
                        $.each(result, function() {
                            categories = appendCategory(categories, this);
                        });
                    }
                    $("#categories").html(categories);
                },

                error: function(result) {
                    categories = getAlertMessage("alert-danger", "Помилка:", "не вдалося отримати дані про статті!");
                    $("#categories").html(categories);
                }
            });
        }

        function appendArticle(articles, article) {
            return articles + '<article class="article card mb-5">'
                            + '<h5 class="article__header card-header">' + encodeSpecialChars(article.category.name) + '</h5>'
                            + '<section class="article__body card-body">'
                            + '<h5 class="article__title card-title">' + encodeSpecialChars(article.name) + '</h5>'
                            + '<pre class="article__text card-text">' + encodeSpecialChars(article.content) + '</pre>'
                            + '<a class="btn btn-primary" href="${pageContext.request.contextPath}/articles/' + article.id + '">Читати далі</a>'
                            <security:authorize access="hasRole('USER') and hasRole('ADMIN')">
                            + '<a class="btn btn-info ms-2" href="${pageContext.request.contextPath}/articles/edit/' + article.id + '">Редагувати</a>'
                            + '<button class="btn btn-warning ms-2" data-delete-article-with-id="' + article.id + '">Видалити</button>'
                            </security:authorize>
                            + '</section>'
                            + '</article>';
        }

        function appendCategory(categories, category) {
            if (category.amountOfArticles > 0) {
                return categories + '<li class="aside-col__list-group-item list-group-item d-flex justify-content-between align-items-center">'
                    + '<a class="aside-col__list-group-item-link" onclick="setCategoryForFilter(' + category.id + ')">'
                    + category.name
                    + '<span class="badge bg-primary float-end">' + category.amountOfArticles + '</span>'
                    + '</a>'
                    + '</li>';
            } else {
                return categories;
            }
        }

        function getMostPopular(topSize) {
            let topArticles = "";
            $.ajax({
                url: baseUrl + "/api/articles/most-popular?topSize=" + topSize,
                type: "GET",
                contentType: "application/json",

                success: function (result) {
                    if (result.length === 0) {
                        topArticles = getAlertMessage("alert-warning", "Попередження:", "жодної популярної статті не знайдено!");
                    } else {
                        $.each(result, function() {
                            topArticles = appendTopArticle(topArticles, this);
                        });
                    }
                    $("#topArticles").html(topArticles);
                },

                error: function(result) {
                    topArticles = getAlertMessage("alert-danger", "Помилка:", "не вдалося отримати дані про найпопулярніші статті!");
                    $("#topArticles").html(topArticles);
                }
            });
        }

        function appendTopArticle(topArticles, topArticle) {
            return topArticles
                + '<li class="aside-col__list-group-item list-group-item d-flex justify-content-between align-items-center">'
                + '<a class="aside-col__list-group-item-link" href="${pageContext.request.contextPath}/articles/' + topArticle.id + '">'
                + topArticle.name
                + '<span class="float-end">' + topArticle.views + '<i class="bi bi-eye ms-2"></i></span>'
                + '</a>'
                + '</li>';
        }

        function getAlertMessage(type, title, content) {
            return '<div class="alert ' + type +' alert-dismissible fade show" role="alert">'
                    + '<strong>' + title + '</strong> ' + content
                    + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>'
                    + '</div>';
        }



        /* сортування та фільтрація */
        function initOrdering() {
            let buttons = $("[data-order-by]");
            buttons.click(function() {
                let currentButton = $(this);
                let orderBy = currentButton.attr("data-order-by");
                let orderDirection = currentButton.attr("data-order-direction");

                if (orderDirection === '') {
                    currentButton.attr("data-order-direction", "asc");
                    currentButton.removeClass("bi-patch-question").addClass("bi-arrow-up");
                } else if (orderDirection === 'asc') {
                    currentButton.attr("data-order-direction", "desc");
                    currentButton.removeClass("bi-arrow-up").addClass("bi-arrow-down");
                } else {
                    currentButton.attr("data-order-direction", "");
                    currentButton.removeClass("bi-arrow-down").addClass("bi-patch-question");
                }

                $.each(buttons, function() {
                    let thisButton = $(this);
                    if (thisButton.attr("data-order-by") !== orderBy) {
                        thisButton.attr("data-order-direction", "");
                        thisButton.removeClass("bi-arrow-up bi-arrow-down").addClass("bi-patch-question");
                    }
                });
            });
        }

        function loadFilteringValues() {
            // категорії
            $.ajax({
                url: baseUrl + "/api/categories",
                type: "GET",
                contentType: "application/json",

                success: function(result) {
                    let options = '<option value="" selected></option>';
                    $.each(result, function() {
                        options += '<option value="' + this.id + '">' + this.name + '</option>';
                    });
                    $("#select-category").html(options);
                },

                error: function(result) {
                    // ...
                }
            });

            // адміністратори - лише вони можуть робити публікації (їх невелика
            // кількість, тому буде доцільним відобразити лише їх у списку)
            $.ajax({
                url: baseUrl + "/api/users/with-role-name?name=ADMIN",
                type: "GET",
                contentType: "application/json",

                success: function(result) {
                    let options = '<option value="" selected></option>';
                    $.each(result, function() {
                        options += '<option value="' + this.id + '">' + this.firstName + ' ' + this.lastName + '</option>';
                    });
                    $("#select-user").html(options);
                },

                error: function(result) {
                    // ...
                }
            });
        }

        function setCategoryForFilter(categoryId) {
            $("#select-category-operation").val("eq");
            $("#select-category").val(categoryId);
            getAllArticles();
        }

        /* пагінація */
        function updatePaginationButtons() {
            if (currentPage - 1 <= 0) {
                $("#pagination-prev-arrow").hide();
                $("#pagination-prev").hide();
            } else {
                $("#pagination-prev>a").text(currentPage - 1);
                $("#pagination-prev-arrow").show();
                $("#pagination-prev").show();
            }

            $("#pagination-current>a").text(currentPage);

            if (currentPage + 1 > Math.ceil(allAmount / currentPageSize)) {
                $("#pagination-next-arrow").hide();
                $("#pagination-next").hide();
            } else {
                $("#pagination-next>a").text(currentPage + 1);
                $("#pagination-next").show();
                $("#pagination-next-arrow").show();
            }
        }

        function initPagination() {
            let prev = () => {
                currentPage -= 1;
                updatePaginationButtons();
                getAllArticles();
            };
            let next = () => {
                currentPage += 1;
                updatePaginationButtons();
                getAllArticles();
            };

            $("#pagination-prev-arrow").click(prev);
            $("#pagination-prev").click(prev);
            //$("#pagination-current").click();
            $("#pagination-next").click(next);
            $("#pagination-next-arrow").click(next);

            $("#select-page-size").change(function() {
                currentPage = 1;
                currentPageSize = +$(this).val();
                updatePaginationButtons();
                getAllArticles();
            });

            updatePaginationButtons();
        }

        function blockingMechanism() {
            $("#select-category-operation").change(function() {
                let state = $(this).val() === "";
                let bindedElement = $("#select-category");
                bindedElement.prop("disabled", state);
                if (state) {
                    bindedElement.val("");
                }
            });

            $("#select-user-operation").change(function() {
                let state = $(this).val() === "";
                let bindedElement = $("#select-user");
                bindedElement.prop("disabled", state);
                if (state) {
                    bindedElement.val("");
                }
            });

            $("#select-name-operation").change(function() {
                let state = $(this).val() === "";
                let bindedElement = $("#input-name");
                bindedElement.prop("disabled", state);
                if (state) {
                    bindedElement.val("");
                }
            });

            $("#select-content-operation").change(function() {
                let state = $(this).val() === "";
                let bindedElement = $("#input-content");
                bindedElement.prop("disabled", state);
                if (state) {
                    bindedElement.val("");
                }
            });

            $("#select-modification-date-operation").change(function() {
                let state = $(this).val() === "";
                let bindedElement = $("#input-modification-date");
                bindedElement.prop("disabled", state);
                if (state) {
                    bindedElement.val("");
                }
            });

            $("#select-views-operation").change(function() {
                let state = $(this).val() === "";
                let bindedElement = $("#input-views");
                bindedElement.prop("disabled", state);
                if (state) {
                    bindedElement.val("");
                }
            });
        }

        function initValidation() {
            $("#input-modification-date").on("input", function() {
                let length = this.value.length;
                if (length === 2 || length === 5) {
                    this.value = this.value + "/";
                }
            });

            $("#refresh-button").click(function() {
                let message = validate();
                if (message !== "") {
                    customAlert("danger", "Помилка", message);
                } else {
                    refresh();
                }
            });
        }

        function validate() {
            let date = $("#input-modification-date").val();
            let views = $("#input-views").val();
            let message = ""

            if (date !== "" && date.match(/^(?:(?:(?:0[1-9]|1\d|2[0-8])\/(?:0[1-9]|1[0-2])|(?:29|30)\/(?:0[13-9]|1[0-2])|31\/(?:0[13578]|1[02]))\/[1-9]\d{3}|29\/02(?:\/[1-9]\d(?:0[48]|[2468][048]|[13579][26])|(?:[2468][048]|[13579][26])00))$/) == null) {
                message += "Дата не відповідає формату.";
            }

            if (views !== "" && views.match(/^\d{1,10}$/) == null) {
                if (message !== "") {
                    message += "<br>";
                }
                message += "Кількість переглядів не відповідає формату.";
            }
            return message;
        }


    </script>
</body>
</html>
