<!DOCTYPE html>
<html data-ng-app="enterprise">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<title>Spring Social | AngularJS</title>
<spring:url value="/js/jquery-1.8.0.min.js" var="jQueryUrl"
	htmlEscape="true" />
<spring:url value="/js/angular.min.js" var="angularUrl"
	htmlEscape="true" />
<spring:url value="/js/app.js" var="appJsUrl" htmlEscape="true" />
<script src="${jQueryUrl}"></script>
<script src="${angularUrl}"></script>
<script src="${appJsUrl}"></script>
</head>
<body>
	<h2>Spring Social</h2>
	<div data-ng-controller="SocialController">
		<button data-ng-click='shareOnFacebook()'>Post on Facebook</button>
		<button data-ng-click='shareOnTwitter()'>Tweet on Twitter</button>
	</div>
</body>
</html>