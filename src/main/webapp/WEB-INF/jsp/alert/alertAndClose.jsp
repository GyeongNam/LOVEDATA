<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Alert</title>
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
    var message = "${alertMsg}";
    alert(message);
    document.close();
    window.close();
    // if ($.browser.msie) {
    //     window.opener='Self';
    //     window.open('','_parent','');
    //     window.close();
    // } else {
    //     window.close(); // 일반적인 현재 창 닫기
    //     window.open('about:blank','_self').self.close();  // IE에서 묻지 않고 창 닫기
    // }
</script>
</body>
</html>
