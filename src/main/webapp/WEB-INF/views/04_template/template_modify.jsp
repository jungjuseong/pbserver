<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>
<script type="text/javascript" src="/js/jquery.form.mini.js" ></script>
</head>
<script type="text/javascript">
$(document).ready(function(){	
	//default
	var useUserVal = '${tempView.useUserGb}';
	if(useUserVal == 1){
		$("#use_user_pop").hide();
	}else if(useUserVal == 2){
		$("#use_user_pop").show();
	}
	var useVal = '${tempView.useGb}';
	
	if(useVal == 1){
		$("#ostype_gb").attr("disabled",true);
		$("#template_type_gb").attr("disabled",true);
		$("#ostype_gb").css({"background-color":"#EEEEEE"});
		$("#template_type_gb").css({"background-color":"#EEEEEE"});
	}else{
		$("#ostype_gb").attr("disabled",false);
		$("#template_type_gb").attr("disabled",false);
		$("#ostype_gb").css({"background-color":""});
		$("#template_type_gb").css({"background-color":""});
	}

	var appContentsGb = '${tempView.appContentsGb}';
	if(appContentsGb == 1){
		$("#fm_app_contents_amt").attr("readonly",false);
	}else if(appContentsGb == 2){
		$("#fm_app_contents_amt").attr("readonly",false);
	}else if(appContentsGb == 3){
		$("#fm_app_contents_amt").attr("readonly",true);
	}
	
	$('#btnModify').click(function(){
		$("#frmModify").attr('action', '/template/modifyOk.html');
		var form = document.getElementById('frmModify');
		
		if (!$('#fm_template_name').val()) {alert("<spring:message code='template.modify.035' />");$('#fm_template_name').focus();return}
		if (!$('#fm_ver_num').val()) {alert("<spring:message code='template.modify.036' />");$('#fm_ver_num').focus();return}		
		if (!$('#fm_description_text').val()) {alert("<spring:message code='template.modify.037' />");$('#fm_description_text').focus();return}
		if ($('#fm_template_type_gb').val() == '1'){
			if (!$('#fm_app_contents_amt').val()) {alert("<spring:message code='template.modify.043' />");$('#fm_app_contents_amt').focus();return}
		}
		
		//location.href = "";
		if(confirm("<spring:message code='template.modify.038' />")){			
		 	form.submit();		 		
		}
	});
	
    $("input[name=fm_use_gb]").change(function(){  //click 함수
    	var ostype   = $("#ostype_gb option:selected").val();
    	var temptype = $("#template_type_gb option:selected").val();

    	if($("input[name=fm_use_gb]:checked").val() == 1){
    		$("#ostype_gb").attr("disabled",true);
    		$("#template_type_gb").attr("disabled",true);
    		$("#ostype_gb").css({"background-color":"#EEEEEE"});
    		$("#template_type_gb").css({"background-color":"#EEEEEE"});
    		$("#fm_ostype_gb").val(ostype);
    		$("#fm_template_type_gb").val(temptype); 		
    	}else{
    		$("#ostype_gb").attr("disabled",false);
    		$("#template_type_gb").attr("disabled",false);
    		$("#ostype_gb").css({"background-color":""});
    		$("#template_type_gb").css({"background-color":""});
    		$("#fm_ostype_gb").val(ostype);
    		$("#fm_template_type_gb").val(temptype);   		
    	}   		    
    });
    
    $(document).on('change','#ostype_gb', function(){
    	var ostype   = $("#ostype_gb option:selected").val();
    	$("#fm_ostype_gb").val(ostype);
    });	
    
    $(document).on('change','#template_type_gb', function(){
    	var temptype = $("#template_type_gb option:selected").val();
		$("#fm_template_type_gb").val(temptype); 
    });	    
    
    $("input[name=fm_use_user_gb]").change(function(){  //click 함수
        if($(this).val() == 1){$("#use_user_pop").hide();$("#useCnt").text("(0<spring:message code='template.modify.028_2' />)");$("#useS").val('');
    	}else{$("#use_user_pop").show();$("#useCnt").text("(${UserCnt}<spring:message code='template.modify.028_2' />)");$("#useS").val('${useSelVal}');}    	
    });    
    
    
    $("input[name=fm_app_contents_gb]").change(function(){  //click 함수
        if($(this).val() == 1){$("#fm_app_contents_amt").attr("readonly",false);$("#fm_app_contents_amt").val('');
    	}else if($(this).val() == 2){$("#fm_app_contents_amt").attr("readonly",false);$("#fm_app_contents_amt").val('');
    	}else if($(this).val() == 3){$("#fm_app_contents_amt").attr("readonly",true);$("#fm_app_contents_amt").val(0);
    	}    	
    });
    
    $('#use_user_pop').click(function(){
    	popup();
    });    
    
	//파일 선택시 이미지 카운트
	//$('input[type=file][name=iconFile], input[type=file][name=captureFile]').click(function(){
	$('input[type=file][name=captureFile]').click(function(){	
		var fileType = $(this).attr("name");
		var maxImgCnt = 1;
		var imgCnt = $(this).parent().find('ul').find('li').size();
		if(fileType=='captureFile'){
			actionUrl = '/template/capturefileupload.html';
			maxImgCnt = 5;
		}
		if(maxImgCnt==imgCnt){
			alert("<spring:message code='template.modify.039' />");
			return false;
		}
	});
	//파일 선택 이벤트
	//$('input[type=file][name=iconFile], input[type=file][name=captureFile]').change(function(){
	$('input[type=file][name=captureFile]').change(function(){	
		var fileType = $(this).attr("name");
		var actionUrl = '/template/iconfileupload.html';
		var maxImgCnt = 1;
		var imgCnt = $(this).parent().find('ul').find('li').size();
		if(fileType=='captureFile'){
			actionUrl = '/template/capturefileupload.html';
			maxImgCnt = 5;
		}
		if(maxImgCnt==imgCnt){
			alert("<spring:message code='template.modify.039' />")
			return false;
		}
		$('#frmModify').attr('action', actionUrl);
		$('#fileType').val(fileType);
		$(this).after('<p id="uploadPercent"><p>');
		ajaxFileUpload();
	});
	//파일 업로드 & 리턴
	function ajaxFileUpload(){
		$('#frmModify').ajaxForm({

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
					if(type=='img'){
						li += '<input type="hidden" name="captureSeq" value="0"/>';						
					}
					li += '<input type="hidden" name="'+type+'OrgFile" value="'+orgFileName+'"/>'; 
					li += '<input type="hidden" name="'+type+'SaveFile" value="'+saveFileName+'"/>'; 
					li += '<input type="hidden" name="thisFileType" value="temp"/>'; 					
					li += '<img src="'+webPath+'" alt="'+orgFileName+'">'; 
					li += '<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='template.modify.045' />"></a></li>'; 
					$('[name='+fileType+']').parents('td').find('ul').append(li);				
				}else{
					alert(error);
				}
				//$('input[type=file]').val('');
				$('input[name=captureFile]').val('');
			}	
	    }); 
		$('#frmModify').submit();
	}
	/*
	//이미지 파일 삭제
	$(document).on('click', '.removeImgBtn', function(){
		var that = $(this);
		var saveFileName = $(this).parents('li').find('input[name*=SaveFile]').val();
		$('#saveFileName').val(saveFileName);
		$('#fileStatus').val('temp');
		var action_url = '/template/deletetmpimg.html';
		$.post(action_url, $('#frmModify').serialize(), function(data){
			//action;
			if(data.error!='none'){
				alert(data.error);
			}
			$(that).parent().remove();
		}, "json");
	});
	*/
	
	//이미지 파일 삭제
	$(document).on('click', '.removeImgBtn', function(){
		var that = $(this);
		var saveFileName = $(this).parents('li').find('input[name*=SaveFile]').val();
		var thisFileType = $(this).parents('li').find('input[name*=thisFileType]').val();
		$('#saveFileName').val(saveFileName);
		
		var captureSeq = 0;
		if($(this).parents('li').find('[name=captureSeq]').size()>0){
			captureSeq = $(this).parents('li').find('[name=captureSeq]').val()
		}
		if(thisFileType=='temp'){//방금올린파일
		//if(captureSeq == 0){//방금올린파일
			$('#fileStatus').val('temp');
			var action_url = '/template/deletetmpimg.html';
			$.post(action_url, $('#frmModify').serialize(), function(data){
				//action;
				if(data.error!='none'){
					alert(data.error);
				}
				$(that).parent().remove();
			}, "json");
		}else{//불러온파일 화면에서 삭제하고 삭제리스트에 쌓는다
			//deleteFileList
			//captureSeq
			var html = '<input typd="hidden" name="deleteFileSeq" value="'+captureSeq+'"/>';
			html += '<input typd="hidden" name="deleteSaveFileName" value="'+saveFileName+'"/>';
			html += '<input typd="hidden" name="deleteFileType" value="'+thisFileType+'"/>';
			$('#deleteFileList').append(html);
			$(that).parent().remove();
		}
	});	
	
	$(document).on('click', '.removeImgBtn2', function(){
		var that = $(this);
		if(confirm("<spring:message code='template.modify.040' />\n<spring:message code='template.modify.041' />")){
			var action_url = '/template/deleteupload.html';
			$.post(action_url, $('#frmModify').serialize(), function(data){
				//action;
				if(data.error!='none'){
					alert(data.error);
				}
				//$(that).parent().remove();
				$(that).remove();
				$("#uploadSaveName").remove();
				$("#uploadInput").empty();
				var html = "";
				html +="<input id='zipFile' name='zipFile' type='file'>";
				html +="<input type='hidden' name='zipOrgFile'  value=''/>";
				html +="<input type='hidden' name='zipSaveFile' value=''/>";				
				$("#uploadInput").append(html);
			}, "json");
		}	
	});
	
	//파일 확장자체크
	$('#zipFile').change(function(e){
		var filename = $(this).val();
		var dot = filename.lastIndexOf(".");
		var ext = filename.substring(dot+1).toLowerCase();
		
		if(!(ext=='zip'||ext=='ZIP')){
			alert('<spring:message code='template.modify.042' />');
			$(this).val('');
		}
	});
	
	$("#fm_app_contents_amt").focusout(function(){
		if( !($(this).is('[readonly]')) && (parseInt($("#fm_app_contents_amt").val()) < 2 || $("#fm_app_contents_amt").val() == "")){
			//message : 2 이상 숫자를 입력해 주십시오.
			alert("<spring:message code='template.modify.044' />");
			$("#fm_app_contents_amt").val('2');
		}
	});

	//20180219 : lsy - downloadTemplate
	$('#uploadSaveName').click(function () {
		$.ajax({
		    type: 'HEAD',
		    url: "${path}${tempView.uploadSaveFile}",
		    success: function() {
		    	window.location.href="${path}${tempView.uploadSaveFile}";
				console.log("${path}${tempView.uploadSaveFile}");
		    },  
		    error: function() {
		        alert('<spring:message code='down.list.030' />');
		    }
		});
	});
});	

function popup(){
	var useM_Val = $("#useS").val();
	useM_Val = useM_Val.replace(/^.(\s+)?/, ""); 
	useM_Val = useM_Val.replace(/(\s+)?.$/, "");
  	 //var frm     = document.popFrm;
  	 var target_ = "userPop";
  	 window.open("/template/user_pop.html?useS="+useM_Val,target_,"width=670, height=403, top=100, left=100, resizable=no, menubar=no, scrollbars=no");
  	 //frm.target  = target_;
  	 //frm.method  = "post";
  	 //frm.action  = "/template/user_pop.html";
  	 //frm.submit();
}

//20180219 : lsy - deleteTemplate
function templateDelete(){
	if(confirm("<spring:message code='user.list.027' />")){
		document.frmModify.action='/template/deleteTemplate.html';//single app 삭제
		document.frmModify.submit();
	}
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
			<!-- 템플릿 수정 -->
			<h2><spring:message code='template.modify.001' /></h2>
			<form method="post" name="frmModify" id="frmModify" enctype="multipart/form-data" action="" >			
			<input type="hidden" name="sh_field"   			id="sh_field"  		 	 value="${shField}" >
			<input type="hidden" name="sh_keyword" 			id="sh_keyword" 		 value="${shKeyword}" >					
			<input type="hidden" name="templateSeq" 		id="templateSeq" 		 value="${tempView.templateSeq}" />	
			<input type="hidden" name="fm_ostype_gb"   	    id="fm_ostype_gb" 		 value="${tempView.ostypeGb}" />
			<input type="hidden" name="fm_template_type_gb" id="fm_template_type_gb" value="${tempView.templateTypeGb}" />
			
			<input type="hidden" name="useS" id="useS" value="${useSelVal}" />		
			
			<input type="hidden" name="fileType" id="fileType" /><!-- 아이콘인지 캡쳐이미지인지 -->
			<input type="hidden" name="saveFileSeq" id="saveFileSeq" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="saveFileName" id="saveFileName" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="fileStatus" id="fileStatus" />
			<input type="hidden" name="status" id="status" value="${status }">
			
			<div id="deleteFileList" style="display:none;">
			</div>
			<div class="section fisrt_section">
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:120px">
							<col style="">
							<col style="width:70px">
							<col style="width:150px">
						</colgroup>
						<tr>
							<th scope="row"><label class="title" for="fm_template_name"><em>*</em> <spring:message code='template.modify.002' /></label></th>
							<td colspan="3">
								<input id="fm_template_name" name="fm_template_name" type="text" value="${tempView.templateName}" style="width:94.7%;">						
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="ostype_gb"><em>*</em> <spring:message code='template.modify.003' /></label></th>
							<td>
								<select id="ostype_gb" name="ostype_gb" style="width:150px;">
									<option value="2" <c:if test="${tempView.ostypeGb == 2}">selected="selected"</c:if>><spring:message code='template.modify.005' /></option>
									<option value="3" <c:if test="${tempView.ostypeGb == 3}">selected="selected"</c:if>><spring:message code='template.modify.006' /></option>
									<option value="1" <c:if test="${tempView.ostypeGb == 1}">selected="selected"</c:if>><spring:message code='template.modify.004' /></option>
									<option value="4" <c:if test="${tempView.ostypeGb == 4}">selected="selected"</c:if>><spring:message code='template.modify.007' /></option>
								</select>
							</td>
							<th style="text-align:center; width:150px;" scope="row"><label class="title" for="fm_ver_num"><em>*</em> <spring:message code='template.modify.011' />&nbsp;</label></th>
							<td>
								<input id="fm_ver_num" name="fm_ver_num" type="text" value="${tempView.verNum}" style="width:80%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="template_type_gb"><em>*</em> <spring:message code='template.modify.008' /></label></th>
							<td colspan="3">
								<select id="template_type_gb" name="template_type_gb" style="width:150px">
									<option value="1" <c:if test="${tempView.templateTypeGb == 1}">selected="selected"</c:if>><spring:message code='template.modify.009' /></option>
									<option value="2" <c:if test="${tempView.templateTypeGb == 2}">selected="selected"</c:if>><spring:message code='template.modify.010' /></option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="fm_app_contents_amt"><em>*</em> <spring:message code='template.modify.012' /></label></th>
							<td colspan="3">
								<span class="category_area">
									<input id="fm_app_contents_amt" class="AMT tCenter" name="fm_app_contents_amt" type="text" value="${tempView.appContentsAmt}" readonly style="width:138px;">
								</span>
								<c:if test="${tempView.templateTypeGb == 1}">
								<div style="display:inline;" class="radio_area radio_area_type2">
									<input name="fm_app_contents_gb" id="cq_1" type="radio" value="1" <c:if test="${tempView.appContentsGb == 1}">checked="checked"</c:if>> <label for="cq_1"><spring:message code='template.modify.013' /></label>
									<input name="fm_app_contents_gb" id="cq_2" type="radio" value="2" <c:if test="${tempView.appContentsGb == 2}">checked="checked"</c:if>> <label for="cq_2"><spring:message code='template.modify.014' /></label>
									<input name="fm_app_contents_gb" id="cq_3" type="radio" value="3" <c:if test="${tempView.appContentsGb == 3}">checked="checked"</c:if>> <label for="cq_3"><spring:message code='template.modify.015' /></label>
								</div>
								</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="fm_description_text"><spring:message code='template.modify.016' /></label></th>
							<td colspan="3">
								<textarea id="fm_description_text" name="fm_description_text" cols="" rows="4" style="width:95%;">${tempView.descriptionText}</textarea>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="fm_file"><spring:message code='template.modify.017' /></label></th>
							<td>
								<div class="thumb_area" id="uploadInput">
									<c:if test="${empty tempView.uploadSaveFile}">
										<input id="zipFile" name="zipFile" type="file">
									</c:if>
									<c:if test="${not empty tempView.uploadSaveFile}">
										<input id="zipFile"  name="zipFile"     type="file" style="display: none;">
										<input type="hidden" name="zipOrgFile"  value="${tempView.uploadOrgFile}"/>
										<input type="hidden" name="zipSaveFile" value="${tempView.uploadSaveFile}"/>
										<span id="uploadSaveName" class="template_down">${tempView.uploadSaveFile}</span>						<!--message : 아이콘 썸네일 이미지 닫기  -->
										<a class="removeImgBtn2" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='template.modify.045' />"></a>
									</c:if>
								</div>															
							</td>
							<th style="text-align:center; width:150px;" scope="row"><label class="title" ><spring:message code='template.modify.046' /></label></th>
							<td>
								${tempView.templateSeq}
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="scrn"><spring:message code='template.modify.020' /></label></th>
							<td colspan="3">
								<input id="scrn" name="captureFile" type="file" style="width:95%;">								
								<div class="thumb_area">
									<ul class="clfix">
										<c:if test="${not empty captureList}">
										<c:forEach var="result" items="${captureList }" varStatus="status">
										<li>
											<input type="hidden" name="captureSeq" value="${result.captureSeq }"/>
											<input type="hidden" name="imgOrgFile" value="${result.imgOrgFile }"/>
											<input type="hidden" name="imgSaveFile" value="${result.imgSaveFile }"/>
											<input type="hidden" name="thisFileType" value="capture"/>						
											<img src="<spring:message code='file.upload.path.template.capture.file' />${result.imgSaveFile}" alt=""><!--message : 아이콘 썸네일 이미지 닫기  -->
											<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='template.modify.045' />"></a>
										</li>										
										</c:forEach>
										</c:if>									
									</ul>									
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span class="title"><em>*</em> <spring:message code='template.modify.023' /></span></th>
							<td colspan="3">
								<div class="radio_area">
									<input name="fm_use_gb" id="u_y" type="radio" value="1" <c:if test="${tempView.useGb == 1}">checked="checked"</c:if>>  <label for="u_y"><spring:message code='template.modify.024' /></label>
									<input name="fm_use_gb" id="u_n" type="radio" value="2" <c:if test="${tempView.useGb == 2}">checked="checked"</c:if>> <label for="u_n"><spring:message code='template.modify.025' /></label>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span class="title"><em>*</em> <spring:message code='template.modify.026' /></span></th>
							<td colspan="3">
								<div class="radio_area">
									<input name="fm_use_user_gb" id="um_1" type="radio" value="1" <c:if test="${tempView.useUserGb == 1}">checked="checked"</c:if>> <label for="um_1"><spring:message code='template.modify.027' /></label>
									<input name="fm_use_user_gb" id="um_2" type="radio" value="2" <c:if test="${tempView.useUserGb == 2}">checked="checked"</c:if>> <label for="um_2"><spring:message code='template.modify.028_1' /><span id="useCnt" style="margin-right:5px">(${UserCnt}<spring:message code='template.modify.028_2' />)</span></label>
									<a href="#" id="use_user_pop" class="btn btnL btn_gray_light"><spring:message code='template.modify.029' /></a>
								</div>
							</td>
						</tr>
						<%-- <tr>
							<th scope="row"><span class="title"><em>*</em> <spring:message code='template.modify.015' /></span></th>
							<td colspan="3">
								<div class="radio_area">
									<input name="fm_limit_gb" id="l_y" type="radio" value="1" <c:if test="${tempView.limitGb == 1}">checked="checked"</c:if>> <label for="l_y"><spring:message code='template.modify.024' /></label>
									<input name="fm_limit_gb" id="l_n" type="radio" value="2" <c:if test="${tempView.limitGb == 2}">checked="checked"</c:if>> <label for="l_n"><spring:message code='template.modify.025' /></label>
								</div>
							</td>
						</tr> --%>
					</table>
				</div>
				</form>

				<div class="btn_area_bottom tCenter">
					<a href="#" class="btn btnL btn_red" id="btnModify"><spring:message code='template.modify.033' /></a>
					<a href="/template/list.html?status=${status}" class="btn btnL btn_gray_light"><spring:message code='template.modify.034' /></a>
					<a href="javascript:templateDelete();" class="btn btnL btn_red" ><spring:message code='template.modify.047' /></a>
				</div>
			</div>
			
			<!-- //템플릿 수정 -->
		</div> 
	</div>
	<!-- //conteiner -->
	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>