<%@page import="com.clbee.pbcms.vo.InappVO"%>
<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.clbee.pbcms.vo.InAppList"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>
<script type="text/javascript" src="/js/jquery.validate.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	//버전체크 확인 후 > "기존콘텐츠 사용"하면서 검색할 때 버전체크 재확인 안하도록 하기 위한 로직
	if('${dupleCheckYN}' == 'Y'){
		$("#dupCheckVerNum").val('${dupleVerNum}');
		$("#duplicateCheck").val("Y");
	}else{
		$("#dupCheckVerNum").val('${viewVer}');
	}//버전체크 확인 후 > "기존콘텐츠 사용"하면서 검색할 때 버전체크 재확인 안하도록 하기 위한 로직 - end
	
	//앱 생성 시 필요한 값 선 setting
	$("#storeBundleId").val(opener.window.document.appForm.storeBundleId1.value+opener.window.document.appForm.storeBundleId2.value);
	$("#ostype").val(opener.window.document.appForm.ostype.value);
	//앱 생성 시 필요한 값 선 setting - end
	
	//앱 갱신/새버전 추가할 때, 콘텐츠 생성/수정 시 필요한 값 선 setting	
	$("#relatedAppNameLocal").val(opener.window.document.appForm.appName.value);
//	$("#relatedBundleIdLocal").val($("#storeBundleId").val());
	$("#relatedAppSeqLocal").val(opener.window.document.appForm.appSeq.value);
	$("#relatedAppTypeLocal").val($("#ostype").val());
	
	$("#relatedAppNameExist").val(opener.window.document.appForm.appName.value);
//	$("#relatedBundleIdExist").val($("#storeBundleId").val());
	$("#relatedAppSeqExist").val(opener.window.document.appForm.appSeq.value);
	$("#relatedAppTypeExist").val($("#ostype").val());
	//앱 갱신/새버전 추가할 때, 콘텐츠 생성/수정 시 필요한 값 선 setting - end
	
	//팝업 요청에 따라 버전체크 show / hide
	if('${popupGb}' == 'update'){
		$("#dupCheckVerNum").hide();
		$("#duplicateVerNum").hide();
	}//팝업 요청에 따라 버전체크 show / hide - end
	
	// 콘텐츠 등록방법 div 2개 show/hide control
	// 1) 화면 첫 접속 시 => 모두 hide(radio select 전) / 2)기존꺼 선택 중 > 검색 기능 사용 시 --> 검색 결과 show 처리
	$("#localUpload").hide();
	if('${radioSearch}' == 'exist'){
		$("#contentListUpload").show();
		$("#typeExist").prop("checked", true);
		$("#radioSearch").val("exist");
	}else{
		$("#contentListUpload").hide();
		$("#radioSearch").val("local");
	}
	
	$("input:radio[name=appContentsGb]").click(function(){
		var st = $(":input:radio[name=appContentsGb]:checked").val();
		if(st == "local"){
			$("#radioSearch").val("local");
			$("#localUpload").show();
			$("#contentListUpload").hide();
		}else if(st == "exist"){
			$("#radioSearch").val("exist");
			$("#localUpload").hide();
			$("#contentListUpload").show();
		}
	});
	// 콘텐츠 등록방법 div 2개 show/hide control - end
	
	// 버전 중복 확인(새버전 추가 시)
	$("#duplicateBtn").on("click", function(){
		var inputVerNum = $("#dupCheckVerNum").val();
		if(inputVerNum.length == 0){
			//message : 버전을 입력해주세요.
			alert("<spring:message code='app.request.001' />");
		}else{
			$.ajax({
				url:"/app/request/duplicateVerNum.html",
				type:"POST",
				data:{
					"storeBundleId":$("#storeBundleId").val(),
					"ostype":$("#ostype").val(),
					"inputVerNum":$("#dupCheckVerNum").val(),
					"nowVerNum":'${nowVer}'
				},
				success:function(result){
					switch(result){
						//message : 해당 버전은 사용 가능합니다.
						case 0 : 
							alert("<spring:message code='app.request.002' />");
							$("#duplicateCheck").val("Y");
							break;
						//message : 이미 사용중인 버전입니다.
						case 1 : 
							alert("<spring:message code='app.request.003' />");
							break;
						//message : 버전 중복확인에 문제가 있습니다. 관리자에게 문의 바랍니다.
						case 2 : 
							alert("<spring:message code='app.request.004' />");
							break;
						//message : 기존 앱보다 버전을 높여서 사용해주십시오.
						case 3 : 
							alert("<spring:message code='app.request.011' />");
							break;
						//message : 버전이 변경되지 않았습니다. 변경해서 사용해주십시오.
						case 4 : 
							alert("<spring:message code='app.request.012' />");
							break;
						//message : 버전 자리수를 3자리로 맞춰주시기 바랍니다.
						case 5 : 
							alert("<spring:message code='app.request.013' />");
							break;
					}
				}
			});
		}
	});// 버전 중복 확인(새버전 추가 시) - end	
	
	// 앱 갱신/새버전 추가 처리(각각의 로직 분기 처리)
	$("#appMake").on("click", function(){		
		var ret = $(":input:radio[name=appContentsGb]:checked").val();
		
		//새버전 추가하는 경우 > 버전 중복확인 했는지 체크
		if('${popupGb}' == 'newVer' && $("#duplicateCheck").val() != "Y"){
			alert("<spring:message code='app.request.make.001' />");
			return false;
		}
		
		//실제 앱 갱신/새버전 추가하는 로직
		if(ret == "exist"){//기존 콘텐츠 등록
			if(checkboxLength()){
				var contentSeq = "";
				$(":checkbox[name='contentsList']:checked").each(function(){
					contentSeq = $(this).val();
				});
				appMakeRequest(ret, contentSeq);
			}
		}else if(ret == "local"){//PC 등록
			appMakeRequest(ret, "no");
		}else{//기타 경우
			alert("<spring:message code='app.request.make.002' />");
			return false;
		}
		
	});
});

function goToSearch(){	
	$("#dupleVerNum").val($("#dupCheckVerNum").val());
	$("#dupleCheckYN").val($("#duplicateCheck").val());
	
	document.searchForm.action="/app/request.html";
	document.searchForm.method="GET";
	document.searchForm.submit();
}

function checkboxLength(){
	var checkLenght = $("input:checkbox[name=contentsList]:checked").length;
	
	if(checkLenght == 0){
		alert("<spring:message code='app.request.versionCheck.001' />");
		return false;
	}else if(checkLenght >= 2){
		alert("<spring:message code='app.request.versionCheck.002' />");
		return false;
	}else if(checkLenght == 1){
		return true;
	}else{
		alert("<spring:message code='app.request.versionCheck.003' />");
		return false;
	}
}

//앱 생성 요청 > 갱신 / 새버전 추가 2가리 ajax call
function appMakeRequest(ret, contentSeq){
	if('${popupGb}' == 'newVer'){
		$("#relatedAppVerNumExist").val($("#dupCheckVerNum").val());
		$("#relatedAppVerNumLocal").val($("#dupCheckVerNum").val());
	}else if('${popupGb}' == 'update'){
		$("#relatedAppVerNumExist").val("${nowVer }");
		$("#relatedAppVerNumLocal").val("${nowVer }");
	}
	
	if(ret == "exist"){
		if('${ostype}' == '4'){
			$("#provisionExist").val("");
		}else{
			$("#provisionExist").val($("#provisionSelect option:selected").val());
		}
		
		$.ajax({
			url:"/app/makeExist.html",
			type:"POST",
            data:{	
            	contentSeq : contentSeq,
            	relatedAppName : $("#relatedAppNameExist").val(),
            	relatedAppSeq : $("#relatedAppSeqExist").val(),
            	relatedAppType : $("#relatedAppTypeExist").val(),
            	relatedAppVerNum : $("#relatedAppVerNumExist").val(),
            	provisionSeq : $("#provisionExist").val(),
            	popupGb : '${popupGb}'
            },
			success:function(result){
				switch(result){
					case 0 : //success
						alert("<spring:message code='app.modify.alert2' />")
						window.close();
						break ;
					case 1 : //fail
						alert("<spring:message code='app.modify.alert1' />" );
						break;
				}
			}
		});
	}else if(ret == "local"){
		if('${ostype}' == '4'){
			$("#provisionLocal").val("");
		}else{
			$("#provisionLocal").val($("#provisionSelect option:selected").val());
		}
		
		//1. 콘텐츠 등록 폼 값 유무 체크
		var isValid = false;
		isValid = customValidate({
			rules: {
				contentsName: {
					required: true
				},
				verNum: {
					required: true
				},
				uploadContentsFile: {
					required: true
				}
			},
			messages: {
				contentsName: {
					//message : 콘텐츠 이름을 입력해 주십시오.
					required: "<spring:message code='contents.write.037' />"
				},
				verNum: {
					//message : 버전을 입력해 주십시오.
					required: "<spring:message code='contents.write.038' />"
				},
				uploadContentsFile: {
					//message : 파일을 등록해 주십시오.
					required: "<spring:message code='contents.write.039' />"
				} 
			},
			errorPlacement: function(error, element) {
				error.appendTo( element.parent("td") );
			},
			validClass:"success"
		});

		//2. 값 유효체크 완료 되면 데이터 처리
		if(isValid){
			var formData = new FormData($("#contents_write_f")[0]);
			
			$.ajax({
				url:"/app/makeLocal.html",
				type:"POST",
				data:formData,
				contentType:false,
				processData:false,
				success:function(result){
					switch(result){
						case 0 : //success
							alert("<spring:message code='app.modify.alert2' />")
							window.close();
							break ;
						case 1 : //fail
							alert("<spring:message code='app.modify.alert1' />" );
							break;
					}
				}
			});
		}
	}
}

</script>
</head>
<body class="popup" style="width:800px; height:600px;overflow-y:hidden;">
<!-- wrap -->
<div class="pop_wrap" style="width: 745px;">

	<!-- conteiner -->
	<div id="container">
		
		<!-- contents list_area -->
		<div class="contents list_area">
		
			<div class="pop_header clfix">
				<c:choose>
					<c:when test="${popupGb == 'newVer'}">
						<h1><spring:message code='app.request.009' /></h1>
					</c:when>
					<c:otherwise>
						<h1><spring:message code='app.request.008' /></h1>
					</c:otherwise>
				</c:choose>
			</div>
	
			<!-- contents_detail -->
			<div class="pop_contents">
				<div id="verNumText">	
					<table>
						<tr>
							<td style="text-align: left; width:30%;">
								<h2 class="pd20"><spring:message code='app.request.007' />${nowVer }</h2>
							</td>
							<td style="text-align: left; width:20%;">
								<input id="dupCheckVerNum" name="dupCheckVerNum" type="text" value="">
							</td>
							<td style="text-align: center; width:25%;">
								<div id="duplicateVerNum">
									<a href="#duplicateBtn" id="duplicateBtn" class="btn btnL btn_gray_light"><spring:message code='app.request.010' /></a>
								</div>
							</td>
							<td style="text-align: center; width:25%;">
								<a href="#appMake" id="appMake" class="btn btnL btn_red">
									<c:choose>
										<c:when test="${popupGb == 'newVer'}">
											<spring:message code='app.request.006' />
										</c:when>
										<c:otherwise>
											<spring:message code='app.request.005' />
										</c:otherwise>
									</c:choose>
								</a>
							</td>
						</tr>
					</table>
				</div>
				
				<table>
					<tr>
						<td style="text-align: left; width:30%;">
							<h2 class="pd20"><spring:message code='app.inapp.list.text23' /></h2>
						</td>
						<td style="text-align: left; width:70%;" colspan="3">
							<div class="radio_area radio_area_type2">
								<input name="appContentsGb" id="typeLocal" type="radio" value="local">
									<label for="typeLocal"><spring:message code='app.request.contentsAdd.001' /></label>
								<input name="appContentsGb" id="typeExist" type="radio" value="exist">
									<label for="typeExist"><spring:message code='app.request.contentsAdd.002' /></label>
							</div>
						</td>
					</tr>
					<tr>
						<c:choose>
							<c:when test="${ostype != '4'}">
								<td style="text-align: left; width:30%;">
									<h2 class="pd20"><spring:message code='app.write.text15' /></h2>
								</td>
								<td style="text-align: left; width:70%;" colspan="3">
									<select id="provisionSelect" name="provisionSelect">
										<c:if test="${provision ne null}">
											<c:forEach var="i" begin="0" end="${provision.size()-1}">
												<option value="${provision[i].provSeq }">
													${provision[i].provName }
												</option>
											</c:forEach>
										</c:if>
									</select>
								</td>
							</c:when>
						</c:choose>
					</tr>
				</table>
					
				<div id="localUpload" style="height:400px;overflow-y:scroll;">
					<div class="contents join_area_pop">
						<!-- 콘텐츠 등록 -->
						<form action="#" id="contents_write_f" method="POST" enctype="multipart/form-data" style="width:700px; margin: 0 auto;">
							<input type="hidden" id="completGb"  name="completGb" value="2"/>
							<input type="hidden" name="limitGb" value="2"/>
							
							<input type="hidden" name="companySeq" value="<sec:authentication property="principal.memberVO.companySeq" />"/>
							<input type="hidden" name="regUserSeq" value="<sec:authentication property="principal.memberVO.userSeq" />"/>
							<input type="hidden" name="regUserId" value="<sec:authentication property="principal.memberVO.userId" />"/>
							<input type="hidden" name="regUserGb" value="<sec:authentication property="principal.memberVO.userGb" />"/>
							<input type="hidden" name="chgUsersSeq" value="<sec:authentication property="principal.memberVO.userSeq" />"/>
							<input type="hidden" name="chgUsersId" value="<sec:authentication property="principal.memberVO.userId" />"/>
							<input type="hidden" name="chgUsersGb" value="<sec:authentication property="principal.memberVO.userGb" />"/>
						
							<input type="hidden" id="relatedAppNameLocal" name="relatedAppName" value="">
							<input type="hidden" id="relatedAppSeqLocal" name="relatedAppSeq" value="">
							<input type="hidden" id="relatedAppTypeLocal" name="relatedAppType"  value="">
							<input type="hidden" id="relatedAppVerNumLocal" name="relatedAppVerNum" value="">
							<input type="hidden" id="provisionLocal" name="provisionSeq" value="">
							<input type="hidden" id="popupGb" name="popupGb"  value="${popupGb }">
							
							<div class="section fisrt_section">
								<div class="table_area">
									<table class="rowtable writetable">
										<colgroup>
											<col style="width:150px">
											<col style="">
											<col style="width:70px">
											<col style="width:150px">
										</colgroup>
										<tr>
											<th scope="row"><label class="title" for="contentsName"><em>*</em> <spring:message code='contents.write.002' /></label></th><!--message : 이름  -->
											<td colspan="3">
												<input id="contentsName" name="contentsName" type="text" style="width:94.7%;">						
											</td>
										</tr>
										<tr>
											<th scope="row"><label class="title" for="contentsType"><em>*</em> <spring:message code='contents.write.003' /></label></th><!--message : 유형 -->
											<td>
												<select id="contentsType" name="contentsType" style="width:150px;">
													<option value="1" >HTML5</option>
												</select>
											</td>
											<th style="text-align:right; width:150px;"><label class="title" for="verNum"><em>*</em> <spring:message code='contents.write.007' /></label></th><!--message : 버전 -->
											<td>
												<input id="verNum" name="verNum" type="text" style="width:80%;">						
											</td>
										</tr>
										<tr>
											<th scope="row"><label class="title" for="descriptionText"><spring:message code='contents.write.010' /></label></th><!--message : 설명 -->
											<td colspan="3">
												<textarea id="descriptionText" name="descriptionText" cols="" rows="4" style="width:95%;"></textarea>
											</td>
										</tr>
										<tr style="border-bottom : thin dotted #000000;">
											<th scope="row"><label class="title" for="uploadContentsFile"><em>*</em> <spring:message code='contents.write.011' /></label></th><!--message : 업로드 -->
											<td colspan="3">
												<input id="uploadContentsFile" name="uploadContentsFile" type="file" style="width:95%;">
											</td>
										</tr>
										<tr>
											<th><span class="title"><em>*</em> <spring:message code='contents.write.014' /></span></th><!--message : 사용여부 -->
											<td colspan="3">
												<div class="radio_area">
													<input name="useGb" id="u_y" type="radio" value="1" checked="checked"> <label for="u_y"><spring:message code='contents.write.015' /></label><!--message : 예 -->
													<input name="useGb" id="u_n" type="radio" value="2" disabled="disabled"> <label for="u_n"><spring:message code='contents.write.016' /></label><!--message : 아니오 -->
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="title"><em>*</em> <spring:message code='contents.write.020' /></span></th><!--message : 배포 범위 -->
											<td colspan="3">
												<div class="radio_area coupon_area" style="width:100%">
													<span style="width:100%; margin-top:9px;">
														<input type="radio" name="distrGb" id="m1" value="1" disabled="disabled"/><label for="m1"><spring:message code='contents.write.021' /></label><!--message : 회원 로그인 -->
														<input type="radio" name="distrGb" id="m2" value="2" disabled="disabled"/><label for="m2"><spring:message code='contents.write.022' /></label><br/><!--message : 비회원 -->
													</span>
													<span>
														<input type="radio" name="memDownGb" id="d1" disabled="disabled"/> <label for="d1"><spring:message code='contents.write.023' /></label><!--message : 다운로드 횟수 -->
														<input name="memDownAmt" type="text"  class="tCenter" style="width:120px;" readonly>
														&nbsp;&nbsp; <spring:message code='anonymous.option.010' />&nbsp;<input name ="memDownCnt" type="text" class="tCenter" style="width:120px;" readonly onkeypress="return digit_check(event)" maxlength="3"/>
													</span>
													<br>
													<span>
														<input type="radio" name="memDownGb" id="d2" disabled="disabled" /> <label for="d2"><spring:message code='contents.write.029' /></label><!--message : 유효기간 -->
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="d_SDATE" name="SDATE" type="text" title="start date" class="date fmDate1" value="" disabled readonly/>
														&nbsp;&nbsp;~&nbsp;&nbsp;
														<input id="d_EDATE" name="EDATE" type="text" title="end date"   class="date toDate1" value="" disabled readonly/>
													</span>
													<br>
													<span>
														<input type="radio" name="memDownGb" id="d3"  value="3" disabled="disabled" /> <label for="d3"><spring:message code='anonymous.option.002' /></label>
													</span>
												</div>
											</td>
										</tr>
										<tr><!--message : 비회원용 쿠폰 발행 -->
											<th><span class="title spacing"><em>*</em> <spring:message code='contents.write.025' /></span> <br>&nbsp;&nbsp;&nbsp;<!-- <a href="#"><img src="../images/btn_q.jpg" alt="" title="쿠폰 사용 도움말"></a> --></th>
											<td colspan="3">
												<div class="radio_area coupon_area" style="width:100%">
													<span style="width:100%;">
														<input name="couponGb" id="cou_y" type="radio" value="1" disabled="disabled"> <label for="cou_y"><spring:message code='contents.write.026' /></label><!--message : 예 -->
														<input name="couponGb" id="cou_n" type="radio" value="2" disabled="disabled"> <label for="cou_n" style="margin-right:78px"><spring:message code='contents.write.027' /></label><!--message : 아니오 -->
														<input id="couponNum" name="couponNum" type="text" style="width:43.2%;" readonly>
													</span>
													<br/>
													<span style="width:100%;">
														<input type="radio" name="nonmemDownGb" id="c1"  disabled="disabled"/> <label for="c1"><spring:message code='contents.write.028' /></label><!--message : 다운로드 횟수 -->
														<input name="nonmemDownAmt" type="text"  class="tCenter" style="width:120x;" readonly>
														&nbsp;&nbsp; <spring:message code='anonymous.option.010' />&nbsp;<input name="nonmemDownCnt" type="text"  class="tCenter" style="width:120px; " disabled readonly value="" onkeypress="return digit_check(event)" maxlength="3"/>
													</span>
													<br>
													<span style="width:100%;">	
														<input type="radio" name="nonmemDownGb" id="c2" disabled="disabled" /> <label for="c2"><spring:message code='contents.write.029' /></label><!--message : 유효기간 -->
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="c_SDATE" name="nonmemDownStartDt" type="text" title="start date" class="date fmDate1" value="" disabled readonly />
														&nbsp;&nbsp;~&nbsp;&nbsp;
														<input id="c_EDATE" name="nonmemDownStartDt" type="text" title="end date"   class="date toDate1" value="" disabled readonly/>
													</span>
													<br>
													<span style="width:100%;">
														<input type="radio" name="nonmemDownGb" id="c3"  value="3" disabled="disabled" /> <label for="c3"><spring:message code='anonymous.option.002' /></label>
													</span>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</form>
						<!-- //콘텐츠 등록 -->
					</div>
				</div>
				
				<div id="contentListUpload">
					<form name="searchForm" id="searchForm">
						<input type="hidden" id="radioSearch" name="radioSearch" value="" />
						<input type="hidden" id="popupGb" name="popupGb" value="${popupGb }" />
						<input type="hidden" id="dupleVerNum" name="dupleVerNum" value="" />
						<input type="hidden" id="dupleCheckYN" name="dupleCheckYN" value="" />
						<input type="hidden" id="nowVer" name="nowVer" value="${nowVer }" />
						
						<div class="form_area tRight">
							<spring:message code='app.request.search.001' />
							<select name="searchDate" id="searchDate">
								<option value="all" <c:if test="${'all' eq searchDate }">selected="selected"</c:if>><spring:message code='app.request.search.003' /></option>
								<option value="week1" <c:if test="${'week1' eq searchDate }">selected="selected"</c:if>><spring:message code='app.request.search.004' /></option>
								<option value="week2" <c:if test="${'week2' eq searchDate }">selected="selected"</c:if>><spring:message code='app.request.search.005' /></option>
								<option value="month1" <c:if test="${'month1' eq searchDate }">selected="selected"</c:if>><spring:message code='app.request.search.006' /></option>
								<option value="month3" <c:if test="${'month3' eq searchDate }">selected="selected"</c:if>><spring:message code='app.request.search.007' /></option>
								<option value="month6" <c:if test="${'month6' eq searchDate }">selected="selected"</c:if>><spring:message code='app.request.search.008' /></option>
							</select>
							
							<spring:message code='app.request.search.002' />
							<input name="searchValue" id="searchValue" type="text" style="width:250px;" value="${searchValue }">
							<a href="javascript:goToSearch();" class="btn btnM  btn_gray_dark"><spring:message code='app.inapp.list.text9' /></a>
						</div>
					</form>
					
					<form name="appRequestForm" id="appRequestForm">
						<input type="hidden" id="duplicateCheck" name="duplicateCheck " value="N" />
						<input type="hidden" id="storeBundleId" name="storeBundleId " value="" />
						<input type="hidden" id="ostype" name="ostype " value="" />
						
						<input type="hidden" id="relatedAppNameExist" name="relatedAppName" value="">
						<input type="hidden" id="relatedAppSeqExist" name="relatedAppSeq" value="">
						<input type="hidden" id="relatedAppTypeExist" name="relatedAppType"  value="">
						<input type="hidden" id="relatedAppVerNumExist" name="relatedAppVerNum" value="">
						<input type="hidden" id="provisionExist" name="provisionSeq" value="">
						
						<div class="pop_section">
							<!-- table_area -->
							<div class="table_area" style="height:330px;overflow-y:scroll;">
								<table class="coltable">
									<colgroup>
										<col style="width:">
										<col style="width:90px">
										<col style="width:90px">
										<col style="width:105px">
										<col style="width:90px">
										<col style="width:90px">
										<col style="width:50px">
									</colgroup>
									<caption></caption>
									<tr>
										<th scope="col"><spring:message code='app.request.list.001' /></th>
										<th scope="col"><spring:message code='app.request.list.002' /></th>
										<th scope="col"><spring:message code='app.request.list.003' /></th>
										<th scope="col"><spring:message code='app.request.list.004' /></th>
										<th scope="col"><spring:message code='app.request.list.005' /></th>
										<th scope="col"><spring:message code='app.request.list.006' /></th>
										<th scope="col"><spring:message code='app.request.list.007' /></th>
									</tr>
									<c:choose>
										<c:when test="${empty list}">
										<tr>
											<td align="center" colspan="7" ><spring:message code='app.request.no.list' /></td>
										</tr>							
										</c:when>
										<c:otherwise>
											<c:forEach var="result" items="${list}" varStatus="status">
												<tr>
													<td>${result.contentsName }</td>
													<td>${result.verNum }</td>
													<td>${result.contentsSize}</td>
													<td class="state">
														<c:choose>
															<c:when test="${'1' eq result.completGb}">
																<img src="/images/icon_circle_green.png" alt=""> <spring:message code='app.inapp.list.text12' /> 
															</c:when>
															<c:when test="${'2' eq result.completGb}">
																<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.inapp.list.text13' /> 
															</c:when>
															<c:when test="${'3' eq result.completGb}">
																<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.inapp.list.text20' />
															</c:when>
															<c:when test="${'4' eq result.completGb}">
																<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.inapp.list.text21' /> 
															</c:when>
														</c:choose>
													</td>
													<td><fmt:formatDate value="${result.chgDt }" pattern="yyyy-MM-dd"/></td>
													<td>${result.regUserId}</td>
													<td><input type="checkbox" id="contents_${result}" name="contentsList" value="${result.contentsSeq }" ></td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</table>
							</div><!-- //table_area -->
		
							<sec:authorize access="!hasRole('ROLE_ADMIN_SERVICE')">
								<c:choose>
									<c:when test="${fn:indexOf(menuFunction, '122') != -1 }">
										<div class="btn_area_bottom tRight">
											<a class="btn btnL btn_red" href="javascript:appRegist();"><spring:message code='app.inapp.list.text14' /></a>
										</div>
									</c:when>
								</c:choose>
							</sec:authorize>
						</div>	
					</form>
				</div>
			</div>
			<!-- //contents_detail -->
				
		</div>
		<!-- contents list_area -->
	</div>
	<!-- //conteiner -->
</div>
<!-- //wrap -->

</body>
</html>
