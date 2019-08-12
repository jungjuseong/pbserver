<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>
</head>
<style type="text/css">#pop_category_wrap .category_detail{ width:45%;}</style>
<script type="text/javascript">
	$(document).ready(function(){	
		$("#toSel").val("${param.useS}")
		
		$(document).on('click', '[id^=number]', function(event){
			
			//sequence 		= 선택한 값
			//pointer 		= 선택한 값으로 기준을 세움
			//listSequence 	= 선택한 값들의 리스트 
			var pointer = $("#pointer").val();
			var sequence = $(this).attr("id").substring(6);
			

			
			var currentTable = $(this).parent().parent().attr("id").substring(11)
			var lastTable = $("#lastSelectTable").val();
			


			
			
			if(event.ctrlKey || event.metaKey){
				if(event.ctrlLeft){
				}else{
					if(event.shiftKey){
						//alert("ctrl + shiftkey");
						return;
					}

					//alert("bye");
					//alert("ctrl-right"+sequence);
					if( lastTable !== currentTable ){
						clearSelect();
						$("#selectSequence").val("0");
					}
					var listSequence = $("#selectSequence").val().split(",");
					
					//listSequence안에, 해당 클릭sequence가 있을경우 ( 하얀색 토글 :  삭제 )
					if($.inArray(sequence, listSequence) > -1){
						$(this).css("background-color", "#FFFFFF");
						$(this).css("color", "#000000");
						var temp = $("#selectSequence").val().split(",");
						temp.splice(temp.indexOf(sequence), 1);
						sequence =temp
					}else{
					//listSequence안에, 해당 클릭sequence가 없을 경우 ( 파란색 토글 : 추가)
						$(this).css("background-color","#1E90FF");
						$(this).css("color","#FFFFFF");
						var temp = $("#selectSequence").val();
						sequence = sequence+ ","+ temp;
					}
					//if(currentTable ===  "2") alert(sequence)
					$("#selectSequence").val(sequence);
				}
			}else if(event.shiftKey){
				if(event.ctrlKey){
					//alert("shift + ctrlKey")
					return;
				}
				//shiftkey를 누르면 무조건 초기화
				clearSelect();

				var listTemp;
				var selectList;
				
				
				
				$(this).parent().find("tr").each(function(entry){
					if(entry != 0){
						var temp = $(this).parent().find("tr").eq(entry).attr("id").substring(6);
						listTemp =  listTemp+ "," + temp;
						//alert(listTemp);
					}
				});
				
				listTemp = listTemp.split(","); 
				listTemp.splice(listTemp.indexOf("undefined"), 1);
				if(pointer == '') pointer = listTemp[0];
				//alert(sequence);
				/*alert(listTemp.indexOf(sequence)); */
				//alert(pointer);
				//alert(listTemp.indexOf(pointer))
				if(listTemp.indexOf(sequence) > listTemp.indexOf(pointer)){
					//alert("first");
					//clearSelect();
					for ( var i =listTemp.indexOf(pointer); i <= listTemp.indexOf(sequence); i ++){
						//alert("hello");
						$(this).parent().find("tr").eq(i+1).css("background-color", "##1E90FF");
						$(this).parent().find("tr").eq(i+1).css("color", "#FFFFFF");
	
						selectList = selectList+ ","+ $(this).parent().find("tr").eq(i+1).attr("id").substring(6);
						//alert($(this).parent().find("tr").eq(i).attr("id").substring(6));
					}
				}else{
					//alert("second");
					//clearSelect();
					for ( var i = listTemp.indexOf(sequence); i <= listTemp.indexOf(pointer); i ++){
						//alert("hello");
						$(this).parent().find("tr").eq(i+1).css("background-color", "##1E90FF");
						$(this).parent().find("tr").eq(i+1).css("color", "#FFFFFF");
	
						selectList = selectList+ ","+ $(this).parent().find("tr").eq(i+1).attr("id").substring(6);
						//alert($(this).parent().find("tr").eq(i).attr("id").substring(6));
					}
				}
				//alert(selectList);
				selectList = selectList.split(","); 
				selectList.splice(selectList.indexOf("undefined"), 1);
				$("#selectSequence").val(selectList);
				//alert(selectList);
				/* var listSequence = listTemp.split(",");
				alert(listSequence) */
				
				/* $(this).parent().find("tr").each(function(entry){
					$(this).parent().find("tr").eq(entry).css("background-color", "##1E90FF");
					$(this).parent().find("tr").eq(entry).css("color", "#FFFFFF");
				}); */
			}else{
				clearSelect();
				$("#pointer").val(sequence);
				$(this).css("background-color","#1E90FF");
				$(this).css("color","#FFFFFF");
				$("#selectSequence").val(sequence);
			}
			$("#lastSelectTable").val($(this).parent().parent().attr("id").substring(11));
		});

		$("#btnRight").on("click",  function(event){
			var previousTable = $("#lastSelectTable").val();
			var before = $("#assignTable1").find("tr").size();
			var userId ="";
			var userName ="";
			var userOneDepartment ="";
			var userTwoDepartment ="";
			
			
			if($("#toSel").val() == ""){
				var toSel = [];
			}else{
				var toSel = $("#toSel").val().split(",");
			}

			var listSequence = $("#selectSequence").val().split(",");
			//alert(listSequence);
			listSequence.forEach(function(entry){
				$("#assignTable1").find("#number"+entry).each(function() {
					   userId = $(this).find("td").eq(0).html();
					   userName = $(this).find("td").eq(1).html();
					   userOneDepartment = $(this).find("td").eq(2).html();
					   userTwoDepartment = $(this).find("td").eq(3).html();
					   userSeq = $(this).attr("alt");
						toSel.push(userSeq);
						$("#toSel").val(toSel);
					});
//alert(entry);
					$("#assignTable1").find("#number"+entry).remove();
					
					if( entry != "0" && previousTable == "1"){
						//alert(entry);
						$("#assignTable2").append(
								"<tr id='number"+entry+"'>"
									+"<td>"+userId+"</td>"
									+"<td>"+userName+"</td>"
									+"<td>"+userOneDepartment+"</td>"
									+"<td>"+userTwoDepartment+"</td>"
								+"</tr>"
						);
					}

			});
			var after = $("#assignTable1").find("tr").size();
			
			if(before != after) $("#selectSequence").val('0');
			
/* 			
			var count = $("#selectSequence").val();
			alert(count); */
		});

		$("#btnLeft").on("click",  function(event){
			
			var previousTable = $("#lastSelectTable").val();
			var before = $("#assignTable2").find("tr").size();
			//alert(before);
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

			var listSequence = $("#selectSequence").val().split(",");
			
			listSequence.forEach(function(entry){
				$("#assignTable2").find("#number"+entry).each(function() {
					   userId = $(this).find("td").eq(0).html();
					   userName = $(this).find("td").eq(1).html();
					   userOneDepartment = $(this).find("td").eq(2).html();
					   userTwoDepartment = $(this).find("td").eq(3).html();
					   userSeq = $(this).attr("alt");
					   var index = jQuery.inArray( userSeq , toSel);
					   toSel.splice(index , 1);
					});

					$("#assignTable2").find("#number"+entry).remove();
					if( entry != "0" && previousTable == "2"){
						//alert(entry);
						$("#assignTable1").append(
								"<tr id='number"+entry+"'>"
									+"<td>"+userId+"</td>"
									+"<td>"+userName+"</td>"
									+"<td>"+userOneDepartment+"</td>"
									+"<td>"+userTwoDepartment+"</td>"
								+"</tr>"
						);
					}
					/* toSel.push(userSeq);
					$("#toSel").val(toSel); */
			});
			
			$("#toSel").val(toSel);

			var after = $("#assignTable2").find("tr").size();

			//alert(after);
			if(before != after) $("#selectSequence").val('0');
			/* 
			$("#assignTable2").find("#number"+$("#selectSequence").val()).each(function() {
			   userId = $(this).find("td").eq(0).html();
			   userName = $(this).find("td").eq(1).html();
			   userOneDepartment = $(this).find("td").eq(2).html();
			   userTwoDepartment = $(this).find("td").eq(3).html();
			   userSeq = $(this).attr("alt");
			});
			
			$("#assignTable2").find("#number"+$("#selectSequence").val()).remove();

			$("#assignTable1").append(
					"<tr id='number"+permittedUserSize+"'>"
						+"<td>"+userId+"</td>"
						+"<td>"+userName+"</td>"
						+"<td>"+userOneDepartment+"</td>"
						+"<td>"+userTwoDepartment+"</td>"
					+"</tr>"
			);

			$("#selectSequence").val('0');
			var index = jQuery.inArray( userSeq , toSel);
			toSel.splice(index , 1);
			$("#toSel").val(toSel); */
		});

		$('#btnOk').click(function(e){
			$("#useS",opener.document).val($('#toSel').val());
			/* $("#useCnt",opener.document).text("("+$(".fm_userList2").length+"<spring:message code='template.modify.028_2' />)"); */
			self.close();
		});

		$('#btnSh').click(function(){
			if($("#toSel").val() == ""){
				var toSel = [];
			}else{
				var toSel = $("#toSel").val().split(",");
			}
			$("#useS").val(toSel);
			
			var url = "/assignment/search.html";
			var dataToBeSent = $("#frmSh").serialize();
			$.post(url, dataToBeSent, function(data, textStatus) {
					  //data contains the JSON object
					  //textStatus contains the status: success, error, etc

					 if(data.length > 0){
						$("#assignTable1").html('');
						$('#assignTable1').append(
								"<colgroup>"
									+"<col style='width:45%'>"
									+"<col style='width:20%'>"
									+"<col style='width:'>"
									+"<col style='width:'>"
								+"</colgroup>"
								+"<tr class='bottomline bold' style='background-color:#F3F3F3;'>"
									+"<td><spring:message code='extend.local.070' /></td>"
									+"<td><spring:message code='extend.local.064' /></td>"
									+"<td><spring:message code='extend.local.048' />1</td>"
									+"<td><spring:message code='extend.local.048' />2</td>"
								+"</tr>"
							);
						for(var i=0;i<data.length;i++){
							$('#assignTable1').append(
									"<tr id='number"+data[i].userSeq+"' alt='"+data[i].userSeq+"'>"
									+"<td>"+data[i].userId+"</td>"
									+"<td>"+data[i].lastName+data[i].firstName+"</td>"
									+"<td>"+((data[i].onedepartmentName != null) ? data[i].onedepartmentName : "")+"</td>"
									+"<td>"+((data[i].twodepartmentName != null) ? data[i].twodepartmentName : "")+"</td>"
								+"</tr>"
							);
						}
					}else{
						$("#assignTable1").html('');
						$('#assignTable1').append(
								"<colgroup>"
									+"<col style='width:45%'>"
									+"<col style='width:20%'>"
									+"<col style='width:'>"
									+"<col style='width:'>"
								+"</colgroup>"
								+"<tr class='bottomline bold' style='background-color:#F3F3F3;'>"
									+"<td><spring:message code='extend.local.070' /></td>"
									+"<td><spring:message code='extend.local.064' /></td>"
									+"<td><spring:message code='extend.local.048' />1</td>"
									+"<td><spring:message code='extend.local.048' />2</td>"
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
				$("#useS").val(toSel);
				
				var url = "/assignment/search.html";
				var dataToBeSent = $("#frmSh").serialize();
				$.post(url, dataToBeSent, function(data, textStatus) {
						  //data contains the JSON object
						  //textStatus contains the status: success, error, etc

						if(data.length > 0){
							$("#assignTable1").html('');
							$('#assignTable1').append(
									"<colgroup>"
										+"<col style='width:45%'>"
										+"<col style='width:20%'>"
										+"<col style='width:'>"
										+"<col style='width:'>"
									+"</colgroup>"
									+"<tr class='bottomline bold' style='background-color:#F3F3F3;'>"
										+"<td><spring:message code='extend.local.070' /></td>"
										+"<td><spring:message code='extend.local.064' /></td>"
										+"<td><spring:message code='extend.local.048' />1</td>"
										+"<td><spring:message code='extend.local.048' />2</td>"
									+"</tr>"
								);
							for(var i=0;i<data.length;i++){
								$('#assignTable1').append(
										"<tr id='number"+data[i].userSeq+"' alt='"+data[i].userSeq+"'>"
										+"<td>"+data[i].userId+"</td>"
										+"<td>"+data[i].lastName+data[i].firstName+"</td>"
										+"<td>"+((data[i].onedepartmentName != null) ? data[i].onedepartmentName : "")+"</td>"
										+"<td>"+((data[i].twodepartmentName != null) ? data[i].twodepartmentName : "")+"</td>"
									+"</tr>"
								);
							}
						}else{
							$("#assignTable1").html('');
							$('#assignTable1').append(
									"<colgroup>"
										+"<col style='width:45%'>"
										+"<col style='width:20%'>"
										+"<col style='width:'>"
										+"<col style='width:'>"
									+"</colgroup>"
									+"<tr class='bottomline bold' style='background-color:#F3F3F3;'>"
										+"<td><spring:message code='extend.local.070' /></td>"
										+"<td><spring:message code='extend.local.064' /></td>"
										+"<td><spring:message code='extend.local.048' />1</td>"
										+"<td><spring:message code='extend.local.048' />2</td>"
									+"</tr>"
								);
						} 
				}, "json");
			}
		});
	});
	
function clearSelect(){
	$("#assignTable1").find("tr").each(function(entry){
		if(entry != 0){
			$("#assignTable1").find("tr").eq(entry).css("background-color", "#FFFFFF");
			$("#assignTable1").find("tr").eq(entry).css("color", "#000000");
		}
		//alert($(this).parent().find("tr").eq(entry).attr("id"));
	});
	$("#assignTable2").find("tr").each(function(entry){
		if(entry != 0){
			$("#assignTable2").find("tr").eq(entry).css("background-color", "#FFFFFF");
			$("#assignTable2").find("tr").eq(entry).css("color", "#000000");
		}
		//alert($(this).parent().find("tr").eq(entry).attr("id"));
	});
}

</script>

<body class="popup" style="width:100%;  hieght:100%;">
	<!-- wrap -->
	<div class="pop_wrap">
		<!-- conteiner -->
		<div id="container">
			<div class="contents">
				<div class="pop_header clfix">
					<h1> <spring:message code='extend.local.028' /></h1>
				</div>

				<form method="post" name="frmUseMem" id="frmUseMem" onsubmit="return false;">
					<!--바로 전에 선택한 임시 sequence  -->
					<input type="hidden" id="selectSequence" 		name="selectSequence"       value="0" />
					<input type="hidden" id="permittedSequence" 	name="permittedSequence"       value="0" />
					<input type="hidden" id="toSel" 				name="toSel"       value="" />
					<input type="hidden" id="pointer"				name="pointer"		value=""/>
					<input type="hidden" id="lastSelectTable"		name="lastSelectTable"	value=""/>
					<!--바로 sequence  -->
				</form>
				<!-- contents_detail -->
				<div class="pop_contents list_area" id="pop_category_wrap">
				<form method="post" id="frmSh" name="frmSh" onsubmit="return false;">
					<div class="form_area tRight">
						<select name="searchType">
							<option value="1"><spring:message code='extend.local.070' /></option>
							<option value="2"><spring:message code='extend.local.064' /></option>
							<option value="3"><spring:message code='extend.local.048' />1</option>
							<option value="4"><spring:message code='extend.local.048' />2</option>
						</select>
						<input type="text"   id="searchValue"name="searchValue"   style="width:250px;">
						<input type="hidden" id="useS" 	name="useS"       value="" />
						<a href="#1" class="btn btnM  btn_gray_dark" id="btnSh"><spring:message code='template.pop.002' /></a>
					</div>
				</form>	
					<br/>
					<div class="pop_section">
						<div class="clfix">
							<div class="category_detail">
								<h2> <spring:message code='extend.local.069' /></h2>
								<div class="scrollit outline tCenter defaultCursor preventTextSelection">
									<table id = "assignTable1">
										<colgroup>
											<col style="">
											<col style="width:20%">
											<col style="width:">
											<col style="width:">
										</colgroup>
										<tr class="bottomline bold" style="background-color:#F3F3F3;">
											<td><spring:message code='extend.local.070' /></td>
											<td><spring:message code='extend.local.064' /></td>
											<td><spring:message code='extend.local.048' />1</td>
											<td><spring:message code='extend.local.048' />2</td>
										</tr>
										<c:choose>	
											<c:when test="${empty userList}">																		
											</c:when>
											<c:otherwise>
												<c:forEach var="i" begin="0" end="${userList.size()-1}">
													<tr id="number${userList[i].userSeq }" alt="${userList[i].userSeq }">
														<td>${userList[i].userId} <%-- ${userList[i].userSeq} --%></td>
														<td>${userList[i].lastName}${userList[i].firstName}</td>
														<td>${userList[i].onedepartmentName }</td>
														<td>${userList[i].twodepartmentName }</td>
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
								<h2> <spring:message code='extend.local.071' /></h2>
								<div class="scrollit outline tCenter defaultCursor preventTextSelection">
									<table id ="assignTable2">
										<colgroup>
											<col style="width:">
											<col style="width:20%">
											<col style="width:">
											<col style="width:">
										</colgroup>
										<tr class="bottomline bold" style="background-color:#F3F3F3;">
											<td><spring:message code='extend.local.070' /></td>
											<td><spring:message code='extend.local.064' /></td>
											<td><spring:message code='extend.local.048' />1</td>
											<td><spring:message code='extend.local.048' />2</td>
										</tr>
										<c:choose>	
											<c:when test="${empty permitList}">																		
											</c:when>
											<c:otherwise>
													<c:forEach var="i" begin="0" end="${permitList.size()-1}">
														<tr id="number${permitList[i].userSeq }"  alt="${permitList[i].userSeq }">
															<td>${permitList[i].userId}<%-- ${permitList[i].userSeq} --%></td>
															<td>${permitList[i].lastName}${permitList[i].firstName}</td>
															<td>${permitList[i].onedepartmentName }</td>
															<td>${permitList[i].twodepartmentName }</td>
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