// 모달 열기
$("#modal_opne_btn").click(function(){
 	var phone_f = $("#selectphone").val();
	var phone_s = $("#str_phone02").val();
	var phone_l = $("#str_phone03").val();
    $("#phone_num").text(phone_f + "-" + phone_s + "-" + phone_l);
    $("#modal").fadeIn();
    $(".modal_content").fadeIn();
});
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
    }
    else{
      s_relult1.text("10자리 ~ 16자리 이내로 입력해주세요.");
    }
  }
  else if(pw.search(/\s/) != -1){
    s_relult1.text("비밀번호는 공백 없이 입력해주세요.");
  }
  else if(num < 0 || (eng < 0 && spe < 0) ){
    s_relult1.text("영문과 숫자, 특수문자를 혼합하여 입력해주세요.");

  }
  else {
    s_relult1.css("color", "green")
    s_relult1.text("사용가능한 비밀번호 입니다.");
  }
}
// 비밀번호 확//
function passwordcheck(){
  var password1 = $('#pwd1').val();
  var password2 = $('#pwd2').val();
  var s_relult2 = $('#pwd_check');
  if (password1 == password2) {
    if (password2 == 0) {
      s_relult2.text("");
    }
    else {
      s_relult2.css("color", "green")
      s_relult2.text('비밀번호가 일치합니다.');
    }
  }
  else {
    s_relult2.css("color", "red")
    s_relult2.text('비밀번호가 일치하지 않습니다.');

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
 $(".checkbox11").change(function(){
   if($(".checkbox11").is(":checked")){
     $(".checkbox12").val("yes");
   } else {
     $(".checkbox12").val("no");
   }
 });
// email 중복확인

var mail1_ck1 = 0;
var mail1_ck2 = 0;

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });

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
        s_relult.css("color", "red")
        s_relult.text("사용 중인 이메일입니다!");
      }
      else {
        s_relult.css("color", "green")
        s_relult.text("사용 가능한 아이디입니다.");
      }
    },
    error : function(){
      console.log(data);
    }
  });
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
        s_relult.css("color", "red")
        s_relult.text("사용 중인 닉네임입니다!");
      }
      else {
        s_relult.css("color", "green")
        s_relult.text("사용 가능한 닉네임입니다.");
      }
    },
    error : function(){
      console.log(datas);
    }
  });
}
