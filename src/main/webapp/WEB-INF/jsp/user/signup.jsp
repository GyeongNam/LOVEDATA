<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
﻿<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿

<link href="/css/signup.css" rel="stylesheet">

<html>
<head>
<title>회원가입ㅣLOVEDATA</title>
</head>

<body>
<%@ include file="../layout/header.jsp" %>
  <div class="content">
   <div class="logo">
     <h1>LOVEDATA</h1>
   </div>
   <table class="signup">
     <tr>
       <td class="thead">
         <label class = "label"><strong>이메일(아이디) *</strong></label>
       </td>
       <td>
         <div class="emailcontent">
           <input type="text" name="str_email01" id="str_email01" required="required"> @
           <input type="text" name="str_email02" id="str_email02" placeholder="">
           <select name="str_email03" id="selectEmail" required="required">
             <option value="1">직접입력</option>
             <option value="naver.com" >naver.com</option>
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
           <input class="checkbox1" type="checkbox" name="assent" value="1"/>  <spen class = "spen"> 이메일 수신에 동의합니다.</spen>
         </div>
         <div>
           <span class = "spen"></span>
         </div>
       </td>
      </tr>
       <tr>
         <td class="thead">
         <label class = "label"><strong>비밀번호 *</strong> </label>
        </td>
        <td>
           <input type="password" name="userPwd" id="pwd1" onKeyup="chkpw()" class="form-control" required>
          <spen class="spen" id=pwd_rule>영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 10자~16자.</spen>
        </td>
       </tr>
       <tr>
         <td class="thead">
         <label class = "label"><strong>비밀번호 확인 *</strong></label>
        </td>
        <td>
          <input type="password" name="reuserPwd" id="pwd2"  onKeyup="passwordcheck()" class="form-control" required>
         <spen class="spen" id= "pwd_check"></spen>
         </td>
       </tr>
       <tr>
         <td class="thead">
         <label class = "label"><strong>닉네임 *</strong></label>
         </td>
          <td>
           <input type="text" class="nickname" name="nickname" id="nickname" minlength=10 maxlength=16 required >
           <spen class="spen" id= "nickname_check" ></spen>
            </td>
       </tr>
       <tr>
         <td class="thead">
          <label class = "label"><strong>이름 *</strong></label>
          </td>
          <td>
            <input type="text" name="userName" id="new_name" required>
            </td>
        </tr>
        <tr>
          <td class="thead">
          <label class = "label"><strong>휴대폰번호 *</strong></label>
          </td>
          <td>
          <div class="str_phone">
            <select name="str_phone01" id="selectphone" required="required">
              <option value="010">010</option>
              <option value="011" >011</option>
              <option value="017">017</option>
            </select> -
            <input type="text" class= "phone_nums"  onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"minlength='3' maxlength='4' name="str_phone02" id="str_phone02" required="required"> -
            <input type="text" class= "phone_nums" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" minlength='4' maxlength='4' name="str_phone03" id="str_phone03" required="required">
            <button type="button" id="modal_opne_btn" name="button">휴대폰 인증</button>
          </div>
          </td>
        </tr>
        <tr>
          <td class="thead">
          <label class = "label"><strong>생년월일</strong></label>
          </td>
          <td>
            <input type="date" id="birthday" name="birthday"
            value="2000-01-01"
            min="1930-01-01" max="2050-12-31" required>
          </td>
        </tr>
        <tr>
          <td class="thead">
          <label class = "label"><strong>성별</strong></label>
          </td>
          <td>
            <select name="gender" id = "gender" required="required">
              <option value="m">남자</option>
              <option value="w">여자</option>
            </select>
          </td>
        </tr>
    </table>
    <div class="sand_back">
    <button class="sand"  id="sub" >
      <b>가입하기</b>
    </button>
    <button class="back" onclick="location.href='/LOVEDATA' "  id="subcc" >
      <b>취소</b>
    </button>
	</div>
  <div id="modal" class="modal">
    <div class="modal_content">
      <div>아래 번호로 인증번호를 발송했습니다.</div>
      <div id="phone_num"></div>
      <input type="text" name="email_ck" id="security" placeholder=" 숫자 6자리" required>
      <button type="button" id="">인증확인</button>
      <div id="phone_numcheck">인증하세요!</div>
      <button type="button" id="modal_close_btn">닫기</button>
    </div>
  </div>
</div>
<%@ include file="../layout/footer.jsp" %>
</body>
</html>

<script src="/js/signup.js"></script>
