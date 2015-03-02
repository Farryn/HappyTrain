<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Покупка билета</title>
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
	
	<form action="/happytrain/buyticket" method="POST">
		<label>Имя</label><br/>
		<input type="text" name="firstName" value="${user.firstName}" /><br/>
		<label>Фамилия</label><br/>
		<input type="text" name="lastName" value="${user.lastName}" /><br/>
		<label>Дата рождения</label><br/>
		<input type="text" name="birthDate" value="${user.birthDate}" /><br/>
		<label>Номер поезда</label><br/>
		<input type="text" name="train" value="${param.train}" /><br/>
		<label>Станция отправления</label><br/>
		<input type="text" name="stationFrom" value="${param.stationFrom}" /><br/>
		<label>Станция прибытия</label><br/>
		<input type="text" name="stationTo" value="${param.stationTo}" /><br/>
		<label>Время отправления</label><br/>
		<input type="text" name="depTime" value="${param.depTime}" /><br/>
		<input type="hidden" name="run" value="${param.run}" />
		<input type="submit" value="Купить" />
	</form>
	</div>
</body>
</html>