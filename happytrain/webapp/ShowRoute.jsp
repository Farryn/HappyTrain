<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список станций</title>
<link href="css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/fonts.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body>
<div id="wrapper">
	<%@include file="Header.jsp" %>
	 <table  id="beauty-table" >
	  <thead>
		   <tr>
		    <td align="center" valign="top">Название станции</td>
		    <c:if test="${haveRun > 0}">
			    <td align="center" valign="top">Прибытие</td>
			    <td align="center" valign="top">Отправление</td>
		    </c:if>
		   </tr>
	   </thead>
	   <tbody>
	   		<c:if test="${emptyList == 1}">
				<tr><td colspan="3">Нет результатов</td></tr>
			</c:if>
		   <c:forEach var="item" items="${stationList}" varStatus="status">
				<tr>
					<td ><c:out value="${item.name}" /></td>
					<c:if test="${haveRun > 0}">
						<td ><c:out value="${timetableList[status.index].arrivalDateTime}" /></td>
						<td ><c:out value="${timetableList[status.index].departureDateTime}" /></td>
					</c:if>
				</tr>
		   </c:forEach>
	   </tbody>
	 </table>

</div>
</body>
</html>