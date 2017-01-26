<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>За автора</title>
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
	<div id="textbox">
		<div class="row">
			<c:out value="${aboutTheAuthorText}" escapeXml="false"/>
		</div>
		<div class="row">
			<div class="col-sm-offset-5 col-sm-2">
			<br>
			<button class="btn btn-info btn-block" onclick="history.back();" type="button">Обратно</button>
			</div>
		</div>
	</div>
	<div id="footer">
		<hr>
		<a href="/app/admin">Вход за администратор</a>
		<br>
	    <hr>
	    Copyright © 2017 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
	</div>
</div>
</body>
</html>