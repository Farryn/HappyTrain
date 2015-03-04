<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/happytrain/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="/happytrain/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<title>Добавить станцию</title>

</head>
<body>
<div id="wrapper">
	<%@include file="/Header.jsp" %>
		<div id="portfolio" class="container">
			<c:if test="${fail == null}">
				<form  action="/happytrain/addstation" method="POST" id="input-form">
					<ul id="wrapper-ul">
						<li>
							<label>Название станции</label>
							<input type="text" name="stationName" />
						</li>
					</ul>
					<input type="submit"  value="Отправить" class="button">
				</form>
			</c:if>
			<c:if test="${fail == 0}">
				<div id="message"> Операция завершена успешно</div>
			</c:if>
			<c:if test="${fail == 1}">
				<div id="message"> Ошибка при добавлении данных</div>
			</c:if>
		</div>	
			

</div>
</body>
</html>