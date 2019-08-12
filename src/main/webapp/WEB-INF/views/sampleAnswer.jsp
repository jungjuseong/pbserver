<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>
<script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.10.3.min.js"></script>
<script type="text/javascript" src="/js/custom-form-elements.min.js"></script><!-- radio & checkbox -->
<script type="text/javascript" src="/js/placeholders.min.js"></script><!-- placeholder -->
<script type="text/javascript" src="/js/global_func.js"></script>
<script type="text/javascript" src="/js/jquery.validate.js"></script>
<script type="text/javascript" src="/js/jquery-dateFormat.js"></script>
<script>


$(document).ready(function(){
	var doNotStringify = {
		    currentTarget: true,
		    delegateTarget: true,
		    originalEvent: true,
		    target: true,
		    toElement: true,
		    view : true
		}
	
	var jsonObj = jQuery.parseJSON( '${json}' );

    writeToDom('Formatted', JSON.stringify(jsonObj, null, 4));
	
	/* alert(jsonObj.name === "pb0001"); */
	//입력된 시간
	/* split.each */
	/* var inputYear = split[0];
	var inputmonth = split[1];
	var inputdate = split[2];
	alert(split);
	$("#inputText").append(split[0]);  */
/* 	var html ="";
	
	$(split).each(function(index){
		
		$("#inputText").append(split[index]); 
	});
	 */
/* var html= "";
html += "<span id='uploadOrgName'>" + fileName +"</span>";
html += "<a class='removeImgBtn2' href='#'><img src='/images/btn_close_s.png' alt='아이콘 썸네일 이미지 닫기'></a>"
$("#inputText").append(html);  */


/* $("[name = SORT]:checked").each(function(index){
	if(index>0){
		sortStr +=",";
	}
	sortStr +=$(this).val();
});  */
});


function writeToDom(title, content) {
    $("#results").append("<div class='header'></div><div><pre>" + content + "</pre></div>");
}



</script>
</head>



<body>
<div id="results"></div>
	<!-- <table>
		<tr>
			<td>123</td>
			<td>456</td>
		</tr>
	</table> -->
	
	<%-- ${json} --%>
</body>
</html>