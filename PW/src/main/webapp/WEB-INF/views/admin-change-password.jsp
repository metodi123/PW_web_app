<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Смяна на парола</title>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/label-setup.js" />"></script>
</head>
<body>
<div id="wrapper">
    <div id="header">
        <a id="header-text" href="/app"><span class="text-underlined">WEB</span>LOG</a>
    </div>
    <div id="menu">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="<c:url value="/admin" />"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a>
				</div>
				<div class="navbar-inner">
					<ul class="nav navbar-nav">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Публикации<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/admin/posts/new" />">Нова публикация</a></li>
								<li><a href="<c:url value="/admin/posts" />">Публикации</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Етикети<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/admin/labels" />">Етикети</a></li>
								<li><a href="<c:url value="/admin/labels/colors" />">Цветове</a></li>
							</ul>
						</li>
						<li><a href="<c:url value="/admin/intro" />">Въведение</a></li>
						<li><a href="<c:url value="/admin/aboutTheAuthor" />">За мен</a></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Ресурси<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/admin/images" />">Изображения</a></li>
								<li><a href="<c:url value="/admin/files" />">Файлове</a></li>
							</ul>
						</li>
						<li class="dropdown active">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Профил<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li class="active"><a href="<c:url value="/admin/changePassword" />">Смяна на парола</a></li>
							</ul>
						</li>
					</ul>
					<ul class="nav navbar-nav navbar-right logout-button">
						<form class="navbar-form" action="/app/logout" method="post">
							<button class="btn btn-default navbar-right" type="submit">Изход</button>
						</form>
					</ul>
				</div>
			</div>
		</nav>
	</div>
	<div id="textbox">
			<form action="/app/changePassword" method="post" class="form-horizontal">
			<div class="form-group">
				<label for="password" class="col-sm-4 control-label">Парола: </label>
				<div class="col-sm-4">
					<input type="password" class="form-control" name="password" id="password" pattern="[a-zA-Z0-9-_!@#$%^&*\.]{1,35}" title="Въведете до 35 символа" required>
				</div>
			</div>
			<div class="form-group">
				<label for="newPassword" class="col-sm-4 control-label">Нова парола: </label>
				<div class="col-sm-4">
					<input type="password" class="form-control" name="newPassword" id="newPassword" pattern="[a-zA-Z0-9-_!@#$%^&*\.]{1,35}" title="Въведете до 35 символа" required>
				</div>
			</div>
			<div class="form-group">
				<label for="newPasswordRepeat" class="col-sm-4 control-label">Повторете новата парола: </label>
				<div class="col-sm-4">
					<input type="password" class="form-control" name="newPasswordRepeat" id="newPasswordRepeat" pattern="[a-zA-Z0-9-_!@#$%^&*\.]{1,35}" title="Въведете до 35 символа" required>
				</div>
			</div>
			<div class="form-group">
		    	<div class="col-sm-offset-4 col-sm-2">
			    	<input type="hidden" name="username" value="${sessionScope.currentUser.username}">
					<input type="hidden" name="currentPassword" value="${sessionScope.currentUser.password}">
					<input type="hidden" name="userType" value="admin">
		      		<button type="submit" class="btn btn-success btn-block">Изпрати</button>
		    	</div>
		  	</div>
		</form>
		<c:if test="${message == 'InvalidDataEntered'}">
			<div class="alert alert-warning" role="alert">
				Данните са невалидни.
			</div>
		</c:if>
	</div>
	<div id="footer">
	    <hr>
	    Copyright © 2017 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
	</div>
</div>
</body>
</html>