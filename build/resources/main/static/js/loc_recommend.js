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