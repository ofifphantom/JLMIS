<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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

		<script src="${pageContext.request.contextPath}/junlin/js/ace-extra.min.js"></script>
		<!--[if lt IE 9]>
		<script src="${pageContext.request.contextPath}/junlin/js/html5shiv.js"></script>
		<script src="${pageContext.request.contextPath}/junlin/js/respond.min.js"></script>
		<![endif]-->
		<script src="${pageContext.request.contextPath}/junlin/js/jquery-1.10.2.min.js"></script>

		<script type="text/javascript">
			if("ontouchend" in document)
				document.write("<script src='${pageContext.request.contextPath}/junlin/js/jquery.mobile.custom.min.js'>" +
					"<" + "script>");
		</script>
		<script src="${pageContext.request.contextPath}/junlin/js/bootstrap.min.js"></script>
		<!--自动补全插件-->
		<script src="${pageContext.request.contextPath}/junlin/js/typeahead-bs2.min.js"></script>
		<!--[if lte IE 8]>
		  <script src="${pageContext.request.contextPath}/junlin/js/excanvas.min.js"></script>
		<![endif]-->
		<script src="${pageContext.request.contextPath}/junlin/js/ace-elements.min.js"></script>
		<script src="${pageContext.request.contextPath}/junlin/js/ace.min.js"></script>
		<script src="${pageContext.request.contextPath}/junlin/js/layer/layer.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/junlin/js/laydate/laydate.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/junlin/script/public.js" type="text/javascript"></script>
		<!--menu.js用于动态加载左侧导航栏-->
		<!--<script src="${pageContext.request.contextPath}/junlin/js/menu.js"></script>-->

		<!--引入JQuery EasyUI  核心UI 文件 CSS-->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/junlin/js/easyui-1.3.6/themes/default/easyui.css">
		<!--引入EasyUI图标文件-->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/junlin/js/easyui-1.3.6/themes/icon.css">
		<!--引入JQuery 核心库-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/junlin/js/easyui-1.3.6/jquery.min.js"></script>
		<!--引入JQuery EasyUI  核心库 1.3.6版本-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/junlin/js/easyui-1.3.6/jquery.easyui.min.js"></script>
		<!--进入 EasyUI中文提示信息-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/junlin/js/easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>
		<style>
			#sidebar {
				width: 190px;
				padding-top: 20px;
				
				
			}
			#sidebar ul li>div{
			padding-left:15px;
			}
			.tree-node {
				height: 40px;
				padding-top: 11px;
			}
		</style>

	</head>
<%
if(session.getAttribute("loginName")==null){
	response.sendRedirect("login.jsp");
}

%>
	<body>

		<div class="navbar navbar-default" id="navbar">
			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<ul>
							<li>
								<img alt="Brand" src="${pageContext.request.contextPath}/junlin/img/logo.png" style="height: 45px;margin-right: 10px;"/>
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
							<a id="quit" role="button" class="btn" href="javascript:exit()" >退出
							</a>
						</li>
						<li>
							<a href="javascript:modifyPwd()" role="button" class="btn">修改密码
							</a>
						</li>
						<li>
							<span><%= session.getAttribute("loginName")%>	/	<%= session.getAttribute("userName")%>	/	<%= session.getAttribute("userGroup")%></span>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="main-container" id="main-container">
			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>
				<div class="sidebar" id="sidebar">
					<!-- #sidebar-shortcuts -->
					<ul class="easyui-tree" id="nav_list">

					</ul>
					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>
					<script type="text/javascript">
						try {
							ace.settings.check('sidebar', 'collapsed')
						} catch(e) {}
					</script>
				</div>
				<div class="main-content">
					<iframe id="iframe" style="border: 0; width: 100%;background-color: #f7f6f6!important;" name="iframe" frameborder="0" src=""></iframe>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		
	</body>
	<script>
	var url="first.html";
	var obj0;
	<%-- $(function(){
		setInterval(function(){
			$.ajax({
				url: "<%=request.getContextPath() %>/checkSessionIsExist",
				type: "get",
				dataType: "json",
				async: false,
				cache: false,
				traditional: true,
				success: function(data) {
					console.log(data.success)
					if(data.success==false){
						alert(data.success)
						window.location.reload();
					}
				}
			});
		},1000);
	})
	 --%>
	

		jQuery(document).ready(function() {
			$.each($(".submenu"), function() {
				var $aobjs = $(this).children("li");
				var rowCount = $aobjs.size();
				var divHeigth = $(this).height();
				$aobjs.height(divHeigth / rowCount);
			});
			//初始化宽度、高度    
			$("#main-container").height($(window).height() - 75);
			$("#iframe").height($(window).height() - 81);
			$(".sidebar").height($(window).height() - 95);
			var thisHeight = $("#nav_list").height($(window).outerHeight() - 124);
			//当文档窗口发生改变时 触发  
			$(window).resize(function() {
				$("#main-container").height($(window).height() - 75);
				$("#iframe").height($(window).height() - 81);
				$(".sidebar").height($(window).height() - 95);
				var thisHeight = $("#nav_list").height($(window).outerHeight() - 124);
				$("#iframe").attr("src", url).ready();
				if( $(window).width() > 1180 ) {
					console.log(0)
					$("#search_Classification_span").css({
						"margin-left":"38px"
					});
				}else{
					console.log(1)
					$("#search_Classification_span").css({
						"margin-left":"42px"
					});
				}
			});

		});
		/*********************点击事件*********************/

		$(document).ready(function() {
			$('#Exit_system').on('click', function() {
				layer.confirm('是否确定退出系统？', {
					btn: ['是', '否'], //按钮
					icon: 2,
				}, function() {
					location.href = "<%=request.getContextPath() %>/com/tyzd/tenement/loginMain/logout1";
				});
			});
		})

		$("#nav_list").tree({
				url: "<%=request.getContextPath() %>/backgroundManagement/background/userManager/selectMenuById?id="+${userId},/* null *///获取远程数据URL
 				/* data: [
				{
					"id": 1,
					"text": "后台配置",
					"state": "open",
					"children": [
					{
						"id":6,
						"text": "分类配置",
						"state": "open",
						"children": [
						{
							"id": 4,
							"text": "一级分类",
							"checked":true,
							"iconCls": "icon-no",
							"attributes": {
								"name": "junlin/mis_jsp/backgroundConfiguration/classification/classificationone.jsp"
							},
						}, 
						{
							"id": 5,
							"text": "二级分类",
							"iconCls": "icon-no",
							"attributes": {
								"name": "junlin/mis_jsp/backgroundConfiguration/classification/classificationtwo.jsp"
							},
						}
						]
					}, 
					{
						"id": 7,
						"text": "商品配置",
						"iconCls": "icon-no",
						"attributes": {
							"name": "junlin/mis_jsp/backgroundConfiguration/goods/goods.jsp"
						},

					}, 
					{
						"id": 8,
						"text": "活动配置",
						"iconCls": "icon-no",
						"state": "closed",
						"children":[
						{
							"id": 9,
							"text": "优惠券",
							"iconCls": "icon-no",
							"attributes": {
								"name": "junlin/mis_jsp/backgroundConfiguration/activity/coupon.jsp"
							}
						},
						{
							"id": 10,
							"text": "活动列表",
							"iconCls": "icon-no",
							"attributes": {
								"name": "junlin/mis_jsp/backgroundConfiguration/activity/activity.jsp"
							}
						},
						{
							"id": 11,
							"text": "活动审核",
							"iconCls": "icon-no",
							"attributes": {
								"name": "junlin/mis_jsp/backgroundConfiguration/activity/activityCheck.jsp"
							},
						}
						]
					},
					{
						"id": 12,
						"text": "广告配置",
						"iconCls": "icon-no",
						"state": "closed",
						"children":[
						{
							"id": 29,
							"text": "开屏广告",
							"iconCls": "icon-no",
							"attributes": {
								"name": "junlin/mis_jsp/backgroundConfiguration/advertisement/bannerScreen.jsp"
							}
						},
						{
							"id": 30,
							"text": "首页广告",
							"iconCls": "icon-no",
							"attributes": {
								"name": "junlin/mis_jsp/backgroundConfiguration/advertisement/bannerHome.jsp"
							}
						},
						{
							"id": 31,
							"text": "分类广告",
							"iconCls": "icon-no",
							"attributes": {
								"name": "junlin/mis_jsp/backgroundConfiguration/advertisement/bannerCategories.jsp"
							},
						},
						{
							"id": 32,
							"text": "限时抢购",
							"iconCls": "icon-no",
							"attributes": {
								"name": "junlin/mis_jsp/backgroundConfiguration/advertisement/flashSale.jsp"
							},
						},
						{
							"id": 32,
							"text": "热门分类",
							"iconCls": "icon-no",
							"attributes": {
								"name": "junlin/mis_jsp/backgroundConfiguration/advertisement/hotCategories.jsp"
							},
						}
						]
					}, 
					{
						"id": 28,
						"text": "包邮配置",
						"iconCls": "icon-no",
						"attributes": {
							"name": "junlin/mis_jsp/backgroundConfiguration/postage/postage.jsp"
						},

					}
					]
				}, 
				{
					"id": 3,
					"text": "VIP管理",
					"state": "closed",
					"children": [{
						"id":13,
						"text": "VIP管理",
						"iconCls": "icon-no",
						"attributes": {
								"name": "junlin/mis_jsp/vipManagement/vip.jsp"
							},
					}, {
						"id":14,
						"text": "待添加列表",
						"iconCls": "icon-no",
						"attributes": {
								"name": "junlin/mis_jsp/vipManagement/vipFauture.jsp"
							},
					}]
				},
				{
					"id":16,
					"text": "订单管理",
					"state": "closed",
					"children":[{
						"id":33,
						"text":"基础订单管理",
						"iconCls": "icon-no",
						"attributes": {
							"name": "junlin/mis_jsp/orderManagement/order/searchOrder.jsp"
						},
					},{
						"id":34,
						"text":"线下支付审核",
						"iconCls": "icon-no",
						"attributes": {
							"name": "junlin/mis_jsp/orderManagement/order/pendingAudit.jsp"
						},
					},{
						"id":35,
						"text":"线下支付确认",
						"iconCls": "icon-no",
						"attributes": {
							"name": "junlin/mis_jsp/orderManagement/order/pendingConfirmed.jsp"
						},
					},{
						"id":36,
						"text":"待发货订单",
						"iconCls": "icon-no",
						"attributes": {
							"name": "junlin/mis_jsp/orderManagement/order/pendingSendOutGoods.jsp"
						},
					},{
						"id":37,
						"text":"售后服务",
						"iconCls": "icon-no",
						"attributes": {
							"name": "junlin/mis_jsp/orderManagement/order/customerService.jsp"
						},
					},
					],
				
				},{
					"id":18,
					"text":"增票管理",
					"state": "closed",
					"children": [ {
						"id":21,
						"text": "增票审核",
						"iconCls": "icon-no",
						"attributes": {
								"name": "junlin/mis_jsp/ticketIncreaseManagement/invoice/invoice.jsp"
							},
					}]
				},
				{
					"id":18,
					"text":"后台管理",
					"state": "closed",
					"children": [{
						"id":20,
						"text": "用户",
						"iconCls": "icon-no",
						"attributes": {
							"name": "junlin/mis_jsp/backgroundManagement/background/user.jsp"
						},
					}, {
						"id":21,
						"text": "操作日志",
						"iconCls": "icon-no",
						"attributes": {
								"name": "junlin/mis_jsp/backgroundManagement/background/log.jsp"
							},
					}]
				},
				{
					"id":24,
					"text":"报表系统",
					"state": "closed",
					"children":[{
						"id":25,
						"text": "综合检索",
						"iconCls": "icon-no",
						"attributes": {
						"name": "backgroundManagement/background/form/Search"
						},
					},
					{
						"id":26,
						"text": "订单报表",
						"iconCls": "icon-no",
						"attributes": {
						"name": "backgroundManagement/background/form/Order"
						},
					},
					{
						"id":27,
						"text": "商品销量报表",
						"iconCls": "icon-no",
						"attributes": {
						"name": "backgroundManagement/background/form/Goods"
						},
					}
					]
				}
				
				], //节点数据加载  */
				lines: false,
				checkbox: false,
				onlyLeafCheck: false, //定义是否只在末节点之前显示复选框
				dnd: false, //定义是否启动拖拽功能
				onClick: function(node) {
					var urlobj = node.attributes;
					if(urlobj != undefined) {
						for(var Key in urlobj) {
							url = urlobj[Key];
							console.log("111111111111111111:",url);
							$("#iframe").attr("src", url).ready();
						}
					}

				},
				onLoadSuccess:function(node, param){
//					console.log(param);
					//var aa=getfirstChildren(param);
					getfirstChildren(param);
					var urlobj = obj0.attributes;
					if(urlobj != undefined) {
						for(var Key in urlobj) {
							url = urlobj[Key]; 
							$("#iframe").attr("src", url).ready();
						}
					}
					/* makeTreeOpen(param); */
				},
				onLoadError: function(arguments) {
					console.log(arguments);
				}
			})
			
			function getChildren(obj){
			return obj[0].children;
			}
			function getfirstChildren(obj){
		
				if(getChildren(obj)!=undefined){
					obj0=getChildren(obj);
					getfirstChildren(obj0);
				}else{
					obj0=obj[0];
					var node=$("#nav_list").tree('find', obj0.id-0);
					$('#nav_list').tree('select', node.target);
				}
				//return obj;
			} 
			
			function OpenNode(nodeId){
				var node=$("#nav_list").tree('find', nodeId);
				$('#nav_list').tree('expand', node.target);
				
			}
			function makeTreeOpen(arr){
				arr=arr[0];
				OpenNode(arr["id"]-0);
				var res=getChildren(arr);
				if(res!=undefined){
					makeTreeOpen(res);
				}
			}
			
			function exit(){
				publicMessageLayer("退出", function() {
					 location.href="<%=request.getContextPath()%>/login";			
				});
		}
			
			function modifyPwd(){
				$("#iframe").attr("src", "<%=request.getContextPath()%>/junlin/mis_jsp/backgroundManagement/background/changePwd.jsp").ready();
			}
			
			
			
	</script>
</html>