let sortOrder = "VIEW_DES";
let activeDistrict = "전국";

function changeSort(sortType) {
    console.log(sortType.value);
    let navbarDropdownMenuLink = document.getElementById("navbarDropdownMenuLink");
    switch (sortType.value) {
        case "mostViewed" :
            navbarDropdownMenuLink.setAttribute("value", sortType.value);
            navbarDropdownMenuLink.innerHTML = "조회순"
            sortOrder = "VIEW_DES";
            break;
        case "mostLiked" :
            navbarDropdownMenuLink.setAttribute("value", sortType.value);
            navbarDropdownMenuLink.innerHTML = "좋아요 순"
            sortOrder = "LIKE_DES";
            break;
        case "mostRecent" :
            navbarDropdownMenuLink.setAttribute("value", sortType.value);
            navbarDropdownMenuLink.innerHTML = "최신 등록순"
            sortOrder = "DATE_DES";
            break;
        case "mostOldest" :
            navbarDropdownMenuLink.setAttribute("value", sortType.value);
            navbarDropdownMenuLink.innerHTML = "오래된 등록순"
            sortOrder = "DATE_ASC";
            break;
        default :
            console.log("소트 타입에 없는 값입니다.")
            navbarDropdownMenuLink.setAttribute("value", "mostViewed");
            navbarDropdownMenuLink.innerText = "조회순"
            sortOrder = "VIEW";
    }
}

function changeActiveDistrict(district) {
    console.log(district.value);
    let districtDropdownMenuLink = document.getElementById("districtDropdownMenuLink");
    districtDropdownMenuLink.setAttribute("value", district.value);
    districtDropdownMenuLink.innerHTML = district.value;
    activeDistrict = district.value;
}

function onClickSearch_Course() {
    let form;
    let url;
    let admin;

    url = new URL(window.location.href);

    form = document.createElement("form");
    form.method = "get";

    if (url.pathname.split('/')[1] === "admin") {
        isAdmin = true;
        form.action="/admin/cor_recommend/search";
    } else {
        isAdmin = false;
        form.action="/service/cor_recommend/search";
    }

    let keyword = document.getElementById("keyword").value;

    // console.log("keyword : " + keyword);
    // console.log("sortOrder : " + sortOrder);
    // console.log("tags : " + tagList);

    let input = [];
    for (let i = 0; i < 4; i++) {
        input[i] = document.createElement("input");
        $(input[i]).attr("type", "hidden");

        if (i === 0) {
            $(input[0]).attr("name", "keyword");
            $(input[0]).attr("value", keyword);
        }

        if (i === 1) {
            $(input[1]).attr("name", "sortOrder");
            $(input[1]).attr("value", sortOrder);
        }

        if (i === 2) {
            $(input[2]).attr("name", "tags");
            $(input[2]).attr("value", tagList);
        }

        if (i === 3) {
            $(input[3]).attr("name", "searchType");
            if (isAdmin) {
                if (keyword !== "") {
                    if (tagList.length !== 0) {
                        $(input[3]).attr("value", "DISABLED_TITLE_TAG");
                    } else {
                        $(input[3]).attr("value", "DISABLED_TITLE");
                    }
                } else {
                    $(input[3]).attr("value", "DISABLED_TAG");
                }
            } else {
                if (keyword !== "") {
                    if (tagList.length !== 0) {
                        $(input[3]).attr("value", "TITLE_TAG");
                    } else {
                        $(input[3]).attr("value", "TITLE");
                    }
                } else {
                    $(input[3]).attr("value", "TAG");
                }
            }
        }

        form.appendChild(input[i]);
    }

    document.body.appendChild(form);
    form.submit();
}

function onClickSearch() {
    let form;
    let url;
    let isAdmin;

    url = new URL(window.location.href);

    form = document.createElement("form");
    form.method = "get";

    if (url.pathname.split('/')[1] === "admin") {
        isAdmin = true;
        form.action="/admin/loc_recommend/search";
    } else {
        isAdmin = false;
        form.action="/service/loc_recommend/search";
    }

    let keyword = document.getElementById("keyword").value;

    // console.log("keyword : " + keyword);
    // console.log("sortOrder : " + sortOrder);
    // console.log("tags : " + tagList);

    let input = [];
    for (let i = 0; i < 5; i++) {
        input[i] = document.createElement("input");
        $(input[i]).attr("type", "hidden");

        if (i === 0) {
            $(input[0]).attr("name", "keyword");
            $(input[0]).attr("value", keyword);
        }

        if (i === 1) {
            $(input[1]).attr("name", "sortOrder");
            $(input[1]).attr("value", sortOrder);
        }

        if (i === 2) {
            $(input[2]).attr("name", "tags");
            $(input[2]).attr("value", tagList);
        }

        if (i === 3) {
            $(input[3]).attr("name", "searchType");
            if (isAdmin) {
                if (keyword !== "") {
                    if (tagList.length !== 0) {
                        $(input[3]).attr("value", "DISABLED_TITLE_TAG");
                    } else {
                        $(input[3]).attr("value", "DISABLED_TITLE");
                    }
                } else {
                    $(input[3]).attr("value", "DISABLED_TAG");
                }
            } else {
                if (keyword !== "") {
                    if (tagList.length !== 0) {
                        $(input[3]).attr("value", "TITLE_TAG");
                    } else {
                        $(input[3]).attr("value", "TITLE");
                    }
                } else {
                    $(input[3]).attr("value", "TAG");
                }
            }
        }

        if (i == 4) {
            $(input[4]).attr("name", "district")
            $(input[4]).attr("value", activeDistrict);
        }

        form.appendChild(input[i]);
    }

    document.body.appendChild(form);
    form.submit();
}