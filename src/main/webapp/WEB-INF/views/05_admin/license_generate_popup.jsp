<%@page import="com.clbee.pbcms.vo.InappVO"%>
<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.clbee.pbcms.vo.InAppList"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>

<script type="text/javascript">
function licenseGenerate(){	
	if($("#userCopyCount").val() == ""){
		alert("<spring:message code='license.generate.005' />");
		$("#userCopyCount").focus();
		return false;
	}else{
		var formData = $("#licenseForm").serialize();
		$.ajax({
			cache : false,
			url : "/man/license/generateLicense.html",
			type : "POST",
			data : formData,
			success : function(result){
				switch(result){
					case 1 : 
						alert("<spring:message code='license.generate.006' />");
						opener.parent.location.reload();
						window.close();
						break;
					case 0 : 
						alert("<spring:message code='license.generate.007' />");
						break;
				}
			},
			error : function(){
				alert("<spring:message code='license.generate.007' />");
			}
		});
	}
}

function onlyNumber(event){
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ){
        return;
    }else{
        return false;
    }
}
 
function removeChar(event) {
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ){
        return;
    }else{
    	event.target.value = event.target.value.replace(/[^0-9]/g, "");
    }   
}
</script>
</head>
<body>
	<!-- wrap -->
	<div class="pop_wrap" style="width: 500px">
	
		<!-- conteiner -->
		<div id="container">
			<form id="licenseForm" name="licenseForm" method="post" action="" onsubmit="return false;" >
				<div class="contents join_area">
					<div class="pop_header clfix">
						<h1><spring:message code='license.list.017' /></h1>
					</div>
					
					<div class="section fisrt_section">
						<div class="table_area">
							<table class="rowtable writetable">
								<colgroup>
									<col style="width:15%">
									<col style="">
								</colgroup>
								<tr>
									<th></th>
									<td><input id="userCopyCount" name="userCopyCount" type="text" style="width:90%;" maxlength="50" onkeydown="return onlyNumber(event)" onkeyup="removeChar(event)" placeholder="<spring:message code='license.generate.002' />" ></td>
								</tr>
								<tr>
									<th></th>
									<td>
										<select id="periodGb" name="periodGb" style="width:150px;">
											<option value="1"><spring:message code='license.generate.003' /></option>
											<option value="2"><spring:message code='license.generate.004' /></option>
											<option value="3"><spring:message code='license.generate.008' /></option>									
										</select>
									</td>
								</tr>
								<tr>
	<%-- 								<th><label class="title" for="licenseGb">&nbsp;<em>*</em> <spring:message code='license.list.008' /></label></th>
									<td>
										<select id="licenseGb" name="licenseGb" style="width:150px;">
											<option value="1"><spring:message code='app.modify.text13' /></option>									
										</select>
									</td> --%>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</form>
		</div>
		<!-- //conteiner -->
	
		<div class="btn_area_bottom tRight" style="margin-top: 10px;">
			<a class="btn btnL btn_red" href="javascript:licenseGenerate();"><spring:message code='license.list.017' /></a>
		</div>
	</div><!-- //wrap -->

</body>
</html>