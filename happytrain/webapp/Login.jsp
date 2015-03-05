<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link href="/happytrain/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="/happytrain/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<script src="/happytrain/js/jquery.js"></script>
<script src="/happytrain/js/verify.notify.js" type="text/javascript"></script>
</head>
<body>
<div id="wrapper">
	<%@include file="Header.jsp" %>
	<div id="portfolio" class="container">
		<c:if test="${failMessage != null }">
			<div id="message">
				${failMessage}
			</div>
		</c:if>
		<form action="/happytrain/login" method="post" id="input-form">
			<ul id="wrapper-ul">
				<li>
					<label>Login</label>
				    <input type="text" name="login" data-validate="required,alphanumeric">
				</li>
				<li>
				    <label>Password</label>
				    <input type="password" name="password" data-validate="required,alphanumeric">
				</li>
		    </ul>
		    <input type="hidden" name="url" value="${URL}">
			<input type="hidden" name="servletUrl" value="${servletUrl}">
			<input type="submit" value="Submit" class="button">
		</form>
	</div>
</div>
</body>
</html>