<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Поиск поезда</title>
</head>
<body>
<table width="100%">
	<tr>
		<td width="80%">
			<form  action="showtrain" method="GET">
			    
			 <table  border="0" >
				
			   <caption>Поиск поезда</caption>
			
			   <tr>
				    <td align="right" valign="top">От станции</td>
				    <td>
				    	<select name="stationFrom" >
				    		  <c:forEach var="item" items="${stationList}">
							  	<option value="${item.name}" ${item.name == stationFrom.name ? 'selected="selected"' : ''}><c:out value="${item.name}" /></option>
							  </c:forEach>
						</select>
					</td>
				    <td align="right" valign="top">До станции</td>
				    <td>
						<select name="stationTo" >
				    		  <c:forEach var="item" items="${stationList}">
							  	<option value="${item.name}" ${item.name == stationTo.name ? 'selected="selected"' : ''}><c:out value="${item.name}" /></option>
							  </c:forEach>
						</select>
				
					</td>
			   </tr>
			
			   <tr>
				    <td align="right" valign="top">Время от</td>
				    <td><input type="text" name="from" size="25" value="01-01-2015 00:00:00"></td>
				    <td align="right" valign="top">Время до</td>
				    <td><input type="text" name="to" size="25" value="01-01-2015 00:09:00"></td>
			   </tr>
			   <tr>
				    <td align="right" colspan="2"></td>
				    <td align="right" colspan="2">
				     	<input type="submit" name="submit" value="Отправить">
				    </td>
			   </tr>
			
			 </table>
			
			</form>
			<c:if test="${haveResult > 0}">
				<table  border="1" >
				
				  <thead>
					   <tr>
						    <td align="center" valign="top">Номер поезда</td>
						    <td align="center" valign="top">Отправление</td>
						    <td align="center" valign="top">Прибытие</td>
						    <td align="center" valign="top">Места</td>
						    <td align="center" valign="top">Маршрут</td>
						    <td align="center" valign="top">Покупка</td>
					   </tr>
				   </thead>
				   <tbody>
					   <c:forEach var="item" items="${runList}" varStatus="status">
							<tr>
								<td ><c:out value="${item.trainId.number}" /></td>
								<td ><c:out value="${departureDateTime[status.index]}" /></td>
								<td ><c:out value="${arrivalDateTime[status.index]}" /></td>
								<td ><c:out value="${availableSeats[status.index]}" /></td>
								<td ><a href="route?train=${item.trainId.id}&run=${item.id}">Список станций</a></td>
								<td ><a href="buyTicket?">Купить билет</a></td>
							</tr>
					   </c:forEach>
				   </tbody>
				   
				   
				
				 </table>
			 </c:if>
			
		</td>
		<td valign="top">
			<a href="timetable">Показать расписание</a>
			<a href="alltrains">Показать поезда</a>
			<a href="passengers">Показать пассажиров</a>
			<a href="AddStation.jsp">Добавить станцию</a>
			<a href="AddTrain.jsp">Добавить поезд</a>
		</td>
	</tr>
</table>
</body>
</html>