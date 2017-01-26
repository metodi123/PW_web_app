<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Етикети</title>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <link href="<c:url value="/resources/data_tables/datatables.min.css" />" rel="stylesheet" type="text/css" />
	<script src="<c:url value="/resources/data_tables/datatables.min.js" />"></script>
	<script src="<c:url value="/resources/js/load-data-table-appearance.js" />"></script>
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
		<div class="create-label">
			<form action="/app/admin/labels/create" method="post">
				<div class="row">
					<label for="name" class="col-sm-offset-2 col-sm-3 control-label text-right">Име на нов етикет:</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" name="name" id="name" maxlength="20" required onkeyup="labelTextSetup(this.value, document.getElementsByClassName('label-text')[0], 'new-label-color')">
					</div>
				</div>
				<div class="hide-element" id="new-label-color">
					<div class="row">
						<br>
						<label for="labelColor" class="col-sm-offset-2 col-sm-3 control-label text-right">Избор на цвят:</label>
						<div class="col-sm-5">
							<c:forEach var="labelColor" items="${labelColors}">
								<input type="radio" name="labelColor" id="<c:out value="${labelColor.id}"/>" value="<c:out value="${labelColor.value}"/>" required>
									<button type="button" class="btn btn-primary btn-xs" style="border-radius: 8px; background-color: <c:out value="${labelColor.value}"/>; border-color: <c:out value="${labelColor.value}"/>" onclick="selectLabel('<c:out value="${labelColor.id}"/>')">
										<span class="label-text"></span>
									</button>
								<br>
							</c:forEach>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-offset-5 col-sm-2">
							<br>
							<button type="submit" class="btn btn-info btn-block">Запази</button>
						</div>
					</div>
				</div>
			</form>
		</div>
		<br>
		<c:choose>
		    <c:when test="${labels[0] != null}">
		      	<table id="dataTable" style="background-color:white" class="table table-striped table-bordered table-hover table-condensed">
					<col width="90%">
			        <col width="10%">	
			        <caption>
				    	Етикети
			        </caption>
			        <thead>
			             <tr>
				             <th>Изглед</th>
				             <th>Редактирай</th>
			             </tr>
			        </thead>
			        <tbody>
			        	<c:forEach var="label" items="${labels}">
			        		<tr>
			        			<td>
									<button type="button" class="btn btn-primary btn-xs" style="border-radius: 8px; background-color: <c:out value="${label.color.value}"/>; border-color: <c:out value="${label.color.value}"/>">
										<span><c:out value="${label.text}"/></span>
									</button>
								</td>
								<td>
									<form action="/app/admin/labels/edit" method="get">
										<button type="submit" class="btn btn-primary">
											<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
										</button>
										<input type="hidden" name="id" value="${label.id}">
									</form>
								</td>
							</tr>
						</c:forEach>
			        </tbody>
				</table>     
		    </c:when>
		    <c:otherwise>
		    	<div class="alert alert-warning" role="alert">
		       		Няма намерени резултати.
		       	</div>
		    </c:otherwise>
		</c:choose>
		<c:if test="${message == 'InvalidDataEntered'}">
			<div class="alert alert-warning" role="alert">
				Бяха въведени невалидни данни.
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