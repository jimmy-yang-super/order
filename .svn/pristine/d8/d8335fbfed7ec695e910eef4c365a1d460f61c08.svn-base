$(document).ready(function(){
	var WF = WF;
	WF = {
			// 校验
			validate: function(winDiv){
				if(winDiv == undefined || winDiv == ""){
					return true;
				}
				var validateFlag = true;
				$("#"+ winDiv).find("input[name='inputInfoAll']," +
						"[name='inputInfoTask']," +
						"[name='inputInfoCompany']," +
						"[name='inputInfoOther']" ).each(function(index,element){
					
					var dataValidate = $(element).attr("data-validate");
					
					if(dataValidate == "none"){
						// 忽略，不校验
						return;
					}
					var key = $(element).parent("td").prev().text();
					var val = $(element).val();
					// 校验，非空，默认
					if(val == ""){
						alert(key + "不能为空。");
						validateFlag = false;
						return false;
					}
					if(dataValidate != undefined && dataValidate != ""){
						var dataValidateArray = dataValidate.split(";");
						for(var n=0;n<dataValidateArray.length;n++){
							var dataValidate = dataValidateArray[n];
							if(dataValidate == undefined || dataValidate == ""){
								continue;
							}
							var dataValidateMap = dataValidate.split(":");
							if(dataValidateMap == undefined || dataValidateMap.length != 2){
								continue;
							}
							var dataValidateKey = dataValidateMap[0];
							var dataValidateValue = dataValidateMap[1];
							if(dataValidateKey == "number"){
								if(dataValidateValue == "true" || dataValidateValue == true){
									// 数字校验
									var reg = new RegExp("^[0-9]*$");
									if(!reg.test(val)){
										alert(key + " 请输入数字");
										validateFlag = false;
										return false;
									}
								}
							}
							if(dataValidateKey == "min"){
								if(val == undefined || val.length < dataValidateValue ){
									alert(key + " 至少" + dataValidateValue + "个字" );
									validateFlag = false;
									return false;
								}
							}
						}
					}
				});
				return validateFlag;
			},
	};
	window.location.WF =window.WF = WF;
});