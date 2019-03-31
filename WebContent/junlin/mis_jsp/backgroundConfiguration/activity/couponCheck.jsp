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
		<title>优惠券审核</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		
		<style>
			
			#lookDiv{
				padding-top: 20px;
			}
			 .text-red{
			 color:#9e2020;
			 }
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">优惠券审核</h4>
					<div class="search-div clearfix">
						<div class="clearfix">
							<span class="l_f"> 
							编号： <input type="text" value="" id="query_identifier" onblur="cky(this)"/>
						</span>
							<span class="l_f"> 
							名称： <input type="text" value="" id="query_name" onblur="cky(this)"/>
						</span>
							<span class="l_f"> 
							创建人姓名： <input type="text"  value="" id="query_operatorIdentifier" onblur="cky(this)"/>
						</span>
						<span class="l_f"> 
							创建时间： <input type="text"  value="" id="search_operatorTime" placeholder="请选择时间" readonly=""  />
						</span>
							<span class="r_f"> 
							<input type="button" id="btn_search"  class="btncss btn_search" value="搜索" />
						</span>
						</div>

					</div>
					<div class="opration-div clearfix">
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="couponRefuse('驳回选中优惠券',1)" style="margin-right: 0;"><i class="fa fa-times"></i> 驳回</button>
							
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="couponPass('通过选中优惠券',1)"><i class="fa fa-check"></i> 通过</button>
						</span>
						<span class="jl_f_l">
							<input type="checkbox" onclick="checkboxController(this)"   name=""  id="checkAll" style="margin:0 5px 0 0;"/>
						</span>
						<span class="jl_f_l">
							<label for="checkAll">全选</label>
						</span>

					</div>
					<div class="table_menu_list">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>选择</th>
									<th>编号</th>
									<th>名称</th>
									<th>金额(元)</th>
									<th>数量/已领取数量(张)</th>
									<th>门槛(元)</th>
									<th>有效期</th>
									<th>领取时间</th>
									<th>规则</th>
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
									<td>粮食</td>
									<td>100</td>
									<td>100</td>
									<td>200</td>
									<td>2017.12.02 - 2018.01.20</td>
									<td>2017.10.02</td>
									<td>一人一张</td>
									<td>失效</td>
									<td>
										<input type="button" class="btncss edit" onclick="couponLook()" value="详情" />
										<input type="button" class="btncss edit" onclick="couponEdit()" value="编辑" />
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="id" checked="checked" />
									</td>
									<td>
										00100101
									</td>
									<td>粮食</td>
									<td>100</td>
									<td>100</td>
									<td>200</td>
									<td>2017.12.02 - 2018.01.20</td>
									<td>2017.10.02</td>
									<td>一人一张</td>
									<td>通过</td>
									<td>
										<input type="button" class="btncss edit" onclick="couponLook()" value="详情" />
										<input type="button" class="btncss edit" onclick="couponEdit()" value="编辑" />
									</td>
								</tr> -->

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		<!--详情-->
		<div id="lookDiv" style="display: none;">
			<form class="container">
			<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">编号：</label>
					<div class="col-xs-4" id="view_identifier">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">名称：</label>
					<div class="col-xs-4" id="view_name">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">金额：</label>
					<div class="col-xs-4" id="view_price">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">数量/已领取数量：</label>
					<div class="col-xs-4" id="view_total">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">门槛：</label>
					<div class="col-xs-4" id="view_useLimit">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">有效期：</label>
					<div class="col-xs-4" id="view_validatetime">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">领取时间：</label>
					<div class="col-xs-4" id="view_gettime">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">领取规则：</label>
					<div class="col-xs-4" id="view_rules">
						
					</div>
				</div>
			</form>

		</div>

		
	
	</body>

	<script>
	
	
	var contextPath = "${pageContext.request.contextPath}";
	var oTable1;
	//页面赋值
	var rowData_g={};
	$("#pass_btn").click(function(){
		couponPass("通过此优惠券",0,rowData_g);
	});
	$("#refuse_btn").click( function(){
		couponRefuse("驳回此优惠券",0,rowData_g);
	});
	
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
							"flag":2,
							"identifier": $("#query_identifier").val(),
							"name": $("#query_name").val(),
							"operatorIdentifier": $("#query_operatorIdentifier").val(),
							"operatorTime": $("#search_operatorTime").val()
							
						});
					},
					"url": "<%=basePath%>backgroundConfiguration/activity/couponInformation/dataTables"
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
						"sWidth": "15%",
						"sClass": "center"
					}, {
						"mData": "name",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center"
						
					}, {
						"mData": "price",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center"
					}, {
						"mData": "total",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							return data+"/"+(row.total-row.count)
						}
						
					},
					{
						"mData": "useLimit",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center"
					}, {
						"mData": "endValidityTime",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							
							var  date = getSmpFormatDateByLong(row.beginValidityTime, false)+"~"+getSmpFormatDateByLong(data, false);
							return	date;
						}

					},{
						"mData": "endTime",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							
							var  date = getSmpFormatDateByLong(row.beginTime, false)+"~"+getSmpFormatDateByLong(data, false);
							return	date;
						}

					},{
						"mData": "rules",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							
							switch (data) {
							case 0:
								return '注册返券';
								break;
							case 1:
								return '领券';
								break;
							case 2:
								return	'购物返券';
								break;
							default:
								break;
							}
						}
					},{
						"mData": "id",
						"bSortable": false,
						"sWidth": "20%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							var pass="通过此优惠券";
							var refuse="驳回此优惠券";
							return '<td><input type="button" class="btncss edit" onclick=\'couponLook(' + JSON.stringify(row) + ')\' value="详情" />'+
								   '<button type="button" class="btncss jl-btn-importent" onclick=\'couponPass("'+pass+'",0,' + JSON.stringify(row) + ')\'>通过</button>'+
								   '<button type="button" class="btncss jl-btn-defult" onclick=\'couponRefuse("'+refuse+'",0,' + JSON.stringify(row) + ')\'>驳回</button></td>';	
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
	
	
	/*时间插件*/
	laydateTwo('#validatetime',function(){
	
	});
	laydateTwo('#gettime',function(){
	
	});
	
		/*详情*/
		function couponLook(data){
			$("#view_identifier").html(data.identifier);
			$("#view_name").html(data.name);
			$("#view_price").html(data.price+" 元");
			$("#view_total").html(data.total+"/"+(data.total-data.count)+" 张");
			$("#view_useLimit").html(data.useLimit+" 元");
			var bTime=getSmpFormatDateByLong(data.beginValidityTime, false);
			var eTime=getSmpFormatDateByLong(data.endValidityTime, false);
			$("#view_validatetime").html(bTime+" ~ "+eTime);
			var bTime1=getSmpFormatDateByLong(data.beginTime, false);
			var eTime1=getSmpFormatDateByLong(data.endTime, false);
			$("#view_gettime").html(bTime1+" ~ "+eTime1);
			switch (data.rules) {
			case 0:
				$("#view_rules").html('注册返券');
				break;
			case 1:
				$("#view_rules").html('领券');
				break;
			case 2:
				$("#view_rules").html('购物返券');
				break;
			default:
				break;
			}
			
			
			layer.open({
				type: 1,
				title: "优惠券详情",
				closeBtn: 1,
				area: ['1000px', '600px'],
				content: $("#lookDiv"),
				btn: ['关闭'],
				yes: function(index) {
					layer.close(index);
				}
			});
		}

	

		/*
		 * 本方法用于通过活动。
		 * 传入字段：str，提示文字,type,多选还是当前优惠券(1:多选,0:当前优惠券)
		 */
		function couponPass(str,type,rowData) {
			
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
				url :'<%=basePath%>backgroundConfiguration/activity/couponInformation/updateStateByIds',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: {
					"ids" : ids,
					"state" : 0
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
		 * 本方法用于驳回优惠券。
		 * 传入字段：str，提示文字,type,多选还是当前优惠券(1:多选,0:当前优惠券)
		 */
		function couponRefuse(str,type,rowData){
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
					url :'<%=basePath%>backgroundConfiguration/activity/couponInformation/updateStateByIds',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data: {
						"ids" : ids,
						"state" : 5
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