<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список пассажиров</title>
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
   <caption>Список пассажиров</caption>
   <thead>
	   <tr>
	    	<td align="center" valign="top">Имя</td>
	    	<td align="center" valign="top">Фамилия</td>
		    <td align="center" valign="top">Дата рождения</td>
		    <td align="center" valign="top">Станция отправления</td>
		    <td align="center" valign="top">Станция прибытия</td>
	   </tr>
   </thead>
   <tbody>
	   <c:forEach var="item" items="${passengerList}" varStatus="status">
			<tr>
				<td ><c:out value="${item.userId.firstName}" /></td>
				<td ><c:out value="${item.userId.lastName}" /></td>
				<td ><c:out value="${item.userId.birthDate}" /></td>
				<td ><c:out value="${item.stationFrom}" /></td>
				<td ><c:out value="${item.stationTo}" /></td>
			</tr>
	   </c:forEach>
   </tbody>
 </table>


</body>
</html>