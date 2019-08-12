<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../inc/top.jsp" %>
<%@ page session="true" %>
<script type="text/javascript" src="/js/jquery.form.mini.js" ></script>
<script src="/js/jquery.validate.js"></script>
<script type="text/javascript">
var provisionPopup;
$(document).ready(function(){
	<sec:authorize access="isAuthenticated()">  
		<sec:authentication property="principal.authorities" var="userRole" />
	</sec:authorize>

	//alert('${appVO.distrGb}');
	completGbStatus('load');
	memDownGbStatus();nomemDownGbStatus();nomemCoupon('load');
	settemplateAppcontents('load');
	//custom radio button action;

	var useUserVal = '${appVO.useUserGb}';
	if(useUserVal == 1){
		$("#fixDiv").css("margin","10px 0px");
		$("#use_user_pop").hide();
	}else if(useUserVal == 2){
		$("#fixDiv").css("margin","3px 0px");
		$("#use_user_pop").show();
	}

	if('${appVO.appContentsGb}' == 3){
		$("#appContentsAmt").prop("readonly", true);
	}

	if('${appVO.regGb}' == '2' ){
		$("#appContentsAmt").prop("readonly", true);
	}

	$("[name=useUserGb]").change( function(){
		/* $.ajax({
            url: "/deviceIsOver200.html" ,
            type: "POST" ,
            async : false ,
            data:{
                           },
            success: function (result){
               switch (result){
                      case 0 : alert("해당 이메일을 사용할 수 있습니다" );
                     $( "#emailValidFlag" ).prop("value" ,1);
                      break ;
                    case 1 : alert("해당 이메일이 이미 존재 합니다." );
                      break ;
                    case 2 : alert("[심각]해당 이메일이 2개 이상 존재합니다. 중복되는 DB정보 삭제 요망" );
                      break ;
               }
            }
        }); */

		if($(this).val() == '1'){
			$("#fixDiv").css("margin","10px 0px");
			$("#use_user_pop").hide();
		}else if($(this).val() == '2'){
			$("#fixDiv").css("margin","3px 0px");
			$("#use_user_pop").show();
		}
	});

	//파일 선택시 이미지 카운트
	$('input[type=file][name=iconFile], input[type=file][name=captureFile]').click(function(e){
		if("<c:out value='${userRole}'/>" == "[ROLE_ADMIN_SERVICE]"){
			e.preventDefault();
		}else{
			var fileType = $(this).attr("name");
			var maxImgCnt = 1;
			var imgCnt = $(this).parent().find('ul').find('li').size();
			if(fileType=='captureFile'){
				actionUrl = '/app/capturefileupload.html';
				maxImgCnt = 5;
			}
			if(maxImgCnt==imgCnt){
				//message : 기존 이미지를 삭제하셔야 합니다.
				alert("<spring:message code='app.modify.text51' />")
				return false;
			}
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
			alert("<spring:message code='app.modify.text51' />")
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
					if(type=='img'){
						li += '<input type="hidden" name="captureSeq" value="0"/>';						
					}
					li += '<input type="hidden" name="'+type+'OrgFile" value="'+orgFileName+'"/>'; 
					li += '<input type="hidden" name="'+type+'SaveFile" value="'+saveFileName+'"/>'; 
					li += '<input type="hidden" name="thisFileType" value="temp"/>'; 					
					li += '<img src="'+webPath+'" alt="'+orgFileName+'">'; 
					li += '<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.modify.text71' />"></a></li>'; 
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
		var thisFileType = $(this).parents('li').find('input[name*=thisFileType]').val();
		$('#saveFileName').val(saveFileName);
		
		var captureSeq = 0;
		if($(this).parents('li').find('[name=captureSeq]').size()>0){
			captureSeq = $(this).parents('li').find('[name=captureSeq]').val()
		}
		if(thisFileType=='temp'){//방금올린파일
		//if(captureSeq == 0){//방금올린파일
			$('#fileStatus').val('temp');
			var action_url = '/app/deletetmpimg.html';
			$.post(action_url, $('#appForm').serialize(), function(data){
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
		var url = "/app/provision/list.html?distrProfile=" + distrProfile+"&provId="+provId+"&appSeq=${appVO.appSeq}";			
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
			alert("<spring:message code='app.modify.text76' />");
		}
		return huk;
	}

	///template 검색팝업
	//var templatePopup;
	var winName = 'TemplateProvision';
	$('[name=templateName], #templateNameBtn').focus(function(){
		if($("#appContentsAmt").val().length == 0){
			alert("<spring:message code='app.modify.text52' />");
			$("#appContentsAmt").focus();
		}else{
			$('#templateNameBtn').focus().click();
		}
	});

	$('#templateNameBtn').click(function(){
		var winWidth = 670;
		var winHeight = 740;
		var winPosLeft = (screen.width - winWidth)/2;
		var winPosTop = (screen.height - winHeight)/2;
		var ostypeGb = $('[name=ostype]').val();
		var appContentsAmt = $("#appContentsAmt").val();
		var templateTypeGb = '${appVO.regGb }';
		var appContentsGb = $("[name=appContentsGb]:checked").val();

		var url = "/app/template/list.html?currentPage=1&templateTypeGb=" + templateTypeGb + "&ostypeGb=" + ostypeGb +"&appContentsAmt=" + appContentsAmt +"&appContentsGb=" + appContentsGb;		
		var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
		//if(!templatePopup){
			templatePopup = window.open(url, winName, opt);			
		//}
	});	

	//카테고리검색팝업 작업중
	var categoryPopup
	$('#categorySearch').click(function(){
		//alert('앱 수정 화면에서만 등록이 가능합니다');
		var winWidth = 680;
		var winHeight = 333;
		var winPosLeft = (screen.width - winWidth)/2;
		var winPosTop = (screen.height - winHeight)/2;
		var url = "/app/category/category_write.html?storeBundleId=${appVO.storeBundleId}";		
		var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
		//if(!templatePopup){
			categoryPopup = window.open(url, "categoryPopup", opt);			
		//}	
	});

	//inapp search popup
	var inappPopup
	$('#inAppContentsSearch').click(function(){
		//alert('앱 수정 화면에서만 등록이 가능합니다');
		//alert('앱 수정 화면에서만 등록이 가능합니다');
		var winWidth = 750;
		var winHeight = 600;
		var winPosLeft = (screen.width - winWidth)/2;
		var winPosTop = (screen.height - winHeight)/2;
		// testModify
		// baseCode
		var url = "/book/inapp/list.html?storeBundleId="+ $("#storeBundleId1").val() + $("#storeBundleId2").val();

				
		var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
		//if(!templatePopup){
			inappPopup = window.open(url, "inappPopup", opt);			
		//}			
	});

	$('#registBtn').click(function(){
		$("#appForm").attr('action', '/book/modify.html');
		var isVaid = customValidate({
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
				    	/* versionCode :{
				    		required : true,
				    		maxlength : 10,
				    	} */
			    },

			    messages: {
			    	appContentsAmt: {
						required: "<spring:message code='app.modify.text52' />",
						maxlength: "<spring:message code='app.modify.text53' />",
						number : "<spring:message code='app.modify.text54' />"
					},
					appName: {
						required: "<spring:message code='app.modify.text55' />",
						maxlength : "<spring:message code='app.modify.text56' />"
					},
					fileName: {
						required: "<spring:message code='app.modify.text57' />",
						maxlength : "<spring:message code='app.modify.text56' />"
					},
			    	verNum: {
						required: "<spring:message code='app.modify.text59' />",
						maxlength : "<spring:message code='app.modify.text60' />"
			    	},
					/* versionCode :{
						required: "<spring:message code='app.write.text22.2' />" + "<spring:message code='app.write.text62' />",
						maxlength : "10자 이하로 입력해 주세요."
					} */
				},

				 errorPlacement: function(error, element) {
					error.appendTo( element.parent("td") );
				},
				validClass:"success"
			});
/* 			if(isVaid&&$('[name=provSeq]').size()==0){
				alert("<spring:message code='app.modify.text61' />");
				$('.detail_area.identity_area.provsearch').find('a').focus();
				return false;
				isVaid = false;
			} */
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
				if($('#storeBundleId1').val()==''||$('#storeBundleId2').val()==''){
					alert("<spring:message code='app.write.text55' />");
					$('#storeBundleId1').focus();
					isVaid = false;
					return false;
				}
			}
			//alert(isVaid);
			if(isVaid){
/* 				completGbStatus('load');
				alert('================='+validRadioWidthText());
				//isVaid = validRadioWidthText();
				//alert(isVaid); */
				var unvlid = 0;
				$('input:radio:checked').parents('span').find('input:text').each(function(){
					//alert(1);
					var radioName = $(this).attr('name');
					//alert(radioName);//&&radioName!='couponNum'
					if(!$(this).val()&&radioName!='couponNum' && $(this).prop('readonly') == false){
						var msgObj = $(this).parents('span').find('label');
						alert($(msgObj).text()+"<spring:message code='app.modify.text66' />");
						$(this).focus();
						isVaid = false;
						unvlid++;
						return false;
					}else if(!$(this).val()&&radioName=='couponNum'){
						if($(this).prop('readonly')==false){
							alert("<spring:message code='app.modify.text67' />");
							$(this).focus();
							isVaid = false;
							unvlid++;
							return false;
						}
					}else{
						//radioTextValid = true;
					}
				}).promise().done(function(){
					//eturn unvlid;
				});				
			}

		 	if(isVaid){
		 		isVaid = checkAppappContentsAmtTemplateaContentsAmt();	 		
		 	}
			//alert(isVaid);
		 	if(isVaid){
		 		isVaid = inappCntVsappContentsAmt();	 		
		 	}
			if(isVaid){
				if(!validateStoreBundleId2()){
					isVaid = false;		
					return false;
				}
			}

			var form = document.getElementById('appForm');
			//alert(isVaid);
		 	if(isVaid){
		 		$("#couponNum").attr("disabled", false);
		 			
		 		$("[name=ostype]").attr("disabled", false);
		 		form.submit();		 		
		 	}
	});

	$('[name=storeBundleId]').each(function(){
		
		var provId = $(this).val();
		var lastDotIndex = provId.lastIndexOf(".");
		var lastCharbyDot = provId.substring(lastDotIndex+1, provId.length);
		var exceptlastCharbyDot = provId.substring(0, lastDotIndex+1);
		
		$('#storeBundleId1').val(exceptlastCharbyDot);
		$('#storeBundleId2').val(lastCharbyDot);
		if(lastCharbyDot!="*"){
			$('#storeBundleId2').prop("readonly", true);
		}
	});

	//식별자 삭제
	$(document).on('click', '.removeProvBtn', function(){
		var ul = $(this).parents("ul");
		$(this).parents('li').remove();
		//alert($(ul).find('li').size());
		if($(ul).find('li').size()==0){
			//$('#storeBundleId1').prop("readonly", false);
			//$('#storeBundleId2').prop("readonly", true).val('*');
		}else{
			//provId
			var provId = $(ul).find('li').find('[name=provId]').val();
			if(provId.indexOf('*')>-1){
				alert(1);
				$('#storeBundleId2').prop("readonly", false);
			}else{
				$('#storeBundleId2').prop("readonly", true);				
			}
		}
	});

	//radio controll
	var completGb;
	//completGbStatus('load');

	$('#cq_3').mousedown(function(){
		checkAppappContentsAmtTemplateaContentsAmt();
	});
	
	$('input:radio[name=completGb]').change(function(){
		completGbStatus('action');
	});
	$('input:radio[name=memDownGb]').change(function(){
		//alert(1);
		memDownGbStatus();
	});
	$('input:radio[name=nonmemDownGb]').change(function(){
		//alert(2);
		nomemDownGbStatus();
	});
	$('input:radio[name=couponGb]').change(function(){
		//alert(2);
		//couponNumStatus();
		nomemCoupon('action');
	});
	$('input:radio[name=distrGb]').change(function(){
		//alert(2);
		nomemCoupon('action');
	});

	//고정, 최대, 제한없을음 누를때 
	$("[name=appContentsGb]").click(function(){
		if($("[name=appContentsGb]:checked").val() == '3'){
			$("#appContentsAmt").val("0");
			$("#appContentsAmt").attr("readonly", true);
			$("#templateName").val("");
		}else{
			$("#templateName").val("");
			$("#appContentsAmt").val($("#tempAmt").val());
			if($("#appContentsAmt").val() == '0'){
				$("#appContentsAmt").attr("readonly", false);
				$("#appContentsAmt").val('${inappCnt}'); 
				$("#tempAmt").val('${inappCnt}');
				$("#pastTempForappContentsAmt").val('${inappCnt}');
			}else{
				$("#appContentsAmt").attr("readonly", false);
			}
		}
	});

	$("#appContentsAmt").keyup(function(){
		$("#tempAmt").val($(this).val());
	});

	//서가앱일때 콘텐츠수에따른 완성여부 가능성 return boolean
	function inappCntVsappContentsAmt(){
		var isValid = false;
		//varcompletGb_  = $('input:radio[name=completGb]:checked').val();
		var inappCnt = $('#inappCnt').val();
		var regGb = '${appVO.regGb }';
		//alert(regGb);
		if(regGb=='1'){
			var completGb = $('[name=completGb]:checked').val();
			//alert("completGb==="+completGb);
			var appContentsAmt = $('#appContentsAmt').val();
			var appContentsGb = $('[name=appContentsGb]:checked').val();
			//alert('inappCnt='+inappCnt+'/'+'appContentsAmt='+appContentsAmt+'/'+'appContentsGb='+appContentsGb+'/');
			if(completGb=='1' && '${userRole}' != '[ROLE_ADMIN_SERVICE]'){
				if(appContentsGb=='1'){
					if(inappCnt==appContentsAmt){
						isValid = true;
					}else if(parseInt(inappCnt) > parseInt(appContentsAmt)){
						alert("<spring:message code='app.modify.text65' />");
						return false;
					}else if(parseInt(inappCnt) < parseInt(appContentsAmt)){
						alert("<spring:message code='app.modify.text64' />");
						return false;
					}
				}else if(appContentsGb=='2'){
					if(inappCnt<=appContentsAmt){
						isValid = true;
					}else{
						alert("<spring:message code='app.modify.text65' />");
						return false;
					}
				}else{
					$('#appContentsAmt').val('0');
					isValid = true;
				}				
			}else{
				isValid = true;
			}
		}else{
			isValid = true;
		}
		return isValid;
	}

	function completGbStatus(type){

		if( "${appVO.app_resultCode}" == "2" && $('input:radio[name=completGb]:checked').val() == 1){
			alert("<spring:message code='app.modify.text75' />");
			$("[name=completGb]").eq(1).prop('checked',true);
		}else{
			
			//버전 업데이트한 이앱이 사용 여부가 YES이어야만, 
			//완성여부 에따라 이전 버전을 사용안함으로 바꿀수있다. 
			if("${appVO.useGb}" == "1"){
				if("${appVO.completGb}" == "2" && $("[name=completGb]:checked").val() == '1'){
					$("#isCompleteNoToYes").val("UPDATEOTHERYES");
				}else if("${appVO.completGb}" == "2" && $("[name=completGb]:checked").val() == '2'){
					$("#isCompleteNoToYes").val("UPDATEOTHERNO");
				}
			}
			completGb  = $('input:radio[name=completGb]:checked').val();
			var radioArr = ["distrGb", "memDownGb", "couponGb", "nonmemDownGb", "installGb"];
			if(completGb==2){
				$('input[type=radio]').each(function(index){
					if(radioArr.indexOf($(this).attr('name'))>-1){
						$(this).prop('checked', false).attr('disabled', 'disabled');
					}
				}).promise().done( function(){ memDownGbStatus();nomemDownGbStatus();nomemCoupon();} );

				$("#couponNum").attr("readonly",true);
				$("#couponNum").attr("disabled",true);
				$("#couponNum").val("");
			}else{
				var cname = '';
				var pname = '';
				if(type=='action'){
					if(inappCntVsappContentsAmt()==true){
						$('input[type=radio]').each(function(index){
							//if(index>3&index<12){
							if(radioArr.indexOf($(this).attr('name'))>-1){	
								$(this).attr('disabled', false);
								if($(this).attr('name')!=pname){
									$(this).prop('checked', true);
								}
							}
							pname = $(this).attr('name');
						}).promise().done( function(){
							memDownGbStatus();
							nomemDownGbStatus();
							nomemCoupon();
						});
					}else{
						$('input:radio[name=completGb]').prop('checked', false);
						$('input:radio[name=completGb]').eq(1).prop('checked', true);
						$("#couponNum").attr("readonly",true);
						$("#couponNum").attr("disabled",true);
						return;
					}
				}
				$("#couponNum").attr("readonly",false);
				$("#couponNum").attr("disabled",true);
			}
		}

		//load시 완성여부가 '예'이면 콘텐츠 수량 수정 불가능
		if(type == 'load'){
			if($("input:radio[name=completGb]:checked").val() == '1'){
				$("#appContentsAmt").attr("readonly", true);
				$('[name=appContentsGb]').each(function(index){
					if($(this).is(":checked")) ;
					else $(this).attr("disabled", true);
				});
				$("[name=completGb]").eq(1).attr("disabled", true);
			}
		}
	}

	//배포범위 다운로드 유효기간 radio action
	function memDownGbStatus(){
		$('input:radio[name=memDownGb]').each(function(index){
			//alert(index+'==='+$(this).is(':checked'));
			if($(this).is(':checked')==false){
				$(this).parents('span').find('input:text').prop('readonly', true);
				$(this).parents('span').find('input:text').prop('disabled', true);
			}else{
				if($(this).val() == '1'){
					$(this).parents('span').find('input:text').eq(0).prop('readonly', false);
					$(this).parents('span').find('input:text').eq(1).prop('readonly', true);
				}else{
					$(this).parents('span').find('input:text').prop('readonly', false);
					$(this).parents('span').find('input:text').prop('disabled', false);
				}
			}
		});
	}

	//쿠폰다운로드 유효기간 radio action
	function nomemDownGbStatus(){
		$('input:radio[name=nonmemDownGb]').each(function(index){
			//alert(index+'==='+$(this).is(':checked'));
			if($(this).is(':checked')==false){
				$(this).parents('span').find('input:text').prop('readonly', true);
				$(this).parents('span').find('input:text').prop('disabled', true);
			}else{
				if($(this).val() == '1'){
					$(this).parents('span').find('input:text').eq(0).prop('readonly', false);
					$(this).parents('span').find('input:text').eq(1).prop('readonly', true);
				}
				else{
					$(this).parents('span').find('input:text').prop('readonly', false);
					$(this).parents('span').find('input:text').prop('disabled', false);
				}
			}
		});
	}

	//비회원쿠폰발행
	function nomemCoupon(type){
		var distrGb = $('[name=distrGb]:checked').val();
		var couponGb = $('[name=couponGb]:checked').val();
		//alert(distrGb);
		//alert(couponGb);
/* 
		if(distrGb=='1'&&type!='load'){
			$('[name=memDownGb]').prop('checked', false).attr('disabled', false).parents('span').find('input:text').val('').prop('readonly', true);
			$('[name=memDownGb]').eq(0).prop('checked', true).attr('disabled', false).parents('span').find('input:text').eq(0).val('').prop('readonly', false);
		}
		if(couponGb=='1'&&type!='load'){
			$('[name=nonmemDownGb]').prop('checked', false).attr('disabled', false).parents('span').find('input:text').val('').prop('readonly', true);
			$('[name=nonmemDownGb]').eq(0).prop('checked', true).attr('disabled', false).parents('span').find('input:text').eq(0).val('').prop('readonly', false);
		}else{
			$('[name=nonmemDownGb]').prop('checked', false).attr('disabled', 'disabled').parents('span').find('input:text').val('').prop('readonly', true);
			$('[name=memDownGb]').prop('checked', false).attr('disabled', false).parents('span').find('input:text').val('').prop('readonly', true);
			$('[name=memDownGb]').eq(0).prop('checked', true).attr('disabled', false).parents('span').find('input:text').eq(0).val('').prop('readonly', false);			
		} */
		if(distrGb=='2'&&couponGb=='1'){
			$('[name=memDownGb]').prop('checked', false).attr('disabled', 'disabled').parents('span').find('input:text').val('').prop('readonly', true);
			if(!$("[name = couponNum]").val()){
				$('input:radio[name=couponGb]').parents('span').find('input:text').prop('readonly', false);
				if(type != 'load') setcoupNum();
			}
		}else if(distrGb=='1'&&couponGb=='1'){
			if($('[name=memDownGb]:checked').size()==0){
				$('[name=memDownGb]').prop('checked', false).attr('disabled', false).parents('span').find('input:text').val('').prop('readonly', true);
				$('[name=memDownGb]').eq(0).prop('checked', true).attr('disabled', false).parents('span').find('input:text').eq(0).val('').prop('readonly', false);
			}
			if($('[name=nonmemDownGb]:checked').size()==0){
				$('[name=nonmemDownGb]').prop('checked', false).attr('disabled', false).parents('span').find('input:text').val('').prop('readonly', true);
				$('[name=nonmemDownGb]').eq(0).prop('checked', true).attr('disabled', false).parents('span').find('input:text').eq(0).val('').prop('readonly', false);
			}
			if(!$("[name = couponNum]").val()){
				$('input:radio[name=couponGb]').parents('span').find('input:text').prop('readonly', false);
				if(type != 'load') setcoupNum();
			}
		}else if(couponGb=='2'){
			$('[name=nonmemDownGb]').prop('checked', false).attr('disabled', 'disabled').parents('span').find('input:text').val('').prop('readonly', true);
			$('input:radio[name=couponGb]').parents('span').find('input:text').val('').prop('readonly', true);	
			if($('[name=memDownGb]:checked').size()==0){
				$('[name=memDownGb]').prop('checked', false).attr('disabled', false).parents('span').find('input:text').val('').prop('readonly', true);
				$('[name=memDownGb]').eq(0).prop('checked', true).attr('disabled', false).parents('span').find('input:text').eq(0).val('').prop('readonly', false);
			}
		}
	}

	//submit 직전 radio 관련 text validation
	var radioTextValid = false;
	function validRadioWidthText(){
		//alert(1);
		var unvlid = 0;
		$('input:radio:checked').parents('span').find('input:text').each(function(){
			//alert(1);
			var radioName = $(this).attr('name');
			//alert(radioName);//&&radioName!='couponNum'
			if(!$(this).val()&&radioName!='couponNum'){
				var msgObj = $(this).parents('span').find('label');
				alert($(msgObj).text()+"<spring:message code='app.modify.text63' />");
				$(this).focus();
				radioTextValid = false;
				unvlid++;
				return false;
			}else if(!$(this).val()&&radioName=='couponNum'){
				if(!$(this).is('readonly')){
					alert("<spring:message code='app.modify.text62' />");
					$(this).focus();
					radioTextValid = false;
					unvlid++;
					return false;
				}
			}else{
				//radioTextValid = true;
			}
		}).promise().done(function(){
			return unvlid;
		});
		//alert('unvlid==='+unvlid);
	}

	//쿠폰번호입력
	function setcoupNum(){
		var d = new Date();
		var curr_year = d.getFullYear();
		var curr_month = d.getMonth();
		var curr_date = d.getDate();
		var curr_hour = d.getHours();
		var curr_min = d.getMinutes();

		var curr_sec = d.getSeconds();
		var curr_msec = d.getMilliseconds();
		var aaa = ''+curr_year+curr_month+curr_date+curr_hour+curr_min+curr_sec+curr_msec+'';
		//alert(aaa);
		//alert(Math.random().toString(36).substr(2, 10));
		//alert(aaa+''+Math.random().toString(36).substr(2, 10));
		$.ajax({
			url:"/couponGenerate.html",
			type:"POST",
			success:function(result){
				$("[name = couponNum]").val(result);
			}
		});
	}

	//콘텐츠 수량 입력할때
	$("#appContentsAmt").focusout(function(){
		if(  !($(this).is('[readonly]')) &&( parseInt($("#appContentsAmt").val()) < 2 || $("#appContentsAmt").val() == "") ){
			alert("<spring:message code='app.modify.text72' />");
			$("#appContentsAmt").val($("#pastTempForappContentsAmt").val());
			return;
		}
		if($("[name=templateName]").val().length !== 0){
			if( ($("[name=maxTemplateContentsGb]").val() != '3')  && parseInt($("[name=appContentsAmt]").val()) > parseInt($("[name=maxTemplateContentsAmt]").val()) ){
				alert("<spring:message code='anonymous.option.008' />");
				$("#appContentsAmt").val($("#pastTempForappContentsAmt").val());
				return;
			}
		}
		if( ($("[name=appContentsGb]:checked").val() == '1') && (parseInt($(this).val()) < parseInt("${inappCnt}")) ){
			alert("<spring:message code='anonymous.option.009' />");
			$("#appContentsAmt").val($("#pastTempForappContentsAmt").val());
			$("#tempAmt").val($("#pastTempForappContentsAmt").val());
			return;
		}
		if( ($("[name=appContentsGb]:checked").val() == '2') && (parseInt($(this).val()) < parseInt("${inappCnt}")) ){
			alert("<spring:message code='app.modify.text65' />");
			$("#appContentsAmt").val($("#pastTempForappContentsAmt").val());
			$("#tempAmt").val($("#pastTempForappContentsAmt").val());
			return;
		}
		$("#pastTempForappContentsAmt").val($("#appContentsAmt").val());
	});

	//완성여부가 아니오일 경우 쿠폰창 readonly
	if($("[name=completGb]:checked").val() =="2"){
		$("#couponNum").attr("readonly", true);
	}

	//쿠폰 번호 입력 불가
	$("#couponNum").attr("disabled", true );

	$("[name=ostype]").css({"background-color":"#EEEEEE"});
	$("[name=ostype]").attr("disabled",true);
	
	//버젼 입력
	$('#verNum').bind('focusout', function(){
		versionNameVerify();
	});

	//버전 네임 검증
	function versionNameVerify(){
		var verNum = $('#verNum').val();
		var pattern = /^[0-9]{1}[.]{1}[0-9]{1}[.]{1}[0-9]{1}$/;
			/* /^[0-9]{1,3}-[0-9]{1,3}-[0-9]{1,3}$/ */
		if(verNum.length != 0){
			var huk = pattern.test(verNum); 
			if(!huk){
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
				alert("<spring:message code='anonymous.option.005' />");
				$('#versionCode').val("");
				return
			}
			huk = pattern2.test(versionCode); 
			if(!huk){
				alert("<spring:message code='anonymous.option.006' />");
				$('#versionCode').val("");
				return;
			}
		}
	});

	$("#d_EDATE").mousedown(function(e){
		if( $("#d_SDATE").val() == null || $("#d_SDATE").val() == ""){
			//message : 시작 날짜를 입력해주십시오.
			alert("<spring:message code='contents.modify.039' />");
			e.preventDefault();
		}
	});

	$("#c_EDATE").mousedown(function(e){
		if($("#c_SDATE").val() == null || $("#c_SDATE").val() == ""){
			//message : 시작 날짜를 입력해주십시오.
			alert("<spring:message code='contents.modify.039' />");
			e.preventDefault(); 
		} 
	});

	$("#c_SDATE").change( function(){

		//현재 시간
		var d = new Date();
		var curr_year = d.getFullYear();
		var curr_month = d.getMonth();
		var curr_date = d.getDate();
		var curr_hour = d.getHours();
		var curr_min = d.getMinutes();

		//HTML에서 가져온 날자 값
		var inputDate = $("#c_SDATE").val();
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

		/* alert(curr_year);
		alert(curr_month);
		alert(curr_date); */
		if((curr_year <= inputYear) && (curr_month <= inputmonth) && (curr_date <= inputdate)){
			
		}else{
			var inputValue = curr_year +"-"+(curr_month)+"-"+curr_date;
			/* alert(inputValue); */
			$("#c_SDATE").val(inputValue);
			//message : 현재보다 날자가 같거나 나중이어야 합니다.
			alert("<spring:message code='contents.modify.040' />");
		}
	});

	$("#d_SDATE").change( function(){
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

		/* alert(curr_year);
		alert(curr_month);
		alert(curr_date); */
		if((curr_year <= inputYear) && (curr_month <= inputmonth) && (curr_date <= inputdate)){
			
		}else{
			var inputValue = curr_year +"-"+(curr_month)+"-"+curr_date;
			/* alert(inputValue); */
			$("#d_SDATE").val(inputValue);
			//message : 현재보다 날자가 같거나 나중이어야 합니다.
			alert("<spring:message code='contents.modify.040' />");
		}
	});

	$("#use_user_pop").click( function(){
		var target_ = "userPop";
		var useS = $("#useS").val();
	  	window.open("/assignment/user.html?useS="+useS,target_,"width=985, height=450, top=100, left=100, resizable=no, menubar=no, scrollbars=no");
	});

	$("#goToHistory").click( function(){
		window.open("/app/history.html?","popup","width=700, height=450, top=100, left=100, resizable=no, menubar=no, scrollbars=no");
	});
	
	$("#appRequest").click( function(){
		var appSeqTest = '${param.appSeq}'
		var templateName = $("#templateName").val();
		var templateSeq  = $("#templateSeq").val();
		var appContentsAmtJ = $("#appContentsAmt").val();
		var appNameJ = $("[name=appName]").val();
		var fileNameJ = $("[name=fileName]").val();
		var appContentsGbJ = $("[name=appContentsGb]").val();
		var regGbJ = $("[name=regGb]").val();
		var storeBundleIdJ = $("[name=storeBundleId1]").val()+$("[name=storeBundleId2]").val();
		var descriptionTextJ = $("[name=descriptionText]").val();
		var verNumJ = $("[name=verNum]").val();
		var versionCodeJ = $("[name=versionCode]").val();

		if(typeof templateName != 'undefined' && templateName.length > 0){
			$.ajax({
	            url: "/book/createTextAppRequestPOST.html" ,
	            type: "POST" ,
	            async : false ,
	            data:{	
	            	dataType : "app",
	            	appContentsAmt : appContentsAmtJ,
	            	appSeq : appSeqTest,
	            	templateSeq : templateSeq,
	            	appName : appNameJ,
					fileName : fileNameJ,
					appContentsGb : appContentsGbJ,
					regGb : regGbJ,
					storeBundleId : storeBundleIdJ,
					descriptionText : descriptionTextJ,
					verNum : verNumJ,
					ostype : '${appVO.ostype}',
					versionCode : versionCodeJ
	            },
	            success: function (result){
	               switch (result){
	                    case 0 : alert("요청 실패" );
	                      break ;
	                    case 1 : alert("변환 되었습니다.")
	                      break ;
	                    case 2 : alert("세션이 만료되었습니다. 다시 로그인해 주십시오.");
	                      break;
	                    case 5 : alert("알수 없는 에러 ")
	                    	break;
	               }
	            }
	        });
		}else{
			alert("템플릿을 등록해주세요.")
		}
	});
});



//쿠폰발생 action
function couponNumStatus(){
	var couponNumStatusValue = 0;
	couponNumStatusValue = $('input:radio[name=couponGb]:checked').val();
	if(couponNumStatusValue=='1'){
		//alert(couponNumStatusValue);		
		$('input:radio[name=couponGb]').parents('span').find('input:text').prop('readonly', false);
		setcoupNum();
	}else{
		$('input:radio[name=couponGb]').parents('span').find('input:text').val('').prop('readonly', true);	
	}
}

function companyCreatorLimit(){
}

function cancelResist(){
	if(confirm("<spring:message code='app.modify.text68' />")){
		document.appFrm.action='/book/list.html';
		document.appFrm.submit();		
	}
}

//관련 탬플릿이 있는 경우나 등록 수정시 탬플릿의 컨탠츠 타입 갯수 validate
function checkAppappContentsAmtTemplateaContentsAmt(){
	var regGb = "${appVO.regGb}";
	var valid = true;
	if(regGb=='1'){		
		var appContentsAmt = $('#appContentsAmt').val();
		var appContentsGb = $('[name=appContentsGb]:checked').val();
		var cappContentsAmt = $('[name=cappContentsAmt]').val();
		var cappContentsGb = $('[name=cappContentsGb]').val();
		if(cappContentsGb){
			if(cappContentsGb!='3'){
				if(appContentsGb=='3'){
					alert("<spring:message code='app.modify.text69' />");
					valid = false;
				}else{
					if(parseInt(appContentsAmt)> parseInt(cappContentsAmt)){
						alert("<spring:message code='app.modify.text70' />");
						valid = false;
					}
				}
				
			}
		}
	}
	return valid;
}


//관련 탬플릿이 있는 경우나 등록 수정시 탬플릿의 컨탠츠 타입 갯수 set
function settemplateAppcontents(action){
	var cappContentsAmt = 0;
	var cappContentsGb = 3;
	//alert($('[name=templateSeq]').val());
	if($('[name=templateSeq]').val()){
		$.getJSON( "/app/template/appcontentsamt.html?templateSeq="+$('[name=templateSeq]').val(), function( data ) {
			//alert(data);
			cappContentsAmt = data.appContentsAmt;
			cappContentsGb = data.appContentsGb;
			//alert('cappContentsAmt==='+cappContentsAmt+'/cappContentsGb='+cappContentsGb);
			$('[name=cappContentsAmt]').val(cappContentsAmt);
			$('[name=cappContentsGb]').val(cappContentsGb);
			if(action&&action=='action'){
				checkAppappContentsAmtTemplateaContentsAmt();
			}
		});
	}
}
</script>
</head>
<body >

<!-- wrap -->
<div id="wrap" class="sub_wrap">
	
	<!-- header -->
	<%@ include file="../inc/header.jsp" %>

	<!-- conteiner -->
	<div id="container">
		<div class="contents join_area">
		<form name="appFrm" method="get" action="/book/list.html" >
			<input type="hidden" name="currentPage" id="currentPage" value="${appList.currentPage }">
			<input type="hidden" name="searchType" id="searchType" value="${appList.searchType }">
			<input type="hidden" name="searchValue" id="searchValue" value="${appList.searchValue }">
		</form>
		<form name="appForm" id="appForm" method="post" enctype="multipart/form-data" action="" >
			<input type="hidden" name="currentPage" id="" value="${appList.currentPage }">
			<input type="hidden" name="searchType" id="" value="${appList.searchType }">
			<input type="hidden" name="searchValue" id="" value="${appList.searchValue }">		
			<input type="hidden" name="appSeq" id="" value="${appVO.appSeq }">
			<input type="hidden" name="regGb" value="${appVO.regGb }" /><!-- 앱구분 단일앱, 서가앱 -->
			<input type="hidden" name="fileType" id="fileType" /><!-- 아이콘인지 캡쳐이미지인지 -->
			<input type="hidden" name="saveFileSeq" id="saveFileSeq" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="saveFileName" id="saveFileName" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="fileStatus" id="fileStatus" />
			<input type="hidden" name="inappCnt" id="inappCnt" value="${inappCnt}"/>
			<input type="hidden" name="cappContentsAmt" id="cappContentsAmt" value="${templateVO.appContentsAmt }"/>
			<input type="hidden" name="cappContentsGb" id="cappContentsGb" value="${templateVO.appContentsGb }"/>
			<input type="hidden" name="isCompleteNoToYes" id="isCompleteNoToYes" value= ""/>
			<!-- 맨처음 페이지 로딩 될때, 템플릿의 최대 허용 콘텐츠 수량을 범위를 입력 -->
			<input type="hidden" name="maxTemplateContentsAmt" id="maxAppContentsAmt" value="${templateVO.appContentsAmt }"/>
			<input type="hidden" name="maxTemplateContentsGb" id="maxTemplateContentsGb" value="${templateVO.appContentsGb }" />
			<input type="hidden" name="pastTempForappContentsAmt" id="pastTempForappContentsAmt" value="${appVO.appContentsAmt }" />
			<input type="hidden" name="tempAmt"				id="tempAmt" value="${appVO.appContentsAmt }"/>
			<input type="hidden" name="useS" id="useS" value="${useSelVal }" />
		<div id="deleteFileList" style="display:none;">
		</div>
			<!-- join_area -->
			<h2><spring:message code='app.modify.title' /></h2>
			
			<div class="tab_area">
				<ul>
					<c:if test="${'2' eq appVO.regGb }">
					<li class="<c:if test="${'2' eq appVO.regGb }">current</c:if>"><a href="#regGb_"><spring:message code='app.modify.text1' /></a></li>
					</c:if>
					<c:if test="${'1' eq appVO.regGb }">
					<li class="last<c:if test="${'1' eq appVO.regGb }"> current</c:if>"><a href="#regGb_"><spring:message code='app.modify.text2' /></a></li>
					</c:if>
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
							<th><label class="title" for="cq"><em>*</em> <spring:message code='app.modify.text3' /></label></th>
							<td>
								<span class="category_area">
								<c:choose>
									<c:when test="${'2' eq appVO.regGb }">
									<input id="appContentsAmt" class="tCenter onlyNum" name="appContentsAmt" type="text" value="1" style="ime-mode:disabled;width:138px;" onkeypress="return digit_check(event)">
									</c:when>
									<c:when test="${'1' eq appVO.regGb }">
									<input id="appContentsAmt" class="tCenter onlyNum" name="appContentsAmt" type="text" value="${appVO.appContentsAmt}" style="ime-mode:disabled;width:138px;" onkeypress="return digit_check(event)">
									</c:when>
								</c:choose>
								</span>
								<c:if test="${'1' eq appVO.regGb }">
									<div style=" display: inline; " class="radio_area radio_area_type2">
										<input name="appContentsGb" id="cq_1" type="radio" value="1" <c:if test="${empty appVO.appContentsGb ||'1' eq appVO.appContentsGb }">checked="checked"</c:if>> <label for="cq_1"><spring:message code='app.modify.text4' /></label>
										<input name="appContentsGb" id="cq_2" type="radio" value="2" <c:if test="${'2' eq appVO.appContentsGb }">checked="checked"</c:if>> <label for="cq_2"><spring:message code='app.modify.text5' /></label>
										<input name="appContentsGb" id="cq_3" type="radio" value="3" <c:if test="${'3' eq appVO.appContentsGb }">checked="checked"</c:if>> <label for="cq_3"><spring:message code='app.modify.text6' /></label>
									</div>
								</c:if>
							</td>
						</tr>
						<tr>
							<th><label class="title" for="name"><em>*</em> <spring:message code='app.modify.text7' /></label></th>
							<td>
								<input id="appName" name="appName" type="text" style="width:95%;" value="${appVO.appName }" maxlength="50">							
							</td>
						</tr>
						<tr>
							<th><label class="title" for="file"><em>*</em> <spring:message code='app.modify.text8' /></label></th>
							<td>
								<input id="fileName" name="fileName" type="text" style="width:95%;" value="${appVO.fileName }" maxlength="50">
							</td>
						</tr>
						<tr>
							<th><label class="title" for="compatibility"><em>*</em> <spring:message code='app.modify.text9' /></label></th>
							<td>
								<select name="ostype" style="width:150px;">
									<option value="2" <c:if test="${empty appVO.ostype || '2' eq appVO.ostype }">selected="selected"</c:if>><spring:message code='app.modify.text11' /></option>
									<option value="3" <c:if test="${'3' eq appVO.ostype }">selected="selected"</c:if>><spring:message code='app.modify.text12' /></option>
									<option value="1" <c:if test="${'1' eq appVO.ostype }">selected="selected"</c:if>><spring:message code='app.modify.text10' /></option>
									<option value="4" <c:if test="${'4' eq appVO.ostype }">selected="selected"</c:if>><spring:message code='app.modify.text13' /></option>									
								</select>
							</td>
						</tr>
						<tr>
							<th><label class="title" for="identifiers"> <spring:message code='app.modify.text73' /></label></th>
								<td>
								<c:choose>
									<c:when test="${'1' eq appVO.provisionGb}"><input id="provisionGb" name="provisionGb" type="text" style="width:138px;" value="Adhoc" readonly></c:when>
									<c:when test="${'2' eq appVO.provisionGb}"><input id="provisionGb" name="provisionGb" type="text" style="width:138px;" value="AppStore" readonly></c:when>
									<c:otherwise>
										<input id="provisionGb" name="provisionGb" type="text" style="width:138px;" value="" readonly>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th><label class="title" for="identifiers"><em>*</em> <spring:message code='app.write.text14' /></label></th>
							<td>
								<c:if test="${not empty bundleList}">							
									<c:forEach var="result" items="${bundleList}" varStatus="status">
										<input type="hidden" name="storeBundleId" value="${result.bundleName }">
									</c:forEach>
								</c:if>								
								<input id="storeBundleId1" name="storeBundleId1" type="text" style="width:316px;" value="" readonly>
								<input id="storeBundleId2" name="storeBundleId2" type="text" style="width:168px;" value="" />
							</td>
						</tr>						
						<tr <c:if test="${'4' eq appVO.ostype }">style="display:none;"</c:if>>
							<th class="MobileProvision"><label class="title" for="identifiers"><spring:message code='app.write.text15' /></label></th>
							<td>
								<!-- <input id="storeBundleId" name="storeBundleId" type="text" style="width:138px;">-->
								<div class="provarea" >
									<ul>
										<c:if test="${appVO.ostype!='4'}">
											<c:if test="${not empty bundleList}">
												<c:forEach var="result" items="${bundleList }" varStatus="status">
												<li class="provli">
													<div>
														<input type="hidden" name="bundleSeq" value="${result.bundleSeq }">
														<input type="hidden" name="provSeq" value="${result.provSeq }">
														<input type="hidden" name="provTestGb" value="${result.provTestGb }">
														<input type="hidden" name="provId" value="${result.bundleName }">
														<input type="hidden" name="provOrgId" value="${result.provisonVO.provId }">
														<span>Name: </span><span>${result.provisonVO.distrProfileName}</span>,
														<span>Status : </span><span>
															<c:if test="${not empty result.provTestGb }">
																<c:choose>
																	<c:when test="${'2' eq result.provTestGb }">
																		AppStore
																	</c:when>
																	<c:otherwise>
																		Test
																	</c:otherwise>
																</c:choose>
															</c:if>
														</span>
															<!-- 
															 <span>ID: </span><span><input type="text" name="provId1" value="" readonly style="width:70px;"></span><span><input type="text" name="provId2" value="" style="width:60px;"></span>												
															 -->
															 
															<a class="removeProvBtn"	href="#remove_prov"><img	src="/images/btn_close_s.png" alt="provison 제거"></a>
													</div>
												</li>
												</c:forEach>
											</c:if>	
										</c:if>								
<!-- 										<li>
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
						<c:choose>
							<c:when test="${appVO.ostype == '4'}">
								<tr>
									<th class="versionName"><label class="title line_right" for="version"><em>*</em> <spring:message code='app.modify.text26.1' /></label></th>
									<td>
										<input id="verNum" name="verNum" type="text" style="width:95%;" value="${appVO.verNum }">  <!-- width:78% -->
										<%-- <a href="#" id="goToHistory" class="btn btnL btn_gray_light line_left"><spring:message code='contents.modify.004' /></a> --%>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<th class="versionName"><label class="title line_right" for="version"><em>*</em> <spring:message code='app.modify.text26' /></label></th>
									<td>
										<input id="verNum" name="verNum" type="text" style="width:78%;" value="${appVO.verNum }">
										<%-- <a id="appHistory" href="#appHistory" class="btn btnL btn_gray_light line_left"><spring:message code='contents.modify.004' /></a> --%>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
						
						<c:if test="${appVO.ostype == '4'}">
							<tr >
								<th><label class="title" for="version"><em>*</em> <spring:message code='app.modify.text26.2' /></label></th>
								<td>
									<input  id="versionCode" name="versionCode" type="text" value="${appVO.versionCode }" style="width:95%;">
								</td>
							</tr>
						</c:if>
						<tr>
							<th><label class="title" for="template"><spring:message code='app.modify.text24' /></label></th>
							<td>
								<input type="hidden" id="templateSeq" name="templateSeq" value="${appVO.templateSeq }"/>
								<input id="templateName" name="templateName" type="text" style="width:78%;" class="line_right" value="${appVO.templateName }">
								<a id="templateNameBtn" href="#templateNameBtn_" class="btn btnL btn_gray_light line_left"><spring:message code='app.modify.text25' /></a>
							</td>							
						</tr>
						<tr>
							<th><label class="title" for="ex"><spring:message code='app.modify.text17' /></label></th>
							<td>
								<textarea id="ex" name="descriptionText" cols="" rows="4" style="width:95%;">${appVO.descriptionText }</textarea>
							</td>
						</tr>
						<tr>
							<th><label class="title" for="mh"><spring:message code='app.modify.text27' /></label></th>
							<td>
								<textarea id="mh" name="chgText" cols="" rows="4" style="width:95%;">${appVO.chgText }</textarea>
							</td>
						</tr>
						<tr>
							<th><label class="title" for="icon"><em>*</em> <spring:message code='app.modify.text18' /></label></th>
							<td>
								<input id="icon" name="iconFile" type="file">
								<div class="thumb_area icon_area">
									<ul class="clfix">	
									<c:if test="${not empty appVO.iconSaveFile }">
										<li>
											<input type="hidden" name="iconOrgFile" value="${appVO.iconOrgFile }"/>
											<input type="hidden" name="iconSaveFile" value="${appVO.iconSaveFile }"/>
											<input type="hidden" name="thisFileType" value="icon"/>
											<img src="<spring:message code='file.upload.path.app.icon.file' />${appVO.iconSaveFile}" alt="">
											<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.modify.text71' />"></a>
										</li>
									</c:if>
									</ul>
								</div>
							</td>							
						</tr>
						<tr>
							<th><label class="title" for="scrn"><em>*</em> <spring:message code='app.modify.text21' /></label></th>
							<td>
								<input id="scrn" name="captureFile" type="file">							
								<div class="thumb_area">
									<ul class="clfix">
										<c:if test="${not empty captureList}">
											<c:forEach var="result" items="${captureList }" varStatus="status">
												<li>
													<input type="hidden" name="captureSeq" value="${result.captureSeq }"/>
													<input type="hidden" name="imgOrgFile" value="${result.imgOrgFile }"/>
													<input type="hidden" name="imgSaveFile" value="${result.imgSaveFile }"/>
													<input type="hidden" name="thisFileType" value="capture"/>											
													<img src="<spring:message code='file.upload.path.app.capture.file' />${result.imgSaveFile}" alt="">
													<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.modify.text71' />"></a>
												</li>										
											</c:forEach>
										</c:if>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='extend.local.061' /></span></th>
							<td>
								<div id="fixDiv" class="radio_area" style="margin:0;">
									<input name="useUserGb" id="um_1" type="radio" value="1" <c:if test="${appVO.useUserGb == 1}">checked="checked"</c:if>> <label style="margin-right:100px;" for="um_1"><spring:message code='template.modify.027' /></label>
									<input name="useUserGb" id="um_2" type="radio" value="2" <c:if test="${appVO.useUserGb == 2}">checked="checked"</c:if>> <label for="um_2"><spring:message code='extend.local.077' /><%-- <span id="useCnt" style="margin-right:5px">(${UserCnt}<spring:message code='template.modify.028_2' />)</span> --%></label>
									<a href="#" id="use_user_pop" class="btn btnL btn_gray_light"> <spring:message code='extend.local.028' /></a>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='app.modify.text29' /></span></th>
							<td>
								<div class="radio_area">
									<input name="useGb" id="u_y"  type="radio" value="1" <c:if test="${'1' eq appVO.useGb }">checked="checked"</c:if> /> <label for="u_y"><spring:message code='app.modify.text30' /></label>
									<input name="useGb" id="u_n"  type="radio" value="2" <c:if test="${'2' eq appVO.useGb }">checked="checked"</c:if> /> <label for="u_n"><spring:message code='app.modify.text31' /></label>
								</div>
							</td>
						</tr>
						<tr style="border-bottom : thin dotted #000000;">
							<th><span class="title"><em>*</em> <spring:message code='app.modify.text32' /></span></th>
							<td>
								<div class="radio_area">
									<input name="completGb" id="c_y"  type="radio" value="1" <c:if test="${'1' eq appVO.completGb }">checked="checked"</c:if>> <label for="c_y"><spring:message code='app.modify.text33' /></label>
									<input name="completGb" id="c_n"  type="radio" value="2" <c:if test="${'2' eq appVO.completGb }">checked="checked"</c:if>> <label for="c_n"><spring:message code='app.modify.text34' /></label>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='app.modify.text74' /></span></th>
							<td>
								<div class="radio_area">
									<input name="installGb" id="i_y"  type="radio" value="1" <c:if test="${'1' eq appVO.installGb }">checked="checked"</c:if>> <label for="i_y"><spring:message code='app.modify.text33' /></label>
									<input name="installGb" id="i_n"  type="radio" value="2" <c:if test="${'2' eq appVO.installGb }">checked="checked"</c:if>> <label for="i_n"><spring:message code='app.modify.text34' /></label>
								</div>
							</td>							
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='app.modify.text35' /></span></th>
							<td>
								<div class="radio_area coupon_area" style="width:100%">
									<span  style="width:100%; margin-top:9px;" >
										<input type="radio" name="distrGb" id="m1"  value="1" <c:if test="${'1' eq appVO.distrGb }">checked="checked"</c:if> /> <label for="m1"><spring:message code='app.modify.text36' /></label>
										<input type="radio" name="distrGb" id="m2"  value="2" <c:if test="${'2' eq appVO.distrGb }">checked="checked"</c:if>/> <label for="m2"><spring:message code='app.modify.text37' /></label><br/>
									</span>
									<span>
										<input type="radio" name="memDownGb" id="d1"  value="1" <c:if test="${'1' eq appVO.memDownGb }">checked="checked"</c:if>/><label for="d1"> <spring:message code='app.modify.text38' /></label>
										<input name="memDownAmt" type="text"  class="tCenter" style="width:120px; " value="${appVO.memDownAmt }"  onkeypress="return digit_check(event)"  maxlength="3" />
										&nbsp;&nbsp; <spring:message code='anonymous.option.010' />&nbsp;<input name ="memDownCnt" type="text" class="tCenter" style="width:120px;" value="${appVO.memDownCnt }" onkeypress="return digit_check(event)" maxlength="3"/>
									</span>
									<br/>
									<span>
										<input type="radio" name="memDownGb" id="d2"  value="2" <c:if test="${'2' eq appVO.memDownGb }">checked="checked"</c:if> /> <label for="d2"><spring:message code='app.modify.text39' /></label>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="d_SDATE" name="memDownStartDt" type="text" title="start date" class="date fmDate1" value="<fmt:formatDate value="${appVO.memDownStartDt}" pattern="yyyy-MM-dd"/>" />
										&nbsp;&nbsp;~&nbsp;&nbsp;
										<input id="d_EDATE" name="memDownEndDt" type="text" title="end date"   class="date toDate1" value="<fmt:formatDate value="${appVO.memDownEndDt}" pattern="yyyy-MM-dd"/>" />
									</span>
									<br>
									<span>
										<input type="radio" name="memDownGb" id="d3"  value="3" <c:if test="${'3' eq appVO.memDownGb }">checked="checked"</c:if> /> <label for="d3"><spring:message code='anonymous.option.002' /></label>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="title spacing"><em>*</em> <spring:message code='app.modify.text40' /></span> <!-- <br>&nbsp;&nbsp;&nbsp;<a href="#"><img src="/images/btn_q.jpg" alt="" title="쿠폰 사용 도움말"></a> --></th>
							<td>
								<div class="radio_area coupon_area" style="width:100%">
									<span style="width:100%;">
										<input name="couponGb" id="cou_y"  type="radio" value="1" <c:if test="${'1' eq appVO.couponGb }">checked="checked"</c:if>> <label for="cou_y"><spring:message code='app.modify.text33' /></label>
										<input name="couponGb" id="cou_n"  type="radio" value="2" <c:if test="${'2' eq appVO.couponGb }">checked="checked"</c:if>> <label for="cou_n" style="margin-right:78px"><spring:message code='app.modify.text41' /></label>
										<input id="couponNum" name="couponNum" type="text" style="width:43.2%;"  value="${appVO.couponNum }">
									</span>
									<br/>
									<span style="width:100%;">
										<input type="radio" name="nonmemDownGb" id="c1"  value="1" <c:if test="${'1' eq appVO.nonmemDownGb }">checked="checked"</c:if>/><label for="c1"> <spring:message code='app.modify.text42' /></label>
										<input name="nonmemDownAmt" type="text"  class="tCenter" style="width:120px; " value="${appVO.nonmemDownAmt }" onkeypress="return digit_check(event)" maxlength="3" />
										&nbsp;&nbsp; <spring:message code='anonymous.option.010' />&nbsp;<input name="nonmemDownCnt" type="text"  class="tCenter" style="width:120px; " value="${appVO.nonmemDownCnt }" onkeypress="return digit_check(event)" maxlength="3"/>
									</span>
									<br/>
									<span style="width:100%;">
										<input type="radio" name="nonmemDownGb" id="c2"  value="2" <c:if test="${'2' eq appVO.nonmemDownGb }">checked="checked"</c:if>/> <label for="c2"><spring:message code='app.modify.text43' /></label>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="c_SDATE" name="nonmemDownStarDt" type="text" title="start date" class="date fmDate2" value="<fmt:formatDate value="${appVO.nonmemDownStarDt}" pattern="yyyy-MM-dd"/>" />
										&nbsp;&nbsp;~&nbsp;&nbsp;
										<input id="c_EDATE" name="nonmemDownEndDt" type="text" title="end date"   class="date toDate2" value="<fmt:formatDate value="${appVO.nonmemDownEndDt}" pattern="yyyy-MM-dd"/>" />
									</span>
									<br>
									<span style="width:100%;">
										<input type="radio" name="nonmemDownGb" id="c3"  value="3" <c:if test="${'3' eq appVO.nonmemDownGb }">checked="checked"</c:if>/> <label for="c3"><spring:message code='anonymous.option.002' /></label>
									</span>
								</div>
							</td>
						</tr>
						<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
							<tr>
								<th scope="row"><span class="title"><em>*</em> <spring:message code='app.modify.text44' /></span></th>
								<td colspan="3">
									<div class="radio_area">
										<input name="limitGb" id="l_y"  type="radio" value="1" <c:if test="${'1' eq appVO.limitGb }">checked="checked"</c:if>> <label for="l_y"><spring:message code='app.modify.text45' /></label>
										<input name="limitGb" id="l_n"  type="radio" value="2" <c:if test="${empty appVO.limitGb || '2' eq appVO.limitGb }">checked="checked"</c:if>> <label for="l_n"><spring:message code='app.modify.text46' /></label>
									</div>
								</td>
							</tr>
						</sec:authorize>
					</table>
				</div>
				<c:if test="${'1' eq appVO.regGb }">
					<div class="btn_area_bottom tCenter">
						<a id="categorySearch" href="#categorySearch" class="btn btnL btn_gray_light"><spring:message code='app.modify.text47' /></a>
						<a id="inAppContentsSearch" href="#inAppContentsSearch" class="btn btnL btn_gray_light"><spring:message code='app.modify.text48' /></a>
					</div>
				</c:if>

				<div class="btn_area_bottom tCenter">
					<a id="registBtn" href="#registBtn_;" class="btn btnL btn_red"><spring:message code='app.modify.text49' /></a>
					<a href="javascript:cancelResist();" class="btn btnL btn_gray_light"><spring:message code='app.modify.text50' /></a>
					<!-- testModify -->
					<a id="appRequest" class="btn btnL btn_gray_light">앱 생성 요청</a>
				</div>
			</div>
			
				
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
<sec:authorize access="!hasRole('ROLE_INDIVIDUAL_MEMBER') && hasRole('ROLE_COMPANY_CREATOR') && !hasRole('ROLE_COMPANY_DISTRIBUTOR') && !hasRole('ROLE_COMPANY_MEMBER')">
<script>
$(document).ready(function(){
	var useGbTrIndex = $('[name=useGb]').parents('tr').index();
		$('.rowtable.writetable').find('tr').each(function(index){
			if(index>=useGbTrIndex){
				$(this).find("input:text").prop('readonly', true);
				$(this).find('input:radio').each(function(){
					if(!$(this).is(':checked')){
						$(this).attr('disabled', true);
					}
				});
			}
		});
	$("[name=limitGb]").attr('disabled', false);
});
</script>
</sec:authorize>