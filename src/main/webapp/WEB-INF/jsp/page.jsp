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
        <title>My records</title>
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
                        <li class="active"><a href="${context}/page">My records</a></li>
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
                <h2>User ${sessionScope.login} page</h2>
            </div>
        </div>

        <div class="container">
            <button id="addRecordBtn" type="button" class="btn btn-primary">Add Record</button>
        </div>

        <div id="addFormContainer" class="container hidden">
        <div class="panel panel-primary">
          <div class="panel-heading">
            <button id="cancelAddRec" type="button" class="close">
              <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
            </button>
            <h3 class="panel-title">New Record</h3>
          </div>
          <div class="panel-body">
            <form id="recAddForm" role="form">
              <div class="form-group">
                <label for="inputTitle">Title</label>
                <input type="text" class="form-control" id="inputTitle" placeholder="Title" required>
              </div>
              <div class="form-group">
                <label for="inputDescription">Description</label>
                <textarea rows="5" class="form-control" id="inputDescription" placeholder="Description" required></textarea>
              </div>
              <div class="form-group">
                <label for="inputContent">Content</label>
                <textarea rows="15" class="form-control" id="inputContent" placeholder="Content" required></textarea>
              </div>
              <input id="recordId" type="hidden" name="recordId" value="${record.id}">
              <div class="form-group">
                <button type="submit" class="btn btn-primary">Save</button>
                <button type="reset" class="btn btn-default">Clear</button>
              </div>
            </form>
          </div>
        </div>
        </div>
        <c:choose>
            <c:when test="${empty records}">
                <div class="container">
                    <p>You have not records yet.</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="record" items="${records}">
                    <div class="container">
                            <h3><a href="${context}/record?id=${record.id}">${record.title}</a></h3>
                            <p>${record.description}</p>
                            <p class="text-right">posted at <fmt:formatDate value="${record.date}" type="both" pattern="HH:mm | dd.MM.yyyy"/></p>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>