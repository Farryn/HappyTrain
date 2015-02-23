<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Поиск поезда</title>
</head>
<body>

<form name="forma1" action="showtrain" method="GET">
    
 <table  border="0" >

   <caption>Поиск поезда</caption>

   <tr>
    <td align="right" valign="top">От станции</td>
    <td>
    	<select name="stationFrom" >
    		  <c:forEach var="item" items="${stationList}">
			  	<option value="${item.name}"><c:out value="${item.name}" /></option>
			  </c:forEach>
		</select>
	</td>
    <td align="right" valign="top">До станции</td>
    <td>
		<select name="stationTo" >
    		  <c:forEach var="item" items="${stationList}">
			  	<option value="${item.name}"><c:out value="${item.name}" /></option>
			  </c:forEach>
		</select>

	</td>
   </tr>

   <tr>
    <td align="right" valign="top">Время от</td>
    <td><input type="text" name="from" size="25"></td>
    <td align="right" valign="top">Время до</td>
    <td><input type="text" name="to" size="25"></td>
   </tr>
   <tr>
   <td align="right" colspan="2">
    
    </td>
    <td align="right" colspan="2">
     <input type="submit" name="submit" value="Отправить">
     <input type="reset" name="reset" value="Очистить">
    </td>
   </tr>

 </table>

</form>
</body>
</html>