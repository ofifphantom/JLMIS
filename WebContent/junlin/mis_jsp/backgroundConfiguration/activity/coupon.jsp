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
		<title>优惠券</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		
		<style>
			#editDiv {
				padding-top: 50px;
			}
			#lookDiv{
				padding-top: 20px;
			}
			 .text-red{
			 color:#9e2020;
			 }
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">优惠券</h4>
					<div class="search-div clearfix">
						<div class="clearfix">
							<span class="l_f"> 
							编号： <input type="text" value="" id="query_identifier" onblur="cky(this)"/>
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
							<input type="button" id="btn_search"  class="btncss btn_search" value="搜索" />
						</span>
						</div>

					</div>
					<div class="opration-div clearfix">
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="couponEduce()" style="margin-right: 0;"><i class="fa fa-download"></i> 导出</button>
							
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="couponDel()"><i class="fa fa-trash"></i> 删除</button>
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="couponInvalid()"><i class="fa fa-times"></i> 立即失效</button>
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="couponReturn()"><i class="fa fa-mail-forward"></i> 撤回</button>
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-importent" onclick="couponAdd()"><i class="fa fa-plus"></i> 新增</button>
						</span>
						<span class="jl_f_l">
							<input type="checkbox" onclick="checkboxController(this)"  id="checkAll" style="margin:0 5px 0 0;"/>
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
									<th>金额(元)</th>
									<th>数量/已领取数量(张)</th>
									<th>门槛(元)</th>
									<th>有效期</th>
									<th>领取时间</th>
									<th>规则</th>
									<th>状态</th>
									<th width="21%">操作</th>
								</tr>

							</thead>
							<tbody>
								<!-- <tr>
									<td>
										<input type="checkbox" name="id" checked="checked" />
									</td>
									<td>
										00100101
									</td>
									<td>粮食</td>
									<td>100</td>
									<td>100</td>
									<td>200</td>
									<td>2017.12.02 - 2018.01.20</td>
									<td>2017.10.02</td>
									<td>一人一张</td>
									<td>失效</td>
									<td>
										<input type="button" class="btncss edit" onclick="couponLook()" value="详情" />
										<input type="button" class="btncss edit" onclick="couponEdit()" value="编辑" />
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="id" checked="checked" />
									</td>
									<td>
										00100101
									</td>
									<td>粮食</td>
									<td>100</td>
									<td>100</td>
									<td>200</td>
									<td>2017.12.02 - 2018.01.20</td>
									<td>2017.10.02</td>
									<td>一人一张</td>
									<td>通过</td>
									<td>
										<input type="button" class="btncss edit" onclick="couponLook()" value="详情" />
										<input type="button" class="btncss edit" onclick="couponEdit()" value="编辑" />
									</td>
								</tr> -->

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		<!--详情-->
		<div id="lookDiv" style="display: none;">
			<form class="container">
			<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">编号：</label>
					<div class="col-xs-4" id="view_identifier">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">名称：</label>
					<div class="col-xs-4" id="view_name">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">金额：</label>
					<div class="col-xs-4" id="view_price">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">数量/已领取数量：</label>
					<div class="col-xs-4" id="view_total">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">门槛：</label>
					<div class="col-xs-4" id="view_useLimit">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">有效期：</label>
					<div class="col-xs-4" id="view_validatetime">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">领取时间：</label>
					<div class="col-xs-4" id="view_gettime">
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-3"></div>
					<label class="col-xs-2 control-label">领取规则：</label>
					<div class="col-xs-4" id="view_rules">
						
					</div>
				</div>
			</form>

		</div>

		<!--新增 编辑 -->
		<div id="editDiv" style="display: none;">
			<form class="container">

				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="name" class="col-xs-2 control-label">名称：</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="name" maxlength="100"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="money" class="col-xs-2 control-label">金额(元)：</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="price" onkeyup="pressMoney(this)" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="count" class="col-xs-2 control-label">数量(张)：</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="total" onkeyup="pressInteger(this)" maxlength="9"/>
						
					</div>
					<div id="tips" class="col-xs-4 text-red">已领取<span id="getedCount"></span>张</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="rule" class="col-xs-2 control-label">门槛(元)：</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="useLimit" onkeyup="pressMoney(this)" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="begintime" class="col-xs-2 control-label">有效期：</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="validatetime" placeholder="请选择有效期时间" readonly="" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="gettime" class="col-xs-2 control-label">领取时间：</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="gettime" placeholder="请选择有效期时间" readonly="" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="getrule" class="col-xs-2 control-label">领取规则：</label>
					<div class="col-xs-4">
						<select name="" id="rules" class="form-control">
							<option value="-1">--请选择--</option>
							<option value="0">注册返券</option>
							<option value="1">领劵</option>
							<option value="2">购物返券</option>
						</select>
					</div>
				</div>
				<div class="form-group">
				<div class="col-xs-4"></div>
				<div class="col-xs-8 tip">注：该页面所有字段均为必填</div>
				</div>
			</form>

		</div>
	
	</body>

	<script>
	
	
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
							"flag":1,
							"identifier": $("#query_identifier").val(),
							"name": $("#query_name").val(),
							"operatorIdentifier": $("#query_operatorIdentifier").val(),
							"operatorTime": $("#search_operatorTime").val()
							
						});
					},
					"url": "<%=basePath%>backgroundConfiguration/activity/couponInformation/dataTables"
				},

				"aoColumns": [{
						"mData": "id",
						"bSortable": true,
						"sWidth": "5%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							if(row.state == 0||row.state == 1||row.state == 2){
								return '<td><input type="checkbox" onclick="checkboxClick(\'#checkAll\')" name="id" value="' + row.id + '"/></td>';
							}else{
								return '';
							}
						}
					}, {
						"mData": "identifier",
						"bSortable": false,
						"sWidth": "15%",
						"sClass": "center"
					}, {
						"mData": "name",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center"
						
					}, {
						"mData": "price",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center"
					}, {
						"mData": "total",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							return data+"/"+(row.total-row.count)
						}
						
					},
					{
						"mData": "useLimit",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center"
					}, {
						"mData": "endValidityTime",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							
							var  date = getSmpFormatDateByLong(row.beginValidityTime, false)+"~"+getSmpFormatDateByLong(data, false);
							return	date;
						}

					},{
						"mData": "endTime",
						"bSortable": false,
						"sWidth": "10%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							
							var  date = getSmpFormatDateByLong(row.beginTime, false)+"~"+getSmpFormatDateByLong(data, false);
							return	date;
						}

					},{
						"mData": "rules",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							
							switch (data) {
							case 0:
								return '注册返券';
								break;
							case 1:
								return '领券';
								break;
							case 2:
								return	'购物返券';
								break;
							default:
								break;
							}
						}
					},{
						"mData": "state",
						"bSortable": false,
						"sWidth": "5%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							
							switch (data) {
							case 0:
								return '正常';
								break;
							case 1:
								return '已失效';
								break;
							case 2:
								return '已撤回';
								break;
							case 3:
								return '未送审';
								break;
							case 4:
								return '待审核';
								break;
							case 5:
								return	'已驳回';
								break;
							default:
								break;
							}
						}
					},{
						"mData": "id",
						"bSortable": false,
						"sWidth": "20%",
						"sClass": "center",
						"mRender": function(data, type, row) {
							if(row.state == 3 || row.state == 5){
								return '<td><input type="button" class="btncss edit" onclick=\'couponLook(' + JSON.stringify(row) + ')\' value="详情" />'+
								'<input type="button" class="btncss edit" onclick=\'couponEdit(' + JSON.stringify(row) + ')\' value="编辑" />'
								+'<input type="button" class="btncss edit" onclick=\'couponSend(' + JSON.stringify(row) + ')\' value="送审" /></td>';
							}else{
								return '<td><input type="button" class="btncss edit" onclick=\'couponLook(' + JSON.stringify(row) + ')\' value="详情" /></td>'
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
	
	
	/*时间插件*/
	laydateTwo('#validatetime',function(){
		
	/* console.log(splitStr(time)[0]);
	console.log(splitStr(time)[1]); */
	});
	laydateTwo('#gettime',function(){
		
	/* console.log(splitStr(time)[0]);
	console.log(splitStr(time)[1]); */
	});
	
		

		/*修改*/
		function couponEdit(data) {
			$("#tips").removeClass("hidden");
			$("#name").val(data.name);
			$("#price").val(data.price);
			$("#total").val(data.total);
			$("#getedCount").html(data.total-data.count);
			$("#useLimit").val(data.useLimit);
			var bTime=getSmpFormatDateByLong(data.beginValidityTime, false);
			var eTime=getSmpFormatDateByLong(data.endValidityTime, false);
			$("#validatetime").val(bTime+" ~ "+eTime);
			var bTime1=getSmpFormatDateByLong(data.beginTime, false);
			var eTime1=getSmpFormatDateByLong(data.endTime, false);
			$("#gettime").val(bTime1+" ~ "+eTime1);
			$("#rules").val(data.rules);
			
			layer.open({
				type: 1,
				title: "编辑优惠券",
				closeBtn: 1,
				area: ['1000px'],
				content: $("#editDiv"),
				btn: ['提交', '取消'],
				yes: function(index) {
					
					if($("#name").val() == "") {
						laywarn("优惠券名称不能为空!");
						return false;
					}else if($("#price").val() == "") {
						laywarn("金额不能为空!");
						return false;
					}else if($("#total").val() == "" ) {
						laywarn("数量不能为空!");
						return false;
					}else if($("#total").val() < ($("#getedCount").html()-0) ) {
						laywarn("优惠券数量不能低于已领取数量!");
						return false;
					}else if($("#useLimit").val() == "") {
						laywarn("门槛不能为空!");
						return false;
					}else if(($("#useLimit").val()-0)-($("#price").val()-0)<0) {
						laywarn("门槛金额要大于优惠券金额!");
						return false;
					}else if($("#validatetime").val() == "") {
						laywarn("请选择有效期!");
						return false;
					}else if($("#gettime").val() == ""){
						laywarn("请选择领取时间!");
						return false;
					}else{
						var validatetime_bg=splitStr($("#validatetime").val())[0];
						var validatetime_end=splitStr($("#validatetime").val())[1];
						var gettime_bg=splitStr($("#gettime").val())[0];
						var gettime_end=splitStr($("#gettime").val())[1];
					}
					if(!compareDate(gettime_bg,validatetime_bg)){
						laywarn("开始领取时间要比有效期开始时间早!");
						return false;
					}else if(!compareDate(gettime_end,validatetime_end)){
						laywarn("结束领取时间要比有效期结束时间早!");
						return false;
					}else if($("#rules").val()=="-1" ){
						laywarn("请选择领取规则!");
						return false;
					}
					
					$.ajax({
						url :'<%=basePath%>backgroundConfiguration/activity/couponInformation/updateCouponInformationById',
						type : "POST",
						dataType : "json",
						async: false,
						cache: false,
						data :{
							"id" : data.id,
							"name" : $("#name").val(),
							"price" : $("#price").val(),
							"total" : $("#total").val(),
							"useLimit" : $("#useLimit").val(),
							"beginValidityTime" : validatetime_bg,
							"endValidityTime" : validatetime_end,
							"beginTime" : gettime_bg,
							"endTime" : gettime_end,
							"rules" : $("#rules").val(),
						},
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
				end:function(index){
					clearForm("editDiv","");
  				}
			});
		}
		/*新增*/
		function couponAdd() {
			$("#tips").addClass("hidden");
			layer.open({
				type: 1,
				title: "新增优惠券",
				closeBtn: 1,
				area: ['1000px'],
				content: $("#editDiv"),
				btn: ['提交', '取消'],
				yes: function(index) {
					if($("#name").val() == "") {
						laywarn("优惠券名称不能为空!");
						return false;
					}else if($("#price").val() == "") {
						laywarn("金额不能为空!");
						return false;
					}else if($("#total").val() == "" ) {
						laywarn("数量不能为空!");
						return false;
					}else if($("#useLimit").val() == "") {
						laywarn("门槛不能为空!");
						return false;
					}else if(($("#useLimit").val()-0)-($("#price").val()-0)<0) {
						laywarn("门槛金额要大于优惠券金额!");
						return false;
					}else if($("#validatetime").val() == "") {
						laywarn("请选择有效期!");
						return false;
					}else if($("#gettime").val() == ""){
						laywarn("请选择领取时间!");
						return false;
					}else{
						var validatetime_bg=splitStr($("#validatetime").val())[0];
						var validatetime_end=splitStr($("#validatetime").val())[1];
						var gettime_bg=splitStr($("#gettime").val())[0];
						var gettime_end=splitStr($("#gettime").val())[1];
					}
					if(!compareDate(gettime_bg,validatetime_bg)){
						laywarn("开始领取时间要比有效期开始时间早!");
						return false;
					}else if(!compareDate(gettime_end,validatetime_end)){
						laywarn("结束领取时间要比有效期结束时间早!");
						return false;
					}else if($("#rules").val()=="-1" ){
						laywarn("请选择领取规则!");
						return false;
					}
					
					
					$.ajax({
						url :'<%=basePath%>backgroundConfiguration/activity/couponInformation/addCouponInformation',
						type : "POST",
						dataType : "json",
						async: false,
						cache: false,
						data :{
							"name" : $("#name").val(),
							"price" : $("#price").val(),
							"total" : $("#total").val(),
							"useLimit" : $("#useLimit").val(),
							"beginValidityTime" : validatetime_bg,
							"endValidityTime" : validatetime_end,
							"beginTime" : gettime_bg,
							"endTime" : gettime_end,
							"rules" : $("#rules").val(),
						},
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
				end:function(index){
					clearForm("editDiv","");
  				}
			});
		}
		
		/*详情*/
		function couponLook(data){
			$("#view_identifier").html(data.identifier);
			$("#view_name").html(data.name);
			$("#view_price").html(data.price+" 元");
			$("#view_total").html(data.total+"/"+(data.total-data.count)+" 张");
			$("#view_useLimit").html(data.useLimit+" 元");
			var bTime=getSmpFormatDateByLong(data.beginValidityTime, false);
			var eTime=getSmpFormatDateByLong(data.endValidityTime, false);
			$("#view_validatetime").html(bTime+" ~ "+eTime);
			var bTime1=getSmpFormatDateByLong(data.beginTime, false);
			var eTime1=getSmpFormatDateByLong(data.endTime, false);
			$("#view_gettime").html(bTime1+" ~ "+eTime1);
			switch (data.rules) {
			case 0:
				$("#view_rules").html('注册返券');
				break;
			case 1:
				$("#view_rules").html('领券');
				break;
			case 2:
				$("#view_rules").html('购物返券');
				break;
			default:
				break;
			}
			
			
			layer.open({
				type: 1,
				title: "优惠券详情",
				closeBtn: 1,
				area: ['1000px', '600px'],
				content: $("#lookDiv"),
				btn: ['关闭'],
				yes: function(index) {
					layer.close(index);
				}
			});
		}

		/*删除*/
		function couponDel() {
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
					url :'<%=basePath%>backgroundConfiguration/activity/couponInformation/deleteCouponInformationByIds',
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

		/*导出*/
		function couponEduce() {
			publicMessageLayer("导出下列显示的数据", function() {
				var name = encodeURI(encodeURI($("#query_name").val())); 
				var identifier = encodeURI(encodeURI($("#query_identifier").val()));
				var operatorIdentifier = encodeURI(encodeURI($("#query_operatorIdentifier").val()));
				var export_operatorTime=encodeURI(encodeURI($("#search_operatorTime").val()));
				var URL="<%=basePath%>backgroundConfiguration/activity/couponInformation/exportCouponInformation?identifier="+identifier+"&name="+name+"&operatorIdentifier="+operatorIdentifier+"&operatorTime="+export_operatorTime;	
				    location.href=URL;
					return false;			
			})
		}

		/*立即失效*/
		function couponInvalid() {
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
			publicMessageLayer("将选中数据立即失效", function() {
				$.ajax({
					url :'<%=basePath%>backgroundConfiguration/activity/couponInformation/updateStateToInvalid',
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
							layfail(data.msg);
						}
						
					}
				});
			})
		}

		/*撤回*/
		function couponReturn() {
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
			publicMessageLayer("将选中数据撤回", function() {
				$.ajax({
					url :'<%=basePath%>backgroundConfiguration/activity/couponInformation/updateStateToBackout',
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
							layfail(data.msg);
						}
						
					}
				});
				
			})
		}
		
		/*送审*/
		function couponSend(row) {
			
			publicMessageLayer("送审该优惠券", function() {
				var ids=[];
				var id = row.id;	
				ids.push(id);
				$.ajax({
					url :'<%=basePath%>backgroundConfiguration/activity/couponInformation/updateStateByIds',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data: {
						"ids" : ids,
						"state" : 4
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
		
		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
	</script>

</html>