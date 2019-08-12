<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../inc/top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
$(document).ready(function(){
	var table = $("#menu_list > tbody > tr");
	
	//20180430 : lsy - checkbox checked할 값들 check하기
	var groupChecked = "${groupUserVO.menuFunction}".split(","), connectMenu = "";
	$.each(table, function(index){
		connectMenu = $(table).eq(index).children().eq(3).children().attr("value");
		for(var i=0;i<groupChecked.length;i++){
			if(connectMenu == groupChecked[i]){
				$(table).eq(index).children().eq(3).children().prop("checked", true);
				break;
			}
		}
	});
	//20180430 : lsy - checkbox checked할 값들 check하기 - end
	
	// *상위메뉴 rowspan 처리
	var same_cnt = 0, connectMenu = "";
		
	$.each(table, function(index){
		//1. first line skip (because of column)
		if(index <= 1){}
		//2. second, condition setting
		else if(index == 2){
			firstSetting(index);
		}
		//3. third, if last tr and rowspan is need
		else if( (index+1) == table.length){
			if(same_cnt > 1){	//same count == 1, not rowspan
				same_cnt += 1;
				rowspanTable(index, same_cnt);
				firstSetting(index);
			}
		}
		//3. remainder, first connectMenu and this tr's connectMenu compare
		else{
			if(connectMenu == $(table).eq(index).children().eq(3).children().attr("data-item")){
				//4. (same) or (if menu structure is large+medium+small(ex>management) -> not same) , same_cnt plus
				same_cnt += 1;
				if((index+1) == table.length){
					rowspanTable(index, same_cnt);
					firstSetting(index);
				}
			}else{	//5. not same(last row that rowspan)
				if(checkMenuLarge($(table).eq(index-1).children().eq(3).children().attr("data-item"))){
					same_cnt += 1;
				}else{
					if(same_cnt > 1){	//same count == 1, not rowspan
						rowspanTable(index - 1, same_cnt);
						firstSetting(index);
					}
				}
			}
		}
	});
	
	function firstSetting(index){
		connectMenu = "";
		connectMenu = $(table).eq(index).children().eq(3).children().attr("data-item");
		same_cnt = 0;
		same_cnt += 1;
	}
	
	function checkMenuLarge(connectMenu){	//case menu_structure is large+medium+small
		<c:forEach var="i" begin="0" end="${MenuListLarge.size()-1}">
			var checkLarge = "${MenuListLarge[i].connectMenu }";
			if(checkLarge.indexOf(",") != -1){
				var checkArray = checkLarge.split(",");
				
				for(var i=0;i<checkArray.length;i++){
					<c:forEach var="j" begin="0" end="${MenuListMedium.size()-1}">
						var checkMedium = "${MenuListMedium[j].connectMenu }";
						if(checkMedium == connectMenu){
							return true;
						}
					</c:forEach>
				}
			}
		</c:forEach>
		return false;
	}
	
	function rowspanTable(index, same_cnt){
		for(i = index ; i >= (index - same_cnt + 1) ; i--){
			var rowspan = $(table).eq(i);
			if( i == (index - same_cnt + 1) ){
				rowspan.children().eq(0).attr("rowspan", same_cnt);

				//1>출력하는 대메뉴 권한꺼 칸 합치기
				<c:forEach var="i" begin="0" end="${MenuListLarge.size()-1}">
					var check = "${MenuListLarge[i].connectMenu }";
					//1) 중메뉴가 있는 대메뉴인 경우
					if(check.indexOf(",") != -1){
						var checkArray = check.split(",");
						
						if( (checkArray[0] == "${MenuListMedium[0].menuSeq}") && ("${MenuListMedium[0].connectMenu}" == rowspan.children().eq(0).html()) ){
							rowspan.children().eq(0).text("<spring:message code='${MenuListLarge[i].menuName}' />");
						}
					}
					//2) 중메뉴가 없는 대메뉴인 경우
					else{
						if(rowspan.children().eq(0).html() == check){
							rowspan.children().eq(0).text("<spring:message code='${MenuListLarge[i].menuName}' />");
						}
					}
				</c:forEach>

				//2>미출력하는 메뉴 권한꺼 칸 합치기
				<c:forEach var="i" begin="0" end="${MenuListEtc.size()-1}">
					var check = "${MenuListEtc[i].connectMenu }";
					//1) 중메뉴가 있는 대메뉴인 경우
					if(check.indexOf(",") != -1){
						var checkArray = check.split(",");
						
						if( (checkArray[0] == "${MenuListMedium[0].menuSeq}") && ("${MenuListMedium[0].connectMenu}" == rowspan.children().eq(0).html()) ){
							rowspan.children().eq(0).text("<spring:message code='${MenuListLarge[i].menuName}' />");
						}
					}
					//2) 중메뉴가 없는 대메뉴인 경우
					else{
						if(rowspan.children().eq(0).html() == check){
							rowspan.children().eq(0).text("<spring:message code='${MenuListEtc[i].menuName}' />");
						}
					}
				</c:forEach>
			}else{
				rowspan.children().eq(0).remove();
			}
		}		
	}
	// *상위메뉴 rowspan 처리 - end
	
	// when checkbox check, default check contents -> auto check(각 메뉴의 번호/항목값들이 불변이라는 전제 필수!)
	//1. 각 페이지의 등록/수정등의 처리를 선택하기 위해서는 목록이 필수로 선택되어야 한다.(자동체크)
	//2. 각 페이지의 목록이 해제되는 경우, 해당 페이지의 등록/수정 체크항목들도 전부 해제된다
	//3. 인앱콘텐츠의 항목들이 선택 될 경우, 앱 목록이 자동 체크되어야 한다.
	//4. 앱 목록이 해제되는 경우, 인앱콘텐츠 체크항목들도 전부 해제된다.
	$("input[id^='menu_']").on("click", function(){
		var connectMenu = $(this).attr("data-item");
		var thisId = $(this).attr("id");

		// 1. checkbox - list auto check process
		if($(this).is(":checked") == true){
			$("input[id^='menu_']").each(function(index){
				if(thisId != $(this).attr("id")){
					if(connectMenu == $(this).attr("data-item") && $(this).attr("data-value") == "1" ){
						if($(this).is(":checked") == false){
							$(this).prop("checked", true);
							return false;
						}
					}
				}
			});
		}

		// 2. checkbox - list auto check release process
		if( $(this).attr("data-value") == "1" && $(this).is(":checked") == false ){
			var releaseId = $(this).attr("data-item");
			$("input[id^='menu_']").each(function(index){
				if( $(this).attr("data-item") == releaseId && $(this).is(":checked") == true){
					$(this).prop("checked", false);
				}
			});
		}

		// 3. checkbox - inapp contents check, app list auto check
		if( $(this).attr("data-item") == "107" && $(this).is(":checked") == true ){
			if( $("#menu_0").is(":checked") == false ){
				$("#menu_0").prop("checked", true);
			}
		}

		// 4. checkbox - app list check release, inapp contents check auto release
		if( $(this).attr("id") == "menu_0" && $(this).is(":checked") == false ){
			$("input[id^='menu_']").each(function(index){
				if( $(this).attr("data-item") == "107" && $(this).is(":checked") == true){
					$(this).prop("checked", false);
				}
			});
		}
	});
	// when checkbox check, default check contents -> auto check - end
	
	//modify group process
	$("#modifyBtn").click(function(e){//그룹 생성 처리
		//1. 빈값 체크
		if($("#groupName").val() == ""){
			//message : 그룹명을 입력해주세요.
			alert("<spring:message code='group.write.012' />");
			return false;
		}

		//2. 그룹명 중복체크
		if($("#groupName").val() != "${groupUserVO.groupName}"){
			$.ajax({
				url:"/man/group/groupNameOverlap.html",
				type:"POST",
				data:{
					"groupName":$("#groupName").val()
				},
				success:function(result){
					switch(result){
						case 0 :
							modifySubmit();
							break;
						//message : 해당 그룹명이 이미 존재 합니다.
						case 1 : alert("<spring:message code='group.write.008' />");
							break;
						//message : [심각] 해당 그룹명이 2개 이상 존재하거나, DB Error 발생
						case 2 : alert("<spring:message code='group.write.009' />");
							break;
					}
				}
			});
		}else{
			modifySubmit();
		}
	});
	
	function modifySubmit(){
		//message : 수정 하시겠습니까?
		if(confirm("<spring:message code='group.modify.003' />")){
			//3. checked된 메뉴들의 상위 메뉴값 만들기 
			var menuLarge = "3", menuMedium = "";
			var checkLarge = "", checkMedium = "";
			
			$("input[id^='menu_']").each(function(index){
				if($(this).is(":checked")){
					var checkMenuConnect = $(this).attr("data-item");
					if($(this).attr("data-value") == "1"){
						
						//1) 일반 대메뉴 make
						<c:forEach var="i" begin="0" end="${MenuListLarge.size()-1}">
							checkLarge = "${MenuListLarge[i].connectMenu }";						
							var checkLargeArray = checkLarge.split(",");
							
							for(var j=0;j<checkLargeArray.length;j++){
								if(checkMenuConnect == checkLargeArray[j]){
									menuLarge = menuMake(menuLarge, "${MenuListLarge[i].menuSeq }");
								}
							}
							
							checkLarge = "";
						</c:forEach>

						//2) 중메뉴 make and 중메뉴들에 대한 대메뉴 add
						<c:forEach var="i" begin="0" end="${MenuListMedium.size()-1}">
							checkMedium = "${MenuListMedium[i].connectMenu }";
							
							if(checkMenuConnect == checkMedium){
								menuMedium = menuMake(menuMedium, "${MenuListMedium[i].menuSeq }");
								
								<c:forEach var="j" begin="0" end="${MenuListLarge.size()-1}">
									checkLarge = "${MenuListLarge[j].connectMenu }";													
									var checkLargeArray = checkLarge.split(",");
									
									for(var j=0;j<checkLargeArray.length;j++){
										if("${MenuListMedium[i].menuSeq }" == checkLargeArray[j]){
											menuLarge = menuMake(menuLarge, "${MenuListLarge[j].menuSeq }");
										}
									}
									
									checkLarge = "";
								</c:forEach>
							}
							
							checkMedium = "";
						</c:forEach>
					}
				}
			});
			
			//4-1. 상위메뉴값 중복값 제거(대메뉴)
			var arrayLarge = menuLarge.split(",");							
			var result = [];
			var resultText = '';
			$.each(arrayLarge, function (index, element){
			    if ($.inArray(element, result) == -1){
			        result.push(element);
			        resultText += element + '\r\n';
			    }
			});							
			//5-1. 순서 정렬(대메뉴)
			arrayLarge = "";
			arrayLarge = selectionSort(result.slice());
			$("#menuLarge").val(arrayLarge);
	
			//4-2. 상위메뉴값 중복값 제거(중메뉴)
			var arrayMedium = menuMedium.split(",");
			result = null;
			result = [];
			resultText = '';
			$.each(arrayMedium, function (index, element){
			    if ($.inArray(element, result) == -1){
			        result.push(element);
			        resultText += element + '\r\n';
			    }
			});
			//5-2. 순서 정렬(중메뉴)
			arrayMedium = "";
			arrayMedium = selectionSort(result.slice());							
			$("#menuMedium").val(arrayMedium);
			
			$("#group_modify_f").submit();
		}
	}
	
	function menuMake(menuReal, menu){
		if(menuReal == ""){
			menuReal = menu;
		}else{
			menuReal = menuReal+","+menu;
		}
		return menuReal;
	}
	
	function selectionSort(arr) { 
		var len = arr.length; 
		var min; 
		
		for(var outer=0;outer<len-1;++outer){
			min = outer;
			for(var inner=outer+1;inner<len;++inner){
				if(arr[inner] < arr[min]){
					min = inner;
				}
			}
			swap(arr,outer,min);
		}
		
		return arr;
	}
	
	function swap(arr, index1, index2){
		var temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}
	
});

function cancelResist(){
	//message : 지금까지 입력한 자료가 사라집니다. 취소하시겠습니까?
	if(confirm("<spring:message code='group.write.005' />")){
		window.location.href="/man/group/list.html?currentPage=${param.currentPage}&searchValue=${param.searchValue}";
	}
}
</script>
</head>

<body>

<!-- wrap -->
<div id="wrap" class="sub_wrap">
	<!-- header -->
	<%@ include file="../inc/header.jsp" %>
	<!-- header - end -->

	<!-- conteiner -->
	<div id="container">
		<div class="contents table_type1">
		<c:set var="curi" value="${requestScope['javax.servlet.forward.request_uri']}" />
		
			<!-- man header -->
			<div class="tab_area">
				<ul>
					<li <c:if test="${fn:containsIgnoreCase(curi, '/group/')}">class="current last"</c:if>>
						<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_COMPANY_MEMBER')">
						<a href="/man/group/list.html?currentPage=1&searchValue="><spring:message code='man.header.005' /></a>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN_SERVICE')">
						<a href="/man/group/list.html?currentPage=1&searchValue="><spring:message code='man.header.006' /></a>
						</sec:authorize>
					</li>
				</ul>
			</div>
			<!-- man header - end -->
			
			<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_COMPANY_MEMBER')">
			<h2><spring:message code='group.modify.001' /></h2>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ADMIN_SERVICE')">
			<h2><spring:message code='group.modify.001.member' /></h2>
			</sec:authorize>
			
			
			<form id="group_modify_f" name="group_modify_f" method="post" action="/man/group/modify.html?currentPage=${param.currentPage}&searchValue=${param.searchValue}" >
				<input type="hidden" id="menuLarge" name="menuLarge" value=""/>
				<input type="hidden" id="menuMedium" name="menuMedium" value=""/>
				<input type="hidden" id="groupSeq" name="groupSeq" value="${groupUserVO.groupSeq }"/>
				<input type="text" style="display: none;" />	<!-- form submit bug prevent -->
				<div class="section fisrt_section">
				
					<div class="table_area">
						<table id="menu_list" class="coltable">
							<colgroup>
								<col style="width:70px">
								<col style="width:150px">
								<col style="width:350px">
								<col style="width:130px">
							</colgroup>
							<tr class="group">
								<th scope="row"><label class="title" for="userId"><em>*</em> <spring:message code='group.write.007' /></label></th>
								
								<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_COMPANY_MEMBER')">
									<td colspan="3" style="text-align:left;"><input id="groupName" name="groupName" type="text" style="width:50%;" class="line_right" value="${groupUserVO.groupName }"></td>
									<input type="hidden" id="memberGb" name="memberGb" value=""/>
								</sec:authorize>
								<sec:authorize access="hasAnyRole('ROLE_ADMIN_SERVICE')">
									<td colspan="2" style="text-align:left;"><input id="groupName" name="groupName" type="text" style="width:70%;" class="line_right" value="${groupUserVO.groupName }"></td>
									<td>
										<div>
											<c:choose>
												<c:when test = "${groupUserVO.memberGb == '1' }">
													<input name="memberGb" id="company_member" type="radio" value="1" checked="checked" disabled > <label for="company_member"><spring:message code='member.join.051' /></label>&nbsp;&nbsp;&nbsp;
													<input name="memberGb" id="private_member" type="radio" value="2" disabled> <label for="private_member"><spring:message code='member.join.052' /></label>
												</c:when>
												<c:when test = "${groupUserVO.memberGb == '2' }">
													<input name="memberGb" id="company_member" type="radio" value="1" disabled> <label for="company_member"><spring:message code='member.join.051' /></label>&nbsp;&nbsp;&nbsp;
													<input name="memberGb" id="private_member" type="radio" value="2" checked="checked" disabled > <label for="private_member"><spring:message code='member.join.052' /></label>
												</c:when>
											</c:choose>
										</div>
									</td>
								</sec:authorize>
							</tr>
							<tr>
								<th scope="col"><spring:message code='group.write.002' /></th>
								<th scope="col"><spring:message code='group.write.003' /></th>
								<th scope="col"><spring:message code='group.write.004' /></th>
								<th scope="col"><spring:message code='group.write.006' /></th>
							</tr>
							<c:forEach var="i" begin="0" end="${MenuListFunction.size()-1}">
								<tr>
									<td>${MenuListFunction[i].connectMenu }</td>
									<td><spring:message code="${MenuListFunction[i].menuName }" /></td>
									<td><spring:message code="${MenuListFunction[i].menuDescription }" /></td>
									<td><input type="checkbox" id="menu_${i}" name="menuFunction" value="${MenuListFunction[i].menuSeq }" data-value="${MenuListFunction[i].mustYn }" data-item="${MenuListFunction[i].connectMenu }"></td>
								</tr>
							</c:forEach>
						</table>
					</div>
	
					<div class="btn_area_bottom tCenter">
						<a id="modifyBtn" href="#modifyBtn" class="btn btnL btn_red"><spring:message code='group.modify.002' /></a>
						<a href="javascript:cancelResist();" class="btn btnL btn_gray_light"><spring:message code='group.write.011' /></a>
					</div>	
									
				</div>
			</form>
			
		</div>
	</div>
	<!-- conteiner - end -->
	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- footer - end -->

</div>
<!-- wrap - end -->

</body>
</html>