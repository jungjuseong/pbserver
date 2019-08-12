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
	var noticeSeq = '${noticeSeq}';
	var userSeq = '${userSeq}';
	

	
	
	/* if(noticeSeq == "" && userSeq == ""){
		alert("<spring:message code='app.category.list.text9' />");
		self.close();
	} */		

	if('${param.storeBundleId}' != "" && '${param.inappSeq}' != ""){
		var url = "/app/inapp/getList.html";
		var dataToBeSent = $("#frmCate").serialize();		
		$.post(url, dataToBeSent, function(data, textStatus) {
			  //data contains the JSON object
			  //textStatus contains the status: success, error, etc
			 //var JsonSize = data.length;
			$('#selCate2').html('');

			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					if('${param.inappSeq}' == data[i].inappSeq) $('#selCate2').prepend("<option " + (($.browser.safari == true ) ? "style='font-size:17px;'" : "") + "value='"+data[i].inappSeq+"'  style='background-color:red;' id='selCateVal"+data[i].inappSeq+"'>"+ (($.browser.safari == true ) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "") + data[i].inappName+"</option>"); 
					else $('#selCate2').prepend("<option " + (($.browser.safari == true ) ? "style='font-size:17px;'" : "") + "value='"+data[i].inappSeq+"'  id='selCateVal"+data[i].inappSeq+"'>"+ (($.browser.safari == true ) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "") + data[i].inappName+"</option>");		
				}
			}else{
				$('#selCate2').html('');
			}
			  
		}, "json");
	}
	
	
	$("#selCate1").change(function(){
		var identifier = $(this).val();
		var thisText = $("#selCateVal"+identifier).text();
		//alert(thisText);

		$('#storeBundleId').val(identifier);
		$('#cateName').val(thisText);
		$('#depth').val('1');

		var url = "/app/inapp/getList.html";
		var dataToBeSent = $("#frmCate").serialize();		
		$.post(url, dataToBeSent, function(data, textStatus) {
			  //data contains the JSON object
			  //textStatus contains the status: success, error, etc
			 //var JsonSize = data.length;
			$('#selCate2').html('');
			
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					$('#selCate2').prepend("<option " + (($.browser.safari == true ) ? "style='font-size:17px;'" : "") + "value='"+data[i].inappSeq+"' id='selCateVal"+data[i].inappSeq+"'>"+ (($.browser.safari == true ) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "") + data[i].inappName+"</option>");		
				}
			}else{
				$('#selCate2').html('');
			}
	  
		}, "json");
	});

	$("#selCate2").change(function(){
		var thisSeq = $(this).val();
		var thisText = $("#selCateVal"+thisSeq).text();
		//alert(thisText);

		$('#inappSeq').val(thisSeq);
		$('#cateName').val(thisText);
		$('#depth').val('2');	

	});
});

function selectCategory(){
	//alert(1);
	var storeBundleId = '';
	var inappSeq = '';
	var depth = $('#depth').val();
	storeBundleId = $('#storeBundleId').val();
	inappSeq = $('#inappSeq').val();		

	//alert(categorySeq);
	//alert(categoryName);
	$(window.opener.document).find('#storeBundleId').val(storeBundleId);
	$(window.opener.document).find('#inappSeq').val(inappSeq);
	self.close();
}

</script>

<!--

categorySeq1   === > appSeq
categorySeq2   === > inappSeq
  -->

<body class="popup" style="width:670px;">
<!-- wrap -->
<div class="pop_wrap">	
	
	<!-- conteiner -->
	<div id="container">
		<div class="contents">
			<div class="pop_header clfix">
				<h1> <spring:message code='extend.local.009' /></h1>
			</div>
			
			<form method="post" name="frmCate" id="frmCate" action="return ture;">
				<input type="hidden" name="noticeSeq"   	id="noticeSeq"   		value="${noticeSeq}" />
				<input type="hidden" name="cateName" 		id="cateName"  		value="" />			
				<input type="hidden" name="depth"    		id="depth"    		value="" />		
				<input type="hidden" name="storeBundleId" 			id="storeBundleId"   	value="${param.storeBundleId }" />		
				<input type="hidden" name="inappSeq" 		id="inappSeq"   	value="${param.inappSeq }" />		
			</form>
			
			<!-- contents_detail -->
			<div class="pop_contents" id="pop_category_wrap">
				<div class="pop_section">
					<div class="clfix">
						<div class="category_detail">
							<h2> <spring:message code='extend.local.075' /></h2>
							<div>							
								<select name="selCate1" id="selCate1" multiple>
								<c:choose>	
									<c:when test="${empty appList}">																		
									</c:when>
									<c:otherwise>
										<c:forEach var="i" begin="0" end="${appList.size()-1}">
											<c:if test="${param.storeBundleId == appList[i].storeBundleId }" >
												<option name="list" value="${appList[i].storeBundleId}" style="background-color:red;"   id="selCateVal${appList[i].storeBundleId}">${appList[i].storeBundleId }</option>
											</c:if>
											<c:if test="${param.storeBundleId != appList[i].storeBundleId || empty param.storeBundleId}">
												<option value="${appList[i].storeBundleId}" name="list" id="selCateVal${appList[i].storeBundleId}">${appList[i].storeBundleId }</option>
											</c:if>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								</select>
							</div>
						</div>
						<div class="category_detail">
							<h2> <spring:message code='extend.local.010' /></h2>
							<div>
								<select name="selCate2" id="selCate2" multiple></select>
							</div>
						</div>
					</div>
					<div class="btn_area_bottom tCenter">
						<a href="javascript:selectCategory();" class="btn btnL btn_red"><spring:message code='app.category.list.text17' /></a>
					</div>			
				</div>
			</div>
			<!-- //contents_detail -->
		</div>
	</div>
	<!-- //conteiner -->

</div><!-- //wrap -->

<script>
	$(document).ready(function(){
		if($.browser.safari){
			$("[name=list]").each(function(){

				$("#selCate1").css("font-size", "17px");
				$("#selCate2").css("font-size", "17px");
				var string2 = $(this).html();
				$(this).html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+string2);
				$(this).css("font-size","17px");
			});
		}
	});
</script>
</body>
</html>