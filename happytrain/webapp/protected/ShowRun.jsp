<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список рейсов</title>
</head>
<body>
<header>
	<div style="float:right">
					<span>Добро пожаловать, <c:out value="${user.login}" default="Гость" /></span>
					<c:if test="${user == null}">
						<br/><span><a href="Login.jsp">Войдите</a> или <a href="Register.jsp">зарегистрируйтесь</a></span>
					</c:if>
					<c:if test="${user != null}">
						<br/><span><a href="logout">Выйти</a></span>
					</c:if>
	</div>
</header>

    
 <table  border="1" >
   <caption>Список рейсов</caption>
   <thead>
	   <tr>
	    	<td align="center" valign="top">Номер поезда</td>
	    	<td align="center" valign="top">Отправление</td>
		    <td align="center" valign="top">Прибытие</td>
		    <td align="center" valign="top">Пассажиры</td>
	   </tr>
   </thead>
   <tbody>
	   <c:forEach var="item" items="${runList}" varStatus="status">
			<tr>
				<td ><c:out value="${item.trainId.number}" /></td>
				<td ><c:out value="${item.startTime}" /></td>
				<td ><c:out value="${item.finishTime}" /></td>
				<td ><a href="passenger?run=${item.id}"> Показать пассажиров</a></td>
			</tr>
	   </c:forEach>
   </tbody>
 </table>


</body>
</html>