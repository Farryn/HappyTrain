<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/happytrain/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="/happytrain/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js" type="text/javascript"></script>
<script>
$(document).ready(function() {
    var wrapper         = $("#wrapper-ul"); //Fields wrapper
    var add_button      = $("#add_button"); //Add button ID
    var remove_button   = $(".remove_field_button"); //Add button ID
    var td_to_remove    = $(".td_to_remove");
    
    $(add_button).click(function(e){ //on add input button click
    	e.preventDefault();
        $(wrapper).append('<li>'+
				            		'<select name="stationList[]" >'+
				            			'<c:forEach var="item" items="${stationList}">'+
						  					'<option value="${item.name}"> <c:out value="${item.name}" /> </option>'+
						  				'</c:forEach></select>'+
						  				
					            		//'<input type="button" value="Удалить станцию" class="remove_field_button">'+
			            		'</li>'); 
        
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
<div id="wrapper">
	<div id="header-wrapper">
		<div id="header" class="container">
			<div id="logo">
				<h1><a href="/happytrain"><span>HappyTrain</span></a></h1>
			</div>
			<div id="login">
				<span>Добро пожаловать, </span>
				<span id="name"><c:out value="${user.login}" default="Гость" /></span>
					<c:if test="${user == null}">
						<br/><span><a href="Login.jsp">Войдите</a> или <a href="Register.jsp">зарегистрируйтесь</a></span>
					</c:if>
					<c:if test="${user != null}">
						<br/><span><a href="logout">Выйти</a></span>
					</c:if>
			</div>
		</div>
		<div id="menu" class="container">
			<ul>
				<li class="current_page_item"><a href="/happytrain" accesskey="1" title="">Поиск поезда</a></li>
				<li><a href="timetable" accesskey="1" title="">Расписание</a></li>
				<li><a href="alltrains" accesskey="2" title="">Все поезда</a></li>
				<li><a href="/happytrain/protected/AddStation.jsp" accesskey="3" title="">Добавить поезд</a></li>
				<li><a href="/happytrain/protected/AddTrain.jsp" accesskey="4" title="">Добавить станцию</a></li>
			</ul>
		</div>
	</div>
	
		<div id="portfolio" class="container">
			<c:if test="${fail == null}">
				<form  action="/happytrain/addtrain" method="POST" id="input-form">
					<ul id="wrapper-ul">
						<li>
							<label>Номер поезда</label>
							<input type="text" name="trainNumber" />
						</li>
						<li>
							<label>Число мест</label>
							<input type="text" name="seatsCount"  />
						</li>
						
						<li>
							<label>Маршрут</label>
							<input type="button" value="Добавить станцию" class="button" id="add_button">
						</li>
						<li>
							<select name="stationList[]" >
							    	<c:forEach var="item" items="${stationList}">
										  	<option value="${item.name}" ><c:out value="${item.name}" /></option>
									</c:forEach>
							</select>
						</li>
						
						
					</ul>
					<input type="submit"  value="Отправить" class="button">
				</form>
			</c:if>
			<c:if test="${fail == 0}">
				<div id="message"> Операция завершена успешно</div>
			</c:if>
			<c:if test="${fail == 1}">
				<div id="message"> Ошибка при добавлении данных</div>
			</c:if>
		</div>	
</div>		

</body>
</html>