$(document).ready(function(){
	var WF = {
			// 初始化
			init: function(){
				WF.selectDiv();
				WF.uploadDiv();
				WF.imgShow();
			},
			
			taskFinish:function(){
				alert("操作成功");
				window.opener.location.reload();
				window.close();
			},
			
			getVariableByName:function(name){
				var value = "";
				$.ajax({
					url:"/wf/task/getvariable/" + procInstId + "/" + name,
					type:'post',    
				    cache:false,    
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
			
			// 图片点击放大事件
			imgShow:function(){
				$('input[name="taskInfoImg"]').each(function(index, element){
					var fileIds = $(element).val();
					var fileIdArray = fileIds.split(";");
					for(var i=0;i<fileIdArray.length;i++){
						var fileId = fileIdArray[i];
						if(fileId != ""){
							$(element).after("<img name=\"taskInfoImg\" alt=\"\" src=\"/file/download/" + fileId + "?maxLength=100\" /><a href=\"/file/download/" 
									+ fileId + "\" >下载</a>");
						}
					}
				});
				$('img[name="taskInfoImg"]').each(function(index, element){
					$(element).width(50);
					$(element).height(50);
					$(element).click(function(e){
						$("#taskInfoFade").remove();
	                    var width = $(this).width();
	                    var height = $(this).height();
	                    var size = 10;
	                    var dataSize = $(this).attr("data-size");
                    	$("<div>", { id: "taskInfoFade" }).html("<img id='i2' src='"+ $(element).attr("src") +"' />")
                    	.css({ "position": "absolute", "left": e.pageX/2, "top": e.pageY/2 }).fadeIn()
                    	.click(function(e) {
/*                          $(this).fadeOut(); 
                            $(this).parent("#taskInfoFade").remove();*/
                            e.stopPropagation();
                        }).appendTo("body");
                    	e.stopPropagation();
	                });
                    $(document).click(function() {
                         $("#taskInfoFade").remove();
					});
				});

			},
			
			// 弹出隐藏层
			showDiv:function(show_div){
				var bg_div = "fade";
				document.getElementById(show_div).style.display = 'block';
				document.getElementById(bg_div).style.display = 'block';
				var bgdiv = document.getElementById(bg_div);
				bgdiv.style.width = document.body.scrollWidth;
				//bgdiv.style.height = $(document).height();
				$("#" + bg_div).height($(document).height());
			},
			
			// 关闭弹出层
			closeDiv:function(show_div) {
				var bg_div = "fade";
				document.getElementById(show_div).style.display = 'none';
				document.getElementById(bg_div).style.display = 'none';
			},
			
			// 单选空间渲染
			selectDiv:function(){
				//　初始化值
				$(".nice-select4").each(function(index,element){
					var val = $(this).prev().val();
					var changeFlag = true;
					$(this).find("ul li").each(function(index, ele){
						if($(ele).attr("data-value") == val){
							changeFlag = false;
							$(this).parents('[name="nice-select4"]').find('input').val($(this).text());
						}
					});
					if(changeFlag == true && $(this).prev().attr("data-select") != "none"){
						var first = $(this).find("ul li:first");
						$(this).prev().val(first.attr("data-value"));
						$(this).find("input").val(first.text());
					}
				});
				
				$(".line_box li").each(function(){
					$(this).click(function(){
						$(".line_box li").addClass("line_change").removeClass("line_che");
						$(this).addClass("line_che").removeClass("line_change");
					})
				});

				$('[name="nice-select4"]').click(function(e) {
					$('[name="nice-select4"]').find('ul').hide();
					$(this).find('ul').show();
					e.stopPropagation();
				});
				$('[name="nice-select4"] li').hover(function(e) {
					$(this).toggleClass('on');
					e.stopPropagation();
				});
				$('[name="nice-select4"] li').click(function(e) {
					var val = $(this).text();
					var dataVal = $(this).attr("data-value");
					$(this).parents('[name="nice-select4"]').find('input').val(val);
					$(this).parents('[name="nice-select4"]').prev().val(dataVal);
					$('[name="nice-select4"] ul').hide();
					e.stopPropagation();
				});
				$(document).click(function() {
					$('[name="nice-select4"] ul').hide();
				});
			},
			
			getFileNameByFileId:function(fileId){
				if(fileId == "" || fileId == undefined )
					return "";
				var fileName = "";
				$.ajax({
					url:"/file/get/info/" + fileId,
					type:'post',    
				    cache:false,    
				    dataType:'json',
				    async:false,
					success:function(data){ // 流程处理成功
						fileName = data.fileName;
					},
					error:function(data){
						// 流程处理失败
//						console.log("error" + data);
					}
				});
				return fileName;
			},
			
			// 上传控件
			uploadDiv: function(){
				// 上传 
				$("[name='fileupload']").each(function(index, element){
					$(this).css("display","none");
					$(this).fileupload({
				        url: "/file/upload/" + enterpriseId + "?test=1",
				        dataType: 'json',
				        done: function (e, data) {
				        	var fileId = data.result.fileId;
				        	// 上传成功
				        	var url = "/file/download/" + fileId;
				        	var fileInputId = $(this).attr("fileId");
				        	var fileIds = $("#" + fileInputId).val();
				        	if(fileId != undefined && fileIds != ""){
				        		fileIds = fileIds + ";"
				        	}
				        	$("#" + fileInputId).val(fileIds + data.result.fileId);
				        	$(this).after("<div id='" + fileId + "Div' ><p class=\"pic_box\">" + data.result.fileName + "</p> " +
				        			" <a class=\"upload_btn del\" onclick=\"WF.deleteFileBtn('" + fileInputId + "', '" + fileId + "');\" href=\"javascript:void(0);\">删除</a></div>");
				        }
				    });
					var fileInputId = $(this).attr("fileId");
					var fileIds = $("#" + fileInputId).val();
					var fileIdArray = fileIds.split(";");
					for(var i=0;i<fileIdArray.length;i++){
						var fileId = fileIdArray[i];
						if(fileId == "" || fileId == undefined){
							continue;
						}
						var url = "/file/download/" + fileId;
						var fileName = WF.getFileNameByFileId(fileId);
			        	$(this).after("<div id='" + fileId + "Div' ><p class=\"pic_box\">" + fileName + "</p> " +
			        			" <a class=\"upload_btn del \" onclick=\"WF.deleteFileBtn('" + fileInputId + "', '" + fileId + "');\" href=\"javascript:void(0);\">删除</a></div>");
					}
				});
				
				$(".upload_btn").click(function(e){
					$(this).nextAll("#fileupload").click();
				});
			},
			
			
			// 文件删除按钮
			deleteFileBtn:function (fileInputId, fileId){
				$("#" + fileId + "Div").remove();
				var fileIds = $("#" + fileInputId).val();
				if(fileIds.indexOf(fileId) >= 0 ){
					fileIds = fileIds.replace(fileId + ";", "");
					fileIds = fileIds.replace(";" + fileId, "");
					fileIds = fileIds.replace(fileId, "");
				}
				$("#" + fileInputId).val(fileIds);
			},
			
			// 校验
			validate: function(winDiv){
				if(winDiv == "")
					return true;
				var requireFlag = true;
				$("#"+ winDiv).find("input[name='inputInfoAll'],[name='inputInfoTask'],[name='inputInfoCompany'],[name='inputInfoOther']").each(function(index,element){
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
						requireFlag = false;
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
										requireFlag = false;
										return false;
									}
								}
							}
							if(dataValidateKey == "min"){
								if(val == undefined || val.length < dataValidateValue ){
									alert(key + " 至少" + dataValidateValue + "个字" );
									requireFlag = false;
									return false;
								}
							}
						}
					}
				});
				return requireFlag;
			},
			
			// 任务提交
			submitTask: function(winDiv, taskData){
				var actionName = $("#" + winDiv).find("p.title").text();
				if(actionName == ""){
					actionName = "完成";
				}
				if(taskData == undefined){
					taskData = "";
				}
				taskData = taskData + "&taskActionName=" + actionName;
				if(winDiv != undefined && winDiv != ""){
					$("#" + winDiv).find("input[name='inputInfoAll'],[name='inputInfoTask']").each(function(index,element){
						taskData =  taskData + "&" + $(element).attr("id") + "=" + $(element).val();
					});
				}
				$.ajax({
					url:"/wf/task/submit/" + taskId,
					data:taskData,
					type:'post',    
				    cache:false,    
				    dataType:'json',
				    async:false,
					success:function(data){ // 流程处理成功
						
					},
					error:function(data){
						// 流程处理失败
//						console.log(data);
						return;
					}
				}, 300);
			},
			
			// 保存任务数据
			saveTask: function(winDiv, taskData){
				if(taskData == undefined){
					taskData = "";
				}
				var companyData = "";
				if(winDiv != undefined && winDiv != ""){
					$("#"+ winDiv).find("input[name='inputInfoAll'],[name='inputInfoTask']").each(function(index,element){
						taskData =  taskData + "&" + $(element).attr("id") + "=" + $(element).val();
					});
				}
				$.ajax({
					url:"/wf/task/save/" + taskId,
					data:taskData,
					type:'post',    
				    cache:false,    
				    dataType:'json',
				    async:false,
					success:function(data){ // 流程处理成功
						
					},
					error:function(data){
						// 流程处理失败
//						console.log(data);
						return;
					}
				}, 300);
			},
			
			//添加任务备注信息
			addTaskComment: function(winDiv, commentData) {
				if(commentData == undefined){
					commentData = "";
				}
				if(winDiv != undefined && winDiv != ""){
					$("#"+ winDiv).find("[data-info]").each(function(index,element){
						var tempFlag = $(this).attr("data-info");
						var datas = tempFlag.split(";");
						for(var i in datas){
							if(datas[i]=="inputInfoTaskComment" || datas[i]=="inputInfoAll"){
								commentData = commentData + "&" + $(element).attr("id") + "=" + $(element).val();
							}
						}
					});
				}
				$.ajax({
					url:"/wf/task/addComment/" + taskId + "/orderPay",
					data:commentData,
					type:'post',    
				    cache:false,    
				    dataType:'json',
				    async:false,
					success:function(data){ // 流程处理成功
					},
					error:function(data){
						// 流程处理失败
//						console.log(data);
						return;
					}
				}, 300);
			},
			//更新订单数据[暂时不调用]
			updateOrderInfo: function(winDiv, orderInfoData){
				if(orderInfoData == undefined){
					orderInfoData = "";
				}
				if(winDiv != undefined && winDiv != ""){
					$("#"+ winDiv).find("[data-info]").each(function(index,element){
						var tempFlag = $(this).attr("data-info");
						var datas = tempFlag.split(";");
						for(var i in datas){
							if(datas[i]=="inputInfoOrder" || datas[i]=="inputInfoAll"){
								orderInfoData = orderInfoData + "&" + $(element).attr("id") + "=" + $(element).val();
							}
						}
					});
				}
				$.ajax({
					url:"/wf/order/update/" + orderId,
					data:orderInfoData,
					type:'post',    
				    cache:false,    
				    dataType:'json',
				    async:false,
					success:function(data){ // 流程处理成功
						alert("操作成功");
						window.opener.location.reload();
						window.close();
						return;
					},
					error:function(data){
						// 流程处理失败
//						console.log(data);
						return;
					}
				}, 300);
			},
			//添加支付记录
			addPayProcess: function(winDiv, payProcessData){
				if(payProcessData == undefined){
					payProcessData = "";
				}
				if(winDiv != undefined && winDiv != ""){
					$("#"+ winDiv).find("[data-info]").each(function(index,element){
						var tempFlag = $(this).attr("data-info");
						var datas = tempFlag.split(";");
						for(var i in datas){
							if(datas[i]=="inputInfoPayProcess" || datas[i]=="inputInfoAll"){
								payProcessData = payProcessData + "&" + $(element).attr("id") + "=" + $(element).val();
							}
						}
					});
				}
				$.ajax({
					url:"/wf/payprocess/add/" + orderId,
					data:payProcessData,
					type:'post',    
				    cache:false,    
				    dataType:'json',
				    async:false,
					success:function(data){ // 流程处理成功
					},
					error:function(data){
						// 流程处理失败
//						console.log(data);
						return;
					}
				});
			},
			//添加支出记录
			addPayOutProcess: function(winDiv, payProcessData){
				if(payProcessData == undefined){
					payProcessData = "";
				}
				if(winDiv != undefined && winDiv != ""){
					$("#"+ winDiv).find("[data-info]").each(function(index,element){
						var tempFlag = $(this).attr("data-info");
						var datas = tempFlag.split(";");
						for(var i in datas){
							if(datas[i]=="inputInfoPayProcess" || datas[i]=="inputInfoAll"){
								payProcessData = payProcessData + "&" + $(element).attr("id") + "=" + $(element).val();
							}
						}
					});
				}
				$.ajax({
					url:"/wf/payout/process/add/" + orderId,
					data:payProcessData,
					type:'post',    
				    cache:false,    
				    dataType:'json',
				    async:false,
					success:function(data){ // 流程处理成功
					},
					error:function(data){
						// 流程处理失败
//						console.log(data);
						return;
					}
				});
			},
			
			// 暂停流程
			suspendProcess:function(processInstanceId){
				$.ajax({
					url:"/wf/process/suspend/" + processInstanceId,
					type:'post',
				    cache:false,
				    dataType:'json',
				    async:false,
					success:function(data){ // 流程处理成功
						alert("操作成功");
					},
					error:function(data){
						alert("操作失败");
						// 流程处理失败
//						console.log(data);
						return;
					}
				}, 300);
			},
			
			// 激活流程
			activateProcess:function(processInstanceId){
				$.ajax({
					url:"/wf/process/activate/" + processInstanceId,
					type:'post',
				    dataType:'json',
				    async:false,
					success:function(data){ // 流程处理成功
						alert("操作成功");
					},
					error:function(data){
						alert("操作失败");
						// 流程处理失败
//						console.log(data);
						return;
					}
				}, 300);
			},
			
			// 保存企业数据
			updateCompanyInfo: function(winDiv, companyData, businessId){
				if(companyData == undefined){
					companyData = "";
				}
				if(winDiv != undefined && winDiv != ""){
					$("#" + winDiv).find("input[name='inputInfoAll']").each(function(index,element){
						companyData =  companyData + "&" + $(element).attr("id") + "=" + $(element).val();
					});
				}
				if(winDiv == "" || winDiv == undefined){
					alert("操作成功");
					window.opener.location.reload();
					window.close();
					return;
				}
				$.ajax({
					url:"/wf/company/update/" + enterpriseId + "/" + businessId,
					data:companyData,
					type:'post',    
				    dataType:'json',
				    async:false,
					success:function(data){
						
					},
					error:function(data){
						alert("操作失败");
//						console.log(data);
						return;
					}
				}, 300);
			},
			
			// 更新企业附件
			updateCompanyAttach: function(winDiv, businessId){
				var attachInfo = "";
				if(winDiv != undefined && winDiv != ""){
					$("#"+ winDiv).find("[data-info]").each(function(index,element){
						var tempFlag = $(this).attr("data-info");
						var datas = tempFlag.split(";");
						for(var i in datas){
							if(datas[i]=="inputInfoCompanyAttach"){
								attachInfo = attachInfo + "&" + $(element).attr("id") + "=" + $(element).val();
							}
						}
					});
				}
				$.ajax({
					url:"/wf/companyattach/update/" + enterpriseId + "/" + businessId,
					data:attachInfo,
					type:'post',    
				    dataType:'json',
				    async:false,
					success:function(data){
						
					},
					error:function(data){
						alert("操作失败");
//						console.log(data);
						return;
					}
				}, 300);
			},
			// 更新企业客户信息
			updateCompanyKehu: function(winDiv){
				var attachInfo = "";
				if(winDiv != undefined && winDiv != ""){
					$("#"+ winDiv).find("[data-info]").each(function(index,element){
						var tempFlag = $(this).attr("data-info");
						var datas = tempFlag.split(";");
						for(var i in datas){
							if(datas[i]=="inputInfoCompanyKehu"){
								attachInfo = attachInfo + "&" + $(element).attr("id") + "=" + $(element).val();
							}
						}
					});
				}
				$.ajax({
					url:"/wf/companyKehu/update/" + enterpriseId,
					data:attachInfo,
					type:'post',    
				    dataType:'json',
				    async:false,
					success:function(data){
						
					},
					error:function(data){
						alert("操作失败");
//						console.log(data);
						return;
					}
				}, 300);
			}
			
		};
	WF.init();
	window.location.WF =window.WF = WF;
}());

