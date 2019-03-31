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
		<title>首页广告</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		
		
		<script src="${pageContext.request.contextPath}/junlin/js/photoload.js" type="text/javascript"></script>


		<style>
			#editDiv {
				padding-top: 20px;
			}
			/* .lookPic-div{
				margin-top:20px;
				margin-bottom:10px;
				text-align:center;
			}
			.lookPic-div img{
				max-width: 600px;
			} */
			#goodsDiv>div{
			padding:0;
			}
			td>.btncss.jl-btn-uneffect {
				background: #f08132;
				border-radius: 4px;
				color: white;
				border: 1px solid #fff;
				letter-spacing: 1px;
				height: 25px;
				line-height: 25px;
			}
			td>.btncss.jl-btn-uneffect:hover{
				background: #f4b282;
			}
			
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">首页广告</h4>
					<div class="search-div clearfix">
					<div class="clearfix">
							<span class="l_f"> 
							编号： <input type="text" value="" id="query_identifier" onblur="cky(this)" />
						</span>
							<span class="l_f"> 
							名称： <input type="text" value="" id="query_name" onblur="cky(this)"/>
						</span>
						<span class="l_f"> 
							创建人姓名： <input type="text"  value="" id="query_operatorIdentifier" onblur="cky(this)"/>
						</span>
						<span class="l_f"> 
							创建时间： <input type="text"  value="" id="search_operatorTime" placeholder="请选择时间" readonly=""  />
						</span>
						<span class="r_f"> 
							<input type="button" id="btn_search"  class="btncss btn_search" value="搜索"  />
						</span>

						</div>

					</div>
					<div class="opration-div clearfix">
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="homeEduce()" style="margin-right: 0;"><i class="fa fa-download"></i> 导出</button>
							
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="homeDel()"><i class="fa fa-trash"></i> 删除</button>
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-importent" onclick="homeAdd()"><i class="fa fa-plus"></i> 新增</button>
						</span>
						<span class="jl_f_l">
							<input type="checkbox"  id="checkAll" style="margin:0 5px 0 0;" onclick="checkboxController(this)" />
						</span>
						<span class="jl_f_l">
							<label for="checkAll">全选</label>
						</span>
					</div>
					<div class="table_menu_list">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>选择</th>
									<th>编号</th>
									<th>名称</th>
									<th>图片预览</th>
									<th>链接类型</th>
									<th>链接参数信息</th>
									<th>生效时间</th>
									<th>创建人</th>
									<th>创建时间</th>
									<th>操作</th>
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
			<form class="container" id="inserOrUpdateDiv">
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label class="col-xs-2 control-label">分类：</label>
					<input type="text" class="hidden" id="type" name="type" value="1"/>
					<div class="col-xs-4">
						首页广告
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="" class="col-xs-2 control-label">名称：</label>
					<div class="col-xs-6">
						<input type="text" class="form-control" id="name"  name="name" maxlength="100" placeholder="请输入首页广告图名称" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="" class="col-xs-2 control-label">跳转类型：</label>
					<div class="col-xs-6">
						<select class="form-control" id="urlType" name="urlType" onchange="chooseGoods(this)">
							<option value="-1">--跳转类型--</option>
							<option value="0">商品</option>
							<option value="1">活动</option>
						</select>
					</div>
				</div>
						<div class="form-group hidden" id="chooseGoods">
					<div class="col-xs-2"></div>
					<label for="" class="col-xs-2 control-label">商品名称：</label>
					<div  class="col-xs-6" id="goodsDiv">
					<div class="col-xs-4" >
						<select class="form-control" id="classificationOne" onchange="loadClassificationTwo(this)" name="">
							<option value="-1">--一级分类--</option>
							
						</select>
					</div>
					<div class="col-xs-4">
						<select class="form-control" id="classificationTwo" onchange="loadGoodsName(this)" name="classificationTwo" style="border-left:0;border-right:0;">
							<option value="-1">--二级分类--</option>
							
						</select>
					</div>
					<div class="col-xs-4">
						<select class="form-control" id="goodsName" name="goodsId">
							<option value="-1">--商品名称--</option>
							
						</select>
					</div>
					</div>
					<div id="activityDiv">
					<div class="col-xs-6">
						<select class="form-control" id="activityName" name="activityId">
							<option value="-1">--活动名称（编号）--</option>
						
						</select>
					</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="" class="col-xs-2 control-label">图片：</label>
					<div class="col-xs-4">
						<div class="big-photo">
							<div id="preview">
								<img id="imghead" border="0" src="${pageContext.request.contextPath}/junlin/img/photo_icon.jpg" width="90" height="90" onclick="$('#previewImg').click();">
							</div>
							<input type="file" onchange="previewImage(this)" style="display: none;" id="previewImg" name="previewImg" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg">
							<!--<input id="uploaderInput" class="uploader__input" style="display: none;" type="file" accept="" multiple="">-->
						</div>
					</div>
				</div>
				
				<div class="form-group">
				<div class="col-xs-4"></div>
				<div class="col-xs-8 tip">注：<br/>1.上传的图片大小应小于2M,图片尺寸建议为1500*547（单位：像素）<br/>
				2.该页面所有字段均为必填</div>
				</div>
			</form>
		</div>
	
	</div>
	</body>

	<script>
	var type = 1;//首页广告
	
	var contextPath = "${pageContext.request.contextPath}";
	var oTable1;
	
	//查询
	$("#btn_search").click(function() {
		
		oTable1.fnDraw();
		$("#checkAll").attr("checked",false);
		
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
							"identifier": $("#query_identifier").val(),
							"name": $("#query_name").val(),
							"operatorIdentifier": $("#query_operatorIdentifier").val(),
							"type" : type,
							"operatorTime": $("#search_operatorTime").val()
							
						});
					},
					"url": "<%=basePath%>backgroundConfiguration/advertisement/advertisementInformation/dataTables"
				},

				"aoColumns": [{
						"mData": "id",
						"bSortable": true,
						"sWidth": "5%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							return '<td><input type="checkbox" onclick="checkboxClick(\'#checkAll\')" name="id" value="' + row.id + '"/></td>';
						}
					}, {
						"mData": "identifier",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center"
					}, {
						"mData": "name",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center"
						
					}, {
						"mData": "picUrl",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center",
						"mRender": function(data, type, row) {

							if(data!=null&&data!=""){
								return '<span class="look-span" onclick=\'lookPic(' + JSON.stringify(data) + ')\'>预览</span>';
							}
							else{
								return '<span>暂无图片</span>';
							}
						}
						
					}, {
						"mData": "urlType",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							
							switch (data) {
							case 0:
								return '商品';
								break;
							case 1:
								return '活动';
								break;
							
							default:
								return '';
								break;
							}
						}
					},{
						"mData": "urlParameterId",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							switch (row.urlType) {
							case 0:
								return row.goodsDetails.name+"("+row.goodsDetails.classification.parentName+"/"+row.goodsDetails.classification.name+")";
								break;
							case 1:
								return	row.activityInformation.name+"("+row.activityInformation.identifier+")";
								break;
							
							default:
								return '';
								break;
							}
							
						}
					}, {
						"mData": "effectTime",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"bVisible": false,
						"mRender": function(data, type, row) {
							if(data == null){
								return "";
							}
							return	getSmpFormatDateByLong(data, false);
						}

					},{
						"mData": "operatorIdentifier",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center",
						"mRender": function(data,type,row) {
							if(data!=null){
								if(row.user!=null){
									return data+"("+row.user.name+")";
								}
								else{
									return data;
								}
								
							}
							else{
								return "";
							}
							
						}

					},{
						"mData": "operatorTime",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							return	getSmpFormatDateByLong(data, true);
						}
					},{
						"mData": "id",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							if(row.effect == 0){
								return '<td><input type="button" class="btncss edit jl-btn-uneffect " onclick=\'homeEffect(this,'+ JSON.stringify(row) +')\' value="失效" /></td>';
							}
							return '<td><input type="button" class="btncss edit" onclick=\'homeEffect(this,'+ JSON.stringify(row) +')\' value="生效" /></td>';
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
	
	var goodsData=null;
	var activityData = null;
	var oneIndex=-1;
	var twoIndex = -1;
	//下拉列表赋值   一级分类
	$.ajax({
		url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/getClassificationByParentIdForNotPresell' ,
		type : "POST",
		dataType : "json",
		success : function(data) {
			goodsData = data;
		}
	}); 
	
	
	//下拉列表赋值   活动名称
	$.ajax({
		url :'<%=basePath%>backgroundConfiguration/activity/activityInformation/getAllPassActivityInformation' ,
		type : "POST",
		dataType : "json",
		success : function(data) {
			activityData = data;
			
		}
	});
	
	
	
		function chooseGoods(thisinput){
			if(($(thisinput).val()-0)==0){//商品
				$("#chooseGoods").removeClass("hidden");
				$("#chooseGoods").find("label").html("商品名称：");
				$("#goodsDiv").removeClass("hidden");
				$("#activityDiv").addClass("hidden");
				
				$("#classificationOne").empty();
				$("#classificationTwo").empty();
				$("#goodsName").empty();
				$("#classificationOne").append('<option value="-1" selected="selected">--一级分类--</option>');
				$("#classificationTwo").append('<option value="-1" selected="selected">--二级分类--</option>');
				$("#goodsName").append('<option value="-1" selected="selected">--商品名称--</option>');
				for ( var i = 0; i < goodsData.length; i++) {
					var option = $("<option data-index="+i+" value='"+goodsData[i].id+"'>"+ goodsData[i].name+"</option>");
					$("#classificationOne").append(option);
				}
				
				
			}else if(($(thisinput).val()-0)==1){//活动
				$("#chooseGoods").removeClass("hidden");
				$("#chooseGoods").find("label").html("活动名称：");
				$("#goodsDiv").addClass("hidden");
				$("#activityDiv").removeClass("hidden");
				
				$("#activityName").empty();
				$("#activityName").append('<option value="-1" selected="selected">--活动名称(编号)--</option>');
				for ( var i = 0; i < activityData.length; i++) {
					var option = $("<option value='"+activityData[i].id+"'>"+ activityData[i].name+"("+activityData[i].identifier +")</option>");
					$("#activityName").append(option);
				}
			}else{
				$("#chooseGoods").addClass("hidden");
			}
		}
	
		/* 选择一级分类 给二级分类的下拉框赋值  */
		function loadClassificationTwo(that) {
			$("#classificationTwo").empty();
			$("#goodsName").empty();
			$("#goodsName").append("<option value='-1' selected>--商品名称--</option>");
			if($(that).val() == "-1") {
				$("#classificationTwo").append("<option value='-1' selected>--二级分类--</option>");
				return;
			}
			<%-- $.ajax({
				url: '<%=basePath%>backgroundConfiguration/classification/classification/selectTwoByOneId?id=' + $(that).val(),
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				success: function(data) {
					$("#classificationTwo").append("<option value='-1' selected>--二级分类--</option>");
					for(var i = 0; i < data.length; i++) {
						var option = $("<option value='" + data[i].id + "'>" +data[i].name + "</option>");
						$("#classificationTwo").append(option);
					}
					
				}
			}); --%>
			if($(that).val()!="-1"){
				oneIndex = $(that).find("option:selected").attr("data-index");
				var data = goodsData[oneIndex]["twoClassifications"];
				$("#classificationTwo").append("<option value='-1' selected>--二级分类--</option>");
				for ( var i = 0; i < data.length; i++) {
					var option = $("<option data-index="+i+" value='" + data[i].id + "'>" +data[i].name + "</option>");
					$("#classificationTwo").append(option);
				} 
			}
		}
		/* 选择二级分类 给商品名称的下拉框赋值  */
		function loadGoodsName(that) {
			$("#goodsName").empty();
			if($(that).val() == "-1") {
				$("#goodsName").append("<option value='-1' selected>--商品名称--</option>");
				return;
			}
			<%-- $.ajax({
				url: '<%=basePath%>backgroundConfiguration/goods/goodsManager/selectAllGoodsDetails?classificationId=' + $(that).val(),
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				success: function(data) {
					$("#goodsName").append("<option value='-1' selected>--商品名称--</option>");
					for(var i = 0; i < data.length; i++) {
						var option = $("<option value='" + data[i].id + "'>" +data[i].name + "</option>");
						$("#goodsName").append(option);
					}
					
				}
			}); --%>
			if($(that).val()!="-1"){
				twoIndex=$(that).find("option:selected").attr("data-index");
				var data = goodsData[oneIndex]["twoClassifications"][twoIndex]["goodsDetails"];
				$("#goodsName").append("<option value='-1' selected>--商品名称--</option>");
				for ( var i = 0; i < data.length; i++) {
					var option = $("<option value='" + data[i].id + "'>" +data[i].name + "</option>");
					$("#goodsName").append(option);
				}
			}
		}
	
		/*预览*/
		function lookPic(src) {
			layer.open({
				type: 1,
				title: "图片",
				closeBtn: 1,
				area: ['800px', ''],
				offset: 't',
				content: "<div class='lookPic-div'><img src='<%=basePath%>" + src + "' /></div>",
				btn: ['关闭']
			});
			
		}
	
		
		/*新增*/
		function homeAdd() {
			layer.open({
				type: 1,
				title: "新增首页广告",
				closeBtn: 1,
				area: ['1000px', '450px'],
				content: $("#editDiv"),
				btn: ['提交', '取消'],
				yes: function(index) {
				
					if($("#name").val() == "") {
						laywarn("名称不能为空!");
						return false;
					}else if($("#urlType").val() == "-1") {
						laywarn("跳转类型不能为空!");
						return false;
					}else if($("#urlType").val() == "0" && $("#goodsName").val() =="-1" ) {
						laywarn("请完善跳转商品信息!");
						return false;
					}else if($("#urlType").val() == "1" && $("#activityName").val() =="-1" ) {
						laywarn("请选择跳转活动信息!");
						return false;
					}
					var file=$("#previewImg").val();
					if(file!=""){	
						/* 分割之后根据后缀判断图片类型 */
						var splitPicName=file.split(".");
						if(splitPicName[splitPicName.length-1].toUpperCase()!='JPG'&&splitPicName[splitPicName.length-1].toUpperCase()!='JPEG'&&splitPicName[splitPicName.length-1].toUpperCase()!='GIF'&&splitPicName[splitPicName.length-1].toUpperCase()!='PNG'&&splitPicName[splitPicName.length-1].toUpperCase()!='BMP'){
							layer.alert("上传文件类型不对,只能是图片!", {
								title : '提示框',
								icon : 0,
							});
							return ;
						}
					}else{
						laywarn("广告图片不能为空!");
						return false;
					}
					var formData = new FormData($("#inserOrUpdateDiv")[0]);
					$.ajax({
						url :'<%=basePath%>backgroundConfiguration/advertisement/advertisementInformation/addAdvertisementInformation',
						type : "POST",
						dataType : "json",
						async: false,
						cache: false,
						data :formData,
						contentType : false,
						processData : false,//processData的默认值是true。用于对data参数进行序列化处理
						success : function(data) {

							if(data.success) {
								laysuccess(data.msg);
							} else {
								layfail(data.msg);
							}
								
							oTable1.fnDraw();
							$("#checkAll").attr("checked",false);
							layer.close(index);
							

						}
					});	
						
				},
				end: function(index, layero) {
					clearForm("editDiv","",'<%=basePath%>');
					$("#type").val("1");
					$("#chooseGoods").addClass("hidden");
					
				}
			});
		}
		/*删除*/
		function homeDel() {
			var ids=[];
			var boxes = $("input[name='id']");
			for(var i=0;i<boxes.length;i++){
			  if(boxes[i].checked == true){
			       ids.push(boxes[i].value);
			  }
			}
			
			if(ids.length<=0){
				laywarn("请选择数据!");
				return;
			}
			publicMessageLayer("删除选中数据", function() {

				$.ajax({
					url :'<%=basePath%>backgroundConfiguration/advertisement/advertisementInformation/deleteAdvertisementInformationByIds',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data: {
						"ids" : ids
					},
					
					traditional:true,
					success: function(data) {
						if(data.success) {
							laysuccess(data.msg);
							oTable1.fnDraw();
							$("#checkAll").attr("checked",false);
						} else {
							if(data.msg != "信息删除失败!"){
								layer.open({
									type: 1,
									title: "警告",
									closeBtn: 1,
									area: ['400px', ''],
									content:"<div style='width:80%;margin:0 auto;padding:20px 0;text-align:center;word-wrap: break-word;word-break: normal;'>"+ data.msg+"</div>" ,
									btn: ['确认'],
									yes: function(index) {
										layer.close(index);
									}
								});
							}else{
								layfail(data.msg);
							}
							
						}
						
					}
				});
			})
		}
		
		/*生效*/
		function homeEffect(thisbtn,data) {
			var effect = 1;//默认为失效
			var layerMsg = "失效此广告";
			if($(thisbtn).val() == "生效"){
				effect = 0;
				layerMsg = "生效此广告";
			}
			
			publicMessageLayer(layerMsg, function() {
				
				$.ajax({
					url :'<%=basePath%>backgroundConfiguration/advertisement/advertisementInformation/updateHomeAdToEffectOrUneffect',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data: {
						"id" : data.id,
						"identifier" : data.identifier,
						"type" : type,
						"effect" : effect
					},
					
					traditional:true,
					success: function(data) {
						if(data.success) {
							laysuccess(data.msg);
							oTable1.fnDraw();
							$("#checkAll").attr("checked",false);
						} else {
							layfail(data.msg);
						}
						
					}
				});
			})
		}
		
		function homeEduce(){
			publicMessageLayer("导出下列显示的数据", function() {
				var name = encodeURI(encodeURI($("#query_name").val())); 
				var identifier = encodeURI(encodeURI($("#query_identifier").val()));
				var operatorIdentifier = encodeURI(encodeURI($("#query_operatorIdentifier").val()));
				var export_operatorTime=encodeURI(encodeURI($("#search_operatorTime").val()));
				 
				var URL="<%=basePath%>backgroundConfiguration/advertisement/advertisementInformation/exportAdvertisementInformation?type="+type+"&identifier="+identifier+"&name="+name+"&operatorIdentifier="+operatorIdentifier+"&operatorTime="+export_operatorTime;	
				    location.href=URL;
					return false;			
			})
		}
		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
	
	</script>

</html>