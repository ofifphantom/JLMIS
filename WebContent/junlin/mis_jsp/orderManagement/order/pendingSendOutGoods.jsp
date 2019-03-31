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
		<title>待发货订单</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		
		<style>
			#editDiv,#lookPayMessage,#lookInvoice {
				padding-top: 20px;
			}
			.col-xs-2{
				padding-right: 0;
			}
			.imgbox img{
				width: 100%;
				border: 1px solid #ccc;
			}
			#lookPayMessage img{
				width: 100%;
			}
			#look_invoiceMsg a,#look_payMessage a{
				text-decoration: underline;
    			color: #ef8336;
			}
			#look_invoiceMsg a:hover,#look_payMessage a:hover{
    			color: #f1b489;
			}
			#offline_payment_voucher_url_one img,#offline_payment_voucher_url_two img{
				cursor: pointer;
			}
			#sendOut label{
			line-height:32px;
			}
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">待发货订单</h4>
					<div class="search-div clearfix">
						<div class="clearfix">
							<span class="l_f"> 
							订单号： <input type="text" value="" id="search_orderNo"/>
						</span>
							<span class="l_f"> 
							用户姓名： <input type="text" value="" id="search_userName"/>
						</span>
							<span class="l_f"> 
							手机号： <input type="text"  value="" id="search_phone"/>
						</span>
							<span class="l_f"> 
							下单时间： <input type="text"  value="" id="search_operatorTime" placeholder="请选择时间" readonly=""  />
						</span>
							<span class="r_f"> 
							<input type="button"  class="btncss btn_search" id="btn_search" value="搜索" />
						</span>
						</div>

					</div>
					<div class="opration-div clearfix">

					</div>
					<div class="table_menu_list">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>订单号</th>
									<th>商品名称</th>
									<th>数量</th>
									<th>实付款</th>
									<th>用户姓名</th>
									<th>电话</th>
									<th>下单时间</th>
									<th>订单状态</th>
									<th>审核状态</th>
									<th width="21%">操作</th>
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
			<form class="container">

				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">订单号：</label>
					<div class="col-xs-4" id="look_orderNo">
					123456789
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label  class="col-xs-2 control-label">商品：</label>
					<div class="col-xs-4" id="look_goods">
						<ul>
							<li>上好佳   ×100  ￥19,00</li>
							<li>谷粒多   ×100  ￥19,00</li>
						</ul>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">订单价格：</label>
					<div class="col-xs-4" id="look_orderPrice">
					￥100,00
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">优惠信息：</label>
					<div class="col-xs-4" id="look_preferential">
					优惠券：￥5.00
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">邮费：</label>
					<div class="col-xs-4" id="look_postage">
					￥5.00
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">实付款：</label>
					<div class="col-xs-4" id="look_payMoney">
					优惠券：￥95.00
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">用户姓名：</label>
					<div class="col-xs-4" id="look_userName">
					张三
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">用户电话：</label>
					<div class="col-xs-4" id="look_userPhone">
					15639841562
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">下单时间：</label>
					<div class="col-xs-4" id="look_placeOrderTime">
					2017-04-24 21:31
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">支付时间：</label>
					<div class="col-xs-4" id="look_payTime">
					2017-04-24 21:54
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">收货信息：</label>
					<div class="col-xs-6" id="look_consigneeMsg">
					张三 182569784152 北京朝阳区南三环
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">发票信息：</label>
					<div class="col-xs-4" id="look_invoiceMsg">
					不开发票
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">订单状态：</label>
					<div class="col-xs-4" id="look_orderState">
					待确认
					</div>
				</div>
				<div class="form-group" id="offline_type">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">线下支付信息：</label>
					<div class="col-xs-8 imgbox" id="look_payMessage">
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label  class="col-xs-2 control-label">订单状态详情：</label>
					<div class="col-xs-7" id="look_orderStateDetail">
						<ul>
							<li>2017-04-29 支付成功</li>
							<li>2017-04-29 订单提交成功</li>
						</ul>
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
		
		<!--查看发票-->
		<div id="lookInvoice" style="display: none;">
			<form class="container">
			<div class="form-group" id="invoice_type">
					<div class="col-xs-2"></div>
					<label class="col-xs-4 control-label">发票类型：</label>
					<div class="col-xs-5" id="look_invoice_type">
					
					</div>
				</div>
				<div class="form-group" id="invoice_content">
					<div class="col-xs-2"></div>
					<label class="col-xs-4 control-label">发票内容：</label>
					<div class="col-xs-5" id="look_invoice_content">
					
					</div>
				</div>
				<div class="form-group" id="invoice_looked_up_type">
					<div class="col-xs-2"></div>
					<label class="col-xs-4 control-label">发票抬头类型：</label>
					<div class="col-xs-6" id="look_invoice_looked_up_type">
					
					</div>
				</div>
				<div id="invoiceTypeUnit">
					<div class="form-group" id="unit_name">
						<div class="col-xs-2"></div>
						<label class="col-xs-4 control-label">单位名称：</label>
						<div class="col-xs-6" id="look_unit_name">
						
						</div>
					</div>
					<div class="form-group" id="taxpayer_identification_number">
						<div class="col-xs-2"></div>
						<label  class="col-xs-4 control-label">纳税人识别号：</label>
						<div class="col-xs-6" id="look_taxpayer_identification_number">
							
						</div>
					</div>
				</div>
				<div id="increasedTicket">
					<div class="form-group" id="registered_address">
						<div class="col-xs-2"></div>
						<label class="col-xs-4 control-label">注册地址：</label>
						<div class="col-xs-6" id="look_registered_address">
						
						</div>
					</div>
					<div class="form-group" id="registered_tel">
						<div class="col-xs-2"></div>
						<label class="col-xs-4 control-label">注册电话：</label>
						<div class="col-xs-6" id="look_registered_tel">
						
						</div>
					</div>	
					<div class="form-group" id="deposit_bank">
						<div class="col-xs-2"></div>
						<label class="col-xs-4 control-label">开户银行：</label>
						<div class="col-xs-6" id="look_deposit_bank">
						
						</div>
					</div>
					<div class="form-group" id="bank_account">
						<div class="col-xs-2"></div>
						<label class="col-xs-4 control-label">银行账户：</label>
						<div class="col-xs-6" id="look_bank_account">
						
						</div>
					</div>	
				</div>
			</form>

		</div>
		
		<!--查看线下支付信息-->
		<div id="lookPayMessage" style="display: none;">
			<form class="container">
				<div class="col-xs-5">
					<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-4 control-label">汇款人姓名：</label>
					<div class="col-xs-5" id="offline_remitter_name">
					
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-4 control-label">汇款人账号：</label>
					<div class="col-xs-5" id="offline_remitter_account">
					
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-4 control-label">收款人姓名：</label>
					<div class="col-xs-6" id="offline_payee_name">
					
					</div>
				</div>
				<div id="invoiceTypeUnit">
					<div class="form-group">
						<div class="col-xs-2"></div>
						<label class="col-xs-4 control-label">收款人账号：</label>
						<div class="col-xs-6" id="offline_payee_account">
						
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-2"></div>
						<label  class="col-xs-4 control-label">汇款金额：</label>
						<div class="col-xs-6" id="offline_remittance_amount">
							
						</div>
					</div>
				</div>
				</div>
				<div class="col-xs-7">
					<div id="increasedTicket">
					<div class="form-group">
						<label class="col-xs-4 control-label">支付凭证1：</label>
						<div class="col-xs-6" id="offline_payment_voucher_url_one">
						
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">支付凭证2：</label>
						<div class="col-xs-6" id="offline_payment_voucher_url_two">
						
						</div>
					</div>	
				</div>
				</div>
			
				
			</form>

		</div>

		<div id="sendOut" style="display: none;padding-top: 50px;padding-left: 17%;">
			<div class="form-group">
				<label class="col-xs-2 control-label">车牌号：</label>
				<div class="col-xs-7">
					<input type="text" class="form-control" id="carID" data-provide="typeahead" placeholder="输入车牌号可自动进行检索近期使用过的车牌号" onkeyup="cky(this)" maxlength="30"/>
				</div>
			</div>
		</div>
<!--修改收货地址  -->
		<div id="contactModifyDiv" style="display: none;margin:30px 0;">
		<form class="container">
				<div class="form-group">
						<label class="col-xs-2 control-label">收货人</label>
						<div class="col-xs-10" >
							<input type="text" id="contact_consignee" class="form-control" onblur="cky(this)" maxlength="20">
						</div>
				</div>
				<div class="form-group">
						<label class="col-xs-2 control-label">联系方式</label>
						<div class="col-xs-10" >
							<input type="text" id="contact_information" class="form-control" onblur="cky(this)">
						</div>
				</div>
		
				<div class="form-group">
						<label class="col-xs-2 control-label">收货地址</label>
						<div class="col-xs-10">
							<input type="text" id="contact_address" class="form-control" onblur="cky(this)" maxlength="400">
						</div>
				</div>
			</form>
		
		</div>
	</body>

	<script>
		/* 车牌号的自动搜索功能 */
    	$('#carID').typeahead({
    		source:function(query, process) {
				return $.ajax({
					url: '<%=basePath%>orderManagement/order/order/selectTenCarId',
					type: 'post',
					data: {
						carId: query
					},
					success: function(result) {
						var resultList = [];
						console.log(result.length);
						for(var i = 0; i < result.length; i++) {
							var aItem = result[i];
							resultList.push(aItem);
						}
						return process(resultList);
					},
				});

			},
			updater: function(obj) {
				return obj;
			}
    	})
    	
   	var oTable1;
	$("#btn_search").click(function() {
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
							"orderNo": $("#search_orderNo").val(),
							"userName": $("#search_userName").val(),
							"phone": $("#search_phone").val(),
							"page":4,
							"placeOrderTime": $("#search_operatorTime").val()
						});
					},
					"url": "<%=basePath%>orderManagement/order/order/getManagerMsg"
				},

				"aoColumns": [{
						"mData": "orderNo",
						"bSortable": true,
						"sWidth": "15%",
						"sClass": "center"
					}, {
						"mData": "orderGoodsName",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center"
					}, {
						"mData": "orderGoodsNum",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center",

					}, {
						"mData": "orderGoodsPrice",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center",
						"mRender": function(data,type,row) {
							return (row.orderPresentPrice+row.postage).toFixed(2);
						} 
					}, {
						"mData": "user.name",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center",
					}, {
						"mData": "user.phone",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center"
					}, {
						"mData": "placeOrderTime",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center",
						"mRender": function(data) {
							return getSmpFormatDateByLong(data, true);
						} 
					}, {
						"mData": "orderState",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center" ,
						"mRender": function(data) {
							if(data==1){
								return "待发货";
							
							} 
							else{
								return "";
							}
						}
					}, {
						"mData": "offlinePayment.state",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center" ,
						"bVisible":false,
						"mRender": function(data, type, row) {
								//待审核
								if(data==1){
									return "未审核";
								}
								//审核通过
								else if(data>=3){
									return "通过";
								}
								else{
									return "未通过";
								}
							
						}
					},

					{
						"mData": "id",
						"bSortable": false,
						"sWidth": "20%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							if(row.orderState==1){
								return '<td><input type="button" class="btncss edit" onclick="order('+data+')" value="详情" />'
								+'<input type="button" class="btncss edit" onclick="contactModify('+data+')" value="修改收货信息" />'
								+'<input type="button" class="btncss edit" onclick=\'orderSendOut(' + JSON.stringify(row) + ')\' value="发货" /></td>';
							}
							else{
								return '<td><input type="button" class="btncss edit" onclick="order('+data+')" value="详情" /></td>'
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
	
	
		/*详情*/
		function order(id) {
			/* 根据订单id获取查看详情页面里的值 */
			$.ajax({
				url: '<%=basePath%>orderManagement/order/order/checkOrderDetailsByOrderId',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: {
					"id": id
				},
				success: function(data) {
					$("#look_orderNo").html(data.orderNo);
					var goodsDetail="";
					for(var i=0;i<data.orderDetails.length;i++){
						if(i<data.orderDetails.length-1){
							goodsDetail+=data.orderDetails[i].goodsName+"&nbsp;&nbsp;x"+data.orderDetails[i].goodsQuantity+"&nbsp;&nbsp;¥"+data.orderDetails[i].goodsPaymentPrice.toFixed(2)+"<br>";
						}
						else{
							goodsDetail+=data.orderDetails[i].goodsName+"&nbsp;&nbsp;x"+data.orderDetails[i].goodsQuantity+"&nbsp;&nbsp;¥"+data.orderDetails[i].goodsPaymentPrice.toFixed(2);
						}
					}
					$("#look_goods").html(goodsDetail);
					$("#look_orderPrice").html("¥"+data.orderOriginalPrice.toFixed(2));
					if(data.preferentialType==null||data.preferentialType==""){
						$("#look_preferential").html("无");
					}
					$("#look_preferential").html("¥"+(data.orderOriginalPrice-data.orderPresentPrice).toFixed(2));
					$("#look_postage").html("¥"+data.postage);
					$("#look_payMoney").html("¥"+(data.orderPresentPrice+data.postage).toFixed(2));
					$("#look_userName").html(data.user.name);
					$("#look_userPhone").html(data.user.phone);
					$("#look_placeOrderTime").html(getSmpFormatDateByLong(data.placeOrderTime, true));
					if(data.payTime!=null){
						$("#look_payTime").html(getSmpFormatDateByLong(data.payTime, true));	
					}
					else{
						$("#look_payTime").html("");
					}
					$("#look_consigneeMsg").html(data.consigneeName+"&nbsp;"+data.consigneeTel+"&nbsp;"+data.consigneeAddress);
					if(data.invoice==null){
						$("#look_invoiceMsg").html("不开发票");
					}
					else{
						$("#look_invoiceMsg").html("<a href='javascript:lookInvoiceMsg(" + JSON.stringify(data.invoice) + ")'>已开发票,点击查看</a>");
					}
					if(data.orderState==1){
						$("#look_orderState").html("待发货");
					}
					else{
						$("#look_orderState").html("");
					}
					var orderStateDetailMsg="";
					for(var i=0;i<data.orderStateDetails.length;i++){
						if(i<data.orderStateDetails.length-1){
							orderStateDetailMsg+=getSmpFormatDateByLong(data.orderStateDetails[i].addTime, true)+"&nbsp;"+data.orderStateDetails[i].orderStateDetail+"<br>";
						}
						else{
							orderStateDetailMsg+=getSmpFormatDateByLong(data.orderStateDetails[i].addTime, true)+"&nbsp;"+data.orderStateDetails[i].orderStateDetail;
						}
					}
					if(data.payType==1){
						$("#offline_type").css("display","block");
						$("#look_payMessage").html("<a href='javascript:lookPayMessage(" + JSON.stringify(data.offlinePayment) + ")'>点击查看</a>");
					}
					else{
						$("#offline_type").css("display","none");
					}
					
					
					if(data.orderStateDetails.length==0){
						$("#look_orderStateDetail").html("暂无状态信息");
					}else{
						$("#look_orderStateDetail").html(orderStateDetailMsg);
					}
					
					

				}
			});
			
			layer.open({
				type: 1,
				title: "订单详情",
				closeBtn: 1,
				area: ['1000px', ''],
				content: $("#editDiv"),
				btn: ['关闭'],
				yes: function(index) {
					layer.close(index);
				},
				end:function(){
					$("#look_orderNo").html("");
					$("#look_goods").html("");
					$("#look_orderPrice").html("");
					$("#look_preferential").html("");
					$("#look_postage").html("");
					$("#look_payMoney").html("");
					$("#look_userName").html("");
					$("#look_userPhone").html("");
					$("#look_placeOrderTime").html("");
					$("#look_payTime").html("");
					$("#look_consigneeMsg").html("");
					$("#look_invoiceMsg").html("");
					$("#look_orderState").html("");
					$("#look_payMessage").html("");
					$("#look_orderStateDetail").html(""); 	
				}
			});
		}
		
		/*查看发票信息*/
		function lookInvoiceMsg(data){
			/* 发票类型 */
			if(data.invoiceType==0){
				$("#look_invoice_type").html("普通发票");
				$("#increasedTicket").css("display","none");
			}
			else{
				$("#look_invoice_type").html("增值发票");
				$("#increasedTicket").css("display","block");
				$("#look_registered_address").html(data.registeredAddress);
				$("#look_registered_tel").html(data.registeredTel);
				$("#look_deposit_bank").html(data.depositBank);
				$("#look_bank_account").html(data.bankAccount);
				
			}
			/* 发票内容 */
			if(data.invoiceContent==0){
				$("#look_invoice_content").html("商品");
			}
			else{
				$("#look_invoice_content").html("明细");
			}
			/* 发票抬头 */
			if(data.invoiceLookedUpType==0){
				$("#look_invoice_looked_up_type").html("单位");
				$("#invoiceTypeUnit").css("display","block");
				$("#look_unit_name").html(data.unitName);
				$("#look_taxpayer_identification_number").html(data.taxpayerIdentificationNumber);
				
			}
			else{
				$("#look_invoice_looked_up_type").html("个人");			
				$("#invoiceTypeUnit").css("display","none");
			}
			
			
			layer.open({
				type: 1,
				title: "发票详情",
				closeBtn: 1,
				area: ['600px', ''],
				content: $("#lookInvoice"),
				btn: ['关闭'],
				yes:function(index){
					layer.close(index);
				},
				end:function(){
					$("#look_invoice_type").html("");
					$("#increasedTicket").css("display","block");
					$("#look_registered_address").html("");
					$("#look_registered_tel").html("");
					$("#look_deposit_bank").html("");
					$("#look_bank_account").html("");
					$("#look_invoice_content").html("");
					$("#look_invoice_looked_up_type").html("");
					$("#invoiceTypeUnit").css("display","block");
					$("#look_unit_name").html("");
					$("#look_taxpayer_identification_number").html("");
				}
			});
		}
		
		/* 查看线下付款详情 */
		function lookPayMessage(data){
			$("#offline_remitter_name").html(data.remitterName);
			$("#offline_remitter_account").html(data.remitterAccount);
			$("#offline_payee_name").html(data.payeeName);
			$("#offline_payee_account").html(data.payeeAccount);
			$("#offline_remittance_amount").html(data.remittanceAmount);
			if(data.paymentVoucherUrlOne!=null && data.paymentVoucherUrlOne!=""){
				$("#offline_payment_voucher_url_one").html("<img class='img-responsive' src='${pageContext.request.contextPath}/"+data.paymentVoucherUrlOne+"' onclick='checkImg(this)' />");
			}
			else{
				$("#offline_payment_voucher_url_one").html("暂无图片");
			}
			
			if(data.paymentVoucherUrlTwo!=null && data.paymentVoucherUrlTwo!=""){
				$("#offline_payment_voucher_url_two").html("<img class='img-responsive' src='${pageContext.request.contextPath}/"+data.paymentVoucherUrlTwo+"' onclick='checkImg(this)' />");
			}
			else{
				$("#offline_payment_voucher_url_two").html("暂无图片");
			}
			
			layer.open({
				type: 1,
				title: "线下支付详情",
				closeBtn: 1,
				area: ['1100px', ''],
				offset:'20%',
				content: $("#lookPayMessage"),
				btn: ['关闭'],
				yes:function(index){
					layer.close(index);
				},
				end:function(){
					$("#offline_remitter_name").html("");
					$("#offline_remitter_account").html("");
					$("#offline_payee_name").html("");
					$("#offline_payee_account").html("");
					$("#offline_remittance_amount").html("");
					$("#offline_payment_voucher_url_one").html("");
					$("#offline_payment_voucher_url_two").html("");
				}
			});
		}
		
		/*点击查看图片*/
		function checkImg(e) {
			var src=$(e).attr("src")
			layer.open({
				type: 1,
				title: "图片",
				closeBtn: 1,
				area: ['1100px', ''],
				content: "<div class='text-center' style='margin:15px 0'><img src='" + src + "' /></div>",
				btn: ['关闭']
			});

		}

		/*发货*/
		function orderSendOut(rowData) {
			layer.open({
				type: 1,
				title: "订单发货",
				closeBtn: 1,
				area: ['800px', '300px'],
				content: $("#sendOut"),
				btn: ['提交'],
				yes: function(index) {
					if($("#carID").val()==null||$("#carID").val()==""){
						laywarn("车牌号不能为空!");
						return;
					}
					$.ajax({
						url: '<%=basePath%>orderManagement/order/order/shippingGoods',
						type: "POST",
						dataType: "json",
						async: false,
						cache: false,
						data: {
							"oId": rowData.id,
							"carId":$("#carID").val(),
							"orderNo":rowData.orderNo
							
						},

						success: function(data) {
							if(data.success) {
								laysuccess(data.msg);
								oTable1.fnDraw();
							} else {
								layfail(data.msg);
							}
							layer.close(index);
						}
					});
				},
				end:function(){
					clearForm("sendOut");
				}
			});
		}
		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
		
		
		
		/*修改收货人、联系电话、收货地址  */
		function contactModify(){
			layer.open({
				type: 1,
				title: "修改",
				closeBtn: 1,
				area: ['600px', ''],
				content: $("#contactModifyDiv"),
				btn: ['提交','取消'],
				yes:function(index){
					if(checkContactModifyDiv()){
						layer.close(index);
					}
				},
				end:function(){
					$("#contactModifyDiv input").val("");
				}
			});
		}
		/*contactModifyDiv判空与校验  */
		function checkContactModifyDiv(){
			var res=true;
			if($("#contact_consignee").val()==""||$("#contact_information").val()==""||$("#contact_address").val()==""){
				layfail("表单未填完整，请完善后再提交！");
				res=false;
			}else if(!checkMobilePhone($("#contact_information").val())){
				layfail("联系方式格式不正确！");
				res=false;
			}
			return res;
		}
	</script>
</html>