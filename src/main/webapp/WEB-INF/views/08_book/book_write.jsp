<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>
<%@ page session="true" %>
<sec:authorize access="isAuthenticated()">  
	<sec:authentication property="principal.memberVO.companySeq" var="companySeq" />
</sec:authorize>
<script type="text/javascript" src="/js/jquery.form.mini.js" ></script>
<script src="/js/jquery.validate.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	if("${param.regGb}" == 1)$("#appContentsAmt").val("2");
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
			alert("<spring:message code='app.write.text43' />")
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
			alert("<spring:message code='app.write.text43' />");
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
					li += '<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.write.text44' />"></a></li>'; 
					$('[name='+fileType+']').parents('td').find('ul').append(li);				
				}else{
					alert(error);
				}
				$('input[type=file]').val('');
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
	//호환성 선택시
	var preOstype;
	var preOstype1;
	var preOstype2;
	$('select[name=ostype]').on('focus', function(){
		preOstype = $(this).val();
		preOstype1 = "ios";
		if(preOstype=='4'){
			preOstype1 = "android";
		}
	}).change(function(){
		preOstype = $(this).val();
		preOstype2 = "ios";
		
		$('[name=templateSeq]').val('');
		$('[name=templateName]').val('');
		$('[name=cappContentsAmt]').val('');
		$('[name=cappContentsGb]').val('');
		
		/*cappContentsAmt와 maxTemplateContentsAmt는 사실상 같은거임..
		   서로 다른 사람이 수정하다보니 새로 field가 불필요하게 추가가됨.*/
		$('[name=maxTemplateContentsAmt]').val('');
		$('[name=maxTemplateContentsGb]').val('');
		if(preOstype=='4'){
			preOstype2 = "android";
		}
		if(preOstype1!==preOstype2){
			$('#storeBundleId1').prop('readonly', false);
			$('#storeBundleId2').val('*').prop('readonly', true);
		}
		var distrProfile = "MobileProvision";
		if($(this).val()=='4'){
			distrProfile = '<spring:message code="app.write.text16" />';
			$('.MobileProvision').parents('tr').hide();
			$('.provarea').find('ul').html('');
			$('#storeBundleId1').prop('readonly', false);
			$('#storeBundleId2').prop('readonly', false);
			$('#versionCode').parents('tr').show();
			$('#versionCode').val('');
			$('#versionCode').attr('disabled', false);
			$('.versionName').find('label').html("<em>*</em> <spring:message code='app.write.text22.1' />")
		}else{
			$('.MobileProvision').parents('tr').show();
			$('#versionCode').parents('tr').hide();
			$('#versionCode').val('undefined');
			$('#versionCode').attr('disabled', true);
			$('.versionName').find('label').html("<em>*</em> <spring:message code='app.write.text22' />")
			
		}
		//$('.detail_area.identity_area').find('a').html(distrProfile);
 		$('.MobileProvision').find('label').html(distrProfile);
	});

	var provisionPopup;
	//프로비전 검색팝업
	var winName = 'MobileProvision';
	$('.detail_area.identity_area').find('a').click(function(){
		var winWidth = 670;
		var winHeight = 446;
		var winPosLeft = (screen.width - winWidth)/2;
		var winPosTop = (screen.height - winHeight)/2;
		//var distrProfile = $('.detail_area.identity_area').find('a').text();
		var distrProfile = $('.MobileProvision').find('label').text();
		var provId = '';
		var storeBundleId1 = $('#storeBundleId1').val();
		var storeBundleId2 = $('#storeBundleId2').val();
		if(storeBundleId2==''){
			storeBundleId2 = '*';
		}		
		if(storeBundleId1&&storeBundleId2){
			provId = storeBundleId1+storeBundleId2;			
		}
		var url = "/app/provision/list.html?distrProfile=" + distrProfile+"&provId="+provId+"&appSeq="+0;		
		var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
		//if(!provisionPopup){
			provisionPopup = window.open(url, winName, opt);			
		//}
	});

	$('#storeBundleId1').focusout(function(){
		addstoreBundleId1Dot();
	});

	function addstoreBundleId1Dot(){
		var storeBundleId1 = $('#storeBundleId1').val();
		if(storeBundleId1.length>1){
			//var lastDotIndex = storeBundleId1.lastIndexOf('.');
			var storeBundleId1Length = storeBundleId1.length-1;
			if(storeBundleId1.charAt(storeBundleId1Length)!='.'){
				$('#storeBundleId1').val($('#storeBundleId1').val()+'.');
			}
		}
	}

	//provId 이벤트
	$('#storeBundleId2').bind('keyup', function(){
		if(!validateStoreBundleId2()){
		}
	});

	//provId두번째 부분 검증
	function validateStoreBundleId2(){
		var storeBundleId2 = $('#storeBundleId2').val();
		var pattern = /^([0-9a-zA-Z]+)(\.[0-9a-zA-Z]+){0,99}$/; 
		var huk = pattern.test(storeBundleId2); 
		if(!huk){
			alert("<spring:message code='app.write.text61' />");
			$('#storeBundleId2').val("");
			/* $('#storeBundleId2').focus(); */
		}
		return huk;
	}

	//template 검색팝업
	//var templatePopup;
	var winName = 'TemplateProvision';
	$('[name=templateName], #templateNameBtn').focus(function(){
		if($("#appContentsAmt").val().length == 0){
			//message : 콘텐츠 수량을 입력해 주세요.
			alert("<spring:message code='app.write.text46' />");
			$("#appContentsAmt").focus();
		}else{
			$('#templateNameBtn').focus().click();
		}
	});

	/* //input창에 변화가 올때 check 해줄수 있는 method
	$.event.special.inputchange = {
		    setup: function() {
		        var self = this, val;
		        $.data(this, 'timer', window.setInterval(function() {
		            val = self.value;
		            if ( $.data( self, 'cache') != val ) {
		                $.data( self, 'cache', val );
		                $( self ).trigger( 'inputchange' );
		            }
		        }, 20));
		    },
		    teardown: function() {
		        window.clearInterval( $.data(this, 'timer') );
		    },
		    add: function() {
		        $.data(this, 'cache', this.value);
		    }
		};
	
	// template input창에 text가 들어갔을떄의 trigger
	$("#templateName").on('inputchange', function(){
		if($("[name=templateName]").val().length !== 0){

		}
	}); */

	$('#templateNameBtn').click(function(){
		var winWidth = 670;
		var winHeight = 740;
		var winPosLeft = (screen.width - winWidth)/2;
		var winPosTop = (screen.height - winHeight)/2;
		var ostypeGb = $('[name=ostype]').val();
		var appContentsAmt = $("#appContentsAmt").val();
		var templateTypeGb = '${appVO.regGb }';
		var appContentsGb = $("[name=appContentsGb]:checked").val();
		var url = "/app/template/list.html?currentPage=1&templateTypeGb=" + templateTypeGb + "&ostypeGb=" + ostypeGb + "&appContentsAmt=" + appContentsAmt +"&appContentsGb=" + appContentsGb;		
		var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
		//if(!templatePopup){
			templatePopup = window.open(url, winName, opt);			
		//}
	});

	//카테고리검색팝업 작업중
	$('#categorySearch').click(function(){
		alert('<spring:message code='app.write.text45' />');
	});

	//inapp search popup
	$('#inAppContentsSearch').click(function(){
		alert('<spring:message code='app.write.text45' />');
	});

	$('#registBtn').click(function(e){	
		var form = document.getElementById('appForm');
	 	if($("#preventSubmit").val() == '0'){

			$("#appForm").attr('action', '/book/regist.html');
			var isVaid = false;
			isVaid = customValidate({
				 	 rules: {
				    	appContentsAmt:{
				    		required: true,
				    		maxlength : 10,
				    		number : true
				    	},
				    	appName:{
				    		required: true,
				    		maxlength : 50
				    	},    	
				    	fileName:{
				    		required: true,
				    		maxlength : 50
				    	},    	
				    	verNum:{
				    		required: true,
				    		maxlength : 20
				    	},
				    	versionCode : {
				    		required: true
				    	}
				    },
				    messages: {
				    	appContentsAmt: {
							required: "<spring:message code='app.write.text46' />",
							maxlength: "<spring:message code='app.write.text47' />",
							number : "<spring:message code='app.write.text48' />"
						},
						appName: {
							required: "<spring:message code='app.write.text49' />",
							maxlength : "<spring:message code='app.write.text50' />"
						},
						fileName: {
							required: "<spring:message code='app.write.text51' />",
							maxlength : "<spring:message code='app.write.text50' />"
						},
				    	verNum:{
							required: "<spring:message code='app.write.text22' />" + "<spring:message code='app.write.text62' />",
							maxlength : "<spring:message code='app.write.text54' />"
				    	},
						versionCode:{
							required: "<spring:message code='app.write.text22.2' />" + "<spring:message code='app.write.text62' />",
						}							
					},
					 errorPlacement: function(error, element) {
						error.appendTo( element.parent("td") );
					},
					validClass:"success"
				});	
				//if(isVaid&&$('[name=provSeq]').size()==0){
				if(isVaid){
					if($('[name=ostype]').val()!='4'){
						if(!$('.provarea').find('ul').find('li').length>0){
							alert("<spring:message code='app.write.text55' />");
							$('.detail_area.identity_area.provsearch').find('a').focus();
							isVaid = false;	
							return false;
								
						}
					}
					if($('#storeBundleId1').val()=='' || $('#storeBundleId2').val()==''){
						alert("<spring:message code='app.write.text55' />");
						$('#storeBundleId1').focus();
						isVaid = false;
						return false;
					}
				}
				if(isVaid){
					if(!validateStoreBundleId2()){
						isVaid = false;	
						return false;
	
					}
				}
				if(isVaid){
					var cnt = checkBuldeId();
					if(parseInt(cnt) > 0){
						//message : 중복되는 식별자가 있습니다.
						alert("<spring:message code='anonymous.option.007' />");
						isVaid = false;
						return false;
					}
				}
				var form = document.getElementById('appForm');
			 	if(isVaid){
			 		$("#preventSubmit").val('1');
					$("#appForm").submit();
			 	}
	 	}else{
	 		//연속 등록 방지 및 # href방지
	 		e.preventDefault();
	 	}
	});

	//프로비젼 아이디 택스트 수정시
	$(document).on('keyup', '[name=provId2]', function(){
		var provId1 = $(this).parents('li').find('[name=provId1]').val();
		var provId2 = $(this).val();
		var provId = provId1+provId2;
		$(this).parents('li').find('[name=provId]').val(provId);
	});

	//식별자 삭제
	$(document).on('click', '.removeProvBtn', function(){
		var ul = $(this).parents("ul");
		$(this).parents('li').remove();
		//alert($(ul).find('li').size());
		if($(ul).find('li').size()==0){
			$('#storeBundleId1').prop("readonly", false);
			$('#storeBundleId2').prop("readonly", true).val('*');
		}else{
			//provId
			var provId = $(ul).find('li').find('[name=provId]').val();
			if(provId.indexOf('*')>-1){
				$('#storeBundleId2').prop("readonly", false);
			}else{
				$('#storeBundleId2').prop("readonly", true);				
			}
		}
	});

	//콘텐츠 수량 입력할때
	$("#appContentsAmt").focusout(function(){
		if( !($(this).is('[readonly]')) && (parseInt($("#appContentsAmt").val()) < 2 || $("#appContentsAmt").val() == "") ){
			//message : 2 이상 일벽해 주쉽시오.
			alert("<spring:message code='app.write.text58' />");
			$("#appContentsAmt").val('2');
		}

		if($("[name=templateName]").val().length !== 0){
			if( $("[name=appContentsAmt]").val() > $("[name=maxTemplateContentsAmt]").val() ){
				//message : 앱의 콘텐츠 수량이 템플릿의 수량보다 초과될수 없습니다.
				alert("<spring:message code='anonymous.option.003' />");
				$("#appContentsAmt").val($("[name=maxTemplateContentsAmt]").val());
			}
		}
	});

	//고정, 최대, 제한없을음 누를때 
	$("[name=appContentsGb]").click(function(){
		if($("[name=appContentsGb]:checked").val() == '3'){
			$("#appContentsAmt").val("0");
			$("#appContentsAmt").attr("readonly", true);
			$("#templateName").val("");
		}else{
			$("#templateName").val("");
			$("#appContentsAmt").val("2");
			if($("#appContentsAmt").val() == '0'){
				$("#appContentsAmt").attr("readonly", false);
			}else{
				$("#appContentsAmt").attr("readonly", false);
			}
		}
	});

	//버젼 입력
	$('#verNum').bind('focusout', function(){
			qwe();
	});

	//버전 네임 검증
	function qwe(){
		var verNum = $('#verNum').val();
		var pattern = /^[0-9]{1}.[0-9]{1}.[0-9]{1}$/;
			/* /^[0-9]{1,3}-[0-9]{1,3}-[0-9]{1,3}$/ */
		if(verNum.length != 0){
			var huk = pattern.test(verNum); 
			if(!huk){
				//message : 버전 형식은 1.0.0, 1.0.1와 같은 형식으로 입력하셔야 합니다.
				alert("<spring:message code='anonymous.option.004' />");
				$('#verNum').val("");
				/* $('#storeBundleId2').focus(); */
			}
		}
	}

	//버젼 코드 검증
	$('#versionCode').keyup( function(){
		var versionCode = $('#versionCode').val();
		var pattern1 = /^[0-9]{1,99}$/;
		var pattern2 = /^[0-9]{1,3}$/;
		if(versionCode.length != 0){
			var huk = pattern1.test(versionCode); 

			if(!huk){
				//message : 숫자만 입력할 수 있습니다.
				alert("<spring:message code='anonymous.option.005' />");
				$('#versionCode').val("");
				return;
				/* $('#storeBundleId2').focus(); */
			}

			huk = pattern2.test(versionCode); 
			if(!huk){
				//message : 3자리 이상 입력할 수 없습니다.
				alert("<spring:message code='anonymous.option.006' />");
				$('#versionCode').val("");
				/* $('#storeBundleId2').focus(); */
				return;
			}

			if(parseInt(versionCode) == 0){
				alert("<spring:message code='anonymous.option.011' />");
				$("#versionCode").val("");
				return;
			}
		}
	});

	//파일 이름 검증 
	$("#fileName").keyup( function(){
		var fileName = $(this).val();
		var pattern1 = /^[a-zA-Z0-9]{1,}$/;
		var pattern2 = /^[a-zA-Z0-9]{1,40}$/;
		if(fileName.length != 0){
			var huk = pattern1.test(fileName); 

			if(!huk){
				alert("<spring:message code='anonymous.option.013' />");
				$("#fileName").val("");
				return ;
			}
			huk = pattern2.test(fileName);
			if(!huk){
				alert("<spring:message code='anonymous.option.012' />");
				$("#fileName").val("");
				return ;
			}
		}
	});
});

function cancelResist(){
	if(confirm("<spring:message code='app.write.text56' />")){
		document.listFrm.action='/app/list.html';
		document.listFrm.submit();		
	}
}

function submit(){
	var form = document.getElementById('appForm');
	form.submit();		
}

function checkBuldeId(){
	var bundleName = $('#storeBundleId1').val()+$('#storeBundleId2').val();
	var osType = $('[name=ostype]').val();
	var action_url = '/app/checkprovid.html?bundleName='+bundleName+'&osType='+osType+'&companySeq='+'${companySeq}';
	var cnt;
	$.ajax({
        url: action_url ,
        async: false,
        type: "GET" ,
        	success: function (result){
				cnt = result;
            }
        });
	return cnt;
}

</script>
</head>


<body >

<!-- wrap -->
<div id="wrap" class="sub_wrap">
	
	<!-- header -->
	<%@ include file="../inc/header.jsp" %>
	<!-- //header -->

	
	<!-- conteiner -->
	<div id="container">
		<div class="contents join_area">
		<form name="listFrm" method="get" action="/app/list.html" >
			<input type="hidden" name="currentPage" id="currentPage" value="${appList.currentPage }">
			<input type="hidden" name="searchType" id="searchType" value="${appList.searchType }">
			<input type="hidden" name="searchValue" id="searchValue" value="${appList.searchValue }">
		</form>
		<form name="appForm" id="appForm" method="post" enctype="multipart/form-data" action="" >
			<input type="hidden" name="regGb" value="${appVO.regGb }" /><!-- 앱구분 단일앱, 서가앱 -->
			<input type="hidden" name="fileType" id="fileType" /><!-- 아이콘인지 캡쳐이미지인지 -->
			<input type="hidden" name="saveFileSeq" id="saveFileSeq" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="saveFileName" id="saveFileName" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="fileStatus" id="fileStatus" />
			<input type="hidden" name="maxTemplateContentsAmt" id="maxAppContentsAmt" />
			<input type="hidden" name="maxTemplateContentsGb" id="maxTemplateContentsGb" />
			<input type="hidden" id="preventSubmit" name="preventSubmit" value="0"/>
			<input type="hidden" id="limitGb"  name="limitGb" value="2"/>

			<!-- join_area -->
			<h2><spring:message code='app.write.title' /></h2>
			
			<div class="tab_area">
				<ul>
					<li class="<c:if test="${'2' eq appVO.regGb }">current</c:if>"><a href="/app/regist.html?regGb=2"><spring:message code='app.write.text1' /></a></li>
					<li class="last<c:if test="${'1' eq appVO.regGb }"> current</c:if>"><a href="/app/regist.html?regGb=1"><spring:message code='app.write.text2' /></a></li>
				</ul>
			</div>
			
			<div class="section fisrt_section">
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:150px">
							<col style="">
						</colgroup>
						<tr>
							<th><label class="title" for="cq"><em>*</em> <spring:message code='app.write.text3' /></label></th>
							<td>
								<span class="category_area">
								<c:choose>
									<c:when test="${'2' eq appVO.regGb }">
									<input id="appContentsAmt" class="tCenter onlyNum" name="appContentsAmt" type="text" readonly="readonly" value="1" style="ime-mode:disabled;width:138px;" onkeypress="return digit_check(event)">
									</c:when>
									<c:when test="${'1' eq appVO.regGb }">
									<input id="appContentsAmt" class="tCenter onlyNum" name="appContentsAmt" type="text" style="ime-mode:disabled;width:138px;" onkeypress="return digit_check(event)">
									</c:when>
								</c:choose>
								</span>
								<c:if test="${'1' eq appVO.regGb }">
									<div style="display: inline;" class="radio_area radio_area_type2">
										<input name="appContentsGb" id="cq_1" type="radio" value="1" checked="checked"> <label for="cq_1"><spring:message code='app.write.text4' /></label>
										<input name="appContentsGb" id="cq_2" type="radio" value="2"> <label for="cq_2"><spring:message code='app.write.text5' /></label>
										<input name="appContentsGb" id="cq_3" type="radio" value="3"> <label for="cq_3"><spring:message code='app.write.text6' /></label>
									</div>
								</c:if>
							</td>
						</tr>
						<tr>
							<th><label class="title" for="name"><em>*</em> <spring:message code='app.write.text7' /></label></th>
							<td>
								<input id="appName" name="appName" type="text" style="width:95%;" maxlength="50">						
							</td>
						</tr>
						<tr>
							<th><label class="title" for="file"><em>*</em> <spring:message code='app.write.text8' /></label></th>
							<td>
								<input id="fileName" name="fileName" type="text" style="width:95%;" maxlength="50">
							</td>
						</tr>
						<tr>
							<th><label class="title" for="compatibility"><em>*</em> <spring:message code='app.write.text9' /></label></th>
							<td>
								<select name="ostype" style="width:150px;">
									<option value="2" selected><spring:message code='app.write.text11' /></option>
									<option value="3"><spring:message code='app.write.text12' /></option>
									<option value="1"><spring:message code='app.write.text10' /></option>
									<option value="4"><spring:message code='app.write.text13' /></option>
								</select>
							</td>
						</tr>
						<tr>
							<th><label class="title" for="identifiers"><em>*</em> <spring:message code='app.write.text14' /></label></th>
							<td>
								<input id="storeBundleId1" name="storeBundleId1" type="text" style="width:316px;">
								<input id="storeBundleId2" name="storeBundleId2" type="text" style="width:168px;" value="*" readonly />
							</td>
						</tr>
						<tr>
							<th class="MobileProvision"><label class="title" for="identifiers"><spring:message code='app.write.text15' /></label></th>
							<td>
								<!-- <input id="storeBundleId" name="storeBundleId" type="text" style="width:138px;">-->
								<div class="provarea" >
									<ul>
<!-- 								<li>
										<div><span>Profile Name : </span><span>iOS_UPDATE</span><span> / Profile ID : </span><span>Com.Apple	</span><a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="아이콘 썸네일 이미지 닫기"></a></div>
										</li>
										<li>
										<div><span>Profile Name : </span><span>iOS_UPDATE</span><span> / Profile ID : </span><span>Com.Apple	</span><a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="아이콘 썸네일 이미지 닫기"></a></div>
									</li> -->
									</ul>
								</div>
								<div class="detail_area identity_area provsearch" >
									<!--<a href="#" class="btn btnL btn_gray_light">KeyStore</a><!-- Android -->
									<a href="#" class="btn btnL btn_gray_light"><spring:message code='btn.search' /></a><!-- iOS -->
									<!-- <input id="storeName" name="storeName" type="text" style="width:58%;"> -->

								</div>
							</td>
						</tr>
						<tr>
							<th class="versionName"><label class="title" for="version"><em>*</em> <spring:message code='app.write.text22' /></label></th>
							<td>
								<input id="verNum" name="verNum" type="text" style="width:95%;">
							</td>
						</tr>
						<tr style="display:none" >
							<th><label class="title" for="version"><em>*</em> <spring:message code='app.write.text22.2' /></label></th>
							<td>
								<input  id="versionCode" name="versionCode" type="text" value='undefined' disabled='disabled' style="width:95%;">
							</td>
						</tr>
						<tr>
							<th><label class="title" for="template"><spring:message code='app.write.text21' /></label></th>
							<td>
								<input type="hidden" name="templateSeq" />
								<input id="templateName" name="templateName" type="text" style="width:78%;" class="line_right">
								<a id="templateNameBtn" href="#templateNameBtn_" class="btn btnL btn_gray_light line_left"><spring:message code='btn.search' /></a>
							</td>
						</tr>
						<tr>
							<th><label class="title" for="ex"><spring:message code='app.write.text17' /></label></th>
							<td>
								<textarea id="ex" name="descriptionText" cols="" rows="4" style="width:95%;"></textarea>
							</td>
						</tr>
						<tr>
							<th><label class="title" for="icon"><spring:message code='app.write.text18' /></label></th>
							<td>
								<input id="icon" name="iconFile" type="file">
								<div class="thumb_area icon_area">
									<ul class="clfix">

									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<th><label class="title" for="scrn"><spring:message code='app.write.text59' /></label></th>
							<td>
								<input id="scrn" name="captureFile" type="file">
								
								<div class="thumb_area">
									<ul class="clfix">

									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='extend.local.061' /></span></th>
							<td>
								<div class="radio_area">
									<input name="useUserGb" id="um_1" type="radio" value="1" checked="checked"> <label style="margin-right:100px;" for="um_1"><spring:message code='template.modify.027' /></label>
									<input name="useUserGb" id="um_2" type="radio" value="2"  disabled="disabled"> <label for="um_2"><spring:message code='template.modify.028_1' /></label>
									<%-- <a href="#" id="use_user_pop" class="btn btnL btn_gray_light"><spring:message code='template.modify.029' /></a> --%>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='app.write.text23' /></span></th>
							<td>
								<div class="radio_area">
									<input name="useGb" id="u_y" type="radio" value="1" checked="checked"> <label for="u_y"><spring:message code='app.write.text24' /></label>
									<input name="useGb" id="u_n" type="radio" value="2" disabled="disabled"> <label for="u_n"><spring:message code='app.write.text25' /></label>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='app.write.text26' /></span></th>
							<td>
								<div class="radio_area">
									<input name="completGb" id="c_y" type="radio" value="1" disabled="disabled"> <label for="c_y"><spring:message code='app.write.text27' /></label>
									<input name="completGb" id="c_n" type="radio" value="2" checked="checked"  > <label for="c_n"><spring:message code='app.write.text28' /></label>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='app.write.text60' /></span></th>
							<td>
								<div class="radio_area">
									<input name="installGb" id="i_y"  type="radio" value="1" disabled="disabled" <c:if test="${'1' eq appVO.installGb }">checked="checked"</c:if>> <label for="i_y"><spring:message code='app.modify.text33' /></label>
									<input name="installGb" id="i_n"  type="radio" value="2" disabled="disabled" <c:if test="${'2' eq appVO.installGb }">checked="checked"</c:if>> <label for="i_n"><spring:message code='app.modify.text34' /></label>
								</div>
							</td>							
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='app.write.text29' /></span></th>
							<td>
								<div class="radio_area coupon_area" style="width:100%">
									<span  style="width:100%; margin-top:9px;" >
										<input type="radio" name="distrGb" id="m1" value="1" disabled="disabled" onclick="alert(1);"/> <label for="m1"><spring:message code='app.write.text30' /></label>
										<input type="radio" name="distrGb" id="m2" value="2" disabled="disabled"/> <label for="m2"><spring:message code='app.write.text31' /></label><br/>
									</span>
									<span>
										<input type="radio" name="memDownGb" id="d1" value="1" disabled="disabled"/><label for="d1"><spring:message code='app.write.text32' /></label>
										<input name="memDownAmt" type="text"  class="tCenter" style="width:120px; " readonly onkeypress="return digit_check(event)">
										&nbsp;&nbsp; <spring:message code='anonymous.option.010' />&nbsp;<input name ="memDownCnt" type="text" class="tCenter" readonly="readonly" style="width:120px;" value="" onkeypress="return digit_check(event)" maxlength="3"/>
									</span>
									<br/>
									<span>
										<input type="radio" name="memDownGb" id="d2" value="2" disabled="disabled" /><label for="d2"><spring:message code='app.write.text33' /></label>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="d_SDATE" name="memDownStartDt" type="text" title="start date" class="date " value="" readonly/>
										&nbsp;&nbsp;~&nbsp;&nbsp;
										<input id="d_EDATE" name="memDownEndDt" type="text" title="end date"   class="date " value="" readonly/>
									</span>
									<br>
									<span>
										<input type="radio" name="memDownGb" id="d3" value="3" disabled="disabled" /><label for="d3"><spring:message code='app.write.text6' /></label>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="title spacing"><em>*</em> <spring:message code='app.write.text34' /></span> <br>&nbsp;&nbsp;&nbsp;<!-- <a href="#"><img src="/images/btn_q.jpg" alt="" title="쿠폰 사용 도움말"></a> --></th>
							<td>
								<div class="radio_area coupon_area" style="width:100%">
									<span style="width:100%;">
										<input name="couponGb" id="cou_y" type="radio" value="1" disabled="disabled"><label for="cou_y"><spring:message code='app.write.text35' /></label>
										<input name="couponGb" id="cou_n" type="radio" value="2" disabled="disabled"><label for="cou_n" style="margin-right:78px"><spring:message code='app.write.text36' /></label><input id="template" name="couponNum" type="text" style="width:45.2%;" readonly>
									</span>
									<br/>
									<span>
										<input type="radio" name="nonmemDownGb" id="c1" disabled="disabled"/><label for="c1"><spring:message code='app.write.text37' /></label>
										<input name="nonmemDownAmt" type="text"  class="tCenter" style="width:120px; " readonly onkeypress="return digit_check(event)">
										&nbsp;&nbsp; <spring:message code='anonymous.option.010' />&nbsp;<input name="nonmemDownCnt" type="text" readonly="readonly"  class="tCenter" style="width:120px;" value="" onkeypress="return digit_check(event)" maxlength="3"/>
									</span>
									<br>
									<span>
										<input type="radio" name="nonmemDownGb" id="c2" disabled="disabled"/><label for="c2"><spring:message code='app.write.text38' /></label>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="c_SDATE" name="noMemDownStartDt" type="text" title="start date" class="date " value="" readonly/>
										&nbsp;&nbsp;~&nbsp;&nbsp;
										<input id="c_EDATE" name="noMemDownEndDt" type="text" title="end date"   class="date " value="" readonly/>
									</span>
									<br>
									<span>
										<input type="radio" name="nonmemDownGb" id="c3" value="3" disabled="disabled"/><label for="c3"><spring:message code='app.write.text6' /></label>
									</span>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<c:if test="${'1' eq appVO.regGb }">
				<div class="btn_area_bottom tCenter">
					<a id="categorySearch" href="#categorySearch" class="my-button btn btnL btn_gray_light"><spring:message code='app.write.text39' /></a>
					<a id="inAppContentsSearch" href="#inAppContentsSearch" class="btn btnL btn_gray_light"><spring:message code='app.write.text40' /></a>
				</div>
				</c:if>
				<div class="btn_area_bottom tCenter">
					<a id="registBtn" href="#registBtn_;" class="btn btnL btn_red"><spring:message code='app.write.text41' /></a>
					<a href="javascript:cancelResist();" class="btn btnL btn_gray_light"><spring:message code='app.write.text42' /></a>
				</div>
				
				<!-- testModify -->
				앱 생성 요청
			</div>
			<!-- //join_area -->
		</form>
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>