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

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });

$('#sendMail').click(function (){
	//input 값
	var email1 = $("#email1").val();
	console.log($("#email1").val());
	var email2 = $("#email2").val();
	console.log($("#email2").val());

	var postdata = {"address" : email1, "domain" : email2 }

	// if(!email1){
	if(!email1){
		alert("값을 입력해주세요");
		return false;
	}
	if(!email2){
		alert("도메인을 입력해주세요");
		return false;
	}else{
		$.ajax({
			url: " /mail",
			dataType: 'json',
			contentType: "application/json; charset=UTF-8",
			data: JSON.stringify(postdata),
			type: "POST",
			success:function(data){
				var datas = data;
				console.log(datas.msg);
				if(datas.msg == "1"){
					alert("이메일이 전송되었습니다.");
				}
				else{
					alert("등록되지 않은 이메일입니다.");
				}
			},
			error : function(){
				console.log(data);
			}
		});

		return true;
	}

});

var nums = 0;
var num_check = 0;
// 인증번호 보내기
function SMSsned(){
	var phone1 = $('#selectphone').val();
	var phone2 = $('#str_phone02').val();
	var phone3 = $('#str_phone03').val();
	var postdata = { "phones" : phone1 + "-" + phone2 + "-" + phone3 };

	$.ajax({
		url: "/sendsms",
		dataType: 'json',
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(postdata),
		type: "POST",
		success:function(data){
			console.log(data);
			nums = data.num;
		},
		error : function(){
			console.log(data);
		}
	});
}
// 인증번호 확인하기
function rnum_check(){
	security = $('#security').val();
	retext = $('#phone_check');

	if (nums == security) {
		if (security == 0) {
			alert("인증번호를 전송하세요.");
			num_check = 0;
		}
		else {
			retext.css("color", "green");
			retext.text('인증된 번호입니다.');
			num_check = 1;
		}
	}
	else {
		retext.css("color", "red");
		retext.text('인증되지 않은 번호입니다.');
		num_check = 0;
	}
}

//아이디찾기 클릭이벤트
function idfind(){
	if (num_check == 1){

		var phone1 = $('#selectphone').val();
		var phone2 = $('#str_phone02').val();
		var phone3 = $('#str_phone03').val();
		var postdata = { "phones" : phone1 + phone2 + phone3 };

		$.ajax({
			url: "/idfind",
			dataType: 'json',
			contentType: "application/json; charset=UTF-8",
			data: JSON.stringify(postdata),
			type: "POST",
			success:function(data){
				// console.log(data);
				$("#find_id_list").append($("<li>고객님의 아이디는</li>"));
				$.each(data, function(index, item){
					console.log(item);

					$("#nonfind_id").hide();
					$("#find_id_list").append($("<li>"+item+"</li>"));
					// document.body..appendChild(newDiv);
				});
				$("#find_id_list").append($("<li>입니다.</li>"));
			},
			error : function(){
				console.log(data);
			}
		});
	}
	else{
		alert("인증번호를 확인해주세요.");
	}
}