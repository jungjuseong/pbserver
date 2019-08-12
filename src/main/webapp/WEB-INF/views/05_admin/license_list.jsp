<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../inc/top.jsp" %>

<script>
$(document).ready(function(){
	$("#searchMember").keypress(function(event){
		 if (event.keyCode == 13) {
		 	event.preventDefault();
		 	goToSearch();
		 }
	});
	
	if('${searchStatus}' != 'null' || '${searchStatus}' != ''){
		$("[name=searchStatus]").val('${searchStatus}').prop("selected", true);
	}
});

function goToPage(goPage){
	$("#currentPage").val(goPage);
	document.licenseForm.action="/man/license/list.html";
	document.licenseForm.submit();
}

function licenseGeneratePopup(){
	var url = "/man/license/generateLicense.html";
	var popupName = "licenseGeneratePopup";
	
	popupOpen(url, popupName);
}

//라이센스 폐기처리/폐기사유 확인
function customDisposal(licenseSeq, licenseStatus){
	var url = "/man/license/disposalLicense.html?licenseSeq="+licenseSeq+"&licenseStatus="+licenseStatus;
	var popupName = "licenseDisposalPopup";
	
	popupOpen(url, popupName);
}

function popupOpen(url, popupName){
	var winWidth = 520;
	var winHeight = 300;
	var winPosLeft = (screen.width - winWidth)/2;
	var winPosTop = (screen.height - winHeight)/2;
	var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
	
	window.open(url, popupName, opt);
}
</script>
</head>
<body>

<div id="wrap" class="sub_wrap">
	<%@ include file="../inc/header.jsp" %>
	
	<form name="licenseForm" method="post" action="/man/license/list.html" >
		<input type="hidden" name="currentPage" id="currentPage" value="${ currentPage  }">
		
		<div id="container">
			<div class="contents list_area">
				<!-- man header -->
				<%@ include file="../inc/man_header.jsp" %>
				<!-- //man header -->
				<h2> <spring:message code='license.list.001' /></h2>
	
				<div class="section fisrt_section">
					<div class="con_header clfix">
						<div class="result fLeft">
							<spring:message code='template.list.002_1' /> ${licenseList.totalCount }<spring:message code='template.list.002_2' />
						</div>
						<div class="form_area fRight">
							<fieldset>
								<select id="searchStatus" name="searchStatus">
									<option value="0" id="all">all</option>
									<option value="1" id="generate"><spring:message code='license.list.013' /></option>
									<option value="2" id="regist"><spring:message code='license.list.014' /></option>
									<option value="3" id="expire"><spring:message code='license.list.015' /></option>
									<option value="4" id="disposal"><spring:message code='license.list.016' /></option>
								</select>
								<input name="searchMember" id="searchMember" type="text" value="${ searchMember }" placeholder="<spring:message code='license.list.011' />">
								<a href="javascript:goToPage('1');" class="btn btnM"><spring:message code='user.list.026' /></a>
							</fieldset>
						</div>
					</div>
					<div class="table_area">
						<table class="coltable">
							<colgroup>
								<col style="width:">
								<col style="width:100px">
								<col style="width:60px">
								<col style="width:60px">
								<col style="width:100px">
								<col style="width:120px">
								<col style="width:100px">
								<col style="width:100px">
								<col style="width:80px">
							</colgroup>
							<tr>
								<th scope="col"><spring:message code='license.list.004' /></th>
								<th scope="col"><spring:message code='license.list.006' /></th>
								<th scope="col"><spring:message code='license.list.010' /></th>
								<th scope="col"><spring:message code='license.list.018' /></th>
								<th scope="col"><spring:message code='license.list.005' /></th>
								<th scope="col"><spring:message code='license.list.011' /></th>
								<th scope="col"><spring:message code='license.list.007' /></th>
								<th scope="col"><spring:message code='license.list.009' /></th>
								<th scope="col"></th>
							</tr>
							<c:choose>
								<c:when test="${empty licenseList.list}">
									<tr>
										<td align="center" colspan="9" > <spring:message code='license.list.012' /></td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="i" begin="0" end="${licenseList.list.size()-1}">
										<tr>
											<td>${licenseList.list[i].licenseNum}</td>
											<td><fmt:formatDate value="${licenseList.list[i].generateDt}" pattern="yyyy-MM-dd"/></td>
											<td>${licenseList.list[i].userCopyCount}</td>
											<td>
												<c:if test="${licenseList.list[i].periodGb eq '1' }" > <spring:message code='license.generate.003' /></c:if>
												<c:if test="${licenseList.list[i].periodGb eq '2' }" > <spring:message code='license.generate.004' /></c:if>
												<c:if test="${licenseList.list[i].periodGb eq '3' }" > <spring:message code='license.generate.008' /></c:if>
											</td>
											<td>
												<c:if test="${licenseList.list[i].licenseStatus eq '1' }" > <spring:message code='license.list.013' /></c:if>
												<c:if test="${licenseList.list[i].licenseStatus eq '2' }" > <spring:message code='license.list.014' /></c:if>
												<c:if test="${licenseList.list[i].licenseStatus eq '3' }" > <spring:message code='license.list.015' /></c:if>
												<c:if test="${licenseList.list[i].licenseStatus eq '4' }" > <spring:message code='license.disposal.004' /></c:if>
											</td>
											<td>
												<c:choose>
													<c:when test="${empty licenseList.list[i].userId}">-</c:when>
													<c:otherwise>${licenseList.list[i].userId}</c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:choose>
													<c:when test="${empty licenseList.list[i].registDt}">-</c:when>
													<c:otherwise><fmt:formatDate value="${licenseList.list[i].registDt}" pattern="yyyy-MM-dd"/></c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:choose>
													<c:when test="${empty licenseList.list[i].expireDt}">-</c:when>
													<c:otherwise><fmt:formatDate value="${licenseList.list[i].expireDt}" pattern="yyyy-MM-dd"/></c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:if test="${ (licenseList.list[i].licenseStatus eq '1') or (licenseList.list[i].licenseStatus eq '2') or (licenseList.list[i].licenseStatus eq '4') }" >
													 <a href="#disposal" id="disposal" onclick="customDisposal('${licenseList.list[i].licenseSeq}', '${licenseList.list[i].licenseStatus}')" class="btn btnXXS">
													 	<c:choose>
													 		<c:when test="${ (licenseList.list[i].licenseStatus eq '4') }"><spring:message code='license.disposal.005' /></c:when>
													 		<c:otherwise><spring:message code='license.disposal.004' /></c:otherwise>
													 	</c:choose>
													 </a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</table>
					</div> <!-- table area -->
					<div class="paging">
						<c:if test="${licenseList.startPage != 1 }">
						<!--message : 이전 페이지로 이동  -->
							<a href="javascript:goToPage('${licenseList.startPage-licenseList.pageSize}');" class="paging_btn"><img src="/images/icon_arrow_prev_page.png"></a>
						</c:if>
						<c:forEach var="i" begin="${licenseList.startPage }" end="${licenseList.endPage}">
						    <c:if test="${currentPage==i }"><span class="current"><c:out value="${i}"/></span></c:if>
						    <c:if test="${currentPage!=i }"> <a href="javascript:goToPage('${i}');"><c:out value="${i}"/></a></c:if>
						</c:forEach>
						<c:if test="${licenseList.endPage != 1 && licenseList.endPage*licenseList.maxResult < licenseList.totalCount}">
							<!--message : 다음 페이지로 이동  -->
							<a href="javascript:goToPage('${licenseList.endPage+1}');" class="paging_btn"><img src="/images/icon_arrow_next_page.png"></a>
						</c:if>
					</div>
					<div class="btn_area_bottom fRight clfix" style="margin-top:-42px;">
						<a class="btn btnL btn_red" href="javascript:licenseGeneratePopup();"><spring:message code='license.list.017' /></a>
					</div>
				</div>
			</div>
		</div>
	</form>

	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->
</div>

</body>
</html>