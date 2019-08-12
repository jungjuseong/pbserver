<%@page import="com.clbee.pbcms.vo.InappVO"%>
<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.clbee.pbcms.vo.InAppList"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>

<script type="text/javascript">
$(document).ready(function(){
	if('${licenseStatus}' == '4'){
		$("#disposalReasonText").val('${disposalReason}');
		$("#disposalReasonText").prop('readonly', true);
	}
});

function licenseDisposal(){
	if($("#disposalReasonText").val() == ''){
		alert("<spring:message code='license.list.020' />");
		$("#disposalReasonText").focus();
		return false;
	}else{
		if(confirm("<spring:message code='license.disposal.001' />")){
			var formData = $("#licenseForm").serialize();		
			$.ajax({
				url:"/man/license/disposal.html",
				type:"POST",
				data:{
					"licenseSeq":$("#licenseSeq").val(),
					"disposalReasonText":$("#disposalReasonText").val()
				},
				success:function(result){
					switch(result){
						case 0 :
							alert("<spring:message code='license.disposal.002' />");
							break;
						case 1 :
							alert("<spring:message code='license.disposal.003' />");
							opener.parent.location.reload();
							window.close();
							break;
					}
				}
			});
		}
	}
}
</script>
</head>
<body>
	<!-- wrap -->
	<div class="pop_wrap" style="width: 500px">
	
		<!-- conteiner -->
		<div id="container">
			<form id="licenseForm" name="licenseForm" method="post" action="" onsubmit="return false;" >
				<input type="hidden" name="licenseSeq" id="licenseSeq" value="${licenseSeq }">
				
				<div class="contents join_area">
					<div class="pop_header clfix">
						<c:choose>
							<c:when test="${licenseStatus eq '4'}"><h1><spring:message code='license.disposal.006' /></h1></c:when>
							<c:otherwise><h1><spring:message code='license.list.019' /></h1></c:otherwise>
						</c:choose>
					</div>
					
					<div class="section fisrt_section">
						<div class="table_area">
							<table class="rowtable writetable">
								<colgroup>
									<col style="width:10%">
									<col style="">
								</colgroup>
								<tr>
									<th></th>
									<td>
										<textarea id="disposalReasonText" name="disposalReasonText" rows="4" style="width:95%;" placeholder="<spring:message code='license.list.020' />"></textarea>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</form>
		</div>
		<!-- //conteiner -->
	
		<div class="btn_area_bottom tRight" style="margin-top: 10px;">
			<c:if test="${licenseStatus ne '4'}"><a class="btn btnL btn_red" href="javascript:licenseDisposal();"><spring:message code='license.list.019' /></a></c:if>
		</div>
	</div><!-- //wrap -->

</body>
</html>