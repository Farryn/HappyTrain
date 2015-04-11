<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<script src="${pageContext.request.contextPath}/resources/js/jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/verify.notify.js" type="text/javascript"></script>
<title><spring:message code="menu.addStation"  /></title>

</head>
<body>
<div id="wrapper">
	<%@include file="/Header.jsp" %>
		<div id="portfolio" class="container">
			<c:if test="${fail == null}">
				<form  action="/happytrain/addstation" method="POST" id="input-form">
					<ul id="wrapper-ul">
						<li>
							<label><!-- Название станции --><spring:message code="beauty-table.stationName"  /> </label>
							<input type="text" name="stationName" data-validate="required,alphanumeric"/>
						</li>
					</ul>
					<spring:message code="input-form.submit" var="submit" />
					<input type="submit"  value="${submit }" class="button">
				</form>
			</c:if>
			<c:if test="${fail == 0}">
				<div id="message"> <!-- Операция завершена успешно --><spring:message code="message.success" /></div>
			</c:if>
			<c:if test="${fail == 1}">
				<div id="message"> <!-- Ошибка при добавлении данных --> <spring:message code="message.failAdding" /></div>
			</c:if>
		</div>	
			

</div>
</body>
</html>