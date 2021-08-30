let input = document.getElementById("imgInput");
let isBuffered = false;

$(document).ready(function(){
    qu_secret = $("#qu_secret").val();
    if(qu_secret=="true"){
        $("#qu_select").val("1");
    }else {
        $("#qu_select").val("0");
    }
})


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
        let item = document.getElementById("img_" + (index+1));
        reader.onload= e => {
            item.src = e.target.result;
            item.parentElement.setAttribute("class", "card col-3 p-0 m-2");
        }
        if (index != 9) {
            document.getElementById("img_" + (index+2)).parentElement.setAttribute("class", "card col-3 p-0 m-2");
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

function onClickRegister() {
    console.log("submit butten clicked");
    var $fileUpload = $("input[type='file']");
    var loginCheck = null;
    var debugCheck = {"debug": true}

    if (tagList.length < 3) {
        alert("해시태그는 최소 3개 이상 추가해야합니다.");
        return
    }

    $.ajax({
        type: "POST",
        url: "/rest/authenticationCheck",
        data: JSON.stringify(debugCheck),
        dataType: 'json',
        contentType: "application/json; charset=UTF-8",
        beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            // do something ...
            console.log("Login Check Success");
            console.log("is login : " + response);

            if (response) {
                if (parseInt($fileUpload.get(0).files.length) < 3) {
                    alert("최소 3개의 이미지 파일은 업로드 해야합니다.")
                } else if (parseInt($fileUpload.get(0).files.length) > 10) {
                    alert("최대 10개의 이미지 파일만 업로드 가능합니다.");
                } else {
                    var formData = $("form");
                    $.ajax({
                        type: "POST",
                        url: "/service/loc/tags",
                        data: {
                            tags: tagList //notice that "myArray" matches the value for @RequestParam
                            //on the Java side
                        },
                        success: function (response) {
                            // do something ...
                            console.log("장소 등록 성공");
                            alert("장소 등록 성공");
                        },
                        error: function (e) {
                            alert("에러 발생\n페이지를 새로고침 해주세요")
                            console.log("태그 등록 실패");
                        }
                    });
                    formData.submit();
                }
            }
            else {
                alert("장소 등록 실패 : 로그인을 해주세요");
                console.log("장소 등록 실패 : 로그인을 해주세요");
            }
        },error: function (e) {
            // console.log("Login Check Failed")
            // alert("장소 등록 실패");
            // console.log("장소 등록 실패");
            // onClickRegister();
            console.log(e);
        }

    });
}
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

    console.log("이미지"+selectedImageIndex);
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