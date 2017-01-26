<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Вход</title>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
</head>
<body>
<div id="wrapper">
	<div id="header">
        <a id="header-text" href="/app"><span class="text-underlined">WEB</span>LOG</a>
    </div>
    <div id="loginbox">
		<form action="/app/admin/login" method="post">
			<div class="row">
				<div class="col-sm-9">
					<div class="row">
						<div class="col-sm-offset-6 col-sm-4">
							<label for="username">Потребителско име: </label><br>
							<input type="text" class="form-control" name="username" id="username" pattern="[a-zA-Z0-9-_\.]{1,35}" title="Въведете до 35 символа" required>
						</div>
					</div>
					<div class="row">
					<br>
						<div class="col-sm-offset-6 col-sm-4">
							<label for="password">Парола: </label><br>
							<input type="password" class="form-control" name="password" id="password" pattern="[a-zA-Z0-9-_!@#$%^&*\.]{1,35}" title="Въведете до 35 символа" required>
						</div>
					</div>
					<div class="row">
					<br>
						<div class="col-sm-offset-7 col-sm-2">
							<input type="hidden" name="userType" value="admin">
							<button type="submit" class="btn btn-primary btn-block">Вход</button>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<br>
				<div class="col-sm-offset-3 col-sm-6">
					<c:if test="${message == 'InvalidUser'}">
		    			<div class="alert alert-warning" role="alert">
		   					Въведено е грешно потребителско име или парола.
		   				</div>
					</c:if>
				</div>
			</div>
		</form>
	</div>
	<div id="footer">
		<hr>
		Copyright © 2017 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
	</div>
</div>
</body>
</html>