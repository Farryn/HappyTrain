<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список поездов</title>
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

   <caption>Список поездов</caption>
  <thead>
	   <tr>
	    <td align="center" valign="top">Номер поезда</td>
	    <td align="center" valign="top">Количество мест</td>
	    <td align="center" valign="top">Маршрут</td>
	    <td align="center" valign="top">Добавить рейс</td>
	   </tr>
   </thead>
   <tbody>
	   <c:forEach var="item" items="${trainList}" varStatus="status">
	   		
					<tr>
					
						<td ><c:out value="${item.number}" /></td>
						<td ><c:out value="${item.seatsCount}" /></td>
						<td ><a href="route?train=${item.id}">Список станций</a></td>
						<td ><a href="AddRun.jsp?train=${item.id}">Добавить рейс</a></td>
					</tr>
				
			
	   </c:forEach>
   </tbody>
   
   

 </table>


</body>
</html>