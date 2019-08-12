<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- 변수 지원, 흐름 제어, URL처리 -->
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %><!-- xml코어, 흐름제어, xml변환 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><!-- 지역, 메세지형식, 숫자, 날짜형식 -->
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %><!-- 데이터베이스 -->
<%@ include file="../inc/top.jsp" %>
</head>

<script type="text/javascript">
$(document).ready(function(){	
	var storeBundleId = '${storeBundleId}';
	var userSeq = '${userSeq}';
	if(storeBundleId == "" && userSeq == ""){
		alert("<spring:message code='app.category.list.text9' />");
		self.close();
	}		

	// 첫번째 카테고리의 선택을 바꿀때 이벤트
	$("#selCate1").change(function(){
		var thisSeq = $(this).val();
		var thisText = $("#selCateVal"+thisSeq).text();
		//alert(thisText);

		$('#fm_category1').val(thisText);
		$('#categorySeq1').val(thisSeq);
		$('#cateName').val(thisText);
		$('#depth').val('1');

		var url = "/app/category/category_list2.html";
		var dataToBeSent = $("#frmCate").serialize();		
		$.post(url, dataToBeSent, function(data, textStatus) {
			  //data contains the JSON object
			  //textStatus contains the status: success, error, etc
			 //var JsonSize = data.length;
			$('#fm_category2').val('');
			$('#selCate2').html('');
			/*
			alert(data.length);
			alert(data[0].categoryName+"00");
			alert(data[1].categoryName+"11");
			alert(data[2].categoryName+"22");
			*/
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					$('#selCate2').append("<option value='"+data[i].categorySeq+"' id='selCateVal"+data[i].categorySeq+"'>"+data[i].categoryName+"</option>");		
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
			 // $('#selCate2').append("<option value='"+cateSeq+"' id='selCateVal"+cateSeq+"'>"+cateName+"</option>");
		}, "json");
	});

	// 두번째 카테고리 선택을 바꿀때 이벤트
	$("#selCate2").change(function(){
		var thisSeq = $(this).val();
		var thisText = $("#selCateVal"+thisSeq).text();

		$('#fm_category2').val(thisText);
		$('#categorySeq2').val(thisSeq);
		$('#cateName').val(thisText);
		$('#depth').val('2');
	});
});

// 저장시
function Save(storeBundleId, depth){
	var curVal  = $('#fm_category'+depth).val();
	var curVal1 = $('#fm_category1').val();
	var returnValue = "";
	
	
	
	$.ajax({
        url: "/categoryIsDuplicated.html" ,
        type: "POST" ,
        async : false ,
        data:{
        	storeBundleId : "${param.storeBundleId}",
        	categoryName : curVal
                       },
        success: function (result){
           switch (result){
                  case 0 : 
                  	returnValue = false;  
               	  	alert("중복된 카테고리 이름이 있습니다");
                  break ;
                  	case 1 : 
                  	returnValue = true;
                  break ;
                  
                  case -1 : 
                  	returnValue = false;
                  	alert("알수없는 에러 ");
                  break ;
           }
        }
    });

	if( returnValue == false) return;
	
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

	$('#cateName').val(curVal);
	$('#depth').val(depth);

	var url = "/app/category/category_writeOK.html";
	var dataToBeSent = $("#frmCate").serialize();
	$.post(url, dataToBeSent, function(data, textStatus) {
		  //data contains the JSON object
		  //textStatus contains the status: success, error, etc
		  var cateSeq     = data.categorySeq;
		  var cateName    = data.categoryName;
		  var cateParent  = data.categoryParent;
		  var cateUserGb  = data.chgUserGb;
		  var cateRegId   = data.chgUserId;
		  
		  $('#categorySeq'+depth).val(cateSeq);
		  $('#depth').val(depth);
		  $('#selCate'+depth).append("<option value='"+cateSeq+"' id='selCateVal"+cateSeq+"'>"+cateName+"</option>");	 
	}, "json");
}

// 수정 버튼 클릭시
function Modify(storeBundleId, depth){
	var curVal = $('#fm_category'+depth).val();
	if($('#categorySeq'+depth).val() == "" || curVal == "" ){
		alert("<spring:message code='app.category.list.text13' /> "+depth+"<spring:message code='app.category.list.text14' />");	
		return;
	}
	$('#cateName').val(curVal);

	var url = "/app/category/category_modifyOK.html";
	var dataToBeSent = $("#frmCate").serialize();
	$.post(url, dataToBeSent, function(data, textStatus) {
		  //data contains the JSON object
		  //textStatus contains the status: success, error, etc
		  var cateSeq     = data.categorySeq;
		  var cateName    = data.categoryName;
		  var cateParent  = data.categoryParent;
		  var cateUserGb  = data.chgUserGb;
		  var cateRegId   = data.chgUserId;

		  $('#categorySeq'+depth).val(cateSeq);
		  $('#depth').val(depth);
		  $('#selCateVal'+cateSeq).remove();
		  $('#selCate'+depth).append("<option value='"+cateSeq+"' id='selCateVal"+cateSeq+"'>"+cateName+"</option>");
	}, "json");
}

// 삭제 버튼 클릭시
function Delete(storeBundleId, depth){
	var curVal = $('#fm_category'+depth).val();
	
	if($('#categorySeq'+depth).val() == "" || curVal == "" ){
		alert("<spring:message code='app.category.list.text12' /> "+depth+"<spring:message code='app.category.list.text14' />");	
		return;
	}
	$('#cateName').val(curVal);

	var url = "/app/category/category_deleteOK.html";
	var dataToBeSent = $("#frmCate").serialize();
	$.post(url, dataToBeSent, function(data, textStatus) {
		  //data contains the JSON object
		  //textStatus contains the status: success, error, etc
		  var cateSeq     = data.categorySeq;
		  
		  $('#cateName').val('');
		  $('#categorySeq'+depth).val('');
		  $('#depth').val('');
		  $('#fm_category'+depth).val('');
		  $('#selCateVal'+cateSeq).remove();  	 
		  
		  if(depth == "1"){
			  $('#categorySeq2').val('');
			  $('#fm_category2').val('');
			  $('#selCate2').html('');  	 		  
		  }
	}, "json");	
}

// 선택후 저장버튼을 눌렀을때 이벤트
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
<body class="popup" style="width:670px;">
<!-- wrap -->
<div class="pop_wrap">	

	<!-- conteiner -->
	<div id="container">
		<div class="contents">
			<div class="pop_header clfix">
				<h1><spring:message code='app.category.list.text1' /></h1>
			</div>

			<form method="post" name="frmCate" id="frmCate" action="return ture;">
				<input type="hidden" name="storeBundleId"   	id="storeBundleId"   		value="${storeBundleId}" />
				<input type="hidden" name="cateName" 	id="cateName"  		value="" />			
				<input type="hidden" name="depth"    	id="depth"    		value="" />		
				<input type="hidden" name="categorySeq1" id="categorySeq1"   	value="" />		
				<input type="hidden" name="categorySeq2" id="categorySeq2"   	value="" />		
			</form>

			<!-- contents_detail -->
			<div class="pop_contents" id="pop_category_wrap">
				<div class="pop_section">
					<div class="clfix">
						<div class="category_detail">
							<h2><spring:message code='app.category.list.text4' /></h2>
							<div class="form_area">
								<input name="fm_category1" id="fm_category1" type="text" style="width:150px;">
								<a class="btn btnXS btn_gray_dark" href="#1" onClick="javascript:Save('${storeBundleId}','1')" ><spring:message code='app.category.list.text6' /></a>
								<a class="btn btnXS btn_gray_dark" href="#2" onClick="javascript:Modify('${storeBundleId}','1')" ><spring:message code='app.category.list.text7' /></a>
								<a class="btn btnXS btn_gray_dark" href="#3" onClick="javascript:Delete('${storeBundleId}','1')" ><spring:message code='app.category.list.text8' /></a>
							</div>
							<div>							
								<select name="selCate1" id="selCate1" multiple>
								<c:choose>	
									<c:when test="${empty InAppList}">																		
									</c:when>
									<c:otherwise>
										<c:forEach var="i" begin="0" end="${InAppList.size()-1}">
										<option value="${InAppList[i].categorySeq}" id="selCateVal${InAppList[i].categorySeq}">${InAppList[i].categoryName}</option>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								</select>
							</div>
						</div>
						<div class="category_detail">
							<h2><spring:message code='app.category.list.text5' /></h2>
							<div class="form_area">
								<input name="fm_category2" id="fm_category2" type="text" style="width:150px;">
								<a class="btn btnXS btn_gray_dark" href="#1" onClick="javascript:Save('${storeBundleId}','2')" ><spring:message code='app.category.list.text6' /></a>
								<a class="btn btnXS btn_gray_dark" href="#2" onClick="javascript:Modify('${storeBundleId}','2')" ><spring:message code='app.category.list.text7' /></a>
								<a class="btn btnXS btn_gray_dark" href="#3" onClick="javascript:Delete('${storeBundleId}','2')" ><spring:message code='app.category.list.text8' /></a>
							</div>
							<div>
								<select name="selCate2" id="selCate2" multiple></select>
							</div>
						</div>
					</div>
					<c:if test="${not empty isInapp && 'Y' eq isInapp}">
				<div class="btn_area_bottom tCenter">
					<a href="javascript:selectCategory();" class="btn btnL btn_red"><spring:message code='app.category.list.text17' /></a>
				</div>
					</c:if>					
				</div>
			</div>
			<!-- //contents_detail -->
		</div>
	</div>
	<!-- //conteiner -->
</div><!-- //wrap -->

</body>
</html>