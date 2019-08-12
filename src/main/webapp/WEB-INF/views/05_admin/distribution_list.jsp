<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>
<%@ page session="true" %>
<script type="text/javascript">
<!--
//-->
//document.getElementById("provForm").reset();
var fileType = 'mobileprovision';
$(document).ready(function(){
	
	$('#distrProfileName').remove();
	$('form').each(function(){
		this.reset();
	});
	$('#provForm').val('');
		
	var isEditing = false;
	function resetForm(){
		var isInputed = false;
		$('.distribution_area').find('form').each(function(){
			
			if($(this).find('#provSeq').size()){//수정일 경우
				
			}
			$(this).find('input[type=text], input[type=password]').each(function(){
				//console.log($(this).val());
				if($(this).val()){
					isInputed = true;
				}
			});				

				//$(this).find('input:radio, input:')
			
			if(isInputed){
				//message : 입력하던 부분이 사라집니다. 계속 진행하시겠습니까?
				//if(confirm("<spring:message code='man.distribution.list.text30' />")){
					$('#distrProfileName').remove();
					this.reset();				
					$('.distribution_area').find('fieldset').show();
					$('.proc').hide();			
					return false;					
				//}
			}else{
				$('.distribution_area').find('fieldset').show();
				$('.proc').hide();	
				return false;	
			}
		});
	}
	//등록불러오기
	$('.btn.btnL.btn_red.regist').click(function(){
		resetForm();
		$('.btn.btnL.btn_gray_dark.regist.proc').show();
		showPasswordByProvFile();
	});
	//등록proc
	$('.btn.btnL.btn_gray_dark.regist.proc').click(function(){
		var action_url = '/man/provision/regist.html';
		var action_url = '/man/provision/regist.html';
		if(validateProv){
			$('#provForm').attr('action', action_url);
			//alert($('#provForm').attr('method'));
			$('#provSeq').remove();
			if(!validateProvId()){
				alert("<spring:message code='extend.local.041' />");
				$('#provId').focus();
				return false;
			}
			$('#provForm').submit();			
		};
	});
	//수정불러오기
	$('.update_fm').click(function(){
		resetForm();
		var provSeq = $(this).parents('tr').attr('id');
		var action_url = '/man/provision/modify.html?provSeq='+provSeq;
		$.getJSON(action_url, function(data){
			var provSeq = data.provSeq;
			var provName = data.provName;
			var distrProfileName = data.distrProfileName;
			var provId = data.provId;
			var distrProfile = data.distrProfile;
			var provTestGb = data.provTestGb;
			$('#provSeq').val(provSeq);
			$('#provName').val(provName);
			//$('#provFile').val(distrProfileName);
			$('#provFile').after('<div id="distrProfileName">'+distrProfileName+'.'+distrProfile+'</div>');
			$('#provId').val(provId);
			$('#distrProfile').val(distrProfile);
			$('input:radio[name=provTestGb]:radio[value='+provTestGb+']').click();
			showPasswordByProvFile();
			$('.btn.btnL.btn_gray_dark.update.proc').show();

			fileType = distrProfile;
			$("#update_proc").focus();
		}).fail(function(){
			alert("<spring:message code='man.distribution.list.text22' />")
		});
	});
	//수정proc
	$('.btn.btnL.btn_gray_dark.update.proc').click(function(){
		var action_url = '/man/provision/modify.html';
		if(validateProv){
			$('#provForm').attr('action', action_url);
			$('#provForm').submit();			
		};
	});
	//삭제proc
	$('.table_area.table_type1').find('.btn.btnXS').click(function(){
		//message : 해당 데이터 및 파일이 삭제 됩니다. 계속 진행하시겠습니까?
		if(confirm("<spring:message code='man.distribution.list.text31' />")){
			var action_url = '/man/provision/delete.html';
			var provSeq = $(this).parents('tr').attr('id');
			$('#provSeq').val(provSeq);
			$('#provForm').attr('action', action_url);
			$('#provForm').submit();
		}
	});
	//validate
	function validateProv(){
		var msg = '';
		var isValidate = false
		$('.distribution_area').find('form').find('input').each(function(){
			if(!$(this).val()&&$(this).attr('type')!='hidden'){
				if($(this).attr('type')=='file' && !$('#distrProfileName').size()&& !$('#provSeq').size()){
					msg = $(this).attr('title')+"<spring:message code='man.distribution.list.text27' />";
				}else if($(this).attr('type')=='text'||($(this).attr('type')=='password'&&fileType=='keystore')){
					msg = $(this).parent().find('label').text()+"<spring:message code='man.distribution.list.text27' />";
				}
				alert(msg);
				$(this).focus();
				isValidate = false;
				return false;
			}else{
				isValidate = true;
			}
		});
		return isValidate;
	}
	//등록수정에러페이지에서 다시 돌아올때
	<c:if test="${not empty provSeq}">
	var provSeq = '${provSeq}';
	if(provSeq=="reg"){
		//등록
		$('.btn.btnL.btn_red.regist').click();
	}else{
		//수정
		$('#'+provSeq).find('.update_fm').click();		
	}
	</c:if>
	
	//파일우선등록표시
	$('input[type=text], input[type=password], input[type=radio]').focus(function(){
		if(!($('#provFile').val()||$('#distrProfileName').size())){
			alert("<spring:message code='man.distribution.list.text23' />");
			$('#provFile').focus();			
		}
	});
	//파일 확장자체크
	$('#provFile').change(function(e){
		var filename = $(this).val();
		var dot = filename.lastIndexOf(".");
		var ext = filename.substring(dot+1).toLowerCase();
		//alert('['+ext+']');
		if(ext!='mobileprovision'){
			alert("<spring:message code='man.distribution.list.text24' />");
			$(this).val('');
		}else{
			fileType = ext;
			showPasswordByProvFile();
		}
	});
	function showPasswordByProvFile(){
		/* if(fileType.toLowerCase()=='keystore'){
			$('input[type=password]').parents('.input_area').css('visibility', 'visible');
		}else{
			$('input[type=password]').parents('.input_area').css('visibility', 'hidden');			
		}	 */	
	}
	function validateProvId(){
		var provId = $('#provId').val();
		var pattern = /^([0-9a-zA-Z]+)(\.[0-9a-zA-Z*]+){1,99}$/; 
		var huk = pattern.test(provId); 
		//alert(huk);
		if(huk&&provId.indexOf('*')>-1){
			if(provId.indexOf('*')!=provId.lastIndexOf('*')||provId.indexOf('*')!=provId.length-1){
				//alert("provId.indexOf('*')==="+provId.indexOf('*'));
				//alert("provId.length==="+provId.length);
				huk = false;
			}else{
				if(provId.charAt(provId.indexOf('*')-1)!='.'){
					huk = false;
				}
			}
			
		}
		//alert(huk);
		return huk;
	}
});
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
			<!-- man header -->
			<%@ include file="../inc/man_header.jsp" %>
			<!-- //man header -->

			<h2><spring:message code='man.distribution.list.text4' /></h2>
		
			<!-- section -->
			<div class="section fisrt_section">

				<!-- btn area -->
				<div class="btn_area_bottom tRight clfix mb20">
					<a class="btn btnL btn_red regist" href="#regist_proc"><spring:message code='man.distribution.list.text11' /></a>
				</div>
				<!-- //btn area -->

				<!-- table_area -->
				<div class="table_area table_type1">
					<table class="coltable">
						<colgroup>
							<col style="width:">
							<col style="width:">
							<col style="width:">
							<col style="width:">
							<col style="width:105px">
							<col style="width:80px">
						</colgroup>
						<caption></caption>
						<tr>
							<th scope="col"><spring:message code='man.distribution.list.text5' /></th>
							<th scope="col"><spring:message code='man.distribution.list.text6' /></th>
							<th scope="col"><spring:message code='man.distribution.list.text7' /></th>
							<th scope="col"><spring:message code='man.distribution.list.text8' /></th>
							<th scope="col"><spring:message code='man.distribution.list.text9' /></th>
							<th scope="col"><spring:message code='man.distribution.list.text29' /></th>
							<th scope="col"><spring:message code='man.distribution.list.text10' /></th>
						<c:choose>
							<c:when test="${empty provisionList}">
							<tr>
								<td align="center" colspan="7" ><spring:message code='app.no.contents' /></td>
							</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="i" begin="0" end="${provisionList.size()-1}">
									<tr id="${provisionList[i].provSeq}">
										<td>${provisionList[i].provName }</td>
										<td><a class="update_fm" href="#update_proc" title="<spring:message code='man.distribution.list.text25' />">${provisionList[i].distrProfileName}</a></td>
										<td>${provisionList[i].provId }</td>
										<td>${provisionList[i].distrProfile }</td>
										<td><fmt:formatDate value="${provisionList[i].chgDt }" pattern="yyyy-MM-dd"/></td>
										<td>
										<c:choose>
											<c:when test="${provisionList[i].provTestGb=='1'}">Adhoc</c:when>
											<c:when test="${provisionList[i].provTestGb=='2'}">AppStore</c:when>											
										</c:choose>
										</td>
										<td><a class="btn btnXS" href="#"><spring:message code='man.distribution.list.text14' /></a></td>	
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>						
					</table>
				</div><!-- //table_area -->

				<div class="distribution_area" >
				<form name="provForm" id="provForm" method="post" action="" enctype="multipart/form-data" >
				<input type="hidden" name="provSeq" id="provSeq" value="" />
						<fieldset style="display:none;">
							<div class="clfix">
								<input id="provFile" name="provFile" type="file" title="provision file">

								<div class="input_area" style="width:100%; float:left">
									<label for=""><spring:message code='man.distribution.list.text17' /></label>
									<input id="provName" name="provName" type="text" style="width:80%;" maxlength="40">
								</div>
								<div class="input_area" style="width:195px; float:left">
									<label for="" style="font-size : 11px; "><spring:message code='man.distribution.list.text29' /></label>
									<input name="provTestGb" id="provTestGbTest" type="radio"  value="1" style="width:10%;  margin-left: 3%;" checked="checked" />
									<label for="provTestGbTest">Adhoc</label>
									<input name="provTestGb" id="provTestGbApp" type="radio"  value="2" style="width:10%;  margin-left: 3%;" />
									<label for="provTestGbApp">AppStore</label>
								</div>
								<div class="input_area" style="width:193px; float:right;">
									<label for=""><spring:message code='man.distribution.list.text21' /></label>
									<input id="provId" name="provId" type="text" style="width:65%;">
								</div>
							</div>
							<div class="btn_area_bottom clfix clear">
								<a id="update_proc" class="btn btnL btn_gray_dark update proc" href="#update_proc" style="display:none;"><spring:message code='man.distribution.list.text19' /></a><!-- 수정일 경우 -->
								<a id="regist_proc" class="btn btnL btn_gray_dark regist proc" href="#regist_proc" style="display:none;"><spring:message code='app.write.text41' /></a><!-- 신규등록일 경우 -->
							</div>
						</fieldset>
				</form>
				</div>
				
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
