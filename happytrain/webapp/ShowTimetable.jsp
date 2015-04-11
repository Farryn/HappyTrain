<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="menu.timetable"  /></title>
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css"/>
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.datetimepicker.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/global.js"></script>
<script src="https://raw.githubusercontent.com/jpillora/verifyjs/gh-pages/dist/verify.notify.js" type="text/javascript"></script>
</head>
<body>
<div id="wrapper">
	<%@include file="Header.jsp" %>
	<div id="portfolio" class="container">
		<form  action="timetable" method="POST" id="main-form">
			    
			 	<label><!-- Станция --><spring:message code="main-form.station"  /></label>
				<select name="station" >
				    	<c:forEach var="item" items="${stationList}">
							<option value="${item.name}" ${item.name == station ? 'selected="selected"' : ''}><c:out value="${item.name}" /></option>
						</c:forEach>
				</select>
				
			    <label><!-- ОТ --><spring:message code="main-form.from"  /></label>
			 	<input type="text" name="from" class="datetimepicker_mask" value="${from}" readonly="readonly" >
			 	<label><!-- ДО --><spring:message code="main-form.to"  /></label>
			 	<input type="text" name="to" class="datetimepicker_mask" value="${to}" readonly="readonly" >
			 	<spring:message code="main-form.search" var="search" />
			 	<input type="submit" name="submit" value="${search}" class="button">
			
			
			
			</form>
			<c:if test="${haveResult > 0}">
				<table  id="beauty-table" >
				
				  <thead>
					   <tr>
						    <td align="center" valign="top">
						    	<!-- Номер поезда --><spring:message code="beauty-table.trainNumber"  />
						    </td>
						    <td align="center" valign="top">
						    	<!-- Отправление --><spring:message code="beauty-table.departure"  />
						    </td>
						    <td align="center" valign="top">
						    	<!-- Прибытие --><spring:message code="beauty-table.arrival"  />
						    </td>
					   </tr>
				   </thead>
				   <tbody>
				   		<c:if test="${emptyList == 1}">
				   			<tr><td colspan="3">
				   				<!-- Нет результатов --><spring:message code="beauty-table.emptyResult"  />
				   			</td></tr>
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