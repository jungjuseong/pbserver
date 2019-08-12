<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../inc/top.jsp" %>
<script>
$(document).ready(function(){
	$("input[name=inputs]").keyup(function(){
		if(this.value.length >= $(this).attr("maxlength")){
			$(this).next('input[name=inputs]').focus();
			return false;
		}
	})
	
	$('#registBtn').click(function(){
		$("#licenseNum").val($("#license1").val()+$("#license2").val()+$("#license3").val()+$("#license4").val());
		$.ajax({
			url:"/my/licenseRegistCheck.html",
			type:"POST",
			data:{
				"licenseNum":$("#licenseNum").val()
			},
			success:function(result){
				switch(result){
					case 0 : 
						alert("<spring:message code='mypage.license.002' />");
						break;
					case 1 : 
						$('#licenseForm').submit();
						break;
					case 2 : 
						alert("<spring:message code='mypage.license.003' />");
						break;
					case 3 : 
						alert("<spring:message code='mypage.license.004' />");
						break;
					case 4 : 
						alert("<spring:message code='mypage.license.005' />");
						break;
				}
			}
		});
	});
});
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
		
			<!-- mypage header -->
			<%@ include file="../inc/mypage_header.jsp" %>
			
			<!-- 라이센스 등록 -->
			<h2><spring:message code='mypage.license.001' /></h2>
			<form action="/my/licenseRegist.html" method="post" id="licenseForm" name="licenseForm">
				<input type="hidden" id="licenseNum" name="licenseNum" value="" >
				<input type="hidden" id="licenseSeq" name="licenseSeq" value="${licenseSeq }" >
				<input type="hidden" id="modify_gb" name="modify_gb" value="modify_password" >
				<div class="section">
					<div class="table_area">
						<table class="rowtable1 writetable">
							<colgroup>
								<col style="">
							</colgroup>
							<tr>
								<td>
									<input type="text" id="license1" name="inputs" style="width:20%; font-size: 30px; text-align: center;" maxlength="5">&nbsp;-
									<input type="text" id="license2" name="inputs" style="width:20%; font-size: 30px; text-align: center;" maxlength="5">&nbsp;-
									<input type="text" id="license3" name="inputs" style="width:20%; font-size: 30px; text-align: center;" maxlength="5">&nbsp;-
									<input type="text" id="license4" name="inputs" style="width:20%; font-size: 30px; text-align: center;" maxlength="5">
								</td>
							</tr>
						</table>
					</div>
	
					<div class="btn_area_bottom tCenter">
						<c:choose>
							<c:when test="${licenseSeq eq null }">
								<a href="#" id="registBtn" class="btn btnL btn_red"><spring:message code='mypage.license.001' /></a>
							</c:when>
							<c:otherwise>
								<a href="#" id="registBtn" class="btn btnL btn_red"><spring:message code='mypage.license.010' /></a>
								<a href="/my/license.html" class="btn btnL btn_gray_light"><spring:message code='app.write.text42' /></a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- //conteiner -->
	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>