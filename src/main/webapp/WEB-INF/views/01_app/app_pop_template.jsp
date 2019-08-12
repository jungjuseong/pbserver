<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>
<script type="text/javascript">
$(document).ready(function(){

	//선택
	//btn btnXS choose
	$(document).on('click', '.btn.btnXS.choose', function(){
		//alert(1);
		var templateseq = $(this).parents('tr').attr('id');
		var templateName = $(this).parents('tr').find('td').eq('0').text();
		var maxTemplateContentsAmt = $(this).parents('tr').find('td').eq('7').text();
		var maxTemplateContentsGb =$(this).parents('tr').find('td').eq('8').text();
		//alert(templateseq+'/'+templateName);
		$(window.opener.document).find('[name=templateSeq]').val(templateseq);
		$(window.opener.document).find('[name=templateName]').val(templateName);
		$(window.opener.document).find('[name=cappContentsAmt]').val(maxTemplateContentsAmt);
		$(window.opener.document).find('[name=cappContentsGb]').val(maxTemplateContentsGb);
		/*cappContentsAmt와 maxTemplateContentsAmt는 사실상 같은거임..
		   서로 다른 사람이 수정하다보니 새로 field가 불필요하게 추가가됨.*/
		$(window.opener.document).find('[name=maxTemplateContentsAmt]').val(maxTemplateContentsAmt);
		$(window.opener.document).find('[name=maxTemplateContentsGb]').val(maxTemplateContentsGb);
		self.close();			
	});

	//상세 이미지 불러오기
	$('.getDetail').click(function(){
		var seq = $(this).parents('tr').attr('id');
		var action_url = '/app/template/capture.html?captureGb=4&boardSeq='+seq;
		//var action_url = '/app/template/capture.html?captureGb=4&boardSeq=16';//+seq;
		$.getJSON(action_url, function(data){

			var html='';
			if(data&&data.length>0){
				for(var i=0;i<data.length;i++){
					//alert(data[i].imgSaveFile);
					var imgOrgFile = data[i].imgOrgFile;
					var imgSaveFile = data[i].imgSaveFile;
					var imgPath = "<spring:message code='file.upload.path.template.capture.file' />";
					html += '<li><img src="'+imgPath+imgSaveFile+'" alt="'+imgOrgFile+'"></li>';
				}
				$('.thumb_area').show().find('ul').html('').append(html);
			}else{
				$('.thumb_area').hide().find('ul').html('');
			}
		}).fail(function(){
			alert("<spring:message code='app.template.list.text22' />")
		});
	});	

	//검색택스트리턴
	$('input[name=searchValue]').keyup(function(e){
		if(e.keycode=='13'){
			goToSearch(1);		
		}
	});	
});

function goToSearch(page){
	$('[name=currentPage]').val(page);
	$('#templateForm').attr('action','/app/template/list.html');
	$('#templateForm').submit();
}

<!--

//-->
</script>
</head>

<body class="popup" style="width:670px; height:740px;">
<!-- wrap -->
<div class="pop_wrap">

	<!-- conteiner -->
	<div id="container">
		<div class="contents list_area">
			<div class="pop_header clfix">
				<h1><spring:message code='app.template.list.text21' /></h1>
			</div>

			<!-- contents_detail -->
			<div class="pop_contents">
				<form id="templateForm" name="templateForm" method="post" action="/app/template/list.html" >
				<input type="hidden" name="templateTypeGb" value="${TemplateVO.templateTypeGb}" />
				<input type="hidden" name="ostypeGb" value="${TemplateVO.ostypeGb}" />
				<input type="hidden" name="currentPage" value="${templateList.currentPage}" />
				<input type="hidden" name="appContentsAmt" value="${TemplateVO.appContentsAmt}" />
				<input type="hidden" name="appContentsGb" value="${TemplateVO.appContentsGb }" />
				<input type="hidden" name=seq value="" />
				<div class="form_area tRight">
					<input name="searchValue" type="text" style="width:250px;" value="${templateList.searchValue }">
					<a href="javascript:goToSearch(1);" class="btn btnM  btn_gray_dark"><spring:message code='app.provision.list.text1' /></a>
				</div>
				</form>

				<div class="pop_section">
					<div class="result">
						<spring:message code='app.template.list.text4' /> ${templateList.totalCount }<spring:message code='app.template.list.text4.1' />
					</div>
					<!-- table_area -->
					<div class="table_area">
						<table class="coltable">
							<colgroup>
								<col style="width:">
								<col style="width:">
								<col style="width:90px">
								<col style="width:90px">
								<col style="width:100px">
								<col style="width:78px">
							</colgroup>
							<caption></caption>
							<tr>
								<th scope="col"><spring:message code='app.template.list.text1' /></th>
								<th scope="col"><spring:message code='app.template.list.text6' /></th>
								<th scope="col"><spring:message code='app.template.list.text7' /></th>
								<th scope="col"><spring:message code='app.template.list.text8' /></th>
								<th scope="col"><spring:message code='app.template.list.text19' /></th>
								<th scope="col"><spring:message code='app.template.list.text20' /></th>
							</tr>
						<c:choose>
							<c:when test="${empty templateList}">
							<tr>
								<td align="center" colspan="6" ><spring:message code='app.no.contents' /></td>
							</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="result" items="${templateList.list}" varStatus="status">
									<tr id="${result.templateSeq }">										
										<td><a class="getDetail" href="#getDetail_">${result.templateName} (${result.verNum })</a></td>
										<td>
											<c:choose>
												<c:when test="${'1' eq result.ostypeGb }">Universal</c:when>
												<c:when test="${'2' eq result.ostypeGb }">iPhone</c:when>
												<c:when test="${'3' eq result.ostypeGb }">iPad</c:when>
												<c:when test="${'4' eq result.ostypeGb }">Android</c:when>
											</c:choose>											
										</td>
										<td>${result.appContentsAmt}<spring:message code='app.template.list.text4.1' /></td>
										<td>
											<c:choose>
												<c:when test="${'2' eq result.templateTypeGb }"><spring:message code='app.template.list.text9' /></c:when>
												<c:when test="${'1' eq result.templateTypeGb }"><spring:message code='app.template.list.text10' /></c:when>
											</c:choose>										
										</td>
											<c:choose>
												<c:when test="${'1' eq result.useGb && '2' eq result.limitGb}">
												<td class="state"><img src="/images/icon_circle_green.png" alt=""> <spring:message code='app.template.list.text17' /></td>
												<td><a class="btn btnXS choose" href="#choose_"><spring:message code='app.template.list.text18' /></a></td>
												</c:when>
												<c:otherwise>
												<td class="state"><img src="/images/icon_circle_red.png" alt=""> <spring:message code='app.template.list.text16' /></td>
												<td></td>
												</c:otherwise>
											</c:choose>												
										<td></td>
										<td hidden="hidden">${result.appContentsAmt}</td>
										<td hidden="hidden">${result.appContentsGb}</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						</table>
					</div><!-- //table_area -->
					<!--페이징-->

					<div class="paging">
						<c:if test="${templateList.startPage != 1 }">
							<a href="javascript:goToSearch('${templateList.startPage-templateList.pageSize}');" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='app.template.list.text23' />"></a>
						</c:if>
						<c:forEach var="i" begin="${templateList.startPage }" end="${templateList.endPage}">
						    <c:if test="${templateList.currentPage==i }"><span class="current"><c:out value="${i}"/></span></c:if>
						    <c:if test="${templateList.currentPage!=i }"> <a href="javascript:goToSearch('${i}');"><c:out value="${i}"/></a></c:if>
		<%-- 					    <c:out value="${i}"/>
							    <c:if test="${param.page==i }"></strong></c:if> --%>
						</c:forEach>
						<c:if test="${templateList.endPage != 1 && templateList.endPage*templateList.maxResult < templateList.totalCount}">
							<a href="javascript:goToSearch('${templateList.endPage+1}');" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='app.template.list.text24' />"></a>
						</c:if>
					</div>
					<!--//페이징-->					
					
					<!--템플릿 썸네일-->
					<div class="thumb_area" style="display:none;">
						<h2><spring:message code='app.template.list.text2' /></h2>
						<ul class="clfix">

						</ul>
					</div>
					<!--//템플릿 썸네일-->
					
				</div>

			</div>
			<!-- //contents_detail -->
			
		</div>
	</div>
	<!-- //conteiner -->

</div><!-- //wrap -->

</body>
</html>
