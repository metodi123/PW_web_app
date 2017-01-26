<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Файлове</title>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/copy-to-clipboard.js" />"></script>
    <script src="<c:url value="/resources/js/select-count-value.js" />"></script>
</head>
<body onload="selectCountValue(<c:out value="${count}"/>)">
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
						<li class="dropdown active">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Ресурси<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/admin/images" />">Изображения</a></li>
								<li class="active"><a href="<c:url value="/admin/files" />">Файлове</a></li>
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
		<div class="row">
			<div class="col-sm-7">
				<div class="resource add-resource">
					<form action="${resourceUrl}/uploadFile" enctype="multipart/form-data" method="post">
						<div class="col-sm-2">
							<label for="file" class="control-label">Нов файл:</label>
						</div>
						<div class="col-sm-offset-1 col-sm-6">
							<input type="file" class="form-control" name="file" id="file" size="50"/>
						</div>
						<div class="col-sm-3">
							<button type="submit" class="btn btn-info btn-block">Запази</button>
						</div>
					</form>
				</div>
			</div>
			<div class="col-sm-5">
				<div class="resource count-selector">
					<form action="/app/admin/files/page/1">
						<div class="row">
							<div class="col-sm-4">
								<label for="count" class="control-label">Брой резултати:</label>
							</div>
							<div class="col-sm-4">
								<select name="count" id="count" class="form-control">
									<option value="5" id="5">5</option>
									<option value="10" id="10">10</option>
									<option value="20" id="20">20</option>
									<option value="50" id="50">50</option>
									<option value="100" id="100">100</option>
								</select>
							</div>
							<div class="col-sm-4">
								<button type="submit" class="btn btn-info btn-block">Избери</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:forEach var="file" items="${files}">
			<div class="resource">
				<div class="row">
					<div class="col-sm-9">
						<div class="row">
							<div class="col-sm-12">
								<p>Файл: <strong><a href="<c:out value="${resourceUrl}/${file.name}"/>"><c:out value="${file.name}"/></a></strong></p>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-10">
								<input type="text" class="form-control" id="<c:out value="text-for-button-${file.id}"/>" value="<c:out value="${resourceUrl}/${file.name}"/>" readonly>
							</div>
							<div class="col-sm-2">
								<button class="copy-text-button btn btn-default btn-block" id="<c:out value="${file.id}"/>">Копирай</button>
							</div>
						</div>
						<br><br>
						<form action="${resourceUrl}/deleteFile" method="post" class="form-horizontal" onsubmit="return confirm('Наистина ли искате да изтриете този запис от системата?');">	
							<div class="form-group">
							    <div class="col-sm-2">
							    	<input type="hidden" name="name" value="${file.name}">
							    	<button type="submit" class="btn btn-danger btn-block">Премахни</button>
							    </div>
							  </div>
						</form>
					</div>
				</div>
			</div>
		</c:forEach>
		<div id="pager">
			<div class="row">
				<div class="col-sm-3">
					<c:if test="${previousPageNumber != 0}">
						<a  class="btn btn-info btn-lg btn-block" href="/app/admin/files/page/<c:out value="${previousPageNumber}"/>?count=<c:out value="${count}"/>"><span class="glyphicon glyphicon-chevron-left"></span> Назад</a>
					</c:if>
				</div>
				<div class="col-sm-offset-6 col-sm-3">
					<c:if test="${nextPageNumber != 0}">
						<a  class="btn btn-info btn-lg btn-block" href="/app/admin/files/page/<c:out value="${nextPageNumber}"/>?count=<c:out value="${count}"/>">Напред <span class="glyphicon glyphicon-chevron-right"></span></a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<hr>
		Copyright © 2017 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
	</div>
</div>
</body>
</html>