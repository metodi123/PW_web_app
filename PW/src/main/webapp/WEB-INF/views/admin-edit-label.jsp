<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Етикет - редактиране</title>
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
						<li class="dropdown active">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Етикети<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li class="active"><a href="<c:url value="/admin/labels" />">Етикети</a></li>
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
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Профил<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/admin/changePassword" />">Смяна на парола</a></li>
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
		<form action="/app/admin/labels/edit" method="post" class="form-horizontal">	
			<div class="row">
					<div class="col-sm-offset-3 col-sm-9">
						<label for="name" class="col-sm-3 control-label">Име на етикет:</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="name" id="name" value="<c:out value="${label.text}"/>" maxlength="20" required onkeyup="labelTextSetup(this.value, document.getElementsByClassName('label-text')[0], 'new-label-color')">
						</div>
					</div>
				</div>
				<div class="row" id="new-label-color">
					<div class="col-sm-offset-3 col-sm-9">
						<br>
						<label for="labelColor" class="col-sm-3 control-label">Избор на цвят:</label>
						<div class="col-sm-offset-3">
							<c:forEach var="labelColor" items="${labelColors}">
								<input type="radio" name="labelColor" id="<c:out value="${labelColor.id}"/>" value="<c:out value="${labelColor.value}"/>" required
								<c:if test="${labelColor.value == label.color.value}">
									checked
								</c:if>
								>
									<button type="button" class="btn btn-primary btn-xs" style="border-radius: 8px; background-color: <c:out value="${labelColor.value}"/>; border-color: <c:out value="${labelColor.value}"/>" onclick="selectLabel('<c:out value="${labelColor.id}"/>')">
										<span class="label-text"><c:out value="${label.text}"/></span>
									</button>
								<br>
							</c:forEach>
						</div>
					</div>
					<div class="col-sm-offset-5 col-sm-2">
						<br>
						<input type="hidden" name="id" value="${label.id}">
						<button type="submit" class="btn btn-success btn-block">Запази</button>
					</div>
				</div>
		</form>
		<c:if test="${message == 'InvalidDataEntered'}">
			<div class="alert alert-warning" role="alert">
				Бяха въведени невалидни данни.
			</div>
		</c:if>
		<br>
		<form action="/app/admin/labels/delete" method="post" class="form-horizontal" onsubmit="return confirm('Наистина ли искате да изтриете този запис от системата?');">
			<div class="form-group">
		    	<div class="col-sm-offset-5 col-sm-2">
		    		<input type="hidden" name="id" value="${label.id}">
		    		<button type="submit" class="btn btn-danger btn-block">Премахни</button>
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