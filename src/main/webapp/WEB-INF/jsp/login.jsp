<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Login</title>
    </head>
    <body>
        <h3>Login</h3>
        <form action="${context}/login" method="POST">
            Login<input type="text" size="20" name="login">
            <br/>
            Password<input type="password" size="20" name="password">
            <br/>
            <input type="submit" value="Log In">
        </form>
    </body>
</html>