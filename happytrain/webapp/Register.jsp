<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Регистрация</title>
</head>
<body>
	<form action="register" method="POST">
		<label>Имя</label><br/>
		<input type="text" name="first_name" /><br/>
		<label>Фамилия</label><br/>
		<input type="text" name="last_name" /><br/>
		<label>Дата рождения</label><br/>
		<input type="text" name="birth_date" value="01-01-2015 00:00:00"/><br/>
		<label>Логин</label><br/>
		<input type="text" name="login" /><br/>
		<label>Пароль</label><br/>
		<input type="text" name="password" /><br/>
		<input type="submit" value="Добавить" />
	</form>
</body>
</html>