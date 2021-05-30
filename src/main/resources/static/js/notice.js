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
