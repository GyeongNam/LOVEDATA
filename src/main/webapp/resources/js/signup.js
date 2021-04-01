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
﻿// 비밀번호 유효성
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
//


  $(".phone_nums").keydown( function(){
      $(this).val( $(this).val().replace(/[^0-9]/gi,"")
    );
  });
