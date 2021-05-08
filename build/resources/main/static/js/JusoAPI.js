function goPopup(){
    // 
    // 주소검색을 수행할 팝업 페이지를 호출합니다.
    // 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
    var pop = window.open("/popup/jusoPopup","pop","width=570,height=420, scrollbars=yes, resizable=yes");

    // 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes");
}


function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
    // 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
    document.form.roadFullAddr.value = roadFullAddr;
    document.form.roadAddrPart1.value = roadAddrPart1;
    document.form.roadAddrPart2.value = roadAddrPart2;
    document.form.addrDetail.value = addrDetail;
    document.form.engAddr.value = engAddr;
    document.form.jibunAddr.value = jibunAddr;
    document.form.zipNo.value = zipNo;
    document.form.admCd.value = admCd;
    document.form.rnMgtSn.value = rnMgtSn;
    document.form.bdMgtSn.value = bdMgtSn;
    document.form.detBdNmList.value = detBdNmList;
    /** 2017년 2월 추가제공 **/
    document.form.bdNm.value = bdNm;
    document.form.bdKdcd.value = bdKdcd;
    document.form.siNm.value = siNm;
    document.form.sggNm.value = sggNm;
    document.form.emdNm.value = emdNm;
    document.form.liNm.value = liNm;
    document.form.rn.value = rn;
    document.form.udrtYn.value = udrtYn;
    document.form.buldMnnm.value = buldMnnm;
    document.form.buldSlno.value = buldSlno;
    document.form.mtYn.value = mtYn;
    document.form.lnbrMnnm.value = lnbrMnnm;
    document.form.lnbrSlno.value = lnbrSlno;
    /** 2017년 3월 추가제공 **/
    document.form.emdNo.value = emdNo;
}