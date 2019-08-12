
(function($) {

	var userAgent = navigator.userAgent.toLowerCase();

	//------------------------------------------------------------------------------
	// 즐겨찾기
	//------------------------------------------------------------------------------
	$.fn.fnAddFvr = function( pUrl, pTit ) {
		if ( pUrl != "" ) {
			window.external.AddFavorite( pUrl, pTit );
		}
	}

	//------------------------------------------------------------------------------
	// input/selectbox 입력 값 체크
	//------------------------------------------------------------------------------
	$.fn.fnIptChk = function() {

		var bTf = false;

		if ( $.trim(this.val()) == "" ) bTf = false;
		else bTf = true;

		return bTf;

	};

	//------------------------------------------------------------------------------
	// Enter Key 이동
	//------------------------------------------------------------------------------
	$.fn.fnEntKey = function(pFunc) {

		if ( window.event && window.event.keyCode == 13 ) {
			eval(pFunc);
		}

	};

	//------------------------------------------------------------------------------
	// radio/checkbox 선택 체크
	//------------------------------------------------------------------------------
	$.fn.fnCkChk = function() {

		var bTf = false;

		if ( $("input[name='" + this.attr("name") + "']").is(":checked") ) bTf = true;
		else bTf = false;

		return bTf;

	};

	$.fnBrowser = {
		version: (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1],
		safari: /webkit/.test( userAgent ),
		opera: /opera/.test( userAgent ),
		msie: /msie/.test( userAgent ) && !/opera/.test( userAgent ),
		mozilla: /mozilla/.test( userAgent ) && !/(compatible|webkit)/.test( userAgent )
	};

	//------------------------------------------------------------------------------
	// 팝업 : JQUERY
	//------------------------------------------------------------------------------
	$.fn.popupWindow = function(instanceSettings){
		
		return this.each(function(){
		
		$(this).click(function(){
		
		$.fn.popupWindow.defaultSettings = {
			centerBrowser:0, // center window over browser window? {1 (YES) or 0 (NO)}. overrides top and left
			centerScreen:0, // center window over entire screen? {1 (YES) or 0 (NO)}. overrides top and left
			height:500, // sets the height in pixels of the window.
			left:0, // left position when the window appears.
			location:0, // determines whether the address bar is displayed {1 (YES) or 0 (NO)}.
			menubar:0, // determines whether the menu bar is displayed {1 (YES) or 0 (NO)}.
			resizable:0, // whether the window can be resized {1 (YES) or 0 (NO)}. Can also be overloaded using resizable.
			scrollbars:0, // determines whether scrollbars appear on the window {1 (YES) or 0 (NO)}.
			status:0, // whether a status line appears at the bottom of the window {1 (YES) or 0 (NO)}.
			width:500, // sets the width in pixels of the window.
			windowName:null, // name of window set from the name attribute of the element that invokes the click
			windowURL:null, // url used for the popup
			top:0, // top position when the window appears.
			toolbar:0 // determines whether a toolbar (includes the forward and back buttons) is displayed {1 (YES) or 0 (NO)}.
		};
		
		settings = $.extend({}, $.fn.popupWindow.defaultSettings, instanceSettings || {});
		
		var windowFeatures =    'height=' + settings.height +
								',width=' + settings.width +
								',toolbar=' + settings.toolbar +
								',scrollbars=' + settings.scrollbars +
								',status=' + settings.status + 
								',resizable=' + settings.resizable +
								',location=' + settings.location +
								',menuBar=' + settings.menubar;

				settings.windowName = this.name || settings.windowName;
				settings.windowURL = this.href || settings.windowURL;
				var centeredY,centeredX;

				if(settings.centerBrowser){
					if ($.fnBrowser.msie) {//hacked together for IE browsers
						centeredY = (window.screenTop - 120) + ((((document.documentElement.clientHeight + 120)/2) - (settings.height/2)));
						centeredX = window.screenLeft + ((((document.body.offsetWidth + 20)/2) - (settings.width/2)));
					}else{
						centeredY = window.screenY + (((window.outerHeight/2) - (settings.height/2)));
						centeredX = window.screenX + (((window.outerWidth/2) - (settings.width/2)));
					}
					window.open(settings.windowURL, settings.windowName, windowFeatures+',left=' + centeredX +',top=' + centeredY).focus();
				}else if(settings.centerScreen){
					centeredY = (screen.height - settings.height)/2;
					centeredX = (screen.width - settings.width)/2;
					window.open(settings.windowURL, settings.windowName, windowFeatures+',left=' + centeredX +',top=' + centeredY).focus();
				}else{
					window.open(settings.windowURL, settings.windowName, windowFeatures+',left=' + settings.left +',top=' + settings.top).focus();	
				}
				return false;
			});
			
		});	
	};

	//------------------------------------------------------------------------------
	// 리턴 : 팝업 창 중앙 JS
	//------------------------------------------------------------------------------
	$.fn.fnPopWinCt = function (pUrl, pTitle, pW, pH, pScrlYn) {
		var lsWinOption;
		var sSYN;
		//var iLeft, iTop;
		iLeft = (screen.width - pW)/2;
		iTop = (screen.height - pH)/2;

		sSYN = pScrlYn;
		switch (pScrlYn) {
		case 'Y' : sSYN = 'yes'; break;
		case 'N' : sSYN = 'no'; break;
		default : sSYN = 'auto'; break;
		}

		pTitle = pTitle.replace(" ", "_");

		lsWinOption = "width=" + pW + ", height=" + pH;

		lsWinOption += " toolbar=no, directories=no, status=no, menubar=no, location=no, resizable=no, left=" + iLeft + ", top=" + iTop + ", scrollbars="+sSYN;
		var loNewWin = window.open(pUrl, pTitle, lsWinOption);

		loNewWin.focus();
	};

	//------------------------------------------------------------------------------
	// 페이징 이동 함수
	//------------------------------------------------------------------------------
	$.fn.fnPageMove = function( pGo ) {
		var oFrmNm = $(this);

		oFrmNm.attr( "target", "" );
		oFrmNm.find( "input[name='shPageNo']" ).val(pGo);
		oFrmNm.submit();
	};

	//------------------------------------------------------------------------------
	// 검색
	//------------------------------------------------------------------------------
	$.fn.fnSearch = function( pPath ) {
		$("input[name='shPageNo']").val( "1" );

		$("#frmMove").attr("method", "post"); //method
		$("#frmMove").attr("target", "_self"); //target 지정
		$("#frmMove").attr("action", pPath).submit();
	};

	//------------------------------------------------------------------------------
	// 파일다운로드
	//------------------------------------------------------------------------------
	$.fn.fnFlDn = function( pVal ) {
		var frmDn = $(this);

		frmDn.find("input[name='arrFileNm']").val( pVal ); 
		frmDn.attr( "target", "frProc" ); 
		frmDn.attr( "method", "post" ); 
		frmDn.attr( "action", "/include/function/Lib_FileDown.asp" ); 
		frmDn.submit();
	};

	//------------------------------------------------------------------------------
	// SelectBox 전체 체크
	//------------------------------------------------------------------------------
	$.fn.fnSelBoxAll = function( pNm ) {
		var fCkTf = $(this).is(":checked");

		$("input:checkbox[name=" + pNm + "]").each(function(){
			$(this).prop("checked", fCkTf);
		});
	};

	//------------------------------------------------------------------------------
	// 아이디 저장 쿠키
	//------------------------------------------------------------------------------
	$.fn.fnIdSvCook = function( pObj ) {
		var fCkTf = $(this).is(":checked");
alert(fCkTf)
		// 쿠키 저장
		if ( fCkTf ) {
			$.cookie( "ID_CHK_YN", true );
			$.cookie( "USER_ID", pObj.val() );
		}
		// 쿠키 삭제
		else {
			$.removeCookie("ID_CHK_YN");
			$.removeCookie("USER_ID");
		}

		if ( fCkTf ) {
			if ( $.cookie("USER_ID") != null && $.cookie("USER_ID") != "" ) {
				pObj.val( $.cookie("USER_ID") );
				$(this).prop("checekd", fCkTf);
			}
		}

	};

	//------------------------------------------------------------------------------
	// 이메일 저장 쿠키
	//------------------------------------------------------------------------------
	$.fn.fnEmailSvCook = function( pObj ) {
		var fCkTf = $(this).is(":checked");

		// 쿠키 저장
		if ( fCkTf ) {
			$.cookie( "EMAIL_CHK_YN", true );
			$.cookie( "STU_EMAIL", pObj.val() );
		}
		// 쿠키 삭제
		else {
			$.removeCookie("EMAIL_CHK_YN");
			$.removeCookie("STU_EMAIL");
		}

		if ( fCkTf ) {
			if ( $.cookie("STU_EMAIL") != null && $.cookie("STU_EMAIL") != "" ) {
				pObj.val( $.cookie("STU_EMAIL") );
				$(this).prop("checekd", fCkTf);
			}
		}

	};

})(jQuery);


//------------------------------------------------------------------------------
// 달력 : jquery-ui-1.10.3.min.js > datepicker
//------------------------------------------------------------------------------
$(function(){
	var date = new Date();
	var dates = $( ".nlDate" ).datepicker({
		regional:'ko',
		dateFormat:"yy-mm-dd",
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true // 전월, 익월 표시
	});

	var dates = $( ".btDate" ).datepicker({
		regional:'ko',
		dateFormat:"yy-mm-dd",
		changeMonth: true,
		changeYear: true,
		yearRange: 'c-100:c',
		showOtherMonths: true // 전월, 익월 표시
	});

	$( ".fmDate1" ).datepicker({
		regional:'ko',
		dateFormat:"yy-mm-dd",
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true, // 전월, 익월 표시
		onClose: function( selectedDate ) {
			$( ".toDate1" ).datepicker( "option", "minDate", selectedDate );
		}
	});

	$( ".toDate1" ).datepicker({
		regional:'ko',
		dateFormat:"yy-mm-dd",
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true, // 전월, 익월 표시
		onClose: function( selectedDate ) {
			$( ".fmDate1" ).datepicker( "option", "maxDate", selectedDate );
		}
	});

	$( ".fmDate2" ).datepicker({
		regional:'ko',
		dateFormat:"yy-mm-dd",
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true, // 전월, 익월 표시
		onClose: function( selectedDate ) {
			$( ".toDate2" ).datepicker( "option", "minDate", selectedDate );
		}
	});

	$( ".toDate2" ).datepicker({
		regional:'ko',
		dateFormat:"yy-mm-dd",
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true, // 전월, 익월 표시
		onClose: function( selectedDate ) {
			$( ".fmDate2" ).datepicker( "option", "maxDate", selectedDate );
		}
	});

	$( ".fmDate3" ).datepicker({
		regional:'ko',
		dateFormat:"yy-mm-dd",
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true, // 전월, 익월 표시
		onClose: function( selectedDate ) {
			$( ".toDate3" ).datepicker( "option", "minDate", selectedDate );
		}
	});

	$( ".toDate3" ).datepicker({
		regional:'ko',
		dateFormat:"yy-mm-dd",
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true, // 전월, 익월 표시
		onClose: function( selectedDate ) {
			$( ".fmDate3" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
	
	$( ".coDate1" ).datepicker({
		regional:'ko',
		dateFormat:"yy-mm-dd",
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true // 전월, 익월 표시
	});
	
/*//	//숫자만 입력되게 하는 클래스
//	$('.onlyNum').keypress(function(event){
//		event = (event) ? event : window.event;
//	    var charCode = (event.which) ? event.which : event.keyCode;
//	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
//	    	event.preventDefault();
//	        return false;
//	    }
////		alert(keyCode);
////		var keyCode = event.keyCode;
////		if(keyCode<49){
////			//alert(event.keyCode);
////			event.preventDefault();
////		}
////		if(keyCode>57){
////			event.preventDefault();
////		}
//		
//	});
*/});

//------------------------------------------------------------------------------
// 리턴 : 팝업 창 중앙 JS
//------------------------------------------------------------------------------
function fnPopWinDrt( pUrl, pTitle, pW, pH, pScrlYn ) {
	var lsWinOption;
	var sSYN;

	//var iLeft, iTop;
	iLeft = (screen.width - pW)/2;
	iTop = (screen.height - pH)/2;

	sSYN = pScrlYn;
	switch (pScrlYn) {
	case 'Y' : sSYN = 'yes'; break;
	case 'N' : sSYN = 'no'; break;
	default : sSYN = 'auto'; break;
	}

	pTitle = pTitle.replace(" ", "_");

	lsWinOption = "width=" + pW + ", height=" + pH;

	lsWinOption += " toolbar=no, directories=no, status=no, menubar=no, location=no, resizable=no, left=" + iLeft + ", top=" + iTop + ", scrollbars="+sSYN;
	var loNewWin = window.open(pUrl, pTitle, lsWinOption);

	loNewWin.focus();
}

//------------------------------------------------------------------------------
// 메인 다이내믹 팝업 주소, 제목, 가로길이, 세로길이, 좌측 공간, 상단공간, 스크롤 여부
//------------------------------------------------------------------------------
function fnPopMain(pUrl, pTitle, pW, pH, pL, pT, pScrlYn) {
    var lsWinOption;
    var sSYN;

    //var iLeft, iTop;
    iLeft = pL;
    iTop = pT;

    sSYN = pScrlYn;
    switch (pScrlYn) {
        case 'Y': sSYN = 'yes'; break;
        case 'N': sSYN = 'no'; break;
        default: sSYN = 'auto'; break;
    }

    pTitle = pTitle.replace(" ", "_");

    lsWinOption = "width=" + pW + ", height=" + pH;

    lsWinOption += " toolbar=no, directories=no, status=no, menubar=no, location=no, resizable=no, left=" + iLeft + ", top=" + iTop + ", scrollbars=" + sSYN;
    var loNewWin = window.open(pUrl, pTitle, lsWinOption);
	if (!loNewWin){
		//alert("팝업차단을 해제해주세요.");
		return
	}
	loNewWin.focus();
	return loNewWin;
}


//------------------------------------------------------------------------------
// 기능 : 동영상
//------------------------------------------------------------------------------
function fnMovPly(sUrl, sW, sH, sMode) {
	document.write("<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='"+sW+"' height='"+sH+"'>");
	document.write("<param name='movie' value='"+sUrl+"'>");
	document.write("<param name='quality' value='high'>");
	document.write("<param name='WMODE' value='"+sMode+"'>");
	document.write("<embed src='"+sUrl+"' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='"+sW+"' height='"+sH+"'></embed>");
	document.write("</object>");
}


//------------------------------------------------------------------------------
// SNS 연동
//------------------------------------------------------------------------------
function fnSnsSend( pTy, pVal, pUrl) {
	var href = "";
	var stat = "";
	switch(pTy)
	{
		case "sns1cnt":		// Twitter
			href = "http://twitter.com/?status=" + encodeURIComponent(text) + " " + encodeURIComponent(pUrl);
			stat = "";
			break;
		case "sns2cnt":		// FaceBook
			href = "http://www.facebook.com/sharer/sharer.php?t="+ encodeURIComponent(text) +"&u="+ encodeURIComponent(pUrl);
			stat = "";
			break;
		case "sns3cnt":		// Me2Day
			href = "http://me2day.net/posts/new?new_post[body]=\"" + encodeURIComponent(text) + " " + encodeURIComponent(pUrl);
			stat = "";
			break;
		case "sns4cnt":		// YozmDaum
			href = "http://yozm.daum.net/api/popup/prePost?link=" + encodeURIComponent(pUrl) + "&prefix=" + encodeURIComponent(prefix);
			stat = "width=466, height=356";
			break;
		case "sns5cnt":		// 誘몃━以鍮�
			//href = "http://me2day.net/posts/new?new_post[body]=\"" + encodeURIComponent(text) + "\":" + encodeURIComponent(pUrl);
			//stat = "";
			break;
	}
	window.open(href, "ContentSend", stat);

}

/**
 * 서브및 할때 validation
 {
 	 rules: {
    	appContentsAmt:{//-->input id
    		required: true,//필수항목
    		maxlength : 10,//최대길이
    		number : true//숫자만
    	}    	
    },
    messages: {
    	appContentsAmt: {
			required: "콘텐츠 수량을 입력해주세요",
			maxlength: "용량이 너무 큽니다.",
			number : "수량은 숫자로만 입력할 수 있습니다."
		}
	},	
	 errorPlacement: function(error, element) {
		error.appendTo( element.parent("td") );
	},
	validClass:"success"
}
 */
function customValidate(data){
	//alert(data.messages.appContentsAmt.required);
	var isValid = false;
	var rules = data.rules;
	var messages = data.messages;
	var num_check=/^[0-9]*$/;
	//var html ='';
	for(var index in rules){
		//html +=index+'=='+rules[index]+'\\n';
		for(var index2 in rules[index]){
			if(index2=='required'&&rules[index][index2]&&$('#'+index).val()==''){
				alert(messages[index][index2]);
				$('#'+index).focus();
				return false;
			}
			//console.log(index);
			if(index2=='maxlength'&&rules[index][index2]<$('#'+index).val().length){
				alert(messages[index][index2]);
				$('#'+index).focus();
				return false;
			}
			if(index2=='number'&&rules[index][index2]&&!num_check.test($('#'+index).val())){
				alert(messages[index][index2]);
				$('#'+index).focus();
				return false;
			}
		}
	} 
	isValid = true;
	return isValid
	//alert(html);
}
function digit_check(evt){
	var code = evt.which?evt.which:event.keyCode;
	if(code < 48 || code > 57){
	return false;
	}
}
