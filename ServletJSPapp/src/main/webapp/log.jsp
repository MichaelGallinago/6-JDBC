<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Login</title>
</head>
<body>
<p>Вход в систему</p>

<form action="log" method="POST">
    Login: <input type="text" name="login"/>
    Password: <input type="password" name="password"/>
    <input type="submit" value="Войти">
</form>
<a href="registration">Зарегистрироваться</a>
</body>
</html>