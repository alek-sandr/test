<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Login</title>
    </head>
    <body>
        <h3>Login</h3>
        <form action='<%= request.getContextPath() %>/login' method="POST">
            Login<input type="text" size="20" name="login">
            <br/>
            Password<input type="password" size="20" name="password">
            <br/>
            <input type="submit" value="Log In">
        </form>
    </body>
</html>