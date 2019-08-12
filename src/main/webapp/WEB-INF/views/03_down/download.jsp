<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>
</head>
<script type="text/javascript">
$(document).ready(function(){		
	$('#downloadProc').click(function(){
		var action = "/down/download_proc.html";
		$('#downFm').attr('action', action);
		//alert($('[name=downSeq]').val());
		//alert($('[name=DownGubun]').val());
		$('#downFm').submit();
	});
});	
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
			<h2><spring:message code='down.list.017' /></h2>
			
			<!-- section -->
			<div class="section fisrt_section">
			<form name="downFm" id="downFm" action="" target="_blank" >
			<input type="hidden" name="downSeq" value="${downSeq }" />
			<input type="hidden" name="DownGubun" value="${DownGubun }" />
			<input type="hidden" name="DownKaU" value="${DownKaU }" />
			<input type="hidden" name="DownVer" value="${DownVer }" />
			<input type="hidden" name="coupon_Num" value="${coupon_Num }" />
			</form>
				<div class="con_header clfix">
					<div class="form_area fRight">

					</div>
				</div>
				<!-- table_area -->
				<div class="table_area">
					<table class="coltable">
						<colgroup>
							<col style="width:">
							<col style="width:">
							<col style="width:">
							<col style="width:">
							<col style="width:">
						</colgroup>
						<caption></caption>
						<tr>
							<th scope="col"><spring:message code='down.list.025' /></th>
							<th scope="col"><spring:message code='down.list.026' /></th>
							<th scope="col"><spring:message code='down.list.012' /></th>
							<th scope="col"><spring:message code='down.list.013' /></th>
							<th scope="col"><spring:message code='down.list.017' /></th>
						</tr>
						<tr>
							<td>${DownGubun}</td>
							<td>${DownKaU}</td>
							<td>${DownName}</td>
							<td>${DownVer}</td>
							<td><a id="downloadProc" href="#downloadProc_" class="btn btnXS downloadGo" alt=""><spring:message code='down.list.017' /></a></td>
						</tr>			
					</table>
				</div><!-- //table_area -->				

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
