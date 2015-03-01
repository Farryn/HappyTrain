<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Покупка билета</title>
</head>
<body>
	<form action="/happytrain/buyticket" method="POST">
		<label>Имя</label><br/>
		<input type="text" name="firstName" value="${user.firstName}" /><br/>
		<label>Фамилия</label><br/>
		<input type="text" name="lastName" value="${user.lastName}" /><br/>
		<label>Дата рождения</label><br/>
		<input type="text" name="birthDate" value="${user.birthDate}" /><br/>
		<label>Номер поезда</label><br/>
		<input type="text" name="train" value="${param.train}" /><br/>
		<label>Станция отправления</label><br/>
		<input type="text" name="stationFrom" value="${param.stationFrom}" /><br/>
		<label>Станция прибытия</label><br/>
		<input type="text" name="stationTo" value="${param.stationTo}" /><br/>
		<label>Время отправления</label><br/>
		<input type="text" name="depTime" value="${param.depTime}" /><br/>
		<input type="hidden" name="run" value="${param.run}" />
		<input type="submit" value="Купить" />
	</form>
</body>
</html>