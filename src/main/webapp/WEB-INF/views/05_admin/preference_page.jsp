<%@page import="com.clbee.pbcms.util.ConditionCompile"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>

<script>
$(document).ready(function(){
	$("#modifyBtn").click(function(){
		$("#user_modify_f").submit();
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
			<!-- man header -->
			<%-- <c:set var="curi" value="${requestScope['javax.servlet.forward.request_uri']}" /> --%>
			<!-- man header -->
			<%@ include file="../inc/man_header.jsp" %>
			<%-- <div class="tab_area">
				<ul>
					<sec:authorize access="hasRole('ROLE_COMPANY_MEMBER')">
					<!--messsage : 사용자  -->
						<li <c:if test="${fn:containsIgnoreCase(curi, '/preference/')}">class="current last"</c:if>><a href="/man/preference/modify.html"><spring:message code='extend.local.011' /></a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
					<!--message : 회원  -->
						<li <c:if test="${fn:containsIgnoreCase(curi, '/preference/')}">class="current last"</c:if>><a href="/man/preference/modify.html"><spring:message code='extend.local.011' /></a></li>
					</sec:authorize>
				</ul>
			</div> --%>
			
			<h2><spring:message code='extend.local.011' /></h2>
			
			<form action="/man/preference/modify.html" method="POST" id="user_modify_f">
				<div class="section fisrt_section">
					<div class="table_area">
						<table class="rowtable writetable thtable">
							<colgroup>
								<col style="width:120px">
								<col style="width:210px">
								<col style="width:120px">
								<col style="">
							</colgroup>
							<tr>
								<th scope="row"><label class ="title" for="localFilePath"><em>*</em> <spring:message code='extend.local.012' /></label></th>
								<td colspan="3">
									<input id="localFilePath" name="localFilePath" type="text" value="${localFilePath}" style=" width:95.7%;">
								</td>
							</tr>
							<tr>
								<th scope="row"><label class ="title" for="iOSInstallPath"><em>*</em> <spring:message code='extend.local.013' /></label></th>
								<td colspan="3">
									<input id="iOSInstallPath" name="iOSInstallPath" type="text" value="${iOSInstallPath}" style="width:95.7%;">
								</td>
							</tr>
							<%-- <tr>
								<th scope="row"><label class ="title" for="fileServerPath"><em>*</em> <spring:message code='extend.local.014' /></label></th>
								<td colspan="3">
									<input id="fileServerPath" name="fileServerPath" type="text" value="${fileServerPath }" style="width:95.7%;">
								</td>
							</tr> --%>
							<tr>
								<th scope="row"><label class ="title" for="basicIP"><em>*</em> <spring:message code='extend.local.015.1' /></label></th>
								<td colspan="3">
									<input id="basicIP" name="basicIP" type="text" value="${basicIP }" style="width:95.7%;">
								</td>
							</tr>
							<tr>
								<th scope="row"><label class ="title" for="FTPIP"><em>*</em> <spring:message code='extend.local.015' /></label></th>
								<td colspan="3">
									<input id="FTPIP" name="FTPIP" type="text" value="${FTPIP }" style="width:95.7%;">
								</td>
							</tr>
							<tr>
								<th scope="row"><label class ="title" for="FTPID"><em>*</em> <spring:message code='extend.local.016' /></label></th>
								<td colspan="3">
									<input id="FTPID" name="FTPID" type="text" value="${FTPID }" style="width:95.7%;">
								</td>
							</tr>
							<tr>
								<th scope="row"><label class ="title" for="FTPPW"><em>*</em> <spring:message code='extend.local.017' /></label></th>
								<td colspan="3">
									<input id="FTPPW" name="FTPPW" type="text" value="${FTPPW }" style="width:95.7%;">
								</td>
							</tr>
							<tr>
								<th scope="row"><label class ="title" for="emailID"><em>*</em> <spring:message code='extend.local.018' /></label></th>
								<td colspan="3">
									<input id="emailID" name="emailID" type="text" value="${emailID }" style="width:95.7%;">
								</td>
							</tr>
							<tr>
								<th scope="row"><label class ="title" for="emailPW"><em>*</em> <spring:message code='extend.local.019' /></label></th>
								<td colspan="3">
									<input id="emailPW" name="emailPW" type="text" value="${emailPW }" style="width:95.7%;">
								</td>
							</tr>
							<tr>
								<th scope="row"><label class ="title" for="emailDomain"><em>*</em> <spring:message code='extend.local.020' /></label></th>
								<td colspan="3">
									<input id="emailDomain" name="emailDomain" type="text" value="${emailDomain}" style="width:95.7%;">
								</td>
							</tr>
							<%-- <tr>
								<th scope="row"><label class ="title" for="deviceFlag"><em>*</em> <spring:message code='extend.local.020' /></label></th>
								<td colspan="3">
									<input id="deviceFlag" name="deviceFlag" type="text" value="${emailDomain}" style="width:95.7%;">
								</td>
							</tr> --%>
							<%
								if(ConditionCompile.isDeviceOption)
								out.println("<tr>");
								out.println("<th><span class='title'><em>*</em><spring:message code='extend.local.082' />");
							%>
							<spring:message code='extend.local.082' />
							<% 
								out.println("</span></th>");
								/* String deviceFlag=  (String)request.getAttribute("deviceFlag");
								out.println(deviceFlag); */
								out.println("<td>");
								out.println("<div class='radio_area'>");
								out.println("<input name='deviceFlag' id='u_y'  type='radio' value='true'");
								%>
								<c:if test="${'true' eq deviceFlag }">checked='checked'</c:if>
								<%out.println("/> <label for='u_y'>");%><spring:message code='app.modify.text30' />
								<%out.println("</label>");
								out.println("<input name='deviceFlag' id='u_n'  type='radio' value='false'");
								%><c:if test="${'false' eq deviceFlag }">checked='checked'</c:if>
								<%out.println("/> <label for='u_n'>");%><spring:message code='app.modify.text31' />
								<%out.println("</label>");
								out.println("</div>");	
								out.println("</td>");	
								out.println("</tr>");
/* ;							
								out.println("<div class='radio_area'>");
								out.println("<input name='deviceFlag' id='u_y'  type='radio' value='true' <c:if test=\"${'true' eq deviceFlag }\">checked='checked'</c:if> /> <label for='u_y'><spring:message code='app.modify.text30' /></label>");
								out.println("<input name='deviceFlag' id='u_n'  type='radio' value='false' <c:if test=\"${'false' eq deviceFlag }\">checked='checked'</c:if> /> <label style='margin-right:0px;' for='u_n'><spring:message code='app.modify.text31' /></label>");
								out.println("</div>");		
									
										 */	
							%>

							<!-- deviceFlag -->
						</table>
					</div>
					<div class="btn_area_bottom tCenter">
						<a href="#modifyBtn" id="modifyBtn" class="btn btnL btn_red"><spring:message code='user.modify.016' /></a>
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