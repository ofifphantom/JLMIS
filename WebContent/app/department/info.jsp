<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common_css.jsp" %>
<%@include file="/common_js.jsp" %>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>部门编辑</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<style type="text/css">
			.subbtn{
				display: block;
				background-color: #fff;
				border-top: 1px solid #e5e5e5;
				margin: 5px 0px;
				padding: 10px;
			}
		</style>
	</head>
	<body>
		<div class="row">
			<div class="col-md-12">
				<form class="form-horizontal" role="form" name="submitForm" id="submitForm">
					<input type="hidden" id="departmentId" name="departmentId" value="${department.departmentId}"/>
			 		<div class="form-group">
			 			<label class="col-md-3 control-label" for="departmentName">部门名称：</label>
			 			<div class="col-md-9">
							<input type="text" id="departmentName" name="departmentName"  placeholder="部门名称不能为空"  value="${department.departmentName}"
								class="form-control input-xlarge" required data-msg-required="请输入部门名称" maxlength="55"/>
						</div>
			 		</div>
			 		<div class="form-group">
			 			<label class="col-md-3 control-label" for="departmentCode">部门编号：</label>
			 			<div class="col-md-9">
							<input type="text" id="departmentCode" name="departmentCode" value="${department.departmentCode}" 
								 class="form-control input-xlarge"  maxlength="7"/>
						</div>
			 		</div>
			 		
			 		<div class="form-group">
						<label class="col-md-3 control-label" for="departmentDesc">部门职能：</label>
			 			<div class="col-md-9">
			 				<textarea name="departmentDesc" id="departmentDesc" class="form-control input-xlarge">${department.departmentDesc}</textarea>
						</div>
			 		</div>
			 		<div class="clearfix subbtn">
						<div class="col-md-offset-4 col-md-8">
							<input type="button" class="btn btn-default" data-dismiss="modal" value="关 闭"/>&nbsp;&nbsp;&nbsp;
							<input type="submit" class="btn btn-info" value="保 存"/>
						</div>
					</div>
				</form>
			</div>
		</div>
	</body>
	<script>
		var contextPath = "${pageContext.request.contextPath}";
		$(function(){
			$("#submitForm").validate({
				submitHandler: function() {
					var url = contextPath+"/department/addOrUpdate";
					$('#submitForm').ajaxSubmit({
						type: "post",
	    				url:url,
						dataType:"json",
	            		data: $('form[name="submitForm"]').serialize(),
	                    success:function(data){
	    	            	if(data.code){
	    						layer.msg(data.msg,{icon:6,time:2000});
								$('#addOrEditModal').modal('hide');
								$("div").remove(".modal-backdrop");
								$('#iframe',parent.document).attr("src", contextPath+"/department/list").ready();
	    					}else{
	    						layer.msg(data.msg,{icon:5,time:2000});
	    					}
	    	            },
	                    error: function (data) {
	                         
	                    }
	    			});
				}
			})
		})
		
	
		//配置错误提示的节点，默认为label，这里配置成 span （errorElement:'span'）
		$.validator.setDefaults({
			errorElement:'span'
		});
		
		//配置通用的默认提示语
		$.extend($.validator.messages, {
			required: '必填',
		    equalTo: "请再次输入相同的值"
		});
		
		
	</script>
</html>