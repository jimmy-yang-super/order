$(document).ready(function(){
	// 注册资金汇总
	$("[name='capitalSize']").keyup(function(){
		var regCapital = 0;
		$("[name='capitalSize']").each(function(index, ele){
			regCapital = Number(regCapital).add($(ele).val());
		});
		$("#regCapital").val(regCapital);
		$("#regCapital").next("span").text(regCapital);
	});
	
    // 公司注册地址切换
	var addressType = $("#addressType").val();
	if(addressType == undefined || addressType == "" || addressType == "0"){
		addressType = "1";
	}
	initAddressTypeChange(addressType);
	$("[name='addressTypeRadio']").change(function(e){
		addressTypeChange($(this).val());
	});
	
	// 提交
	$(".control_area .next.pop_box2").click(function(){
		// 调用流程接口
//		if(sourceType == "WF"){
//	    	var createArchiveStatus =WF.getVariableByName("createArchiveStatus");
//	    	if(createArchiveStatus != '2'){
//	    		// 提交
//	    		roleRelationSave();
//	    		LV.enterpriseSave();
//	    		
//	    		alert("请到档案组创建企业档案");
//	    		return;
//	    	}
//		}
		
		var validateArray = new Array();
		
		// 自然人股东，法人股东
		validateArray.push($("div[data-info='partnerInfo']"));
		
		// 注册地址信息
		var addressTypeVal = $("#addressType").val();
		if(addressTypeVal == "1"){
			validateArray.push($("#addressType1Div"));
		}else{
			validateArray.push($("#addressType2Div"));
		}
		
		// 快递信息，经营范围
		validateArray.push($(".editInfo"));
		
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
		// 股东校验
		if($("#partnerDiv p").length == 0 && $("#partnerDiv1 p").length == 0){
			alert("请添加股东");
			return
		}
		var flag = 0;
		var idNums = '';
		$("[name='idNum']").each(function(index, ele){
			if( idNums.indexOf($(ele).val()) > -1){
				alert("股东身份证号重复");
				flag = 1;
				return false;
			}
			idNums +=  $(ele).val();
		});
		if(flag == 1){
			return
		}
		$('.cd-box2').addClass('is-visible');
	});
	
	// 提交 - 确定
	$(".cd-popup.cd-box2 .con_box .btn .btn1").click(function(){
		// 防止二次点击
		if(enterprseNumVerity()){
			return
		}
		$(this).unbind("click");
		// 提交
		LV.enterpriseSave();
		LV.enterpriseSubmit();
		roleRelationSave();
		
		// 调用流程接口
		if(sourceType == "WF"){
			var fuHuaQiAddressApplyFlag = WF.getVariableByName("fuHuaQiAddressApplyFlag");
			var TiJiaoWangShangApplyFlag = WF.getVariableByName("TiJiaoWangShangApplyFlag");
			if(fuHuaQiAddressApplyFlag == ""){
				fuHuaQiAddressApplyFlag = 1;
			}
			if(TiJiaoWangShangApplyFlag == ""){
				TiJiaoWangShangApplyFlag = 1;
			}
			window.opener.location.WF.submitTask("111", "&fuHuaQiAddressApplyFlag="+fuHuaQiAddressApplyFlag+"&TiJiaoWangShangApplyFlag="+TiJiaoWangShangApplyFlag+"&gongShangInfoTuiHuiFlag="+window.opener.location.gongShangInfoTuiHuiFlag + "&applyMethod=" + $("#applyMethod").val() + "&chanQuanType=" + $("#chanQuanType").val()+ "&addressFormat=" + $("#addressFormat").val());
			window.opener.location.WF.taskFinish();
			window.close();
		}
	});
	
	// 暂存
	$(".control_area .save").click(function(){
		var flag = 0;
		var idNums = '';
		$("[name='idNum']").each(function(index, ele){
			if( idNums.indexOf($(ele).val()) > -1){
				alert("股东身份证号重复");
				flag = 1;
				return false;
			}
			idNums +=  $(ele).val();
		});
		if(flag == 1){
			return
		}
		LV.enterpriseSave();
		roleRelationSave();
		alert("保存成功");
	});
	
});

/**
 * 初始化，注册地址
 * @param val
 */
function initAddressTypeChange(val){
	addressTypeChange(val, true);
}

/**
 * 注册地址
 * @param val
 */
function addressTypeChange(val, init){
	if(val == "1"){
		$("#addressType1Div").show();
		$("#addressType2Div").hide();
		// 孵化器地址，初始化
		if(init == true){
			
		}
	}else{
		$("#addressType1Div").hide();
		$("#addressType2Div").show();
	}
	$("#addressType").val(val);
	$("#addressTypeRadio" + val).attr("checked", true);
	if(init != true){
		// 变化
		$("#addressType" + val + "Div input[data-info='enterprise:main']").val("");
		$("#addressType" + val + "Div input[data-info='addressInfo:main']").each(function(index, ele){
			$(ele).val("");
			LV.initSelect($(ele).attr("id"));
		});
		$("#addressInfo span").each(function(index, element){
			$(element).text("");
		});
		
		
		$("#addressType" + val + "Div div[class='pic_box']").remove();
		
	}
}


/**
 * 添加自然人股东
 */
var naturalPartnerNum = 1;
function addPersonPartner(){
	$("#partnerDiv").show();
	$("#partnerDiv").append("<div class=\"box1\"><p class=\"title\">自然人股东：</p>" +
			"<div class=\"con\" data-info=\"partnerInfo\" data-roleType=\"naturalPartner\" data-relationId=\"natural"+naturalPartnerNum+"\" style=\"width:730px;\">" +
		"<div class=\"show_box\">" +
			"<div class=\"show\" >" +
				"<label>姓名：</label>" +
				"<input type=\"text\" class=\"w_text_100\" placeholder=\"姓名\" data-validate=\"required:true;\" id=\"name\" data-info=\"partnerInfo:main\" >"+
				"<label>联系电话：</label>" +
    			"<input type=\"text\" id=\"phoneNum\" class=\"w_text_100\" placeholder=\"联系电话\" data-validate=\"mobilePhone:true;required:false;\"  data-info=\"partnerInfo:main\" />" +
    			"<label>身份证号：</label>" +
				"<input type=\"text\" class=\"w_text_150\" placeholder=\"身份证号\" data-validate=\"identity:true;required:true;\" id=\"idNum\" name=\"idNum\"  data-info=\"partnerInfo:main\" >"+
				"<label>出资日期：</label>"+
				"<input type=\"text\" class=\"w_text_100\" placeholder=\"出资日期\" data-validate=\"required:true;\" id=\"capitalDate\" data-info=\"partnerInfo:ext\" >"+
				"<label>出资额，万元：</label>"+
				"<input type=\"text\" class=\"w_text_100\" placeholder=\"出资额，万元\" data-validate=\"required:true;number:true;\" id=\"capitalSize\" name=\"capitalSize\" data-info=\"partnerInfo:ext\" onblur=\"blurCapitalSize();\">"+
			"</div>"+
			"<input type=\"hidden\" id=\"idPicIds\" placeholder=\"自然人股东身份证扫描件\" data-validate=\"required:true;\" data-info=\"partnerInfo:main\" />"+
			"<a class=\"btn\" name=\"fileupload_btn\" href=\"javascript:void(0);\" data-display=\"natural"+naturalPartnerNum+"\" ></a>" +
			"<span class=\"t3\">"+
				"<div class=\"cue_ceng3\">"+
					"<i class=\"angle2\"><s></s></i>"+
					"<ul>"+
						"<li>"+
							"<p class=\"pic\"><img src=\"/images/enterprise/upload/show_id.jpg\"></p>"+
							"<p>身份证扫描件</p>"+
						"</li>"+
					"</ul>"+
				"</div>"+
			"</span>"+
			"<div id=\"natural"+naturalPartnerNum+"\" ></div>"+
		"</div>"+
	"</div><a class=\"btn_del\" href=\"javascript:void(0);\" onclick=\"delPartnerZhuCe(this)\" >删除</a></div>");
	
	naturalPartnerNum++;
	LV.uploadDiv();
}

/**
 * 添加法人股东
 */
var legalPartnerNum = 1;
function addComPartner(){
	$("#partnerDiv1").show();
	$("#partnerDiv1").append("<div class=\"box1\"><p class=\"title\">单位股东：</p>" +
			"<div class=\"con\" data-info=\"partnerInfo\" data-roleType=\"legalPartner\" data-relationId=\"legal"+legalPartnerNum+"\" style=\"width:820px;\">"+
		"<div class=\"show_box\">"+
			"<div class=\"show\" style=\"margin-bottom: 10px;\" >"+
				"<label>法定代表人：</label>"+
				"<input type=\"text\" class=\"w_text_100\" placeholder=\"法定代表人\" data-validate=\"required:true;number:true;\"  id=\"legalPerson\"  data-info=\"partnerInfo:ext\" />"+
				"<label>公司名称：</label>"+
				"<input type=\"text\" class=\"w_text_130\" placeholder=\"公司名称\" data-validate=\"required:true;number:true;\"  id=\"name\" data-info=\"partnerInfo:main\" />"+
				"<label>营业执照号码：</label>"+
				"<input type=\"text\" class=\"w_text_100\" placeholder=\"营业执照号码\" data-validate=\"required:true;number:true;\"  id=\"businessLicenseNum\" data-info=\"partnerInfo:main\" />"+
				"<label>出资额，万元：</label>"+
				"<input type=\"text\" class=\"w_text_100\" placeholder=\"出资额,万元\" data-validate=\"required:true;number:true;\"  id=\"capitalSize\" name=\"capitalSize\" data-info=\"partnerInfo:ext\" onblur=\"blurCapitalSize();\"/>"+
			"</div>"+
			"<input type=\"hidden\" id=\"businessLicense2\"  placeholder=\"单位股东营业执照副本扫描件\" data-validate=\"required:true;\" data-info=\"partnerInfo:main\" />"+
			"<a class=\"btn\" name=\"fileupload_btn\" href=\"javascript:void(0);\" data-display=\"legal"+legalPartnerNum+"\"  ></a>" +
			"<div id=\"legal"+legalPartnerNum+"\" ></div>"+
		"</div>"+
	"</div><a class=\"btn_del\" href=\"javascript:void(0);\" onclick=\"delPartnerZhuCe(this)\" >删除</a></div>");
	legalPartnerNum++;
	LV.uploadDiv();
}


function delPartnerZhuCe(ele){
	var parentDiv = $(ele).parent("div");
	var relationId = parentDiv.attr("data-relationId");
	if(relationId != undefined && relationId != ""){
		$.post("/enterprise/business/roleRelationDel", 
				"&relationId=" + relationId, 
				function(data){
			parentDiv.remove();
		});
	}else{
		parentDiv.remove();
	}
	blurCapitalSize();
}

function blurCapitalSize(){
	var regCapital = 0;
	$("[name='capitalSize']").each(function(index, ele){
		regCapital = Number(regCapital).add($(ele).val());
	});
	$("#regCapital").val(regCapital);
	$("#regCapital").next("span").text(regCapital);
}