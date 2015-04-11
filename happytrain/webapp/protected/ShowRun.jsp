<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="menu.runList"  /></title>
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body>
<div id="wrapper">
<%@include file="/Header.jsp" %>
  <table  id="beauty-table" >
   <thead>
	   <tr>
	    	<td align="center" valign="top">
	    		<!-- Номер поезда --> <spring:message code="beauty-table.trainNumber"  />
	    	</td>
	    	<td align="center" valign="top">
	    		<!-- Отправление --><spring:message code="beauty-table.departure"  />
	    	</td>
		    <td align="center" valign="top">
		    	<!-- Прибытие --> <spring:message code="beauty-table.arrival"  />
		    </td>
		    <td align="center" valign="top">
		    	<!-- Пассажиры --> <spring:message code="beauty-table.passengers"  />
		    </td>
	   </tr>
   </thead>
   <tbody>
  		 <c:if test="${emptyList == 1}">
				<tr><td colspan="4">
					<!-- Нет результатов --><spring:message code="beauty-table.emptyResult"  />
				</td></tr>
		 </c:if>
	   <c:forEach var="item" items="${runList}" varStatus="status">
			<tr>
				<td ><c:out value="${item.trainId.number}" /></td>
				<td ><c:out value="${item.startTime}" /></td>
				<td ><c:out value="${item.finishTime}" /></td>
				<td ><a href="passenger?run=${item.id}">
					 <!-- Показать пассажиров --><spring:message code="beauty-table.showPassengers"  />
				 </a></td>
			</tr>
	   </c:forEach>
   </tbody>
 </table>

</div>
</body>
</html>