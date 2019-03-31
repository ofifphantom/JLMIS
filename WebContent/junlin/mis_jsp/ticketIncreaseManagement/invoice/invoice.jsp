<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%
	String path = request.getContextPath();
	String basePath = path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="utf-8" />
		<title>增票审核</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		
		<style>
			#editDiv {
				padding-top: 50px;
			}
			
			.imgbox img {
				width: 100%;
				border: 1px solid #ccc;
			}
			.container{
				max-width: 95%;
			}
			
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">增票审核</h4>
					<div class="search-div clearfix">
						<div class="clearfix">
							<span class="l_f"> 
							编号： <input type="text" id="query_identifier" value="" onblur="cky(this)" />
						</span>
							<span class="l_f"> 
							用户姓名： <input type="text" id="query_name" value="" onblur="cky(this)" />
						</span>
							<span class="l_f"> 
							手机号： <input type="text" id="query_phone" value="" onblur="cky(this)"/>
						</span>
							<span class="l_f"> 
							提交时间： <input type="text"  value="" id="search_operatorTime" placeholder="请选择时间" readonly=""  />
						</span>
							<span class="l_f"> 
							状态：<select  id="query_state">
								<option selected="selected" value="-1">--请选择--</option>
								<option  value="0">待审核</option>
								<option  value="2">已通过</option>
								<option  value="1">未通过</option>
							</select>
						</span>
							<span class="r_f"> 
							<input type="button" id="btn_search"  class="btncss btn_search" value="搜索" />
						</span>
						</div>

					</div>
					<div class="opration-div clearfix">

					</div>
					<div class="table_menu_list">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>编号</th>
									<th>用户姓名</th>
									<th>电话</th>
									<th>提交时间</th>
									<th>审核状态</th>
									<th>增票信息</th>
									<th width="14%">操作</th>
								</tr>

							</thead>
							<tbody>
								<!-- <tr>
									<td>1234578979</td>
									<td>李四</td>
									<td>14789654231</td>
									<td>2017-10-02&nbsp;&nbsp;22：10</td>
									<td>待审核</td>
									<td>
										<input type="button" class="btncss edit" onclick="order()" value="增票详情" />
									</td>
									<td>
										<input type="button" class="btncss edit" onclick="orderPass()" value="通过" />
										<input type="button" class="btncss edit" onclick="orderRefuse()" value="不通过" />
									</td>
								</tr>
								<tr>
									<td>1234578979</td>
									<td>李四</td>
									<td>14789654231</td>
									<td>2017-10-02&nbsp;&nbsp;22：10</td>
									<td>待审核</td>
									<td>
										<input type="button" class="btncss edit" onclick="order()" value="增票详情" />
									</td>
									<td>
										<input type="button" class="btncss edit" onclick="orderPass()" value="通过" />
										<input type="button" class="btncss edit" onclick="orderRefuse()" value="不通过" />
									</td>
								</tr>
								<tr>
									<td>1234578979</td>
									<td>李四</td>
									<td>14789654231</td>
									<td>2017-10-02&nbsp;&nbsp;22：10</td>
									<td>待审核</td>
									<td>
										<input type="button" class="btncss edit" onclick="order()" value="增票详情" />
									</td>
									<td>
										<input type="button" class="btncss edit" onclick="orderPass()" value="通过" />
										<input type="button" class="btncss edit" onclick="orderRefuse()" value="不通过" />
									</td>
								</tr> -->

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

		<!--详情 -->
		<div id="editDiv" style="display: none;">
			<form class="container">
				<div class="col-xs-6">
					<div class="form-group">
						<label class="col-xs-3 control-label">用户姓名：</label>
						<div class="col-xs-4" id="userName">
							
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">用户电话：</label>
						<div class="col-xs-4" id="phone">
							
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">单位名称：</label>
						<div class="col-xs-4" id="unitName">
							
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">纳税人识别码：</label>
						<div class="col-xs-4" id="taxpayerIdentificationNumber">
							
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">注册地址：</label>
						<div class="col-xs-9" id="registeredAddress">
							
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">注册电话：</label>
						<div class="col-xs-4" id="registeredTel">
							
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">开户银行：</label>
						<div class="col-xs-4" id="depositBank">
							
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">银行账户：</label>
						<div class="col-xs-4" id="bankAccount">
							
						</div>
					</div>
					
				</div>
				<div class="col-xs-6">
					<div class="form-group">
						<div class="col-xs-12"><span class="text-danger">*</span>&nbsp;税务登记副本</div>
						<div class="col-xs-12 imgbox" id="imgDiv">
							<img id="businessLicenseUrl" src="${pageContext.request.contextPath}/junlin/img/orderpj.jpg" />
						</div>
					</div>
				</div>

				<!--<div class="form-group">
					<div class="col-xs-12 text-center">
						<button type="button" class="btncss jl-btn-importent class-add">提交</button>
						<button type="button" class="btncss jl-btn-defult">取消</button>
					</div>
				</div>-->
			</form>

		</div>

	</body>

	<script>
	
	var contextPath = "${pageContext.request.contextPath}";
	var oTable1;
	
	//查询
	$("#btn_search").click(function() {
		
		oTable1.fnDraw();
		
	});
	
	
	
	jQuery(function($) {
	
		//datatable赋值
		oTable1 = $('#datatable')
			.dataTable({
				"aaSorting": [
					[6, "asc"]
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
							"identifier": $("#query_identifier").val(),
							"name": $("#query_name").val(),
							"phone": $("#query_phone").val(),
							"state" : $("#query_state").val()-0,
							"operatorTime": $("#search_operatorTime").val()
							
						});
					},
					"url": "<%=basePath%>orderManagement/invoice/vatInvoiceAptitude/dataTables"
				},

				"aoColumns": [{
						"mData": "identifier",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center"
					}, {
						"mData": "user.name",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center"
						
					}, {
						"mData": "user.phone",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center"
					}, {
						"mData": "operatorTime",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							var  date = getSmpFormatDateByLong(data, true);
							return	date;
						}
					},
					{
						"mData": "state",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							switch (data) {
							case 0:
								return '待审核';
								break;
							case 1:
								return '未通过';
								break;
							case 2:
								return	'已通过';
								break;
							default:
								break;
							}
							
						}
					}, {
						"mData": "id",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							return '<td><input type="button" class="btncss edit" onclick=\'order(' + JSON.stringify(row) + ')\' value="增票详情" /></td>';
						}

					},{
						"mData": "state",
						"bSortable": true,
						"sWidth": "20%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							switch (row.state) {
							case 0:
								return '<td><input type="button" class="btncss edit" onclick=\'orderPass(' + JSON.stringify(row) + ')\' value="通过" />'+
								'<input type="button" class="btncss edit" onclick=\'orderRefuse(' + JSON.stringify(row) + ')\' value="不通过" /></td>';
								break;
							case 1:
								return '<td><input type="button" class="btncss edit" disabled value="未通过" /></td>';
								break;
							case 2:
								return '<td><input type="button" class="btncss edit" disabled value="已通过" /></td>';
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
	
	
	
	
	
		/*查看详情*/
		function order(rowData) {
			$("#userName").html(rowData.user.name);
			$("#phone").html(rowData.user.phone);
			$("#unitName").html(rowData.unitName);
			$("#taxpayerIdentificationNumber").html(rowData.taxpayerIdentificationNumber);
			$("#registeredAddress").html(rowData.registeredAddress);
			$("#registeredTel").html(rowData.registeredTel);
			$("#depositBank").html(rowData.depositBank);
			$("#bankAccount").html(rowData.bankAccount);
			
			if(rowData.businessLicenseUrl!="" && rowData.businessLicenseUrl!=null){
				$("#imgDiv").html("<img id='businessLicenseUrl' src='${pageContext.request.contextPath}/junlin/img/orderpj.jpg' />");
				$("#businessLicenseUrl").attr("src",'<%=basePath%>'+rowData.businessLicenseUrl);
				
			}else{
				$("#imgDiv").html("<span class='alert-box'><span class='fa fa-bell-o'></span>&nbsp;&nbsp;用户未上传资质扫描件</span>");
			}
			layer.open({
				type: 1,
				title: "增票详情",
				closeBtn: 1,
				area: ['80%', '80%'],
				content: $("#editDiv"),
				btn: ['关闭'],
				yes: function(index) {
					layer.close(index);
				}
			});
		}

		/*通过*/
		function orderPass(rowData) {
			publicMessageLayer("通过该增票的审核", function() {
				
				$.ajax({
					url :'<%=basePath%>orderManagement/invoice/vatInvoiceAptitude/updateState',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data: {
						"identifier" : rowData.identifier,
						"id" : rowData.id,
						"state" : 2
					},
					
					traditional:true,
					success: function(data) {
						if(data.success) {
							laysuccess(data.msg);
							oTable1.fnDraw();
							
						} else {
							layfail(data.msg);
						}
						
						
					}
				});
				
			});
		}
		/*不通过*/
		function orderRefuse(rowData) {
			publicMessageLayer("不通过该增票的审核", function() {
				$.ajax({
					url :'<%=basePath%>orderManagement/invoice/vatInvoiceAptitude/updateState',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data: {
						"identifier" : rowData.identifier,
						"id" : rowData.id,
						"state" : 1
					},
					
					traditional:true,
					success: function(data) {
						if(data.success) {
							laysuccess(data.msg);
							oTable1.fnDraw();
							
						} else {
							layfail(data.msg);
						}
						
						
					}
				});
			});
		}
		
		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
	</script>

</html>