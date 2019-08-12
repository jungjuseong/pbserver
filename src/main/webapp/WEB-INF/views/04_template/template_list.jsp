<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>
</head>
<script type="text/javascript">
$(document).ready(function(){
	if(typeof(history.pushState) == 'function'){
		var urlNow = location.href.split("?");
		history.pushState(null, null, urlNow[0]);
	}
	
	$('.btnTemplateWrite').click(function(){
		//location.href = "";
		var urlWrite = "/template/write.html?status="+$("#status").val();
		location.href = urlWrite;
		//$('#frmSearch').attr('action', urlSearch).submit();
	});
	
	$('#btnSh').click(function(){
		var urlSearch = "/template/list.html";
		$('#frmSearch').attr('action', urlSearch).submit();
	});
	
	$('.modifyGo').click(function(){
		var thisSeq = $(this).attr('alt');
		var urlSearch = "/template/modify.html";		
		$('#thisSeq').val(thisSeq);		
		$('#frmSearch').attr('action', urlSearch).submit();
	});	
	
	$("#sh_keyword").keypress(function(e){
		if(e.keyCode=='13')goToSearch();
	});
	
	if("${status}" == "n"){
		$("input:checkbox[id='all']").prop("checked", true);
	}

	$("#all").click(function(){
		if($("input:checkbox[id='all']").is(":checked")){
			$("#status").val("n");
		}else{
			$("#status").val("y");
		}

		document.frmSearch.action='/template/list.html';
		document.frmSearch.submit();
	});
	
});

function goToSearch(_page){
	//var page = _page?_page:$('#page').val();
	$('#currentPage').val('1');
	if(_page){
		$('#currentPage').val(_page);
	}

	document.frmSearch.action='/template/list.html';
	document.frmSearch.submit();
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
			<h2><spring:message code='template.list.001' /></h2>
			
			<!-- section -->
			<div class="section fisrt_section">
				<div class="con_header">
					<div class="result fLeft">
						<spring:message code='template.list.002_1' /> ${templateList.totalCount}<spring:message code='template.list.002_2' />
					</div>
					<div class="form_area fRight">
						<form method="post" name="frmSearch"  id="frmSearch" >
							<input type="hidden" name="currentPage" id="currentPage" value="${templateList.currentPage }">
							<input type="hidden" id="thisSeq" name="thisSeq" value="" />
							<input type="hidden" name="status" id="status" value="${status }">
							<fieldset>
								<div class="checkbox_area">
									<input name="checkChkVal_0" id="all" class="checkChkVal" type="checkbox" value="0">
									<label for="all">All</label>
								</div>
								<select name="sh_field" id="sh_field">
									<option value=""<c:if test="${shField == ''}">selected="selected"</c:if>><spring:message code='template.list.013' /></option>
									<option value="templateName"<c:if test="${shField == 'templateName'}">selected="selected"</c:if>><spring:message code='template.list.014' /></option>
								</select>
								<input name="sh_keyword" type="text" value="${shKeyword}">
								<a href="#1" class="btn btnM" id="btnSh"><spring:message code='template.list.012' /></a>
							</fieldset>
						</form>
					</div>
				</div>
				<!-- table_area -->
				<div class="table_area">
					<table class="coltable">
						<colgroup>
							<col style="width:">
							<col style="width:">
							<col style="width:80px">
							<col style="width:70px">
							<col style="width:110px">
							<col style="width:100px">
							<col style="width:90px">
							<col style="width:90px">
						</colgroup>
						<caption></caption>
						<tr>
							<th scope="col"><spring:message code='template.list.014' /></th>
							<th scope="col">OS</th>
							<th scope="col"><spring:message code='template.list.005' /></th>
							<th scope="col"><spring:message code='template.list.006' /></th>
							<th scope="col"><spring:message code='template.list.007' /></th>
							<th scope="col"><spring:message code='template.list.008' /></th>
							<th scope="col"><spring:message code='template.list.009' /></th>
							<th scope="col"><spring:message code='template.list.023' /></th>
						</tr>
						<c:choose>	
							<c:when test="${empty templateList.list}">		
								<tr>
									<td align="center" colspan="8" ><spring:message code="list.nodata.001" /></td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="result" items="${templateList.list}" varStatus="status">
									<tr>						
										<td><a href="#" alt="${result.templateSeq}" class="modifyGo" >${result.templateName}</a></td>
										<td><c:if test="${result.ostypeGb == 1}">Universal</c:if><c:if test="${result.ostypeGb == 2}">iPhone</c:if><c:if test="${result.ostypeGb == 3}">iPad</c:if><c:if test="${result.ostypeGb == 4}">Android</c:if></td>
										<td>${result.verNum}</td>
										<td><c:if test="${result.templateTypeGb == 1}"><spring:message code='template.modify.009' /></c:if><c:if test="${result.templateTypeGb == 2}"><spring:message code='template.modify.010' /></c:if></td>
										<td>
											<c:if test="${result.appContentsGb == 0}"> ${result.appContentsAmt}<spring:message code='template.list.015' /></c:if>
											<c:if test="${result.appContentsGb == 1}"> <spring:message code='template.modify.013' />(${result.appContentsAmt}<spring:message code='template.list.015' />)</c:if>
											<c:if test="${result.appContentsGb == 2}"> <spring:message code='template.modify.014' />(${result.appContentsAmt}<spring:message code='template.list.015' />)</c:if>
											<c:if test="${result.appContentsGb == 3}"> <spring:message code='template.modify.015' /></c:if>
										</td>
										<td class="state">
											<c:choose>
												<c:when test="${result.limitGb == 1}">
													<img src="/images/icon_circle_red.png" alt=""> <spring:message code='template.list.016' />
												</c:when>
												<c:when test="${result.useGb == 1 && result.limitGb == 2}">
													<img src="/images/icon_circle_green.png" alt=""> <spring:message code='template.list.018' />
												</c:when>
												<c:when test="${result.useGb == 2 && result.limitGb == 2}">
													<img src="/images/icon_circle_red.png" alt=""> <spring:message code='template.list.019' />
												</c:when>														
											</c:choose>
										</td>
										<td><fmt:formatDate value="${result.regDt}" pattern="yyyy-MM-dd" type="date" /></td>
										<td><fmt:formatDate value="${result.chgDt}" pattern="yyyy-MM-dd" type="date" /></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>
				</div><!-- //table_area -->
				
				<!--페이징-->
				<div class="paging">
					<c:if test="${templateList.startPage != 1 }">
					<!--message : 이전 페이지로 이동  -->
					<a href="javascript:goToSearch('{templateList.startPage-templateList.pageSize}');" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='template.list.021' />"></a>
					</c:if>
					<c:forEach var="i" begin="${templateList.startPage }" end="${templateList.endPage}">
						    <c:if test="${templateList.currentPage==i }"><span class="current"><c:out value="${i}"/></span></c:if>
						    <c:if test="${templateList.currentPage!=i }"> <a href="javascript:goToSearch('${i}');"><c:out value="${i}"/></a></c:if>
					</c:forEach>
					<c:if test="${templateList.endPage != 1 && templateList.endPage*templateList.maxResult < templateList.totalCount}">
					<!--message : 다음 페이지로 이동  -->
					<a href="javascript:goToSearch('${templateList.endPage+1}');" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='template.list.022' />"></a>
					</c:if>
				</div>				
				<!--//페이징-->				
				

				<!-- btn area -->
				<div class="btn_area_bottom fRight clfix" style="margin-top:-42px;">
					<a class="btn btnL btn_red btnTemplateWrite" href="#"><spring:message code='template.list.020' /></a>
				</div>
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
