<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<div class="row" style="margin: 3px">
	<div class="row my-3">
		<h2>리뷰 작성 페이지</h2>
		<div class="col">
			<table class="table">
				<tbody>
				<tr>
					<th>코스 이름</th>
					<td></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea rows="8" cols="40"></textarea></td>
				</tr>
				<tr>
					<th>총 별점</th>
					<td>
						<div id="total_rating">
							<c:forEach begin="1" end="5" var="i">
								<image class="sc_icon_small" src="http://localhost:8080/image/icon/star_black.png"></image>
							</c:forEach>
						</div>
					</td>
				</tr>
				<tr>
					<th>장소추천도</th>
					<td>
						<div id="loc_rating">
							<c:forEach begin="1" end="5" var="i">
								<image class="sc_icon_small" src="http://localhost:8080/image/icon/star_black.png" onclick="fillRating('loc', ${i})"></image>
							</c:forEach>
						</div>
					</td>
				</tr>
				<tr>
					<th>이동편리성</th>
					<td>
						<div id="move_rating">
							<c:forEach begin="1" end="5" var="i">
								<image class="sc_icon_small" src="http://localhost:8080/image/icon/star_black.png" onclick="fillRating('move', ${i})"></image>
							</c:forEach>
						</div>
					</td>
				</tr>
				<tr>
					<th>시간소요도</th>
					<td>
						<div id="time_rating">
							<c:forEach begin="1" end="5" var="i">
								<image class="sc_icon_small" src="http://localhost:8080/image/icon/star_black.png" onclick="fillRating('time', ${i})"></image>
							</c:forEach>
						</div>
					</td>
				</tr>
				<tr>
					<th>재방문의사</th>
					<td>
						<div id="revisit_rating">
							<c:forEach begin="1" end="5" var="i">
								<image class="sc_icon_small" src="http://localhost:8080/image/icon/star_black.png" onclick="fillRating('revisit', ${i})"></image>
							</c:forEach>
						</div>
					</td>
				</tr>
				<tr>
					<th style="margin: 8px">이미지</th>
					<td>
						<div class="col d-flex justify-content-end">
							<image type="button" id="img_move_left" name="img_move_left"
								   src="/image/icon/left-arrow.png" onclick="onClickImgMoveLeft()"
								   style="height: 30px"></image>
							<image type="button" id="img_move_right" name="img_move_right"
								   src="/image/icon/right-arrow.png" onclick="onClickImgMoveRight()"
								   style="height: 30px"></image>
						</div>
						<div id="canvas" class="row d-flex justify-content-start"
							 style="overflow-x: scroll; max-width: min-content /*outline: blue thick solid;*/">
							<input class="visually-hidden" id="imgInput" name="files" type="file" multiple
								   accept="image/*" onchange="readImage()">
							<c:forEach var="i" begin="1" end="3">
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
							</c:forEach>
<%--			<div class="row">
										<div class="card col-3 p-0 m-2"></div>
										<div class="card col-3 p-0 m-2"></div>
										<div class="card col-3 p-0 m-2"></div>
									</div>				--%>
						</div>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<form id="form" name="form" method="post">
	<input type="hidden" id="cor_name" name="cor_name" value=""/>
	<input type="hidden" id="cor_info" name="cor_info" value=""/>
	<%-- Todo returnURL작성 필수! --%>
	<input type="hidden" id="returnUrl" name="returnUrl" value=""/>
	<input type="hidden" id="resultType" name="resultType" value=""/>
	<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 START-->
	<!--
	<input type="hidden" id="encodingType" name="encodingType" value="EUC-KR"/>
	 -->
	<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 END-->
</form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
<script defer>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    window.onload = new function () {
        let returnURL = document.getElementById('returnUrl');
        returnURL.value = opener.document.URL;
	}

    let locRate;
    let moveRate;
    let timeRate;
    let revisitRate;
    let totalRate;
    let activeRatingImgSrc = "/image/icon/star_color.png";
    let inactiveRatingImgSrc = "/image/icon/star_black.png";

	function fillRating(type, index) {
        let selectedRating = null;

        switch (type) {
            case 'loc' :
                selectedRating = document.getElementById('loc_rating');
                locRate = index;
                break;
			case 'move' :
                selectedRating = document.getElementById('move_rating');
                moveRate = index;
                break;
			case 'time' :
                selectedRating = document.getElementById('time_rating');
                timeRate = index;
			    break;
			case 'revisit' :
                selectedRating = document.getElementById('revisit_rating');
                revisitRate = index;
			    break;
		}

		if (selectedRating == null) {
            console.log('selectedRating is null');
		    return;
		}

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

        if (locRate !== undefined && moveRate !== undefined && timeRate !== undefined && revisitRate !== undefined) {
            totalRating = document.getElementById("total_rating");
		}

        if (totalRating == null) {
            console.log("all rating hasn't been rated yet")
            return;
		}

        totalRate = Math.floor((locRate + moveRate + timeRate + revisitRate) / 4);

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
        for (let i = 1; i <= 10; i++) {
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
            let item = document.getElementById("img_" + (index + 1));
            reader.onload = e => {
                item.src = e.target.result;
                item.parentElement.setAttribute("class", "card col-3 p-0 m-2");
            }
            if (index != 9) {
                document.getElementById("img_" + (index + 2)).parentElement.setAttribute("class", "card col-3 p-0 m-2");
            }
            reader.readAsDataURL(file);
            console.log(item);
        })

        // // 기존에 있던 이미지 지우기
        for (let i = fileList.length + 1; i <= 10; i++) {
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

        for (let i = 1; i <= 10; i++) {
            if (i >= index) {
                if (i !== 10) {
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

        for (let i = input.files.length + 1; i <= 10; i++) {
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
</body>
</html>
