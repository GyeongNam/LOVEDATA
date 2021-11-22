<%@ page import="com.project.love_data.dto.ReportDTO" %>
<%@ page import="java.util.*" %>
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

		td {
			vertical-align: middle;
        }

        body {
            font-family: 'Jua', sans-serif;
        }
	</style>
	<title>Report Cluster Search Popup</title>
</head>
<body>
<div class="container-xxl pt-5 pb-5">
	<div class="row justify-content-between align-items-center mb-5">
		<div class="col flex-shrink-0 mb-5 mb-md-0">
			<h1 class="display-4 mb-0 ms-3">신고 내역</h1>
		</div>
	</div>
	<div class="row d-flex">
		<div class="flex-row-reverse d-flex">
			<button type="button" onclick="processItems()" class="btn btn-primary me-5">처리</button>
			<div class="col-3 pt-1">
				<label>
					처리 방식
					<input type="text" id="resultText" name="result" list="resultList">
				</label>
				<datalist id="resultList">
					<option value="False Positive">
					<option value="True Positive">
				</datalist>
			</div>
		</div>
		<div class="row my-3">
			<div class="d-flex justify-content-center align-items-md-center">
				<table class="table text-center" id="reportClusterTable">
					<thead>
						<th scope="col">No</th>
						<th scope="col">ID</th>
						<th scope="col">
							<div class="dropdown">
								<button id="repTypeDropDownBtn" class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									신고 타입
								</button>
								<div class="dropdown-menu" id="repTypeDropDownMenu" aria-labelledby="completeTypeDropDownBtn"/>
							</div>
						</th>
						<th scope="col">신고 내용</th>
						<th scope="col">신고일</th>
						<th scope="col">
							<div class="dropdown">
							<button id="completeDropDownBtn" class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								상태
							</button>
							<div class="dropdown-menu" id="completeDropDownMenu" aria-labelledby="completeTypeDropDownBtn">
								<a class="dropdown-item" onclick="changeCompleteType('ALL')">전체 </a>
								<a class="dropdown-item" onclick="changeCompleteType('PROGRESS')">진행 중 </a>
								<a class="dropdown-item" onclick="changeCompleteType('COMPLETE')">처리 완료 </a>
							</div>
						</div></th>
						<th scope="col">
							처리
							<input class="form-check-input" type="checkbox" onclick="checkAllItems(this)">
						</th>
					</thead>
					<tbody id="table">
					<c:choose>
						<c:when test="${!empty pageResultDTO.dtoList}">
							<c:set var="reportList" value="${pageResultDTO.dtoList}"/>
							<c:forEach var="i" begin="0" end="${pageResultDTO.dtoList.size() -1}">
								<tr id="row_${i}">
									<td>${i + 1}</td>
									<td>${pageResultDTO.dtoList.get(i).repNo}</td>
									<td>${resultDTORepTypeKRList.get(i)}</td>
									<td class="text-truncate">${pageResultDTO.dtoList.get(i).repContent}</td>
									<td>${pageResultDTO.dtoList.get(i).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
									<td><%
											List<ReportDTO> reportDTOList = (List<ReportDTO>) pageContext.getAttribute("reportList");
                                            int i = (int) pageContext.getAttribute("i");

											if (reportDTOList.get(i).isComplete()) {
												out.println("처리 완료");
											} else {
												out.println("진행 중");
											}
										%></td>
									<td>
										<input class="form-check-input mt-0" type="checkbox" value="${pageResultDTO.dtoList.get(i).repNo}">
									</td>
								</tr>
							</c:forEach>
						</c:when>
					</c:choose>
					</tbody>
				</table>
			</div>
		</div>
		<div class="col d-flex">
			<button class="btn btn-primary">삭제</button>
			<button class="btn btn-primary">블라인드 취소</button>
		</div>
	</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
<script defer>
    var token = document.querySelector("meta[name='_csrf']").getAttribute('content');
    var header = document.querySelector("meta[name='_csrf_header']").getAttribute('content');
	let repType = [];
    let repTypeDropDown = document.getElementById("repTypeDropDownMenu");

    <c:if test="${!empty repTypeList}">
		<c:forEach var="i" begin="0" end="${repTypeList.size() - 1}">
    		repType.push('${repTypeList.get(i)}');
		</c:forEach>
	</c:if>

    $("#repTypeDropDownMenu").append('<a class="dropdown-item" onclick="changeRepType(\'ALL\')">전체 </a>');
    for (let i = 0; i < repType.length; i++) {
		$("#repTypeDropDownMenu").append('<a class="dropdown-item" onclick="changeRepType(\'' + repType[i] +'\')">' + repType[i] + ' </a>');
    }
</script>
<script defer>
	function changeRepType(type) {
        let url = new URL(window.location.href);

        url.searchParams.set('repType', type);

        location.href = url;
	}

    function checkAllItems(check) {
        let checkBoxes = document.querySelectorAll('input[type="checkbox"]');

        for (let i = 0; i < checkBoxes.length; i++) {
            if (check.checked) {
                checkBoxes[i].checked = true;
			} else {
                checkBoxes[i].checked = false;
			}
        }
	}

    function processItems() {
        let checkBoxes = document.querySelectorAll('input[type="checkbox"]:checked');
		let values = [];
        let text = document.getElementById("resultText").value;

        for (let i = 0; i < checkBoxes.length; i++) {
			values.push(Number.parseInt(checkBoxes[i].value));
        }

        $.ajax({
            type: "POST",
            url: "/admin/report_center/report_process",
            data: {
                rcNoList : values,
				result : text
            },
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                // do something ...
                // console.log("선택된 이미지 삭제 성공");
                // alert("선택된 이미지 삭제 성공");
                location.reload();
            }, error: function (e) {
                console.log("신고 처리 과정에 오류 발생");
                console.log(e);
            }
        });
	}
</script>
<script defer>
    function changeCompleteType(completeType) {
        let url = new URL(window.location.href);

        switch (completeType) {
            case 'ALL' :
                url.searchParams.set('completeType', 'ALL');
                break;
            case 'PROGRESS' :
                url.searchParams.set('completeType', 'PROGRESS');
                break;
            case 'COMPLETE' :
            default :
                url.searchParams.set('completeType', 'COMPLETE');
        }

        location.href = url;
    }
</script>
</body>
</html>
