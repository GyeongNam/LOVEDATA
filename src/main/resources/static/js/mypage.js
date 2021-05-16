//내정보 탭 변경
function MenuTab(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();


//프로필 이미지 등록
function handleFileSelect(event) {
    var input = this;
    console.log(input.files)
    if (input.files && input.files.length) {
        var reader = new FileReader();
        this.enabled = false
        reader.onload = (function (e) {
            console.log(e)
            $("#preview").html(['<img class="thumb" src="', e.target.result, '" title="', escape(e.name), '"/>'].join(''))
        });
        reader.readAsDataURL(input.files[0]);
    }
}
$('#file').change(handleFileSelect);
$('.file-edit-icon').on('click', '.preview-de', function () {
    $("#preview").empty()
    $("#file").val("");
});
$('.preview-edit').click( function() {
    $("#file").click();
} );



//생년월일 select box
$(document).ready(function () {
    setDateBox();
});

// select box 연도 , 월 표시
function setDateBox() {
    var dt = new Date();
    var year = "";
    var com_year = dt.getFullYear();

    // 발행 뿌려주기
    $("#year").append("<option value=''>년도</option>");

    // 올해 기준으로 -50년부터 +1년을 보여준다.
    for (var y = (com_year - 50); y <= (com_year + 1); y++) {
        $("#year").append("<option value='" + y + "'>" + y + " 년" + "</option>");
    }

    // 월 뿌려주기(1월부터 12월)
    var month;
    $("#month").append("<option value=''>월</option>");
    for (var i = 1; i <= 12; i++) {
        $("#month").append("<option value='" + i + "'>" + i + " 월" + "</option>");
    }

    // 일 뿌려주기(1일부터 31일)
    var day;
    $("#day").append("<option value=''>일</option>");
    for (var i = 1; i <= 31; i++) {
        $("#day").append("<option value='" + i + "'>" + i + " 일" + "</option>");
    }

}