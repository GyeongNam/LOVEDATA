<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="_csrf" content="${_csrf.token}">
	<meta name="_csrf_header" content="${_csrf.headerName}">
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
	</style>
	<title>Location Search Popup</title>
</head>
<body>
<div class="container-fluid mt-3">
	<div class="row" style="margin: 3px">
		<div class="col d-flex align-content-between p-0 align-items-md-center">
			<div class="col">
				<span>장소 검색</span>
				<select name="location_search_order" id="location_search_order">
					<option value="mostViewed">조회순</option>
					<option value="mostLiked">좋아요 순</option>
					<option value="mostRecent">최신등록 순</option>
					<option value="mostOldest">오래된 등록 순</option>
				</select>
			</div>
			<div class="col d-flex justify-content-end">
				<input type="text" placeholder="장소 검색" id="keyword" name="keyword"/>
				<button class="btn btn-primary me-2" type="button" id="searchBtn" onclick="onClickSearch()">Search
				</button>
			</div>
		</div>
		<div class="row my-3">
			<div class="col d-flex justify-content-center align-items-md-center" id="searchResultArea">
				<table class="table" id="searchResultTable">
					<thead>
					<th scope="col">#</th>
					<th scope="col">장소 이름</th>
					<th scope="col">장소 주소</th>
					<th scope="col">등록 유저</th>
					<th scope="col">조회수</th>
					<th scope="col">좋아요수</th>
					<th scope="col">등록일</th>
					</thead>
					<tbody id="tableBody">
<%--					<tr onclick="location.href='/'" style="cursor:hand">--%>
<%--						<td>1</td>--%>
<%--						<td>광화문</td>--%>
<%--						<td>서울 종로구 효자로 12 국립고궁박물관</td>--%>
<%--					</tr>--%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<form id="form" name="form" method="post">
		<input type="hidden" id="loc_name" name="loc_name" value=""/>
		<%--		<input type="hidden" id="returnUrl" name="returnUrl" value=""/>--%>
		<input type="hidden" id="loc_addr" name="loc_addr" value=""/>
		<input type="hidden" id="loc_tel" name="loc_tel" value=""/>
		<input type="hidden" id="loc_info" name="loc_info" value=""/>
		<input type="hidden" id="returnUrl" name="returnUrl" value="http://localhost:8080/service/cor_registration"/>
		<input type="hidden" id="resultType" name="resultType" value=""/>
		<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 START-->
		<!--
		<input type="hidden" id="encodingType" name="encodingType" value="EUC-KR"/>
		 -->
		<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 END-->
	</form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
<script defer>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    let sortOrder = "VIEW_DES";

    function onCheckSortOrder() {
        let sortSelect = document.getElementById("location_search_order");
		let sort = sortSelect.options[sortSelect.selectedIndex].value;
		let searchResult = null;

        switch (sort) {
            case "mostViewed" :
                sortOrder = "VIEW_DES";
                break;
            case "mostLiked" :
                sortOrder = "LIKE_DES";
                break;
            case "mostRecent" :
                sortOrder = "DATE_DES";
                break;
            case "mostOldest" :
                sortOrder = "DATE_ASC";
                break;
            default :
                sortOrder = "VIEW";
        }
    }

    function clearSearchHistory() {
        let tableBody = document.getElementById("tableBody");

        if (tableBody.hasChildNodes()){
            while (tableBody.childElementCount > 0) {
                tableBody.removeChild(tableBody.children.item(0));
			}
		}
	}

    function onClickSearch() {
        let keyword = document.getElementById("keyword").value;
        onCheckSortOrder();

        let json = {
            "keyword" : keyword,
			"sortOrder" : sortOrder,
			"searchType" : "TITLE"
		}

        $.ajax({
            type: "POST",
            url: "/rest/service/loc/search",
            data: JSON.stringify(json),
            dataType: 'json',
            contentType: "application/json; charset=UTF-8",
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                if (response == null) {
                    console.log("통신 실패!")
					return false;
				} else {
                    console.log("통신 성공!");
                    console.log(response);
                    searchResult = response;

                    clearSearchHistory();
                    if (searchResult.size != 0) {
                        addLocationItem();
                    }

                    return true;
				}
            },
            error: function (e) {
                console.log("통신 문제 발생!")
				return false;
            }
        });
    }

    function addLocationItem() {
        let tableBody = document.getElementById("tableBody");

        for (let i = 0; i < searchResult.size; i++) {
            let row = tableBody.insertRow();
            let newCell1 = row.insertCell(0);
            let newCell2 = row.insertCell(1);
            let newCell3 = row.insertCell(2);
            let newCell4 = row.insertCell(3);
            let newCell5 = row.insertCell(4);
            let newCell6 = row.insertCell(5);
            let newCell7 = row.insertCell(6);
            let newCell8 = row.insertCell(7);
            let newCell9 = row.insertCell(8);

            let locName = "locName_" + i;
            let locAddr = "locAddr_" + i;
            let locID = "locID_" + i;
            let locNo = "locNo_" + i;
            let locUser = "locUserName_" + i;
            let locRegDate = "locRegDate_" + i;
            let locViewCount = "locViewCount_" + i;
            let locLikeCount = "locLikeCount_" + i;

            let clickHandler = function (row) {
                return function() {
                    locNo = row.getElementsByTagName("td")[7].innerText;
                    locID = row.getElementsByTagName("td")[8].innerText;
                    locName = row.getElementsByTagName("td")[1].innerText;

                    opener.locationCallBack(locNo, locID, locName);
                    window.close();
				}
			}

            row.onclick = clickHandler(row);
			row.style.cursor = "hand";

            newCell1.innerText = i+1;
            newCell2.innerText = searchResult[locName];
            newCell3.innerText = searchResult[locAddr];
            newCell4.innerText = searchResult[locUser];
            newCell5.innerText = searchResult[locViewCount];
            newCell6.innerText = searchResult[locLikeCount];
            newCell7.innerText = searchResult[locRegDate];
            newCell8.innerText = searchResult[locNo];
            newCell9.innerText = searchResult[locID];
            newCell8.className = "visually-hidden";
            newCell9.className = "visually-hidden";
        }

        function onClickLink(locNo, locID, locName) {
            opener.locationCallBack(locNo, locID, locName);
            window.close();
		}
	}
</script>
</body>
</html>
