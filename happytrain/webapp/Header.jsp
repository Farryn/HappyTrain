<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>   
	<div id="header-wrapper">
		<div id="header" class="container">
			<div id="logo">
				<h1><a href="${pageContext.request.contextPath}/"><span>HappyTrain</span></a></h1>
			</div>
			<div id="login">
				<span>Добро пожаловать, </span>
				<span id="name"><c:out value="${pageContext.request.userPrincipal.name}" default="Гость" /></span>
					<c:if test="${pageContext.request.userPrincipal.name == null}">
						<br/><span><a href="${pageContext.request.contextPath}/login">Войдите</a> или 
						<a href="${pageContext.request.contextPath}/register">зарегистрируйтесь</a></span>
					</c:if>
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<br/><span><a href="${pageContext.request.contextPath}/j_spring_security_logout">Выйти</a></span>
					</c:if>
			</div>
		</div>
		<div id="menu" class="container">
			<ul>
				<li ><a href="${pageContext.request.contextPath}/" accesskey="1" title="">Поиск поезда</a></li>
				<li><a href="${pageContext.request.contextPath}/timetable" accesskey="1" title="">Расписание</a></li>
				<sec:authorize access="hasAnyAuthority('admin, employee')">
						<li><a href="${pageContext.request.contextPath}/alltrains" accesskey="2" title="">Все поезда</a></li>
						<li><a href="${pageContext.request.contextPath}/addstation" accesskey="3" title="">Добавить станцию</a></li>
						<li><a href="${pageContext.request.contextPath}/addtrain" accesskey="4" title="">Добавить поезд</a></li>
				</sec:authorize>
			</ul>
		</div>
	</div>