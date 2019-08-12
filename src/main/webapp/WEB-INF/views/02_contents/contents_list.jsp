<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>

<script type="text/javascript" src="/js/jquery.form.js"></script>
<script type="text/javascript" src="/js/jquery-dateFormat.js"></script>
<script>

$(document).ready(function(){
	/* $("[name = SORT]:checkbox").change( function(){
		if($("#all:checekd").length){
			
		}
	}); */
	
	/* var downloadInfo = $('#downloadInfo')[0].outerHTML;
	
	var 
	alert(downloadInfo.substring(119, 129)); */
	var toChkVal = "";
	//var shChkVal = '${ToChk}';
	var shChkVal = '${ToChk}';
	var shChkVal_ = "";
	
	if(shChkVal != ""){
//	 	alert("Hello111");
		shChkVal_ = shChkVal.split(",");
		for(var i=0; i < shChkVal_.length;i++){
			$('input[name=checkChkVal_'+shChkVal_[i]+']').prop('checked', true);
		}
	}else{
//		alert("Hello222");
		$('input[name^=checkChkVal]:checkbox').prop('checked', true);
		$('input[name^=checkChkVal]:checked').each(function() {toChkVal += ","+$(this).val();});$("#toChk").val(toChkVal+",");	
		/* if(toChkVal){
			var to_Chk = $("#toChk").val();
			to_Chk = to_Chk.replace(/^.(\s+)?/, ""); 
			to_Chk = to_Chk.replace(/(\s+)?.$/, "");
			$("#toChk_").val(to_Chk);	
		}		 */
	}
	
	$('#all').click(function(event){
		if(!$('#all').is(":checked")){
			event.preventDefault() ;
		}else{
			$('input[name^=checkChkVal]:checkbox').prop('checked', true);
			$("#toChk").val(',1,2,3,4,');
			$("#toChk_").val('1,2,3,4');
			$("#page").val("1");
			$("#sortForm").submit();
		}
	});
	
	
	$('#html5,#epub3,#pdf').click(function () {
		$("#searchValue").val("");
		
		
		toChkVal = "";
		var chkYN = $('input[name^=checkChkVal]:checked');
		if(chkYN.length == 3){
			$('#all').prop('checked', this.checked);
		}
		$('input[name^=checkChkVal]:checked').each(function() {
			toChkVal += ","+$(this).val();
		});

		toChkVal = toChkVal.replace(",,",',');
		if(chkYN.length == 0){
			$("#toChk").val(',1,2,3,4,');
		}else{
			$("#toChk").val(toChkVal+",");
		}
		
		var to_Chk = $("#toChk").val();
		to_Chk = to_Chk.replace(/^.(\s+)?/, ""); 
		to_Chk = to_Chk.replace(/(\s+)?.$/, "");
		$("#toChk_").val(to_Chk);	
		$("#page").val("1");
		$("#sortForm").submit();
		/* goToSearch();
		 */
	});		
	
	$("#searchBtn").click(function(){
		toChkVal = "";
		var chkYN = $('input[name^=checkChkVal]:checked');
		if(chkYN.length == 3){
			$('#all').prop('checked', this.checked);
		}
		$('input[name^=checkChkVal]:checked').each(function() {
			toChkVal += ","+$(this).val();
		});

		toChkVal = toChkVal.replace(",,",',');
		if(chkYN.length == 0){
			$("#toChk").val(',1,2,3,4,');
		}else{
			$("#toChk").val(toChkVal+",");
		}
		
		var to_Chk = $("#toChk").val();
		to_Chk = to_Chk.replace(/^.(\s+)?/, ""); 
		to_Chk = to_Chk.replace(/(\s+)?.$/, "");
		
		
		$("#toChk_").val(to_Chk);
		$("#page").val("1");
		$("#sortForm").submit();
	});
	
	$(".downloadGo").click(function(){
		var thisVal = $(this).attr("alt");
		var spVal = thisVal.split("|&|");
		
		var memDownGb = spVal[0];
		var memDownCnt = spVal[1];
		var memDownAmt = spVal[2];
		var memDownStartDt = spVal[3];
		var memDownEndDt = spVal[4];
		var uploadSaveFile = spVal[5];
		var contentsSeq = spVal[6];
		var contentsType = spVal[7];
		var uploadOrgFile  = spVal[8];
		
		//데스크 탑인 경우
		if(!(/Android|iPhone|iPad/i.test(navigator.userAgent))){
			switch (memDownGb){
			case '1' :
				if(parseInt(memDownCnt) >= parseInt(memDownAmt)){
					alert("<spring:message code='down.pop.013' />");
					//다운로드 할 수 없으므로 막음
					return;
				}else{
					//계쏙 진행
				}
				break;
			case '2' :
				//substring 한 이유
				//2015-05-15 00:00:00 에서 00:00:00을 빼기위함..
				//
				if(compareDateBetweenCurrentAndValidDate(memDownStartDt.substring(0,10), memDownEndDt.substring(0, 10))){
					//다운로드가 가능한상태
				}else{
					//다운로드가 불가능한 상태
					return;
				}
				break;
			case '3' :
				//제한없음 상태
				break;
		}
			/*	<input type="hidden" name = "ostype" value=""/>
				<input type="hidden" name = "orgFileName" value=""/>
				<input type="hidden" name = "saveFileName" value=""/>  */
			
			downloadCounting("2", memDownCnt, contentsSeq, "CONTENTS");
			$("#saveFileName").val(uploadSaveFile);
			$("#orgFileName").val(uploadOrgFile);
			$("#downSeq").val(contentsSeq);
			switch(contentsType){
				case '1' :
					$("#ostype").val("HTML5");
					break;
				case '2' :
					$("#ostype").val("ePub3");
					break;
				case '3' :
					$("#ostype").val("PDF");
					break;
			}
			
			/*var pathss = uploadSaveFile.split(".");
			alert("usr/local/apache-tomcat-7.0.57/webapps/DocsBuilderCMS/_uplod/data/contents/"+pathss[0]+"/"+uploadOrgFile);
			$.ajax({
			    type: 'HEAD',
			    url: "usr/local/apache-tomcat-7.0.57/webapps/DocsBuilderCMS/_uplod/data/contents/"+pathss[0]+"/"+uploadOrgFile,
			    success: function() {
			    	document.downloadForm.action = "/down/down.html";
					document.downloadForm.submit();
			    },  
			    error: function() {
			        alert('<spring:message code='down.list.030' />');
			    }
			});*/
			var saveName = $("#saveFileName").val()
			var onlySaveName = saveName.substring(0,saveName.lastIndexOf("."));
			$.ajax({
			    type: 'HEAD',
			    url: "<spring:message code='file.path.contents.file' />"+onlySaveName+"/"+$("#saveFileName").val(),
			    success: function() {
			    	document.downloadForm.action = "/down/down.html";
			    	document.downloadForm.submit();
			    },  
			    error: function() {
			        alert('<spring:message code='down.list.030' />');
			    }
			});
			
			/* window.location.href='${defaultDownloadUrl}' + uploadSaveFile; */
			
		}else{
			//모바일인 경우
			//message : 웹 브라우저에서만 다운로드가 가능합니다.
			alert("<spring:message code='down.pop.012' />");
			return;

		}
		/* ${defaultDownloadUrl}${contentList.list[i].uploadSaveFile} */
	});
	
	$('#searchValue').keypress(function(event) {
	    if (event.keyCode == 13) {
	        event.preventDefault();

			toChkVal = "";
			var chkYN = $('input[name^=checkChkVal]:checked');
			if(chkYN.length == 3){
				$('#all').prop('checked', this.checked);
			}
			$('input[name^=checkChkVal]:checked').each(function() {
				toChkVal += ","+$(this).val();
			});

			toChkVal = toChkVal.replace(",,",',');
			if(chkYN.length == 0){
				$("#toChk").val(',1,2,3,4,');
			}else{
				$("#toChk").val(toChkVal+",");
			}
			
			var to_Chk = $("#toChk").val();
			to_Chk = to_Chk.replace(/^.(\s+)?/, ""); 
			to_Chk = to_Chk.replace(/(\s+)?.$/, "");

			$("#page").val("1");
			$("#toChk_").val(to_Chk);

			$("#sortForm").submit();
	    }
	});

});

function compareDateBetweenCurrentAndValidDate(MEMSTDT, MEMENDT){
	//현재 시간
	var now;

	$.ajax({
        url: "/getCurrentTime.html" ,
        type: "POST" ,
        async : false,

        success: function (result){
        	now = result;
        }
    });

	var nowDate = new Date(Date.parse(now));
	var curr_year = nowDate.getFullYear();
	var curr_month = nowDate.getMonth();
	var curr_date = nowDate.getDate();
	var curr_hour = nowDate.getHours();
	var curr_min = nowDate.getMinutes();

	//DB에서 가져온 해당 앱의 날자 값
	var stDate = new Date(Date.parse(MEMSTDT));
	var endDate = new Date(Date.parse(MEMENDT));
	//입력된 시간
	var stYear = stDate.getFullYear();
	var stMonth = stDate.getMonth();
	var stDate = stDate.getDate();

	
	var endYear = endDate.getFullYear();
	var endMonth = endDate.getMonth();
	var endDate = endDate.getDate();

	if((curr_year >= stYear) && (curr_month >= stMonth) && (curr_date >= stDate)){
		if((curr_year <= endYear) && (curr_month <= endMonth) && (curr_date <= endDate)){
			return true;
		}
		//message : 다운로드 기간이 유효하지 않습니다
		alert("<spring:message code='down.control.004' />");
		return false;
	}else{
		//message : 다운로드 기간이 유효하지 않습니다
		alert("<spring:message code='down.control.004' />");
		return false;
	}
};

function downloadCounting(isCoupon, downCnt, downSeq, sort){
	//isCoupon이 1  => 쿠폰이다
	//isCoupon이 2  => 일반 다운로드
	$.ajax({
		url: "/downloadCounting.html" ,
		type: "POST" ,
		data:{
			"downCnt" : downCnt,
			"downSeq" : downSeq,
			"sort" : sort,
			"isCoupon" : isCoupon
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

function goToPage(page){
	
	toChkVal = "";
	var chkYN = $('input[name^=checkChkVal]:checked');
	if(chkYN.length == 3){
		$('#all').prop('checked', this.checked);
	}
	$('input[name^=checkChkVal]:checked').each(function() {
		toChkVal += ","+$(this).val();
	});

	toChkVal = toChkVal.replace(",,",',');
	if(chkYN.length == 0){
		$("#toChk").val(',1,2,3,4,');
	}else{
		$("#toChk").val(toChkVal+",");
	}
	
	var to_Chk = $("#toChk").val();
	to_Chk = to_Chk.replace(/^.(\s+)?/, ""); 
	to_Chk = to_Chk.replace(/(\s+)?.$/, "");
	
	$("#page").val(page);
	$("#toChk_").val(to_Chk);
	$("#sortForm").submit();
}

function goToModify(contentsSeq){	
	var searchType = $('#comboBox :selected').text();
	var searchValue = $('#searchValue').val();
	
	window.location.href="/contents/modify.html?page=<c:out value='${param.page}'/>&contentsSeq="+contentsSeq+"&searchType=<c:out value='${param.searchType}'/>&searchValue=<c:out value='${param.searchValue}'/>";
}

//20180329 : lsy - popup_version
function popup_version(contents_name){
	var winWidth = 850;
	var winHeight = 600;
	var winPosLeft = (screen.width - winWidth)/2;
	var winPosTop = (screen.height - winHeight)/2;
	var url = "/contents/history.html?&contents_name="+encodeURI(encodeURIComponent(contents_name));		
	var opt = "width=" + winWidth + ", height=" + winHeight + ", top=" + winPosTop + ", left=" + winPosLeft + ", scrollbars=No, resizeable=No, status=No, toolbar=No";
	
	window.open(url, "versionPopup", opt);
}
//20180329 : lsy - popup_version - end


/* function goToModify(userSeq){
	var searchType = $('#comboBox :selected').text();
	var searchValue = $('#searchValue').val();
	
	
	window.location.href="/man/user/modify.html?page=<c:out value='${param.page}'/>&userSeq="+userSeq+"&searchType=<c:out value='${param.searchType}'/>&searchValue=<c:out value='${param.searchValue}'/>";
} */

</script>
</head>

<body>
<!-- wrap -->
<div id="wrap" class="sub_wrap">
	
	<!-- header -->
	<%@ include file="../inc/header.jsp" %>
	<!-- //header -->

	<!-- conteiner -->
	<div id="container">
		<div class="contents list_area">
			<h2><spring:message code='contents.list.001' /></h2>
			<form id ="downloadForm" name="downloadForm" action="/down/down.html" method="POST">
				<input type="hidden" id="ostype" name = "ostype" value=""/>
				<input type="hidden" id="downSeq" name="downSeq" value=""/>
				<input type="hidden" id="orgFileName" name = "orgFileName" value=""/>
				<input type="hidden" id="saveFileName" name = "saveFileName" value=""/>
			</form>
			<!-- section -->
			
			<!--			
			app.inapp.list.text15=총 
			app.inapp.list.text16=개
			  -->
			<div class="section fisrt_section">
				<div class="con_header">
					<div class="result fLeft">
						<spring:message code='app.inapp.list.text15' /> ${contentList.totalCount }<spring:message code='app.inapp.list.text16' />
					</div>
					<div class="form_area fRight">
						<form id="sortForm" action="/contents/list.html" method="POST">
							<fieldset>
								<div class="checkbox_area">
									<input type="hidden" name="toChk" id="toChk" value="${ToChk}" />
									<input type="hidden" name="toChk_" id="toChk_" value="${ToChk_}" />
									<input name="checkChkVal_1" id="all" type="checkbox" value="1"><label for="all">All</label>
									<input name="checkChkVal_2" id="html5" type="checkbox" value="2"><label for="html5">HTML5</label>
									<input name="checkChkVal_3" id="epub3" type="checkbox" value="3"><label for="epub3">ePub3</label>
									<input name="checkChkVal_4" id="pdf" type="checkbox" value="4"><label for="pdf">PDF</label>
								</div>
								<select name="searchType">
									<option value="contentsName"><spring:message code='contents.list.011' /></option>
								</select>
								<input id="searchValue" name="searchValue" type="text" value="${searchValue}">
								<a href="#" id="searchBtn" class="btn btnM"><spring:message code='contents.list.010' /></a>
								<input type="hidden" name="isPost" id="isPost" value="true" />
								<input type="hidden" name="page" id="page" value="${param.page }" />
							</fieldset>
						</form>
					</div>
				</div>
				<!-- table_area -->
				<div class="table_area">
					<table class="coltable">
						<colgroup>
							<col style="width:130px">
							<col style="width:75px">
							<col style="width:">
							<col style="width:80px">
							<col style="width:100px">
							<col style="width:120px">
							<col style="width:110px">
							<col style="width:90px">
							<col style="width:80px">
						</colgroup>
						<caption></caption>
						<tr>
							<th scope="col"><spring:message code='contents.list.011' /></th>
							<th scope="col"><spring:message code='contents.list.012' /></th>
							<th scope="col"><spring:message code='contents.list.013' /></th>
							<th scope="col"><spring:message code='contents.list.014' /></th>
							<th scope="col"><spring:message code='contents.list.015' /></th>
							<th scope="col"><spring:message code='contents.list.016' /></th>
							<th scope="col"><spring:message code='contents.list.017' /></th>
							<th scope="col"><spring:message code='contents.list.018' /></th>
							<th scope="col"><spring:message code='contents.list.019' /></th>
						</tr>
						<c:choose>
							<c:when test="${empty contentList.list}">
							<tr>								<!--message : 등록된 콘텐츠가 없습니다.  -->
								<td align="center" colspan="9" ><spring:message code='contents.list.030' /><%-- <spring:message code='app.no.contents' /> --%></td>
							</tr>
							</c:when>
							<c:otherwise>
									<c:forEach var="i" begin="0" end="${contentList.list.size()-1}">
									<tr>
										<td>
											<c:choose>
												<c:when test="${fn:indexOf(menuFunction, '113') != -1 }">
													<a href=javascript:goToModify(${contentList.list[i].contentsSeq});>${contentList.list[i].contentsName}</a>
												</c:when>
												<c:otherwise>
													${contentList.list[i].contentsName}
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${contentList.list[i].contentsType == '1' }">HTML5</c:when>
												<c:when test="${contentList.list[i].contentsType == '2' }">ePub3</c:when>
												<c:when test="${contentList.list[i].contentsType == '3' }">PDF</c:when>
											</c:choose>
										</td>
										<td>${contentList.list[i].appName }</td>
										<td><a href="javascript:popup_version('${contentList.list[i].contentsName}');">${contentList.list[i].verNum}</a></td>
										<td>
											<c:choose>
												<c:when test="${contentList.list[i].distrGb == '1' }"><spring:message code='contents.list.023' /></c:when>
												<c:when test="${contentList.list[i].distrGb == '2' }"><spring:message code='contents.list.024' /></c:when>
											</c:choose>
										</td>
										<td id="downloadInfo">
											
											${ formattedDownInfo[i]}
											<%-- <c:choose>
												<c:when test="${contentList.list[i].distrGb == '2' && contentList.list[i].couponGb == '1'}">
													<c:choose>
														<c:when test="${contentList.list[i].nonmemDownGb =='1' }">쿠폰 ${contentList.list[i].nonmemDownAmt }</c:when>
														<c:when test="${contentList.list[i].nonmemDownGb =='2' }">쿠폰 ${contentList.list[i].nonmemStarDt } ~  ${contentList.list[i].nonmemEndDt }</c:when>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${contentList.list[i].memDownGb == '1' }">일반 ${contentList.list[i].memDownAmt }</c:when>
														<c:when test="${contentList.list[i].memDownGb == '2' }">일반 ${contentList.list[i].memDownStartDt} ~ ${contentList.list[i].memDownEndDt }</c:when>
													</c:choose>
												</c:otherwise>
											</c:choose> --%>
										</td>
										<td>${formattedDate[i]}</td>
										<td class="state">
 											<c:choose>
												<c:when test="${contentList.list[i].limitGb == '1' }">
													<img src="/images/icon_circle_red.png" alt=""> <spring:message code='contents.list.029' /></c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${contentList.list[i].useGb == '2' }">
														<img src="/images/icon_circle_red.png" alt=""> <spring:message code='contents.list.028' /></c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${contentList.list[i].completGb == '1' }">
																<img src="/images/icon_circle_green.png" alt=""> <spring:message code='contents.list.027' /></c:when>
																<c:when test="${contentList.list[i].completGb == '2' }">
																<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='contents.list.022' /></c:when>
																<c:when test="${contentList.list[i].completGb == '3' }">
																<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='contents.list.033' /></c:when>
																<c:when test="${contentList.list[i].completGb == '4' }">
																<img src="/images/icon_circle_yellow.png" alt=""> <spring:message code='contents.list.034' /></c:when>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
												<!--
														사용여부 : useGb
														완성여부 : completGb
														제한여부 : limitGb
													  -->
										</td>
										
										<td><a href="#1" class="btn btnXS downloadGo" alt="${contentList.list[i].memDownGb}|&|${contentList.list[i].memDownCnt}|&|${contentList.list[i].memDownAmt}|&|${contentList.list[i].memDownStartDt}|&|${contentList.list[i].memDownEndDt}|&|${contentList.list[i].uploadSaveFile}|&|${contentList.list[i].contentsSeq}|&|${contentList.list[i].contentsType}|&|${contentList.list[i].uploadOrgFile}">download</a></td>
									</tr>
 								</c:forEach>
							</c:otherwise>
						</c:choose> 
					</table>
				</div><!-- //table_area -->
				
				<!--페이징-->
				<div class="paging">
					<c:if test="${contentList.startPage != 1 }">
					<a href="#" onclick="goToPage(${contentList.startPage-10})" class="paging_btn"><img src="/images/icon_arrow_prev_page.png" alt="<spring:message code='contents.list.031' />"></a>
					</c:if>
					<c:forEach var="i" begin="${contentList.startPage }" end="${contentList.endPage}">
						    <c:if test="${param.page==i }"><span class="current"><c:out value="${i}"/></span></c:if>
						    <c:if test="${param.page!=i }"> <a href="#" onclick="goToPage(${i})"><c:out value="${i}"/></a></c:if>
	<%-- 					    <c:out value="${i}"/>
						    <c:if test="${param.page==i }"></strong></c:if> --%>
					</c:forEach>
					<c:if test="${contentList.endPage*contentList.maxResult < contentList.totalCount}">
					<a href="#" onclick="goToPage(${contentList.endPage+1})" class="paging_btn"><img src="/images/icon_arrow_next_page.png" alt="<spring:message code='contents.list.032' />"></a>
					</c:if>
				</div>
				<!--//페이징-->

				<!-- btn area -->
				
				<sec:authorize access="!hasRole('ROLE_ADMIN_SERVICE')">
					<c:choose>
						<c:when test="${fn:indexOf(menuFunction, '112') != -1 }">
							<div class="btn_area_bottom fRight clfix" style="margin-top:-42px;">
								<a class="btn btnL btn_red" href="/contents/write.html"><spring:message code='contents.list.026' /></a>
							</div>
						</c:when>
					</c:choose>
				</sec:authorize>
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
