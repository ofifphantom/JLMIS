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
		<title>发票审核</title>
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
					<h4 class="text-title">发票审核</h4>
					<div class="search-div clearfix">
						<div class="clearfix">
							<span class="l_f"> 
							订单号： <input type="text" value="" />
						</span>
							<span class="l_f"> 
							用户姓名： <input type="text" value="" />
						</span>
							<span class="l_f"> 
							手机号： <input type="text"  value="" />
						</span>
						<span class="l_f"> 
							提交时间： <input type="text"  value="" id="search_operatorTime" placeholder="请选择时间" readonly=""  />
						</span>
							<span class="l_f"> 
							状态：<select>
								<option selected="selected" value="-1">--请选择--</option>
								<option selected="selected" value="">待审核</option>
								<option selected="selected" value="">已通过</option>
								<option selected="selected" value="">未通过</option>
							</select>
						</span>
							<span class="r_f"> 
							<input type="button"  class="btncss btn_search" value="搜索" />
						</span>
						</div>

					</div>
					<div class="opration-div clearfix">

					</div>
					<div class="table_menu_list">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>序号</th>
									<th>用户姓名</th>
									<th>电话</th>
									<th>提交时间</th>
									<th>审核状态</th>
									<th>支付信息</th>
									<th width="14%">操作</th>
								</tr>

							</thead>
							<tbody>
								<tr>
									<td>1234578979</td>
									<td>李四</td>
									<td>14789654231</td>
									<td>2017-10-02&nbsp;&nbsp;22：10</td>
									<td>待审核</td>
									<td>
										<input type="button" class="btncss edit" onclick="order()" value="发票详情" />
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
										<input type="button" class="btncss edit" onclick="order()" value="发票详情" />
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
										<input type="button" class="btncss edit" onclick="order()" value="发票详情" />
									</td>
									<td>
										<input type="button" class="btncss edit" onclick="orderPass()" value="通过" />
										<input type="button" class="btncss edit" onclick="orderRefuse()" value="不通过" />
									</td>
								</tr>

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

		<!--新增 编辑 -->
		<div id="editDiv" style="display: none;">
			<form class="container">
				<div class="col-xs-6">
					<div class="form-group">
						<label class="col-xs-3 control-label">用户姓名：</label>
						<div class="col-xs-4">
							张三
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">用户电话：</label>
						<div class="col-xs-4">
							15639841562
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">单位名称：</label>
						<div class="col-xs-4">
							123456789
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">纳税人识别码：</label>
						<div class="col-xs-4">
							12348749
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">注册人地址：</label>
						<div class="col-xs-9">
							Lorem ipsum dolor sit amet, consectetur adipisicing elit. Modi neque nam velit nemo enim quam possimus doloribus.
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">注册电话：</label>
						<div class="col-xs-4">
							152698746562
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">开户银行：</label>
						<div class="col-xs-4">
							北京市建设银行
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">银行账户：</label>
						<div class="col-xs-4">
							012697455
						</div>
					</div>
					
				</div>
				<div class="col-xs-6">
					<div class="form-group">
						<div class="col-xs-12"><span class="text-danger">*</span>&nbsp;税务登记副本</div>
						<div class="col-xs-12 imgbox">
							<img src="../../../img/orderpj.jpg" />
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
		/*修改*/
		function order() {
			layer.open({
				type: 1,
				title: "发票详情",
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
		function orderPass() {
			publicMessageLayer("该发票审核通过", function() {
				laysuccess("操作成功");
			})
		}
		/*不通过*/
		function orderRefuse() {
			publicMessageLayer("该发票审核不通过", function() {
				laysuccess("操作成功");
			})
		}
		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
	</script>

</html>