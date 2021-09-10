<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sample Test Index</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<body>
<h1>Starter Page</h1>
<p><a href="/all">all</a></p>
<p><a href="/member">member</a></p>
<p><a href="/admin">admin</a></p>
<p><a href="/sample/model_addattributes">model_addattributes</a></p>
<p><a href="/sample/response_body">response_body</a></p>
<form method="post" action="/sample/response_body_value">
    <button type="submit">response_body_value</button>
    <sec:csrfInput/>
</form>
<p><a href="/sample/tmapTest">t맵</a></p>
<p><a href="/sample/tmapPathFindingTest">t맵 경로찾기 샘플</a></p>
</body>
</html>
