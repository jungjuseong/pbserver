<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>
</head>

<body class="popup" style="width:100%; height:100%;">
<!-- wrap -->
<div class="pop_wrap">
	<!-- conteiner -->
	<div id="container">
		<div class="contents list_area">
			<div class="pop_header clfix">
				<h1>
					${name}
					<spring:message code='distribute.title.003' />		
				</h1>
			</div>

			<!-- contents_detail -->
			<div class="pop_contents">
				<div class="pop_section">
					<div class="table_area table_type1">
						<table class="coltable">
							<colgroup>
								<col style="width:80px">
								<col style="width:150px">
								<col style="width:80px">
								<col style="width:150px">
								<col style="width:">
							</colgroup>
							<caption></caption>
							<tr>
								<th scope="col"><spring:message code='restore.001' /></th>
								<th scope="col"><spring:message code='restore.002' /></th>
								<th scope="col"><spring:message code='restore.003' /></th>
								<th scope="col"><spring:message code='restore.004' /></th>
								<th scope="col"><spring:message code='restore.005' /></th>
							</tr>							
							<c:forEach var="result" items="${list}" varStatus="status">
								<tr>
									<td>${result.distributeRequestId }</td>
									<td><fmt:formatDate value="${result.distributeReqDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td>${result.restoreUserId }</td>
									<td><fmt:formatDate value="${result.distributeRestoreDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td class="modify_detail"><div>${result.restoreText }</div></td>
								</tr>
							</c:forEach> 
						</table>
					</div>
				</div>
			</div>
			<!-- //contents_detail -->
		</div>
	</div>
	<!-- //conteiner -->

</div><!-- //wrap -->

</body>
</html>
