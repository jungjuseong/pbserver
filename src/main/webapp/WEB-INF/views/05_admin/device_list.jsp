<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../inc/top.jsp" %>

<script>

$(document).ready(function(){
	$("#tempSearch").keypress(function(event){
		 if (event.keyCode == 13) {
		 	event.preventDefault();
		 	goToSearch();
		 }
	});
	
	if('${param.searchType}' == '1'){
		$("#userId").attr("selected", "selected");
	}else if('${param.searchType}' == '2'){
		$("#userName").attr("selected", "selected");
	}else if('${param.searchType}' == '3'){
		$("#onedepartment").attr("selected", "selected");
	}else if('${param.searchType}' == '4'){
		$("#twodepartment").attr("selected", "selected");
	}
	$("[name=comboBox]").change(function(){
		$("#searchType").val($(this).val());
	});
});


function goToSearch(){
	$("#searchValue").val(encodeURI($("#tempSearch").val()));

	document.deviceFrm.action='/man/device/list.html?page=1';
	document.deviceFrm.submit();
}

</script>
</head>
<body>
<div id="wrap" class="sub_wrap">
	
	<%@ include file="../inc/header.jsp" %>
	
	<div id="container">
		<div class="contents list_area">
			<!-- man header -->
			<%@ include file="../inc/man_header.jsp" %>
			<!-- //man header -->
			<h2> <spring:message code='extend.local.050' /></h2>

			<div class="section fisrt_section">
				<form name="deviceFrm" method="get" action="/man/device/list.html?page=1" >
					<input type="hidden" name="page" id="page" value="${ param.page }">
					<input type="hidden" name="searchType" id="searchType" value="${param.searchType }">
					<input type="hidden" id="searchValue" name="searchValue" type="text" value="" style="width:250px;">
				</form>
				<div class="con_header clfix">
					<div class="result fLeft">
						<spring:message code='template.list.002_1' /> ${deviceList.totalCount }<spring:message code='template.list.002_2' />
					</div>
					<div class="form_area fRight">
						<form>
							<fieldset>
								<select id="comboBox" name="comboBox">
									<option value="1" id="userId"><spring:message code='extend.local.045' /></option>
									<option value="2" id="userName"><spring:message code='extend.local.047' /></option>
									<option value="3" id="onedepartment"><spring:message code='extend.local.048' />1</option>
									<option value="4" id="twodepartment"><spring:message code='extend.local.048' />2</option>
								</select>
								<input name="" id="tempSearch" type="text" value="${ searchValue }">
								<a href="javascript:goToSearch();" class="btn btnM"><spring:message code='user.list.026' /></a>
							</fieldset>
						</form>
					</div>
				</div>
					<div class="table_area">
						<table class="coltable">
							<colgroup>
								<col style="width:140px">
								<col style="width:120px">
								<col style="width:140px">
								<col style="width:130px">
								<col style="width:130px">
								<col style="width:100px">
								<col style="width:110px">
							</colgroup>
							<tr>
								<th scope="col"><spring:message code='extend.local.045' /></th>
								<th scope="col"><spring:message code='extend.local.046' /></th>
								<th scope="col"><spring:message code='extend.local.047' /></th>
								<th scope="col"><spring:message code='extend.local.048' />1</th>
								<th scope="col"><spring:message code='extend.local.048' />2</th>
								<th scope="col"><spring:message code='extend.local.051' /></th>
								<th scope="col"><spring:message code='extend.local.049' /></th>
							</tr>
							<c:choose>
								<c:when test="${empty deviceList.list}">
								<tr>
									<!--message : 등록된 사용자가 없습니다.   -->
									<td align="center" colspan="9" > <spring:message code='extend.local.052' /><%-- <spring:message code='app.no.contents' /> --%></td>
								</tr>
								</c:when>
							<c:otherwise>
								<c:forEach var="i" begin="0" end="${deviceList.list.size()-1}">
									<tr>
										<td>
											<c:choose>
												<c:when test="${fn:indexOf(menuFunction, '252') != -1 }">
													<a href="/man/device/modify.html?page=${param.page }&deviceSeq=${deviceList.list[i].deviceSeq}&searchType=${param.searchType}&searchValue=${param.searchValue}">${deviceList.list[i].deviceInfo}</a>
												</c:when>
												<c:otherwise>
													${deviceList.list[i].deviceInfo}
												</c:otherwise>
											</c:choose>
										</td>
										<td>${deviceList.list[i].deviceType }</td>
										<td>${deviceList.list[i].memberVO.userId }</td>
										<td>${deviceList.list[i].memberVO.onedepartmentName}</td>
										<td>${deviceList.list[i].memberVO.twodepartmentName}</td>
										<td>
											<c:if test="${'1' eq deviceList.list[i].useGb}"><img src="/images/icon_circle_green.png" alt=""> <spring:message code='extend.local.053' /></c:if>
											<c:if test="${'2' eq deviceList.list[i].useGb}"><img src="/images/icon_circle_red.png" alt=""> <spring:message code='extend.local.055' /></c:if>
										</td>
										<td><fmt:formatDate value="${deviceList.list[i].regDt}" pattern="yyyy-MM-dd"/></td>
									</tr>
 								</c:forEach>
							</c:otherwise>
						</c:choose>
						</table>
					</div> <!-- table area -->
					<div class="paging">
					<c:if test="${deviceList.startPage != 1 }">
					<!--message : 이전 페이지로 이동  -->
					<a href="/man/device/list.html?page=${deviceList.startPage-deviceList.pageSize}&searchType=${param.searchType}&searchValue=${param.searchValue}" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='user.list.036' />"></a>
					</c:if>
					<c:forEach var="i" begin="${deviceList.startPage }" end="${deviceList.endPage}">
						    <c:if test="${param.page==i }"><span class="current"><c:out value="${i}"/></span></c:if>
						    <c:if test="${param.page!=i }"> <a href="/man/device/list.html?page=${i}&searchType=${param.searchType}&searchValue=${param.searchValue}"><c:out value="${i}"/></a></c:if>
	<%-- 					    <c:out value="${i}"/>
						    <c:if test="${param.page==i }"></strong></c:if> --%>
					</c:forEach>
					<c:if test="${deviceList.endPage != 1 && deviceList.endPage*deviceList.maxResult < deviceList.totalCount}">
					<!--message : 다음 페이지로 이동  -->
					<a href="/man/device/list.html?page=${deviceList.endPage+1}&searchType=${param.searchType}&searchValue=${param.searchValue}" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='user.list.037' />"></a>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->
</div>

</body>
</html>