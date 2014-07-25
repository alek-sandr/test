<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8"/>
        <title>Main page</title>
    </head>
    <body>
        <a href='<%= request.getContextPath() %>/index.jsp'>Main Page</a>
        <a href='<%= request.getContextPath() %>/cabinet.jsp'>My cabinet</a>
        <a href='<%= request.getContextPath() %>/login.jsp?logout=true'>Logout</a>
        <br/>
        <h3>User <%= session.getAttribute("login") %> page</h3>
        <%
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("HH:mm | dd.MM.yyyy");
            for (int i = 0; i < 10; i++) {
                out.println("<p>aasasasasasdasdasd, posted at " + dateFormat.format(new java.util.Date()) + "</p>");
            }
        %>
    </body>
</html>