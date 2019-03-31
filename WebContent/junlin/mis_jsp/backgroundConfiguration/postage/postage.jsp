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
		<title>包邮设置</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		<script type="text/javascript" src="${pageContext.request.contextPath}/junlin/js/jquery-citys/jquery.citys.js"></script>

<!-- 		<script src="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js"></script> -->


		<style>
		
			.search-div {
				padding-top: 30px;
			}
			
			.search-div>.clearfix{
				padding-top: 10px;
			}
			
			.title_lf {
				width: 90px;
				text-align: right;
				padding-right: 5px;
				line-height: 30px;
			}
			
			.location-ul li {
				display: inline-block;
				width: 200px;
				text-align: left;
				line-height: 30px;
			}
			
			.delli-div {
				display: inline-block;
				color: #ff7e7e;
			}
			
		</style>

	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">包邮设置</h4>
					<div class="search-div">
						<div class="clearfix">
							<div class="jl_f_l title_lf">
								包邮金额&nbsp;<i class="text-danger">*</i>:
							</div>
							<input type="text" value="" id="freeDeliveryMoney" placeholder="填写金额" onkeyup="pressMoney(this)"/>
						</div>
						<div class="clearfix">
							<div class="jl_f_l title_lf">
								范围&nbsp;<i class="text-danger">*</i>:
							</div>
							<div class="jl_f_l citys" style="width: 80%;" id="location" >
								<ul class="clearfix location-ul" id="show_location">
									
								</ul>
								<div id="select_location">
								<select id="province" name="province">
								</select>
								<select id="city" name="city">
								</select>
								<select id="area" name="area">
								</select>
								<select id="ring" name="ring">
								<option value="-1" data-name='全部'>--全部--</option>
								<option value="2" data-name='二环'>二环</option>
								<option value="3" data-name='三环'>三环</option>
								<option value="4" data-name='四环'>四环</option>
								<option value="5" data-name='五环'>五环</option>
								<option value="6" data-name='六环'>六环</option>
								<option value="7" data-name='七环'>七环</option>
								</select>
								<button type="button" class="btncss jl-btn-defult" id="location_add"><i class="fa fa-plus"></i> 新增</button>
								</div>
							</div>
						</div>
						<div class="clearfix">
							<div class="jl_f_l title_lf">
								基础邮费&nbsp;<i class="text-danger">*</i>:
							</div>
							<input type="text" value="" id="basePostage" placeholder="填写不包括基础邮费" onkeyup="pressMoney(this)"/>
						</div>
						<div class="clearfix text-center" style="margin-top: 20px;">
							<input type="button" class="btncss jl-btn-importent"  value="提交" onclick="submitValue()"/>
							<input type="reset" class="btncss jl-btn-defult" value="取消" />
						</div>

					</div>

				</div>
			</div>
		</div>

	</body>
	<script>
	/*  if(remote_ip_info){
        $('#location').citys({province:remote_ip_info['province'],city:remote_ip_info['city'],area:remote_ip_info['district']});
    }  */
    
    var selected_options=[]; //保存选中的地址
    var index=0;  //第几个选中的
    (function abc(){
    	$('#location').citys({code:110000});
        $('#province').attr("disabled","disabled");
    })();
       
    jQuery(function($) { 	
    	//进入该页面时获取信息显示到界面上
    	$.ajax({
			url: '<%=basePath%>/backgroundConfiguration/postage/freedelivery/getFreeDeliveryMsg',
			type: "POST",
			dataType: "json",
			async: false,
			cache: false,
			success: function(data) {
				if(data!=null){
					$("#freeDeliveryMoney").val(data.freeDeliveryMoney);
					$("#basePostage").val(data.basePostage);
					var addressMsg="";
					for(var i=0;i<data.freeDeliveryAddresses.length;i++){
						//通过data-code这个属性名获取其对应的html(即中文名称)
						var province_html=$("#province").find("option[data-code='"+data.freeDeliveryAddresses[i].province+"']").html();
						var area_html=$("#area").find("option[data-code='"+data.freeDeliveryAddresses[i].county+"']").html();
						var ring_html=$("#ring").find("option[value='"+data.freeDeliveryAddresses[i].ring+"']").attr("data-name");
						//把获取到的名称组合起来显示到界面上
						var name=province_html+" "+area_html+" "+ring_html;
						var chooseAddress='<li date-id='+index+'>'+name+'<div onclick="delli(this)" class="delli-div"><i class="fa fa-times"></i></div></li>';
						$(".location-ul").append(chooseAddress);
						//把选中的option的值和编码还有名称保存下来，方便删除的时候append。
						var selected_option={"province":data.freeDeliveryAddresses[i].province,"area":{"value":data.freeDeliveryAddresses[i].county,"dataCode":data.freeDeliveryAddresses[i].county,"name":area_html},"ring":data.freeDeliveryAddresses[i].ring};
						selected_options[index]=selected_option;
						index++;
						$("#area option").each(function(){
							if($(this).attr("value")==data.freeDeliveryAddresses[i].county){
								$(this).remove();
							}
						})
						/* 如果添加完了，需要把选择框给隐藏 */
						if($("#area option").size()==0){
							$("#select_location").css("display","none");
						}
					}
				}

			}
		});
    })
         
	var province_index;
	var city_index;
	var area_index;
	var ring_index;
	var province_name,city_name,area_name,ring_name;
		$("#location_add").click(function(){
			/* 获取选中的是第几个 */
			province_index=document.getElementById("province").selectedIndex;
			city_index=document.getElementById("city").selectedIndex;
			area_index=document.getElementById("area").selectedIndex;
			ring_index=document.getElementById("ring").selectedIndex;
			/* 获取选中的option的名称*/
			province_name=$("#province").find("option:selected").html();
			city_name=$("#city").find("option:selected").html();
			area_name=$("#area").find("option:selected").html();
			ring_name=$("#ring").find("option:selected").attr("data-name");
			var name="";
			if($("#city").css("display")!='none'){
				name=province_name+" "+city_name+" "+area_name+" "+ring_name;
			}
			else{
				name=province_name+" "+area_name+" "+ring_name;
			}
			/* 往显示选择范围的div中添加选中的数据 */
			var $li='<li date-id='+index+'>'+name+'<div onclick="delli(this)" class="delli-div"><i class="fa fa-times"></i></div></li>';
			$(".location-ul").append($li);
			//把选中的option的值和编码还有名称保存下来，方便删除的时候append。
			var selected_option={"province":$("#province").find("option:selected").val(),"area":{"value":$("#area").find("option:selected").val(),"dataCode":$("#area").find("option:selected").attr("data-code"),"name":area_name},"ring":$("#ring").find("option:selected").val()};
			selected_options[index]=selected_option;
			console.log(selected_options);
			//不管后面选的是几环，都要删除当前选中的区
			document.getElementById("area").options.remove(area_index);
			$("#ring").val(-1);
			/* 如果添加完了，需要把选择框给隐藏 */
			if($("#area option").size()==0){
				$("#select_location").css("display","none");
			}
			index++;
			
			
			console.log(selected_options);
		})

		/*
		 * 删除地址
		 */
		function delli(thisdelli) {
			/* 获取删除的li的id */
			var dataIndex=$(thisdelli).parent().attr("date-id");
			$(thisdelli).parent().remove();
			if($("#area option").size()==0){
				$("#select_location").css("display","block");
			}
			/* 从添加时拼好的json串中取出相应的值，拼成option放入区中 */
			var optionHtml='<option value='+selected_options[dataIndex].area.value+' data-code='+selected_options[dataIndex].area.dataCode+'>'+selected_options[dataIndex].area.name+'</option>';
			$("#area").append(optionHtml);
			//如果把添加的地址删完了，需要重置下面两个的值。
			if($("#show_location>li").length==0){
				selected_options=[];
			    index=0;
			}
			//此时要删除选中的结果集里的该项
			else{
				//(从哪个位置开始，移除几个)
				selected_options.splice(dataIndex,1);
				
			}
			
		}
		
		/* 提交 */
		function submitValue(){
			if($("#freeDeliveryMoney").val()==""){
				laywarn("包邮金额不能为空!")
				return ;
			}		
			if($("#show_location>li").length==0){
				laywarn("请选择范围!")
				return ;	
			}
			if($("#basePostage").val()==""){
				laywarn("基础邮费不能为空!")
				return ;	
			}
				
			$.ajax({
				url: '<%=basePath%>/backgroundConfiguration/postage/freedelivery/addOrUpdateFreeDeliveryMsg',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: {
					"freeDeliveryAddress":JSON.stringify(selected_options),
					"freeDeliveryMoney":$("#freeDeliveryMoney").val(),
					"basePostage":$("#basePostage").val()
				},
				success: function(data) {
					if(data.success) {
						laysuccess(data.msg);
					} else {
						layfail(data.msg);
					}

				}
			});
		}
	</script>
</html>