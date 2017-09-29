function ft() {
	var cityId = $("#citity").val();
	var temp = map.get(cityId).split(",");
	for (var i = 0; i < temp.length; i++) {
		var str = cityId + "_" + temp[i];
		$("#" + str).show();//("display", "block");
	}
	// 隐藏其他的
	if (map != null) {
		var keys = map.keySet();
		for (i in keys) {
			key = keys[i];
			if (key != cityId) {
				if (typeof (map.get(key)) != "undefined") {
					var mt = map.get(key).split(",");
					for (var i = 0; i < mt.length; i++) {
						var str = key + "_" + mt[i];
						$("#" + str).hide();//("display", "none");
					}
				}
			}
		}
	}
}

function dyncProduct() {
	var cityId = $("#citity").val();
	if (cityId.length > 0) {
		jQuery.ajax({
			url : '/order/dyncProduct',
			type : "post",
			data : {
				"cityId" : cityId
			},
			dataType : "json",
			success : function(data, textStatus) {
				alert(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
}

function reset(backurl) {
	window.location.href = backurl;
}

function displayAerea() {
	if ($('#cityArea li').is(':hidden')) {
		$('#cityArea li').show();
	}
}

function validateCode() {
	var phoneNum = $("#phoneNum").val();
	if (phoneNum.length > 0) {
		jQuery
				.ajax({
					url : '/order/historyByphone',
					type : "post",
					data : {
						"phoneNum" : phoneNum
					},
					dataType : "text",
					success : function(data, textStatus) {
						if (data != "#") {
							var mmt = data.split("#");
							var userInfo = mmt[1];
							if (userInfo != "") {
								var tmp = userInfo.split(";");
								$("#kehname").val(tmp[0]);
								$("#tongydz").val(tmp[1]);
								if (tmp[2] != "") {
									/*
									 * var tt = $('[name="nice-select2"]
									 * li[data-value="' + tmp[2] +
									 * '_1"]').text(); alert(tt);
									 * $('[name="nice-select2"] li[data-value="' +
									 * tmp[2] +
									 * '_1"]').parents('[name="nice-select2"]').find('input').val(tt);
									 * $('[name="nice-select2"] ul').hide();
									 */
									$("#initchannel").val(tmp[2]);
									$("#initchannel").attr("readonly","readonly");
									$("#kehchannel").val(tmp[6]);
								}
								$("#kehzj").val(tmp[3]);
								$("#email").val(tmp[4]);
								// $("#gongsmc").val(tmp[5]);
								$("#tongxdz").val(tmp[7]);
								var suosgs = tmp[8];
								if (suosgs != "") {
									var mtp = suosgs.split("&");
									var htmlstr = "<ul>"
									for (var i = 0; i < mtp.length; i++) {
										var mm = mtp[i];
										var ttr = mm.split(",");
										htmlstr = htmlstr
												+ "<li name=\"suosgs\" data-value=\""
												+ ttr[0] + "_" + ttr[1] + "\">"
												+ ttr[1] + "</li>";
									}
									htmlstr = htmlstr + "</ul>";
									$("#glsuosgs").html(htmlstr);
								}
							} else {
								$("#kehname").val("");
								$("#tongydz").val("");
								$("#initchannel").val("请选择客户来源");
								$("#kehzj").val("");
								$("#email").val("");
								$("#gongsmc").val("");
								$("#tongxdz").val("");
								$("#dissuosgs").val("请选择所属公司");
								$("#hiddissuosgs").val("");
								$("#glsuosgs")
										.html(
												"<ul><li data-value=\"_新建公司\" name=\"suosgs\">新建公司</li></ul>");

							}
							var datas = mmt[0];
							if (datas != "") {
								var st = datas.split(";");
								var htmlStr = "";
								if (st.length > 0) {
									for (var i = 0; i < st.length; i++) {
										var mt = st[i];
										var temp = mt.split("|");
										var fivePostion = "";
										if (temp[5] == null) {
											fivePostion = "";
										} else {
											fivePostion = temp[5];
										}
										htmlStr = htmlStr
												+ " <tr><td>"
												+ temp[0]
												+ "</td><td>"
												+ temp[1]
												+ "</td><td>"
												+ temp[2]
												+ "</td><td>"
												+ temp[3]
												+ "</td><td><span class=\"red\">"
												+ temp[4]
												+ "元</span></td><td>"
												+ fivePostion
												+ "</td><td><span class=\"green\"> <a href='/order/modifyOrder/"
												+ temp[0] + ".html'>" + temp[6]
												+ "</a></span></td></tr>";
									}
								}
								$("#history").append(htmlStr);
							} else {
								$("#history tr:gt(0)").remove();
							}
						} else {
							$("#history tr:gt(0)").remove();
							$("#kehname").val("");
							$("#tongydz").val("");
							$("#kehchannl").val("");
							$("#initchannel").val("请选择客户来源");
							$("#kehzj").val("");
							$("#email").val("");
							$("#gongsmc").val("");
							$("#tongxdz").val("");
							$("#dissuosgs").val("请选择所属公司");
							$("#hiddissuosgs").val("");
							$("#glsuosgs")
									.html(
											"<ul><li data-value=\"_新建公司\" name=\"suosgs\">新建公司</li></ul>");
						}
						initEvent();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
					}
				});
	}
}

var clickcount = 0;// 控制提交多次
function submitForm() {
	var phoneNum = $("#phoneNum").val();
	if (phoneNum.length == "") {
		alert("请输入客户手机号！！");
		return false;
	} else {
		// 手机号验证
		if (!(/^1[3|4|5|7|8][0-9]\d{8}$/.test(phoneNum))) {
			alert("输入的手机号格式不对，请核对后再次输入！！");
			return false;
		} else {
			if (phoneNum.length != 11) {
				alert("输入的手机号位数不对，请核对后再次输入！！");
				return false;
			}
		}
	}
	// 所属公司
	var shgs = $("#dissuosgs").val();
	if (shgs == null || shgs == "" || shgs == "请选择所属公司") {
		alert("请输入所属公司！！");
		return false;
	}
	// 客户来源必输项
	var kehchann = $("#kehchannel").val();
	var terraceOrderId = $("#terraceOrderId").val();
	if (kehchann.length == "") {
		alert("请输入客来源！！");
		return false;
	}else{
		if(kehchann == 25 && terraceOrderId == ""){
			alert("请输入平台订单编号！！");
			return false;
		}
	}
	// 注册区域必填项
	var citity = $("#citity").val();
	if (citity.length < 0) {
		alert("请输入注册区域所在的城市！！");
		return false;
	}
	var citityarea = $("#citityarea").val();
	if (citityarea.length < 0) {
		alert("请输入注册区域所在的城市的地区！！");
		return false;
	}
	// 迭代array
	// 格式 :商品id_类别多个以','分割(注：整体多个以‘|’分割)
	var selectProduct = "";
	var map = new Map();
	if (products.length > 0) {
		for (var i = 0; i < products.length; i++) {
			var temp = products[i];
			var tempstr = temp.split("_");
			var proId = tempstr[0];
			var cateId = tempstr[1];
			if (!map.containsKey(proId)) {
				if (typeof (map.get(proId)) != "undefined") {
					map.put(proId, map.get(proId) + "," + cateId);
				} else {
					map.put(proId, cateId);
				}
			} else {
				map.put(proId, cateId);
			}
		}
	}
	// 迭代map
	if (map != null) {
		var keys = map.keySet();
		for (i in keys) {
			var key = keys[i];
			var val = map.get(key);
			if (typeof (val) != "undefined") {
				selectProduct = selectProduct + key + "_" + val + ";";
			}
		}
	}
	if (selectProduct == "") {
		alert("请选择要提交的商品信息！！");
		return false;
	}
	if (clickcount != 0) {
		return false;
	}
	clickcount++;
	$("#selectProduct").val(selectProduct);
	document.getElementById('myForm').submit();
}

function dyncArea(divide) {
	var parentId = "";
	if (divide == 0) {
		parentId = $("#area li:first").attr("data-value");
	} else {
		parentId = $("#citity").val();
	}
	ft();
	if (parentId.length > 0) {
		jQuery.ajax({
			url : '/order/dyncmicarea',
			type : "post",
			data : {
				"parentId" : parentId
			},
			dataType : "json",
			success : function(data, textStatus) {
				if (data != "") {
					var htmlstr = "";
					var arid = "101"
					for (var i = 0; i < data.length; i++) {
						htmlstr = htmlstr + "<li onclick=\"clickLi("
								+ data[i].AreasEntity.areaid
								+ ");\" name=\"area\" data-value=\""
								+ data[i].AreasEntity.areaid + "\">"
								+ data[i].AreasEntity.name + "</li>";
					}
					if (divide == 0) {
						// 默认 北京朝阳区
						$("#initarea").val("朝阳区");
						$("#citityarea").val("101");
					} else {
						$("#initarea").val(data[0].AreasEntity.name);
						$("#citityarea").val(data[0].AreasEntity.areaid);
					}
				}
				$("#cityArea").html(htmlstr);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
}

function clickLi(areaId) {
	var tt = $('[name="nice-select2"] li[data-value="' + areaId + '"]').text();
	var mt = $('[name="nice-select2"] li[data-value="' + areaId + '"]')
			.parents('[name="nice-select2"]').find('input').val(tt);
	$('#cityArea li').hide();
	// alert($('[name="nice-select2"] ul'));
	$('[name="nice-select2"] ul').hide();
	$("#citityarea").val(areaId);
}

function init(map) {
	map.put("1", "0,1,2,3");
	map.put("2", "4,5,6,7");
}