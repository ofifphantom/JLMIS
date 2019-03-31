<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>测试用食品后台管理系统</title>

		<link href="${pageContext.request.contextPath}/junlin/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/junlin/css/ace.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/junlin/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/junlin/css/ace-skins.min.css" />
		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="${pageContext.request.contextPath}/junlin/css/ace-ie.min.css" />
		<![endif]-->
		<link href="${pageContext.request.contextPath}/junlin/css/mine/all.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/junlin/css/mine/index.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/junlin/css/mine/sudoku.css" rel="stylesheet" />

		<script src="${pageContext.request.contextPath}/junlin/js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript">
			if("ontouchend" in document)
				document.write("<script src='../js/jquery.mobile.custom.min.js'>" +
					"<" + "script>");
		</script>
		<script src="${pageContext.request.contextPath}/junlin/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/junlin/js/layer/layer.js" type="text/javascript"></script>
		<!--[if lt IE 9]>
		<script src="${pageContext.request.contextPath}/junlin/js/html5shiv.js"></script>
		<script src="${pageContext.request.contextPath}/junlin/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>
		<div class="navbar navbar-default" id="navbar">
			<script type="text/javascript">
				try {
					ace.settings.check('navbar', 'fixed')
				} catch(e) {}
			</script>
			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<ul>
							<li>
								<img alt="Brand" src="${pageContext.request.contextPath}/junlin/img/logo.png" />
							</li>
							<li style="margin-top: 5px;">
								<span>食品后台管理系统</span>
							</li>
							<li class="clearfix"></li>
						</ul>
					</a>
				</div>
				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li>
							<a href="login.html" id="quit" role="button" class="btn">退出
							</a>
						</li>
						<li>
							<a href="" role="button" class="btn">修改密码
							</a>
						</li>
						<li>
							<span data-toggle="dropdown" href="#" class="dropdown-toggle">
								<span>admin<shiro:principal/>	/	超级管理员</span>
							</span>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="sudoku">
			<a href="index.html">
				<div class="box text-center">
					<div class="img-box">
						<img class="img1" src="${pageContext.request.contextPath}/junlin/img/mis01.png" />
					</div>
					<span class="title">MIS后台</span>
				</div>
			</a>
			<a href="index.html">
				<div class="box text-center">
					<div class="img-box">
						<img class="img2" src="${pageContext.request.contextPath}/junlin/img/buy01.png" />
					</div>
					<span class="title">购销存</span>
				</div>
			</a>
			<a href="index.html">
				<div class="box text-center">
					<div class="img-box">
						<img class="img3" src="${pageContext.request.contextPath}/junlin/img/use01.png" />
					</div>
					<span class="title">客服系统</span>
				</div>
			</a>
			<div class="clearfix"></div>
		</div>
	</body>
	<script>
		$(".box").hover(function() {
			var text = $(this).find(".title").text();
			$(this).find(".title").addClass("title1");
			$(this).css({
				"background": "url(${pageContext.request.contextPath}/junlin/img/sdoukenbg2.png)"
			})
			if(text == "MIS后台") {
				$(".img1").attr("src", "${pageContext.request.contextPath}/junlin/img/mis02.png")
			}
			if(text == "购销存") {
				$(".img2").attr("src", "${pageContext.request.contextPath}/junlin/img/buy02.png")
			}
			if(text == "客服系统") {
				$(".img3").attr("src", "${pageContext.request.contextPath}/junlin/img/use02.png")
			}
		});
		$(".box").mouseleave(function() {
			var text = $(this).find(".title").text();
			$(this).find(".title").removeClass("title1");
			$(this).css({
				"background": "url(${pageContext.request.contextPath}/junlin/img/sdoukenbg.png)"
			})
			if(text == "MIS后台") {
				$(".img1").attr("src", "${pageContext.request.contextPath}/junlin/img/mis01.png")
			}
			if(text == "购销存") {
				$(".img2").attr("src", "${pageContext.request.contextPath}/junlin/img/buy01.png")
			}
			if(text == "客服系统") {
				$(".img3").attr("src", "${pageContext.request.contextPath}/junlin/img/use01.png")
			}
		})
	</script>

</html>