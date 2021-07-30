$(function () {

    $('nav ul li a').click(function () {


        var href = $(this).attr('href')


        var top = $(href).offset().top
        console.log(href, top)

        $('html,body').animate({
            'scrollTop': top
        })

    })
    var h=0
    $(window).scroll(function () {

        var scrT = parseInt($(this).scrollTop())

        $('h3').text(scrT)
        h=$(window).height()

        $(window).resize(function(){
            h=$(window).height()

        })
        console.log(h)

        // if(scrT>=0 && scrT<h*1){
        //     $('nav ul li a').removeClass('on')

        //     $('nav ul li').eq(0).find('a').addClass('on')
        // }else if(scrT>h*1 && scrT<h*2 ){
        //     $('nav ul li a').removeClass('on')

        //     $('nav ul li').eq(1).find('a').addClass('on')
        // }else if(scrT>=h*2&& scrT<h* 3 ){
        //     $('nav ul li a').removeClass('on')

        //     $('nav ul li').eq(2).find('a').addClass('on')
        // }else if(scrT>=h*3 && scrT<h*4 ){
        //     $('nav ul li a').removeClass('on')

        //     $('nav ul li').eq(3).find('a').addClass('on')
        // }

        // for(var i=0;i<=4;i++){
        //     if(scrT>=h*i && scrT<h*(i+1) ){
        //         $('nav ul li a').removeClass('on')

        //         $('nav ul li').eq(i).find('a').addClass('on')
        //     }
        // }

        $('section').each(function(i){
            var secTop=$(this).offset().top
            console.log(i,secTop)

            if(secTop<=scrT){
                $('nav ul li a').removeClass('on')
                $('nav ul li').eq(i).find('a').addClass('on')

                $('article p').removeClass('on')
                $(this).find('article p').addClass('on')
                $('article h2').removeClass('on')
                $(this).find('article h2').addClass('on')

            }
        })



    })//scroll



    $('section').mousewheel(function (e, delta) {

        if (delta > 0) {

            var prev=  $(this).prev().offset().top
            $('html,body').animate({
                'scrollTop': prev
            })

        } else if (delta < 0) {

            var next=  $(this).next().offset().top
            $('html,body').animate({
                'scrollTop': next
            })
        }

    })//mousewheel


})