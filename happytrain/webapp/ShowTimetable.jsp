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
<link href="css/jquery.datetimepicker.css" rel="stylesheet" type="text/css"/>
<script src="js/jquery.js"></script>
<script src="js/jquery.datetimepicker.js"></script>
<script src="js/global.js"></script>
</head>
<body>
<div id="wrapper">
	<%@include file="Header.jsp" %>
	<div id="portfolio" class="container">
		<form  action="timetable" method="GET" id="main-form">
			    
			 	<label>Станция</label>
				<select name="station" >
				    	<c:forEach var="item" items="${stationList}">
							<option value="${item.name}" ${item.name == station ? 'selected="selected"' : ''}><c:out value="${item.name}" /></option>
						</c:forEach>
				</select>
				
			    <label>Время от</label>
			 	<input type="text" name="from" class="datetimepicker_mask" value="${from}">
			 	<label>Время до</label>
			 	<input type="text" name="to" class="datetimepicker_mask" value="${to}">
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
</div>
</body>
</html>