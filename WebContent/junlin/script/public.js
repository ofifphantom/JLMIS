//分割时间
function splitStr(str) {
	var arr = str.split("~");
	arr[0] = trim(arr[0]);
	arr[1] = trim(arr[1]);
	return arr;
}
//删除两端字串
function trim(str) { //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

//去除空格
function cky(obj) {
	var t = obj.value.replace(/(^\s*)|(\s*$)/g, "");
	if(obj.value != t)
		obj.value = t;
}

//去除空格
function cky_obj($obj) {
	var t = $obj.val().replace(/(^\s*)|(\s*$)/g, "");
	if($obj.val() != t)
		$obj.val(t);
}

//清空表单
function clearForm(id, textareastr, url, $tree) {
	$("#" + id + " input").val("");
	$("#" + id + " select").val("-1");
	$("#" + id + " textarea").val(textareastr);
	$("#" + id + " img").attr("src", url + '/junlin/img/photo_icon.jpg');
	$("#" + id + " img").css({
		"height": 90,
		"width": 90
	});
	/*富文本框清空*/
	$("#editor .w-e-text").html("<p>请<b>删除此行</b>并在此处进行编辑。</p>")

	/*还原tree*/
	if($tree != undefined) {
		expandAll($tree);
		uncheckedAll($tree);
	}

}

//清空多张图片
function clearImgs(classname, url) {
	$("." + classname + "").html('<div class="big-photo"><div class="preview" id="preview0">' +
		'<img id="imghead0" border="0" src="' + url + '/junlin/img/photo_icon.jpg" width="90" height="90" onclick="$(\'#previewImg0\').click();" alt="0" >' +
		'</div><input type="file" onchange="previewImage(this,0)" style="display: none;" id="previewImg0" name="previewImg" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg"></div>');

}
//展开tree
function expandAll($tree) {
	var node = $tree.tree('getSelected');
	if(node) {
		$tree.tree('expandAll', node.target);
	} else {
		$tree.tree('expandAll');
	}
}
//设置tree所有为未选中
function uncheckedAll($tree) {

	var node = $tree.tree('getChecked', 'checked');
	$(node).each(function(index, obj) {
		if(obj) {
			$tree.tree('uncheck', obj.target);
		} else {
			$tree.tree('uncheck');
		}
	})

}

//layer
function publicMessageLayer(atext, callFun) {
	layer.confirm("你确定要" + atext + "吗？", {
		  btn: ['确认','取消'] //按钮
		}, function(index){
			callFun();
			layer.close(index);
		}, function(){});
}

function MessageLayer(atext, callFun) {
	layer.confirm(atext, {
		  btn: ['确认','取消'] //按钮
		}, function(index){
			callFun();
			layer.close(index);
		}, function(){
		});
}

//成功
function laysuccess(str) {
	layer.msg(str, {
		icon: 6,
		time: 2000
	});

}
//失败
function layfail(str) {
	layer.msg(str, {
		icon: 5,
		time: 1500
	});

}
//警告
function laywarn(str) {
	layer.msg(str, {
		icon: 0,
		time: 1500
	});

}
//时间插件
function laydateTwo(id, callFun) {
	laydate.render({
		elem: id,
		type: 'date',
		range: '~',
		min: 0,
		format: 'yyyy-MM-dd',
		change: function(value, date, endDate) {
			time = value;
			callFun();
		}
	});
}

//时间插件
function laydateTwo_max0(id, callFun) {
	laydate.render({
		elem: id,
		type: 'date',
		range: '~',
		max: 0,
		format: 'yyyy-MM-dd',
		change: function(value, date, endDate) {
			time = value;
			callFun();
		}
	});
}

//比较字符串类型日期（2017-11-22）
function compareDate(startDate, endDate) {
	var startMonth = startDate.substring(5, startDate.lastIndexOf("-"));
	var startDay = startDate.substring(startDate.length, startDate.lastIndexOf("-") + 1);
	var startYear = startDate.substring(0, startDate.indexOf("-"));

	var endMonth = endDate.substring(5, endDate.lastIndexOf("-"));
	var endDay = endDate.substring(endDate.length, endDate.lastIndexOf("-") + 1);
	var endYear = endDate.substring(0, endDate.indexOf("-"));

	if(Date.parse(startMonth + "/" + startDay + "/" + startYear) >
		Date.parse(endMonth + "/" + endDay + "/" + endYear)) {
		return false;
	}
	return true;
}

//全选全不选
function checkboxAll(checkall) {
	$(checkall).click(function() {
		if(this.checked) {
			$("#datatable :checkbox").prop("checked", true);
		} else {
			$("#datatable :checkbox").prop("checked", false);
		}
	});
	$("#datatable input[type=checkbox]").click(function() {
		if($("#datatable input:checkbox:checked").length == $("#datatable input:checkbox").length) {
			$(checkall).prop("checked", true);
		} else {
			$(checkall).prop("checked", false);
		}
	})
}

function checkboxController(thischeckbox) {
	if(thischeckbox.checked) {
		$("#datatable :checkbox").prop("checked", true);
	} else {
		$("#datatable :checkbox").prop("checked", false);
	}
}

function checkboxClick(checkall) {
	if($("#datatable input:checkbox:checked").length == $("#datatable input:checkbox").length) {
		$(checkall).prop("checked", true);
	} else {
		$(checkall).prop("checked", false);
	}
}

function clearSearchableSelect(idstr){
	/*console.log(getParentdElement(document.querySelector("#"+idstr), "searchable-select-item"))*/
	console.log(idstr,document.getElementById(idstr));
	var fireOnThis =getNextNode(document.getElementById(idstr)).getElementsByClassName("searchable-select-item")[0];
    var evObj = document.createEvent('MouseEvents');
    evObj.initMouseEvent('click',true,true);
    fireOnThis.dispatchEvent(evObj);
}

//带搜索的select框的默认选择
function SearchableSelectsetValue(selector, value) {
	/*给select赋值*/
	$(selector).val(value);
	/*给插件赋值*/
	let $parent = $(selector).next();
	let $items = $parent.find(".searchable-select-items")
	let $ain_node = $parent.find(".searchable-select-item[data-value='" + value + "']");

	$parent.find(".searchable-select-holder").html($ain_node.html());
	$parent.find(".searchable-select-item").removeClass("selected");
	$ain_node.addClass("selected");

}

/*兼容nextSibling*/
function getNextNode(node){
    node=typeof node=="string"?document.getElementById(node):node;
    var nextNode=node.nextSibling;
    if(!nextNode)return null;
    if(!document.all){  //FF不识别document.all
        while(true){
            if(nextNode.nodeType==1){
                break;
            }else{
                if(nextNode.nextSibling){
                    nextNode=nextNode.nextSibling;
                }else{
                    break;
                }
            }
        }
    }
    return nextNode;
}


/*正则验证*/
/*是否为空*/
function isnotEmpty(str) {
	str = trim(str);
	if(str == "" || str == undefined || str === "") {
		return false;
	} else {
		return true;
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
/* 检查密码是否正确 
 规则:共8-16位,字母数字下划线组合(字母大小写不限)
 验证安全级别:var reg=/^(?=.{8,16})(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*_).*$/;
 */
function checkPassword(pwd) {
	var re = new RegExp(/^[0-9a-zA-Z_]{8,16}$/);
	var retu = pwd.match(re);
	if(retu) {
		return true;
	} else {
		return false;
	}
}
/* 检查用户名是否正确 
 规则:共2-20个,汉字
 */
function checkChinese(username) {
	var re = new RegExp(/^[\u4e00-\u9fa5]{2,20}$/);
	var retu = username.match(re);
	if(retu) {
		return true;
	} else {
		return false;
	}
}

/*
 * jquery方法
 * 只能输入整数，不能输入小数
 * 请使用onkeyup()调用
 */
function pressInteger(ob) {
	ob.value = ob.value.replace(/[^\d]/g, ""); //清除"数字"以外的字符
}
/* 
 * jquery方法
 * 可以输入数值以及小数点小数点后只能输入两位
 * 请使用onkeyup()调用
 * 传入参数
 */
function pressMoney(ob) {
	ob.value = ob.value.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
	ob.value = ob.value.replace(/^\./g, ""); //验证第一个字符是数字
	ob.value = ob.value.replace(/\.{2,}/g, "."); //只保留第一个, 清除多余的
	ob.value = ob.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	ob.value = ob.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
	if(ob.value-0>=10000000){
		laywarn("价格过大");
		ob.value ="";
	}
}

/* 
 * jquery方法
 * 可以输入小于1的数值以及小数点小数点后只能输入两位
 * 请使用onkeyup()调用
 * 传入参数
 */
function pressSmallNum(ob) {
	ob.value = ob.value.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
	ob.value = ob.value.replace(/^\d/gi, "0."); //验证第一个字符是数字
	ob.value = ob.value.replace(/\.{2,}/g, "."); //只保留第一个, 清除多余的
	ob.value = ob.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	ob.value = ob.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
}