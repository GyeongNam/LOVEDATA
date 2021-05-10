function onSortChange() {
    console.log("changed");
    alert("changed");
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
            navbarDropdownMenuLink.innerHTML = "좋아요 순"
            break;
        case "mostRecent" :
            navbarDropdownMenuLink.setAttribute("value", sortType.value);
            navbarDropdownMenuLink.innerHTML = "최신 등록순"
            break;
        case "mostOldest" :
            navbarDropdownMenuLink.setAttribute("value", sortType.value);
            navbarDropdownMenuLink.innerHTML = "오래된 등록순"
            break;
        default :
            navbarDropdownMenuLink.setAttribute("value", "mostViewed");
            console.log("소트 타입에 없는 값입니다.")
            navbarDropdownMenuLink.
            break;
    }
}