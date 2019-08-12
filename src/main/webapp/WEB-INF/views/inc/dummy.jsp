<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/js/jquery-1.10.2.min.js"></script>
<!-- 
msg : 경고창 문구
url : 보낼 url
type : 액션방식
	- -1 : history.back();
	- replace : window.location.replace
	- close : self.close();
	- close&popenerreplace
	- href
	- paramName 파라미터키 paramValue 파라미터값 
 -->
<script>


<c:if test="${(empty type) && (empty msg) && (empty url) && not empty validPassword}">

if("<c:out value='${validPassword}'/>" == "false"){
	alert("<spring:message code='dummy.001' />");
	window.location.replace("password.html");
} 

</c:if>

<c:if test="${not empty msg}">
alert('${msg}');
</c:if>
<c:if test="${'-1' eq type}">
history.back();
</c:if>
<c:if test="${'replace' eq type && not empty url}">
window.location.replace("${url}");
</c:if>
<c:if test="${'href' eq type && not empty url}">
	<c:if test="${empty paramName || empty paramValue}">
		window.location.href="${url}";
	</c:if>

	<c:if test="${not empty paramName && not empty paramValue}">
	window.location.href="${url}?${paramName}=${paramValue}";
	</c:if>
</c:if>
<c:if test="${'close' eq type}">
self.close();
</c:if>
<c:if test="${'close&popenerreplace' eq type}">
opener.replace();
self.close();
</c:if>

</script>
</head>
<body>

</body>
</html>