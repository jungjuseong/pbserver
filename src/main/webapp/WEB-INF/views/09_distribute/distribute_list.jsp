<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.clbee.pbcms.vo.AppList"%>
<%@page import="com.clbee.pbcms.vo.InAppList"%>
<%@page import="com.clbee.pbcms.vo.ContentList"%>
<%@ include file="../inc/top.jsp" %>
</head>

<!-- 20180417 : lsy - distributeReqList develop first -->

<script>
$(document).ready(function(){
	<!-- 20180420 : lsy - if this case is inapp distribute accept/restore and return, this popup close -->
	if("${objectGb}" == "inapp" && "${ret}" == "y"){
		window.opener.location.reload();
		window.close();
	}
	<!-- 20180420 : lsy - if this case is inapp distribute accept/restore and return, this popup close - end -->
	if("${objectGb}" == "app"){
		$("#totalCountApp").addClass("result");
	}else if("${objectGb}" == "inapp"){
		$("#totalCountInapp").addClass("result");
	}else if("${objectGb}" == "contents"){
		$("#totalCountContents").addClass("result");
	}
	
	//url 접속 시, checkbox 세팅
	if("${myListGb}" == "y"){
		$("#myList").prop("checked", true);
	}else{
		$("#myList").prop("checked", false);
	}
	
	//내 배포요청 내역 check에 따른 링크 처리
	$("#myList").click(function(){
		if($("#myList").prop("checked")){
			window.location.href="/distribute/list.html?page=1&objectGb=${objectGb}&myListGb=y";
		}else{
			window.location.href="/distribute/list.html?page=1&objectGb=${objectGb}&myListGb=n";
		};
	});
});

function goToDistribute(objectSeq, type, storeBundleId){
	if(type == "app"){
		window.location.href="/app/modify.html?currentPage=1&appSeq="+objectSeq+"&myListGb=${myListGb}";
	}else if(type == "inapp"){		
		var winWidth = 750;
		var winHeight = 600;
		var winPosLeft = (screen.width - winWidth)/2;
		var winPosTop = (screen.height - winHeight)/2;
		var url = "/app/inapp/modify.html?inappSeq="+objectSeq+"&storeBundleId=" + storeBundleId+"&myListGb=${myListGb}";
		var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
		
		inappPopup = window.open(url, "inappPopup", opt);
	}if(type == "contents"){
		window.location.href="/contents/modify.html?contentsSeq="+objectSeq+"&myListGb=${myListGb}";
	}
}

</script>
<body>

<!-- wrap -->
<div id="wrap" class="sub_wrap">
	
	<!-- header -->
	<%@ include file="../inc/header.jsp" %>
	<!-- //header -->

	<!-- conteiner -->
	<div id="container">
		<div class="contents list_area">
			
			<h2><spring:message code='distribute.title.002' /></h2>
			
			<!-- section -->
			<div class="section fisrt_section">
				<div class="con_header clfix">
					<div class="fLeft">
						<a href="/distribute/list.html?page=1&objectGb=app&myListGb=${myListGb}">
							<span id="totalCountApp"><spring:message code='distribute.list.006' /><spring:message code='app.inapp.list.text15' /> ${totalCountApp } <spring:message code='app.inapp.list.text16' /></span>
						</a>&nbsp;&nbsp;&nbsp;
						<a href="/distribute/list.html?page=1&objectGb=inapp&myListGb=${myListGb}">
							<span id="totalCountInapp"><spring:message code='distribute.list.007' /><spring:message code='app.inapp.list.text15' /> ${totalCountInapp } <spring:message code='app.inapp.list.text16' /></span>
						</a>&nbsp;&nbsp;&nbsp;
						<a href="/distribute/list.html?page=1&objectGb=contents&myListGb=${myListGb}">
							<span id="totalCountContents"><spring:message code='distribute.list.008' /><spring:message code='app.inapp.list.text15' /> ${totalCountContents } <spring:message code='app.inapp.list.text16' /></span>
						</a>
					</div>
					
					<sec:authorize access="hasAnyRole('ROLE_COMPANY_MEMBER','ROLE_USER')">
						<div class="fRight">
							<div class="checkbox_area">
								<input name="myList" id="myList" class="checkChkVal" type="checkbox">
								<label for="myList"><spring:message code='app.list.table.status6' /></label>
							</div>
						</div>
					</sec:authorize>
				</div>
				
				<!-- table_area -->
				<c:choose>
					<%-- 1. case app --%>
					<c:when test="${objectGb == 'app'}">
						<div class="table_area">
							<table class="coltable">
								<colgroup>
									<col style="width:170px">
									<col style="width:60px">
									<col style="width:70px">
									<col style="width:70px">
									<col style="width:70px">
									<col style="width:70px">
									<col style="width:120px">
									<col style="width:120px">
									<col style="width:100px">
								</colgroup>
								<caption></caption>
								<tr>
									<th scope="col"><spring:message code='distribute.list.app.001' /></th>
									<th scope="col"><spring:message code='distribute.list.app.002' /></th>
									<th scope="col"><spring:message code='distribute.list.app.003' /></th>
									<th scope="col"><spring:message code='distribute.list.app.004' /></th>
									<th scope="col"><spring:message code='distribute.list.app.005' /></th>
									<th scope="col"><spring:message code='distribute.list.app.006' /></th>
									<th scope="col"><spring:message code='distribute.list.app.007' /></th>
									<th scope="col"><spring:message code='distribute.list.app.008' /></th>
									<th scope="col"><spring:message code='distribute.list.005' /></th>
								</tr>
								
		 						<c:choose>
									<c:when test="${empty appList.list}">
										<tr>
											<!--message : 배포 요청 내역이 없습니다.   -->
											<td align="center" colspan="9" ><spring:message code='distribute.list.004' /></td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="i" begin="0" end="${appList.list.size()-1}">
											<tr>
												<td>
													<c:choose>
														<c:when test="${fn:indexOf(menuFunction, '142') != -1 }">
															<a href="javascript:goToDistribute(${appList.list[i].appSeq}, 'app');">${appList.list[i].appName}</a>
														</c:when>
														<c:otherwise>
															${appList.list[i].appName}
														</c:otherwise>
													</c:choose>
												</td>
												<td>${appList.list[i].verNum}</td>
												<td>
													<c:choose>
														<c:when test="${appList.list[i].ostype eq '1' }">Universal</c:when>
														<c:when test="${appList.list[i].ostype eq '2' }">iPhone</c:when>
														<c:when test="${appList.list[i].ostype eq '3' }">iPad</c:when>
														<c:when test="${appList.list[i].ostype eq '4' }">Android</c:when>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${'1' eq appList.list[i].regGb }"><spring:message code='app.list.text4' /></c:when>
														<c:when test="${'2' eq appList.list[i].regGb }"><spring:message code='app.list.text5' /></c:when>
													</c:choose>
												</td>
												<td>${appList.list[i].appContentsAmt}</td>
												<td>${appList.list[i].appSize}</td>
												<td><fmt:formatDate value="${appList.list[i].regDt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
												<td><fmt:formatDate value="${appList.list[i].distributeReqDt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
												<td>${appList.list[i].distributeRequestId}</td>
											</tr>
		 								</c:forEach>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
				
						<div class="paging">
							<!--message : 이전 페이지로 이동  -->
							<c:if test="${appList.startPage != 1 }">
								<a href="/distribute/list.html?page=${appList.startPage-appList.pageSize}&objectGb=${objectGb}&myListGb=${myListGb}" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='user.list.036' />"></a>
							</c:if>
		
							<c:forEach var="i" begin="${appList.startPage }" end="${appList.endPage}">
							    <c:if test="${param.page==i }"><span class="current"><c:out value="${i}"/></span></c:if>
							    <c:if test="${param.page!=i }"> <a href="/distribute/list.html?page=${i}&objectGb=${objectGb}&myListGb=${myListGb}"><c:out value="${i}"/></a></c:if>
							</c:forEach>
							
							<!--message : 다음 페이지로 이동  -->
							<c:if test="${appList.endPage != 1 && appList.endPage*appList.maxResult < appList.totalCount}">
								<a href="/distribute/list.html?page=${appList.endPage+1}&objectGb=${objectGb}&myListGb=${myListGb}" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='user.list.037' />"></a>
							</c:if>
						</div>
						<!--페이징-->
						
					</c:when>
					
					<%-- 2. case inapp --%>
					<c:when test="${objectGb == 'inapp'}">
						<div class="table_area">
							<table class="coltable">
								<colgroup>
									<col style="width:200px">
									<col style="width:60px">
									<col style="width:130px">
									<col style="width:90px">
									<col style="width:120px">
									<col style="width:120px">
									<col style="width:100px">
								</colgroup>
								<caption></caption>
								<tr>
									<th scope="col"><spring:message code='distribute.list.inapp.001' /></th>
									<th scope="col"><spring:message code='distribute.list.inapp.002' /></th>
									<th scope="col"><spring:message code='distribute.list.inapp.003' /></th>
									<th scope="col"><spring:message code='distribute.list.inapp.004' /></th>
									<th scope="col"><spring:message code='distribute.list.inapp.005' /></th>
									<th scope="col"><spring:message code='distribute.list.inapp.006' /></th>
									<th scope="col"><spring:message code='distribute.list.005' /></th>
								</tr>
								
		 						<c:choose>
									<c:when test="${empty inappList.list}">
										<tr>
											<!--message : 배포 요청 내역이 없습니다.   -->
											<td align="center" colspan="7" ><spring:message code='distribute.list.004' /></td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="i" begin="0" end="${inappList.list.size()-1}">
											<tr>
												<td>
													<c:choose>
														<c:when test="${fn:indexOf(menuFunction, '142') != -1 }">
															<a href="javascript:goToDistribute(${inappList.list[i].inappSeq}, 'inapp', '${inappList.list[i].storeBundleId}');">${inappList.list[i].inappName}</a>
														</c:when>
														<c:otherwise>
															${inappList.list[i].inappName}
														</c:otherwise>
													</c:choose>
												</td>
												<td>${inappList.list[i].verNum}</td>
												<td>${inappList.list[i].categoryName }</td>
												<td>${inappList.list[i].inappSize}</td>
												<td><fmt:formatDate value="${inappList.list[i].regDt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
												<td><fmt:formatDate value="${inappList.list[i].distributeReqDt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
												<td>${inappList.list[i].distributeRequestId}</td>
											</tr>
		 								</c:forEach>
									</c:otherwise>
								</c:choose>
							</table>
						</div>					
				
						<div class="paging">
							<!--message : 이전 페이지로 이동  -->
							<c:if test="${inappList.startPage != 1 }">
								<a href="/distribute/list.html?page=${inappList.startPage-inappList.pageSize}&objectGb=${objectGb}&myListGb=${myListGb}" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='user.list.036' />"></a>
							</c:if>
		
							<c:forEach var="i" begin="${inappList.startPage }" end="${inappList.endPage}">
							    <c:if test="${param.page==i }"><span class="current"><c:out value="${i}"/></span></c:if>
							    <c:if test="${param.page!=i }"> <a href="/distribute/list.html?page=${i}&objectGb=${objectGb}&myListGb=${myListGb}"><c:out value="${i}"/></a></c:if>
							</c:forEach>
							
							<!--message : 다음 페이지로 이동  -->
							<c:if test="${inappList.endPage != 1 && inappList.endPage*inappList.maxResult < inappList.totalCount}">
								<a href="/distribute/list.html?page=${inappList.endPage+1}&objectGb=${objectGb}&myListGb=${myListGb}" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='user.list.037' />"></a>
							</c:if>
						</div>
						<!--페이징-->
						
					</c:when>
					
					<%-- 3. case contents --%>
					<c:when test="${objectGb == 'contents'}">
						<div class="table_area">
							<table class="coltable">
								<colgroup>
									<col style="width:200px">
									<col style="width:60px">
									<col style="width:90px">
									<col style="width:90px">
									<col style="width:90px">
									<col style="width:120px">
									<col style="width:120px">
									<col style="width:100px">
								</colgroup>
								<caption></caption>
								<tr>
									<th scope="col"><spring:message code='distribute.list.contents.001' /></th>
									<th scope="col"><spring:message code='distribute.list.contents.002' /></th>
									<th scope="col"><spring:message code='distribute.list.contents.003' /></th>
									<th scope="col"><spring:message code='distribute.list.contents.004' /></th>
									<th scope="col"><spring:message code='distribute.list.contents.005' /></th>
									<th scope="col"><spring:message code='distribute.list.contents.006' /></th>
									<th scope="col"><spring:message code='distribute.list.contents.007' /></th>
									<th scope="col"><spring:message code='distribute.list.005' /></th>
								</tr>
								
		 						<c:choose>
									<c:when test="${empty contentsList.list}">
										<tr>
											<!--message : 배포 요청 내역이 없습니다.   -->
											<td align="center" colspan="8" ><spring:message code='distribute.list.004' /></td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="i" begin="0" end="${contentsList.list.size()-1}">
											<tr>
												<td>
													<c:choose>
														<c:when test="${fn:indexOf(menuFunction, '142') != -1 }">
															<a href="javascript:goToDistribute(${contentsList.list[i].contentsSeq}, 'contents');">${contentsList.list[i].contentsName}</a>
														</c:when>
														<c:otherwise>
															${contentsList.list[i].contentsName}
														</c:otherwise>
													</c:choose>
												</td>
												<td>${contentsList.list[i].verNum}</td>
												<td>
													<c:choose>
														<c:when test="${contentsList.list[i].contentsType == '1' }">HTML5</c:when>
														<c:when test="${contentsList.list[i].contentsType == '2' }">ePub3</c:when>
														<c:when test="${contentsList.list[i].contentsType == '3' }">PDF</c:when>
													</c:choose>
												</td>
												<td>${contentsList.list[i].appName}</td>
												<td>${contentsList.list[i].contentsSize}</td>
												<td><fmt:formatDate value="${contentsList.list[i].regDt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
												<td><fmt:formatDate value="${contentsList.list[i].distributeReqDt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
												<td>${contentsList.list[i].distributeRequestId}</td>
											</tr>
		 								</c:forEach>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
				
						<div class="paging">
							<!--message : 이전 페이지로 이동  -->
							<c:if test="${contentsList.startPage != 1 }">
								<a href="/distribute/list.html?page=${contentsList.startPage-contentsList.pageSize}&objectGb=${objectGb}&myListGb=${myListGb}" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='user.list.036' />"></a>
							</c:if>
		
							<c:forEach var="i" begin="${contentsList.startPage }" end="${contentsList.endPage}">
							    <c:if test="${param.page==i }"><span class="current"><c:out value="${i}"/></span></c:if>
							    <c:if test="${param.page!=i }"> <a href="/distribute/list.html?page=${i}&objectGb=${objectGb}&myListGb=${myListGb}"><c:out value="${i}"/></a></c:if>
							</c:forEach>
							
							<!--message : 다음 페이지로 이동  -->
							<c:if test="${contentsList.endPage != 1 && contentsList.endPage*contentsList.maxResult < contentsList.totalCount}">
								<a href="/distribute/list.html?page=${contentsList.endPage+1}&objectGb=${objectGb}&myListGb=${myListGb}" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='user.list.037' />"></a>
							</c:if>
						</div>
						<!--페이징-->
						
					</c:when>
				</c:choose>
				<!-- //table_area -->
				
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