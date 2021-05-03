function onClickLike(like) {
    if (like.getAttribute("src") === "/image/icon/like/love_black.png") {
        like.src = "/image/icon/like/love_color.png";
    } else {
        like.src = "/image/icon/like/love_black.png";
    }
}