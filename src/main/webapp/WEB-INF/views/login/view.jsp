<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>

<%@ include file="/WEB-INF/views/layout/commonLib.jsp" %>

<style>
body.login-page {
	/* background-image: url('/resources/images/intro.jpg'); */
	background-position: center;
	background-size: cover;
	background-repeat: no-repeat;
}
</style>

<script>

	function getCookieValue(cookieName){
	
		// 자바 스크립트 로직
		var cookies = document.cookie.split("; ")
		
		for(var i = 0 ; i < cookies.length ; i++) {
			
			var cookie = cookies[i].split("=");
			
			if(cookie[0] == cookieName) {
				console.log(cookie[1]);
			}
		}
	
		//원하는 쿠키가 없는경우
		return "";
		
	}
	
	function setCookie(cookieName, cookieValue, expires) {
	
		var today = new Date();
	
		// 현재날짜에서 미래로 + expires 만큼 한 날짜 구하기
		today.setDate(today.getDate() + expires);
	
		document.cookie = cookieName + "=" + cookieValue + "; path=/; expires="+ today.toGMTString();
	
		console.log(document.cookie)
		
	}
	
	// 해당 쿠키의 expires속성을 과거날짜로 변경
	function deleteCookie(cookieName) {
		setCookie(cookieName, "", -1);
	}
	
	$(function(){
		
		// remember me cookie 확인
		if(Cookies.get("REMEMBERME") == "Y"){
			$("#inputEmail").val(Cookies.get("USERNM"));
			$("input[type=checkbox]").prop("checked", true);
		};
	
		// 로그인 버튼이 클릭 되었을 때 이벤트 헨들러
		$("#logBtn").on("click", function(){
	
			if( $("input[type=checkbox]").prop("checked") ){
				Cookies.set("USERNM", $("#inputEmail").val());
				Cookies.set("REMEMBERME", "Y");
			} else {
				Cookies.remove("USERNM");
				Cookies.remove("REMEMBERME");
			}

			console.log()
			// submit
			$("form").submit();
	
		})
	
	});


</script>

</head>
<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<a href="#"><b>관리자 로그인</b></a>
		</div>
		<!-- /.login-logo -->
		<div class="card">
			<div class="card-body login-card-body">
				<p class="login-box-msg">Sign in to start your session</p>

				<form action="${pageContext.request.contextPath }/login">
					<div class="form-group has-feedback">
						<input type="text" class="form-control" name="userid"
							placeholder="아이디를 입력하세요." value="${userid }"> <span
							class="glyphicon glyphicon-envelope form-control-feedback"></span>
					</div>
					<div class="form-group has-feedback">
						<input type="password" class="form-control" name="pass"
							placeholder="패스워드를 입력하세요." value=""> <span
							class="glyphicon glyphicon-lock form-control-feedback"></span>
					</div>
					<div class="row">
						<div class="col-sm-8">
							<div class="checkbox icheck">
								<label> <input type="checkbox" name="rememberMe"
									value=""> Remember Me
								</label>
							</div>
						</div>
						<!-- /.col -->
						<div class="col-sm-4">
							<button type="button" class="btn btn-primary btn-block btn-flat" id="logBtn">로그인</button>
						</div>
						<!-- /.col -->
					</div>
				</form>

			</div>
			<!-- /.login-box-body -->
		</div>
	</div>
	<!-- /.login-box -->




</body>
</html>








