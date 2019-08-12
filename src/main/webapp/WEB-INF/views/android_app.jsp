<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><%@taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- 변수 지원, 흐름 제어, URL처리 -->

<html>
<meta name="viewport" content="width=device-width">
<head>
<style>
body {
    background: #3c3b3e;
    color: #3c3b3e;
}

h3 {
	color:#fff;
	border-top: 1px solid #333;
    border-bottom: #555858;
    list-style-type: none;
    padding: 10px 10px 10px 10px;
    background-color: #060708;
    overflow: hidden;
}

ul {
    color: #aaa;
    border: 1px solid #333333;
    font: bold 18px "Helvetica Neue", Helvetica;
    padding: 0;
    margin: 15px 10px 17px 10px;
}

ul li {
    color: #666;
    border-top: 1px solid #333;
    border-bottom: #555858;
    list-style-type: none;
    padding: 10px 10px 10px 10px;
    background-image: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#4c4d4e), to(#404142));
    overflow: hidden;
}

a {
	color:#fff;
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PageBuilderCMS <spring:message code='anonymous.option.001' /></title><script>$(document).ready(function(){
	
	/* alert($(window).width());
	scale = ($(window).width() / 102); 
	$("#android").css("zoom",scale); */
	/* window.onresize = function() { 
		scale = ($(window).width() / 1024); 
		
		alert(scale);
		$("#android").css("zoom",scale); 
	} */
	
		$("#linkDown").click(function(){
			//downType이 1일때에만 downloadCnt를 올린다. <- 이전
			//지금은 그냥 다운로드 카운트를 무조건 올린다.
			// 통계낼때의 다운로드 카운트랑, 현재 이앱의 대한 다운로드 카운트 ( 다운로드 횟수 지정 옵션의 fieldname)랑 같이 공유함..
			var downType = $("[name=downType]").val();
			alert(downType);
			var downCnt = $("[name=downCnt]").val();
			var downGubun = $("[name=DownGubun]").val();
			var downSeq = $("[name=downSeq]").val();
			var isCoupon = $("[name=isCoupon]").val();
				$.ajax({
		            url: "/downloadCounting.html" ,
		            type: "POST" ,
		            data:{
		               "downCnt"   : downCnt,
		               "downSeq"   : downSeq,
		               "sort"      : downGubun,
		               "isCoupon"  : isCoupon
		                           },
		               success: function (result){
		               		switch (result){
		                    	case 0 : 
		                            break ;
		                        case 1 : 
		                        	break ;
	
		                    }
		      	      }
		        });
		});});</script>
</head>
<body >
	<div id="android" align="center">
   		<div class="toolbar">
	        <h3>${DownName } <spring:message code='anonymous.option.001' /></h3>
        </div>
        <div><image src="${iconPath}${iconName }" width="57" height="57"></div>
        	<input type="hidden" name="downSeq" value="${downSeq }" />
			<input type="hidden" name="DownGubun" value="${DownGubun }" />
			<input type="hidden" name="DownKaU" value="${DownKaU }" />
			<input type="hidden" name="DownVer" value="${DownVer }" />
			
			<input type="hidden" name="coupon_Num" value="${coupon_Num }" />
			<input type="hidden" name="ostype" value="${ostype}" />
			<input type="hidden" name="downCnt" value="${downCnt}" />
			<input type="hidden" name="downType" value="${downType }"/>
			<input type="hidden" name="isCoupon" value="${isCoupon }"/>
        <ul class="rounded">							<!--bundel  -->	<!-- app_name -->  			<c:if test="${osFlag == 'Android' }"><li><a id="linkDown" target="_blank" href="${filePath}${DownKaU}${DownVer}/${fileName}${DownVer}.apk"><spring:message code='down.list.017' /></a></li></c:if>
  	      	<c:if test="${osFlag == 'iOS' }"><li><a id="linkDown" target="_blank" href="itms-services://?action=download-manifest&url=${filePath}${DownKaU}${DownVer}.plist"><spring:message code='down.list.017' /></a></li></c:if>
        </ul>

	</div>

</body>
</html>