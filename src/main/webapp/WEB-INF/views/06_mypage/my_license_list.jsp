<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../inc/top.jsp" %>

<script>
function deleteLicenseUseDevice(licensesubSeq){
	$.ajax({
		url:"/my/deleteLicenseUseDevice.html",
		type:"POST",
		data:{
			"licensesubSeq":licensesubSeq
		},
		success:function(result){
			switch(result){
				case 0 : 
					alert("<spring:message code='mypage.license.device.008' />");
					break;
				case 1 : 
					window.location.reload();
					break;
			}
		}
	});
}

function goToSearch(){
	document.licenseForm.submit();
}

function goToPage(goPage){
	$("#currentPage").val(goPage);
	document.licenseForm.submit();
}
</script>
</head>
<body>

<div id="wrap" class="sub_wrap">
	
	<%@ include file="../inc/header.jsp" %>
	
	<div id="container">
		<form id="licenseForm" name="licenseForm" method="post" action="/my/license.html" >
			<input type="hidden" id="currentPage" name="currentPage" value="1">
			
			<div class="my_license_device list_area">			
				<!-- man header -->
				<%@ include file="../inc/mypage_header.jsp" %>
				<!-- //man header -->
				<h2> <spring:message code='mypage.license.006' /></h2>
					
				<div class="section fisrt_section">
					<div class="paging_license">
						<div class="form_area fRight">
							<a class="btn btnL btn_red" href="/my/licenseRenew.html"><spring:message code='mypage.license.010' /></a>
						</div>
					</div>
					
					<div class="table_area">
						<table class="coltable">
							<colgroup>
								<col style="width:">
								<col style="width:100px">
								<col style="width:120px">
								<col style="width:120px">
								<col style="width:50px">
								<col style="width:200px">
							</colgroup>
							<tr>
								<th scope="col"><spring:message code='license.list.004' /></th>
								<th scope="col"><spring:message code='license.list.018' /></th>
								<th scope="col"><spring:message code='license.list.007' /></th>
								<th scope="col"><spring:message code='license.list.009' /></th>
								<th scope="col"><spring:message code='mypage.license.007' /></th>
								<th scope="col"><spring:message code='mypage.license.008' /> / Total Copy</th>
							</tr>
							<tr>
							</tr>
							<c:choose>
								<c:when test="${empty licenseList.list}">
								<tr>
									<td align="center" colspan="5" ><spring:message code='license.list.012' /></td>
								</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="i" begin="0" end="${licenseList.list.size()-1}">
										<tr>
											<td>${licenseList.list[i].licenseNum}</td>
											<td>
												<c:if test="${licenseList.list[i].periodGb eq '1' }" > <spring:message code='license.generate.003' /></c:if>
												<c:if test="${licenseList.list[i].periodGb eq '2' }" > <spring:message code='license.generate.004' /></c:if>
												<c:if test="${licenseList.list[i].periodGb eq '3' }" > <spring:message code='license.generate.008' /></c:if>
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
												<c:choose>
													<c:when test="${licenseList.list[i].remainDt eq -1}">-</c:when>
													<c:otherwise>${licenseList.list[i].remainDt}<spring:message code='mypage.license.009' /></c:otherwise>
												</c:choose>
											</td>
											<td>
												${licenseList.list[i].licenseUser} / ${licenseList.list[i].userCopyCount}
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</table>
					</div> <!-- table area -->
					
					<div class="paging_license_bottom">
						<div class="form_area fRight">
							<input type="text" id="searchValue" name="searchValue" value="${searchValue }" placeholder="<spring:message code='mypage.license.device.009' />">
							<a href="javascript:goToSearch();" class="btn btnM"><spring:message code='user.list.026' /></a>
						</div>
					</div>
				</div>
			</div>
		
			<div class="license_device list_area">
				<div class="section section_license">
					<div class="table_area">
						<table class="coltable">
							<colgroup>
								<col style="width:80px">
								<col style="width:80px">
								<col style="width:80px">
								<col style="width:80px">
								<col style="width:30px">
							</colgroup>
							<tr>
								<th scope="col"><spring:message code='mypage.license.device.009' /></th>
								<th scope="col"><spring:message code='mypage.license.device.004' /></th>
								<th scope="col">OS</th>
								<th scope="col"><spring:message code='mypage.license.device.005' /></th>
								<th scope="col"></th>
							</tr>
							<c:choose>
								<c:when test="${empty licenseUseDevice.list}">
								<tr>
									<td align="center" colspan="5" ><spring:message code='mypage.license.device.006' /></td>
								</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="i" begin="0" end="${licenseUseDevice.list.size()-1}">
										<tr>
											<td>${licenseUseDevice.list[i].userId}</td>
											<td>${licenseUseDevice.list[i].deviceName}</td>
											<td>${licenseUseDevice.list[i].deviceOs}</td>
											<td><fmt:formatDate value="${licenseUseDevice.list[i].useStartDt}" pattern="yyyy-MM-dd"/></td>
											<td>
												<a href="" id="disabled" onclick="deleteLicenseUseDevice('${licenseUseDevice.list[i].licensesubSeq}')" class="btn btnXXS"><spring:message code='mypage.license.device.007' /></a>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</table>
					</div> <!-- table area -->
				</div>
			
				<div class="paging">
					<c:if test="${licenseUseDevice.startPage != 1 }">
					<!--message : 이전 페이지로 이동  -->
						<a href="javascript:goToPage('${licenseUseDevice.startPage-licenseUseDevice.pageSize}');" class="paging_btn"><img src="/images/icon_arrow_prev_page.png"></a>
					</c:if>
					<c:forEach var="i" begin="${licenseUseDevice.startPage }" end="${licenseUseDevice.endPage}">
					    <c:if test="${currentPage==i }"><span class="current"><c:out value="${i}"/></span></c:if>
					    <c:if test="${currentPage!=i }"> <a href="javascript:goToPage('${i}');"><c:out value="${i}"/></a></c:if>
					</c:forEach>
					<c:if test="${licenseUseDevice.endPage != 1 && licenseUseDevice.endPage*licenseUseDevice.maxResult < licenseUseDevice.totalCount}">
						<!--message : 다음 페이지로 이동  -->
						<a href="javascript:goToPage('${licenseUseDevice.endPage+1}');" class="paging_btn"><img src="/images/icon_arrow_next_page.png"></a>
					</c:if>
				</div>				
				<!--페이징-->
			</div>
		</form>
	</div>

	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->
</div>

</body>
</html>