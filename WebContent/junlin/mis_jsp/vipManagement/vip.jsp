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
		<title>VIP管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		
		<style>
			#editDiv {
				padding-top: 20px;
			}
			
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">用户列表</h4>
					<div class="search-div clearfix">
						<div class="clearfix">
							<span class="l_f"> 
							编号： <input type="text" value="" id="search_identifier" onkeyup="cky(this)"/>
						</span>
							<span class="l_f"> 
							姓名： <input type="text" value="" id="search_name" onkeyup="cky(this)"/>
						</span>
							<span class="l_f"> 
							手机号： <input type="text"  value="" id="search_tel" onkeyup="cky(this)"/>
						</span>
						<span class="l_f"> 
							创建人姓名： <input type="text"  value="" id="search_operatorIdentifier" onkeyup="cky(this)"/>
						</span>
							<span class="l_f"> 
							创建时间： <input type="text"  value="" id="search_operatorTime" placeholder="请选择时间" readonly=""  />
						</span>
						<span class="l_f" style="margin-left: 10px;"> 
							月交易额：  <input type="text"  value="" id="search_lowestPayMoney" placeholder="请输入交易额下限" onkeyup="this.value=this.value.replace(/[^\d.]/g, '').replace(/^\./g, '').replace('.', '$#$').replace(/\./g, '').replace('$#$', '.')" style="margin-right:32px;"/>-----<input style="margin-left:32px;" type="text"  value="" id="search_onlinePayMoney" placeholder="请输入交易额上限" onkeyup="this.value=this.value.replace(/[^\d.]/g, '').replace(/^\./g, '').replace('.', '$#$').replace(/\./g, '').replace('$#$', '.')"/>
						</span>
							<span class="r_f"> 
							<input type="button"  class="btncss btn_search" id="btn_search" value="搜索"  />
						</span>

						</div>

					</div>
					<div class="opration-div clearfix">
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="vipEduce()" style="margin-right: 0;"><i class="fa fa-download"></i> 导出</button>
							
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="vipDel()"><i class="fa fa-trash"></i> 删除</button>
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-importent" onclick="vipAdd()"><i class="fa fa-plus"></i> 新增</button>
						</span>
						<span class="jl_f_l">
							<input type="checkbox" id="checkAll" style="margin:0 5px 0 0;" onclick="checkboxController(this)"/>
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
										<th>姓名</th>
										<th>手机</th>
										<th>总交易金额/订单数</th>
										<th>月交易金额/订单</th>  
										<th>创建人</th>
										<th>创建时间</th>
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

		<!--新增 编辑 -->
		<div id="editDiv" style="display: none;">
			<form class="container">
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="viptel" class="col-xs-2 control-label">手机号&nbsp;<i class="text-danger">*</i></label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="viptel" onblur="viptelCheck(this)" onkeyup="cky(this)"/>
					</div>
				</div>
			</form>
		</div>

	</body>

	<script>
	
	var oTable1;
	$("#btn_search").click(function() {
		$("#checkAll").removeAttr("checked");
		oTable1.fnDraw();
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
							"isVIP": "1",
							"identifier": $("#search_identifier").val(),
							"name": $("#search_name").val(),
							"operatorIdentifierName": $("#search_operatorIdentifier").val(),
							"telphoneNo": $("#search_tel").val(),
							"lowestPayMoney":$("#search_lowestPayMoney").val(),
							"onlinePayMoney":$("#search_onlinePayMoney").val(),
							"isVipTime": $("#search_operatorTime").val()
						});
					},
					"url": "<%=basePath%>backgroundManagement/background/userManager/getUserMsg"
				},

				"aoColumns": [{
						"mData": "",
						"bSortable": true,
						"sWidth": "5%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							return '<td><input type="checkbox" name="user_identifier" value="' + row.identifier + '" onclick="checkboxClick(\'#checkAll\')"/></td>';
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
						"sClass": "center",

					}, {
						"mData": "phone",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center"
					}, {
						"mData": "historyPayMoney",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center",
						"mRender": function(data,type,row) {
							if(data!=null&&data!=""){
								return (data).toFixed(2)+"/"+row.historyPayNum;
							}
							else{
								return "0";
							}
							
						}
					}, {
						"mData": "monthHistoryPayMoney",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center",
						"mRender": function(data,type,row) {
							if(data!=null&&data!=""){
								return (data).toFixed(2)+"/"+row.monthHistoryPayNum;
							}
							else{
								return "0";
							}
						}
					}, {
						"mData": "operatorIdentifier",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center",
						"mRender": function(data,type,row) {
							if(data!=null){
								if(row.operatorName!=null){
									return data+"("+row.operatorName+")";
								}
								else{
									return data;
								}
								
							}
							else{
								return "";
							}
						}
					}, {
						"mData": "isVipTime",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center",
						"mRender": function(data) {
							if(data!=null&&data!=""){
								return getSmpFormatDateByLong(data, true);
							}
							else{
								return "";
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
	
	
		/*正则验证*/
		var viptel = false;

		function viptelCheck(thisinput) {
			viptel = checkMobilePhone($(thisinput).val());
			if(!isnotEmpty($(thisinput).val())) {
				laywarn("请输入手机号");
			} else if(!viptel) {
				laywarn("用户手机格式错误");
			}
			return viptel;
		}
		
		/*新增*/
		function vipAdd() {
			layer.open({
				type: 1,
				title: "新增VIP",
				closeBtn: 1,
				area: ['800px', '180px'],
				content: $("#editDiv"),
				btn: ['提交', '取消'],
				yes: function(index) {

					if(viptelCheck("#viptel")) {
						 
						/*
						 *判断该手机号是否进行注册，只有注册的手机号才可进行新增操作。
						 */
						 $.ajax({
								url: '<%=basePath%>backgroundManagement/background/userManager/getUserByPhone',
								type: "POST",
								dataType: "json",
								async: false,
								cache: false,
								data: {
									"phone":$("#viptel").val()
								},
								success: function(data) {
									if(data.success) {
										//alert(data.user.identifier);
										$.ajax({
											url: '<%=basePath%>backgroundManagement/background/userManager/insertUserByPhone',
											type: "POST",
											dataType: "json",
											async: false,
											cache: false,
											data: {
												"phone":$("#viptel").val(),
												"identifier":data.user.identifier
											},
											success: function(data) {
												if(data.success) {
													laysuccess(data.msg);
													oTable1.fnDraw();
													$("#checkAll").removeAttr("checked");
												} else {
													layfail(data.msg);
												}

											}
										});
										layer.close(index);
									} else {
										layfail(data.msg);
									}

								}
							});					
					}

				},
				end: function(index, layero) {
					clearForm("editDiv");
				}
			});
		}
		/*删除*/
		function vipDel() {
			var str = "";
			$("input:checkbox[name='user_identifier']:checked").each(function() {
				str += $(this).val() + ",";
			})
			if(str == "") {
				laywarn("请选择数据!");
				return;
			}
			MessageLayer("是否取消下列选中用户的VIP资格？", function() {
				$.ajax({
					url: '<%=basePath%>backgroundManagement/background/userManager/operationVIPUser?isVip=0',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data: $("#datatable_form").serialize(),
					success: function(data) {
						if(data.success) {
							laysuccess(data.msg);
							oTable1.fnDraw();
							$("#checkAll").removeAttr("checked");
						} else {
							layfail(data.msg);
						}

					}
				});
			})
		}
		
		function vipEduce(){
			var export_identifier=encodeURI(encodeURI($("#search_identifier").val()));
			var export_name=encodeURI(encodeURI($("#search_name").val()));
			var export_operatorIdentifierName=encodeURI(encodeURI($("#search_operatorIdentifier").val()));
			var export_telphoneNo=encodeURI(encodeURI($("#search_tel").val()));
			var export_lowestPayMoney=encodeURI(encodeURI($("#search_lowestPayMoney").val()));
			var export_onlinePayMoney=encodeURI(encodeURI($("#search_onlinePayMoney").val()));
			var export_isVipTime=encodeURI(encodeURI($("#search_operatorTime").val()));
			publicMessageLayer("导出全部数据", function() {
				var URL="<%=basePath%>backgroundManagement/background/userManager/exportUserVIP?identifier="+export_identifier+"&name="+export_name+"&operatorIdentifierName="+export_operatorIdentifierName+"&telphoneNo="+export_telphoneNo+"&lowestPayMoney="+export_lowestPayMoney+"&onlinePayMoney="+export_onlinePayMoney+"&isVipTime="+export_isVipTime;
			    location.href=URL;
				return false;	
			});
		}
	
		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
	</script>
</html>