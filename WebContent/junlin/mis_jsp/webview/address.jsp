<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/";
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
	<head>
		<meta charset="UTF-8">
		<meta name ="viewport" content ="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
		<title></title>
		<link rel="stylesheet" href="<%=basePath%>junlin/js/phonealert/style.css">
		<style>
			* {
				margin: 0;
				border: 0;
				list-style: none;
				text-decoration: none;
				box-sizing: border-box;
				font-family: "微软雅黑";
			}
			body,
			.container,
			.container>div,
			#address_div>ul{
				width: 100%;
				margin: 0;
				padding: 0;
				background: #f1f2f6;
				font-size: 16px;
				color: #555;
			}
			/*下面浮动的btn begin*/
			#add_div{
				text-align: center;
				position: fixed;
				bottom: 0;
				line-height: 50px;
			}
			.btn{
				display: inline-block;
				width:80%;
				height: 40px;
				background: #60b746;
				color: white;
				font-size: 16px;
				letter-spacing: 1px;
			}
			/*下面浮动的btn end*/
			
			/*上面的地址内容  begin*/
			#address_div{
				margin-bottom: 50px;
			}
			#address_div li{
				width: 100%;
				line-height: 40px;
				background: #fff;
				padding:5px 10px 0px 10px;
				margin-bottom: 10px;
			}
			#address_div .per_name{
				margin-right: 5px;
			}
			.per_address{
				border-bottom: 1px solid #f1f2f6;
			}
			.per_dosomething>div{
				float: left;
				width: 50%;
			}
			.per_dosomething>.div_left{
				text-align: left;
			}
			.per_dosomething>.div_right{
				text-align: right;
			}
			.div_right>span{
				display: inline-block;
				padding-left: 10px;
			}
			.div_left>label>input,.div_left>label>span,
			.div_right>span>img,.div_right>span>span{
				vertical-align: middle;
			}
			.icon{
				height: 15px;
				margin-right: 3px;
			}
			.no_address{
				width: 100%;
				margin-top: 35%;
				color: #555;
				text-align: center;
				letter-spacing: 1px;
			}
			/*上面的地址内容  end*/
			/*弹出框 begin*/
			 .show-list{
	            width:80%;
	            margin: 0 auto;
	        }
	        .show-list li{
	            height: 1rem;
	            font-size: 16px;
	            display: flex;
	            flex-direction: row;
	            justify-content: center;
	            align-items: center;
	            border-bottom: 1px solid #dcdcdc;
	        }
			/*弹出框 end*/
			.clearfix{
				clear: both;
			}
		</style>
	</head>
	<body>
		<div class="container">
			<div id="address_div">
				<ul id="list">
					<%-- <li>
						<article>
							<span class="per_name">
							范小雨
							</span>
							<span class="per_tel">
							1547896542
							</span>
						</article>
						<article class="per_address">北京市海淀区三环到四环之间</article>
						<div class="per_dosomething">
							<div class="div_left">
								<label onclick="check_address(this)" class="check_address">
								<input type="radio" name="default_address" checked="checked"/>&nbsp;默认地址
								</label>
							</div>
							<div class="div_right">
								<span onclick="edit_address()"><img class="icon" src="<%=basePath%>junlin/img/phoneimg/icon_edit1.png"/>编辑</span>
								<span onclick="delete_address()"><img class="icon" src="<%=basePath%>junlin/img/phoneimg/icon_delete.png"/>删除</span>
							</div>
						</div>
						<div class="clearfix"></div>
					</li> --%>
				</ul>
				
			</div>
			<div id="add_div">
				<button type="button" class="btn" onclick="add_address()">新增地址</button>
			</div>
		</div>
	</body>
	<script src="<%=basePath%>junlin/js/jquery-1.10.2.min.js"></script>
	<script src="<%=basePath%>junlin/js/phonealert/popups.js"></script><!--弹出框-->
	<script type="text/javascript">
		
	
		var res = parseURL(window.location.href);
		
		$(document).ready(function(){
			if(res.isSave!=null){
				if(res.isSave=="yes"){
					Android.isCheckAddOrEdit("false");
				}
			}
			inint();
		});
		/*修改默认地址*/
		function check_address(id,thislabel){
			$.ajax({
				url: '<%=basePath%>shippingAddress/setPretermissionShippingAddressById',
				type: "POST",
				dataType: "json",
				async: false,
				data: {
					"userId": res.userId,
					"id":id
				},
				success: function(data) {
					if(data.code=200){
						//$(".check_address").html('<input type="radio" name="default_address"/>&nbsp;<span>设为默认</span>');
						//$(thislabel).html('<input type="radio" name="default_address" checked="checked"/>&nbsp;<span>默认地址</span>');
						inint();
					}else{
						phoneAlert(data.msg)
					}
				}
			});
		}
		
		/*编辑地址*/
		function edit_address(id){
			Android.isCheckAddOrEdit("true");
			window.location.href="addressModify.jsp?id="+id+
			"&userId="+res.userId
			;
		}
		/*删除地址*/
		function delete_address(id){
			jqalert({
	            /* title:'提示', */
	            prompt:'是否确定删除',
	            yestext:'确定',
	            notext:'取消',
	            yesfn:function () {
//	              jqtoast('提交成功');
	            	$.ajax({
	    				url: '<%=basePath%>shippingAddress/deleteShippingAddressById',
	    				type: "POST",
	    				dataType: "json",
	    				async: false,
	    				data: {
	    					"userId": res.userId,
	    					"id":id
	    				},
	    				success: function(data) {
	    					if(data.code=200){
	    						window.location.reload();
	    					}else{
	    						phoneAlert(data.msg)
	    					}
	    				}
	    			});
	            },
	            nofn:function () {
//	              jqtoast('你点击了取消');
	            }
	        })
						
			
		
		}
		
		/*添加地址*/
		function add_address(){
			Android.isCheckAddOrEdit("true");
			//跳转页面
			window.location.href="addressModify.jsp?userId="+res.userId;
		}
		
		/*获取所有的收货地址，跟据userid*/
		function inint(){
			$("#list").html("");
			$.ajax({
				url: '<%=basePath%>shippingAddress/getShippingAddressByUserId',
				type: "GET",
				dataType: "json",
				async: false,
				data: {
					"userId": res.userId
					/* "userId": 22 */
				},
				success: function(data) {
					if(data.code=200){
						var resultData=data.resultData;//收货地址list
						if(resultData.length>0){
							for(var i=0;i<resultData.length;i++){
								var checked='/>&nbsp;<span>设为默认</span>'
								if(resultData[i].isCommonlyUsed==1){checked='checked="checked"/>&nbsp;<span>默认地址</span>';}
								$("#list").append('<li>'+
										'<article>'+
										'<span class="per_name">'+
										resultData[i].consigneeName+/*收货姓名*/
										'</span>'+
										'<span class="per_tel">'+
										resultData[i].consigneeTel+/*收货电话*/
										'</span>'+
									'</article>'+
									'<article class="per_address">'+resultData[i].region+resultData[i].detailedAddress+/*收货区域*/'</article>'+
									'<div class="per_dosomething">'+
										'<div class="div_left">'+
											'<label onclick="check_address('+resultData[i].id+',this)" class="check_address">'+
											'<input type="radio" name="default_address"'+checked+
											'</label>'+
										'</div>'+
										'<div class="div_right">'+
											'<span onclick="edit_address('+resultData[i].id+')"><img class="icon" src="<%=basePath%>junlin/img/phoneimg/icon_edit1.png"/><span>编辑</span></span>'+
											'<span onclick="delete_address('+resultData[i].id+')"><img class="icon" src="<%=basePath%>junlin/img/phoneimg/icon_delete.png"/><span>删除</span></span>'+
										'</div>'+
									'</div>'+
									'<div class="clearfix"></div>'+
								'</li>');
							}
						}else{
							$("#list").append('<div class="no_address">您没有收货地址，请添加。</div>');
						}
					}else{
						phoneAlert(data.msg)
					}
				}
			});
		}
		/*判断是否传参*/
		function parseURL(url) {
			var url = url.split("?")[1];
			if(url != undefined) {
				var val = url.split("&");
				var len = val.length;
				var res = {};
				var arr = [];
				for(var i = 0; i < len; i++) {
					arr = val[i].split("=");
					res[arr[0]] = arr[1];
				}
				return res;
			} else {
				return false;
			}

		}

		
		/*提示框*/
		function phoneAlert(mess){
			jqalert({
                    title:'',
                    content:mess,
                    click_bg:true
                })
		}
	</script>
</html>