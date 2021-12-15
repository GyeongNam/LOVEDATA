// var slideIndex = 1;
// showSlides(slideIndex);
//
// function plusSlides(n) {
//     showSlides(slideIndex += n);
// }
//
// function currentSlide(n) {
//     showSlides(slideIndex = n);
// }
//
// function showSlides(n) {
//     var i;
//     var slides = document.getElementsByClassName("mySlides");
//     var dots = document.getElementsByClassName("dot");
//     if (n > slides.length) {slideIndex = 1}
//     if (n < 1) {slideIndex = slides.length}
//     for (i = 0; i < slides.length; i++) {
//         slides[i].style.display = "none";
//     }
//     for (i = 0; i < dots.length; i++) {
//         dots[i].className = dots[i].className.replace(" active", "");
//     }
//     slides[slideIndex-1].style.display = "block";
//     dots[slideIndex-1].className += " active";
// }


$(document).ready(function () {
    $(".mySlideDiv").not(".active").hide(); //화면 로딩 후 첫번째 div를 제외한 나머지 숨김

    setInterval(nextSlide, 4000); //4초(4000)마다 다음 슬라이드로 넘어감
});

//이전 슬라이드
function prevSlide() {
    $(".mySlideDiv").hide(); //모든 div 숨김
    var allSlide = $(".mySlideDiv"); //모든 div 객체를 변수에 저장
    var currentIndex = 0; //현재 나타난 슬라이드의 인덱스 변수

    //반복문으로 현재 active클래스를 가진 div를 찾아 index 저장
    $(".mySlideDiv").each(function(index,item){
        if($(this).hasClass("active")) {
            currentIndex = index;
        }

    });

    //새롭게 나타낼 div의 index
    var newIndex = 0;

    if(currentIndex <= 0) {
        //현재 슬라이드의 index가 0인 경우 마지막 슬라이드로 보냄(무한반복)
        newIndex = allSlide.length-1;
    } else {
        //현재 슬라이드의 index에서 한 칸 만큼 뒤로 간 index 지정
        newIndex = currentIndex-1;
    }

    //모든 div에서 active 클래스 제거
    $(".mySlideDiv").removeClass("active");

    //새롭게 지정한 index번째 슬라이드에 active 클래스 부여 후 show()
    $(".mySlideDiv").eq(newIndex).addClass("active");
    $(".mySlideDiv").eq(newIndex).show();

}

//다음 슬라이드
function nextSlide() {
    $(".mySlideDiv").hide();
    var allSlide = $(".mySlideDiv");
    var currentIndex = 0;

    $(".mySlideDiv").each(function(index,item){
        if($(this).hasClass("active")) {
            currentIndex = index;
        }

    });

    var newIndex = 0;

    if(currentIndex >= allSlide.length-1) {
        //현재 슬라이드 index가 마지막 순서면 0번째로 보냄(무한반복)
        newIndex = 0;
    } else {
        //현재 슬라이드의 index에서 한 칸 만큼 앞으로 간 index 지정
        newIndex = currentIndex+1;
    }

    $(".mySlideDiv").removeClass("active");
    $(".mySlideDiv").eq(newIndex).addClass("active");
    $(".mySlideDiv").eq(newIndex).show();

}


// 상단이동기능
$(document).ready(function() {
    // 위로가기 기능
    $('#gotop').click(function () {
        $('html, body').animate({
            scrollTop: 0
        }, 500, function () {
            $('#gotop').fadeOut();
        });
    });
});


$(window).scroll(function() {
    //웹 브라우저의 스크롤 바의 상단 위치 값
    var posY = $(window).scrollTop(); //스크롤 탑의 꼭대기는 0이다
    // console.log(posY);
    // 위로가기 보이고 숨기기 처리
    var gotopY = 700; //intro 페이지의 세로 길이는 1080px이다
    if (posY > gotopY) {
        $('#gotop').fadeIn(); //스크롤이 1080px보다 많이 내려오면 gotop버튼을 보여준다
    } else {
        $('#gotop').fadeOut(); //스크롤이 1080px보다 적은 값이면 gotop을 숨긴다
    }
});

var slideIndex2 = 0; //slide index

// HTML 로드가 끝난 후 동작
window.onload=function(){
    showSlides2(slideIndex2);

    // Auto Move Slide
    // var sec = 3000;
    // setInterval(function(){
    //     slideIndex2++;
    //     showSlides2(slideIndex2);
    //
    // }, sec);
}


// Next/previous controls
function moveSlides2(n) {
    slideIndex2 = slideIndex2 + n
    showSlides2(slideIndex2);
}

// Thumbnail image controls
function currentSlide2(n) {
    slideIndex2 = n;
    showSlides(slideIndex2);
}

function showSlides2(n) {

    var slides = document.getElementsByClassName("mySlides2");
    var dots = document.getElementsByClassName("dot2");
    var size = slides.length;

    if ((n+1) > size) {
        slideIndex2 = 0; n = 0;
    }else if (n < 0) {
        slideIndex2 = (size-1);
        n = (size-1);
    }

    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }

    slides[n].style.display = "block";
    dots[n].className += " active";
}
