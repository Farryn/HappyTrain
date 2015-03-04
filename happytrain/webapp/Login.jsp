<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
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
		<c:if test="${failMessage != null }">
			<div id="message">
				${failMessage}
			</div>
		</c:if>
		<form action="login" method="post" id="input-form">
			<ul id="wrapper-ul">
				<li>
					<label>Login</label>
				    <input type="text" name="login">
				</li>
				<li>
				    <label>Password</label>
				    <input type="password" name="password">
				</li>
		    </ul>
		    <input type="hidden" name="url" value="${URL}">
			<input type="hidden" name="servletUrl" value="${servletUrl}">
			<input type="submit" value="Submit" class="button">
		</form>
	</div>
</div>
</body>
</html>