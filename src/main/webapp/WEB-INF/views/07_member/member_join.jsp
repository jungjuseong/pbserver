<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../inc/top.jsp" %>
<script src="/js/jquery.validate.js"></script>
<script>

	 
$(document).ready(function() {/* 페이지 로딩이 다 된다음에 이 Function을 실행하겠다 라는 의미 */
	
	$("#lastName").keyup(function(){
		if($(this).val() == "DEMO"){
			$(".rowtable").find("tr").eq(4).find("em").html("")
			$("#isUseMail").val("false");
			if($("#email").val().length == 0){
				$("#userStatus").prop("value","4");
			}else{
				$("#userStatus").prop("value","5");
			}
		}else{
			$(".rowtable").find("tr").eq(4).find("em").html("*")
			$("#isUseMail").val("true");
			$("#userStatus").prop("value","5");

		}
	});
	
	
	jQuery.validator.addMethod("specialChar", function(value, element){
		return this.optional( element ) || /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=_-])(?=.*[0-9]).{0,16}$/.test(value);
	}, "wrong nic number");
	
/*	20180511 : lsy - radio delete, select add
	$("#company_member, #private_member").on("click",function(){
		if($("#company_member").prop("checked")){
			$(".second_section").show();
		}else{
			$(".second_section").hide();	
		}
	});

	$("#company_member, #private_member").ready(function(){
		if($("#company_member").prop("checked")){
			$(".second_section").show();
		}else{
			$(".second_section").hide();	
		}
	});
*/	
	$("#groupSelect").ready(function(){
		groupSetting();
	});
	
	$("#groupSelect").on("change", function(){
		groupSetting();
	});
	
	function groupSetting(){
		if( $("#groupSelect option:selected").attr("data-value") == "1" ){
			$(".second_section").show();
		}else if( $("#groupSelect option:selected").attr("data-value") == "2" ){
			$(".second_section").hide();
		}
		$("#groupName").val($("#groupSelect option:selected").attr("data-id"));
		$("#companyGb").val($("#groupSelect option:selected").attr("data-value"));
	}
//	20180511 : lsy - radio delete, select add - end

	$("#emailBtn").on("click", function(){
		var inputEmail = $("#email").val();
		if(inputEmail.length == 0){
			//message : 메일을 입력해 주십시오
			alert("<spring:message code='member.join.022' />")
		}
		else{
			if($("#email-error:visible").length){
				//message : 메일 형식이 적당하지 않습니다
				alert("<spring:message code='member.join.023' />")
			}
			else{
				$.ajax({
					url:"emailValidation.html",
					type:"POST",
					data:{
						"inputEmail":inputEmail
					},
					success:function(result){
						switch(result){
							//message : 해당 메일을 사용할 수 있습니다.
							case 0 : alert("<spring:message code='member.join.024' />");
								$("#emailValidFlag").prop("value",1);
								break;
							//message : 해당 메일이 이미 존재합니다
							case 1 : alert("<spring:message code='member.join.025' />");
								break;
							//message : [심각] 해당 메일이 2개 이상 존재하거나, DB Error 발생
							case 2 : alert("<spring:message code='member.join.026' />");
								break;
						}
					}
				});
			}
		}
	});

	
	$("#userIdBtn").on("click", function(){
		var inputUserId = $("#userId").val();

		if(inputUserId.length == 0){
			//message : 아이디를 입력해 주십시오
			alert("<spring:message code='member.join.027' />")
		}
		else{
			if($("#userId-error:visible").length){//1일때 
				//message : 아이디형식이 적당하지 않습니다.
				alert("<spring:message code='member.join.028' />")
			}else{
				$.ajax({
					url:"userIdValidation.html",
					type:"POST",
					data:{
						"inputUserId":inputUserId
					},
					success:function(result){
						switch(result){
							//message : 해당 아이디를 사용할 수 있습니다.
							case 0 : alert("<spring:message code='member.join.029' />");
								$("#idValidFlag").prop("value",1);
								break;
							//message : 해당 아이디가 이미 존재 합니다.
							case 1 : alert("<spring:message code='member.join.030' />");
								break;
							//message : [심각] 해당 아이디가 2개 이상 존재하거나, DB Error 발생
							case 2 : alert("<spring:message code='member.join.031' />");
								break;
						}
					}
				});
			}
		}
	});
	
 	$("#member_join_f").validate({
		
	    rules: {
	    	firstName: {
	    		required : true,
	    		maxlength : 20
	    	},
	    	lastName: {
	    		required : true,
	    		maxlength : 20
	    	},
	    	userId: {
	    		required: true,
	    		minlength : 8,
	    		maxlength : 20
	    	},
	    	userPw:{
	    		required: true,
	    		minlength : 8,
	    		specialChar : true,
	    		maxlength : 20
	    	},
	    	email: {
	    		required: { 
					depends :function () {  
						if($("#isUseMail").val() == "true"){
							return true;
						}else{
							return false;
						}
					} 
				},
				email: true,
				maxlength : 50
			},
			userPwConfirm : {
				required: true,
	    		minlength : 8,
	    		specialChar : true,
				equalTo: "#userPw",
				maxlength : 20
			},
			phone : {
				required : true,
				number : true,
				maxlength : 20
			},
			companyName : {
				required : true,
				maxlength : 50
			},
			companyTel : {
				required : true,
				number : true,
				maxlength : 20
			},
	    },

	    messages: {
	    	//messsage : 이름을 입력해 주십시오
	    	firstName: {
	    		required : "<spring:message code='member.join.033' />",
	    		maxlength : "20<spring:message code='extend.local.002' />"
	    	},
	    	//message : 성을 입력해 주십시오
			lastName: {
				required : "<spring:message code='member.join.032' />",
				maxlength : "20<spring:message code='extend.local.002' />"
			},
			userId: {
				//message : 아이디를 입력해 주십시오
				required: "<spring:message code='member.join.027' />",
				//message : 8글자 이상, 영문으로 등록하셔야 합니다.
				minlength: "<spring:message code='member.join.035' />",
				maxlength: "20<spring:message code='extend.local.002' />"
			},
			userPw: {
				//message : 패스워드를 입력해 주십시오
				required: "<spring:message code='member.join.036' />",
				//message : 8글자 이상 입력해주셔야 합니다.
				minlength : "<spring:message code='member.join.037' />",
				//message : 영문, 숫자 및 특수기호를 조합하여 등록하셔야 합니다.
				specialChar : "<spring:message code='member.join.038' />",
				maxlength: "20<spring:message code='extend.local.002' />"
			},
			
			userPwConfirm: {
				//message : 패스워드를 입력해 주십시오
				required: "<spring:message code='member.join.036' />",
				//message : 8글자 이상 입력해주셔야 합니다.
				minlength : "<spring:message code='member.join.037' />",
				//message : 영문, 숫자 및 특수기호를 조합하여 등록하셔야 합니다.
				specialChar : "<spring:message code='member.join.038' />",
				//message : 위와 똑같은 비밀번호를 입력해 주십시오
				equalTo: "<spring:message code='member.join.039' />",
				maxlength: "20<spring:message code='extend.local.002' />"
			},
			
			phone : {
				//message : 전화번호를 입력해 주십시오
				required : "<spring:message code='member.join.040' />",
				//message : 숫자만 입력해 주십시오
				number : "<spring:message code='member.join.042' />",
				maxlength: "20<spring:message code='extend.local.002' />"
			},
			
			email:{
				//message : 메일을 입력해 주십시오
				required : "<spring:message code='member.join.043' />",
				//message : 적당한 형식의 메일을 입력해 주십시오
				email : "<spring:message code='member.join.044' />",
				maxlength: "50<spring:message code='extend.local.002' />"
			},
			
			
			companyName : {
				//message : 기업명을 입력해 주십시오
				required : "<spring:message code='member.join.045' />",
				maxlength: "50<spring:message code='extend.local.002' />"
			},
			
			companyTel : {
				//message : 대표전화(회사전화번호)를 입력해주십시오
				required : "<spring:message code='member.join.046' />",
				//message : 숫자만 입력해 주십시오
				number : "<spring:message code='member.join.042' />",
				maxlength: "20<spring:message code='extend.local.002' />"
			}
		},
		
		errorPlacement: function(error, element) {
			error.appendTo( element.parent("td") );
		}
	});
	
	$("#member_join_f").on("submit", function(event){
		
		if($("#idValidFlag").val() == "0"){
			event.preventDefault();
			//messsage : 아이디 중복 확인해 주십시오
			alert("<spring:message code='member.join.048' />");
			$("#userId").focus();
		}else if($("#emailValidFlag").val() == "0" && $("#isUseMail").val() == "true"){
			event.preventDefault();
			//message : 메일 중복 확인해 주십시오
			alert("<spring:message code='member.join.049' />");
			$("#email").focus();
		}else if($("#emailValidFlag").val() == "0" && $("#isUseMail").val() == "false" && $("#email").val().length > 0){
			//message : 메일 중복 확인해 주십시오
			alert("<spring:message code='member.join.049' />");
			$("#email").focus();
		}
		else{
			if(!$(".error").text().length && $("#preventSubmit").val() == 0){
				$("#preventSubmit").val("1");
				//message : [메일 인증 발송] 메일 인증 후 로그인이 가능합니다.
				
				if($("#email").val().length > 0){
					alert("<spring:message code='member.join.047' />");
				}else{
					alert("<spring:message code='extend.local.003' />");
				}
			}else{
				event.preventDefault();
			}
		}
	});
	
	$("#email").keyup(function(){
		$("#emailValidFlag").prop("value",0);
		if($("#email").val().length == 0 && $("#lastName").val() == "DEMO"){
			$("#userStatus").prop("value","4");
		}else{
			$("#userStatus").prop("value","5");
		}
	});
	
	$("#userId").keyup(function(){
		$("#idValidFlag").prop("value",0);
		/*emailValidFlag*/
		/*idValidFlag*/
	});
	
	/* $("#username").focus(function() {
		var firstname = $("#firstname").val();
		var lastname = $("#lastname").val();
		if (firstname && lastname && !this.value) {
			this.value = firstname + "." + lastname;
		}
	}); */
});


function cancelResist(){
	//message : 지금까지 입력한 자료가 사라집니다. 취소하시겠습니까?
	if(confirm("<spring:message code='contents.modify.041' />")){
		window.location.href="/";
	}
}


</script>
</head>


<body>
<!-- wrap -->
<div id="wrap" class="sub_wrap">
	<!-- conteiner -->
	<div id="container">
		<div class="contents join_area">
			<!-- join_area -->
			<h2><spring:message code='member.join.020' /></h2>
			<form action="#" method="post" name="member_join_f" id="member_join_f" class="member_join_f">
			<div class="section first_section">
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:150px">
							<col style="width:210px">
							<col style="width:120px">
							<col style="">
						</colgroup>
						<tr>
							<th scope="row"><label class="title" for="userId"><em>*</em> <spring:message code='member.join.001' /></label></th>
							<td colspan="3">
								<input id="userId" name="userId"  type="text" style="width:79%;"  class="line_right">
								<a href="#" id="userIdBtn" class="btn btnL btn_gray_light line_left" ><spring:message code='member.join.009' /></a>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="userPw"><em>*</em> <spring:message code='member.join.003' /></label></th>
							<td colspan="3">
								<input id="userPw" name="userPw" type="password" style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="userPwConfirm"><em>*</em> <spring:message code='member.join.005' /></label></th>
							<td colspan="3">
								<input id="userPwConfirm" name="userPwConfirm" type="password" style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="lastName"><em>*</em> <spring:message code='member.join.006' /></label></th>
							<td>
								<input id="lastName" name="lastName" type="text"  style="width:85%;">
							</td>
							<th scope="row"><label class="title" for="firstName"><em>*</em> <spring:message code='member.join.007' /></label></th>
							<td>
								<input id="firstName" name="firstName" type="text" style="width:87%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="email"><em>*</em> <spring:message code='member.join.008' /></label></th>
							<td colspan="3">
								<input id="email" name="email" type="email" style="width:79%;" class="line_right">
								<a href="#" id="emailBtn" class="btn btnL btn_gray_light line_left"><spring:message code='member.join.009' /></a>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="phone"><em>*</em> <spring:message code='member.join.010' /></label></th>
							<td colspan="3">
								<input id="phone" name="phone" type="text" style="width:94.7%;">
							</td>
						</tr>
						
						<tr>
							<th scope="row"><label class="title"><em>*</em> <spring:message code='member.join.011' /></label></th>
							<td colspan="3">
								<select id="groupSelect" name="groupSelect">
									<c:if test="${groupList ne null}">
										<c:forEach var="i" begin="0" end="${groupList.size()-1}">
											<c:choose>
												<c:when test="i==0">
													<option value="${groupList[i].groupName }" data-value="${groupList[i].memberGb}" data-id="${groupList[i].groupSeq}" selected="selected">
														<c:choose>
															<c:when test="${groupList[i].memberGb eq '1'}">
																<spring:message code='member.join.051' />
															</c:when>
															<c:otherwise>
																<spring:message code='member.join.052' />
															</c:otherwise>
														</c:choose>
														: ${groupList[i].groupName }
													</option>
												</c:when>
												<c:otherwise>
													<option value="${groupList[i].groupName }" data-value="${groupList[i].memberGb}" data-id="${groupList[i].groupSeq}">
														<c:choose>
															<c:when test="${groupList[i].memberGb eq '1'}">
																<spring:message code='member.join.051' />
															</c:when>
															<c:otherwise>
																<spring:message code='member.join.052' />
															</c:otherwise>
														</c:choose>
														: ${groupList[i].groupName }
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:if>
								</select>
							</td>
						</tr>						
<%-- 20180511 : lsy - radio delete, select add						
						<tr>
							<th scope="row"><span class="title"><em>*</em> <spring:message code='member.join.011' /></span></th>
							<td colspan="3">
								<div class="radio_area">
									<input name="companyGb" id="company_member" type="radio" value="1" checked="checked" > <label for="company_member"><spring:message code='member.join.012' /></label>
									<input name="companyGb" id="private_member" type="radio" value="2"> <label for="private_member"><spring:message code='member.join.013' /></label>
								</div>
							</td>
						</tr>
 --%>						
					</table>
				</div>
			</div>
			
			<!-- 추가 입력 -->
			<div class="section second_section">
				<h3><spring:message code='member.join.014' /></h3>
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:150px">
							<col style="">
						</colgroup>
						<tr>
							<th scope="row"><label class="title" for="companyName"><em>*</em> <spring:message code='member.join.015' /></label></th>
							<td>
								<input id="companyName" name="companyName" type="text" style="width:94.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="companyTel"><em>*</em> <spring:message code='member.join.016' /></label></th>
							<td>
								<input id="companyTel" name="companyTel" type="text" style="width:94.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="zipcode"><spring:message code='member.join.017' /></label></th>
							<td colspan="3">
								<input id="zipcode" name="zipcode" type="text" class="line_right" style="width:95%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="addrFirst"><spring:message code='member.join.018' /></label></th>
							<td>
								<input id="addrFirst" name="addrFirst" type="text" style="width:95%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="addrDetail"><spring:message code='member.join.019' /></label></th>
							<td>
								<input id="addrDetail" name="addrDetail" type="text" style="width:95%;">
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!-- //추가 입력 -->
			<div class="btn_area_bottom tCenter">
				<input type="submit" onclick="" class="btn btnL btn_red" value="<spring:message code='member.join.020' />"/>
				<a href="javascript:cancelResist();" class="btn btnL btn_gray_light"><spring:message code='member.join.021' /></a>
				<input type="hidden" id="preventSubmit" name="preventSubmit" value="0"/>
				<input type="hidden" name="userStatus" id="userStatus" value="5"/><!-- 5는 사용대기의 의미 -->
				<input type="hidden" name="emailValidFlag" id="emailValidFlag" value="0"/>
				<input type="hidden" name="-" id="idValidFlag" value="0"/>
				<input type="hidden" name="userGb" id="userGb" value="127"/>
				<input type="hidden" name="emailChkGb" id="emailChkGb" value="N"/>
				<input type="hidden" name="isUseMail" id="isUseMail" value="true"/>
				
				<!-- 20180511 : lsy - group add -->
				<input type="hidden" name="groupName" id="groupName" value=""/>
				<input type="hidden" name="companyGb" id="companyGb" value=""/>
				<!-- 20180511 : lsy - group add - end -->
			</div>
			<!-- //join_area -->
			</form>
		</div>
	</div>
	<!-- //conteiner --->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>