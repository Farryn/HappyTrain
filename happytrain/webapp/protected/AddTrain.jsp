<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/verify.notify.js" type="text/javascript"></script>

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
						  				
					            		//'<button type="button" class="remove_field_button"><img src="/happytrain/css/images/cross2.png"></button>'+
			            		'</li>'); 
        
    });
    $(".remove_field_button").click(function() {
    	alert("da");
    	$('li').remove();
        
  });

   
});

</script>
<title><spring:message code="menu.addTrain"  /></title>

</head>
<body>
<div id="wrapper">
	<%@include file="/Header.jsp" %>
		<div id="portfolio" class="container">
			<c:if test="${fail == null}">
				<form  action="/happytrain/addtrain" method="POST" id="input-form">
					<ul id="wrapper-ul">
						<li>
							<label><!-- Номер поезда --><spring:message code="beauty-table.trainNumber"  /></label>
							<input type="text" name="trainNumber" data-validate="required,alphanumeric"/>
						</li>
						<li>
							<label><!-- Число мест --><spring:message code="beauty-table.seatsCount"  /></label>
							<input type="text" name="seatsCount" data-validate="required,number" />
						</li>
						
						<li>
							<label><!-- Маршрут --><spring:message code="beauty-table.route"  /></label>
							<spring:message code="input-form.add"  var="add"/>
							<input type="button" value="${add}" class="button" id="add_button">
						</li>
						<li>
							<select name="stationList[]" >
							    	<c:forEach var="item" items="${stationList}">
										  	<option value="${item.name}" ><c:out value="${item.name}" /></option>
									</c:forEach>
							</select>
						</li>
						<li>
							<select name="stationList[]" >
							    	<c:forEach var="item" items="${stationList}">
										  	<option value="${item.name}" ><c:out value="${item.name}" /></option>
									</c:forEach>
							</select>
						</li>
						
						
					</ul>
					<spring:message code="input-form.submit"  var="submit"/>
					<input type="submit"  value="${submit }" class="button">
				</form>
			</c:if>
			<c:if test="${fail == 0}">
				<div id="message"><!-- Операция завершена успешно --> <spring:message code="message.success" /></div>
			</c:if>
			<c:if test="${fail == 1}">
				<div id="message"> <!-- Ошибка при добавлении данных --> <spring:message code="message.failAdding" /></div>
			</c:if>
		</div>	
</div>	


</body>
</html>