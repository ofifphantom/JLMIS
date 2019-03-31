<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="utf-8" />
		<title>订单报表</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		<style>
			.search-div .btn_search {
				margin-right: 0;
			}
			
			#educeDiv {
				padding-top: 20px;
				width: 420px;
				margin: 0 auto;
			}
			#educeDiv>article:first-child{
				margin-bottom: 20px;
			}
			#educeDiv>article:first-child>p{
				font-size:16px ;
				line-height: 30px;
				color: #555;
				font-weight: bold;
			}
			#educeDiv article .table td{
				font-family: "微软雅黑";
				font-size: 13.5px;
				color: #8c8c8c;
				font-weight: 200;
				padding: 6px 4px;
			}
			
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">订单报表</h4>
					<div class="search-div clearfix">
					<form id="From" action="Order" method="post">
						<div class="clearfix">
							<span class="l_f"> 
								下单时间：<input type="text" name="choosetime" value="${map.choosetime}" id="choosetime" placeholder="请选择时间" readonly="" style="width: 200px;" />
							</span>
							<span class="l_f"> 
								订单状态：<select name="selectType" id="selectType" value="${map.selectType}">
											<option value="-1" selected="selected">全部</option>
											<option value="0">待支付</option>
											<option value="1">待发货</option>
											<option value="2">待收货</option>
											<option value="3">已完成</option>
											<option value="4">已取消</option>
											<option value="5">已关闭</option>
											<option value="6">售后中</option>
											<option value="9">已付款</option>
										</select>
							</span>
							<span class="r_f"> 
								<input type="button"  class="btncss jl-btn-importent"  value="搜索" onclick="searchOrder()" />
								<input type="button"  class="btncss jl-btn-defult" value="导出" onclick="educeOrder()" />
							</span>
						</div>
					</form>
					</div>
					<div class="opration-div clearfix">
					</div>
					<div class="table_menu_list">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>订单号</th>
									<th>订单状态</th>
									<th>商品种类</th>
									<th>商品数量</th>
									<th>用户姓名</th>
									<th>用户电话</th>
									<th>收货信息</th>
									<!-- <th>发票信息</th> -->
									<th>下单时间</th>
									<th>支付时间</th>
									<th>订单价格</th>
									<th>优惠价格</th>
									<th>实付款</th>
									<th>配送信息</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty list}">
										<c:forEach items="${list}" var="var" varStatus="vs">
										
											<tr>
												<td>${var.orderNo}</td>
												<td>
												<c:choose>
													<c:when test="${var.orderState==0}">待支付</c:when>
													<c:when test="${var.orderState==1}">待发货</c:when>
													<c:when test="${var.orderState==2}">待收货</c:when>
													<c:when test="${var.orderState==3}">已完成</c:when>
													<c:when test="${var.orderState==4}">已取消</c:when>
													<c:when test="${var.orderState==5}">已关闭</c:when>
													<c:when test="${var.orderState==6}">售后中</c:when>
													<c:when test="${var.orderState==7}">已关闭</c:when>
													<c:when test="${var.orderState==8}">已关闭</c:when>
													<c:when test="${var.orderState==9}">已支付</c:when>
													<c:when test="${var.orderState==10}">已完成</c:when>
													<c:otherwise>
													
													</c:otherwise>
												</c:choose>
												</td>
												<td>
													<c:if test="${not empty var.orderDetails}">
														<c:forEach items="${var.orderDetails}" var="var1" varStatus="vs1">
															<c:choose>
																<c:when test="${vs1.index<fn:length(var.orderDetails) }">
																${var1.goodsDetails.classification.name}<br>
																</c:when>
																<c:otherwise>
																${var1.goodsDetails.classification.name}
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:if>
												</td>
												<td>
													<c:if test="${not empty var.orderDetails}">
														<c:forEach items="${var.orderDetails}" var="var1" varStatus="vs1">
															<c:choose>
																<c:when test="${vs1.index<fn:length(var.orderDetails) }">
																${var1.goodsQuantity}<br>
																</c:when>
																<c:otherwise>
																${var1.goodsQuantity}
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:if>
												</td>
												<td>${var.user.name }</td>
												<td>${var.user.phone }</td>
												<td>
												${var.consigneeName }<br>
												${var.consigneeTel }<br>
												${var.consigneeAddress }
												</td>
												<%-- <td>
												${var.invoice.invoiceType}<br>
												${var.invoice.unitName}<br>
												${var.invoice.taxpayerIdentificationNumber}
												</td> --%>
												<td><fmt:formatDate type="both" value="${var.placeOrderTime }"/></td>
												<td><fmt:formatDate type="both" value="${var.payTime }"/></td>
												<td>${var.orderOriginalPrice }</td>
												<td><fmt:formatNumber type="number" value="${var.orderOriginalPrice - var.orderPresentPrice}" maxFractionDigits="2"/></td>
												<td><fmt:formatNumber type="number" value="${var.orderPresentPrice }" maxFractionDigits="2"/></td>
												<td>${var.carIdSend }</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="14">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

		<section id="educeDiv" style="display: none;">
			<article>
				<p>导出时间：${not empty map.choosetime?map.choosetime:'全部'}</p>
				<p>订单状态：
					<c:choose>
						<c:when test="${map.selectType==0}">待支付</c:when>
						<c:when test="${map.selectType==1}">待发货</c:when>
						<c:when test="${map.selectType==2}">待收货</c:when>
						<c:when test="${map.selectType==3}">已完成</c:when>
						<c:when test="${map.selectType==4}">已取消</c:when>
						<c:when test="${map.selectType==5}">已关闭</c:when>
						<c:when test="${map.selectType==6}">售后中</c:when>
						<c:when test="${map.selectType==7}">已关闭</c:when>
						<c:when test="${map.selectType==8}">已关闭</c:when>
						<c:when test="${map.selectType==9}">已付款</c:when>
						<c:when test="${map.selectType==10}">已完成</c:when>
						<c:otherwise>
						全部
						</c:otherwise>
					</c:choose>
				</p>
			</article>
			<article>
				<table class="table table-bordered">
					<tr>
						<td>订单数</td>
						<td>${OrderNum}</td>
						<td>订单总金额</td>
						<td>￥${OrderMONEY}</td>
					</tr>
					<tr>
						<td>取消订单数</td>
						<td>${QOrderNum}</td>
						<td>取消订单总金额</td>
						<td>￥${QOrderMONEY}</td>
					</tr>
					<tr>
						<td>退货订单数</td>
						<td>${TOrderNum}</td>
						<td>退货订单总金额</td>
						<td>￥${TOrderMONEY}</td>
					</tr>
					<tr>
						<td>实际订单数</td>
						<td>${SOrderNum}</td>
						<td>实际订单总金额</td>
						<td>￥${SOrderMONEY}</td>
					</tr>
					<tr>
						<td colspan="2">成本金额</td>
						<td colspan="2">￥${CostMONEY}</td>
					</tr>
					<tr>
						<td colspan="2">利润</td>
						<td colspan="2">￥${ProfitMONEY}</td>
					</tr>
				</table>
			</article>
		</section>

	</body>

	<script>
	
	laydateTwo_max0('#choosetime', function() {
			
		});

		//保持搜索条件
		$("#selectType").val(${map.selectType});
		/*查询*/
		function searchOrder() {
			$("#From").submit();
		}

		/*导出*/
		function educeOrder() {
			layer.open({
				type: 1,
				title: "订单报表",
				closeBtn: 1,
				area: ['500px', ''],
				content: $("#educeDiv"),
				btn: ['确定导出'],
				yes: function(index) {
					$("#From").attr("action","exportOrder");
					$("#From").submit();
					$("#From").attr("action","Order");
					layer.close(index);
					//laysuccess("导出成功");
				}
			});
		}
	</script>
</html>