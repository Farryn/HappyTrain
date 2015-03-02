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
	<div id="header-wrapper">
		<div id="header" class="container">
			<div id="logo">
				<h1><a href="/"><span>HappyTrain</span></a></h1>
			</div>
			<div id="login">
				<span>Добро пожаловать, </span>
				<span id="name"><c:out value="${user.login}" default="Гость" /></span>
					<c:if test="${user == null}">
						<br/><span><a href="Login.jsp">Войдите</a> или <a href="Register.jsp">зарегистрируйтесь</a></span>
					</c:if>
					<c:if test="${user != null}">
						<br/><span><a href="logout">Выйти</a></span>
					</c:if>
			</div>
		</div>
		<div id="menu" class="container">
			<ul>
				<li class="current_page_item"><a href="#" accesskey="1" title="">Поиск поезда</a></li>
				<li><a href="timetable" accesskey="1" title="">Расписание</a></li>
				<li><a href="alltrains" accesskey="2" title="">Все поезда</a></li>
				<li><a href="protected/AddStation.jsp" accesskey="3" title="">Добавить поезд</a></li>
				<li><a href="protected/AddTrain.jsp" accesskey="4" title="">Добавить станцию</a></li>
			</ul>
		</div>
	</div>
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
	</div>
</body>
</html>