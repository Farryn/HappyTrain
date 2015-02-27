<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список поездов</title>
</head>
<body>


   
 <table  border="1" >

   <caption>Список поездов</caption>
  <thead>
	   <tr>
	    <td align="center" valign="top">Номер поезда</td>
	    <td align="center" valign="top">Количество мест</td>
	    <td align="center" valign="top">Маршрут</td>
	    <td align="center" valign="top">Добавить рейс</td>
	   </tr>
   </thead>
   <tbody>
	   <c:forEach var="item" items="${trainList}" varStatus="status">
	   		
					<tr>
					
						<td ><c:out value="${item.number}" /></td>
						<td ><c:out value="${item.seatsCount}" /></td>
						<td ><a href="route?train=${item.id}">Список станций</a></td>
						<td ><a href="AddRun.jsp?train=${item.id}">Добавить рейс</a></td>
					</tr>
				
			
	   </c:forEach>
   </tbody>
   
   

 </table>


</body>
</html>