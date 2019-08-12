<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- 변수 지원, 흐름 제어, URL처리 -->
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %><!-- xml코어, 흐름제어, xml변환 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><!-- 지역, 메세지형식, 숫자, 날짜형식 -->
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %><!-- 데이터베이스 -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%-- <%@ taglib prefix="form"uri="http://www.springframework.org/tags/form" %> --%>

<!doctype html>
<html lang="ko">
<head>
<title>PageBuilder CMS</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7" />
<meta name="format-detection" content="telephone=no">
<meta name="viewport" content="width=device-width, user-scalable=yes, target-densitydpi=device-dpi">
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<link rel="stylesheet" type="text/css" href="/css/contents.css" />
<link rel="stylesheet" type="text/css" href="/css/jquery-ui-1.10.3.css">
<!--[if lt IE 10]>
<link rel="stylesheet" type="text/css" href="/css/ie9.css" />
<![endif]-->

<script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.10.3.min.js"></script>
<script type="text/javascript" src="/js/custom-form-elements.min.js"></script><!-- radio & checkbox -->
<script type="text/javascript" src="/js/placeholders.min.js"></script><!-- placeholder -->
<script type="text/javascript" src="/js/global_func.js"></script>