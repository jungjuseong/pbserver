/**
 * 	회원가입 Fuction
 */



$("#member_join_f").validate({
		rules:{
			userId:{required:true, minlength:6, maxlength:20},
			JS_lastName:{required:true, minlength:1, maxlength:20},
			userPw:{required:true, minlength:6, maxlength:30},
			userPwConfirmw:{required:true, minlength:6, maxlength:30,  equalTo: "#userPw"},
			email:{required:true, email:true},
			phone:{required:true, number:true, minlength:6, maxlength:16},
		},
		messages:{
			userId:{required:"6~20文字で必ず入力しなければなりません。"},
			JS_lastName:{required:"お名前は必ず入力しなければなりません。"},
			userPw:{required:"6~20文字で必ず入力しなければなりません。"},
			userPwConfirm:{required:"6~20文字で必ず入力しなければなりません。"},
			email:{required:"e-mailが正しくありません。"},
			phone:{required:"電話番号が正しくありません。数字のみ入力してください。"},
		},invalidHandler: function() {
			setTimeout(function(){ 
				$("input.error").each(function(){
					var obj_w=$(this).width();
					var left_val=$(this).offset().left+3;
					var top_val=$(this).offset().top+2;
					$(this).css({
						left:obj_w+left_val+"px",
						top:top_val+"px"
					});	
				});
				
			},5);
		}
	});