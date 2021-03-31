<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
<link href="\main\resources\fream\css" rel="stylesheet">
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!
</h1>

<P>  The time on the server is ${serverTime}. </P>
<a href="infoFind.do">아이디/비밀번호 찾기</a>
<a href="signup.do">회원가입</a>
</body>
</html>
