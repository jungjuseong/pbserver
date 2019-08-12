<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../inc/top.jsp" %>

<script>
$(document).ready(function(){
	
	
	if("${param.dateIsCheck}" == "1"){
		$('#dateCheckGb').prop('checked', true);
		$("[name=userStartDt]").attr("readonly",false);
		$("[name=userEndDt]").attr("readonly",false);
		$("[name=userStartDt]").attr("disabled",false);
		$("[name=userEndDt]").attr("disabled",false);
	}else if("${param.dateIsCheck}" == "2"){
		$('#dateCheckGb').prop('checked', false);
		$("[name=userStartDt]").attr("readonly",true);
		$("[name=userEndDt]").attr("readonly",true);
		$("[name=userStartDt]").attr("disabled",true);
		$("[name=userEndDt]").attr("disabled",true);
	}
	
	
	$("#tempSearch").keypress(function(event){
		 if (event.keyCode == 13) {
		 	event.preventDefault();
		 	goToSearch();
		 }
	});
	
	$("[name=comboBox]").change(function(){
		$("#searchType").val($(this).val());
	});
	
	
	$("#dateCheckGb").change(function(){
		if($(this).is(':checked')){
			$("#dateIsCheck").val("1");
			$('#dateCheckGb').prop('checked', true);
			$("#dateGb").val('1');
			$("[name=userEndDt]").val($("[name=tempEndDt]").val());
			$("[name=userStartDt]").val($("[name=tempStartDt]").val());
			$("[name=userStartDt]").attr("readonly",false);
			$("[name=userEndDt]").attr("readonly",false);
			$("[name=userStartDt]").attr("disabled",false);
			$("[name=userEndDt]").attr("disabled",false);
		}else{
			$("#dateIsCheck").val("2");
			$('#dateCheckGb').prop('checked', false);
			$("#dateGb").val('2');
			$("[name=tempStartDt]").val($("[name=userStartDt]").val());
			$("[name=tempEndDt]").val($("[name=userEndDt]").val());
			$("#startDate").val('');
			$("#endDate").val('');
			$("[name=userStartDt]").val('');
			$("[name=userEndDt]").val('');
			$("[name=userStartDt]").attr("readonly",true);
			$("[name=userEndDt]").attr("readonly",true);
			$("[name=userStartDt]").attr("disabled",true);
			$("[name=userEndDt]").attr("disabled",true);
		}
	});

	$("#d_SDATE").change(function(){
		
		var startDate = $("#d_SDATE").val();
		$("#startDate").val(startDate);
	});

	$("#d_EDATE").change(function(){
		
		var endDate = $("#d_EDATE").val();
		$("#endDate").val(endDate);
	});
});


function goToSearch(){
	$("#searchValue").val(encodeURI($("#tempSearch").val()));
	
	var mySearchType = $("#comboBox").val();
	$("#searchType").val(mySearchType);

	var startDate = $("#d_SDATE").val();
	var endDate = $("#d_EDATE").val();
	var dateCheckGb = $("#dateCheckGb").is(":checked");

	if( dateCheckGb === true && (startDate.length < 1 || endDate.length <1) ){
		alert("<spring:message code='extend.local.096' />")
	}else{	
		document.noticeFrm.action='/app/log/list.html';
		document.noticeFrm.submit();
	}
}

function outputToCsv(){
	$("#searchValue").val(encodeURI($("#tempSearch").val()));
	
	var mySearchType = "${param.searchType}";
	$("#searchType").val(mySearchType);
	
	document.noticeFrm.action='/outputToCsv.html';
	document.noticeFrm.method="POST";
	document.noticeFrm.submit();
}

function jsonDataView(log_seq){
	$("#log_seq").val(log_seq);

	document.noticeFrm.action='/app/log/dataView.html';
	document.noticeFrm.method="POST";
	document.noticeFrm.submit();
}

</script>
</head>
<body>
<body class="popup" style="width:100%;  hieght:100%;">
	<!-- wrap -->
	<div class="pop_wrap">

	<div id="container">
		<div class="contents list_area" style="height:450px">

			<!-- //man header -->
			<h2> <spring:message code="extend.local.092" /></h2>

			<div class="section fisrt_section">
				<form name="noticeFrm" method="get" action="/man/notice/list.html?page=1" >
					<input type="hidden" name="page" id="page" value="${ param.page  }">
					<input type="hidden" name="searchType" id="searchType" value="1">
					<input type="hidden" id="searchValue" name="searchValue" type="text" value="" style="width:250px;">
					<c:if test="${ not empty param.storeBundleId }">
					<input type="hidden" name="storeBundleId" id="storeBundleId" value="${param.storeBundleId }">
					</c:if>
					<c:if test="${ not empty param.inappSeq }">
					<input type="hidden" name="inappSeq" id="inappSeq" value="${param.inappSeq }">
					</c:if>
					<input type="hidden" name="startDate" id="startDate" value="${startDate }"/>
					<input type="hidden" name="endDate" id="endDate" value="${endDate }"/>
					<input type="hidden" name="dateIsCheck" id ="dateIsCheck" value="${param.dateIsCheck }"/>
					<!-- 20170104 : lsy - Add logData View -->
					<input type="hidden" name="log_seq" id="log_seq" value="1">
				</form>
				<div class="con_header clfix">
					<div class="result fLeft">
						<spring:message code='template.list.002_1' /> ${logList.totalCount }<spring:message code='template.list.002_2' />
					</div>
					<div class="form_area fRight">
						<form>
							<fieldset>
								<input id="dateCheckGb" type="checkbox"  /> <spring:message code='extend.local.095' />&nbsp;&nbsp;
								<input id="d_SDATE" style="padding-left:5px; width:110px;" readonly="readonly" disabled="disabled" name="userStartDt" type="text" title="start date" class="date fmDate1" value="<c:if test="${ '1' eq param.dateIsCheck}">${startDate }</c:if>" />
										&nbsp;&nbsp;~&nbsp;&nbsp;
								<input id="d_EDATE" style="width:110px;" name="userEndDt" type="text" title="end date" readonly="readonly" disabled="disabled"  class="date toDate1" value="<c:if test="${ '1' eq param.dateIsCheck}">${endDate }</c:if>" />
								<select id="comboBox" name="comboBox" style="margin-left:20px;">
									<c:if test="${'1' eq isSingle }">
									<option value="1" ><spring:message code='extend.local.097' /></option>
									</c:if>
									<option value="2" ><spring:message code='page.index.001' /></option>
								</select>
								<input name=""  id="tempSearch" type="text" value="${ searchValue }">
								<a href="javascript:goToSearch();" class="btn btnM"><spring:message code='user.list.026' /></a>
							</fieldset>
						</form>
					</div>
				</div>
					<div class="table_area">
						<table class="coltable">
							<colgroup>
							<c:if test="${'1' eq isSingle }">
								<col style="width:140px">
								</c:if>
								<col style="width:140px">
								<col style="width:130px">
								<col style="width:130px">
								<col style="width:130px">
								<!-- 20170104 : lsy - Add logData View -->
								<col style="width:30px">
							</colgroup>
							<tr>
								<c:if test="${'1' eq isSingle }">
									<th scope="col"><spring:message code="extend.local.097"/></th>
								</c:if>
								<th scope="col"><spring:message code='user.list.003' /></th>
								<th scope="col">pageGb</th>
								<th scope="col">dataGb</th>
								<th scope="col"><spring:message code='app.list.table.col8' /></th>
								<!-- 20170104 : lsy - Add logData View -->
								<th scope="col">dataView</th>
							</tr>
							<c:choose>
								<c:when test="${empty logList.list}">
								<tr>
									<!-- 20170104 : lsy - Add logData View -->
									<td align="center" colspan="10" > <spring:message code='extend.local.094' /></td>
								</tr>
								</c:when>
							<c:otherwise>
								<c:forEach var="i" begin="0" end="${logList.list.size()-1}">
									<tr>
										<c:if test="${'1' eq isSingle }">
											<td>${logList.list[i].inappVO.inappName }</td>
										</c:if>
										<td>${logList.list[i].regMemberVO.userId }</td>
										<td>${logList.list[i].pageGb }</td>
										<td>${logList.list[i].dataGb }</td>
										<td><fmt:formatDate value="${logList.list[i].regDt}" pattern="yyyy-MM-dd"/></td>
										<!-- 20170104 : lsy - Add logData View -->
										<td><a href="javascript:jsonDataView('${logList.list[i].logSeq }')">view</a></td>
									</tr>
 								</c:forEach>
							</c:otherwise>
						</c:choose>
						</table>
					</div> <!-- table area -->
					<div class="paging">
					<c:if test="${logList.startPage != 1 }">
					<!--message : 이전 페이지로 이동  -->
					<a href="/app/log/list.html?page=${logList.startPage-logList.pageSize}&searchType=${param.searchType}&searchValue=${param.searchValue}" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='user.list.036' />"></a>
					</c:if>
					<c:forEach var="i" begin="${logList.startPage }" end="${logList.endPage}">
					    <c:if test="${param.page==i }"><span class="current"><c:out value="${i}"/></span></c:if>
					    <c:if test="${param.page!=i }"> <a href="/app/log/list.html?page=${i}&searchType=${param.searchType}&searchValue=${param.searchValue}"><c:out value="${i}"/></a></c:if>
	<%-- 			    <c:out value="${i}"/>
					    <c:if test="${param.page==i }"></strong></c:if> --%>
					</c:forEach>
					<c:if test="${logList.endPage != 1 && logList.endPage*logList.maxResult < logList.totalCount}">
					<!--message : 다음 페이지로 이동  -->
					<a href="/app/log/list.html?page=${logList.endPage+1}&searchType=${param.searchType}&searchValue=${param.searchValue}" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='user.list.037' />"></a>
					</c:if>
				</div>
				<div class="btn_area_bottom fRight clfix" style="margin-top:-42px;">
					<%-- <a class="btn btnL btn_gray_light" href="#"><spring:message code='user.list.024' /></a> --%>
					<!-- <a class="btn btnL btn_red" href="javascript:outputToCsv()"><spring:message code='extend.local.093' /></a> -->
				</div>
			</div>
		</div>
	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->
	</div>
</div>
</body>
</html>