<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Покупка билета</title>
<link href="/happytrain/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="/happytrain/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body>
<div id="wrapper">
	<div id="header-wrapper">
		<div id="header" class="container">
			<div id="logo">
				<h1><a href="/happytrain"><span>HappyTrain</span></a></h1>
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
				<li class="current_page_item"><a href="/happytrain" accesskey="1" title="">Поиск поезда</a></li>
				<li><a href="timetable" accesskey="1" title="">Расписание</a></li>
				<li><a href="alltrains" accesskey="2" title="">Все поезда</a></li>
				<li><a href="/happytrain/protected/AddStation.jsp" accesskey="3" title="">Добавить поезд</a></li>
				<li><a href="/happytrain/protected/AddTrain.jsp" accesskey="4" title="">Добавить станцию</a></li>
			</ul>
		</div>
	</div>
	<div id="portfolio" class="container">
		<c:if test="${fail == null}">
			<form action="/happytrain/buyticket" method="POST" id="input-form">
				<ul id="wrapper-ul">
					<%--<li>
						<label>Имя</label>
						<input type="text" name="firstName" value="${user.firstName}" />
					</li>
					<li>
						<label>Фамилия</label>
						<input type="text" name="lastName" value="${user.lastName}" />
					</li>
					<li>
						<label>Дата рождения</label>
						<input type="text" name="birthDate" value="${user.birthDate}" />
					</li> --%>
					<li>
						<label>Номер поезда</label>
						<input type="text" name="train" value="${param.train}" />
					</li>
					<li>
						<label>Станция отправления</label>
						<input type="text" name="stationFrom" value="${param.stationFrom}" />
					</li>
					<li>
						<label>Станция прибытия</label>
						<input type="text" name="stationTo" value="${param.stationTo}" />
					</li>
					<%--<li>
						<label>Время отправления</label>
						<input type="text" name="depTime" value="${param.depTime}" />
					</li> --%>
				</ul>
				<input type="hidden" name="depTime" value="${param.depTime}" />
				<input type="hidden" name="run" value="${param.run}" />
				<input type="submit" value="Купить" class="button"/>
			</form>
		</c:if>
		<c:if test="${fail == 0}">
			<div id="message"> Операция завершена успешно</div>
		</c:if>
		<c:if test="${fail == 1}">
			<div id="message"> ${failMessage }</div>
		</c:if>
	</div>
	</div>
</body>
</html>