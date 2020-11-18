<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="x-ua-compatible" content="ie=edge">

<title>회원 상세정보</title>

<%@ include file="/WEB-INF/views/layout/commonLib.jsp" %>

<script type="text/javascript">

	$(document).ready(function(){

		//client side에서는 서버사이드 변수나 값을 사용가능
		member("${param.userid}")
		//memberAjax(${param.userid})으로 하게되면 문자열로 인식하지않는다 잘못된코드
		
		$('#updateBtn').on('click', function(){
			document.location="/member/update?userid=${param.userid}";
		});

		$("#backBtn").on('click', function(){
			document.location="/login/main";
		})

		$("#deleteBtn").on('click', function(){

			 if (confirm("정말 삭제하시겠습니까??") == true){    //확인
				document.location="/member/delete?userid=${param.userid}";
			 }else{   //취소
			     return false;
			 }
		})

	});

	function member(userid){
		$.ajax({url : "/member/member",
			data : {userid : userid},
			method : "post",
			success : function(data){
				$('#pictureViewImg').attr('src', '${cp }/profileImgView?userid=' + data.memberVo.userid);
				$('#userid').html(data.memberVo.userid);
				$('#usernm').html(data.memberVo.usernm);
				$('#alias').html(data.memberVo.alias);
				$('#addr1').html(data.memberVo.addr1);
				$('#addr2').html(data.memberVo.addr2);
				$('#zipcode').html(data.memberVo.zipcode);
				$('#updateBtn').attr('data-userid', data.memberVo.userid);
				
			}
		});
	}

</script>



</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">

		<%@ include file="/WEB-INF/views/layout/header.jsp" %>
		<%@ include file="/WEB-INF/views/layout/left.jsp" %>


		<div id="if_list_div" style="position: relative; padding: 0; overflow: hidden;">
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">

				<!-- Main content -->
				<section class="content register-page" style="height:100%;">
					<div class="container-fluid">
						<div class="login-logo">
							<b>회원 상세정보</b>
						</div>
						<!-- form start -->
						<div class="card">
							<div class="register-card-body">
								<form role="form" class="form-horizontal" >
									<div class="input-group mb-3">
										<div class="mailbox-attachments clearfix" style="text-align: center; width:100%;">
											<div class="mailbox-attachment-icon has-img" id="pictureView" style="border: 1px solid green; height: 200px; width: 140px; margin: 0 auto;">
												<img id="pictureViewImg" style="width:100%; height:100%;"/>
											</div>
											
										</div>
										<br />
									</div>
									
									<div class="form-group row">
										<label for="id" class="col-sm-3" style="font-size: 0.9em;">
											<span style="color: red; font-weight: bold;">*</span>아이디
										</label>
										<div class="col-sm-9 input-group-sm">
											<span class="input-group-append-sm" id="userid"></span>
										</div>
									</div>
									
									<div class="form-group row">
										<label for="pwd" class="col-sm-3" style="font-size: 0.9em;">
											<span style="color: red; font-weight: bold;">*</span>패스워드</label>
										<div class="col-sm-9 input-group-sm">
										<span class="input-group-append-sm" id="pass">*******</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label for="name" class="col-sm-3" style="font-size: 0.9em;">
											<span style="color: red; font-weight: bold;">*</span>이 름
										</label>
										<div class="col-sm-9 input-group-sm">
											<span class="input-group-append-sm" id="usernm"></span>
										</div>

									</div>
									<div class="form-group row">
										<label for="alias" class="col-sm-3" style="font-size: 0.9em;">별명</label>
										<div class="col-sm-9 input-group-sm">
											<span class="input-group-append-sm" id="alias"></span>
										</div>
									</div>
									<div class="form-group row">
										<label for="addr1" class="col-sm-3 control-label">주소</label>
										<div class="col-sm-3 input-group-sm">
											<span name="addr1" class="form-control" id="addr1"></span>
										</div>
										<div class="col-sm-3 input-group-sm">
											<span name="addr1" class="form-control" id="addr2"></span>
										</div>
										
										<div class="col-sm-2 input-group-sm">
											<span name="addr1" class="form-control" id="zipcode"></span>
										</div>
										
									</div>
									
									<div class="card-footer">
										<div class="row">
											<div class="col-sm-6">
												<button type="button" id="updateBtn" class="btn btn-info">수정</button>
												<button type="button" id="deleteBtn" class="btn btn-info">삭제</button>
											</div>

											<div class="col-sm-6">
												<button type="button" id="backBtn" onclick="CloseWindow();" class="btn btn-default float-right">&nbsp;&nbsp;&nbsp;목 &nbsp;&nbsp;록&nbsp;&nbsp;&nbsp;</button>
											</div>

										</div>
									</div>
								</form>
							</div>
							<!-- register-card-body -->
						</div>
					</div>
				</section>
				<!-- /.content -->
			</div>
			<!-- /.content-wrapper -->
		</div>
	</div>

	<!-- Main Footer -->
	<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
	
	
	</div>
	<!-- ./wrapper -->

</body>
</html>







