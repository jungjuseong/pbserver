<%@page import="com.clbee.pbcms.vo.InappVO"%>
<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.clbee.pbcms.vo.InAppList"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>

<script type="text/javascript">
$(document).ready(function(){
	var inappCnt = 0;
	if('${availableCnt }'){
		inappCnt = '${availableCnt}';
	}
	//alert(inappCnt);
	$(window.opener.document).find('[name=inappCnt]').val(inappCnt);
		
	$("#searchValue").keypress(function(e){
		if(e.keyCode=='13'){
			goToSearch();
		}
	});
	
	if("${param.isAvailable}" == "false") {
		$("#all").prop("checked", false);
		$("#isAvailable").val("false");
	}else {
		$("#all").prop("checked", true);
		$("#isAvailable").val("true");
	}

	$("#all").click(function(){
		if($("#isAvailable").val() == "false"){
			$("#isAvailable").val("true");
		}else{
			$("#isAvailable").val("false");
		}
		
		document.appFrm.action='/app/inapp/list.html';
		document.appFrm.method='GET';
		document.appFrm.submit();
	});
});

function appRegist(){
		//window.location.href= '/app/regist.html';
		/* window.location.href='/app/inapp/regist.html'; */
		document.appFrm.action='/app/inapp/regist.html';
		document.appFrm.method='GET';
		//alert(document.appFrm.action);
		document.appFrm.submit();
}

function goToSearch(_page){
	//var page = _page?_page:$('#page').val();
	$('#currentPage').val('1');
	if(_page){
		$('#currentPage').val(_page);
	}
	document.appFrm.action='/app/inapp/list.html';
	document.appFrm.method='GET';
	document.appFrm.submit();
}

function appModify(seq){
	$('#inappSeq').val(seq);
	//window.location.href= '/app/regist.html';
	document.appFrm.action='/app/inapp/modify.html';
	document.appFrm.method='GET';
	document.appFrm.submit();
}

//20180329 : lsy - popup_version
function popup_version(inapp_name, store_bundle_id){
	if(store_bundle_id == null || store_bundle_id == ""){
		alert("<spring:message code='history.007' />");
	}else{
		var winWidth = 850;
		var winHeight = 600;
		var winPosLeft = (screen.width - winWidth)/2;
		var winPosTop = (screen.height - winHeight)/2;
		var url = "/inapp/history.html?store_bundle_id="+store_bundle_id+"&inapp_name="+encodeURI(encodeURIComponent(inapp_name));		
		var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
		
		window.open(url, "versionPopup", opt);
	}
}
//20180329 : lsy - popup_version - end

</script>
</head>
<body class="popup" style="width:700px; height:600px;">
<!-- wrap -->
<div class="pop_wrap" style="width: 745px;">

	<!-- conteiner -->
	<div id="container">
		<div class="contents list_area">
			<div class="pop_header clfix">
				<h1><spring:message code='app.inapp.list.text1' /></h1>
			</div>

			<!-- contents_detail -->
			<div class="pop_contents">
				<h2 class="pd20"><spring:message code='app.inapp.list.text2' /></h2>
				<form name="appFrm" method="post" action="/app/inapp/list.html" >
					<input type="hidden" name="inappSeq" id="inappSeq" value="0">
					<input type="hidden" name="storeBundleId" id="storeBundleId" value="${vo.storeBundleId}">
					<input type="hidden" name="isAvailable" id="isAvailable" value=""/>
					<!-- testModify -->
					<c:if test="${ 'true' eq param.test }">
						<input type="hidden" name="test" id="test" value="${param.test }">
					</c:if>
					<div class="form_area tRight">
						<div class="checkbox_area">
							<input name="checkChkVal_0" id="all" class="checkChkVal" type="checkbox" value="0">
							<label for="all"><spring:message code='app.inapp.list.text19' /></label>
						</div>
						<select name="searchType">
							<option value="inappName" <c:if test="${'inappName' eq inAppList.searchType }">selected="selected"</c:if>><spring:message code='app.inapp.list.text8' /></option>
							<%-- <option value="descriptionText" <c:if test="${'descriptionText' eq inAppList.searchType }">selected="selected"</c:if>><spring:message code='app.inapp.list.text10' /></option> --%>
						</select>
						<input name="searchValue" type="text" style="width:250px;" value="${inAppList.searchValue }">
						<a href="javascript:goToSearch();" class="btn btnM  btn_gray_dark"><spring:message code='app.inapp.list.text9' /></a>
					</div>
				</form>
				<div class="pop_section">
					<div class="result">
						<spring:message code='app.inapp.list.text15' /> ${inAppList.totalCount }<spring:message code='app.inapp.list.text16' />
					</div>
					<!-- table_area -->
					<div class="table_area">
						<table class="coltable">
							<colgroup>
								<col style="width:">
								<col style="width:">
								<col style="width:90px">
								<col style="width:105px">
								<col style="width:90px">
							</colgroup>
							<caption></caption>
							<tr>
								<th scope="col"><spring:message code='app.inapp.list.text3' /></th>
								<th scope="col"><spring:message code='app.inapp.list.text4' /></th>
								<th scope="col"><spring:message code='app.inapp.list.text5' /></th>
								<th scope="col"><spring:message code='app.inapp.list.text6' /></th>
								<th scope="col"><spring:message code='app.inapp.list.text7' /></th>
							</tr>
							<c:choose>
								<c:when test="${empty inAppList.list||0 eq inAppList.totalCount }">
								<tr>
									<td align="center" colspan="5" ><spring:message code='app.no.contents' /></td>
								</tr>							
								</c:when>
								<c:otherwise>
									<c:forEach var="result" items="${inAppList.list}" varStatus="status">
										<tr>
											<td>
												<c:choose>
													<c:when test="${fn:indexOf(menuFunction, '123') != -1 }">
														<a href="javascript:appModify('${result.inappSeq}');">${result.inappName }</a>
													</c:when>
													<c:otherwise>
														${result.inappName }
													</c:otherwise>
												</c:choose>
											</td>
											<td>${result.categoryName }</td><!-- 20180329 : lsy - popup_version -->
											<td><a href="javascript:popup_version('${result.inappName }', '${result.storeBundleId }');">${result.verNum}</a></td>
											<td><fmt:formatDate value="${result.chgDt }" pattern="yyyy-MM-dd"/></td>
											<td class="state">
												<c:choose>
													<c:when test="${'1' eq result.limitGb}">
					 									<img src="/images/icon_circle_red.png" alt=""> <spring:message code='app.inapp.list.text11' />
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${'2' eq result.useGb}">
							 									<img src="/images/icon_circle_red.png" alt=""> <spring:message code='app.inapp.list.text19' />
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${'1' eq result.completGb}">
																		<img src="/images/icon_circle_green.png" alt=""> <spring:message code='app.inapp.list.text12' /> 
																	</c:when>
																	<%-- 20180411 : lsy - add status - distribute Request --%>
																	<c:when test="${'2' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.inapp.list.text13' /> 
																	</c:when>
																	<c:when test="${'3' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.inapp.list.text20' />
																	</c:when>
																	<c:when test="${'4' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.inapp.list.text21' /> 
																	</c:when>
																	<%-- 2018040419 : incorrect source 
																	<c:otherwise>
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.inapp.list.text13' />
																	</c:otherwise>
																	--%>
																</c:choose>											
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</td>
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
			</div>
			<!-- //contents_detail -->
			
		</div>
	</div>
	<!-- //conteiner -->

</div><!-- //wrap -->

</body>
</html>
