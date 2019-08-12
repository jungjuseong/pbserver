<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../inc/top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		return this.optional( element ) || /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=_-])(?=.*[0-9]).{0,16}$/.test(value);
	}, "wrong nic number"); 
	
	$("#user_write_f").validate({
	    rules: {
	    	firstName: "required",
	    	lastName: "required",
	    	userId: {
	    		required: true,
	    		minlength : 8
	    	},
	    	userPw:{
	    		required: true,
	    		minlength : 8,
	    		specialChar : true
	    	},

			userPwConfirm : {
				required: true,
	    		minlength : 8,
	    		specialChar : true,
				equalTo: "#userPw",
			},
			phone : {
				required : true,
				number : true
			},	
	    	email: {
				required: { 
					depends :function () {  
						if($("#isUser").val() == "false"){
							return true;
						}else{
							return false;
						}
					} 
				},
				email: true
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
	    	firstName: "<spring:message code='user.write.030' />",
	    	//message : 이름을 입력해 주십시오.
			lastName: "<spring:message code='user.write.031' />",
			userId: {
				//message : 아이디를 입력해 주십시오.
				required: "<spring:message code='user.write.032' />",
				//message : 8글자 이상 입력해주셔야 합니다.
				minlength: "<spring:message code='user.write.035' />"
			},
			userPw: {
				//message : 패스워드를 입력해 주십시오.
				required: "<spring:message code='user.write.034' />",
				//message : 8글자 이상 입력해주셔야 합니다.
				minlength : "<spring:message code='user.write.035' />",
				//message : 영문, 숫자 및 특수기호를 조합하여 등록하셔야 합니다.
				specialChar : "<spring:message code='user.write.036' />"
			},
			
			userPwConfirm: {
				//message : 패스워드를 입력해 주십시오.
				required: "<spring:message code='user.write.034' />",
				//message : 8글자 이상 입력해주셔야 합니다.
				minlength : "<spring:message code='user.write.035' />",
				//message : 영문, 숫자 및 특수기호를 조합하여 등록하셔야 합니다.
				specialChar : "<spring:message code='user.write.036' />",
				//message : 위와 똑같은 비밀번호를 입력해 주십시오.
				equalTo: "<spring:message code='user.write.037' />"
			},
			phone : {
				//message : 전화번호를 입력해 주십시오.
				required : "<spring:message code='user.write.038' />",
				//message : 숫자만 입력해 주십시오.
				number : "<spring:message code='user.write.039' />"
			},
			email:{
				//message : 메일을 입력해 주십시오.
				required : "<spring:message code='user.write.040' />",
				//message : 적당한 형식의 메일을 입력해 주십시오.
				email : "<spring:message code='user.write.041' />"
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
			alert("<spring:message code='user.write.020' />")
		}else{
			if($("#email-error:visible").length){
				//message : 메일 형식이 적당하지 않습니다.
				alert("<spring:message code='user.write.021' />")
			}
			else{
				$.ajax({
					url:"/member/emailValidation.html",
					type:"POST",
					data:{
						"inputEmail":inputEmail
					},
					success:function(result){
						switch(result){
							//message : 해당 메일을 사용할 수 있습니다.
							case 0 : alert("<spring:message code='user.write.022' />");
								$("#emailValidFlag").prop("value",1);
								break;
							//message : 해당 메일이 이미 존재합니다.
							case 1 : alert("<spring:message code='user.write.023' />");
								break;
							//message : [심각] 해당 메일이 2개 이상 존재하거나, DB Error 발생
							case 2 : alert("<spring:message code='user.write.024' />");
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
			//message : 아이디를 입력해 주십시오.
			alert("<spring:message code='user.write.025' />")
		}else{
			if($("#userId-error:visible").length){
				//message : 아이디형식이 적당하지 않습니다.
				alert("<spring:message code='user.write.026' />")
			}else{
				$.ajax({
					url:"/member/userIdValidation.html",
					type:"POST",
					data:{
						"inputUserId":inputUserId
					},
					success:function(result){
						switch(result){
							//message : 해당 아이디를 사용할 수 있습니다.
							case 0 : alert("<spring:message code='user.write.027' />");
								$("#idValidFlag").prop("value",1);
								break;
							//message : 해당 아이디가 이미 존재 합니다.
							case 1 : alert("<spring:message code='user.write.028' />");
								break;
							//message : [심각] 해당 아이디가 2개 이상 존재하거나, DB Error 발생
							case 2 : alert("<spring:message code='user.write.029' />");
								break;
						}
					}
				});
			}
		}
	});

	$("#user_write_f").on("submit", function(event){
		
		if($("#idValidFlag").val() == "0"){
			event.preventDefault();
			//message : 아이디 중복 확인해 주십시오.
			alert("<spring:message code='user.write.043' />");
			$("#userId").focus();
		}else if($("#emailValidFlag").val() == "0" && $("#isUser").val()=="false"){
			event.preventDefault();
			//message : 메일 중복 확인해 주십시오.
			alert("<spring:message code='user.write.044' />");
			$("#email").focus();
		}else if($("#emailValidFlag").val() == "0" && $("#isUser").val()=="true" && $("#email").val().length ){
			event.preventDefault();
			//message : 메일 중복 확인해 주십시오.
			alert("<spring:message code='user.write.044' />");
			$("#email").focus();
		}else{
			if(!$(".error").text().length && $("#preventSubmit").val() == 0){
				if($("#email").val().length){
					//message : [메일 인증 발송] 메일 인증 후 로그인이 가능합니다.
					alert("<spring:message code='user.write.042' />");
					$("#preventSubmit").val("1");
				}else if(!($("#dateCheckGb").is(":checked")) || ($("#dateCheckGb").is(":checked") && $("#d_SDATE").val().length && $("#d_EDATE").val().length)){
					//사용자로 메일 입력없이 회원가입했을대,
					$("#userStatus").val("4");
					$("#preventSubmit").val("1");
					
					$.ajax({
			            url: "/userLimitIsOver.html" ,
			            type: "POST" ,
			            async : false ,
			            data:{
			            	companySeq : '${currentCompanySeq}'
			                           },
			            success: function (result){
			               switch (result){
			                      case 0 : alert("<spring:message code='extend.local.088' />" );
			                      	$("#preventSubmit").val("0");
			                     	 event.preventDefault();
			                     				                     	
			                      break ;
			                    case 1 : 	alert("<spring:message code='extend.local.003' />");
			                    	$("#preventSubmit").val("1");
			                      break ;
			                     }
			            }
			        });
				}				
			}else{
				event.preventDefault();
			}
		}
	});

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
						var j=i+1;
						$("#departmentValue2").append("<option id='secondSelCateVal"+data[i].departmentSeq+"' alt='"+ data[i].departmentSeq+"' value='"+data[i].departmentSeq+"'>" + data[i].departmentName+"</option>");
						/*
							<option alt="${DepartmentList[i].departmentSeq }" value="${i+1 }"> ${DepartmentList[i].departmentName }</option>
						*/
					}
				}
			});
		}
	});

	$("#dateCheckGb").change(function(){
		if($(this).is(':checked')){
			$('#dateCheckGb').prop('checked', true);
			$("#dateGb").val('1');
			$("[name=userStartDt]").attr("readonly",false);
			$("[name=userEndDt]").attr("readonly",false);
			$("[name=userStartDt]").attr("disabled",false);
			$("[name=userEndDt]").attr("disabled",false);
		}else{
			$('#dateCheckGb').prop('checked', false);
			$("#dateGb").val('2');
			$("[name=userStartDt]").val('');
			$("[name=userEndDt]").val('');
			$("[name=userStartDt]").attr("readonly",true);
			$("[name=userEndDt]").attr("readonly",true);
			$("[name=userStartDt]").attr("disabled",true);
			$("[name=userEndDt]").attr("disabled",true);
		}
	});
	
	$("[name=groupSelect]").change(function(){
/* 		20180511 : lsy - group add -> radio condition temp delete	-	2
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
 */		
		$("#groupName").val($("#groupSelect option:selected").attr("data-id"));
	});	
//20180511 : lsy - group add -> radio condition temp delete	-	2 end	
	$(document).on('change', "#departmentValue2", function(){
		var thisSeq = $("#departmentValue2").val();
		var departmentSeq = $("#secondSelCateVal"+thisSeq).attr("alt");
		$("#twodepartmentSeq").val(departmentSeq);
	});
	
	$("#d_EDATE").mousedown(function(e){
		if($("#d_SDATE").val() == null || $("#d_SDATE").val() == ""){
			//message : 시작 날짜를 입력해주십시오.
			alert("<spring:message code='contents.modify.039' />");
			e.preventDefault();
		} 		
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

function cancelResist(){
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
		<c:set var="curi" value="${requestScope['javax.servlet.forward.request_uri']}" />
			<!-- man header -->
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
			<form method="post" name="frmCate" id="frmCate" action="return ture;">
				<input type="hidden" name="categorySeq1" id="categorySeq1"  value="" />		
			</form>
			<h2><spring:message code='user.write.001' /></h2>
			<form action="write.html?page=${param.page}&searchType=${param.searchType}&searchValue=${param.searchValue}&isAvailable=${param.isAvailable}" method="post" name="user_write_f" id="user_write_f" class="user_write_f">
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
							<th scope="row"><label class="title" for="userId"><em>*</em> <spring:message code='user.write.002' /></label></th>
							<td colspan="3">
								<input id="userId" name="userId" type="text" style="width:80%;" class="line_right">
								<a href="#" id="userIdBtn" class="btn btnL btn_gray_light line_left"><spring:message code='user.write.003' /></a>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="userPw"><em>*</em> <spring:message code='user.write.004' /></label></th>
							<td colspan="3">
								<input id="userPw" name="userPw" type="password" style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="userPwConfirm"><em>*</em> <spring:message code='user.write.007' /></label></th>
							<td colspan="3">
								<input id="userPwConfirm" name="userPwConfirm" type="password" style="width:95.7%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="lastName"><em>*</em> <spring:message code='user.write.008' /></label></th>
							<td>
								<input id="lastName" name="lastName" type="text" style="width:87%;">
							</td>
							<th scope="row"><label class="title" for="firstName"><em>*</em> <spring:message code='user.write.009' /></label></th>
							<td>
								<input id="firstName" name="firstName" type="text" style="width:87%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label id="test" class="title" for="email"><em>*</em> <spring:message code='user.write.010' /></label></th>
							<td colspan="3">
								<input id="email" name="email" type="email" value="" style="width:79%;" class="line_right">
								<a href="#" id="emailBtn" class="btn btnL btn_gray_light line_left"><spring:message code='user.write.003' /></a>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="phone"><em>*</em> <spring:message code='user.write.011' /></label></th>
							<td colspan="3">
								<input id="phone" name="phone" type="text" style="width:95.7%;">
							</td>
						</tr>
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
												<c:if test="${DepartmentList[i].departmentSeq == memberVO.onedepartmentSeq }"><option id="selCateVal${DepartmentList[i].departmentSeq}" selected="selected" alt="${DepartmentList[i].departmentSeq }" value="${i+1 }"> ${DepartmentList[i].departmentName }</option></c:if>
												<c:if test="${DepartmentList[i].departmentSeq != memberVO.onedepartmentSeq }"><option id="selCateVal${DepartmentList[i].departmentSeq}" alt="${DepartmentList[i].departmentSeq }" value="${i+1 }"> ${DepartmentList[i].departmentName }</option></c:if>
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
												<c:when test="i==0">
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
							<th scope="row"><span class="title"><em>*</em> <spring:message code='user.write.012' /></span></th>
							<td colspan="3">
								<div class="radio_area radio_area_type2">
									<input name="userGb" id="userGb" type="radio" value="29" checked="checked"> <label for="permissionDist"> <spring:message code='extend.local.006' /></label>
									<input name="userGb" id="userGb" type="radio" value="21"> <label for="permission1"><spring:message code='user.write.014' /></label>
									<input name="userGb" id="userGb" type="radio" value="5"> <label for="permission2"><spring:message code='user.write.015' /></label>
									<input name="userGb" id="userGb" type="radio" value="1"> <label for="permission3"><spring:message code='user.write.013' /></label>
								</div>
							</td>
						</tr>
 --%>						
						<tr id="useDate">
							<th scope="row"><span class="title"> <spring:message code='extend.local.007' /></span></th>
							<td colspan="3">
								<input id="dateCheckGb" type="checkbox" /> <spring:message code='extend.local.008' />&nbsp;&nbsp;
								<input id="d_SDATE" style="padding-left:5px;" name="userStartDt" readonly="readonly" disabled="disabled"type="text" title="start date" class="date fmDate1" value="<fmt:formatDate value="${memberVO.userStartDt}" pattern="yyyy-MM-dd"/>" />
										&nbsp;&nbsp;~&nbsp;&nbsp;
								<input id="d_EDATE" name="userEndDt" type="text" title="end date" readonly="readonly" disabled="disabled" class="date toDate1" value="<fmt:formatDate value="${memberVO.userEndDt}" pattern="yyyy-MM-dd"/>" />
							</td>
						</tr>
					</table>
				</div>

				<div class="btn_area_bottom tCenter">
					<input type="submit" id="registBtn" class="btn btnL btn_red" value="<spring:message code='user.write.016' />"/>
					<a href="javascript:cancelResist();" class="btn btnL btn_gray_light"><spring:message code='user.write.017' /></a>
					<input type="hidden" name="companySeq" id="companySeq" value="${currentCompanySeq}"/>
					<input type="hidden" name="companyGb" id="companyGb" value="1"/>
					<input type="hidden" name="userStatus" id="userStatus" value="5"/>
					<input type="hidden" name="emailValidFlag" id="emailValidFlag" value="0"/>
					<input type="hidden" name="idValidFlag" id="idValidFlag" value="0"/>
					<input type="hidden" id="preventSubmit" name="preventSubmit" value="0"/>
					<input type="hidden" id="isUser" name="isUser" value="false"/>
					<input type="hidden" id="onedepartmentSeq" name="onedepartmentSeq" value=""/>
					<input type="hidden" id="twodepartmentSeq" name="twodepartmentSeq" value=""/>
					<input type="hidden" id="dateGb"           name="dateGb"           value="2"/>
					
					<!-- 20180511 : lsy - group add -->
					<input type="hidden" name="userGb" id="userGb" value="63"/>
					<input type="hidden" name="groupName" id="groupName" value=""/>
				</div>
			</div>
			</form>
			<!-- //사용자 등록 -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>