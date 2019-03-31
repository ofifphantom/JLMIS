Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

$.ajaxSetup({
			complete : function(XMLHttpRequest, textStatus) {
				if (textStatus == "parsererror") {
					layer.alert("登陆超时！请重新登陆！", {
								skin : 'layui-layer-lan' // 样式类名
								,
								closeBtn : 0
							}, function() {
								window.location.href = '/Property/login.jsp';
							});
				} 
			}
		})

/* 检查手机号码是否正确 */
function checkMobilePhone(mobilePhone) {
	var re = new RegExp(/^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/);
	var retu = mobilePhone.match(re);
	if (retu) {
		return true;
	} else {
		return false;
	}

}

/* 检查电话是否正确 */
function checkTelephone(Telephone) {
	var re = new RegExp(/^([0-9]{3,4}-)?[0-9]{7,8}$/);
	var retu = Telephone.match(re);
	if (retu) {
		return true;
	} else {
		return false;
	}
}

/* 检查身份号是否正确 */
function checkIDCard(IDCard) {
	var re1 = new RegExp(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/);
	var re2 = new RegExp(/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/);
	var retu1 = IDCard.match(re1);
	var retu2 = IDCard.match(re2);
	if (retu1 || retu2) {
		return true;
	} else {
		return false;
	}
}
