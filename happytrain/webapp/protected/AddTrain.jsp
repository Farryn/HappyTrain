<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="../css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js" type="text/javascript"></script>
<script src="../js/global.js" type="text/javascript"></script>
<title>Добавить поезд</title>

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
				<li><a href="protected/AddStation.jsp" accesskey="3" title="">Добавить станцию</a></li>
				<li><a href="protected/AddTrain.jsp" accesskey="4" title="">Добавить поезд</a></li>
			</ul>
		</div>
	</div>
		<div id="portfolio" class="container">
			<form  action="/happytrain/addtrain" method="POST" id="input-form">
				<label>Номер поезда</label>
				<input type="text" name="trainNumber" >
				<label>Число мест</label>
				<input type="text" name="seatsCount"  >
				<label>Маршрут</label>
				<input type="button" value="Добавить станцию" class="add_field_button">
				<select name="stationList[]" >
				    	<c:forEach var="item" items="${stationList}">
							  	<option value="${item.name}" ${item.name == stationFrom.name ? 'selected="selected"' : ''}><c:out value="${item.name}" /></option>
						</c:forEach>
				</select>
				
				<input type="submit"  value="Отправить" >
			</form>
		</div>	
</div>		

</body>
</html>