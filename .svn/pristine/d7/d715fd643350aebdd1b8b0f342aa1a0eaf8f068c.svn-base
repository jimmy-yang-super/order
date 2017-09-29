$(document).ready(function(){
	
	if(taskDefinitionKey != undefined && taskDefinitionKey == "LocalTaxInfo"){
		$("#localTaxRegTaskMethodDiv").show();
		$("#localTaxRegTaskMethod").attr("data-validate", "required:true;").attr("data-info", "enterprise:main");
	}else{
		$("#localTaxRegTaskMethodDiv").hide();
		$("#localTaxRegTaskMethod").attr("data-validate", "").attr("data-info", "");
	}
	// 提交
	$(".control_area .next.pop_box2").click(function(){
		// 校验
		var validateArray = new Array();
		validateArray.push($(".edit_box"));

		if(!JX.validateArray(validateArray)){
			return;
		}
		
		var financeIdNum = $("div[data-roleType='finance'] #idNum").val();
		var legalPersonIdNum = $("div[data-roleType='legalPerson'] #idNum").val();
		if(financeIdNum == legalPersonIdNum){
			alert("法定代表人与财务负责人不能为同一人，请返回修改");
			return;
		}
		
		if(sourceType == "WF"){
			if(taskDefinitionKey == "LocalTaxInfo"){
				if(WF.getVariableByName("localTaxInfoDataStatus") != "2"){
					alert("档案组未确认相关材料已存放，请到档案组存放档案");
					return;
				}
			}
			
			if(taskDefinitionKey == "NationalTaxInfo"){
				if(WF.getVariableByName("nationalTaxInfoDataStatus") != "2"){
					alert("档案组未确认相关材料已存放，请到档案组存放档案");
					return;
				}
			}
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
		
		// 调用流程接口
		if(sourceType == "WF"){
			window.opener.location.WF.submitTask("111");
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

