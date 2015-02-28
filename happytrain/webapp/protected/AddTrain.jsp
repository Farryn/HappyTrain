<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
    var max_fields      = 10; //maximum input boxes allowed
    var wrapper         = $(".input_fields_wrap"); //Fields wrapper
    var add_button      = $(".add_field_button"); //Add button ID
    var remove_button   = $(".remove_field_button"); //Add button ID
    var td_to_remove    = $(".td_to_remove");
    
    var x = 1; //initlal text box count
    $(add_button).click(function(e){ //on add input button click
        e.preventDefault();
        if(x < max_fields){ //max input box allowed
            x++; //text box increment
            $(wrapper).append('<tr class="td_to_remove">'+
				            		'<td ><select name="stationList[]" >'+
				            			'<c:forEach var="item" items="${stationList}">'+
						  					'<option value="${item.name}" ><c:out value="${item.name}" /></option>'+
						  				'</c:forEach></select></td>'+
				            		'<td><input type="button" value="Удалить станцию" class="remove_field_button"></td>'+
			            		'</tr>'); 
        }
    });
    $(remove_button).on("click", function(e) {
    	e.preventDefault();
        $('tr').remove();
  });

   
});

</script>
<title>Добавить поезд</title>
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

			<form  action="/happytrain/addtrain" method="POST">
			    
			 <table  border="0"  class="input_fields_wrap">
				
				<tr>
				    <td align="right" valign="top">Номер поезда</td>
				</tr>
			    <tr>
				    <td><input type="text" name="trainNumber" ></td>
			    </tr>
			    <tr>
				    <td align="right" valign="top">Число мест</td>
				</tr>
			    <tr>
				    <td><input type="text" name="seatsCount"  ></td>
			    </tr>
			    <tr>
				    <td align="right" valign="top">Маршрут</td>
				</tr>
				<tr>
					<td><input type="button" value="Добавить станцию" class="add_field_button"></td>
				</tr>
				<tr>
					<td>
						<select name="stationList[]" >
				    		  <c:forEach var="item" items="${stationList}">
							  	<option value="${item.name}" ${item.name == stationFrom.name ? 'selected="selected"' : ''}><c:out value="${item.name}" /></option>
							  </c:forEach>
						</select>
					</td>
				</tr>
				
			   
			
			  
			
			 </table>
			<input type="submit"  value="Отправить" >
			</form>
			
			

</body>
</html>