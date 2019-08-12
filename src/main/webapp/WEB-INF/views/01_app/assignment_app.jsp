<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>
</head>
<style type="text/css">#pop_category_wrap .category_detail{ width:45%;}</style>
<script type="text/javascript">
	$(document).ready(function(){	
		
		
		
		
		$("#toSel").val("${param.useA}")
		
		$(document).on('click', '[id^=number]', function(){
			var sequence = $(this).attr("id").substring(6);
			
			if(sequence != $("#selectSequence").val()){
				$("#number"+$("#selectSequence").val()).css("background-color", "#FFFFFF");
				$("#number"+$("#selectSequence").val()).css("color", "#000000");
			}
			$("#selectSequence").val(sequence);
			$(this).css("background-color","#1E90FF");
			$(this).css("color","#FFFFFF");
		});
		
		$("#btnRight").on("click",  function(){
			var permittedUserSize = $("#selectSequence").val();
			var userId ="";
			var userName ="";
			var userOneDepartment ="";
			var userTwoDepartment ="";
			var userSeq = "";
			
			if($("#toSel").val() == ""){
				var toSel = [];
			}else{
				alert("<spring:message code='extend.local.063' />");
				return ;
				var toSel = $("#toSel").val().split(",");
			}

			$("#assignTable1").find("#number"+$("#selectSequence").val()).each(function() {
			   userId = $(this).find("td").eq(0).html();
			   userName = $(this).find("td").eq(1).html();
			   userOneDepartment = $(this).find("td").eq(2).html();
			   userTwoDepartment = $(this).find("td").eq(3).html();
			   userSeq = $(this).attr("alt");
			});
			
			$("#assignTable1").find("#number"+$("#selectSequence").val()).remove();

			$("#assignTable2").append(
					"<tr id='number"+permittedUserSize+"' alt='"+permittedUserSize+"'>"
						+"<td>"+userId+"</td>"
						+"<td>"+userName+"</td>"
						+"<td>"+userOneDepartment+"</td>"
						+"<td>"+userTwoDepartment+"</td>"
					+"</tr>"
			);
			$("#selectSequence").val('0');
			toSel.push(userSeq);
			$("#toSel").val(toSel);
		});
		
		$("#btnLeft").on("click",  function(){
			var permittedUserSize =$("#selectSequence").val();
			var userId ="";
			var userName ="";
			var userOneDepartment ="";
			var userTwoDepartment ="";
			var userSeq ="";

			if($("#toSel").val() == ""){
				var toSel = [];
			}else{
				var toSel = $("#toSel").val().split(",");
			}


			$("#assignTable2").find("#number"+$("#selectSequence").val()).each(function() {
			   userId = $(this).find("td").eq(0).html();
			   userName = $(this).find("td").eq(1).html();
			   userOneDepartment = $(this).find("td").eq(2).html();
			   userTwoDepartment = $(this).find("td").eq(3).html();
			   userSeq = $(this).attr("alt");
			});
			
			$("#assignTable2").find("#number"+$("#selectSequence").val()).remove();

			$("#assignTable1").append(
					"<tr id='number"+permittedUserSize+"' alt='"+permittedUserSize+"'>"
						+"<td>"+userId+"</td>"
						+"<td>"+userName+"</td>"
						+"<td>"+userOneDepartment+"</td>"
						+"<td>"+userTwoDepartment+"</td>"
					+"</tr>"
			);

			$("#selectSequence").val('0');
			var index = jQuery.inArray( userSeq , toSel);
			toSel.splice(index , 1);
			$("#toSel").val(toSel);
		});

		$('#btnOk').click(function(e){
			$("#useA",opener.document).val($('#toSel').val());
			/* $("#useCnt",opener.document).text("("+$(".fm_userList2").length+"<spring:message code='template.modify.028_2' />)"); */
			self.close();
		});

		$('#btnSh').click(function(){
			if($("#toSel").val() == ""){
				var toSel = [];
			}else{
				var toSel = $("#toSel").val().split(",");
			}
			$("#useA").val(toSel);

			var url = "/assignment/search.html";
			var dataToBeSent = $("#frmSh").serialize();
			$.post(url, dataToBeSent, function(data, textStatus) {
					  //data contains the JSON object
					  //textStatus contains the status: success, error, etc

					 if(data.length > 0){
						$("#assignTable1").html('');
						$('#assignTable1').append(
								"<colgroup>"
									+"<col style=''>"
									+"<col style='width:20%'>"
									+"<col style='width:'>"
									+"<col style='width:'>"
								+"</colgroup>"
								+"<tr class='bottomline bold'>"
									+"<td><spring:message code='extend.local.064' /></td>"
									+"<td><spring:message code='extend.local.065' /></td>"
									+"<td>OS</td>"
									+"<td>TYPE</td>"
								+"</tr>"
							);

						for(var i=0;i<data.length;i++){
							$('#assignTable1').append(
									"<tr id='number"+data[i].appSeq+"' alt='"+data[i].appSeq+"'>"
									+"<td>"+data[i].appName+"</td>"
									+"<td>"+data[i].storeBundleId+"</td>"
									+"<td>"+((data[i].ostype == 1) ? 'Universal' : (data[i].ostype == 2) ? 'iPhone' : (data[i].ostype == 3) ? 'iPad' : (data[i].ostype == 4) ? 'Android' : '??')+"</td>"
									+"<td>"+((data[i].regGb == 1) ? 'Library' : 'Single')+"</td>"
								+"</tr>"
							);
						}
					}else{
						$("#assignTable1").html('');
						$('#assignTable1').append(
								"<colgroup>"
									+"<col style=''>"
									+"<col style='width:20%'>"
									+"<col style='width:'>"
									+"<col style='width:'>"
								+"</colgroup>"
								+"<tr class='bottomline bold'>"
									+"<td><spring:message code='extend.local.064' /></td>"
									+"<td><spring:message code='extend.local.065' /></td>"
									+"<td>OS</td>"
									+"<td>TYPE/td>"
								+"</tr>"
							);
					} 
			}, "json");
		});

		$('#searchValue').keyup(function(e){	
			if (e.keyCode == 13) {
				if($("#toSel").val() == ""){
					var toSel = [];
				}else{
					var toSel = $("#toSel").val().split(",");
				}
				$("#useA").val(toSel);
				
				var url = "/assignment/search.html";
				var dataToBeSent = $("#frmSh").serialize();
				$.post(url, dataToBeSent, function(data, textStatus) {
						  //data contains the JSON object
						  //textStatus contains the status: success, error, etc

						if(data.length > 0){
							$("#assignTable1").html('');
							$('#assignTable1').append(
									"<colgroup>"
										+"<col style=''>"
										+"<col style='width:20%'>"
										+"<col style='width:'>"
										+"<col style='width:'>"
									+"</colgroup>"
									+"<tr class='bottomline bold' style='background-color:#F3F3F3;'>"
										+"<td><spring:message code='extend.local.064' /></td>"
										+"<td><spring:message code='extend.local.065' /></td>"
										+"<td>OS</td>"
										+"<td>TYPE</td>"
									+"</tr>"
								);
							for(var i=0;i<data.length;i++){
								$('#assignTable1').append(
										"<tr id='number"+data[i].appSeq+"' alt='"+data[i].appSeq+"'>"
										+"<td>"+data[i].appName+"</td>"
										+"<td>"+data[i].storeBundleId+"</td>"
										+"<td>"+((data[i].ostype == 1) ? 'Universal' : (data[i].ostype == 2) ? 'iPhone' : (data[i].ostype == 3) ? 'iPad' : (data[i].ostype == 4) ? 'Android' : '??')+"</td>"
										+"<td>"+((data[i].regGb == 1) ? 'Library' : 'Single')+"</td>"
									+"</tr>"
								);
							}
						}else{
							$("#assignTable1").html('');
							$('#assignTable1').append(
									"<colgroup>"
										+"<col style=''>"
										+"<col style='width:20%'>"
										+"<col style='width:'>"
										+"<col style='width:'>"
									+"</colgroup>"
									+"<tr class='bottomline bold' style='background-color:#F3F3F3;'>"
										+"<td><spring:message code='extend.local.064' /></td>"
										+"<td><spring:message code='extend.local.065' /></td>"
										+"<td>OS</td>"
										+"<td>TYPE</td>"
									+"</tr>"
								);
						} 
				}, "json");
			}
		});
	});
</script>

<body class="popup" style="width:100%;  hieght:100%;">
	<!-- wrap -->
	<div class="pop_wrap">
		<!-- conteiner -->
		<div id="container">
			<div class="contents">
				<div class="pop_header clfix">
					<h1> <spring:message code='extend.local.066' /></h1>
				</div>
	
				<form method="post" name="frmUseMem" id="frmUseMem" onsubmit="return false;">
					<!--바로 전에 선택한 임시 sequence  -->
					<input type="hidden" id="selectSequence" 		name="selectSequence"       value="0" />
					<input type="hidden" id="permittedSequence" 	name="permittedSequence"       value="0" />
					<input type="hidden" id="toSel" 	name="toSel"       value="" />
					<!--바로 sequence  -->
				</form>
				<!-- contents_detail -->
				<div class="pop_contents list_area" id="pop_category_wrap">
					<form method="post" id="frmSh" name="frmSh" onsubmit="return false;">
						<div class="form_area tRight">
							<select name="searchType">
								<option value="1"><spring:message code='extend.local.064' /></option>
								<option value="2"><spring:message code='extend.local.065' /></option>
								<option value="3">OS</option>
								<option value="4">TYPE</option>
							</select>
							<input type="text"   id="searchValue"name="searchValue"   style="width:250px;">
							<input type="hidden" id="useA" 	name="useA"       value="" />
							<a href="#1" class="btn btnM  btn_gray_dark" id="btnSh"><spring:message code='template.pop.002' /></a>
						</div>
					</form>
					<br/>
					<div class="pop_section">
						<div class="clfix">
							<div class="category_detail">
								<h2> <spring:message code='extend.local.067' /></h2>
								<div class="scrollit outline tCenter defaultCursor preventTextSelection">
									<table id = "assignTable1">
										<colgroup>
											<col style="">
											<col style="width:20%">
											<col style="width:">
											<col style="width:">
										</colgroup>
										<tr class="bottomline bold" style="background-color:#F3F3F3;">
											<td><spring:message code='extend.local.064' /></td>
											<td><spring:message code='extend.local.065' /></td>
											<td>OS</td>
											<td>TYPE</td>
										</tr>
										<!--
											$('#assignTable1').append(
													"<tr id='number"+data[i].appSeq+"' alt='"+data[i].appSeq+"'>"
													+"<td>"+data[i].appName+"</td>"
													+"<td>"+data[i].storeBundleId+"</td>"
													+"<td>"+((data[i].ostype == 1) ? 'Universal' : (data[i].ostype == 2) ? 'iPhone' : (data[i].ostype == 3) ? 'iPad' : (data[i].ostype == 4) ? 'Android' : '??')+"</td>"
													+"<td>"+((data[i].regGb == 1) ? data[i].twodepartmentName : "")+"</td>"
												+"</tr>"
											);  
										-->
										
										<c:choose>	
											<c:when test="${empty notPermitList}">																		
											</c:when>
												<c:otherwise>
													<c:forEach var="i" begin="0" end="${notPermitList.size()-1}">
														<tr id="number${notPermitList[i].appSeq }" alt="${notPermitList[i].appSeq }">
															<td>${notPermitList[i].appName}</td>
															<td>${notPermitList[i].storeBundleId}</td>
															<td>
																<c:choose>
																	<c:when test="${'1' eq notPermitList[i].ostype }">Universal</c:when>
																	<c:when test="${'2' eq notPermitList[i].ostype }">iPhone</c:when>
																	<c:when test="${'3' eq notPermitList[i].ostype }">iPad</c:when>
																	<c:when test="${'4' eq notPermitList[i].ostype }">Android</c:when>
																</c:choose>	
															</td>
															<td>
																<c:choose>
																	<c:when test="${'1' eq notPermitList[i].regGb }">Library</c:when>
																	<c:when test="${'2' eq notPermitList[i].regGb }">Single</c:when>
																</c:choose>	
															</td>
														</tr>
													</c:forEach>
											</c:otherwise>
										</c:choose>
									</table>
								</div>
							</div>
							
							<div class="arrow_area">
								<a href="#1" id="btnLeft"><img src="/images/btn_arrow_left.png" alt="<spring:message code='template.pop.012' />"></a><!--message : 허가 회원 목록에서 회원 목록으로 이동  -->
								<a href="#2" id="btnRight"><img src="/images/btn_arrow_right.png" alt="<spring:message code='template.pop.013' />"></a><!--message : 회원 목록에서 허가 회원 목록으로 이동  -->
							</div>
							<div class="category_detail fRight">
								<h2> <spring:message code='extend.local.068' /></h2>
								<div class="scrollit outline tCenter defaultCursor preventTextSelection">
									<table id ="assignTable2">
										<colgroup>
											<col style="width:">
											<col style="width:20%">
											<col style="width:">
											<col style="width:">
										</colgroup>
										<tr class="bottomline bold" style="background-color:#F3F3F3;">
											<td><spring:message code='extend.local.064' /></td>
											<td><spring:message code='extend.local.065' /></td>
											<td>OS</td>
											<td>TYPE</td>
										</tr>
										<c:choose>	
											<c:when test="${empty permitList}">																		
											</c:when>
											<c:otherwise>
													<c:forEach var="i" begin="0" end="${permitList.size()-1}">
														<tr id="number${permitList[i].appSeq }" alt="${notPermitList[i].appSeq }">
															<td>${permitList[i].appName}</td>
															<td>${permitList[i].storeBundleId}</td>
															<td>
																<c:choose>
																	<c:when test="${'1' eq permitList[i].ostype }">Universal</c:when>
																	<c:when test="${'2' eq permitList[i].ostype }">iPhone</c:when>
																	<c:when test="${'3' eq permitList[i].ostype }">iPad</c:when>
																	<c:when test="${'4' eq permitList[i].ostype }">Android</c:when>
																</c:choose>	
															</td>
															<td>
																<c:choose>
																	<c:when test="${'1' eq permitList[i].regGb }">Library</c:when>
																	<c:when test="${'2' eq permitList[i].regGb }">Single</c:when>
																</c:choose>	
															</td>
														</tr>
													</c:forEach>
											</c:otherwise>
										</c:choose>
									</table>
								</div>
							</div>
						</div>
	
						<div class="btn_area_bottom tCenter">
							<a href="#1" class="btn btnL btn_red" id="btnOk" ><spring:message code='template.pop.007' /></a>
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