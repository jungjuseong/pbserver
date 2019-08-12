<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../inc/top.jsp" %>

<script>
$(document).ready(function(){
	
	var useUserVal = '${noticeVO.useUserGb}';
	var appVal = '${noticeVO.appGb}';

	
	if(useUserVal == 1){
		$("#fixDiv1").css("margin","10px 0px");
		$("#use_user_pop").hide();
		
	}else if(useUserVal == 2){
		$("#fixDiv1").css("margin","10px 0px");
		$("#use_user_pop").show();
	}
	
	$("[name=useUserGb]").change( function(){
		if($(this).val() == '1'){
			$("#fixDiv1").css("margin","10px 0px");
			$("#use_user_pop").hide();
		}else if($(this).val() == '2'){
			$("#fixDiv1").css("margin","10px 0px");
			$("#use_user_pop").show();
		}
	});

	if(appVal == 1){
		$("#fixDiv2").css("margin","10px 0px");
		$("#use_app_pop").hide();
	}else if(appVal == 2){
		$("#fixDiv2").css("margin","10px 0px");
		$("#use_app_pop").show();
	}

	$("[name=appGb]").change( function(){
		if($(this).val() == '1'){
			$("#fixDiv2").css("margin","10px 0px");
			$("#use_app_pop").hide();
		}else if($(this).val() == '2'){
			$("#fixDiv2").css("margin","10px 0px");
			$("#use_app_pop").show();
		}
	});

	$("#d_EDATE").mousedown(function(e){
		if($("#d_SDATE").val() == null || $("#d_SDATE").val() == ""){
			//message : 시작 날짜를 입력해주십시오.
			alert("<spring:message code='contents.modify.039' />");
			e.preventDefault();
		}
	});

	$("#d_SDATE").change( function() {

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

	});

	$("#modifyBtn").click(function(){
		$("#notice_modify_f").submit();
	});

	$("#use_user_pop").click( function(){
		var target_ = "userPop";
		var useS = $("#useS").val();
	  	window.open("/assignment/user.html?useS="+useS,target_,"width=985, height=450, top=100, left=100, resizable=no, menubar=no, scrollbars=no");
	});

	$("#use_app_pop").click( function(){
		var target_ = "userPop";
		var storeBundleId = $("#storeBundleId").val();
		var inappSeq = $("#inappSeq").val();
		window.open("/assignment/app.html?storeBundleId="+storeBundleId+"&inappSeq="+inappSeq ,target_,"width=670, height=340, top=100, left=100, resizable=no, menubar=no, scrollbars=no");
	});
});



function cancelMethod(){
	window.location.href="/man/notice/list.html?page=${param.page}&searchType=${param.searchType}&searchValue=${param.searchValue}";
}

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
		<div class="contents join_area">
			<!-- man header -->
			<c:set var="curi" value="${requestScope['javax.servlet.forward.request_uri']}" />
			<!-- man header -->

			<div class="tab_area">
				<ul>
					<!--messsage : 사용자  -->
					<li <c:if test="${fn:containsIgnoreCase(curi, '/notice/')}">class="current last"</c:if>><a href="/man/notice/list.html?page=1"><spring:message code='extend.local.072' /></a></li>
				</ul>
			</div>

			<!-- //man header -->
				<h2><spring:message code='extend.local.035' /></h2>
				<form action="/man/notice/modify.html" method="POST" id="notice_modify_f">
					<input type="hidden" name="noticeSeq" 	id="noticeSeq"  value="${param.noticeSeq }" />
					<input type="hidden" name="useS"		id="useS"  		value="${ useS }" />
					<input type="hidden" name="storeBundleId" 		id="storeBundleId"		value="${ storeBundleId }" 	/> <!-- 선택된 앱    -->
					<input type="hidden" name="inappSeq"	id="inappSeq"	value="${ inappSeq }" 	/> <!-- 선택된 인앱  -->
				<div class="section fisrt_section">
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:120px">
							<col style="">
						</colgroup>
						<tr>
							<th scope="row"><label class="title" for="deviceInfo"><em>*</em> <spring:message code='extend.local.024' /></label></th>
							<td colspan="3">
								<input id="noticeName" name="noticeName" type="text"  value="${noticeVO.noticeName }" style="width:95.7%;">
							</td>
						</tr>

						<tr>
							<th scope="row"><label class="title" for="deviceType"><em>*</em> <spring:message code='extend.local.025' /></label></th>
							<td colspan="3">
								<textarea id="noticeText" name="noticeText" cols="" rows="4" style="width:95%;">${noticeVO.noticeText }</textarea>
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='extend.local.026' /></span></th>
							<td>
								<div id="fixDiv1"   class="radio_area" >
									<input name="useUserGb" id="u_y"  type="radio" value="1" <c:if test="${'1' eq noticeVO.useUserGb }">checked="checked"</c:if> /> <label for="u_y"> All</label>
									<input name="useUserGb" id="u_n"  type="radio" value="2" <c:if test="${'2' eq noticeVO.useUserGb }">checked="checked"</c:if> /> <label for="u_n"> <spring:message code='extend.local.027' /></label>
									<a href="#"  style="position:absolute; top:310px;right: 127px;" id="use_user_pop" class="btn btnL btn_gray_light"> <spring:message code='extend.local.028' /></a>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='extend.local.029' /></span></th>
							<td>
								<div id="fixDiv2"   class="radio_area">
									<input name="appGb" id="a_y"  type="radio" value="1" <c:if test="${'1' eq noticeVO.appGb }">checked="checked"</c:if> /> <label for="a_y"> All</label>
									<input name="appGb" id="a_n"  type="radio" value="2" <c:if test="${'2' eq noticeVO.appGb }">checked="checked"</c:if> /> <label for="a_n"> <spring:message code='extend.local.030' /></label>
									<a href="#" style="position:absolute; top:355px;right: 140px;" id="use_app_pop" class="btn btnL btn_gray_light"> <spring:message code='extend.local.029' /></a>
								</div>
							</td>
						</tr>
						<tr id = "useDate">
							<th scope="row"><span class="title"><em>*</em> <spring:message code='extend.local.031' /></span></th>
							<td colspan="3">
								<input id="d_SDATE" style="padding-left:5px;" name="noticeStartDt" type="text" title="start date" class="date fmDate1" value="<fmt:formatDate value="${noticeVO.noticeStartDt}" pattern="yyyy-MM-dd"/>" />
										&nbsp;&nbsp;~&nbsp;&nbsp;
								<input id="d_EDATE" name="noticeEndDt" type="text" title="end date" class="date toDate1" value="<fmt:formatDate value="${noticeVO.noticeEndDt}" pattern="yyyy-MM-dd"/>" />
							</td>
						</tr>
						<tr>
							<th><span class="title"><em>*</em> <spring:message code='extend.local.032' /></span></th>
							<td>
								<div class="radio_area">
									<input name="publicGb" id="u_y"  type="radio" value="1" <c:if test="${'1' eq noticeVO.publicGb }">checked="checked"</c:if> /> <label for="u_y"> <spring:message code='extend.local.033' /></label>
									<input name="publicGb" id="u_n"  type="radio" value="2" <c:if test="${'2' eq noticeVO.publicGb }">checked="checked"</c:if> /> <label for="u_n"> <spring:message code='extend.local.034' /></label>
								</div>
							</td>
						</tr>
					</table>
				</div>

				<div class="btn_area_bottom tCenter">
					<a href="#modifyBtn" id="modifyBtn" class="btn btnL btn_red"><spring:message code='user.modify.016' /></a>
					<a href="javascript:cancelMethod();" class="btn btnL btn_gray_light"><spring:message code='user.modify.017' /></a>
				</div>
			</div>
			</form>
			<!-- //사용자 수정 -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->


</body>
</html>