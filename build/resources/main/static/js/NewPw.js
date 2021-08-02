//새 비밀번호 양식 확인
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

// 수정 된 비밀번호 확인
function repasswordcheck(){
    var pwd1 = $('#pwd1').val();
    var pwd2 = $('#NewPasswordre').val();
    var p_chack = $('#password_check');
    if (pwd1 == pwd2) {
        if (pwd2 == 0) {
            p_chack.text("");
            mainrepwd = false;
        }
        else {
            p_chack.css("color", "green");
            p_chack.text('새로운 비밀번호가 일치합니다.');
            mainrepwd = true;
        }
    }
    else {
        p_chack.css("color", "red");
        p_chack.text('새로운 비밀번호가 일치하지 않습니다.');
        mainrepwd = false;
    }
}

