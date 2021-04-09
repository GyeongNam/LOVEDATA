<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Member</title>
</head>
<body>
    <h1>Member test....</h1>
    <div>
        <sec:authentication property=""><h3></h3></sec:authentication>
    </div>
    <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
</body>
</html>
