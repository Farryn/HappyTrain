<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список рейсов</title>
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
	    	<td align="center" valign="top">Отправление</td>
		    <td align="center" valign="top">Прибытие</td>
		    <td align="center" valign="top">Пассажиры</td>
	   </tr>
   </thead>
   <tbody>
  		 <c:if test="${emptyList == 1}">
				<tr><td colspan="4">Нет результатов</td></tr>
		 </c:if>
	   <c:forEach var="item" items="${runList}" varStatus="status">
			<tr>
				<td ><c:out value="${item.trainId.number}" /></td>
				<td ><c:out value="${item.startTime}" /></td>
				<td ><c:out value="${item.finishTime}" /></td>
				<td ><a href="passenger?run=${item.id}"> Показать пассажиров</a></td>
			</tr>
	   </c:forEach>
   </tbody>
 </table>

</div>
</body>
</html>