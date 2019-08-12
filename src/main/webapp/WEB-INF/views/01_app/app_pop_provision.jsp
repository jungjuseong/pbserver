<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>
<%@ page session="true" %>
<script type="text/javascript">
$(document).ready(function(){
	//선택시
	$('a.btn.btnXS').click(function(){
		var distrProfileName = $(this).parents('tr').find('td').eq(1).text();
		var provId = $(this).parents('tr').find('td').eq(2).text();
		var provSeq = $(this).parents('tr').attr('id');
		var provTestGb = $(this).parents('tr').find('.provTestGbClass').val();
		var isValid = true;
		var lastDotIndex = provId.lastIndexOf(".");
		var lastCharbyDot = provId.substring(lastDotIndex+1, provId.length);
		var exceptlastCharbyDot = provId.substring(0, lastDotIndex+1);
		//alert("lastCharbyDot==="+lastCharbyDot);
		//alert("exceptlastCharbyDot==="+exceptlastCharbyDot);
		//if(lastCharbyDot=='*')
		var provCnt = 0;
		$(window.opener.document).find('[name=provSeq]').each(function(){
			provCnt++;
/* 			if($(this).val()==provSeq){
				alert("<spring:message code='app.provision.list.text8' />");
				isValid = false;
				return false;
			} */
			if(provCnt==2){
				alert("<spring:message code='app.provision.list.text9' />");		
				isValid = false;
				return false;
			}
		});
		if(isValid){
			var html = '<li class="provli"><div>';
			html +='<input type="hidden" name="provSeq" value="'+provSeq+'" />';
			html +='<input type="hidden" name="provTestGb" value="'+provTestGb+'" />';
			html +='<input type="hidden" name="provId" value="'+provId+'" />';
			html +='<span>Name: </span><span>'+distrProfileName+'</span>, ';
			var provTestGbStr = "Adhoc";
			if(provTestGb=="2"){
				provTestGbStr = "AppStore";
			}
			html +='<span>Status: </span><span>'+provTestGbStr+'</span> ';

			html +='<a class="removeProvBtn" href="#remove_prov"><img src="/images/btn_close_s.png" alt="provison 제거"></a></div>';
			html +='</li>';
			
			$(window.opener.document).find('#storeBundleId1').val(exceptlastCharbyDot).prop("readonly", true);
			if($(window.opener.document).find('.provarea').find('ul').find('li').size()==1){
				if($(window.opener.document).find('#storeBundleId2').val()=="*"){
					if(lastCharbyDot=='*'){
						$(window.opener.document).find('#storeBundleId2').val(lastCharbyDot).prop("readonly", false);
					}else{
						$(window.opener.document).find('#storeBundleId2').val(lastCharbyDot).prop("readonly", true);
					} 	
				}
			}else{
				if(lastCharbyDot=='*'){
					$(window.opener.document).find('#storeBundleId2').val(lastCharbyDot).prop("readonly", false);
				}else{
					$(window.opener.document).find('#storeBundleId2').val(lastCharbyDot).prop("readonly", true);
				} 				
			}		
			
			$(window.opener.document).find('.provarea').find('ul').append(html);
			self.close();			
		}
	});

	//검색버튼클릭
	$('.btn.btnM.btn_gray_dark.search').click(function(){
		$('form').submit();		
	});

	//검색택스트리턴
	$('input[name=provName]').keyup(function(e){
		if(e.keycode=='13'){
		$('.btn.btnM.btn_gray_dark.search').click();		
		}
	});
});
<!--

//-->
</script>
</head>
<body class="popup" style="width:670px; height:400px;">
<!-- wrap -->
<div class="pop_wrap">

	<!-- conteiner -->
	<div id="container">
		<div class="contents list_area">
			<div class="pop_header clfix">
				<h1>${provisionVo.distrProfile} <spring:message code='app.provision.list.text1' /></h1>
			</div>

			<!-- contents_detail -->
			<form method="get" action="" name="" >
			<div class="pop_contents">
				<div class="form_area tRight">
					<input name="provName" type="text" style="width:250px;" value="${provisionVo.provName}">
					<input name="distrProfile" type="hidden" value="${provisionVo.distrProfile}" />
					<input name="provId" type="hidden" value="${provisionVo.provId}" />
					<input name="appSeq" type="hidden" value="${appSeq}" />
					<a href="#search;" class="btn btnM  btn_gray_dark search"><spring:message code='app.provision.list.text1' /></a>
				</div>

				<div class="pop_section">
					<div class="table_area table_type1">
						<table class="coltable">
							<colgroup>
								<col style="width:">
								<col style="width:">
								<col style="width:">
								<col style="width:105px">
								<col style="width:80px">
							</colgroup>
							<caption></caption>
							<tr>
								<th scope="col"><spring:message code='app.provision.list.text2' /></th>
								<th scope="col">${provisionVo.distrProfile}</th>
								<th scope="col"><spring:message code='app.provision.list.text4' /></th>
								<th scope="col"><spring:message code='app.provision.list.text5' /></th>
								<th scope="col"><spring:message code='app.provision.list.text6' /></th>
							</tr>
						<c:choose>
							<c:when test="${empty provisionList}">
							<tr>
								<td align="center" colspan="5" ><spring:message code='app.no.contents' /></td>
							</tr>
							</c:when>
							<c:otherwise>
									<c:forEach var="i" begin="0" end="${provisionList.size()-1}">
									<tr id="${provisionList[i].provSeq}">
										<td>${provisionList[i].provName }</td>
										<td>${provisionList[i].distrProfileName}</td>
										<td>${provisionList[i].provId }</td>
										<td><fmt:formatDate value="${provisionList[i].chgDt }" pattern="yyyy-MM-dd"/></td>
										<td><a class="btn btnXS" href="#select"><spring:message code='app.provision.list.text7' /></a></td>
										<input type="hidden" class="provTestGbClass" name="provTestGb" value="${provisionList[i].provTestGb }" />	
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>	
						</table>
					</div>
				</div>
			</div>
			</form>
			<!-- //contents_detail -->
		</div>
	</div>
	<!-- //conteiner -->

</div><!-- //wrap -->

</body>
</html>
