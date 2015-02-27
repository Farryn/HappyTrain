<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Добавить станцию</title>
</head>
<body>
<table width="100%">
	<tr>
		<td width="80%">
			<form  action="addstation" method="POST">
			    
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
			
			
		</td>
		
	</tr>
</table>
</body>
</html>