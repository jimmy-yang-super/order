$(document).ready(function(){
	var LV = {
			// 初始化
			init: function(){
				LV.initFun();
				LV.initSelect();
				LV.uploadDiv();
				LV.initUploadPic();
			},
			initFun:function(){
				//去除字符串头部空格或指定字符
				String.prototype.trimStart = function(c){
				    if(c==null||c==""){
				        var str= this.replace('/^/s*/', '');
				        return str;
				    }else{
				        var rg=new RegExp("^"+c+"*");
				        var str= this.replace(rg, '');
				        return str;
				    }
				}

				//去除字符串尾部空格或指定字符
				String.prototype.trimEnd = function(c){
				    if(c==null||c==""){
				        var str= this;
				        var rg = /s/;
				        var i = str.length;
				        while (rg.test(str.charAt(--i)));
				        return str.slice(0, i + 1);
				    }else{
				        var str= this;
				        var rg = new RegExp(c);
				        var i = str.length;
				        while (rg.test(str.charAt(--i)));
				        return str.slice(0, i + 1);
				    }
				},
				/**
				 ** 加法函数，用来得到精确的加法结果
				 ** 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
				 ** 返回值：
				 **/
				Number.prototype.add = function accAdd(arg) {
				    var r1, r2, m, c, arg1 = arg, arg2 = this;
				    try {
				        r1 = arg1.toString().split(".")[1].length;
				    } catch (e) {
				        r1 = 0;
				    }
				    try {
				        r2 = arg2.toString().split(".")[1].length;
				    } catch (e) {
				        r2 = 0;
				    }
				    c = Math.abs(r1 - r2);
				    m = Math.pow(10, Math.max(r1, r2));
				    if (c > 0) {
				        var cm = Math.pow(10, c);
				        if (r1 > r2) {
				            arg1 = Number(arg1.toString().replace(".", ""));
				            arg2 = Number(arg2.toString().replace(".", "")) * cm;
				        } else {
				            arg1 = Number(arg1.toString().replace(".", "")) * cm;
				            arg2 = Number(arg2.toString().replace(".", ""));
				        }
				    } else {
				        arg1 = Number(arg1.toString().replace(".", ""));
				        arg2 = Number(arg2.toString().replace(".", ""));
				    }
				    return (arg1 + arg2) / m;
				},
				/**
				 ** 减法函数，用来得到精确的减法结果
				 ** 说明：javascript的减法结果会有误差，在两个浮点数相减的时候会比较明显。这个函数返回较为精确的减法结果。
				 ** 返回值：精确结果
				 **/
				Number.prototype.sub = function (arg) {
				    var r1, r2, m, n, arg1 = arg, arg2 = this;
				    try {
				        r1 = arg1.toString().split(".")[1].length;
				    }catch (e) {
				        r1 = 0;
				    }
				    try {
				        r2 = arg2.toString().split(".")[1].length;
				    }catch (e) {
				        r2 = 0;
				    }
				    m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
				    n = (r1 >= r2) ? r1 : r2;
				    return ((arg1 * m - arg2 * m) / m).toFixed(n);
				},
				/**
				 ** 乘法函数，用来得到精确的乘法结果
				 ** 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
				 ** 调用：accMul(arg1,arg2)
				 ** 返回值：arg1乘以 arg2的精确结果
				 **/
				Number.prototype.mul = function (arg) {
				    var m = 0, s1 = arg1.toString(), s2 = arg2.toString(), arg1 = arg , arg2 = this;
				    try {
				        m += s1.split(".")[1].length;
				    }catch (e) {
				    	
				    }
				    try {
				        m += s2.split(".")[1].length;
				    }catch (e) {
				    	
				    }
				    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
				},
				/** 
				 ** 除法函数，用来得到精确的除法结果
				 ** 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
				 ** 调用：accDiv(arg1,arg2)
				 ** 返回值：arg1除以arg2的精确结果
				 **/
				Number.prototype.div = function (arg) {
				    var t1 = 0, t2 = 0, r1, r2, arg1 = arg , arg2 = this;
				    try {
				        t1 = arg1.toString().split(".")[1].length;
				    } catch (e) {
				    	
				    }
				    try {
				        t2 = arg2.toString().split(".")[1].length;
				    } catch (e) {
				    	
				    }
				    with (Math) {
				        r1 = Number(arg1.toString().replace(".", ""));
				        r2 = Number(arg2.toString().replace(".", ""));
				        return (r1 / r2) * pow(10, t2 - t1);
				    }
				}
			},
			/**
			 * 通过id获取字典值
			 * @param dicDataId
			 */
			getDicDataById:function(type, dicDataId){
				var value = "";
				$.ajax({
					url:"/enterprise/common/getdicdatabyid",
					type:'post',    
				    cache:false,
				    data:"&type=" + type + "&dicDataId=" + dicDataId,
				    dataType:'json',
				    async:false,
					complete:function(data){
						if(data != undefined && data != "" 
							&& data.responseText != undefined 
							&& data.responseText != "undefined" ){
							
							value = data.responseText;
						}
					}
				});
				return value;
			},
			/**
			 * 获取字典列表
			 * @param type 类型，例如：企业库字典，区域字典，服务字典
			 * @param dicData key
			 */
			getDicData:function(type, parentId){
				var value = "";
				$.ajax({
					url:"/enterprise/common/getdicdatalist",
					type:'post',    
				    cache:false,
				    data:"&type=" + type + "&parentId=" + parentId,
				    dataType:'json',
				    async:false,
					complete:function(data){
						if(data != undefined && data != "" 
							&& data.responseText != undefined 
							&& data.responseText != "undefined" ){
							value = data.responseText;
						}
					}
				});
				return $.parseJSON(value);
			},
			/**
			 * 公司核名，组合名字
			 */
			fullName:function (){
				var checkNameStatus = $("#checkNameStatus").val();
				if(checkNameStatus == "1"){
					return;
				}
				var regionLocationInName = $("#regionLocationInName").val(); // 名称中行政区划所在的位置
				var shopName = $("#shopName").val(); // 字号
				var industryCharacteristics = $("#industryCharacteristics").val(); // 行业特点
				var organizationType = $("#organizationType").val(); // 组织形式
				var city = LV.getDicDataById("city", $("#cityId").val()); // 城市
				var fullName = "";
				if(regionLocationInName == "1"){
					fullName = city + shopName + industryCharacteristics + organizationType;
				}else if(regionLocationInName == "2"){
					fullName =  shopName + "（" + city + "）" + industryCharacteristics + organizationType;
				}else if(regionLocationInName == "3"){
					fullName =  shopName + industryCharacteristics + "（" + city + "）" + organizationType;
				}
				$(".green_line span").text(fullName);
				$("#checkedNameDiv #name").val(fullName);
			},
			/**
			 * 选择值变化，联动
			 */
			selectDataChange:function (obj, dataVal){
				// 选择城市，联动区域
				if($(obj).attr("id") == "cityId"){
					var dicData = LV.getDicData("city", dataVal);
					var areaHtml = "";
					for(var p in dicData){
						areaHtml += "<li data-value=\"" + p + "\">" + dicData[p] + "</li>"
					}
					$("#areaId").next().find("ul").html(areaHtml);
					LV.initSelect("areaId");
				}
				// 选择城市，行业特点，组织形式，联动公司名称
				if($(obj).attr("id") == "cityId" 
					|| $(obj).attr("id") == "industryCharacteristics"
					|| $(obj).attr("id") == "organizationType"){
					LV.fullName();
				}
				
				// 选择地址合作商，联动地址类型
				if($(obj).attr("id") == "addressPartnerCode"){
					var dicData = LV.getDicData("addressTemplateId", dataVal);
					var areaHtml = "";
					for(var p in dicData){
						areaHtml += "<li data-value=\"" + p + "\">" + dicData[p] + "</li>"
					}
					$("#addressTemplateId").next().find("ul").html(areaHtml);
					LV.initSelect("addressTemplateId");
					$("#addressInfo span").each(function(index, element){
						$(element).text("");
					});
				}
				
				// 选择地址区域，联动详细信息
				if($(obj).attr("id") == "addressTemplateId"){
					var dicData = LV.getDicData("addressInfo", dataVal);
					$("#addressInfo span").each(function(index, element){
						$(element).text(dicData[$(element).attr("id")]);
					});
				}
				
				
			},
			/**
			 * 初始化下拉选择
			 * @param selectId
			 */
			initSelect:function(selectId){
				var selectObj;
				if(selectId != undefined && selectId != "" ){
					selectObj = $("#" + selectId).next();
				}else{
					selectObj = $('div[name="nice-select"]');
				}
				//　初始化值
				$(selectObj).each(function(index,element){
					var selectValInput=$(element).prev("input");
					var val = selectValInput.val();
					var changeFlag = true;
					$(this).find("ul li").each(function(index, ele){
						if(val != undefined && val != "" && $(ele).attr("data-value") == val){
							changeFlag = false;
							$(element).find('input').val($(ele).text());
							LV.selectDataChange(selectValInput, val);
						}
					});
					if(changeFlag == true){
						if(selectValInput.attr("data-select") != "none"){
							var first = $(element).find("ul li:first");
							selectValInput.val(first.attr("data-value"));
							$(element).find("input").val(first.text());
							LV.selectDataChange(selectValInput, first.attr("data-value"));
						}else if(selectValInput.attr("data-select-write") == "true"){
							// 可输入
							$(element).find("input").val(val);
						}else {
							// 清空数据
							$(element).find("input").val("请选择...");
							selectValInput.val("");
						}
					}
				});

				$(selectObj).unbind("click").click(function(e) {
					$(selectObj).find('ul').hide();
					$(this).find('ul').show();
					e.stopPropagation();
				});
				
				$(selectObj).find('li').unbind("hover").hover(function(e) {
					$(this).toggleClass('on');
					e.stopPropagation();
				});
				
				$(selectObj).find('li').unbind("click").click(function(e) {
					var val = $(this).text(); // 选择值-显示
					var dataVal = $(this).attr("data-value"); // 选择值
					
					var parents=$(this).parents('[name="nice-select"]');
					var preDataVal = parents.prev().val(); // 原始值
					parents.find('input').val(val);
					parents.prev().val(dataVal);
					$(selectObj).find('ul').hide();
					
					if(dataVal != preDataVal){
						LV.selectDataChange(parents.prev(), dataVal);
					}
					e.stopPropagation();
				});
				
				$(document).unbind("click").click(function() {
					$('[name="nice-select"] ul').hide();
				});
			},
			// 上传控件
			uploadDiv: function(){
				// 上传 
				$("a[name='fileupload_btn']").each(function(index, element){
					var picDivId = LV.generateMixed();
					$(element).html("<div id=\"" + picDivId + "\"></div>");
					//图片上传
				    upload.init({
				        upload_url: "/file/upload/" + enterpriseId + "?test=1",
				        file_size_limit : "5 MB",
				        button_placeholder_id: picDivId,
				        file_upload_limit : 5, //总上传张数限制
				        file_queue_limit : 1, //单次上传张数
						queueError : function(file, errorCode, message){
							if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED){
								alert("您此次上传了太多文件.\n" + (message === 0 ? "您已达到此次上传限制." : "您单次最多能上传" + message + "个文件."));
					            return;
					        }
							switch (errorCode){
					            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
					                alert("文件大小超过限制!");
					                break;
					            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
					                alert("不能上传0节字文件!");
					                break;
					            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
					                alert("不允许上传文件类型的文件!");
					                break;
					            default:
					            	alert("未知错误!");
					                break;
					        }
						},
						dialogComplete : function(numFilesSelected, numFilesQueued) {
							this.startUpload();
						},
						start:function(file){
							
						},
						error : function(file, errorCode, message){

							//报错
							if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED){
								alert("您此次上传了太多文件.\n" + (message === 0 ? "您已达到此次上传限制." : "您单次最多能上传" + message + "个文件."));
					            return;
					        }
							
							switch (errorCode){
					            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
					                alert("文件大小超过限制!");
					                break;
					            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
					                alert("不能上传0节字文件!");
					                break;
					            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
					                alert("不允许上传文件类型的文件!");
					                break;
					            default:
					            	alert("未知错误!");
					                break;
					        }
						},
						success : function(file, serverData){
							if(serverData == "false"){
								alert("上传失败，请重新上传");
								
							}else{
								
					        	var fileId = $.parseJSON(serverData).fileId;
					        	// 上传成功
					        	var dataFile = $(element).prev("input");
					        	var dataFileVal = $(dataFile).val();
					        	if(dataFileVal != undefined && dataFileVal != "" && fileId != undefined && fileId != ""){
					        		dataFileVal = dataFileVal + ";";
					        	}
					        	$(dataFile).val(dataFileVal + fileId);
					        	if($(element).attr("data-display") != undefined && $(element).attr("data-display") != "" ){
						        	LV.initUploadDiv($("#" + $(element).attr("data-display")), fileId, dataFile);
					        	}else{
						        	LV.initUploadDiv(element, fileId, dataFile);
					        	}
							}
						},
						queueComplete : function(){
							//队列完成
						}
				    });
				});
			},
			
			initUploadPic:function(){
				$("a[name='fileupload_btn']").each(function(index, element){
					// 初始化原有图片
					var dataFile = $(element).prev();
		        	var dataFileVal = $(dataFile).val();
		        	if(dataFileVal != undefined && dataFileVal != ""){
		        		var dataFileValFileIds = dataFileVal.split(";");
		        		for(var num=0;num<dataFileValFileIds.length;num++){
		        			var fileId = dataFileValFileIds[num];
				        	if($(element).attr("data-display") != undefined && $(element).attr("data-display") != "" ){
					        	LV.initUploadDiv($("#" + $(element).attr("data-display")), fileId, dataFile);
				        	}else{
					        	LV.initUploadDiv(element, fileId, dataFile);
				        	}
		        		}
		        	}
				});
			},
			initUploadDiv:function(ele, fileId, dataFile){
    			var url = "/file/download/" + fileId;
	        	if($(ele).nextAll("div[class='pic_box']") != undefined && $(ele).nextAll("div[class='pic_box']").length == 0){
	        		$(ele).after("<div class=\"pic_box\"><p class=\"t1\">图片预览</p><ul class=\"gallery\"></ul></div>");
	        	}
    			var picBoxDiv = $(ele).next("div[class='pic_box']");
	        	$(picBoxDiv).find("ul").append("<li>" +
	        			"<img src=\"" + url + "?maxLength=100 \">" +
	        			"<span class=\"cz_box\">" +
	        			"	<a href=\"" + url + "\" down=\"1\" >下载</a>" +
	        			"	<a href=\"javascript:void(0);\" >删除</a>" +
	        			"</span></li>");
	        	$(picBoxDiv).find("ul li:last .cz_box a:last").click(function(){
	        		var fileIds = $(dataFile).val();
					if(fileIds.indexOf(fileId) >= 0 ){
						fileIds = fileIds.replace(fileId + ";", "");
						fileIds = fileIds.replace(";" + fileId, "");
						fileIds = fileIds.replace(fileId, "");
					}
					$(dataFile).val(fileIds);
					var parentli = $(this).parent().parent("li");
					var liSize = $(parentli).parent("ul").find("li").length;
					if(liSize == 1){
						$(parentli).parent().parent(".pic_box").remove();
					}else{
						parentli.remove();
					}
	        	});
			},
			/**
			 * 删除股东
			 */
			delPartner:function(ele){
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
			},
			/**
			 * 业务信息提交
			 */
			enterpriseSubmit:function(){
				LV.enterpriseSave();
				var param = "&enterpriseId=" + enterpriseId + "&nextBusinessKey=" + nextBusinessKey;
				$.ajax({
					url:"/enterprise/business/enterpriseSubmit",
					type:'post',    
				    data:param,
				    dataType:'json',
				    async:false,
					complete:function(data){
						
					}
				});
			},
			/**
			 * 业务信息保存
			 */
			enterpriseSave:function(){
				var mainData = {};
				
				// 核名业务
				var enterpriseData = {};
				$("[data-info='enterprise:main']").each(function(index, element){
					enterpriseData[$(element).attr("id")] =  $(element).val();
				});
				mainData[businessKey] = enterpriseData;
				// 公司相关人员信息
				var partnerInfoArray = new Array();
				$("[data-info='partnerInfo']").each(function(index, element){
					
					var partnerInfo = {};
					// 角色
					partnerInfo["dataRoleType"] = $(element).attr("data-roleType");
					// 主体信息
					var partnerInfoMain = {};
					$(element).find("input[data-info='partnerInfo:main']").each(function(idx, ele){
						partnerInfoMain[$(ele).attr("id")] =  $(ele).val();
					});
					partnerInfo["partnerInfoMain"] = partnerInfoMain;
					
					// 扩展信息
					var partnerInfoExt = {};
					$(element).find("input[data-info='partnerInfo:ext']").each(function(idx, ele){
						partnerInfoExt[$(ele).attr("id")] =  $(ele).val();
					});
					partnerInfo["partnerInfoExt"] = partnerInfoExt;
					
					partnerInfoArray.push(partnerInfo); 
				});
				mainData["partnerInfoArray"] = partnerInfoArray;
				
				// 公司地址信息
				var addressData = {};
				$("[data-info='addressInfo:main']").each(function(index, element){
					addressData[$(element).attr("id")] =  $(element).val();
				});
				mainData["addressData"] = addressData;
				var param = "&enterpriseId=" + enterpriseId + "&data=" + JSON.stringify(mainData);
				$.ajax({
					url:"/enterprise/business/enterpriseSave",
					type:'post',    
				    data:param,
				    dataType:'json',
				    async:false,
					complete:function(data){
					}
				});
			},
			/**
			 * 获取经营范围
			 */
			getOperatingRange:function(addressType, parentId, searchKey){
				var param = "";
				if(addressType != undefined && addressType != ""){
					param += "&addressType=" + addressType;
				}
				if(parentId != undefined && parentId != ""){
					param += "&parentId=" + parentId;
				}
				if(searchKey != undefined && searchKey != ""){
					param += "&searchKey=" + searchKey;
				}
				
				var value = "";
				$.ajax({
					url:"/enterprise/business/getOperatingRange",
					type:'post',    
				    data:param,
				    dataType:'json',
				    async:false,
					complete:function(data){
						if(data != undefined && data != "" 
							&& data.responseText != undefined 
							&& data.responseText != "undefined" ){
							value = data.responseText;
						}
					}
				});
				if(value != undefined && value != ""){
					return $.parseJSON(value);
				}
			},
			/**
			 * 获取企业相关人员
			 * @returns
			 */
			getPersonRole:function(){
				var param = "&enterpriseId=" + enterpriseId;
				var value = "";
				$.ajax({
					url:"/enterprise/business/getPersonRole",
					type:'post',    
				    data:param,
				    dataType:'json',
				    async:false,
					complete:function(data){
						if(data != undefined && data != "" 
							&& data.responseText != undefined 
							&& data.responseText != "undefined" ){
							value = data.responseText;
						}
					}
				});
				if(value != undefined && value != ""){
					return $.parseJSON(value);
				}
			},
			
			/**
			 * 获取企业相关人员
			 * @returns
			 */
			getPersonRoleByRoleId:function(roleId){
				var param = "&enterpriseId=" + enterpriseId;
				if(roleId != undefined && roleId != ""){
					param += "&roleId=" + roleId;
				}
				var value = "";
				$.ajax({
					url:"/enterprise/business/getPersonRole",
					type:'post',    
				    data:param,
				    dataType:'json',
				    async:false,
					complete:function(data){
						if(data != undefined && data != "" 
							&& data.responseText != undefined 
							&& data.responseText != "undefined" ){
							value = data.responseText;
						}
					}
				});
				if(value != undefined && value != ""){
					return $.parseJSON(value);
				}
			},
			/**
			 * 
			 */
			checkExclusionRole:function(roleId, exclusionRoleType){

				var exclusionRoleFlag = false;

				// 针对注册页面
				var frameworkType = $("#frameworkType").val();
				var obj = "";
				if(frameworkType == "1"){
					obj = $("#simplyFrameworkDiv");
				}else{
					obj = $("#complexFrameworkDiv");
				}
				$("#" + obj.attr("id") + ",#enterpriseReplenishInfo,#legalPersonDiv").find(".xzry_btn.pop_box3").each(function(index, ele){
					var roleType = $(ele).attr("data-roleType");
					var roleIdArray = $(ele).parent().prev().children("[data-roleIdArray]").attr("data-roleIdArray");
					if(roleType != undefined && roleType != "" && (";" + exclusionRoleType + ";").indexOf(";" + roleType + ";") >= 0){
						// 是排斥的角色
						if(roleIdArray != undefined && roleIdArray != "" && (";" + roleIdArray + ";").indexOf(";" + roleId + ";") >= 0){
							exclusionRoleFlag = true;
							return true;
						}
					}else{
						
					}
				});
				
				// 针对国地税报道页面
/*				if(businessKey == "guoDiTax"){
					// 后台查询人员角色
					var personRole = LV.getPersonRoleByRoleId(roleId);
					if(personRole != undefined && personRole != ""){
						var personRoleList = personRole.personRoleList;
						var html ="";
						for(var i=0;i<personRoleList.length;i++){
							// 如果已经选中，初始化选中状态
							var roleType = personRoleList[i]["roleType"];
							if(roleType != undefined && roleType != ""){
								var roleTypeArray = roleType.split(";");
								for(var n=0;n<roleTypeArray.length;n++){
									if(roleTypeArray[n] != undefined && roleTypeArray[n] != "" && (";" + exclusionRoleType + ";").indexOf(";" + roleTypeArray[n] + ";") >= 0){
										exclusionRoleFlag = true;
										i = 10000;
										break;
									}
								}
							}
						}
					}
				}*/
				return exclusionRoleFlag;
			},
			/**
			 * 人员选择控件
			 * @param selectEle
			 * @param roleType
			 * @param exclusionRoleType
			 * @param selectModel
			 * @param submitFun
			 */
			selectPerson:function(selectEle, roleType, exclusionRoleType, selectModel, submitFun){
				var selectPersonDiv = $(".cd-popup.cd-box3");
				// 获取人员
				var personRole = LV.getPersonRole();
				if(personRole != undefined && personRole != ""){
					var personRoleList = personRole.personRoleList;
					var html ="";
					for(var i=0;i<personRoleList.length;i++){
						// 如果已经选中，初始化选中状态
						var roleName = personRoleList[i]["name"];
						var roleId = personRoleList[i]["id"];
						
						// 判断是否为互斥角色
						if(LV.checkExclusionRole(roleId, exclusionRoleType)){
							continue;
						}
						var liClass = "cd-popup-close off";
						var roleIdArray = $(selectEle).parent().prev("li").children("input[data-roleIdArray]").attr("data-roleIdArray");
						if(roleIdArray != undefined && (";" + roleIdArray + ";").indexOf(";" + roleId + ";") >= 0){
							liClass = "cd-popup-close che_on";
						}
						
						html += "<li class=\"" + liClass + "\" data-roleId=\"" + roleId 
						+ "\" data-roleType=\"" + personRoleList[i]["roleType"] + "\" >" + roleName + "</li>";
					}		
					// 初始化控件人员
					$(selectPersonDiv).find(".ry_box ul").html(html);
				}
				
				if(selectModel == "one"){
					// 单选
					$(selectPersonDiv).find("p[class='title']").text("选择人员（单选）");
				}else{
					// 多选
					$(selectPersonDiv).find("p[class='title']").text("选择人员（多选）");
				}
				
				// 初始化选中的人员
				LV.initSelectedPerson(selectPersonDiv, selectModel);
				
				// 添加人员
				$(selectPersonDiv).find(".btn .addperson").click(function(){
			        $(".add_box").show();
			    });
				
				// 添加人员 - 取消
				$(selectPersonDiv).find(".add_box .cancelAdd").unbind("click").click(function(){
			        $(".add_box").hide();
			    });
				
				// 添加人员 - 确定
				$(selectPersonDiv).find(".add_box .submitAdd").unbind("click").click(function(){
					//校验数据
					var validateArray = new Array();
					validateArray.push($(".cd-popup.cd-box3 div[data-info='alertPartnerInfo']"));
					if(!JX.validateArray(validateArray)){
						return;
					}

					var mainData = {};
					var partnerInfoArray = new Array();
					$(".add_box [data-info='alertPartnerInfo']").each(function(index, element){
						var partnerInfo = {};
						// 角色
						partnerInfo["dataRoleType"] = "otherRoleType";
						// 主体信息
						var partnerInfoMain = {};
						$(element).find("input[data-info='partnerInfo:main']").each(function(idx, ele){
							partnerInfoMain[$(ele).attr("id")] =  $(ele).val();
						});
						partnerInfo["partnerInfoMain"] = partnerInfoMain;
						partnerInfoArray.push(partnerInfo);
					});
					mainData["partnerInfoArray"] = partnerInfoArray;
					var param = "&enterpriseId=" + enterpriseId + "&data=" + JSON.stringify(mainData);
					
					$.ajax({
						url:"/enterprise/business/enterpriseSave",
						type:'post',    
					    data:param,
					    dataType:'json',
					    async:false,
						complete:function(data){
							if(data != undefined){
								data = $.parseJSON(data.responseText);
								var person = data.partnerInfoArray[0].partnerInfoMain;
								// 
								// 判断是否为互斥角色
								if(LV.checkExclusionRole(person.roleId, exclusionRoleType)){
									alert(person.name + "已经担任其他角色，不可担任此角色");
									return;
								}
								
								if(selectModel == "one"){
									$(".cd-popup.cd-box3 .ry_box ul li").each(function(index, ele){
										$(ele).addClass("off").removeClass("che_on");
									});
								}
								$(".cd-popup.cd-box3 .ry_box ul").append("<li class=\"cd-popup-close che_on\" data-roleId=\"" + person.roleId 
								+ "\" data-roleType=\"" + roleType + "\" >" + person.name + "</li>");
							}
							// 清空数据
							$("#otherPersonDisplay").next(".pic_box").remove();
							$(".add_box [data-info='alertPartnerInfo'] input[data-info='partnerInfo:main']").each(function(index, element){
								$(element).val("");
							});
							// 初始化选中的人员
							LV.initSelectedPerson(selectPersonDiv, selectModel);
						}
					});
					$(".add_box").hide();
				});
				// 选择人员 - 取消
				$(selectPersonDiv).find(".btn .cancel").unbind("click").click(function(){
					$('.cd-box3').removeClass('is-visible');
				});
			    
				// 选择人员 - 确定
				$(selectPersonDiv).find(".submit").unbind("click").click(function(){
					// 保存人员关系
					var roleId = "";
					var roleName = "";
					$(selectPersonDiv).find(".ry_box li[class='cd-popup-close che_on']").each(function(index, element){
						roleId += $(element).attr("data-roleId") + ";";
						roleName += $(element).text() + "；";
					});
					if(roleName != undefined && roleName != ""){
						roleName = roleName.substring(0,roleName.length - 1);
					}
					// 回调
					submitFun(roleName, roleId.trimEnd(";"));
				});
			},
			/**
			 * 初始化人员
			 * @param selectPersonDiv
			 * @param selectModel
			 */
			initSelectedPerson:function(selectPersonDiv, selectModel){
				$(selectPersonDiv).find(".ry_box ul").find("li").unbind("click").click(function(){
					if($(this).attr("class") == "cd-popup-close che_on"){
						$(this).addClass("off").removeClass("che_on");
					}else{
			    		// 未选中状态
			        	if(selectModel == "one"){
				            $(this).siblings().addClass("off").removeClass("che_on");
				            $(this).addClass("che_on").removeClass("off");
			        	} else if (selectModel == "more"){
			        		$(this).addClass("che_on").removeClass("off");
			        	}
					}
				});
			},
			/**
			 * 获取随字符串
			 * @returns {String}
			 */
			generateMixed:function(){
				var chars = ['0','1','2','3','4','5','6','7','8','9','A',
				             'B','C','D','E','F','G','H','I','J','K',
				             'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
			     var res = "";
			     for(var i = 0; i < 10 ; i ++) {
			         var id = Math.ceil(Math.random()*35);
			         res += chars[id];
			     }
			     return res;
			}


	};
	LV.init();
	window.location.LV =window.LV = LV;
}());