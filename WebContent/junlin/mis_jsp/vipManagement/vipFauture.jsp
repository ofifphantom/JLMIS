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
		<title>VIP管理----待添加列表</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>

		<style>
			#editDiv {
				padding-top: 20px;
			}
			
			#choosetree {
				overflow-y: hidden;
				border: 1px solid #ccc;
				color: #555555;
				padding: 20px 10px;
			}
			
			.search-div .btn_search {
				margin-right: 4px;
			}
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top: 10px;">
					<h4 class="text-title">待添加</h4>
					<div class="search-div clearfix">
						<div class="clearfix">
							<span class="l_f"> 起点交易总额： <input type="text" value=""
							id="search_historyPayMoney" onkeyup="this.value=this.value.replace(/[^\d.]/g, '').replace(/^\./g, '').replace('.', '$#$').replace(/\./g, '').replace('$#$', '.')"/>
						</span> <span class="l_f"> 交易时间： <input type="text" value=""
							id="activitytime" placeholder="请选择时间区间" readonly=""
							style="width: 190px;" />
						</span> <span class="l_f"> 总交易订单数： <input type="number" value=""
							id="search_historyPayNum" />
								<span class="l_f"> 
							创建时间： <input type="text"  value="" id="search_operatorTime" placeholder="请选择时间" readonly=""  />
						</span>

						</span> <span class="r_f"> <input type="button"
							class="btncss btn_search" id="btn_search" value="搜索" />
						</span>

						</div>

					</div>
					<div class="opration-div clearfix">
						<!--<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="vipDel()"><i class="fa fa-trash"></i> 删除</button>
						</span>-->
						<span class="r_f">
						<button type="button" class="btncss jl-btn-importent"
							onclick="vipAdd(1)" style="margin-right: 0;">
							<i class="fa fa-star"></i>设为VIP
						</button>
					</span> <span class="jl_f_l"> <input type="checkbox" id="checkAll"
						style="margin: 0 5px 0 0;" onclick="checkboxController(this)" />
					</span> <span class="jl_f_l"> <label for="checkAll">全选</label>
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
										<th>交易金额/订单数</th>
										<th>月交易金额/订单</th>
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
					<label for="viptel" class="col-xs-2 control-label">手机号</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="viptel" onblur="viptelCheck(this)" />
					</div>
				</div>
			</form>
		</div>

	</body>

	<script>
		var oTable1;
		var isSetStartHistoryMoney=false; //判断是否设置起点金额
		var startPayTime="";//交易开始时间
		var endPayTime="";//交易结束时间
		$("#btn_search").click(function() {
			if($("#activitytime").val()!=null&&$("#activitytime").val()!=""){
				startPayTime=splitStr($("#activitytime").val())[0];
				endPayTime=splitStr($("#activitytime").val())[1];
			}
			else{
				startPayTime="";
				endPayTime="";
			}
			
			if($("#search_historyPayMoney").val() != null && $("#search_historyPayMoney").val() != "") {
				isSetStartHistoryMoney=true;
			}
			else{
				isSetStartHistoryMoney=false;
			}
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
								"isVIP": "0",
								"historyPayMoney": $("#search_historyPayMoney").val(),
								"historyPayNum": $("#search_historyPayNum").val(),
								"startPayTime":startPayTime,
								"endPayTime":endPayTime,
								"operatorTime": $("#search_operatorTime").val()
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
						"mRender": function(data, type, row) {
							if(data != null && data != "") {
								return (data).toFixed(2) + "/" + row.historyPayNum;
							} else {
								return "0";
							}

						}
					}, {
						"mData": "monthHistoryPayMoney",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							if(data != null && data != "") {
								return (data).toFixed(2) + "/" + row.monthHistoryPayNum;
							} else {
								return "0";
							}
						}
					}],
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
		
		/*时间插件，实时获取触发时间*/
		laydateTwo_max0('#activitytime',function(){});
		
		/*新增*/
		function vipAdd() {
			var str = "";
			$("input:checkbox[name='user_identifier']:checked").each(function() {
				str += $(this).val() + ",";
			})
			if(str == "") {
				laywarn("请选择数据!");
				return;
			}
			if(!isSetStartHistoryMoney){
				MessageLayer("你还未根据起点交易额进行检索,确定进行设定操作？",function(){
					publicMessageLayer("设定选中账号为VIP", function() {
						$.ajax({
							url: '<%=basePath%>backgroundManagement/background/userManager/operationVIPUser?isVip=1',
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
				});
				<%-- layer.open({
					type: 1,
					title: "提示",
					closeBtn: 1,
					area: ['380px', '130px'],
					content: "<div class='text-center' style='line-height:40px'>你还未根据起点交易额进行检索,确定进行设定操作？</div>",
					btn: ['确认', '取消'],
					yes: function(index) {
						layer.close(index);
						publicMessageLayer("设定选中账号为VIP", function() {
							$.ajax({
								url: '<%=basePath%>backgroundManagement/background/userManager/operationVIPUser?isVip=1',
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
				}); --%>
			}
			else{
				publicMessageLayer("设定选中账号为VIP", function() {
					$.ajax({
						url: '<%=basePath%>backgroundManagement/background/userManager/operationVIPUser?isVip=1',
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
			
		}
		/*删除*/
		/*function vipDel() {
			publicMessageLayer("删除选中账号", function() {

				laysuccess("删除成功");
			})
		}*/
		
		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
	</script>

</html>