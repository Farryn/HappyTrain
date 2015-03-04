<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список поездов</title>
<link href="css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/fonts.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body>

<div id="wrapper">
	<%@include file="/Header.jsp" %>
 <table  id="beauty-table" >
  <thead>
	   <tr>
	    <td align="center" valign="top">Номер поезда</td>
	    <td align="center" valign="top">Количество мест</td>
	    <td align="center" valign="top">Маршрут</td>
	    <td align="center" valign="top">Список рейсов</td>
	    <td align="center" valign="top">Добавить рейс</td>
	   </tr>
   </thead>
   <tbody>
  		 <c:if test="${emptyList == 1}">
				<tr><td colspan="5">Нет результатов</td></tr>
		</c:if>
	   <c:forEach var="item" items="${trainList}" varStatus="status">
					<tr>
					
						<td ><c:out value="${item.number}" /></td>
						<td ><c:out value="${item.seatsCount}" /></td>
						<td ><a href="route?train=${item.id}">Список станций</a></td>
						<td ><a href="run?train=${item.id}">Рейсы</a></td>
						<td ><a href="addrun?train=${item.id}">Добавить рейс</a></td>
					</tr>
	   </c:forEach>
   </tbody>
   
   

 </table>

</div>
</body>
</html>