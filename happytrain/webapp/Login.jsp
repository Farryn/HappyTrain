<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<form action="login" method="post">
	<label>Login</label>
    <input type="text" name="login">
    <label>Password</label>
    <input type="password" name="password">
    <input type="hidden" name="url" value="${URL}">
    <input type="hidden" name="servletUrl" value="${servletUrl}">
    <input type="submit" value="Submit"> ${error}
</form>
</body>
</html>