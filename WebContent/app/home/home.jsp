<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath}/static/assets/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/font-awesome.min.css" />
<link href="${pageContext.request.contextPath}/static/assets/css/codemirror.css" rel="stylesheet">
<!--[if IE 7]>
		  <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
		<![endif]-->
<!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->
<script src="${pageContext.request.contextPath}/static/assets/js/ace-extra.min.js"></script>
<!--[if lt IE 9]>
		<script src="assets/js/html5shiv.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
<!--[if !IE]> -->
<script src="${pageContext.request.contextPath}/static/assets/js/jquery.min.js"></script>
<!-- <![endif]-->
<script src="${pageContext.request.contextPath}/static/assets/dist/echarts.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/bootstrap.min.js"></script>


<title>无标题文档</title>
</head>

<body>
	<div class="page-content clearfix">

		<div class="news_style" style="margin-top: 30px; width: 400px;">
			<div class="title_name">物业各项费用应缴提醒   
			<select id="days1" style="float: right;margin: 8px 5px;font-size:13.5px;" onchange="overdue()">
							<option value="0">-预警天数-</option>	
							<option value="1">1天</option>
							<option value="3">3天</option>
							<option value="5">5天</option>
							<option value="7">7天</option>
						</select>
			</div>
			<div style="width: 400px; height: 284px; overflow: auto;">
				<ul class="list" id="fy" style="overflow: auto;">
				</ul>
			</div>
		</div>
		<div class="news_style" style="margin-top: 30px; width: 400px;">
			<div class="title_name">物业各项费用逾期提醒
			<select id="days2" style="float: right;margin: 8px 5px;font-size:13.5px;" onchange="shouldPay()">
							<option value="0">-预警天数-</option>	
							<option value="-7">7天</option>
							<option value="-15">15天</option>
							<option value="-30">30天</option>
							<option value="-90">90天</option>
						</select>
			</div>
			<div style="width: 400px; height: 284px; overflow: auto;">
				<ul class="list" id="yq" style="overflow: auto;">
				</ul>
			</div>
		</div>
		<div class="news_style" style="margin-top: 30px; width: 400px;">
			<div class="title_name">物业维修提醒</div>
			<div style="width: 400px; height: 284px; overflow: auto;">
				<ul class="list" id="wx">
				</ul>
			</div>
		</div>
		<div class="news_style" style="margin-top: 30px; width: 400px;">
			<div class="title_name">社区活动提醒
			<select id="days3" style="float: right;margin: 8px 5px;font-size:13.5px;" onchange="communityActivityMsg()">
							<option value="0">-提醒天数-</option>	
							<option value="1">1天</option>
							<option value="3">3天</option>
							<option value="5">5天</option>
							<option value="7">7天</option>
						</select>
			</div>
			<div style="width: 400px; height: 284px; overflow: auto;">
				<ul class="list" id="sqhd">
				</ul>
			</div>
		</div>
	</div>


	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		$(function() {
			overdue();
			shouldPay();
			repairMsg();
			communityActivityMsg();
		});
		
		function overdue(){
			$("#fy").empty();
			var day1=$("#days1").val();
			if(day1==0){
				day1=7;
			}

			$.ajax({
						url : contextPath + "/com/tyzd/tenement/home/getOverdueMsg?day="+day1,
						type : "POST",
						dataType : "json",
						success : function(data) {
							if(data.length==0){
								$("#fy")
								.append(
										" <li><i class='icon-bell red'></i><b>暂无数据</b></li>");
							
							}
							for(var i=0;i<data.length;i++){
								if(data[i].overdueDay==0){
									$("#fy")
									.append(
											" <li><i class='icon-bell red'></i><b>"+data[i].buildingName+"#"+data[i].buildingUnit+"-"+data[i].buildingHouseNumber+"</b>的业主的<b>"+data[i].payServiceName+"</b>将于今天天到期</li>");
								}
								else{
									$("#fy")
									.append(
											" <li><i class='icon-bell red'></i><b>"+data[i].buildingName+"#"+data[i].buildingUnit+"-"+data[i].buildingHouseNumber+"</b>的业主的<b>"+data[i].payServiceName+"</b>还有"+data[i].overdueDay+"天到期</li>");
									}
								}
							
			
							
						},

					});
		}
		
		function shouldPay(){
			
			$("#yq").empty();
			var day2=$("#days2").val();
			if(day2==0){
				day2=-90;
			}
			$.ajax({
						url : contextPath + "/com/tyzd/tenement/home/getShouldPayMsg?day="+day2,
						type : "POST",
						dataType : "json",
						success : function(data) {
							if(data.length==0){
								$("#yq")
								.append(
										" <li><i class='icon-bell red'></i><b>暂无数据</b></li>");
							
							}
							
							for(var i=0;i<data.length;i++){
								
								
									$("#yq")
									.append(
											" <li><i class='icon-bell red'></i><b>"+data[i].buildingName+"#"+data[i].buildingUnit+"-"+data[i].buildingHouseNumber+"</b>的业主的<b>"+data[i].payServiceName+"</b>已欠费"+data[i].overdueDay+"天</li>");
									
							}
			
							
						},

					});
		}
		
        function repairMsg(){
			
			$("#wx").empty();
			
			$.ajax({
						url : contextPath + "/com/tyzd/tenement/home/getRepairMsg",
						type : "POST",
						dataType : "json",
						success : function(data) {
							if(data.length==0){
								$("#wx")
								.append(
										" <li><i class='icon-bell red'></i><b>暂无数据</b></li>");
							
							}
							for(var i=0;i<data.length;i++){
								if(data[i].repairStateID==1){
									$("#wx")
									.append(
											" <li><i class='icon-bell red'></i><b>维修单号为:"+data[i].repairNum+"</b>的维修单子可接单</li>");
									
								}
								else if(data[i].repairStateID==6){
									$("#wx")
									.append(
											" <li><i class='icon-bell red'></i><b>维修单号为:"+data[i].repairNum+"</b>的维修单子需结算</li>");
									
								}
								
									
							}
			
							
						},

					});
		}
function communityActivityMsg(){
			
			$("#sqhd").empty();
			var day3=$("#days3").val();
			if(day3==0){
				day3=7;
			}
			$.ajax({
						url : contextPath + "/com/tyzd/tenement/home/getCommunityActivityMsg?day="+day3,
						type : "POST",
						dataType : "json",
						success : function(data) {
							if(data.length==0){
								$("#sqhd")
								.append(
										" <li><i class='icon-bell red'></i><b>暂无数据</b></li>");
							
							}
							for(var i=0;i<data.length;i++){
									$("#sqhd")
									.append(
											" <li><i class='icon-bell red'></i><b>活动名称为:"+data[i].act_name+"的活动</b>将于<b>"+data[i].formatActdate+"</b>举行</li>");
									
								}
							
							
						},

					});
		}
	</script>
</body>
</html>