<%@page import="java.util.List"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="../inc/top.jsp" %>

<link rel="stylesheet" href="/js/jsonView/jquery.jsonview.css" />
<script type="text/javascript" src="/js/jsonView/jquery.jsonview.js" ></script>

<script type="text/javascript">
	$(function() {
		var jsonData = ${logData.data }
		$("#jstree_div").JSONView(jsonData);
		// with options
		$("#jstree_div-collasped").JSONView(jsonData, { collapsed: true });
	});
</script>
</head>
<body>
<!-- wrap -->
<div id="wrap" class="sub_wrap">
	<!-- conteiner -->
	<div id="container">
		<div class="contents join_area" style="margin: 100px 20px 100px;">
			<div id="jstree_div">
			</div>
		</div>
	</div>
	<!-- //conteiner -->

</div>
<!-- //wrap -->

</body>
</html>