<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>


</head>

<script type="text/javascript" >
$(document).ready(function(){	

	$('.btnOk').click(function(){

		var result;
		if (!$('#fm_coupon_num').val()) {
			alert("<spring:message code='down.pop.006' />");$('#fm_coupon_num').focus();return
		}
		//location.href = "";

		if(confirm("<spring:message code='down.pop.007' />")){
			var url = "/down/couponOk.html";
			var dataToBeSent = $("#frmCoupon").serialize();			

			$.post(url, dataToBeSent, function(data, textStatus) {
				result = data.DownList[0];
				//alert(data.TotalCnt);
				if(1 == data.TotalCnt){
					data.DownList[0].KEYVAL
					//alert(data.DownList[0].GUBUN);
					if(data.DownList[0].KEYVAL != ''){
						if(data.DownList[0].USE_YN != '1' ){
							if(data.DownList[0].GUBUN == "APP"){
								alert("<spring:message code='down.pop.008' />");
								return;
							}
							if(data.DownList[0].GUBUN == "CONTENTS"){
								alert("<spring:message code='down.pop.009' />");
								return;
							}
							if(data.DownList[0].GUBUN == "INAPP"){
								alert("<spring:message code='down.pop.010' />");
								return;
							}
							
							
							
						}else if(data.DownList[0].INSTALL_YN != "1"){
							if(data.DownList[0].GUBUN == "APP"){
								alert("<spring:message code='down.pop.008' />");
								return;
							}
							if(data.DownList[0].GUBUN == "CONTENTS"){
								alert("<spring:message code='down.pop.009' />");
								return;
							}
							if(data.DownList[0].GUBUN == "INAPP"){
								alert("<spring:message code='down.pop.010' />");
								return;
							}
						}else{
							//웹화면에서 
							//쿠폰으로 앱 다운 불가 처리 

							//모바일 화면에서 
							//콘첸츠, HTML등 앱 설치용이 아닌것은 보이지 않게

							// 위의말 근거로는 콘텐츠 인 경우는 쿠폰 다운로드는
							// 웹이든모바일 모두 다운로드 되어야함 됨..

							// 나는 웹이다ㅣ!!!!!!!
							if(!(/Android|iPhone|iPad/i.test(navigator.userAgent))){
								if(data.DownList[0].GUBUN == "CONTENTS"){
									cuponDownProcedure(result, "CONTENTS");
									return;
								}

								if(data.DownList[0].GUBUN == "APP"){
									alert("<spring:message code='down.list.029' />");
									return;
								}

							// 나는 모바일이다!!!!!!!
							}else{
								if(data.DownList[0].GUBUN == "CONTENTS"){
									alert("<spring:message code='down.pop.012' />");
									return;
								}

								if(data.DownList[0].GUBUN == "APP"){
									cuponDownProcedure(result, "APP");
									return;
								}
							}
						}
					}else{
						alert("<spring:message code='down.pop.004' />");
						return;
					}
				}else{
					alert("<spring:message code='down.pop.005' />");		
					return;
				}
			}, "json");
		 	//form.submit();
		}
	});
});	


function compareDateBetweenCurrentAndValidDate(result){
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
	var stDate = result.NONMEMDOWNSTDT;
	var endDate = result.NONMEMDOWNENDT;
	var stSplit = stDate.split("-");
	var endSplit = endDate.split("-");
	//입력된 시간
	var stYear = stSplit[0];
	var stMonth = stSplit[1];
	var stDate = stSplit[2];
	
	var endYear = endSplit[0];
	var endMonth = endSplit[1];
	var endDate = endSplit[2];
	
	curr_month += 1
	
	//월 Reformat
	if(curr_month < 10){
		curr_month = "0"+curr_month;
	}
	
	//Date Reformat
	if(curr_date < 10){
		curr_date = "0"+curr_date;
	}

	if(curr_year > stYear || (curr_year == stYear && (curr_month > stMonth || (curr_month == stMonth && curr_date >= stDate)))){
		if(curr_year < endYear || (curr_year == endYear  && (curr_month < endMonth || (curr_month == endMonth  && curr_date <= endDate)))){
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







 
function cuponDownProcedure(result, sort){
	var downloadYN = 'Y';
	switch (result.NONDOWNYN){
		case '1' :
			if(parseInt(result.NONDOWNCNT) >= parseInt(result.NONDOWNAMT)){
				downloadYN ='N';
				alert("<spring:message code='down.pop.013' />");
			}else{
				$("#downCnt",opener.document).val(result.DOWNCNT+1);
			}
			break;
		case '2' :
			var validInfo =  compareDateBetweenCurrentAndValidDate(result);
			if( validInfo == false){
				downloadYN = 'N';
			}
			break;
		case '3' :
			break;
	}

	//쿠폰 다운로드
	if(downloadYN == 'Y'){
		$("#downGubun", opener.document).val(result.GUBUN);
		$("#downName", opener.document).val(result.NAME);
		$("#downKaU", opener.document).val(result.KEYVAL);
		$("#downVer", opener.document).val(result.VERNUM);
		$("#downSeq", opener.document).val(result.SEQ);
		$("#downCnt", opener.document).val(result.NONDOWNCNT);
		$("#downType", opener.document).val(result.NONDOWNYN);
		$("#coupon_Num", opener.document).val(result.COUPON_NUM);
		$("#ostype", opener.document).val(result.OSTYPE);
		$("#fileName", opener.document).val(result.FILENAME);
		//1은 지금 쿠폰이라는 뜻
		$("#isCoupon", opener.document).val('1');
		
		
		$("#DownGubun", opener.document).val(result.GUBUN);
		$("#DownName", opener.document).val(result.NAME);
		$("#DownKaU", opener.document).val(result.KEYVAL);
		$("#DownVer", opener.document).val(result.VERNUM);


		/* SELECT
		'APP' as GUBUN, provision_gb as PROVISION_GUBUN, file_name as FILENAME, install_gb as INSTALL_YN, app_seq as SEQ, (SELECT company_seq FROM TB_MEMBER MEM WHERE MEM.user_seq = TB_APP.reg_user_seq) as COMPANY_SEQ,
		app_name as NAME, CASE ostype WHEN '4' THEN '4' WHEN '1' THEN '5' WHEN '2' THEN '6' WHEN '3' THEN '7' END OSCODE, ostype as OSCODE2,
		CASE ostype WHEN '1' THEN 'Universal' WHEN '2' THEN 'iPhone' WHEN '3' THEN 'iPad' WHEN '4' THEN 'Android' ELSE '' END as OSTYPE, ver_num as VERNUM,
		reg_gb as CATETYPE, CASE distr_gb WHEN '1' THEN '회원' WHEN '2' THEN '비회원' END as SCOPE, reg_dt as REGDATE, (SELECT bundle_name FROM TB_BUNDLE BUN WHERE BUN.app_seq = TB_APP.app_seq limit 1) as KEYVAL, reg_user_seq as REG_SEQ, icon_save_file as ICON_NAME,
		use_gb AS USE_YN, complet_gb as COMPLETE_YN, distr_gb as SCOPE_YN, mem_down_gb as DOWNYN, CAST(IFNULL(mem_down_cnt,0) as UNSIGNED) as DOWNCNT, CAST(IFNULL(mem_down_amt,0) as UNSIGNED) as DOWNAMT, DATE_FORMAT(mem_down_start_dt, '%Y-%m-%d') as MEMDOWNSTDT, 
		DATE_FORMAT(mem_down_end_dt, '%Y-%m-%d') as MEMDOWNENDT, coupon_gb as COUPON_YN, coupon_num as COUPON_NUM, nonmem_down_gb as NONDOWNYN, CAST(IFNULL(nonmem_down_cnt,0) as UNSIGNED) as NONDOWNCNT, CAST(IFNULL(nonmem_down_amt,0) as UNSIGNED) as NONDOWNAMT, 
		DATE_FORMAT(nonmem_down_star_dt, '%Y-%m-%d') as NONMEMDOWNSTDT, DATE_FORMAT(nonmem_down_end_dt, '%Y-%m-%d') as NONMEMDOWNENDT, limit_gb as LIMIT_YN
		FROM TB_APP  */
		if(sort == "CONTENTS"){
			$.ajax({
                url: "/downloadCounting.html" ,
                type: "POST" ,
                data:{
                   "sort" : sort,
                   "nonmemDownGb" : result.NONDOWNYN,
                   "nonmemDownSTDT" : result.NONMEMDOWNSTDT,
                   "nonmemDownENDT" : result.NONMEMDOWNENDT,
                   "downCnt" : result.NONDOWNCNT,
                   "downSeq"    : result.SEQ,
                   "isCoupon"   : "1"
                               },
                               success: function (result){
                            	   switch (result){
                            	   case 0 :
                            		   alert("<spring:message code='extend.local.059' />");
                            		   break;
                            	   case 1 :
                            		   break;
                  	    	   }
                  }
            });
			$(opener.document).find("#downFrm").attr("action","/down/download_proc.html").submit();
			self.close();
		}else if(sort == "APP"){
			$(opener.document).find("#downFrm").attr("action","/down/down.html").submit();
			self.close();
		}
	}
};
</script>
<body>

<!-- wrap -->
<div id="wrap" class="sub_wrap">

	
	<!-- conteiner --><em></em>
	<div id="container">
		<div class="contents join_area">
			<!-- 쿠폰 번호 입력 -->
			<h2><spring:message code='down.pop.001' /></h2>
			<form method="post" id="frmCoupon" name="frmCoupon" >
			<div class="section">
				<div class="table_area">
					<table class="rowtable writetable">
						<colgroup>
							<col style="width:100px">
							<col style="">
						</colgroup>
						<tr>
							<th style="width:115px;"><label class="title" for="cq"><em>*</em> <spring:message code='down.pop.002' /></label></th>
							<td>
								<input name="fm_coupon_num" id="fm_coupon_num" type="text" style="width:90%;">
							</td>
						</tr>
					</table>
				</div>

				<div class="btn_area_bottom tCenter">
					<a href="#" class="btn btnL btn_red btnOk"><spring:message code='down.pop.003' /></a>
				</div>
			</div>
			</form>
			<!-- //쿠폰 번호 입력 -->
		</div>
	</div>
	<!-- //conteiner -->

</div><!-- //wrap -->

</body>
</html>