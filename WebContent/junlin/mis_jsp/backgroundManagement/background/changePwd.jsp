<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>测试用食品后台管理系统</title>
		<link href="${pageContext.request.contextPath}/junlin/css/bootstrap.min.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/junlin/css/mine/all.css" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/junlin/css/mine/public.css" />
		<style type="text/css">
			html,body.content{
			height:100%;
			}
			.content {
				padding:30px 40px;
			}
			.form-horizontal{
			margin-top:10%;
			}
			input.form-control{
				border-radius: 0;
			}
			.text-title .btn{
			float:right;
			}
		</style>
	</head>

	<body class="content">
		<div class="">
			<div class="">
				<h4 class="text-title">操作日志 
				<button type="button" class="btn btn-sm"  onclick="javascript:history.back(-1);">取消</button>
				</h4>
				<form class="form-horizontal">
					<div class="form-group">
						<div class="col-xs-2 "></div>
						<label for="oldPassword" class="col-xs-2 control-label">原密码&nbsp;<i class="text-danger">*</i></label>
						<div class="col-xs-5">
							<input type="password" class="form-control" id="oldPassword" onblur="oldpwdCheck(this)" onkeyup="cky(this)">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-2 "></div>
						<label for="Password" class="col-xs-2 control-label">密码&nbsp;<i class="text-danger">*</i></label>
						<div class="col-xs-5">
							<input type="password" class="form-control" id="Password" onblur="userpwdCheck(this)" onkeyup="cky(this)">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-2 "></div>
						<label for="Password2" class="col-xs-2 control-label">再次密码&nbsp;<i class="text-danger">*</i></label>
						<div class="col-xs-5">
							<input type="password" class="form-control" id="Password2" onblur="aginpwdCheck(this)" onkeyup="cky(this)">
						</div>
					</div>
					<div class="form-group text-center">
						<div class="col-sm-12 col-xs-12">
							<button type="button" class="btn btn-success" onclick="getresult()">提交</button>
							<button type="reset" class="btn" onclick="reset()">重置</button>
						</div>
					</div>
				</form>
			</div>

		</div>
	</body>
	<script src="${pageContext.request.contextPath}/junlin/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/junlin/js/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/junlin/script/public.js" type="text/javascript"></script>
	<script>
		var userpwd = false;
		var aginpwd = false;
		var oldpwd = false;

		function reset() {
			userpwd = false;
			aginpwd = false;
			oldpwd = false;
		}

		function oldpwdCheck(thisinput) {
			oldpwd = false;
			if(!isnotEmpty($(thisinput).val())) {
				laywarn("原密码不能为空");
			} else {
				//此处验证密码是否正确，如果正确返回true;
				$.ajax({
					url: '<%=basePath%>backgroundManagement/background/userManager/decidePwd',
					type: "POST",
					dataType: "json",
					async: false,
					cache: false,
					data:{
						"password":$(thisinput).val()
					},

					success: function(data) {
						if(data.success) {
							oldpwd = true;
						} else {
							oldpwd = false;
							laywarn("原密码不正确");
						}

					}
				});
				
			}

			if(isnotEmpty($("#Password").val())&&oldpwd) {
				userpwd = false;
				aginpwd = false;
				if($("#Password").val() == $("#oldPassword").val()) {
					laywarn("新密码不能和原密码相同");
					oldpwd=false;
				} else {
					userpwd = true;
					aginpwd = true;
				}
			}
			return oldpwd;
		}

		function userpwdCheck(thisinput) {
			userpwd = checkPassword($(thisinput).val());
			if(!isnotEmpty($(thisinput).val())) {
				laywarn("密码不能为空");
				userpwd = false;
			} else {
				if(!userpwd) {
					laywarn("密码为8-16位字母数字下划线组合");
					userpwd = false;
				} else if($("#Password").val() == $("#oldPassword").val()) {
					laywarn("新密码不能和原密码相同");
					userpwd = false;
				} else {
					userpwd = true;
				}
			}
			return userpwd;
		}

		function aginpwdCheck(thisinput) {
			aginpwd = false;
			if(!isnotEmpty($("#Password").val())) {
				laywarn("请先输入密码");
				$(thisinput).val("")
			} else {
				if(!isnotEmpty($(thisinput).val())) {
					laywarn("请再次输入密码");
				} else if($("#Password").val() == $(thisinput).val()) {
					aginpwd = true;
				} else {
					laywarn("两次输入的密码不相同，请重新输入");
				}
			}
			return aginpwd;
		}

		function getresult() {
			if(oldpwdCheck("#oldPassword")){
				if(userpwdCheck("#Password")){
					if(aginpwdCheck("#Password2")){
						$.ajax({
							url: '<%=basePath%>backgroundManagement/background/userManager/updatePwd',
							type: "POST",
							dataType: "json",
							async: false,
							cache: false,
							data:{
								"password":$("#Password").val()
							},
							success: function(data) {
								if(data.success) {
									laysuccess(data.msg);
									layer.confirm('密码修改成功，需重新登录。', {
										  btn: ['确定'],//按钮
										  closeBtn: 0,
										  title :'提示'
										}, function(){
											top.location.href="<%=request.getContextPath()%>/login";	
										}	
									);
									
								} else {
									layfail(data.msg);
								}
							}
						});
					}					
				}
			}
		}
	</script>

</html>