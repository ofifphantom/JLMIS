(function($) {
	$.menu = {
		jsonWhere : function(data, action) {
			if (action == null)
				return;
			var reval = new Array();
			$(data).each(function(i, v) {
						if (action(v)) {
							reval.push(v);
						}
					});

			return reval;
		},
		loadMenu : function() {
			var data = [];
			// 获取用户的菜单信息
			$.ajax({
						url : contextPath + "/getMenus",
						type : "get",
						dataType : "json",
						async : false,
						success : function(result) {
							data = result;
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {

						}
					});
			var _html = "";
			$.each(data, function(i) {

				var row = data[i];

				if (row.pid == "0") {

					_html += '<li>';
					var childNodes = $.menu.jsonWhere(data, function(v) {
								return v.pid == row.id
							});
					if (childNodes.length > 0) {
						_html += '<a href="#" class="dropdown-toggle">';
						_html += '<i class="'
								+ row.icon
								+ '"></i><span class="menu-text">'
								+ row.title
								+ '</span><b class="arrow icon-angle-down"></b>';
						_html += '</a>';
						_html += ' <ul class="submenu">';
						$.each(childNodes, function(i) {
							var subRow = childNodes[i];
							var subchildNodes = $.menu.jsonWhere(data,
									function(v) {
										return v.pid == subRow.id
									});
							_html += '<li class="home">';
							if (subchildNodes.length > 0) {
								_html += '<a href="#" class="dropdown-toggle">';
								_html += '<i class="'
										+ subRow.icon
										+ '"></i><span class="menu-text">'
										+ subRow.title
										+ '</span><b class="arrow icon-angle-down"></b>';
								_html += '</a>';
								_html += '<ul class="submenu">';
								$.each(subchildNodes, function(i) {
									var subchildNodeRow = subchildNodes[i];
									_html += '<li class="home">';
									_html += '<a href="javascript:void(0)" class="iframeurl" name="'
											+ contextPath
											+ subchildNodeRow.url
											+ '" title="'
											+ subchildNodeRow.title
											+ '"><i class="icon-double-angle-right"></i>'
											+ subchildNodeRow.title + '</a>';
									_html += '</li>';
								});
								_html += '</ul>';
							} else {
								_html += '<a href="javascript:void(0)" class="iframeurl" name="'
										+ contextPath
										+ subRow.url
										+ '" title="'
										+ subRow.title
										+ '"><i class="icon-double-angle-right"></i>'
										+ subRow.title + '</a>';
							}
							_html += '</li>';
						});
						_html += '</ul>';
					} else {
						_html += '<a href="javascript:void(0)" name="'
								+ contextPath + row.url
								+ '" class="iframeurl" title="' + row.title
								+ '"><i class="' + row.icon
								+ '"></i><span class="menu-text">' + row.title
								+ '</span></a>';
					}
					_html += '</li>';
				}
			});

			$("#nav_list").append(_html);
		}
	};
	$(function() {
				$.menu.loadMenu();

			})
})(jQuery);