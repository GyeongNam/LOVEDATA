


$("#modal_opne_btn").click(function(){
 	var phone_f = $("#selectphone").val();
	var phone_s = $("#str_phone02").val();
	var phone_l = $("#str_phone03").val();
    $("#phone_num").text(phone_f + "-" + phone_s + "-" + phone_l);
    $("#modal").fadeIn();
    $(".modal_content").fadeIn();
});

 $("#modal_close_btn").click(function(){
    $("#modal").fadeOut();
    $(".modal_content").fadeOut();
});
﻿
