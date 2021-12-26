var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
    oAppRef : oEditors,
    elPlaceHolder : "smartEditor", //저는 textarea의 id와 똑같이 적어줬습니다.
    // sSkinURI : "/smartditor2/SmartEditor2Skin.html", //경로를 꼭 맞춰주세요!
    sSkinURI : "/se2/SmartEditor2Skin.html",
    fCreator : "createSEditor2",
    htParams : {
        // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
        bUseToolbar : true,

        // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
        bUseVerticalResizer : false,

        // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
        bUseModeChanger : false
    }
});

function save(){
    oEditors.getById["smartEditor"].exec("UPDATE_CONTENTS_FIELD", []);
    //스마트 에디터 값을 텍스트컨텐츠로 전달
    var content = document.getElementById("smartEditor").value;
    $("#form_name").val(content);
    var title = $("#title").val();
    if (title.replace(/\s|　/gi, "").length == 0) {
        alert("제목을 입력해주세요.");
        $("#title").focus();
    }else {
        $("Form").submit();
    }

    // alert(document.getElementById("smartEditor").value);
    // 값을 불러올 땐 document.get으로 받아오기
    return;
}

function ev_save(){
    oEditors.getById["smartEditor"].exec("UPDATE_CONTENTS_FIELD", []);
    //스마트 에디터 값을 텍스트컨텐츠로 전달
    var content = document.getElementById("smartEditor").value;
    $("#form_name").val(content);
    var title = $("#title").val();
    var start = $("#start").val();
    var stop = $("#stop").val();
    var end = $("#end").val();
    var itme = $("#end").val();
    if (title.replace(/\s|　/gi, "").length == 0) {
        alert("제목을 입력해주세요.");
        $("#title").focus();
        return;
    }
    if (start.replace(/\s|　/gi, "").length == 0) {
        alert("시작일을 입력해주세요.");
        $("#start").focus();
        return;
    }
    if (stop.replace(/\s|　/gi, "").length == 0) {
        alert("마감일을 입력해주세요.");
        $("#stop").focus();
        return;
    }
    if (end.replace(/\s|　/gi, "").length == 0) {
        alert("추첨일을 입력해주세요.");
        $("#end").focus();
        return;
    }
    if (itme.replace(/\s|　/gi, "").length == 0) {
        alert("선물 수를 입력해주세요.");
        $("#itme").focus();
        return;
    }



    $("Form").submit();

    // alert(document.getElementById("smartEditor").value);
    // 값을 불러올 땐 document.get으로 받아오기
    return;
}

function upload(){
    oEditors.getById["smartEditor"].exec("UPDATE_CONTENTS_FIELD", []);
    //스마트 에디터 값을 텍스트컨텐츠로 전달
    var content = document.getElementById("smartEditor").value;
    $("#form_name").val(content);
    var title = $("#title").val();
    if (title.replace(/\s|　/gi, "").length == 0) {
        alert("제목을 입력해주세요.");
        $("#title").focus();
    }else {
        $("Form").submit();
    }
    // alert(document.getElementById("smartEditor").value);
    // 값을 불러올 땐 document.get으로 받아오기
    return;
}