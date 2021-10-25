var mainmail = false;
var mainpwd = false;
var mainrepwd = false;
var mainnick = false;
var mainname = false;
var mainphone = false;
var nums = 0;


// 모달 닫기
$("#modal_close_btn").click(function(){
  $("#modal").fadeOut();
  $(".modal_content").fadeOut();
});

// 비밀번호 유효성
function chkpw(){
  var pw = $("#pwd1").val();
  var num = pw.search(/[0-9]/g);
  var eng = pw.search(/[A-z]/ig);
  var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
  var s_relult1 = $('#pwd_rule');
  s_relult1.css("color", "red")
  if(pw.length < 10 || pw.length > 16){
    if(pw.length == 0){
      s_relult1.text("영문, 숫자, 특수문자를 포함한 10자리 이상 입력하세요.");
      mainpwd = false;
    }
    else{
      s_relult1.text("10자리 ~ 16자리 이내로 입력해주세요.");
      mainpwd = false;
    }
  }
  else if(pw.search(/\s/) != -1){
    s_relult1.text("비밀번호는 공백 없이 입력해주세요.");
    mainpwd = false;
  }
  else if(num < 0 || (eng < 0 && spe < 0) ){
    s_relult1.text("영문과 숫자, 특수문자를 혼합하여 입력해주세요.");
    mainpwd = false;

  }
  else {
    s_relult1.css("color", "green");
    s_relult1.text("사용가능한 비밀번호 입니다.");
    mainpwd = true;
  }
}

// 비밀번호 확인
function passwordcheck(){
  var password1 = $('#pwd1').val();
  var password2 = $('#pwd2').val();
  var s_relult2 = $('#pwd_check');
  if (password1 == password2) {
    if (password2 == 0) {
      s_relult2.text("");
      mainrepwd = false;
    }
    else {
      s_relult2.css("color", "green");
      s_relult2.text('비밀번호가 일치합니다.');
      mainrepwd = true;
    }
  }
  else {
    s_relult2.css("color", "red");
    s_relult2.text('비밀번호가 일치하지 않습니다.');
    mainrepwd = false;
  }
}

// 이메일 선택
$('#selectEmail').change(function(){
  $("#selectEmail option:selected").each(function (){
    if($(this).val()== '1'){ //직접입력일 경우
      $("#str_email02").val('');//값 초기화
      $("#str_email02").attr("readonly",false); //활성화
    }
    else{    //직접입력이 아닐경우
      $("#str_email02").attr("readonly",true); //비활성화
      $("#str_email02").val($(this).text()); //선택값 입력
    }
  });
});

// 핸드폰 숫자만 입력가능
$(".phone_nums").keydown( function(){
  $(this).val( $(this).val().replace(/[^0-9]/gi,"")
  );
});

// 이메일 수신동의 체크박스 체크
$(".checkbox12").val(false);
$(".checkbox11").change(function(){
  if($(".checkbox11").is(":checked")){
    $(".checkbox12").val(true);
  } else {
    $(".checkbox12").val(false);
  }
});

// ajax token
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });

// email 중복확인
function email_check(){
  var mail1 = $('#str_email01').val();
  var mail2 = $('#str_email02').val();
  var mail = mail1+"@"+mail2;
  var s_relult = $('#email_check');

  var postdata = {"mail" : mail }

  $.ajax({
    url: " /email_check",
    dataType: 'json',
    contentType: "application/json; charset=UTF-8",
    data: JSON.stringify(postdata),
    type: "POST",
    success:function(data){
      var datas = data;
      console.log(datas.msg);
      if(datas.msg =="0"){
        s_relult.css("color", "red");
        s_relult.text("사용 중인 이메일입니다!");
        mainmail = false;
      }
      else {
        if(mail1.search(/\s/) != -1 || mail2.search(/\s/) != -1 || mail1.length == 0 || mail2.length == 0 ){
          s_relult.css("color", "red");
          s_relult.text("정확히 메일을 입력해주세요.");
          mainmail = false;
        }
        else {
          s_relult.css("color", "green");
          s_relult.text("사용 가능한 아이디입니다.");
          mainmail = true;
        }
      }
    },
    error : function(){
      console.log(data);
    }
  });
}
// 이름 공백 확인
function name_check(){
  var userName = $('#userName').val();
  var userchek = $('#userName_check');
  if(userName.search(/\s/) != -1 || userName.length == 0){
    userchek.css("color", "red");
    userchek.text("이름에 공백이 포함되었습니다.!");
    mainname = false;
  }
  else {
    userchek.css("color", "green");
    userchek.text("사용 가능한 이름입니다.");
    mainname = true;
  }
}


// 닉네임 중복확인
function nick_check(){
  var nickname = $('#nickname');
  var s_relult = $('#nickname_check');

  var postdata = {"nickname" : nickname.val() }

  $.ajax({
    url: " /nick_check",
    dataType: 'json',
    contentType: "application/json; charset=UTF-8",
    data: JSON.stringify(postdata),
    type: "POST",
    success:function(data){
      var datas = data.msg;
      console.log(datas);
      if(datas=="0"){
        s_relult.css("color", "red");
        s_relult.text("사용 중인 닉네임입니다!");
        mainnick = false;
      }
      else {
        if(nickname.val().search(/\s/) != -1 || nickname.val().length == 0){
          s_relult.css("color", "red");
          s_relult.text("닉네임을 확인해 주세요!");
          mainnick = false;
        }
        else {
          s_relult.css("color", "green");
          s_relult.text("사용 가능한 닉네임입니다.");
          mainnick = true;
        }
      }
    },
    error : function(){
      console.log(datas);
    }
  });
}
// 인증번호 보내기
function SMSsned(){

  var phone1 = $('#selectphone').val();
  var phone2 = $('#str_phone02').val();
  var phone3 = $('#str_phone03').val();
  var regPhone = /(01[0|1|6|9|7])[-](\d{3}|\d{4})[-](\d{4}$)/g;

  var postdata = { "phones" : phone1 + "-" + phone2 + "-" + phone3 };

  if(!regPhone.test(phone1 + "-" + phone2 + "-" + phone3)){
    alert("휴대폰 번호를 확인하세요");
  }else {
    $.ajax({
      url: "/sendsms",
      dataType: 'json',
      contentType: "application/json; charset=UTF-8",
      data: JSON.stringify(postdata),
      type: "POST",
      success:function(data){
        console.log(data);
        nums = data.num;

        var phone_f = $("#selectphone").val();
        var phone_s = $("#str_phone02").val();
        var phone_l = $("#str_phone03").val();
        $("#phone_num").text(phone_f + "-" + phone_s + "-" + phone_l);
        $("#modal").fadeIn();
        $(".modal_content").fadeIn();
      },
      error : function(){
        console.log(data);
      }
    });
  }
}
// 인증번호 확인하기
function rnum_check(){
  security = $('#security').val();
  retextmodal = $('#phone_numcheck');
  retext = $('#phone_check');

  if (nums == security) {
    if (security == 0) {
      retextmodal.text("인증번호를 입력하세요.");
      mainphone = false;
    }
    else {
      retextmodal.css("color", "green");
      retextmodal.text('인증번호가 일치합니다.');
      retext.css("color", "green");
      retext.text('인증된 번호입니다.');
      mainphone = true;
    }
  }
  else {
    retextmodal.css("color", "red");
    retextmodal.text('인증번호가 일치하지 않습니다.');
    retext.css("color", "red");
    retext.text('인증되지 않은 번호입니다.');
    mainphone = false;
  }
}

//최종 확인
function signup_check(){
   if(mainmail == false){
      alert("메일을 확인하세요.");
      return false;
   }
   else if(mainpwd == false){
     alert("비밀번호를 확인하세요");
     return false;
   }
   else if(mainrepwd == false){
     alert("비밀번호 확인를 확인하세요");
     return false;
   }
   else if(mainnick == false){
     alert("닉네임을 확인하세요");
     return false;
   }
   else if(mainname == false){
     alert("이름을 확인하세요");
     return false;
   }
   else if(mainphone == false){
     alert("휴대폰을 인증하세요");
     return false;
   }
}
function Ssignup_check(){
  if(mainphone == false){
    alert("휴대폰을 인증하세요");
    return false;
  }
}

// 동의 모두선택 / 해제
const agreeChkAll = document.querySelector('input[name=agree_all]');
agreeChkAll.addEventListener('change', (e) => {
  let agreeChk = document.querySelectorAll('input[name=agree]');
  for(let i = 0; i < agreeChk.length; i++){
    agreeChk[i].checked = e.target.checked;
  }
});
const modal = document.getElementById("modal2");
function modal2(){
  $('.abc:checked').length == $('.abc').length
    var a = $("input[type='checkbox'].abc");
    if(a.length == a.filter(":checked").length){
      modal.style.display = "none"
    }
    else {
      alert('약관을 모두 체크해주세요.');
    }
}