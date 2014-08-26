<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${record.title}</title>
        <link rel="stylesheet" href="./css/bootstrap.min.css">
        <link rel="stylesheet" href="./css/webapp.css">
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="./js/ie10-viewport-bug-workaround.js"></script>
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
        <!-- Static navbar -->
        <div class="navbar navbar-inverse navbar-static-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">TestWebApp</a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="${context}/page">My records</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="${context}/cabinet">Cabinet</a></li>
                        <li><a href="${context}/logout">Logout</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>
        <div class="container">
            <div class="page-header">
                <h2>${record.title}</h2>
            </div>
        </div>
        <div class="container">
            ${record.content}
            <p>posted at <fmt:formatDate value="${record.date}" type="both" pattern="HH:mm | dd.MM.yyyy"/></p>
        </div>
        <div id="comments" class="container">
            <h3>Comments</h3>
            <c:choose>
                <c:when test="${empty comments}">
                    <div id="no-comments" class="container">
                        <p>No comments yet.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="comment" items="${comments}">
                        <div class="comment">
                            <p><a href="user?user=${comment.author.login}"><strong>${comment.author.login}</strong></a>,
                              <small><fmt:formatDate value="${comment.date}" type="both" pattern="HH:mm | dd.MM.yyyy"/></small></p>
                            <div class="content">${comment.content}</div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="container">
          <button id="addCommentBtn" type="button" class="btn btn-primary">Add Comment</button>
        </div>

        <div id="addCommentContainer" class="container hidden">
          <div class="panel panel-primary">
            <div class="panel-heading">
              <button id="cancelAddComment" type="button" class="close">
                <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
              </button>
              <h3 class="panel-title">New Comment</h3>
            </div>
            <div class="panel-body">
              <form id="commentAddForm" role="form">
                <div class="form-group">
                  <label for="inputContent">Description</label>
                  <textarea rows="3" class="form-control" id="inputContent" placeholder="Comment" required></textarea>
                </div>
                <div class="form-group">
                  <button type="submit" class="btn btn-primary">Save</button>
                  <button type="reset" class="btn btn-default">Clear</button>
                </div>
                <input type="hidden" id="recordId" name="recordId" value="${record.id}">
              </form>
            </div>
          </div>
        </div>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>