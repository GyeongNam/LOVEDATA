<%@ page import="org.springframework.security.core.annotation.AuthenticationPrincipal" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link href="/css/ServiceCenter.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/service/loc.css">

    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <title>LOVEDATA:ServiceCenter</title>
</head>
<%@ include file="../layout/header.jsp"%>
<body>
<div class="container-fluid d-flex" style="padding-top: 100px">
    <div class="col-2" id="sidebar">
        <ul class="nav nav-pills flex-column col-2 position-fixed" style="top: 40%">
            <div class="accordion text-center" id="loc">
                <hr>
                <div class="card">
                    <div class="card-header" id="headingLoc">
                        <h2 class="mb-0">
                            <button class="btn btn-link btn-block" type="button" data-toggle="collapse"
                                    data-target="#loc_collapse" aria-expanded="true" aria-controls="collapseOne"
                                    style="text-decoration: none; color: #FF6699; font-weight: bold">
                                고객센터
                            </button>
                        </h2>
                    </div>
                    <div id="loc_collapse" class="collapse show" aria-labelledby="headingLoc" data-parent="#loc">
                        <div class="card-body center-pill">
                            <p><a href="/ServiceCenter/Notice/1" class="highlight-not-selected-text-menu ">- 공지 사항</a></p>
                            <p><a href="/ServiceCenter/Questions/1" class="highlight-not-selected-text-menu">- 문의 사항</a></p>
                            <p><a href="/ServiceCenter/Event/1" class="highlight-not-selected-text-menu">- 이벤트</a></p>
                            <p><a href="/ServiceCenter/Policy" class="highlight-selected-text-menu">- LOVEDATA 정책</a></p>
                            <p><a href="/ServiceCenter/Withdrawal" class="highlight-not-selected-text-menu">- 회원 탈퇴</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </ul>
    </div>
    <div class="container-fluid" id="display_center" style="margin-right: 30px">
        <div class="col" id="top_navbar">
            <nav class="navbar navbar-expand-sm navbar-light static-top">
                <h3 class="mx-3">LOVE DATA 정책</h3>
            </nav>
        </div>
        <div id="Policy" class="tabcontent">
            <div class="Ptab">
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy1')"id="open">총직</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy2')">개인정보처리방침</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy3')">이용계약 당사자의 의무</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy4')">서비스 이용 및 이용제한</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy5')">청약철회, 과오납금의 환급 및 이용계약의 해지</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy6')">손해배상 및 면책조항</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy7')">제휴 맺는법</button>
            </div>
            <div id="Policy1" class="Ptabcontent">
                <h3>${Chapter1title}</h3>
                <p>${Article1}</p>

                <p>${Article2}</p>

                <p>${Article3}</p>

                <p>${Article4}</p>

                <p>${Article5}</p>

                <p>${Article6}</p>

                <p>${Article7}</p>
            </div>

            <div id="Policy2" class="Ptabcontent">
                <h3>${Chapter2title}</h3>
                <p>${Article8}</p>

            </div>

            <div id="Policy3" class="Ptabcontent">
                <h3>${Chapter3title}</h3>
                <p>${Article9}</p>

                <p>${Article10}</p>
            </div>
            <div id="Policy4" class="Ptabcontent">
                <h3>${Chapter4title}</h3>
                <p>${Article11}</p>

                <p>${Article12}</p>

                <p>${Article13}</p>

                <p>${Article14}</p>

                <p>${Article15}</p>

                <p>${Article16}</p>

                <p>${Article17}</p>

                <p>${Article18}</p>

                <p>${Article19}</p>

                <p>${Article20}</p>
            </div>
            <div id="Policy5" class="Ptabcontent">
                <h3>${Chapter5title}</h3>
                <p>${Article21}</p>

                <p>${Article22}</p>

                <p>${Article23}</p>

                <p>${Article24}</p>
            </div>
            <div id="Policy6" class="Ptabcontent">
                <h3>${Chapter6title}</h3>
                <p>${Article25}</p>

                <p>${Article26}</p>

                <p>${Article27}</p>

                <p>${Article28}</p>

                <p>${Article29}</p>
            </div>
            <div id="Policy7" class="Ptabcontent">
     <span style="font-size: 10pt;">● 제휴 절차 안내</span>&nbsp;</p><table style="background-color: rgb(166, 188, 209);" class="__se_tbl" border="0" cellpadding="0" cellspacing="1" _se2_tbl_template="16"><tbody>
<tr><td style="width: 151px; height: 127px; padding: 3px 4px 2px; color: rgb(255, 255, 255); text-align: left; font-weight: normal; background-color: rgb(201, 224, 240);" class=""><p style="text-align: center; " align="center"><span style="font-size: 10pt; color: rgb(0, 0, 0);">&nbsp;STEP 1</span></p></td>
<td style="width: 1110px; height: 127px; padding: 3px 4px 2px; color: rgb(61, 118, 171); background-color: rgb(255, 255, 255);" class=""><p style="text-align: left;" align="left"><strong style="color: rgb(85, 85, 85); font-family: dotum, sans-serif;"><span style="font-size: 10pt;">제휴 등록</span></strong></p><p style="text-align: left;" align="left"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">- LOVE DATA 사이트 내 문의사항을 통해 제휴 문의(ex) OOO 제휴 문의합니다.)를 등록합니다.</span></p><p style="text-align: left;" align="left"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif;"></span><span style="color: rgb(0, 0, 0); font-size: 10pt;">- 문의사항을 통해 제휴 문의를 등록할 시 아래와 같은 사항을 포함하여 작성해주시기 바랍니다.</span></p><p style="text-align: left;" align="left"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">&nbsp;1) 제휴 업체의 업종​(ex) 음식점)</span></p><p style="text-align: left;" align="left"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">&nbsp;2) 제휴 업체가 원하는 제휴 구분(ex) 이벤트 프로모션, 멤버십 제안, 홍보)</span></p><p style="text-align: left;" align="left"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">&nbsp;3) 회사명</span></p><p style="text-align: left;" align="left"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">&nbsp;4) 담당자명(이름)</span></p><p style="text-align: left;" align="left"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">&nbsp;5) 제휴 업체 주소</span></p><p style="text-align: left;" align="left"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">&nbsp;6) 담당자 연락처 및 이메일 주소</span></p><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;"><div style="text-align: left;" align="left"><span style="font-size: 10pt;">- 문의사항을 통해 제휴 문의를 등록 하시면 제휴 문의가 완료된것입니다.</span></div></span><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;"><div style="text-align: left;" align="left"><span style="font-size: 10pt;">- 특허 및 디자인, 저작권 등 각종 지식재산권의 공개 내지 공유가 필요한 요청의 경우 반드시 권리 등록을</span></div></span><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;"><div style="text-align: left;" align="left"><span style="font-size: 10pt;">완료하시고 당사로 제안요청을 하여 주시기 바랍니다.</span></div></span><p style="text-align: left;" align="left"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">- 제휴는 제안자의 영업비밀이나 기밀사항에 해당하지 않는 ‘공개 가능한 내용’만 서술하여주시기 바랍니다.</span><span style="font-size: 10pt;">&nbsp;</span></p></td>
</tr>
<tr><td style="width: 151px; height: 87px; padding: 3px 4px 2px; color: rgb(255, 255, 255); text-align: left; font-weight: normal; background-color: rgb(201, 224, 240);" class=""><p style="text-align: center; "><span style="font-size: 10pt; color: rgb(0, 0, 0);">&nbsp;</span><span style="font-size: 10pt; color: rgb(0, 0, 0);"> STEP 2</span></p><p>&nbsp;</p></td>
<td style="width: 1110px; height: 87px; padding: 3px 4px 2px; color: rgb(61, 118, 171); background-color: rgb(255, 255, 255);" class=""><p style="text-align: left;"><strong style="color: rgb(85, 85, 85); font-family: dotum, sans-serif;"><span style="font-size: 10pt;">제휴 검토</span></strong></p><p style="text-align: left;"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">- 문의사항을 통해 제휴 문의를 등록 하시면 담당자가 등록하신 제안 내용을 면밀히 검토합니다.</span><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">담당자가 내용을 검토 후 필요한 자료가</span></p><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;"><div style="text-align: left;"><span style="font-size: 10pt;">있는 경우 재문의를 드릴 수 있습니다.</span></div></span><p style="text-align: left;"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">- 제휴 내용 및 관련자료는 제휴 검토 목적으로만 사용됩니다.</span>&nbsp;</p></td>
</tr>
<tr><td style="width: 151px; height: 118px; padding: 3px 4px 2px; color: rgb(255, 255, 255); text-align: left; font-weight: normal; background-color: rgb(201, 224, 240);" class=""><p style="text-align: center;"><span style="font-size: 10pt; color: rgb(0, 0, 0);">&nbsp;</span><span style="text-align: left; font-size: 10pt; color: rgb(0, 0, 0);">&nbsp;STEP 3</span><span style="font-size: 10pt; color: rgb(0, 0, 0);">​</span></p><p style="text-align: center;">&nbsp;</p></td>
<td style="width: 1110px; height: 118px; padding: 3px 4px 2px; color: rgb(61, 118, 171); background-color: rgb(255, 255, 255);" class=""><p style="text-align: left;" align="left"><span style="font-size: 10pt;">&nbsp;</span><strong style="color: rgb(85, 85, 85); font-family: dotum, sans-serif;"><span style="font-size: 10pt;">제휴 종료</span></strong></p><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;"><div style="text-align: left;" align="left"><span style="font-size: 10pt;">- 제휴 내용의 검토 결과를 회신드립니다.</span></div></span><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;"><div style="text-align: left;" align="left"><span style="font-size: 10pt;">- 검토 후 회신은 입력하신 연락처 혹은 메일 주소로 회신드립니다.</span></div></span><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;"><div style="text-align: left;" align="left"><span style="font-size: 10pt;">- 제휴 진행이 결정되면, 담당자가 제휴를 위한 별도 절차를 안내하고 해당 제안 건을 종료합니다.</span></div></span><p style="text-align: left;" align="left"><span style="color: rgb(85, 85, 85); font-family: dotum, sans-serif; font-size: 10pt;">- 제휴가 받아들여지지 않는 경우 제휴 문의 내 관련자료는 즉시 파기됩니다.</span></p></td>
</tr>

</tbody>
</table><p style="text-align: center;" align="center">&nbsp;</p><p>&nbsp;</p><p><span style="color: rgb(102, 102, 102); font-family: dotum, sans-serif; font-size: 10pt;">LOVE DATA 내 제휴는 문의 사항 페이지에 접수된 건만 정식으로 검토됩니다.</span></p><p><span style="color: rgb(102, 102, 102); font-family: dotum, sans-serif; font-size: 10pt;">LOVE DATA에 좋은 의견 주셔서 감사합니다..</span></p><p>&nbsp;</p><p>&nbsp;</p><div style="text-align: center;" align="center"></div><p>&nbsp;</p></span>
                </div>
            </div>
    </div>
</div>
<input  type="hidden" id="affiliate" value="${affiliate}">
</body>
<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>

<script defer src="/js/ServiceCenter.js"></script>
<script>
    var box1_exist = document.getElementById("affiliate");
    if(box1_exist.value>0){
        document.getElementById('Policy7').style.display = "block";
    }
    else {
        document.getElementById('Policy1').style.display = "block";
    }
</script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

    body {
        font-family: 'Jua', sans-serif;
    }
</style>
</html>