<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> <spring:message code="menu.register"  /></title>
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
	<%@include file="Header.jsp" %>
	<div id="portfolio" class="container">
	<c:if test="${fail == null}">
		<form action="register" method="POST" id="input-form">
			<ul id="wrapper-ul">
				<li>
					<label><!-- Имя --><spring:message code="input-form.name"  /></label>
					<input type="text" name="first_name" data-validate="required,alpha"/>
				</li>
				<li>
					<label><!-- Фамилия --><spring:message code="input-form.lastName"  /></label>
					<input type="text" name="last_name" data-validate="required,alpha"/>
				</li>
				<li>
					<label><!-- Дата рождения --><spring:message code="input-form.birthDate"  /></label>
					<input type="text" name="birth_date" value="${from}" class="datetimepicker_mask" readonly="readonly"/>
				</li>
				<li>
					<label><!-- Логин --><spring:message code="input-form.login"  /></label>
					<input type="text" name="login" data-validate="required,alphanumeric"/>
				</li>
				<li>
					<label><!-- Пароль --><spring:message code="input-form.password"  /></label>
					<input type="password" name="password" data-validate="required,alphanumeric"/>
				</li>
			</ul>
			<spring:message code="input-form.submit"  var="submit"/>
			<input type="submit" value="${submit }" class="button"/>
		</form>
	</c:if>
	<c:if test="${fail == 0}">
			<div id="message"> <!-- Операция завершена успешно --> <spring:message code="message.success"  /></div>
	</c:if>
	<c:if test="${fail == 1}">
			<div id="message"> <!-- Ошибка при добавлении данных --><spring:message code="message.failAdding"  /></div>
	</c:if>
	</div>
	</div>
</body>
</html>