<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>
<script type="text/javascript" src="/js/jquery.form.mini.js" ></script>
</head>
<script type="text/javascript">
$(document).ready(function(){	
	//default
	$("#use_user_pop").hide();
	$("#fm_app_contents_amt").attr("readonly",true);
	$("#fm_app_contents_amt").val(0);
	if(typeof(history.pushState) == 'function'){
		var urlNow = location.href.split("?");
		history.pushState(null, null, urlNow[0]);
	}
	
	$('.btnWrite').click(function(){
		$("#frmWrite").attr('action', '/template/writeOk.html');
		var form = document.getElementById('frmWrite');
		
		//이름이 없을경우
		if (!$('#fm_template_name').val()) {alert("<spring:message code='template.write.032' />");$('#fm_template_name').focus();return}
		if ($('#fm_template_name').val().length > 20){
			alert("20<spring:message code='extend.local.060' />");$('#fm_template_name').focus();return
		}
		//버젼이 없을경우
		if (!$('#fm_ver_num').val()) {alert("<spring:message code='template.write.033' />");$('#fm_ver_num').focus();return}
		//설명이 없을경우
		//if (!$('#fm_description_text').val()) {alert("<spring:message code='template.write.034' />");$('#fm_description_text').focus();return}
		if ($('#fm_template_type_gb').val() == '1'){
			//message : 숫자를 입력해주십시오	
			if (!$('#fm_app_contents_amt').val()) {alert("<spring:message code='template.write.038' />");$('#fm_app_contents_amt').focus();return}
		}
		
		//location.href = "";
		if(confirm("<spring:message code='template.write.035' />")){			
		 	form.submit();
		}
	});

    $("input[name=fm_use_user_gb]").change(function(){  //click 함수
        if($(this).val() == 1){$("#use_user_pop").hide();
        	$("#useCnt").text("(0명)");$("#useS").val('');
    	}else{$("#use_user_pop").show();}
    });

    $("input[name=fm_app_contents_gb]").change(function(){  //click 함수
        if($(this).val() == 1){$("#fm_app_contents_amt").attr("readonly",false);$("#fm_app_contents_amt").val('2');
    	}else if($(this).val() == 2){$("#fm_app_contents_amt").attr("readonly",false);$("#fm_app_contents_amt").val('2');
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
			alert("<spring:message code='template.write.036' />");
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
			alert("<spring:message code='template.write.036' />")
			return false;
		}
		$('#frmWrite').attr('action', actionUrl);
		$('#fileType').val(fileType);
		$(this).after('<p id="uploadPercent"><p>');
		ajaxFileUpload();
	});
	//파일 업로드 & 리턴
	function ajaxFileUpload(){
		$('#frmWrite').ajaxForm({

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
					//message : 아이콘 썸네일 이미지 닫기
					li += '<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='template.write.040' />"></a></li>'; 
					$('[name='+fileType+']').parents('td').find('ul').append(li);
				}else{
					alert(error);
				}
				//$('input[type=file]').val('');
				$('input[name=captureFile]').val('');
			}
	    });
		$('#frmWrite').submit();
	}
	//이미지 파일 삭제
	$(document).on('click', '.removeImgBtn', function(){
		var that = $(this);
		var saveFileName = $(this).parents('li').find('input[name*=SaveFile]').val();
		$('#saveFileName').val(saveFileName);
		$('#fileStatus').val('temp');
		var action_url = '/template/deletetmpimg.html';
		$.post(action_url, $('#frmWrite').serialize(), function(data){
			//action;
			if(data.error!='none'){
				alert(data.error);
			}
			$(that).parent().remove();
		}, "json");
	});
	
	//파일 확장자체크
	$('#zipFile').change(function(e){
		var filename = $(this).val();
		var dot = filename.lastIndexOf(".");
		var ext = filename.substring(dot+1).toLowerCase();
		//alert('['+ext+']');
		if(!(ext=='zip'||ext=='ZIP')){
			alert("<spring:message code='template.write.037' />");
			$(this).val('');
		}
	});	

	$("#fm_template_type_gb").change(function(){
		if($("#fm_template_type_gb").val() == "1"){
			$("#cq_1, #cq_2, #cq_3").show();
			$("#fm_app_contents_amt").val("0");
			$("[name=fm_app_contents_gb]").val("3");
			$("#cq_3").prop("checked", true);
		}else if($("#fm_template_type_gb").val() == "2"){
			$("#cq_1, #cq_2, #cq_3").hide();
			$("#fm_app_contents_amt").val("1");
			$("#fm_app_contents_amt").attr("readonly", true);
			$("[name=fm_app_contents_gb]").val("0");
		}
	});

	
	$("#fm_app_contents_amt").focusout(function(){
		if($("[name=fm_template_type_gb]").val() == '1' && !($("[name=fm_app_contents_gb]:checked").val()=="3")){
			if( !($(this).is('[readonly]')) && (parseInt($("#fm_app_contents_amt").val()) < 2 || $("#fm_app_contents_amt").val() == "")){
				//message : 2 이상 숫자를 입력해 주십시오
				alert("<spring:message code='template.write.039' />");
				$("#fm_app_contents_amt").val('2');
			}
		}
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
			<!-- 템플릿 등록 -->
			<h2><spring:message code='template.write.001' /></h2>
			<form method="post" name="frmWrite" id="frmWrite" enctype="multipart/form-data" action="" >
			<input type="hidden" name="useS" id="useS" value="" />			
			
			<input type="hidden" name="fileType" id="fileType" /><!-- 아이콘인지 캡쳐이미지인지 -->
			<input type="hidden" name="saveFileSeq" id="saveFileSeq" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="saveFileName" id="saveFileName" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="fileStatus" id="fileStatus" />	
			<input type="hidden" name="status" id="status" value="${status }">		
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
							<th scope="row"><label class="title" for="fm_template_name"><em>*</em> <spring:message code='template.write.002' /></label></th>
							<td colspan="3">
								<input id="fm_template_name" name="fm_template_name" type="text" style="width:94.7%;">						
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="fm_ostype_gb"><em>*</em> <spring:message code='template.write.003' /></label></th>
							<td>
								<select id="fm_ostype_gb" name="fm_ostype_gb" style="width:150px;">
									<option value="2" selected><spring:message code='template.write.005' /></option>
									<option value="3"><spring:message code='template.write.006' /></option>
									<option value="1"><spring:message code='template.write.004' /></option>
									<option value="4"><spring:message code='template.write.007' /></option>
								</select>
							</td>
							<th style="text-align:right; width:150px;" scope="row"><label class="title" for="fm_ver_num"><em>*</em> <spring:message code='template.write.008' /></label></th>
							<td>
								<input id="fm_ver_num" name="fm_ver_num" type="text" style="width:80%;">
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="fm_template_type_gb"><em>*</em> <spring:message code='template.write.009' /></label></th>
							<td colspan="3">
								<select id="fm_template_type_gb" name="fm_template_type_gb" style="width:150px">
									<option value="1"><spring:message code='template.write.010' /></option>
									<option value="2"><spring:message code='template.write.011' /></option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="fm_app_contents_amt"><em>*</em> <spring:message code='template.write.012' /></label></th>
							<td colspan="3">
								<span class="category_area">
									<input id="fm_app_contents_amt" class="AMT tCenter" name="fm_app_contents_amt" value="" type="text" readonly style="width:138px;">
								</span>
								<div style="display:inline;" class="radio_area radio_area_type2">
									<input name="fm_app_contents_gb" id="cq_1" type="radio" value="1"> <label id="cq_1" for="cq_1"><spring:message code='template.write.013' /></label>
									<input name="fm_app_contents_gb" id="cq_2" type="radio" value="2"> <label id="cq_2" for="cq_2"><spring:message code='template.write.014' /></label>
									<input name="fm_app_contents_gb" id="cq_3" type="radio" value="3" checked="checked"> <label id="cq_3" for="cq_3"><spring:message code='template.write.015' /></label>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="fm_description_text"><spring:message code='template.write.016' /></label></th>
							<td colspan="3">
								<textarea id="fm_description_text" name="fm_description_text" cols="" rows="4" style="width:95%;"></textarea>
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for=""fm_file""><spring:message code='template.write.017' /></label></th>
							<td colspan="3">
								<input id="zipFile" name="zipFile" type="file" style="width:95%;">
								<!-- <input id=""fm_file"" name="fm_file" type="file" style="width:95%;"> -->
								<!-- 
								<input id="icon" name="iconFile" type="file" style="width:95%;">			
								<div class="thumb_area icon_area">
									<ul class="clfix">

									</ul>
								</div>
								 -->								
							</td>
						</tr>
						<tr>
							<th scope="row"><label class="title" for="scrn"><spring:message code='template.write.020' /></label></th>
							<td colspan="3">
								<input id="scrn" name="captureFile" type="file" style="width:95%;">								
								<div class="thumb_area">
									<ul class="clfix"></ul>
									
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span class="title"><em>*</em> <spring:message code='template.write.023' /></span></th>
							<td colspan="3">
								<div class="radio_area">
									<input name="fm_use_gb" id="u_y" type="radio" value="1" disabled>  <label for="u_y"><spring:message code='template.write.024' /></label>
									<input name="fm_use_gb" id="u_n" type="radio" value="2" checked> <label for="u_n"><spring:message code='template.write.025' /></label>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span class="title"><em>*</em> <spring:message code='template.write.026' /></span></th>
							<td colspan="3">
								<div class="radio_area">
									<input name="fm_use_user_gb" id="um_1" type="radio" value="1" checked="checked"> <label for="um_1"><spring:message code='template.write.027' /></label>
									<input name="fm_use_user_gb" id="um_2" type="radio" value="2" disabled> <label for="um_2"><spring:message code='template.write.028_1' /><span id="useCnt" style="margin-right:5px">(0<spring:message code='template.write.028_2' />)</span></label>
									<a href="#" id="use_user_pop" class="btn btnL btn_gray_light"><spring:message code='template.write.029' /></a>
								</div>
							</td>
						</tr>
					</table>
				</div>
				</form>
				<div class="btn_area_bottom tCenter">
					<a href="#1" class="btn btnL btn_red btnWrite"><spring:message code='template.write.030' /></a>
					<a href="/template/list.html?status=${status}" class="btn btnL btn_gray_light"><spring:message code='template.write.031' /></a>
				</div>
			</div>
			
			<!-- //템플릿 등록 -->
		</div>
	</div>
	<!-- //conteiner -->
	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>