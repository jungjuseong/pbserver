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
					<c:choose>
						<c:when test="${type_gb eq 'app'}">
							${app_name} 
						</c:when>
						<c:when test="${type_gb eq 'contents'}">
							${contents_name} 
						</c:when>
						<c:when test="${type_gb eq 'inapp'}">
							${inapp_name} 
						</c:when>
					</c:choose>
					<spring:message code='history.001' />		
				</h1>
			</div>

			<!-- contents_detail -->
			<div class="pop_contents">
				<div class="pop_section">
					<div class="table_area table_type1">
						<table class="coltable">
							<colgroup>
								<col style="width:60px">
								<col style="width:90px">
								<col style="width:">
								<col style="width:70px">
								<col style="width:350px">
								<col style="width:90px">
							</colgroup>
							<caption></caption>
							<tr>
								<th scope="col"><spring:message code='history.002' /></th>
								<th scope="col"><spring:message code='history.003' /></th>
								<th scope="col"><spring:message code='history.004' /></th>
								<th scope="col"><spring:message code='history.005' /></th>
								<th scope="col"><spring:message code='history.006' /></th>
								<th scope="col"><spring:message code='history.008' /></th>
							</tr>
							<c:choose>
								<c:when test="${type_gb eq 'app'}">
									<c:forEach var="result" items="${appList}" varStatus="status">
										<tr>
											<td>${result.verNum }</td>
											<td>${result.regUserId }</td>
											<td><fmt:formatDate value="${result.distributeCompletDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td>${result.appSize }</td>
											<td class="modify_detail"><div>${result.descriptionText }</div></td>
											<td class="state">
												<c:choose>
													<c:when test="${'1' eq result.limitGb}">
					 									<img src="/images/icon_circle_red.png" alt=""> <spring:message code='app.list.table.status2' /><!-- 제한 -->
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${'2' eq result.useGb}">
							 									<img src="/images/icon_circle_red.png" alt=""> <spring:message code='app.list.table.status2' /><!-- 미사용 -->
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${'1' eq result.completGb}">
																		<img src="/images/icon_circle_green.png" alt=""> <spring:message code='app.list.table.status1' /><!-- 완료 --> 
																	</c:when>
																	<c:when test="${'2' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.list.table.status3' /> <!-- 테스트중 -->
																	</c:when>
																	<c:when test="${'3' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.list.table.status4' /> <!-- 배포요청 -->
																	</c:when>
																	<c:when test="${'4' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.list.table.status5' /> <!-- 배포반려 -->
																	</c:when>
																</c:choose>											
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach> 
								</c:when>
								<c:when test="${type_gb eq 'contents'}">
									<c:forEach var="result" items="${contentsList}" varStatus="status">
										<tr>
											<td>${result.verNum }</td>
											<td>${result.regUserId }</td>
											<td><fmt:formatDate value="${result.distributeCompletDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td>${result.contentsSize }</td>
											<td class="modify_detail"><div>${result.descriptionText }</div></td>
											<td class="state">
												<c:choose>
													<c:when test="${'1' eq result.limitGb}">
					 									<img src="/images/icon_circle_red.png" alt=""> <spring:message code='app.list.table.status2' /><!-- 제한 -->
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${'2' eq result.useGb}">
							 									<img src="/images/icon_circle_red.png" alt=""> <spring:message code='app.list.table.status2' /><!-- 미사용 -->
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${'1' eq result.completGb}">
																		<img src="/images/icon_circle_green.png" alt=""> <spring:message code='app.list.table.status1' /><!-- 완료 --> 
																	</c:when>
																	<c:when test="${'2' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.list.table.status3' /> <!-- 테스트중 -->
																	</c:when>
																	<c:when test="${'3' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.list.table.status4' /> <!-- 배포요청 -->
																	</c:when>
																	<c:when test="${'4' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.list.table.status5' /> <!-- 배포반려 -->
																	</c:when>
																</c:choose>											
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach> 
								</c:when>
								<c:when test="${type_gb eq 'inapp'}">
									<c:forEach var="result" items="${inappList}" varStatus="status">
										<tr>
											<td>${result.verNum }</td>
											<td>${result.regUserId }</td>
											<td><fmt:formatDate value="${result.distributeCompletDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td>${result.inappSize }</td>
											<td class="modify_detail"><div>${result.descriptionText }</div></td>
											<td class="state">
												<c:choose>
													<c:when test="${'1' eq result.limitGb}">
					 									<img src="/images/icon_circle_red.png" alt=""> <spring:message code='app.list.table.status2' /><!-- 제한 -->
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${'2' eq result.useGb}">
							 									<img src="/images/icon_circle_red.png" alt=""> <spring:message code='app.list.table.status2' /><!-- 미사용 -->
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${'1' eq result.completGb}">
																		<img src="/images/icon_circle_green.png" alt=""> <spring:message code='app.list.table.status1' /><!-- 완료 --> 
																	</c:when>
																	<c:when test="${'2' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.list.table.status3' /> <!-- 테스트중 -->
																	</c:when>
																	<c:when test="${'3' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.list.table.status4' /> <!-- 배포요청 -->
																	</c:when>
																	<c:when test="${'4' eq result.completGb}">
																		<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='app.list.table.status5' /> <!-- 배포반려 -->
																	</c:when>
																</c:choose>											
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach> 
								</c:when>
							</c:choose>
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
