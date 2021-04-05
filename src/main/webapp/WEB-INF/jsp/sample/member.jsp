<%--
  Created by IntelliJ IDEA.
  User: ARA
  Date: 2021-04-05
  Time: 오전 2:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Member</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<body>
<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
    <h1>Member test....</h1>
</body>
</html>
