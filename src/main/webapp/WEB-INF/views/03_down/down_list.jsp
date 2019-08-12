<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>
</head>
<script type="text/javascript">
$(document).ready(function(){	
	<sec:authorize access="isAuthenticated()">  
		<sec:authentication property="principal.authorities" var="userRole" />
	</sec:authorize>

	
	21
	//배포자일경우엔, 모바일따로, 웹따로
	//그 외일 경우엔, 사용하실 장비에서 다운로드해야됩니다.
	var toChkVal = "";
	var shChkVal = '${ToChk_}';
	var shChkVal_ = "";
	
	if(/Android|iPhone|iPad/i.test(navigator.userAgent)){
		$("#isMobile").val("TRUE");
	}else{
		$("#isMobile").val("FALSE");
	}
	
	if(shChkVal != ""){
		shChkVal_ = shChkVal.split(",");
		for(var i=0; i < shChkVal_.length;i++){
			$('input[name=checkChkVal_'+shChkVal_[i]+']').prop('checked', true);
		}
	}else{
		$('input[name^=checkChkVal]:checkbox').prop('checked', true);
		$('input[name^=checkChkVal]:checked').each(function() {toChkVal += ","+$(this).val();});$("#toChk").val(toChkVal+",");	
		if(toChkVal){
			var to_Chk = $("#toChk").val();
			to_Chk = to_Chk.replace(/^.(\s+)?/, ""); 
			to_Chk = to_Chk.replace(/(\s+)?.$/, "");
			$("#toChk_").val(to_Chk);	
		}
	}
	
	$("#searchValue").keypress(function(e){
		if(e.keyCode=='13')goToSearch();
	});
	
	//전체선택 All
	$('#all').click(function () {
		$('input[name^=checkChkVal]:checkbox').prop('checked', this.checked);
		toChkVal = "";
		if(this.checked){
			$('input[name^=checkChkVal]:checked').each(function() {
				toChkVal += ","+$(this).val();
			});
			$("#toChk").val(toChkVal+",");
		}else{
			$("#toChk").val('');
		}
		
		goToSearch(1);
	});
	 
	$('#html5,#epub3,#pdf,#ios,#android').click(function () {
		toChkVal = "";
		var chkYN = $('input[name^=checkChkVal]:checked');
		if(chkYN.length == 5){
			$('#all').prop('checked', this.checked);
		}
		$('input[name^=checkChkVal]:checked').each(function() {
			toChkVal += ","+$(this).val();
		});

		toChkVal = toChkVal.replace(",,",',');
		if(chkYN.length == 0){
			$("#toChk").val('');
		}else{
			$("#toChk").val(toChkVal+",");
		}
		//alert(toChkVal);		
		goToSearch(1);
	});	

	
	/* if( /|AppleWebKit|/i.test(navigator.userAgent) && "<c:out value='${userRole}'/>" == "[ROLE_COMPANY_DISTRIBUTOR]"){
		alert("배포자 다운로드");
	}else{
		
	}
	*/
	

	
	$('.downloadGo').click(function () {
		
		var thisVal = $(this).attr("alt");
		var spVal = thisVal.split("|&|");

//		if("<c:out value='${userRole}'/>" == "[ROLE_COMPANY_DISTRIBUTOR]" || "<c:out value='${userRole}'/>" == "[ROLE_COMPANY_MIDDLEADMIN]" || "<c:out value='${userRole}'/>" == "[ROLE_COMPANY_CREATOR]" ){
		if(${fn:indexOf(menuFunction, '131') != -1 }){
			if(!(/Android|iPhone|iPad/i.test(navigator.userAgent))){
				//데스크탑일때
				if( spVal[0] == "CONTENTS" ){
					//콘텐츠 인 경우

					$('#downGubun').val(spVal[0]);
					$('#downKaU').val(spVal[1]);
					$('#downName').val(spVal[2]);
					$('#downVer').val(spVal[3]);
					$('#downSeq').val(spVal[4]);
					$('#ostype').val(spVal[5]);
					$('#fileName').val(spVal[6]);
					$('#downCnt').val(spVal[8]);
					//2는 현재 일반다운로드라는 뜻
					$('#isCoupon').val('2');
					downloadCounting('2', spVal);
					document.downFrm.action='/down/down.html';
					document.downFrm.submit();
					return ;
				}
				if("Android" == spVal[5]){
					//앱인 경우
					//2는 현재 일반다운로드
					downloadCounting('2', spVal);
					if("apk" == spVal[13]){
						$.ajax({
						    type: 'HEAD',
						    url: "${path}android/"+spVal[1]+spVal[3]+"/"+spVal[6]+spVal[3]+".apk",
						    success: function() {
						    	window.location.href="${path}android/"+spVal[1]+spVal[3]+"/"+spVal[6]+spVal[3]+".apk";
								console.log("${path}android/"+spVal[1]+spVal[3]+"/"+spVal[6]+spVal[3]+".apk")
						    },  
						    error: function() {
						        alert('<spring:message code='down.list.030' />');
						    }
						});
						//window.location.href="${path}android/"+spVal[1]+spVal[3]+"/"+spVal[6]+spVal[3]+".apk";
						//console.log("${path}android/"+spVal[1]+spVal[3]+"/"+spVal[6]+spVal[3]+".apk")
					}
					else{
						$.ajax({
						    type: 'HEAD',
						    url: "${path}contents/"+spVal[1]+"/contents.zip",
						    success: function() {
						    	window.location.href="${path}contents/"+spVal[1]+"/contents.zip";
								console.log("${path}contents/"+spVal[1]+"/contents.zip")
						    },  
						    error: function() {
						        alert('<spring:message code='down.list.030' />');
						    }
						});
						//window.location.href="${path}contents/"+spVal[1]+"/contents.zip";
						//console.log("${path}contents/"+spVal[1]+"/contents.zip")
					}

				}else {
					//2는 현재 일반다운로드 라는 뜻
					downloadCounting('2', spVal);
					$.ajax({
						    type: 'HEAD',
						    url: "${pathIos}"+spVal[1]+spVal[3]+"/"+spVal[6]+spVal[3]+".ipa",
						    success: function() {
						    	window.location.href="${pathIos}"+spVal[1]+spVal[3]+"/"+spVal[6]+spVal[3]+".ipa";
								console.log("${pathIos}"+spVal[1]+spVal[3]+"/"+spVal[6]+spVal[3]+".ipa")
						    },  
						    error: function() {
						    	alert('<spring:message code='down.list.030' />');
						    }
					});
					/*window.location.href="${path}ios/"+spVal[1]+spVal[3]+"/"+spVal[6]+spVal[3]+".ipa";
					console.log("${path}ios/"+spVal[1]+spVal[3]+"/"+spVal[6]+spVal[3]+".ipa")*/
				}
			}else{

				if(spVal[0]!="APP" ||spVal[1] != ""){
					//if(confirm("다운로드하겠습니까?")){
						
						$('#downGubun').val(spVal[0]);
						$('#downKaU').val(spVal[1]);
						$('#downName').val(spVal[2]);
						$('#downVer').val(spVal[3]);
						$('#downSeq').val(spVal[4]);
						$('#ostype').val(spVal[5]);
						$('#fileName').val(spVal[6]);
						$('#downCnt').val(spVal[8]);
						$("#iconName").val(spVal[12]);
						//2는 현재 일반다운로드라는 뜻
						$('#isCoupon').val('2');
						document.downFrm.action='/down/down.html';
						document.downFrm.submit();
					//}
				}else{
					//message : KEY값이나 URL 주소값이 없습니다.
					alert("<spring:message code='down.list.024' />");
					return;
				}
			}
		}else{
			//배포자가 아닐경우
			if(!(/Android|iPhone|iPad/i.test(navigator.userAgent))){
				//데스크탑일 경우
				if(spVal[0] == "APP" ){
					//앱인 경우
					alert("<spring:message code='down.list.029' />");
					return;
				}
				if( spVal[0] == "CONTENTS" ){
					//콘텐츠 인 경우

					
					$('#downGubun').val(spVal[0]);
					$('#downKaU').val(spVal[1]);
					$('#downName').val(spVal[2]);
					$('#downVer').val(spVal[3]);
					$('#downSeq').val(spVal[4]);
					$('#ostype').val(spVal[5]);
					$('#fileName').val(spVal[6]);
					$('#downCnt').val(spVal[8]);
					$("#iconName").val(spVal[12]);
					//2는 현재 일반다운로드라는 뜻
					$('#isCoupon').val('2');
					downloadCounting('2', spVal);
					document.downFrm.action='/down/down.html';
					document.downFrm.submit();
				}
			}else{
				//모바일일 경우
				if(spVal[0]!="APP" ||spVal[1] != ""){
					//if(confirm("다운로드하겠습니까?")){
						
						$('#downGubun').val(spVal[0]);
						$('#downKaU').val(spVal[1]);
						$('#downName').val(spVal[2]);
						$('#downVer').val(spVal[3]);
						$('#downSeq').val(spVal[4]); 
						$('#ostype').val(spVal[5]);
						$('#fileName').val(spVal[6]);
						$('#downCnt').val(spVal[8]);
						$("#iconName").val(spVal[12]);
						//2는 현재 일반다운로드라는 뜻
						$('#isCoupon').val('2');
						$("#downFrm").submit();
						document.downFrm.action='/down/down.html';
						document.downFrm.submit();
					//}
				}
				//모바일일 경우 콘텐츠는 다운로드 목록에 아예 뜨지가 않습니다.
				//따라서 이경우는 단순히 특정에러메시지만 띄웁니다.
				else{
					//message : KEY값이나 URL 주소값이 없습니다.
					alert("<spring:message code='down.list.024' />");
					return;
				}
			}
		}
	});		
	
	//var downLoadPopup;
	var winName = 'DownLoad';
	$('.btn_coupon').click(function () {
		//alert("*");
		var winWidth = 1000;
		var winHeight = 370;
		var winPosLeft = (screen.width - winWidth)/2;
		var winPosTop = (screen.height - winHeight)/2;
		//var distrProfile = $('.detail_area.identity_area').find('a').text();
		//var url = "/down/poplist.html?distrProfile=" + distrProfile;		
		var url = "/down/pop_coupon.html";		
		var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
		//if(!downLoadPopup){
			//downLoadPopup = window.open(url, winName, opt);			
		//}
		window.open(url, winName, opt);
	});
});	


function downloadCounting(isCoupon, spVal){
	//isCoupon이 1  => 쿠폰이다
	//isCoupon이 2  => 일반 다운로드
	$.ajax({
		url: "/downloadCounting.html" ,
		type: "POST" ,
		data:{
			"downCnt"   : spVal[8],
			"downSeq"   : spVal[4],
			"sort"      : spVal[0],
			"isCoupon"  : isCoupon
		},
		success: function (result){
			switch (result){
				case 0 : 
					break ;
				case 1 : 
					break ;
			}
		}
	});
}



function goToSearch(_page){
	//var page = _page?_page:$('#page').val();
	
	var to_Chk = $("#toChk").val();
	

	to_Chk = to_Chk.replace(/^.(\s+)?/, ""); 
	to_Chk = to_Chk.replace(/(\s+)?.$/, "");
	$("#toChk_").val(to_Chk);
	
	$('#currentPage').val('1');
	$('#isMobile').val('${param.isMobile}');
	if(_page){
		$('#currentPage').val(_page);
	}
	
	document.downFrm.action ="/down/list.html";
	document.downFrm.submit();
	/* window.location.href='/down/list.html?currentPage='+_page+'&isMobile=${param.isMobile}&toChk_='+to_Chk; */
}

function goToViewScreen(path){
	var fullPath = "<spring:message code='file.path.contents.file' />"+path+"/view/pb.html";
	$.ajax({
	    type: 'HEAD',
	    url: "<spring:message code='file.path.contents.file' />"+path+"/view/pb.html",
	    success: function() {
	    	window.open(fullPath);
	    },  
	    error: function() {
	        alert('<spring:message code='down.list.030' />');
	    }
	});
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
		<div class="contents list_area">
			<h2><spring:message code='down.list.001' /></h2>
			<!--
			mem_down_gb as DOWNYN, CAST(IFNULL(mem_down_cnt,0) as UNSIGNED) as DOWNCNT, 
			CAST(IFNULL(mem_down_amt,0) as UNSIGNED) as DOWNAMT, 
			DATE_FORMAT(mem_down_start_dt, '%Y-%m-%d') as MEMDOWNSTDT, DATE_FORMAT(mem_down_end_dt, '%Y-%m-%d') as MEMDOWNENDT, 
			coupon_gb as COUPON_YN, nonmem_down_gb as NONDOWNYN, 
			CAST(IFNULL(nonmem_down_cnt,0) as UNSIGNED) as NONDOWNCNT, CAST(IFNULL(nonmem_down_amt,0) as UNSIGNED) as NONDOWNAMT, 
			DATE_FORMAT(nonmem_down_star_dt, '%Y-%m-%d') as NONMEMDOWNSTDT, DATE_FORMAT(nonmem_down_end_dt, '%Y-%m-%d') as NONMEMDOWNENDT,
			  -->
			<!-- section -->
			<div class="section fisrt_section">
				<div class="con_header clfix">
					<div class="form_area fRight">
					<form name="downFrm" id="downFrm" method="post" action="/down/list.html" data-ajax="false" >
						<input type="hidden" name="toChk"       id="toChk" 	     value="${ToChk}" />
						<input type="hidden" name="toChk_"      id="toChk_" 	 value="${ToChk_}" />
						<input type="hidden" name="currentPage" id="currentPage" value="${MapList.currentPage }">
						<input type="hidden" name="downGubun"   id="downGubun"   value="">
						<input type="hidden" name="DownGubun"   id="DownGubun"   value="">
						<input type="hidden" name="downName"    id="downName"    value="">
						<input type="hidden" name="downKaU"     id="downKaU"     value="">
						<input type="hidden" name="DownKaU"     id="DownKaU"     value="">
						<input type="hidden" name="downVer"     id="downVer"     value="">
						<input type="hidden" name="DownVer"     id="DownVer"     value="">
						<input type="hidden" name="downSeq"     id="downSeq"     value="">
						<input type="hidden" name="downCnt"     id="downCnt"     value="">
						<input type="hidden" name="isMobile"    id="isMobile"    value="${param.isMobile}">
						<input type="hidden" name="isCoupon"    id="isCoupon"    value="">
						<input type="hidden" name="downType"    id="downType"    value="">
						<input type="hidden" name="directDown"  id="directDown"  value="">
						<input type="hidden" name="2"     		id="downVer"     value="">
						<input type="hidden" name="3"     		id="downSeq"     value="">
						<input type="hidden" name="ostype"      id="ostype"      value="">
						<input type="hidden" name="coupon_Num"  id="coupon_Num"  value="">
						<input type="hidden" name="fileName"    id="fileName"    value="">
						<input type="hidden" name="iconName"    id="iconName"    value="">
							<fieldset>
								<div class="checkbox_area">
									<input name="checkChkVal_0" id="all" class="checkChkVal" type="checkbox" value="0">
									<label for="all">All</label>
									<input name="checkChkVal_1" id="html5" class="checkChkVal" type="checkbox" value="1">
									<label for="html5">HTML5</label>
									<input name="checkChkVal_2" id="epub3" class="checkChkVal" type="checkbox" value="2">
									<label for="epub3">ePub3</label>
									<input name="checkChkVal_3" id="pdf" class="checkChkVal" type="checkbox" value="3">
									<label for="pdf">PDF</label>
									<input name="checkChkVal_5" id="ios" class="checkChkVal" type="checkbox" value="5">
									<label for="ios">iOS</label>
									<input name="checkChkVal_4" id="android" class="checkChkVal" type="checkbox" value="4">
									<label for="android">Android</label>
								</div>
								<select id="searchType" name="searchType">
									<option value="" <c:if test="${MapList.searchType == ''}">selected="selected"</c:if>><spring:message code='down.list.008' /></option>									
									<option value="NAME" <c:if test="${MapList.searchType == 'NAME'}">selected="selected"</c:if>><spring:message code='down.list.011' /></option>																											
								</select>
								<input id="searchValue" name="searchValue" type="text" value="${MapList.searchValue }" style="width:250px;">
								<a href="javascript:goToSearch();" class="btn btnM"><spring:message code='down.list.010' /></a>
							</fieldset>
						</form>
					</div>
				</div>
				<!-- table_area -->
				<div class="table_area">
					<table class="coltable">
						<colgroup>
							<col style="width:40px">
							<col style="width:">
							<col style="width:90px">
							<col style="width:90px">
							<col style="width:110px">
							<col style="width:120px">
							<c:if test="${'NOMB' eq param.isMobile}">
							<col style="width:80px">
							</c:if>
							<col style="width:120px">
							<col style="width:90px">
						</colgroup>
						<caption></caption>
						<tr>
							<th scope="col">&nbsp;</th>
							<c:if test="${'NOMB' eq param.isMobile}">
							<th scope="col"><spring:message code='down.list.011' /></th>
							</c:if>
							<th scope="col"><spring:message code='down.list.012' /></th>
							<th scope="col"><spring:message code='down.list.013' /></th>
							<th scope="col"><spring:message code='down.list.014' /></th>
							<th scope="col"><spring:message code='down.list.015' /></th>
							<th scope="col"><spring:message code='app.list.table.col6' /></th>
							<th scope="col"><spring:message code='down.list.016' /></th>
							<th scope="col"><spring:message code='down.list.017' /></th>
						</tr>
					<c:choose>
						<c:when test="${empty DownList}">
						<tr>
							<td align="center" colspan="8" ><spring:message code="list.nodata.001" /></td>
						</tr>
						</c:when>
						<c:otherwise>
								<c:forEach var="result" items="${DownList}" varStatus="status">					
								<%-- <sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')"> --%>
								<tr>
									<td><c:if test="${not empty result.ICON_NAME && result.GUBUN eq 'APP'}"><img src="<spring:message code='file.upload.path.app.icon.file' />${result.ICON_NAME }" alt="" style="max-width:24px;" /></c:if><c:if test="${result.GUBUN eq 'CONTENTS'}"></c:if></td>
									<td>${result.NAME}</td>
									<c:if test="${'NOMB' eq param.isMobile}">
									<td>${result.OSTYPE}<!-- _${result.OSCODE}--></td>
									</c:if>
									<td>${result.VERNUM}</td>
									<td><c:if test="${result.CATETYPE == 1}"><spring:message code='down.list.021' /></c:if><c:if test="${result.CATETYPE == 2}"><spring:message code='down.list.020' /></c:if><c:if test="${result.CATETYPE == 3}">CONTENTS</c:if></td>
									<td><c:if test="${result.SCOPE_YN == 1}"><spring:message code='down.list.018' /></c:if><c:if test="${result.SCOPE_YN == 2}"><spring:message code='down.list.019' /></c:if></td>
									<td>${result.SIZE }</td>
									<td><fmt:formatDate value="${result.CHGDATE}" pattern="yyyy-MM-dd" type="date" /></td>
									<%--  --%>
									<c:choose>
										<c:when test="${ 'CONTENTS' eq result.GUBUN }">
											<td><a href="javascript:goToViewScreen('${fn:substring(result.FILENAME, 0, fn:indexOf(result.FILENAME, '.'))}');" class="btn btnXS view">view</a></td>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${'NOMB' eq param.isMobile}">
													<c:choose>
														<c:when test="${fn:indexOf(menuFunction, '133') != -1 }">
															<td>
																<c:choose>
																	<c:when test="${result.OSTYPE == 'Android'}">
																		<a href="#1" style="text-decoration:none !important; margin-right:5px;" class="btnXS downloadGo" alt="${result.GUBUN}|&|${result.KEYVAL}|&|${result.NAME}|&|${result.VERNUM}|&|${result.SEQ}|&|${result.OSTYPE}|&|${result.FILENAME}|&|${result.PROVISION_GUBUN}|&|${result.DOWNCNT}|&|${result.DOWNYN}|&|${result.MEMDOWNSTDT}|&|${result.MEMDOWNENDT}|&|${result.ICON_NAME }|&|apk" >apk</a><a href="#1" style="text-decoration:none !important;" class="btnXS downloadGo" alt="${result.GUBUN}|&|${result.KEYVAL}|&|${result.NAME}|&|${result.VERNUM}|&|${result.SEQ}|&|${result.OSTYPE}|&|${result.FILENAME}|&|${result.PROVISION_GUBUN}|&|${result.DOWNCNT}|&|${result.DOWNYN}|&|${result.MEMDOWNSTDT}|&|${result.MEMDOWNENDT}|&|${result.ICON_NAME }|&|zip" >zip</a>
																	</c:when>
																	<c:otherwise>
																		<a href="#1" style="text-decoration:none !important; margin-right:5px;" class="btnXS downloadGo" alt="${result.GUBUN}|&|${result.KEYVAL}|&|${result.NAME}|&|${result.VERNUM}|&|${result.SEQ}|&|${result.OSTYPE}|&|${result.FILENAME}|&|${result.PROVISION_GUBUN}|&|${result.DOWNCNT}|&|${result.DOWNYN}|&|${result.MEMDOWNSTDT}|&|${result.MEMDOWNENDT}|&|${result.ICON_NAME }|&|ipa" >ipa</a>
																	</c:otherwise>
																</c:choose>
															</td>
														</c:when>
														<c:otherwise>
															<td><a href="#1" class="btn btnXS downloadGo" alt="${result.GUBUN}|&|${result.KEYVAL}|&|${result.NAME}|&|${result.VERNUM}|&|${result.SEQ}|&|${result.OSTYPE}|&|${result.FILENAME}|&|${result.PROVISION_GUBUN}|&|${result.DOWNCNT}|&|${result.DOWNYN}|&|${result.MEMDOWNSTDT}|&|${result.MEMDOWNENDT}|&|${result.ICON_NAME }" >download</a></td>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<td><a href="#1" class="btn btnXS downloadGo" alt="${result.GUBUN}|&|${result.KEYVAL}|&|${result.NAME}|&|${result.VERNUM}|&|${result.SEQ}|&|${result.OSTYPE}|&|${result.FILENAME}|&|${result.PROVISION_GUBUN}|&|${result.DOWNCNT}|&|${result.DOWNYN}|&|${result.MEMDOWNSTDT}|&|${result.MEMDOWNENDT}|&|${result.ICON_NAME }" >download</a></td>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
				</div><!-- //table_area -->
				<!--페이징-->
				<div class="paging">
					<c:if test="${MapList.startPage != 1 }">																								<!--message : 이전 페이지로 이동  -->
					<a href="javascript:goToSearch('${MapList.startPage-MapList.pageSize}');" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='down.list.027' />"></a>
					</c:if>
					<c:forEach var="i" begin="${MapList.startPage }" end="${MapList.endPage}">
						<c:if test="${MapList.currentPage==i }"><span class="current"><c:out value="${i}"/></span></c:if>
						<c:if test="${MapList.currentPage!=i }"> <a href="javascript:goToSearch('${i}');"><c:out value="${i}"/></a></c:if>
	<%-- 					    <c:out value="${i}"/>
						    <c:if test="${param.page==i }"></strong></c:if> --%>
					</c:forEach>
					<c:if test="${MapList.endPage*MapList.maxResult < MapList.totalCount}">														<!--message : 다음 페이지로 이동  -->
						<a href="javascript:goToSearch('${MapList.endPage+1}');" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='down.list.028' />"></a>
					</c:if>
				</div>
				<!--//페이징-->

				<!-- btn area -->
				<div class="btn_area_bottom fRight clfix">
					<a class="btn btnL btn_red btn_coupon" href="#1"><spring:message code='down.list.022' /></a>
				</div>
				<!-- //btn area -->
				
			</div><!-- //section -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>
