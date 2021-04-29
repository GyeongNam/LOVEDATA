<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
<link href="\main\resources\static\css" rel="stylesheet">
	<title>Home</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>
	<div id = "mySidenav" class="sidenav">
	</div>
	<!--  부트스트랩 js 사용 -->
	<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
</body>
<%--<%@ include file="layout/footer.jsp" %>--%>
</html>
