<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Location Map</title>
</head>
<body onload="initTmap();">
	<c:choose>
		<c:when test="${isNullLoc eq true}">
			<span>해당 장소의 정보가 없습니다.</span>
			<%
				if(true) return;
			%>
		</c:when>
		<c:otherwise>
			<%--<select id="city_do" name="city_do"></select>--%>
			<%--<select id="gu_gun" name="gu_gun"></select>--%>
			<%--<input type="text" class="text_custom" id="roadAddr" name="roadAddr"--%>
			<%--	   value="진관1로 21-10">--%>
			<%--<input type="text" class="text_custom" id="detailAddr" name="detailAddr"--%>
			<%--	   value="124-904">--%>
			<%--<button id="btn_select">적용하기</button>--%>
			<div id="map_div"></div>
		</c:otherwise>
	</c:choose>
</body>
<script	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://apis.openapi.sk.com/tmap/jsv2?version=1&appKey=l7xx3241520698ba4a96b002203c64d57242"></script>
<script type="text/javascript">
    var map, marker1;
    function initTmap() {

        // 1. 지도 띄우기
        map = new Tmapv2.Map("map_div", {
            center : new Tmapv2.LatLng(37.570028, 126.986072),
            width : "100%",
            height : "600px",
            zoom : 15,
            zoomControl : true,
            scrollwheel : true
        });

        // 마커 초기화
        marker1 = new Tmapv2.Marker(
            {
                icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_a.png",
                iconSize : new Tmapv2.Size(24, 38),
                map : map
            });

        // 2. API 사용요청
        var city_do = '${dto.siDo}';
        var gu_gun = '${dto.siGunGu}';
        var dong = '${dto.roadAddr}';

        // 시구가 동시에 있어도 가능!
        // var city_do = "경기도";
        // var gu_gun = "고양시 덕양구"
        // var dong = "동헌로 305";

        $.ajax({
            method : "GET",
            url : "https://apis.openapi.sk.com/tmap/geo/geocoding?version=1&format=json&callback=result",
            async : false,
            data : {
                "appKey" : "l7xx3241520698ba4a96b002203c64d57242",
                "coordType" : "WGS84GEO",
                "city_do" : city_do,
                "gu_gun" : gu_gun,
                "dong" : dong,
                "addressFlag" : "F02"
            },
            success : function(response) {
                var resultData = response.coordinateInfo;
                var lon, lat;

                if (resultData.lon.length > 0) {
                    lon = resultData.lon;
                    lat = resultData.lat;
                } else {
                    lon = resultData.newLon;
                    lat = resultData.newLat;
                }

                //기존 마커 삭제
                marker1.setMap(null);
                console.log("위도(latitude) : " + lat);
                console.log("경도(logitude) : " + lon);

                var markerPosition = new Tmapv2.LatLng(
                    Number(lat), Number(lon));
                //마커 올리기
                marker1 = new Tmapv2.Marker(
                    {
                        position : markerPosition,
                        icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_a.png",
                        iconSize : new Tmapv2.Size(
                            24, 38),
                        map : map
                    });
                map.setCenter(markerPosition);

            },
            error : function(request, status, error) {
                console.log("code:"
                + request.status + "\n"
                + "message:"
                + request.responseText
                + "\n" + "error:" + error);
            }
        });
    }
</script>
<script>
	// https://chichi-story.tistory.com/18

    $('document').ready(function() {
        var area0 = ["시/도 선택","서울특별시","인천광역시","대전광역시","광주광역시","대구광역시","울산광역시","부산광역시","경기도","강원도","충청북도","충청남도","전라북도","전라남도","경상북도","경상남도","제주도"];
        var area1 = ["강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"];
        var area2 = ["계양구","남구","남동구","동구","부평구","서구","연수구","중구","강화군","옹진군"];
        var area3 = ["대덕구","동구","서구","유성구","중구"];
        var area4 = ["광산구","남구","동구",     "북구","서구"];
        var area5 = ["남구","달서구","동구","북구","서구","수성구","중구","달성군"];
        var area6 = ["남구","동구","북구","중구","울주군"];
        var area7 = ["강서구","금정구","남구","동구","동래구","부산진구","북구","사상구","사하구","서구","수영구","연제구","영도구","중구","해운대구","기장군"];
        var area8 = ["고양시","과천시","광명시","광주시","구리시","군포시","김포시","남양주시","동두천시","부천시","성남시","수원시","시흥시","안산시","안성시","안양시","양주시","오산시","용인시","의왕시","의정부시","이천시","파주시","평택시","포천시","하남시","화성시","가평군","양평군","여주군","연천군"];
        var area9 = ["강릉시","동해시","삼척시","속초시","원주시","춘천시","태백시","고성군","양구군","양양군","영월군","인제군","정선군","철원군","평창군","홍천군","화천군","횡성군"];
        var area10 = ["제천시","청주시","충주시","괴산군","단양군","보은군","영동군","옥천군","음성군","증평군","진천군","청원군"];
        var area11 = ["계룡시","공주시","논산시","보령시","서산시","아산시","천안시","금산군","당진군","부여군","서천군","연기군","예산군","청양군","태안군","홍성군"];
        var area12 = ["군산시","김제시","남원시","익산시","전주시","정읍시","고창군","무주군","부안군","순창군","완주군","임실군","장수군","진안군"];
        var area13 = ["광양시","나주시","목포시","순천시","여수시","강진군","고흥군","곡성군","구례군","담양군","무안군","보성군","신안군","영광군","영암군","완도군","장성군","장흥군","진도군","함평군","해남군","화순군"];
        var area14 = ["경산시","경주시","구미시","김천시","문경시","상주시","안동시","영주시","영천시","포항시","고령군","군위군","봉화군","성주군","영덕군","영양군","예천군","울릉군","울진군","의성군","청도군","청송군","칠곡군"];
        var area15 = ["거제시","김해시","마산시","밀양시","사천시","양산시","진주시","진해시","창원시","통영시","거창군","고성군","남해군","산청군","의령군","창녕군","하동군","함안군","함양군","합천군"];
        var area16 = ["서귀포시","제주시","남제주군","북제주군"];



        // 시/도 선택 박스 초기화

        $("select[name^=city_do]").each(function() {
            $selsido = $(this);
            $.each(eval(area0), function() {
                $selsido.append("<option value='"+this+"'>"+this+"</option>");
            });
            $selsido.next().append("<option value=''>구/군 선택</option>");
        });



        // 시/도 선택시 구/군 설정

        $("select[name^=city_do]").change(function() {
            var area = "area"+$("option",$(this)).index($("option:selected",$(this))); // 선택지역의 구군 Array
            var $gu_gun = $(this).next(); // 선택영역 군구 객체
            $("option",$gu_gun).remove(); // 구군 초기화

            if(area == "area0")
                $gu_gun.append("<option value=''>구/군 선택</option>");
            else {
                $.each(eval(area), function() {
                    $gu_gun.append("<option value='"+this+"'>"+this+"</option>");
                });
            }
        });


    });
</script>
</html>
