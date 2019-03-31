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
		<title>二级分类</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>
		<script src="${pageContext.request.contextPath}/junlin/js/photoload.js"></script>

		<style>
			#editDiv {
				padding-top: 20px;
			}
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">二级分类</h4>
					<div class="search-div clearfix">
						<div class="clearfix">
							<span class="l_f"> 编号： <input type="text" value=""
							id="identifier" onkeyup="cky(this)"/>
						</span> <span class="l_f"> 名称： <input type="text" value=""
							id="search_name" onkeyup="cky(this)"/>
						</span> <span class="l_f"> 创建人姓名： <input type="text" value=""
							id="operatorIdentifier" onkeyup="cky(this)"/>
						</span>
						<span class="l_f"> 
							创建时间： <input type="text"  value="" id="search_operatorTime" placeholder="请选择时间" readonly=""  />
						</span>
							<span class="r_f"> 
							<input type="button"  class="btncss btn_search" id="btn_search" value="搜索" />
						</span>
						</div>

					</div>
					<div class="opration-div clearfix">
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="classifitionEduce()" style="margin-right: 0;"><i class="fa fa-download"></i> 导出</button>
							
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="classifitionDel()"><i class="fa fa-trash"></i> 删除</button>
						</span>
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="classifitionAdd()"><i class="fa fa-plus"></i> 新增</button>
						</span>
						<span class="jl_f_l">
							<input type="checkbox" id="checkAll" style="margin:0 5px 0 0;" onclick="checkboxController(this)"/>
						</span>
						<span class="jl_f_l">
							<label for="checkAll">全选</label>
						</span>

					</div>
					<div class="table_menu_list">
					<form id="datatable_form">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>选择</th>
									<th>编号</th>
									<th>名称</th>
									<th>一级分类</th>
									<th>关键词</th>
									<th>图片</th>
									<th>创建人</th>
									<th>创建时间</th>
									<th>操作</th>
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
				<div class="form-group" style="display:none">
					<div class="col-xs-4">
						<input type="text" class="form-control" id="primaryKey" name="primaryKey" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="parentId" class="col-xs-2 control-label">选择一级分类</label>
					<div class="col-xs-4">
						<select id="parentId" name="parentId" class="form-control">
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="name" class="col-xs-2 control-label">名称</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="name" name="name" maxlength="60" onkeyup="cky(this)"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="key_word" class="col-xs-2 control-label">关键词</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="key_word"  name="key_word" maxlength="40" onkeyup="cky(this)"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="key_word" class="col-xs-2 control-label">图片上传</label>
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
				
				<div class="form-group">
				<div class="col-xs-4"></div>
				<div class="col-xs-8 tip">注：<br/>1.上传的图片大小应小于2M,尺寸建议为280*280（单位：像素）<br/>
				2.该页面所有字段均为必填</div>
				</div>
			</form>

		</div>

	</body>

	<script>
		
		var oTable1;
		$("#btn_search").click(function() {
			$("#checkAll").removeAttr("checked");
			oTable1.fnDraw();
		});
		jQuery(function($) {
			//datatable赋值
			oTable1 = $('#datatable')
				.dataTable({
					"aaSorting": [
						[0, "asc"]
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
								"oneOrTwo": "two",
								"identifier": $("#identifier").val(),
								"name": $("#search_name").val(),
								"operatorName": $("#operatorIdentifier").val(),
								"operatorTime": $("#search_operatorTime").val()
							});
						},
						"url": "<%=basePath%>backgroundConfiguration/classification/classification/getManagerMsg"
					},

					"aoColumns": [{
							"mData": "sort",
							"bSortable": true,
							"sWidth": "5%",
							"sClass": "center",
							"mRender": function(data, type, row) {
								return '<td><input type="checkbox" name="id" value="' + row.id + '" onclick="checkboxClick(\'#checkAll\')"/></td>';
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
						},{
							"mData": "parentName",
							"bSortable": false,
							"sWidth": "15%",
							"sClass": "center"
						}, {
							"mData": "keyWord",
							"bSortable": false,
							"sWidth": "15%",
							"sClass": "center",

						}, {
							"mData": "picUrl",
							"bSortable": false,
							"sWidth": "15%",
							"sClass": "center",
							"mRender": function(data) {
								if(data!=null&&data!=""){
									return '<span class="look-span" onclick=\'lookPic(' + JSON.stringify(data) + ')\'>预览</span>';
								}
								else{
									return '<span>暂无图片</span>';
								}
								
							}
						}, {
							"mData": "operatorIdentifier",
							"bSortable": false,
							"sWidth": "15%",
							"sClass": "center",
							"mRender": function(data,type,row) {
								if(data!=null&&data!=""){
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
						}, {
							"mData": "operatorTime",
							"bSortable": false,
							"sWidth": "15%",
							"sClass": "center",
							"mRender": function(data) {
								return getSmpFormatDateByLong(data, true);
							}
						},

						{
							"mData": "id",
							"bSortable": false,
							"sWidth": "20%",
							"sClass": "center",
							"mRender": function(data, type, row) {

								return '<td><input type="button" class="btncss edit"' +
									'onclick=\'classifitionEdit(' + JSON.stringify(row) + ')\' value="编辑" />' +
									' <input type="button" class="btncss" value="上移" onclick="removeUpOrDown(' + data + ',\'up\')"/>' +
									' <input type="button" class="btncss" value="下移" onclick="removeUpOrDown(' + data + ',\'down\')"/></td>'
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
		
		/*修改*/
		function classifitionEdit(classificationMsg) {
			//获取数据显示在界面中
			$.ajax({
				url: '<%=basePath%>backgroundConfiguration/classification/classification/selectById',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: {
					"id": classificationMsg.id
				},

				success: function(data) {
					$("#parentId").val(data.parentId);
					$("#name").val(data.name);
					$("#key_word").val(data.keyWord);
					if(data.picUrl!=null&&data.picUrl!=""){
						$("#imghead").attr("src",'<%=basePath%>'+data.picUrl);
					}
					else{
						$("#imghead").attr("src",'<%=basePath%>/junlin/img/photo_icon.jpg');
					}
					
					$("#primaryKey").val(data.id);
				}
			});
			
			layer.open({
				type: 1,
				title: "编辑二级分类",
				closeBtn: 1,
				area: ['800px', ''],
				content: $("#editDiv"),
				btn: ['提交', '取消'],
				yes: function(index) {
					if($("#parentId").val() == "-1"){
						laywarn("一级分类不能为空!");
						return false;
					}
					else if($("#name").val() == "") {
						laywarn("分类名称不能为空!");
						return false;
					} 
					else if($("#key_word").val() == "") {
						laywarn("关键词不能为空!");
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
					}
					
					//编辑时先判断该分类名称是否存在
					$.ajax({
						url: '<%=basePath%>backgroundConfiguration/classification/classification/selectByNameAndIdAndParentId',
						type: "POST",
						dataType: "json",
						async: false,
						cache: false,
						data: {
							"name": $("#name").val(),
							"id": classificationMsg.id,
							"parentId": $("#parentId").val()
						},
						success: function(data) {
							if(data.success) {
								var formData = new FormData($("#inserOrUpdateDiv")[0]);
								$.ajax({
									url: '<%=basePath%>backgroundConfiguration/classification/classification/updateTwoPrimaryKey',
									type: "POST",
									dataType: "json",
									async: false,
									cache: false,
									data :formData,
									contentType : false,
			    					processData : false,//processData的默认值是true。用于对data参数进行序列化处理
									success: function(data) {
										if(data.success) {
											laysuccess(data.msg);
										} else {
											layfail(data.msg);
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
				btn2: function(index, layero) {
					layer.close(index);
				},
				end: function() {
					clearForm("editDiv", "",'<%=basePath%>');
					
				}
			});
		}
		/*新增*/
		function classifitionAdd() {
			layer.open({
				type: 1,
				title: "新增二级分类",
				closeBtn: 1,
				area: ['800px', ''],
				content: $("#editDiv"),
				btn: ['提交', '取消'],
				yes: function(index) {
					
					if($("#parentId").val() == "-1"){
						laywarn("一级分类不能为空!");
						return false;
					}
					else if($("#name").val() == "") {
						laywarn("分类名称不能为空!");
						return false;
					} 
					else if($("#key_word").val() == "") {
						laywarn("关键词不能为空!");
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
					}
					else{
						laywarn("分类图片不能为空!");
						return false;
					}
					
					//添加时先判断该分类名称是否存在
					$.ajax({
						url: '<%=basePath%>backgroundConfiguration/classification/classification/selectByNameAndParentId',
						type: "POST",
						dataType: "json",
						async: false,
						cache: false,
						data: {
							"name": $("#name").val(),
							"parentId": $("#parentId").val()
						},
						success: function(data) {
							if(data.success) {
								var formData = new FormData($("#inserOrUpdateDiv")[0]);
								$.ajax({
									url: '<%=basePath%>backgroundConfiguration/classification/classification/addTwoClassificationMsg',
									type: "POST",
									dataType: "json",
									async: false,
									cache: false,
									data :formData,
									contentType : false,
			    					processData : false,//processData的默认值是true。用于对data参数进行序列化处理
									success: function(data) {
										if(data.success) {
											laysuccess(data.msg);
										} else {
											layfail(data.msg);
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
				btn2: function(index, layero) {
					layer.close(index);
				},
				end: function() {
					clearForm("editDiv", "",'<%=basePath%>');
					
				}
			});
		}
		/*删除*/
		function classifitionDel() {
			var str = "";
			$("input:checkbox[name='id']:checked").each(function() {
				str += $(this).val() + ",";
			})
			if(str == "") {
				laywarn("请选择数据!");
				return;
			}
			//删除时需确认该分类下有无包含的商品
			$.ajax({
				url: '<%=basePath%>backgroundConfiguration/classification/classification/selectByClassificationId',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				data: $("#datatable_form").serialize(),

				success: function(data) {
					if(data.success) {
						//根据主键批量查询分类是否与广告或活动有关联
						$.ajax({
							url: '<%=basePath%>backgroundConfiguration/classification/classification/getClassificationIsContactToADOrACTByIds',
							type: "POST",
							dataType: "json",
							async: false,
							cache: false,
							data: $("#datatable_form").serialize(),

							success: function(data) {
								if(data.success) {
									publicMessageLayer("删除选中数据", function() {
										$.ajax({
											url: '<%=basePath%>backgroundConfiguration/classification/classification/delBatchByPrimaryKey',
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

								} else {
									layer.msg(data.msg, {
										icon: 0,
										time: 2500
									});

								}

							}
						});

					} else {
						layer.msg(data.msg, {
							icon: 0,
							time: 2000
						});

					}

				}
			});
		}
		
		/* 上移或下移操作 */
		function removeUpOrDown(id, upOrDown) {
			var str = "";
			if("up" == upOrDown) {
				str = "进行上移操作";
			} else {
				str = "进行下移操作";
			}

			publicMessageLayer(str, function() {
				$.ajax({
					url: '<%=basePath%>backgroundConfiguration/classification/classification/moveUpOrDown',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data: {
						"id": id,
						"oneOrTwo": "two",
						"upOrDown": upOrDown
					},
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
		}
		
		//给添加时需要选择的一级分类下拉框赋值
		function getDataToOneClassification(){
			$.ajax({
				url: '<%=basePath%>backgroundConfiguration/classification/classification/getAllOneClassification',
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				success: function(data) {
					if(data.length==0){
						$("#parentId").append("<option value='-1' selected>--暂无数据，请到一级分类页面进行添加。--</option>");					
					}
					else{
						$("#parentId").append("<option value='-1' selected>--请选择--</option>");
						for(var i=0;i<data.length;i++){							
							var option = $("<option value='"+data[i].id+"'>"
									+ data[i].name + "</option>");
							$("#parentId").append(option);
						}
					}
				}
			});
		}

		/*导出*/
		function classifitionEduce() {
			var export_identifier=encodeURI(encodeURI($("#identifier").val()));
			var export_oneOrTwo=encodeURI(encodeURI("two"));
			var export_searchName=encodeURI(encodeURI($("#search_name").val()));
			var export_operatorName=encodeURI(encodeURI($("#operatorIdentifier").val()));
			var export_operatorTime=encodeURI(encodeURI($("#search_operatorTime").val()));
			
			publicMessageLayer("导出下列显示的数据", function() {
					var URL="<%=basePath%>backgroundConfiguration/classification/classification/exportClassification?oneOrTwo="+export_oneOrTwo+"&identifier="+export_identifier+"&name="+export_searchName+"&operatorName="+export_operatorName+"&operatorTime="+export_operatorTime;
				    location.href=URL;
					return false;			
			})
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
		
		
		laydate.render({
			elem: "#search_operatorTime",
			type: 'date',
			format: 'yyyy-MM-dd'
		});
	</script>

</html>