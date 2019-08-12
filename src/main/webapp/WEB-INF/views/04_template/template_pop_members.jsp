<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>
</head>
<style type="text/css">#pop_category_wrap .category_detail{ width:280px;}</style>
<script type="text/javascript">
$(document).ready(function(){	
	$('#btnLeft').click(function(){		
		var str = "";
		var selLenth1 = $("#fm_userList1 option:selected").length; //선택한 회원수1
		var selLenth2 = $("#fm_userList2 option:selected").length; //선택한 회원수2
		var Lenth2 = $(".fm_userList2").length; //회원수2 목록
		
		var nHtml      	   = "";
		var delToSelVal    = "";
		
		if(Lenth2 == 0){
			alert("<spring:message code='template.pop.008' />");
			return;
		}
		if(selLenth2 == 0){
			alert("<spring:message code='template.pop.009' />");
			return;
		}

		$("#fm_userList2 option:selected").each(function () {   
			temSelVal =""
			if($(this).is(":checked") == true){
				/*toSelVal  */
				toSelVal  = $("#toSel").val();
				selVal  = $(this).val();
				selVal  = selVal.split( '|$|' );
				
				delToSelVal  += ",|"+selVal[0]+"|";
				
				nHtml = "";
				nHtml += "<option value='"+selVal[0]+"|$|"+selVal[1]+"|$|"+selVal[2]+"|$|"+selVal[3]+"' id='fm_userList1_"+selVal[0]+"' class='fm_userList1'>"+selVal[1]+"("+selVal[2]+selVal[3]+")</option>";
				$("#fm_userList1").append(nHtml);	
				$("#fm_userList2_"+selVal[0]).remove();
			}
		});

		
		if(delToSelVal != "") {
			if(selLenth2 > 1 || Lenth2 == 1){
				toSelVal  = toSelVal.replace(delToSelVal+',','');
			}else{
				toSelVal  = toSelVal.replace(delToSelVal,'');
			}
		}
		/*toSelVal  */
		toSelVal = toSelVal.replace(",,",',');
		$("#toSel").val(toSelVal);
	});
	
	$('#btnRight').click(function(){
		var str = "";
		var selLenth1 = $("#fm_userList1 option:selected").length; //선택한 회원수1
		var Lenth2    = $(".fm_userList2").length; //회원수2 목록
		var nHtml      	   = "";
		var addToSelVal    = "";

		//if(selLenth1+Lenth2 > 3){
		//	alert("<spring:message code='template.pop.010' />");
		//	return;
		//}
		$("#fm_userList1 option:selected").each(function () {   
			temSelVal =""
			if($(this).is(":checked") == true){
				toSelVal  = $("#toSel").val();
				selVal  = $(this).val();
				selVal  = selVal.split( '|$|' );
				addToSelVal  += ",|"+selVal[0]+"|";

				nHtml = "";
				nHtml += "<option value='"+selVal[0]+"|$|"+selVal[1]+"|$|"+selVal[2]+"|$|"+selVal[3]+"' id='fm_userList2_"+selVal[0]+"' class='fm_userList2'>"+selVal[1]+"("+selVal[2]+selVal[3]+")</option>";
				$("#fm_userList2").append(nHtml);	
				$("#fm_userList1_"+selVal[0]).remove();
			}else{
				alert("<spring:message code='template.pop.011' />");
				return;
			}
		});

		if(addToSelVal != "") {
			toSelVal  = toSelVal  + addToSelVal + ",";			
		}

		toSelVal = toSelVal.replace(",,",',');
		$("#toSel").val(toSelVal);		
	});

	$('#btnSh').click(function(){
		var useM_Val = $("#toSel").val();
		useM_Val = useM_Val.replace(/^.(\s+)?/, ""); 
		useM_Val = useM_Val.replace(/(\s+)?.$/, "");
		$("#shUseS").val(useM_Val);		
		var url = "/template/user_sh_list.html";
		var dataToBeSent = $("#frmSh").serialize();
		$.post(url, dataToBeSent, function(data, textStatus) {
			  //data contains the JSON object
			  //textStatus contains the status: success, error, etc
			 if(data.length > 0){
				$('#fm_userList1').html('');
				for(var i=0;i<data.length;i++){
					$('#fm_userList1').prepend("<option value='"+data[i].userSeq+"|$|"+data[i].userId+"|$|"+data[i].lastName+"|$|"+data[i].firstName+"' id='fm_userList1_"+data[i].userSeq+"' class='fm_userList1'>"+data[i].userId+"("+data[i].lastName+data[i].firstName+")</option>");		
				}
			 }else{
				$('#fm_userList1').html('');
			 }
		}, "json");
	});

	$('#sh_keyword').keyup(function(e){	
		if (e.keyCode == 13) {
		var useM_Val = $("#toSel").val();
		useM_Val = useM_Val.replace(/^.(\s+)?/, ""); 
		useM_Val = useM_Val.replace(/(\s+)?.$/, "");
		$("#shUseS").val(useM_Val);		
		var url = "/template/user_sh_list.html";
		var dataToBeSent = $("#frmSh").serialize();
			$.post(url, dataToBeSent, function(data, textStatus) {
				  //data contains the JSON object
				  //textStatus contains the status: success, error, etc
				 if(data.length > 0){
					$('#fm_userList1').html('');
					for(var i=0;i<data.length;i++){
						$('#fm_userList1').prepend("<option value='"+data[i].userSeq+"|$|"+data[i].userId+"|$|"+data[i].lastName+"|$|"+data[i].firstName+"' id='fm_userList1_"+data[i].userSeq+"' class='fm_userList1'>"+data[i].userId+"("+data[i].lastName+data[i].firstName+")</option>");		
					}
				 }else{
					$('#fm_userList1').html('');
				 }
			}, "json");
		}
	});

	$('#btnOk').click(function(e){
		$("#useS",opener.document).val($('#toSel').val());
		$("#useCnt",opener.document).text("("+$(".fm_userList2").length+"<spring:message code='template.modify.028_2' />)");
		self.close();
	});
});
</script>

<body class="popup" style="width:670px;">
<!-- wrap -->
<div class="pop_wrap">
	
	
	<!-- conteiner -->
	<div id="container">
		<div class="contents">
			<div class="pop_header clfix">
				<h1><spring:message code='template.pop.001' /></h1>
			</div>

			<form method="post" name="frmUseMem" id="frmUseMem" onsubmit="return false;">
				<input type="hidden" id="toSel" 	name="toSel"       value="${use_user_seq}" />
				<input type="hidden" id="temptoSel" name="temptoSel"   value="${use_user_seq}" />
			</form>
			<!-- contents_detail -->
			<div class="pop_contents list_area" id="pop_category_wrap">
			<form method="post" id="frmSh" name="frmSh" onsubmit="return false;">
				<input type="hidden" id="shUseS" 	name="shUseS"       value="" />
				<div class="form_area tRight">
					<!-- 
					<select name="">
						<option value="">이름</option>
						<option value="">설명</option>
					</select>
					 -->
					<input name="sh_keyword" id="sh_keyword" type="text" style="width:250px;">
					<a href="#1" class="btn btnM  btn_gray_dark" id="btnSh"><spring:message code='template.pop.002' /></a>
				</div>
			</form>	
				<br/>
				<div class="pop_section">
					<div class="clfix">
						<div class="category_detail">
							<h2><spring:message code='template.pop.005' /></h2>
							<div>
							<select name="fm_userList1" id="fm_userList1" multiple>
								<c:choose>	
									<c:when test="${empty userList}">																		
									</c:when>
									<c:otherwise>
											<c:forEach var="i" begin="0" end="${userList.size()-1}">							
												<option value="${userList[i].userSeq}|$|${userList[i].userId}|$|${userList[i].lastName}|$|${userList[i].firstName}" id="fm_userList1_${userList[i].userSeq}" class="fm_userList1">${userList[i].userId}(${userList[i].lastName}${userList[i].firstName})</option>
											</c:forEach>
									</c:otherwise>
								</c:choose>		
							</select>
							</div>
						</div>
						
						<div class="arrow_area">
							<a href="#1" id="btnLeft"><img src="/images/btn_arrow_left.png" alt="<spring:message code='template.pop.012' />"></a><!--message : 허가 회원 목록에서 회원 목록으로 이동  -->
							<a href="#2" id="btnRight"><img src="/images/btn_arrow_right.png" alt="<spring:message code='template.pop.013' />"></a><!--message : 회원 목록에서 허가 회원 목록으로 이동  -->
						</div>
						<div class="category_detail fRight">
							<h2><spring:message code='template.pop.006' /></h2>
							<div>
								<select name="fm_userList2" id="fm_userList2" multiple>
									<c:choose>
										<c:when test="${empty userList2}">
										</c:when>
										<c:otherwise>
												<c:forEach var="i" begin="0" end="${userList2.size()-1}">							
													<option value="${userList2[i].userSeq}|$|${userList2[i].userId}|$|${userList2[i].lastName}|$|${userList2[i].firstName}" id="fm_userList2_${userList2[i].userSeq}" class="fm_userList2">${userList2[i].userId}(${userList2[i].lastName}${userList2[i].firstName})</option>
												</c:forEach>
										</c:otherwise>
									</c:choose>
								</select>
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