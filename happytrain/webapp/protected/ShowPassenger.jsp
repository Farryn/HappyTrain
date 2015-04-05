<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список пассажиров</title>
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body>
<div id="wrapper">
	<%@include file="/Header.jsp" %>
 <table  id="beauty-table">
   <thead>
	   <tr>
	    	<td align="center" valign="top">Имя</td>
	    	<td align="center" valign="top">Фамилия</td>
		    <td align="center" valign="top">Станция отправления</td>
		    <td align="center" valign="top">Станция прибытия</td>
	   </tr>
   </thead>
   <tbody>
   		<c:if test="${emptyList == 1}">
			<tr><td colspan="5">Нет результатов</td></tr>
		</c:if>
	   <c:forEach var="item" items="${passengerList}" varStatus="status">
			<tr>
				<td ><c:out value="${item.firstName}" /></td>
				<td ><c:out value="${item.lastName}" /></td>
				<td ><c:out value="${item.stationFrom}" /></td>
				<td ><c:out value="${item.stationTo}" /></td>
			</tr>
	   </c:forEach>
   </tbody>
 </table>

</div>
</body>
</html>