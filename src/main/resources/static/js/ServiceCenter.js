//내정보 탭 변경
function MenuTab(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();

//정책 탭 변경
function ServiceCenterTab(evt, tabName) {
    var k, Ptabcontent, Ptablinks;
    Ptabcontent = document.getElementsByClassName("Ptabcontent");
    for (k = 0; k < Ptabcontent.length; k++) {
        Ptabcontent[k].style.display = "none";
    }
    Ptablinks = document.getElementsByClassName("Ptablinks");
    for (k = 0; k < Ptablinks.length; k++) {
        Ptablinks[k].className = Ptablinks[k].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("FirstTabOpen").click();

function gologin(){
    alert("로그인 해 주세요!");
    location.href="/login";
}

function Qsearch(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });

    var select =$('#Questions_selitm');
    var text =$('#Questions_search');

    var tbody = document.getElementById("tbody").getElementsByTagName("tbody")[0];
    while(tbody.rows.length > 0){
        tbody.deleteRow(0);
    }

    var postdata = {
        "select" : select.val(),
        "text" : text.val()
    }

    $.ajax({
        url: "/Questions/search",
        dataType: 'json',
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(postdata),
        type: "POST",
        success:function(data){
            var list = data.questions;

            list.forEach(function(element){

                var answer;
                if(element.qu_answer){
                    answer = "답변완료"
                }else {
                    answer = "답변중"
                }
                var Qtbody = document.getElementById("Qtbody");
                var tr = document.createElement( 'TR' );
                console.log(element.qu_secret);
                if(element.qu_secret){
                    tr.innerHTML =
                        "<td>"+element.qu_no+"</td>" +
                        "<td onclick=window.open('about:blank').location.href='/ServiceCenter/Questions/"+element.qu_no+"';>"+element.qu_title+" [비밀글] </td>" +
                        "<td>"+element.qu_user+"</td>" +
                        "<td>"+element.qu_date+"</td>" +
                        "<td>"+answer+"</td>";
                    Qtbody.appendChild(tr);
                }else {
                    tr.innerHTML =
                        "<td>"+element.qu_no+"</td>" +
                        "<td onclick=window.open('about:blank').location.href='/ServiceCenter/Questions/"+element.qu_no+"';>"+element.qu_title+"</td>" +
                        "<td>"+element.qu_user+"</td>" +
                        "<td>"+element.qu_date+"</td>" +
                        "<td>"+answer+"</td>";

                    Qtbody.appendChild(tr);
                }
            });

        },
        error : function(){

        }
    });



}

