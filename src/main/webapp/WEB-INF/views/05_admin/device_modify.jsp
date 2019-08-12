<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>

<script>
$(document).ready(function(){
	
	$("#modifyBtn").click(function(){
		if($("[name=useGb]:checked").val()  == 1){
			$.ajax({
	            url: "/deviceIsOver200.html" ,
	            type: "POST" ,
	            async : false,
	            data:{
	                           },
	            success: function (result){
	            	if(result >= 200) alert("<spring:message code='extend.local.042' />");
	            	else $("#device_modify_f").submit();
	            }
	        });
		}else{
			$("#device_modify_f").submit();
		}
	});
});

function cancelMethod(){
	window.location.href="/man/device/list.html?page=${param.page}&searchType=${param.searchType}&searchValue=${param.searchValue}";
}
</script>
</head>
<body>

<!-- wrap -->
<div id="wrap" class="sub_wrap">
	
	<!-- header -->
	<%@ include file="../inc/header.jsp" %>
	<!-- //header -->

	
	<!-- conteiner -->
	<div id="container">
		<div class="contents join_area">
			<!-- man header -->
			<c:set var="curi" value="${requestScope['javax.servlet.forward.request_uri']}" />
			<!-- man header -->
			
			
			<div class="tab_area">
				<ul>
					<!--messsage : 사용자  -->
					<li <c:if test="${fn:containsIgnoreCase(curi, '/device/')}">class="current last"</c:if>><a href="/man/device/list.html?page=1"><spring:message code='extend.local.043' /></a></li>
				</ul>
			</div>
			<!-- //man header -->
				<h2><spring:message code='extend.local.044' /></h2>
			<form action="/man/device/modify.html" method="POST" id="device_modify_f">
				<input type="hidden" name="deviceSeq" id="deviceSeq"  value="${param.deviceSeq }" />		
			<div class="section fisrt_section">
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:120px">
							<col style="">
						</colgroup>
						<tr>
							<th scope="row"><label class="title" for="deviceInfo"><em>*</em> <spring:message code='extend.local.045' /></label></th>
							<td colspan="3">
								<input id="deviceInfo" name="deviceInfo" type="text" value="${deviceVO.deviceInfo }" style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="deviceType"><em>*</em> <spring:message code='extend.local.046' /></label></th>
							<td colspan="3">
								<input id="deviceType" name="deviceType" type="text" readonly="readonly" value="${deviceVO.deviceType }" style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="deviceUuid"><em>*</em> UUID</label></th>
							<td colspan="3">
								<input id="deviceUuid" name="deviceUuid" type="text" readonly="readonly" value="${deviceVO.deviceUuid}"  style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="regUserSeq"><em>*</em> <spring:message code='extend.local.047' /></label></th>
							<td >
								<input id="regUserSeq" name="regUserSeq" disabled readonly="readonly" type="text" value="${deviceVO.memberVO.userId }" style="width:69%;">
							</td>
							<th style="text-align:right;"><label class="title" for="name"><em>*</em> <spring:message code='extend.local.048' /></label></th>
							<td>
								<input id="verNum" style="float:right; margin-right:15px; text-align:center; width:220px;" name="verNum" type="text" readonly="readonly" value="${deviceVO.memberVO.onedepartmentName} <c:if test ="${(!empty deviceVO.memberVO.onedepartmentName) && (!empty deviceVO.memberVO.twodepartmentName)   }">></c:if> ${deviceVO.memberVO.twodepartmentName}" >						
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="regDt"><em>*</em> <spring:message code='extend.local.049' /></label></th>
							<td colspan="3">
								<input id="regDt" name="regDt" type="text" readonly="readonly" value="<fmt:formatDate value="${deviceVO.regDt}" pattern="yyyy-MM-dd"/>" style="width:26.8%;">
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='app.modify.text29' /></span></th>
							<td>
								<div class="radio_area">
									<input name="useGb" id="u_y"  type="radio" value="1" <c:if test="${'1' eq deviceVO.useGb }">checked="checked"</c:if> /> <label for="u_y"><spring:message code='app.modify.text30' /></label>
									<input name="useGb" id="u_n"  type="radio" value="2" <c:if test="${'2' eq deviceVO.useGb }">checked="checked"</c:if> /> <label style="margin-right:0px;" for="u_n"><spring:message code='app.modify.text31' /></label>
								</div>
							</td>
						</tr>
					</table>
				</div>

				<div class="btn_area_bottom tCenter">
					<a href="#modifyBtn" id="modifyBtn" class="btn btnL btn_red"><spring:message code='user.modify.016' /></a>
					<a href="javascript:cancelMethod();" class="btn btnL btn_gray_light"><spring:message code='user.modify.017' /></a>
				</div>
			</div>
			</form>
			<!-- //사용자 수정 -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->


</body>
</html>