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
document.getElementById("defaultOpen").click();

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
document.getElementById("FirstTabOpen").click();

function gologin(){
    alert("로그인 해 주세요!");
    location.href="/login";
}

function Qsearch(){
    var select =$('#Notice_select').val();
    var text =$('#keyword').val();

    var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

    if(special_pattern.test(text) == true) {
        alert("특수문자는 안됩니다.")
    } else {
        return location.href="/ServiceCenter/Questions/search/"+select+"/"+text+"/1";
    }
}

$(document).ready(function() {
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

