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
		<title>低价活动审核</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		<script src="${pageContext.request.contextPath}/junlin/js/photoloads.js" type="text/javascript"></script>
		<style>
			#lookDiv {
				padding-top: 20px;
			}
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">低价活动审核</h4>
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
							<span class="l_f" style="display:none;"> 
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
							<button type="button" class="btncss jl-btn-defult" onclick="activityRefuse('拒绝选中活动',1)" style="margin-right: 0;"><i class="fa fa-times"></i> 拒绝</button>
							
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="activityPass('通过选中活动',1)"><i class="fa fa-check"></i> 通过</button>
						</span>
						<span class="jl_f_l">
							<input type="checkbox" onclick="checkboxController(this)"   name=""  id="checkAll" style="margin:0 5px 0 0;"/>
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
									<th>提交人</th>
									<th>提交时间</th>
									<th>操作</th>
								</tr>

							</thead>
							<tbody>
							</tbody>
						</table>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!--详情 -->
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
				<div class="form-group">
					<label class="col-xs-2 control-label">提交人：</label>
					<div class="col-xs-4" id="view_submitterIdentifier">
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">提交时间：</label>
					<div class="col-xs-4" id="view_submitTime">
						
					</div>
				</div>
				<div class="form-group editshow hidden" id="view_warning">
					<label class="col-xs-2 control-label" >预警：</label>
					<div  class="col-xs-4">
						有商品价格低于库存价格
					</div>
				</div>
				<div class="form-group change-hidden">
					<div class="col-xs-12 text-center">
						<button type="button" class="btncss jl-btn-importent" id="pass_btn" >通过</button>
						<button type="button" class="btncss jl-btn-defult" id="refuse_btn">拒绝</button>
					</div>
				</div>
			</form>
		</div>


	</body>

	<script>
	
	var contextPath = "${pageContext.request.contextPath}";
	var oTable1;
	
	//查询
	$("#btn_search").click(function() {
		
		oTable1.fnDraw();
		$("#checkAll").attr("checked",false);
		
	});
	
	
	
	jQuery(function($) {
	
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
							"flag": 3,
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
						"sWidth": "5%",
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
						"sWidth": "10%",
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
					},{
						"mData": "submitterIdentifier",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							if(data!=null && data!="" && row.submitterName!=null && row.submitterName!=""){
								return data+"("+ row.submitterName +")";
							}else if(data!=null && data!=""){
								return data;
							}else{
								return "";
							}
						}
					}, {
						"mData": "submitTime",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							var  date = getSmpFormatDateByLong(data, true)
							return	date;
						}
					},{
						"mData": "state",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							var pass="通过此活动";
							var refuse="拒绝此活动";
							return '<td><input type="button" class="btncss edit" onclick=\'activityLook(' + JSON.stringify(row) + ')\' value="详情" />'+
								   '<button type="button" class="btncss jl-btn-importent" onclick=\'activityPass("'+pass+'",0,' + JSON.stringify(row) + ')\'>通过</button>'+
								   '<button type="button" class="btncss jl-btn-defult" onclick=\'activityRefuse("'+refuse+'",0,' + JSON.stringify(row) + ')\'>拒绝</button></td>';	
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
		
		//页面赋值
		var rowData_g={};
		$("#pass_btn").click(function(){
			activityPass("通过此活动",0,rowData_g);
		});
		$("#refuse_btn").click( function(){
			activityRefuse("拒绝此活动",0,rowData_g);
		});
		/*详情*/
		function activityLook(rowData) {
			rowData_g=rowData;
			if(rowData.state==1){
				$(".change-hidden").removeClass("hidden");
			}else{
				$(".change-hidden").addClass("hidden");
			}
			
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
						
						if(rowData.submitterIdentifier!=null && rowData.submitterIdentifier!="" && rowData.submitterName!=null && rowData.submitterName!=""){
							$("#view_submitterIdentifier").html(rowData.submitterIdentifier+"("+ rowData.submitterName +")");
						}else if(rowData.submitterIdentifier!=null && rowData.submitterIdentifier!=""){
							$("#view_submitterIdentifier").html(rowData.submitterIdentifier);
						}else{
							$("#view_submitterIdentifier").html("");
						}
						$("#view_submitTime").html(getSmpFormatDateByLong(rowData.submitTime, true));
						
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
							
							end: function(index){
								
								$(".view_goods-list ul").html("")
								$(".view_coupon-list ul").html("")
							
							}
						});
				
				}
			});
		}
		
		/*
		 * 本方法用于通过活动。
		 * 传入字段：str，提示文字,type,多选还是当前活动(1:多选,0:当前活动)
		 */
		function activityPass(str,type,rowData) {
			
			var ids=[];
			if(type==1){
				var boxes = $("input[name='id']");
				for(var i=0;i<boxes.length;i++){
			        if(boxes[i].checked == true){
			            ids.push(boxes[i].value);
			        }
			    }
			}else{
				var id = rowData.id;	
				ids.push(id);
			}
			
			if(ids.length<=0){
				laywarn("请选择数据!");
				return;
			}
			
			publicMessageLayer(str, function() {
				
				
				$.ajax({
				url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/updateState',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: {
					"ids" : ids,
					"flag" : 4
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
					layer.closeAll('page');
					
				}
			});
				

			})
		}
		
		/*
		 * 本方法用于拒绝活动。
		 * 传入字段：str，提示文字,type,多选还是当前活动(1:多选,0:当前活动)
		 */
		function activityRefuse(str,type,rowData){
			var ids=[];
			if(type==1){
				var boxes = $("input[name='id']");
				for(var i=0;i<boxes.length;i++){
			        if(boxes[i].checked == true){
			            ids.push(boxes[i].value);
			        }
			    }
			}else{
				var id = rowData.id;	
				ids.push(id);
			}
			
			if(ids.length<=0){
				laywarn("请选择数据!");
				return;
			}
			publicMessageLayer(str, function() {
				
				$.ajax({
					url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/updateState',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data: {
						"ids" : ids,
						"flag" : 3
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
						layer.closeAll('page');


					}
				});
				
				
				
			})
		}
		
		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
	</script>

</html>