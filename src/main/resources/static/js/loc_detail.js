function CopyUrlToClipboard() {
    var obShareUrl = document.getElementById("ShareUrl");
    obShareUrl.value = window.document.location.href;  // 현재 URL 을 세팅해 줍니다.
    obShareUrl.select();  // 해당 값이 선택되도록 select() 합니다
    document.execCommand("copy"); // 클립보드에 복사합니다.
    obShareUrl.blur(); // 선택된 것을 다시 선택안된것으로 바꿈니다.
    alert("URL이 클립보드에 복사되었습니다");
}

function ManagePopup(sPopupId, sPopupBgId, sCase)
{
    console.log("ManagePopup Called");

    if ( sCase == "닫기" ) {
        // document.getElementById(sPopupBgId).style.display = "none";
        document.getElementById(sPopupId).style.display = "none";
    }
    else if ( sCase == "열기" ) {
        // document.getElementById(sPopupBgId).style.display = "";
        document.getElementById(sPopupId).style.display = "";
    }
}