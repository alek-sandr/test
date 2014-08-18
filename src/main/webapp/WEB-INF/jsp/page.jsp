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
        <title>Main page</title>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
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
                        <li class="active"><a href="${context}/page">Main</a></li>
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
        <c:choose>
            <c:when test="${empty records}">
                <div class="container">
                    <p>You have not records yet.</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="record" items="${records}">
                    <div class="container">
                        <!-- <div class="jumbotron"> -->
                            <h3>Record</h3>
                            <p>${record.content} posted at
                            <fmt:formatDate value="${record.date}" type="both" pattern="HH:mm | dd.MM.yyyy"/></p>
                        <!-- </div> -->
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>