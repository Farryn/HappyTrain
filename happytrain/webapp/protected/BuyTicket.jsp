<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="menu.purchase"  /></title>
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body>
<div id="wrapper">
	<%@include file="/Header.jsp" %>
	<div id="portfolio" class="container">
		<c:if test="${fail == null}">
			<form action="${pageContext.request.contextPath}/buyticket" method="POST" id="input-form">
				<ul id="wrapper-ul">
					<%--<li>
						<label>Имя</label>
						<input type="text" name="firstName" value="${user.firstName}" />
					</li>
					<li>
						<label>Фамилия</label>
						<input type="text" name="lastName" value="${user.lastName}" />
					</li>
					<li>
						<label>Дата рождения</label>
						<input type="text" name="birthDate" value="${user.birthDate}" />
					</li> --%>
					<li>
						<label><!-- Номер поезда --><spring:message code="beauty-table.trainNumber"  /></label>
						<input type="text" name="train" value="${param.train}" readonly="readonly"/>
					</li>
					<li>
						<label><!-- Станция отправления --><spring:message code="beauty-table.depStation"  /></label>
						<input type="text" name="stationFrom" value="${param.stationFrom}" readonly="readonly"/>
					</li>
					<li>
						<label><!-- Станция прибытия --><spring:message code="beauty-table.arrStation"  /></label>
						<input type="text" name="stationTo" value="${param.stationTo}" readonly="readonly"/>
					</li>
					<li>
						<label><!-- Время отправления --><spring:message code="beauty-table.depTime"  /></label>
						<input type="text" name="depTime" value="${param.depTime}" readonly="readonly"/>
					</li> 
				</ul>
				<input type="hidden" name="depTime" value="${param.depTime}" />
				<input type="hidden" name="run" value="${param.run}" />
				<spring:message code="input-form.buy" var="buy" />
				<input type="submit" value="${buy }" class="button"/>
			</form>
		</c:if>
		<c:if test="${fail == 0}">
			<div id="message"> <!-- Операция завершена успешно --><spring:message code="message.success" /></div>
		</c:if>
		<c:if test="${fail == 1}">
			<div id="message"> <spring:message code="message.noSeats" /></div>
		</c:if>
		<c:if test="${fail == 2}">
			<div id="message"> <spring:message code="message.alreadyRegistered" /></div>
		</c:if>
		<c:if test="${fail == 3}">
			<div id="message"> <spring:message code="message.tooLate" /></div>
		</c:if>
		<c:if test="${fail == 4}">
			<div id="message"> <spring:message code="message.failAdding" /></div>
		</c:if>
	</div>
	</div>
</body>
</html>