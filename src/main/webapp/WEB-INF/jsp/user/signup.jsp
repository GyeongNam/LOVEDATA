<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script defer src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<link href="/css/signup.css" rel="stylesheet">

<html>
<head>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
    </style>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <title>회원가입ㅣLOVEDATA</title>
</head>
<body>
<div id="header" class="join_membership" role="banner">
    <h1>
        <a href="/" class="h_logo">
            <img class="site-logo" src="/image/icon/LOVEDATAlogo.png">
        </a>
    </h1>
</div>
<%--<%@ include file="../layout/header.jsp" %>--%>
<c:set var="set_user" value='<%=request.getAttribute("social") == null ? false : request.getAttribute("social")%>'></c:set>
<form class="signupform" action="/signup_add" method="post">
    <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
    <div class="content">
        <table class="signup">
            <tr>
                <td class="thead"><label class="label"><strong>이메일(아이디)
                    *</strong></label>
                </td>
                <td>
                    <div class="emailcontent">
                        <c:choose>
                            <c:when test="${set_user eq false}">
                                <input class="input_style" type="text" name="str_email01" id="str_email01"
                                       required="required" onblur="email_check()"
                                       value="<%= request.getAttribute("str_email01") == null ? "" : request.getAttribute("str_email01") %>">
                                @ <input class="input_style" type="text"
                                         name="str_email02" id="str_email02"
                                         onblur="email_check()" required
                                         placeholder="" value="<%= request.getAttribute("str_email02") == null ? "" : request.getAttribute("str_email02") %>">
                                <select name="str_email03" id="selectEmail" onblur="email_check()" required="required">
                                    <option value="1">직접입력</option>
                                    <option value="naver.com">naver.com</option>
                                    <option value="hanmail.net">hanmail.net</option>
                                    <option value="hotmail.com">hotmail.com</option>
                                    <option value="nate.com">nate.com</option>
                                    <option value="yahoo.co.kr">yahoo.co.kr</option>
                                    <option value="empas.com">empas.com</option>
                                    <option value="dreamwiz.com">dreamwiz.com</option>
                                    <option value="freechal.com">freechal.com</option>
                                    <option value="lycos.co.kr">lycos.co.kr</option>
                                    <option value="korea.com">korea.com</option>
                                    <option value="gmail.com">gmail.com</option>
                                    <option value="hanmir.com">hanmir.com</option>
                                    <option value="paran.com">paran.com</option>
                                </select>
                            </c:when>
                            <c:when test="${set_user eq true}">   <%--	소셜 로그인으로 들어올경우--%>
                                <input  class="input_style" type="text" name="str_email01" id="str_email01"
                                       required="required"
                                       value="<%= request.getAttribute("str_email01") == null ? "" : request.getAttribute("str_email01") %>" readonly>
                                @ <input class="input_style" type="text"
                                         name="str_email02" id="str_email02"
                                         required
                                         placeholder="" value="<%= request.getAttribute("str_email02") == null ? "" : request.getAttribute("str_email02") %> " readonly>
                                <select name="str_email03" id="selectEmail" required="required" readonly>
                                    <option value="1">직접입력</option>
                                    <option value="naver.com">naver.com</option>
                                    <option value="hanmail.net">hanmail.net</option>
                                    <option value="hotmail.com">hotmail.com</option>
                                    <option value="nate.com">nate.com</option>
                                    <option value="yahoo.co.kr">yahoo.co.kr</option>
                                    <option value="empas.com">empas.com</option>
                                    <option value="dreamwiz.com">dreamwiz.com</option>
                                    <option value="freechal.com">freechal.com</option>
                                    <option value="lycos.co.kr">lycos.co.kr</option>
                                    <option value="korea.com">korea.com</option>
                                    <option value="gmail.com">gmail.com</option>
                                    <option value="hanmir.com">hanmir.com</option>
                                    <option value="paran.com">paran.com</option>
                                </select >
                            </c:when>
                        </c:choose>
                    </div>
                    <div>
                        <input  class="checkbox11" type="checkbox" name="recv_email"/>
                        <input  class="checkbox12" type="hidden" name="recv_email" value=false/>
                        <spen class="spen"> 이메일 수신에 동의합니다.</spen>
                    </div>
                    <div>
                        <span class="spen" id="email_check"></span>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>비밀번호
                    *</strong> </label></td>
                <td>
                    <c:choose>
                        <c:when test="${set_user eq false}">
                            <input  class="input_style" type="password" name="userPwd" id="pwd1"
                                   onKeyup="chkpw()" value="<%= request.getAttribute("pwd")  == null ? "" :  request.getAttribute("pwd")%>" class="form-control"  required>
                        </c:when>
                        <c:when test="${set_user eq true}">   <%--	소셜 로그인으로 들어올경우--%>
                            <input class="input_style" type="password" name="userPwd" id="pwd1"
                                   value="<%= request.getAttribute("pwd")  == null ? "" :  request.getAttribute("pwd")%>" class="form-control"  required readonly>
                        </c:when>
                    </c:choose>
                    <spen
                            class="spen" id=pwd_rule>영문 대소문자/숫자/특수문자 중 2가지 이상 조합,
                        10자~16자.
                    </spen>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>비밀번호
                    확인 *</strong></label></td>
                <td>
                    <c:choose>
                        <c:when test="${set_user eq false}">
                            <input class="input_style" type="password" name="reuserPwd" id="pwd2"
                                   onKeyup="passwordcheck()" value="<%= request.getAttribute("pwd")  == null ? "" :  request.getAttribute("pwd")%>" class="form-control" required>
                        </c:when>
                        <c:when test="${set_user eq true}">   <%--	소셜 로그인으로 들어올경우--%>
                            <input class="input_style" type="password" name="reuserPwd" id="pwd2"
                                   value="<%= request.getAttribute("pwd")  == null ? "" :  request.getAttribute("pwd")%>" class="form-control" required readonly>
                        </c:when>
                    </c:choose>
                    <spen
                            class="spen" id="pwd_check"></spen>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>닉네임
                    *</strong></label></td>
                <td>
                    <c:choose>
                        <c:when test="${set_user eq false}">
                            <input class="input_style" type="text" onblur="nick_check()" class="nickname" name="nickname"
                                   id="nickname" value="<%= request.getAttribute("nickname")  == null ? "" :  request.getAttribute("nickname")%>" minlength=3 required>
                        </c:when>
                        <c:when test="${set_user eq true}">   <%--	소셜 로그인으로 들어올경우--%>
                            <input class="input_style" type="text" class="nickname" name="nickname"
                                   id="nickname" value="<%= request.getAttribute("nickname")  == null ? "" :  request.getAttribute("nickname")%>" minlength=3 required>
                        </c:when>
                    </c:choose>
                    <spen class="spen"
                          id="nickname_check"></spen>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>이름
                    *</strong></label></td>
                <td>
                    <c:choose>
                        <c:when test="${set_user eq false}">
                            <input class="input_style" type="text" name="userName" id="userName" onblur="name_check()" id="new_name" required>
                        </c:when>
                        <c:when test="${set_user eq true}">   <%--	소셜 로그인으로 들어올경우--%>
                            <input class="input_style" type="text" name="userName" id="userName" id="new_name" required>
                        </c:when>
                    </c:choose>

                    <spen class="spen"
                          id="userName_check"></spen>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>휴대폰번호
                    *</strong></label></td>
                <td>
                    <div class="str_phone">
                        <select class="phone-selectmenu" name="str_phone01" id="selectphone" required="required">
                            <option value="010">010</option>
                            <option value="011">011</option>
                            <option value="017">017</option>
                        </select> - <input type="text" class="phone_nums"
                                           onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"
                                           minlength='3' maxlength='4' name="str_phone02" id="str_phone02"
                                           required="required"> - <input type="text"
                                                                         class="phone_nums"
                                                                         onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"
                                                                         minlength='4' maxlength='4' name="str_phone03"
                                                                         id="str_phone03"
                                                                         required="required">
                        <button type="button" onclick="SMSsned()" id="modal_opne_btn" name="button">휴대폰
                            인증
                        </button>
                        <br>
                    </div>
                    <spen
                            class="spen" id="phone_check">인증번호로 인증하세요
                    </spen>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>생년월일</strong></label>
                </td>
                <td><input  class="input_style" type="date" id="birthday" name="birthday"
                           value="2000-01-01" min="1930-01-01" max="2050-12-31" required>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>성별</strong></label>
                </td>
                <td><select class="selectmenu" name="gender" id="gender" required="required">
                    <option value=true>남자</option>
                    <option value=false>여자</option>
                </select></td>
            </tr>
        </table>
        <input type="hidden" name="social" value="<%= request.getAttribute("social")  == null ? false : request.getAttribute("social")%>">
        <input type="hidden" name="social_info" value="<%= request.getAttribute("social_info")  == null ? "" : request.getAttribute("social_info")%>">
        <input type="hidden" name="social_id" value="<%= request.getAttribute("social_id")  == null ? "" : request.getAttribute("social_id")%>">
        <input type="hidden" name="profile_pic" value="<%=request.getAttribute("profile_pic") == null ? "" : request.getAttribute("profile_pic")%>">
        <div class="sand_back">
            <c:choose>
                <c:when test="${set_user eq false}">
                    <button class="submit" type="submit" id="sub"
                            onclick="return signup_check();">
                        <b>가입하기</b>
                    </button>
                </c:when>
                <c:when test="${set_user eq true}">   <%--	소셜 로그인으로 들어올경우--%>
                    <button class="submit" type="submit" id="sub"
                            onclick="return Ssignup_check();">
                        <b>가입하기</b>
                    </button>
                </c:when>
            </c:choose>
            <button class="back" onclick="location.href='/' " id="subcc">
                <b>취소</b>
            </button>
        </div>
        <div id="modal" class="modal">
            <div class="modal_content">
                <div>아래 번호로 인증번호를 발송했습니다.</div>
                <div id="phone_num"></div>
                <input type="text" name="phone_check2" id="security"
                       placeholder=" 숫자 6자리">
                <button type="button" id="rnum" onclick="rnum_check()">인증확인</button>
                <div id="phone_numcheck">인증하세요!</div>
                <button type="button" id="modal_close_btn">닫기</button>
            </div>
        </div>


    </div>
</form>

<div id="modal2" class="modal-overlay">
    <div class="modal-window">
        <div class="title">
            <h2>약관 동의</h2>
        </div>
        <div class="content">

            <label >
                <input type="checkbox" class="abc" name="agree" value="1">
                <span>개인정보 이용 약관<strong>(필수)</strong></span>
                <textarea class="noresize">${Article8}</textarea>
            </label>
            <label>
                <input type="checkbox" class="abc" name="agree" value="2">
                <span>LOVEDATA 사용 약관 1<strong>(필수)</strong></span>
                <textarea class="noresize">
                        ${Article9}
                    ${Article10}
                        </textarea>
            </label>
            <label>
                <input type="checkbox" class="abc" name="agree" value="3">
                <span>LOVEDATA 사용 약관 2<strong>(필수)</strong></span>
                <textarea class="noresize">
                        ${Article11}

                    ${Article12}

                    ${Article13}

                    ${Article14}

                    ${Article15}

                    ${Article16}

                    ${Article17}

                    ${Article18}

                    ${Article19}

                    ${Article20}
                    </textarea>
            </label>
            <div>
                <label>
                    <input type="checkbox" name="agree_all" id="agree_all">
                    <span>모두 동의합니다</span>
                </label>

            </div>
            <div style="text-align: center">
                <button onclick="modal2()">확인</button>
                <button onclick="location.href='/login'">취소</button>
            </div>
        </div>
    </div>
</div>

<%--<%@ include file="../layout/footer.jsp" %>--%>
</body>
</html>

<script defer src="/js/signup.js"></script>
