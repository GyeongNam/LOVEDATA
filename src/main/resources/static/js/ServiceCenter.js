//내정보 탭 변경
function MenuTab(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
// document.getElementById("defaultOpen").click();

//정책 탭 변경
function ServiceCenterTab(evt, tabName) {
    var k, Ptabcontent, Ptablinks;
    Ptabcontent = document.getElementsByClassName("Ptabcontent");
    for (k = 0; k < Ptabcontent.length; k++) {
        Ptabcontent[k].style.display = "none";
    }
    Ptablinks = document.getElementsByClassName("Ptablinks");
    for (k = 0; k < Ptablinks.length; k++) {
        Ptablinks[k].className = Ptablinks[k].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
// document.getElementById("FirstTabOpen").click();

function gologin(){
    alert("로그인 해 주세요!");
    location.href="/login";
}

function Qsearch(){
    var select =$('#Notice_select').val();
    var text =$('#keyword').val();

    var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
    if( text == '' ||text == null ){
        alert( '값을 입력해주세요' );
    }
    else {
        if(special_pattern.test(text) == true) {
            alert("특수문자는 안됩니다.")
        } else {
            return location.href="/ServiceCenter/Questions/search/"+select+"/"+text+"/1";
        }
    }
}

function Usearch(){
    var select =$('#Notice_select').val();
    var text =$('#keyword').val();

    var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
    if( text == '' ||text == null ){
        alert( '값을 입력해주세요' );
    }
    else {
        if(special_pattern.test(text) == true) {
            alert("특수문자는 안됩니다.")
        } else {
            return location.href="/admin/user/search/"+select+"/"+text+"/1";
        }
    }
}

function SMSUsearch(){
    var select =$('#Notice_select').val();
    var text =$('#keyword').val();

    var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
    if( text == '' ||text == null ){
        alert( '값을 입력해주세요' );
    }
    else {
        if(special_pattern.test(text) == true) {
            alert("특수문자는 안됩니다.")
        } else {
            return location.href="/admin/SendMessage/search/"+select+"/"+text+"/1";
        }
    }
}

function Nsearch(){
    var select =$('#Notice_select').val();
    var text =$('#keyword').val();

    var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
    if( text == '' ||text == null ){
        alert( '값을 입력해주세요' );
    }
    else {
        if(special_pattern.test(text) == true) {
            alert("특수문자는 안됩니다.")
        } else {
            return location.href="/ServiceCenter/Notice/search/"+select+"/"+text+"/1";
        }
    }
}

function event_search(){
    var select =$('#Notice_select').val();
    var text =$('#keyword').val();

    var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
    if( text == '' ||text == null ){
        alert( '값을 입력해주세요' );
    }
    else {
        if(special_pattern.test(text) == true) {
            alert("특수문자는 안됩니다.")
        } else {
            return location.href="/ServiceCenter/Event/search/"+select+"/"+text+"/1";
        }
    }
}

function admin_Nsearch(){
    var select =$('#Notice_select').val();
    var text =$('#keyword').val();

    var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
    if( text == '' ||text == null ){
        alert( '값을 입력해주세요' );
    }
    else {
        if(special_pattern.test(text) == true) {
            alert("특수문자는 안됩니다.")
        } else {
            return location.href="/admin/notice/search/"+select+"/"+text+"/1";
        }
    }
}
var setCookie = function(name, value, exp) {
    var date = new Date();
    date.setTime(date.getTime() + exp*24*60*60*1000);
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
};
var getCookie = function(name) {
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value? value[2] : null;
};


var pages = null;
function pages_open(pagess){
    var is_page = getCookie("sc_page");
    if(is_page==null){
        console.log("쿠키없음");
        setCookie("sc_page", "1", 1);
        pages=1;
    }else {
        console.log("쿠키있음: "+ is_page);
        pages = parseInt(is_page);
        pagess= is_page;
    }

    console.log("pages : "+ pagess);
    var qu_pages = $("#qu_pages").val();
    var qu_pagess = $("#qu_pagess").val();
    for(var i=1; i<=parseInt(qu_pagess); i++) {
        var item = document.getElementById(i);
        item.style.display="none";
    }
    if(pagess==1){
        for(var i=1; i<=10; i++) {
            var item = document.getElementById(i);
            item.style.display="block";
        }
    }
    if(pagess==qu_pages){
        console.log("끝pagess: "+pagess*10);
        console.log("끝qu_pagess: "+qu_pagess);
        for(var i=(pagess-1)*10+1; i<=parseInt(qu_pagess); i++) {
            var item = document.getElementById(i);
            item.style.display="block";
        }
    }
    if(pagess>1 && pagess<qu_pages){
        console.log("중간");
        for(var i=(pagess-1)*10+1; i<=(pagess-1)*10+10; i++) {
            var item = document.getElementById(i);
            item.style.display="block";
        }
    }
}
function plupage(){
    var qu_pages = $("#qu_pages").val();
    pages = pages+1;

    if(pages<=qu_pages){
        setCookie("sc_page", pages, 1);
        pages_open(pages)
    }
    else {
        pages = qu_pages;
        setCookie("sc_page", pages, 1);
        alert("최대페이지 입니다.")
    }
}

function subpage(){
    var qu_pages = $("#qu_pages").val();
    pages = pages-1;
    console.log("쿠키확인"+pages);
    if(pages>=1){
        setCookie("sc_page", pages, 1);
        pages_open(pages)
    }
    else {
        pages = 1;
        setCookie("sc_page", pages, 1);
        alert("최소페이지 입니다.")
    }
}

function ad_pages_open(pagess){
    var is_page = getCookie("ad_page");
    if(is_page==null){
        console.log("쿠키없음");
        setCookie("ad_page", "1", 1);
        pages=1;
    }else {
        console.log("쿠키있음: "+ is_page);
        pages = parseInt(is_page);
        pagess= is_page;
    }

    console.log("pages : "+ pagess);
    var qu_pages = $("#qu_pages").val();
    var qu_pagess = $("#qu_pagess").val();
    for(var i=1; i<=parseInt(qu_pagess); i++) {
        var item = document.getElementById(i);
        item.style.display="none";
    }
    if(pagess==1){
        for(var i=1; i<=10; i++) {
            var item = document.getElementById(i);
            item.style.display="block";
        }
    }
    if(pagess==qu_pages){
        console.log("끝pagess: "+pagess*10);
        console.log("끝qu_pagess: "+qu_pagess);
        for(var i=(pagess-1)*10+1; i<=parseInt(qu_pagess); i++) {
            var item = document.getElementById(i);
            item.style.display="block";
        }
    }
    if(pagess>1 && pagess<qu_pages){
        console.log("중간");
        for(var i=(pagess-1)*10+1; i<=(pagess-1)*10+10; i++) {
            var item = document.getElementById(i);
            item.style.display="block";
        }
    }
}
function ad_plupage(){
    var qu_pages = $("#qu_pages").val();
    pages = pages+1;

    if(pages<=qu_pages){
        setCookie("ad_page", pages, 1);
        ad_pages_open(pages)
    }
    else {
        pages = qu_pages;
        setCookie("ad_page", pages, 1);
        alert("최대페이지 입니다.")
    }
}

function ad_subpage(){
    var qu_pages = $("#qu_pages").val();
    pages = pages-1;
    console.log("쿠키확인"+pages);
    if(pages>=1){
        setCookie("ad_page", pages, 1);
        ad_pages_open(pages)
    }
    else {
        pages = 1;
        setCookie("ad_page", pages, 1);
        alert("최소페이지 입니다.")
    }
}

function password_ck(){
    // ajax token
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });

    var password = $('#password_ck').val();
    var s_relult = $('#s_relult');
    var postdata = {"password" : password }

    $.ajax({
        url: "/password_ckd",
        dataType: 'json',
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(postdata),
        type: "POST",
        success:function(data){
            var datas = data;
            console.log(datas.msg);
            if(datas.msg =="0"){
                s_relult.css("color", "red");
                s_relult.text("비밀번호 확인: 비밀번호가 다릅니다.");
                $("#userOut").prop('disabled', true);
            }
            if(datas.msg =="1"){
                s_relult.css("color", "green");
                s_relult.text("비밀번호 확인: 비밀번호가 일치합니다.");
                alert("회원탈퇴를 원하시면 아래 '회원탈퇴' 버튼을 눌러주세요.");
                $("#userOut").prop('disabled', false);
            }
        },
        error : function(){
        }
    });
}

$(document).ready(function() {
    //회원탈퇴 비활성화
    $("#userOut").prop('disabled', true);

    var is_page = getCookie("sc_page");

    var ad_page = getCookie("ad_page");

    var qu_pagess = $("#qu_pagess").val();
    for(var i=1; i<=parseInt(qu_pagess); i++) {
        var item = document.getElementById(i);
        item.style.display="none";
    }

    if(is_page==null){
        pages_open(1);
    }else {
        pages_open(is_page);
    }

    if(ad_page==null){
        ad_pages_open(1);
    }else {
        ad_pages_open(ad_page);
    }

    var target_imgs = $("#imgDisplay");
    console.log(target_imgs)

    target_imgs.load(function(){
        var width = $(this).outerWidth();
        if(width >= 10) $(this).css("width", "10");
    });

    for(var i=0 ; target_imgs && i<target_imgs.length ; i++)
    {
        var width = target_imgs.eq(i).outerWidth();
        if(width >= 10) target_imgs.eq(i).css("width", "10");
    }
});

function noti_de(){
    if (confirm("정말 삭제하시겠습니까??") == true){    //확인
        document.getElementById('nform').submit();
    }else{   //취소
        return false;
    }
}

function lovedata_delete(){
    if (confirm("정말 탈퇴하시겠습니까??") == true){    //확인
        document.getElementById('lovedata_delete').submit();
    }else{   //취소
        return false;
    }
}