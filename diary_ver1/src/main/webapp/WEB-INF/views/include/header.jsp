<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!--  jstl의 core 기능 사용을 위한 태그 라이브러릴 설정  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring MVC Project</title>
<!--  화면 크기 설정 
너비는 화면에 가득 차게 기본 비율은 1배 최대 비율은 1배 확대 축소 안되게 설정 
모바일 페이지에서 주로 설정  -->
<meta
		content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
		name='viewport'>
<!--  부트 스트랩 적용을 위한 css 설정  -->		
<link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"
		type="text/css" />

<!--  IE9 버전 이하의 브라우저에서 HTML5를 적용하기 위한 설정  -->		
<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.comrespond/1.4.2respond.min.js"></script>
<![endif ]-->
<style>
		*{
				padding-left:5px;
		}
		img{
				padding:0px;
		}
</style>

</head>
<!--  jquery 설정  -->
<script src="/resources/jquery/jquery.js"></script>

<!-- class 와 role 속성을 이용해서 부트스트랩 적용  -->
<body class="skin-blue sidebar-mini">
			<div class="wrapper">
					<header class="main-header">
						 <div class="page-header">
								<h1>My Diary-Board </h1>
						</div>
           			</header>
           </div>
			<aside class="main-sidebar">
					<section class="sidebar">
							<ul class="nav nav-tabs">
									<li role="presentation" class="active"><a href="#">메인</a></li>
									<li role="presentation"><a href="/board/list">목록보기</a></li>
									
									<!-- 로그인이 되지 않은 경우  -->
									<c:if test="${user == null}">
										<li role="presentation"><a href="user/join">회원가입</a></li>
									</c:if>
									<c:if test="${user != null}">
									<li role="presentation"><a href="/board/write">게시물 쓰기</a></li>
										<li role="presentation">
												<span class="badge">
														<img src="/userimage/${user.image}" 
														  width="30"  height="30" />
												</span>
											     <a href="#">${user.nickname}님 로그인</a>
										</li>	
									</c:if>
							</ul>
					</section>
			</aside>
			<div>