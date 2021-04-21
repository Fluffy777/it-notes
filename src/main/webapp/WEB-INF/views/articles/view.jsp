<%@ page import="com.fluffy.spring.domain.Article" %>
<%@ page import="com.fluffy.spring.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%
    User currentUser = (User) request.getAttribute("currentUser");
    int articleId = (int) request.getAttribute("articleId");
%>

<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Fluffy">
    <meta name="description" content="Корисні статті із фрагментами коду, що допоможуть під час розробки Ваших проектів.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/static/img/common/favicon.ico"/>">
    <title>Перегляд статті</title>

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
        <div class="row justify-content-center">
            <div class="col-10">
                <article class="article card mb-3">
                    <h1 id="category-name" class="article__header card-header"></h1>
                    <section id="article-body" class="article__body card-body">
                        <h2 id="name" class="article__title card-title"></h2>
                        <pre id="content" class="article__text card-text"></pre>
                        <p id="author" class="text-end text-secondary"></p>
                        <p id="last-modified" class="text-end text-secondary"></p>
                    </section>
                </article>
            </div>
        </div>
        <div class="row justify-content-center">
            <!-- Коментарі -->
            <div id="comments" class="col-10">
            </div>
        </div>

        <security:authorize access="!isAnonymous()">
            <div class="row justify-content-center">
                <div class="col-10">
                    <form id="comment-form">
                        <div class="row">
                            <div class="col-auto me-4">
                                <% if (currentUser.getGender().equals(User.Gender.MALE)) { %>
                                    <img class="profile__logo mb-2" id="thumbnail" src="${pageContext.request.contextPath}/<%="icons/" + currentUser.getId() + "/" + currentUser.getIcon()%>" alt="Фото" onerror="this.onerror = null; this.src = `<c:url value="/resources/static/img/profile/male.png"/>`;">
                                <%} else {%>
                                    <img class="profile__logo mb-2" id="thumbnail" src="${pageContext.request.contextPath}/<%="icons/" + currentUser.getId() + "/" + currentUser.getIcon()%>" alt="Фото" onerror="this.onerror = null; this.src = `<c:url value="/resources/static/img/profile/female.png"/>`;">
                                <%} %>
                            </div>
                            <div class="col">
                                <div id="reply-to-panel" class="mb-2">
                                    <a id="reply-to" class="text-secondary me-2"></a>
                                    <i class="bi bi-reply-fill"></i>
                                    <button type="button" class="btn-close ms-auto" onclick="hideReplyToPanel()"></button>
                                </div>
                                <textarea class="form-control mb-3" id="input-content" rows="5" placeholder="Ваш коментар" required="required"></textarea>
                                <button class="btn btn-primary" type="submit">Надіслати</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </security:authorize>
        <div class="row text-center mb-3">
            <a href="${pageContext.request.contextPath}/">На головну</a>
        </div>
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
        let idToLocalIdMap = {};

        $(document).ready(function() {
            <security:authorize access="!isAnonymous()">
                hideReplyToPanel();
            </security:authorize>
            loadArticle();
            loadComments();

            <security:authorize access="!isAnonymous()">
                $("#comment-form").submit(function(event) {
                    event.preventDefault();
                    doPost();
                });
            </security:authorize>
        });

        function updateArticle(articleId) {
            $.ajax({
                url: baseUrl + "/api/articles/view",
                type: "PUT",
                data: JSON.stringify(articleId),
                contentType: "application/json",
                dataType: 'json',

                error: function () {
                    customAlert("danger", "Помилка", 'Не вдалося оновити кількість переглядів статті');
                }
            });
        }

        function loadArticle() {
            $.ajax({
                url: baseUrl + "/api/articles/<%=articleId%>",
                type: "GET",
                contentType: "application/json",

                success: function(result) {
                    $("#category-name").text(result.category.name);
                    $("#name").text(result.name);
                    $("#content").text(result.content);
                    $("#author").text(result.user.firstName + " " + result.user.lastName);
                    $("#last-modified").text(new Date(result.modificationDate).toLocaleString());

                    document.querySelector("title").innerText = result.category.name + ": " + result.name;


                    updateArticle(result.id);
                    /*
                    updateArticle({
                        id: result.id,
                        category: {
                            id: result.category.id,
                            name: result.category.name
                        },
                        user: result.user,
                        name: result.name,
                        content: result.content,
                        views: result.views + 1
                    });

                     */

                    appendSeeAlsoBlock();
                },

                error: function(result) {
                    customAlert("danger", "Помилка", "Не вдалося отримати дані про статтю");
                }
            });
        }

        function appendSeeAlsoBlock() {
            $.ajax({
                url: baseUrl + "/api/articles/same-articles?id=<%=articleId%>&maxCount=3",
                type: "GET",
                contentType: "application/json",

                success: function(result) {
                    if (result.length > 0) {
                        let content = '<h3>Дивіться також:</h3><ul>';
                        $.each(result, function() {
                            content += '<li><a href="${pageContext.request.contextPath}/articles/' + this.id + '">' + this.name + '</a></li>';
                        });
                        content += '</ul>';
                        $("#article-body").append(content);
                    }
                },

                error: function(result) {
                    customAlert("danger", "Помилка", "Не вдалося завантажити рекомендації до статті");
                }
            });
        }

        function loadComments() {
            $.ajax({
                url: baseUrl + "/api/comments/by-article?articleId=<%=articleId%>",
                type: "GET",
                contentType: "application/json",

                success: function(result) {
                    if (result.length > 0) {
                        let comments = "";
                        $.each(result, function() {
                            idToLocalIdMap[this.id] = this.localId;
                            comments = appendComment(comments, this);
                        });
                        $("#comments").html(comments + $("#comments").html());
                    }
                },

                error: function(result) {
                    customAlert("danger", "Помилка", "Не вдалося завантажити коментарі до статті");
                }
            });
        }

        function appendComment(comments, comment) {
            var deleteButton = '';
            var contentEditableFlag = '';
            <security:authorize access="hasRole('USER') and hasRole('ADMIN')">
            var deleteButton = '<button class="btn-close ms-auto" data-delete-comment-with-id="' + comment.id + '" onclick="deleteComment(this)"></button>';
            var contentEditableFlag = 'contenteditable="true"';
            </security:authorize>
            <security:authorize access="hasRole('USER') and !hasRole('ADMIN')">
            var deleteButton = <%=currentUser.getId()%> === comment.user.id ? '<button class="btn-close ms-auto" data-delete-comment-with-id="' + comment.id + '" onclick="deleteComment(this)"></button>' : '';
            var contentEditableFlag = <%=currentUser.getId()%> === comment.user.id ? 'contenteditable="true"' : '';
            </security:authorize>
            let replyTo = (comment.parentId !== null) ? '<a class="text-secondary me-2" href="#comment-' + idToLocalIdMap[comment.parentId] + '">#' + idToLocalIdMap[comment.parentId] + '</a>'
                            + '<i class="bi bi-reply-fill me-2"></i>' : '';
            if (comment.user.gender === 'MALE') {
                var defaultGenderImage = 'onerror="this.onerror = null; this.src = `<c:url value="/resources/static/img/profile/male.png"/>`;"';
            } else {
                var defaultGenderImage = 'onerror="this.onerror = null; this.src = `<c:url value="/resources/static/img/profile/female.png"/>`;"';
            }

            let commentParentId = (comment.parentId !== null) ? 'data-comment-parent-id="' + comment.parentId + '"' : '';

            return comments + '<div class="row" data-comment-id="' + comment.id + '" ' + commentParentId + '>'
                            + '<div class="col-auto me-4">'
                            + '<img class="profile__logo mb-2" src="${pageContext.request.contextPath}' + "/icons/" + comment.user.id + "/" + comment.user.icon + '" alt="Фото"' + defaultGenderImage + '>'
                            + '</div>'
                            + '<div class="col">'
                            + '<div class="d-flex">'
                            + replyTo
                            + '<a name="comment-' + comment.localId + '" class="me-2" onclick="showReplyToPanel(' + comment.id + ', ' + comment.localId + ')">#' + comment.localId + '</a>'
                            + '<h4>' + comment.user.firstName + " " + comment.user.lastName + '</h4>'
                            + deleteButton
                            + '</div>'
                            + '<p ' + contentEditableFlag + ' onfocus="startCommentUpdate(this)" onfocusout="finishCommentUpdate(this, ' + comment.id + ', ' + comment.localId + ', ' + comment.parentId + ')">' + comment.content + '</p>'
                            + '<div class="text-secondary float-end">' + new Date(comment.modificationDate).toLocaleString() + '</div>'
                            + '</div>'
                            + '<hr>'
                            + '</div>';
        }

        <security:authorize access="!isAnonymous()">
            let currentParentId = null;

            function doPost() {
                let comment = {
                    article: {
                        id: <%=articleId%>
                    },
                    parentId: currentParentId,
                    content: $("#input-content").val(),
                    user: {
                        id: <%=currentUser.getId()%>,
                        firstName: '<%=currentUser.getFirstName()%>',
                        lastName: '<%=currentUser.getLastName()%>',
                        icon: '<%=currentUser.getIcon()%>',
                        gender: '<%=currentUser.getGender().toString()%>'
                    }
                };

                $.ajax({
                    url: baseUrl + "/api/comments",
                    type: "POST",
                    data: JSON.stringify(comment),
                    dataType: "json",
                    contentType: "application/json",

                    success: function(result) {
                        idToLocalIdMap[result.id] = result.localId;
                        $("#comments").append(appendComment("", result));
                        $("#input-content").val("")
                    },

                    error: function(result) {
                        customAlert("danger", "Помилка", "Не вдалося додати коментар");
                    }
                });
            }

            function deleteComment(currentButton) {
                let id = currentButton.getAttribute("data-delete-comment-with-id");
                $.ajax({
                    url: baseUrl + "/api/comments/" + id,
                    type: "DELETE",

                    success: function (result) {
                        deleteCommentRecursive($('[data-comment-id="' + id + '"]'));
                    },

                    error: function(result) {
                        customAlert("danger", "Помилка", "Не вдалося видалити цей коментар");
                    }
                });
            }

            function deleteCommentRecursive(comment) {
                if (comment.length > 0) {
                    let commentId = +comment.attr("data-comment-id");

                    comment.remove();
                    if (currentParentId === commentId) {
                        hideReplyToPanel();
                    }
                    deleteCommentRecursive($('[data-comment-parent-id="' + commentId + '"]'));
                }
            }

            let previousCommentContent;

            function finishCommentUpdate(currentParagraph, commentId, commentLocalId, commentParentId) {
                $(currentParagraph).removeClass("form-control");
                let newCommentContent = $(currentParagraph).text();

                if (newCommentContent !== previousCommentContent) {
                    let comment = {
                        id: commentId,
                        article: {
                            id: <%=articleId%>
                        },
                        localId: commentLocalId,
                        parentId: commentParentId,
                        content: newCommentContent,
                        user: {
                            id: <%=currentUser.getId()%>,
                            firstName: '<%=currentUser.getFirstName()%>',
                            lastName: '<%=currentUser.getLastName()%>',
                            icon: '<%=currentUser.getIcon()%>'
                        }
                    };

                    $.ajax({
                        url: baseUrl + "/api/comments/" + comment.id,
                        type: "PUT",
                        data: JSON.stringify(comment),
                        contentType: "application/json",
                        dataType: 'json',

                        success: function(result) {
                            $('[data-comment-id="' + result.id + '"]>.col>div:last-child').text(new Date(result.modificationDate).toLocaleString());
                        },

                        error: function(result) {
                            $(currentParagraph).text(previousCommentContent);
                            customAlert("danger", "Помилка", "Не вдалося оновити коментар");
                        }
                    });
                }
            }

            function startCommentUpdate(currentParagraph) {
                previousCommentContent = currentParagraph.innerText;
                $(currentParagraph).addClass("form-control");
            }

            function showReplyToPanel(parentId, to) {
                currentParentId = parentId;
                $("#reply-to").text("#" + to).attr("href", "#comment-" + to);
                $("#reply-to-panel").css("display", "flex");
            }

            function hideReplyToPanel() {
                currentParentId = null;
                $("#reply-to-panel").css("display", "none");
            }
        </security:authorize>

    </script>
</body>
</html>
