/**
 * jquery.area.js
 * 移动端省市区三级联动选择插件（北京市）（可扩展全国）
 * author: 赵小昕
 * date: 2018-01-31
**/

/*定义三级省市区数据*/
var province = [{code:"110000",name:"北京市"}];

var city = 		[
					[
					{code:"110101",name:"东城区"},
					{code:"110102",name:"西城区"}, 
					{code:"110105",name:"朝阳区"}, 
					{code:"110106",name:"丰台区"},
					{code:"110107",name:"石景山区"},
					{code:"110108",name:"海淀区"},
					{code:"110109",name:"门头沟区"},
					{code:"110111",name:"房山区"},
					{code:"110112",name:"通州区"},
					{code:"110113",name:"顺义区"},
					{code:"110114",name:"昌平区"},
					{code:"110115",name:"大兴区"},
					{code:"110117",name:"平谷区"},
					{code:"110116",name:"怀柔区"},
					{code:"110118",name:"密云区"},
					{code:"110119",name:"延庆区"}
					]

				];
var district = 	[
					[
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					[{code:"2",name:"二环"},{code:"3",name:"三环"},{code:"4",name:"四环"},{code:"5",name:"五环"},{code:"6",name:"六环"},{code:"7",name:"七环"}],
					]
					
				];
var expressArea, areaCont, areaList = $("#areaList"), areaTop = areaList.offset().top;
var chooseObj={province:{code:"",name:""},city:{code:"",name:""},district:{code:"",name:""}};

/*初始化省份*/
function intProvince() {
	areaCont = "";
	for (var i=0; i<province.length; i++) {
		areaCont += '<li  data-code="'+province[i]["code"]+'" onClick="selectP(' + i + ');">' + province[i]["name"] + '</li>';
	}
	areaList.html(areaCont);
	$("#areaBox").scrollTop(0);
	$("#backUp").removeAttr("onClick").hide();
}
intProvince();

/*选择省份*/
function selectP(p) {
	areaCont = "";
	areaList.html("");
	for (var j=0; j<city[p].length; j++) {
		areaCont += '<li data-code="'+city[p][j]["code"]+'" onClick="selectC(' + p + ',' + j + ');">' + city[p][j]["name"] + '</li>';
	}
	areaList.html(areaCont);
	$("#areaBox").scrollTop(0);
	expressArea = province[p]["name"]  + " > ";
	chooseObj.province.code=province[p]["code"];
	chooseObj.province.name=province[p]["name"];
	$("#backUp").attr("onClick", "intProvince();").show();
}

/*选择城市*/
function selectC(p,c) {
	areaCont = "";
	for (var k=0; k<district[p][c].length; k++) {
		areaCont += '<li  data-code="'+district[p][c][k]["code"]+'" onClick="selectD(' + p + ',' + c + ',' + k + ');">' + district[p][c][k]["name"] + '</li>';
	}
	areaList.html(areaCont);
	$("#areaBox").scrollTop(0);
	var sCity = city[p][c]["name"] ;
	chooseObj.city.name=city[p][c]["name"];
	chooseObj.city.code=city[p][c]["code"];
	if (sCity != "省直辖县级行政单位") {
		if (sCity == "东莞市" || sCity == "中山市" || sCity == "儋州市" || sCity == "嘉峪关市") {
			expressArea += sCity;
			$("#expressArea dl dd").html(expressArea);
			clockArea();
		} else if (sCity == "市辖区" || sCity == "市辖县" || sCity == "香港岛" || sCity == "九龙半岛" || sCity == "新界" || sCity == "澳门半岛" || sCity == "离岛" || sCity == "无堂区划分区域") {
			expressArea += "";
		} else {
			expressArea += sCity + " > ";
		}
	}
	$("#backUp").attr("onClick", "selectP(" + p + ");");
}

/*选择区县*/
function selectD(p,c,d) {
	clockArea();
	expressArea += district[p][c][d]["name"] ;
	chooseObj.district.name=district[p][c][d]["name"];
	chooseObj.district.code=district[p][c][d]["code"];
	$("#expressArea dl dd").html(expressArea);
}

/*关闭省市区选项*/
function clockArea() {
	$("#areaMask").fadeOut();
	$("#areaLayer").animate({"bottom": "-100%"});
	intProvince();
}

$(function() {
	/*打开省市区选项*/
	$("#expressArea").click(function() {
		$("#areaMask").fadeIn();
		$("#areaLayer").animate({"bottom": 0});
	});
	/*关闭省市区选项*/
	$("#areaMask, #closeArea").click(function() {
		clockArea();
	});
});