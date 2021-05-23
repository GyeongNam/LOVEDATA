let tagList = [];
let index;

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
    tag.parentElement.style.display="none";
    tag.parentElement.firstElementChild.setAttribute("value", "");
    tagList.pop();
    console.log("list count : " + (--index));
    console.log(tagList);
}

function onClickLike(like) {
    if (like.getAttribute("src") === "/image/icon/like/love_black.png") {
        like.src = "/image/icon/like/love_color.png";
    } else {
        like.src = "/image/icon/like/love_black.png";
    }
}