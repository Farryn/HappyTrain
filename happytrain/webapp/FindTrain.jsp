<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> <spring:message code="menu.findTrain"  /></title>
<link href="<c:url value="resources/css/default.css" />" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js" type="text/javascript"></script>


<script src="${pageContext.request.contextPath}/resources/js/verify.notify.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.datetimepicker.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/global.js" type="text/javascript"></script>

</head>
<body>
<div id="wrapper">
	
		<%@include file="Header.jsp" %>
	
		<div id="portfolio" class="container">
			<form  action="showtrain" method="GET" id="main-form">
			    <label><!-- От станции --> <spring:message code="main-form.fromStation"  /></label>
			 	<select name="stationFrom" >
				    		  <c:forEach var="item" items="${stationList}">
							  	<option value="${item.name}" ${item.name == stationFrom? 'selected="selected"' : ''}><c:out value="${item.name}" /></option>
							  </c:forEach>
				</select>
			 	<label><!-- До станции --><spring:message code="main-form.toStation"  /></label>
			 	<select name="stationTo" >
				    		  <c:forEach var="item" items="${stationList}">
							  	<option value="${item.name}" ${item.name == stationTo ? 'selected="selected"' : ''}><c:out value="${item.name}" /></option>
							  </c:forEach>
				</select>
			 	<label><!-- От --> <spring:message code="main-form.from"  /></label>
			 	<input type="text" name="from" required class="datetimepicker_mask" value="${from}" readonly="readonly" />
			 	<label><!-- До --> <spring:message code="main-form.to"  /></label>
			 	<input type="text" name="to" required class="datetimepicker_mask" value="${to}" readonly="readonly"/>
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
						    	<!-- Отправление --> <spring:message code="beauty-table.departure"  />
						    </td>
						    <td align="center" valign="top">
						    	<!-- Прибытие --> <spring:message code="beauty-table.arrival"  />
						    </td>
						    <td align="center" valign="top">
						    	<!-- Места --> <spring:message code="beauty-table.seatsCount"  />
						    </td>
						    <td align="center" valign="top">
						    	<!-- Маршрут --> <spring:message code="beauty-table.route"  />
						    </td>
						    <c:if test="${pageContext.request.userPrincipal.name != null}">
						    	<td align="center" valign="top">
						    		<!-- Покупка --> <spring:message code="beauty-table.buy"  />
						    	</td>
						    </c:if>
						    
					   </tr>
				   </thead>
				   
				   <tbody>
				   		<c:if test="${emptyList == 1}">
				   			<tr><td colspan="<c:if test="${pageContext.request.userPrincipal.name != null}">6</c:if>
				   							 <c:if test="${pageContext.request.userPrincipal.name == null}">5</c:if>" >
				   				<!-- Нет результатов --><spring:message code="beauty-table.emptyResult"  />
				   			</td></tr>
				   		</c:if>
						   <c:forEach var="item" items="${timetableList}" varStatus="status">
								<tr>
									<td ><c:out value="${item.trainNumber}" /></td>
									<td ><c:out value="${item.departureDateTime}" /></td>
									<td ><c:out value="${item.arrivalDateTime}" /></td>
									<td ><c:out value="${item.availableSeats}" /></td>
									<td ><a href="route?train=${item.trainId}&run=${item.runId}">
										<!-- Список станций --><spring:message code="beauty-table.stationList"  />
									</a></td>
									<c:if test="${pageContext.request.userPrincipal.name != null}">
										<td >
											<a href="protected/BuyTicket.jsp?train=${item.trainNumber}&run=${item.runId}&stationFrom=${stationFrom}&stationTo=${stationTo}&depTime=${item.departureDateTime}">
												<!-- Купить билет --><spring:message code="beauty-table.buyTicket"  />
											</a
										></td>
									</c:if>
								</tr>
						   </c:forEach>
				   </tbody>
				   
				   
				
				 </table>
			 </c:if>
			</div>
		</div>	

</body>
</html>