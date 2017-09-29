$(document).ready(function(){
	
	// 名称核准选择初始化
	var checkNameStatus = $("#checkNameStatus").val();
	if(checkNameStatus == undefined || checkNameStatus == "" || checkNameStatus == "0"){
		checkNameStatus = "2";
	}
	checkNameChange(checkNameStatus);
	// 名称核准选择变化
	$("[name='checkNameStatusSelect']").change(function(e){
		var checkNameStatus = $(this).val();
		checkNameChange(checkNameStatus);
		if(checkNameStatus == "2"){
			LV.fullName();
		}
	});
	
	
	//名称中行政区划所在的位置初始化
	var regionLocationInName = $("#regionLocationInName").val();
	if(regionLocationInName == undefined || regionLocationInName == "" || regionLocationInName == "0"){
		regionLocationInName = "1";
		$("#regionLocationInName" + regionLocationInName).attr("checked", true);
		$("#regionLocationInName").val(regionLocationInName);
	}else{
		$("#regionLocationInName" + regionLocationInName).attr("checked", true);
	}
	LV.fullName();
	
	// 名称核准选择变化
	$("[name='regionLocationInName']").change(function(e){
		$("#regionLocationInName").val($(this).val());
		LV.fullName();
	});
	
	$("#shopName").change(function(e){
		LV.fullName();
	});
	
	// 行业特点联想提示
	$("#industryCharacteristicsText").keyup(function(e){
		$(this).next("ul")
		var val = $(this).val();
		if(val != ""){
			$(this).next("ul").hide();
		}else{
			$(this).next("ul").show();
		}
		$("#industryCharacteristics").val(val);
		LV.fullName();
	}).focus(function(){
		if($(this).val() == "请选择..."){
			$(this).val("");
		}
	});
	
	
	// 提交
	$(".control_area .next.pop_box2").click(function(){
		var validateArray = new Array();
		
		// 名称校验
		var checkNameStatus = $("#checkNameStatus").val();
		if(checkNameStatus == "1"){
			validateArray.push($("#checkedNameDiv"));
		}else{
			validateArray.push($("#checkNameDiv"));
		}

		validateArray.push($("#partnerDiv"));
		
		if(!JX.validateArray(validateArray)){
			return;
		}
//		// 股东校验
//		if($("#partnerDiv p").length == 0){
//			alert("请添加股东");
//			return
//		}
		
		$('.cd-box2').addClass('is-visible');
	});
	// 提交 - 确定
	$(".cd-popup.cd-box2 .con_box .btn .btn1").click(function(){
		// 防止二次点击
		$(this).unbind("click");
		// 提交
		LV.enterpriseSave();
		LV.enterpriseSubmit();
		
		// 调用流程接口
		if(sourceType == "WF"){
			window.opener.location.WF.submitTask("111", "","1");
			window.opener.location.WF.taskFinish();
	    	window.close();
		}
	});
	
	// 暂存
	$(".control_area .save").click(function(){
		LV.enterpriseSave();
		alert("保存成功");
	});
	
});

/**
 * 添加自然人股东
 */
function addPersonPartner(){
	$("#partnerDiv").append("<p class=\"title\">自然人股东：</p>" +
			"<div class=\"con\" data-info=\"partnerInfo\" data-roleType=\"naturalPartner\" >" +
			"	<label>姓名：</label>" +
			"	<input type=\"text\" id=\"name\" class=\"w_text_130\" placeholder=\"姓名\" data-validate=\"chinese:true;required:true;\" data-info=\"partnerInfo:main\" />" +
			"	<label>联系电话：</label>" +
			"	<input type=\"text\" id=\"phoneNum\" class=\"w_text_130\" placeholder=\"联系电话\" data-validate=\"telphone:true;required:false;\" data-info=\"partnerInfo:main\" />" +
			"	<label>身份证号码：</label>" +
			"	<input type=\"text\" id=\"idNum\" class=\"w_text_150\" placeholder=\"身份证号码\" data-validate=\"identity:true;required:true;\" data-info=\"partnerInfo:main\" />" +
			"	<a class=\"btn_del\" href=\"javascript:void(0);\" onclick=\"LV.delPartner(this)\" >删除</a></div>");
	LV.uploadDiv();
}

/**
 * 添加法人股东
 */
function addComPartner(){
	$("#partnerDiv").append("<p class=\"title\">单位股东：</p>" +
			"<div class=\"con\" data-info=\"partnerInfo\" data-roleType=\"legalPartner\" >" +
			"	<label>公司名称：</label>" +
			"	<input type=\"text\" id=\"name\" class=\"w_text_130\" placeholder=\"公司名称\" data-validate=\"chineseCode:true;required:true;\" data-info=\"partnerInfo:main\" />" +
			"	<label>法定代表人：</label>" +
			"	<input type=\"text\" id=\"legalPerson\" class=\"w_text_130\" placeholder=\"法定代表人\" data-validate=\"chinese:true;required:true;\" data-info=\"partnerInfo:ext\" />" +
			"	<label>营业执照号码：</label>" +
			"	<input type=\"text\" id=\"businessLicenseNum\" class=\"w_text_150\" placeholder=\"营业执照号码\" data-validate=\"number:true;required:true;\" data-info=\"partnerInfo:main\" />" +
			"	<a class=\"btn_del\" href=\"javascript:void(0);\" onclick=\"LV.delPartner(this)\" >删除</a></div>");
	LV.uploadDiv();
}

/**
 * 名称核准
 * @param val
 */
function checkNameChange(val){
	if(val == "1"){
		$("#checkedNameDiv").show();
		$("#checkNameDiv").hide();
	}else{
		$("#checkedNameDiv").hide();
		$("#checkNameDiv").show();
	}
	$("#checkNameStatus").val(val);
	$("#checkNameStatusSelect" + val).attr("checked", true);
}
