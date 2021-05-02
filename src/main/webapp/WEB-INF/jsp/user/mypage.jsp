<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
    <link href="/css/mypage.css" rel="stylesheet">
    <title>Home</title>
</head>
<%@ include file="../layout/header.jsp"%>
<body>
<div id="content">
    <div class="title">마이페이지</div>
    <div id="side-menu"></div>
    <div id="user-info"></div>
    <div class="info-box">
        <div class="tab">
            <button class="tablinks" onclick="openCity(event, 'myinfo')" id="defaultOpen">내 정보</button>
            <button class="tablinks" onclick="openCity(event, 'Review')">나의 리뷰</button>
            <button class="tablinks" onclick="openCity(event, 'Myplace')">나의 코스/장소</button>
            <button class="tablinks" onclick="openCity(event, 'like')">찜 목록</button>
            <button class="tablinks" onclick="openCity(event, 'Today')">최근 본 코스</button>
        </div>
        <div class="info-content" >
            <div id="myinfo" class="tabcontent">
                <div>
                    <table class="tg">
                        <tbody class="info-tbd">
                        <tr>
                            <td class="tg-0pky">이름</td>
                            <td>
                                <input class="tg-0pky"type="text" name="name" required
                                       minlength="4" maxlength="8" size="10">
                            </td>
                        </tr>
                        <tr>
                            <td class="tg-0pky">닉네임</td>
                            <td class="tg-0lax">
                                <input class="tg-0pky"type="text" name="name" required
                                       minlength="4" maxlength="8" size="10">
                                <button>중복 확인</button>
                            </td>
                        </tr>
                        <tr>
                            <td class="tg-0pky">이메일</td>
                            <td class="tg-0pky"></td>
                        </tr>
                        <tr>
                            <td class="tg-0pky">기존비밀번호</td>
                            <td class="tg-0lax">
                                <input class="tg-0pky"type="password" name="name" required
                                       minlength="4" maxlength="8" size="10">
                            </td>
                        </tr>
                        <tr>
                            <td class="tg-0lax">새 비밀번호</td>
                            <td class="tg-0lax">
                                <input class="tg-0pky"type="password" name="name" required
                                       minlength="4" maxlength="8" size="10">
                            </td>
                        </tr>
                        <tr>
                            <td class="tg-0lax">새 비밀번호 확인</td>
                            <td class="tg-0lax">
                                <input class="tg-0pky"type="password" name="name" required
                                       minlength="4" maxlength="8" size="10">
                            </td>
                        </tr>
                        <tr>
                            <td class="tg-0lax">휴대폰 번호</td>
                            <td class="tg-0lax">
                                <select>
                                    <option value="empty" selected>선택하세요</option>
                                    <option value="010">010</option>
                                    <option value="011">011</option>
                                    <option value="018">018</option>
                                </select>
                                -
                                <input type="text" required>
                                -
                                <input type="text" required>
                                <button>휴대폰번호 인증</button>
                            </td>
                        </tr>
                        <tr>
                            <td class="tg-0lax">생년월일</td>
                            <td class="tg-0lax">
                                <select name="year" id="year">
                                    <option value="empty" selected>선택하세요</option>
                                    <option value="1970">1970</option>
                                    <option value="1971">1971</option>
                                    <option value="1972">1972</option>
                                    <option value="1973">1973</option>
                                    <option value="1974">1974</option>
                                    <option value="1975">1975</option>
                                    <option value="1976">1976</option>
                                    <option value="1977">1977</option>
                                    <option value="1978">1978</option>
                                    <option value="1979">1979</option>
                                    <option value="1980">1980</option>
                                    <option value="1981">1981</option>
                                    <option value="1982">1982</option>
                                    <option value="1983">1983</option>
                                    <option value="1984">1984</option>
                                    <option value="1985">1985</option>
                                    <option value="1986">1986</option>
                                    <option value="1987">1987</option>
                                    <option value="1988">1988</option>
                                    <option value="1989">1989</option>
                                    <option value="1990">1990</option>
                                    <option value="1991">1991</option>
                                    <option value="1992">1992</option>
                                    <option value="1993">1993</option>
                                    <option value="1994">1994</option>
                                    <option value="1995">1995</option>
                                    <option value="1996">1996</option>
                                    <option value="1997">1997</option>
                                    <option value="1998">1998</option>
                                    <option value="1999">1999</option>
                                    <option value="2000">2000</option>
                                    <option value="2001">2001</option>
                                    <option value="2002">2002</option>
                                    <option value="2003">2003</option>
                                    <option value="2004">2004</option>
                                    <option value="2005">2005</option>
                                    <option value="2006">2006</option>
                                    <option value="2007">2007</option>
                                    <option value="2008">2008</option>
                                    <option value="2009">2009</option>
                                    <option value="2010">2010</option>
                                    <option value="2011">2011</option>
                                    <option value="2012">2012</option>
                                    <option value="2013">2013</option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2017">2018</option>
                                    <option value="2017">2019</option>
                                    <option value="2017">2020</option>
                                    <option value="2017">2021</option>
                                </select>
                                <select name="month" id="month">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="6">6</option>
                                    <option value="7">7</option>
                                    <option value="8">8</option>
                                    <option value="9">9</option>
                                    <option value="10">10</option>
                                    <option value="11">11</option>
                                    <option value="12">12</option>
                                </select>
                                <select name="day">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="6">6</option>
                                    <option value="7">7</option>
                                    <option value="8">8</option>
                                    <option value="9">9</option>
                                    <option value="10">10</option>
                                    <option value="11">11</option>
                                    <option value="12">12</option>
                                    <option value="13">13</option>
                                    <option value="14">14</option>
                                    <option value="15">15</option>
                                    <option value="16">16</option>
                                    <option value="17">17</option>
                                    <option value="18">18</option>
                                    <option value="19">19</option>
                                    <option value="20">20</option>
                                    <option value="21">21</option>
                                    <option value="22">22</option>
                                    <option value="23">23</option>
                                    <option value="24">24</option>
                                    <option value="25">25</option>
                                    <option value="26">26</option>
                                    <option value="27">27</option>
                                    <option value="28">28</option>
                                    <option value="29">29</option>
                                    <option value="30">30</option>
                                    <option value="31">31</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="tg-0lax">성별</td>
                            <td class="tg-0lax">
                                <input type="radio"name='gender' value='male' />남성
                                <input type="radio"name='gender' value='female' />여성
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div id="Review" class="tabcontent">
                <h3>나의 리뷰</h3>
                <p>리뷰 작성공간</p>
            </div>

            <div id="Myplace" class="tabcontent">
                <h3>나의 코스/장소</h3>
                <p>Tokyo is the capital of Japan.</p>
            </div>
            <div id="like" class="tabcontent">
                <h3>찜 목록</h3>
                <p>Tokyo is the capital of Japan.</p>
            </div>
            <div id="Today" class="tabcontent">
                <h3>최근 본 코스</h3>
                <p>Tokyo is the capital of Japan.</p>
            </div>
        </div>
    </div>




</div>
</body>
<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/mypage.js"></script>
</html>