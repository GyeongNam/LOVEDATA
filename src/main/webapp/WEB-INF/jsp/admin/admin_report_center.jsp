<%@ page import="com.project.love_data.model.service.ReportCluster" %>
<%@ page import="java.util.*" %>
<%@ page import="com.project.love_data.dto.ReportClusterDTO" %>
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

        td{
            vertical-align: middle;
        }

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
								<p><a href="/admin/upload_cache" class="highlight-not-selected-text-menu">upload 파일 캐시 삭제</a></p>
<%--								<p><a href="/admin/loc_recommend" class="highlight-not-selected-text-menu">- 추천 장소(어드민)</a></p>--%>
<%--								<p><a href="/admin/cor_recommend" class="highlight-not-selected-text-menu">- 추천 코스(어드민)</a></p>--%>
								<p class="mb-0"><a href="/admin/report_center" class="highlight-selected-text-menu">신고 센터</a></p>
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
					<h1 class="display-4 mb-0">신고처리</h1>
				</div>
			</div>
			<div class="row d-flex">
				<div class="row my-3">
					<div class="d-flex justify-content-center align-items-md-center">
						<table class="table text-center" id="reportClusterTable">
							<thead>
							<th scope="col">No</th>
							<th scope="col">ID</th>
							<th scope="col">Post ID</th>
							<th scope="col">유저</th>
							<th scope="col">타입</th>
							<th scope="col">
								<div class="dropdown">
								<button id="completeTypeDropDownBtn" class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									처리 중
								</button>
								<div class="dropdown-menu" aria-labelledby="completeTypeDropDownBtn">
									<a class="dropdown-item" onclick="changeCompleteType('ALL')">전체 </a>
									<a class="dropdown-item" onclick="changeCompleteType('PROGRESS')">처리 중 </a>
									<a class="dropdown-item" onclick="changeCompleteType('COMPLETE')">완료 </a>
								</div>
							</div>
							</th>
							<th scope="col">신고수</th>
							<th scope="col">관리</th>
							</thead>
							<tbody id="table">
							<c:set var="reportClusterList" value="${clusterPageResultDTO.dtoList}"/>
							<c:set var="pageNum" value="${param.page}"/>
							<c:choose>
								<c:when test="${!empty reportClusterList}">
									<c:forEach var="i" begin="0" end="${reportClusterList.size() - 1}">
										<tr id="row_${i}">
											<td>
												<c:choose>
													<c:when test="${pageNum ne null}">
														<%
															int pageNum = Integer.parseInt(request.getParameter("page"));
															int j = (int) pageContext.getAttribute("i");
															int result = 0;
															if (pageNum > 1) {
																result = (pageNum - 1) * 10 + j + 1;
															} else {
																result = j + 1;
															}
															out.println(String.valueOf(result));
														%>
													</c:when>
													<c:otherwise>
														${i + 1}
													</c:otherwise>
												</c:choose>
											</td>
											<td>${reportClusterList.get(i).rcNo}</td>
											<td><%
												List<ReportClusterDTO> reportClusterList = (List<ReportClusterDTO>) pageContext.getAttribute("reportClusterList");
												int i = (int) pageContext.getAttribute("i");

                                                if (reportClusterList.get(i).getPostNo() == null) {
                                                    out.println("삭제된 게시글");
												} else {
                                                    out.println(reportClusterList.get(i).getPostNo());
												}
											%></td>
											<td>${clusterUserNickList.get(i)}</td>
											<td><%
                                                switch (reportClusterList.get(i).getPostType()) {
													case "LOC" :
                                                        out.println("장소");
                                                        break;
													case "COR" :
														out.println("코스");
                                                        break;
													case "COM" :
														out.println("댓글");
                                                        break;
													case "REV" :
														out.println("리뷰");
                                                        break;
													case "PROFILE_PIC" :
														out.println("프로필 사진");
                                                        break;
													default :
														out.println("NULL");
                                                        break;
												}
											%></td>
											<td><%
												if (reportClusterList.get(i).isRcComplete()) {
													out.println("처리 완료");
												} else {
													out.println("진행 중");
												}
											%></td>
											<td>${reportClusterList.get(i).repCount}</td>
											<td><button class="btn btn-primary" onclick="openReportClusterDetailPopup(${reportClusterList.get(i).rcNo})">상세</button></td>
										</tr>
									</c:forEach>
								</c:when>
							</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<%--	PageNumber	--%>
			<c:set var="completeTypeParam" value="${param.completeType}"/>
			<div class="container d-flex" id="">
				<div class="col" id="page_number">
					<nav aria-label="Page navigation example">
						<ul class="pagination justify-content-center">
							<c:if test="${clusterPageResultDTO.next eq true}">
								<c:choose>
									<c:when test="${completeTypeParam ne null}">
										<li class="page-item">
											<a class="page-link" href="/admin/report_center?completeType=${completeTypeParam}&page=${clusterPageResultDTO.start - 1}"
											   aria-label="Previous">
												<span aria-hidden="true">&laquo;</span>
											</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="page-item">
											<a class="page-link" href="/admin/report_center?page=${clusterPageResultDTO.start - 1}"
											   aria-label="Previous">
												<span aria-hidden="true">&laquo;</span>
											</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:forEach var="j" begin="${clusterPageResultDTO.start}" end="${clusterPageResultDTO.end}">
								<c:choose>
									<c:when test="${clusterPageResultDTO.page eq j}">
										<c:if test="${completeTypeParam ne null}">
											<li class="page-item active">
												<a class="page-link"
												   href="/admin/report_center?completeType=${completeTypeParam}&page=${clusterPageResultDTO.pageList.get(j-1)}">
														${clusterPageResultDTO.pageList.get(j-1)}
												</a>
											</li>
										</c:if>
										<c:if test="${completeTypeParam eq null}">
											<li class="page-item active">
												<a class="page-link"
												   href="/admin/report_center?page=${clusterPageResultDTO.pageList.get(j-1)}">
														${clusterPageResultDTO.pageList.get(j-1)}
												</a>
											</li>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${completeTypeParam ne null}">
											<li class="page-item">
												<a class="page-link"
												   href="/admin/report_center?completeType=${completeTypeParam}&page=${clusterPageResultDTO.pageList.get(j-1)}">
														${clusterPageResultDTO.pageList.get(j-1)}
												</a>
											</li>
										</c:if>
										<c:if test="${completeTypeParam eq null}">
											<li class="page-item">
												<a class="page-link"
												   href="/admin/report_center?page=${clusterPageResultDTO.pageList.get(j-1)}">
														${clusterPageResultDTO.pageList.get(j-1)}
												</a>
											</li>
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${clusterPageResultDTO.next eq true}">
								<c:choose>
									<c:when test="${completeTypeParam ne null}">
										<li class="page-item">
											<a class="page-link" href="/admin/report_center?completeType=${completeTypeParam}&page=${clusterPageResultDTO.end + 1}"
											   aria-label="Previous">
												<span aria-hidden="true">&raquo;</span>
											</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="page-item">
											<a class="page-link" href="/admin/report_center?page=${clusterPageResultDTO.end + 1}"
											   aria-label="Previous">
												<span aria-hidden="true">&raquo;</span>
											</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:if>
						</ul>
					</nav>
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
            case '진행 중' :
                row.style.background = '#ffdef2';
				break;
			case '처리 완료' :
			default :
                row.style.background = '#e2eeff';
		}
	}

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
<script defer>
	let urlList = [];
    let aryLength = ${urlList.size()};
    let rowLength = '${reportClusterList.size()}';

    for (let i = 0; i < rowLength; i++) {
        let row = document.getElementById('row_' + i);

        // 상태 (5)로 테이블 색상 결정
        tableRowColor(row, row.children.item(5).innerText);
    }

    <c:choose>
		<c:when test="${!empty urlList}">
			<c:forEach var="i" begin="0" end="${urlList.size()-1}">
			urlList.push('${urlList.get(i)}');
			</c:forEach>

			for (let i = 0; i < aryLength; i++) {
				let row = document.getElementById('row_' + i);

				if (urlList[i] != null && urlList[i] !== '') {
                    for (let j = 0; j < row.childElementCount; j++) {
                        let col = row.children.item(j);
                        // 관리(7) 제외
                        if (j == 7) {
                            continue;
                        }
                        col.onclick = function() {
                            window.location = urlList[i];
                        }
                        row.style.cursor = 'pointer';
                    }
				}
			}
		</c:when>
	</c:choose>

	let url = new URL(location.href);

    if (url.searchParams.has("completeType")) {
        let dropDown = document.getElementById("completeTypeDropDownBtn")
        switch (url.searchParams.get("completeType")) {
            case "ALL" :
                dropDown.innerText = "전체 ";
                break;
			case "PROGRESS" :
                dropDown.innerText = "처리 중 ";
				break;
			case "COMPLETE" :
                dropDown.innerText = "처리 완료 ";
		}
	}
</script>
<script defer>
    function openReportClusterDetailPopup(rcNo) {
        var pop = window.open("/admin/report_center/report_detail?rcNo=" + rcNo, "pop", "width=1200,height=600, scrollbars=yes, resizable=yes");
    }

    function reportClusterCallBack() {
        location.reload();
    }
</script>
</html>
