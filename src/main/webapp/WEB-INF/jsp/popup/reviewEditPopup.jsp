<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%--	<meta name="viewport" content="width=device-width, initial-scale=1">--%>
	<meta name="_csrf" content="${_csrf.token}">
	<meta name="_csrf_header" content="${_csrf.headerName}">
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/service/score.css">
	<style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
	</style>
	<title>Review Register Popup</title>
</head>
<body>
<c:set var="maxRevImgCount" value="3"></c:set>
<sec:authorize access="isAnonymous()">
	<span>리뷰를 수정하기 전에 로그인 해주세요</span>
	<%
		if( true ) return;
	%>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
	<c:set var="user_no"><sec:authentication property="principal.user_no"/></c:set>
	<c:choose>
		<c:when test="${dto.userNo} ne ${user_no}">
			<sec:authorize access="!hasAnyRole('ROLE_ADIMN')">
				<span>리뷰 작성자가 아닙니다. 리뷰를 수정할 수 없습니다.</span>
			</sec:authorize>
		</c:when>
	</c:choose>
	<%-- https://stackoverflow.com/questions/4185814/fixed-table-cell-width --%>
	<div class="row" style="margin: 3px">
		<div class="row my-3">
			<h2>리뷰 작성 페이지</h2>
			<div class="col">
				<table class="table" style="table-layout: fixed;">
					<tbody>
					<tr>
						<th>코스 이름</th>
						<td colspan="6"><span id="popup_cor_name">${pageContext.request.getParameter("corName")}</span></td>
					</tr>
					<tr>
						<th>내용</th>
						<td colspan="6"><textarea rows="8" cols="40" id="rev_content" name="rev_content">${dto.revContent}</textarea></td>
					</tr>
					<tr>
						<th>장소추천도</th>
						<td colspan="6">
							<div id="loc_rating">
								<c:forEach begin="1" end="5" var="i">
									<c:choose>
										<c:when test="${i <= dto.sc_loc}">
											<image class="sc_icon_small" src="/image/icon/star_color.png" onclick="fillRating('loc', ${i})"></image>
										</c:when>
										<c:otherwise>
											<image class="sc_icon_small" src="/image/icon/star_black.png" onclick="fillRating('loc', ${i})"></image>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</div>
						</td>
					</tr>
					<tr>
						<th>이동편리성</th>
						<td colspan="6">
							<div id="move_rating">
								<c:forEach begin="1" end="5" var="i">
									<c:choose>
										<c:when test="${i <= dto.sc_move}">
											<image class="sc_icon_small" src="/image/icon/star_color.png" onclick="fillRating('move', ${i})"></image>
										</c:when>
										<c:otherwise>
											<image class="sc_icon_small" src="/image/icon/star_black.png" onclick="fillRating('move', ${i})"></image>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</div>
						</td>
					</tr>
					<tr>
						<th>시간소요도</th>
						<td colspan="6">
							<div id="time_rating">
								<c:forEach begin="1" end="5" var="i">
									<c:choose>
										<c:when test="${i <= dto.sc_time}">
											<image class="sc_icon_small" src="/image/icon/star_color.png" onclick="fillRating('time', ${i})"></image>
										</c:when>
										<c:otherwise>
											<image class="sc_icon_small" src="/image/icon/star_black.png" onclick="fillRating('time', ${i})"></image>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</div>
						</td>
					</tr>
					<tr>
						<th>재방문의사</th>
						<td colspan="6">
							<div id="revisit_rating">
								<c:forEach begin="1" end="5" var="i">
									<c:choose>
										<c:when test="${i <= dto.sc_revisit}">
											<image class="sc_icon_small" src="/image/icon/star_color.png" onclick="fillRating('revisit', ${i})"></image>
										</c:when>
										<c:otherwise>
											<image class="sc_icon_small" src="/image/icon/star_black.png" onclick="fillRating('revisit', ${i})"></image>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</div>
						</td>
					</tr>
					<tr>
						<th>총 별점</th>
						<td colspan="6">
							<div id="total_rating">
								<c:forEach begin="1" end="5" var="i">
									<c:choose>
										<c:when test="${i <= dto.sc_total}">
											<image class="sc_icon_small" src="/image/icon/star_color.png"></image>
										</c:when>
										<c:otherwise>
											<image class="sc_icon_small" src="/image/icon/star_black.png"></image>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</div>
						</td>
					</tr>
					<tr>
						<th style="margin: 8px">이미지</th>
						<td colspan="6">
							<div class="row">
								<div class="col d-flex justify-content-end">
									<image type="button" id="img_move_left" name="img_move_left"
										   src="/image/icon/left-arrow.png" onclick="onClickImgMoveLeft()"
										   style="height: 30px"></image>
									<image type="button" id="img_move_right" name="img_move_right"
										   src="/image/icon/right-arrow.png" onclick="onClickImgMoveRight()"
										   style="height: 30px"></image>
								</div>
								<div id="canvas" class="row flex-nowrap mx-0"
									 style="overflow-x: scroll; /*outline: blue thick solid;*/">
									<c:forEach var="i" begin="1" end="${maxRevImgCount}">
										<c:choose>
											<c:when test="${revImgList eq null}">
												<c:choose>
													<c:when test="${i eq 1}">
														<div class="card col-3 p-0 m-2">
															<img src="/image/icon/480px-Solid_white.png" alt="" id="img_${i}"
																 class="visible bd-place card-img"
																 style="height: 244px; width: 100%; outline: none">
															<div class="d-flex justify-content-center card-img-overlay"
																 style="align-items: center">
																<img class="btn btn-lg align-middle" onclick="onClickAddImage()"
																	 id="imgAdd_${i}"
																	 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png"
																	 style="height: 30%; z-index: 2">
															</div>
															<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden"
																 style="align-items: flex-start">
																<img class="btn btn-lg align-middle p-0" id="imgDel_${i}"
																	 onclick="deleteImage(this)"
																	 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png"
																	 style="z-index: 2">
															</div>
															<div class="d-flex justify-content-center card-img-overlay p-0 visually-hidden"
																 style="align-items: center">
																<img class="w-100 h-100" id="imgSel_${i}" onclick="onSelectImage(${i})"
																	 src="/image/icon/480px-Solid_white.png"
																	 style="opacity : 0.0; z-index: 1;">
															</div>
														</div>
													</c:when>
													<c:otherwise>
														<div class="card col-3 p-0 m-2 visually-hidden">
															<img src="/image/icon/480px-Solid_white.png" alt="" id="img_${i}"
																 class="visible bd-place card-img"
																 style="height: 244px; width: 100%; outline: none">
															<div class="d-flex justify-content-center card-img-overlay"
																 style="align-items: center">
																<img class="btn btn-lg align-middle" onclick="onClickAddImage()"
																	 id="imgAdd_${i}"
																	 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png"
																	 style="height: 30%; z-index: 2">
															</div>
															<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden"
																 style="align-items: flex-start">
																<img class="btn btn-lg align-middle p-0" id="imgDel_${i}"
																	 onclick="deleteImage(this)"
																	 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png"
																	 style="z-index: 2">
															</div>
															<div class="d-flex justify-content-center card-img-overlay p-0 visually-hidden"
																 style="align-items: center">
																<img class="w-100 h-100" id="imgSel_${i}" onclick="onSelectImage(${i})"
																	 src="/image/icon/480px-Solid_white.png"
																	 style="opacity : 0.0; z-index: 1;">
															</div>
														</div>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${i <= revImgList.size()}">
														<div class="card col-3 p-0 m-2">
															<img src="${revImgList.get(i-1).img_url}" alt="" id="img_${i}"
																 class="visible bd-place card-img"
																 style="height: 244px; width: 100%; outline: none">
															<div class="d-flex justify-content-center card-img-overlay visually-hidden"
																 style="align-items: center">
																<img class="btn btn-lg align-middle" onclick="onClickAddImage()"
																	 id="imgAdd_${i}"
																	 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png"
																	 style="height: 30%; z-index: 2">
															</div>
															<div class="d-flex justify-content-end card-img-overlay p-0"
																 style="align-items: flex-start">
																<img class="btn btn-lg align-middle p-0" id="imgDel_${i}"
																	 onclick="deleteImage(this)"
																	 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png"
																	 style="z-index: 2">
															</div>
															<div class="d-flex justify-content-center card-img-overlay p-0"
																 style="align-items: center">
																<img class="w-100 h-100" id="imgSel_${i}" onclick="onSelectImage(${i})"
																	 src="/image/icon/480px-Solid_white.png"
																	 style="opacity : 0.0; z-index: 1;">
															</div>
														</div>
													</c:when>
													<c:when test="${i == revImgList.size()+1}">
														<div class="card col-3 p-0 m-2">
															<img src="/image/icon/480px-Solid_white.png" alt="" id="img_${i}"
																 class="visible bd-place card-img"
																 style="height: 244px; width: 100%; outline: none">
															<div class="d-flex justify-content-center card-img-overlay"
																 style="align-items: center">
																<img class="btn btn-lg align-middle" onclick="onClickAddImage()"
																	 id="imgAdd_${i}"
																	 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png"
																	 style="height: 30%; z-index: 2">
															</div>
															<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden"
																 style="align-items: flex-start">
																<img class="btn btn-lg align-middle p-0" id="imgDel_${i}"
																	 onclick="deleteImage(this)"
																	 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png"
																	 style="z-index: 2">
															</div>
															<div class="d-flex justify-content-center card-img-overlay p-0"
																 style="align-items: center">
																<img class="w-100 h-100" id="imgSel_${i}" onclick="onSelectImage(${i})"
																	 src="/image/icon/480px-Solid_white.png"
																	 style="opacity : 0.0; z-index: 1;">
															</div>
														</div>
													</c:when>
													<c:otherwise>
														<div class="card col-3 p-0 m-2 visually-hidden">
															<img src="/image/icon/480px-Solid_white.png" alt="" id="img_${i}"
																 class="visible bd-place card-img"
																 style="height: 244px; width: 100%; outline: none">
															<div class="d-flex justify-content-center card-img-overlay"
																 style="align-items: center">
																<img class="btn btn-lg align-middle" onclick="onClickAddImage()"
																	 id="imgAdd_${i}"
																	 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png"
																	 style="height: 30%; z-index: 2">
															</div>
															<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden"
																 style="align-items: flex-start">
																<img class="btn btn-lg align-middle p-0" id="imgDel_${i}"
																	 onclick="deleteImage(this)"
																	 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png"
																	 style="z-index: 2">
															</div>
															<div class="d-flex justify-content-center card-img-overlay p-0 visually-hidden"
																 style="align-items: center">
																<img class="w-100 h-100" id="imgSel_${i}" onclick="onSelectImage(${i})"
																	 src="/image/icon/480px-Solid_white.png"
																	 style="opacity : 0.0; z-index: 1;">
															</div>
														</div>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</div>
							</div>
						</td>
					</tr>
					</tbody>
				</table>
				<div class="d-flex align-content-center justify-content-center">
					<button class="btn-lg btn-primary" id="rev_submit" name="rev_submit" onclick="onClickSubmit()">전송</button>
				</div>
			</div>
		</div>
	</div>
	<form action="/service/rev_edit" id="form" name="form" method="post" enctype="multipart/form-data">
		<input type="hidden" id="rev_no" name="rev_no" value="${dto.revNo}">
		<input type="hidden" id="rev_id" name="rev_id" value="${dto.revUuid}">
		<input type="hidden" id="user_no" name="user_no" value="${user_no}">
		<input type="hidden" id="cor_no" name="cor_no" value="${pageContext.request.getParameter("corNo")}">
		<input type="hidden" id="rev_content_input" name="rev_content_input" value="${dto.revContent}">
		<input type="hidden" id="rev_total_rate" name="rev_total_rate" value="${dto.sc_total}">
		<input type="hidden" id="rev_loc_rate" name="rev_loc_rate" value="${dto.sc_loc}">
		<input type="hidden" id="rev_move_rate" name="rev_move_rate" value="${dto.sc_move}">
		<input type="hidden" id="rev_time_rate" name="rev_time_rate" value="${dto.sc_time}">
		<input type="hidden" id="rev_revisit_rate" name="rev_revisit_rate" value="${dto.sc_revisit}">
		<input class="visually-hidden" id="imgInput" name="files" type="file" multiple
			   accept="image/*" onchange="readImage()">
		<input type="hidden" id="resultType" name="resultType" value="review"/>
		<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 START-->
		<!--
		<input type="hidden" id="encodingType" name="encodingType" value="EUC-KR"/>
		 -->
		<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 END-->
	</form>
</sec:authorize>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
<script>
	window.onload = function() {
        // getRevImg()
        let dtoImgURLList = [];
        let dt = new DataTransfer();

        <c:forEach items="${revImgList}" var="id">
        	dtoImgURLList.push("${id.img_url}");
        </c:forEach>

        for (let i = 0; i < dtoImgURLList.length; i++){
            let temp = dtoImgURLList[i].split('/');
            let uuid = temp[temp.length - 1];
            let mediaType = "image/" + uuid.split('.')[1];
            dt.items.add(new File([dtoImgURLList[i]], uuid, {type : mediaType, lastModified : Date.now()}));
        }

        input.files = dt.files;
	}
</script>
<script defer>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    // window.onload = new function () {
    //     let returnURL = document.getElementById('returnUrl');
    //     returnURL.value = opener.document.URL;
	// }

    let locRate;
    let moveRate;
    let timeRate;
    let revisitRate;
    let totalRate;
    let activeRatingImgSrc = "/image/icon/star_color.png";
    let inactiveRatingImgSrc = "/image/icon/star_black.png";

	function fillRating(type, index) {
        let selectedRating = null;
        let selectedRatingInput = null;

        switch (type) {
            case 'loc' :
                selectedRating = document.getElementById('loc_rating');
                selectedRatingInput = document.getElementById('rev_loc_rate');
                locRate = index;
                break;
			case 'move' :
                selectedRating = document.getElementById('move_rating');
                selectedRatingInput = document.getElementById('rev_move_rate');
                moveRate = index;
                break;
			case 'time' :
                selectedRating = document.getElementById('time_rating');
                selectedRatingInput = document.getElementById('rev_time_rate');
                timeRate = index;
			    break;
			case 'revisit' :
                selectedRating = document.getElementById('revisit_rating');
                selectedRatingInput = document.getElementById('rev_revisit_rate');
                revisitRate = index;
			    break;
		}

		if (selectedRating == null || selectedRatingInput == null) {
            console.log('selectedRating or selectedRatingInput is null');
		    return;
		}

		selectedRatingInput.value = index;

        for (let i = 0; i < index; i++) {
			selectedRating.children.item(i).src = activeRatingImgSrc;
        }

        for (let i = index; i < 5; i++) {
            selectedRating.children.item(i).src = inactiveRatingImgSrc;
		}

        checkRatingChange();
	}

    function checkRatingChange() {
        let totalRating = null;
        let totalRatingInput = null;

        if (locRate !== undefined && moveRate !== undefined && timeRate !== undefined && revisitRate !== undefined) {
            totalRating = document.getElementById("total_rating");
            totalRatingInput = document.getElementById("rev_total_rate");
		}

        if (totalRating == null) {
            console.log("all rating hasn't been rated yet")
            return;
		}

        totalRate = Math.floor((locRate + moveRate + timeRate + revisitRate) / 4);
        totalRatingInput.value = totalRate;

        // console.log((locRate + moveRate + timeRate + revisitRate) / 4);
        // console.log(totalRate);

        for (let i = 0; i < totalRate; i++) {
            totalRating.children.item(i).src = activeRatingImgSrc;
        }

        for (let i = totalRate; i < 5; i++) {
            totalRating.children.item(i).src = inactiveRatingImgSrc;
        }
    }
</script>
<script defer>
    let input = document.getElementById("imgInput");
    let isBuffered = false;

    function onClickAddImage() {
        $('#imgInput').trigger('click');
    }

    function toggleAddDelBtn(offset) {
        for (let i = 1; i <= ${maxRevImgCount}; i++) {
            let btnAddParent = document.getElementById("imgAdd_" + i).parentElement;
            let btnDelParent = document.getElementById("imgDel_" + i).parentElement;
            let btnSelParent = document.getElementById("imgSel_" + i).parentElement;

            if (offset < i) {
                btnAddParent.setAttribute('class', 'd-flex justify-content-center card-img-overlay');
                btnDelParent.setAttribute('class', 'd-flex justify-content-end card-img-overlay p-0 visually-hidden');
                btnSelParent.setAttribute('class', 'd-flex justify-content-center card-img-overlay p-0 visually-hidden');
            } else {
                btnAddParent.setAttribute('class', 'd-flex justify-content-center card-img-overlay visually-hidden');
                btnDelParent.setAttribute('class', 'd-flex justify-content-end card-img-overlay p-0');
                btnSelParent.setAttribute('class', 'd-flex justify-content-center card-img-overlay p-0');
            }
        }
    }

    function readImage() {
        let fileList = Array.from(input.files);

        console.log(input.files);
        console.log(fileList);
        fileList.forEach((file, index) => {
            let reader = new FileReader();
            // console.log(i + "번 째 아이템이 등록되었습니다.");
			if (index >= ${maxRevImgCount}) {
                deleteFileListImage();
                return false;
			}
            let item = document.getElementById("img_" + (index + 1));
            reader.onload = e => {
                item.src = e.target.result;
                item.parentElement.setAttribute("class", "card col-3 p-0 m-2");
            }
            if (index < (${maxRevImgCount} - 1)) {
                document.getElementById("img_" + (index + 2)).parentElement.setAttribute("class", "card col-3 p-0 m-2");
            }
            reader.readAsDataURL(file);
            console.log(item);
        })

        // // 기존에 있던 이미지 지우기
        for (let i = fileList.length + 1; i <= ${maxRevImgCount}; i++) {
            if (i === (fileList.length + 1)) {
                document.getElementById("img_" + i).parentElement.setAttribute("class", "card col-3 p-0 m-2");
            } else {
                document.getElementById("img_" + i).parentElement.setAttribute("class", "card col-3 p-0 m-2 visually-hidden");
            }

            document.getElementById("img_" + i).src = "/image/icon/480px-Solid_white.png";
        }
        toggleAddDelBtn(fileList.length);
        // onSelectImage(selectedImageIndex + 1);
    }

    function deleteFileListImage() {
        let dt = new DataTransfer();
        dt.files = input.files;

        for (let i = 0; i < ${maxRevImgCount}; i++) {
			dt.items.add(input.files[i]);
        }

        input.files = dt.files;
        console.log(dt.files);
        console.log(input.files);
	}

    function deleteImage(obj) {
        <%-- https://stackoverflow.com/questions/16943605/remove-a-filelist-item-from-a-multiple-inputfile  --%>
        let dt = new DataTransfer();
        dt.files = input.files;

        let objId = obj.id.split('_');
        let index = objId[objId.length - 1];
        console.log(index);
        for (let file of input.files) {
            if (file !== input.files[index - 1]) {
                dt.items.add(file);
            }
        }

        if (selectedImageParent !== null) {
            if (selectedImageIndex < index) {
                onSelectImage(selectedImageIndex);
            } else {
                if (Number(index) + 1 == selectedImageIndex) {
                    onSelectImage(index);
                } else {
                    onClearSelecteImage();
                }
                // onClearSelecteImage();
            }
        }

        for (let i = 1; i <= ${maxRevImgCount}; i++) {
            if (i >= index) {
                if (i !== ${maxRevImgCount}) {
                    document.getElementById("img_" + i).src = document.getElementById("img_" + (i + 1)).src;
                } else {
                    document.getElementById("img_" + i).src = "/image/icon/480px-Solid_white.png";
                }
            }

            // if (dt.items.length+1 == i) {
            //     document.getElementById("imgAdd_"+i).parentElement.setAttribute("class", "d-flex justify-content-center card-img-overlay");
            //     document.getElementById("imgDel_"+i).parentElement.setAttribute("class", "d-flex justify-content-end card-img-overlay p-0 visually-hidden");
            // }

            if (dt.items.length + 1 < i) {
                document.getElementById("img_" + i).parentElement.setAttribute("class", "card col-3 p-0 m-2 visually-hidden");
            }
        }

        input.files = dt.files;
        console.log(dt.files);
        console.log(input.files);

        for (let i = input.files.length + 1; i <= ${maxRevImgCount}; i++) {
            let img = document.getElementById("img_" + i);
            img.src = "/image/icon/480px-Solid_white.png";
        }

        toggleAddDelBtn(input.files.length);
    }
</script>
<script defer>
    let selectedImageParent = null;
    let selectedImageIndex = null;
    let selectedImage = null;

    function onClearSelecteImage() {
        selectedImageParent.style.outline = 'none';
        selectedImageParent = null;
        selectedImageIndex = null;
    }

    function onSelectImage(index) {
        if (selectedImageIndex === index) {
            onClearSelecteImage();
            return;
        }

        selectedImageIndex = index;

        if (selectedImageParent !== null) {
            // selectedImage.style.opacity = 0.0;
            selectedImageParent.style.outline = 'none';
        }

        // console.log(document.getElementById("img_" + index));
        console.log("index : " + index);
        selectedImage = document.getElementById("img_" + index);
        selectedImageParent = selectedImage.parentElement;

        // selectedImage.style.opacity = 0.3;
        selectedImageParent.style.outline = 'solid thick red'
    }

    function onClickImgMoveLeft() {
        let dt = new DataTransfer();
        dt.files = input.files;

        if (selectedImageIndex === null) {
            return;
        }

        let leftObj = input.files.item(selectedImageIndex - 2);
        let selectedObj = input.files.item(selectedImageIndex - 1);
        let leftObjImg = document.getElementById("img_" + (selectedImageIndex - 1)).src;
        let selectedObjImg = document.getElementById("img_" + (selectedImageIndex)).src;

        console.log(selectedImageIndex);
        for (let i = 0; i < input.files.length; i++) {
            if (selectedImageIndex - 1 === i) {
                dt.items.add(leftObj);
                document.getElementById("img_" + (i + 1)).src = leftObjImg;
                continue;
            }

            if (selectedImageIndex - 2 === i) {
                dt.items.add(selectedObj);
                document.getElementById("img_" + (i + 1)).src = selectedObjImg;
                continue;
            }

            dt.items.add(input.files.item(i));
        }

        input.files = dt.files;
        console.log(input.files);
        onSelectImage(selectedImageIndex - 1);
    }

    function onClickImgMoveRight() {
        let dt = new DataTransfer();
        dt.files = input.files;

        if (selectedImageIndex === null) {
            return;
        }

        let rightObj = input.files.item(selectedImageIndex);
        let selectedObj = input.files.item(selectedImageIndex - 1);
        let rightObjImg = document.getElementById("img_" + (selectedImageIndex + 1)).src;
        let selectedObjImg = document.getElementById("img_" + (selectedImageIndex)).src;

        console.log(selectedImageIndex);
        for (let i = 0; i < input.files.length; i++) {
            if (selectedImageIndex - 1 === i) {
                dt.items.add(rightObj);
                document.getElementById("img_" + (i + 1)).src = rightObjImg;
                continue;
            }

            if (selectedImageIndex === i) {
                dt.items.add(selectedObj);
                document.getElementById("img_" + (i + 1)).src = selectedObjImg;
                continue;
            }

            dt.items.add(input.files.item(i));
        }

        input.files = dt.files;
        console.log(input.files);
        onSelectImage(selectedImageIndex + 1);
    }
</script>
<script defer>
    // $("imgInput").change(function(){
    //     var $this = $(this), $clone = $this.clone();
    //     $this.after($clone).appendTo(opener.document.getElementById("form"));
    // });

	function onClickSubmit() {
        let comment = document.getElementById("rev_content");
        let comment_input = document.getElementById("rev_content_input");
        let total_rate = document.getElementById("rev_total_rate");
        let loc_rate = document.getElementById("rev_loc_rate");
        let move_rate = document.getElementById("rev_move_rate");
        let time_rate = document.getElementById("rev_time_rate");
		let revisit_rate = document.getElementById("rev_revisit_rate");
        let rev_no = document.getElementById("rev_no");
		let imgInput = document.getElementById("imgInput");
        let $fileUpload = $("input[type='file']");

		if (comment.value == "") {
		    alert("리뷰 내용이 비어있습니다.\n내용을 채워주세요.");
			return;
		}

		if (total_rate.value == "" || move_rate.value == "" || loc_rate.value == "" || time_rate.value == "" || revisit_rate.value == "") {
		    alert("별점이 전부 입력되지 않았습니다.");
		    return;
		}

		if (parseInt($fileUpload.get(0).files.length) > 3) {
            alert("최대 ${maxRevImgCount}개의 이미지 파일만 업로드 가능합니다.");
            return;
		}

		let reviewMapParam = new Map();

		reviewMapParam.set('totalRate', total_rate.value);
		reviewMapParam.set('locRate', loc_rate.value);
		reviewMapParam.set('moveRate', move_rate.value);
		reviewMapParam.set('timeRate', time_rate.value);
		reviewMapParam.set('revisitRate', revisit_rate.value);
		reviewMapParam.set('commentInput', comment.value);
        reviewMapParam.set('revNo', rev_no.value);

		opener.reviewEditCallBack(reviewMapParam, imgInput);
		window.close();
	}
</script>
</body>
</html>
