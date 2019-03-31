<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/common_css.jsp" %>
<%@include file="/common_js.jsp" %>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>用户编辑</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<style type="text/css">
			.subbtn{
				display: block;
				background-color: #fff;
				border-top: 1px solid #e5e5e5;
				margin: 5px 0px;
				padding: 10px;
			}
			input[type="text"]{
			margin-left:0;
			}
		</style>
	</head>
	<body>
		<div class="row">
			<div class="col-md-12">
				<form class="form-horizontal" role="form" name="submitForm" id="submitForm">
					<input type="hidden" id="userId" name="userId" value="${user.userId}"/>
			 		<div class="form-group">
			 			<label class="col-md-3 control-label" for="userName">用户名称：</label>
			 			<div class="col-md-9">
							<input type="text" id="userName" name="userName"  placeholder="用户名称不能为空"  value="${user.userName}"
								class="form-control input-xlarge" required data-msg-required="请输入用户名称" maxlength="64"/>
						</div>
			 		</div>
			 		
				 	<div class="form-group">
			 			<label class="col-md-3 control-label" for="loginName">用户账号：</label>
			 			<div class="col-md-9">
							<input type="text" id="loginName" name="loginName"  placeholder="用户账号不能为空"  value="${user.loginName}"
								class="form-control input-xlarge" required data-msg-required="请输入用户账号" maxlength="255"/>
						</div>
				 	</div>
			 		<c:if test="${user.userId == null}">
				 		<div class="form-group">
				 			<label class="col-md-3 control-label" for="password">用户密码：</label>
				 			<div class="col-md-9">
								<input type="text" id="password" name="password"  placeholder="用户密码不能为空"  value="${user.password}"
									class="form-control input-xlarge" required data-msg-required="请输入用户密码" maxlength="255"/>
							</div>
				 		</div>
			 		</c:if>
			 		<c:if test="${user.userId != null}">
				 		<div class="form-group" style="display:none">
				 			<label class="col-md-3 control-label" for="password">用户密码：</label>
				 			<div class="col-md-9">
								<input type="text" id="password" name="password"  placeholder="用户密码不能为空"  value="${user.password}"
									class="form-control input-xlarge" required data-msg-required="请输入用户密码" maxlength="255"/>
							</div>
				 		</div>
			 		</c:if>
			 		
			 		<div class="form-group">
			 			<label class="col-md-3 control-label" for="mobile">联系方式：</label>
			 			<div class="col-md-9">
							<input type="text" id="mobile" name="mobile" value="${user.mobile}" 
								 class="form-control input-xlarge"  maxlength="11" 
								 required data-rule-phone="true"  data-msg-phone="请输入正确格式" />
						</div>
			 		</div>
			 		
			 		<div class="form-group">
						<label class="col-md-3 control-label" for="departmentId">所属部门：</label>
			 			<div class="col-md-9">
			 			<c:if test="${user.departmentId != -1}">
			 				<select id="departmentId" name="departmentId" class="form-control input-xlarge">
			 					<c:forEach items="${departments}" var="department">
			 					<c:if test="${department.departmentId == user.departmentId}">
			 						<option value="${department.departmentId}" selected="selected">${department.departmentName }</option>
			 					</c:if>
			 					<c:if test="${department.departmentId != user.departmentId}">
			 						<option value="${department.departmentId}">${department.departmentName }</option>
			 					</c:if>
			 					</c:forEach>
			 				</select>
			 				</c:if>
			 				<c:if test="${user.departmentId == -1}">
			 				<select id="departmentId" name="departmentId" class="form-control input-xlarge">
			 					<option value="${department.departmentId}"></option>
			 				</select>
			 				</c:if>
						</div>
			 		</div>
			 		
			 		<div class="form-group">
						<label class="col-md-3 control-label" for="roleId">所属角色：</label>
			 			<div class="col-md-9">
			 			<c:if test="${user.departmentId != -1}">
			 				<select id="roleId" name="roleId" class="form-control input-xlarge">
			 					<c:forEach items="${roles}" var="role">
			 					    <c:if test="${role.roleId == user.roleId}">
			 						<option value="${role.roleId}" selected="selected">${role.roleName }</option>
			 						</c:if>
			 						<c:if test="${role.roleId != user.roleId}">
			 						<option value="${role.roleId}">${role.roleName }</option>
			 						</c:if>
			 					</c:forEach>
			 				</select>
			 				</c:if>
			 				<c:if test="${user.departmentId == -1}">
			 				<select id="roleId" name="roleId" class="form-control input-xlarge">
			 					<c:forEach items="${roles}" var="role">
			 					    <c:if test="${role.roleId == user.roleId}">
			 						<option value="${role.roleId}" selected="selected">${role.roleName }</option>
			 						</c:if>
			 						
			 					</c:forEach>
			 				</select>
			 				</c:if>
						</div>
			 		</div>
			 		<div class="form-group">
						<label class="col-md-3 control-label" for="isvalid">是否有效：</label>
			 			<div class="col-md-9">
			 			<c:if test="${user.departmentId != -1}">
			 			<select id="isvalid" name="isvalid" class="form-control input-xlarge">
			 					<option value="1">有效</option>
			 					<option value="0">无效</option>
			 				</select>
			 			</c:if>
			 			<c:if test="${user.departmentId == -1}">
			 			<select id="isvalid" name="isvalid" class="form-control input-xlarge">
			 					<option value="1" >有效</option>
			 				</select>
			 			</c:if>
			 				
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
			var userId = '${user.userId}';
			if(userId != null && userId != ''){
				$("#roleId").val('${user.roleId}');
				$("#isvalid").val('${user.isvalid}');
				$("#departmentId").val('${user.departmentId}');
			}
			$("#submitForm").validate({
				
				submitHandler: function() {
					
					var url = contextPath+"/user/addOrUpdate";
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
								$('#iframe',parent.document).attr("src", contextPath+"/user/list").ready();
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
		
		//电话或手机验证规则  
		jQuery.validator.addMethod("phone", function (value, element) {
		    var phone=/(^1[3|4|5|7|8]\d{9}$)|(^\d{3,4}-\d{7,8}$)|(^\d{7,8}$)|(^\d{3,4}-\d{7,8}-\d{1,4}$)|(^\d{7,8}-\d{1,4}$)/;
		    return this.optional(element) || (phone.test(value));
		}, "格式不对");
		
		
	</script>
</html>