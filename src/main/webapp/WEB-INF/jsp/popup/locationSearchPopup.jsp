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
	<div class="row">
		<div class="col d-flex">
			<span>장소 검색</span>
			<select name="location_search_type" id="location_search_type">
				<option value="조회순">조회순</option>
				<option value="좋아요 순">좋아요 순</option>
				<option value="최신 등록순">최신등록 순</option>
				<option value="오래된 등록순">오래된 등록 순</option>
			</select>
		</div>
		<div class="row">
			<div class="col">
				<input type="text" placeholder="장소 검색" id="keyword" name="keyword"/>
				<button class="btn btn-primary mx-2" type="button" id="searchBtn" onclick="onClickSearch()">Search</button>
			</div>
		</div>
	</div>
	<form id="form" name="form" method="post">
		<input type="hidden" id="loc_name" name="loc_name" value=""/>
		<%--		<input type="hidden" id="returnUrl" name="returnUrl" value=""/>--%>
		<input type="hidden" id="loc_addr" name="loc_addr" value=""/>
		<input type="hidden" id="loc_tel" name="loc_tel" value=""/>
		<input type="hidden" id="loc_info" name="loc_info" value=""/>
		<input type="hidden" id="returnUrl" name="returnUrl" value="http://localhost:8080/service/loc_registration"/>
		<input type="hidden" id="resultType" name="resultType" value=""/>
		<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 START-->
		<!--
		<input type="hidden" id="encodingType" name="encodingType" value="EUC-KR"/>
		 -->
		<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 END-->
	</form>
	<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
			crossorigin="anonymous"></script>
	<script defer>
        function onClickSearch() {
            let form;
            form = document.createElement("form");
            form.method = "get";
            form.action="/service/loc_recommend/search"

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

                form.appendChild(input[i]);
            }

            document.body.appendChild(form);
            form.submit();
        }
	</script>
</body>
</html>
