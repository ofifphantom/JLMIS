<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>角色编辑</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/common_css.jsp" %>
		<%@include file="/common_js.jsp" %>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/zTree/css/zTreeStyle/metro.css" />
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
					<input type="text" id="roleId" name="roleId"  value="${role.roleId}" style="display:none"/>
			 		<div class="form-group">
			 			<label class="col-md-3 control-label" for="roleName">角色名称：</label>
			 			<div class="col-md-9">
							<input type="text" id="roleName" name="roleName"  placeholder="角色名称不能为空"  value="${role.roleName}"
								class="form-control input-xlarge" required data-msg-required="请输入角色名称" maxlength="55"/>
								
						</div>
			 		</div>
			 		<div class="form-group">
						<label class="col-md-3 control-label" for="roleDesc">角色描述：</label>
			 			<div class="col-md-9">
			 				<textarea name="roleDesc" id="roleDesc" class="form-control input-xlarge">${role.roleDesc}</textarea>
						</div>
			 		</div>
			 		<div class="form-group">
						<label class="col-md-3 control-label" for="re">角色权限：</label>
			 			<div class="col-md-9">
			 				<input type="hidden" id="resIds" name="resIds"/>
			 				<ul id="tree" class="ztree"></ul>
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
	
	<script src="${pageContext.request.contextPath}/static/js/zTree/js/jquery.ztree.all-3.5.min.js"></script>
	<script>
		var contextPath = "${pageContext.request.contextPath}";
		var zTree;
		$(function(){
			$("#submitForm").validate({
				submitHandler: function() {
					var treeObj = $.fn.zTree.getZTreeObj("tree");
			        var nodes = treeObj.getCheckedNodes(true);
			        if(nodes.length == 0){
			        	layer.alert("请选择角色权限!", {
				            skin: 'layui-layer-lan'
				            ,closeBtn: 0
				            ,shift: 4 //动画类型
				        });
			        	return;
			        }
			        var resIds = [];
			        for (var i = 0; i < nodes.length; i++) {
			        	resIds.push(nodes[i].id);
			        }
					
			        $("#resIds").val(resIds);
					var url = contextPath+"/role/addOrUpdate";
					$('#submitForm').ajaxSubmit({
						type: "post",
	    				url:url,
						dataType:"json",
	            		data: $('form[name="submitForm"]').serialize(),
	                    success:function(data){
	    	            	if(data.code){
	    						layer.msg(data.msg,{icon:6,time:3000});
								$('#addOrEditModal').modal('hide');
								$("div").remove(".modal-backdrop");
								$('#iframe',parent.document).attr("src", contextPath+"/role/list").ready();
	    					}else{
	    						layer.msg(data.msg,{icon:5,time:2000});
	    					}
	    	            },
	                    error: function (data) {
	                         
	                    }
	    			});
				}
			});
			
			/**
			*权限树
			**/
			 var setting = {
		        check: {
		            enable: true
		        },
		        view: {
		
		            dblClickExpand: false,
		            showLine: true,
		            selectedMulti: false
		        },
		        data: {
		            simpleData: {
		                enable:true,
		                idKey: "id",
		                pIdKey: "pid",
		                rootPId: 0
		            }
		        },
		        callback: {
		            beforeClick: function(treeId, treeNode) {
		                var zTree = $.fn.zTree.getZTreeObj("tree");
		                if (treeNode.isParent) {
		                    zTree.expandNode(treeNode);
		                    return false;
		                }
		            }
		        }
		    };
		    
		    $.ajax({
	    		type: "get",
	            url: contextPath +"/role/getPermissions?roleId="+$("#roleId").val(),
	            async: false,
	            dataType:"json", 
	            success: function (data) {
	            	 $.fn.zTree.init($("#tree"), setting,data);
	            	/* var resIds = '${resIds}'.split(",");
	            	alert('${resIds}');
	            	var jsonstr = '([';
	            	$.each(data,function(i){
	            		jsonstr += '{"id":'+data[i].id+',"name":"'+data[i].name+'","pid":'+data[i].pid+',"open":'+data[i].open+'';
	            		$.each(resIds,function(j){
	            			alert(data[i].id);
	            			alert(resIds[j]);
	            			if(resIds[j] == data[i].id){
	            				jsonstr += ',"checked":true';
	            				return;
	            			}
	            		})
	            		jsonstr += '},';
	            	})
	            	jsonstr += '])';
	            	alert(jsonstr);
	            	var json = eval(jsonstr.substring(0,jsonstr.length-3)+'])');
	            	alert(json);
	                $.fn.zTree.init($("#tree"), setting,json); */
	            },
	            error: function (msg) {
	            	layer.msg("权限获取失败，请重新获取",{icon:6,time:3000});
	               // alert("权限获取失败，请重新获取");
	            }
	    	});
		});
		
	
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