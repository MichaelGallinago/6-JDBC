<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Registration</title>
</head>
<body>
<p>Введите данные для регистрации в системе</p>

<form action="registration" method="POST">
    Email: <input type="text" name="email"/>
    Login: <input type="text" name="login"/>
    Password: <input type="password" name="password"/>
    <input type="submit" value="Зарегистрироваться">
</form>
<a href="log">Войти, если уже зарегистрирован.</a>
</body>
</html>
