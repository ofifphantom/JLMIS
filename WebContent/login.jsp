<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>测试用食品后台管理系统</title>
<link href="${pageContext.request.contextPath}/junlin/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/junlin/css/mine/all.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/junlin/css/mine/login.css" rel="stylesheet">

</head>
<body>
<!-- 获取保存在缓存里的登录名 -->
<%
Cookie cookies[]=request.getCookies();
String loginName="";
String password="";
 if(cookies!=null){
	for(int i=0;i<cookies.length;i++){
		String name=cookies[i].getName();
		if("loginName".equals(name)){
			loginName=cookies[i].getValue();
		}
		else if("password".equals(name)){
			password=cookies[i].getValue();
		}
		
	 }
 }

%>
	<div class="container-fluid">
		<img class="bg-img" src="${pageContext.request.contextPath}/junlin/img/bg.png" width="100%" />
		<div class="clearfix"></div>

		<div class="content">
			<div class="leftdiv">
				<img src="${pageContext.request.contextPath}/junlin/img/01.png" width="100%" />
			</div>
			<div class="rightdiv">
				<form class="form-horizontal" action="javascript:doLogin()" method="post">
					<div class="form-group">
						<label for="loginName" class="col-xs-2 control-label">用户名</label>
						<div class="col-xs-10">
							<input type="text" class="form-control" placeholder="请输入登录名" id="loginName" name="loginName" value="<%= loginName %>">
						</div>
					</div>
					<div class="form-group">
						<label for="Password" class="col-xs-2 control-label">密<span
							class="text-white">密</span>码
						</label>
						<div class="col-xs-10">
							<input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" value="<%= password %>">
						</div>
					</div>
					<div class="form-group">
						<label for="validateCode" class="col-xs-2 control-label">验证码</label>
						<div class="col-xs-6">
							<input type="text" class="form-control" id="validateCode" name="validateCode"/>
						</div>
						<div class="col-xs-4">
							<img  id="code" style="width: 90px" alt="验证码,看不清楚?请点击刷新验证码" onClick="myReload()" height="40px" src="PictureCheckCode"/><br/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-6 col-xs-6">
							<div class="checkbox">
							<% if(loginName!=""&&loginName!=null){ %>
								<label><input type="checkbox" name="remenberLoginName" id="remenberComCodeAndLoginName" checked="checked">记住密码</label>
							<% }else { %>
							<label><input type="checkbox" name="remenberLoginName" id="remenberComCodeAndLoginName">记住密码</label>
							<% } %>
							</div>
						</div>
						<!-- <div class="col-sm-6 col-xs-6 text-right">
							<a class="forgot-password-link">忘记密码</a>
						</div> -->
					</div>
					<div class="form-group">
						<div class="col-sm-12 col-xs-12">
							<button type="submit" class="btn btn-block btn-success">登
								录</button>
						</div>
					</div>
				</form>
			</div>
			<div class="clearfix"></div>

		</div>

	</div>
</body>
<script src="${pageContext.request.contextPath}/junlin/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/junlin/js/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/junlin/script/window.js" type="text/javascript"></script>
<script>
$(window).resize(function() {
	 location.reload();
});

	var begin = function() {
		$(".bg-img").height(winHeight);
		$(".content").css({
			"margin-top" : (winHeight - $(".content").height()) * 0.41 + "px"
		})
	}();
	
	//验证码
	function myReload(){
	    document.getElementById("code").src=document.getElementById("code").src + "?nocache="+new Date().getTime();
	}
	
	//登录
	function doLogin(){
		var remenberComCodeAndLoginName="false";
		if($("#remenberComCodeAndLoginName").is(":checked")){
			remenberComCodeAndLoginName="true";
		}
		else{
			remenberComCodeAndLoginName="false";
		}
	    var loginName=$("#loginName").val();
	    var password=$("#password").val();
	    var validateCode=$("#validateCode").val();
	    
	   if(loginName==""||loginName==null||password==""||password==""){
	    	layer.msg("请输入用户名及密码",{icon:1,time:1500});
	    	return;
	    }else if(validateCode==""||validateCode==null){
	    	layer.msg("请输入验证码!",{icon:1,time:1500});
	    	return;
	    }else{
	    	$.ajax({
	        	url : "${pageContext.request.contextPath}/doLogin",
	        	type : 'post',
	        	data : {
	        		"loginName":loginName,
	        		"password":password,
	        		"validateCode":validateCode,
	        		"remenberComCodeAndLoginName":remenberComCodeAndLoginName
	        	},
	        	dataType : "json",
	        	success : function(data) {
	        		if(data.code){
	        			location.href= "${pageContext.request.contextPath}/index";
	        		}else{
	        			layer.msg(data.msg,{icon:1,time:1500});
	        			myReload();
	        		}
	        	},
				error : function(data){
					
				}        	
	        });
	    }
	}
</script>
</html>