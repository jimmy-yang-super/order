function intmap(map){
	//公司注册
	map.put("1001","0");
	map.put("1002","1");
	
	//map.put("1007","2");   去除企业年报
	map.put("1008","2");
	map.put("1010","3");
	map.put("1011","4");
	map.put("1012","5");
	map.put("1013","6");
	map.put("1014","7");
	//表示其他
	map.put("10013","25");
	
	
	//代理记账
	map.put("1003","0");
	map.put("1004","1");
	map.put("1005","2");
	map.put("1015","3");
	
	map.put("4002","0");
	//map.put("4006","1");

	map.put("4001","0");
	map.put("4003","1");
	map.put("4004","2");
	map.put("10014","3");
	map.put("4005","4");
	
	map.put("10015","5");
	map.put("10016","10");

	//公司变更   -- 2015-09-11
	map.put("2005","4");
	map.put("2006","5");
	map.put("2007","6");
	map.put("2008","7");
	map.put("2011","8");
	map.put("2013","9");	
	map.put("2014","10");	
	map.put("2015","11");	
	map.put("2016","12");	
	map.put("2017","13");	
	map.put("2018","14");	
	map.put("2019","15");	
	map.put("2020","16");	
	map.put("20021","17");

	map.put("30017","20");
	
	map.put("北京","1");
	map.put("深圳","2");
	
	map.put("东城区","103");
	map.put("西城区","104");
	map.put("朝阳区","101");
	map.put("海淀区","102");
	map.put("丰台区","105");
	map.put("石景山区","106");
	map.put("通州区","107");
	map.put("昌平区","108");
	map.put("门头沟区","109");
	map.put("房山区","111");
	map.put("崇文区","112");
	map.put("顺义区","113");
	map.put("宣武区","114");
	map.put("大兴区","115");
	map.put("怀柔区","116");
	map.put("平谷区","117");
	
	map.put("福田区","201");
	map.put("罗湖区","202");
	map.put("南山区","203");
	map.put("宝安区","204");
	map.put("龙岗区","205");
	map.put("盐田区","206");
	
	map.put("光明新区","207");
	map.put("龙华新区","208");
	map.put("坪山新区","209");
	map.put("大鹏新区","210");
	map.put("前海","211");
}
function initpage(orderChildinfo, productId, divide) {
	var temp = orderChildinfo.split("_");
	var orderChildId = temp[0];
	var cateId = temp[1];
	var val = productId + "_" + cateId;
	var f = "";
	if (productId != "1104" && productId != "2203") {
		f = map.get(cateId);
		val = val + "_" + f;
	} else {
		if (typeof (map.get(cateId)) != "undefined") {
			divide = map.get(cateId);
		}
		val = val + "_" + divide;
	}
	$("input:checkbox[value='" + val + "']").attr('checked', 'true');
	var tm = "";
	var tmp = "";
	if (temp[2] == "qit") {
		//表示其他
		if (temp.length > 5) {
			if (productId == "1101" || productId =="2201") {
				tmp = "1";
			}
			if (productId == "1102") {
				tmp = "4";
			}
			if (productId == "1104" || productId == "2203") {
				tmp = "5";
			}
			if (productId == "1105" || productId =="2202") {
				tmp = "2";
			}
			tm = "_" + tmp;
			var mc = temp[3];
			if (mc == null) {
				mc = "";
			}
			var mcdesc = temp[4];
			if (mcdesc == null) {
				mcdesc = "";
			}
			var price = temp[5];
			qitmcDisplay(productId, mc, mcdesc, price);
			val = productId + "_" + cateId;
		}
	}
	$("input:checkbox[value='" + val + tm + "']").attr('checked', 'true');
	var property = temp[2];
	if (property.indexOf("[") > -1) { 
		property = property.replace("[", "").replace("]", "");
		var tem = property.split(",");
		if (productId != "1104" && productId !="2203") {
			for (var i = 0; i < tem.length; i++) {
				var tm = cateId + "_" + tem[i];
				var tt = $('[name="nice-select2"] li[data-value="' + tm + '"]').text();
				$('[name="nice-select2"] li[data-value="' + tm + '"]').parents('[name="nice-select2"]').find("input[type='text']").val(tt);
				$('[name="nice-select2"] li[data-value="' + tm + '"]').parents('[name="nice-select2"]').find("input[type='hidden']").val(tem[i]);
				$('[name="nice-select2"] ul').hide();
			}
			dyncprice(productId,orderChildId,f);
		} else {
			for (var i = 0; i < tem.length; i++) {
				var tm = cateId + "_" + tem[i] + "_" + divide;
				var tt = $('[name="nice-select2"] li[data-value="' + tm+ '"]:first').text();
				$('[name="nice-select2"] li[data-value="' + tm + '"]:first').parents('[name="nice-select2"]').find('input').val(tt);
				$('[name="nice-select2"] ul').hide();
			}
			dyncprice(productId,orderChildId,divide);
			var t = "";
			if (divide != 0) {
				t = divide;
			}
			if(temp[3] == ""){
				$("#bigsl" + t).val(1);
			}else{
				$("#bigsl" + t).val(temp[3]);
			}
			if(temp[4] == ""){
				$("#smallsl" + t).val(0);
			}else{
				$("#smallsl" + t).val(temp[4]);
			}
		}
	} else {
		var tt1 = cateId + "_" + property;
		var tt = $('[name="nice-select2"] li[data-value="' + tt1 + '"]').text();
		$('[name="nice-select2"] li[data-value="' + tt1 + '"]').parents('[name="nice-select2"]').find('input').val(tt);
		$('[name="nice-select2"] ul').hide();
	}
}

function qitmcDisplay(productId, mc, mcdesc, price) {
	if (productId == "1101" || productId =="2201") {
		$("#qitmc").val(mc);
		$("#qitcontent").val(mcdesc);
		$("#qitprice").val(price);
		if(price == ""){
			$("#qitprice_25").text(0);
		}else{
			$("#qitprice_25").text(price);
		}
	}
	if (productId == "1102") {
		$("#biangqitmc").val(mc);
		$("#biangqitcontent").val(mcdesc);
		$("#biangqitprice").val(price);
		if(price == ""){
			$("#biangprice_17").text(0);
		}else{
			$("#biangprice_17").text(price);
		}
	}
	if (productId == "1105" || productId =="2202") {
		$("#dlqitmc").val(mc);
		$("#dlqitcontent").val(mcdesc);
		$("#dlqitprice").val(price);
		if(price == ""){
			$("#dlprice_10").text(0);
		}else{
			$("#dlprice_10").text(price);
		}
	}
	if (productId == "1104" || productId == "2203") {
		$("#tradeqitmc").val(mc);
		$("#tradeqitcontent").val(mcdesc);
		$("#tradeqitprice").val(price);
		if(price == ""){
			$("#trandeqit_20").text(0);
		}else{
			$("#trandeqit_20").text(price);
		}
	}
}


function dyncprice(productId,orderChildId,tm){
	var mt ="";
	if(tm != 0){
		mt = tm;
	}
	jQuery.ajax({
		url : '/order/dyncprice',
		type : "post",
		data : {
			"orderChildId" : orderChildId
		},
		dataType : "text",
		success : function(data, textStatus) {
			if(data != ""){
				if(productId == "1101" || productId =="2201"){
					$("#price_"+tm).text(data);
					$("#zcprice_"+tm).val(data);
				}
				if(productId == "1105" || productId =="2202"){
					$("#dlprice_"+tm).text(data);
					$("#dljzprice_"+tm).val(data);
				}
				if(productId =="1102"){
					$("#biangprice_"+tm).text(data);
					$("#biangmt_"+tm).val(data);
				}
				if(productId == "1104" || productId == "2203"){
					$("#gstrande"+mt).text(data);
					$("#gstrandezc"+mt).val(data);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}


function dyqitprice(onfield,chanfield,checkboxid){
	var pri = $("#"+onfield).val();
	var productId = $("#productId").val();
	clickcheckbox(checkboxid);
	if(isNaN(pri)){
		alert("请输入合法的数字字符！！");
		return false;
	}else{
		if(pri == ""){
			pri = 0;
		}
		$("#"+chanfield).text(pri);
		if(productId ==  '1102'){
			bianGenprice(productId);
		}else{
			sumPrice(productId);
		}
	}
}


function clickcheckbox(thisa){
	var productId =  $("#productId").val();
	var flag = $("input:checkbox[value='"+thisa+"']").attr('checked');
	var tmp = thisa.split("_");
	var mt = tmp[2];
	var cateId = tmp[1];
	if(flag !="checked"){
		if(productId == "1104" || productId == "2203"){
			var tm ="";
			if(mt != "0"){
				tm = mt;
			}
			if(mt == "20"){
				//其他
				$("#trandeqit_20").text(0);
			}else{
				$("#gstrande"+tm).text(0);
				$("#gstrandezc"+tm).val(0);
				$("#bigsl"+tm).val("");
				$("#smallsl"+tm).val("");
			}
		}
		if(productId == "1101" || productId =="2201"){
			if(mt == "25"){
				//其他
				$("#qitprice_25").text(0);
			}else{
				if(cateId == "1007"){
					$("#zcgongsgmnb").val("");
				}
				if(cateId == "1008"){
					$("#zcgongsgmgsmchz").val("");
				}
				$("#price_"+mt).text(0);
				$("#zcprice_"+mt).val(0);
			}
		}
		if(productId == "1102"){
			//公司变更
			if(mt == "17"){
				//其他
				$("#biangprice_17").text(0);
			}else{
				$("#biangprice_"+mt).text(0);
				$("#biangmt_"+mt).val(0);
			}
		}
		if(productId == "1105" || productId =="2202"){
			//代理记账
			if(mt == "10"){
				//其他
				$("#dlprice_10").text(0);
			}else{
				$("#dlprice_"+mt).text(0);
				$("#dljzprice_"+mt).val(0);
			}
		}
	}else{
		if(productId == "1104" || productId == "2203"){
			var tm ="";
			if(mt != "0"){
				tm = mt;
			}
			if(mt == "20"){
				var price = $("#tradeqitprice").val();
				//其他
				if(price == ""){
					price = 0;
				}
				$("#trandeqit_20").text(price);
			}else{
				var cityVal = $("#rradecity"+tm).val();
				var bigsl = $("#bigsl"+tm).val();
				if(bigsl == ""){
					$("#bigsl"+tm).val(1);
				}
				var smallsl = $("#smallsl"+tm).val();
				if(smallsl == ""){
					$("#smallsl"+tm).val(0);
				}
				//商标同步价格
				dynact(cateId,"",mt,cityVal);
			}
		}
		if(productId == "1101" || productId =="2201"){
			if(mt == "25"){
				var pri = $("#qitprice").val();
				//其他
				if(pri == ""){
					pri = 0;
				}
				$("#qitprice_25").text(pri);
			}else{
				 var dataVal = $("#zccitityarea_"+mt).val();
				  var cityVal = $("#zccity_"+mt).val();
				  var gongsmc = $("#gongsmc").val();
				  if(cateId == "1007"){
						$("#zcgongsgmnb").val(gongsmc);
					}
					if(cateId == "1008"){
						$("#zcgongsgmgsmchz").val(gongsmc);
					}
				  //公司注册同步价格
				  dynact(cateId,dataVal,mt,cityVal);
			}
		}
		if(productId == "1102"){
			//公司变更
			if(mt == "17"){
				var pri = $("#biangqitprice").val();
				//其他
				if(pri == ""){
					pri = 0;
				}
				$("#biangprice_17").text(pri);
			}else{
				var dataVal = $("#biangcitityarea_"+mt).val();
				var cityVal = $("#gongsadecity_"+mt).val();
				//公司变更同步价格
				//dynact(cateId,dataVal,mt,cityVal);
				 biangyw(cateId,dataVal,mt,cityVal);
			}
		}
		if(productId == "1105" || productId =="2202"){
			//代理记账
			if(mt == "10"){
				var pri = $("#dlqitprice").val();
				//其他
				if(pri == ""){
					pri = 0;
				}
				$("#dlprice_10").text(pri);
			}else{
			  var dataVal = $("#dljzcitityarea_"+mt).val();
			  var cityVal = $("#dljzcity_"+mt).val();
			  //代理记账同步价格
			  dynact(cateId,dataVal,mt,cityVal);
			}
		}
	}
	if(productId == '1102'){
		bianGenprice(productId);
	}else{
		sumPrice(productId);
	}
}

function tradecount(type,tm,cateId){
	var mt ="";
	if(tm !=0){
		mt = tm;
	}
	if(type ="b"){
	 var bgsl =  $("#bigsl"+mt).val();
	 if(isNaN(bgsl)){
			alert("请输入合法的数字字符！！");
			return false;
		}else{
			 var cityVal = $("#rradecity"+mt).val();
			  //商标同步价格
			  dynact(cateId,"",tm,cityVal);
		}
	}
    if(type ="s"){
    	 var bgsl =  $("#smallsl"+mt).val();
    	 if(isNaN(bgsl)){
 			alert("请输入合法的数字字符！！");
 			return false;
 		}else{
 			 var cityVal = $("#rradecity"+mt).val();
 			  //商标同步价格
 			  dynact(cateId,"",tm,cityVal);
 		}
	}
}
function biangyw(cateId,dataVal,tm,cityVal){
	var productId = $("#productId").val();
	var bgqyxs = $("#biangqyxs_"+tm).val(); 
	//获得属性集合
	var perties ="";
	if(bgqyxs != null && bgqyxs != null){
		perties = bgqyxs;
	}
	
	jQuery.ajax({
		url : '/order/dyChangeprice',
		type : "post",
		data : {
			"productId" : productId,
			"localcity":dataVal,
			"cateId":cateId,
			"perties":perties,
			"cityId":cityVal
		},
		dataType : "text",
		success : function(data, textStatus) {
			if(data != ""){
				if(productId =="1102"){
					$("#biangprice_"+tm).text(data);
					$("#biangmt_"+tm).val(data);
				}
				//改变 应付款
				bianGenprice(productId);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	
}

function bianGenprice(productId){
	var price = 0;
	var tempprice =0;
	var other = 0;
	var shifj = 0;
	var qtp =0;
	
	var i = 0;
	$('input[name="selectProduct"]:checked').each(function() {
		
		var temp = $(this).val();
		var tp = temp.split("_");
		var tm = tp[2];
		var caId =  tp[1];
		if(productId =="1102"){
			if(tm =="17"){
				//其他
				var qitpri = $("#biangprice_17").text();
				if(qitpri == ""){
					qitpri = 0;
				}
				qtp = parseInt(qitpri);
				price = price + parseInt(qitpri);
				i++;
			}else{
				var pri =  $("#biangprice_"+tm).text();
				if(caId =='2013' || caId =='2014'){
					other = other + parseInt(pri);
				}else{
					var tprice = parseInt(pri);
					if(tempprice < tprice){
						tempprice = tprice;
					}
					i++;
				}
				price = price + parseInt(pri);
			}
		}
		
	});
	shifj = other + tempprice +qtp;
	if(i >= 4){
		price = price + 300;
		shifj = shifj + 300;
	}
	$("#orderprice").text(price);
	$("#yingfje").text(shifj);
	$("#yingfjesub").val(price);
	$("#yingfpaycon").val(shifj);
	
}

function  dynact(cateId,dataVal,tm,cityVal){
	var productId = $("#productId").val();
	var lcity ="";
	//获得属性集合
	var perties ="";
	if(productId == "1101" || productId =="2201"){
		if(cateId == "1007"|| cateId == "1001" ){
			lcity = dataVal;
		}else{
			lcity = cityVal;
		}
		var comp = $("#zccompanytype_"+tm).val();
        if(cateId =="1003"){
			if(comp == '44'||comp =='外资公司'){
				lcity = dataVal;
			}else{
				lcity = cityVal;
			}
		}
		if(comp != ""){
			perties = perties + comp +",";
		}
		var gsgm = $("#zcgongsgm_"+tm).val();
		if(gsgm != ""){
			perties = perties + gsgm +",";
		}
		if(cateId == "1002"){
			var pro = $("#addresstypevalue").val();
			lcity = dataVal;
			perties = pro;
		}
	}
	if(productId == "1105" || productId =="2202"){
		//代理记账
		lcity = cityVal;//区域
		if(cateId =="4002"){
			lcity = dataVal;
		} 
		if(cateId == "4001" || cateId =="4002"){
			var dljzgongsgm_ = $("#dljzgongsgm_"+tm).val();
			if(dljzgongsgm_ != ""){
				perties = perties + dljzgongsgm_ +",";
			}
			var serviceCircle_ = $("#serviceCircle_"+tm).val();
			if(serviceCircle_ !=""){
				perties = perties + serviceCircle_ +",";
			}
		}
		if(cateId == "4003"){
			var dljzkahbank_ = $("#dljzkahbank_"+tm).val();
            if(dljzkahbank_ != ""){
            	perties = perties + dljzkahbank_ +",";
			}
		}
	}
	if(productId == "1102"){
		lcity = cityVal;//区域
	}
	var bigsl ="";
	var smallsl ="";
	var mt ="";
	if(productId == "1104"|| productId == "2203" ){
		lcity = cityVal;//区域
		if(tm != 0){
			mt = tm;
		}
		var tradeOrderzc = $("#tradeOrderzc"+mt).val();
		if(tradeOrderzc !=""){
			perties = perties + tradeOrderzc +",";
		}
		bigsl = $("#bigsl"+mt).val();
		smallsl = $("#smallsl"+mt).val();
	}
	if(perties != ""){
		if(perties.lastIndexOf(",") > 0){
			perties = perties.substring(0,perties.lastIndexOf(","));
		}
		
	}
	
	jQuery.ajax({
		url : '/order/dynamicPrice',
		type : "post",
		data : {
			"productId" : productId,
			"localcity":lcity,
			"cateId":cateId,
			"perties":perties,
			"bigsl":bigsl,
			"smallsl":smallsl
		},
		dataType : "text",
		success : function(data, textStatus) {
			if(data != ""){
				if(productId == "1101" || productId =="2201"){
					$("#price_"+tm).text(data);
					$("#zcprice_"+tm).val(data);
					
				}
				if(productId == "1105" || productId =="2202"){
					$("#dlprice_"+tm).text(data);
					$("#dljzprice_"+tm).val(data);
				}
				if(productId =="1102"){
					$("#biangprice_"+tm).text(data);
					$("#biangmt_"+tm).val(data);
				}
				if(productId == "1104" || productId == "2203"){
					$("#gstrande"+mt).text(data);
					$("#gstrandezc"+mt).val(data);
				}
				//改变 应付款
				sumPrice(productId);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}
//计算勾选价格
function sumPrice(productId){
	var price = 0;
	var bigcount =0;
	var selec = 0;//记录是否同时勾选 公司注册 代理地址  国税报道 地税报道
	$('input[name="selectProduct"]:checked').each(function() {
		var temp = $(this).val();
		var tp = temp.split("_");
		var tm = tp[2];
		if(productId == "1101" || productId =="2201"){
			//记录是否同时勾选 公司注册 代理地址  国税报道 地税报道
			var cateId =tp[1];
			if(cateId == '1001' || cateId == '1002' || cateId =='1011' || cateId == '1012'){
				selec++;
			}
			if(tm =="25"){
				//其他
				var qitpri = $("#qitprice_25").text();
				if(qitpri == ""){
					qitpri = 0;
				}
				price = price + parseInt(qitpri);
			}else{
				var pri =  $("#price_"+tm).text();
				price = price + parseInt(pri);
			}
		}
		if(productId == "1105" || productId =="2202"){
			if(tm =="10"){
				//其他
				var qitpri = $("#dlprice_10").text();
				if(qitpri == ""){
					qitpri = 0;
				}
				price = price + parseInt(qitpri);
			}else{
				var pri =  $("#dlprice_"+tm).text();
				price = price + parseInt(pri);
			}
		}
		if(productId =="1102"){
			if(tm =="17"){
				//其他
				var qitpri = $("#biangprice_17").text();
				if(qitpri == ""){
					qitpri = 0;
				}
				price = price + parseInt(qitpri);
			}else{
				var pri =  $("#biangprice_"+tm).text();
				price = price + parseInt(pri);
			}
		}
		if(productId == "1106"){
			var pri = $("#qitprice").text();
			price = price + parseInt(pri);
		}
		if(productId == "1104" || productId == "2203"){
			var mt = "";
			if(tm != "0"){
				mt = tm;
			}
			if(tm =="20"){
				//其他
				var qitpri = $("#trandeqit_20").text();
				if(qitpri == ""){
					qitpri = 0;
				}
				price = price + parseInt(qitpri);
			}else{
				var tc = $("#bigsl"+mt).val();
				bigcount = bigcount + parseInt(tc)
				var pri =  $("#gstrande"+mt).text();
				price = price + parseInt(pri);
			}
		}
	});
	if(productId == "1104" || productId == "2203"){
		if(bigcount >= 4){
			//price = price - 200;
		}
	}
	//记录是否同时勾选 公司注册 代理地址  国税报道 地税报道 优惠400
	var yfje = price;
//	if(selec == 4){
//		yfje = parseInt(yfje)-400;
//	}
	
	$("#orderprice").text(price);
	$("#yingfje").text(yfje);
	$("#yingfjesub").val(price);
	$("#yingfpaycon").val(yfje);
	
}

function zccateinit(){
	var productId = $("#productId").val();
	var gongsmc = $("#gongsmc").val();
	if(productId != ""){
		if(productId == "1101"){
			$('input[name="selectProduct"]:checked').each(function() {
				var temp = $(this).val();
				var tp = temp.split("_");
				var cateID = tp[1];
				if(cateID == "1007"){
					$("#zcgongsgmnb").val(gongsmc);
				}
	            if(cateID == "1008"){
	            	$("#zcgongsgmgsmchz").val(gongsmc);
				}
			});
		}
	}
}

