<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="defaultDateTimeFormatter" class="com.project.love_data.util.DefaultLocalDateTimeFormatter"></jsp:useBean>
<jsp:useBean id="simpleDateTimeFormatter" class="com.project.love_data.util.SimpleLocalDateTimeFormatter"></jsp:useBean>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="_csrf" content="${_csrf.token}">
	<meta name="_csrf_header" content="${_csrf.headerName}">
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
	</style>
	<title>Report Cluster Search Popup</title>
</head>
<body>
<div class="row d-flex">
	<div class="row my-3">
		<div class="d-flex justify-content-center align-items-md-center">
			<table class="table text-center" id="reportClusterTable">
				<thead>
					<th scope="col">No</th>
					<th scope="col">ID</th>
					<th scope="col">신고 분류</th>
					<th scope="col">신고 내용</th>
					<th scope="col">신고일</th>
					<th scope="col">처리</th>
				</thead>
				<tbody id="table">
				<c:choose>
					<c:when test="${!empty pageResultDTO.dtoList}">
						<c:forEach var="i" begin="0" end="${pageResultDTO.dtoList.size() -1}">
							<tr id="row_${i}">
								<td>${i + 1}</td>
								<td>${pageResultDTO.dtoList.get(i).repNo}</td>
								<td>${pageResultDTO.dtoList.get(i).repType}</td>
								<td class="text-truncate">${pageResultDTO.dtoList.get(i).repContent}</td>
								<td>${pageResultDTO.dtoList.get(i).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
								<td><button class="btn-primary">삭제</button> </td>
							</tr>
						</c:forEach>
					</c:when>
				</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
<script defer>

</script>
</body>
</html>
