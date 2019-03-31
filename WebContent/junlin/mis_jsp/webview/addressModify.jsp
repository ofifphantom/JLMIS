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
		<meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
		<title></title>
		<link rel="stylesheet" href="<%=basePath%>junlin/js/phonealert/style.css">
		<link rel="stylesheet" href="<%=basePath%>junlin/js/phoneCity/style.css">
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
			#address_div>ul {
				width: 100%;
				margin: 0;
				padding: 0;
				background: #fff;
				font-size: 16px;
				color: #555;
			}
			/*下面浮动的btn begin*/
			
			#add_div {
				text-align: center;
				position: fixed;
				bottom: 0;
				line-height: 50px;
			}
			
			.btn {
				display: inline-block;
				width: 80%;
				height: 40px;
				background: #60b746;
				color: white;
				font-size: 16px;
				letter-spacing: 1px;
			}
			/*下面浮动的btn end*/
			/*上面的地址内容  begin*/
			
			#address_div {
				width: 100%;
				line-height: 40px;
				background: #fff;
				padding: 5px 10px 0px 10px;
				margin-bottom: 10px;
			}
			
			#address_div>article {
				border-bottom: 1px solid #f1f2f6;
			}
			
			.phone_input {
				height: 30px;
				border: none;
				outline: none;
				font-size: 16px;
			}
			
			textarea {
				width: 100%;
				height: 60px;
				font-family: "微软雅黑";
				font-size: 16px;
				color: #555;
				line-height: 30px;
				resize: none;
				outline: none;
				padding-left: 0;
			}
			
			input[type="radio"] {
				height: 30px;
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
        
			
			.clearfix {
				clear: both;
			}
			
			.fr {
				float: right;
			}
		</style>
	</head>

	<body>
		<div class="container">
			<div id="address_div">
				<article>收货人：<input type="text"  id="user_name" class="phone_input" placeholder="2-5个字" maxlength="5" /></article>
				<article>联系方式：<input type="text" id="user_tel" class="phone_input" placeholder="11位数字" maxlength="11" /></article>
				<article class="express-area">
					<a id="expressArea" href="javascript:void(0)">
                	<dl>
	                    <dt>选择地区：</dt>
	                    <dd>市 > 区> 环 </dd>
	                    <span class="fr">请选择></span>
	                </dl>
	                <div class="clearfix"></div>
            		</a>
					</article>
				<article><textarea id="user_deatil" placeholder="详细地址：5-30个字" maxlength="30"></textarea></article>
				<article><label for="default_radio">设为默认：</label><input id="default_radio" class="fr" type="radio" value="0" onclick="change_check(this)" /></article>
			</div>
			<div id="add_div">
				<button type="button" class="btn" onclick="save_address()">保存</button>
			</div>
		</div>
		
		 <!--选择地区弹层-->
        <section id="areaLayer" class="express-area-box">
            <header>
                <h3>选择地区</h3>
                <a id="backUp" class="back" href="javascript:void(0)" title="返回"></a>
                <a id="closeArea" class="close" href="javascript:void(0)" title="关闭"></a>
            </header>
            <article id="areaBox">
                <ul id="areaList" class="area-list"></ul>
            </article>
        </section>
        <!--遮罩层-->
        <div id="areaMask" class="mask"></div>
	</body>
	<script src="<%=basePath%>junlin/js/jquery-1.10.2.min.js"></script>
	<script src="<%=basePath%>junlin/js/phonealert/popups.js"></script><!--弹出框-->
	<script src="<%=basePath%>junlin/js/phoneCity/jquery.area.js"></script><!--三级联动-->
	<script src="<%=basePath%>junlin/script/public.js"></script>
	<script type="text/javascript">
		/*设定弹出框大小*/
		setSize();
        addEventListener('resize',setSize);
        function setSize() {
            document.documentElement.style.fontSize = document.documentElement.clientWidth/750*100+'px';
        }
		var res = parseURL(window.location.href);
		if(res.id!=null){
			console.log(res);
			/*请在下方两种方案选一执行
			 * 1、请根据res传参id查询信息并在此处为页面赋值。
			 * 2、将所需参数全部传过来，他们将全部存储在res内部，请在此处为页面内容赋值。
			 * */
			 var n=0;
			 var resultData
			 $.ajax({
					url: '<%=basePath%>shippingAddress/getShippingAddressByUserId',
					type: "GET",
					dataType: "json",
					async: false,
					data: {
						"userId": res.userId
					},
					success: function(data) {
						if(data.code=200){
							resultData=data.resultData;//收货地址list
							for(;n<resultData.length;n++){
								if(resultData[n].id==res.id)break;
							}
						}else{
							phoneAlert(data.msg)
						}
					}
				});
			 
			$("#user_name").val(resultData[n].consigneeName);
			$("#user_tel").val(resultData[n].consigneeTel);
			//"region" : chooseObj.province.name+chooseObj.city.name+chooseObj.province.name,
			$("#user_deatil").val(resultData[n].detailedAddress);
			$("#default_radio").val(resultData[n].isCommonlyUsed);
			selectP(0);
			var cityname;//区city[0][]
			for(var i=0;i<city[0].length;i++){
				if(resultData[n].countyCode==city[0][i].code){
					cityname=city[0][i].name;
					selectC(0,i)
					break;
				}
			}
			var districtname;
			
			for(var i=0;i<district[0][0].length;i++){
				if(resultData[n].ringCode==district[0][0][i].code){
					districtname=district[0][0][i].name;
					selectD(0,0,i)
					break;
				}
			}
			if(resultData[n].isCommonlyUsed==1){
				$("#default_radio").click();
				change_check(document.getElementById("default_radio")); 
				$('#default_radio').attr('disabled',"true");
				
			}
			chooseObj={province:{code:""+resultData[n].provinceCode,name:"北京市"},city:{code:""+resultData[n].countyCode,name:cityname},district:{code:""+resultData[n].ringCode,name:districtname}};
			 console.log(chooseObj);
			/* "provinceCode": chooseObj.province.code,
			"cityCode" :  "0",
			"countyCode" : chooseObj.city.code,
			"ringCode" : chooseObj.district.code */
		}

		/*保存地址*/
		function save_address() {
			if(confirmation()){
				/*请在此处添加保存地址的代码*/
				/*chooseObj里面是已获取的选中的三级联动的数据
				 * default_radio选中时，value为1，未选中值为0
				 * 其余数据请自行获取
				 console.log(chooseObj);
				 */
				 console.log(chooseObj);
				 
				if(res.id==null){//新增
					var date={
							"userId" : res.userId,
							"consigneeName" : $("#user_name").val(),
							"consigneeTel" : $("#user_tel").val(),
							"region" : chooseObj.province.name+chooseObj.city.name+chooseObj.district.name,
							"detailedAddress" :  $("#user_deatil").val(),
							"isCommonlyUsed" :  $("#default_radio").val(),
							"provinceCode": chooseObj.province.code,
							"cityCode" :  "0",
							"countyCode" : chooseObj.city.code,
							"ringCode" : chooseObj.district.code
							};
					$.ajax({
						url: '<%=basePath%>shippingAddress/insertShippingAddress',
						type: "POST",
						dataType: "json",
						async: false,
						data: date,
						success: function(data) {
							if(data.code=200){
								phoneAlert("保存成功");
								//跳转页面
								window.location.href = "address.jsp?userId="+res.userId+"&isSave=yes"
							}else{
								phoneAlert(data.msg)
							}
						}
					});
				}else{//修改
					var date={
							"id" : res.id,				
							"userId" : res.userId,
							"consigneeName" : $("#user_name").val(),
							"consigneeTel" : $("#user_tel").val(),
							"region" : chooseObj.province.name+chooseObj.city.name+chooseObj.district.name,
							"detailedAddress" :  $("#user_deatil").val(),
							"isCommonlyUsed" :  $("#default_radio").val(),
							"provinceCode": chooseObj.province.code,
							"cityCode" :  "0",
							"countyCode" : chooseObj.city.code,
							"ringCode" : chooseObj.district.code
							};
					$.ajax({
						url: '<%=basePath%>shippingAddress/updateShippingAddressById',
						type: "POST",
						dataType: "json",
						async: false,
						data: date,
						success: function(data) {
							if(data.code=200){
								phoneAlert("保存成功");
								//跳转页面
								window.location.href = "address.jsp?userId="+res.userId+"&isSave=yes"
							}else{
								phoneAlert(data.msg)
							}
						}
					});
				}
			}
		}
		
		/*验证：不能为空*/
		function confirmation(){
			var res=false;
			if($("#user_name").val().length==0){
				phoneAlert("请填写收货人姓名");
			}else if($("#user_name").val().length<2&&$("#user_name").val().length>0){
				phoneAlert("请填写正确的收货人姓名（2-5个字）");
			}else if($("#user_tel").val().length==0){
				phoneAlert("请填写联系方式");
			}else if(!checkMobilePhone($("#user_tel").val()+"")){
				phoneAlert("请填写正确的手机号");
			} else if(chooseObj.district.code==""){
				phoneAlert("请选择所在地区");
			}else if($("#user_deatil").val()==""){
				phoneAlert("请填写详细地址");
			}else if($("#user_deatil").val().length<5||$("#user_deatil").val().length>30){
				phoneAlert("请填写正确的详细地址（5-30个字）");
			}else{
				res=true;
			}
			return res;
		}
		
		
		/*提示框*/
		function phoneAlert(mess){
			jqalert({
                    title:'',
                    content:mess,
                    click_bg:true
                })
		}

		/*radio可选*/
		function change_check(thisinput) {
			var radioCheck = $(thisinput).val();
			if("1" == radioCheck) {
				$(thisinput).attr("checked", false);
				$(thisinput).val("0");
			} else {
				$(thisinput).val("1");
			}

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
		
		/* 检查手机号码是否正确 
		 规则:共11位,第一位为1,第二位为34578
		 */
		function checkMobilePhone(mobilePhone) {
			var re = new RegExp(/^1[34578]\d{9}$/);
			var retu = mobilePhone.match(re);
			if(retu) {
				return true;
			} else {
				return false;
			}
		}
	</script>

</html>