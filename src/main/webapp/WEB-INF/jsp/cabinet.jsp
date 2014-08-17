<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>User cabinet</title>
    </head>
    <body>
        <a href="${context}/page">Main Page</a>
        <a href="${context}/cabinet">My cabinet</a>
        <a href="${context}/login?logout=true">Logout</a>
        <br/>
        <h3>User ${sessionScope.login} cabinet</h3>
    </body>
</html>