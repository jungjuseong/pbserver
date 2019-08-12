<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../inc/top.jsp" %>
<script>

$(document).ready(function(){
	$('#submitBtn').click(function(){
		/* var userPw = $("#userPw").val(); */
		$('#pwReConfirm').submit();
		/* $.ajax({
			url:"modify.html",
			type:"POST",
			data:{
				"userPw" : userPw
			},
			success:function(result){
				switch(result){
					case 0 : 
						alert("패스워드가 일치하지 않습니다.");
						break;
					case 1 : 
						alert("패스워드가 일치합니다.");
						break;
				}
			}
		});
		 window.location.replace("modify.html?password=<c:out value='${memberVO.userPw}'/>");  */
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
			<!-- 비밀번호 재확인 -->
			<h2><spring:message code='mypage.password.001' /></h2>
			<form action="modify.html" method="post"  id="pwReConfirm" name="pwReConfirm" class="pwReConfirm">
				<div class="section">
					<div class="table_area">
						<table class="rowtable writetable">
							<colgroup>
								<col style="width:100px">
								<col style="">
							</colgroup>
							<tr>
							<!--message : 비밀번호  -->
								<th><label class="title" for="userPw"><em>*</em> <spring:message code='mypage.password.002' /></label></th>
								<td>
									<input id="userPw" name="userPw" type="password" style="width:95%;">
								</td>
							</tr>
						</table>
					</div>
	
					<div class="btn_area_bottom tCenter">
					<!--message : 확인  -->
						<a href="#" id="submitBtn" class="btn btnL btn_red"><spring:message code='mypage.password.003' /></a>
						<input type="hidden" id="modify_gb" name="modify_gb" value="modify_password" >
					</div>
				</div>
			</form>
			<!-- //비밀번호 재확인 -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>