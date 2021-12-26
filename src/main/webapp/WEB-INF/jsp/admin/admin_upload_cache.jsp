<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<jsp:useBean id="defaultDateTimeFormatter" class="com.project.love_data.util.DefaultLocalDateTimeFormatter"></jsp:useBean>
<jsp:useBean id="simpleDateTimeFormatter" class="com.project.love_data.util.SimpleLocalDateTimeFormatter"></jsp:useBean>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%--	<meta name="viewport" content="width=device-width, initial-scale=1">--%>
	<meta name="_csrf" content="${_csrf.token}">
	<meta name="_csrf_header" content="${_csrf.headerName}">
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<link rel="stylesheet" href="/css/service/loc.css">
	<style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
	</style>
	<title>Admin Dashboard</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body id="body" class="bg-light">
	<div class="container-fluid d-flex" style="padding-top: 100px">
		<div class="col-2" id="sidebar">
			<ul class="nav nav-pills flex-column col-2 position-fixed" style="top: 40%">
				<div class="accordion text-center" id="loc">
					<hr>
					<div class="card">
						<div class="card-header" id="headingLoc">
							<h2 class="mb-0">
								<button class="btn btn-link btn-block loc_highlight-selected-nav-menu" type="button" aria-expanded="true" aria-controls="collapseOne">
									어드민
								</button>
							</h2>
						</div>
						<div id="loc_collapse" class="show">
							<div class="card-body center-pill">
								<p><a href="/admin/dash" class="highlight-not-selected-text-menu">대시보드</a></p>
								<p><a href="/admin/user/1" class="highlight-not-selected-text-menu">유저 관리</a></p>
								<p><a href="/admin/buisnessman" class="highlight-not-selected-text-menu">사업자 관리</a></p>
								<p><a href="/admin/event" class="highlight-not-selected-text-menu">이벤트 관리</a></p>
								<p><a href="/admin/SendMessage" class="highlight-not-selected-text-menu">메시지 발송</a></p>
								<p><a type="button" class="highlight-not-selected-text-menu" data-toggle="collapse" data-target="#service_collapse" aria-expanded="false">공지사항과 문의사항</a></p>
								<div id="service_collapse" class="panel-collapse collapse">
									<p>
										<a href="/admin/notice_add" class="highlight-not-selected-text-menu">- 공지사항 작성</a>
									</p>
									<p>
										<a href="/admin/qna/1" class="highlight-not-selected-text-menu">- 문의사항 답변</a>
									</p>
								</div>
								<p><a href="/admin/upload_cache" class="highlight-selected-text-menu">upload 파일 캐시 삭제</a></p>
<%--								<p><a href="/admin/loc_recommend" class="highlight-not-selected-text-menu">- 추천 장소(어드민)</a></p>--%>
<%--								<p><a href="/admin/cor_recommend" class="highlight-not-selected-text-menu">- 추천 코스(어드민)</a></p>--%>
								<p class="mb-0"><a href="/admin/report_center" class="highlight-not-selected-text-menu">신고 센터</a></p>
							</div>
						</div>
					</div>
				</div>
			</ul>
		</div>

<%--	https://startbootstrap.com/previews/material-admin-pro	--%>
		<div class="container-xxl ps-5 pt-5 pb-5 ms-3" style="padding-right : 0px">
			<div class="row justify-content-between align-items-center mb-5">
				<div class="col flex-shrink-0 mb-5 mb-md-0">
					<h1 class="display-4 mb-0">삭제된 이미지 관리</h1>
				</div>
			</div>
			<div class="row d-flex">
				<div class="row flex-row-reverse">
					<button class="btn btn-primary col-1" onclick="onClickDeleteImgBtn()">삭제</button>
				</div>
				<div class="row my-3">
					<div class="d-flex justify-content-center align-items-md-center">
						<table class="table text-center" id="recentLocCorTable">
							<thead>
							<th scope="col">No</th>
							<th scope="col">타입</th>
							<th scope="col">업로드 유저</th>
							<th scope="col">파일명</th>
							<th scope="col">파일 크기</th>
							<th scope="col">등록일</th>
							<th scope="col">삭제일</th>
							<th scope="col">선택<input class="form-check-input ms-2 mt-0" type="checkbox" onclick="checkAllItems(this)" value="total_check"></th>
							</thead>
							<tbody id="table">
								<c:choose>
									<c:when test="${!empty fileNameList}">
										<c:forEach var="i" begin="0" end="${fileNameList.size() - 1}">
											<tr id="row_${i}">
												<td>${i + 1}</td>
												<td>${fileTypeList.get(i)}</td>
												<c:choose>
													<c:when test="${fileUploadUserList.get(i) ne null}">
														<td>${fileUploadUserList.get(i)}</td>
													</c:when>
													<c:otherwise>
														<td>NULL</td>
													</c:otherwise>
												</c:choose>
												<td onclick="location.href='/image/upload/${fileNameList.get(i)}'"
													id="row_${i}_filename"
													onmouseover="openPreviewFileImage('${fileNameList.get(i)}', ${i})"
													onmouseout="closePreviewFileImage()" style="cursor: zoom-in;">
														${fileNameList.get(i)}
												</td>
												<td>${fileSizeList.get(i)}</td>
												<c:choose>
													<c:when test="${lastModifiedTimeList.get(i) ne null}">
														<td>${lastModifiedTimeList.get(i).format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
													</c:when>
													<c:otherwise>
														<td>NULL</td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${fileDeletedTimeList.get(i) ne null}">
														<td>${fileDeletedTimeList.get(i).format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
													</c:when>
													<c:otherwise>
														<td>NULL</td>
													</c:otherwise>
												</c:choose>
												<td><input type="checkbox" name="fileCheckBox${i}" value="${fileNameList.get(i)}"
														   onclick="addCheck(this)" style="margin: 4px;"/></td>
											</tr>
										</c:forEach>
									</c:when>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
<script defer>
	function tableRowColor(row, type) {
        switch (type) {
            case '장소' :
                row.style.background = '#ffdef2';
				break;
			case '코스' :
                row.style.background = '#e2eeff';
				break;
			case '리뷰' :
			default :
                row.style.background = '#C3FFC3';
		}
	}
</script>
<script defer>
	let urlList = [];
    let aryLength = ${fileNameList.size()};
    let fileOriginURLList = [];

    <c:choose>
		<c:when test="${!empty fileNameList}">
			<c:forEach var="i" begin="0" end="${fileOriginURLList.size()-1}">
			fileOriginURLList.push('${fileOriginURLList.get(i)}');
			</c:forEach>

			for (let i = 0; i < aryLength; i++) {
				let row = document.getElementById('row_' + i);
				if (fileOriginURLList[i] != null) {
					// row.onclick = 'location.href=' + urlList[i];
                    // 타입 (2)로 테이블 색상 결정
                    tableRowColor(row, row.children.item(2).innerText);

					// for (let j = 0; j < row.childElementCount; j++) {
					// 	// 파일명 (4), 선택(8) 제외
					// 	let col = row.children.item(j);
					// 	if (j == 4 || j == 8) {
					// 		continue;
					// 	}
					//
					// 	col.addEventListener("click", function() {
					// 		location.href = fileOriginURLList[i];
                    //         col.style.cursor = 'pointer';
					// 	})
					// }
				}
			}
		</c:when>
	</c:choose>
</script>
<script defer>
	function openPreviewFileImage(fileName, index) {
        let filePath = "/image/upload/" + fileName;
		let img = document.createElement("img");
        img.id = 'previewImg';
        img.src = filePath;
        img.style.left = event.pageX;
        img.style.top = event.pageY;
        img.style.position = 'absolute';
        img.style.zIndex = 10;
        img.style.maxWidth = '500px';
        img.style.maxHeight = '300px';
        // img.style.border = '1px solid black';
        // document.getElementById('row_' + index).appendChild(img);
		document.getElementById('row_' + index).appendChild(img);
	}

    function closePreviewFileImage() {
        let previewImg = document.getElementById('previewImg');
        previewImg.remove();
	}
</script>
<script defer>
	let checkBoxList = [];
    var token = document.querySelector("meta[name='_csrf']").getAttribute('content');
    var header = document.querySelector("meta[name='_csrf_header']").getAttribute('content');

	function onClickDeleteImgBtn() {
        // console.log(document.querySelectorAll('input[type="checkbox"]:checked').length);
        // console.log(document.querySelectorAll('input[type="checkbox"]:checked'));
        // document.querySelectorAll('input[type="checkbox"]:checked').forEach(value => console.log(value.value));

		if (!confirm('선택한 이미지를 삭제하시겠습니까?')){
            return;
		}

		if (checkBoxList.length === 0) {
            alert('선택된 이미지가 없습니다!');
            return;
		}

        console.log(checkBoxList);

        $.ajax({
            type: "POST",
            url: "/admin/del_upload_cache",
            data: {
                pathName : checkBoxList,
				pathType : 'UPLOAD'
			},
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                // do something ...
                // console.log("선택된 이미지 삭제 성공");
                // alert("선택된 이미지 삭제 성공");
				let msg = '';
				$(response).each(function(index, item) {
                    console.log(index + '번째 파일 (' + checkBoxList[index] + ') 삭제 결과 : ' + item);
                    msg += (index+1) + '번째 파일 (' + checkBoxList[index] + ') 삭제 \n';
                    // if (item) {
                    //     for (let i = 0; i < aryLength; i++) {
					// 		if ($('row_' + i + '_filename').innerText === checkBoxList[index]) {
                    //             console.log(true);
                    //             let element = $('row_' + i);
                    //             element.remove();
					// 		}
                    //     }
					// }
				})
				alert(msg);
				location.reload();
            }, error: function (e) {
                console.log("선택된 이미지 삭제 실패");
                console.log(e);
            }
        });
	}

    // function onChangeCheckBox(checkbox) {
    //     checkBoxList.push(checkbox.value);
    //     console.log(checkBoxList);
	// }

    // function checkAllItems(check) {
    //     let checkBoxes = document.querySelectorAll('input[type="checkbox"]');
	//
    //     for (let i = 0; i < checkBoxes.length; i++) {
    //         if (check.checked) {
    //             checkBoxes[i].checked = true;
    //             if (i != 0) checkBoxList.push(checkBoxes[i].value);
    //         } else {
    //             checkBoxes[i].checked = false;
    //             if (i != 0) checkBoxList.splice(checkBoxList.indexOf(checkBoxes[i].value), 1);
    //         }
    //     }
    // }

    function checkAllItems(check) {
        let checkBoxes = document.querySelectorAll('input[type="checkbox"]');
        checkBoxList = [];

        for (let i = 0; i < checkBoxes.length; i++) {
            if (check.checked) {
                if (checkBoxes[i].value !== 'total_check') {
                    checkBoxList.push(checkBoxes[i].value);
                }
            }

            checkBoxes[i].checked = check.checked;
        }
    }

    function addCheck(check) {
        if (checkBoxList.indexOf(check.value) === -1) {
            checkBoxList.push(check.value);
            return;
        }

        checkBoxList.splice(checkBoxList.indexOf(check.value), 1);
    }
</script>
</html>
