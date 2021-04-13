/**
 * 
 */
function find_id_popup() {
	window.open("<%= request.getContextPath() %>/user/find-id.jsp",
		"FIndUserID",
		"width=400,height=400,left=600");
}

//이메일 선택
$('.selectbox').change(function() {
	$(".selectbox option:selected").each(function() {
		if ($(this).val() == '1') { //직접입력일 경우
			$("#email2").val(''); //값 초기화
			$("#email2").attr("readonly", false); //활성화
		} else { //직접입력이 아닐경우 
		    $("#email2").attr("readonly", true); //비활성화
			$("#email2").val($(this).text()); //선택값 입력
		}
	});
});

$('#sendMail').click(function (){
	//input 값
	var email1 = $("#email1").val();
	console.log($("#email1").val());
	var email2 = $("#email2").val();
	console.log($("#email2").val());
	// if(!email1){
	if(!email1){
		alert("값을 입력해주세요");
		return false;
	}
	if(!email2){
		alert("도메인을 입력해주세요");
		return false;
	}else{
		alert("이메일이 전송되었습니다.");
	}
	return  true;
});

$('#mobilenumberauthbutton').click(function(){
	if('.phone-first.value =="empty"'){
		alert("휴대폰 앞 번호를 선택하세요.");
		return false;
	}
	else{
		alert("입력한 이메일로 인증번호를 전송하였습니다.");
	}
	
});
