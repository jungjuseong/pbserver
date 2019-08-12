<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>

<script type="text/javascript" src="/js/jquery.validate.js"></script>
<script>

$(document).ready(function(){
	$("#appSearch").click(function(){
		 window.open("/contents/write/popUpContents.html","","location=0,menubar=0,status=0,scrollbars=0,width=680, height=333, top=100, left=100, resizable= yes");
	});

	//파일 확장자체크
	$('#uploadContentsFile').change(function(e){
		var filename = $(this).val();
		var dot = filename.lastIndexOf(".");
		var ext = filename.substring(dot+1).toLowerCase();
		
		switch ($("#contentsType").val()) {
			case '1' : 
				if(!(ext=='html' || ext == 'zip')){
		  			$(this).val('');
		  			//message : HTML 파일 형식이 필요합니다.
					alert("<spring:message code='contents.write.032' />");
				}
				break;
			case '2' :
				if(!(ext=='epub')){
					$(this).val('');
					//message : ePub 파일 형식이 필요합니다.
					alert("<spring:message code='contents.write.033' />");
				}
				break;
			case '3' :
				if(!(ext=='pdf')){
					$(this).val('');
					//message : pdf 파일 형식이 필요합니다.
					alert("<spring:message code='contents.write.034' />");
				}
				break;
		}
	});

	$("#contents_write_f").validate({
	    rules: {
	    	contentsName: "required",
	    	verNum: "required",
	    	uploadContentsFile : "required"
	    },

	    messages: {
	    	//message : 콘텐츠 이름을 입력해 주십시오.
	    	contentsName: "<spring:message code='contents.write.037' />",
	    	//message : 버전을 입력해 주십시오.
	    	verNum: "<spring:message code='contents.write.038' />",
	    	//message : 파일을 등록해 주십시오.
	    	uploadContentsFile : "<spring:message code='contents.write.039' />"
			
		},

		 errorPlacement: function(error, element) {
			error.appendTo( element.parent("td") );
		}
	});

	$("#registBtn").click(function(){
		//20180327 - lsy : develop version managemenet -> version validate logic add
		$.ajax({
			url:"verValidation.html",
			type:"POST",
			data:{
				"contentsName":$("#contentsName").val(),
				"ver_num":$("#verNum").val()
			},
			success:function(result){
				if(result >= 1){
					alert("<spring:message code='contents.write.041' />");
				}else if(result == 0){
					$("#contents_write_f").submit();
				}else{
					alert("<spring:message code='contents.write.042' />");
				}
			}
		});
		//20180327 - lsy : develop version managemenet -> version validate logic add - end
	});

	$('[name=relatedAppName], #appSearch').focus(function(){
		$('#appSearch').focus().click();
	});
});

function cancelResist(){
	if(confirm("<spring:message code='contents.write.040' />")){
		window.location.href="/contents/list.html?page=1";
	}
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
			<!-- 콘텐츠 등록 -->
			<!--message : 콘텐츠 등록  -->
			<h2><spring:message code='contents.write.001' /></h2>
			<form action="#" id="contents_write_f" method="POST" enctype="multipart/form-data">
				<input type="hidden" id="completGb"  name="completGb" value="2"/>
			
				<div class="section fisrt_section">
					<div class="table_area">
						<table class="rowtable writetable">
							<colgroup>
								<col style="width:150px">
								<col style="">
								<col style="width:70px">
								<col style="width:150px">
							</colgroup>
							<tr>
								<th scope="row"><label class="title" for="contentsName"><em>*</em> <spring:message code='contents.write.002' /></label></th><!--message : 이름  -->
								<td colspan="3">
									<input id="contentsName" name="contentsName" type="text" style="width:94.7%;">						
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="contentsType"><em>*</em> <spring:message code='contents.write.003' /></label></th><!--message : 유형 -->
								<td>
									<select id="contentsType" name="contentsType" style="width:150px;">
										<option value="1" >HTML5</option>
										<option value="2" >ePub3</option>
										<option value="3" >PDF</option>
									</select>
								</td>
								<th style="text-align:right; width:150px;"><label class="title" for="verNum"><em>*</em> <spring:message code='contents.write.007' /></label></th><!--message : 버전 -->
								<td>
									<input id="verNum" name="verNum" type="text" style="width:80%;">						
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="appName"><spring:message code='contents.write.008' /></label></th><!--message : 관련 앱 -->
								<td colspan="3">
									<input id="relatedAppName" name="relatedAppName" type="text" style="width:94.7%;" class="line_right" value="">
<%-- 	20180625(월) : lsy - 새버전추가시 번들 중복으로 인한 변경(번들 -> seq)
									<input id="relatedBundleId" name="relatedBundleId" type="hidden" value="">
--%>								<input id="relatedAppSeq" name="relatedAppSeq" type="hidden" value="">
									<input id="relatedInAppSeq" name="relatedInAppSeq" type="hidden" value="">
									<input id="relatedAppType" name="relatedAppType" type="hidden" value="">
<%-- 									<a href="#" id="appSearch" class="btn btnL btn_gray_light line_left"><spring:message code='contents.write.009' /></a> --%>
								</td>
							</tr>
							<tr>
								<th scope="row"><label class="title" for="descriptionText"><spring:message code='contents.write.010' /></label></th><!--message : 설명 -->
								<td colspan="3">
									<textarea id="descriptionText" name="descriptionText" cols="" rows="4" style="width:95%;"></textarea>
								</td>
							</tr>
							<tr style="border-bottom : thin dotted #000000;">
								<th scope="row"><label class="title" for="uploadContentsFile"><em>*</em> <spring:message code='contents.write.011' /></label></th><!--message : 업로드 -->
								<td colspan="3">
									<input id="uploadContentsFile" name="uploadContentsFile" type="file" style="width:95%;">
								</td>
							</tr>
							<tr>
								<th><span class="title"><em>*</em> <spring:message code='contents.write.014' /></span></th><!--message : 사용여부 -->
								<td colspan="3">
									<div class="radio_area">
										<input name="useGb" id="u_y" type="radio" value="1" checked="checked"> <label for="u_y"><spring:message code='contents.write.015' /></label><!--message : 예 -->
										<input name="useGb" id="u_n" type="radio" value="2" disabled="disabled"> <label for="u_n"><spring:message code='contents.write.016' /></label><!--message : 아니오 -->
									</div>
								</td>
							</tr>
							<tr>
								<th><span class="title"><em>*</em> <spring:message code='contents.write.020' /></span></th><!--message : 배포 범위 -->
								<td colspan="3">
									<div class="radio_area coupon_area" style="width:100%">
										<span style="width:100%; margin-top:9px;">
											<input type="radio" name="distrGb" id="m1" value="1" disabled="disabled"/><label for="m1"><spring:message code='contents.write.021' /></label><!--message : 회원 로그인 -->
											<input type="radio" name="distrGb" id="m2" value="2" disabled="disabled"/><label for="m2"><spring:message code='contents.write.022' /></label><br/><!--message : 비회원 -->
										</span>
										<span>
											<input type="radio" name="memDownGb" id="d1" disabled="disabled"/> <label for="d1"><spring:message code='contents.write.023' /></label><!--message : 다운로드 횟수 -->
											<input name="memDownAmt" type="text"  class="tCenter" style="width:120px;" readonly>
											&nbsp;&nbsp; <spring:message code='anonymous.option.010' />&nbsp;<input name ="memDownCnt" type="text" class="tCenter" style="width:120px;" readonly onkeypress="return digit_check(event)" maxlength="3"/>
										</span>
										<br>
										<span>
											<input type="radio" name="memDownGb" id="d2" disabled="disabled" /> <label for="d2"><spring:message code='contents.write.029' /></label><!--message : 유효기간 -->
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="d_SDATE" name="SDATE" type="text" title="start date" class="date fmDate1" value="" disabled readonly/>
											&nbsp;&nbsp;~&nbsp;&nbsp;
											<input id="d_EDATE" name="EDATE" type="text" title="end date"   class="date toDate1" value="" disabled readonly/>
										</span>
										<br>
										<span>
											<input type="radio" name="memDownGb" id="d3"  value="3" disabled="disabled" /> <label for="d3"><spring:message code='anonymous.option.002' /></label>
										</span>
									</div>
								</td>
							</tr>
							<tr><!--message : 비회원용 쿠폰 발행 -->
								<th><span class="title spacing"><em>*</em> <spring:message code='contents.write.025' /></span> <br>&nbsp;&nbsp;&nbsp;<!-- <a href="#"><img src="../images/btn_q.jpg" alt="" title="쿠폰 사용 도움말"></a> --></th>
								<td colspan="3">
									<div class="radio_area coupon_area" style="width:100%">
										<span style="width:100%;">
											<input name="couponGb" id="cou_y" type="radio" value="1" disabled="disabled"> <label for="cou_y"><spring:message code='contents.write.026' /></label><!--message : 예 -->
											<input name="couponGb" id="cou_n" type="radio" value="2" disabled="disabled"> <label for="cou_n" style="margin-right:78px"><spring:message code='contents.write.027' /></label><!--message : 아니오 -->
											<input id="couponNum" name="couponNum" type="text" style="width:43.2%;" readonly>
										</span>
										<br/>
										<span style="width:100%;">
											<input type="radio" name="nonmemDownGb" id="c1"  disabled="disabled"/> <label for="c1"><spring:message code='contents.write.028' /></label><!--message : 다운로드 횟수 -->
											<input name="nonmemDownAmt" type="text"  class="tCenter" style="width:120x;" readonly>
											&nbsp;&nbsp; <spring:message code='anonymous.option.010' />&nbsp;<input name="nonmemDownCnt" type="text"  class="tCenter" style="width:120px; " disabled readonly value="" onkeypress="return digit_check(event)" maxlength="3"/>
										</span>
										<br>
										<span style="width:100%;">	
											<input type="radio" name="nonmemDownGb" id="c2" disabled="disabled" /> <label for="c2"><spring:message code='contents.write.029' /></label><!--message : 유효기간 -->
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="c_SDATE" name="nonmemDownStartDt" type="text" title="start date" class="date fmDate1" value="" disabled readonly />
											&nbsp;&nbsp;~&nbsp;&nbsp;
											<input id="c_EDATE" name="nonmemDownStartDt" type="text" title="end date"   class="date toDate1" value="" disabled readonly/>
										</span>
										<br>
										<span style="width:100%;">
											<input type="radio" name="nonmemDownGb" id="c3"  value="3" disabled="disabled" /> <label for="c3"><spring:message code='anonymous.option.002' /></label>
										</span>
									</div>
								</td>
							</tr>
						</table>
					</div>
	
					<div class="btn_area_bottom tCenter">
						<a href="#" id="registBtn" class="btn btnL btn_red"><spring:message code='contents.write.030' /></a><!--message : 등록 -->
						<a href="javascript:cancelResist()" class="btn btnL btn_gray_light"><spring:message code='contents.write.031' /></a><!--message : 취소 -->
						<input type="hidden" name="companySeq" value="<sec:authentication property="principal.memberVO.companySeq" />"/>
						<input type="hidden" name="limitGb" value="2"/>
						<input type="hidden" name="regUserSeq" value="<sec:authentication property="principal.memberVO.userSeq" />"/>
						<input type="hidden" name="regUserId" value="<sec:authentication property="principal.memberVO.userId" />"/>
						<input type="hidden" name="regUserGb" value="<sec:authentication property="principal.memberVO.userGb" />"/>
						<input type="hidden" name="chgUsersSeq" value="<sec:authentication property="principal.memberVO.userSeq" />"/>
						<input type="hidden" name="chgUsersId" value="<sec:authentication property="principal.memberVO.userId" />"/>
						<input type="hidden" name="chgUsersGb" value="<sec:authentication property="principal.memberVO.userGb" />"/>
						
					</div>
				</div>
			</form>
			<!-- //콘텐츠 등록 -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>