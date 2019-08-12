<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fm" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="curi" value="${requestScope['javax.servlet.forward.request_uri']}" />
<sec:authorize access="isAuthenticated()">  
	<sec:authentication property="principal.memberVO.userId" var="userId" />
	<sec:authentication property="principal.memberVO.userSeq" var="userSeq" />
	<sec:authentication property="principal.isBook" var="isBook" />
</sec:authorize>
	<div id="header">
		<div class="contents">
			<h1><img src="/images/logo.png" alt="로고"></h1>
<script>


$(document).ready(function(){
	
	$("#logout").click(function(){
		$.ajax({
            url: "/logoutFlagOff.html" ,
            type: "POST" ,
            async : false ,
            data:{
            	userSeq : "${userSeq}"
            },
            success: function (result){
               switch (result){
                     case 0 : 
              			alert("<spring:message code='down.control.001' />")
                    	 return ;
                      break ;
                    case 1 : 
                      break ;
               }
            }
        });

	});
});

function moveToDownList(){
	var isMobile = "";

	if(/Android/i.test(navigator.userAgent)){
		isMobile = "ADD";
	}else if(/iPhone|iPad/i.test(navigator.userAgent)){
		isMobile = "IPHD";
	}else{
		isMobile = "NOMB"
	}

	window.location.href="/down/list.html?currentPage=1&isMobile="+isMobile;
}
</script>
			<div class="my_area">
				<ul>
					<sec:authorize access="hasRole('ROLE_ANONYMOUS')">
						<li>GUEST</li>
						<li><a href="/index.html"><spring:message code='header.001' /></a></li>					
					</sec:authorize>
					<sec:authorize access="hasAnyRole('ROLE_ADMIN_SERVICE','ROLE_INDIVIDUAL_MEMBER','ROLE_COMPANY_MEMBER','ROLE_USER')">
						<li><sec:authentication property="principal.username" /></li>
						<li><a href="/mypage/password.html"><spring:message code='header.002' /></a></li>
						<li><a id="logout" href="j_spring_security_logout"><spring:message code='header.003' /></a></li>
					</sec:authorize>
				</ul>
			</div>
			
			<!-- 20180516 : lsy - group concept temp add -->
			<sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_COMPANY_MEMBER', 'ROLE_INDIVIDUAL_MEMBER')">
				<div class="gnb clfix">
					<ul>
					<c:forEach var="i" begin="0" end="${menuLarge.size()-1}">
						<c:choose>
							<c:when test="${fn:containsIgnoreCase(menuLarge[i].checkMenuName, 'down')}">	<!-- 다운로드 시, PC인지 모바일(and/ios)인지 구분하기 위한 분기처리 -->
								<li <c:if test="${fn:containsIgnoreCase(curi, menuLarge[i].checkMenuName)}">class="current"</c:if>><a href="javascript:moveToDownList();"><spring:message code='${menuLarge[i].menuName }' /></a></li>
							</c:when>
							<c:otherwise>
								<li <c:if test="${fn:containsIgnoreCase(curi, menuLarge[i].checkMenuName)}">class="current"</c:if>><a href="${menuLarge[i].pageUrl }"><spring:message code='${menuLarge[i].menuName }' /></a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					</ul>
				</div>
			</sec:authorize>
			<sec:authorize access="!hasAnyRole('ROLE_USER', 'ROLE_COMPANY_MEMBER', 'ROLE_INDIVIDUAL_MEMBER')">
				<c:choose>			
				<c:when test="${userId eq 'bookUser' && isBook eq true }">
					<div class="gnb clfix">
						<ul>
							<sec:authorize access="hasAnyRole('ROLE_ADMIN_SERVICE')">
							<li <c:if test="${fn:containsIgnoreCase(curi, 'app/')}">class="current"</c:if>><a href="/book/list.html?currentPage=1"><spring:message code='app.list.header' /></a></li>
							</sec:authorize>
							<li <c:if test="${fn:containsIgnoreCase(curi, 'down/')}">class="current"</c:if>><a href="javascript:moveToDownList();"><spring:message code='down.list.017' /></a></li>
						</ul>
					</div>
				</c:when>
				
				<c:otherwise>
					<div class="gnb clfix">
						<ul>
							<sec:authorize access="hasAnyRole('ROLE_ADMIN_SERVICE')">
								<li <c:if test="${fn:containsIgnoreCase(curi, 'app/')}">class="current"</c:if>><a href="/app/list.html?currentPage=1&appSeq=&searchValue=&isAvailable=true"><spring:message code='app.list.header' /></a></li>
								<li <c:if test="${fn:containsIgnoreCase(curi, 'contents/')}">class="current"</c:if>><a href="/contents/list.html?page=1"><spring:message code='contents.title.001' /></a></li>
							</sec:authorize>
							
							<li <c:if test="${fn:containsIgnoreCase(curi, 'down/')}">class="current"</c:if>><a href="javascript:moveToDownList();"><spring:message code='down.list.017' /></a></li>
								
							<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
								<li <c:if test="${fn:containsIgnoreCase(curi, 'template/')}">class="current"</c:if>><a href="/template/list.html?status=y"><spring:message code='template.title.001' /></a></li>
								<li <c:if test="${fn:containsIgnoreCase(curi, 'man/')}">class="current"</c:if>><a href="/man/user/list.html?page=1&searchType=&searchValue=&isAvailable=false"><spring:message code='manage.title.001' /></a></li>
							</sec:authorize>							
						</ul>
					</div>
				</c:otherwise>
			</c:choose>
			</sec:authorize>
			<!-- 20180516 : lsy - group concept temp add - end -->
<%-- 			
			<div class="my_area">
				<ul>
					<sec:authorize access="hasRole('ROLE_ANONYMOUS')">
						<li>GUEST</li>
						<li><a href="/index.html"><spring:message code='header.001' /></a></li>					
					</sec:authorize>
					<sec:authorize access="hasAnyRole('ROLE_COMPANY_MIDDLEADMIN','ROLE_COMPANY_MEMBER','ROLE_COMPANY_DISTRIBUTOR','ROLE_COMPANY_CREATOR','ROLE_COMPANY_USER','ROLE_INDIVIDUAL_MEMBER','ROLE_ADMIN_SERVICE')">
						<li><sec:authentication property="principal.username" /></li>
						<li><a href="/mypage/password.html"><spring:message code='header.002' /></a></li>
						<li><a id="logout" href="j_spring_security_logout"><spring:message code='header.003' /></a></li>
					</sec:authorize>
				</ul>
			</div>
			
			<c:choose>			
				<c:when test="${userId eq 'bookUser' && isBook eq true }">
					<div class="gnb clfix">
						<ul>
							<sec:authorize access="hasAnyRole('ROLE_COMPANY_MIDDLEADMIN', 'ROLE_COMPANY_CREATOR','ROLE_ADMIN_SERVICE')">
							<li <c:if test="${fn:containsIgnoreCase(curi, 'app/')}">class="current"</c:if>><a href="/book/list.html?currentPage=1"><spring:message code='app.list.header' /></a></li>
							</sec:authorize>
							<li <c:if test="${fn:containsIgnoreCase(curi, 'down/')}">class="current"</c:if>><a href="javascript:moveToDownList();"><spring:message code='down.list.017' /></a></li>
						</ul>
					</div>
				</c:when>
				
				<c:otherwise>
					<div class="gnb clfix">
						<ul>
							<sec:authorize access="hasAnyRole('ROLE_COMPANY_MIDDLEADMIN', 'ROLE_COMPANY_CREATOR','ROLE_ADMIN_SERVICE')">
								<li <c:if test="${fn:containsIgnoreCase(curi, 'app/')}">class="current"</c:if>><a href="/app/list.html?currentPage=1&appSeq=&searchValue=&isAvailable=true"><spring:message code='app.list.header' /></a></li>
								<li <c:if test="${fn:containsIgnoreCase(curi, 'contents/')}">class="current"</c:if>><a href="/contents/list.html?page=1"><spring:message code='contents.title.001' /></a></li>
							</sec:authorize>
							
								<li <c:if test="${fn:containsIgnoreCase(curi, 'down/')}">class="current"</c:if>><a href="javascript:moveToDownList();"><spring:message code='down.list.017' /></a></li>
								
							<sec:authorize access="hasRole('ROLE_ADMIN_SERVICE')">
								<li <c:if test="${fn:containsIgnoreCase(curi, 'template/')}">class="current"</c:if>><a href="/template/list.html"><spring:message code='template.title.001' /></a></li>
							</sec:authorize>
							
							<sec:authorize access="hasAnyRole('ROLE_COMPANY_MIDDLEADMIN','ROLE_ADMIN_SERVICE','ROLE_COMPANY_MEMBER')">
								<li <c:if test="${fn:containsIgnoreCase(curi, 'man/')}">class="current"</c:if>><a href="/man/user/list.html?page=1&searchType=&searchValue=&isAvailable=false"><spring:message code='manage.title.001' /></a></li>
							</sec:authorize>
								<!--이렇게 복잡하게 된이유는
									권한의 상속기능 때문임
									  -->
							<sec:authorize access="!hasAnyRole('ROLE_ADMIN_SERVICE','ROLE_COMPANY_MEMBER')">
								<sec:authorize access="hasAnyRole('ROLE_COMPANY_DISTRIBUTOR', 'ROLE_INDIVIDUAL_MEMBER')">
									<li <c:if test="${fn:containsIgnoreCase(curi, 'man/')}">class="current"</c:if>><a href="/man/provision/list.html"><spring:message code='manage.title.001' /></a></li>
								</sec:authorize>
							</sec:authorize>
							
							<!-- 20180417 : lsy - distributeReqList add -->
							<sec:authorize access="hasAnyRole('ROLE_COMPANY_MIDDLEADMIN','ROLE_COMPANY_DISTRIBUTOR','ROLE_COMPANY_MEMBER')">
								<li <c:if test="${fn:containsIgnoreCase(curi, 'distribute/')}">class="current"</c:if>><a href="/distribute/list.html?page=1&objectGb=app"><spring:message code='distribute.title.001' /></a></li>
							</sec:authorize>
							<!-- 20180417 : lsy - distributeReqList add - end -->
							
						</ul>
					</div>
				</c:otherwise>
			</c:choose>
 --%>			
			<!-- 
			<div class="gnb clfix">
				<ul>
					<li <c:if test="${fn:containsIgnoreCase(curi, 'app/')}">class="current"</c:if>><a href="/app/list.html">APP</a></li>
					<li <c:if test="${fn:containsIgnoreCase(curi, 'contents/')}">class="current"</c:if>><a href="/contents/list.html?page=1">콘텐츠</a></li>
					<li <c:if test="${fn:containsIgnoreCase(curi, 'down/')}">class="current"</c:if>><a href="/down/list.html"><spring:message code='down.list.017' /></a></li>
					<li <c:if test="${fn:containsIgnoreCase(curi, 'template/')}">class="current"</c:if>><a href="/template/list.html"><spring:message code='template.title.001' /></a></li>
					<li <c:if test="${fn:containsIgnoreCase(curi, 'man/')}">class="current"</c:if>><a href="/man/user/list.html?page=1">관리</a></li>				
				</ul>
			</div>
			 -->
		</div>
	</div>