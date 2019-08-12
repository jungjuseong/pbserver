<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>
<%@ page session="true" %>
<script type="text/javascript" src="/js/jquery.form.mini.js" ></script>
<script type="text/javascript">

$(document).ready(function(){

	//custom radio button action;
	$(document).on('click', 'span.radio', function(){
		var inputRadioName = $(this).next('input').attr('name');
		if(inputRadioName=='useGb'){
			//sample action
		}
	});

	//파일 선택시 이미지 카운트
	$('input[type=file][name=iconFile], input[type=file][name=captureFile]').click(function(){
		var fileType = $(this).attr("name");
		var maxImgCnt = 1;
		var imgCnt = $(this).parent().find('ul').find('li').size();
		if(fileType=='captureFile'){
			actionUrl = '/app/capturefileupload.html';
			maxImgCnt = 5;
		}
		if(maxImgCnt==imgCnt){
			alert("<spring:message code='app.inapp.write.text30' />")
			return false;
		}
	});

	//파일 선택 이벤트
	$('input[type=file][name=iconFile], input[type=file][name=captureFile]').change(function(){
		var fileType = $(this).attr("name");
		var actionUrl = '/app/iconfileupload.html';
		var maxImgCnt = 1;
		var imgCnt = $(this).parent().find('ul').find('li').size();
		if(fileType=='captureFile'){
			actionUrl = '/app/capturefileupload.html';
			maxImgCnt = 5;
		}
		if(maxImgCnt==imgCnt){
			alert("<spring:message code='app.inapp.write.text30' />")
			return false;
		}
		$('#appForm').attr('action', actionUrl);
		$('#fileType').val(fileType);
		$(this).after('<p id="uploadPercent"><p>');
		ajaxFileUpload();
	});

	//파일 업로드 & 리턴
	function ajaxFileUpload(){
		$('#appForm').ajaxForm({
		    beforeSend: function() {
		        var percentVal = '0%';
		        $('#uploadPercent').html(percentVal);
		    },
		    uploadProgress: function(event, position, total, percentComplete) {
		        var percentVal = percentComplete + '%';
		        $('#uploadPercent').html(percentVal);
		    },
			complete: function(xhr) {
				//status.html(xhr.responseText);
				$('#uploadPercent').remove();
				var json_data = JSON.parse(xhr.responseText);

				var orgFileName = json_data.orgFileName;
				var saveFileName = json_data.saveFileName;
				var webPath = json_data.webPath;
				var error = json_data.error;
				var fileType = $('#fileType').val();
				var type = "icon";
				if(fileType=='captureFile'){
					var type = "img";
				}
				if(error=='none'){
					var li = '<li>';
					li += '<input type="hidden" name="'+type+'OrgFile" value="'+orgFileName+'"/>'; 
					li += '<input type="hidden" name="'+type+'SaveFile" value="'+saveFileName+'"/>'; 
					li += '<img src="'+webPath+'" alt="'+orgFileName+'">'; 
					li += '<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.write.text38' />"></a></li>'; 
					$('[name='+fileType+']').parents('td').find('ul').append(li);				
				}else{
					alert(error);
				}
				$('input[type=file][name=iconFile], input[type=file][name=captureFile]').val('');
			}	
	    }); 
		$('#appForm').submit();
	}

	//이미지 파일 삭제
	$(document).on('click', '.removeImgBtn', function(){
		var that = $(this);
		var saveFileName = $(this).parents('li').find('input[name*=SaveFile]').val();
		$('#saveFileName').val(saveFileName);
		$('#fileStatus').val('temp');
		var action_url = '/app/deletetmpimg.html';
		$.post(action_url, $('#appForm').serialize(), function(data){
			//action;
			if(data.error!='none'){
				alert(data.error);
			}
			$(that).parent().remove();
		}, "json");
	});

	$('#registBtn').click(function(e){	
		if($("#preventSubmit").val() == '0'){
			$("#appForm").attr('action', '/book/inapp/regist.html');
			var isValid = false;
			isValid = customValidate({
				 	 rules: {
	 	
				    	verNum:{
				    		required: true,
				    		maxlength : 20
				    	}	    	
				    },
	
				    messages: {
						inappName: {
							required: "<spring:message code='app.inapp.write.text31' />",
							maxlength : "<spring:message code='app.inapp.write.text32' />"
						},
				    	verNum:{
							required: "<spring:message code='app.inapp.write.text33' />",
							mmaxlength : "<spring:message code='app.inapp.write.text34' />"
				    	} 
					},
					
					 errorPlacement: function(error, element) {
						error.appendTo( element.parent("td") );
					},
					validClass:"success"
				});	

				if(isValid){
					var categoryName = $('[name=categoryName]').val();
					var categorySeq = $('[name=categorySeq]').val();
					if(!categoryName||!categorySeq){
						alert("<spring:message code='app.inapp.write.text35' />");
						$('#categorySearch').focus();
						return false;
						isValid = false;
					}
				}


				if(isValid){
					var inappMetaTitle = $("#inappMetaTitle").val();
					var storeBundleId = $("[name=appSeq]").val();
					$.ajax({
	                    url: "/book/inapp/checkIfInappName.html" ,
	                    type: "POST" ,
	                    async: false,
	                    data:{
	                       "inappMetaTitle" : inappMetaTitle,
	                       "storeBundleId" : storeBundleId
	                    },
	                    success: function (result){
	                    	if (result == true){
	                     	}else{
	                        	alert("<spring:message code='extend.local.062' />");
	                        	isValid = false;
	                            return false;
	                     	}
	                    }
	               });

					if(isValid == false) return false;
				}

				var form = document.getElementById('appForm');
			 	if(isValid){
				 	$("#preventSubmit").val('1');
					window.appForm.submit();		 		
			 	}
	 	}else{
	 		e.preventDefault();
	 	}
	});

	//식별자 삭제
	$(document).on('click', '.removeProvBtn', function(){
		$(this).parents('li').remove();
	});	

	$('#categorySearch').click(function(){
		var winWidth = 680;
		var winHeight = 400;
		var winPosLeft = (screen.width - winWidth)/2;
		var winPosTop = (screen.height - winHeight)/2;
		var url = "/app/category/category_write.html?storeBundleId=${vo.storeBundleId}&isInapp=Y";		
		var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
		//if(!templatePopup){
			categoryPopup = window.open(url, "categoryPopup", opt);			
		//}	
	});

	$('#categoryName').focus(function(e){
		e.preventDefault();
		$('#categorySearch').focus().click();
	});

	$("#use_user_pop").click( function(){
		
	});
}); 
function cancelResist(){
	if(confirm("<spring:message code='app.inapp.write.text37' />")){
		document.listFrm.action='/book/inapp/list.html';
		document.listFrm.submit();		
	}
}
function submit(){
	var form = document.getElementById('appForm');
	form.submit();		
}


</script>
</head>

<body style="width:700px;">

<!-- wrap -->
<div id="wrap" class="sub_wrap">
		
	<!-- conteiner -->
	<div id="container">
		<div class="contents join_area" style="margin: 145px 20px 100px;">
		<form name="listFrm" method="get" action="/app/inapp/list.html" >
			<input type="hidden" name="currentPage" id="currentPage" value="1">
			<input type="hidden" name="searchType" id="searchType" value="${inAppList.searchType }">
			<input type="hidden" name="searchValue" id="searchValue" value="${inAppList.searchValue }">
			<input type="hidden" name="storeBundleId" value="${vo.storeBundleId }">
		</form>		
		<form name="appForm" id="appForm" method="post" enctype="multipart/form-data" action="" >
			<input type="hidden" name="storeBundleId" value="${vo.storeBundleId }">
			<input type="hidden" name="fileType" id="fileType" /><!-- 아이콘인지 캡쳐이미지인지 -->
			<input type="hidden" name="saveFileSeq" id="saveFileSeq" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="saveFileName" id="saveFileName" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="fileStatus" id="fileStatus" />		
			<input type="hidden" id="preventSubmit" name="preventSubmit" value="0"/>
			<input type="hidden" id="limitGb"  name="limitGb" value="2"/>
			<!-- 인 앱 콘텐츠 -->
			<h2><spring:message code='app.inapp.write.text1' /></h2>
			
			<div class="section fisrt_section">
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:150px">
							<col style="">
						</colgroup>
						<tr>
								<th scope="row"><label class="title" for="name"><em>*</em> 제목</label></th>
								<td style="width:51%;">
									<input id="inappMetaTitle" name="inappMetaTitle" type="text" value="${ivo.inappName }" style="width:80%;">						
								</td>
								<th style="text-align:right; width:90px;"><label class="title" for="name"><em>*</em> <spring:message code='app.inapp.modify.text4' /></label></th>
								<td>
									<input id="verNum" name="verNum" type="text" value="${ivo.verNum }" style="width:70px;">						
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="name"><em>*</em> 부제목</label></th>
								<td colspan="3">
									<input id="inappMetaSubtitle" name="inappMetaSubtitle" type="text" value="${meta.inappMetaSubtitle }" style="width:95%;">						
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="icon"><em>*</em><spring:message code='app.inapp.modify.text3' /></label></th>
								<td colspan="3">
									<input id="categoryName" name="categoryName" type="text" style="width:79%;" class="line_right" value="${ivo.categoryName }">
									<input id="categorySeq" name="categorySeq" type="hidden" value="${ivo.categorySeq }">
									<a id="categorySearch" href="#categorySearch_" class="btn btnL btn_gray_light line_left"><spring:message code='app.inapp.modify.text5' /></a>
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="name"><em>*</em> 저자</label></th>
								<td colspan="3">
									<input id="inappMetaAuthor" name="inappMetaAuthor" type="text" value="${meta.inappMetaAuthor }" style="width:95%;">						
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="name"><em>*</em> 역자</label></th>
								<td colspan="3">
									<input id="inappMetaTranslator" name="inappMetaTranslator" type="text" value="${meta.inappMetaTranslator }" style="width:95%;">						
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="name"><em>*</em> 쪽수</label></th>
								<td colspan="3">
									<input id="inappMetaPage" name="inappMetaPage" type="text" value="${meta.inappMetaPage }" style="width:95%;">						
								</td>
							</tr>
							<tr>
							<th scope="row"><label class="title" for="name"><em>*</em> 크기</label></th>
								<td colspan="3">
									<input id="inappMetaSize" name="inappMetaSize" type="text" value="${meta.inappMetaSize }" style=" width:95%;">						
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="name"><em>*</em> 정가</label></th>
								<td colspan="3">
									<input id="inappMetaPrice" name="inappMetaPrice" type="text" value="${meta.inappMetaPrice }" style="width:95%;">						
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="name"><em>*</em> ISBN</label></th>
								<td colspan="3">
									<input id="inappMetaISBN" name="inappMetaISBN" type="text" value="${inappMetaISBN[0] }" style="width:46%;">	
									<input id="inappMetaISBN" name="inappMetaISBN" type="text" value="${inappMetaISBN[1] }" style="width:46%;">					
								</td>
							</tr>

							<tr>
								<th scope="row"><label class="title" for="name"><em>*</em> 판매처</label></th>
								<td colspan="3">
									<input id="inappMetaDistributor" name="inappMetaDistributor" type="text" value="${inappMetaDistributor[0] }" style="width:16.5%;">
									<input id="inappMetaDistributor" name="inappMetaDistributor" type="text" value="${inappMetaDistributor[1] }" style="width:16.5%;">		
									<input id="inappMetaDistributor" name="inappMetaDistributor" type="text" value="${inappMetaDistributor[2] }" style="width:16.5%;">		
									<input id="inappMetaDistributor" name="inappMetaDistributor" type="text" value="${inappMetaDistributor[3] }" style="width:16.5%;">		
									<input id="inappMetaDistributor" name="inappMetaDistributor" type="text" value="${inappMetaDistributor[4] }" style="width:16.5%;">								
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="file"> 표지1</label></th>
								<td colspan="3">
									<input id="cover" name="coverFile" type="file" style="width :95%">
									
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="file"> 표지2</label></th>
								<td colspan="3">
									<input id="cover" name="coverFile" type="file" style="width :95%">
									
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="file"> 표지3</label></th>
								<td colspan="3">
									<input id="cover" name="coverFile" type="file" style="width :95%">
									
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="file"> 표지4</label></th>
								<td colspan="3">
									<input id="cover" name="coverFile" type="file" style="width :95%">
									
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="file"> 본문</label></th>
								<td colspan="3">
									<input id="body" name="bodyFile" type="file" style="width :95%">
									
								</td>
							</tr>
						<tr>
							<th scope="row"><label class="title" for="icon"><spring:message code='app.inapp.write.text10' /></label></th>
							<td colspan="3">
								<input id="icon" name="iconFile" type="file">								
								<div class="thumb_area icon_area">
									<ul class="clfix">
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="scrn"><spring:message code='app.inapp.write.text11' /></label></th>
							<td colspan="3">
								<input id="scrn" name="captureFile" type="file">
								
								<div class="thumb_area">
									<ul class="clfix">
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="ex"><spring:message code='app.inapp.modify.text6' /></label></th>
								<td colspan="3">
									<textarea id="descriptionText" name="descriptionText" cols="" rows="4" style="width:95%;">${ivo.descriptionText}</textarea>
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="ex">간략 소개</label></th>
								<td colspan="3">
									<textarea id="inappMetaDescription" name="inappMetaDescription" cols="" rows="4" style="width:95%;">${meta.inappMetaDescription}</textarea>
								</td>
							</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='extend.local.061' /></span></th>
							<td>
								<div class="radio_area">
									<input name="useUserGb" id="um_1" type="radio" value="1" checked="checked"> <label style="margin-right:95px;" for="um_1"><spring:message code='template.modify.027' /></label>
									<input name="useUserGb" id="um_2" type="radio" value="2" disabled="disabled"> <label style="margin:0px;" for="um_2"><spring:message code='extend.local.077' /><span id="useCnt" style="margin-right:5px"></label>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='extend.local.079' /></span></th>
							<td style="width:500px">
								<div class="radio_area">
									<input name="screenType" id="um_1" type="radio" value="1" checked="checked" <c:if test="${ '1' eq ivo.screenType }">checked="checked"</c:if>> <label style="margin-right:81px;" for="um_1"><spring:message code='extend.local.080' /></label>
									<input name="screenType" id="um_2" type="radio" value="2" disabled="disabled" <c:if test="${ '2' eq ivo.screenType }">checked="checked"</c:if>> <label style="margin:0px;" for="um_2"><spring:message code='extend.local.081' /></label>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span class="title"><em>*</em> <spring:message code='app.inapp.write.text14' /></span></th>
							<td colspan="3">
								<div class="radio_area">
									<input name="useGb" id="u_y" type="radio" value="1" checked="checked"> <label for="u_y"><spring:message code='app.inapp.write.text15' /></label>
									<input name="useGb" id="u_n" type="radio" value="2" disabled="disabled"> <label style="margin:0px;" for="u_n"><spring:message code='app.inapp.write.text16' /></label>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span class="title"><em>*</em> <spring:message code='app.inapp.write.text17' /></span></th>
							<td colspan="3">
								<div class="radio_area">
									<input name="completGb" id="c_y" type="radio" value="1" disabled="disabled"> <label for="c_y"><spring:message code='app.inapp.write.text18' /></label>
									<input name="completGb" id="c_n" type="radio" value="2" checked="checked"> <label style="margin:0px;" for="c_n"><spring:message code='app.inapp.write.text19' /></label>
								</div>
							</td>
						</tr>
					</table>
				</div>

				<div class="btn_area_bottom tCenter">
					<a id="registBtn" href="#registBtn_;" class="btn btnL btn_red"><spring:message code='app.inapp.write.text40' /></a>
					<a href="javascript:cancelResist();" class="btn btnL btn_gray_light"><spring:message code='app.inapp.write.text41' /></a>
				</div>
			</div>
			
			<!-- //join_area -->
			</form>
		</div>
	</div>
	<!-- //conteiner -->

</div><!-- //wrap -->

</body>
</html>

<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
<script>
$(document).ready(function(){
	$("input:text").prop('readonly', true);
	$("textarea").prop('readonly', true);
	$('input:radio').each(function(){
		if($(this).attr('name')=='limitGb'){
			return false;
		}
		if(!$(this).is(':checked')){
			$(this).attr('disabled', true);
		}
	});
	$('input:file').hide();
	$('.removeProvBtn, .removeImgBtn').remove();
	$('option').each(function(){
		if(!$(this).is(':selected')){
			$(this).attr('disabled', true);
		}
	});
	

	
});
	function open(){
		return false;
		//alert(1);
	}
</script>
</sec:authorize>