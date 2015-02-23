<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список станций</title>
</head>
<body>


    
 <table  border="1" >

   <caption>Список станций</caption>
  <thead>
	   <tr>
	    <td align="center" valign="top">Название станции</td>
	    <td align="center" valign="top">Прибытие</td>
	    <td align="center" valign="top">Отправление</td>
	   </tr>
   </thead>
   <tbody>
	   <c:forEach var="item" items="${stationList}" varStatus="status">
			<tr>
				<td ><c:out value="${item.name}" /></td>
				<td ><c:out value="${arrivalDateTime[status.index]}" /></td>
				<td ><c:out value="${departureDateTime[status.index]}" /></td>
			</tr>
	   </c:forEach>
   </tbody>
   
   

 </table>


</body>
</html>