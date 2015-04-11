<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="menu.addRun"  /></title>
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css"/>
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.datetimepicker.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/global.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/verify.notify.js" type="text/javascript"></script>
</head>
<body>
<div id="wrapper">
	<%@include file="/Header.jsp" %>
	<div id="portfolio" class="container">
		<c:if test="${fail == null}">
			 <form action="/happytrain/addrun" method="post">   
				 <table  id="beauty-table" >
				   <thead>
					   <tr>
					    	<td align="center" valign="top">
					    		<!-- Станция --> <spring:message code="beauty-table.stationName"  />
					    	</td>
						    <td align="center" valign="top">
						    	<!-- Прибытие --> <spring:message code="beauty-table.arrival"  />
						    </td>
						    <td align="center" valign="top">
						   		<!-- Отправление --> <spring:message code="beauty-table.departure"  />
						    </td>
					   </tr>
				   </thead>
				   <tbody>
				       
						   <c:forEach var="item" items="${stationList}" varStatus="status">
								<tr>
									<td >
										<c:out value="${item.name}" />
										<input type="hidden" name="stationList[]" value="${item.name}"/>
									</td>
									<td ><input type="text" name=arrivalTime[] class="datetimepicker_mask" readonly="readonly" data-validate="required"/> </td>
									<td ><input type="text" name=departureTime[] class="datetimepicker_mask" readonly="readonly" data-validate="required"/></td>
								</tr>
						   </c:forEach>
						   <tr>
						   		<td colspan="3">
									<input type="hidden" name="train" value="${train}"/>			   			
						   			<spring:message code="input-form.submit"  var="submit"/>
									<input type="submit"  value="${submit }" class="button">
						   		</td>
						   </tr>
					   
				   </tbody>
				 </table>
			 </form>
		</c:if>
		<c:if test="${fail == 0}">
			<span id="message"> <!-- Операция завершена успешно --> <spring:message code="message.success" /></span>
		</c:if>
		<c:if test="${fail == 1}">
			<span id="message"> <!-- Ошибка при добавлении данных --> <spring:message code="message.failAdding" /></span>
		</c:if>
	</div>
</div>
</body>
</html>