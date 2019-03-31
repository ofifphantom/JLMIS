<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="utf-8" />
		<title>综合检索</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		<style>
			.search-div .btn_search {
				margin-right: 0;
			}
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">综合检索</h4>
					<form id="From" action="Search" method="post">
						<div class="search-div clearfix">
							<div class="clearfix">
								<span class="l_f"> 
									用户： <input type="text" name='name' value="${map.name}" />
								</span>
								<span class="l_f"> 
									手机号： <input type="text" name='phone' value="${map.phone}" />
								</span>
								<span class="l_f"> 
									商品： <input type="text" name='Sname' value="${map.Sname}" />
								</span>
								<span class="l_f"> 
									订单号： <input type="text" name='OrderNo' value="${map.OrderNo}" />
								</span>
								<span class="l_f"> 
									时间： <input type="text" name='OrderTime' value="${map.OrderTime}" id="choosetime" placeholder="请选择时间" readonly=""  />
								</span>
								<span class="r_f"> 
									<input type="button" onclick="Search()" class="btncss btn_search" value="搜索" />
								</span>
							</div>
	
						</div>
					</form>
					<div class="opration-div clearfix">
					</div>
					<div class="table_menu_list">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>订单号</th>
									<th>商品名称</th>
									<th>用户名</th>
									<th>手机号</th>
									<th>创建时间</th>
								</tr>

							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty list}">
										<c:forEach items="${list}" var="var" varStatus="vs">
											<tr>
												<td>${var.orderNo}</td>
												<td>
													<c:if test="${not empty var.orderDetails}">
														<c:forEach items="${var.orderDetails}" var="var1" varStatus="vs1">
															<c:choose>
																<c:when test="${vs1.index<fn:length(var.orderDetails) }">
																${var1.goodsName}/${var1.goodsSpecificationName}<br>
																</c:when>
																<c:otherwise>
																${var1.goodsName}/${var1.goodsSpecificationName}
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:if>
												</td>
												<td>${var.user.name}</td>
												<td>${var.user.phone}</td>
												<td><fmt:formatDate type="both" value="${var.placeOrderTime}"/></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="5">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script>
		laydate.render({
			elem: "#choosetime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
		
		function Search() {
			$("#From").submit();
		}
	</script>

</html>