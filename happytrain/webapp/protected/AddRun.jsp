<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Добавить рейс</title>
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

 <form action="/happytrain/addrun" method="post">   
	 <table  border="1" >
	   <caption>Добавить рейс</caption>
	   <thead>
		   <tr>
		    	<td align="center" valign="top">Станция</td>
			    <td align="center" valign="top">Прибытие</td>
			    <td align="center" valign="top">Отправление</td>
		   </tr>
	   </thead>
	   <tbody>
	       
			   <c:forEach var="item" items="${stationList}" varStatus="status">
					<tr>
						<td >
							<c:out value="${item.name}" />
							<input type="hidden" name="stationList[]" value="${item.name}"/>
						</td>
						<td ><input type="text" name=arrivalTime[] /> </td>
						<td ><input type="text" name=departureTime[] /></td>
					</tr>
			   </c:forEach>
			   <tr>
			   		<td>
						<input type="hidden" name="train" value="${train}"/>			   			
			   			<input type="submit" value="Добавить" />
			   		</td>
			   </tr>
		   
	   </tbody>
	 </table>
</form>

</body>
</html>