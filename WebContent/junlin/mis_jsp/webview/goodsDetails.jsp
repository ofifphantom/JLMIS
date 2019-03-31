<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="UTF-8">
		<title></title>
		<script src="../../js/jquery-1.10.2.min.js"></script>
		<style>
			* {
				margin: 0;
				border: 0;
				/*list-style: none;
				text-decoration: none;*/
				box-sizing: border-box;
			}
			
			body,
			.maincotent {
				width: 100%;
				text-align: center;
			}
			.maincotent>article{
				color: #999999;
				font-size: 1.5rem;
				/*font-size: 24px;*/
				font-weight: bold;
				margin: 30px 0;
				letter-spacing: 1px;
			}
			#content{
				width:100%;
				height:500px;
			}
		</style>
	</head>

	<body onload="initialize()">
		<div class="maincotent">
			<!-- <article>商品信息</article> -->
			<div id="content">
				
			</div>
		</div>
	</body>
	
	<script type="text/javascript">
		var lat;  
	    function initialize(){  

	    	
	    	
	        //lat = window.demo.GetLat()-0;
	       	lat= GetQueryString("goodsId")
			var goodsUrl='<%=basePath%>goodsInformation/getGoodsDetailMsgByGoodsId?goodsId='+lat
			//window.demo.showToast(goodsUrl);
			$.ajax({
					url: goodsUrl,
					type: 'GET', //GET
					async: false, //或false,是否异步
					dataType: 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					success: function(data) {
						if(data.code!=200){
						$("#content").html("暂无数据");
						}else{
						//window.demo.showToast("我进了ajax");
						$("#content").html(data["resultData"]["details"]);
							//document.getElementById("content").innerHTML=data["resultData"]["details"];
							
						}
						
						
					}
		})
	    }
	    function GetQueryString(name)
    	{
    	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    	     var r = window.location.search.substr(1).match(reg);
    	     if(r!=null)return  unescape(r[2]); return null;
    	}	
		
	</script>
</html>