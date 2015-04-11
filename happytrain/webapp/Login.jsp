<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="menu.login"  /></title>
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/verify.notify.js" type="text/javascript"></script>
</head>
<body>
<div id="wrapper">
	<%@include file="Header.jsp" %>
	<div id="portfolio" class="container">
		
		<form action="<c:url value='/login' />" method='POST' id="input-form">
			<ul id="wrapper-ul">
				<li>
					<label><!-- Login --> <spring:message code="input-form.login"  /></label>
				    <input type="text" name="username" data-validate="required,alphanumeric">
				</li>
				<li>
				    <label><!-- Password --> <spring:message code="input-form.password"  /></label>
				    <input type="password" name="password" data-validate="required,alphanumeric">
				</li>
		    </ul>
		   <spring:message code="input-form.submit" var="submit" />
			<input type="submit" value="${submit}" class="button">
		</form>
		<c:if test="${failMessage != null }">
			<div id="message">
				<spring:message code="input-form.error"  />
			</div>
		</c:if>
	</div>
</div>
</body>
</html>