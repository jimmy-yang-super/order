$(document).ready(function(){
	// 注册资金汇总
	initCapitalSizeChange();
	
	// 提交
	$(".control_area .next.pop_box2").click(function(){
		var validateArray = new Array();
		// 公司基本信息
		validateArray.push($(".enterpriseBaseInfo"));
		
		// 自然人股东，法人股东
		validateArray.push($("div[data-info='partnerInfo']"));
		
		// 公司架构
		var frameworkTypeVal = $("#frameworkType").val();
		if(frameworkTypeVal == "1"){
			validateArray.push($("#simplyFrameworkDiv"));
		}else{
			// 是否有监事会
			var isJianShiMeetingVal = $("#isJianShiMeeting").val();
			if(isJianShiMeetingVal == "1"){
				validateArray.push($("#complexFrameworkDiv"));
			}else{
				validateArray.push($("#complexFrameworkDiv .p1 div:not(#isJianShiMeeting1Div > div)"));
			}
		}

		if(!JX.validateArray(validateArray)){
			return;
		}
		
		// 兼任法人
		var jianRenRoleTypeVal = $("#legalPersonDiv [data-roleIdArray]").attr("data-roleIdArray");
		if(jianRenRoleTypeVal == undefined || jianRenRoleTypeVal == ""){
			alert("请选择兼任法人");
			return;
		}
		
		$('.cd-box2').addClass('is-visible');
	});
	
	// 提交 - 确定
	$(".cd-popup.cd-box2 .con_box .btn .btn1").click(function(){
		// 防止二次点击
		$(this).unbind("click");
		// 提交
		LV.enterpriseSubmit();
		roleRelationSave();
	});
	
	// 暂存
	$(".control_area .save").click(function(){
		LV.enterpriseSave();
		roleRelationSave();
		alert("保存成功");
	});
})

function addPersonPartner(){
	$("#partnerDiv").append("<p class=\"title\">自然人股东：</p>" +
			"<div class=\"con\" data-info=\"partnerInfo\" data-roleType=\"naturalPartner\" >" +
			"<table class=\"partner\" ><tr><td class=\"first\" >" +
			
			"	<label>姓名：</label>" +
			"	<input type=\"text\" id=\"name\" class=\"w_text_150\" placeholder=\"姓名\" data-validate=\"required:true;\" data-info=\"partnerInfo:main\" />" +
			"</td><td class=\"second\">" + 
			"	<label>联系电话：</label>" +
			"	<input type=\"text\" id=\"phoneNum\" class=\"w_text_150\" placeholder=\"联系电话\" data-validate=\"required:true;telPhone:true;\" data-info=\"partnerInfo:main\" />" +
			"</td></tr><tr><td class=\"first\" >" +
			"	<label>身份证号码：</label>" +
			"	<input type=\"text\" id=\"idNum\" class=\"w_text_150\" placeholder=\"身份证号码\" data-validate=\"required:true;identity:true;\" data-info=\"partnerInfo:main\" />" +
			"</td><td class=\"second\">" + 
			"	<label>出资额，万元：</label>" +
			"	<input type=\"number\" id=\"capitalSize\" name=\"capitalSize\" class=\"w_text_150\" placeholder=\"出资额,万元\" data-validate=\"required:true;number:true;\" data-info=\"partnerInfo:ext\" />" +
			"</td></tr></table>" +
			"	<a class=\"btn_del\" style=\"margin-top: 50px;\" href=\"javascript:void(0);\" onclick=\"delPartner(this)\" >删除</a>" +
			"	<input type=\"hidden\" id=\"idPicIds\"  data-info=\"partnerInfo:main\" /> " +
			"	<a class=\"btn\" style=\"margin-top: 50px;\" name=\"fileupload_btn\" href=\"javascript:void(0);\" ></a></div>");
	LV.uploadDiv();
	initCapitalSizeChange();
}

function addComPartner(){
	$("#partnerDiv").append("<p class=\"title\">单位股东：</p>" +
			"<div class=\"con\" data-info=\"partnerInfo\" data-roleType=\"legalPartner\" >" +
			"<table class=\"partner\" ><tr><td class=\"first\" >" +
			"	<label>公司名称：</label>" +
			"	<input type=\"text\" id=\"name\" class=\"w_text_150\" placeholder=\"公司名称\" data-validate=\"required:true;\" data-info=\"partnerInfo:main\" />" +
			"</td><td class=\"second\">" + 
			"	<label>法定代表人：</label>" +
			"	<input type=\"text\" id=\"legalPerson\" class=\"w_text_150\" placeholder=\"法定代表人\" data-validate=\"required:true;\" data-info=\"partnerInfo:ext\" />" +
			"</td></tr><tr><td class=\"first\" >" +
			"	<label>营业执照号码：</label>" +
			"	<input type=\"text\" id=\"businessLicenseNum\" class=\"w_text_150\" placeholder=\"营业执照号码\" data-validate=\"required:true;\" data-info=\"partnerInfo:main\" />" +
			"</td><td class=\"second\">" + 
			"	<label>出资额，万元：</label>" +
			"	<input type=\"number\" id=\"capitalSize\" name=\"capitalSize\" class=\"w_text_150\" placeholder=\"出资额,万元\" data-validate=\"required:true;number:true;\" data-info=\"partnerInfo:ext\" />" +
			"</td></tr></table>" +
			"	<a class=\"btn_del\" style=\"margin-top: 50px;\" href=\"javascript:void(0);\" onclick=\"delPartner(this)\" >删除</a>" +
			"	<input type=\"hidden\" id=\"businessLicense1\"  data-info=\"partnerInfo:main\" /> " +
			"	<a class=\"btn\" style=\"margin-top: 50px;\" name=\"fileupload_btn\" href=\"javascript:void(0);\" ></a></div>");
	LV.uploadDiv();
	initCapitalSizeChange();
}

//注册资金汇总
function initCapitalSizeChange(){
	$("[name='capitalSize']").unbind("change").change(function(){
		var regCapital = 0;
		$("[name='capitalSize']").each(function(index, ele){
			regCapital = Number(regCapital) +  Number($(ele).val());
		});
		$("#regCapital").val(regCapital);
		$("#regCapital").next("span").text(regCapital);
	});
}

function delPartner(ele){
	var parentDiv = $(ele).parent("div");
	var relationId = parentDiv.attr("data-relationId");
	if(relationId != undefined && relationId != ""){
		$.post("/enterprise/business/roleRelationDel", 
				"&relationId=" + relationId, 
				function(data){
			parentDiv.prev().remove();
			parentDiv.remove();
		});
	}else{
		parentDiv.prev().remove();
		parentDiv.remove();
	}
	var regCapital = 0;
	$("[name='capitalSize']").each(function(index, ele){
		regCapital = Number(regCapital) +  Number($(ele).val());
	});
	$("#regCapital").val(regCapital);
	$("#regCapital").next("span").text(regCapital);
}
