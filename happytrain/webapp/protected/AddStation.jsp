<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Добавить станцию</title>
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

			<form  action="/happytrain/addstation" method="POST">
			    
			 <table  border="0" >
				
				<tr>
				    <td align="right" valign="top">Название станции</td>
				</tr>
			    <tr>
				    <td><input type="text" name="stationName" size="50" ></td>
			    </tr>
			    <tr>
				    <td><input type="submit"  value="Отправить" ></td>
			    </tr>
			
			  
			
			 </table>
			
			</form>


</body>
</html>