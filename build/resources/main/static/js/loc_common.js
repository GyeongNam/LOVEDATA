let tagList = [];
let index;
// var lastLikeLocId;
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

function getTagList() {
    return tagList;
}

function setTagList(value) {
    tagList = value;
}

function addTag(tag) {
    // let navbar = document.getElementById("tag-navbar-collapse");
    let list = document.getElementById('tag_list').children;
    let isReachMaxNumber = true;
    let isDuplicated = false;
    const MAX_TAG_LIMIT = 7;

    if (tagList.length >= MAX_TAG_LIMIT) {
        isReachMaxNumber = true;
    } else {
        isReachMaxNumber = false;
    }

    if (isReachMaxNumber) {
        alert("해시태그는 최대 7개까지 추가할 수 있습니다.");
        return;
    }

    for (let i = 0; i < tagList.length; i++) {
        if (tagList[i] === tag.value) {
            isDuplicated = true;
            break;
        }
    }

    if (isDuplicated) {
        alert("중복된 해시태그가 있습니다.");
        return;
    }

    for (let i = 0; i < list.length; i++) {
        if (list.item(i).children.item(0).getAttribute("value") === tag.value) {
            // list.item(i).children.item(0).setAttribute("value", tag.value);
            // list.item(i).children.item(0).innerHTML = tag.value;
            list.item(i).style.display = "inline-block";
            isReachMaxNumber = false;
            tagList.push(tag.value);
            break;
        }
    }
    console.log(tagList);
}

function removeTag(tag) {
    let tagValue = tag.parentElement.firstElementChild.getAttribute("value");
    let tagIndex = tagList.indexOf(tagValue);

    tag.parentElement.style.display = "none";
    tagList.splice(tagIndex, 1);
    console.log(tagList);
}

function onClickLike(Like, user_no, type) {
    var countChangeFlag = true;
    var likeCount = null;
    var item_no = null;
    var item = null;
    var path_noparm = location.pathname;

    console.log(path_noparm);
    if (path_noparm === "/service/loc_detail/ex") {
       if (Like.src === "/image/icon/like/love_black.png") {
           Like.src = "/image/icon/like/love_color.png"
       } else {
           Like.src = "/image/icon/like/love_black.png";
       }
    }
    else if (path_noparm ===  "/service/loc_detail") {
        countChangeFlag = true;
        item = document.getElementById("loc_no");
        item_no = parseInt(item.innerText);
        likeCount = document.getElementById("likeCount");
    }
    else if (path_noparm === "/service/cor_detail") {
        countChangeFlag = true;
        item = document.getElementById("cor_no");
        item_no = parseInt(item.innerText);
        likeCount = document.getElementById("likeCount");
    }
    else {
        likeCount = Like.nextElementSibling.innerText;
        item_no = Like.nextElementSibling.nextElementSibling.innerText;
        item = Like.nextElementSibling;
    }

    var jsonData = {"item_no": item_no, "user_no" : user_no, "type" : type}
    var isClicked = false;
    var map = new Map();

    if (Like.getAttribute("src") === "/image/icon/like/love_color.png") {
        isClicked = true;
    } else {
        isClicked = false;
    }
    console.log("isClicked : " + isClicked);

    map.set("likeCount", likeCount)
    map.set("listCount", likeCount);
    map.set("item_no", item_no);
    map.set("user_no", user_no);
    map.set("jsonData", jsonData);
    map.set("isClicked", isClicked);
    map.set("like", Like);
    map.set("item", item);
    map.set("countChangeFlag", countChangeFlag);

    onLoginCheck(map);
}

function onClickLikeComment(like, cmt_index, user_no, type, cmt_no) {
    var countChangeFlag = true;
    var likeCount = document.getElementById("cmt_like_" + cmt_index);
    var dislikeCount = document.getElementById("cmt_dislike_" + cmt_index);
    var path_noparm = location.pathname;
    var dislike = document.getElementById("cmt_btn_dislike_" + cmt_index);

    countChangeFlag = true;

    var jsonData = {"cmt_no": cmt_no, "user_no" : user_no, "type" : type}
    var isClicked = false;
    var map = new Map();

    if (like.getAttribute("src") === "/image/icon/cmt_like_color.png") {
        isClicked = true;
    } else {
        isClicked = false;
    }
    console.log("isClicked : " + isClicked);

    map.set("likeCount", likeCount);
    map.set("dislikeCount", dislikeCount);
    map.set("jsonData", jsonData);
    map.set("isClicked", isClicked);
    map.set("like", like);
    map.set("dislike", dislike);
    map.set("countChangeFlag", countChangeFlag);
    map.set("type", type);

    onLoginCheck(map);
}

function onClickDislikeComment(dislike, cmt_index, user_no, type, cmt_no) {
    var countChangeFlag = true;
    var dislikeCount = document.getElementById("cmt_dislike_" + cmt_index);
    var likeCount = document.getElementById("cmt_like_" + cmt_index);
    var jsonData = {"cmt_no": cmt_no, "user_no" : user_no, "type" : type, "cmt_index" : cmt_index}
    var isClicked = false;
    var map = new Map();
    var like = document.getElementById("cmt_btn_like_" + cmt_index);

    if (dislike.getAttribute("src") === "/image/icon/cmt_dislike_color.png") {
        isClicked = true;
    } else {
        isClicked = false;
    }
    console.log("isClicked : " + isClicked);

    map.set("dislikeCount", dislikeCount)
    map.set("likeCount", likeCount);
    map.set("jsonData", jsonData);
    map.set("isClicked", isClicked);
    map.set("dislike", dislike);
    map.set("like", like);
    map.set("countChangeFlag", countChangeFlag);
    map.set("type", type);

    onLoginCheck(map);
}

function onLoginCheck(map) {
    var debugCheck = {"debug": false}

    $.ajax({
        type: "POST",
        url: "/rest/authenticationCheck",
        data: JSON.stringify(debugCheck),
        dataType: 'json',
        contentType: "application/json; charset=UTF-8",
        beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            // do something ...
            if (response == false ){
                console.log("Login Check Failed")
                alert("로그인을 해주세요")
                return false;
            }
            console.log("Login Check Success")
            console.log("is login : " + response)

            if (map.get('type') === 'cmt_like' || map.get('type') === 'cmt_dislike') {
                onClickCmtAction(map);
            } else {
                onClickLikeAction(map);
            }

            return true;
        },
        error: function (e) {
            console.log("Login Check Failed")
            alert("로그인을 해주세요")
            return false;
        }
    });
}

function onClickCmtAction(map) {
    var user_no = map.get("user_no");
    var jsonData = map.get("jsonData");
    var isClicked = map.get("isClicked");
    var type = map.get("type");
    var like = map.get("like");
    var dislike = map.get("dislike");
    var countChangeFlag = map.get("countChangeFlag");
    var likeCount = map.get("likeCount");
    var dislikeCount = map.get("dislikeCount");

    if (!isClicked) {
        $.ajax({
            type: "POST",
            url: "/rest/onCmtBtnClicked",
            data: JSON.stringify(jsonData),
            dataType: 'json',
            contentType: "application/json; charset=UTF-8",
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                // do something ...

                if (response) {
                    console.log("Like Success")

                    if (countChangeFlag) {
                        if ("cmt_like" === type) {
                            like.src = "/image/icon/cmt_like_color.png";
                            var temp = parseInt(likeCount.innerText);
                            likeCount.innerText = temp + 1;

                            let url = dislike.src.split('/');
                            let tempStr = "/";

                            for (let i = 3; i < url.length; i++) {
                                tempStr += url[i] + "/";
                            }
                            tempStr = tempStr.slice(0, tempStr.length-1);
                            if (tempStr == "/image/icon/cmt_dislike_color.png") {
                                dislike.src = "/image/icon/cmt_dislike_bw.png";
                                temp = dislikeCount.innerText;
                               dislikeCount.innerText = Number(temp) - 1;
                            }
                        } else {
                            dislike.src = "/image/icon/cmt_dislike_color.png";
                            var temp = parseInt(dislikeCount.innerText);
                            dislikeCount.innerText = temp + 1;

                            let url = like.src.split('/');
                            let tempStr = "/";

                            for (let i = 3; i < url.length; i++) {
                                tempStr += url[i] + "/";
                            }
                            tempStr = tempStr.slice(0, tempStr.length-1);
                            if (tempStr == "/image/icon/cmt_like_color.png") {
                                like.src = "/image/icon/cmt_like_bw.png";
                                temp = likeCount.innerText;
                                likeCount.innerText = Number(temp) - 1;
                            }
                        }
                    }
                } else {
                    alert("이미 좋아요를 누른 댓글입니다.");
                    console.log("Like Failed");
                }
            },
            error: function (e) {
                console.log("Like Error");
            }
        });
    } else {
        $.ajax({
            type: "POST",
            url: "/rest/onCmtBtnClickUndo",
            data: JSON.stringify(jsonData),
            dataType: 'json',
            contentType: "application/json; charset=UTF-8",
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                // do something ...

                if (response) {
                    console.log("Undo Success")

                    if (countChangeFlag) {
                        if ("cmt_like" === type) {
                            like.src = "/image/icon/cmt_like_bw.png";
                            var temp = parseInt(likeCount.innerText);
                            likeCount.innerText = temp - 1;
                        } else {
                            dislike.src = "/image/icon/cmt_dislike_bw.png";
                            var temp = parseInt(dislikeCount.innerText);
                            dislikeCount.innerText = temp - 1;
                        }
                    }

                    // lastLikeLocId = loc_no;
                } else {
                    console.log("Undo Failed");
                }

            },
            error: function (e) {
                console.log("Undo Error");
            }
        });
    }
}

function onClickLikeAction(map) {
    var likeCount = map.get("likeCount");
    var item_no = map.get("item_no");
    var user_no = map.get("user_no");
    var jsonData = map.get("jsonData");
    var isClicked = map.get("isClicked");
    var like = map.get("like");
    var item = map.get("item");
    var countChangeFlag = map.get("countChangeFlag");

    console.log("item_no : " + item_no);
    console.log("user_no : " + user_no);

    if (!isClicked) {
        $.ajax({
            type: "POST",
            url: "/rest/onClickLike",
            data: JSON.stringify(jsonData),
            dataType: 'json',
            contentType: "application/json; charset=UTF-8",
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                // do something ...

                if (response) {
                    console.log("Like Success")

                    like.src = "/image/icon/like/love_color.png";
                    if (countChangeFlag) {
                        if (location.pathname === "/service/loc_detail") {
                            var temp = parseInt(likeCount.innerText);
                            likeCount.innerText = temp + 1;
                        } else if (location.pathname === "/service/cor_detail") {
                            var temp = parseInt(likeCount.innerText);
                            likeCount.innerText = temp + 1;
                        }
                        else {
                            console.log(parseInt(likeCount));
                            console.log(parseInt(likeCount) + 1);
                            console.log(typeof (likeCount) + "\t" + likeCount);
                            item.innerText = parseInt(likeCount) + 1;
                        }
                    }
                    // lastLikeLocId = loc_no;
                } else {
                    alert("이미 좋아요를 누른 댓글입니다.");
                    console.log("Like Failed");
                }
            },
            error: function (e) {
                console.log("Like Error");
            }
        });
    } else {
        $.ajax({
            type: "POST",
            url: "/rest/onClickLikeUndo",
            data: JSON.stringify(jsonData),
            dataType: 'json',
            contentType: "application/json; charset=UTF-8",
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                // do something ...

                if (response) {
                    console.log("Undo Success")

                    like.src = "/image/icon/like/love_black.png";
                    if (countChangeFlag) {
                        if (location.pathname === "/service/loc_detail") {
                            var temp = parseInt(likeCount.innerText);
                            likeCount.innerText = temp - 1;
                        } else if (location.pathname === "/service/cor_detail") {
                            var temp = parseInt(likeCount.innerText);
                            likeCount.innerText = temp - 1;
                        } else {
                            console.log(parseInt(likeCount));
                            console.log(parseInt(likeCount) - 1);
                            console.log(typeof (likeCount) + "\t" + likeCount);
                            item.innerText = parseInt(likeCount) - 1;
                        }
                    }

                    // lastLikeLocId = loc_no;
                } else {
                    console.log("Undo Failed");
                }

            },
            error: function (e) {
                console.log("Undo Error");
            }
        });
    }
}

function LoginCheck() {
    var debugCheck = {"debug": true}

    $.ajax({
        type: "POST",
        url: "/rest/authenticationCheck",
        data: JSON.stringify(debugCheck),
        dataType: 'json',
        contentType: "application/json; charset=UTF-8",
        beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            // do something ...
            console.log("Login Check Success")
            console.log("is login : " + response)
            return true;
        },
        error: function (e) {
            console.log("Login Check Failed")
            alert("로그인을 해주세요")
            return false;
        }
    });
}

function copyURL() {
    var dummy   = document.createElement("input");
    var text    = location.href;

    document.body.appendChild(dummy);
    dummy.value = text;
    dummy.select();
    document.execCommand("copy");
    document.body.removeChild(dummy);
    alert("URL이 클립보드에 복사되었습니다");
}

function ajaxCall() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var name = $("#userName").val();

    var jsonData = {
        "name": name
    }

    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: '/csrf/ajax',
        data: JSON.stringify(jsonData), // String -> json 형태로 변환
        beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        },
        dataType: 'json', // success 시 받아올 데이터 형
        async: true, //동기, 비동기 여부
        cache: false, // 캐시 여부
        success: function (data) {
            console.log(data.name);
        },
        error: function (xhr, status, error) {
            console.log('error:' + error);
        }
    });
}

function removeURLParam(keyword) {
    let url = window.location.href;
    let param = new URLSearchParams(window.location.search);

    // console.log(url);
    // console.log(param.has('page'));

    param.delete(keyword);
    // console.log(param.has('page'));

    url = window.location.protocol + "//" + window.location.host + window.location.pathname + "?" + param;

    // console.log(url);

    /* https://stackoverflow.com/questions/22753052/remove-url-parameters-without-refreshing-page */
    // window.history.pushState(null, null, url);
    window.history.replaceState(null, null, url);
}

function addURLParam(keyword, value) {
    let url = window.location.href;
    let urlParams = new URLSearchParams(window.location.search);
    // console.log(param.has('page'));

    urlParams.set(keyword, value);

    window.location.search = urlParams;

    // console.log(url);

    /* https://stackoverflow.com/questions/22753052/remove-url-parameters-without-refreshing-page */
    window.history.pushState(null, null, url);
    // window.history.replaceState(null, null, url);
}