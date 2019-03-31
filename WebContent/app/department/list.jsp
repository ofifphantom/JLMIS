<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>部门管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common.jsp" %>

	</head>
	<body>
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
						<!---->
						<div class="border clearfix" style="padding-top:13px;">
							<span class="l_f"> <input type="button" id="btn_add"
								class="btncss" value="部门新增" />
							</span>
						</div>
					<div class="table_menu_list">
						<table class="tablecss row col-xs-12" style="margin-bottom:10px;" id="datatable">
							<thead>
								<tr>
									<th width="6%">序号</th>
									<th>部门名称</th>
									<th>部门编号</th>
									<th>部门职能</th>
									<th width="30%">操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="addOrEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                <h4 class="modal-title" id="myModalLabel">部门信息编辑</h4>
		            </div>
		            <div class="modal-body"></div>
		           
		        </div><!-- /.modal-content -->
		    </div><!-- /.modal -->
		</div>
		
		
	</body>
	
	<script>
		var contextPath = "${pageContext.request.contextPath}";
		var oTable1;
		jQuery(function($) {
			oTable1 = $('#datatable').dataTable({
				"bStateSave": false,//状态保存
				"bServerSide" : true,//是否开启服务端查询
				"bAutoWidth" : false,//自动宽度
				"bPaginate": true, //翻页功能
				"bLengthChange": false, //改变每页显示数据数量
		        "bFilter": false, //列筛序功能
		        "bProcessing": false,//进度提示
		        "bInfo": true,//页脚信息
		        "iDisplayLength":15,//默认每页显示的记录数
		        "iDisplayStart":0,
				"ajax" : {
					"type" : "post",
					"dataType" : "json",
					"async" : false,
					"data" : function(d) {
						return $.extend({}, d, {
							//添加额外的参数传给服务器(可以多个)
							//"buildingName" : $("#build_name").val(),
						});
					},
					"url" : contextPath+ "/department/dataTables"
				},
				"aoColumnDefs": [
					{ "bVisible": false, "aTargets": [5] }//
				],
				"aoColumns":[{
					"mData":"",
					"bSortable":false,
					"sDefaultContent":"",
					"sClass":"center"
				},{
					"mData" : "departmentName",
					"bSortable" : false,
					"sClass":"center",
					"sWidth" : "15%"
				},{
					"mData" : "departmentCode",
					"bSortable" : false,
					"sClass":"center",
					"sWidth" : "15%"
				},{
					"mData" : "departmentDesc",
					"bSortable" : false,
					"sClass":"center",
					"sWidth" : "15%"
				},{
					"mData" : "",
					"bSortable" : false,
					"sDefaultContent":"",
					"sClass":"center",
					"sWidth" : "35%",
					"mRender": function (data,type,row) {
						var _html = "";
						
							_html += '<input class="btncss btn_edit" type="button" id="btn_edit" name="bj" value="编辑"/>';
						
							_html += '<input class="btncss btn_delete" type="button" id="btn_delete" name="sc" value="删除"/>';
						
						return _html;
						
					}
				},{
					"mData" : "departmentId",
					"bSortable" : false,
					"sWidth" : "15%"
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

		
		/*部门信息-新增 */
		$("#btn_add").on('click',function(){
			$("input[type='text']").val("");
			$.ajax({
				type:"get",
	    		url:contextPath+"/department/info?departmentId=",
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
			
	    	 /**
		     *修改 
		     **/
		    $("#datatable tbody").on("click","#btn_edit",function(){
		    	var data = oTable1.api().row($(this).closest('tr')).data();//获取该行数据
		    	$("input[type='text']").val("");
				$.ajax({
					type:"get",
		    		url:contextPath+"/department/info?departmentId="+data.departmentId,
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
		        		url:contextPath+"/department/dele",
		        		data:"departmentId="+data.departmentId,
		        		success:function(data){
		                	if(data.code){
		                		layer.msg(data.msg,{icon:6,time:2000});
		                		$('#iframe',parent.document).attr("src", contextPath+"/department/list").ready();
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
		
	</script>
</html>