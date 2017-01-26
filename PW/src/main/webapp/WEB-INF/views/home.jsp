<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>WEBLOG</title>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/select-count-value.js" />"></script>
</head>
<body onload="selectCountValue(<c:out value="${count}"/>)">
<div id="wrapper">
    <div id="header">
		<a id="header-text" href="/app"><span class="text-underlined">WEB</span>LOG</a>
    </div>
	<div id="textbox">
		<div class="row">
			<div class="col-sm-10">
				<div class="intro-resource">
					<c:if test="${not empty introText}">
						<div class="intro-resource">
							<c:out value="${introText}" escapeXml="false"/>
						</div>
					</c:if>
				</div>
				<c:forEach var="post" items="${posts}">
					<div class="post-resource">
						<h3><c:out value="${post.title}"/></h3>
						<hr>
						<c:out value="${post.text}" escapeXml="false"/>
						<hr>
						<div class="post-dates">
							Публикувано: <fmt:formatDate pattern="dd.MM.yyyy HH:mm ч." value="${post.datePublished}" />
							<c:if test="${not empty post.dateEdited}">
								&nbsp;&nbsp;&nbsp;
								Редактирано: <fmt:formatDate pattern="dd.MM.yyyy HH:mm ч." value="${post.dateEdited}" />
							</c:if>
						</div>
						<hr>
						<c:forEach var="label" items="${post.labels}">
							<div class="label-after-post">
								<form action="/app/posts/page/1" method="get">
									<button type="submit" class="btn btn-primary btn-xs" style="border-radius: 8px; background-color: <c:out value="${label.color.value}"/>; border-color: <c:out value="${label.color.value}"/>" >
										<span class="label-text"><c:out value="${label.text}"/></span>
									</button>
									<input type="hidden" name="id" value="${label.id}">
								</form>
							</div>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
			<div class="col-sm-2">
				<div class="count-selector posts-count-selector">
					<form action="/app/posts/page/1">
						<div class="row">
							<label for="count" class="control-label">Брой резултати:</label>
						</div>
						<div class="row">
							<select name="count" id="count" class="form-control">
								<option value="5" id="5">5</option>
								<option value="10" id="10">10</option>
								<option value="20" id="20">20</option>
								<option value="50" id="50">50</option>
								<option value="100" id="100">100</option>
							</select>
						</div>
						<div class="row">
							<c:if test="${id != null}">
								<input type="hidden" name="id" value="${id}">
							</c:if>
							<br>
							<button type="submit" class="btn btn-info btn-block">Избери</button>
						</div>
					</form>
				</div>
				<div class="all-labels">	
					<c:forEach var="label" items="${labels}">
						<form action="/app/posts/page/1" method="get">
							<div class="single-label-in-group">
								<button type="submit" class="btn btn-primary btn-xs" style="border-radius: 8px; background-color: <c:out value="${label.color.value}"/>; border-color: <c:out value="${label.color.value}"/>" >
									<span class="label-text"><c:out value="${label.text}"/></span> <span class="badge label-badge"><c:out value="${fn:length(label.posts)}"/></span>
								</button>
								<input type="hidden" name="id" value="${label.id}">
							</div>
						</form>
					</c:forEach>
				</div>
			</div>
		</div>
		<div id="pager">
			<div class="row">
				<div class="col-sm-3">
					<c:if test="${previousPageNumber != 0}">
						<a class="btn btn-info btn-lg btn-block" href="/app/posts/page/<c:out value="${previousPageNumber}"/>?count=<c:out value="${count}"/><c:if test="${id != null}">&id=<c:out value="${id}"/></c:if>"><span class="glyphicon glyphicon-chevron-left"></span> Назад</a>
					</c:if>
				</div>
				<div class="col-sm-offset-6 col-sm-3">
					<c:if test="${nextPageNumber != 0}">
						<a class="btn btn-info btn-lg btn-block" href="/app/posts/page/<c:out value="${nextPageNumber}"/>?count=<c:out value="${count}"/><c:if test="${id != null}">&id=<c:out value="${id}"/></c:if>">Напред <span class="glyphicon glyphicon-chevron-right"></span></a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<hr>
		<a href="/app/aboutTheAuthor">За автора</a>
		<br>
		<br>
		<a href="/app/admin">Вход за администратор</a>
		<br>
	    <hr>
	    Copyright © 2017 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
	</div>
</div>
</body>
</html>