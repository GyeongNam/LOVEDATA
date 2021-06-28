<%--
  Created by IntelliJ IDEA.
  User: ARA
  Date: 2021-04-05
  Time: 오전 2:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>All</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<body>
    <form>
        <h1>All test....</h1>
        <%
            String serverName = request.getServerName();
            String serverLocalName = request.getLocalName();
            String serverRemoteAddr = request.getRemoteAddr();
            String serverRemoteHost = request.getRemoteHost();

            out.println("<h3>" + serverName + "</h3>");
            out.println("<h3>" + serverLocalName + "</h3>");
            out.println("<h3>" + serverRemoteAddr + "</h3>");
            out.println("<h3>" + serverRemoteHost + "</h3>");
        %>
    </form>
</body>
</html>
