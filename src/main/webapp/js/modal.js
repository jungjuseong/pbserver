$(function() {
	//Modal Layer
	$('a[name=modal]').click(function(e) {
		e.preventDefault();
		var id = $(this).attr('href');
		var maskHeight = $(document).height();
		var maskWidth = $(document).width();

		$('#mask').css({'width':maskWidth,'height':maskHeight});
		$('#boxes').show();
		$('#mask').show();

		var winH = $(window).height();
		var winW = $(document).width();
		$(id).css('top', winH/2-$(id).height()/2);
		$(id).css('left', winW/2-$(id).width()/2);
		$(id).show();
	});

	//Layer popup
	$('a[name=lyrPopup]').click(function(e) {
		e.preventDefault();
		var id = $(this).attr('href');
		var maskHeight = $(document).height();
		var maskWidth = $(document).width();

		$('#lyrPop').show();

		var winH = $(window).height();
		var winW = $(document).width();
		$(id).css('top', winH/2-$(id).height()/2);
		$(id).css('left', winW/2-$(id).width()/2);
		$(id).show();
	});

	$(window).resize(function () {
		var box = $('#boxes .window');
		var box2 = $('#lyrPop .window');

		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
		$('#mask').css({'width':maskWidth,'height':maskHeight});

		var winH = $(window).height();
		var winW = $(window).width();
		box.css('top', winH/2 - box.height()/2);
		box.css('left', winW/2 - box.width()/2);
		box2.css('top', winH/2 - box2.height()/2);
		box2.css('left', winW/2 - box2.width()/2);
	});

	$('.lyrClose').click(function(e) {
		e.preventDefault();
		$('#boxes').hide();
		$('.window').hide();
		$('#lyrPop').hide();
		$('#freeForm').empty().hide();
	});

	$('#mask').click(function () {
		$('#boxes').hide();
		$('.window').hide();
		$('#lyrPop').hide();
		$('#freeForm').empty().hide();
	});

	//Logout Button
	$(".btnLogout").click(function() {
		TnLogOut(document.frmLogin);
	});

	//go Top
	$(".goTop").click(function() {
		$('html, body').animate({scrollTop:0});
	});

	//Login Layer Inputbox Control
	$(".txtInp, .select").focus(function(){
		$(this).removeClass('offInput').addClass('onInput');
	});
	$(".txtInp, .select").blur(function(){
		$(this).removeClass('onInput').addClass('offInput');
	});

	//Product photo mouseover control
	$('.pdtPhoto').hover(function() {
		$(this).children('.pdtAction').toggle();
	});

	// My Tenbyten Lnb
	$('.quickList .menuB dt').click(function() {
		$(this).toggleClass('on');
		$(this).next('dd').toggle();
	});
	$('.allMenu').click(function(){
		$(this).toggleClass('open');
		$('.menuB dt').removeClass('on');
		if ($(this).hasClass('open')){
			$('.menuB dd').show();
			$('.allMenu span').text('전체메뉴 접기');
		} else {
			$('.menuB dd').hide();
			$('.menuB dt.current').next('dd').show();
			$('.allMenu span').text('전체메뉴 보기');
		};
	});
	$('.menuB dt.current').next('dd').show();
	$('.nonMemLnb .menuB dd').show();
	$('.nonMemLnb .menuB dt').click(function(){
		$(this).next('dd').show();
	});

	// My Tenbyten Skin Select
	$('.skinChg .selSkin li').click(function(){
		$('.skinChg .selSkin li').removeClass('current');
		$(this).addClass('current');
	});

	// contents layer
	$('.addInfo').mouseover(function(){
		$(this).children('.contLyr').show();
	});
	$('.addInfo').mouseleave(function(){
		$(this).children('.contLyr').hide();
	});

	// tab
	$('.tabCont').hide();
	$('.tabCont:first').show();
	$('.tabNavi li:first').addClass('current');
	$('.tabNavi li a').hover(function(){
		$('.tabNavi li').removeClass('current');
		$(this).parent().addClass('current');
		var currentTab = $(this).attr('href');
		$('.tabCont').hide();
		$(currentTab).show();
		return false;
	});
});
