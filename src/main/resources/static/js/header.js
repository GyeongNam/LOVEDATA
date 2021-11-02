function gologin(){
    alert("로그인 해 주세요!");
    location.href="/login";
}

$(function(){
    // 스크롤 시 header fade-in
    $(document).on('scroll', function(){
        if($(window).scrollTop() > 200){
            $("#header").removeClass("deactive");
            $("#header").addClass("active");
        }else{
            $("#header").removeClass("active");
            $("#header").addClass("deactive");
        }
    })

});
