<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link href="/css/signup.css" rel="stylesheet">

<html>
<meta charset="UTF-8">
<%-- <meta id="_csrf" name="_csrf" content="${_csrf.token}" />
<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"> --%>

<head>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <title>회원가입ㅣLOVEDATA</title>
</head>
<body>
<%@ include file="../layout/header.jsp" %>
<form class="signupform" action="/signup_add" method="post">
    <%-- <input type="hidden" name="${_csrf.parameterName }"value="${_csrf.token}"> --%>
    <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
    <div class="content">
        <div class="logo">
            <h1>LOVEDATA</h1>
        </div>
        <table class="signup">
            <tr>
                <td class="thead"><label class="label"><strong>이메일(아이디)
                    *</strong></label></td>
                <td>
                    <div class="emailcontent">
                        <input type="text" name="str_email01" id="str_email01"
                               required="required" onblur="email_check()"
<%--                               @Todo request대신 redirectAttribute를 어떻게 꺼내고, 만일  null 값일 경우 ""으로 처리할 수 있도록 하기--%>
<%--                                자바스크립트를 새로 만들어서 처리하는 방안 고민 중--%>
                               value="<%= request.getAttribute("str_email01") == null ? "" : request.getAttribute("str_email01") %>"> @ <input type="text"
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
                        </select> <input class="checkbox11" type="checkbox" name="recv_email"/>
                        <input class="checkbox12" type="hidden" name="recv_email" value=false/>
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
                <td><input type="password" name="userPwd" id="pwd1"
                           onKeyup="chkpw()" value="<%= request.getAttribute("pwd")  == null ? "" :  request.getAttribute("pwd")%>" class="form-control"  required>
                    <spen
                            class="spen" id=pwd_rule>영문 대소문자/숫자/특수문자 중 2가지 이상 조합,
                        10자~16자.
                    </spen>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>비밀번호
                    확인 *</strong></label></td>
                <td><input type="password" name="reuserPwd" id="pwd2"
                           onKeyup="passwordcheck()" value="<%= request.getAttribute("pwd")  == null ? "" :  request.getAttribute("pwd")%>" class="form-control" required>
                    <spen
                            class="spen" id="pwd_check"></spen>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>닉네임
                    *</strong></label></td>
                <td><input type="text" onblur="nick_check()" class="nickname" name="nickname"
                           id="nickname" value="<%= request.getAttribute("nickname")  == null ? "" :  request.getAttribute("nickname")%>" minlength=3 required>
                    <spen class="spen"
                          id="nickname_check"></spen>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>이름
                    *</strong></label></td>
                <td><input type="text" name="userName" id="new_name" required>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>휴대폰번호
                    *</strong></label></td>
                <td>
                    <div class="str_phone">
                        <select name="str_phone01" id="selectphone" required="required">
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
                <td><input type="date" id="birthday" name="birthday"
                           value="2000-01-01" min="1930-01-01" max="2050-12-31" required>
                </td>
            </tr>
            <tr>
                <td class="thead"><label class="label"><strong>성별</strong></label>
                </td>
                <td><select name="gender" id="gender" required="required">
                    <option value=true>남자</option>
                    <option value=false>여자</option>
                </select></td>
            </tr>
        </table>
        <input type="hidden" name="social" value="<%= request.getAttribute("social")  == null ? false : request.getAttribute("social")%>">
        <div class="sand_back">
            <button class="submit" type="submit" id="sub"
                    onclick="return to_submit();">
                <b>가입하기</b>
            </button>
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
                <button type="button" id="">인증확인</button>
                <div id="phone_numcheck">인증하세요!</div>
                <button type="button" id="modal_close_btn">닫기</button>
            </div>
        </div>
    </div>
</form>
<%@ include file="../layout/footer.jsp" %>
</body>
</html>

<script defer src="/js/signup.js"></script>
<script defer src="https://code.jquery.com/jquery-3.4.1.min.js"></script>