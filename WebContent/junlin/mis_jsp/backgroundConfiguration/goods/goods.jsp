<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/";
	String ipPath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();//例：http://localhost:8080/
	String PSIPath=request.getScheme()+"://"+request.getServerName()+":"+"8001";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8" />
<title>商品列表</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/common.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/junlin/css/mine/goods.css" />

<script src="${pageContext.request.contextPath}/junlin/js/photosload.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/junlin/js/editor/wangEditor.js"></script>
<link
	href="${pageContext.request.contextPath}/junlin/js/jQuery-searchableSelect/jquery.searchableSelect.css"
	rel="stylesheet" type="text/css">
<script
	src="${pageContext.request.contextPath}/junlin/js/jQuery-searchableSelect/jquery.searchableSelect.js"></script>
<style>
.search-div .btn_search {
	margin-right: 34px;
}

.search_Classification_span {
	margin-left: 38px;
}
#commentTable{
width:100% !important;
margin-bottom:10px;
}

</style>


</head>

<body class="content">
	<div class="page-content clearfix">
		<div id="Member_Ratings">
			<div class="d_Confirm_Order_style" style="margin-top: 10px;">
				<h4 class="text-title">商品列表</h4>
				<div class="search-div clearfix">
					<div class="clearfix">
						<span class="l_f"> 编号： <input type="text" value=""
							id="identifier" onkeyup="cky(this)" />
						</span> <span class="l_f"> 名称： <input type="text" value=""
							id="search_name" onkeyup="cky(this)" />
						</span> <span class="l_f"> 创建人姓名： <input type="text" value=""
							id="operatorIdentifier" onkeyup="cky(this)" />
						</span> <span class="l_f"> 创建时间： <input type="text" value=""
							id="search_operatorTime" placeholder="请选择时间" readonly="" />
						</span> <span class="l_f"> 促销： <select id="select_activity">
								<option value="-1" selected="selected">--请选择--</option>
								<!-- <option value="现货">现货</option> -->
								<option value="预售">预售</option>
								<option value="折扣">折扣</option>
								<option value="团购">团购</option>
								<option value="秒杀">秒杀</option>
								<option value="立减">立减</option>
								<option value="满减">满减</option>

						</select>
						</span> <span class="l_f"> 库存： <select id="select_gxcGoodsStock">
								<option value="-1" selected="selected">--请选择-</option>
								<option value="0">有库存</option>
								<option value="1">无库存</option>
						</select>
						</span> <span class="l_f search_Classification_span"> 分类： <select
							id="search_oneClassificationSelect"
							onchange="getDataToTwoClassification_search(this)">
						</select> <select id="search_twoClassificationSelect">
						</select>
						</span> <span class="r_f"> <input type="button"
							class="btncss btn_search" id="btn_search" value="搜索" />
						</span>
					</div>

				</div>
				<div class="opration-div clearfix">
					<span class="r_f">
						<button type="button" class="btncss jl-btn-defult"
							onclick="goodsEduce()" style="margin-right: 0;">
							<i class="fa fa-download"></i> 导出
						</button>

					</span> <span class="r_f">
						<button type="button" class="btncss jl-btn-defult"
							onclick="goodsDel()">
							<i class="fa fa-trash"></i>删除
						</button>
					</span> <span class="r_f">
						<button type="button" class="btncss jl-btn-defult"
							onclick="goodsRecommend()">
							<i class="fa  fa-thumbs-up"></i>推荐
						</button>
					</span> <span class="r_f">
						<button type="button" class="btncss jl-btn-defult"
							onclick="goodsOff()">
							<i class="fa fa-arrow-down"></i>下架
						</button>
					</span> <span class="r_f">
						<button type="button" class="btncss jl-btn-defult"
							onclick="goodsOn()">
							<i class="fa fa-arrow-up"></i>上架
						</button>
					</span> <span class="r_f">
						<button type="button" class="btncss jl-btn-importent"
							onclick="goodsAdd()">
							<i class="fa fa-plus"></i>新增
						</button>
					</span> <span class="jl_f_l"> <input type="checkbox" id="checkAll"
						style="margin: 0 5px 0 0;" onclick="checkboxController(this)" />
					</span> <span class="jl_f_l"> <label for="checkAll">全选</label>
					</span>

				</div>
				<div class="table_menu_list">
					<form id="datatable_form">
						<table class="table table-striped table-hover col-xs-12"
							id="datatable">
							<thead class="table-header">
								<tr>
									<th>选择</th>
									<th>商品规格编号</th>
									<th>名称</th>
									<th>价格</th>
									<th>规格</th>
									<th>库存</th>
									<th>商品属性</th>
									<th>促销状态</th>
									<th>是否推荐</th>
									<th>状态</th>
									<th>创建人</th>
									<th>上下架时间</th>
									<th width="21%">操作</th>
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
		<form class="container" id="inserOrUpdateDiv">
			<div class="form-group hidden-div hidden">
				<label class="col-xs-2 control-label">商品规格编号&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-4">
					<input type="text" class="form-control disabled"
						id="update_identifier" name="updateIdentifier11"
						disabled="disabled" value="1234485678" />
				</div>

				<label class="col-xs-1 control-label">创建人&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-4">
					<input type="text" class="form-control disabled"
						id="update_operator_identifier" disabled="disabled" value="张三" />
				</div>
			</div>
			<div class="form-group hidden-div" style="display: none;">
				<label class="col-xs-2 control-label">上架时间&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-4">
					<input type="text" class="form-control disabled"
						disabled="disabled" id="update_onShelvesTime"
						value="2017-11-09 17:18" />
				</div>
			</div>
			<div class="form-group" style="display: none;">
				<label for="goodsDetailId" class="col-xs-2 control-label">商品详情id&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-9">
					<input type="text" class="form-control" id="goodsDetailId"
						name="goodsDetailId" />
				</div>
			</div>
			<div class="form-group" style="display: none;">
				<label for="goodsSpecDetailId" class="col-xs-2 control-label">商品规格id&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-9">
					<input type="text" class="form-control" id="goodsSpecDetailId"
						name="goodsSpecDetailId" />
				</div>
			</div>
			<div class="form-group">
				<input type="text" class="form-control hidden" id="commoditySpecId"
						name="commoditySpecId" />
				<input type="text" class="form-control hidden" id="commodityId"
						name="commodityId" />
				<input type="text" class="form-control hidden" id="commoditySpecIdentifier"
						name="commoditySpecIdentifier" />
				<label for="goodsname" class="col-xs-2 control-label">商品名称&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-9 hidden" id="goodsname_input_div">
					<input type="text" class="form-control" id="goodsname_input" name="goodsname_input" disabled="disabled"> 
				</div>
				<div class="col-xs-9 " id="goodsname_select_div">
					<select class="form-control" id="goodsname">
					</select>
					<!-- <input type="text" class="form-control" id="goodsname" name="goodsname" data-provide="typeahead" placeholder="输入编号、名称、关键字可自动进行检索" onfocus="clearDisabled()" onblur="searchClassification()" onkeyup="cky(this)"/> -->
				</div>
			</div>
			<div class="form-group">
				<label for="specifications" class="col-xs-2 control-label">规格&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-4">
					<input type="text" class="form-control" id="specifications"
						name="specifications" disabled="" placeholder="请先选择商品名称" />
				</div>
				<label for="oldPrice" class="col-xs-1 control-label">原始价格</label>
				<div class="col-xs-4">
					<input type="text" class="form-control" id="oldPrice" placeholder="请输入划横线部分的价格"
						name="oldPrice" onkeyup="this.value=this.value.replace(/[^\d.]/g, '').replace(/^\./g, '').replace('.', '$#$').replace(/\./g, '').replace('$#$', '.')" />
				</div>
			</div>
			<div class="form-group">
				<label for="goodsPrice" class="col-xs-2 control-label">价格&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-4">
					<input type="text" class="form-control" id="goodsPrice"
						name="goodsPrice" disabled="disabled" placeholder="请先选择商品名称"
						onkeyup="this.value=this.value.replace(/[^\d.]/g, '').replace(/^\./g, '').replace('.', '$#$').replace(/\./g, '').replace('$#$', '.')" />
				</div>
				<label class="col-xs-1 control-label">分类&nbsp;<i class="text-danger">*</i></label> <input
					style="display: none;" id="classificationSelect"
					name="classificationSelect" />
				<div class="col-xs-4">
					<div class="col-xs-6" style="padding: 0;">
						<select class="form-control" id="oneClassificationSelect"
							name="oneClassificationSelect"
							onchange="getDataToTwoClassification_addOrUpdate(this)">
						</select>
					</div>
					<div class="col-xs-6" style="padding: 0;">
						<select class="form-control" style="border-left: 0;"
							id="twoClassificationSelect" name="twoClassificationSelect">
						</select>
					</div>
				</div>
				
			</div>
			<div class="form-group">
				<label for="key_word" class="col-xs-2 control-label">关键词&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-4">
					<input type="text" class="form-control" id="key_word"
						name="keyWord" maxlength="10" placeholder="请输入10个字数以下。"
						onkeyup="cky(this)" />
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-xs-2 control-label">展示图&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-9 showDiv">
					<div class="big-photo">
						<div class="preview" id="preview0">
							<img id="imghead0" border="0"
								src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg"
								width="90" height="90" onclick="$('#previewImg0').click();"
								alt="0">
						</div>
						<input type="file" onchange="previewImage(this,0)"
							style="display: none;" id="previewImg0" name="previewImg"
							accept="image/gif,image/jpeg,image/jpg,image/png,image/svg">
					</div>

				</div>
			</div>
			<div class="form-group">
				<label for="introduction" class="col-xs-2 control-label">简介&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-9">
					<textarea class="form-control" id="introduction" maxlength="100"
						name="introdution" rows="3" placeholder="请输入100个字数以下。"
						onkeyup="cky(this)"></textarea>
				</div>
			</div>
			<div class="form-group">
				<input style="display: none;" id="contant" name="contant" /> <label
					for="introduction" class="col-xs-2 control-label">详情&nbsp;<i class="text-danger">*</i></label>
				<div class="col-xs-9">
					<div id="editor">
						<p>
							请<b>删除此行</b>并在此处进行编辑。
						</p>
					</div>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-xs-2"></div>
				<div class="col-xs-9 tip">注：<br/>展示图上传的图片大小应小于2M,尺寸建议为1500*900（单位：像素）</div>
				</div>
		</form>
	</div>

	<!--商品详情-->
	<div id="lookDiv" style="display: none;">
		<form class="container">
			<input type="hidden" value="" id="goodsId"/>
			<div class="form-group">
				<label class="col-xs-2 control-label">商品规格编号:</label>
				<div class="col-xs-4" id="look_identifier">1234485678</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label">创建人:</label>
				<div class="col-xs-4" id="look_operatorIdentifier">张三</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label">创建时间:</label>
				<div class="col-xs-4" id="look_creatTime"></div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label" id="look_ShelvesTime_Name">上架时间：</label>
				<div class="col-xs-4" id="look_ShelvesTime">2017-11-09 17:18</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label">分类：</label>
				<div class="col-xs-4">
					<div class="col-xs-6" style="padding: 0;" id="look_classification">
						二级分类名称 一级分类名称</div>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-xs-2 control-label">名称：</label>
				<div class="col-xs-4" id="look_goodsDetailName">开心豆</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-xs-2 control-label">规格：</label>
				<div class="col-xs-4" id="look_specifications">100袋/箱</div>
			</div>
			<div class="form-group">
				<label for="key_word" class="col-xs-2 control-label">关键词：</label>
				<div class="col-xs-4" id="look_keyWord">花生</div>

			</div>
			<div class="form-group">
				<label for="name" class="col-xs-2 control-label">原始价格：</label>
				<div class="col-xs-4" id="look_old_price">50</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-xs-2 control-label">价格：</label>
				<div class="col-xs-4" id="look_price">50</div>
			</div>
			
			<div class="form-group">
				<label for="name" class="col-xs-2 control-label">库存：</label>
				<div class="col-xs-4" id="look_inventory">200</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-xs-2 control-label">促销状态：</label>
				<div class="col-xs-4" id="look_state"></div>
			</div>
			
			<div class="form-group">
				<label for="name" class="col-xs-2 control-label">展示图：</label>
				<div class="col-xs-9" id="look_goodsDisplayPicture">
					<img border="0"
						src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg"
						width="90" height="90">
				</div>
			</div>
			<div class="form-group">
					<label for="" class="col-xs-2 control-label">评价：</label>
					<div class="col-xs-9">
						<span class="look-span" onclick="checkEvaluate()">查看商品评价</span>

					</div>
			</div>
			<div class="form-group">
				<label for="introduction" class="col-xs-2 control-label">简介：</label>
				<div class="col-xs-9" id="look_introdution">Lorem ipsum dolor
					sit amet, consectetur adipisicing elit. Totam quia in sint iure
					nulla delectus id iusto ullam numquam ea deleniti quod sunt beatae
					consectetur repellendus temporibus et eligendi quaerat.</div>
			</div>
			
			<div class="form-group">

				<label for="introduction" class="col-xs-2 control-label">详情：</label>
				<div class="col-xs-9" id="look_details">内容很长 Lorem ipsum dolor
					sit amet, consectetur adipisicing elit. Totam quia in sint iure
					nulla delectus id iusto ullam numquam ea deleniti quod sunt beatae
					consectetur repellendus temporibus et eligendi quaerat.</div>
			</div>
			
		</form>
	</div>
	
	
	<div id="evaluateDiv"  style="display: none;">
			<div class="container">
				<div class="title-evaluate">全部评价</div>
				<table class="table-evaluate col-xs-12" id="commentTable">
					<!-- <tr>
						<td>
							<div class="evaluate">
								<div class="evaluate-person">
									<div class="evaluate-person-img">
										<img  src="../../../../static/assets/avatars/avatar.png" />
									</div>
									<div class="evaluate-person-name">
										<div class="evaluate-person-name-length">
											123456
										</div>
									</div>
								</div>
								<div class="evaluate-content">
									<div class="evaluate-content-star">
										<div class="starnone"> 
										    <div class="starWrap"> 
										      <div class="star" attr-num="1"></div> 
										    </div> 
										</div> 
									</div>
									<div class="evaluate-content-message">很棒很棒</div>
									<div class="evaluate-content-photo">
										<img onclick="evaluatePic()" class="img-responsive" layer-src="../../../../static/assets/img1/company.jpg" src="../../../../static/assets/img1/company.jpg" alt="" />
										<img onclick="evaluatePic()" class="img-responsive" layer-src="../../../../static/assets/img1/company.jpg" src="../../../../static/assets/img1/company.jpg" alt="" />
										<img onclick="evaluatePic()" class="img-responsive" layer-src="../../../../static/assets/img1/company.jpg" src="../../../../static/assets/img1/company.jpg" alt="" />
										<div style="clear: both;"></div>
									</div>
									<div class="evaluate-content-date">2018-01-24 12:14</div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="evaluate">
								<div class="evaluate-person">
									<div class="evaluate-person-img">
										<img  src="../../../../static/assets/avatars/avatar.png" />
									</div>
									<div class="evaluate-person-name">
										<div class="evaluate-person-name-length">
											12345678901234567890
										</div>
									</div>
								</div>
								<div class="evaluate-content">
									<div class="evaluate-content-star">
										<div class="starnone"> 
										    <div class="starWrap"> 
										      <div class="star" attr-num="4"></div> 
										    </div> 
										</div> 
									</div>
									<div class="evaluate-content-message">很棒很棒</div>
									<div class="evaluate-content-date">2018-01-24 12:14</div>
								</div>
							</div>
						</td>
					</tr> -->
					
				</table>
		
			</div>
			
		</div>

</body>

<script>


var oTable2;
function  getComment(){
	oTable2=$('#commentTable').dataTable({
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
				"id": $("#goodsId").val()
				
			});
		},
		"url": "<%=basePath%>backgroundConfiguration/goods/goodsManager/getCommentMsg"
		
	},

	"aoColumns": [
		{
			"mData": "id",
			"bSortable": false,
			"sWidth": "20%",
			"sClass": "center",
			"bVisible": false
			
		}, {
			"mData": "score",
			"bSortable": false,
			"sWidth": "80%",
			"sClass": "center",
			"mRender": function(data, type, row) {
					let personName=row.personName;
					let score=row.score;
					let content=row.content;
					let commentUrl=row.imgUrl;
					//console.log(row.imgUrl);
					if(commentUrl==null){
						commentUrl="";
					}
					let time=getSmpFormatDateByLong(row.time, true);
					let personPicture="";
					if(row.personPicture!=null){
						personPicture=<%=basePath%>+row.personPicture;
					}else{4
						personPicture=<%=basePath%>+"static/assets/avatars/avatar.png";
					}
				return '<tr><td><div class="evaluate"><div class="evaluate-person"><div class="evaluate-person-img"><img  src='+personPicture+' id="personImg"/></div><div class="evaluate-person-name"><div class="evaluate-person-name-length personName">'+personName+'</div></div></div><div class="evaluate-content"><div class="evaluate-content-star"><div class="starnone">'
				+'<div class="starWrap"><div class="star st" attr-num='+score+'></div> </div> </div> </div><div class="evaluate-content-message goodsContent">'+content+'</div><div class="evaluate-content-photo">'+commentUrl+'<div style="clear: both;"></div></div><div class="evaluate-content-date  commentTime">'+time+'</div></div></div></td></tr>';
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
};
		


			$(function(){
				
				$.ajax({
					url: '<%= PSIPath%>/JLPSI/commodityInfoApi/getCommodityInfo',
					type: "GET",
					dataType: "json",
					data:{},
					async: false,
					cache: false,
					success: function(data) {
						console.log(data);
						$("#goodsname").html();
						 if(data.resultData.length==0){
		                  	 $("#goodsname").append("<option value='-1' selected>--暂无数据，请到进销存系统中进行添加。--</option>");
		                    }
		                 else{
		                     $("#goodsname").append("<option value='-1' selected>--请选择商品--</option>");
		                     for(var i=0;i<data.resultData.length;i++){
		                          var option = $("<option value='"+data.resultData[i].commoditySpecId+"' attr-specName='"+data.resultData[i].specificationName+"' attr-price='"+data.resultData[i].commodityPrice+"' attr-commodityId='"+data.resultData[i].commodityId+"' attr-specIdentifier='"+data.resultData[i].specificationIdentifier+"'>"
		                              + data.resultData[i].commodityName+" 规格:"+data.resultData[i].specificationName + " 商品编号:"+data.resultData[i].commodityIdentifier+"</option>");
		                          $("#goodsname").append(option);
		                     }
		                     
		                 }
						 $("#goodsname").searchableSelect();
					}
				});	
				
			})
	
	/* 商品选择框的值改变事件 */
    function selectCommodityMsg(e,selectValId){	 
    	if(selectValId>0){
    		var specName= $("#goodsname option:selected").attr("attr-specName");
    		var commodityPrice= $("#goodsname option:selected").attr("attr-price");
    		var commodityId= $("#goodsname option:selected").attr("attr-commodityId");
    		var specIdentifier= $("#goodsname option:selected").attr("attr-specIdentifier");
    		
  
    		/* 根据购销存商品id获取mis商品id */
    		$.ajax({
				url: '<%=basePath%>backgroundConfiguration/goods/goodsManager/selectIdByCommodityId',
				type: "GET",
				dataType: "json",
				data:{
					"commodityId":commodityId
				},
				async: false,
				cache: false,
				success: function(data) {
					if(data.success){	
						$("#oneClassificationSelect").val(data.resoult.classification.parentId);
						$("#twoClassificationSelect").empty();
						var option = $("<option selected value='" + data.resoult.classification.id + "'>" +
							data.resoult.classification.name + "</option>");
						$("#twoClassificationSelect").append(option);
						$("#oneClassificationSelect").attr("disabled", "true");
						$("#twoClassificationSelect").attr("disabled", "true");
						$("#goodsDetailId").val(data.resoult.id);
						$("#key_word").val(data.resoult.keyWord);
						$("#introduction").val(data.resoult.introdution);
						$("#editor .w-e-text").html(data.resoult.details)
					}
					else{
						$("#goodsDetailId").val("");
						$("#key_word").val("");
						$("#introduction").val("");
						$("#editor .w-e-text").html("<p>请<b>删除此行</b>并在此处进行编辑。</p>")
							/* $("#classificationSelect").val(""); */
						$("#oneClassificationSelect").removeAttr("disabled");
						$("#twoClassificationSelect").removeAttr("disabled");
						getDataToOneClassification();
					}
				}
			});	
    		
    		
    		$("#specifications").val(specName);
    		$("#goodsPrice").val(commodityPrice);
    		$("#commoditySpecId").val(selectValId);
    		$("#commodityId").val(commodityId);
    		$("#commoditySpecIdentifier").val(specIdentifier);
    	}
    	else{
    		$("#specifications").val("");
    		$("#goodsPrice").val("");
    		$("#commoditySpecId").val("");
    		$("#commodityId").val("");
    		$("#oneClassificationSelect").removeAttr("disabled");
			$("#twoClassificationSelect").removeAttr("disabled");
			$("#twoClassificationSelect").empty();
			$("#twoClassificationSelect").append("<option value='-1' selected>--请先选择一级分类--</option>");
			getDataToOneClassification();
    	}
			
	}
		<%-- $('#goodsname').typeahead({
			source: function(query, process) {
				return $.ajax({
					url: '<%=basePath%>backgroundConfiguration/goods/goodsManager/getGoodsSpecificationDetailsMsgByInputValue',
					type: 'post',
					data: {
						inputValue: query
					},
					success: function(result) {
						var resultList = [];
						console.log(result.length);
						for(var i = 0; i < result.length; i++) {
							var aItem = result[i].identifier + " " + result[i].goodsDetails.name + " " + result[i].goodsDetails.keyWord + " " + result[i].specifications;
							resultList.push(aItem);
						}
						return process(resultList);
					},
				});

			},
			updater: function(obj) {
				var str = obj.split(" ");
				return str[1];
			}
		}) --%>


		var oTable1;
		var oneOrTwo="";
		var classificationId="";
		$("#btn_search").click(function() {
			oneOrTwo="";
			classificationId="";
			if($("#search_twoClassificationSelect").val()==-1&&$("#search_oneClassificationSelect").val()!=-1){
				oneOrTwo="one";
				classificationId=$("#search_oneClassificationSelect").val();
			}
			else if($("#search_twoClassificationSelect").val()!=-1){
				oneOrTwo="two";
				classificationId=$("#search_twoClassificationSelect").val();
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
								"identifier": $("#identifier").val(),
								"name": $("#search_name").val(),
								"operatorName": $("#operatorIdentifier").val(),
								"gxcGoodsStock":$("#select_gxcGoodsStock").val(),
								"oneOrTwo":oneOrTwo,
								"classificationId":classificationId,
								"activityName":$("#select_activity").val(),
								"operatorTime": $("#search_operatorTime").val()
								
							});
						},
						"url": "<%=basePath%>backgroundConfiguration/goods/goodsManager/getGoodsMsg"
					},

					"aoColumns": [{
							"mData": "id",
							"bSortable": true,
							"sWidth": "5%",
							"sClass": "center",
							"mRender": function(data, type, row) {
								return '<td><input type="checkbox" name="id" value="' + row.id + '" onclick="checkboxClick(\'#checkAll\')"/></td>';
							}
						}, {
							"mData": "identifier",
							"bSortable": false,
							"sWidth": "10%",
							"sClass": "center"
						}, {
							"mData": "goodsDetails.name",
							"bSortable": false,
							"sWidth": "10%",
							"sClass": "center",

						}, {
							"mData": "price",
							"bSortable": false,
							"sWidth": "5%",
							"sClass": "center"
						}, {
							"mData": "specifications",
							"bSortable": false,
							"sWidth": "10%",
							"sClass": "center"
						}, {
							"mData": "gxcGoodsStock",
							"bSortable": false,
							"sWidth": "5%",
							"sClass": "center"
						}, {
							"mData": "gxcGoodsState",
							"bSortable": false,
							"sWidth": "8%",
							"sClass": "center",
							"mRender": function(data, type, row) {
								if(data == 1) {
									return "预售";
								} else {
									return '现货';
								}

							}
						}, {
							"mData": "participateActivities",
							"bSortable": false,
							"sWidth": "15%",
							"sClass": "center",
							"mRender": function(data, type, row) {
								if(data == null || data=="") {
									return "无";
								} else {
									return data;
								}

							}
						},{
							"mData": "goodsDetails.recom",
							"bSortable": false,
							"sWidth": "8%",
							"sClass": "center",
							"mRender": function(data, type, row) {
								if(data == 0) {
									return "未推荐";
								} else {
									return '已推荐';
								}

							}
						},  {
							"mData": "state",
							"bSortable": false,
							"sWidth": "5%",
							"sClass": "center",
							"mRender": function(data, type, row) {
								if(data == 0) {
									return "上架中";
								} else if(data == 1) {
									return "已下架";
								} else {
									return '未上架';
								}

							}
						}, {
							"mData": "operatorIdentifier",
							"bSortable": false,
							"sWidth": "10%",
							"sClass": "center",
							"mRender": function(data, type, row) {
								if(data!=null&&data!=""){
									if(row.user!=null){
										return data+"("+row.user.name+")";
									}
									else{
										return data;
									}
									
								}
								else{
									return "无";
								}

							}
						}, {
							"mData": "state",
							"bSortable": false,
							"sWidth": "10%",
							"sClass": "center",
							"mRender": function(data, type, row) {
								if(data == 0) {
									if(row.onShelvesTime != null && row.onShelvesTime != "") {
										return getSmpFormatDateByLong(row.onShelvesTime, false);
									} else {
										return '';
									}

								} else if(data == 1) {
									if(row.offShelvesTime != null && row.offShelvesTime != "") {
										return getSmpFormatDateByLong(row.offShelvesTime, false);;
									} else {
										return '';
									}
								} else {
									return '未上架';
								}

							}
						},

						{
							"mData": "id",
							"bSortable": false,
							"sWidth": "20%",
							"sClass": "center",
							"mRender": function(data, type, row) {

								return '<td><input type="button" class="btncss" onclick=\'goodsLook(' + JSON.stringify(row) + ')\' value="详情" />' +
									'<input type="button" class="btncss" onclick="goodsEdit(' + data + ')" value="编辑" /></td>'
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
	
			/* 给新增时的一级分类下拉框赋值 */
			getDataToOneClassification();

		});

		/* 给新增时的一级分类下拉框赋值 */
		function getDataToOneClassification() {
			$("#oneClassificationSelect").empty();
			$("#twoClassificationSelect").empty();
			$("#search_oneClassificationSelect").empty();
			$("#search_twoClassificationSelect").empty();
			$.ajax({
				url: '<%=basePath%>backgroundConfiguration/classification/classification/getAllOneClassification',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				success: function(data) {

					if(data.length == 0) {
						$("#oneClassificationSelect").append("<option value='-1' selected>--暂无数据，请到一级分类页面进行添加。--</option>");
						$("#search_oneClassificationSelect").append("<option value='-1' selected>--暂无数据，请到一级分类页面进行添加。--</option>");
						$("#twoClassificationSelect").append("<option value='-1' selected>--暂无数据--</option>");
						$("#search_twoClassificationSelect").append("<option value='-1' selected>--暂无数据--</option>");
					} else {
						$("#oneClassificationSelect").append("<option value='-1' selected>--请选择--</option>");
						$("#search_oneClassificationSelect").append("<option value='-1' selected>--请选择--</option>");
						$("#twoClassificationSelect").append("<option value='-1' selected>--请先选择一级分类--</option>");
						$("#search_twoClassificationSelect").append("<option value='-1' selected>--请先选择一级分类--</option>");
						for(var i = 0; i < data.length; i++) {
							var option = $("<option value='" + data[i].id + "'>" +
								data[i].name + "</option>");
							$("#oneClassificationSelect").append(option);
							option = $("<option value='" + data[i].id + "'>" +
								data[i].name + "</option>");
							$("#search_oneClassificationSelect").append(option);
						}
					}
				}
			});
		}

		/* 选择一级分类 给二级分类的下拉框赋值 页面的搜索 */
		function getDataToTwoClassification_search(that) {
			$("#search_twoClassificationSelect").empty();
			if($(that).val() == "-1") {
				$("#search_twoClassificationSelect").append("<option value='-1' selected>--请先选择一级分类--</option>");
				return;
			}
			$.ajax({
				url: '<%=basePath%>backgroundConfiguration/classification/classification/selectTwoByOneId?id=' + $(that).val(),
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				success: function(data) {

					if(data.length == 0) {
						$("#search_twoClassificationSelect").append("<option value='-1' selected>--暂无数据，请到二级分类页面进行添加--</option>");
					} else {
						$("#search_twoClassificationSelect").append("<option value='-1' selected>--请选择--</option>");
						for(var i = 0; i < data.length; i++) {
							var option = $("<option value='" + data[i].id + "'>" +
								data[i].name + "</option>");
							$("#search_twoClassificationSelect").append(option);
						}
					}
				}
			});
		}

		/* 选择一级分类 给二级分类的下拉框赋值  新增页面*/
		function getDataToTwoClassification_addOrUpdate(that) {
			$("#twoClassificationSelect").empty();
			if($(that).val() == "-1") {
				$("#twoClassificationSelect").append("<option value='-1' selected>--请先选择一级分类--</option>");
				return;
			}
			$.ajax({
				url: '<%=basePath%>backgroundConfiguration/classification/classification/selectTwoByOneId?id=' + $(that).val(),
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				success: function(data) {

					if(data.length == 0) {
						$("#twoClassificationSelect").append("<option value='-1' selected>--暂无数据，请到二级分类页面进行添加。--</option>");
					} else {
						$("#twoClassificationSelect").append("<option value='-1' selected>--请选择--</option>");
						for(var i = 0; i < data.length; i++) {
							var option = $("<option value='" + data[i].id + "'>" +
								data[i].name + "</option>");
							$("#twoClassificationSelect").append(option);
						}
					}
				}
			});
		}

		/* 填写名称后 搜索该名称对应的分类 */
		<%-- function searchClassification() {
			$.ajax({
				url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/getGoodsDetailsMsgByName',
				type: "POST",
				dataType: "json",
				data: {
					"name": $("#goodsname").val()
				},
				async: false,
				cache: false,
				success: function(data) {
					if(data != null) {
						$("#oneClassificationSelect").val(data.classification.parentId);
						$("#twoClassificationSelect").empty();
						var option = $("<option selected value='" + data.classification.id + "'>" +
							data.classification.name + "</option>");
						/* $("#classificationSelect").val(data.classification.id); */
						$("#twoClassificationSelect").append(option);
						$("#oneClassificationSelect").attr("disabled", "true");
						$("#twoClassificationSelect").attr("disabled", "true");
						$("#goodsDetailId").val(data.id);
						$("#key_word").val(data.keyWord);
						$("#introduction").val(data.introdution);
						$("#editor .w-e-text").html(data.details)
					}
				}
			});
		} --%>

		/* 当填写名称时,份额里的输入框清空disabled事件 */
		/* function clearDisabled() {
			$("#goodsDetailId").val("");
			$("#key_word").val("");
			$("#introduction").val("");
			$("#editor .w-e-text").html("<p>请<b>删除此行</b>并在此处进行编辑。</p>")
			$("#oneClassificationSelect").removeAttr("disabled");
			$("#twoClassificationSelect").removeAttr("disabled");
			getDataToOneClassification();
		} */

		/*上传图片删除 begin*/
		function delPic(thisbtn) {
			var nextid0 = $('.showDiv img:last').attr("alt");
			var nextid = nextid0 - 0 + 1;
			var $div = '<div class="big-photo"><div class="preview" id="preview' + nextid + '"><img id="imghead' + nextid + '" border="0" src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg" width="90" height="90" onclick="$(\'#previewImg' + nextid + '\').click();" alt="' + nextid + '"></div><input type="file" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" onchange="previewImage(this,' + nextid + ')" style="display: none;" id="previewImg' + nextid + '" name="previewImg"></div>';
			if($('.showDiv').children('.big-photo').length == 10 && ($("#previewImg" + nextid0).val() == "") == false) {
				$(thisbtn).parent().remove();
				$(".showDiv").append($div);
			} else {
				$(thisbtn).parent().remove();
			}
		}
		/*上传图片删除 end*/

		/*编辑时的后台图片的删除 begin*/
		function delPic_oldPic(thisbtn) {
			publicMessageLayer("删除旧图片", function() {
				var nextid0 = $('.showDiv img:last').attr("alt");
				var nextid = nextid0 - 0 + 1;
				var $div = '<div class="big-photo"><div class="preview" id="preview' + nextid + '"><img id="imghead' + nextid + '" border="0" src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg" width="90" height="90" onclick="$(\'#previewImg' + nextid + '\').click();" alt="' + nextid + '"></div><input type="file" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" onchange="previewImage(this,' + nextid + ')" style="display: none;" id="previewImg' + nextid + '" name="previewImg"></div>';
				if($('.showDiv').children('.big-photo').length == 10 && ($("#previewImg" + nextid0).val() == "") == false) {
					$(thisbtn).parent().remove();
					$(".showDiv").append($div);
				} else {
					$.ajax({
						url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/deleteGoodsDisplayPictureByUrl',
						type: "POST",
						dataType: "json",
						data: {
							"url": $(thisbtn).parent().find("img").attr("src"),
							"id": $(thisbtn).parent().find("#oldPicId").val()
						},
						async: false,
						cache: false,
						success: function(data) {
							if(data.success) {
								$(thisbtn).parent().remove();

							} else {
								layfail(data.msg);
							}

						}
					});

				}
				
			})
			
		}
		/*编辑时的后台图片的删除 end*/

		/*富文本框调用 begin*/
		<%-- var E = window.wangEditor;
		var editor = new E('#editor');
		// 或者 var editor = new E( document.getElementById('#editor') )
		
		editor.customConfig.menus = [
		    'head',  // 标题
		    'bold',  // 粗体
		    'fontSize',  // 字号
		    'fontName',  // 字体
		    'italic',  // 斜体
		    'underline',  // 下划线
		    'strikeThrough',  // 删除线
		    'foreColor',  // 文字颜色
		    'backColor',  // 背景颜色
		    'list',  // 列表
		    'justify',  // 对齐方式
		    'quote',  // 引用
		    'image',  // 插入图片
		    'table',  // 表格
		    'undo',  // 撤销
		    'redo'  // 重复
		];
		editor.customConfig.showLinkImg = false;//隐藏网络图片
		// 下面两个配置，使用其中一个即可显示“上传图片”的tab。但是两者不要同时使用！！！
		//editor.customConfig.uploadImgShowBase64 = true;   // 使用 base64 保存图片
		editor.customConfig.uploadImgServer = '<%=basePath%>/backgroundConfiguration/goods/goodsManager/insertPicUrl' // 上传图片到服务器
			//editor.customConfig.uploadFileName  = 'myFileName'; 
		editor.customConfig.uploadFileName = 'yourFileName'
		editor.create();
		$("#editor .w-e-text").attr("name", "editorContent") --%>
		/*富文本框调用 end*/
		
		/*富文本框调用 begin*/
		var E = window.wangEditor;
		var editor = new E('#editor');
		// 或者 var editor = new E( document.getElementById('#editor') )
		
		editor.customConfig.menus = [
		    'head',  // 标题
		    'bold',  // 粗体
		    'fontSize',  // 字号
		    'fontName',  // 字体
		    'italic',  // 斜体
		    'underline',  // 下划线
		    'strikeThrough',  // 删除线
		    'foreColor',  // 文字颜色
		    'backColor',  // 背景颜色
		    'list',  // 列表
		    'justify',  // 对齐方式
		    'quote',  // 引用
		    'image',  // 插入图片
		    'table',  // 表格
		    'undo',  // 撤销
		    'redo'  // 重复
		];
		editor.customConfig.showLinkImg = false;//隐藏网络图片
		// 下面两个配置，使用其中一个即可显示“上传图片”的tab。但是两者不要同时使用！！！
		//editor.customConfig.uploadImgShowBase64 = true;   // 使用 base64 保存图片
		editor.customConfig.uploadImgServer = '<%=basePath%>/backgroundConfiguration/goods/goodsManager/insertPicUrl' // 上传图片到服务器
			//editor.customConfig.uploadFileName  = 'myFileName'; 
		editor.customConfig.uploadFileName = 'yourFileName'
		editor.create();
		$("#editor .w-e-text").attr("name", "editorContent")
		/*富文本框调用 end*/

		/*修改*/
		function goodsEdit(id) {
			$(".hidden-div").removeClass("hidden");
			//在编辑前先获取该条信息的详细信息
			$.ajax({
				url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/getMsgByGSDId',
				type: "POST",
				dataType: "json",
				data: {
					"id": id
				},
				async: false,
				cache: false,
				success: function(data) {
					$("#goodsSpecDetailId").val(data.id);
					$("#update_identifier").val(data.identifier);
					$("#update_operator_identifier").val(data.operatorIdentifier);
					$("#goodsDetailId").val(data.goodsDetails.id); //商品详情名不同时，该输入框里存储的id会做更改
					$("#goodsname_input_div").removeClass("hidden");
					$("#goodsname_input").removeClass("hidden");
					$("#goodsname_input").val(data.goodsDetails.name);
					$("#goodsname_select_div").addClass("hidden");
					if(data.oldPrice!=null&&data.oldPrice>0){
						$("#oldPrice").val(data.oldPrice);
					}
					else{
						$("#oldPrice").val("");
					}
					
					//$("#goodsname").val(data.goodsDetails.name);
					$("#oneClassificationSelect").val(data.goodsDetails.classification.parentId);

					$("#twoClassificationSelect").empty();
					var option = $("<option selected value='" + data.goodsDetails.classification.id + "'>" +
						data.goodsDetails.classification.name + "</option>");
					$("#twoClassificationSelect").append(option);
					$("#oneClassificationSelect").attr("disabled", "true");
					$("#twoClassificationSelect").attr("disabled", "true");

					$("#specifications").val(data.specifications);
					$("#goodsPrice").val(data.price);
					$("#key_word").val(data.goodsDetails.keyWord);
					$("#introduction").val(data.goodsDetails.introdution);
					$("#editor .w-e-text").html(data.goodsDetails.details);
					/* 一个商品规格可能对应多个展示图 */
					var showDivHtml = ""
					for(var i = 0; i < data.goodsDisplayPictures.length; i++) {
						 /* onclick="$(\'#previewImg' + i + '\').click();" */
						showDivHtml += '<div class="big-photo"><input style="display:none;" id="oldPicId" value="' + data.goodsDisplayPictures[i].id + '"><div class="preview" id="preview' + i + '">' +
							'<img id="imghead' + i + '" border="0" src="${pageContext.request.contextPath}/' + data.goodsDisplayPictures[i].picUrl + '" width="90" height="90" alt="' + i + '">' +
							'</div><input type="file" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" onchange="previewImage(this,' + i + ')" style="display: none;" id="previewImg' + i + '" name="previewImg"><div onclick="delPic_oldPic(this)" class="delpic-div"><i class="fa fa-times"></i></div></div>';
					}

					showDivHtml += '<div class="big-photo"><div class="preview" id="preview' + data.goodsDisplayPictures.length + '"><img id="imghead' + data.goodsDisplayPictures.length + '" border="0" src="../../../img/photo_icon.jpg" width="90" height="90" onclick="$(\'#previewImg' + data.goodsDisplayPictures.length + '\').click();" alt="' + data.goodsDisplayPictures.length + '"></div><input type="file" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" onchange="previewImage(this,' + data.goodsDisplayPictures.length + ')" style="display: none;" id="previewImg' + data.goodsDisplayPictures.length + '" name="previewImg"></div>';
					$(".showDiv").html(showDivHtml);
				}
			});

			layer.open({
				type: 1,
				title: "编辑商品详情",
				closeBtn: 1,
				area: ['100%', '100%'],
				content: $("#editDiv"),
				btn: ['提交', '取消'],
				yes: function(index) {
					$("#classificationSelect").val($("#twoClassificationSelect").val());
					$("#contant").val($("#editor .w-e-text").html());
					//填写信息的验证,若验证不同过则 不进行下面的操作	
					if(!validate_update()) {
						return;
					}

					//先查询规格是否重复
					<%-- $.ajax({
						url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/selectGoodsSDMsgBySpecificationAndGoodsSDId',
						type: "POST",
						dataType: "json",
						data: {
							"goodDetailsId": $("#goodsDetailId").val(),
							"goodsSpecificationDetailsId": $("#goodsSpecDetailId").val(),
							"specifications": $("#specifications").val()
						},
						async: false,
						cache: false,
						success: function(data) {
							/* 没有重复，可以添加 */
							if(data.success) { --%>
								var formData = new FormData($("#inserOrUpdateDiv")[0]);

								$.ajax({
									url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/updateGoodsSpecificationDetailsMsg?identifier='+$("#update_identifier").val(),
									type: "POST",
									dataType: "json",
									data: formData,
									contentType: false,
									processData: false, //processData的默认值是true。用于对data参数进行序列化处理
									async: false,
									cache: false,
									success: function(data) {
										if(data.success) {
											laysuccess(data.msg);

										} else {
											layfail(data.msg);
											layer.close(index);
										}

									}
								});
								layer.close(index);
								oTable1.fnDraw();
								$("#checkAll").removeAttr("checked");
						/* 	} else {
								layfail(data.msg);
							}

						}
					}); */
				},
				end: function(index, layero) {
					$("#oneClassificationSelect").removeAttr("disabled");
					$("#twoClassificationSelect").removeAttr("disabled");
					$("#twoClassificationSelect").empty();
					$("#twoClassificationSelect").append("<option value='-1' selected>--请先选择一级分类--</option>");
					
					layer.close(index);
					clearForm("editDiv", "", "<%=basePath%>");
					clearImgs("showDiv", "<%=basePath%>")
					$(".hidden-div").addClass("hidden");
				}
			});
		}

		/*新增*/
		function goodsAdd() {

			$("#goodsname_input").addClass("hidden");
			$("#goodsname_select_div").removeClass("hidden");
			$("#oneClassificationSelect").removeAttr("disabled");
			$("#twoClassificationSelect").removeAttr("disabled");
			layer.open({
				type: 1,
				title: "新增商品",
				closeBtn: 1,
				area: ['100%', '100%'],
				content: $("#editDiv"),
				btn: ['提交', '取消'],
				yes: function(index) {
					$("#classificationSelect").val($("#twoClassificationSelect").val());
					$("#contant").val($("#editor .w-e-text").html());
					//填写信息的验证,若验证不同过则 不进行下面的操作
					if(!validate_insert()) {
						return;
					}
					//先查询规格是否重复
					$.ajax({
						url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/selectGoodsSDMsgBySpecificationAndGoodsId',
						type: "POST",
						dataType: "json",
						data: {
							"commoditySpecificationId": $("#goodsname").val()
						},
						async: false,
						cache: false,
						success: function(data) {
							/* 没有重复，可以添加 */
							if(data.success) {
								var formData = new FormData($("#inserOrUpdateDiv")[0]);

								$.ajax({
									url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/addGoodsSpecificationDetailsMsg',
									type: "POST",
									dataType: "json",
									data: formData,
									contentType: false,
									processData: false, //processData的默认值是true。用于对data参数进行序列化处理
									async: false,
									cache: false,
									success: function(data) {
										if(data.success) {
											laysuccess(data.msg);

										} else {
											layfail(data.msg);
											layer.close(index);
										}

									}
								});
								layer.close(index);
								oTable1.fnDraw();
								$("#checkAll").removeAttr("checked");
							} else {
								layfail(data.msg);
							}

						}
					});
				},
				end: function(index, layero) {
					$("#oneClassificationSelect").removeAttr("disabled");
					$("#twoClassificationSelect").removeAttr("disabled");
					$("#twoClassificationSelect").empty();
					$("#twoClassificationSelect").append("<option value='-1' selected>--请先选择一级分类--</option>");
					
					clearSearchableSelect('goodsname');
					clearForm("editDiv", "", "<%=basePath%>");
					clearImgs("showDiv", "<%=basePath%>")
					
				}
			});
		}

		/*查看详情*/
		var flgComment=false;
		function goodsLook(row) {
			
			$("#goodsId").val(row.id);
			//在编辑前先获取该条信息的详细信息
			if(flgComment){
				oTable2.fnDraw();
			}else{
				getComment();
				flgComment=true;
			}
			
			$.ajax({
				url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/getMsgByGSDId',
				type: "POST",
				dataType: "json",
				data: {
					"id": row.id
				},
				async: false,
				cache: false,
				success: function(data) {
					$("#look_identifier").html(data.identifier);
					$("#look_operatorIdentifier").html(data.operatorIdentifier);
					//商品的状态为上架状态，则显示上架时间
					var participateActivities=row.participateActivities;
					if(participateActivities==null ||participateActivities==""){
						$("#look_state").html("无");
					}else{
						$("#look_state").html(row.participateActivities);
					}
					$("#look_creatTime").html(getSmpFormatDateByLong(data.operatorTime, true));
					if(data.state != null && data.state == 0) {
						$("#look_ShelvesTime_Name").html("上架时间：");
						$("#look_ShelvesTime").html(getSmpFormatDateByLong(data.onShelvesTime, false));
					} else if(data.state != null && data.state == 1) {
						$("#look_ShelvesTime_Name").html("下架时间：");
						$("#look_ShelvesTime").html(getSmpFormatDateByLong(data.offShelvesTime, false));
					} else {
						$("#look_ShelvesTime_Name").html("上/下架时间：");
						$("#look_ShelvesTime").html("未进行上/下架操作。");
					}
					$("#look_classification").html(data.goodsDetails.classification.parentName + "&nbsp;&nbsp;&nbsp;" + data.goodsDetails.classification.name);
					$("#look_goodsDetailName").html(data.goodsDetails.name);
					$("#look_price").html(data.price);
					$("#look_specifications").html(data.specifications);
					$("#look_inventory").html(data.gxcGoodsStock);
					$("#look_keyWord").html(data.goodsDetails.keyWord);
					$("#look_introdution").html(data.goodsDetails.introdution);
					$("#look_details").html(data.goodsDetails.details);
					if(data.oldPrice!=null&data.oldPrice>0){
						$("#look_old_price").html(data.oldPrice);
					}
					else{
						$("#look_old_price").html("无");
					}
					
					var showDivHtml = ""
					for(var i = 0; i < data.goodsDisplayPictures.length; i++) {
						showDivHtml += '<img border="0" src="${pageContext.request.contextPath}/' + data.goodsDisplayPictures[i].picUrl + '" width="90" height="90">';
					}
					$("#look_goodsDisplayPicture").html(showDivHtml);
					
				}
			});
			layer.open({
				type: 1,
				title: "商品详情",
				closeBtn: 1,
				area: ['100%', '100%'],
				content: $("#lookDiv"),
				btn: ['关闭']
			});
		}

		/*删除*/
		function goodsDel() {
			//判断有无选择
			var str = "";
			$("input:checkbox[name='id']:checked").each(function() {
				str += $(this).val() + ",";
			})
			if(str == "") {
				laywarn("请选择数据!");
				return;
			}
			//判断该商品详情或者其所属的商品有没有参加活动或者存在于热门分类以及广告中。
			$.ajax({
				url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/decideGoodsIsHasActivityBeforeDelete',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: $("#datatable_form").serialize(),
				success: function(data) {
					if(data.success) {
						publicMessageLayer("删除选中数据", function() {
							$.ajax({
								url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/delBatchByPrimaryKey',
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
						});
					} else {
						layer.msg(data.msg, {
							icon: 0,
							time: 2500
						});

					}

				}
			});
			
		}
		/*推荐*/
		function goodsRecommend() {
			//判断有无选择
			var str = "";
			$("input:checkbox[name='id']:checked").each(function() {
				str += $(this).val() + ",";
			})
			if(str == "") {
				laywarn("请选择数据!");
				return;
			}
			publicMessageLayer("推荐选中商品", function() {
				$.ajax({
					url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/recomOperation',
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
			});
		}
		/*上架*/
		function goodsOn() {
			//判断有无选择
			var str = "";
			$("input:checkbox[name='id']:checked").each(function() {
				str += $(this).val() + ",";
			})
			if(str == "") {
				laywarn("请选择数据!");
				return;
			}
			MessageLayer("你确定要将选中的商品上架吗？已上架的商品自动忽略此操作。",function(){
				$.ajax({
					url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/onOrOffShelvesOperation?onOrOff=on',
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
			<%-- layer.open({
				type: 1,
				title: "提示",
				closeBtn: 1,
				area: ['450px', '130px'],
				content: "<div class='text-center' style='line-height:40px'>你确定要将选中的商品上架吗？已上架的商品自动忽略此操作。</div>",
				btn: ['确认', '取消'],
				yes: function(index) {
					$.ajax({
						url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/onOrOffShelvesOperation?onOrOff=on',
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
					layer.close(index);
					
				}
			}); --%>

		}
		/*下架*/
		function goodsOff() {
			//判断有无选择
			var str = "";
			$("input:checkbox[name='id']:checked").each(function() {
				str += $(this).val() + ",";
			})
			if(str == "") {
				laywarn("请选择数据!");
				return;
			}
			
			MessageLayer("你确定要将选中的商品下架吗？已下架与未经过上架操作的商品自动忽略此操作。",function(){
				$.ajax({
					url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/onOrOffShelvesOperation?onOrOff=off',
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
			
			<%-- layer.open({
				type: 1,
				title: "提示",
				closeBtn: 1,
				area: ['550px', '130px'],
				content: "<div class='text-center' style='line-height:40px'>你确定要将选中的商品下架吗？已下架与未经过上架操作的商品自动忽略此操作。</div>",
				btn: ['确认', '取消'],
				yes: function(index) {
					$.ajax({
						url: '<%=basePath%>/backgroundConfiguration/goods/goodsManager/onOrOffShelvesOperation?onOrOff=off',
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
					layer.close(index);
					
				}
			}); --%>
		}

		/*导出*/
		function goodsEduce() {
			oneOrTwo="";
			classificationId="";
			if($("#search_twoClassificationSelect").val()==-1&&$("#search_oneClassificationSelect").val()!=-1){
				oneOrTwo="one";
				classificationId=$("#search_oneClassificationSelect").val();
			}
			else if($("#search_twoClassificationSelect").val()!=-1){
				oneOrTwo="two";
				classificationId=$("#search_twoClassificationSelect").val();
			}
			var export_classificationId=encodeURI(encodeURI(classificationId));
			var export_oneOrTwo=encodeURI(encodeURI(oneOrTwo));
			var export_gxcGoodsStock=encodeURI(encodeURI($("#select_gxcGoodsStock").val()));
			var export_identifier=encodeURI(encodeURI($("#identifier").val()));
			var export_name=encodeURI(encodeURI($("#search_name").val()));
			var export_activityName=encodeURI(encodeURI($("#select_activity").val()));
			var export_operatorName=encodeURI(encodeURI($("#operatorIdentifier").val()));
			var export_operatorTime=encodeURI(encodeURI($("#search_operatorTime").val()));
			
			publicMessageLayer("导出全部数据", function() {
				var URL="<%=basePath%>/backgroundConfiguration/goods/goodsManager/exportGoodsSpecificationDetails?identifier="+export_identifier+"&name="+export_name+"&operatorName="+export_operatorName+"&gxcGoodsStock="+export_gxcGoodsStock+"&oneOrTwo="+export_oneOrTwo+"&classificationId="+export_classificationId+"&activityName="+export_activityName+"&operatorTime="+export_operatorTime;
			    location.href=URL;
				return false;	
			});
		}

		/* 验证商品名称 */
		function goodsnameCheck() {
			if(!isnotEmpty($("#goodsname").val())) {
				laywarn("名称不能为空");
				return;
			}
			return true;
		}
		/* 验证商品分类 */
		function twoClassificationSelectCheck() {
			if($("#twoClassificationSelect").val() == -1) {
				laywarn("一二级分类不能为空");
				return;
			}
			return true;
		}
		/* 验证商品规格 */
		function specificationsCheck() {
			if(!isnotEmpty($("#specifications").val())) {
				laywarn("商品规格不能为空");
				return;
			}
			return true;
		}
		/* 验证商品价格 */
		function goodsPriceCheck() {
			if(!isnotEmpty($("#goodsPrice").val())) {
				laywarn("商品价格不能为空");
				return;
			}
			return true;
		}
		/* 验证商品关键词 */
		function keyWordCheck() {
			if(!isnotEmpty($("#key_word").val())) {
				laywarn("商品关键词不能为空");
				return;
			}
			return true;
		}
		/* 验证商品展示图 */
		function previewImgCheck() {
			if(!isnotEmpty($(".showDiv input:first").val())) {
				laywarn("商品展示图不能为空");
				return;
			}
			return true;
		}
		/* 验证商品简介*/
		function introductionCheck() {
			if(!isnotEmpty($("#introduction").val())) {
				laywarn("商品简介不能为空");
				return;
			}
			return true;
		}
		/* 验证商品详情*/
		function contantCheck() {
			if(!isnotEmpty($("#contant").val())) {
				laywarn("商品详情不能为空");
				return;
			}
			return true;
		}
		/* 验证原始价格是否大于价格*/
		function priceCheck() {
			if(!isnotEmpty($("#oldPrice").val())) {
				//laywarn("原始价格不能为空");
				return;
			}
			else{
				if($("#oldPrice").val()-0<$("#goodsPrice").val()-0){
					laywarn("原始价格不能小于价格");
					return;
				}
				if($("#oldPrice").val()-0>=10000000){
					laywarn("价格过大");
					return;
				}
			}
			return true;
		}
		//新增时输入信息的验证
		function validate_insert() {
			if(goodsnameCheck()) {
				if(twoClassificationSelectCheck()) {
					if(specificationsCheck()) {
						if(goodsPriceCheck()) {
							if(keyWordCheck()) {
								if(previewImgCheck()) {
									if(introductionCheck()) {
										if(contantCheck()) {
											if(!isnotEmpty($("#oldPrice").val())||priceCheck()){
												return true;
											}	
										}
									}
								}
							}
						}
					}
				}
			}
			return;
		}
		//编辑时输入信息的验证
		function validate_update() {
			if(keyWordCheck()) {
				if(previewImgCheck()) {
					if(introductionCheck()) {
						if(contantCheck()) {
							if(!isnotEmpty($("#oldPrice").val())||priceCheck()){
								return true;
							}	
						}
					}
				}
			}
			return;
		}
		
		
		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
		
		
		
		/*查看评价*/
		function checkEvaluate(){
			toBeStar();
			layer.open({
				type: 1,
				title: "商品评价",
				closeBtn: 1,
				area: ['100%', '100%'],
				content: $("#evaluateDiv"),
				btn: ['关闭']
			});
		}
		/*变成星星*/
		function toBeStar(){
			$(".evaluate-content-star").each(function(index,dom){
				var num=$(dom).find(".star").attr("attr-num");
				$(dom).find(".star").css("width",((num/5)*100)+"%");
			})
			
		}
		/*图片放大*/
		function evaluatePic(){
			layer.photos({
			  photos: ".evaluate-content-photo"
			  ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
			});
		}
	</script>

</html>