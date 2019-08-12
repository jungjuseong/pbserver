<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>
</head>


<body>
<!-- wrap -->
<div id="wrap" class="sub_wrap">
	
	<!-- conteiner -->
	<div id="container" class="join_ok_wrap">
		<div class="contents">
			<!-- join_area -->
			<div class="join_ok_area">
				<!--message : 회원가입을 환영합니다.  -->
				<c:choose>
					<c:when test="${ 'true' eq emailChk}">
						<h2><spring:message code='member.join.ok.001' /></h2>
						<div class="section first_section">
						<!--message : 귀하의 메일이 확인되었습니다.  -->
						<p><spring:message code='member.join.ok.002' /></p>
						</div>
					</c:when>
					<c:otherwise>
						<h2><spring:message code='extend.local.089' /></h2>
						<div class="section first_section">
						<!--message : 귀하의 메일이 확인되었습니다.  -->
						<p><spring:message code='extend.local.090' /></p>
						</div>
					</c:otherwise>
				</c:choose>
				<div class="btn_area_bottom">
				<!--message : 로그인  -->
					<a href="/index.html" class="btn btnL btn_red"><spring:message code='member.join.ok.003' /></a>
				</div>
			</div>
			<!-- //join_area -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>