<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <style>
        table, th, td {
            padding: 10px;
            border: 1px solid #000000;
            border-collapse: collapse;
        }
    </style>
    <script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        function ajax() {
            $.ajax({
                url: "/sample/response_body_value",
                async: true,
                method: "POST",
                success: function (data) {
                    alert(data)
                },
                error: function () {
                    alert("에러")
                }
            });
        }
    </script>
</head>
<body>
<%--<form method="GET">--%>
<%--    <table>--%>
<%--        <tbody>--%>
<%--        <tr>--%>
<%--            <td>email :</td>--%>
<%--            <td><input name="user_email" type="text" value="Harry"/></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>pw :</td>--%>
<%--            <td><input name="user_pw" type="text" value="Potter"/></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td colspan="2">--%>
<%--                <sec:csrfInput/>--%>
<%--                <input type="submit" value="Save Changes">--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--        </tbody>--%>
<%--    </table>--%>
<%--    <table>--%>
<%--        <thead>--%>
<%--            <tr>--%>
<%--                <th>입력된 이메일</th>--%>
<%--                <th>입력된 비밀번호</th>--%>
<%--            </tr>--%>
<%--        </thead>--%>
<%--        <tbody>--%>
<%--            <tr>--%>
<%--                <td>${map.user_email}</td>--%>
<%--                <td>${map.user_pw}</td>--%>
<%--            </tr>--%>
<%--        </tbody>--%>
<%--    </table>--%>
<%--</form>--%>
<button type="button" onclick="ajax()">ajax 테스트 버튼</button>
<%--@Todo Ajax 기능 테스트 마저 하기--%>

</body>
</html>
