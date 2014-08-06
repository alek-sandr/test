<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>User cabinet</title>
    </head>
    <body>
        <a href='<%= request.getContextPath() %>/page'>Main Page</a>
        <a href='<%= request.getContextPath() %>/cabinet'>My cabinet</a>
        <a href='<%= request.getContextPath() %>/login?logout=true'>Logout</a>
        <br/>
        <h3>User <%= session.getAttribute("login") %> cabinet</h3>
    </body>
</html>