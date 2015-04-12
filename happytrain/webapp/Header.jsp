<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>   
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	<div id="header-wrapper">
		<div id="header" class="container">
			<div id="logo">
				<h1><a href="${pageContext.request.contextPath}/"><span>HappyTrain</span></a></h1>
			</div>
			<div id="login">
				<span> <spring:message code="login.welcome"  />, </span>
				
					<c:if test="${pageContext.request.userPrincipal.name == null}">
						<span id="name">
							<spring:message code="login.guest"  />
						</span>
						<br/>
						<span>
							<a href="${pageContext.request.contextPath}/login">
								<spring:message code="login.login"  />
							</a> 
							<spring:message code="login.or"  />
							<a href="${pageContext.request.contextPath}/register">
								<spring:message code="login.register"  />
							</a>
						</span>
					</c:if>
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<span id="name">
							<c:out value="${pageContext.request.userPrincipal.name}"  />
						</span>
						<br/>
						<span>
							<a href="${pageContext.request.contextPath}/j_spring_security_logout">
								<spring:message code="login.logout"  />
							</a>
						</span>
					</c:if>
			</div>
			<div id="locale">
					<ul >
						<li>
							<span>
								<a href="${pageContext.request.contextPath}/?lang=en">
									<img src="${pageContext.request.contextPath}/resources/css/images/GB.png"/>
								</a>
							</span>
						</li>
						<li>
							<span>
								<a href="${pageContext.request.contextPath}/?lang=ru">
									<img src="${pageContext.request.contextPath}/resources/css/images/RU.png"/>
								</a>
							</span>
						</li>
						<li>
							<span>
								<a href="${pageContext.request.contextPath}/?lang=de">
									<img src="${pageContext.request.contextPath}/resources/css/images/DE.png"/>
								</a>
							</span>
						</li>
					</ul>
			</div>
			
		</div>
		<div id="menu" class="container">
			<ul>
				<li >
					<a href="${pageContext.request.contextPath}/" accesskey="1" title="">
						<spring:message code="menu.findTrain"  />
					</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/timetable" accesskey="1" title="">
						<spring:message code="menu.timetable"  />
					</a>
				</li>
				<sec:authorize access="hasAnyAuthority('admin, employee')">
						<li>
							<a href="${pageContext.request.contextPath}/alltrains" accesskey="2" title="">
								<spring:message code="menu.allTrains"  />
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/addstation" accesskey="3" title="">
								<spring:message code="menu.addStation"  />
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/addtrain" accesskey="4" title="">
								<spring:message code="menu.addTrain"  />
							</a>
						</li>
				</sec:authorize>
			</ul>
		</div>
	</div>