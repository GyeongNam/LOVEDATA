<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Alert</title>
</head>
<body>
<script type="text/javascript">
    var message = "${alertMsg}";
    var url = "${redirectURL}";
    alert(message);
    document.location.href = url;
</script>
</body>
</html>
