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

		$('#regBtn').on('click', function(){
			document.location = "/member/regist";
		})

		$("#perPageNum").val("${param.pageSize == null ? '5' : param.pageSize}");
		$("#searchType").val("${param.searchType == null ? 'gubun' : param.searchType}");
		
		/* $("#perPageNum").on("change", function(){
			memberList(1)
		}); */

		$('#searchBtn').on('click', function(){

			if($("#searchType").val() == 'gubun' || $('#searchKeyWord').val() == ""){
				memberList(1);
			} else {
				searchList(1);
			}
		})
		
		
	});

	function memberList(p){
		$.ajax({url : "/member/list",
				data : {page : p, pageSize : $("#perPageNum").val()},
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

	function searchList(p){
		$.ajax({
			url : '/member/search',
			data : { 
					searchType : $('#searchType').val(),
				 	searchKeyWord : $('#searchKeyWord').val(),
				 	page : p,
				 	pageSize : $("#perPageNum").val()
					},
			type : 'get',
			dataType : 'json',
			success : function(data) {
				
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
					html += '<li class="page-item"><a class="page-link" href="javascript:searchList(1)"><i class="fas fa-angle-double-left"></i></a></li>';
					html += '<li class="page-item"><a class="page-link" href="javascript:searchList('+ (data.cpage-1) +')"><i class="fas fa-angle-left"></i></a></li>';
				}
				
				for(var i = 1 ; i <= data.pages ; i++){
					if(i == data.cpage){
						html += '<li class="page-item active"><span class="page-link">'+ i +'</span></li>';
					} else {
						html += '<li class="page-item"><a class="page-link" href="javascript:searchList('+i+');">'+ i +'</a></li>'; // <a href="javascript:memberListAjax(1);"/>
					}
				}

				if(data.cpage == data.pages ){
					html += '<li class="page-item active"><span class="page-link" href="#"><i class="fas fa-angle-right"></i></span></li>';
					html += '<li class="page-item active"><span class="page-link" href="#"><i class="fas fa-angle-double-right"></i></span></li>';
				} else {
					html += '<li class="page-item"><a class="page-link" href="javascript:searchList('+ (data.cpage+1) +')"><i class="fas fa-angle-right"></i></a></li>';
					html += '<li class="page-item"><a class="page-link" href="javascript:searchList('+ data.pages +')"><i class="fas fa-angle-double-right"></i></a></li>';
				}

				$("ul.pagination").html(html);
					
				
			}
		})


		
	}

</script>

</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">
		
		<%@ include file="/WEB-INF/views/layout/header.jsp" %>
		<%@ include file="/WEB-INF/views/layout/left.jsp" %>


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
							<button id="regBtn" type="button" class="btn btn-primary" onclick="OpenWindow(&#39;registForm.do&#39;,&#39;회원등록&#39;,800,700);">회원등록</button>
							<div id="keyword" class="card-tools" style="width: 550px;">
								<div class="input-group row">
									<!-- sort num -->
									<select class="form-control col-md-3" name="perPageNum" id="perPageNum">
										<option value="5">정렬개수</option>
										<option value="3">3개씩</option>
										<option value="5">5개씩</option>
										<option value="7">7개씩</option>
									</select>
									<!-- search bar -->
									<select class="form-control col-md-3" name="searchType" id="searchType">
										<option value="gubun">검색구분</option>
										<option value="i">아이디</option>
										<option value="n">이름</option>
										<option value="a">별명</option>
									</select> 
									<input id="searchKeyWord" class="form-control" type="text" name="keyword" placeholder="검색어를 입력하세요." value="">
									<span class="input-group-append">
										<button class="btn btn-primary" type="button" id="searchBtn" data-card-widget="search">
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
												<th>이름</th>
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

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
	
	</div>
	<!-- ./wrapper -->

	

</body>
</html>







