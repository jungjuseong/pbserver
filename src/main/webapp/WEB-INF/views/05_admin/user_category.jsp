<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/top.jsp" %>
</head>


<body>

<!-- wrap -->
<div id="wrap" class="sub_wrap">
	
	<!-- header -->
	<%@ include file="../inc/header.jsp" %>
	<!-- //header -->

	
	<!-- conteiner -->
	<div id="container">
		<div class="contents join_area">
			<!-- 앱 카테고리 -->
			<!-- man header -->
			<%@ include file="../inc/man_header.jsp" %>
			<!-- //man header -->
			
			<h2>앱 카테고리</h2>
			
			<div class="section fisrt_section">
				<div class="clfix">
					<div class="category_detail">
						<h3>1차 카테고리</h3>
						<div class="form_area">
							<input name="" type="text" style="width:160px;">
							<a class="btn btnXS btn_gray_dark" href="#">저장</a>
							<a class="btn btnXS btn_gray_dark" href="#">수정</a>
							<a class="btn btnXS btn_gray_dark" href="#">삭제</a>
						</div>
						<div>
							<select name="" multiple>
								<option>소설</option>
								<option>어린이 도서</option>
								<option>잡지</option>
								<option>4</option>
								<option>5</option>
								<option>6</option>
								<option>7</option>
								<option>8</option>
								<option>9</option>
								<option>10</option>
							</select>
						</div>
					</div>
					<div class="category_detail">
						<h3>2차 카테고리</h3>
						<div class="form_area">
							<input name="" type="text" style="width:160px;">
							<a class="btn btnXS btn_gray_dark" href="#">저장</a>
							<a class="btn btnXS btn_gray_dark" href="#">수정</a>
							<a class="btn btnXS btn_gray_dark" href="#">삭제</a>
						</div>
						<div>
							<select name="" multiple></select>
						</div>
					</div>
				</div>

				<div class="btn_area_bottom tCenter">
					<a href="#" class="btn btnL btn_red">+ 카테고리 추가</a>
					<a href="#" class="btn btnL btn_gray_light">삭제</a>
				</div>
			</div>
			
			<!-- //앱 카테고리 -->
		</div>
	</div>
	<!-- //conteiner -->

	
	<!-- footer -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- //footer -->

</div><!-- //wrap -->

</body>
</html>