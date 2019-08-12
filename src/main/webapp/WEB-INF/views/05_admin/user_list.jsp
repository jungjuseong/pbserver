<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.clbee.pbcms.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@page import="com.clbee.pbcms.vo.MemberList"%>
<%@ include file="../inc/top.jsp" %>
</head>

<script>
$(document).ready(function(){
	$("#searchValue").keypress(function(event){
		 if (event.keyCode == 13) {
		 	event.preventDefault();
		 	goToSearch();
		 }
	});
	
	if('${param.searchType}' == 'onedepartmentName'){
		$("#onedepartment").attr("selected", "selected");
	}else if('${param.searchType}' == 'userId'){
		$("#userId").attr("selected", "selected");
	}else if('${param.searchType}' == 'userName'){
		$("#userName").attr("selected", "selected");
	}else if('${param.searchType}' == 'twodepartmentName'){
		$("#twodepartment").attr("selected", "selected");
	}else if('${param.searchType}' == 'userGroup'){
		$("#userGroup").attr("selected", "selected");
	}
	
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
		
		var searchType = $('#comboBox :selected').attr('id');
		var searchValue = $('#searchValue').val();
		var isAvailable = $("#isAvailable").val();

		window.location.href="/man/user/list.html?page=1&searchType="+searchType+"&searchValue="+searchValue+"&isAvailable="+isAvailable;
	});
	
});
//계정 탈퇴
function customDelete(userSeq, currentPage){
	var searchType = $('#comboBox :selected').text();
	var searchValue = $('#searchValue').val();
	var isAvailable = $('#isAvailable').val();
	
	if(searchType == "<spring:message code='user.list.003' />"){
		searchType = 'userId';
	}else if(searchType == "<spring:message code='user.list.004' />"){
		searchType = 'userName';
	}else if(searchType == "<spring:message code='user.list.045' />1"){
		searchType ='onedepartmentName';
	}else if(searchType == "<spring:message code='user.list.045' />2"){
		searchType ='twodepartmentName';
	}else if(searchType == "<spring:message code='user.list.044' />"){
		searchType ='userGroup';
	}
	
	//message : 정말 탈퇴하시겠습니까?
	if(confirm("<spring:message code='user.list.041' />")){
		$.ajax({
			url:"/man/user/delete.html",
			type:"POST",
			data:{
				"userSeq":userSeq
			},
			success:function(result){
				switch(result){
					case 0 : 
						//message : [DB에러] 탈퇴 실패
						alert("<spring:message code='user.list.042' />");
						break;
					case 1 : //page=1&searchType=&searchValue=&isAvailable=false
						window.location.replace("/man/user/list.html?page="+currentPage+"&searchType="+searchType+"&searchValue="+searchValue+"&isAvailable="+isAvailable);
						break;
				}
			}
		});
	}
}
//계정 복원
function customRecover(userSeq, currentPage){
	var searchType = $('#comboBox :selected').text();
	var searchValue = $('#searchValue').val();
	var isAvailable = $('#isAvailable').val();
	
	if(searchType == "<spring:message code='user.list.003' />"){
		searchType = 'userId';
	}else if(searchType == "<spring:message code='user.list.004' />"){
		searchType = 'userName';
	}else if(searchType == "<spring:message code='user.list.045' />1"){
		searchType ='onedepartmentName';
	}else if(searchType == "<spring:message code='user.list.045' />2"){
		searchType ='twodepartmentName';
	}else if(searchType == "<spring:message code='user.list.044' />"){
		searchType ='userGroup';
	}
	
	//message : 정말 복원하시겠습니까?
	if(confirm("<spring:message code='user.list.039' />")){
		$.ajax({
			url:"/man/user/recover.html",
			type:"POST",
			data:{
				"userSeq":userSeq
			},
			success:function(result){
				switch(result){
					case 0 : 
						//message : [DB에러] 복원 실패
						alert("<spring:message code='user.list.040' />");
						break;
					case 1 : 
						window.location.replace("/man/user/list.html?page="+currentPage+"&searchType="+searchType+"&searchValue="+searchValue+"&isAvailable="+isAvailable);
						break;
				}
			}
		});
	}
}
//계정 완전 삭제
function customEliminate(userSeq, currentPage){
	var searchType = $('#comboBox :selected').text();
	var searchValue = $('#searchValue').val();
	var isAvailable = $('#isAvailable').val();
	
	if(searchType == "<spring:message code='user.list.003' />"){
		searchType = 'userId';
	}else if(searchType == "<spring:message code='user.list.004' />"){
		searchType = 'userName';
	}else if(searchType == "<spring:message code='user.list.045' />1"){
		searchType ='onedepartmentName';
	}else if(searchType == "<spring:message code='user.list.045' />2"){
		searchType ='twodepartmentName';
	}else if(searchType == "<spring:message code='user.list.044' />"){
		searchType ='userGroup';
	}
	
	//message : 정말 삭제하시겠습니까?
	if(confirm("<spring:message code='user.list.027' />")){
		$.ajax({
			url:"/man/user/eliminate.html",
			type:"POST",
			data:{
				"userSeq":userSeq
			},
			success:function(result){
				switch(result){
					case 0 : 
						//message : [DB에러] 삭제 실패
						alert("<spring:message code='user.list.035' />");
						break;
					case 1 : 
						window.location.replace("/man/user/list.html?page=1&searchType="+searchType+"&searchValue="+searchValue+"&isAvailable="+isAvailable);
						break;
				}
			}
		});
	}
}

function goToModify(userSeq){
	var searchType = $('#comboBox :selected').text();
	var searchValue = $('#searchValue').val();
	var isAvailable = $('#isAvailable').val();
	
	if(searchType == "<spring:message code='user.list.003' />"){
		searchType = 'userId';
	}else if(searchType == "<spring:message code='user.list.004' />"){
		searchType = 'userName';
	}else if(searchType == "<spring:message code='user.list.045' />1"){
		searchType ='onedepartmentName';
	}else if(searchType == "<spring:message code='user.list.045' />2"){
		searchType ='twodepartmentName';
	}else if(searchType == "<spring:message code='user.list.044' />"){
		searchType ='userGroup';
	}

	window.location.href="/man/user/modify.html?page=<c:out value='${param.page}'/>&searchType="+searchType+"&searchValue="+searchValue+"&isAvailable="+isAvailable+"&userSeq="+userSeq;
}

function goToSearch(){
	var searchType = $('#comboBox :selected').text();
	var searchValue = $('#searchValue').val();
	var isAvailable = $('#isAvailable').val();
	
	if(searchType == "<spring:message code='user.list.003' />"){
		searchType = 'userId';
	}else if(searchType == "<spring:message code='user.list.004' />"){
		searchType = 'userName';
	}else if(searchType == "<spring:message code='user.list.045' />1"){
		searchType ='onedepartmentName';
	}else if(searchType == "<spring:message code='user.list.045' />2"){
		searchType ='twodepartmentName';
	}else if(searchType == "<spring:message code='user.list.044' />"){
		searchType ='userGroup';
	}

	window.location.href="/man/user/list.html?page=1&searchType="+searchType+"&searchValue="+searchValue+"&isAvailable="+isAvailable;
}

</script>
<body>

<input type="hidden" name="isAvailable" id="isAvailable" value="false"/>

<!-- wrap -->
<div id="wrap" class="sub_wrap">
	
	<!-- header -->
	<%@ include file="../inc/header.jsp" %>
	<!-- //header -->

	<!-- conteiner -->
	<div id="container">
		<div class="contents list_area">
		
			<!-- man header -->
			<%@ include file="../inc/man_header.jsp" %>
			<!-- //man header -->
			<sec:authorize access="hasAnyRole('ROLE_INDIVIDUAL_MEMBER','ROLE_COMPANY_MEMBER','ROLE_USER')">
				<h2><spring:message code='user.list.001' /></h2>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
				<h2><spring:message code='user.list.022' /></h2>
			</sec:authorize>
			
			<!-- section -->
			<div class="section fisrt_section">
				<div class="con_header clfix">
					<div class="result fLeft">
						<spring:message code='app.inapp.list.text15' /> ${memberList.totalCount}<spring:message code='template.write.028_2' />
					</div>
					<div class="form_area fRight">
						<fieldset>
							<div class="checkbox_area_userlist">
								<span class="userlist_tooltip"><spring:message code='user.list.043' /></span>
								<input name="checkChkVal_0" id="all" class="checkChkVal" type="checkbox" value="0">
								<label for="all">All</label>
							</div>
							<select id="comboBox" name="">
								<option id="userId"><spring:message code='user.list.003' /></option>
								<option id="userName"><spring:message code='user.list.004' /></option>
								<sec:authorize access="hasAnyRole('ROLE_INDIVIDUAL_MEMBER','ROLE_COMPANY_MEMBER','ROLE_USER')">
									<option id ="onedepartment"><spring:message code='user.list.045' />1</option>
									<option id ="twodepartment"><spring:message code='user.list.045' />2</option>
								</sec:authorize>
								<!-- 20180511 : lsy -  -->
								<option id="userGroup"><spring:message code='user.list.044' /></option>
							</select>
							
							<input name="" id="searchValue" type="text" value="${param.searchValue }">
							<a href="javascript:goToSearch();" class="btn btnM"><spring:message code='user.list.026' /></a>
						</fieldset>
					</div>
				</div>
				<!-- table_area -->
				<div class="table_area">
					<table class="coltable">
						<colgroup>
							<col style="width:100px">
							<col style="width:75px">
							<col style="width:80px">
<%-- 							
							<sec:authorize access="hasAnyRole('ROLE_INDIVIDUAL_MEMBER','ROLE_COMPANY_MEMBER','ROLE_USER')">
								<col style="width:60px">
								<col style="width:60px">
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
								<col style="width:100px">
							</sec:authorize>
 --%>
							<col style="width:80px">
							<col style="width:80px">
							<col style="width:85px">
							<col style="width:55px">
							
							<sec:authorize access="hasAnyRole('ROLE_INDIVIDUAL_MEMBER','ROLE_COMPANY_MEMBER','ROLE_USER')">
								<c:choose>
									<c:when test="${fn:indexOf(menuFunction, '203') != -1 }">
										<col style="width:70px">
									</c:when>
								</c:choose>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
								<col style="width:70px">
							</sec:authorize>
						</colgroup>
						<caption></caption>
						<tr>
							<th scope="col"><spring:message code='user.list.003' /></th>
							<th scope="col"><spring:message code='user.list.004' /></th>
							
							<sec:authorize access="hasAnyRole('ROLE_INDIVIDUAL_MEMBER','ROLE_COMPANY_MEMBER','ROLE_USER')">
								<th scope="col"><spring:message code='user.list.044' /></th>
								<th scope="col"><spring:message code='user.list.045' />1</th>
								<th scope="col"><spring:message code='user.list.045' />2</th>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
								<!-- temp -->
								<th scope="col"><spring:message code='user.list.005' /></th>
								<th scope="col"><spring:message code='user.list.044' /></th>
								<th scope="col"><spring:message code='user.list.008' /></th>
							</sec:authorize>
							
							<th scope="col"><spring:message code='user.list.009' /></th>
							<th scope="col"><spring:message code='user.list.010' /></th>
							
							<sec:authorize access="hasAnyRole('ROLE_INDIVIDUAL_MEMBER','ROLE_COMPANY_MEMBER','ROLE_USER')">
								<c:choose>
									<c:when test="${fn:indexOf(menuFunction, '203') != -1 }">
										<th scope="col"><spring:message code='user.list.011' /></th>
									</c:when>
								</c:choose>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
								<th scope="col"><spring:message code='user.list.011' /></th>
							</sec:authorize>
						</tr>
						
 						<c:choose>
							<c:when test="${empty memberList.list}">
								<tr>
									<!--message : 등록된 사용자가 없습니다.   -->
									<td align="center" colspan="9" ><spring:message code='user.list.028' /><%-- <spring:message code='app.no.contents' /> --%></td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="i" begin="0" end="${memberList.list.size()-1}">
									<tr>
										<sec:authorize access="hasAnyRole('ROLE_INDIVIDUAL_MEMBER','ROLE_COMPANY_MEMBER','ROLE_USER')">
											<td>
												<c:choose>
													<c:when test="${fn:indexOf(menuFunction, '203') != -1 }">
														<a href="javascript:goToModify(${memberList.list[i].userSeq});">${memberList.list[i].userId}</a>
													</c:when>
													<c:otherwise>
														${memberList.list[i].userId}
													</c:otherwise>
												</c:choose>
											</td>
											<td>${memberList.list[i].lastName}${memberList.list[i].firstName}</td>										
											<td>${memberList.list[i].userGroup }
<%-- 											
												<c:choose>
													<c:when test="${memberList.list[i].userGb == '1' }"><spring:message code='user.list.017' /></c:when>
													<c:when test="${memberList.list[i].userGb == '5' }"><spring:message code='user.list.016' /></c:when>
													<c:when test="${memberList.list[i].userGb == '21' }"><spring:message code='user.list.015' /></c:when>
													<c:when test="${memberList.list[i].userGb == '29' }"><spring:message code='extend.local.006' /></c:when>
												</c:choose>
 --%>
											</td>
											<td>${memberList.list[i].onedepartmentName }</td>
											<td>${memberList.list[i].twodepartmentName }</td>
										</sec:authorize>
										
										<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
											<td>
												<a href="javascript:goToModify(${memberList.list[i].userSeq});">${memberList.list[i].userId}</a>
											</td>
											<td>${memberList.list[i].lastName}${memberList.list[i].firstName}</td>
											<td>
												<c:choose>
													<c:when test="${memberList.list[i].companyGb == '1' }"><spring:message code='user.list.021' /></c:when>
													<c:when test="${memberList.list[i].companyGb == '2' }"><spring:message code='user.list.023' /></c:when>
												</c:choose>
											</td>
											<!-- temp -->
											<td>${memberList.list[i].userGroup}</td>
											<td>
												<c:choose>
													<c:when test="${memberList.list[i].companyGb == '1' }">${memberList.list[i].companyVO.companyName }</c:when>
													<c:when test="${memberList.list[i].companyGb == '2' }"><spring:message code='user.list.029' /></c:when>
												</c:choose>
											</td>
										</sec:authorize>
																				
										<td>${formattedDate[i]}</td>
										<td class="state">
										<!--
											user.list.030=탈퇴
											user.list.031=정지
											user.list.032=강제탈퇴
											user.list.033=승인
											user.list.034=대기 
										 -->										
 											<c:choose>
												<c:when test="${memberList.list[i].userStatus == '1' }">
													<img src="/images/icon_circle_red.png" alt=""> <spring:message code='user.list.030' /></c:when>
												<c:when test="${memberList.list[i].userStatus == '2' }">
													<img src="/images/icon_circle_red.png" alt=""> <spring:message code='user.list.031' /></c:when>
												<c:when test="${memberList.list[i].userStatus == '3' }">
													<img src="/images/icon_circle_red.png" alt=""> <spring:message code='user.list.032' /></c:when>
												<c:when test="${memberList.list[i].userStatus == '4' }">
													<img src="/images/icon_circle_green.png" alt=""> <spring:message code='user.list.033' /></c:when>
												<c:when test="${memberList.list[i].userStatus == '5' }">
													<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='user.list.034' /></c:when>
											</c:choose>
										</td>
										
										<sec:authorize access="hasAnyRole('ROLE_INDIVIDUAL_MEMBER','ROLE_COMPANY_MEMBER','ROLE_USER')">
											<c:choose>
												<c:when test="${fn:indexOf(menuFunction, '203') != -1 }">
													<td>
			 											<c:choose>
															<c:when test="${memberList.list[i].userStatus == '1' or memberList.list[i].userStatus == '2' or memberList.list[i].userStatus == '3' }">
																<a href="#recover" id="recover" onclick="customRecover('${memberList.list[i].userSeq}','${param.page}')" class="btn btnXXS"><spring:message code='user.list.038' /></a>
																<a href="#eliminate" id="#eliminate" onclick="customEliminate('${memberList.list[i].userSeq}','${param.page}')" class="btn btnXXS"><spring:message code='user.list.011' /></a>
															</c:when>
															<c:when test="${memberList.list[i].userStatus == '4' or memberList.list[i].userStatus == '5' }">
																<a href="#del" id="del" onclick="customDelete('${memberList.list[i].userSeq}','${param.page}')" class="btn btnXXS"><spring:message code='user.list.030' /></a>
															</c:when>
														</c:choose>
													</td>
												</c:when>											
											</c:choose>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
											<td>
	 											<c:choose>
													<c:when test="${memberList.list[i].userStatus == '1' or memberList.list[i].userStatus == '2' or memberList.list[i].userStatus == '3' }">
														<a href="#recover" id="recover" onclick="customRecover('${memberList.list[i].userSeq}','${param.page}')" class="btn btnXXS"><spring:message code='user.list.038' /></a>
														<a href="#eliminate" id="#eliminate" onclick="customEliminate('${memberList.list[i].userSeq}','${param.page}')" class="btn btnXXS"><spring:message code='user.list.011' /></a>
													</c:when>
													<c:when test="${memberList.list[i].userStatus == '4' or memberList.list[i].userStatus == '5' }">
														<a href="#del" id="del" onclick="customDelete('${memberList.list[i].userSeq}','${param.page}')" class="btn btnXXS"><spring:message code='user.list.030' /></a>
													</c:when>
												</c:choose>
											</td>
										</sec:authorize>
									</tr>
 								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>
				</div><!-- //table_area -->
				
				<div class="paging">
					<c:if test="${memberList.startPage != 1 }">
					<!--message : 이전 페이지로 이동  -->
						<a href="/man/user/list.html?page=${memberList.startPage-memberList.pageSize}&searchType=${param.searchType}&searchValue=${param.searchValue}&isAvailable=${param.isAvailable}" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='user.list.036' />"></a>
					</c:if>
					<c:forEach var="i" begin="${memberList.startPage }" end="${memberList.endPage}">
					    <c:if test="${param.page==i }"><span class="current"><c:out value="${i}"/></span></c:if>
					    <c:if test="${param.page!=i }"> <a href="/man/user/list.html?page=${i}&searchType=${param.searchType}&searchValue=${param.searchValue}&isAvailable=${param.isAvailable}"><c:out value="${i}"/></a></c:if>
<%-- 					    <c:out value="${i}"/>
					    <c:if test="${param.page==i }"></strong></c:if> --%>
					</c:forEach>
					<c:if test="${memberList.endPage != 1 && memberList.endPage*memberList.maxResult < memberList.totalCount}">
						<!--message : 다음 페이지로 이동  -->
						<a href="/man/user/list.html?page=${memberList.endPage+1}&searchType=${param.searchType}&searchValue=${param.searchValue}&isAvailable=${param.isAvailable}" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='user.list.037' />"></a>
					</c:if>
				</div>				
				<!--페이징-->
				
				<!-- btn area -->
				<sec:authorize access="hasAnyRole('ROLE_INDIVIDUAL_MEMBER','ROLE_COMPANY_MEMBER','ROLE_USER')">
					<c:choose>
						<c:when test="${fn:indexOf(menuFunction, '202') != -1 }">
							<div class="btn_area_bottom fRight clfix" style="margin-top:-42px;">
								<%-- <a class="btn btnL btn_gray_light" href="#"><spring:message code='user.list.024' /></a> --%>
								<a class="btn btnL btn_red" href="write.html?page=${param.page}&searchType=${param.searchType}&searchValue=${param.searchValue}&isAvailable=${param.isAvailable}"><spring:message code='user.list.025' /></a>
							</div>
						</c:when>
					</c:choose>
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
										