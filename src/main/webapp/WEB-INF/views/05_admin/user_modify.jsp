<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>

<script type="text/javascript" src="/js/jquery.validate.js"></script>
<script>
$(document).ready(function(){
	$("#groupName").val($("#groupSelect option:selected").attr("data-id"));
	//DEMO입력시, 메일 없이 등록가능
	$("#lastName").keyup(function(){		
		if($(this).val() == "DEMO"){
			$(".rowtable").find("tr").eq(4).find("em").html("")
			$("#isUser").val("true");
		}else{
/* 			20180511 : lsy - group add -> radio condition temp delete	-	1
			if($("[name = userGb]:checked").val() == "1"){
				$("#isUser").val("true");
			}else{
				$(".rowtable").find("tr").eq(4).find("em").html("*")
				$("#isUser").val("false");
			}
*/
			$(".rowtable").find("tr").eq(4).find("em").html("*")
			$("#isUser").val("false");
//			20180511 : lsy - group add -> radio condition temp delete	-	1 end
		}
	});
	
	jQuery.validator.addMethod("specialChar", function(value, element){
		return this.optional( element ) || /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{0,16}$/.test(value);
	}, "wrong nic number");

	if("<c:out value='${modifySuccess}'/>" == "true"){
		//message : 수정되었습니다.
		alert("<spring:message code='user.modify.020' />");
	}
/*	20180511 : lsy - group add -> radio condition temp delete	-	2
	//해당 등록 정보가 사용자로 등록되었을경우
	if("${memberVO.userGb }" == "1"){
		$("#test").html("<em></em> <spring:message code='user.write.010' />");
		$("#useDate").show();
		$("#isUser").val("true");
		if("${memberVO.dateGb}" == "1"){
			$('#dateCheckGb').prop('checked', true);
			$("#dateGb").val('1');
			$("[name=userStartDt]").attr("readonly",false);
			$("[name=userEndDt]").attr("readonly",false);
			$("[name=userStartDt]").attr("disabled",false);
			$("[name=userEndDt]").attr("disabled",false);
		}else if("${memberVO.dateGb}" == "2"){
			$('#dateCheckGb').prop('checked', false);
			$("#dateGb").val('2');
			$("[name=userStartDt]").attr("readonly",true);
			$("[name=userEndDt]").attr("readonly",true);
			$("[name=userStartDt]").attr("disabled",true);
			$("[name=userEndDt]").attr("disabled",true);
		}
		
	}else{
		if($("#lastName").val() == "DEMO"){
			$("#test").html("<em></em> <spring:message code='user.write.010' />");
			$("#isUser").val("true");
		}else{
			$("#test").html("<em>*</em> <spring:message code='user.write.010' />");
			$("#isUser").val("false");
		}
		$("#useDate").hide();
	}
	20180511 : lsy - group add -> radio condition temp delete	-	2 end
*/
	//date value load
	if("${memberVO.dateGb}" == "1"){
		$('#dateCheckGb').prop('checked', true);
		$("#dateGb").val('1');
		$("[name=userStartDt]").attr("readonly",false);
		$("[name=userEndDt]").attr("readonly",false);
		$("[name=userStartDt]").attr("disabled",false);
		$("[name=userEndDt]").attr("disabled",false);
	}else if("${memberVO.dateGb}" == "2"){
		$('#dateCheckGb').prop('checked', false);
		$("#dateGb").val('2');
		$("[name=userStartDt]").attr("readonly",true);
		$("[name=userEndDt]").attr("readonly",true);
		$("[name=userStartDt]").attr("disabled",true);
		$("[name=userEndDt]").attr("disabled",true);
	}
	
	//email value load
	if($("#lastName").val() == "DEMO"){
		$("#test").html("<em></em> <spring:message code='user.write.010' />");
		$("#isUser").val("true");
	}else{
		$("#test").html("<em>*</em> <spring:message code='user.write.010' />");
		$("#isUser").val("false");
	}
	
	if("${memberVO.onedepartmentSeq}"  != ""){
		$("#onedepartmentSeq").val("${memberVO.onedepartmentSeq}");
		$("#categorySeq1").val("${memberVO.onedepartmentSeq}");
		$("#departmentValue").parent().append("<select id='departmentValue2' name='departmentValue2'></select>");
		var url = "/man/department/childList.html";
		var dataToBeSent = $("#frmCate").serialize();
		$.post(url, dataToBeSent, function(data, textStatus) {
			//data contains the JSON object
			//textStatus contains the status: success, error, etc
			//var JsonSize = data.length;
			$("#departmentValue2").append("<option value='0' selected='selected'> <spring:message code='extend.local.005' /></option>");
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					if(data[i].departmentSeq == "${memberVO.twodepartmentSeq}"){
						$("#departmentValue2").append("<option id='secondSelCateVal"+data[i].departmentSeq+"' selected='selected' alt='"+ data[i].departmentSeq+"' value='"+data[i].departmentSeq+"'>" + data[i].departmentName+"</option>");
					}else{
						$("#departmentValue2").append("<option id='secondSelCateVal"+data[i].departmentSeq+"' alt='"+ data[i].departmentSeq+"' value='"+data[i].departmentSeq+"'>" + data[i].departmentName+"</option>");
					}
				}
			}
		});
	}
	

	$("#user_modify_f").validate({
		
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
						if($("#isUser").val() == "false"){
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
			
			userStartDt :{
				required : true
			},
			userEndDt :{
				required : true
			}
	    },

	    messages: {
	    	//message : 성을 입력해 주십시오.
	    	firstName: "<spring:message code='user.modify.021' />",
	    	//message : 이름을 입력해 주십시오.
			lastName: "<spring:message code='user.modify.022' />",
			userPw: {
				//message : 8글자 이상 입력해주셔야 합니다.
				minlength : "<spring:message code='user.modify.026' />",
				//message : 영문, 숫자 및 특수기호를 조합하여 등록하셔야 합니다.
				specialChar : "<spring:message code='user.modify.027' />"
			},

			userPwConfirm: {
				//message : 8글자 이상 입력해주셔야 합니다.
				minlength : "<spring:message code='user.modify.026' />",
				//message : 영문, 숫자 및 특수기호를 조합하여 등록하셔야 합니다.
				specialChar : "<spring:message code='user.modify.027' />",
				//message : 위와 똑같은 비밀번호를 입력해 주십시오.
				equalTo: "<spring:message code='user.modify.028' />"
			},

			phone : {
				//message : 전화번호를 입력해 주십시오.
				required : "<spring:message code='user.modify.029' />",
				//message : 숫자만 입력해 주십시오.
				number : "<spring:message code='user.modify.031' />"
			},

			email:{
				//message : 이메일을 입력해 주십시오.
				required : "<spring:message code='user.modify.032' />",
				//message : 적당한 형식의 이메일을 입력해 주십시오.
				email : "<spring:message code='user.modify.033' />"
			},
			userStartDt :{
				required : ""
			},
			userEndDt:{
				required : ""
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
			alert("<spring:message code='user.modify.032' />");
		}else{
			if($("#email-error:visible").length){
				//message : 메일 형식이 적당하지 않습니다.
				alert("<spring:message code='user.modify.035' />");
			}else if(inputEmail === "<c:out value='${memberVO.email}'/>"){
				//message : 지금 사용하고 있는 메일입니다.
				alert("<spring:message code='user.modify.039' />");
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
							case 0 : alert("<spring:message code='user.modify.036' />");
								$("#emailValidFlag").prop("value",1);
								$("#userStatus").prop("value","5");
								break;
							//message : 해당 메일이 이미 존재합니다.
							case 1 : alert("<spring:message code='user.modify.037' />");
								break;
							//message : [심각] 해당 메일이 2개 이상 존재하거나, DB Error 발생
							case 2 : alert("<spring:message code='user.modify.038' />");
								break;
						}
					}
				});
			}
		}
	});

	$("#user_modify_f").on("submit", function(event){ 
		if($(".error").text().length){
			event.preventDefault();
			$("#preventSubmit").val("0");
			//message : 필수 사항을 입력해 주십시오.
	    	alert("<spring:message code='user.modify.043' />");
	    }else if( $("#emailValidFlag").val() === "0"){
	    	event.preventDefault();
	    	$("#preventSubmit").val("0");
	    	//message : 메일 중복확인을 해주십시오.
	    	alert("<spring:message code='user.modify.044' />");
	    }else{
			if( "<c:out value='${memberVO.email}'/>" !== $("#email").val() ){
				//message : 메일 변경 후 인증을 거치지 않으면 재 로그인시, 로그인이 불가능합니다. 괜찮습니까?
				var result = confirm("<spring:message code='user.modify.040' />");
				if(result == true){
				}else{
					event.preventDefault();
					$("#userStatus").prop("value","4");
					$("#preventSubmit").val("0");
					//value값 바꿀때는 val로 써야함 attr은 바뀌긴하는데 화면상에서는 안ㅇ바뀜 
					$("#email").val( "<c:out value='${memberVO.email}'/>");
				}
			}else{
				if(!$(".error").text().length ){
				}else{
					event.preventDefault();
					$("#preventSubmit").val("0");
				}
			}
	    }
	});

	$("#email").keyup(function(){
		if($("email").val() === "<c:out value='${memberVO.email}'/>"){
			$("#emailValidFlag").prop("value",1);
			$("#userStatus").prop("value","4");
		//}else if( $("email").val() != "<c:out value='${memberVO.email}'/>" && $("[name=userGb]:checked").val() != "1"){
		}else{
			$("#emailValidFlag").prop("value",0);
			$("#userStatus").prop("value","5");
		}
	});

	$("#modifyBtn").click(function(e){
		if($("#preventSubmit").val() == 0){
			$("#preventSubmit").val("1");
			$("#user_modify_f").submit();
		}else{
			e.preventDefault();
			$("#preventSubmit").val("0");
		}
	});
	
/*	20180511 : lsy - group add -> radio condition temp delete	-	3
	$("[name=userGb]").change(function(){
		if($(this).val() == '1'){
			$("#test").html("<em></em> <spring:message code='user.write.010' />");
			$("#emailValidFlag").prop("value",1);
			$("#useDate").show();
			$("#isUser").val("true");
		}else{
			if($("#lastName").val() == "DEMO"){
				$(".rowtable").find("tr").eq(4).find("em").html("")
				$("#isUser").val("true");
			}else{
				if($("[name = userGb]:checked").val() == "1"){
				}else{
					$(".rowtable").find("tr").eq(4).find("em").html("*")
					$("#isUser").val("false");
				}
			}
			$("#emailValidFlag").prop("value",1);
			$("#useDate").hide();
		}
	});
*/
	$("[name=groupSelect]").change(function(){
		$("#groupName").val($("#groupSelect option:selected").attr("data-id"));
	});
//	20180511 : lsy - group add -> radio condition temp delete	-	3

	$("#departmentValue").change(function(){
		if($("#departmentValue").val() == '0'){
			$("#departmentValue2").remove();
		}else{
			
			var thisSeq = $(this).val();
			var departmentSeq =$("#selCateVal"+thisSeq).attr("alt");
			$("#departmentValue2").remove();
			
			$("#categorySeq1").val(departmentSeq);
			$("#onedepartmentSeq").val(departmentSeq);
			$(this).parent().append("<select id='departmentValue2' name='departmentValue2'></select>");
			//<option value='0' selected='selected'> 없음</option>
			var url = "/man/department/childList.html";
			var dataToBeSent = $("#frmCate").serialize();
			$.post(url, dataToBeSent, function(data, textStatus) {
				//data contains the JSON object
				//textStatus contains the status: success, error, etc
				//var JsonSize = data.length;
				$("#departmentValue2").append("<option value='0' selected='selected'> <spring:message code='extend.local.005' /></option>");
				if(data.length > 0){
					for(var i=0;i<data.length;i++){
						$("#departmentValue2").append("<option id='secondSelCateVal"+data[i].departmentSeq+"' alt='"+ data[i].departmentSeq+"' value='"+data[i].departmentSeq+"'>" + data[i].departmentName+"</option>");
						/*
							<option alt="${DepartmentList[i].departmentSeq }" value="${i+1 }"> ${DepartmentList[i].departmentName }</option>
						*/
					}
				}
			});
		}
	});
	
	$(document).on('change', "#departmentValue2", function(){
		var thisSeq = $("#departmentValue2").val();
		var departmentSeq = $("#secondSelCateVal"+thisSeq).attr("alt");
		$("#twodepartmentSeq").val(departmentSeq);
	});

	$("#dateCheckGb").change(function(){
		if($(this).is(':checked')){
			$('#dateCheckGb').prop('checked', true);
			$("#dateGb").val('1');
			$("[name=userEndDt]").val($("[name=tempEndDt]").val());
			$("[name=userStartDt]").val($("[name=tempStartDt]").val());
			$("[name=userStartDt]").attr("readonly",false);
			$("[name=userEndDt]").attr("readonly",false);
			$("[name=userStartDt]").attr("disabled",false);
			$("[name=userEndDt]").attr("disabled",false);
		}else{
			$('#dateCheckGb').prop('checked', false);
			$("#dateGb").val('2');
			$("[name=tempStartDt]").val($("[name=userStartDt]").val());
			$("[name=tempEndDt]").val($("[name=userEndDt]").val());
			$("[name=userStartDt]").val('');
			$("[name=userEndDt]").val('');
			$("[name=userStartDt]").attr("readonly",true);
			$("[name=userEndDt]").attr("readonly",true);
			$("[name=userStartDt]").attr("disabled",true);
			$("[name=userEndDt]").attr("disabled",true);
		}
	});

	$("#d_EDATE").mousedown(function(e){
		if($("#d_SDATE").val() == null || $("#d_SDATE").val() == ""){
			//message : 시작 날짜를 입력해주십시오.
			alert("<spring:message code='contents.modify.039' />");
			e.preventDefault();
		} 		
	});
	
	$("#d_EDATE").change(function(){
		$("[name=tempEndDt]").val($(this).val());
		
		setTimeout(function(){ $("[name=userStartDt").val($("[name=tempStartDt]").val()) }, 100);
	});
	
	$("#d_SDATE").change( function(){

		$("[name=tempStartDt]").val($(this).val());
		//현재 시간
		var d = new Date();
		var curr_year = d.getFullYear();
		var curr_month = d.getMonth();
		var curr_date = d.getDate();
		var curr_hour = d.getHours();
		var curr_min = d.getMinutes();

		//HTML에서 가져온 날자 값
		var inputDate = $("#d_SDATE").val();
		var split = inputDate.split("-");

		//입력된 시간
		var inputYear = split[0];
		var inputmonth = split[1];
		var inputdate = split[2];		
		
		curr_month += 1
		
		//월 Reformat
		if(curr_month < 10){
			curr_month = "0"+curr_month;
		}
		
		//Date Reformat
		if(curr_date < 10){
			curr_date = "0"+curr_date
		}

		/* if( curr_year < inputYear ){
		}else if( curr_year == inputYear ){
			if( curr_month < inputmonth ){
				if( curr_date <= inputdate ){
					
				}
			}else if( curr_month == inputmonth ){
				if( curr_date <= inputdate ){
					
				}else{
					var inputValue = curr_year +"-"+(curr_month)+"-"+curr_date;
					// alert(inputValue); 
					$("#d_SDATE").val(inputValue);
					$("[name=tempStartDt]").val(inputValue);
					//message : 현재보다 날자가 같거나 나중이어야 합니다.
					alert("<spring:message code='contents.modify.040' />");
				}
			}else{
				var inputValue = curr_year +"-"+(curr_month)+"-"+curr_date;
				// alert(inputValue); 
				$("#d_SDATE").val(inputValue);
				$("[name=tempStartDt]").val(inputValue);
				//message : 현재보다 날자가 같거나 나중이어야 합니다.
				alert("<spring:message code='contents.modify.040' />");
			}
		}else{
			var inputValue = curr_year +"-"+(curr_month)+"-"+curr_date;
			// alert(inputValue); 
			$("#d_SDATE").val(inputValue);
			$("[name=tempStartDt]").val(inputValue);
			//message : 현재보다 날자가 같거나 나중이어야 합니다.
			alert("<spring:message code='contents.modify.040' />");
		} */
	});
	
});


function cancelMethod(){
	//message : 지금까지 입력한 자료가 사라집니다. 취소하시겠습니까?
	if(confirm("<spring:message code='user.write.046' />")){
		window.location.href="/man/user/list.html?page=<c:out value='${param.page}'/>&searchType=<c:out value='${param.searchType}'/>&searchValue=<c:out value='${param.searchValue}'/>&isAvailable=<c:out value='${param.isAvailable}'/>";
	}
}

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
			<c:set var="curi" value="${requestScope['javax.servlet.forward.request_uri']}" />
			<!-- man header -->
			<form method="post" name="frmCate" id="frmCate" action="return ture;">
				<input type="hidden" name="categorySeq1" id="categorySeq1" value="" />		
				<input type="hidden" name="toUse" id="toUse" value="true"/>
			</form>			
			
			<div class="tab_area">
				<ul>
					<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_COMPANY_MEMBER')">
					<!--messsage : 사용자  -->
						<li <c:if test="${fn:containsIgnoreCase(curi, '/user/')}">class="current last"</c:if>><a href="/man/user/list.html?page=1&searchType=&searchValue=&isAvailable=false"><spring:message code='man.header.001' /></a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
					<!--message : 회원  -->
						<li <c:if test="${fn:containsIgnoreCase(curi, '/user/')}">class="current last"</c:if>><a href="/man/user/list.html?page=1&searchType=&searchValue=&isAvailable=false"><spring:message code='man.header.002' /></a></li>
					</sec:authorize>
				</ul>
			</div>
			<!-- //man header -->
			<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_COMPANY_MEMBER')">
				<h2><spring:message code='user.modify.002' /></h2>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
				<h2><spring:message code='user.modify.018' /></h2>
			</sec:authorize>
			<form action="/man/user/modify.html?userSeq=${memberVO.userSeq}&page=${param.page}&searchType=${param.searchType}&searchValue=${param.searchValue}&isAvailable=${param.isAvailable}" method="POST" id="user_modify_f">
			<div class="section fisrt_section">
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:120px">
							<col style="width:210px">
							<col style="width:120px">
							<col style="">
						</colgroup>
						<tr>
							<th scope="row"><label class="title" for="userId"><em>*</em> <spring:message code='user.modify.003' /></label></th>
							<td colspan="3">
								<input id="userId" name="userId" type="text" readonly value="${memberVO.userId }" style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="userPw"><em>*</em> <spring:message code='user.modify.004' /></label></th>
							<td colspan="3">
								<input id="userPw" name="userPw" type="password" style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="userPwConfirm"><em>*</em> <spring:message code='user.modify.005' /></label></th>
							<td colspan="3">
								<input id="userPwConfirm" name="userPwConfirm" type="password" style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="lastName"><em>*</em> <spring:message code='user.modify.006' /></label></th>
							<td>
								<input id="lastName" name="lastName" type="text" value="${memberVO.lastName }" style="width:82%;">
							</td>
							<th scope="row"><label class="title" for="firstName"><em>*</em> <spring:message code='user.modify.007' /></label></th>
							<td>
								<input id="firstName" name="firstName" type="text" value="${memberVO.firstName }" style="width:87%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label id="test" class="title" for="email"><em>*</em> <spring:message code='user.modify.008' /></label></th>
							<td colspan="3">
								<input id="email" name="email" type="email" value="${memberVO.email}" style="width:79%;" class="line_right">
								<a href="#emailBtn" id="emailBtn" class="btn btnL btn_gray_light line_left"><spring:message code='user.modify.019' /></a>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="phone"><em>*</em> <spring:message code='user.modify.009' /></label></th>
							<td colspan="3">
								<input id="phone" name="phone" type="text" value="${memberVO.phone}" style="width:95.7%;">
							</td>
						</tr>
						<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_COMPANY_MEMBER')">
						<tr>
							<th scope="row"><label class="title" for="phone"> <spring:message code='extend.local.004' /></label></th>
							<td colspan="3">
								<select id="departmentValue" name="departmentValue">
									<!-- 
										<option value="" <c:if test="${empty appList.searchType }">selected="selected"</c:if>><spring:message code='app.list.table.search2' /></option>
									 -->
									<option value="0" selected="selected"> <spring:message code='extend.local.005' /></option>
									<c:choose>
										<c:when test="${empty DepartmentList}">
										</c:when>
										<c:otherwise>
											<c:forEach var="i" begin="0" end="${DepartmentList.size()-1}">
												<c:if test="${DepartmentList[i].departmentSeq == memberVO.onedepartmentSeq }"><option id="selCateVal${DepartmentList[i].departmentSeq}" selected="selected" alt="${DepartmentList[i].departmentSeq }" value="${DepartmentList[i].departmentSeq }"> ${DepartmentList[i].departmentName }</option></c:if>
												<c:if test="${DepartmentList[i].departmentSeq != memberVO.onedepartmentSeq }"><option id="selCateVal${DepartmentList[i].departmentSeq}" alt="${DepartmentList[i].departmentSeq }" value="${DepartmentList[i].departmentSeq}"> ${DepartmentList[i].departmentName }</option></c:if>
											</c:forEach>
										</c:otherwise>
									</c:choose>
									<!-- <option value="ostype" <c:if test="${'ostype' eq appList.searchType }">selected="selected"</c:if>>운영체제</option> -->
								</select>
							</td>
						</tr>
						
						<tr>
							<th scope="row"><label class="title"><em>*</em> <spring:message code='user.modify.010' /></label></th>
							<td colspan="3">
								<select id="groupSelect" name="groupSelect">
									<c:if test="${groupList ne null}">
										<c:forEach var="i" begin="0" end="${groupList.size()-1}">
											<c:choose>
												<c:when test="${groupList[i].groupSeq == memberVO.groupName}">
													<option value="${groupList[i].groupName }" data-id="${groupList[i].groupSeq}" selected="selected">
														${groupList[i].groupName }
													</option>
												</c:when>
												<c:otherwise>
													<option value="${groupList[i].groupName }" data-id="${groupList[i].groupSeq}">
														${groupList[i].groupName }
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:if>
								</select>
							</td>
						</tr>
<%-- 
						<tr>
							<th scope="row"><label class="title"><em>*</em> <spring:message code='user.modify.010' /></label></th>
							<td colspan="3">
								<div class="radio_area radio_area_type2">
									<c:choose>
										<c:when test = "${memberVO.userGb == '1' }">
											<input name="userGb" id="permissionDist" type="radio" value="29"> <label for="permissionDist"> <spring:message code='extend.local.006' /></label>
											<input name="userGb" id="permissionDist" type="radio" value="21"> <label for="permissionDist"><spring:message code='user.modify.012' /></label>
											<input name="userGb" id="permissionCrea" type="radio" value="5"> <label for="permissionCrea"><spring:message code='user.modify.013' /></label>
											<input name="userGb" id="permissionUser" type="radio" checked="checked" value="1"> <label for="permissionUser"><spring:message code='user.modify.011' /></label>									</c:when>
										<c:when test = "${memberVO.userGb == '21' }">
											<input name="userGb" id="permissionDist" type="radio" value="29"> <label for="permissionDist"> <spring:message code='extend.local.006' /></label>
											<input name="userGb" id="permissionDist" type="radio" checked="checked" value="21"> <label for="permissionDist"><spring:message code='user.modify.012' /></label>
											<input name="userGb" id="permissionCrea" type="radio" value="5"> <label for="permissionCrea"><spring:message code='user.modify.013' /></label>
											<input name="userGb" id="permissionUser" type="radio" value="1"> <label for="permissionUser"><spring:message code='user.modify.011' /></label>
										</c:when>
										<c:when test = "${memberVO.userGb == '5' }">
											<input name="userGb" id="permissionDist" type="radio" value="29"> <label for="permissionDist"> <spring:message code='extend.local.006' /></label>
											<input name="userGb" id="permissionDist" type="radio" value="21"> <label for="permissionDist"><spring:message code='user.modify.012' /></label>
											<input name="userGb" id="permissionCrea" type="radio" checked="checked" value="5"> <label for="permissionCrea"><spring:message code='user.modify.013' /></label>
											<input name="userGb" id="permissionUser" type="radio" value="1"> <label for="permissionUser"><spring:message code='user.modify.011' /></label>
										</c:when>
										<c:when test = "${memberVO.userGb == '29' }">
											<input name="userGb" id="permissionDist" type="radio" value="29" checked="checked" > <label for="permissionDist"> <spring:message code='extend.local.006' /></label>
											<input name="userGb" id="permissionDist" type="radio" value="21"> <label for="permissionDist"><spring:message code='user.modify.012' /></label>
											<input name="userGb" id="permissionCrea" type="radio" value="5"> <label for="permissionCrea"><spring:message code='user.modify.013' /></label>
											<input name="userGb" id="permissionUser" type="radio" value="1"> <label for="permissionUser"><spring:message code='user.modify.011' /></label>
										</c:when>
									</c:choose>
								</div>
							</td>
						</tr>
 --%>
						</sec:authorize>
						<tr id = "useDate">
							<th scope="row"><span class="title"> <spring:message code='extend.local.007' /></span></th>
							<td colspan="3">
								<input id="dateCheckGb" type="checkbox" /> <spring:message code='extend.local.008' />&nbsp;&nbsp;
								<input id="d_SDATE" style="padding-left:5px;" readonly="readonly" disabled="disabled" name="userStartDt" type="text" title="start date" class="date fmDate1" value="<c:if test="${ '1' eq memberVO.dateGb}"><fmt:formatDate value="${memberVO.userStartDt}" pattern="yyyy-MM-dd"/></c:if>" />
										&nbsp;&nbsp;~&nbsp;&nbsp;
								<input id="d_EDATE" name="userEndDt" type="text" title="end date" readonly="readonly" disabled="disabled"  class="date toDate1" value="<c:if test="${ '1' eq memberVO.dateGb}"><fmt:formatDate value="${memberVO.userEndDt}" pattern="yyyy-MM-dd"/></c:if>" />
							</td>
						</tr>
					</table>
				</div>

				<div class="btn_area_bottom tCenter">
					<a href="#modifyBtn" id="modifyBtn" class="btn btnL btn_red"><spring:message code='user.modify.016' /></a>
					<a href="javascript:cancelMethod();" class="btn btnL btn_gray_light"><spring:message code='user.modify.017' /></a>
					<input type="hidden" name="emailValidFlag"  id="emailValidFlag" value="1"/>
					<input type="hidden" id="userStatus" 		name="userStatus" value="${memberVO.userStatus}">
					<input type="hidden" id="preventSubmit" 	name="preventSubmit" value="0"/>
					<input type="hidden" id="isUser" 			name="isUser" value="false"/>
					<input type="hidden" id="onedepartmentSeq"  name="onedepartmentSeq" value=""/>
					<input type="hidden" id="twodepartmentSeq"  name="twodepartmentSeq" value=""/>
					<input type="hidden" id="dateGb" 			name="dateGb" value=""/>
					<input type="hidden" id="tempStartDt"       name="tempStartDt" value=""/>
					<input type="hidden" id="tempEndDt"         name="tempEndDt" value=""/>
					<input type="hidden" name="groupName" id="groupName" value=""/>
				</div>
			</div>
			</form>
			<!-- //사용자 수정 -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>