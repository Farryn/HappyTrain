<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
	<div id="header-wrapper">
		<div id="header" class="container">
			<div id="logo">
				<h1><a href="/happytrain"><span>HappyTrain</span></a></h1>
			</div>
			<div id="login">
				<span>Добро пожаловать, </span>
				<span id="name"><c:out value="${user.login}" default="Гость" /></span>
					<c:if test="${user == null}">
						<br/><span><a href="/happytrain/Login.jsp">Войдите</a> или <a href="/happytrain/Register.jsp">зарегистрируйтесь</a></span>
					</c:if>
					<c:if test="${user != null}">
						<br/><span><a href="/happytrain/logout">Выйти</a></span>
					</c:if>
			</div>
		</div>
		<div id="menu" class="container">
			<ul>
				<li ><a href="/happytrain" accesskey="1" title="">Поиск поезда</a></li>
				<li><a href="/happytrain/timetable" accesskey="1" title="">Расписание</a></li>
				<c:if test="${user.role.name == 'admin' or user.role.name == 'employee'}">
					<li><a href="/happytrain/alltrains" accesskey="2" title="">Все поезда</a></li>
					<li><a href="/happytrain/protected/AddStation.jsp" accesskey="3" title="">Добавить станцию</a></li>
					<li><a href="/happytrain/protected/AddTrain.jsp" accesskey="4" title="">Добавить поезд</a></li>
				</c:if>
			</ul>
		</div>
	</div>