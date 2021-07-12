<%--
  Created by IntelliJ IDEA.
  User: ARA
  Date: 2021-04-08
  Time: 오후 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Admin</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<body>
    <h1>Admin test....</h1>
    <div>
        <p>사용자 번호 : <sec:authentication property="principal.user_no"/></p>
        <p>사용자 번호 : <sec:authentication property="principal.authorities"/></p>
        <p>사용자 이메일 : <sec:authentication property="principal.user_email"/></p>
        <p>사용자 이름 : <sec:authentication property="principal.user_name"/></p>
        <p>사용자 닉네임 : <sec:authentication property="principal.user_nic"/></p>
        <p>사용자 비밀번호(암호화) : <sec:authentication property="principal.user_pw"/></p>
        <p>사용자 생일 : <sec:authentication property="principal.user_birth"/></p>
        <p>사용자 성별 : <sec:authentication property="principal.user_sex"/></p>
        <p>사용자 연락처 : <sec:authentication property="principal.user_phone"/></p>
        <p>사용자 계정 생성시간 : <sec:authentication property="principal.user_regDate"/></p>
        <p>사용자 활성화 : <sec:authentication property="principal.user_Activation"/></p>
        <p>사용자 이메일 수신 여부 : <sec:authentication property="principal.user_emil_re"/></p>
        <p>소셜 로그인 사용 여부 : <sec:authentication property="principal.user_social"/></p>
    </div>
    <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
</body>
</html>
