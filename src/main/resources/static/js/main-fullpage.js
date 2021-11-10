var slideIndex = 1;
showSlides(slideIndex);

function plusSlides(n) {
    showSlides(slideIndex += n);
}

function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    var dots = document.getElementsByClassName("dot");
    if (n > slides.length) {slideIndex = 1}
    if (n < 1) {slideIndex = slides.length}
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex-1].style.display = "block";
    dots[slideIndex-1].className += " active";
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
