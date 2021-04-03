<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>

<!DOCTYPE html>
<html>
<head>
﻿<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<meta charset="UTF-8">
<!-- CSS -->
<link href="/css/find-info.css" rel="stylesheet">
<!-- JS -->
<title>회원정보 찾기 | LOVE DATA</title>
</head>
<body>
<%@ include file="../layout/header.jsp" %>
<div class="find-content">
	<div class="find-info">
		<div class="id-find"><input type="button" value="id찾기" onclick="find_id_popup()"></div>
		<div class="pw-find"><Button>비밀번호 찾기</Button></div>
	</div>
</div>
<%@ include file="../layout/footer.jsp" %>
</body>
</html>
<link href="/js/find-info.js" rel="stylesheet">