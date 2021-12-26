<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<jsp:useBean id="defaultDateTimeFormatter" class="com.project.love_data.util.DefaultLocalDateTimeFormatter"></jsp:useBean>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/service/loc_registration.css">
    <link rel="stylesheet" href="/css/service/loc.css">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
    </style>
    <title>Question add</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>
<div class="container-fluid d-flex" style="padding-top: 100px">
    <div class="col-2" id="sidebar">
        <ul class="nav nav-pills flex-column col-2 position-fixed" style="top: 40%">
            <div class="accordion text-center" id="loc">
                <hr>
                <div class="card">
                    <div class="card-header" id="headingLoc">
                        <h2 class="mb-0">
                            <button class="btn btn-link btn-block" type="button" data-toggle="collapse"
                                    data-target="#loc_collapse" aria-expanded="true" aria-controls="collapseOne"
                                    style="text-decoration: none; color: #FF6699; font-weight: bold">
                                고객센터
                            </button>
                        </h2>
                    </div>
                    <div id="loc_collapse" class="collapse show" aria-labelledby="headingLoc" data-parent="#loc">
                        <div class="card-body center-pill">
                            <p><a href="/ServiceCenter/Notice/1" class="highlight-not-selected-text-menu ">- 공지 사항</a></p>
                            <p><a href="/ServiceCenter/Questions/1" class="highlight-selected-text-menu">- 문의 사항</a></p>
                            <p><a href="/ServiceCenter/Event/1" class="highlight-not-selected-text-menu">- 이벤트</a></p>
                            <p><a href="/ServiceCenter/Policy" class="highlight-not-selected-text-menu">- LOVEDATA 정책</a></p>
                            <p><a href="/ServiceCenter/Withdrawal" class="highlight-not-selected-text-menu">- 회원 탈퇴</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </ul>
    </div>
    <div class="container-fluid" id="display_center" style="margin-right: 30px">
        <div class="container col-lg-6 ">
    <form action="/ServiceCenter/Questions_Post_Update/update" method="post" enctype="multipart/form-data">
        <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
        <input id="qu_no" name="qu_no" type="hidden" value="${qu.qu_no}"/>
        <input id="qu_secret" name="qu_secret" type="hidden" value="${qu.qu_secret}"/>
    <h2> 문의하기 </h2>
    <div class="card" style="padding:20px; border-radius: 15px; margin: 20px auto;">
        <div class="form-group" >
            <label>제목</label>
            <select id="qu_select" name="secret" >
                <option value="0">공개</option>
                <option value="1">비공개</option>
            </select>
            <input class="form-control" type="text" id="title" name="title"  value="${qu.qu_title}">
        </div>
        <div class="form-group">
            <label>작성자</label>
            <input class="form-control" type="text" id="name" name="name" value="<sec:authentication property="principal.user_nic"/>" readonly>
        </div>
        <div class="form-group">
            <label>내용</label>
            <textarea class="form-control" rows="10" maxlength="150" name="info" id="info"required>${qu.qu_text}</textarea>
        </div>
        <div class="row d-flex align-items-end">
            <div class="col">
                <span class="details">이미지</span>
            </div>
            <div class="col d-flex justify-content-end">
                <image type="button" id="img_move_left" name="img_move_left"
                       src="/image/icon/left-arrow.png" onclick="onClickImgMoveLeft()" style="height: 30px"></image>
                <image type="button" id="img_move_right" name="img_move_right"
                       src="/image/icon/right-arrow.png" onclick="onClickImgMoveRight()" style="height: 30px"></image>
            </div>
        </div>
        <div>
            <input class="visually-hidden" id="imgInput" name="files" type="file" multiple accept="image/*" onchange="readImage()">
            <div id="canvas" class="row flex-nowrap mx-0 mt-3" style="overflow-x: scroll">
                <c:forEach var="i" begin="0" end="${10}">
                    <c:choose>
                        <c:when test="${i < qu_img_size}">
                            <div class="card col-3 p-0 m-2">
                                    <%--											<button class="w-25" onclick="console.log('clicked!')"></button>--%>
                                <img src="/image/qna/${qu_img[i].qu_img_url}" alt="" id="img_${(i + 1)}" class="visible bd-place card-img w-100 h-100">
                                <div class="d-flex justify-content-center card-img-overlay visually-hidden" style="align-items: center;">
                                    <img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd_${(i + 1)}"
                                         src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%; z-index: 2">
                                </div>
                                <div class="d-flex justify-content-end card-img-overlay p-0" style="align-items: flex-start">
                                    <img class="btn btn-lg align-middle p-0" id="imgDel_${(i + 1)}" onclick="deleteImage(this)"
                                         src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png" style="z-index: 2">
                                </div>
                                <div class="d-flex justify-content-center card-img-overlay p-0" style="align-items: center">
                                    <img class="w-100 h-100" id="imgSel_${i+1}" onclick="onSelectImage(${i + 1})" src="/image/icon/480px-Solid_white.png"
                                         style="opacity : 0.0; z-index: 1;">
                                </div>
                            </div>
                        </c:when>
                        <c:when test="${i eq qu_img_size}">
                            <div class="card col-3 p-0 m-2">
                                <img src="/image/icon/480px-Solid_white.png" alt="" id="img_${(i + 1)}" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
                                <div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
                                    <img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd_${(i + 1)}"
                                         src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%; z-index: 2">
                                </div>
                                <div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
                                    <img class="btn btn-lg align-middle p-0" id="imgDel_${(i + 1)}" onclick="deleteImage(this)"
                                         src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png" style="z-index: 2">
                                </div>
                                <div class="d-flex justify-content-center card-img-overlay p-0 visually-hidden" style="align-items: center">
                                    <img class="w-100 h-100" id="imgSel_${i+1}" onclick="onSelectImage(${i + 1})" src="/image/icon/480px-Solid_white.png"
                                         style="opacity : 0.0; z-index: 1;">
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="card col-3 p-0 m-2 visually-hidden">
                                <img src="/image/icon/480px-Solid_white.png" alt="" id="img_${(i + 1)}" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
                                <div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
                                    <img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd_${(i + 1)}"
                                         src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%; z-index: 2">
                                </div>
                                <div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
                                    <img class="btn btn-lg align-middle p-0" id="imgDel_${(i + 1)}" onclick="deleteImage(this)"
                                         src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png" style="z-index: 2">
                                </div>
                                <div class="d-flex justify-content-center card-img-overlay p-0 visually-hidden" style="align-items: center">
                                    <img class="w-100 h-100" id="imgSel_${i+1}" onclick="onSelectImage(${i + 1})" src="/image/icon/480px-Solid_white.png"
                                         style="opacity : 0.0; z-index: 1;">
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </div>
        <div class="btn_wrap text-center">
            <button type="submit" id="post_add" name="post_add">수정하기</button>
        </div>
    </div>
    </form>
</div>
    </div>
</div>

<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script defer src="/js/loc_detail.js"></script>
<script defer src="/js/loc_registration.js"></script>
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

    <%--						http://yoonbumtae.com/?p=3304 --%>
    function readImage() {
        let fileList = Array.from(input.files);

        console.log(input.files);
        console.log(fileList);
        fileList.forEach((file, index) => {
            let reader = new FileReader();
            // console.log(i + "번 째 아이템이 등록되었습니다.");
            let item = document.getElementById("img_" + (index+1));
            reader.onload= e => {
                item.src = e.target.result;
                item.parentElement.setAttribute("class", "card col-3 p-0 m-2");
            }
            if (index != 9) {
                document.getElementById("img_" + (index+2)).parentElement.setAttribute("class", "card col-3 p-0 m-2");
            }
            reader.readAsDataURL(file);
            console.log("form"+item);
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
        for (let file of input.files){
            if (file !== input.files[index - 1]){
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
                    document.getElementById("img_"+i).src = document.getElementById("img_"+(i+1)).src;
                } else {
                    document.getElementById("img_"+i).src = "/image/icon/480px-Solid_white.png";
                }
            }

            // if (dt.items.length+1 == i) {
            //     document.getElementById("imgAdd_"+i).parentElement.setAttribute("class", "d-flex justify-content-center card-img-overlay");
            //     document.getElementById("imgDel_"+i).parentElement.setAttribute("class", "d-flex justify-content-end card-img-overlay p-0 visually-hidden");
            // }

            if (dt.items.length+1 < i) {
                document.getElementById("img_"+i).parentElement.setAttribute("class", "card col-3 p-0 m-2 visually-hidden");
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

    function getLocImg() {
        let dtoImgURLList = [];
        let dt = new DataTransfer();
        //qu_img[i].qu_img_url
        <c:forEach items="${qu_img}" var="id">
        dtoImgURLList.push("${id.qu_img_url}");
        </c:forEach>

        console.log(dtoImgURLList);

        for (let i = 0; i < dtoImgURLList.length; i++){
            let temp = dtoImgURLList[i].split('/');
            let uuid = temp[temp.length - 1];
            let mediaType = "image/" + uuid.split('.')[1];
            dt.items.add(new File([dtoImgURLList[i]], uuid, {type : mediaType, lastModified : Date.now()}));
        }

        input.files = dt.files;
    }

    window.addEventListener('onload', getLocImg());
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

        let leftObj = input.files.item(selectedImageIndex-2);
        let selectedObj = input.files.item(selectedImageIndex-1);
        let leftObjImg = document.getElementById("img_" + (selectedImageIndex-1)).src;
        let selectedObjImg = document.getElementById("img_" + (selectedImageIndex)).src;

        console.log(selectedImageIndex);
        for (let i = 0; i < input.files.length; i++) {
            if (selectedImageIndex -1 === i) {
                dt.items.add(leftObj);
                document.getElementById("img_" + (i+1)).src = leftObjImg;
                continue;
            }

            if (selectedImageIndex -2 === i) {
                dt.items.add(selectedObj);
                document.getElementById("img_" + (i+1)).src = selectedObjImg;
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
        let selectedObj = input.files.item(selectedImageIndex-1);
        let rightObjImg = document.getElementById("img_" + (selectedImageIndex+1)).src;
        let selectedObjImg = document.getElementById("img_" + (selectedImageIndex)).src;

        console.log(selectedImageIndex);
        for (let i = 0; i < input.files.length; i++) {
            if (selectedImageIndex -1 === i) {
                dt.items.add(rightObj);
                document.getElementById("img_" + (i+1)).src = rightObjImg;
                continue;
            }

            if (selectedImageIndex === i) {
                dt.items.add(selectedObj);
                document.getElementById("img_" + (i+1)).src = selectedObjImg;
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
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
</html>