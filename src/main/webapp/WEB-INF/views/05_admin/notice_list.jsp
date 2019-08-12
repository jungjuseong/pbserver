<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../inc/top.jsp" %>

<script>
$(document).ready(function(){
	$("#tempSearch").keypress(function(event){
		 if (event.keyCode == 13) {
		 	event.preventDefault();
		 	goToSearch();
		 }
	});
	
	$("[name=comboBox]").change(function(){
		$("#searchType").val($(this).val());
	});
});

function goToSearch(){
	$("#searchValue").val(encodeURI($("#tempSearch").val()));
		
	document.noticeFrm.action='/man/notice/list.html?page=1';
	document.noticeFrm.submit();
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
			<h2> <spring:message code='extend.local.072' /></h2>

			<div class="section fisrt_section">
				<form name="noticeFrm" method="get" action="/man/notice/list.html?page=1" >
					<input type="hidden" name="page" id="page" value="${ param.page  }">
					<input type="hidden" name="searchType" id="searchType" value="1">
					<input type="hidden" id="searchValue" name="searchValue" type="text" value="" style="width:250px;">
				</form>
				<div class="con_header clfix">
					<div class="result fLeft">
						<spring:message code='template.list.002_1' /> ${noticeList.totalCount }<spring:message code='template.list.002_2' />
					</div>
					<div class="form_area fRight">
						<form>
							<fieldset>
								<select id="comboBox" name="comboBox">
									<option value="1" id="userId"><spring:message code='extend.local.024' /></option>
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
							<col style="width:140px">
							<col style="width:130px">
							<col style="width:130px">
							<col style="width:">
						</colgroup>
						<tr>
							<th scope="col"><spring:message code='extend.local.024' /></th>
							<th scope="col"><spring:message code='extend.local.040' /></th>
							<th scope="col"><spring:message code='extend.local.061' /></th>
							<th scope="col"><spring:message code='extend.local.066' /></th>
							<th scope="col"><spring:message code='extend.local.031' /></th>
							<th scope="col"><spring:message code='extend.local.039' /></th>
							<th scope="col"><spring:message code='extend.local.032' /></th>
						</tr>
						<c:choose>
							<c:when test="${empty noticeList.list}">
							<tr>
								<td align="center" colspan="9" > <spring:message code='extend.local.037' /></td>
							</tr>
							</c:when>
						<c:otherwise>
							<c:forEach var="i" begin="0" end="${noticeList.list.size()-1}">
								<tr>
									<td>
                                        <c:choose>
                                            <c:when test="${fn:indexOf(menuFunction, '233') != -1 }">
                                                <a href="/man/notice/modify.html?page=${param.page }&noticeSeq=${noticeList.list[i].noticeSeq}&searchType=${param.searchType}&searchValue=${param.searchValue}">${noticeList.list[i].noticeName}</a>
                                            </c:when>
                                            <c:otherwise>
                                                ${noticeList.list[i].noticeName}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
									<td>${noticeList.list[i].memberVO.userId }</td>
									<td>
										<c:if test="${'1' eq noticeList.list[i].useUserGb }" > All</c:if>
										<c:if test="${'2' eq noticeList.list[i].useUserGb }" > <spring:message code='extend.local.038' /></c:if>
									</td>
									<td>
										<c:if test="${'1' eq noticeList.list[i].appGb }" > All</c:if>
										<c:if test="${'2' eq noticeList.list[i].appGb }" > <spring:message code='extend.local.078' /></c:if>
									</td>
									<td>
										<fmt:formatDate value="${noticeList.list[i].noticeStartDt}" pattern="yyyy-MM-dd"/>~
										<fmt:formatDate value="${noticeList.list[i].noticeEndDt}" pattern="yyyy-MM-dd"/>
									</td>
									<td><fmt:formatDate value="${noticeList.list[i].regDt}" pattern="yyyy-MM-dd"/></td>
									<td>
										<c:if test="${'1' eq noticeList.list[i].publicGb}"><img src="/images/icon_circle_green.png" alt=""> <spring:message code='extend.local.033' /></c:if>
										<c:if test="${'2' eq noticeList.list[i].publicGb}"><img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='extend.local.034' /></c:if>
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					</table>
				</div> <!-- table area -->
				<div class="paging">
					<c:if test="${noticeList.startPage != 1 }">
						<!--message : 이전 페이지로 이동  -->
						<a href="/man/notice/list.html?page=${noticeList.startPage-noticeList.pageSize}&searchType=${param.searchType}&searchValue=${param.searchValue}" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='user.list.036' />"></a>
					</c:if>
					<c:forEach var="i" begin="${noticeList.startPage }" end="${noticeList.endPage}">
					    <c:if test="${param.page==i }"><span class="current"><c:out value="${i}"/></span></c:if>
					    <c:if test="${param.page!=i }"> <a href="/man/notice/list.html?page=${i}&searchType=${param.searchType}&searchValue=${param.searchValue}"><c:out value="${i}"/></a></c:if>
					</c:forEach>
					<c:if test="${noticeList.endPage != 1 && noticeList.endPage*noticeList.maxResult < noticeList.totalCount}">
						<!--message : 다음 페이지로 이동  -->
						<a href="/man/notice/list.html?page=${noticeList.endPage+1}&searchType=${param.searchType}&searchValue=${param.searchValue}" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='user.list.037' />"></a>
					</c:if>
				</div>
				<c:choose>
                    <c:when test="${fn:indexOf(menuFunction, '232') != -1 }">
                        <div class="btn_area_bottom fRight clfix" style="margin-top:-42px;">
                            <%-- <a class="btn btnL btn_gray_light" href="#"><spring:message code='user.list.024' /></a> --%>
                            <a class="btn btnL btn_red" href="write.html?page=${param.page}"><spring:message code='user.list.025' /></a>
                        </div>
                    </c:when>
                </c:choose>
			</div>
		</div>
	</div>

	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->
</div>

</body>
</html>