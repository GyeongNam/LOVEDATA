<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Account Delete Temp</title>
</head>
<body>
<form method="post" action="/user/deleteAccount/process">
<%--    <button onclick="user/deleteAccount/process">회원탈퇴</button>--%>
    <button type="submit">
        회원탈퇴
    </button>
    <sec:csrfInput/>
</form>
</body>
</html>
