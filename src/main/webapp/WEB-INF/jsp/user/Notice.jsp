<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
    <link href="/css/Notice.css" rel="stylesheet">
    <title>Home</title>
</head>
<%@ include file="../layout/header.jsp"%>
<body>
<div id="jb-container">
    <div id="jb-header">
        <h1>고객센터</h1>
    </div>
    <div id="jb-sidebar">
        <div class="tab">

            <button class="tablinks" onclick="MenuTab(event, 'Notice')" id="defaultOpen">공지사항</button>
            <button class="tablinks" onclick="MenuTab(event, 'Questions')">문의사항</button>
            <button class="tablinks" onclick="MenuTab(event, 'Policy')">LOVE DATA 정책</button>
            <button class="tablinks" onclick="MenuTab(event, 'Withdrawal')">회원탈퇴</button>
        </div>
    </div>
    <div id="jb-content">
        <div id="Notice" class="tabcontent">
            <div class="table-head">
                <div class="Notice-head">
                    공지사항
                </div>
                <div class="Nsearch">
                    <select class="selitm">
                        <option value="" selected>제목+내용</option>
                        <option value="">조회수</option>
                        <option value="">등록일</option>
                    </select>
                    <input type="text" class="Notice-search"><button>검색</button>
                </div>
            </div>

            <table>
                <thead>
                <tr>
                    <th>번호</th><th>제목</th><th>등록일</th><th>첨부파일</th><th>조회수</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>인생 맛집 투어</td>
                    <td><span>2022-01-01</span></td>
                    <td></td>
                    <td>123</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td>인생 맛집 투어</td>
                    <td><span>2022-01-01</span></td>
                    <td></td>
                    <td>123</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td>인생 맛집 투어</td>
                    <td><span>2022-01-01</span></td>
                    <td></td>
                    <td>123</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td>인생 맛집 투어</td>
                    <td><span>2022-01-01</span></td>
                    <td></td>
                    <td>123</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td>인생 맛집 투어</td>
                    <td><span>2022-01-01</span></td>
                    <td></td>
                    <td>123</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td>인생 맛집 투어</td>
                    <td><span>2022-01-01</span></td>
                    <td></td>
                    <td>123</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td>인생 맛집 투어</td>
                    <td><span>2022-01-01</span></td>
                    <td></td>
                    <td>123</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td>인생 맛집 투어</td>
                    <td><span>2022-01-01</span></td>
                    <td></td>
                    <td>123</td>
                </tr>
                </tbody>
            </table>
            <div class="pagination">
                <a href="#">&laquo;</a>
                <a href="#">1</a>
                <a href="#">&raquo;</a>
            </div>
        </div>
        <div id="Questions" class="tabcontent">
            <h3>문의 사항</h3>
            <table>
                <thead>
                <tr>
                    <th>번호</th><th>제목</th><th>작성자</th><th>접수일</th><th>상태</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>아니 코스가 잘못된거같아요..</td>
                    <td>도루**</td>
                    <td>2021/05/24</td>
                    <td>접수중</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>코스 수정해주세요!</td>
                    <td>조마*</td>
                    <td>2021/05/11</td>
                    <td>접수완료</td>
                </tr>
                </tbody>
            </table>
            <div class="pagination">
                <a href="#">&laquo;</a>
                <a href="#">1</a>
                <a href="#">&raquo;</a>
            </div>
        </div>
        <div id="Policy" class="tabcontent">
            <h3>LOVE DATA 정책</h3>
            <div class="Ptab">
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'terms')"id="FirstTabOpen">이용약관</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Privacy')">개인정보처리방침</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'SpamMail')">스팸메일정책</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'LegalNotice')">책임의 한계와 법적고지</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Results')">검색결과수집에 대한 정책</button>
            </div>

            <div id="terms" class="Ptabcontent">
                <h3>terms</h3>
                <p>London is the capital city of England.</p>
            </div>

            <div id="Privacy" class="Ptabcontent">
                <h3>Privacy</h3>
                <p>Paris is the capital of France.</p>
            </div>

            <div id="SpamMail" class="Ptabcontent">
                <h3>SpamMail</h3>
                <p>Tokyo is the capital of Japan.</p>
            </div>
            <div id="LegalNotice" class="Ptabcontent">
                <h3>LegalNotice</h3>
                <p>Tokyo is the capital of Japan.</p>
            </div>
            <div id="Results" class="Ptabcontent">
                <h3>Results</h3>
                <p>Tokyo is the capital of Japan.</p>
            </div>
        </div>
        <div id="Withdrawal" class="tabcontent">
            <h3>회원 탈퇴</h3>
        </div>
    </div>
</div>
</body>
<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/notice.js"></script>
</html>