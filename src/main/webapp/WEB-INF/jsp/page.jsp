<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Main page</title>
    </head>
    <body>
        <a href='<%= request.getContextPath() %>/page'>Main Page</a>
        <a href='<%= request.getContextPath() %>/cabinet'>My cabinet</a>
        <a href='<%= request.getContextPath() %>/login?logout=true'>Logout</a>
        <br/>
        <h3>User <%= session.getAttribute("login") %> page</h3>
        <c:choose>
            <c:when test="${empty records}">
                <p>You have not records yet.</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="record" items="${records}">
                    <p>${record.content} posted at
                    <fmt:formatDate value="${record.date}" type="both" pattern="HH:mm | dd.MM.yyyy"/></p>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>