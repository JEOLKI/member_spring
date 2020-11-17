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

<title>회원 리스트</title>


<%@ include file="/WEB-INF/views/layout/commonLib.jsp" %>

<script>

	// 해당 html이 로딩이 완료 되었을 때 실해오디는 이벤트 핸들러 함수
	$(document).ready(function() {

		memberList(1)

		// delegate 방식 : 이미 존재하는 태그(#memberList)에 이벤트를 거는것 
		$("#memberList").on("click", "tr", function() {
			//console.log("memberList tr click");

			// data-userid
			var userid = $(this).data("userid");
			console.log("userid : " + userid);

			document.location = "/member/member?userid=" + userid;

		});
		
	});

	function memberList(p){
		$.ajax({url : "/member/list",
				data : {page : p, pageSize : 5},
				method : "get",
				success : function(data){
					var i = 0;
					// memberList tbody 영역에 들어갈 html 문자열 생성
					var html = "";
					for(var i = 0; i < data.memberList.length ; i++){
						var member = data.memberList[i];
						html += "<tr data-userid='"+ member.userid +"'>";
						html += "	<td>"+ member.userid +"</td>";			
						html += "	<td>"+ member.usernm +"</td>";			
						html += "	<td>"+ member.alias +"</td>";			
						html += "	<td>"+ member.addr1 + member.addr2 +"</td>";			
						html += "	<td>"+ member.fmt_reg_dt +"</td>"; // value 객체에 설정
						html += "</tr>";
					}
					
					$("#memberList").html(html);

					//페이지 내비게이션 html 문자열 동적으로 생성하기
					var html = "";
					if(data.cpage == 1 ){
						html += '<li class="page-item active"><span class="page-link" href="#"><i class="fas fa-angle-double-left"></i></span></li>';
						html += '<li class="page-item active"><span class="page-link" href="#"><i class="fas fa-angle-left"></i></span></li>';
					} else {
						html += '<li class="page-item"><a class="page-link" href="javascript:memberList(1)"><i class="fas fa-angle-double-left"></i></a></li>';
						html += '<li class="page-item"><a class="page-link" href="javascript:memberList('+ (data.cpage-1) +')"><i class="fas fa-angle-left"></i></a></li>';
					}
					
					for(var i = 1 ; i <= data.pages ; i++){


						
						if(i == data.cpage){
							html += '<li class="page-item active"><span class="page-link">'+ i +'</span></li>';
						} else {
							html += '<li class="page-item"><a class="page-link" href="javascript:memberList('+i+');">'+ i +'</a></li>'; // <a href="javascript:memberListAjax(1);"/>
						}
					}

					if(data.cpage == data.pages ){
						html += '<li class="page-item active"><span class="page-link" href="#"><i class="fas fa-angle-right"></i></span></li>';
						html += '<li class="page-item active"><span class="page-link" href="#"><i class="fas fa-angle-double-right"></i></span></li>';
					} else {
						html += '<li class="page-item"><a class="page-link" href="javascript:memberList('+ (data.cpage+1) +')"><i class="fas fa-angle-right"></i></a></li>';
						html += '<li class="page-item"><a class="page-link" href="javascript:memberList('+ data.pages +')"><i class="fas fa-angle-double-right"></i></a></li>';
					}

					$("ul.pagination").html(html);
					
				}
		});
	}

</script>

</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">
	
		<!-- Navbar -->
		<nav class="main-header navbar navbar-expand navbar-white navbar-light">
			<!-- Left navbar links -->
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link" data-widget="pushmenu" href="#"><i class="fas fa-bars"></i></a></li>
				<li class="nav-item d-none d-sm-inline-block"><a href="#" class="nav-link">HOME</a></li>
				<li class="nav-item d-none d-sm-inline-block"><a href="#;" class="nav-link">회원관리</a></li>
			</ul>

			<!-- SEARCH FORM -->
			<form class="form-inline ml-3">
				<div class="input-group input-group-sm">
					<input class="form-control form-control-navbar" type="search" placeholder="Search" aria-label="Search">
					<div class="input-group-append">
						<button class="btn btn-navbar" type="submit">
							<i class="fas fa-search"></i>
						</button>
					</div>
				</div>
			</form>

			<!-- Right navbar links -->
			<ul class="navbar-nav ml-auto">
				<!-- Messages Dropdown Menu -->
				<li class="nav-item dropdown"><a class="nav-link" data-toggle="dropdown" href="#"> <i class="far fa-comments"></i> <span class="badge badge-danger navbar-badge">3</span>
				</a>
					<div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
						<a href="#" class="dropdown-item"> <!-- Message Start -->
							<div class="media">
								<img src="/resources/bootstrap/dist/img/user1-128x128.jpg" alt="User Avatar" class="img-size-50 mr-3 img-circle">
								<div class="media-body">
									<h3 class="dropdown-item-title">
										Brad Diesel <span class="float-right text-sm text-danger"><i class="fas fa-star"></i></span>
									</h3>
									<p class="text-sm">Call me whenever you can...</p>
									<p class="text-sm text-muted">
										<i class="far fa-clock mr-1"></i> 4 Hours Ago
									</p>
								</div>
							</div> <!-- Message End -->
						</a>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item"> <!-- Message Start -->
							<div class="media">
								<img src="./resources/bootstrap/dist/img/user8-128x128.jpg" alt="User Avatar" class="img-size-50 img-circle mr-3">
								<div class="media-body">
									<h3 class="dropdown-item-title">
										John Pierce <span class="float-right text-sm text-muted"><i class="fas fa-star"></i></span>
									</h3>
									<p class="text-sm">I got your message bro</p>
									<p class="text-sm text-muted">
										<i class="far fa-clock mr-1"></i> 4 Hours Ago
									</p>
								</div>
							</div> <!-- Message End -->
						</a>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item"> <!-- Message Start -->
							<div class="media">
								<img src="./resources/bootstrap/dist/img/user3-128x128.jpg" alt="User Avatar" class="img-size-50 img-circle mr-3">
								<div class="media-body">
									<h3 class="dropdown-item-title">
										Nora Silvester <span class="float-right text-sm text-warning"><i class="fas fa-star"></i></span>
									</h3>
									<p class="text-sm">The subject goes here</p>
									<p class="text-sm text-muted">
										<i class="far fa-clock mr-1"></i> 4 Hours Ago
									</p>
								</div>
							</div> <!-- Message End -->
						</a>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item dropdown-footer">See All Messages</a>
					</div></li>
				<!-- Notifications Dropdown Menu -->
				<li class="nav-item dropdown"><a class="nav-link" data-toggle="dropdown" href="#"> <i class="far fa-bell"></i> <span class="badge badge-warning navbar-badge">15</span>
				</a>
					<div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
						<span class="dropdown-header">15 Notifications</span>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item"> <i class="fas fa-envelope mr-2"></i> 4 new messages <span class="float-right text-muted text-sm">3 mins</span>
						</a>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item"> <i class="fas fa-users mr-2"></i> 8 friend requests <span class="float-right text-muted text-sm">12 hours</span>
						</a>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item"> <i class="fas fa-file mr-2"></i> 3 new reports <span class="float-right text-muted text-sm">2 days</span>
						</a>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item dropdown-footer">See All Notifications</a>
					</div></li>
				<li class="nav-item"><a class="nav-link" data-widget="control-sidebar" data-slide="true" href="#"><i class="fas fa-th-large"></i></a></li>
			</ul>
		</nav>
		<!-- /.navbar -->


		<!-- Main Sidebar Container -->
		<aside class="main-sidebar sidebar-dark-primary elevation-4">
			<!-- Brand Logo -->
			<a href="/" class="brand-link"> <img src="/resources/images/line.png" class="brand-image img-circle elevation-3" style="opacity: .8"> <span
				class="brand-text font-weight-light">사용자관리</span>
			</a>

			<!-- Sidebar -->
			<div class="sidebar">
				<!-- Sidebar user panel (optional) -->
				<div class="user-panel mt-3 pb-3 mb-3 d-flex">
					<div class="image">
						<img src="/profile/sally.png" class="img-circle elevation-2" alt="User Image">
					</div>
					<div class="info">
						<div class="row">
							<a class="col-md-8" href="#" class="d-block">sally(병아리)</a>
						</div>
					</div>
				</div>

				<!-- Sidebar Menu -->
				<nav class="mt-2">
					<ul class="nav nav-pills nav-sidebar flex-column subMenuList" data-widget="treeview" data-accordion="false">

					</ul>
				</nav>
				<!-- /.sidebar-menu -->
			</div>

			<!-- /.sidebar -->
		</aside>


		<div id="if_list_div" style="position: relative; padding: 0; overflow: hidden; height: 750px;">
			<div class="content-wrapper" style="min-height: 584px;">
				<!-- Content Header (Page header) -->
				<section class="content-header">
					<div class="container-fluid">
						<div class="row md-2">
							<div class="col-sm-6">
								<h1>회원리스트</h1>
							</div>
							<div class="col-sm-6">
								<ol class="breadcrumb float-sm-right">
									<li class="breadcrumb-item">회원리스트</li>
									<li class="breadcrumb-item">목록</li>
								</ol>
							</div>
						</div>
					</div>
				</section>
				<!-- Main content -->
				<section class="content">
					<div class="card">
						<div class="card-header with-border">
							<button type="button" class="btn btn-primary" onclick="OpenWindow(&#39;registForm.do&#39;,&#39;회원등록&#39;,800,700);">회원등록</button>
							<div id="keyword" class="card-tools" style="width: 550px;">
								<div class="input-group row">
									<!-- sort num -->
									<select class="form-control col-md-3" name="perPageNum" id="perPageNum">
										<option value="">정렬개수</option>
										<option value="3">3개씩</option>
										<option value="5">5개씩</option>
										<option value="7">7개씩</option>
									</select>
									<!-- search bar -->
									<select class="form-control col-md-3" name="searchType" id="searchType">
										<option value="">검색구분</option>
										<option value="i">아이디</option>
										<option value="n">이름</option>
										<option value="a">별명</option>
									</select> <input class="form-control" type="text" name="keyword" placeholder="검색어를 입력하세요." value=""> <span class="input-group-append">
										<button class="btn btn-primary" type="button" id="searchBtn" data-card-widget="search" onclick="searchList_go(1);">
											<i class="fa fa-fw fa-search"></i>
										</button>
									</span>
									<!-- end : search bar -->
								</div>
							</div>
						</div>
						<div class="card-body" style="text-align: center;">
							<div class="row">
								<div class="col-sm-12">
									<table class="table table-bordered">
											<tr>
												<th>아이디</th>
												<th>패스워드</th>
												<th>별명</th>
												<th>도로주소</th>
												<th>등록날짜</th>
												<!-- yyyy-MM-dd  -->
											</tr>
										<tbody id="memberList">
											
										</tbody>
									</table>
								</div>
								<!-- col-sm-12 -->
							</div>
							<!-- row -->
						</div>
						<!-- card-body -->
						<div class="card-footer">
							<nav aria-label="member list Navigation">
								<ul id="paging" class="pagination justify-content-center m-0">
								
								</ul>
							</nav>

						</div>
						<!-- card-footer -->
					</div>
					<!-- card  -->
				</section>
			</div>
		</div>
	</div>

	<!-- Main Footer -->
	<footer class="main-footer">
		<!-- To the right -->
		<div class="float-right d-none d-sm-inline">Anything you want</div>
		<!-- Default to the left -->
		<strong>Copyright &copy; 2014-2019 <a href="https://adminlte.io">AdminLTE.io</a>.
		</strong> All rights reserved.
	</footer>
	</div>
	<!-- ./wrapper -->

	

</body>
</html>







