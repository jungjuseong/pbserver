<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fm" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="curi" value="${requestScope['javax.servlet.forward.request_uri']}" />
	<div class="tab_area">
		<ul>
		<!-- 20180516 : lsy - group concept temp add -->
			<sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_COMPANY_MEMBER', 'ROLE_INDIVIDUAL_MEMBER')">
				<c:forEach var="i" begin="0" end="${menuMedium.size()-1}">
					<li <c:if test="${fn:containsIgnoreCase(curi, menuMedium[i].checkMenuName)}">class="current"</c:if>><a href="${menuMedium[i].pageUrl }"><spring:message code='${menuMedium[i].menuName }' /></a></li>
				</c:forEach>
			</sec:authorize>
			<sec:authorize access="!hasAnyRole('ROLE_USER', 'ROLE_COMPANY_MEMBER', 'ROLE_INDIVIDUAL_MEMBER')">
				<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
				<!--message : 회원  -->
					<li <c:if test="${fn:containsIgnoreCase(curi, '/user/')}">class="current last"</c:if>><a href="/man/user/list.html?page=1&searchType=&searchValue=&isAvailable=false"><spring:message code='man.header.002' /></a></li>
					<!-- 20180510 : lsy - member group management create -->
					<li <c:if test="${fn:containsIgnoreCase(curi, '/group/')}">class="current last"</c:if>><a href="/man/group/list.html?currentPage=1&searchValue="><spring:message code='man.header.006' /></a></li>
					<!-- 20180510 : lsy - member group management create - end -->
					<!-- 20180905 : lsy - license menu develop -->
					<li <c:if test="${fn:containsIgnoreCase(curi, '/license/')}">class="current last"</c:if>><a href="/man/license/list.html"><spring:message code='man.header.007' /></a></li>
					<!-- 20180905 : lsy - license menu develop - end -->
					<li <c:if test="${fn:containsIgnoreCase(curi, '/preference/')}">class="current last"</c:if>><a href="/man/preference/modify.html"><spring:message code='extend.local.011' /></a></li>				
				</sec:authorize>
			</sec:authorize>
<%-- 			
			<sec:authorize access="hasAnyRole('ROLE_COMPANY_MIDDLEADMIN','ROLE_COMPANY_MEMBER')">
			<!--messsage : 사용자  -->
				<li <c:if test="${fn:containsIgnoreCase(curi, '/user/')}">class="current last"</c:if>><a href="/man/user/list.html?page=1&searchType=&searchValue=&isAvailable=false"><spring:message code='man.header.001' /></a></li>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
			<!--message : 회원  -->
				<li <c:if test="${fn:containsIgnoreCase(curi, '/user/')}">class="current last"</c:if>><a href="/man/user/list.html?page=1&searchType=&searchValue=&isAvailable=false"><spring:message code='man.header.002' /></a></li>
			</sec:authorize>
			
			<sec:authorize access="hasAnyRole('ROLE_COMPANY_MIDDLEADMIN','ROLE_COMPANY_MEMBER')">
	
				<!-- 20180424 : lsy - group management create -->
				<li <c:if test="${fn:containsIgnoreCase(curi, '/group/')}">class="current last"</c:if>><a href="/man/group/list.html?currentPage=1&searchValue="><spring:message code='man.header.005' /></a></li>
				<!-- 20180424 : lsy - group management create - end -->
	
				<li <c:if test="${fn:containsIgnoreCase(curi, '/department/')}">class="current last"</c:if>><a href="/man/department/management.html"> <spring:message code='extend.local.073' /></a></li>
	
				<li <c:if test="${fn:containsIgnoreCase(curi, '/notice/')}">class="current last"</c:if>><a href="/man/notice/list.html?page=1"> <spring:message code='extend.local.072' /></a></li>
				
			<!--message : 앱 카테고리  -->
				<li <c:if test="${fn:containsIgnoreCase(curi, '/category/')}">class="current last"</c:if>><a href="#"><spring:message code='man.header.003' /></a></li>
			<!--message : Distribution Profile  -->
				<li <c:if test="${fn:containsIgnoreCase(curi, '/provision/')}">class="current last"</c:if>><a href="/man/provision/list.html"><spring:message code='man.header.004' /></a></li>
				<li <c:if test="${fn:containsIgnoreCase(curi, '/device/')}">class="current last"</c:if>><a href="/man/device/list.html?page=1"><spring:message code='extend.local.043' /></a></li>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
				<!-- 20180510 : lsy - member group management create -->
				<li <c:if test="${fn:containsIgnoreCase(curi, '/group/')}">class="current last"</c:if>><a href="/man/group/list.html?currentPage=1&searchValue="><spring:message code='man.header.006' /></a></li>
				<!-- 20180510 : lsy - member group management create - end -->
				
				<li <c:if test="${fn:containsIgnoreCase(curi, '/preference/')}">class="current last"</c:if>><a href="/man/preference/modify.html"><spring:message code='extend.local.011' /></a></li>				
			</sec:authorize>
 --%>			
		</ul>
	</div>