<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Расписание</title>
<link href="css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/fonts.css" rel="stylesheet" type="text/css" media="all" />
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
	</div>
	<div id="portfolio" class="container">
		<form  action="timetable" method="GET" id="main-form">
			    
			 	<label>Станция</label>
				<select name="station" >
				    	<c:forEach var="item" items="${stationList}">
							<option value="${item.name}" ${item.name == station ? 'selected="selected"' : ''}><c:out value="${item.name}" /></option>
						</c:forEach>
				</select>
				
			    <label>Время от</label>
			 	<input type="text" name="from" size="25" value="01-01-2015 00:00:00">
			 	<label>Время до</label>
			 	<input type="text" name="to" size="25" value="01-01-2015 00:09:00">
			 	<input type="submit" name="submit" value="Найти" class="button">
			
			
			
			</form>
			<c:if test="${haveResult > 0}">
				<table  id="beauty-table" >
				
				  <thead>
					   <tr>
						    <td align="center" valign="top">Номер поезда</td>
						    <td align="center" valign="top">Отправление</td>
						    <td align="center" valign="top">Прибытие</td>
					   </tr>
				   </thead>
				   <tbody>
				   		<c:if test="${emptyList == 1}">
				   			<tr><td colspan="3">Нет результатов</td></tr>
				   		</c:if>
					   <c:forEach var="item" items="${timetableList}" varStatus="status">
							<tr>
								<td ><c:out value="${item.trainNumber}" /></td>
								<td ><c:out value="${item.departureDateTime}" /></td>
								<td ><c:out value="${item.arrivalDateTime}" /></td>
							</tr>
					   </c:forEach>
				   </tbody>
				   
				   
				
				 </table>
			 </c:if>
		</div>
	
</body>
</html>