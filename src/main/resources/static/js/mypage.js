var mynick = true;

//home로 돌아가기
function gohome(){
    alert("홈 화면으로 돌아갑니다!");
    location.href="/";
}
//내정보 탭 변경
function MenuTab(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
// document.getElementById("defaultOpen").click();


//프로필 이미지 등록
function handleFileSelect(event) {
    var input = this;
    // console.log(input.files)
    if (input.files && input.files.length) {
        var reader = new FileReader();
        this.enabled = false
        reader.onload = (function (e) {
            console.log(e)
            $("#preview").html(['<img class="thumb" src="', e.target.result, '" title="', escape(e.name), '"/>'].join(''))
        });
        reader.readAsDataURL(input.files[0]);
    }
}
$('#file').change(handleFileSelect);
$('.file-edit-icon').on('click', '.preview-de', function () {
    $("#preview").empty()
    $("#file").val("");
});
$('.preview-edit').click( function() {
    $("#file").click();
} );



//휴대폰번호 나누기
$(document).ready(function () {
    var no = $("#numnum").val();
    var fnum = no.substring(0,3);
    var snum = no.substring(3,7);
    var lnum = no.substring(7,11);
    console.log(fnum +"  " + snum+"  " + lnum);
    $("#firnum").val(fnum);
    $("#twonum").val(snum);
    $("#lastnum").val(lnum);
});

//생년월일 나누기
$(document).ready(function () {
    var mybusday = $("#mybir").val();
    var myyear = mybusday.substring(0,4);
    var mymon = mybusday.substring(5,7);
    var myday = mybusday.substring(8,11);
    console.log(myyear +"  " + mymon+"  " + myday);
    $("#year").val(myyear);
    $("#month").val(mymon);
    $("#day").val(myday);
});

//성별 나누기
$(document).ready(function () {
    var jd = $("#jender").val();
    var us = $("#user_social").val();
    console.log(jd);
    if(us =="true"){
        if(jd == "true"){
            $("#mann").prop("checked", true).attr('disabled',true);
            $("#womann").attr('disabled',true);
        }
        else{
            $("#womann").prop("checked", true).attr('disabled',true);
            $("#mann").attr('disabled',true);
        }
    }else{


        if(jd == "true"){
            $("#mann").prop("checked", true);
        }
        else{
            $("#womann").prop("checked", true);
        }
    }
});


// //새 비밀번호 양식 확인
// function chkpw(){
//     var pw = $("#pwd1").val();
//     var num = pw.search(/[0-9]/g);
//     var eng = pw.search(/[A-z]/ig);
//     var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
//     var s_relult1 = $('#pwd_rule');
//     s_relult1.css("color", "red")
//     if(pw.length < 10 || pw.length > 16){
//         if(pw.length == 0){
//             s_relult1.text("영문, 숫자, 특수문자를 포함한 10자리 이상 입력하세요.");
//             mainpwd = false;
//         }
//         else{
//             s_relult1.text("10자리 ~ 16자리 이내로 입력해주세요.");
//             mainpwd = false;
//         }
//     }
//     else if(pw.search(/\s/) != -1){
//         s_relult1.text("비밀번호는 공백 없이 입력해주세요.");
//         mainpwd = false;
//     }
//     else if(num < 0 || (eng < 0 && spe < 0) ){
//         s_relult1.text("영문과 숫자, 특수문자를 혼합하여 입력해주세요.");
//         mainpwd = false;
//
//     }
//     else {
//         s_relult1.css("color", "green");
//         s_relult1.text("사용가능한 비밀번호 입니다.");
//         mainpwd = true;
//     }
// }

// // 수정 된 비밀번호 확인
// function repasswordcheck(){
//     var pwd1 = $('#pwd1').val();
//     var pwd2 = $('#NewPasswordre').val();
//     var p_chack = $('#password_check');
//     if (pwd1 == pwd2) {
//         if (pwd2 == 0) {
//             p_chack.text("");
//             mainrepwd = false;
//         }
//         else {
//             p_chack.css("color", "green");
//             p_chack.text('새로운 비밀번호가 일치합니다.');
//             mainrepwd = true;
//         }
//     }
//     else {
//         p_chack.css("color", "red");
//         p_chack.text('새로운 비밀번호가 일치하지 않습니다.');
//         mainrepwd = false;
//     }
// }

// ajax token
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });

// 닉네임 중복확인
function nick_check(){
    var nickname = $('#newnic');
    var s_relult = $('#newnic_check');
    var postdata = {"nickname" : nickname.val() }
    mynick = false;

    $.ajax({
        url: "/nick_check",
        dataType: 'json',
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(postdata),
        type: "POST",
        success:function(data){
            var datas = data.msg;
            console.log(datas);
            if(datas=="0"){
                s_relult.css("color", "red");
                s_relult.text("사용 중인 닉네임 입니다!");
            }
            else {
                if(nickname.val().search(/\s/) != -1 || nickname.val().length == 0){
                    s_relult.css("color", "red");
                    s_relult.text("닉네임을 입력해주세요");
                }
                else {
                    s_relult.css("color", "green");
                    s_relult.text("사용 가능한 닉네임입니다.");
                    mynick = true;
                }
            }
        },
        error : function(){
            console.log(datas);
        }
    });
}

//프로필 사진 삭제
function picdel(){
    $.ajax({
        url: "/mypropicdel",
        dataType: 'json',
        contentType: "application/json; charset=UTF-8",
        // data: JSON.stringify(),
        type: "POST",
        success:function(data){
            var datas = data.msg;
            console.log(datas);
            if(datas=="0"){
                $("#preview").html(['<img class="thumb" src="/image/icon/user/user.png"/>'].join(''))
                // document.getElementById("propic").src = "/image/icon/user/user.png";
                alert("성공");
            }
        },
        error : function(){
            console.log(datas);
        }
    });
}

// 인풋 이미지 삭제
function resetFileForm(elFileForm){
    var orgParent = elFileForm.parentNode;
    var orgNext = elFileForm.nextSibling;

    var tmp = document.createElement('form');
    tmp.appendChild(elFileForm);

    tmp.reset();

    orgParent.insertBefore(elFileForm,orgNext);

}

function submypage(){
    if(mynick == false){
        alert("사용할 수 없는 닉네임입니다. 다시 입력해주세요")
        return;
    }
}

var acc = document.getElementsByClassName("accordion");
var i;

for (i = 0; i < acc.length; i++) {
    acc[i].addEventListener("click", function () {
        this.classList.toggle("active");
        var panel = this.nextElementSibling;
        if (panel.style.maxHeight) {
            panel.style.maxHeight = null;
        } else {
            panel.style.maxHeight = panel.scrollHeight + "px";
        }
    });
}