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
$(document).ready(function() {
    var is_page = getCookie("sc_page");
    console.log("맨처음 쿠키"+is_page);
    var target_imgs = $("#imgDisplay");
    console.log(target_imgs)
    var qu_pages = $("#qu_pages").val();
    var qu_pagess = $("#qu_pagess").val();
    for(var i=1; i<=parseInt(qu_pagess); i++) {
        var item = document.getElementById(i);
        item.style.display="none";
    }
    var is_page = getCookie("sc_page");
    if(is_page==null){
        pages_open(1);
    }else {
        pages_open(is_page);
    }


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
