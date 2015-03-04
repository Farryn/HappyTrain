<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Регистрация</title>
<link href="css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/fonts.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body>
<div id="wrapper">
	<%@include file="Header.jsp" %>
	<div id="portfolio" class="container">
	<c:if test="${fail == null}">
		<form action="register" method="POST" id="input-form">
			<ul id="wrapper-ul">
				<li>
					<label>Имя</label>
					<input type="text" name="first_name" />
				</li>
				<li>
					<label>Фамилия</label>
					<input type="text" name="last_name" />
				</li>
				<li>
					<label>Дата рождения</label>
					<input type="text" name="birth_date" value="01-01-2015 00:00:00"/>
				</li>
				<li>
					<label>Логин</label>
					<input type="text" name="login" />
				</li>
				<li>
					<label>Пароль</label>
					<input type="text" name="password" />
				</li>
			</ul>
			<input type="submit" value="Добавить" class="button"/>
		</form>
	</c:if>
	<c:if test="${fail == 0}">
			<div id="message"> Операция завершена успешно</div>
	</c:if>
	<c:if test="${fail == 1}">
			<div id="message"> Ошибка при добавлении данных</div>
	</c:if>
	</div>
	</div>
</body>
</html>