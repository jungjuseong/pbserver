<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fm" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="curi" value="${requestScope['javax.servlet.forward.request_uri']}" />
	<sec:authorize access="hasAnyRole('ROLE_COMPANY_MEMBER', 'ROLE_INDIVIDUAL_MEMBER')">
		<div class="tab_area">
			<ul>
				<li <c:if test="${fn:containsIgnoreCase(curi, '/mypage/')}">class="current last"</c:if>><a href="/mypage/password.html"><spring:message code='mypage.header.001' /></a></li>
				<li <c:if test="${fn:containsIgnoreCase(curi, '/my/')}">class="current last"</c:if>><a href="/my/license.html"><spring:message code='mypage.header.002' /></a></li>
			</ul>
		</div>
	</sec:authorize>