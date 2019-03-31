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
		<title>用户管理（管理员）</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>

		<!--引入JQuery EasyUI  核心UI 文件 CSS-->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/junlin/js/easyui-1.3.6/themes/default/easyui.css">
		<!--引入EasyUI图标文件-->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/junlin/js/easyui-1.3.6/themes/icon.css">
		<!--引入JQuery EasyUI  核心库 1.3.6版本-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/junlin/js/easyui-1.3.6/jquery.easyui.min.js"></script>
		<!--进入 EasyUI中文提示信息-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/junlin/js/easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>

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
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top: 10px;">
					<h4 class="text-title">用户管理</h4>
					<div class="search-div clearfix">
						<div class="clearfix">
							<span class="l_f"> 姓名： <input type="text" value=""
							id="search_name" onkeyup="cky(this)"/>
						</span> <span class="l_f"> 手机号： <input type="text" value=""
							id="search_tel" onkeyup="cky(this)"/>
						</span>
							<span class="l_f"> 
							创建时间： <input type="text"  value="" id="search_operatorTime" placeholder="请选择时间" readonly=""  />
						</span> 
						<span class="r_f">
							<button type="button" class="btncss jl-btn-importent"
								onclick="userAdd()" >
								<i class="fa fa-plus"></i> 新增
							</button>
						</span> <span class="r_f"> <input type="button"
							class="btncss jl-btn-defult" id="btn_search" value="搜索" />
						</span>

						</div>

					</div>
					<div class="opration-div clearfix"></div>
					<div class="table_menu_list">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>序号</th>
									<th>姓名</th>
									<th>登录名</th>
									<th>手机号</th>
									<th>用户组</th>
									<th>是否有效</th>
									<th>创建人</th>
									<th>创建时间</th>
									<th width="32%">操作</th>
								</tr>

							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

		<!--新增 编辑 -->
		<div id="editDiv" style="display: none;">
			<form class="container" id="addOrUpdateForm">
				<div class="form-group" style="display: none;">
					<div class="col-xs-2"></div>
					<label for="name" class="col-xs-2 control-label">主键</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="primaryKey" name="primaryKey" />
					</div>
				</div>
				<div class="form-group" style="display: none;">
					<div class="col-xs-2"></div>
					<label for="name" class="col-xs-2 control-label">编号</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="updateIdentifier" name="updateIdentifier" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="username" class="col-xs-2 control-label">姓名</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="username" name="name" onkeyup="cky(this)" maxlength="20"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="loginnname" class="col-xs-2 control-label">登录名</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="loginnname" name="loginName" onkeyup="cky(this)" maxlength="50"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="usertel" class="col-xs-2 control-label">手机号</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="usertel" name="phone" onkeyup="cky(this)" maxlength="11"/>
					</div>
				</div>
				<div class="form-group" id="pwdGroup">
					<div class="col-xs-2"></div>
					<label for="userpwd" class="col-xs-2 control-label">密码</label>
					<div class="col-xs-4">
						<input type="password" class="form-control" id="userpwd" name="password" onkeyup="cky(this)" maxlength="50"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="power" class="col-xs-2 control-label">用户组</label>
					<div class="col-xs-4">
						<select class="form-control" id="power" name="userGroup">
							<option value="-1" selected="selected">--请选择--</option>
							<option value="0">超管</option>
							<option value="1">管理员</option>
							<option value="2">操作员</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">权限</label>
					<div class="col-xs-4">
						<ul id="choosetree"></ul>
						<input class="hidden" type="text" id="limits" name="resIds" />
					</div>

				</div>

				<!--<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="key_word" class="col-xs-2 control-label">创建人</label>
					<div class="col-xs-4">
						王武
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="key_word" class="col-xs-2 control-label">创建时间</label>
					<div class="col-xs-4">
						2017.10.02
					</div>
				</div>-->
				<div class="form-group">
				<div class="col-xs-4"></div>
				<div class="col-xs-8 tip">注：该页面所有字段均为必填</div>
				</div>
			</form>

		</div>

	</body>

	<script>
	
		var url="<%=request.getContextPath() %>/backgroundManagement/background/userManager/selectAllMenu?id=";
	
		var oTable1;
		var readyToOnBlur = false;
		$("#btn_search").click(function() {

			oTable1.fnDraw();
		});
		jQuery(function($) {

			oTable1 = $('#datatable').dataTable({
				"aaSorting": [
					[0, "desc"]
				], //默认第几个排序
				"bStateSave": false, //状态保存
				"bServerSide": true, //是否开启服务端查询
				"bAutoWidth": false, //自动宽度
				"bPaginate": true, //翻页功能
				"bLengthChange": false, //改变每页显示数据数量
				"bFilter": false, //列筛序功能
				"bProcessing": false, //进度提示
				"bInfo": true, //页脚信息
				"iDisplayLength": 10, //默认每页显示的记录数
				"iDisplayStart": 0,
				"ajax": {
					"type": "post",
					"dataType": "json",
					"async": false,
					"data": function(d) {

						return $.extend({}, d, {
							//添加额外的参数传给服务器(可以多个)
							"name": $("#search_name").val(),
							"telphoneNo": $("#search_tel").val(),
							"operatorTime": $("#search_operatorTime").val()

						});
					},
					"url": "<%=basePath%>backgroundManagement/background/userManager/getAdministratorMsg"
				},
				"aoColumns": [{
					"mData": "",
					"bSortable": false,
					"sDefaultContent": "",
					"sClass": "center",
					"sWidth": "5%"
				}, {
					"mData": "name",
					"bSortable": false,
					"sClass": "center",
					"sWidth": "10%"
				}, {
					"mData": "loginName",
					"bSortable": false,
					"sClass": "center",
					"sWidth": "10%"
				}, {
					"mData": "phone",
					"bSortable": false,
					"sClass": "center",
					"sWidth": "10%"
				}, {
					"mData": "userGroup",
					"bSortable": false,
					"sClass": "center",
					"sWidth": "10%",
					"mRender": function(data) {
						if(data == 0) {
							return "超级管理员";
						} else if(data == 1) {
							return "管理员";
						} else {
							return "操作员";
						}

					}
				}, {
					"mData": "isEffective",
					"bSortable": false,
					"sClass": "center",
					"sWidth": "5%",
					"mRender": function(data, type, row) {
						if(data == 1) {
							return "有效";
						} else {
							return "无效";
						}
					}
				}, {
					"mData": "operatorIdentifier",
					"bSortable": false,
					"sClass": "center",
					"sWidth": "10%",
					"mRender": function(data, type, row) {
						if(data != null && data != "") {
							return data + "(" + row.operatorName + ")";
						} else {
							return "";
						}
					}
				}, {
					"mData": "createTime",
					"bSortable": false,
					"sClass": "center",
					"sWidth": "10%",
					"mRender": function(data) {
						return getSmpFormatDateByLong(data, true);
					}
				}, {
					"mData": "",
					"bSortable": false,
					"sDefaultContent": "",
					"sClass": "center",
					"sWidth": "30%",
					"mRender": function(data, type, row) {
						var updateHtml = '<td><input type="button" class="btncss edit" onclick=\'userEdit(' + JSON.stringify(row) + ')\' value="编辑" />'
						var deleteHtml = '<input type="button" class="btncss" onclick=\'userDel(' + JSON.stringify(row) + ')\' value="删除" /></td>';
						var resetPWDHtml = '<td><input type="button" class="btncss edit" onclick=\'resetPwd(' + JSON.stringify(row) + ')\' value="重置密码" />'
						var forbiddenHtml = "";
						if(row.id==4){
							return updateHtml;
						}else{
							
							if(row.isEffective == 0) {
								forbiddenHtml = '<input type="button" class="btncss" onclick=\'userNouse(this,' + JSON.stringify(row) + ')\' value="取消禁用" />';
							} else {
								forbiddenHtml = '<input type="button" class="btncss" onclick=\'userNouse(this,' + JSON.stringify(row) + ')\' value="禁用" />';
							}
							return updateHtml + forbiddenHtml + deleteHtml+resetPWDHtml;
						}
						

					}
				}],
				drawCallback: function(settings) {
					/**
					 *生成索引值 
					 **/
					n = this.api().page.info().start;
					this.api().column(0).nodes().each(function(cell, i) {
						cell.innerHTML = i + 1;
					});
				}
			})
		});

		/*正则验证*/
		var usernameres = false;
		var loginnname = false;
		var usertel = false;
		var userpwd = false;
		var power = false;
		var limitsinput = false;
		var checkednodes = [];
		var checkedStr = "";

		function usernameCheck(thisinput) {
			if(readyToOnBlur) {
				cky_obj($(thisinput));
				usernameres = checkChinese($(thisinput).val());
				if(!isnotEmpty($(thisinput).val())) {
					laywarn("请输入用户名");
				} else if(!usernameres) {
					laywarn("用户名为2-20个汉字");
				}
			}

			return usernameres;
		}

		function loginnnameCheck(thisinput) {
			if(readyToOnBlur) {
				cky_obj($(thisinput));
				loginnname = isnotEmpty($(thisinput).val());
				if(!loginnname) {
					laywarn("请输入登录名");
				}
			}
			return loginnname;

		}

		function usertelCheck(thisinput) {
			if(readyToOnBlur) {
				cky_obj($(thisinput));
				usertel = checkMobilePhone($(thisinput).val());
				if(!isnotEmpty($(thisinput).val())) {
					laywarn("请输入手机号");
				} else if(!usertel) {
					laywarn("用户手机格式错误");
				}
			}

			return usertel;
		}

		function userpwdCheck(thisinput) {
			if(readyToOnBlur) {
				cky_obj($(thisinput));
				console.log("in")
				userpwd = checkPassword($(thisinput).val());
				if(!isnotEmpty($(thisinput).val())) {
					laywarn("密码不能为空");
				} else if(!userpwd) {

					laywarn("密码为8-16位字母数字下划线组合");
				}
			}

			return userpwd;
		}

		function powerCheck(thisinput) {
			if(readyToOnBlur) {
				power = false;
				if($(thisinput).val() != "-1" && isnotEmpty($(thisinput).val())) {
					power = true;
				} else {
					laywarn("请选择用户组");
				}
			}

			return power;
		}

		function limitsCheck(thisinput) {
			limitsinput = isnotEmpty($(thisinput).val());
			if(!limitsinput) {
				laywarn("请选择权限");
			}
			return limitsinput;
		}

		function getresult() {
			if(usernameCheck("#username")) {
				if(loginnnameCheck("#loginnname")) {
					if(usertelCheck("#usertel")) {

						if(userpwdCheck("#userpwd")) {
							if(powerCheck("#power")) {
								if(limitsCheck("#limits")) {
									return true;
								}
							}
						}
					}
				}
			}
		}

		function getresult_update() {
			if(usernameCheck("#username")) {
				if(usertelCheck("#usertel")) {
					if(powerCheck("#power")) {
						if(limitsCheck("#limits")) {
							return true;
						}
					}
				}
			}
		}

		/*修改*/
		function userEdit(user) {
			url="<%=request.getContextPath() %>/backgroundManagement/background/userManager/selectAllMenu?id=";
			url+=user.id;
			var elem = document.getElementById("choosetree");  
			elem.innerHTML = "";  
			$("#choosetree").tree({
				url: url, //获取远程数据URL
				/* data: [{
						"id": 2,
						"text": "后台配置",
						"children": [{
							"id": 6,
							"text": "分类配置",
							"children": [{
								"id": 4,
								"text": "一级分类",
								"iconCls": "icon-no"
							}, {
								"id": 5,
								"text": "二级分类",
								"iconCls": "icon-no"
							}]
						}, {
							"id": 7,
							"text": "商品配置",
							"iconCls": "icon-no"

						}, {
							"id": 8,
							"text": "活动配置",
							"iconCls": "icon-no",
							"children": [{
								"id": 9,
								"text": "优惠券",
								"iconCls": "icon-no"
							}, {
								"id": 10,
								"text": "活动列表",
								"iconCls": "icon-no"
							}, {
								"id": 11,
								"text": "活动审核",
								"iconCls": "icon-no"
							}]
						}, {
							"id": 12,
							"text": "广告配置",
							"iconCls": "icon-no"
						}]
					}, {
						"id": 3,
						"text": "VIP管理",
						"children": [{
							"id": 13,
							"text": "VIP管理",
							"iconCls": "icon-no"
						}, {
							"id": 14,
							"text": "待添加列表",
							"iconCls": "icon-no"
						}]
					}, {
						"id": 15,
						"text": "订单管理",
						"children": [{
							"id": 16,
							"text": "订单管理",
							"iconCls": "icon-no"
						}, {
							"id": 17,
							"text": "发票审核",
							"iconCls": "icon-no"
						}]
					}, {
						"id": 18,
						"text": "后台管理",
						"children": [{
							"id": 19,
							"text": "后台管理",
							"children": [{
								"id": 20,
								"text": "用户",
								"iconCls": "icon-no"
							}, {
								"id": 21,
								"text": "操作日志",
								"iconCls": "icon-no"
							}]
						}]
					}, {
						"id": 24,
						"text": "报表系统",
						"children": [{
							"id": 25,
							"text": "综合检测",
							"iconCls": "icon-no"
						}, {
							"id": 26,
							"text": "订单报表",
							"iconCls": "icon-no"
						}, {
							"id": 27,
							"text": "商品销量报表",
							"iconCls": "icon-no",
						}]
					}

				], //节点数据加载 */
				lines: true,
				checkbox: true,
				onlyLeafCheck: false, //定义是否只在末节点之前显示复选框
				dnd: false, //定义是否启动拖拽功能
				onCheck: function() {
					checkednodes = $('#choosetree').tree('getChecked');
					console.log(checkednodes);
					
				},
				onLoadError: function(arguments) {
					console.log(arguments);
				}
			})
			
			readyToOnBlur = true;
			//获取数据显示在界面中
			$.ajax({
				url: '<%=basePath%>backgroundManagement/background/userManager/selectAdminMsgById',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: {
					"id": user.id
				},

				success: function(data) {
					$("#username").val(data.name);
					$("#loginnname").val(data.loginName);
					$("#usertel").val(data.phone);
					$("#power").val(data.userGroup);
					$("#primaryKey").val(data.id);
					$("#updateIdentifier").val(data.identifier);
					$("#loginnname").attr("disabled", "disabled");
					$("#pwdGroup").css("display", "none");
					
				}
			});

			layer.open({
				type: 1,
				title: "编辑用户",
				closeBtn: 1,
				area: ['100%', '100%'],
				content: $("#editDiv"),
				btn: ['提交', '取消'],
				yes: function(index) {
					checkednodes = $('#choosetree').tree('getChecked');
					/*将选中的节点获取ID变成字符串，赋值到id为limits的input框中*/
					getId(checkednodes);
					//获取正则判断完的结果
					var regresult = getresult_update();
					if(regresult) {
						$.ajax({
							url: '<%=basePath%>backgroundManagement/background/userManager/selectAdminByLoginNameAndId',
							type: "POST",
							dataType: "json",
							async: false,
							cache: false,
							data: {
								"id": user.id,
								"loginName": $("#loginnname").val()
							},

							success: function(data) {
								if(data.success) {
									$.ajax({
										url: '<%=basePath%>backgroundManagement/background/userManager/updateAdminMsgById',
										type: "POST",
										dataType: "json",
										async: false,
										cache: false,
										data: $("#addOrUpdateForm").serialize(),

										success: function(data) {
											if(data.success) {
												laysuccess(data.msg);
												if(user.id=='<%=session.getAttribute("userId")%>'){
													setTimeout(function (){parent.location.reload()},500);
												}				
											} else {
												layfail(data.msg);
											}

										}
									});
									layer.close(index);
									oTable1.fnDraw();
								} else {
									layfail(data.msg);
								}

							}
						});

					}

				},
				end: function() {
					$("#loginnname").removeAttr("disabled");
					$("#pwdGroup").css("display", "block");
					clearForm("editDiv", "", "", $("#choosetree"));
				}
			});
		}
		/*新增*/
		function userAdd() {
			url="<%=request.getContextPath() %>/backgroundManagement/background/userManager/selectAllMenu?id=";
			var elem = document.getElementById("choosetree");  
			elem.innerHTML = "";  
			$("#choosetree").tree({
				url: url, //获取远程数据URL
				/* data: [{
						"id": 2,
						"text": "后台配置",
						"children": [{
							"id": 6,
							"text": "分类配置",
							"children": [{
								"id": 4,
								"text": "一级分类",
								"iconCls": "icon-no"
							}, {
								"id": 5,
								"text": "二级分类",
								"iconCls": "icon-no"
							}]
						}, {
							"id": 7,
							"text": "商品配置",
							"iconCls": "icon-no"

						}, {
							"id": 8,
							"text": "活动配置",
							"iconCls": "icon-no",
							"children": [{
								"id": 9,
								"text": "优惠券",
								"iconCls": "icon-no"
							}, {
								"id": 10,
								"text": "活动列表",
								"iconCls": "icon-no"
							}, {
								"id": 11,
								"text": "活动审核",
								"iconCls": "icon-no"
							}]
						}, {
							"id": 12,
							"text": "广告配置",
							"iconCls": "icon-no"
						}]
					}, {
						"id": 3,
						"text": "VIP管理",
						"children": [{
							"id": 13,
							"text": "VIP管理",
							"iconCls": "icon-no"
						}, {
							"id": 14,
							"text": "待添加列表",
							"iconCls": "icon-no"
						}]
					}, {
						"id": 15,
						"text": "订单管理",
						"children": [{
							"id": 16,
							"text": "订单管理",
							"iconCls": "icon-no"
						}, {
							"id": 17,
							"text": "发票审核",
							"iconCls": "icon-no"
						}]
					}, {
						"id": 18,
						"text": "后台管理",
						"children": [{
							"id": 19,
							"text": "后台管理",
							"children": [{
								"id": 20,
								"text": "用户",
								"iconCls": "icon-no"
							}, {
								"id": 21,
								"text": "操作日志",
								"iconCls": "icon-no"
							}]
						}]
					}, {
						"id": 24,
						"text": "报表系统",
						"children": [{
							"id": 25,
							"text": "综合检测",
							"iconCls": "icon-no"
						}, {
							"id": 26,
							"text": "订单报表",
							"iconCls": "icon-no"
						}, {
							"id": 27,
							"text": "商品销量报表",
							"iconCls": "icon-no",
						}]
					}

				], //节点数据加载 */
				lines: true,
				checkbox: true,
				onlyLeafCheck: false, //定义是否只在末节点之前显示复选框
				dnd: false, //定义是否启动拖拽功能
				onCheck: function() {
					checkednodes = $('#choosetree').tree('getChecked');

				},
				onLoadError: function(arguments) {
					console.log(arguments);
				}
			})
			
			
			readyToOnBlur = true;
			layer.open({
				type: 1,
				title: "新增用户",
				closeBtn: 1,
				area: ['100%', '100%'],
				content: $("#editDiv"),
				btn: ['提交', '取消'],
				yes: function(index) {
					/*将选中的节点获取ID变成字符串，赋值到id为limits的input框中*/
					getId(checkednodes);
					//获取正则判断完的结果

					var regresult = getresult();

					if(regresult) {
						$.ajax({
							url: '<%=basePath%>backgroundManagement/background/userManager/selectAdminByLoginName',
							type: "POST",
							dataType: "json",
							async: false,
							cache: false,
							data: {
								"loginName": $("#loginnname").val()
							},
							success: function(data) {
								if(data.success) {
									$.ajax({
										url: '<%=basePath%>backgroundManagement/background/userManager/insertAdminMsg',
										type: "POST",
										dataType: "json",
										async: false,
										cache: false,
										data: $("#addOrUpdateForm").serialize(),

										success: function(data) {
											if(data.success) {
												laysuccess(data.msg);
											} else {
												layfail(data.msg);
											}

										}
									});
									layer.close(index);
									oTable1.fnDraw();
								} else {
									layfail(data.msg);
								}

							}
						});

					}

				},
				btn2: function(index, layero) {
					layer.close(index);
				},
				end: function() {
					clearForm("editDiv", "", "", $("#choosetree"));
				}
			});
		}
		/*删除*/
		function userDel(user) {
			var nowLoginIdentifier = '${identifier}';
			if(user.identifier == nowLoginIdentifier) {
				layfail("当前该账户正在登录，不能删除!");
				return;
			}
			publicMessageLayer("删除此账号", function() {
				$.ajax({
					url: '<%=basePath%>backgroundManagement/background/userManager/deleteByPrimaryKey',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data: {
						"id": user.id,
						"identifier": user.identifier
					},
					success: function(data) {
						if(data.success) {
							laysuccess(data.msg);
							oTable1.fnDraw();
						} else {
							layfail(data.msg);
						}

					}
				});
			})
		}

		/*禁用*/
		function userNouse(thisbtn, user) {
			var nowLoginIdentifier = '${identifier}';
			if(user.identifier == nowLoginIdentifier) {
				layfail("当前该账户正在登录，不能禁用!");
				return;
			}
			var that = thisbtn;
			if($(that).val() == "取消禁用") {
				publicMessageLayer("取消此账号的禁用", function() {
					$.ajax({
						url: '<%=basePath%>backgroundManagement/background/userManager/updateUserForbidden',
						type: "POST",
						dataType: "json",
						async: false,
						cache: false,
						data: {
							"id": user.id,
							"identifier": user.identifier,
							"isEffective": user.isEffective
						},
						success: function(data) {
							if(data.success) {
								laysuccess(data.msg);
								oTable1.fnDraw();
							} else {
								layfail(data.msg);
							}

						}
					});

				})
			} else if($(that).val() == "禁用") {
				publicMessageLayer("禁用此账号", function() {

					$.ajax({
						url: '<%=basePath%>backgroundManagement / background / userManager / updateUserForbidden ',
						type: "POST",
						dataType: "json",
						async: false,
						cache: false,
						data: {
							"id": user.id,
							"identifier": user.identifier,
							"isEffective": user.isEffective
						},
						success: function(data) {
							if(data.success) {
								laysuccess(data.msg);
								oTable1.fnDraw();
							} else {
								layfail(data.msg);
							}

						}
					});

				})
			}

		}

		/*重置密码*/
		function resetPwd(user) {
			var nowLoginIdentifier = '${identifier}';
			if(user.identifier == nowLoginIdentifier) {
				layfail("当前该账户正在登录，不能重置!");
				return;
			}
			publicMessageLayer("重置密码", function() {
				$.ajax({
					url: '<%=basePath%>backgroundManagement/background/userManager/resetPwd',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data: {
						"id": user.id,
						"identifier": user.identifier
					},
					success: function(data) {
						if(data.success) {
							laysuccess(data.msg);
							oTable1.fnDraw();
						} else {
							layfail(data.msg);
						}

					}
				});
			})
		}
		
		/*将选中的节点获取ID变成字符串，赋值到id为limits的input框中*/
		function getId(obj) {
			checkedStr = "";
			$(obj).each(function(i, dom) {
				checkedStr = checkedStr + dom["id"] + ","
			});
			checkedStr = checkedStr.substring(0, checkedStr.length - 1);
			$("#limits").val(checkedStr);
			console.log(checkedStr);
		}

		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
	</script>

</html>