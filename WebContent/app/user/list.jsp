<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!DOCTYPE>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>用户管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@include file="/common.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/js/zTree/css/zTreeStyle/metro.css" />
<script
	src="${pageContext.request.contextPath}/static/js/zTree/js/jquery.ztree.all-3.5.min.js"></script>
<style type="text/css">

</style>
</head>
<body>
	<div class="page-content clearfix">
	<div id="Member_Ratings">
	<div class="d_Confirm_Order_style">
		<div class="search_style">
			<div class="title_names">查询条件</div>
			<ul class="search_content clearfix">
				
					<li><label for="userName">用户名称:</label> <input type="text"
						name="userName" id="userName"
						placeholder="请输入用户名称"></li>
					<li><label for="mobile">联系方式:</label> <input type="text"
						name="mobile" id="mobile"
						placeholder="请输入联系方式"></li>
					<li><label for="roleId">所属角色:</label> <select
						id="roleId" name="roleId">
							<option value="">请选择角色</option>
					</select></li>
					<li><label for="departmentId">所属部门:</label> <select
						id="departmentId" name="departmentId">
							<option value="">请选择部门</option>
					</select></li>
					<li>
					<button type="button" class="btncss" id="btn_search" >
					     <i class="icon-search"></i>查询
					</button>
					</li>
				</ul>
		</div>
        
      
        <div class="border clearfix">
					<span class="l_f">
					<input type="button" id="btn_add" class="btncss" value="用户新增" />
					</span>
		</div>
			
					<div class="table_menu_list" id="tableItem">
						<table class="tablecss row col-xs-12" style="margin-bottom: 10px;font-size: 13px;"
							id="datatable">
							<thead>
								<tr>
									<th width="6%">序号</th>
									<th>用户名称</th>
									<th>用户账号</th>
									<th>联系方式</th>
									<th>所属部门</th>
									<th>所属角色</th>
									<th>是否有效</th>
									<th width="40%">操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
		</div>
	</div>





	<!-- <div class="row" style="margin-top:10px;">
			<div class="col-sm-2">
				<div class="panel panel-primary">
				    <div class="panel-heading">
				        <h3 class="panel-title">部门列表</h3>
				    </div>
				    <div class="panel-body">
				        <div style="-moz-user-select: none;" id="tree" class="ztree">
								
						</div>
					</div>
				</div>
			</div> -->
	<%-- <div class="col-sm-10">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">查询条件</h3>
						</div>
						<div class="panel-body">
							<form name="queryForm" id="queryForm" class="form-inline"
								role="form">
								<div class="form-group">
									<label class="sr-only" for="userName">用户名称</label> <input
										type="text" name="userName" class="form-control" id="userName"
										placeholder="请输入用户名称">
								</div>
								<div class="form-group">
									<label class="sr-only" for="mobile">联系方式</label> <input
										type="text" name="mobile" class="form-control" id="mobile"
										placeholder="请输入联系方式">
								</div>
								<div class="form-group">
									<label class="sr-only" for="roleId">所属角色</label> <select
										id="roleId" name="roleId" class="form-control">
										<option value="">请选择角色</option>
									</select>
								</div>
								&nbsp;&nbsp;&nbsp;
								<button type="button" id="btn_search" class="btn btn-default">查询</button>
							</form>
						</div>
					</div>
					
					
						<!---->
						<div class="border clearfix" style="padding-top: 13px;">
							<span class="l_f"> <input type="button" id="btn_add"
								class="btncss" value="用户新增" />
							</span>
						</div>
					
					<div class="table_menu_list" id="tableItem">
						<table class="tablecss row col-xs-12" style="margin-bottom: 10px;"
							id="datatable">
							<thead>
								<tr>
									<th width="6%">序号</th>
									<th>用户名称</th>
									<th>用户账号</th>
									<th>联系方式</th>
									<th>所属部门</th>
									<th>所属角色</th>
									<th>是否有效</th>
									<th width="40%">操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				
				
				</div>
			</div>
		</div>
	</div> --%>
	

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="addOrEditModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">用户信息编辑</h4>
				</div>
				<div class="modal-body"></div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>


</body>

<script>
		var contextPath = "${pageContext.request.contextPath}";
		var oTable1;
        var departmentLength;
		jQuery(function($) {
			
			/* 所属角色下拉框赋值 */
			$.ajax({
				url : contextPath + "/user/getRole",
				type : "POST",
				dataType : "json",
				success : function(data) {
					for ( var i = 0; i < data.length; i++) {
						var option = $("<option value='"+data[i].roleId+"'>"
								+ data[i].roleName +"</option>")
						$("#roleId").append(option);
					}
				}
			});
			/* 所属部门下拉框赋值 */
			$.ajax({
				url : contextPath + "/user/getDepartment",
				type : "POST",
				dataType : "json",
				success : function(data) {
					departmentLength=data.length;
					for ( var i = 0; i < data.length; i++) {
						var option = $("<option value='"+data[i].departmentId+"'>"
								+ data[i].departmentName +"</option>")
						$("#departmentId").append(option);
					}
				}
			});
			
			
			var setting = {
	  			check: {
	  				enable: false
	  			},
	  			data: {
	  			    simpleData: {
	  			       enable: true
	  			    }
	  			},
				callback: {
	        		//onClick: zTreeOnClick
	        	}
	  		};
			
			oTable1 = $('#datatable').dataTable({
				"bStateSave": false,//状态保存
				"bServerSide" : true,//是否开启服务端查询
				"bAutoWidth" : false,//自动宽度
				"bPaginate": true, //翻页功能
				"bLengthChange": false, //改变每页显示数据数量
		        "bFilter": false, //列筛序功能
		        "bProcessing": false,//进度提示
		        "bInfo": true,//页脚信息
		        "iDisplayLength":20,//默认每页显示的记录数
		        "iDisplayStart":0,
				"ajax" : {
					"type" : "post",
					"dataType" : "json",
					"async" : false,
					"data" : function(d) {
						
						return $.extend({}, d, {
							//添加额外的参数传给服务器(可以多个)
							"userName" : $("#userName").val(),
							"mobile" : $("#mobile").val(),
							"roleId" : $("#roleId").val(),
							"departmentId" : $("#departmentId").val()
							
						});
					},
					"url" : contextPath+ "/user/dataTables?queryParams="+JSON.stringify($("#queryForm").serializeObject())
				},
				"aoColumnDefs": [
					{ "bVisible": false, "aTargets": [8] }//
				],
				"aoColumns":[{
					"mData":"",
					"bSortable":false,
					"sDefaultContent":"",
					"sClass":"center"
				},{
					"mData" : "userName",
					"bSortable" : false,
					"sClass":"center",
					"sWidth" : "15%"
				},{
					"mData" : "loginName",
					"bSortable" : false,
					"sClass":"center",
					"sWidth" : "15%"
				},{
					"mData" : "mobile",
					"bSortable" : false,
					"sClass":"center",
					"sWidth" : "10%"
				},{
					"mData" : "departmentName",
					"bSortable" : false,
					"sClass":"center",
					"sWidth" : "10%"
				},{
					"mData" : "roleName",
					"bSortable" : false,
					"sClass":"center",
					"sWidth" : "10%"
				},{
					"mData" : "isvalid",
					"bSortable" : false,
					"sClass":"center",
					"sWidth" : "8%",
					"mRender":function(data,type,row){
						if(data == "1"){
							return "有效";
						}else{
							return "无效";
						}
					}
				},{
					"mData" : "",
					"bSortable" : false,
					"sDefaultContent":"",
					"sClass":"center",
					"sWidth" : "40%",
					"mRender": function (data,type,row) {
						var _html = "";
						
							_html += '<input class="btncss btn_edit" type="button" id="btn_edit" name="bj" value="编辑"/>';
						
						if(row.departmentId>0){
							
							_html += '<input class="btncss btn_delete" type="button" id="btn_delete" name="sc" value="删除"/>';
						
						}
						
						return _html;
						
					}
				},{
					"mData" : "userId",
					"bSortable" : false
				}],
				fnInitComplete:initComplete,
				drawCallback: function( settings ) {
			        /**
			         *生成索引值 
			         **/
			        n = this.api().page.info().start;
					this.api().column(0).nodes().each(function(cell, i) {
			            cell.innerHTML = i + 1;
			        });
			    }
			})
		});
		
		
		
		/*信息-新增 */
		$("#btn_add").on('click',function(){
			if(departmentLength<=0){
				layer.alert("暂无部门信息,请先到部门管理中添加部门信息！\r\n", {
					title : '提示框',
					icon : 0,
				});
				return false;
			}
			$("input[type='text']").val("");
			$.ajax({
				type:"get",
	    		url:contextPath+"/user/info?userId=",
	    		success:function(data){
	    			$(".modal-body").html(data);
	    			$("#addOrEditModal").modal();
	            }
	    	});
			
		});
		
		/**
	     * 表格加载渲染完毕后执行的方法
	     * @param data
	     */
	    function initComplete(data){
			
	    	/*查询*/
			$("#btn_search").click(function() {
				oTable1.fnDraw();
			});
			
	    	 /**
		     *修改 
		     **/
		    $("#datatable tbody").on("click","#btn_edit",function(){
		    	var data = oTable1.api().row($(this).closest('tr')).data();//获取该行数据
		    	$("input[type='text']").val("");
				$.ajax({
					type:"get",
		    		url:contextPath+"/user/info?userId="+data.userId,
		    		success:function(data){
		    			$(".modal-body").html(data);
		    			$("#addOrEditModal").modal();
		            }
		    	});
		    });
	    	 
		    /**
		     *删除 
		     **/
		    $("#datatable tbody").on("click","#btn_delete",function(){
		    	var data = oTable1.api().row($(this).closest("tr")).data();
		    	
		    	layer.confirm('确定删除该记录？', {
				    btn: ['确定','取消'] //按钮
				}, function(index){
					$.ajax({
		        		type:"post",
		        		url:contextPath+"/user/dele",
		        		data:"userId="+data.userId,
		        		success:function(data){
		                	if(data.code){
		                		layer.msg(data.msg,{icon:6,time:2000});
		                		$('#iframe',parent.document).attr("src", contextPath+"/user/list").ready();
		                	}else{
		                		layer.msg(data.msg,{icon:5,time:2000});
		                	}
		                }
		        	});
					
					layer.close(index);
				}, function(index){
					layer.close(index);
				});
		    })
		}
		
	    function zTreeOnClick(event, treeId, treeNode) {
	    	
		};
		
		$.fn.serializeObject = function(){
		    var o = {};
		    var a = this.serializeArray();
		    $.each(a, function() {
		        if (o[this.name] !== undefined) {
		            if (!o[this.name].push) {
		                o[this.name] = [o[this.name]];
		            }
		            o[this.name].push(this.value || '');
		        } else {
		            o[this.name] = this.value || '';
		        }
		    });
		    return o;
		};

	</script>
</html>