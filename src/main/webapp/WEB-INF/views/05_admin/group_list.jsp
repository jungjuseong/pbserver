<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.clbee.pbcms.vo.GroupList"%>
<%@ include file="../inc/top.jsp" %>
</head>

<script>
function goToModify(groupSeq){
	window.location.href="/man/group/modify.html?currentPage=${param.currentPage}&searchValue="+$("#searchValue").val()+"&groupSeq="+groupSeq;
}

function goToSearch(){
	window.location.href="/man/group/list.html?currentPage=1&searchValue="+$("#searchValue").val();
}

//계정 탈퇴
function customDelete(groupSeq){
	//정말 삭제하시겠습니까?
	if(confirm("<spring:message code='group.delete.001' />")){
		$.ajax({
			url:"/man/group/delete.html",
			type:"POST",
			data:{
				"groupSeq":groupSeq
			},
			success:function(result){
				switch(result){
					case 0 : 
						//message : 그룹 삭제에 문제가 있습니다. 관리자에게 문의하시기 바랍니다.
						alert("<spring:message code='group.delete.002' />");
						break;
					case 1 : 
						//message : 그룹 삭제처리가 완료되었습니다.
						alert("<spring:message code='group.delete.003' />");
						window.location.replace("/man/group/list.html?currentPage=${param.currentPage}&searchValue="+$("#searchValue").val());
						break;
					case 2 : 
						//message : 그룹을 사용중인 사용자가 있습니다. 확인후 다시 삭제해주세요.
						alert("<spring:message code='group.delete.004' />");
						break;
				}
			}
		});
	}
}
</script>
<body>

<!-- wrap -->
<div id="wrap" class="sub_wrap">
	
	<!-- header -->
	<%@ include file="../inc/header.jsp" %>
	<!-- header - end -->

	<!-- conteiner -->
	<div id="container">
		<div class="contents list_area">
		
			<!-- man header -->
			<%@ include file="../inc/man_header.jsp" %>
			<!-- man header - end -->

			<!-- section -->
			<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_COMPANY_MEMBER')">
			<h2><spring:message code='group.list.001' /></h2>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ADMIN_SERVICE')">
			<h2><spring:message code='group.list.001.member' /></h2>
			</sec:authorize>
			
			<div class="section fisrt_section">
			
				<div class="con_header clfix">
					<div class="form_area fRight">
						<fieldset>
							<input name="searchValue" id="searchValue" type="text" value="${param.searchValue }">
							<a href="javascript:goToSearch();" class="btn btnM"><spring:message code='group.list.008' /></a>
						</fieldset>
					</div>
				</div>
				
				<!-- table_area -->
				<div class="table_area">
					<table class="coltable">
						<colgroup>
							<col style="width:80px">
							<col style="width:100px">
							<col style="width:90px">
							<sec:authorize access="hasAnyRole('ROLE_ADMIN_SERVICE')">
								<col style="width:300px">
								<col style="width:60px">
							</sec:authorize>
							<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_COMPANY_MEMBER')">
								<c:choose>
									<c:when test="${fn:indexOf(menuFunction, '213') != -1 }">
										<col style="width:300px">
										<col style="width:60px">
									</c:when>
									<c:otherwise>
										<col style="width:360px">
									</c:otherwise>
								</c:choose>
							</sec:authorize>
						</colgroup>
						<caption></caption>
						<tr>
							<th scope="col"><spring:message code='group.list.003' /></th>
							<th scope="col"><spring:message code='group.list.004' /></th>
							<th scope="col"><spring:message code='group.list.012' /></th>
							<sec:authorize access="hasAnyRole('ROLE_ADMIN_SERVICE')">
								<th scope="col"><spring:message code='group.list.005' /></th>
								<th scope="col"><spring:message code='group.list.006' /></th>
							</sec:authorize>
							<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_COMPANY_MEMBER')">
								<c:choose>
									<c:when test="${fn:indexOf(menuFunction, '213') != -1 }">
										<th scope="col"><spring:message code='group.list.005' /></th>
										<th scope="col"><spring:message code='group.list.006' /></th>
									</c:when>
									<c:otherwise>
										<th scope="col"><spring:message code='group.list.005' /></th>
									</c:otherwise>
								</c:choose>
							</sec:authorize>
						</tr>
						
 						<c:choose>
							<c:when test="${empty groupList.list}">
								<tr>
									<!--message : 등록된 그룹이 없습니다.   -->
									<td align="center" colspan="5" ><spring:message code='group.list.002' /></td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="i" begin="0" end="${groupList.list.size()-1}">
									<tr>
										<sec:authorize access="hasAnyRole('ROLE_ADMIN_SERVICE')">
											<td><a href="javascript:goToModify(${groupList.list[i].groupSeq});">${groupList.list[i].groupName}</a></td>
											<td>${groupList.list[i].menuLarge}</td>
											<td>${groupList.list[i].menuMedium}</td>
											<td>${groupList.list[i].menuFunction}</td>
											<td><a href="javascript:customDelete(${groupList.list[i].groupSeq});" id="del" class="btn btnXXS"><spring:message code='group.list.007' /></a></td>
										</sec:authorize>
										<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_COMPANY_MEMBER')">
											<c:choose>
												<c:when test="${fn:indexOf(menuFunction, '213') != -1 }">
													<td><a href="javascript:goToModify(${groupList.list[i].groupSeq});">${groupList.list[i].groupName}</a></td>
													<td>${groupList.list[i].menuLarge}</td>
													<td>${groupList.list[i].menuMedium}</td>
													<td>${groupList.list[i].menuFunction}</td>
													<td><a href="javascript:customDelete(${groupList.list[i].groupSeq});" id="del" class="btn btnXXS"><spring:message code='group.list.007' /></a></td>
												</c:when>
												<c:otherwise>
													<td>${groupList.list[i].groupName}</td>
													<td>${groupList.list[i].menuLarge}</td>
													<td>${groupList.list[i].menuMedium}</td>
													<td>${groupList.list[i].menuFunction}</td>
												</c:otherwise>
											</c:choose>
										</sec:authorize>
									</tr>
 								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
				<!-- //table_area -->
				
				<!-- paging area -->
				<div class="paging">
					<c:if test="${groupList.startPage != 1 }">
						<!--message : 이전 페이지로 이동  -->
						<a href="/man/group/list.html?currentPage=${groupList.startPage-groupList.pageSize}&searchValue=${param.searchValue}" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='group.list.009' />"></a>
					</c:if>
					<c:forEach var="i" begin="${groupList.startPage }" end="${groupList.endPage}">
					    <c:if test="${param.currentPage==i }"><span class="current"><c:out value="${i}"/></span></c:if>
					    <c:if test="${param.currentPage!=i }"> <a href="/man/group/list.html?currentPage=${i}&searchValue=${param.searchValue}"><c:out value="${i}"/></a></c:if>
					</c:forEach>
					<c:if test="${groupList.endPage != 1 && groupList.endPage*groupList.maxResult < groupList.totalCount}">
						<!--message : 다음 페이지로 이동  -->
						<a href="/man/user/list.html?currentPage=${groupList.endPage+1}&searchValue=${param.searchValue}" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='group.list.010' />"></a>
					</c:if>
				</div>
				<!-- paging area - end -->
				
				<!-- btn area -->
				<sec:authorize access="hasAnyRole('ROLE_ADMIN_SERVICE')">
					<div class="btn_area_bottom fRight clfix" style="margin-top:-42px;">
						<a class="btn btnL btn_red" href="/man/group/write.html?currentPage=${param.currentPage}&searchValue=${param.searchValue}"><spring:message code='group.list.011' /></a>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_COMPANY_MEMBER')">
					<c:choose>
						<c:when test="${fn:indexOf(menuFunction, '212') != -1 }">
							<div class="btn_area_bottom fRight clfix" style="margin-top:-42px;">
								<a class="btn btnL btn_red" href="/man/group/write.html?currentPage=${param.currentPage}&searchValue=${param.searchValue}"><spring:message code='group.list.011' /></a>
							</div>
						</c:when>
					</c:choose>
				</sec:authorize>
				<!-- btn area - end -->
				
			</div>
			<!-- section - end -->
		</div>
	</div>
	<!-- conteiner - end -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- footer - end -->

</div>
<!-- wrap - end -->

</body>
</html>
										