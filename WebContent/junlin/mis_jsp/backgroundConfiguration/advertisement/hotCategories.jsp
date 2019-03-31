<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%
	String path = request.getContextPath();
	String basePath = path + "/";
%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="utf-8" />
		<title>热门分类</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		
		
		<script src="${pageContext.request.contextPath}/junlin/js/photoloads.js" type="text/javascript"></script>


		<style>
			.goods-list ul {
				padding: 0;
			}
			
			.goods-list ul li {
				width: 20%;
				display: inline-block;
				cursor: default;
				text-decoration: underline;
			}
			
			.big-photo {
				margin-top: 10px;
			}
			
			.big-photo img {
				box-shadow: 0px 1px 5px rgba(0, 0, 0, 0.15);
				width:90px;
				height : 90px;
			}
			
			.search-div {
				padding: 25px 0;
				margin-bottom: 20px;
				box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.15);
			}
			
			.col-xs-3 {
				padding: 0;
				line-height: 35px;
			}
			
			#classificationTwo_sale,
			#classificationTwo_new,
			#classificationTwo_presale,
			#classificationTwo_hot {
				border-left: 0;
				border-right: 0;
			}
			
			.form-group {
				margin-bottom: 0;
			}
			
			.search-div .jl-btn-importent {
				margin-left: 40px;
			}
			
			.delgoods-div {
				display: inline-block;
				color: #ff7e7e;
			}
			.tip{
			margin:10px
			}
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">热门分类</h4>
					<form id="inserOrUpdateDiv">
					<div class="search-div clearfix">
						<div class="container-fluid">
							<div class="row">
								<div class="col-xs-10">
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">分类名称：</label>
										<div class="col-xs-10">
											火热促销
										</div>
									</div>
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">商品列表：</label>
										<div class="col-xs-10 goods-list">
											<ul class="col-xs-12" id="goodsul_sale">
											</ul>
											<div class="col-xs-3">
												<select id="classificationOne_sale" class="form-control" data-state="3">
													<option value="-1" selected="selected">--一级分类--</option>
													<!-- <option data-id="1" value="第一大类">第一大类</option>
													<option data-id="2" value="第二大类">第二大类</option>
													<option data-id="3" value="第三大类">第三大类</option>
													<option data-id="4" value="第四大类">第四大类</option>
													<option data-id="5" value="第五大类">第五大类</option> -->
												</select>
											</div>
											<div class="col-xs-3">
												<select id="classificationTwo_sale" class="form-control" data-state="2">
													<option value="-1" selected="selected">--二级分类--</option>
													<!-- <option data-id="1" value="粮食">粮食</option>
													<option data-id="2" value="好吃的">好吃的</option>
													<option data-id="3" value="化妆品">化妆品</option>
													<option data-id="4" value="电器">电器</option> -->
												</select>
											</div>
											<div class="col-xs-3">
												<select id="goods_sale" class="form-control" data-state="1">
													<option value="-1" selected="selected">--商品名称--</option>
													<!-- <option data-id="1" value="九多肉多">九多肉多</option>
													<option data-id="2" value="棉花糖">棉花糖</option>
													<option data-id="3" value="日历">日历</option>
													<option data-id="4" value="笔记本">笔记本</option> -->
												</select>
											</div>
											<div class="col-xs-3">
												<button type="button" class="btncss jl-btn-importent" id="goodsAddBtn_sale">新增</button>
												<input type="text" id="goodsidStr_sale" name="goodsidStr_sale" class="hidden" />
												<input type="text" id="stateStr_sale" name="stateStr_sale" class="hidden" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-2">
									<div class="big-photo">
										<div id="preview0">
											<img id="imghead0" border="0" src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg" width="90" height="90" onclick="$('#previewImg0').click();">
											
										</div>
										<input type="file" onchange="previewImage(this,0)" style="display: none;" id="previewImg0" name="previewImg0" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg">
										<input type="text" id="saleFlag" name="saleFlag" value="0" class="hidden" />
										<!--<input id="uploaderInput" class="uploader__input" style="display: none;" type="file" accept="" multiple="">-->
									</div>
									<div class="tip">注：上传的图片大小应小于2M,图片尺寸建议为164*164（单位：像素）</div>
								</div>
							</div>
						</div>
					</div>

					<div class="search-div clearfix">
						<div class="container-fluid">
							<div class="row">
								<div class="col-xs-10">
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">分类名称：</label>
										<div class="col-xs-10">
											新品上架
										</div>
									</div>
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">商品列表：</label>
										<div class="col-xs-10 goods-list">
											<ul class="col-xs-12" id="goodsul_new">
											</ul>
											<div class="col-xs-3">
												<select id="classificationOne_new" class="form-control" data-state="3">
													<option value="-1" selected="selected">--一级分类--</option>
													<!-- <option data-id="1" value="第一大类">第一大类</option>
													<option data-id="2" value="第二大类">第二大类</option>
													<option data-id="3" value="第三大类">第三大类</option>
													<option data-id="4" value="第四大类">第四大类</option>
													<option data-id="5" value="第五大类">第五大类</option> -->
												</select>
											</div>
											<div class="col-xs-3">
												<!-- <select id="classificationTwo_new" class="form-control" data-state="2">
													<option value="-1" selected="selected">--二级分类--</option>
													<option data-id="1" value="粮食">粮食</option>
													<option data-id="2" value="好吃的">好吃的</option>
													<option data-id="3" value="化妆品">化妆品</option>
													<option data-id="4" value="电器">电器</option>
												</select> -->
											</div>
											<div class="col-xs-3">
												<!-- <select id="goods_new" class="form-control" data-state="1">
													<option value="-1" selected="selected">--商品名称--</option>
													<option data-id="1" value="九多肉多">九多肉多</option>
													<option data-id="2" value="棉花糖">棉花糖</option>
													<option data-id="3" value="日历">日历</option>
													<option data-id="4" value="笔记本">笔记本</option>
												</select> -->
											</div>
											<div class="col-xs-3">
												<button type="button" class="btncss jl-btn-importent" id="goodsAddBtn_new">新增</button>
												<input type="text" id="goodsidStr_new" name="goodsidStr_new" class="hidden" />
												<input type="text" id="stateStr_new" name="stateStr_new" class="hidden" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-2" style="display:none;">
									<div class="big-photo">
										<div id="preview1">
											<img id="imghead1" border="0" src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg" width="90" height="90" onclick="$('#previewImg1').click();">
										</div>
										<input type="file" onchange="previewImage(this,1)" style="display: none;" id="previewImg1" name="previewImg1" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg">
										<input type="text" id="newFlag" name="newFlag" value="0" class="hidden" />
										<!--<input id="uploaderInput" class="uploader__input" style="display: none;" type="file" accept="" multiple="">-->
									</div>
									<div class="tip">注：上传的图片大小应小于2M,图片尺寸建议为164*164（单位：像素）</div>
								</div>
							</div>
						</div>
					</div>

					<div class="search-div clearfix">
						<div class="container-fluid">
							<div class="row">
								<div class="col-xs-10">
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">分类名称：</label>
										<div class="col-xs-10">
											测试用热卖
										</div>
									</div>
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">商品列表：</label>
										<div class="col-xs-10 goods-list">
											<ul class="col-xs-12" id="goodsul_hot">
											</ul>
											<div class="col-xs-3">
												<select id="classificationOne_hot" class="form-control" data-state="3">
													<option value="-1" selected="selected">--一级分类--</option>
													<!-- <option data-id="1" value="第一大类">第一大类</option>
													<option data-id="2" value="第二大类">第二大类</option>
													<option data-id="3" value="第三大类">第三大类</option>
													<option data-id="4" value="第四大类">第四大类</option>
													<option data-id="5" value="第五大类">第五大类</option> -->
												</select>
											</div>
											<div class="col-xs-3">
												<select id="classificationTwo_hot" class="form-control" data-state="2">
													<option value="-1" selected="selected">--二级分类--</option>
													<!-- <option data-id="1" value="粮食">粮食</option>
													<option data-id="2" value="好吃的">好吃的</option>
													<option data-id="3" value="化妆品">化妆品</option>
													<option data-id="4" value="电器">电器</option> -->
												</select>
											</div>
											<div class="col-xs-3">
												<select id="goods_hot" class="form-control" data-state="1">
													<option value="-1" selected="selected">--商品名称--</option>
													<!-- <option data-id="1" value="九多肉多">九多肉多</option>
													<option data-id="2" value="棉花糖">棉花糖</option>
													<option data-id="3" value="日历">日历</option>
													<option data-id="4" value="笔记本">笔记本</option> -->
												</select>
											</div>
											<div class="col-xs-3">
												<button type="button" class="btncss jl-btn-importent" id="goodsAddBtn_hot">新增</button>
												<input type="text" id="goodsidStr_hot" name="goodsidStr_hot" class="hidden" />
												<input type="text" id="stateStr_hot" name="stateStr_hot" class="hidden" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-2">
									<div class="big-photo">
										<div id="preview2">
											<img id="imghead2" border="0" src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg" width="90" height="90" onclick="$('#previewImg2').click();">
											
										</div>
										<input type="file" onchange="previewImage(this,2)" style="display: none;" id="previewImg2" name="previewImg2" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg">
										<input type="text" id="hotFlag" name="hotFlag" value="0" class="hidden" />
										<!--<input id="uploaderInput" class="uploader__input" style="display: none;" type="file" accept="" multiple="">-->
									</div>
									<div class="tip">注：上传的图片大小应小于2M,图片尺寸建议为164*164（单位：像素）</div>
								</div>
							</div>
						</div>
					</div>

					<div class="search-div clearfix">
						<div class="container-fluid">
							<div class="row">
								<div class="col-xs-10">
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">分类名称：</label>
										<div class="col-xs-10">
											爆品预售
										</div>
									</div>
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">商品列表：</label>
										<div class="col-xs-10 goods-list">
											<ul class="col-xs-12" id="goodsul_presale">
											</ul>
											<div class="col-xs-3">
												<select id="classificationOne_presale" class="form-control" data-state="3">
													<option value="-1" selected="selected">--一级分类--</option>
													<!-- <option data-id="1" value="第一大类">第一大类</option>
													<option data-id="2" value="第二大类">第二大类</option>
													<option data-id="3" value="第三大类">第三大类</option>
													<option data-id="4" value="第四大类">第四大类</option>
													<option data-id="5" value="第五大类">第五大类</option> -->
												</select>
											</div>
											<div class="col-xs-3">
												<select id="classificationTwo_presale" class="form-control" data-state="2">
													<option value="-1" selected="selected">--二级分类--</option>
												<!-- 	<option data-id="1" value="粮食">粮食</option>
													<option data-id="2" value="好吃的">好吃的</option>
													<option data-id="3" value="化妆品">化妆品</option>
													<option data-id="4" value="电器">电器</option> -->
												</select>
											</div>
											<div class="col-xs-3">
												<select id="goods_presale" class="form-control" data-state="1">
													<option value="-1" selected="selected">--商品名称--</option>
													<!-- <option data-id="1" value="九多肉多">九多肉多</option>
													<option data-id="2" value="棉花糖">棉花糖</option>
													<option data-id="3" value="日历">日历</option>
													<option data-id="4" value="笔记本">笔记本</option> -->
												</select>
											</div>

											<div class="col-xs-3">
												<button type="button" class="btncss jl-btn-importent" id="goodsAddBtn_presale">新增</button>
												<input type="text" id="goodsidStr_presale" name="goodsidStr_presale" class="hidden" />
												<input type="text" id="stateStr_presale" name ="stateStr_presale" class="hidden" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-2">
									<div class="big-photo">
										<div id="preview3">
											<img id="imghead3" border="0" src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg" width="90" height="90" onclick="$('#previewImg3').click();">
											
										</div>
										<input type="file" onchange="previewImage(this,3)" style="display: none;" id="previewImg3" name="previewImg3" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg">
										<input type="text" id="presaleFlag" name="presaleFlag" value="0" class="hidden" />
										<!--<input id="uploaderInput" class="uploader__input" style="display: none;" type="file" accept="" multiple="">-->
									</div>
									
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
					<div class="col-xs-12 tip">注：<br/>1.上传的图片大小应小于2M,图片尺寸建议为164*164（单位：像素）<br/>该页面所有字段均为必填</div>
					
					</div>

					<div class="text-center">
						<input type="button" class="btncss jl-btn-importent" onclick="hotCategoriesEffect()" value="生效" />
						<input type="button" class="btncss jl-btn-defult" value="取消" onclick="refresh()" />
					</div>
			</form>
				</div>
			</div>
		</div>

	</body>

	<script>
	
	var selectData;
	var index = -1;
	jQuery(function($) {
			
		<%-- 	//下拉列表赋值   一级分类
			$.ajax({
				url :'<%=basePath%>backgroundConfiguration/classification/classification/getAllOneClassification' ,
				type : "POST",
				dataType : "json",
				success : function(data) {
					for ( var i = 0; i < data.length; i++) {
						var option = $("<option data-id='"+data[i].id+"' value='"+data[i].name+"'>"+ data[i].name+"</option>");
						$("#classificationOne_sale").append(option);
						option = $("<option data-id='"+data[i].id+"' value='"+data[i].name+"'>"+ data[i].name+"</option>");
						$("#classificationOne_new").append(option);
						option = $("<option data-id='"+data[i].id+"' value='"+data[i].name+"'>"+ data[i].name+"</option>");
						$("#classificationOne_hot").append(option);
						option = $("<option data-id='"+data[i].id+"' value='"+data[i].name+"'>"+ data[i].name+"</option>");
						$("#classificationOne_presale").append(option);
					}
				}
			});  --%>
			
			/* 一级分类下拉框赋值 */
			$.ajax({
				url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/getClassificationByParentIdForNotPresell' ,
				type : "POST",
				dataType : "json",
				data: {
					//"parentId" : 0
				},
				success : function(data) {
					selectData = data;
					for ( var i = 0; i < data.length; i++) {
						var option = $("<option data-index="+i+" data-id='"+data[i].id+"' value='"+data[i].name+"'>"+ data[i].name+"</option>");
						$("#classificationOne_sale").append(option);
						option = $("<option data-index="+i+" data-id='"+data[i].id+"' value='"+data[i].name+"'>"+ data[i].name+"</option>");
						$("#classificationOne_new").append(option);
						option = $("<option data-index="+i+" data-id='"+data[i].id+"' value='"+data[i].name+"'>"+ data[i].name+"</option>");
						$("#classificationOne_hot").append(option);
						option = $("<option data-index="+i+" data-id='"+data[i].id+"' value='"+data[i].name+"'>"+ data[i].name+"</option>");
						$("#classificationOne_presale").append(option);
					}
				}
			});
			
	
			//页面赋值
			//获取数据显示在界面中
			$.ajax({
				url: '<%=basePath%>backgroundConfiguration/advertisement/advertisementInformation/getMsgForPage',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: {
					
				},

				success: function(data) {
					
					if(data!=null){
						//初始化图片
						if(data.saleFile!="" && data.saleFile!=null){
							$("#imghead0").attr("src",'<%=basePath%>'+data.saleFile);
							$("#saleFlag").val("1");
							
						}else{
							$("#imghead0").attr("src",'<%=basePath%>junlin/img/photo_icon.jpg');
						}
						if(data.newFile!="" && data.newFile!=null){
							$("#imghead1").attr("src",'<%=basePath%>'+data.newFile);
							$("#newFlag").val("1");
						}else{
							$("#imghead1").attr("src",'<%=basePath%>junlin/img/photo_icon.jpg');
						}
						if(data.hotFile!="" && data.hotFile!=null){
							$("#imghead2").attr("src",'<%=basePath%>'+data.hotFile);
							$("#hotFlag").val("1");
						}else{
							$("#imghead2").attr("src",'<%=basePath%>junlin/img/photo_icon.jpg');
						}
						if(data.presaleFile!=""&&data.presaleFile!=null){
							$("#imghead3").attr("src",'<%=basePath%>'+data.presaleFile);
							$("#presaleFlag").val("1");
						}else{
							$("#imghead3").attr("src",'<%=basePath%>junlin/img/photo_icon.jpg');
							
						}

						
						initGoodsList(data.saleObject,data.newObject,data.hotObject,data.presaleObject);
						
					}
				}
			});
	
	
	
	
	
	
	});
	
	
	/*
	 *初始化页面中的商品列表信息
	 */
	
	function initGoodsList(saleObject,newObject,hotObject,presaleObject){
		checkGoods1_sale=saleObject.checkGoods1;
		checkGoods2_sale=saleObject.checkGoods2;
		checkGoods3_sale=saleObject.checkGoods3;
		
		checkGoods1_new=newObject.checkGoods1;
		checkGoods2_new=newObject.checkGoods2;
		checkGoods3_new=newObject.checkGoods3;
		
		checkGoods1_hot=hotObject.checkGoods1;
		checkGoods2_hot=hotObject.checkGoods2;
		checkGoods3_hot=hotObject.checkGoods3;
		
		checkGoods1_presale=presaleObject.checkGoods1;
		checkGoods2_presale=presaleObject.checkGoods2;
		checkGoods3_presale=presaleObject.checkGoods3;
		
		 $(saleObject.dataJsonArray).each(function(i, dom) {
				// console.log(dom);
				 $goods = '<li data-id="' + dom["id"] + '" data-state="' + dom["state"] + '">' + dom["name"]  + '<div onclick="delgoods(this,\'sale\')" class="delgoods-div"><i class="fa fa-times"></i></div></li>';
				 $("#goodsul_sale").append($goods);
			}); 
		 $(newObject.dataJsonArray).each(function(i, dom) {
				// console.log(dom);
				 $goods = '<li data-id="' + dom["id"] + '" data-state="' + dom["state"] + '">' + dom["name"]  + '<div onclick="delgoods(this,\'new\')" class="delgoods-div"><i class="fa fa-times"></i></div></li>';
				 $("#goodsul_new").append($goods);
			}); 
		 $(hotObject.dataJsonArray).each(function(i, dom) {
				// console.log(dom);
				 $goods = '<li data-id="' + dom["id"] + '" data-state="' + dom["state"] + '">' + dom["name"]  + '<div onclick="delgoods(this,\'hot\')" class="delgoods-div"><i class="fa fa-times"></i></div></li>';
				 $("#goodsul_hot").append($goods);
			}); 
		 $(presaleObject.dataJsonArray).each(function(i, dom) {
				// console.log(dom);
				 $goods = '<li data-id="' + dom["id"] + '" data-state="' + dom["state"] + '">' + dom["name"]  + '<div onclick="delgoods(this,\'presale\')" class="delgoods-div"><i class="fa fa-times"></i></div></li>';
				 $("#goodsul_presale").append($goods);
			}); 
		 
		
	}
	
	
	
	
		/*
		 * 字段声明：火热促销（sale）
		 */
		var goodsIdstr_sale = ""; //商品id字符串
		var goodsStatestr_sale = ""; //商品类型字符创
		var checkGoods3_sale = []; //商品选中——一级分类
		var checkGoods2_sale = []; //商品选中——二级分类
		var checkGoods1_sale = []; //商品选中——商品
		//用于记录"火热促销"中下拉菜单选中值
		var classificationOne_sale = {
			id: "",
			state: "3",
			goodsname: "-1"
		};
		var classificationTwo_sale = {
			id: "",
			state: "2",
			goodsname: "-1"
		};
		var goods_sale = {
			id: "",
			state: "1",
			goodsname: "-1"
		};

		/*
		 * 字段声明：新品上架（new）
		 */
		var goodsIdstr_new = ""; //商品id字符串
		var goodsStatestr_new = ""; //商品类型字符创
		var checkGoods3_new = []; //商品选中——一级分类
		var checkGoods2_new = []; //商品选中——二级分类
		var checkGoods1_new = []; //商品选中——商品
		//用于记录"新品上架"中下拉菜单选中值
		var classificationOne_new = {
			id: "",
			state: "3",
			goodsname: "-1"
		};
		var classificationTwo_new = {
			id: "",
			state: "2",
			goodsname: "-1"
		};
		var goods_new = {
			id: "",
			state: "1",
			goodsname: "-1"
		};

		/*
		 * 字段声明：君霖热卖（hot）
		 */
		var goodsIdstr_hot = ""; //商品id字符串
		var goodsStatestr_hot = ""; //商品类型字符创
		var checkGoods3_hot = []; //商品选中——一级分类
		var checkGoods2_hot = []; //商品选中——二级分类
		var checkGoods1_hot = []; //商品选中——商品
		//用于记录"新品上架"中下拉菜单选中值
		var classificationOne_hot = {
			id: "",
			state: "3",
			goodsname: "-1"
		};
		var classificationTwo_hot = {
			id: "",
			state: "2",
			goodsname: "-1"
		};
		var goods_hot = {
			id: "",
			state: "1",
			goodsname: "-1"
		};

		/*
		 * 字段声明：爆品预售（presale）
		 */
		var goodsIdstr_presale = ""; //商品id字符串
		var goodsStatestr_presale = ""; //商品类型字符创
		var checkGoods3_presale = []; //商品选中——一级分类
		var checkGoods2_presale = []; //商品选中——二级分类
		var checkGoods1_presale = []; //商品选中——商品
		//用于记录"新品上架"中下拉菜单选中值
		var classificationOne_presale = {
			id: "",
			state: "3",
			goodsname: "-1"
		};
		var classificationTwo_presale = {
			id: "",
			state: "2",
			goodsname: "-1"
		};
		var goods_presale = {
			id: "",
			state: "1",
			goodsname: "-1"
		};

		/*
		 * 调用getgoods($select, type)当selectde的触发change事件时获取页面内下拉菜单选中值
		 */
		getgoods($("#classificationOne_sale"), 1);
		getgoods($("#classificationTwo_sale"), 2);
		getgoods($("#goods_sale"), 3);
		getgoods($("#classificationOne_new"), 4);
		getgoods($("#classificationTwo_new"), 5);
		getgoods($("#goods_new"), 6);
		getgoods($("#classificationOne_hot"), 7);
		getgoods($("#classificationTwo_hot"), 8);
		getgoods($("#goods_hot"), 9);
		getgoods($("#classificationOne_presale"), 10);
		getgoods($("#classificationTwo_presale"), 11);
		getgoods($("#goods_presale"), 12);

		/*
		 * 调用appendgoods()添加商品
		 */
		appendgoods($("#goodsAddBtn_sale"), "sale");
		appendgoods($("#goodsAddBtn_new"), "new");
		appendgoods($("#goodsAddBtn_hot"), "hot");
		appendgoods($("#goodsAddBtn_presale"), "presale");

		/*
		 * appendgoods()(基本方法)
		 * 用处：添加商品
		 * 传入参数：$btn(触发事件按钮),categorieStr(模块分类名称)
		 * 
		 */

		function appendgoods($btn, categorieStr) {
			var $goods = '';
			$btn.click(function() {
				$goods = ''
				if(eval("goods_" + categorieStr)["goodsname"] != -1) { //商品
					if(checkgoods(categorieStr, eval("goods_" + categorieStr)["state"])) {
						$goods = '<li data-id="' + eval("goods_" + categorieStr)["id"] + '" data-state="' + eval("goods_" + categorieStr)["state"] + '">' + eval("goods_" + categorieStr)["goodsname"] + '<div onclick="delgoods(this,\'' + categorieStr + '\')" class="delgoods-div"><i class="fa fa-times"></i></div></li>';
						eval("checkGoods1_" + categorieStr).push({
							"goodsid": eval("goods_" + categorieStr)["id"],
							"classificationTwoid": eval("classificationTwo_" + categorieStr)["id"],
							"classificationOneid": eval("classificationOne_" + categorieStr)["id"]
						});
//						console.log(eval("checkGoods1_" + categorieStr));
					} else {
						laywarn("请勿选择重复商品");
					}

				} else if(eval("classificationTwo_" + categorieStr)["goodsname"] != -1) { //第二大类
					if(checkgoods(categorieStr, eval("classificationTwo_" + categorieStr)["state"])) {
						$goods = '<li data-id="' + eval("classificationTwo_" + categorieStr)["id"] + '" data-state="' + eval("classificationTwo_" + categorieStr)["state"] + '">' + eval("classificationTwo_" + categorieStr)["goodsname"] + '<div onclick="delgoods(this,\'' + categorieStr + '\')" class="delgoods-div"><i class="fa fa-times"></i></div></li>';
						eval("checkGoods2_" + categorieStr).push({
							"classificationTwoid": eval("classificationTwo_" + categorieStr)["id"],
							"classificationOneid": eval("classificationOne_" + categorieStr)["id"]
						});
//						console.log(eval("checkGoods2_" + categorieStr));
					} else {
						laywarn("请勿选择重复商品");
					}
				} else if(eval("classificationOne_" + categorieStr)["goodsname"] != -1) {//第一大类
					//alert(0);
					if((categorieStr=='new'&&$("#goodsul_new li").length<5)||categorieStr!='new'){
						if(checkgoods(categorieStr, eval("classificationOne_" + categorieStr)["state"])) {
							$goods = '<li data-id="' + eval("classificationOne_" + categorieStr)["id"] + '" data-state="' + eval("classificationOne_" + categorieStr)["state"] + '">' + eval("classificationOne_" + categorieStr)["goodsname"] + '<div onclick="delgoods(this,\'' + categorieStr + '\')" class="delgoods-div"><i class="fa fa-times"></i></div></li>';
							eval("checkGoods3_" + categorieStr).push({
								"classificationOneid": eval("classificationOne_" + categorieStr)["id"]
							});
							
						} else {
							laywarn("请勿选择重复商品");
							
						}
					}
					//console.log(eval("checkGoods3_" + categorieStr));
				} else { //未选中
					laywarn("请先选择商品。");

				}
				if(categorieStr=='new'&&$("#goodsul_new li").length>=5) laywarn("选择商品数不能大于5个。");
				$("#goodsul_" + categorieStr).append($goods);
				console.log(eval("checkGoods3_" + categorieStr));
			})
		}

		/*
		 * delgoods()(基本方法)
		 * 用处：删除商品
		 * 传入参数：thisbtn(删除按钮),categorieStr(模块分类名称)
		 * 
		 */

		function delgoods(thisbtn, categorieStr) {
			$(thisbtn).parent().remove();
			var num = $(thisbtn).parent().attr("data-state") - 0;
			var obj = {
				"goodsid": eval("goods_" + categorieStr)["id"],
				"classificationTwoid": eval("classificationTwo_" + categorieStr)["id"],
				"classificationOneid": eval("classificationOne_" + categorieStr)["id"]
			};
			switch(num) {
				case 1:
					$(eval("checkGoods1_" + categorieStr)).each(function(i, dom) {
						if(dom["goodsid"] == $(thisbtn).parent().attr("data-id")) {
							eval("checkGoods1_" + categorieStr).splice(i, 1);
						}
					});
					break;
				case 2:
					$(eval("checkGoods2_" + categorieStr)).each(function(i, dom) {
						if(dom["classificationTwoid"] == $(thisbtn).parent().attr("data-id")) {
							eval("checkGoods2_" + categorieStr).splice(i, 1);
						}
					});
					break;
				case 3:
					$(eval("checkGoods3_" + categorieStr)).each(function(i, dom) {
						if(dom["classificationOneid"] == $(thisbtn).parent().attr("data-id")) {
							eval("checkGoods3_" + categorieStr).splice(i, 1);

						}
					});
					break;
			}
		}

		/*
		 * checkgoods()(基本方法)
		 * 用处：检查传入商品是否重复
		 * 传入参数：categorieStr(模块分类名称),goodsstate(商品所属分类)
		 * 返回值 ：返回商品是否重复，重复：false,不重复：true.
		 */

		function checkgoods(categorieStr, goodsstate) {
			//console.log("categorieStr:",categorieStr,"    categorieStr:",goodsstate);
			var res = true;
			goodsstate = goodsstate - 0;
			switch(goodsstate) {
				case 1: //goods
					var obj = {
						"goodsid": eval("goods_" + categorieStr)["id"],
						"classificationTwoid": eval("classificationTwo_" + categorieStr)["id"],
						"classificationOneid": eval("classificationOne_" + categorieStr)["id"]
					};
					$(eval("checkGoods1_" + categorieStr)).each(function(i, dom) {
						if(dom["goodsid"] == eval("goods_" + categorieStr)["id"]) {
							res = false;
						}
					});
					$(eval("checkGoods2_" + categorieStr)).each(function(i, dom) {
						if(dom["classificationTwoid"] == eval("classificationTwo_" + categorieStr)["id"]) {
							res = false;
						}
					});
					$(eval("checkGoods3_" + categorieStr)).each(function(i, dom) {
						if(dom["classificationOneid"] == eval("classificationOne_" + categorieStr)["id"]) {
							res = false;
						}
					});
					break;
				case 2: //classificationTwo
					var obj = {
						"classificationTwoid": eval("classificationTwo_" + categorieStr)["id"],
						"classificationOneid": eval("classificationOne_" + categorieStr)["id"]
					};
					$(eval("checkGoods1_" + categorieStr)).each(function(i, dom) {
						if(dom["classificationTwoid"] == eval("classificationTwo_" + categorieStr)["id"]) {
							res = false;
						}
					});
					$(eval("checkGoods2_" + categorieStr)).each(function(i, dom) {
						if(dom["classificationTwoid"] == eval("classificationTwo_" + categorieStr)["id"]) {
							res = false;
						}
					});
					$(eval("checkGoods3_" + categorieStr)).each(function(i, dom) {
						if(dom["classificationOneid"] == eval("classificationOne_" + categorieStr)["id"]) {
							res = false;
						}
					});
					//console.log(eval("checkGoods2_" + categorieStr));
					break;
				case 3: //classificationOne
					var obj = {
						"classificationOneid": eval("classificationOne_" + categorieStr)["id"]
					};

					$(eval("checkGoods1_" + categorieStr)).each(function(i, dom) {
						if(dom["classificationOneid"] == eval("classificationOne_" + categorieStr)["id"]) {
							res = false;
						}
					});
					$(eval("checkGoods2_" + categorieStr)).each(function(i, dom) {
						if(dom["classificationOneid"] == eval("classificationOne_" + categorieStr)["id"]) {
							res = false;
						}
					});
					$(eval("checkGoods3_" + categorieStr)).each(function(i, dom) {
						if(dom["classificationOneid"] == eval("classificationOne_" + categorieStr)["id"]) {
							res = false;
						}
					});

					break;
			}

			return res;
		}

		/*
		 * countgoods()(基本方法)
		 * 用处：计算商品并将选中商品id变成字符串，传给相应input
		 * 
		 */

		function countgoods() {

			/*初始化*/
			goodsIdstr_sale = "";
			goodsStatestr_sale = "";
			goodsIdstr_new = "";
			goodsStatestr_new = "";
			goodsIdstr_hot = "";
			goodsStatestr_hot = "";
			goodsIdstr_presale = "";
			goodsStatestr_presale = "";

			eachli("sale");
			eachli("new");
			eachli("hot");
			eachli("presale");

			function eachli(type) {
				var goodsStatestr = eval("goodsStatestr_" + type);
				var goodsIdstr = eval("goodsIdstr_" + type);
				$("#goodsul_" + type + " li").each(function() {
					goodsStatestr = goodsStatestr + $(this).attr("data-state") + ",";
					goodsIdstr = goodsIdstr + $(this).attr("data-id") + ",";
				});
				goodsIdstr = goodsIdstr.substring(0, goodsIdstr.length - 1);
				goodsStatestr = goodsStatestr.substring(0, goodsStatestr.length - 1).replace(/3/g, "2");
				$("#goodsidStr_" + type).val(goodsIdstr);
				$("#stateStr_" + type).val(goodsStatestr);
			}

		}

		/*
		 * getgoods($select, type)(基本方法)
		 * 用处：用户获取页面内下拉菜单选中值
		 * 传入参数：selectobj(选定对应select),type(指向相应参数，用于将获取参数付给全局变量，便于使用)
		 * 
		 */
		function getgoods($select, type) {
			$select.change(function() {
				var goodsname = $(this).val();
				var state = $(this).attr("data-state");
				var id = $(this).find("option:selected").attr("data-id");
				var mygoods = {
					"id": id,
					"state": state,
					"goodsname": goodsname
				};
				switch(type) {
					case 1:
						classificationOne_sale = mygoods;
						loadClassificationTwo($select,$("#classificationTwo_sale"),$("#goods_sale"));
						
						classificationTwo_sale = {
								id: "",
								state: "2",
								goodsname: "-1"
							};
							goods_sale = {
								id: "",
								state: "1",
								goodsname: "-1"
							};

						break;
					case 2:
						classificationTwo_sale = mygoods;
						loadGoodsName($select,$("#goods_sale"));
						
						goods_sale = {
								id: "",
								state: "1",
								goodsname: "-1"
							};
						break;
					case 3:
						goods_sale = mygoods;
						break;
					case 4:
						classificationOne_new = mygoods;
						loadClassificationTwo($select,$("#classificationTwo_new"),$("#goods_new"));
						classificationTwo_new = {
								id: "",
								state: "2",
								goodsname: "-1"
							};
						goods_new = {
								id: "",
								state: "1",
								goodsname: "-1"
							};
						break;
					case 5:
						classificationTwo_new = mygoods;
						loadGoodsName($select,$("#goods_new"));
						goods_new = {
								id: "",
								state: "1",
								goodsname: "-1"
							};
						break;
					case 6:
						goods_new = mygoods;
						break;
					case 7:
						classificationOne_hot = mygoods;
						loadClassificationTwo($select,$("#classificationTwo_hot"),$("#goods_hot"));
						classificationTwo_hot = {
								id: "",
								state: "2",
								goodsname: "-1"
							};
						goods_hot = {
								id: "",
								state: "1",
								goodsname: "-1"
							};
						break;
					case 8:
						classificationTwo_hot = mygoods;
						loadGoodsName($select,$("#goods_hot"));
						goods_hot = {
								id: "",
								state: "1",
								goodsname: "-1"
							};
						break;
					case 9:
						goods_hot = mygoods;
						break;
					case 10:
						classificationOne_presale = mygoods;
						loadClassificationTwo($select,$("#classificationTwo_presale"),$("#goods_presale"));
						classificationTwo_presale = {
								id: "",
								state: "2",
								goodsname: "-1"
							};
						goods_presale = {
								id: "",
								state: "1",
								goodsname: "-1"
							};
						break;
					case 11:
						classificationTwo_presale = mygoods;
						loadGoodsName($select,$("#goods_presale"));
						goods_presale = {
								id: "",
								state: "1",
								goodsname: "-1"
							};
						break;
					case 12:
						goods_presale = mygoods;
						break;

				}
			})

		}

		/*
		 * hotCategoriesEffect()(基本方法)
		 * 用处：页面内容提交生效
		 */
		function hotCategoriesEffect() {
			var saleFile=$("#previewImg0").val();
			var saleFlag = $("#saleFlag").val();
			
			if(saleFile!=""){	
				/* 分割之后根据后缀判断图片类型 */
				var splitPicName=saleFile.split(".");
				if(splitPicName[splitPicName.length-1].toUpperCase()!='JPG'&&splitPicName[splitPicName.length-1].toUpperCase()!='JPEG'&&splitPicName[splitPicName.length-1].toUpperCase()!='GIF'&&splitPicName[splitPicName.length-1].toUpperCase()!='PNG'&&splitPicName[splitPicName.length-1].toUpperCase()!='BMP'){
					layer.alert("火热促销上传文件类型不对,只能是图片!", {
						title : '提示框',
						icon : 0,
					});
					return ;
				}else{
					 $("#saleFlag").val("1");
				}
			}
			else if(saleFlag==0){
				laywarn("火热促销广告图片不能为空!");
				return false;
			}
			
			
			var newFile=$("#previewImg1").val();
			var newFlag =  $("#newFlag").val();
			if(newFile!=""){	
				//分割之后根据后缀判断图片类型 
				var splitPicName=newFile.split(".");
				if(splitPicName[splitPicName.length-1].toUpperCase()!='JPG'&&splitPicName[splitPicName.length-1].toUpperCase()!='JPEG'&&splitPicName[splitPicName.length-1].toUpperCase()!='GIF'&&splitPicName[splitPicName.length-1].toUpperCase()!='PNG'&&splitPicName[splitPicName.length-1].toUpperCase()!='BMP'){
					layer.alert("新品上架上传文件类型不对,只能是图片!", {
						title : '提示框',
						icon : 0,
					});
					return ;
				}else{
					 $("#newFlag").val("1");
				}
			}
			/*else if(newFlag==0){
				laywarn("新品上架广告图片不能为空!");
				return false;
			} 
			*/
			
			var hotFile=$("#previewImg2").val();
			var hotFlag =  $("#hotFlag").val();
			if(hotFile!=""){	
				/* 分割之后根据后缀判断图片类型 */
				var splitPicName=hotFile.split(".");
				if(splitPicName[splitPicName.length-1].toUpperCase()!='JPG'&&splitPicName[splitPicName.length-1].toUpperCase()!='JPEG'&&splitPicName[splitPicName.length-1].toUpperCase()!='GIF'&&splitPicName[splitPicName.length-1].toUpperCase()!='PNG'&&splitPicName[splitPicName.length-1].toUpperCase()!='BMP'){
					layer.alert("测试用热卖上传文件类型不对,只能是图片!", {
						title : '提示框',
						icon : 0,
					});
					return ;
				}else{
					 $("#hotFlag").val("1");
				}
			}
			else if(hotFlag==0){
				laywarn("测试用热卖广告图片不能为空!");
				return false;
			}
			
			
			var presaleFile=$("#previewImg3").val();
			var presaleFlag =  $("#presaleFlag").val();
			if(presaleFile!=""){	
				/* 分割之后根据后缀判断图片类型 */
				var splitPicName=presaleFile.split(".");
				if(splitPicName[splitPicName.length-1].toUpperCase()!='JPG'&&splitPicName[splitPicName.length-1].toUpperCase()!='JPEG'&&splitPicName[splitPicName.length-1].toUpperCase()!='GIF'&&splitPicName[splitPicName.length-1].toUpperCase()!='PNG'&&splitPicName[splitPicName.length-1].toUpperCase()!='BMP'){
					layer.alert("爆品预售上传文件类型不对,只能是图片!", {
						title : '提示框',
						icon : 0,
					});
					return ;
				}else{
					 $("#presaleFlag").val("1");
				}
			}
			else if(presaleFlag==0){
				laywarn("爆品预售广告图片不能为空!");
				return false;
			}

			publicMessageLayer("生效此广告", function() {
				countgoods();
				
				
				
				
				var formData = new FormData($("#inserOrUpdateDiv")[0]);
				
				$.ajax({
					url :'<%=basePath%>backgroundConfiguration/advertisement/advertisementInformation/effectHotCategories',
					type : "POST",
					dataType : "json",
					async: false,
					cache: false,
					data :formData,
					contentType : false,
					processData : false,//processData的默认值是true。用于对data参数进行序列化处理
					success : function(data) {

						if(data.success) {
							laysuccess(data.msg);
						} else {
							layfail(data.msg);
						}
						

					}
				});
				
				
				/* console.log($("#goodsidStr_sale").val());
				console.log($("#stateStr_sale").val());
				console.log($("#goodsidStr_new").val());
				console.log($("#stateStr_new").val());
				console.log($("#goodsidStr_hot").val());
				console.log($("#stateStr_hot").val());
				console.log($("#goodsidStr_presale").val());
				console.log($("#stateStr_presale").val());  */

				
			})
		}

		function refresh() {
			window.location.reload();
		}
		
		
		/* 选择一级分类 给二级分类的下拉框赋值  */
		function loadClassificationTwo(that,$classificationTwo,$goods) {
			$classificationTwo.empty();
			$goods.empty();
			$goods.append("<option value='-1' selected>--商品名称--</option>");
			if($(that).val() == "-1") {
				$classificationTwo.append("<option value='-1' selected>--二级分类--</option>");
				return;
			}
			<%--var id = $(that).find("option:selected").attr("data-id");
 
			$.ajax({
				url: '<%=basePath%>backgroundConfiguration/classification/classification/selectTwoByOneId?id=' + id,
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				success: function(data) {
					$classificationTwo.append("<option value='-1' selected>--二级分类--</option>");
					for(var i = 0; i < data.length; i++) {
						var option = $("<option data-id='"+data[i].id+"' value='"+data[i].name+"'>" +data[i].name + "</option>");
						$classificationTwo.append(option);
						
					}
					
				}
			}); --%>
			if($classificationTwo.val()!="-1"){
				index = that.find("option:selected").attr("data-index");
				var data = selectData[index]["twoClassifications"];
				$classificationTwo.append("<option value='-1' selected>--二级分类--</option>");
				for ( var i = 0; i < data.length; i++) {
					var option = $("<option data-index="+i+" data-id="+data[i].id+" value='"+data[i].name+"'>"+ data[i].name +"</option>");
					$classificationTwo.append(option);
				} 
			}
			
			
		}
		
		/* 选择二级分类 给商品名称的下拉框赋值  */
		function loadGoodsName(that,$goods) {
			$goods.empty();
			if($(that).val() == "-1") {
				$goods.append("<option value='-1' selected>--商品名称--</option>");
				return;
			}
			<%-- var id = $(that).find("option:selected").attr("data-id");
			$.ajax({
				url: '<%=basePath%>backgroundConfiguration/goods/goodsManager/selectAllGoodsDetails?classificationId=' + id,
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				success: function(data) {
					$goods.append("<option value='-1' selected>--商品名称--</option>");
					for(var i = 0; i < data.length; i++) {
						var option = $("<option data-id='"+data[i].id+"' value='"+data[i].name+"'>" +data[i].name + "</option>");
						$goods.append(option);
					}
					
				}
			}); --%>
			if($goods.val()!="-1"){
				twoIndex=that.find("option:selected").attr("data-index");
				var data = selectData[index]["twoClassifications"][twoIndex]["goodsDetails"];
				$goods.append("<option value='-1' selected>--商品名称--</option>");
				for ( var i = 0; i < data.length; i++) {
					var option = $("<option   data-id="+data[i].id+" value='"+data[i].name+"'>"+ data[i].name +"</option>");
					$goods.append(option);
				}
			}
		}
		
	</script>

</html>