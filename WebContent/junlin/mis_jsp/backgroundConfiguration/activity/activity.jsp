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
		<title>活动</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		<script src="${pageContext.request.contextPath}/junlin/js/photoloads.js" type="text/javascript"></script>
		<link >
		<style>
			#editDiv {
				padding-top: 50px;
				font-size: 14px;
			}
			
			.rule-div,
			.goods-list,
			.coupon-list,
			.presell-goods-list {
				padding: 0;
			}
			.presell-goods-list  .col-xs-2,
			.goods-list .col-xs-2{
				padding-right: 0;
			}
			
			
			.fullcut {
				line-height: 30px;
			}
			.presell-goods-list ul li,
			.goods-list ul li,
			.coupon-list ul li,
			.li-inline li {
				width: 50%;
				display: inline-block;
				cursor: default;
				text-decoration: underline;
			}
			
			.delli-div {
				display: inline-block;
				color: #ff7e7e;
			}
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">活动</h4>
					<div class="search-div clearfix">
						<div class="clearfix">
							<span class="l_f"> 
							编号： <input type="text" value="" id="query_identifier" onblur="cky(this)"/>
						</span>
							<span class="l_f"> 
							名称： <input type="text" value="" id="query_name" onblur="cky(this)"/>
						</span>
							<span class="l_f"> 
							创建人姓名： <input type="text"  value="" id="query_operatorIdentifier"  onblur="cky(this)"/>
						</span>
						<span class="l_f"> 
							创建时间： <input type="text"  value="" id="search_operatorTime" placeholder="请选择时间" readonly=""  />
						</span>
							<span class="l_f"> 
							活动类型： 
							<select id="query_activityType">
								<option selected="selected" value="-1">--请选择--</option>
								<option value="0">折扣</option>
								<option value="1">团购</option>
								<option value="2">秒杀</option>
								<option value="3">立减</option>
								<option value="4">满减</option>
								<option value="5">预售</option>
							</select>
						</span>
							<span class="l_f"> 
							状态： 
							<select  id="query_state">
								<option selected="selected" value="-1">--请选择--</option>
								<option value="0">未送审</option>
								<option value="1">审核中</option>
								<option value="2">审核通过</option>
								<option value="3">审核未通过</option>
								<option value="4">上线中</option>
								<option value="5">已下线</option>
								<option value="6">已失效</option>
								<option value="7">低价活动审核中</option>
							</select>
						</span>
							<span class="r_f"> 
							<input type="button" id="btn_search" class="btncss btn_search" value="搜索" />
						</span>
						</div>

					</div>
					<div class="opration-div clearfix">
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="activityEduce()" style="margin-right: 0;"><i class="fa fa-download"></i> 导出</button>
							
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="activityDel()"><i class="fa fa-trash"></i> 删除</button>
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-importent" onclick="activityAdd()"><i class="fa fa-plus"></i> 新增</button>
						</span>
						<span class="jl_f_l">
							<input type="checkbox" onclick="checkboxController(this)"   name="" id="checkAll" style="margin:0 5px 0 0;"/>
						</span>
						<span class="jl_f_l">
							<label for="checkAll">全选</label>
						</span>

					</div>
					<div class="table_menu_list">
					<form id="datatable_form">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>选择</th>
									<th>编号</th>
									<th>类型</th>
									<th>名称</th>
									<th>介绍</th>
									<th>预算(元)</th>
									<th>活动时间</th>
									<th>状态</th>
									<th width="21%">操作</th>
								</tr>

							</thead>
						 <tbody>
								<!-- <tr>
									<td>
										<input type="checkbox" name="id" checked="checked" />
									</td>
									<td>
										00100101
									</td>
									<td>折扣</td>
									<td>福临门</td>
									<td>200</td>
									<td>200</td>
									<td>2017.10.02-2017.11.02</td>
									<td>审核中</td>
									<td>
										<input type="button" class="btncss edit" onclick="activityLook()" value="详情" />
										<input type="button" class="btncss edit" onclick="activityEdit()" value="编辑" />
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="id" checked="checked" />
									</td>
									<td>
										00100101
									</td>
									<td>折扣</td>
									<td>福临门</td>
									<td>200</td>
									<td>200</td>
									<td>2017.10.02-2017.11.02</td>
									<td>已通过</td>
									<td>
										<input type="button" class="btncss edit" onclick="activityLook()" value="详情" />
										<input type="button" class="btncss edit" onclick="activityEdit()" value="编辑" />
									</td>
								</tr> -->
							</tbody> 
						</table>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!--详情-->
		<div id="lookDiv" style="display: none;">
			<form class="container">
				<div class="form-group">
					<label class="col-xs-2 control-label">编号：</label>
					<div class="col-xs-4" id="view_identifier">
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">名称：</label>
					<div class="col-xs-4" id="view_name">
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">活动时间：</label>
					<div class="col-xs-4" id="view_activitytime">
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">介绍：</label>
					<div class="col-xs-8" id="view_introduction">
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">类型：</label>
					<div class="col-xs-8" id="view_activityType">
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">商品列表：</label>
					<div class="col-xs-8 view_goods-list" >
						<ul class="col-xs-12 li-inline">
							
						</ul>
					</div>
				</div>
				<div class="form-group">
					<label for="" class="col-xs-2 control-label">优惠券：</label>
					<div class="col-xs-8 view_coupon-list">
						<ul class="col-xs-12 li-inline">
							
						</ul>
					</div>
				</div>

				<div class="form-group">
					<label for="" class="col-xs-2 control-label">消息图：</label>
					<div class="col-xs-4">
						<img id="view_imghead0" border="0" src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg" width="90" height="90">
						
					</div>
				</div>
				<div class="form-group">
					<label for="" class="col-xs-2 control-label">展示图：</label>
					<div class="col-xs-4">
						<img id="view_imghead1" border="0" src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg" width="90" height="90">
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">创建人：</label>
					<div class="col-xs-4" id="view_operatorIdentifier">
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">创建时间：</label>
					<div class="col-xs-4" id="view_operatorTime">
						
					</div>
				</div>
				<div class="form-group editshow hidden" id="view_warning">
					<label class="col-xs-2 control-label" >预警：</label>
					<div  class="col-xs-4">
						有商品价格低于库存价格
					</div>
				</div>
			</form>
		</div>

		<!--新增 编辑 -->
		<div id="editDiv" style="display: none;">
			<form class="container" id="inserOrUpdateDiv">
				<div class="form-group editshow hidden">
					<label class="col-xs-2 control-label">编号：</label>
					<div class="col-xs-4" id="identifier">
						
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-xs-2 control-label">名称：</label>
					<div class="col-xs-4">
						<input type="text" name="name" class="form-control" id="name" maxlength="100" />
					</div>
				</div>
				<div class="form-group">
					<label for="activitytime" class="col-xs-2 control-label">活动时间：</label>
					<div class="col-xs-4">
						<input type="text" class="form-control"  id="activitytime" placeholder="请选择有效期时间" readonly="" />
						<input type="text" id="beginValidityTime" name="beginValidityTime" class="hidden" />
						<input type="text" id="endValidityTime" name="endValidityTime" class="hidden" />
					</div>
				</div>
				<div class="form-group">
					<label for="introduction" class="col-xs-2 control-label">介绍：</label>
					<div class="col-xs-8">
						<textarea class="form-control" id="introduction" name="introduction" maxlength="200" rows="3" maxlength="100" placeholder="请输入100个字数以下。"></textarea>
					</div>
				</div>
				<div class="form-group">
					<label for="activityType" class="col-xs-2 control-label">类型：</label>
					<div class="col-xs-2">
						<select id="activityType" name="activityType" class="form-control">
								<option value="-1" selected="selected">--请选择--</option>
								<option value="0">折扣</option>
								<option value="1">团购</option>
								<option value="2">秒杀</option>
								<option value="3">立减</option>
								<option value="4">满减</option>
								<option value="5">预售</option>
						</select>
					</div>
					<div class="rule-div">
						<label class="col-xs-2 control-label text-center">请先选择活动类型</label>
						<!--<div class="col-xs-2 discount">
							<input type="text" name="box1" class="form-control" placeholder="折扣" />
						</div>
						<div class="col-xs-2 discount">
							<input type="text" name="box2" class="form-control" placeholder="单人最大购买数量" />
						</div>
						<label class="col-xs-1 control-label group-purchase">原价：200</label>
						<div class="col-xs-2 group-purchase">
							<input type="text" name="box1" class="form-control" placeholder="团购价格" />
						</div>
						<div class="col-xs-2 group-purchase">
							<input type="text" name="box2" class="form-control" placeholder="单人最大购买数量" />
						</div>
						<label class="col-xs-1 control-label seckill">原价：200</label>
						<div class="col-xs-2 seckill">
							<input type="text" name="box1" class="form-control" placeholder="秒杀价格" />
						</div>
						<div class="col-xs-2 seckill">
							<input type="text" name="box2" class="form-control" placeholder="单人最大购买数量" />
						</div>
						<div class="col-xs-2 minus">
							<input type="text" name="box1" class="form-control" placeholder="立减价格" />
						</div>
						<div class="col-xs-2 minus">
							<input type="text" name="box2" class="form-control" placeholder="单人最大购买数量" />
						</div>
						<div class="col-xs-4 fullcut">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">满</div>
									<input type="text" class="form-control" name="box1">
									<div class="input-group-addon">减</div>
									<input type="text" class="form-control" name="box2">
								</div>
							</div>
						</div>-->
					</div>

				</div>

				<div class="form-group">
					<label class="col-xs-2 control-label">商品列表：</label>
					<div class="col-xs-8 goods-list">
						<ul class="col-xs-12">
							<!--<li data-id="1" data-state="0">a</li>
							<li data-id="2" data-state="1">b</li>
							<li data-id="3" data-state="1">c</li>
							<li data-id="4" data-state="1">d</li>-->
						</ul>
						<div class="col-xs-2">
							<select id="classificationOne"  class="form-control" data-state="3" disabled="disabled">
								<option value="-1" selected="selected">--一级分类--</option>
								
							</select>
						</div>
						<div class="col-xs-2">
							<select id="classificationTwo"  class="form-control" data-state="2" disabled="disabled">
								<option value="-1" selected="selected">--二级分类--</option>
							</select>
						</div>
						<div class="col-xs-2">
							<select id="goods"  class="form-control" data-state="1" disabled="disabled">
								<option value="-1" selected="selected">--商品--</option>
							</select>
						</div>
						<div class="col-xs-2">
							<select id="goodstype"  class="form-control" data-state="0" disabled="disabled">
								<option value="-1" selected="selected">--规格--</option>
							</select>
						</div>
						<div class="col-xs-2">
							<button type="button" class="btncss jl-btn-importent" id="goodsAddBtn">新增</button>
							<input type="text" id="goodsidStr" name="goods" class="hidden" />
							<input type="text" id="stateStr" name="goodsState" class="hidden" />
						</div>

					</div>
				</div>
				
				<!-- <div class="form-group" id="selectDataForPresell" >
					<label class="col-xs-2 control-label">商品列表：</label>
					<div class="col-xs-8 presell-goods-list">
						<ul class="col-xs-12">
							<li data-id="1" data-state="0">a</li>
							<li data-id="2" data-state="1">b</li>
							<li data-id="3" data-state="1">c</li>
							<li data-id="4" data-state="1">d</li>
						</ul>
						<div class="col-xs-2">
							<select id="presell_classificationOne"  class="form-control" data-state="3">
								<option value="-1" selected="selected">--一级分类--</option>
								
							</select>
						</div>
						<div class="col-xs-2">
							<select id="presell_classificationTwo"  class="form-control" data-state="2">
								<option value="-1" selected="selected">--二级分类--</option>
							</select>
						</div>
						<div class="col-xs-2">
							<select id="presell_goods"  class="form-control" data-state="1">
								<option value="-1" selected="selected">--商品--</option>
							</select>
						</div>
						<div class="col-xs-2">
							<select id="presell_goodstype"  class="form-control" data-state="0">
								<option value="-1" selected="selected">--规格--</option>
							</select>
						</div>
						<div class="col-xs-2">
							<button type="button" class="btncss jl-btn-importent" id="presell_goodsAddBtn">新增</button>
							<input type="text" id="presell_goodsidStr" name="presell_goods" class="hidden" />
							<input type="text" id="presell_stateStr" name="presell_goodsState" class="hidden" />
						</div>

					</div>
				</div>
				 -->
				<div class="form-group">
					<label for="" class="col-xs-2 control-label">优惠券：</label>
					<div class="col-xs-8 coupon-list">
						<ul class="col-xs-12">
							<!--<li data-id="1" data-state="0">a</li>
							<li data-id="2" data-state="1">b</li>
							<li data-id="3" data-state="1">c</li>
							<li data-id="4" data-state="1">d</li>-->
						</ul>
						<div class="col-xs-5">
							<select id="couponSelect" class="form-control" data-state="4">
								<option value="-1" selected="selected">选择优惠券</option>
								
							</select>
						</div>
						<div class="col-xs-3">
							<button type="button" class="btncss jl-btn-importent" id="couponAddBtn">新增</button>
							<input type="text" id="couponidStr" name="coupon" class="hidden" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="" class="col-xs-2 control-label">消息图：</label>
					<div class="col-xs-4">
						<div class="big-photo">
							<div id="preview0">
								<img id="imghead0"  border="0" src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg" width="90" height="90" onclick="$('#previewImg0').click();">
							</div>
							<input type="file" onchange="previewImage(this,0)" style="display: none;" id="previewImg0" name="messagePicUrl" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg">
							<!--<input id="uploaderInput" class="uploader__input" style="display: none;" type="file" accept="" multiple="">-->
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="" class="col-xs-2 control-label">展示图：</label>
					<div class="col-xs-4">
						<div class="big-photo">
							<div id="preview1">
								<img id="imghead1"  border="0" src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg" width="90" height="90" onclick="$('#previewImg1').click();">
							</div>
							<input type="file" onchange="previewImage(this,1)" style="display: none;" id="previewImg1" name="showPicUrl" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg">
							<!--<input id="uploaderInput" class="uploader__input" style="display: none;" type="file" accept="" multiple="">-->
						</div>
					</div>
				</div>
				<div class="form-group editshow hidden">
					<label class="col-xs-2 control-label">创建人：</label>
					<div class="col-xs-4" id="edit_operatorIdentifier">
						
					</div>
				</div>
				<div class="form-group editshow hidden">
					<label class="col-xs-2 control-label">创建时间：</label>
					<div class="col-xs-4" id="edit_operatorTime">
						
					</div>
				</div>
				<div class="form-group editshow hidden" id="edit_warning">
					<label class="col-xs-2 control-label">预警：</label>
					<div  class="col-xs-4">
						有商品价格低于库存价格
					</div>
				</div>
				<div class="form-group">
				<div class="col-xs-2"></div>
				<div class="col-xs-9 tip">注：<br/>1.消息图和展示图上传的图片大小应小于2M,消息图尺寸建议为1300*400，展示图尺寸建议为1400*700（单位：像素）<br/>
				2.该页面所有字段均为必填</div>
				</div>
				

		</div>

	</body>

	<script>
	var contextPath = "${pageContext.request.contextPath}";
	var oTable1;
	var selectData=[];
	var selectDataNotPresell=[];
	var selectDataPresell=[];
	//查询
	$("#btn_search").click(function() {
		
		oTable1.fnDraw();
		$("#checkAll").attr("checked",false);
		
	});
	
	
	
	jQuery(function($) {
		
		classificationOneAjax(1,'<%=basePath%>backgroundConfiguration/activity/activityInformation/getClassificationByParentIdForNotPresell');

		classificationOneAjax(2,'<%=basePath%>backgroundConfiguration/activity/activityInformation/getClassificationByParentIdForPresell');
		
		/* 优惠券下拉框赋值 */
		$.ajax({
			url :'<%=basePath%>backgroundConfiguration/activity/couponInformation/selectAllNormalCouponInformation' ,
			type : "POST",
			dataType : "json",
			success : function(data) {
				for ( var i = 0; i < data.length; i++) {
					var option = $("<option  data-id="+data[i].id+" value='"+data[i].name+"(满"+data[i].useLimit+"减"+data[i].price+")'>"+ data[i].name+"(满"+data[i].useLimit+"减"+data[i].price+")" +"</option>");
					$("#couponSelect").append(option);
				}
			}
		});
		
		
		
		//datatable赋值
		oTable1 = $('#datatable')
			.dataTable({
				"aaSorting": [
					[0, "desc"]
				], //默认第几个排序
				"bStateSave": false, //状态保存
				"bLengthChange": false, //改变每页显示数据数量
				"bFilter": false, //列搜索
				"iDisplayLength": 10, //默认每页显示的记录数
				"iDisplayStart": 0,
				"bServerSide": true, //是否开启服务端查询
				"sDom": 't<"row"<"col-sm-6"i><"col-sm-6"p>>',
				"ajax": {
					"type": "post",
					"dataType": "json",
					"async": false,
					"data": function(d) {
						return $.extend({}, d, {
							//添加额外的参数传给服务器(可以多个)
							"flag": 1,
							"identifier": $("#query_identifier").val(),
							"name": $("#query_name").val(),
							"operatorIdentifier": $("#query_operatorIdentifier").val(),
							"activityType" : $("#query_activityType").val()-0,
							"state" : $("#query_state").val()-0,
							"operatorTime": $("#search_operatorTime").val()
						});
					},
					"url": "<%=basePath%>backgroundConfiguration/activity/activityInformation/dataTables"
				},

				"aoColumns": [{
						"mData": "id",
						"bSortable": true,
						"sWidth": "5%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							return '<td><input type="checkbox" onclick="checkboxClick(\'#checkAll\')" name="id" value="' + row.id + '"/></td>';
						}
					}, {
						"mData": "identifier",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center"
					}, {
						"mData": "activityType",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							switch (data) {
							case 0:
								return '折扣';
								break;
							case 1:
								return '团购';
								break;
							case 2:
								return '秒杀';
								break;
							case 3:
								return '立减';
								break;
							case 4:
								return '满减';
								break;
							case 5:
								return '预售';
								break;
							default:
								break;
							}
							
						}

					}, {
						"mData": "name",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center"
						
					}, {
						"mData": "introduction",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center"
					}, {
						"mData": "budget",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							if(data==''||data==null) {
								return "无";
							}else{
								return data;
							}
						}
					},
					{
						"mData": "endValidityTime",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							
							var  date = getSmpFormatDateByLong(row.beginValidityTime, false)+"~"+getSmpFormatDateByLong(data, false);
							return	date;
						}
					}, {
						"mData": "state",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"bVisible": false,

					},

					{
						"mData": "state",
						"bSortable": false,
						"sWidth": "25%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							
							switch (data) {
							case 0:
								var dd = new Date();
								
								if(dd>=(row.endValidityTime+86400000)){
									return '<td><input type="button" class="btncss edit" onclick=\'activityLook(' + JSON.stringify(row) + ')\' value="详情" />'+
									'<input type="button" class="btncss edit" onclick=\'activityEdit(' + JSON.stringify(row) + ')\' value="编辑" /></td>';
									
								}else{
									return '<td><input type="button" class="btncss edit" onclick=\'activityLook(' + JSON.stringify(row) + ')\' value="详情" />'+
									'<input type="button" class="btncss edit" onclick=\'activityEdit(' + JSON.stringify(row) + ')\' value="编辑" />'+
									'<input type="button" class="btncss edit" onclick=\'activityToCheck(' + JSON.stringify(row) + ')\' value="送审" /></td>';
								}
								return "";
								break;
							case 1:
								return	'<td><input type="button" class="btncss edit" onclick=\'activityLook(' + JSON.stringify(row) + ')\' value="审核中" /></td>';
								break;
							case 2:
								return	'<td><input type="button" class="btncss edit" onclick=\'activityLook(' + JSON.stringify(row) + ')\' value="已通过" /></td>';
								break;
							case 3:
								return	'<td><input type="button" class="btncss edit" onclick=\'activityLook(' + JSON.stringify(row) + ')\' value="已拒绝" /></td>';
								break;
							case 4:
								return	'<td><input type="button" class="btncss edit" onclick=\'activityLook(' + JSON.stringify(row) + ')\' value="上线中" /></td>';
								break;
							case 5:
								return	'<td><input type="button" class="btncss edit" onclick=\'activityLook(' + JSON.stringify(row) + ')\' value="已下线" /></td>';
								break;
							case 6:
								return	'<td><input type="button" class="btncss edit" onclick=\'activityLook(' + JSON.stringify(row) + ')\' value="已失效" /></td>';
								break;
							case 7:
								return	'<td><input type="button" class="btncss edit" onclick=\'activityLook(' + JSON.stringify(row) + ')\' value="低价活动审核中" /></td>';
								break;
							default:
								break;
							}
							
							

						}
					}
				],
				"oLanguage": {
					// 国际化配置
					"sLengthMenu": "每页显示 _MENU_ 条记录",
					"sZeroRecords": "没有数据记录",
					"sInfo": "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
					"sInfoEmpty": "",
					"sInfoFiltered": "(全部记录数 _MAX_ 条)",
					"oPaginate": {
						"sFirst": "首页",
						"sPrevious": "前一页",
						"sNext": "后一页",
						"sLast": "尾页"
					}
				}
			});
		

	});
	
	/*一级分类数据请求  */
	function classificationOneAjax(type,urlstr){
		$.ajax({
			url :urlstr ,
			type : "POST",
			dataType : "json",
			async: false,
			success : function(data) {
				if(type==1){
					selectDataNotPresell=data;
				}else{
					selectDataPresell=data;
				}
				
			}
		});
	}
	/*一级分类赋值  */
	function classificationOneAppend(data){
		
		selectData=data;
		console.log("data",data)
		$("#classificationOne").html("<option value='-1'>--一级分类--</option>");
		for ( var i = 0; i < data.length; i++) {
			var option = $("<option data-index="+i+"  data-id="+data[i].id+" value='"+data[i].name+"'>"+ data[i].name +"</option>");
			$("#classificationOne").append(option);
		}
		getgoods($("#classificationOne"), 1);
	}
	
	
	
	
	
	/*新增*/
	function activityAdd() {
		$(".editshow").removeClass("hidden");
		$(".editshow").addClass("hidden");
		layer.open({
			type: 1,
			title: "新增活动",
			closeBtn: 1,
			area: ['100%', '100%'],
			content: $("#editDiv"),
			btn: ['提交', '取消'],
			yes: function(index) {
				countli();
				
		
				if($("#name").val() == "") {
					laywarn("活动名称不能为空!");
					return false;
				} else if($("#activitytime").val() == "") {
					laywarn("请选择活动时间!");
					return false;
				}else if($("#introduction").val() == "" ) {
					laywarn("活动介绍不能为空!");
					return false;
				} else if($("#activityType").val() == -1) {
					laywarn("请选择活动类型!");
					return false;
				}else if($("#box1").val() == "" || $("#box2").val() == "") {
					laywarn("请完善活动类型信息!");
					return false;
				}else if($("#activityType").val()=="1" && ($("#box1").val()-0-price>=0) ){//团购
					laywarn("团购价需小于原价!");
					return false;
				}else if($("#activityType").val()=="2" && ($("#box1").val()-0-price>=0) ){//秒杀
					laywarn("秒杀价需小于原价!");
					return false;
				}else if($("#activityType").val()=="3" && ($("#box1").val()-0-price>=0) ){//立减
					laywarn("立减金额需小于原价!");
					return false;
				}
				else if($("#activityType").val()=="4" && ($("#box2").val()-0-($("#box1").val()-0)>=0) ){//秒杀
					laywarn("请确认满减额符合要求!");
					return false;
				}else if($("#goodsidStr").val() == "") {
					laywarn("商品列表不能为空!");
					return false;
				}
				var messageFile=$("#previewImg0").val();
				if(messageFile!=""){	
					/* 分割之后根据后缀判断图片类型 */
					var splitPicName=messageFile.split(".");
					if(splitPicName[splitPicName.length-1].toUpperCase()!='JPG'&&splitPicName[splitPicName.length-1].toUpperCase()!='JPEG'&&splitPicName[splitPicName.length-1].toUpperCase()!='GIF'&&splitPicName[splitPicName.length-1].toUpperCase()!='PNG'&&splitPicName[splitPicName.length-1].toUpperCase()!='BMP'){
						layer.alert("上传文件类型不对,只能是图片!", {
							title : '提示框',
							icon : 0,
						});
						return ;
					}
				}
				else{
					laywarn("消息图片不能为空!");
					return false;
				}
				var showFile=$("#previewImg1").val();
				if(showFile!=""){	
					/* 分割之后根据后缀判断图片类型 */
					var splitPicName=showFile.split(".");
					if(splitPicName[splitPicName.length-1].toUpperCase()!='JPG'&&splitPicName[splitPicName.length-1].toUpperCase()!='JPEG'&&splitPicName[splitPicName.length-1].toUpperCase()!='GIF'&&splitPicName[splitPicName.length-1].toUpperCase()!='PNG'&&splitPicName[splitPicName.length-1].toUpperCase()!='BMP'){
						layer.alert("上传文件类型不对,只能是图片!", {
							title : '提示框',
							icon : 0,
						});
						return ;
					}
				}
				else{
					laywarn("展示图片不能为空!");
					return false;
				}
				var formData = new FormData($("#inserOrUpdateDiv")[0]);
				$.ajax({
					url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/addActivityInformation',
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
							
						oTable1.fnDraw();
						$("#checkAll").attr("checked",false);
						layer.close(index);
						

					}
				});	

				
			},
			end:function(index){
				layer.close(index);
				clearForm("editDiv", "",'<%=basePath%>');
				$(".goods-list ul,.coupon-list ul").html("");
				$(".rule-div").html('<label class="col-xs-2 control-label text-center">请先选择活动类型</label>');
				goodsIdstr = ""; //商品id字符串
				goodsStatestr = ""; //商品类型字符创
				checkGoods3 = []; //商品选中——一级分类
				checkGoods2 = []; //商品选中——二级分类
				checkGoods1 = []; //商品选中——商品
				checkGoods0 = []; //商品选中——规格
				
				couponObj = [];
				couponIdstr="";
				$("#goodsidStr").val("");
				$("#stateStr").val("");
				$("#couponidStr").val("");
				$(".goods-list select").each(function(index,obj){
					$(this).attr("disabled",true);
				})
				$("#goodstype").parent().removeClass("hidden");
			}
		});
	}
		
	
	
	/*删除*/
	function activityDel() {
		
		var str = "";
		$("input:checkbox[name='id']:checked").each(function() {
			str += $(this).val() + ",";
		})
		if(str == "") {
			laywarn("请选择数据!");
			return;
		}
		
		
		publicMessageLayer("删除选中数据", function() {

			$.ajax({
				url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/deleteActivityInformationByIds',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: $("#datatable_form").serialize(),
				success: function(data) {
					if(data.success) {
						laysuccess(data.msg);
						oTable1.fnDraw();
						$("#checkAll").attr("checked",false);
					} else {
						layfail(data.msg);
					}

				}
			});
			
		})
	}
	
	
	
	/*送审*/
	function activityToCheck(data) {
		var id = data.id;
		var ids=[];
		ids.push(id);
		
		
		publicMessageLayer("送审该活动", function() {

			$.ajax({
				url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/updateState',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: {
					"ids" : ids,
					"flag" : 1
				},
				
				traditional:true,
				success: function(data) {
					if(data.success) {
						laysuccess(data.msg);
						oTable1.fnDraw();
						$("#checkAll").attr("checked",false);
					} else {
						layfail(data.msg);
					}

				}
			});
			
		})
	}
	/*导出*/
	function activityEduce() {
		publicMessageLayer("导出下列显示的数据", function() {
			var name = encodeURI(encodeURI($("#query_name").val())); 
			var identifier = encodeURI(encodeURI($("#query_identifier").val()));
			var operatorIdentifier = encodeURI(encodeURI($("#query_operatorIdentifier").val()));
			var export_operatorTime=encodeURI(encodeURI($("#search_operatorTime").val()));
			var URL="<%=basePath%>backgroundConfiguration/activity/activityInformation/exportActivityInformation?flag=1&identifier="+identifier+"&name="+name+"&operatorIdentifier="+operatorIdentifier+"&activityType="+($("#query_activityType").val()-0)+"&state="+($("#query_state").val()-0)+"&operatorTime="+export_operatorTime;	
			    location.href=URL;
				return false;			
		})
	}
	
	
	
		
	/*修改*/
	function activityEdit(rowData) {
		$(".editshow").removeClass("hidden");
		
		//页面赋值
		//获取数据显示在界面中
			$.ajax({
				url: '<%=basePath%>backgroundConfiguration/activity/activityInformation/getMsgByActivityInformationId',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: {
					"id": rowData.id
				},

				success: function(data) {
					/* console.log("activityCouponJsonArray : ",data.activityCouponJsonArray);
					console.log("activityGoodsJsonArray : ",data.activityGoodsJsonArray);
					console.log("warning : ",data.warning);
					console.log("0:   ",data.checkGoods0);
					console.log("1:   ",data.checkGoods1);
					console.log("2:   ",data.checkGoods2);
					console.log("3:   ",data.checkGoods3);
					console.log("couponID:  ",data.couponObj); */
					
					
					checkGoods0 = data.checkGoods0;
					checkGoods1 = data.checkGoods1;
					checkGoods2 = data.checkGoods2;
					checkGoods3 = data.checkGoods3;
					couponObj = data.couponObj;
					var $coupon, $goods;
					
					 $(data.activityCouponJsonArray).each(function(i, dom) {
						 $coupon = '<li data-id="' + dom["id"]  + '" data-state="4">' + dom["name"] + '<div onclick="delli(this)" class="delli-div"><i class="fa fa-times"></i></div></li>';
						 $(".coupon-list ul").append($coupon);
					}); 
					 $(data.activityGoodsJsonArray).each(function(i, dom) {
						// console.log(dom);
						 $goods = '<li data-id="' + dom["id"] + '" data-state="' + dom["state"] + '">' + dom["name"]  + '<div onclick="delli(this)" class="delli-div"><i class="fa fa-times"></i></div></li>';
							$(".goods-list ul").append($goods);
					}); 
					if(data.warning){
						$("#edit_warning").removeClass("hidden");
					}else{
						$("#edit_warning").addClass("hidden");
					}
					
					
					//alert(rowData.messagePicUrl);
					if(rowData.messagePicUrl!=""){
						
						$("#imghead0").attr("src",'<%=basePath%>'+rowData.messagePicUrl);
						
					}
					else{
						$("#imghead0").attr("src",'<%=basePath%>junlin/img/photo_icon.jpg');
						
					}
					if(rowData.showPicUrl!=""){
						
						$("#imghead1").attr("src",'<%=basePath%>'+rowData.showPicUrl);
						
					}
					else{
						$("#imghead1").attr("src",'<%=basePath%>junlin/img/photo_icon.jpg');
					}
					if(rowData.operatorIdentifier!=null && rowData.operatorIdentifier!=""){
						$("#edit_operatorIdentifier").html(rowData.operatorIdentifier+"("+rowData.user.name+")");
					}else{
						$("#edit_operatorIdentifier").html("");
					}
					
					
					$("#edit_operatorTime").html(getSmpFormatDateByLong(rowData.operatorTime, true));
					
					$("#identifier").html(rowData.identifier);
					$("#name").val(rowData.name);
					var bTime=getSmpFormatDateByLong(rowData.beginValidityTime, false);
					var eTime=getSmpFormatDateByLong(rowData.endValidityTime, false);
					$("#activitytime").val(bTime+" ~ "+eTime);
					$("#beginValidityTime").val(bTime);
					$("#endValidityTime").val(eTime);
					$("#introduction").val(rowData.introduction);
					$("#activityType").val(rowData.activityType);
					ruleDivInit();
					switch(rowData.activityType) {
					case 0://折扣
						$("#box1").val(rowData.discount);
						$("#box2").val(rowData.maxNum);
						break;
					case 1://团购
						$("#box1").val(rowData.discount);
						$("#box2").val(rowData.maxNum);
						$("#tgDiv").html("原价："+data.activityGoodsJsonArray[0]["price"]+"元");
						price = data.activityGoodsJsonArray[0]["price"];
						break;
					case 2://秒杀
						$("#box1").val(rowData.discount);
						$("#box2").val(rowData.maxNum);
						$("#msDiv").html("原价："+data.activityGoodsJsonArray[0]["price"]+"元");
						price = data.activityGoodsJsonArray[0]["price"];
						break;
					case 3://立减
						$("#box1").val(rowData.discount);
						$("#box2").val(rowData.maxNum);
						$("#ljDiv").html("原价："+data.activityGoodsJsonArray[0]["price"]+"元");
						price = data.activityGoodsJsonArray[0]["price"];
						break;
					case 4://满减
						$("#box1").val(rowData.price);
						$("#box2").val(rowData.discount);
						break;
					case 5://预售
						
						break;
					default:
						break;

				}
					
					layer.open({
						type: 1,
						title: "编辑活动",
						closeBtn: 1,
						area: ['100%', '100%'],
						content: $("#editDiv"),
						btn: ['提交', '取消'],
						yes: function(index) {
							
							countli();
							
							
							
							if($("#name").val() == "") {
								laywarn("活动名称不能为空!");
								return false;
							} else if($("#activitytime").val() == "") {
								laywarn("请选择活动时间!");
								return false;
							}else if($("#introduction").val() == "" ) {
								laywarn("活动介绍不能为空!");
								return false;
							} else if($("#activityType").val() == -1) {
								laywarn("请选择活动类型!");
								return false;
							}else if($("#box1").val() == "" || $("#box2").val() == "") {
								laywarn("请完善活动类型信息!");
								return false;
							}else if($("#activityType").val()=="1" && ($("#box1").val()-0-price>=0) ){//团购
								laywarn("团购价需小于原价!");
								return false;
							}else if($("#activityType").val()=="2" && ($("#box1").val()-0-price>=0) ){//秒杀
								laywarn("秒杀价需小于原价!");
								return false;
							}else if($("#activityType").val()=="3" && ($("#box1").val()-0-price>=0) ){//立减
								laywarn("立减金额需小于原价!");
								return false;
							}
							else if($("#activityType").val()=="4" && ($("#box2").val()-0-($("#box1").val()-0)>=0) ){//秒杀
								laywarn("请确认满减额符合要求!");
								return false;
							}else if($("#goodsidStr").val() == "") {
								laywarn("商品列表不能为空!");
								return false;
							}
				
							var formData = new FormData($("#inserOrUpdateDiv")[0]);
							$.ajax({
								url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/updateActivityInformation?id='+rowData.id,
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
										
									oTable1.fnDraw();
									$("#checkAll").attr("checked",false);
									layer.close(index);
									

								}
							});	
							
						},
						end:function(index){
							layer.close(index);
							clearForm("editDiv", "",'<%=basePath%>');
							$(".goods-list ul,.coupon-list ul").html("");
							$(".rule-div").html('<label class="col-xs-2 control-label text-center">请先选择活动类型</label>');
							goodsIdstr = ""; //商品id字符串
							goodsStatestr = ""; //商品类型字符创
							checkGoods3 = []; //商品选中——一级分类
							checkGoods2 = []; //商品选中——二级分类
							checkGoods1 = []; //商品选中——商品
							checkGoods0 = []; //商品选中——规格
							couponObj = [];
							couponIdstr="";
							$("#goodsidStr").val("");
							$("#stateStr").val("");
							$("#couponidStr").val("");
							$(".goods-list select").each(function(index,obj){
								$(this).attr("disabled",true);
							})
							$("#goodstype").parent().removeClass("hidden");
						}
					});
				}
			});
			
			
			
	
	}
		

	
	/*详情*/
	function activityLook(rowData) {
		
		
		//页面赋值
		//获取数据显示在界面中
			$.ajax({
				url: '<%=basePath%>backgroundConfiguration/activity/activityInformation/getMsgByActivityInformationId',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: {
					"id": rowData.id
				},

				success: function(data) {
			
					var $coupon, $goods;
					
					if(data.activityCouponJsonArray==""||data.activityCouponJsonArray==null){
						 $(".view_coupon-list ul").html("无优惠券");
					}else{
						 $(data.activityCouponJsonArray).each(function(i, dom) {
							 $coupon = '<li>' + dom["name"] + '</li>';
							 $(".view_coupon-list ul").append($coupon);
						}); 
					}
					 $(data.activityGoodsJsonArray).each(function(i, dom) {
						 $goods = '<li>' + dom["name"]  + '</li>';
							$(".view_goods-list ul").append($goods);
					}); 
					if(data.warning){
						$("#view_warning").removeClass("hidden");
					}else{
						$("#view_warning").addClass("hidden");
					}
					
					
					//alert(rowData.messagePicUrl);
					if(rowData.messagePicUrl!=""){
						
						$("#view_imghead0").attr("src",'<%=basePath%>'+rowData.messagePicUrl);
						
					}
					else{
						$("#view_imghead0").attr("src",'<%=basePath%>junlin/img/photo_icon.jpg');
						
					}
					if(rowData.showPicUrl!=""){
						
						$("#view_imghead1").attr("src",'<%=basePath%>'+rowData.showPicUrl);
						
					}
					else{
						$("#view_imghead1").attr("src",'<%=basePath%>junlin/img/photo_icon.jpg');
					}
					
					if(rowData.operatorIdentifier!=null && rowData.operatorIdentifier!=""){
						$("#view_operatorIdentifier").html(rowData.operatorIdentifier+"("+rowData.user.name+")");
					}else{
						$("#view_operatorIdentifier").html("");
					}
					
					$("#view_operatorTime").html(getSmpFormatDateByLong(rowData.operatorTime, true));
					
					$("#view_identifier").html(rowData.identifier);
					$("#view_name").html(rowData.name);
					var bTime=getSmpFormatDateByLong(rowData.beginValidityTime, false);
					var eTime=getSmpFormatDateByLong(rowData.endValidityTime, false);
					$("#view_activitytime").html(bTime+" ~ "+eTime);
					
					$("#view_introduction").html(rowData.introduction);
					
					var str="";
					switch(rowData.activityType) {
					case 0://折扣
						str="折扣（折扣："+rowData.discount+" ，   单人最大购买数量："+rowData.maxNum+"）"
						$("#view_activityType").html(str);
						/* $("#box1").val(rowData.discount);
						$("#box2").val(rowData.maxNum); */
						break;
					case 1://团购
						str="团购（团购价："+rowData.discount+"元，   单人最大购买数量："+rowData.maxNum+"）"
						$("#view_activityType").html(str);
					/* 	$("#box1").val(rowData.discount);
						$("#box2").val(rowData.maxNum);
						$("#tgDiv").html("原价："+data.activityGoodsJsonArray[0]["price"]+"元"); */
						break;
					case 2://秒杀
						str="秒杀（秒杀价："+rowData.discount+"元，   单人最大购买数量："+rowData.maxNum+"）"
						$("#view_activityType").html(str);
						/* $("#box1").val(rowData.discount);
						$("#box2").val(rowData.maxNum);
						$("#msDiv").html("原价："+data.activityGoodsJsonArray[0]["price"]+"元"); */
						break;
					case 3://立减
						str="立减（立减金额："+rowData.discount+"元，   单人最大购买数量："+rowData.maxNum+"）"
						$("#view_activityType").html(str);
						/* $("#box1").val(rowData.discount);
						$("#box2").val(rowData.maxNum); */
						break;
					case 4://满减
						str="满减（满："+rowData.price+"  减："+rowData.discount+"）"
						$("#view_activityType").html(str);
						/* $("#box1").val(rowData.price);
						$("#box2").val(rowData.discount); */
						break;
					case 5://预售
						str="预售"
						$("#view_activityType").html(str);
						/* $("#box1").val(rowData.price);
						$("#box2").val(rowData.discount); */
						break;
					default:
						break;

				}
				
					layer.open({
						type: 1,
						title: "活动详情",
						closeBtn: 1,
						area: ['100%', '100%'],
						content: $("#lookDiv"),
						btn: ['关闭'],
						yes: function(index) {
							layer.close(index);
						},
						end: function(index){
							$(".view_goods-list ul").html("")
							$(".view_coupon-list ul").html("")
						}
					});
			
			}
		});
		
		
		
		
		
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		/*商品列表 begin*/
		var activityType = -1; //活动类型
		var goodsIdstr = ""; //商品id字符串
		var goodsStatestr = ""; //商品类型字符创
		var checkGoods3 = []; //商品选中——一级分类
		var checkGoods2 = []; //商品选中——二级分类
		var checkGoods1 = []; //商品选中——商品
		var checkGoods0 = []; //商品选中——规格
		var price = 0;
		//用于记录下拉菜单选中值
		var classificationOne = {
			id: "",
			state: "3",
			goodsname: "-1"
		};
		var classificationTwo = {
			id: "",
			state: "2",
			goodsname: "-1"
		};
		var goods = {
			id: "",
			state: "1",
			goodsname: "-1"
		};
		var goodstype = {
			id: "",
			state: "0",
			goodsname: "-1"
		};
		
		getgoods($("#classificationTwo"), 2);
		getgoods($("#goods"), 3);
		getgoods($("#goodstype"), 4);

		$("#activityType").change(function() {
			//初始化
			$(".goods-list ul").html("");
			checkGoods3 = []; //商品选中——分类
			checkGoods2 = []; //商品选中——分类
			checkGoods1 = []; //商品选中——商品
			checkGoods0 = []; //商品选中——规格

			ruleDivInit();

		})
		$("#goodsAddBtn").click(function() {
			
			if(activityType == -1) {
				laywarn("请先选择活动类型");
			} else if(activityType == 2 || activityType == 3 || activityType == 1) { //单商品0
				if($(".goods-list ul li").length < 1) {
					appendgoods(0);
					if($("#goodstype").find("option:selected").attr("data-price")!=undefined){
						price = $("#goodstype").find("option:selected").attr("data-price")-0
						var priceStr ="原价："+ price +"元";

						if($("#activityType").val()=="1"){
							
							$("#tgDiv").html(priceStr);
						}else if($("#activityType").val()=="2"){
							$("#msDiv").html(priceStr);
						}else if($("#activityType").val()=="3"){
							$("#ljDiv").html(priceStr);
						}
					}
						

				} else {
					MessageLayer("该活动类型只能选择单商品，是否替换？", function() {
						appendgoods(0);
						if($("#goodstype").find("option:selected").attr("data-price")!=undefined){
							price = $("#goodstype").find("option:selected").attr("data-price")-0
							var priceStr ="原价："+ price +"元";

							if($("#activityType").val()=="1"){
								
								$("#tgDiv").html(priceStr);
							}else if($("#activityType").val()=="2"){
								$("#msDiv").html(priceStr);
							}else if($("#activityType").val()=="3"){
								$("#ljDiv").html(priceStr);
							}
						}

					})
				}
			} else if(activityType == 0 || activityType == 4) { //多商品1
				appendgoods(1);

			}else if(activityType == 5){
				appendgoods(2);
			}
			//console.log(checkGoods0)
			//console.log(checkGoods1)
			//console.log(checkGoods2)
			//console.log(checkGoods3)
			

		})
		function ruleDivInit(){
			$("#classificationOne").html("<option value='-1'>--一级分类--</option>");
			$("#classificationTwo").html("<option value='-1'>--二级分类--</option>");
			$("#goods").html("<option value='-1'>--商品--</option>");
			$("#goodstype").html("<option value='-1'>--规格--</option>");
			
			$("#goodstype").parent().removeClass("hidden");
			var amin = $("#activityType").val() - 0;
			activityType = amin;
			switch(amin) {
				case 0:
					laysuccess("请在折扣处输入以0开始的两位小数");
					classificationOneAppend(selectDataNotPresell);
					$(".rule-div").html('<div class="col-xs-7">折扣：<input type="text" name="box1" id="box1" placeholder="折扣(如:88折填写0.88)" style="margin-right: 10px;" onkeyup="pressSmallNum(this)" />单人最大购买数量：<input type="text" id="box2" name="box2" placeholder="单人最大购买数量" onkeyup="pressInteger(this)" maxlength="9"/></div>');
					break;
				case 1:
					classificationOneAppend(selectDataNotPresell);
					$(".rule-div").html(`<label id="tgDiv" class="col-xs-8 control-label">请先选择商品</label>
							<div class="col-xs-4"></div>
							<div class="col-xs-8">
							团购价：<input type="text" name="box1" id="box1" placeholder="团购价格" onkeyup="pressMoney(this)" />
							单人最大购买数量：<input type="text" id="box2" name="box2" placeholder="单人最大购买数量" onkeyup="pressInteger(this)" maxlength="9"/>
						</div>`);
					
					//$(".rule-div").html('<label id="tgDiv" class="col-xs-2 control-label text-center">请先选择商品</label><div  class="col-xs-4"><div class="col-xs-12"><label class="col-xs-6 text-right">团购价：</label><input type="text" name="box1" id="box1" placeholder="团购价格" onkeyup="pressMoney(this)" /></div><div class="col-xs-12"> <label class="col-xs-6 text-right">单人最大购买数量：</label><input type="text" id="box2" name="box2" placeholder="单人最大购买数量" onkeyup="pressInteger(this)" maxlength="11"/></div></div>');
					break;
				case 2:
					classificationOneAppend(selectDataNotPresell);
					$(".rule-div").html(`<label id="msDiv" class="col-xs-8 control-label">请先选择商品</label>
							<div class="col-xs-4"></div>
							<div class="col-xs-8">
							秒杀价：<input type="text" name="box1" id="box1"  placeholder="秒杀价格" onkeyup="pressMoney(this)"  />
							单人最大购买数量：<input type="text" id="box2" name="box2" placeholder="单人最大购买数量" onkeyup="pressInteger(this)"  maxlength="9"/>
						</div>`);
					
					//$(".rule-div").html('<label id="msDiv" class="col-xs-2 control-label text-center">请先选择商品</label><div class="col-xs-4"><div class="col-xs-12"><label class="col-xs-6 text-right">秒杀价：</label><input type="text" name="box1" id="box1"  placeholder="秒杀价格" onkeyup="pressMoney(this)"  /></div><div class="col-xs-12"><label class="col-xs-6 text-right">单人最大购买数量：</label><input type="text" id="box2" name="box2" placeholder="单人最大购买数量" onkeyup="pressInteger(this)"  maxlength="11"/></div></div>');
					break;
				case 3:
					classificationOneAppend(selectDataNotPresell);
					$(".rule-div").html(`<label id="ljDiv" class="col-xs-8 control-label">请先选择商品</label>
							<div class="col-xs-4"></div>
							<div class="col-xs-8">
							立减价：<input type="text" name="box1" id="box1"  placeholder="立减金额" onkeyup="pressMoney(this)"  />
							单人最大购买数量：<input type="text" name="box2" id="box2" placeholder="单人最大购买数量" onkeyup="pressInteger(this)"  maxlength="9"/>
						</div>`);
					//$(".rule-div").html('<label id="ljDiv" class="col-xs-2 control-label text-center">请先选择商品</label><div class="col-xs-4"><div class="col-xs-12"><label class="col-xs-6 text-right">立减价：</label><input type="text" name="box1" id="box1"  placeholder="立减金额" onkeyup="pressMoney(this)"  /></div><div class="col-xs-12"><label class="col-xs-6 text-right">单人最大购买数量：</label><input type="text" name="box2" id="box2" placeholder="单人最大购买数量" onkeyup="pressInteger(this)"  maxlength="11"/></div></div>');
					
					/* $(".rule-div").html('<div class="col-xs-7">立减金额：<input type="text" name="box1" id="box1"  placeholder="立减金额" onkeyup="pressMoney(this)"  />单人最大购买数量：<input type="text" name="box2" id="box2" placeholder="单人最大购买数量" onkeyup="pressInteger(this)" /></div>'); */
					break;
				case 4:
					classificationOneAppend(selectDataNotPresell);
					$(".rule-div").html('<div class="col-xs-4 fullcut"><div class="form-group"><div class="input-group"><div class="input-group-addon">满</div><input type="text" class="form-control" name="box1" id="box1" onkeyup="pressMoney(this)" ><div class="input-group-addon" style="border-left: 0;border-right: 0;">减</div><input type="text" class="form-control" id="box2" name="box2" onkeyup="pressMoney(this)" ></div></div></div>');
					break;
				case 5:
					classificationOneAppend(selectDataPresell);
					$("#goodstype").parent().addClass("hidden");
					$(".rule-div").html('');
					break;
				default:
					$(".rule-div").html('<label class="col-xs-2 control-label">请先选择活动类型</label>');

			}
			if(amin!="-1"){
				$(".goods-list select").each(function(index,obj){
					$(this).attr("disabled",false);
				})
			}else{
				$(".goods-list select").each(function(index,obj){
					$(this).attr("disabled",true);
				})
			}
			/* $("#classificationOne").val(-1);
			$("#classificationTwo").val(-1);
			$("#goods").val(-1);
			$("#goodstype").val(-1); */
			
			
			
			classificationOne = {
					id: "",
					state: "3",
					goodsname: "-1"
				};
			classificationTwo = {
					id: "",
					state: "2",
					goodsname: "-1"
				};
			goods = {
					id: "",
					state: "1",
					goodsname: "-1"
				};
			goodstype = {
					id: "",
					state: "0",
					goodsname: "-1"
				};
		}
		

		function appendgoods(num) {
			var $goods;
			if(num == 0) { //单商品
				if(goodstype == undefined || goodstype["goodsname"] == '-1' || goodstype["id"] == "") {
					if(classificationOne == undefined || classificationOne["goodsname"] == '-1' || classificationOne["id"] == ""){
						laywarn("请先选择商品。");
					}else{
						laywarn("该活动类型只能选单商品，请选择单个商品。");
					}
					//					break;
				} else {
					$(".goods-list ul").html("");
					$goods = '<li data-id="' + goodstype["id"] + '" data-state="' + goodstype["state"] + '">' + goods["goodsname"] + '(' + goodstype["goodsname"] + ')<div onclick="delli(this)" class="delli-div"><i class="fa fa-times"></i></div></li>';
					$(".goods-list ul").append($goods);
				}
			} else if(num == 1) { //多商品
				if(goodstype["goodsname"] != '-1') {
					if(checkgoodsA()) {
						$goods = '<li data-id="' + goodstype["id"] + '" data-state="' + goodstype["state"] + '">' + goods["goodsname"] + '(' + goodstype["goodsname"] + ')<div onclick="delli(this)" class="delli-div"><i class="fa fa-times"></i></div></li>';
						checkGoods0.push({
							"goodstypeid": goodstype["id"],
							"goodsid": goods["id"],
							"classificationTwoid": classificationTwo["id"],
							"classificationOneid": classificationOne["id"]
						});
					} else {
						laywarn("请勿选择重复商品");
					}

				} else {
					if(goods["goodsname"] != '-1') {
						if(checkgoodsB()) {
							$goods = '<li data-id="' + goods["id"] + '" data-state="' + goods["state"] + '">' + goods["goodsname"] + '<div onclick="delli(this)" class="delli-div"><i class="fa fa-times"></i></div></li>';
							checkGoods1.push({
								"goodsid": goods["id"],
								"classificationTwoid": classificationTwo["id"],
								"classificationOneid": classificationOne["id"]
							});
						} else {
							laywarn("请勿选择重复商品");
						}

					} else {
						if(classificationTwo["goodsname"] != '-1') {
							if(checkgoodsC()) {
								$goods = '<li data-id="' + classificationTwo["id"] + '" data-state="' + classificationTwo["state"] + '">' + classificationTwo["goodsname"] + '<div onclick="delli(this)" class="delli-div"><i class="fa fa-times"></i></div></li>';
								checkGoods2.push({
									"classificationTwoid": classificationTwo["id"],
									"classificationOneid": classificationOne["id"]
								});
							} else {
								laywarn("请勿选择重复商品");
							}

						} else {
							if(classificationOne["goodsname"] != '-1') {
								if(checkgoodsD()) {
									$goods = '<li data-id="' + classificationOne["id"] + '" data-state="' + classificationOne["state"] + '">' + classificationOne["goodsname"] + '<div onclick="delli(this)" class="delli-div"><i class="fa fa-times"></i></div></li>';
									checkGoods3.push({
										"classificationOneid": classificationOne["id"]
									});
								} else {
									laywarn("请勿选择重复商品");
								}

							} else {
								laywarn("请先选择商品。");
							}
						}
					}
				}
				$(".goods-list ul").append($goods);
				/*console.log(checkGoods0);
				console.log(checkGoods1);
				console.log(checkGoods2);
				console.log(checkGoods3);*/
			}else if(num == 2) {
				if(goods["goodsname"] != '-1'){
					if(checkgoodsB()) {
						$goods = '<li data-id="' + goods["id"] + '" data-state="' + goods["state"] + '">' + goods["goodsname"] + '<div onclick="delli(this)" class="delli-div"><i class="fa fa-times"></i></div></li>';
						checkGoods1.push({
							"goodsid": goods["id"],
							"classificationTwoid": classificationTwo["id"],
							"classificationOneid": classificationOne["id"]
						});
						$(".goods-list ul").append($goods);
					} else {
						laywarn("请勿选择重复商品");
					}
				}else{
					layfail("请完善商品信息");
				}
			}

		}

		function checkgoodsA() { //最后一个分类有值（type）
			var res = true;
			var obj = {
				"goodstypeid": goodstype["id"],
				"goodsid": goods["id"],
				"classificationTwoid": classificationTwo["id"],
				"classificationOneid": classificationOne["id"]
			};
			$(checkGoods0).each(function(i, dom) {
				if(dom["goodstypeid"] == goodstype["id"]) {
					res = false;
				}
			});
			$(checkGoods1).each(function(i, dom) {
				if(dom["goodsid"] == goods["id"]) {
					res = false;
				}
			});
			$(checkGoods2).each(function(i, dom) {
				if(dom["classificationTwoid"] == classificationTwo["id"]) {
					res = false;
				}
			});
			$(checkGoods3).each(function(i, dom) {
				if(dom["classificationOneid"] == classificationOne["id"]) {
					res = false;
				}
			});
			return res;
		}

		function checkgoodsB() { //倒数第二个有值(goods)
			var res = true;
			var obj = {
				"goodsid": goods["id"],
				"classificationTwoid": classificationTwo["id"],
				"classificationOneid": classificationOne["id"]
			};
			$(checkGoods0).each(function(i, dom) {
				if(dom["goodsid"] == goods["id"]) {
					res = false;
				}
			});
			$(checkGoods1).each(function(i, dom) {
				if(dom["goodsid"] == goods["id"]) {
					res = false;
				}
			});
			$(checkGoods2).each(function(i, dom) {
				if(dom["classificationTwoid"] == classificationTwo["id"]) {
					res = false;
				}
			});
			$(checkGoods3).each(function(i, dom) {
				if(dom["classificationOneid"] == classificationOne["id"]) {
					res = false;
				}
			});
			return res;
		}

		function checkgoodsC() { //倒数第三个有值(classificationTwo)
			var res = true;
			var obj = {
				"classificationTwoid": classificationTwo["id"],
				"classificationOneid": classificationOne["id"]
			};
			$(checkGoods0).each(function(i, dom) {
				if(dom["classificationTwoid"] == classificationTwo["id"]) {
					res = false;
				}
			});
			$(checkGoods1).each(function(i, dom) {
				if(dom["classificationTwoid"] == classificationTwo["id"]) {
					res = false;
				}
			});
			$(checkGoods2).each(function(i, dom) {
				if(dom["classificationTwoid"] == classificationTwo["id"]) {
					res = false;
				}
			});
			$(checkGoods3).each(function(i, dom) {
				if(dom["classificationOneid"] == classificationOne["id"]) {
					res = false;
				}
			});
			return res;
		}

		function checkgoodsD() { //倒数第四个有值(classificationOne)
			var res = true;
			var obj = {
				"classificationOneid": classificationOne["id"]
			};
			$(checkGoods0).each(function(i, dom) {
				if(dom["classificationOneid"] == classificationOne["id"]) {
					//console.log(1);
					res = false;
				}
			});
			$(checkGoods1).each(function(i, dom) {
				if(dom["classificationOneid"] == classificationOne["id"]) {
					//console.log(2);
					res = false;
				}
			});
			$(checkGoods2).each(function(i, dom) {
				if(dom["classificationOneid"] == classificationOne["id"]) {
					//console.log(3);
					res = false;
				}
			});
			$(checkGoods3).each(function(i, dom) {
				if(dom["classificationOneid"] == classificationOne["id"]) {
					//console.log(4);
					res = false;
				}
			});
			return res;
		}

		function getgoods($select, type) {
			$select.change(function() {
				var goodsname = $(this).val();
				var state = $(this).attr("data-state");
				var id = $(this).find("option:selected").attr("data-id");
				var index = $("#classificationOne").find("option:selected").attr("data-index");
				var twoIndex=-1;
				var mygoods = {
					"id": id,
					"state": state,
					"goodsname": goodsname
				};
				//console.log(mygoods);
				switch(type) {
					case 1:
						classificationOne = mygoods;
						
						/* 二级分类下拉框赋值 */
						$("#classificationTwo").empty();
						$("#classificationTwo").append('<option value="-1" selected="selected">--二级分类--</option>');
						$("#goods").empty();
						$("#goods").append('<option value="-1" selected="selected">--商品--</option>');
						$("#goodstype").empty();
						$("#goodstype").append('<option value="-1" selected="selected">--规格--</option>');
						
							 classificationTwo = {
								id: "",
								state: "2",
								goodsname: "-1"
							};
							 goods = {
								id: "",
								state: "1",
								goodsname: "-1"
							};
							 goodstype = {
								id: "",
								state: "0",
								goodsname: "-1"
							};
						
						<%-- $.ajax({
							url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/getClassificationByParentId' ,
							type : "POST",
							dataType : "json",
							data: {
								"parentId" : classificationOne["id"]-0
							},
							success : function(data) {
								
								for ( var i = 0; i < data.length; i++) {
									var option = $("<option  data-id="+data[i].id+" value='"+data[i].name+"'>"+ data[i].name +"</option>");
									$("#classificationTwo").append(option);
								}
							}
						}); --%>
						
						if(goodsname!="-1"){
							var data = selectData[index]["twoClassifications"];
							for ( var i = 0; i < data.length; i++) {
								var option = $("<option data-index="+i+" data-id="+data[i].id+" value='"+data[i].name+"'>"+ data[i].name +"</option>");
								$("#classificationTwo").append(option);
							} 
						}
						
						
					
						break;
					case 2:
						classificationTwo = mygoods;
						/* 商品下拉框赋值 */
						$("#goods").empty();
						$("#goods").append('<option value="-1" selected="selected">--商品--</option>');
						$("#goodstype").empty();
						$("#goodstype").append('<option value="-1" selected="selected">--规格--</option>');
						
								 goods = {
									id: "",
									state: "1",
									goodsname: "-1"
								};
								 goodstype = {
									id: "",
									state: "0",
									goodsname: "-1"
								};
						<%-- $.ajax({
							url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/getGoodsDetailsByClassificationId' ,
							type : "POST",
							dataType : "json",
							data: {
								"classificationId" : classificationTwo["id"]-0
							},
							success : function(data) {
								
								for ( var i = 0; i < data.length; i++) {
									var option = $("<option  data-id="+data[i].id+" value='"+data[i].name+"'>"+ data[i].name +"</option>");
									$("#goods").append(option);
								}
							}
						}); --%>
						
						if(goodsname!="-1"){
							twoIndex=$("#classificationTwo").find("option:selected").attr("data-index");
							var data = selectData[index]["twoClassifications"][twoIndex]["goodsDetails"];
							for ( var i = 0; i < data.length; i++) {
								var option = $("<option data-index="+i+"  data-id="+data[i].id+" value='"+data[i].name+"'>"+ data[i].name +"</option>");
								$("#goods").append(option);
							}
						}
						break;
					case 3:
						goods = mygoods;
						/* 规格下拉框赋值 */
						$("#goodstype").empty();
						$("#goodstype").append('<option value="-1" selected="selected">--规格--</option>');
								 goodstype = {
									id: "",
									state: "0",
									goodsname: "-1"
								};
						<%-- $.ajax({
							url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/selectGoodsSpecificationDetailsByGoodsId' ,
							type : "POST",
							dataType : "json",
							data: {
								"goodsId" : goods["id"]-0
							},
							success : function(data) {
								
								for ( var i = 0; i < data.length; i++) {
									var option = $("<option  data-price="+data[i].price+" data-id="+data[i].id+" value='"+data[i].specifications+"'>"+ data[i].specifications +"</option>");
									$("#goodstype").append(option);
								}
							}
						}); --%>
						if(goodsname!="-1"){
							twoIndex=$("#classificationTwo").find("option:selected").attr("data-index");
							var goodIndex=$("#goods").find("option:selected").attr("data-index");
							var data = selectData[index]["twoClassifications"][twoIndex]["goodsDetails"][goodIndex]["goodsSpecificationDetails"];
							for ( var i = 0; i < data.length; i++) {
								var option = $("<option  data-price="+data[i].price+" data-id="+data[i].id+" value='"+data[i].specifications+"'>"+ data[i].specifications +"</option>");
								$("#goodstype").append(option);
							} 
						}
						break;
					case 4:
						
						goodstype = mygoods;
						
						break;
				}
			})

		}

		function delli(thisdelli) {
			$(thisdelli).parent().remove();
			var num = $(thisdelli).parent().attr("data-state") - 0;
			var obj = {
				"goodstypeid": goodstype["id"],
				"goodsid": goods["id"],
				"classificationTwoid": classificationTwo["id"],
				"classificationOneid": classificationOne["id"]
			};
			switch(num) {
				case 0:
					$(checkGoods0).each(function(i, dom) {
						if(dom["goodstypeid"] == $(thisdelli).parent().attr("data-id")) {
							checkGoods0.splice(i, 1);
						}
					});
					break;
				case 1:
					$(checkGoods1).each(function(i, dom) {
						if(dom["goodsid"] == $(thisdelli).parent().attr("data-id")) {
							checkGoods1.splice(i, 1);
						}
					});
					break;
				case 2:
					$(checkGoods2).each(function(i, dom) {
						if(dom["classificationTwoid"] == $(thisdelli).parent().attr("data-id")) {
							checkGoods2.splice(i, 1);
						}
					});
					break;
				case 3:
					$(checkGoods3).each(function(i, dom) {
						if(dom["classificationOneid"] == $(thisdelli).parent().attr("data-id")) {
							checkGoods3.splice(i, 1);

						}
					});
					break;
				case 4:
					$(couponObj).each(function(i, dom) {
						if(dom["id"]== $(thisdelli).parent().attr("data-id")) {
							couponObj.splice(i, 1);
						}
					});
					break;
			}

		}

		function countli() { //获取商品id.state
			$("#goodsidStr").val("");
			$("#stateStr").val("");
			$("#couponidStr").val("");
			goodsStatestr="";
			goodsIdstr="";
			couponIdStr="";
			$(".goods-list ul li").each(function() {
				goodsStatestr = goodsStatestr + $(this).attr("data-state") + ",";
				goodsIdstr = goodsIdstr + $(this).attr("data-id") + ",";
				//console.log(goodsIdstr);
			});
			$(".coupon-list ul li").each(function() {
				couponIdStr = couponIdStr + $(this).attr("data-id") + ",";
				
			});
			goodsIdstr = goodsIdstr.substring(0, goodsIdstr.length - 1);
			goodsStatestr = goodsStatestr.substring(0, goodsStatestr.length - 1).replace(/3/g, "2");
			couponIdStr=couponIdStr.substring(0, couponIdStr.length - 1);
			$("#goodsidStr").val(goodsIdstr);
			$("#stateStr").val(goodsStatestr);
			$("#couponidStr").val(couponIdStr);
			
			//console.log(goodsIdstr);
			//console.log(goodsStatestr);
			//console.log(couponIdStr);
		}

		/*商品列表 end*/

		/*添加优惠券 begin*/
		var couponObj = [];//优惠券
		var couponIdStr="";//所有的优惠券
		$("#couponAddBtn").click(function() {
				var flag = true;
				var couponname = $("#couponSelect").val();
				var id = $("#couponSelect").find("option:selected").attr("data-id");
				var $coupon = '<li data-id="' + id  + '">' + couponname + '<div onclick="delli(this)" class="delli-div"><i class="fa fa-times"></i></div></li>';
				
				if(couponname=="-1"){
					laywarn("请先选择优惠券");
				}else{
					$(couponObj).each(function(i, dom) {
					if(dom["id"]== id) {
						flag = false;
					}
				});
				if(flag) {
					couponObj.push({
						"id": id
					});
					$(".coupon-list ul").append($coupon);
				} else {
					laywarn("请勿选择重复优惠券");
				}
				}
			})
			/*添加优惠券 end*/

		/*时间插件*/
		laydateTwo('#activitytime', function() {
				$("#beginValidityTime").val(splitStr(time)[0])
				$("#endValidityTime").val(splitStr(time)[1])
			/* console.log(splitStr(time)[0]);
			console.log(splitStr(time)[1]); */
			
		});

		
		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});

	

		

		

		
	</script>

</html>