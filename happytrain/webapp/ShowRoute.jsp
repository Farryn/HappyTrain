<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список станций</title>
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

   <caption>Список станций</caption>
  <thead>
	   <tr>
	    <td align="center" valign="top">Название станции</td>
	    <c:if test="${haveRun > 0}">
		    <td align="center" valign="top">Прибытие</td>
		    <td align="center" valign="top">Отправление</td>
	    </c:if>
	   </tr>
   </thead>
   <tbody>
	   <c:forEach var="item" items="${stationList}" varStatus="status">
			<tr>
				<td ><c:out value="${item.name}" /></td>
				<c:if test="${haveRun > 0}">
					<td ><c:out value="${arrivalDateTime[status.index]}" /></td>
					<td ><c:out value="${departureDateTime[status.index]}" /></td>
				</c:if>
			</tr>
	   </c:forEach>
   </tbody>
   
   

 </table>


</body>
</html>