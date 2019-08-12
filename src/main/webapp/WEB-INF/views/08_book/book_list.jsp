<%@page import="com.clbee.pbcms.vo.AppVO"%>
<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.clbee.pbcms.vo.AppList"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="curi" value="${requestScope['javax.servlet.forward.request_uri']}" />

</head>

<script type="text/javascript">
$(document).ready(function(){	
	$("#tempSearch").keypress(function(e){
		if(e.keyCode=='13')goToSearch();
	});
});	


// 앱 등록 버튼 눌렀을시
function appRegist(){
	$('#appSeq').remove();
	//window.location.href= '/app/regist.html';
	//testModify
	if( "${param.test} " == true ){
		window.location.href='/book/regist.html?test='+"${param.test}";
	}else{
		//baseCode
		document.appFrm.action='/book/regist.html'; 
		document.appFrm.submit();
	}
}

// 검색버튼 눌럿을시
function goToSearch(_page){
	//var page = _page?_page:$('#page').val();
	
	$("#currentPage").val(_page);
	if(_page==""){
		$("#currentPage").val('1');
	}

	$("#searchValue").val(encodeURI($("#tempSearch").val()));
	document.appFrm.action='/book/list.html';
	document.appFrm.submit();
}

// 앱 이름을 눌렀을시 -> 앱 수정화면으로 이동
function appModify(seq){
	$('#appSeq').val(seq);
	
	//window.location.href= '/app/regist.html';
	document.appFrm.action='/book/modify.html';
	document.appFrm.submit();
}


//inapp search popup
var inappPopup
function inAppPop(storeBundleId){
	//alert('앱 수정 화면에서만 등록이 가능합니다');
	//alert('앱 수정 화면에서만 등록이 가능합니다');
	var winWidth = 750;
	var winHeight = 600;
	var winPosLeft = (screen.width - winWidth)/2;
	var winPosTop = (screen.height - winHeight)/2;
	var url = "/book/inapp/list.html?storeBundleId=" + storeBundleId;		
	var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
	//if(!templatePopup){
		inappPopup = window.open(url, "inappPopup", opt);			
	//}			
};


//testModify
function goToTest(){
	window.location.href="/book/list.html";
}


function moveToDownList(){
	var isMobile = "";

	
	if(/Android/i.test(navigator.userAgent)){
		isMobile = "ADD";
	}else if(/iPhone|iPad/i.test(navigator.userAgent)){
		isMobile = "IPHD";
	}else{
		isMobile = "NOMB"
	}

	window.location.href="/down/list.html?currentPage=1&isMobile="+isMobile;
}

</script>
<body>

<!-- wrap -->
<div id="wrap" class="sub_wrap">
	
	<!-- header -->
	<%@ include file="../inc/header.jsp" %>
	<!-- conteiner -->
	<div id="container">
	
		<div class="contents list_area">
			<h2><spring:message code='app.list.title' /></h2>
			<!-- testModify -->
			<!-- <a href="javascript:goToTest();" class="btn btnM">테스트</a> -->
			<!-- section -->
			<div class="section fisrt_section">
				<form name="appFrm" method="get" action="/book/list.html" >
					<input type="hidden" name="currentPage" id="currentPage" value="${appList.currentPage }">
					<input type="hidden" name="appSeq" id="appSeq" value="">
					<input type="hidden" id="searchValue" name="searchValue" type="text" value="" style="width:250px;">
					<!-- testModify -->
					<c:if test="${ 'true' eq param.test }">
						<input type="hidden" id="test" name="test" value="${param.test }" >
					</c:if>
				</form>
				<div class="con_header clfix">
					<div class="form_area fRight">
						<select id="searchType" name="searchType">
							<!-- 
								<option value="" <c:if test="${empty appList.searchType }">selected="selected"</c:if>><spring:message code='app.list.table.search2' /></option>
							 -->
							<option value="appName" <c:if test="${'appName' eq appList.searchType }">selected="selected"</c:if>><spring:message code='app.list.table.search3' /></option>
							<!-- <option value="ostype" <c:if test="${'ostype' eq appList.searchType }">selected="selected"</c:if>>운영체제</option> -->
						</select>
						<input id="tempSearch" name="tempSearch" type="text" value="${appList.searchValue }" style="width:250px;">
						<a href="javascript:goToSearch();" class="btn btnM"><spring:message code='app.list.table.search1' /></a>
					</div>
				</div>
				
				<!-- table_area -->
				<div class="table_area">
					<table class="coltable">
						<colgroup>
							<col style="width:24px">
							<col style="width:">
							<col style="width:60px">
							<col style="width:110px">
							<col style="width:90px">
							<col style="width:90px">
							<col style="width:90px">
							<col style="width:120px">
							<col style="width:110px">
							<col style="width:90px">
						</colgroup>
						<caption></caption>
						<tr>
							<th scope="col"></th>
							<th scope="col"><spring:message code='app.list.table.col1' /></th>
							<th scope="col"><spring:message code='app.list.table.col2' /></th>
							<th scope="col"><spring:message code='app.list.table.col3' /></th>
							<th scope="col"><spring:message code='app.list.table.col4' /></th>
							<th scope="col"><spring:message code='app.list.table.col5' /></th>
							<th scope="col"><spring:message code='app.list.table.col6' /></th>
							<th scope="col"><spring:message code='app.list.table.col7' /></th>
							<th scope="col"><spring:message code='app.list.table.col8' /></th>
							<th scope="col"><spring:message code='app.list.table.col9' /></th>
						</tr>
						<c:choose>
							<c:when test="${empty appList.list}">
								<tr>
									<td align="center" colspan="10" ><spring:message code='app.no.contents' /></td>
								</tr>
							</c:when>
							<c:otherwise>
									<c:forEach var="result" items="${appList.list}" varStatus="status">
									<tr id="${result.appSeq}">
										<td><a href="javascript:appModify('${result.appSeq}');"><c:if test="${not empty result.iconSaveFile }"><img src="<spring:message code='file.upload.path.app.icon.file' />${result.iconSaveFile }" alt="" style="max-width:24px;" /></c:if></a></td>
										<td><a href="javascript:appModify('${result.appSeq}');">${result.appName }</a></td>
										<td>${result.verNum}</td>
										<td>
											<c:choose>
												<c:when test="${'1' eq result.ostype }">Universal</c:when>
												<c:when test="${'2' eq result.ostype }">iPhone</c:when>
												<c:when test="${'3' eq result.ostype }">iPad</c:when>
												<c:when test="${'4' eq result.ostype }">Android</c:when>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${'2' eq result.regGb }">Single</c:when>
												<c:when test="${'1' eq result.regGb }">
												<a href="javascript:inAppPop('${result.storeBundleId}');" alt="">Library</a>
												</c:when>
											</c:choose>
										</td>
										<td>${result.appContentsAmt}</td>
										<td>${result.appSize}</td>
										<td>
										<c:choose>
										<c:when test="${'1' eq result.limitGb}">
										</c:when>
										<c:otherwise>
											<c:choose>
											<c:when test="${'2' eq result.useGb}">
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${'1' eq result.completGb}">										
													<c:choose>
														<c:when test="${'2' eq result.distrGb && '1' eq result.couponGb}">
															<c:choose>														
																<c:when test="${'1' eq result.nonmemDownGb }">
																	${result.nonmemDownAmt}<spring:message code='app.list.text1' />
																</c:when>
																<c:when test="${'2' eq result.nonmemDownGb }">
																	<fmt:formatDate value="${result.nonmemDownStarDt}" pattern="yyyy-MM-dd"/>~
																	<fmt:formatDate value="${result.nonmemDownEndDt}" pattern="yyyy-MM-dd"/>
																</c:when>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${'1' eq result.memDownGb }">
																	${result.memDownAmt}<spring:message code='app.list.text1' />
																</c:when>
																<c:when test="${'2' eq result.memDownGb }">	
																	<fmt:formatDate value="${result.memDownStartDt}" pattern="yyyy-MM-dd"/>~
																	<fmt:formatDate value="${result.memDownEndDt}" pattern="yyyy-MM-dd"/>
																</c:when>
															</c:choose>														
														</c:otherwise>
													</c:choose>
													</c:when>
													<c:otherwise>
													</c:otherwise>												
												</c:choose>											
											</c:otherwise>
											</c:choose>
										</c:otherwise>
										</c:choose>
										</td>
										<td><fmt:formatDate value="${result.chgDt }" pattern="yyyy-MM-dd"/></td>
										<td class="state">
										<c:choose>
										<c:when test="${'1' eq result.limitGb}">
		 									<img src="/images/icon_circle_red.png" alt=""> <spring:message code='app.list.table.status2' /><!-- 제한 -->
										</c:when>
										<c:otherwise>
											<c:choose>
											<c:when test="${'2' eq result.useGb}">
			 									<img src="/images/icon_circle_red.png" alt=""> <spring:message code='app.list.table.status2' /><!-- 미사용 -->
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${'1' eq result.completGb}">
														<img src="/images/icon_circle_green.png" alt=""> <spring:message code='app.list.table.status1' /><!-- 완료 --> 
													</c:when>
													<c:otherwise>
														<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.list.table.status3' /><!-- 테스트중 -->
													</c:otherwise>
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
				
				<!--페이징-->
				<div class="paging">
					<c:if test="${appList.startPage != 1 }">
					<a href="javascript:goToSearch('{appList.startPage-appList.pageSize}');" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="prev"></a>
					</c:if>
						<c:forEach var="i" begin="${appList.startPage }" end="${appList.endPage}">
							    <c:if test="${appList.currentPage==i }"><span class="current"><c:out value="${i}"/></span></c:if>
							    <c:if test="${appList.currentPage!=i }"> <a href="javascript:goToSearch('${i}');"><c:out value="${i}"/></a></c:if>
								<%--
									<c:out value="${i}"/>
							    	<c:if test="${param.page==i }"></strong></c:if> 
							    --%>
						</c:forEach>
					<c:if test="${appList.endPage*appList.maxResult < appList.totalCount}">
					<a href="javascript:goToSearch('${appList.endPage+1}');" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="next"></a>
					</c:if>
				</div>				
				<!--//페이징-->

				<!-- btn area -->
				<sec:authorize access="!hasRole('ROLE_ADMIN_SERVICE')">
					<div class="btn_area_bottom fRight clfix">
						<a class="btn btnL btn_red" href="javascript:appRegist();"><spring:message code='app.list.table.regist' /></a>
					</div>
				</sec:authorize>
				<!-- //btn area -->
			</div><!-- //section -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>
