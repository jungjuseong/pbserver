<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>
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
			<!-- 탈퇴하기 -->
			<h2>탈퇴하기</h2>
			<div class="section">
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:100px">
							<col style="">
						</colgroup>
						<tr>
							<th><label class="title" for="pass"><em>*</em> 비밀번호</label></th>
							<td>
								<input id="pass" name="" type="text" style="width:95%;">
							</td>
						</tr>
					</table>
				</div>

				<div class="btn_area_bottom tCenter">
					<a href="#" class="btn btnL btn_red">확인</a>
				</div>
			</div>
			<!-- //탈퇴하기 -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>