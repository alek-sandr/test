<%
    if (session.getAttribute("auth") == null || !(Boolean) session.getAttribute("auth")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8"/>
        <title>User cabinet</title>
    </head>
    <body>
        <a href='<%= request.getContextPath() %>/index.jsp'>Main Page</a>
        <a href='<%= request.getContextPath() %>/cabinet.jsp'>My cabinet</a>
        <a href='<%= request.getContextPath() %>/login.jsp?logout=true'>Logout</a>
        <br/>
        <h3>User <%= session.getAttribute("login") %> cabinet</h3>
    </body>
</html>