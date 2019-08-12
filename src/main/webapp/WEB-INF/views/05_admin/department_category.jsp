<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- 변수 지원, 흐름 제어, URL처리 -->
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %><!-- xml코어, 흐름제어, xml변환 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><!-- 지역, 메세지형식, 숫자, 날짜형식 -->
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %><!-- 데이터베이스 -->
<%@ include file="../inc/top.jsp" %>
</head>

<script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>

<script type="text/javascript">
$(document).ready(function(){	
	var companySeq = '${companySeq}';
	if(companySeq == ""){
		alert("<spring:message code='app.category.list.text9' />");
		self.close();
	}		
		
	$("#selCate1").change(function(){
		var thisSeq = $(this).val();
		var thisText = $("#selCateVal"+thisSeq).text();
		var thisUse = $("#selCateVal"+thisSeq).attr("alt");
		

		if(thisUse == '2'){
			$("#firstCease").html("<spring:message code='extend.local.054' />");
		}else if(thisUse == '1'){
			$("#firstCease").html("<spring:message code='extend.local.055' />");
		}
		//alert(thisText);
		
		$('#fm_category1').val(thisText.trim());
		$('#categorySeq1').val(thisSeq);
		$('#cateName').val(thisText.trim());
		$('#useGb').val(thisUse);
		$('#depth').val('1');
		
		
		
		var url = "/man/department/childList.html";
		var dataToBeSent = $("#frmCate").serialize();		
		$.post(url, dataToBeSent, function(data, textStatus) {
			  //data contains the JSON object
			  //textStatus contains the status: success, error, etc
			 //var JsonSize = data.length;
			$('#fm_category2').val('');
			$('#selCate2').html('');


			
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					if(data[i].useGb == '1'){
						$('#selCate2').append("<option alt='1'"+(($.browser.safari == true ) ? "style='font-size:13px;'" : "") + "value='"+data[i].departmentSeq+"'  id='selCateVal"+data[i].departmentSeq+"'>"+(($.browser.safari == true ) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "") +data[i].departmentName+"</option>");
					}else if(data[i].useGb == '2'){
						$('#selCate2').append("<option alt='2'"+(($.browser.safari == true ) ? "style='font-size:13px; color:red;'" : "style='color:red;'") + "  value='"+data[i].departmentSeq+"' id='selCateVal"+data[i].departmentSeq+"'>"+(($.browser.safari == true ) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "") +data[i].departmentName+"</option>");
					}
							
				}
			}else{
				$('#selCate2').html('');
			}

			  /*
			  var cateSeq     = data.categorySeq;
			  var cateName    = data.categoryName;
			  var cateParent  = data.categoryParent;
			  var cateUserGb  = data.chgUserGb;
			  var cateRegId   = data.chgUserId;
			  */
			  
			 // $('#categorySeq').val(cateSeq);
			 // $('#depth').val(depth);
			 // $('#selCate2').prepend("<option value='"+cateSeq+"' id='selCateVal"+cateSeq+"'>"+cateName+"</option>");			  
		}, "json");
	});
	
	$("#selCate2").change(function(){
		var thisSeq = $(this).val();
		var thisText = $("#selCateVal"+thisSeq).text();
		var thisUse = $("#selCateVal"+thisSeq).attr("alt");
		
		if(thisUse == '2'){
			$("#secondCease").html("<spring:message code='extend.local.054' />");
		}else if(thisUse == '1'){
			$("#secondCease").html("<spring:message code='extend.local.055' />");
		}
		
		
		$('#fm_category2').val(thisText.trim());
		$('#categorySeq2').val(thisSeq);
		$('#cateName').val(thisText.trim());
		$('#useGb').val(thisUse);
		$('#depth').val('2');
				
		/*
		var url = "/app/category/category_list2.html";
		var dataToBeSent = $("#frmCate").serialize();		
		$.post(url, dataToBeSent, function(data, textStatus) {
			  //data contains the JSON object
			  //textStatus contains the status: success, error, etc
		}, "json");
		*/
		
	});	
});	

function Save(companySeq, depth){
	var curVal  = $('#fm_category'+depth).val();
	var curVal1 = $('#fm_category1').val();

	if(depth == 2){
		if($('#categorySeq1').val() == "" || curVal1 == "" ){
			alert("<spring:message code='app.category.list.text10' />");	
			return;
		}		
	}	
	
	if($('#fm_category'+depth).val() == ""){
		alert(depth+"<spring:message code='app.category.list.text14' />");	
		$('#fm_category'+depth).focus();
		return;
	}

	$('#cateName').val(curVal.trim());
	$('#depth').val(depth);
	$('#useGb').val('1');
	var url = "/man/department/writeOK.html";
	var dataToBeSent = $("#frmCate").serialize();
	$.post(url, dataToBeSent, function(data, textStatus) {
		  //data contains the JSON object
		  //textStatus contains the status: success, error, etc
		  var departmentSeq     = data.departmentSeq;
		  var departmentName    = data.departmentName;
		  var departmentParent  = data.departmentParent;
		  var departmentRegId   = data.regUserId;
		  
		  $('#categorySeq'+depth).val(departmentSeq);
		  $('#depth').val(depth);
		  $('#selCate'+depth).append("<option alt='1'" + (($.browser.safari == true ) ? "style='font-size:13px;'" : "") + "value='"+departmentSeq+"' id='selCateVal"+departmentSeq+"'>"+(($.browser.safari == true ) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "") +departmentName+"</option>");	 
	}, "json");
}

function Modify(companySeq, depth){
	var curVal = $('#fm_category'+depth).val();
	
	if($('#categorySeq'+depth).val() == "" || curVal == "" ){
		alert("<spring:message code='app.category.list.text13' /> "+depth+"<spring:message code='app.category.list.text14' />");	
		return;
	}
	
	$('#cateName').val(curVal.trim());
	
	var url = "/man/department/modifyOK.html";
	var dataToBeSent = $("#frmCate").serialize();
	$.post(url, dataToBeSent, function(data, textStatus) {
		  //data contains the JSON object
		  //textStatus contains the status: success, error, etc
		  
		  //$('#categorySeq'+depth).val(departmentSeq);
		  $('#depth').val(depth);
		  
		  for( var j=0; j< data.length; j++){
			  
			  $('#selCateVal'+data[j].departmentSeq).remove();
		  }
		  for( var i =0; i< data.length ; i++){

			  if( data[i].useGb == '1'){
				  $('#selCate'+depth).append("<option alt='1'"+ (($.browser.safari == true ) ? "style='font-size:13px;'" : "") + " value='"+data[i].departmentSeq+"' id='selCateVal"+data[i].departmentSeq+"'>" +(($.browser.safari == true ) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "")+ data[i].departmentName+"</option>");  
			  }else if( data[i].useGb == '2'){
				  $('#selCate'+depth).append("<option alt='2'"+ (($.browser.safari == true ) ? "style='font-size:13px;color:red;'" : "style='color:red;'") + "value='"+data[i].departmentSeq+"' id='selCateVal"+data[i].departmentSeq+"'>" +(($.browser.safari == true ) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "")+data[i].departmentName+"</option>");
			  } 
		  }
		  
		  
		  	 
	}, "json");
}

function Cease(companySeq, depth){
	var curVal = $('#fm_category'+depth).val();
	
	if($('#categorySeq'+depth).val() == "" || curVal == "" ){
		alert("<spring:message code='app.category.list.text12' /> "+depth+"<spring:message code='app.category.list.text14' />");	
		return;
	}	
	
	$('#cateName').val(curVal.trim());
	if($("#useGb").val() == '1'){
		$("#useGb").val('2') 
	}else if($("#useGb").val() == '2'){
		$("#useGb").val('1')
	}
	
	//alert
	
	var url = "/man/department/modifyOK.html";
	var dataToBeSent = $("#frmCate").serialize();
	$.post(url, dataToBeSent, function(data, textStatus) {
		  //data contains the JSON object
		  //textStatus contains the status: success, error, etc
		  
		  //$('#categorySeq'+depth).val(departmentSeq);
		  $('#depth').val(depth);
		  
		  for( var j=0; j< data.length; j++){
			  $('#selCateVal'+data[j].departmentSeq).remove();
		  }
		  
		  for( var i =0; i< data.length ; i++){

			  if( data[i].useGb == '1'){
				  $('#selCate'+depth).append("<option alt='1'"+ (($.browser.safari == true ) ? "style='font-size:13px;'" : "") + " value='"+data[i].departmentSeq+"' id='selCateVal"+data[i].departmentSeq+"'>" +(($.browser.safari == true ) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "")+ data[i].departmentName+"</option>");  
			  }else if( data[i].useGb == '2'){
				  $('#selCate'+depth).append("<option alt='2'"+ (($.browser.safari == true ) ? "style='font-size:13px;color:red;'" : "style='color:red;'") + "value='"+data[i].departmentSeq+"' id='selCateVal"+data[i].departmentSeq+"'>" +(($.browser.safari == true ) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "")+data[i].departmentName+"</option>");
			  } 
		  }
	}, "json");
}

function selectCategory(){
	//alert(1);
	var categorySeq = '';
	var categoryName1 = $('#fm_category1').val();
	var categoryName2 = $('#fm_category2').val();
	var categoryName = '';
	var depth = $('#depth').val();
	if(depth=='1'){
		categorySeq = $('#categorySeq1').val();
		categoryName = categoryName1;
	}else if(depth=='2'){
		categorySeq = $('#categorySeq2').val();		
		categoryName = categoryName1+' > '+categoryName2;
	}else{
		alert("<spring:message code='app.category.list.text16' />");
		return false;
	}
	//alert(categorySeq);
	//alert(categoryName);
	$(window.opener.document).find('[name=categorySeq]').val(categorySeq);
	$(window.opener.document).find('[name=categoryName]').val(categoryName);
	self.close();
}
</script>
<body>
<!-- wrap -->
<div id="wrap" class="sub_wrap">
	
	<!-- header -->
	<%@ include file="../inc/header.jsp" %>
	<!-- //header -->

	
	<!-- conteiner -->
	<div id="container">
		<div class="contents join_area">
			<!-- 앱 카테고리 -->
			<!-- man header -->
			<%@ include file="../inc/man_header.jsp" %>
			<!-- //man header -->
			
			<h2><spring:message code='extend.local.056' /></h2>


			<form method="post" name="frmCate" id="frmCate" action="return ture;">
				<input type="hidden" name="companySeq"   	id="companySeq" value="${companySeq}" />
				<input type="hidden" name="cateName" 	id="cateName"  		value="" />
				<input type="hidden" name="depth"    	id="depth"    		value="" />
				<input type="hidden" name="categorySeq1" id="categorySeq1"  value="" />
				<input type="hidden" name="categorySeq2" id="categorySeq2"  value="" />
				<input type="hidden" name="useGb" 		id="useGb"   		value="" />
				<input type="hidden" name="toUse"       id="toUse"			value="false"/>
			</form>

			<div class="section fisrt_section">
				<div class="clfix">
					<div class="category_detail">
						<h3><spring:message code='extend.local.057' /></h3>
						<div class="form_area">
								<input name="fm_category1" id="fm_category1" type="text" style="width:150px;">
								<a class="btn btnXS btn_gray_dark" href="#1" onClick="javascript:Save('${companySeq}','1')" ><spring:message code='app.category.list.text6' /></a>
								<a class="btn btnXS btn_gray_dark" href="#2" onClick="javascript:Modify('${companySeq}','1')" ><spring:message code='app.category.list.text7' /></a>
								<a id="firstCease" class="btn btnXS btn_gray_dark" href="#3" onClick="javascript:Cease('${companySeq}','1')" > <spring:message code='extend.local.055' /></a>
							</div>
						<div>
							<select name="selCate1" id="selCate1" multiple>
								<c:choose>	
									<c:when test="${empty DepartmentList}">																		
									</c:when>
									<c:otherwise>
										<c:forEach var="i" begin="0" end="${DepartmentList.size()-1}">
											<c:if test = "${DepartmentList[i].useGb == '1' }"><option alt="1" name="list" value="${DepartmentList[i].departmentSeq}" id="selCateVal${DepartmentList[i].departmentSeq}">${DepartmentList[i].departmentName}</option></c:if>
											<c:if test = "${DepartmentList[i].useGb == '2' }"><option alt="2" name="list" style="color:red;" value="${DepartmentList[i].departmentSeq}" id="selCateVal${DepartmentList[i].departmentSeq}">${DepartmentList[i].departmentName}</option></c:if>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</select>
						</div>
					</div>
					<div class="category_detail">
						<h3><spring:message code='extend.local.058' /></h3>
						<div class="form_area">
							<input name="fm_category2" id="fm_category2" type="text" style="width:150px;">
							<a class="btn btnXS btn_gray_dark" href="#1" onClick="javascript:Save('${companySeq}','2')" ><spring:message code='app.category.list.text6' /></a>
							<a class="btn btnXS btn_gray_dark" href="#2" onClick="javascript:Modify('${companySeq}','2')" ><spring:message code='app.category.list.text7' /></a>
							<a id="secondCease" class="btn btnXS btn_gray_dark" href="#3" onClick="javascript:Cease('${companySeq}','2')" > <spring:message code='extend.local.055' /></a>
						</div>
						<div>
							<select name="selCate2" id="selCate2" multiple></select>
						</div>
					</div>
				</div>
			</div>
			
			<!-- //앱 카테고리 -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->
</body>
<script>
$(document).ready(function(){
	if($.browser.safari){
		$("#selCate1").css("font-size","15px");
		$("#selCate1").css("padding-top", "10px");
		$("#selCate2").css("font-size","15px");
		$("#selCate2").css("padding-top", "10px");
		$("[name=list]").each(function(){
			var string = $(this).html();
			$(this).html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+string);
			$(this).css("font-size","13px");
		});
	}
});
</script>
}
</html>