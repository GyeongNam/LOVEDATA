<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
	</style>
	<title>Course PathFinding</title>
</head>
<body onload="initTmap();" style="max-width: 1140px">
<%--<div class="ft_area">--%>
<%--	<div class="ft_select_wrap row d-flex">--%>
<%--		<div class="ft_select col">--%>
<%--			<select class="form-select" id="selectLevel" style="max-width: 600px">--%>
<%--				<option value="0" selected="selected">교통최적+추천</option>--%>
<%--				<option value="1">교통최적+무료우선</option>--%>
<%--				<option value="2">교통최적+최소시간</option>--%>
<%--				<option value="3">교통최적+초보</option>--%>
<%--				<option value="4">교통최적+고속도로우선</option>--%>
<%--				<option value="10">최단거리+유/무료</option>--%>
<%--				<option value="12">이륜차도로우선</option>--%>
<%--				<option value="19">교통최적+어린이보호구역 회피</option>--%>
<%--			</select>--%>
<%--&lt;%&ndash;			<select id="year">&ndash;%&gt;--%>
<%--&lt;%&ndash;				<option value="N" selected="selected">교통정보 표출 옵션</option>&ndash;%&gt;--%>
<%--&lt;%&ndash;				<option value="Y">Y</option>&ndash;%&gt;--%>
<%--&lt;%&ndash;				<option value="N">N</option>&ndash;%&gt;--%>
<%--&lt;%&ndash;			</select>&ndash;%&gt;--%>
<%--			<button class="btn btn-primary" id="btn_select">적용하기</button>--%>
<%--		</div>--%>
<%--	</div>--%>
<%--	<div class="map_act_btn_wrap clear_box"></div>--%>
<%--	<div class="clear"></div>--%>
<%--</div>--%>
<div class="container-sm p-0">
	<div class="row">
		<div class="col d-flex">
			<select class="form-select" id="selectLevel" style="max-width: 600px">
				<option value="0" selected="selected">교통최적+추천</option>
				<option value="1">교통최적+무료우선</option>
				<option value="2">교통최적+최소시간</option>
				<option value="3">교통최적+초보</option>
				<option value="4">교통최적+고속도로우선</option>
				<option value="10">최단거리+유/무료</option>
				<option value="12">이륜차도로우선</option>
				<option value="19">교통최적+어린이보호구역 회피</option>
			</select>
			<%--			<select id="year">--%>
			<%--				<option value="N" selected="selected">교통정보 표출 옵션</option>--%>
			<%--				<option value="Y">Y</option>--%>
			<%--				<option value="N">N</option>--%>
			<%--			</select>--%>
			<button class="btn btn-primary" id="btn_select">적용하기</button>
		</div>
	</div>
</div>

<div id="map_wrap" class="map_wrap">
	<div id="map_div"></div>
</div>
<div class="map_act_btn_wrap clear_box"></div>
<p id="result"></p>
<div class="row">
	<div class="d-flex justify-content-center align-items-md-center">
		<table class="table text-center" id="recentLocCorTable">
			<thead>
				<th scope="col">총 거리</th>
				<th scope="col">총 시간</th>
				<th scope="col">총 요금</th>
				<th scope="col">예상 택시 요금</th>
			</thead>
			<tbody id="table">
			</tbody>
		</table>
	</div>
</div>
<p id="nullLocationWarning"></p>
</body>
<script	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://apis.openapi.sk.com/tmap/jsv2?version=1&appKey=l7xx3241520698ba4a96b002203c64d57242"></script>
<script type="text/javascript">
    var map;
    var markerInfo;
    //출발지,도착지 마커
    var marker_s, marker_e, marker_p;
    //경로그림정보
    var drawInfoArr = [];
    var drawInfoArr2 = [];
    var resultInfoArr = [];

    var chktraffic = [];
    var resultdrawArr = [];
    var resultMarkerArr = [];

    function getCoordinates(city_do, gu_gun, road_addr) {
        // 2. API 사용요청
        var city_do = city_do;
        var gu_gun = gu_gun;
        var dong = road_addr;
        let corList = [];

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

                // console.log("위도(latitude) : " + lat);
                // console.log("경도(logitude) : " + lon);

                corList.push(Number(lat));
                corList.push(Number(lon));
            },
            error : function(request, status, error) {
                console.log("code:"
                + request.status + "\n"
                + "message:"
                + request.responseText
                + "\n" + "error:" + error);

                return null;
            }
        });

        return corList;
    }

    function initTmap() {
        let corList = [];

        <c:forEach var="i" begin="0" end="${locDTOList.size()-1}">
			corList.push(getCoordinates('${locDTOList.get(i).siDo}', '${locDTOList.get(i).siGunGu}', '${locDTOList.get(i).roadAddr}'));
		</c:forEach>
        // console.log(corList);

		let mapInitXCor = 0;
        let mapInitYCor = 0;
        for (let x of corList) {
			mapInitXCor += x[0];
            mapInitYCor += x[1];
        }
		mapInitXCor = mapInitXCor / corList.length;
        mapInitYCor = mapInitYCor / corList.length;

        console.log(mapInitXCor, mapInitYCor);
        console.log("시작점 좌표 (x) : " + corList[0][0] + "\t\t(y) : " + corList[0][1]);
        console.log("끝점 좌표 (x) : " + corList[corList.length-1][0] + "\t\t(y) : " + corList[corList.length-1][1]);

        let passList = "";
        // 처음과 끝을 제외한 중간 값들만 추가
        for (let i = 1; i < corList.length-1; i++) {
            passList += corList[i][1] + ", " + corList[i][0] + "_";
        }
        passList = passList.slice(0, passList.length-1);
        console.log(passList);


        //lonlat 인스턴스 생성.
        var lonlat =  new Tmapv2.LatLng(corList[0][0], corList[0][1]);
        var lonlat2 = new Tmapv2.LatLng(corList[corList.length-1][0], corList[corList.length-1][1]);
        //bounds 인스턴스를 생성합니다.
        var latlngBounds = new Tmapv2.LatLngBounds(lonlat, lonlat2);
        // 1. 지도 띄우기
        // 지도 센터는 (시작지점 + 도착지점 )/2로 계산하기
        // 줌 레벨은 어떻게 설정할 건지 생각해보기
        map = new Tmapv2.Map("map_div", {
            center : new Tmapv2.LatLng(corList[0][0], corList[0][1]),
            width : "100%",
            height : "600px",
			zoom: 13,
            zoomControl : true,
            scrollwheel : true
        });
        // map.fitBounds(latlngBounds);
        // map.zoomToMaxExtent( );

        // 2. 시작, 경유지, 도착 심볼찍기
        // 시작
        marker_s = new Tmapv2.Marker(
            {
                position : new Tmapv2.LatLng(corList[0][0], corList[0][1]),
                icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png",
                iconSize : new Tmapv2.Size(24, 38),
                map : map
            });

        // 경유지 심볼 찍기

        for (let i = 1; i < corList.length - 1; i++) {
            marker = new Tmapv2.Marker({
                position : new Tmapv2.LatLng(corList[i][0], corList[i][1]),
                icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_" + i + ".png",
                iconSize : new Tmapv2.Size(24, 38),
                map:map
            });
            console.log(i + "번째 경유지 추가!");
            console.log("좌표 : " + corList[i][0] + ", " + corList[i][1]);
            resultMarkerArr.push(marker);
        }

        //도착
        marker_e = new Tmapv2.Marker(
            {
                position : new Tmapv2.LatLng(corList[corList.length-1][0], corList[corList.length-1][1]),
                icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png",
                iconSize : new Tmapv2.Size(24, 38),
                map : map
            });

        // 3. 경로탐색 API 사용요청
        $("#btn_select")
            .click(
                function() {

                    //기존 맵에 있던 정보들 초기화
                    resettingMap();

                    var searchOption = $("#selectLevel").val();
                    let nullLocIdx = [];

                    // var trafficInfochk = $("#year").val();
                    var trafficInfochk = 'N';

                    //JSON TYPE EDIT [S]
                    $.ajax({
                            type : "POST",
                            url : "https://apis.openapi.sk.com/tmap/routes?version=1&format=json&callback=result",
                            async : false,
                            data : {
                                "appKey" : "l7xx3241520698ba4a96b002203c64d57242",
                                "startX" : corList[0][1],
                                "startY" : corList[0][0],
								"startName" : "${locDTOList.get(0).loc_name}",
                                "endX" : corList[corList.length-1][1],
                                "endY" : corList[corList.length-1][0],
								"endName" : "${locDTOList.get(locDTOList.size()-1).loc_name}",
								"passList" : passList,
                                "reqCoordType" : "WGS84GEO",
                                "resCoordType" : "EPSG3857",
                                "searchOption" : searchOption,
                                "trafficInfo" : trafficInfochk
                            },
                            success : function(response) {

                                var resultData = response.features;

                                var tDistance = "총 거리 : "
                                    + (resultData[0].properties.totalDistance / 1000)
                                        .toFixed(1) + "km";
                                var tTime = " 총 시간 : "
                                    + (resultData[0].properties.totalTime / 60)
                                        .toFixed(0) + "분";
                                var tFare = " 총 요금 : "
                                    + resultData[0].properties.totalFare.toLocaleString('ko-KR')
                                    + "원";
                                var taxiFare = " 예상 택시 요금 : "
                                    + resultData[0].properties.taxiFare.toLocaleString('ko-KR')
                                    + "원";

                                // $("#result").text(
                                // tDistance + tTime + tFare
                                // + taxiFare);
								if (document.getElementById("table").hasChildNodes()) {
                                    document.getElementById("table").deleteRow(-1);
                                }
                                let row = document.getElementById("table").insertRow(0);
                                let cellAry = [];
                                for (let i = 0; i < 4; i++) {
									let cell = row.insertCell(i);
                                    if (i === 0) cell.innerText = tDistance;
                                    else if (i === 1) cell.innerText = tTime;
                                    else if (i === 2) cell.innerText = tFare;
                                    else cell.innerText = taxiFare;
                                    cellAry.push(cell);
                                }
                                
                                <c:choose>
									<c:when test="${!empty nullLocIdxList}">
		                                // console.log("nullLocIdxList is not empty")

										<c:forEach var="i" begin="0" end="${nullLocIdxList.size()-1}">
											nullLocIdx.push(Number(${nullLocIdxList.get(i) + 1}));
										</c:forEach>

										console.log(nullLocIdx);
										let tempString = "[ ";
										for (let i = 0; i < nullLocIdx.length; i++) {
											tempString += nullLocIdx[i] + ", ";
										}
                                        tempString = tempString.slice(0, tempString.length-2);
                                        tempString += " ]";
                                		$("#nullLocationWarning").text(tempString + "번째 장소들은 삭제되어 제외되었습니다.");
									</c:when>
								</c:choose>

                                let iterationIndex = 0;

                                //교통정보 표출 옵션값을 체크
                                if (trafficInfochk == "Y") {
                                    for ( var i in resultData) { //for문 [S]
                                        var geometry = resultData[i].geometry;
                                        var properties = resultData[i].properties;

                                        if (geometry.type == "LineString") {
                                            //교통 정보도 담음
                                            chktraffic
                                                .push(geometry.traffic);
                                            var sectionInfos = [];
                                            var trafficArr = geometry.traffic;

                                            for ( var j in geometry.coordinates) {
                                                // 경로들의 결과값들을 포인트 객체로 변환
                                                var latlng = new Tmapv2.Point(
                                                    geometry.coordinates[j][0],
                                                    geometry.coordinates[j][1]);
                                                // 포인트 객체를 받아 좌표값으로 변환
                                                var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                                    latlng);

                                                sectionInfos
                                                    .push(convertPoint);
                                            }

                                            drawLine(sectionInfos,
                                                trafficArr);
                                        } else {

                                            var markerImg = "";
                                            var pType = "";
                                            var count = 1;

                                            if (properties.pointType == "S") { //출발지 마커
                                                markerImg = "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png";
                                                pType = "S";
                                            } else if (properties.pointType == "E") { //도착지 마커
                                                markerImg = "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png";
                                                pType = "E";
                                            } else { //각 포인트 마커
                                                markerImg = "http://topopen.tmap.co.kr/imgs/point.png";
                                                pType = "P"
                                            }

                                            // 경로들의 결과값들을 포인트 객체로 변환
                                            var latlon = new Tmapv2.Point(
                                                geometry.coordinates[0],
                                                geometry.coordinates[1]);
                                            // 포인트 객체를 받아 좌표값으로 다시 변환
                                            var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                                latlon);

                                            var routeInfoObj = {
                                                markerImage : markerImg,
                                                lng : convertPoint._lng,
                                                lat : convertPoint._lat,
                                                pointType : pType
                                            };
                                            // 마커 추가
                                            addMarkers(routeInfoObj);
                                        }
                                    }//for문 [E]

                                } else {

                                    for ( var i in resultData) { //for문 [S]
                                        var geometry = resultData[i].geometry;
                                        var properties = resultData[i].properties;

                                        if (geometry.type == 'LineString') {
                                            for ( var j in geometry.coordinates) {
// 경로들의 결과값들을 포인트 객체로 변환
                                                var latlng = new Tmapv2.Point(
                                                    geometry.coordinates[j][0],
                                                    geometry.coordinates[j][1]);
// 포인트 객체를 받아 좌표값으로 변환
                                                var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                                    latlng);
// 포인트객체의 정보로 좌표값 변환 객체로 저장
                                                var convertChange = new Tmapv2.LatLng(
                                                    convertPoint._lat,
                                                    convertPoint._lng);
// 배열에 담기
                                                if(lineYn){
                                                    drawInfoArr.push(convertChange);
                                                }

                                            }
                                            drawLine(drawInfoArr,'0');
                                        } else {
                                            var markerImg = '';
                                            var pType = '';

                                            if(properties.pointType == 'S' || properties.pointType == 'E' || properties.pointType == 'N'){
                                                lineYn = true;
                                            }else{
                                                lineYn = false;
                                            }

                                            if (properties.pointType == 'S') { //출발지 마커
                                                markerImg = 'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png';
                                                    pType = 'S';
                                            } else if (properties.pointType == 'E') { //도착지 마커
                                                markerImg = 'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png';
                                                    pType = 'E';
                                            } else if (properties.pointType.includes('B')) {
                                                if (properties.index !== undefined) {
                                                    iterationIndex++;
                                                    markerImg = "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_" + iterationIndex + ".png";
                                                    pType = 'B';
                                                } else {
                                                    continue;
                                                }
											} else { //각 포인트 마커
                                                markerImg = 'http://topopen.tmap.co.kr/imgs/point.png';
                                                pType = 'P'
                                            }

                                            // 경로들의 결과값들을 포인트 객체로 변환
                                            var latlon = new Tmapv2.Point(
                                                geometry.coordinates[0],
                                                geometry.coordinates[1]);
                                            // 포인트 객체를 받아 좌표값으로 다시 변환
                                            var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                                latlon);

                                            var routeInfoObj = {
                                                markerImage : markerImg,
                                                lng : convertPoint._lng,
                                                lat : convertPoint._lat,
                                                pointType : pType
                                            };

                                            // Marker 추가
                                            addMarkers(routeInfoObj);
                                        }
                                    }//for문 [E]
                                }
                            },
                            error : function(request, status, error) {
                                console.log("code:"
                                + request.status + "\n"
                                + "message:"
                                + request.responseText
                                + "\n" + "error:" + error);
                            }
                        });
                    //JSON TYPE EDIT [E]
                });
    }

    function addComma(num) {
        var regexp = /\B(?=(\d{3})+(?!\d))/g;
        return num.toString().replace(regexp, ',');
    }

    //마커 생성하기
    function addMarkers(infoObj) {
        var size = new Tmapv2.Size(24, 38);//아이콘 크기 설정합니다.

        if (infoObj.pointType == "P") { //포인트점일때는 아이콘 크기를 줄입니다.
            size = new Tmapv2.Size(8, 8);
        }

        marker_p = new Tmapv2.Marker({
            position : new Tmapv2.LatLng(infoObj.lat, infoObj.lng),
            icon : infoObj.markerImage,
            iconSize : size,
            map : map
        });

        resultMarkerArr.push(marker_p);
    }

    //라인그리기
    function drawLine(arrPoint, traffic) {
        var polyline_;

        if (chktraffic.length != 0) {

            // 교통정보 혼잡도를 체크
            // strokeColor는 교통 정보상황에 다라서 변화
            // traffic :  0-정보없음, 1-원활, 2-서행, 3-지체, 4-정체  (black, green, yellow, orange, red)

            var lineColor = "";

            if (traffic != "0") {
                if (traffic.length == 0) { //length가 0인것은 교통정보가 없으므로 검은색으로 표시

                    lineColor = "#06050D";
                    //라인그리기[S]
                    polyline_ = new Tmapv2.Polyline({
                        path : arrPoint,
                        strokeColor : lineColor,
                        strokeWeight : 6,
                        map : map
                    });
                    resultdrawArr.push(polyline_);
                    //라인그리기[E]
                } else { //교통정보가 있음

                    if (traffic[0][0] != 0) { //교통정보 시작인덱스가 0이 아닌경우
                        var trafficObject = "";
                        var tInfo = [];

                        for (var z = 0; z < traffic.length; z++) {
                            trafficObject = {
                                "startIndex" : traffic[z][0],
                                "endIndex" : traffic[z][1],
                                "trafficIndex" : traffic[z][2],
                            };
                            tInfo.push(trafficObject)
                        }

                        var noInfomationPoint = [];

                        for (var p = 0; p < tInfo[0].startIndex; p++) {
                            noInfomationPoint.push(arrPoint[p]);
                        }

                        //라인그리기[S]
                        polyline_ = new Tmapv2.Polyline({
                            path : noInfomationPoint,
                            strokeColor : "#06050D",
                            strokeWeight : 6,
                            map : map
                        });
                        //라인그리기[E]
                        resultdrawArr.push(polyline_);

                        for (var x = 0; x < tInfo.length; x++) {
                            var sectionPoint = []; //구간선언

                            for (var y = tInfo[x].startIndex; y <= tInfo[x].endIndex; y++) {
                                sectionPoint.push(arrPoint[y]);
                            }

                            if (tInfo[x].trafficIndex == 0) {
                                lineColor = "#06050D";
                            } else if (tInfo[x].trafficIndex == 1) {
                                lineColor = "#61AB25";
                            } else if (tInfo[x].trafficIndex == 2) {
                                lineColor = "#FFFF00";
                            } else if (tInfo[x].trafficIndex == 3) {
                                lineColor = "#E87506";
                            } else if (tInfo[x].trafficIndex == 4) {
                                lineColor = "#D61125";
                            }

                            //라인그리기[S]
                            polyline_ = new Tmapv2.Polyline({
                                path : sectionPoint,
                                strokeColor : lineColor,
                                strokeWeight : 6,
                                map : map
                            });
                            //라인그리기[E]
                            resultdrawArr.push(polyline_);
                        }
                    } else { //0부터 시작하는 경우

                        var trafficObject = "";
                        var tInfo = [];

                        for (var z = 0; z < traffic.length; z++) {
                            trafficObject = {
                                "startIndex" : traffic[z][0],
                                "endIndex" : traffic[z][1],
                                "trafficIndex" : traffic[z][2],
                            };
                            tInfo.push(trafficObject)
                        }

                        for (var x = 0; x < tInfo.length; x++) {
                            var sectionPoint = []; //구간선언

                            for (var y = tInfo[x].startIndex; y <= tInfo[x].endIndex; y++) {
                                sectionPoint.push(arrPoint[y]);
                            }

                            if (tInfo[x].trafficIndex == 0) {
                                lineColor = "#06050D";
                            } else if (tInfo[x].trafficIndex == 1) {
                                lineColor = "#61AB25";
                            } else if (tInfo[x].trafficIndex == 2) {
                                lineColor = "#FFFF00";
                            } else if (tInfo[x].trafficIndex == 3) {
                                lineColor = "#E87506";
                            } else if (tInfo[x].trafficIndex == 4) {
                                lineColor = "#D61125";
                            }

                            //라인그리기[S]
                            polyline_ = new Tmapv2.Polyline({
                                path : sectionPoint,
                                strokeColor : lineColor,
                                strokeWeight : 6,
                                map : map
                            });
                            //라인그리기[E]
                            resultdrawArr.push(polyline_);
                        }
                    }
                }
            } else {

            }
        } else {
            polyline_ = new Tmapv2.Polyline({
                path : arrPoint,
                strokeColor : "#DD0000",
                strokeWeight : 6,
                map : map
            });
            resultdrawArr.push(polyline_);
        }

    }

    //초기화 기능
    function resettingMap() {
        //기존마커는 삭제
        marker_s.setMap(null);
        marker_e.setMap(null);

        if (resultMarkerArr.length > 0) {
            for (var i = 0; i < resultMarkerArr.length; i++) {
                resultMarkerArr[i].setMap(null);
            }
        }

        if (resultdrawArr.length > 0) {
            for (var i = 0; i < resultdrawArr.length; i++) {
                resultdrawArr[i].setMap(null);
            }
        }

        chktraffic = [];
        drawInfoArr = [];
        resultMarkerArr = [];
        resultdrawArr = [];
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
</html>
