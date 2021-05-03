function onSortChange() {
    console.log("changed");
    alert("changed");
}

function onClickLike(like) {
    if (like.getAttribute("src") === "/image/icon/like/love_black.png") {
        like.src = "/image/icon/like/love_color.png";
    } else {
        like.src = "/image/icon/like/love_black.png";
    }
}

function changeSort(sortType) {
    console.log(sortType.value);
    let navbarDropdownMenuLink = document.getElementById("navbarDropdownMenuLink");
    switch (sortType.value) {
        case "mostViewed" :
            navbarDropdownMenuLink.setAttribute("value", sortType.value);
            navbarDropdownMenuLink.innerHTML = "조회순"
            break;
        case "mostLiked" :
            navbarDropdownMenuLink.setAttribute("value", sortType.value);
            navbarDropdownMenuLink.innerHTML = "추천순"
            break;
        case "mostRecent" :
            navbarDropdownMenuLink.setAttribute("value", sortType.value);
            navbarDropdownMenuLink.innerHTML = "가장 최근"
            break;
        case "mostOldest" :
            navbarDropdownMenuLink.setAttribute("value", sortType.value);
            navbarDropdownMenuLink.innerHTML = "가장 오래전"
            break;
        default :
            navbarDropdownMenuLink.setAttribute("value", "mostViewed");
            console.log("소트 타입에 없는 값입니다.")
            navbarDropdownMenuLink.
            break;
    }
}

function removeTag(tag) {
    // tag.parentElement.style.display = "none";
    tag.parentElement.style.display="none";
    tag.value = null;
}

function addTag(tag) {
    // let navbar = document.getElementById("tag-navbar-collapse");
    let list = document.getElementById('tag_list').children;
    let addedFlag = false;
    console.log(list.item(0).className);
    console.log(list.item(0).valueOf());
    for (let i = 0; i < 7; i++) {
        if (list.item(i).children.item(0).getAttribute("value") === "") {
            list.item(i).children.item(0).setAttribute("value", tag.value);
            list.item(i).children.item(0).innerHTML = tag.value;
            list.item(i).style.display = "inline-block";
            addedFlag = true;
            console.log("added Good")
            break;
        }
    }

    if (!addedFlag) {
        console.log("added Failed")
        alert("해시태그는 최대 7개까지 추가할 수 있습니다.");
        return;
    } else {
        console.log(list.item(0).valueOf());
    }
    console.log("list count : " + list.length);
    console.log("list type : " + typeof (list) + "\nvalue : " + list);
}