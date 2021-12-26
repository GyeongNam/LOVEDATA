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
                            <p><a href="/ServiceCenter/Policy" class="highlight-not-selected-text-menu ">- LOVEDATA 정책</a></p>
                            <p><a href="/ServiceCenter/Withdrawal" class="highlight-selected-text-menu">- 회원 탈퇴</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </ul>
    </div>
    <div class="container-fluid" id="display_center" style="margin-right: 30px">
        <div id="Withdrawal" class="tabcontent">
            <h3>회원 탈퇴</h3>
            <div class="card" style="padding:20px; border-radius: 15px; margin: 20px auto;">
            <span><p><span style="color: rgb(148, 72, 195); font-family: &quot;맑은 고딕&quot;; font-size: 11pt; font-weight: bold; text-indent: 0in;">탈퇴
            안내</span>​</p><p><span style="font-size: 8pt; font-family: &quot;맑은 고딕&quot;; color: rgb(119, 119, 119);">회원탈퇴를 신청하기 전에 안내 사항을 꼭 확인해주세요</span><span style="font-size: 8pt; font-family: the; color: rgb(119, 119, 119);">.</span><span style="font-size: 8pt; font-family: the; color: rgb(241, 231, 247);">.</span>​</p><p><span style="font-size: 10pt; font-family: &quot;맑은 고딕&quot;; color: rgb(148, 72, 195); font-weight: bold;">사용하고 계신 아이디</span><span style="font-size: 10pt; font-family: the; color: rgb(148, 72, 195); font-weight: bold;"> (<sec:authentication property="principal.user_email"/>)</span><span style="font-size: 10pt; font-family: &quot;맑은 고딕&quot;; color: rgb(148, 72, 195); font-weight: bold;">는
            탈퇴할 경우 재사용 및 복구가 불가능합니다</span><span style="font-size: 10pt; font-family: the; color: rgb(148, 72, 195); font-weight: bold;">.</span></p>

            <p style="margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed;"><span style="font-size:8.0pt;
            font-family:&quot;맑은 고딕&quot;;mso-ascii-font-family:the;mso-fareast-font-family:&quot;맑은 고딕&quot;;
            mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:minor-latin;mso-fareast-theme-font:
            minor-fareast;mso-bidi-theme-font:minor-bidi;color:red;mso-font-kerning:12.0pt;
            language:ko;mso-style-textfill-type:solid;mso-style-textfill-fill-color:red;
            mso-style-textfill-fill-alpha:100.0%">탈퇴한 아이디는 본인과 타인 모두 재사용 및 복구가 </span><span style="font-size:8.0pt;font-family:&quot;맑은 고딕&quot;;mso-ascii-font-family:the;
            mso-fareast-font-family:&quot;맑은 고딕&quot;;mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:
            minor-latin;mso-fareast-theme-font:minor-fareast;mso-bidi-theme-font:minor-bidi;
            color:red;mso-font-kerning:12.0pt;language:ko;mso-style-textfill-type:solid;
            mso-style-textfill-fill-color:red;mso-style-textfill-fill-alpha:100.0%">불가</span><span style="font-size:8.0pt;font-family:&quot;맑은 고딕&quot;;mso-ascii-font-family:the;
            mso-fareast-font-family:&quot;맑은 고딕&quot;;mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:
            minor-latin;mso-fareast-theme-font:minor-fareast;mso-bidi-theme-font:minor-bidi;
            color:#777777;mso-font-kerning:12.0pt;language:ko;mso-style-textfill-type:solid;
            mso-style-textfill-fill-color:#777777;mso-style-textfill-fill-alpha:100.0%">하오니</span><span style="font-size:8.0pt;font-family:the;mso-ascii-font-family:the;mso-fareast-font-family:
            &quot;맑은 고딕&quot;;mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:minor-latin;
            mso-fareast-theme-font:minor-fareast;mso-bidi-theme-font:minor-bidi;color:#777777;
            mso-font-kerning:12.0pt;language:ko;mso-style-textfill-type:solid;mso-style-textfill-fill-color:
            #777777;mso-style-textfill-fill-alpha:100.0%"> 신중하게 </span><span style="font-size:8.0pt;font-family:&quot;맑은 고딕&quot;;mso-ascii-font-family:the;
            mso-fareast-font-family:&quot;맑은 고딕&quot;;mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:
            minor-latin;mso-fareast-theme-font:minor-fareast;mso-bidi-theme-font:minor-bidi;
            color:#777777;mso-font-kerning:12.0pt;language:ko;mso-style-textfill-type:solid;
            mso-style-textfill-fill-color:#777777;mso-style-textfill-fill-alpha:100.0%">선텍하시기</span><span style="font-size:8.0pt;font-family:the;mso-ascii-font-family:the;mso-fareast-font-family:
            &quot;맑은 고딕&quot;;mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:minor-latin;
            mso-fareast-theme-font:minor-fareast;mso-bidi-theme-font:minor-bidi;color:#777777;
            mso-font-kerning:12.0pt;language:ko;mso-style-textfill-type:solid;mso-style-textfill-fill-color:
            #777777;mso-style-textfill-fill-alpha:100.0%"> 바랍니다</span><span style="font-size:8.0pt;font-family:the;mso-ascii-font-family:the;mso-fareast-font-family:
            &quot;맑은 고딕&quot;;mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:minor-latin;
            mso-fareast-theme-font:minor-fareast;mso-bidi-theme-font:minor-bidi;color:#777777;
            mso-font-kerning:12.0pt;language:en-US;mso-style-textfill-type:solid;
            mso-style-textfill-fill-color:#777777;mso-style-textfill-fill-alpha:100.0%">.</span></p>

            <p style="margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed;">&nbsp;</p>

            <p style="margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed;"><span style="font-size:10.0pt;
            font-family:&quot;맑은 고딕&quot;;mso-ascii-font-family:the;mso-fareast-font-family:&quot;맑은 고딕&quot;;
            mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:minor-latin;mso-fareast-theme-font:
            minor-fareast;mso-bidi-theme-font:minor-bidi;color:#9448C3;mso-font-kerning:
            12.0pt;language:ko;font-weight:bold;mso-style-textfill-type:solid;mso-style-textfill-fill-color:
            #9448C3;mso-style-textfill-fill-alpha:100.0%">탈퇴 후 회원정보 및 개인형 서비스 이용기록은 모두
            삭제됩니다</span><span style="font-size:10.0pt;font-family:the;mso-ascii-font-family:
            the;mso-fareast-font-family:&quot;맑은 고딕&quot;;mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:
            minor-latin;mso-fareast-theme-font:minor-fareast;mso-bidi-theme-font:minor-bidi;
            color:#9448C3;mso-font-kerning:12.0pt;language:en-US;font-weight:bold;
            mso-style-textfill-type:solid;mso-style-textfill-fill-color:#9448C3;mso-style-textfill-fill-alpha:
            100.0%">.</span></p>

            <p style="margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed;"><span style="font-size:8.0pt;
            font-family:&quot;맑은 고딕&quot;;mso-ascii-font-family:the;mso-fareast-font-family:&quot;맑은 고딕&quot;;
            mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:minor-latin;mso-fareast-theme-font:
            minor-fareast;mso-bidi-theme-font:minor-bidi;color:red;mso-font-kerning:12.0pt;
            language:ko;font-weight:bold;mso-style-textfill-type:solid;mso-style-textfill-fill-color:
            red;mso-style-textfill-fill-alpha:100.0%">틸퇴</span><span style="font-size:8.0pt;
            font-family:the;mso-ascii-font-family:the;mso-fareast-font-family:&quot;맑은 고딕&quot;;
            mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:minor-latin;mso-fareast-theme-font:
            minor-fareast;mso-bidi-theme-font:minor-bidi;color:red;mso-font-kerning:12.0pt;
            language:ko;font-weight:bold;mso-style-textfill-type:solid;mso-style-textfill-fill-color:
            red;mso-style-textfill-fill-alpha:100.0%"> 후에는&nbsp;
            아이디 </span><span style="font-size: 8pt; font-family: the; font-weight: bold;"><sec:authentication property="principal.user_email"/></span><span style="font-size:8.0pt;font-family:&quot;맑은 고딕&quot;;mso-ascii-font-family:the;
            mso-fareast-font-family:&quot;맑은 고딕&quot;;mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:
            minor-latin;mso-fareast-theme-font:minor-fareast;mso-bidi-theme-font:minor-bidi;
            color:red;mso-font-kerning:12.0pt;language:ko;font-weight:bold;mso-style-textfill-type:
            solid;mso-style-textfill-fill-color:red;mso-style-textfill-fill-alpha:100.0%">로
            다시 가입할 수 없으며 아이디와 데이터는 복구할 수 없습니다</span><span style="font-size:8.0pt;
            font-family:the;mso-ascii-font-family:the;mso-fareast-font-family:&quot;맑은 고딕&quot;;
            mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:minor-latin;mso-fareast-theme-font:
            minor-fareast;mso-bidi-theme-font:minor-bidi;color:red;mso-font-kerning:12.0pt;
            language:en-US;font-weight:bold;mso-style-textfill-type:solid;mso-style-textfill-fill-color:
            red;mso-style-textfill-fill-alpha:100.0%">.</span></p>

            <p style="margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed;"><span style="font-size:8.0pt;
            font-family:&quot;맑은 고딕&quot;;mso-ascii-font-family:the;mso-fareast-font-family:&quot;맑은 고딕&quot;;
            mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:minor-latin;mso-fareast-theme-font:
            minor-fareast;mso-bidi-theme-font:minor-bidi;color:red;mso-font-kerning:12.0pt;
            language:ko;font-weight:bold;mso-style-textfill-type:solid;mso-style-textfill-fill-color:
            red;mso-style-textfill-fill-alpha:100.0%">게시판형 서비스에 남아 있는 게시글은 탈퇴 후 삭제할 수 없습니다</span><span style="font-size:8.0pt;font-family:the;mso-ascii-font-family:the;mso-fareast-font-family:
            &quot;맑은 고딕&quot;;mso-bidi-font-family:+mn-cs;mso-ascii-theme-font:minor-latin;
            mso-fareast-theme-font:minor-fareast;mso-bidi-theme-font:minor-bidi;color:red;
            mso-font-kerning:12.0pt;language:en-US;font-weight:bold;mso-style-textfill-type:
            solid;mso-style-textfill-fill-color:red;mso-style-textfill-fill-alpha:100.0%">.</span></p>

            <p style="margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed;">&nbsp;</p></span>
            </div>
                <div class="card" style="padding:20px; border-radius: 15px; margin: 20px auto;">
                    <spen id="s_relult">비밀번호 확인</spen>
                    <input type="password" id="password_ck"/>
                    <button id="password_ck_b" onclick="password_ck()">확인</button>
                </div>
        </div>
        <form method="post" action="/lovedata_delete" id="lovedata_delete">
            <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
            <input type="hidden" name="user_no" id="user_no" value="<sec:authentication property="principal.user_no"/>"/>
            <button id="userOut" onclick="lovedata_delete()" >(임시)회원탈퇴</button>
        </form>
    </div>
</div>
</body>
<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script defer src="/js/ServiceCenter.js"></script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

    body {
        font-family: 'Jua', sans-serif;
    }
</style>
</html>