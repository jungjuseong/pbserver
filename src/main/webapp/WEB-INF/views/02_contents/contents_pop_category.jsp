<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>
</head>


<script>

/*depth랑 prepend만 기억하자  */

$(document).ready(function(){
	$("#selectApp").change(function(){
		var thisSeq = $(this).val();
		var temp = $(this).attr("alt");
		var thisText = $( "#selectApp option:selected" ).text();
		$('#storeBundleId').val(thisSeq);
		$('#hiddenAppName').val(thisText);
		$('#depth').val('1');
		
		var url = "/contents/getInAppList.html";
		var dataToBeSent = $("#popUpList_f").serialize();	
		
		$.post(url, dataToBeSent, function(data, textStatus) {
			  //data contains the JSON object
			  //textStatus contains the status: success, error, etc
			$('#hiddenInAppName').val('');
			$('#selectInApp').html('');
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					$('#selectInApp').append("<option value='"+data[i].inappSeq+"' id='selectInAppSeq"+data[i].inappSeq+"'>"+data[i].inappName+"</option>");		
				}
			}else{
				$('#selectInApp').html('');
			}
		}, "json");
	});

	$("#selectInApp").change(function(){
		var thisSeq = $(this).val();
		var thisText = $("#selectInAppSeq"+thisSeq).text();

		//alert(thisText);
		$('#hiddenInAppName').val(thisText);
		$('#inAppSeq').val(thisSeq);
		$('#depth').val('2');
	});
});

function selectCategory(){
	//alert(1);
	
	var appName = $('#hiddenAppName').val();
	var inAppName = $('#hiddenInAppName').val();
	var relatedAppName = '';
	var relatedBundleId = '';
	var relatedAppType = '';
	var relatedInAppSeq = '';
	var depth = $('#depth').val();
	if(depth=='1'){
		relatedAppType = '1';
		relatedBundleId = $("#selectApp").val();
		relatedAppName = appName;
	}else if(depth=='2'){
		relatedAppType = '2';
		relatedBundleId = $("#selectApp").val();
		relatedInAppSeq = $('#inAppSeq').val();		
		relatedAppName = appName+' > '+inAppName;
	}else{
		alert("<spring:message code='contents.pop.category.005' />");
		return false;
	}
	//alert(categorySeq);
	//alert(categoryName);
	//alert(relatedBundleId);
	//alert(relatedInAppSeq);
	$(window.opener.document).find('[name=relatedAppType]').val(relatedAppType);
	$(window.opener.document).find('[name=relatedBundleId]').val(relatedBundleId);
	$(window.opener.document).find('[name=relatedInAppSeq]').val(relatedInAppSeq);
	$(window.opener.document).find('[name=relatedAppName]').val(relatedAppName);
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
				<h1><spring:message code='contents.pop.category.001' /></h1>
			</div>

			<%-- <form method="post" name="frmCate" id="frmCate" action="return ture;">
				<input type="hidden" name="appSeq"   	id="appSeq"   		value="${appSeq}" />
				<input type="hidden" name="cateName" 	id="cateName"  		value="" />			
				<input type="hidden" name="depth"    	id="depth"    		value="" />		
				<input type="hidden" name="categorySeq1" id="categorySeq1"   	value="" />		
				<input type="hidden" name="categorySeq2" id="categorySeq2"   	value="" />		
			</form> --%>


			<form mehtod="POST" name="popUpList_f" id="popUpList_f" action="return true;">
				<input type="hidden" name="storeBundleId"   	id="storeBundleId"   		value="" />
			</form>
				<input type="hidden" name="hiddenAppName"   id="hiddenAppName"   value=""/>
				<input type="hidden" name="hiddenInAppName" id="hiddenInAppName" value=""/>
				<input type="hidden" name="depth"           id="depth"           value=""/>
				<input type="hidden" name="inAppSeq"        id="inAppSeq"        value=""/>
			<!-- contents_detail -->
			<div class="pop_contents" id="pop_category_wrap">
				<div class="pop_section">
					<div class="clfix">
						<div class="category_detail">
							<h2><spring:message code='contents.pop.category.002' /></h2>
							<div>
								<!-- <select name="" multiple>
									<option>소설</option>
									<option>어린이 도서</option>
									<option>잡지</option>
									<option>4</option>
									<option>5</option>
									<option>6</option>
									<option>7</option>
									<option>8</option>
									<option>9</option>
									<option>10</option>
								</select> -->
								<select name="selectApp" id="selectApp" multiple>
								<c:choose>	
									<c:when test="${empty appList}">																		
									</c:when>
									<c:otherwise>
										<c:forEach var="i" begin="0" end="${appList.size()-1}">
											<option value="${appList[i].storeBundleId}" alt="1"  id="selectAppSeq${appList[i].storeBundleId}">${appList[i].appName}</option>
										</c:forEach>
									</c:otherwise>
								</c:choose>										
								</select>
								
								<%-- <select name="selCate1" id="selCate1" multiple>
								<c:choose>	
									<c:when test="${empty InAppList}">																		
									</c:when>
									<c:otherwise>
										<c:forEach var="i" begin="0" end="${InAppList.size()-1}">
										<option value="${InAppList[i].categorySeq}" id="selCateVal${InAppList[i].categorySeq}">${InAppList[i].categoryName}</option>
										</c:forEach>
									</c:otherwise>
								</c:choose>										
								</select> --%>
							</div>
						</div>
						<div class="category_detail">
							<h2><spring:message code='contents.pop.category.003' /></h2>
							<div>
								<select name="selectInApp" id="selectInApp" multiple>
								</select>
							</div>
						</div>
					</div>
					
					<div class="btn_area_bottom tCenter">
						<a href="javascript:selectCategory()" class="btn btnL btn_red"><spring:message code='contents.pop.category.004' /></a>
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