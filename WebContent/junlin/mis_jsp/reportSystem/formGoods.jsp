<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>         
<%
	String path = request.getContextPath();
	String basePath = path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="utf-8" />
		<title>商品销量报表</title>
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
					<h4 class="text-title">商品销量报表</h4>
					<div class="search-div clearfix">
					<form id="From" action="Goods" method="post">
						<div class="clearfix">
							<span class="l_f"> 
								下单时间：<input type="text" name="choosetime" value="${map.choosetime}" id="choosetime" placeholder="请选择时间" readonly="" style="width: 200px;" />
							</span>
							<span class="l_f"> 
							<input id="citOne" name="citOne" style="display: none;" value="${map.citOne}">
							<input id="citTwo" name="citTwo" style="display: none;" value="${map.citTwo}">
								商品一级分类：<select  id="classificationOne"  onchange="loadClassificationTwo()" name="classificationOne">
											<option value="-1">--全部--</option>
										</select>
								商品二级分类：<select  id="classificationTwo"  name="classificationTwo" onchange="loadClassificationTwo1()">
											<option value="-1">--全部--</option>
										</select>
							</span>
							<span class="r_f"> 
								<input type="button"  class="btncss jl-btn-importent"  value="搜索" onclick="searchOrder()" />
								<input type="button"  class="btncss jl-btn-defult" value="导出" onclick="educeGoods()" />
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
									<th>商品编号</th>
									<th>商品名称</th>
									<th>销售总量</th>
									<th>售后数量</th>
									<th>实际销售数量</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty list}">
										<c:forEach items="${list}" var="var" varStatus="vs">
											<tr>
												<td>${var.identifier}</td>
												<td>${var.goods_name}/${var.goods_specification_name}</td>
												<td>${var.NUM}</td>
												<td>${var.SNUM}</td>
												<td>${var.NUM-var.SNUM}</td>
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

		<section id="educeDiv" style="display: none;">
			<article>
				<p>导出时间：${not empty map.choosetime?map.choosetime:'全部'}</p>
				<p>商品种类：${not empty map.citOne&&map.citOne!=''&&not empty map.classificationOne?not empty map.citTwo&&map.citTwo!=''&&not empty map.classificationTwo?map.citOne.concat('-'.concat(map.citTwo)):map.citOne:'全部'}</p>
			</article>
			<article>
				<table class="table table-bordered">
					<tr>
						<td>商品数量</td>
						<td>${GoodsNum}</td>
					</tr>
					<tr>
						<td>商品销量数量</td>
						<td>${SGoodsNum}</td>
					</tr>
					<tr>
						<td>售后数量</td>
						<td>${BGoodsNum}</td>
					</tr>
					<tr>
						<td>实际销售数量</td>
						<td>${RGoodsNum}</td>
					</tr>
				</table>
			</article>
		</section>

	</body>

	<script>
		laydateTwo_max0('#choosetime', function() {
			
		});

		/*查询*/
		function searchOrder() {
			$("#From").submit();
		}

		var goodsData=null;
		var activityData = null;
		var oneIndex=-1;
		var twoIndex = -1;
		//下拉列表赋值   一级分类
		$.ajax({
			url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/getClassificationByParentId' ,
			type : "POST",
			async:false,
			dataType : "json",
			success : function(data) {
				goodsData = data;
			}
		}); 
		
		
		function chooseGoods(){
				$("#classificationOne").empty();
				$("#classificationTwo").empty();
				$("#classificationOne").append('<option value="-1" selected="selected">--全部--</option>');
				$("#classificationTwo").append('<option value="-1" selected="selected">--全部--</option>');
				for ( var i = 0; i < goodsData.length; i++) {
					var option = $("<option data-index="+i+" value='"+goodsData[i].id+"'>"+ goodsData[i].name+"</option>");
					$("#classificationOne").append(option);
				}
		}
		/* 选择一级分类 给二级分类的下拉框赋值  */
		function loadClassificationTwo() {
			$("#classificationTwo").empty();
			//传入名称赋值
			$("#citOne").val($("#classificationOne").find("option:selected").text());

			if($("#classificationOne").val() == "-1") {
				$("#classificationTwo").append("<option value='-1' selected>--全部--</option>");
				return;
			}
			if($("#classificationOne").val()!="-1"){
				oneIndex = $("#classificationOne").find("option:selected").attr("data-index");
				var data = goodsData[oneIndex]["twoClassifications"];
				$("#classificationTwo").append("<option value='-1' selected>--全部--</option>");
				for ( var i = 0; i < data.length; i++) {
					var option = $("<option data-index="+i+" value='" + data[i].id + "'>" +data[i].name + "</option>");
					$("#classificationTwo").append(option);
				} 
			}
		}
		
		/* 给二级分类的	//传入名称赋值  */
		function loadClassificationTwo1() {
			$("#citTwo").val($("#classificationTwo").find("option:selected").text());
		}
		
		/*导出*/
		function educeGoods() {
			layer.open({
				type: 1,
				title: "商品销量报表",
				closeBtn: 1,
				area: ['500px', ''],
				content: $("#educeDiv"),
				btn: ['确定导出'],
				yes: function(index) {
					$("#From").attr("action","exportGoods");
					$("#From").submit();
					$("#From").attr("action","Goods");
					layer.close(index);
					laysuccess("导出成功");
				}
			});
		}
		
		
		chooseGoods();
		$("#classificationOne").val(${map.classificationOne});
		loadClassificationTwo();
		$("#classificationTwo").val(${map.classificationTwo});
		loadClassificationTwo1();
	</script>
</html>