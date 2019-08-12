<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <!-- 변수 지원, 흐름 제어, URL처리 -->
<%@ include file="../inc/top.jsp" %>
<script type="text/javascript" src="/js/jquery.validate.js"></script>
<script>

$(document).ready(function(){
/* 	20180528 : lsy - lsy - 그룹권한 추가(기존권한 삭제)
	if( "${memberVO.userGb}" == "1"){
		$("#isUseMail").val("false");
		$(".rowtable").find("tr").eq(4).find("em").html("")
	}else{
		$("#isUseMail").val("true");
	}
 */
	if($("#lastName").val() == "DEMO"){
		$("#isUseMail").val("false");
		$(".rowtable").find("tr").eq(4).find("em").html("")
	}else{
		$("#isUseMail").val("true");
	}
	
	jQuery.validator.addMethod("specialChar", function(value, element){
		return this.optional( element ) || /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=_-])(?=.*[0-9]).{0,16}$/.test(value);
	}, "wrong nic number");
	
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
/* 			20180528 : lsy - lsy - 그룹권한 추가(기존권한 삭제)
			if($("[name = userGb]:checked").val() == "1"){
				$("#isUseMail").val("false");
			}else{
				$(".rowtable").find("tr").eq(4).find("em").html("*")
				$("#isUseMail").val("true");
				$("#userStatus").prop("value","5");
			}
*/			//20180528 : lsy - lsy - 그룹권한 추가(기존권한 삭제) > 기존 로직 변경
			$(".rowtable").find("tr").eq(4).find("em").html("*")
			$("#isUseMail").val("true");
			$("#userStatus").prop("value","5");
		}
	});

	$("#userPw").blur(function(){
		if($("#userPw").val() == ""){
			$("#userPw-error").hide();
		}
	});

	$("#withDrawalBtn").click(function(){
		//message : 정말 탈퇴하시겠습니까?
		if(confirm("<spring:message code='mypage.modify.048' />")){
			var userSeq = "<c:out value='${memberVO.userSeq}'/>";
			var companySeq = "<c:out value='${memberVO.companySeq}'/>"

			$.ajax({
				url:"withDrawal.html",
				type:"POST",
				data:{
					"userSeq" : userSeq,
					"companySeq" : companySeq
				},
				success:function(result){
					switch(result){
						//message : 탈퇴 오류
						case 0 : alert("<spring:message code='mypage.modify.046' />");
							break;
						//message : 탈퇴 되었습니다.
						case 1 : alert("<spring:message code='mypage.modify.047' />");
						window.location.replace("j_spring_security_logout"); 
							break;
					}
				}
			});
		}
	});

	/* 
	$("#lastName").keyup(function(){
		if($(this).val() == "DEMO"){
			$(".rowtable").find("tr").eq(4).find("em").html("")
			$("#isUseMail").val("false");
		}else{
			if($("[name = userGb]:checked").val() == "1"){
				$("#isUseMail").val("false");
			}else{
				$(".rowtable").find("tr").eq(4).find("em").html("*")
				$("#isUseMail").val("true");
			}
		}
	}); */
	
	$("#mypage_modify_f").validate({
	    rules: {
	    	firstName: "required",
	    	lastName: "required",
	    	userPw:{
	    		minlength : 8,
	    		specialChar : true
	    	},
	    	email: {
				required: {
					depends : function () {  
						if( $("#isUseMail").val() == "true"){
							return true;
						}else{
							return false;
						}
					} 
				},
				email: true
			},
			userPwConfirm : {
	    		minlength : 8,
	    		specialChar : true,
				equalTo: "#userPw",
			},
			phone : {
				required : true,
				number : true
			},			
			companyName : {
				required : true
			},			
			companyTel : {
				required : true,
				number : true
			}, 
	    },

	    messages: {
	    	//message : 성을 입력해 주십시오
	    	firstName: "<spring:message code='mypage.modify.026' />",
	    	//message : 이름을 입력해 주십시오
			lastName: "<spring:message code='mypage.modify.025' />",
			userPw: {
				//message : 8글자 이상 입력해주셔야 합니다.
				minlength : "<spring:message code='mypage.modify.030' />",
				//message : 영문, 숫자 및 특수기호를 조합하여 등록하셔야 합니다.
				specialChar : "<spring:message code='mypage.modify.031' />"
			},
			
			userPwConfirm: {
				//message : 8글자 이상 입력해주셔야 합니다.
				minlength : "<spring:message code='mypage.modify.030' />",
				//message : 영문, 숫자 및 특수기호를 조합하여 등록하셔야 합니다.
				specialChar : "<spring:message code='mypage.modify.031' />",
				//message : 위와 똑같은 비밀번호를 입력해 주십시오
				equalTo: "<spring:message code='mypage.modify.032' />"
			},
			
			phone : {
				//message : 전화번호를 입력해 주십시오.
				required : "<spring:message code='mypage.modify.033' />",
				//message : 숫자만 입력해 주십시오.
				number : "<spring:message code='mypage.modify.035' />"
			},
			
			email:{
				//message : 메일을 입력해 주십시오.
				required : "<spring:message code='mypage.modify.040' />",
				//message : 메일 형식이 적당하지 않습니다.
				email : "<spring:message code='mypage.modify.041' />"
			},
			
			//message : 기업명을 입력해 주십시오.
			companyName : "<spring:message code='mypage.modify.038' />",
			
			companyTel : {
				//message : 대표전화(회사전화번호)를 입력해주십시오.
				required : "<spring:message code='mypage.modify.039' />",
				//message : 숫자만 입력해 주십시오.
				number : "<spring:message code='mypage.modify.035' />"
			} 
		},
		 errorPlacement: function(error, element) {
			error.appendTo( element.parent("td") );
		}
	});

	$("#emailBtn").on("click", function(){
		var inputEmail = $("#email").val();

		if(inputEmail.length == 0){
			//message : 메일을 입력해 주십시오.
			alert("<spring:message code='mypage.modify.040' />");
		}
		else{
			if($("#email-error:visible").length){
				//message : 메일 형식이 적당하지 않습니다.
				alert("<spring:message code='mypage.modify.041' />");
			}
			else if(inputEmail === "<c:out value='${memberVO.email}'/>"){
				//message : 지금 사용하고 있는 메일입니다.
				alert("<spring:message code='mypage.modify.045' />");
			}else{
				$.ajax({
					url:"/member/emailValidation.html",
					type:"POST",
					data:{
						"inputEmail":inputEmail
					},
					success:function(result){
						switch(result){
							//message : 해당 메일을 사용할 수 있습니다.
							case 0 : alert("<spring:message code='mypage.modify.042' />");
								$("#emailValidFlag").prop("value",1);
								$("#userStatus").prop("value","5");
								break;
							//message : 해당 메일이 이미 존재합니다.
							case 1 : alert("<spring:message code='mypage.modify.043' />");
								break;
							//message : [심각] 해당 메일이 2개 이상 존재하거나, DB Error 발생
							case 2 : alert("<spring:message code='mypage.modify.044' />");
								break;
						}
					}
				});
			}
		}
	});

	$("#email").keyup(function(){
		if($("#email").val() == "<c:out value='${memberVO.email}'/>"){
			$("#emailValidFlag").prop("value", 1);
			$("#userStatus").prop("value","4");
		}else{
			if($("#email").val().length == 0 && $("#lastName").val() == "DEMO"){
				$("#userStatus").prop("value","4");
			}else{
				$("#emailValidFlag").prop("value", 0);
			}
		}
	});

	$("#mypage_modify_f").on("submit", function(event){ 
		if($("#emailValidFlag").val() == "0" && $("#isUseMail").val() == "true"){
			event.preventDefault();
			//message : 메일 중복 확인해 주십시오
			alert("<spring:message code='mypage.modify.024' />");
			$("#email").focus();
		}
		else if( $("#emailValidFlag").val() == "0" && $("#isUseMail").val() == "false" && $("#email").val().length > 0){
	    	event.preventDefault();
	    	//message : 메일 중복확인을 해주십시오.
	    	alert("<spring:message code='mypage.modify.024' />");
	    	$("#email").focus();
	    }else{
	    	if(!$(".error").text().length  && $("#preventSubmit").val() == 0 ){
				$("#preventSubmit").val("1");
//				20180528 : lsy - lsy - 그룹권한 추가(기존권한 삭제) > 기존 로직 변경
//				if( "<c:out value='${memberVO.email}'/>" != $("#email").val() && '${memberVO.userGb}' != '1' && $("#email").val().length > 0){
				if( "<c:out value='${memberVO.email}'/>" != $("#email").val() && $("#email").val().length > 0){
					//message : 메일 변경 후 인증을 거치지 않으면 재 로그인시, 로그인이 불가능합니다. 괜찮습니까?
					var result = confirm("<spring:message code='mypage.modify.020' />");
					if(result == true){
						$("#preventSubmit").val("1");	
					}else{
						event.preventDefault();
						$("#userStatus").prop("value","4");
						//value값 바꿀때는 val로 써야함 attr은 바뀌긴하는데 화면상에서는 안ㅇ바뀜 
						$("#email").val( "<c:out value='${memberVO.email}'/>");
						$("#preventSubmit").val("0");
					}
				}else{
					
				}
			}else{
				event.preventDefault();
			}
	    }
	}); 

	$("#myPageModifyBtn").click(function(e){
		if($("#preventSubmit").val() == 0){

			$("#mypage_modify_f").submit();
		}else{
			e.preventDefault();
		}
	});
	
	
	
	if("<c:out value='${modifySuccess}'/>" == "true"){
		//message : 수정되었습니다.
		alert("<spring:message code='mypage.modify.049' />");
	}
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
		
			<!-- mypage header -->
			<%@ include file="../inc/mypage_header.jsp" %>
			
			<!-- join_area -->
			<h2><spring:message code='mypage.modify.001' /></h2>
			<a href="#" id="withDrawalBtn" name="withDrawalBtn" class="btn btnM btnM_gray_light"><spring:message code='mypage.modify.002' /></a>
			<form action="/mypage/modify.html" method="post" name="mypage_modify_f" id="mypage_modify_f" class="mypage_modify_f">
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
							<th scope="row"><label class="title" for="userId"><em>*</em> <spring:message code='mypage.modify.003' /></label></th>
							<td colspan="3">
								<input id="userId" name="userId" type="text" value="${memberVO.userId }"style="width:95.7%;" readonly>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="userPw">&nbsp;&nbsp;&nbsp;<spring:message code='mypage.modify.004' /></label></th>
							<td colspan="3">
								<input id="userPw" name="userPw" type="password" style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="userPwConfirm">&nbsp;&nbsp;&nbsp;<spring:message code='mypage.modify.005' /></label></th>
							<td colspan="3">
								<input id="userPwConfirm" name="userPwConfirm" type="password" style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="lastName"><em>*</em> <spring:message code='mypage.modify.007' /></label></th>
							<td>
								<input id="lastName" name="lastName" type="text" value="${memberVO.lastName}" style="width:82%;">
							</td>
							<th scope="row"><label class="title" for="firstName"><em>*</em> <spring:message code='mypage.modify.008' /></label></th>
							<td>
								<input id="firstName" name="firstName" type="text" value="${memberVO.firstName}"style="width:87%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label id="test" class="title" for="email"><em>*</em> <spring:message code='mypage.modify.009' /></label></th>
							<td colspan="3">
								<input id="email" name="email" type="email" value="${memberVO.email}" style="width:79%;" class="line_right">
								<a href="#" id="emailBtn" class="btn btnL btn_gray_light line_left"><spring:message code='mypage.modify.010' /></a>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="phone"><em>*</em> <spring:message code='mypage.modify.011' /></label></th>
							<td colspan="3">
								<input id="phone" name="phone" type="text"   value="${memberVO.phone}"style="width:95.7%;">
							</td>
						</tr>
					</table> 
				</div>
			</div>

			<!-- 추가 입력 -->
			<c:if test="${memberVO.userGb eq 127 && memberVO.companyGb eq 1}">
			<div class="section second_section">
				<h3><spring:message code='mypage.modify.012' /></h3>
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:150px">
							<col style="">
						</colgroup>
						<tr>
							<th scope="row"><label class="title" for="companyName"><em>*</em> <spring:message code='mypage.modify.013' /></label></th>
							<td>
								<input id="companyName" name="companyName" type="text" value="${companyVO.companyName}" style="width:94.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="companyTel"><em>*</em> <spring:message code='mypage.modify.014' /></label></th>
							<td>
								<input id="companyTel" name="companyTel" type="text" value="${companyVO.companyTel}" style="width:94.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="zipcode"> <spring:message code='mypage.modify.015' /></label></th>
							<td colspan="3">
								<input id="zipcode" name="zipcode" type="text" value="${companyVO.zipcode}" style="width:94.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="addrFirst"> <spring:message code='mypage.modify.016' /></label></th>
							<td>
								<input id="addrFirst" name="addrFirst" type="text" value="${companyVO.addrFirst}" style="width:95%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="addrDetail"> <spring:message code='mypage.modify.017' /></label></th>
							<td>
								<input id="addrDetail" name="addrDetail" type="text" value="${companyVO.addrDetail}" style="width:95%;">
							</td>
						</tr>
					</table>
				</div>
			</div>
			</c:if>
			<!-- 추가 입력 -->

			<div class="btn_area_bottom tCenter">
				<a href="#" id="myPageModifyBtn" class="btn btnL btn_red"><spring:message code='mypage.modify.018' /></a>
				<input type="hidden" id="emailValidFlag" name="emailValidFlag" value="1">
				<input type="hidden" id="modify_gb" name="modify_gb" value="modify_page" >
				<input type="hidden" id="userStatus" name="userStatus" value="${memberVO.userStatus}"> 
				<input type="hidden" id="userSeq" name="userSeq" value="${memberVO.userSeq}">
				<input type="hidden" id="preventSubmit" name="preventSubmit" value="0"/>
				<input type="hidden" id="isUseMail" name="isUseMail" value="true"/>
			</div>
			</form>
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