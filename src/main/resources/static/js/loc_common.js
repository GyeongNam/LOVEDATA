let tagList = [];
let index;
var lastLikeLocId;
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

function addTag(tag) {
    // let navbar = document.getElementById("tag-navbar-collapse");
    let list = document.getElementById('tag_list').children;
    let isReachMaxNumber = true;
    let isDuplicated = false;

    console.log(list.item(0).className);
    console.log(list.item(0).valueOf());

    for (let i = 0; i < 7; i++) {

        if (list.item(i).children.item(0).getAttribute("value") === tag.value) {
            isDuplicated = true;
            break;
        }

        if (list.item(i).children.item(0).getAttribute("value") === "") {
            list.item(i).children.item(0).setAttribute("value", tag.value);
            list.item(i).children.item(0).innerHTML = tag.value;
            list.item(i).style.display = "inline-block";
            isReachMaxNumber = false;
            index = i + 1;
            tagList.push(tag.value);
            break;
        }
    }

    if (isDuplicated) {
        alert("중복된 해시태그가 있습니다.");
        return;
    }

    if (isReachMaxNumber) {
        alert("해시태그는 최대 7개까지 추가할 수 있습니다.");
        return;
    }

    console.log("list count : " + index);
    console.log(tagList);
}

function removeTag(tag) {
    tag.parentElement.style.display = "none";
    tag.parentElement.firstElementChild.setAttribute("value", "");
    tagList.pop();
    console.log("list count : " + (--index));
    console.log(tagList);
}

function onClickLike(Like) {
    var countChangeFlag = true;
    var likeCount = null;
    var loc_no = null;
    var loc = null;
    var path_noparm = location.pathname;

    console.log(path_noparm);
    if (path_noparm ===  "/service/loc_detail" || path_noparm === "/service/loc_detail/ex") {
        countChangeFlag = false;
        loc = document.getElementById("loc_no");
    } else {
        likeCount = Like.nextElementSibling.innerText;
        loc_no = Like.nextElementSibling.nextElementSibling.innerText;
        loc = Like.nextElementSibling;
    }
    var jsonData = {"loc_no": loc_no}
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
    map.set("loc_no", loc_no);
    map.set("jsonData", jsonData);
    map.set("isClicked", isClicked);
    map.set("like", Like);
    map.set("loc", loc);
    map.set("countChangeFlag", countChangeFlag);

    onLoginCheck(map);
}

function onLoginCheck(map) {
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
            onClickLikeAction(map);
            return true;
        },
        error: function (e) {
            console.log("Login Check Failed")
            alert("로그인을 해주세요")
            return false;
        }
    });
}

function onClickLikeAction(map) {
    var likeCount = map.get("likeCount");
    var loc_no = map.get("loc_no");
    var jsonData = map.get("jsonData");
    var isClicked = map.get("isClicked");
    var like = map.get("like");
    var loc = map.get("loc");
    var countChangeFlag = map.get("countChangeFlag");

    console.log("loc_no : " + loc_no);

    // Todo ajax를 인증용 하나와 db에 접속용 두번 보내기
    // 마지막으로 클릭한 loc_id를 저장하는 전역변수 만들고, 성공적으로 저장시 해당 변수에 값을 저장
    // 이미 클릭한 버튼을 다시 클릭시 db에서 좋아요 수를 빼고, 이미지를 원래대로 돌리게끔 설정

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
                console.log("Like Success")

                like.src = "/image/icon/like/love_color.png";
                if (countChangeFlag) {
                    console.log(parseInt(likeCount));
                    console.log(parseInt(likeCount) + 1);
                    console.log(typeof (likeCount) + "\t" + likeCount);
                    loc.innerText = parseInt(likeCount) + 1;
                }
                lastLikeLocId = loc_no;
            },
            error: function (e) {
                console.log("Like Failed")
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
                console.log("Undo Success")

                like.src = "/image/icon/like/love_black.png";
                if (countChangeFlag) {
                    console.log(parseInt(likeCount));
                    console.log(parseInt(likeCount) - 1);
                    console.log(typeof (likeCount) + "\t" + likeCount);
                    loc.innerText = parseInt(likeCount) - 1;
                }

                lastLikeLocId = loc_no;
            },
            error: function (e) {
                console.log("Undo Failed")
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