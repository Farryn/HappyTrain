<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Добавить рейс</title>
<link href="./css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="./css/fonts.css" rel="stylesheet" type="text/css" media="all" />
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
	<div id="portfolio" class="container">
		<c:if test="${fail == null}">
			 <form action="/happytrain/addrun" method="post">   
				 <table  id="beauty-table" >
				   <thead>
					   <tr>
					    	<td align="center" valign="top">Станция</td>
						    <td align="center" valign="top">Прибытие</td>
						    <td align="center" valign="top">Отправление</td>
					   </tr>
				   </thead>
				   <tbody>
				       
						   <c:forEach var="item" items="${stationList}" varStatus="status">
								<tr>
									<td >
										<c:out value="${item.name}" />
										<input type="hidden" name="stationList[]" value="${item.name}"/>
									</td>
									<td ><input type="text" name=arrivalTime[] /> </td>
									<td ><input type="text" name=departureTime[] /></td>
								</tr>
						   </c:forEach>
						   <tr>
						   		<td colspan="3">
									<input type="hidden" name="train" value="${train}"/>			   			
						   			<input type="submit" value="Добавить" class="button" />
						   		</td>
						   </tr>
					   
				   </tbody>
				 </table>
			 </form>
		</c:if>
		<c:if test="${fail == 0}">
			<span> Операция завершена успешно</span>
		</c:if>
		<c:if test="${fail == 1}">
			<span> Ошибка при добавлении данных</span>
		</c:if>
	</div>
</div>
</body>
</html>