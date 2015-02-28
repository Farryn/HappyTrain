<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Покупка билета</title>
</head>
<body>
	<form action="buyticket" method="POST">
		<label>Имя</label><br/>
		<input type="text" name="first_name" value="${user.firstName}" /><br/>
		<label>Фамилия</label><br/>
		<input type="text" name="last_name" value="${user.lastName}" /><br/>
		<label>Дата рождения</label><br/>
		<input type="text" name="birth_date" value="${user.birthDate}" /><br/>
		<label>Номер поезда</label><br/>
		<input type="text" name="train_number" value="${param.train}" /><br/>
		<label>Станция отправления</label><br/>
		<input type="text" name="station_from" value="${param.stationFrom}" /><br/>
		<label>Станция прибытия</label><br/>
		<input type="text" name="station_to" value="${param.stationTo}" /><br/>
		<label>Время отправления</label><br/>
		<input type="text" name="dep_time" value="${param.depTime}" /><br/>
		<input type="hidden" name="run_id" value="${param.run}" />
		<input type="submit" value="Купить" />
	</form>
</body>
</html>