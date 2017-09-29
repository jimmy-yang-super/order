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
		if(sourceType == "WF"){
	    	var createArchiveStatus =WF.getVariableByName("createArchiveStatus");
	    	if(createArchiveStatus != '2'){
	    		// 提交
	    		roleRelationSave();
	    		LV.enterpriseSave();
	    		
	    		alert("请到档案组创建企业档案");
	    		return;
	    	}
		}
		
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
		
		$('.cd-box2').addClass('is-visible');
	});
	
	// 提交 - 确定
	$(".cd-popup.cd-box2 .con_box .btn .btn1").click(function(){
		// 防止二次点击
		$(this).unbind("click");
		// 提交
		LV.enterpriseSave();
		LV.enterpriseSubmit();
		roleRelationSave();
		
		// 调用流程接口
		if(sourceType == "WF"){
			window.opener.location.WF.submitTask("111", "&gongShangInfoTuiHuiFlag="+window.opener.location.gongShangInfoTuiHuiFlag + "&applyMethod=" + $("#applyMethod").val() + "&chanQuanType=" + $("#chanQuanType").val());
			window.opener.location.WF.taskFinish();
			window.close();
		}
	});
	
	// 暂存
	$(".control_area .save").click(function(){
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


