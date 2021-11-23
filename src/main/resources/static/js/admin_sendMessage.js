function selectAll(selectAll)  {
    const checkboxes
        = document.getElementsByName('user');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })
}

function putCheckList() {
    peopleArr = new Array();
    var idxArr = new Array();

    $("input[name=user]:checked").each(function() {
        idxArr.push($("input[name=user]").index(this));
    });

    for (var i = 0; i < idxArr.length; i++) {
        var obj = new Object();
        obj.name = $("#tbl_peopleList tbody").children().eq(idxArr[i]).children().eq(1).text();
        obj.age = $("#tbl_peopleList tbody").children().eq(idxArr[i]).children().eq(2).text();
        peopleArr.push(obj);
    }
}

var peopleArr = new Array();	// 체크된 항목을 담기 위한 배열 선언

$(document).ready(function() {
    // 체크 박스 클릭 시 전체선택
    $("#chkAll").click(function() {
        if($("#chkAll").is(":checked")) $("input[name=chk]").prop("checked", true);
        else $("input[name=chk]").prop("checked", false);
        putCheckList();
    });

    $("input[name=chk]").change(function() {
        // 체크박스 갯수와  체크된 체크박스 갯수 비교 후 불일치시 헤더 체크박스 해제
        if($(this).length != $("input[name=chk]:checked").length) $("#chkAll").prop("checked", false);
        putCheckList();
    });

    $("#btn_showChkList").click(function() {
        if(peopleArr.length == 0) {
            $("#txt_getChkList").val("");
            alert("체크된 항목이 없습니다.");
            return;
        }

        var str = "";
        for (var i = 0; i < peopleArr.length; i++) {
            str += "순번 : " + parseInt(i+1) + ", 이름 : " + peopleArr[i].name + ", 나이 : " + peopleArr[i].age + "\n";
        }

        $("#txt_getChkList").val(str);
    });
});