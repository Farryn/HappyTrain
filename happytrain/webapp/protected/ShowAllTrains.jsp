<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="menu.traiList"  /></title>
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
	    	<!-- Количество мест --> <spring:message code="beauty-table.seatsCount"  />
	    </td>
	    <td align="center" valign="top">
	    	<!-- Маршрут --><spring:message code="beauty-table.route"  />
	    </td>
	    <td align="center" valign="top">
	   		<!-- Список рейсов --><spring:message code="menu.runList"  />
	    </td>
	    <td align="center" valign="top">
	    	<!-- Добавить рейс --><spring:message code="menu.addRun"  />
	    </td>
	   </tr>
   </thead>
   <tbody>
  		 <c:if test="${emptyList == 1}">
				<tr><td colspan="5">
					<!-- Нет результатов --><spring:message code="beauty-table.emptyResult"  />
				</td></tr>
		</c:if>
	   <c:forEach var="item" items="${trainList}" varStatus="status">
					<tr>
					
						<td ><c:out value="${item.number}" /></td>
						<td ><c:out value="${item.seatsCount}" /></td>
						<td ><a href="route?train=${item.id}">
							<!-- Список станций --><spring:message code="beauty-table.stationList"  />
						</a></td>
						<td ><a href="run?train=${item.id}">
							<!-- Рейсы --><spring:message code="menu.runList"  />
						</a></td>
						<td ><a href="addrun?train=${item.id}">
							<!-- Добавить рейс --><spring:message code="menu.addRun"  />
						</a></td>
					</tr>
	   </c:forEach>
   </tbody>
   
   

 </table>

</div>
</body>
</html>