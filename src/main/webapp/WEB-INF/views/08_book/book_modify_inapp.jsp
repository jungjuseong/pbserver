<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>

<script type="text/javascript" src="/js/jquery.form.mini.js" ></script>
<script src="/js/jquery.validate.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	
	<sec:authorize access="isAuthenticated()">  
		<sec:authentication property="principal.authorities" var="userRole" />
	</sec:authorize>


	var availableCnt = 0;
	if('${availableCnt}'){
		availableCnt = '${availableCnt}';
	}

	var useUserVal = '${ivo.useUserGb}';
	if(useUserVal == 1){
		$("#use_user_pop").hide();
	}else if(useUserVal == 2){
		$("#use_user_pop").show();
	}

	// 사용자 지정 버튼 위치 번경 ( 초기화 )
	var iconHeight = parseInt($("#icon").css('height'));
	var captureHeight = parseInt($("#capture").css('height'));
	var popUpHeight = parseInt($("#use_user_pop").css('top'));
	
	$("#use_user_pop").css('top', popUpHeight+iconHeight+"px");

	$("[name=useUserGb]").change( function(){
		if($(this).val() == '1'){
			$("#use_user_pop").hide();
		}else if($(this).val() == '2'){
			$("#use_user_pop").show();
		}
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

	$("#searchValue").keypress(function(e){
		if(e.keyCode=='13')goToSearch();
	});

	//alert('${appVO.distrGb}');
	completGbStatus('load');
	memDownGbStatus();nomemDownGbStatus();nomemCoupon('load');

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
			if(fileType =="coverFile" || fileType == "bodydFile"){
				alert("기존 PDF파일을 삭제 하셔야 합니다.")
				return false;
			}
			// message : 기존 이미지를 삭제 하셔야 합니다.
			alert("<spring:message code='app.inapp.modify.text32' />")
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
			
			alert("<spring:message code='app.inapp.modify.text32' />");
			return false;
		}
		$('#appForm').attr('action', actionUrl);
		$('#fileType').val(fileType);
		$(this).after('<p id="uploadPercent"><p>');
		ajaxFileUpload(parseInt(imgCnt)+1);
	});

	
	/* //파일 선택 이벤트
	$('input[type=file][name=iconFile], input[type=file][name=captureFile], input[type=file][name=coverFile], input[type=file][name=bodyFile]').change(function(){
		var fileType = $(this).attr("name");
		var actionUrl = '/app/iconfileupload.html';
		var maxImgCnt = 1;
		var imgCnt = $(this).parent().find('ul').find('li').size();
		if(fileType=='captureFile'){
			actionUrl = '/app/capturefileupload.html';
			maxImgCnt = 5;
		}
		if(fileType=="coverFile"){
			actionUrl = '/book/coverFileupload.html';
			maxImgCnt = 4;
		}
		if(maxImgCnt==imgCnt){
			
			alert("<spring:message code='app.inapp.modify.text32' />");
			return false;
		}
		$('#appForm').attr('action', actionUrl);
		$('#fileType').val(fileType);
		$(this).after('<p id="uploadPercent"><p>');
		ajaxFileUpload(parseInt(imgCnt)+1);
	}); */
	//파일 업로드 & 리턴
	function ajaxFileUpload(fileCnt){
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
				}else if(fileType == 'bodyFile' || fileType == 'coverFile'){
					var type = "pdf";
				}
				if(error=='none'){
					var li = '<li>';
					if(type=='img'){
						li += '<input type="hidden" name="captureSeq" value="0"/>';
						li += "<img id='capture' src='"+webPath+"' alt='"+orgFileName+"'>";
						li += '<input type="hidden" name="'+type+'OrgFile" value="'+orgFileName+'"/>'; 
						li += '<input type="hidden" name="'+type+'SaveFile" value="'+saveFileName+'"/>'; 
						li += '<input type="hidden" name="thisFileType" value="temp"/>'; 
						li += '<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.modify.text44' />"></a></li>'; 
					}else if(type == 'pdf'){
						/*<span>${ivo.inappOrgFile }</span>				
						  <a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.modify.text44' />"></a> 
						*/
						li += "cover"+fileCnt+".pdf";
						li += "<a style='position:relative; left:65px; top:-19px;' class='removeImgBtn' href='#remove_img'><img src='/images/btn_close_s.png' alt='<spring:message code='app.inapp.modify.text44' />'></a>";
					}
					else{
						li += "<img id='icon' src='"+webPath+"' alt='"+orgFileName+"'>"; 
						li += '<input type="hidden" name="'+type+'OrgFile" value="'+orgFileName+'"/>'; 
						li += '<input type="hidden" name="'+type+'SaveFile" value="'+saveFileName+'"/>'; 
						li += '<input type="hidden" name="thisFileType" value="temp"/>'; 
						li += '<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.modify.text44' />"></a></li>'; 
					}

					$('[name='+fileType+']').parents('td').find('ul').append(li);				
				}else{
					alert(error);
				}
				//$('input[type=file][name=iconFile], input[type=file][name=captureFile], input[type=file][name=coverFile], input[type=file][name=bodyFile]').val('');
				$('input[type=file][name=iconFile], input[type=file][name=captureFile]').val('');
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

	//파일 선택시 기존파일
	$('input[type=file][name=inappFile]').click(function(e){
		var fileType = $(this).attr("name");
		var maxImgCnt = 1;
		var imgCnt = $(this).parent().find('ul').find('li').size();
		
		if("<c:out value='${userRole}'/>" == "[ROLE_ADMIN_SERVICE]"){
			e.preventDefault();
		}else{
			if(maxImgCnt==imgCnt){
				//message : 기존 파일을 삭제하셔야 합니다.
				alert("<spring:message code='app.inapp.modify.text33' />");
				return false;
			}
		}
	});
	//파일 선택시 기존파일
	$('input[type=file][name=inappFile]').change(function(e){		
		
		//파일 확장자체크
		var filename = $(this).val();
		var dot = filename.lastIndexOf(".");
		var ext = filename.substring(dot+1).toLowerCase();
		alert('['+ext+']');
		if(!(ext=='zip')){
			//message : 잘못된 파일입니다. zip파일만 등록할 수 있습니다.
			alert("<spring:message code='app.inapp.modify.text34' />");
			$(this).val('');
			return false;
		}
	});
	
					
	//카테고리검색팝업 작업중
	$('#categorySearch').click(function(){
		var winWidth = 680;
		var winHeight = 400;
		var winPosLeft = (screen.width - winWidth)/2;
		var winPosTop = (screen.height - winHeight)/2;
		var url = "/app/category/category_write.html?storeBundleId=${ivo.storeBundleId}&isInapp=Y";		
		var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
		//if(!templatePopup){
			categoryPopup = window.open(url, "categoryPopup", opt);			
		//}	
	});	

	$('#registBtn').click(function(){	
		$("#appForm").attr('action', '/book/inapp/modify.html');
		var isVaid = customValidate({
			 	 rules: {

	
			    	categoryName:{
			    		required: true
			    	},    	
	
			    	verNum:{
			    		required: true,
			    		maxlength : 20
			    	}	   	
			    },

			    messages: {

					categoryName: {
						//message : 카테고리 이름을 입력해주세요.
						required: "<spring:message code='app.inapp.modify.text37' />"
					},
			    	verNum:{
						//message : 버전을 입력해주세요.
			    		required: "<spring:message code='app.inapp.modify.text38' />",
						//message : 20자 이하로 입력해주세요.
			    		mmaxlength : "<spring:message code='app.inapp.modify.text39' />"
			    	} 
				},
				
				 errorPlacement: function(error, element) {
					error.appendTo( element.parent("td") );
				},
				validClass:"success"
			});		

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
					if(!$(this).val()&&radioName!='couponNum'){
						var msgObj = $(this).parents('span').find('label');
												//message : 를 입력하셔야 합니다.
						alert($(msgObj).text()+"<spring:message code='app.inapp.modify.text40' />");
						$(this).focus();
						isVaid = false;
						unvlid++;
						return false;
					}else if(!$(this).val()&&radioName=='couponNum'){
						if($(this).prop('readonly')==false){
							//message : 쿠폰번호를 입력하셔야 합니다. 
							alert("<spring:message code='app.inapp.modify.text41' />");
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
			/* if(isVaid){
				//inappSaveFile
				//inappFile
				if(!$('[name=inappSaveFile]').size()&&!$('[name=inappFile]').val()){
					isVaid= false;
					//message : 인 앱 파일을 추가하셔야 합니다.
					alert("<spring:message code='app.inapp.modify.text42' />");
					$('[name=inappFile]').focus();
					return false;
				}
			} */
			var form = document.getElementById('appForm');

			//String inappName, String appSeq,
			if(isVaid && ($("#nameChanged").val() == '2')){

				var inappName = $("#inappName").val();
				var appSeq = $("[name=appSeq]").val();
				$.ajax({
                    url: "/app/inapp/checkIfInappName.html" ,
                    type: "POST" ,
                    async: false,
                    data:{
                       "inappName" : inappName,
                       "appSeq" : appSeq
                                   },
                                   success: function (result){
                                           if (result == true){
                                           }else{
                                        	   alert("<spring:message code='extend.local.062' />");
                                        	   isVaid = false;
                                        	   return false;
                                           }
                                   }
                    });
				if(isVaid == false) return false;
			}

		 	if(isVaid){
		 		form.submit();		 		
		 	}
	});
	
	//식별자 삭제
	$(document).on('click', '.removeProvBtn', function(){
		$(this).parents('li').remove();
	});
	
	//radio controll
	var completGb;
	//completGbStatus('load');
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

	function completGbStatus(type){
		completGb  = $('input:radio[name=completGb]:checked').val();
		var radioArr = ["distrGb", "memDownGb", "couponGb", "nonmemDownGb"];
		if(completGb==2){
			$('input[type=radio]').each(function(index){
				if(radioArr.indexOf($(this).attr('name'))>-1){
					$(this).prop('checked', false).attr('disabled', 'disabled');
				}
			}).promise().done( function(){ memDownGbStatus();nomemDownGbStatus();nomemCoupon();} );
		}else{
			var cname = '';
			var pname = '';
			if(type=='action'){
				var appContentsAmt = '${appContentsAmt}';
				var appContentsGb = '${appContentsGb}';
				if(appContentsGb!='3'){
					if(appContentsAmt <= availableCnt){
						$("[name=completGb]").eq(1).prop("checked", true);
						alert("<spring:message code='app.inapp.list.text17' />");
						return false;
					}
				}
				
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
			}
		}
	}
	
	//배포범위 다운로드 유효기간 radio action
	function memDownGbStatus(){
		$('input:radio[name=memDownGb]').each(function(index){
			//alert(index+'==='+$(this).is(':checked'));
			if($(this).is(':checked')==false){
				$(this).parents('span').find('input:text').val('').prop('readonly', true);
			}else{
				$(this).parents('span').find('input:text').prop('readonly', false);
			}
		});
	}

	//쿠폰다운로드 유효기간 radio action
	function nomemDownGbStatus(){
		$('input:radio[name=nonmemDownGb]').each(function(index){
			//alert(index+'==='+$(this).is(':checked'));
			if($(this).is(':checked')==false){
				$(this).parents('span').find('input:text').val('').prop('readonly', true);
			}else{
				$(this).parents('span').find('input:text').prop('readonly', false);
			}
		});
	}

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
				setcoupNum();
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
				setcoupNum();
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
				//message : 를 입력하셔야 합니다.
				alert($(msgObj).text()+"<spring:message code='app.inapp.modify.text40' />");
				$(this).focus();
				radioTextValid = false;
				unvlid++;
				return false;
			}else if(!$(this).val()&&radioName=='couponNum'){
				if(!$(this).is('readonly')){
					//message : 쿠폰번호를 입력하셔야 합니다.
					alert("<spring:message code='app.inapp.modify.text41' />");
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
	$('#categoryName').focus(function(e){
		e.preventDefault();
		$('#categorySearch').focus().click();
	});
	
	$("#verNum").focusout(function(){
		var verNum = $("#verNum").val();

		var pattern = /^[0-9]{1}[.]{1}[0-9]{1}$/;
		if(verNum.length != 0){
			var huk = pattern.test(verNum);
			
			if(!huk){
				alert("<spring:message code='anonymous.option.015' />")
				$("#verNum").val("");
				return
			}
		}
	});
	
	$("#icon, #scrn").click(function(e){
		if("<c:out value='${userRole}'/>" == "[ROLE_ADMIN_SERVICE]"){
			e.preventDefault();
		}else{
			
		}
	});
	
	$("#inappName").keyup(function(){
		var inappName = $(this).val();
		var tempName = $("#tempName").val();
		if(inappName !=  tempName) $("#nameChanged").val('2');
		else $("#nameChanged").val('1');
	});
	
	$("#use_user_pop").click( function(){
		var target_ = "userPop";
		var useS = $("#useS").val();
		var inappSeq = $("[name=inappSeq]").val();
	  	window.open("/assignment/user.html?useS="+useS+"&inappSeq="+inappSeq,target_,"width=985, height=450, top=100, left=100, resizable=no, menubar=no, scrollbars=no");
	});
	
	$("#appRequest").click( function(){
		var inappSeqTest = '${param.inappSeq}'
		$.ajax({
            url: "/book/createTextAppRequestPOST.html" ,
            type: "POST" ,
            async : false ,
            data:{
            	dataType : "inapp",
            	inappSeq : inappSeqTest,
            	storeBundleId : '${param.storeBundleId}'
            },
            success: function (result){
               switch (result){
                    case 0 : alert("요청 실패" );
                      break ;
                    case 1 : alert("변환 되었습니다.");
                      break ;
                    case 2 : alert("세션이 만료되었습니다. 다시 로그인해 주십시오.");
                    	break;
                    case 3 : alert("앱 생성후 인앱을 생성할 수 있습니다.")
                    	break;
                    case 5 : alert("알수 없는 에러 ");
                    	break;
               }
            }
        });

	});
});
function cancelResist(){
	if(confirm("<spring:message code='app.inapp.modify.text43' />")){
		document.listFrm.action='/book/inapp/list.html';
		document.listFrm.submit();		
	}
}


</script>
</head>


<body>


<!-- wrap -->
<div id="wrap" class="sub_wrap">

	<!-- conteiner -->
	<div id="container">
		<div class="contents join_area" style="margin: 145px 20px 100px;">
		<form name="listFrm" method="get" action="/book/inapp/list.html" >
			<input type="hidden" name="currentPage" id="currentPage" value="1">
			<input type="hidden" name="searchType" id="searchType" value="${inAppList.searchType }">
			<input type="hidden" name="searchValue" id="searchValue" value="${inAppList.searchValue }">
			<input type="hidden" name="storeBundleId" value="${ivo.storeBundleId }">
		</form>
		<form name="appForm" id="appForm" method="post" enctype="multipart/form-data" action="" >
			<input type="hidden" name="inappSeq" value="${ivo.inappSeq }" /><!-- 앱구분 단일앱, 서가앱 -->
			<input type="hidden" name="storeBundleId" value="${ivo.storeBundleId }" /><!-- 앱구분 단일앱, 서가앱 -->
			<input type="hidden" name="currentPage" value="1">
			<input type="hidden" name="searchType" value="${inAppList.searchType }">
			<input type="hidden" name="searchValue" value="${inAppList.searchValue }">
			<input type="hidden" name="fileType" id="fileType" /><!-- 아이콘인지 캡쳐이미지인지 -->
			<input type="hidden" name="saveFileSeq" id="saveFileSeq" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="saveFileName" id="saveFileName" /><!-- 파일삭제시 사용할 저장명 -->
			<input type="hidden" name="fileStatus" id="fileStatus" />
			<input type="hidden" name="useS" id="useS" value="${useS }" />
			<input type="hidden" name="nameChanged" id="nameChanged" value="1" />
			<input type="hidden" name="tempName"    id="tempName" value="${ivo.inappName }"/>
			<!-- 인 앱 콘텐츠 -->
			<h2><spring:message code='app.inapp.modify.text1' /></h2>
			
			<div class="section fisrt_section">
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:120px">
							<col style="width:210px">
							<col style="width:120px">
							<col style>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><label class="title" for="name"><em>*</em> 제목</label></th>
								<td style="width:51%;">
									<input id="inappName" name="inappName" type="text" value="${ivo.inappName }" style="width:80%;">						
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
									<c:choose>
										<c:when test='${not empty meta.inappMetaCover1}'>
											<div id="uploadInput">
												<input type="hidden" name="inappOrgFile" value="${meta.inappMetaCover1 }"/>
														<input type="hidden" name="thisFileType" value="cover1"/>
														<span>${meta.inappMetaCover1 }</span>
														<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.modify.text44' />"></a>
											</div>
										</c:when>
										<c:when test="${empty meta.inappMetaCover1}">
											<div id="uploadInput">
											</div>
										</c:when>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="file"> 표지2</label></th>
								<td colspan="3">
									<input id="cover" name="coverFile" type="file" style="width :95%">
									<c:choose>
										<c:when test='${not empty meta.inappMetaCover2}'>
											<div id="uploadInput">
												<input type="hidden" name="inappOrgFile" value="${meta.inappMetaCover2 }"/>
														<input type="hidden" name="thisFileType" value="cover1"/>
														<span>${meta.inappMetaCover2 }</span>
														<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.modify.text44' />"></a>
											</div>
										</c:when>
										<c:when test="${empty meta.inappMetaCover2}">
											<div id="uploadInput">
											</div>
										</c:when>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="file"> 표지3</label></th>
								<td colspan="3">
									<input id="cover" name="coverFile" type="file" style="width :95%">
									<c:choose>
										<c:when test='${not empty meta.inappMetaCover3}'>
											<div id="uploadInput">
												<input type="hidden" name="inappOrgFile" value="${meta.inappMetaCover3 }"/>
														<input type="hidden" name="thisFileType" value="cover1"/>
														<span>${meta.inappMetaCover3 }</span>
														<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.modify.text44' />"></a>
											</div>
										</c:when>
										<c:when test="${empty meta.inappMetaCover3}">
											<div id="uploadInput">
											</div>
										</c:when>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="file"> 표지4</label></th>
								<td colspan="3">
									<input id="cover" name="coverFile" type="file" style="width :95%">
									<c:choose>
										<c:when test='${not empty meta.inappMetaCover4}'>
											<div id="uploadInput">
												<input type="hidden" name="inappOrgFile" value="${meta.inappMetaCover4 }"/>
														<input type="hidden" name="thisFileType" value="cover1"/>
														<span>${meta.inappMetaCover4 }</span>
														<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.modify.text44' />"></a>
											</div>
										</c:when>
										<c:when test="${empty meta.inappMetaCover4}">
											<div id="uploadInput">
											</div>
										</c:when>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="file"> 본문</label></th>
								<td colspan="3">
									<input id="body" name="bodyFile" type="file" style="width :95%">
									<c:choose>
										<c:when test='${not empty meta.inappMetaBody}'>
											<div id="uploadInput">
												<input type="hidden" name="inappOrgFile" value="${meta.inappMetaBody }"/>
														<input type="hidden" name="thisFileType" value="cover1"/>
														<span>${meta.inappMetaBody }</span>
														<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.modify.text44' />"></a>
											</div>
										</c:when>
										<c:when test="${empty meta.inappMetaBody}">
											<div id="uploadInput">
											</div>
										</c:when>
									</c:choose>
								</td>
							</tr>

	<%-- 					<tr>
								<th scope="row"><label class="title" for="file"><em>*</em> <spring:message code='app.inapp.modify.text7' /></label></th>
								<td colspan="3">
									<input id="scrn" name="inappFile" type="file">
										<ul class="clfix">
											<c:if test="${not empty ivo.inappSaveFile }">
												<li>
													<input type="hidden" name="inappOrgFile" value="${ivo.inappOrgFile }"/>
													<input type="hidden" name="inappSaveFile" value="${ivo.inappSaveFile }"/>
													<input type="hidden" name="thisFileType" value="inapp"/>
													<span>${ivo.inappOrgFile }</span>
													<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.modify.text44' />"></a>
												</li>
											</c:if>
										</ul>
								</td>
							</tr> --%>
							<tr>
								<th scope="row"><label class="title" for="icon"><spring:message code='app.inapp.modify.text11' /></label></th>
								<td colspan="3">
									<input id="icon" name="iconFile" type="file">								
									<div class="thumb_area icon_area">
										<ul class="clfix">
										<c:if test="${not empty ivo.iconSaveFile }">
											<li>
												<input type="hidden" name="iconOrgFile" value="${ivo.iconOrgFile }"/>
												<input type="hidden" name="iconSaveFile" value="${ivo.iconSaveFile }"/>
												<input type="hidden" name="thisFileType" value="icon"/>
												<img id="icon" src="<spring:message code='file.upload.path.inapp.icon.file' />${ivo.iconSaveFile}" alt="">
												<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.modify.text44' />"></a>
											</li>
										</c:if>
										</ul>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="scrn"><spring:message code='app.inapp.modify.text12' /></label></th>
								<td colspan="3">
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
														<img id="capture" src="<spring:message code='file.upload.path.inapp.capture.file' />${result.imgSaveFile}" alt="">
														<a class="removeImgBtn" href="#remove_img"><img src="/images/btn_close_s.png" alt="<spring:message code='app.inapp.modify.text44' />"></a>
													</li>
												</c:forEach>
											</c:if>
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
								<td style="width:500px">
									<div class="radio_area">
										<input name="useUserGb" id="um_1" type="radio" value="1" <c:if test="${ '1' eq ivo.useUserGb }">checked="checked"</c:if>> <label style="margin-right:95px;" for="um_1"><spring:message code='template.modify.027' /></label>
										<input name="useUserGb" id="um_2" type="radio" value="2" <c:if test="${ '2' eq ivo.useUserGb }">checked="checked"</c:if>> <label style="margin:0px;" for="um_2"><spring:message code='extend.local.077' /></label>
										<a  href="#use_user_pop"  id="use_user_pop" class="btn btnL btn_gray_light"> <spring:message code='extend.local.028' /></a>
									</div>
								</td>
							</tr>
							<tr>
								<th><span class="title"><em>*</em> <spring:message code='extend.local.079' /></span></th>
								<td style="width:500px">
									<div class="radio_area">
										<input name="screenType" id="um_1" type="radio" value="1" <c:if test="${ '1' eq ivo.screenType }">checked="checked"</c:if>> <label style="margin-right:81px;" for="um_1"><spring:message code='extend.local.080' /></label>
										<input name="screenType" id="um_2" type="radio" value="2" <c:if test="${ '2' eq ivo.screenType }">checked="checked"</c:if>> <label for="um_2"><spring:message code='extend.local.081' /></label>
									</div>
								</td>
							</tr>
							<tr>
								<th><span class="title"><em>*</em> <spring:message code='app.inapp.modify.text13' /></span></th>
								<td>
									<div class="radio_area">
										<input name="useGb" id="u_y"  type="radio" value="1" <c:if test="${'1' eq ivo.useGb }">checked="checked"</c:if> /><label for="u_y"><spring:message code='app.inapp.modify.text14' /></label>
										<input name="useGb" id="u_n"  type="radio" value="2" <c:if test="${'2' eq ivo.useGb }">checked="checked"</c:if> /><label style="margin:0px;" for="u_n"><spring:message code='app.inapp.modify.text15' /></label>
									</div>
								</td>
							</tr>
							<tr>
								<th><span class="title"><em>*</em> <spring:message code='app.inapp.modify.text16' /></span></th>
								<td>
									<div class="radio_area">
										<input name="completGb" id="c_y"  type="radio" value="1" <c:if test="${'1' eq ivo.completGb }">checked="checked"</c:if>><label for="c_y"><spring:message code='app.inapp.modify.text14' /></label>
										<input name="completGb" id="c_n"  type="radio" value="2" <c:if test="${'2' eq ivo.completGb }">checked="checked"</c:if>><label style="margin:0px;" for="c_n"><spring:message code='app.inapp.modify.text15' /></label>
									</div>
								</td>
							</tr>
							<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">>
								<tr>
									<th scope="row"><span class="title"><em>*</em><spring:message code='app.inapp.modify.text27' /></span></th>
									<td colspan="3">
										<div class="radio_area">
											<input name="limitGb" id="l_y"  type="radio" value="1" <c:if test="${'1' eq ivo.limitGb }">checked="checked"</c:if>> <label for="l_y"><spring:message code='app.inapp.modify.text28' /></label>
											<input name="limitGb" id="l_n"  type="radio" value="2" <c:if test="${empty ivo.limitGb || '2' eq ivo.limitGb }">checked="checked"</c:if>> <label for="l_n"><spring:message code='app.inapp.modify.text29' /></label>
										</div>
									</td>
								</tr>
							</sec:authorize>
						</tbody>
					</table>
				</div>

				<div class="btn_area_bottom tCenter">
					<a id="registBtn" href="#registBtn_;" class="btn btnL btn_red"><spring:message code='app.inapp.modify.text30' /></a>
					<a href="javascript:cancelResist();" class="btn btnL btn_gray_light"><spring:message code='app.inapp.modify.text31' /></a>
					<a id="appRequest" href="#" class="btn btnL btn_gray_light"> 인앱 만들기 요청</a>
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