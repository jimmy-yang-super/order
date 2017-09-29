$(document).ready(function(){
	// 提交
	$(".control_area .next.pop_box2").click(function(){
		// 校验
		var validateArray = new Array();
		validateArray.push($(".edit_box"));

		if(!JX.validateArray(validateArray)){
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
		yinHangKHRelationSave();
	});
	
	// 暂存
	$(".control_area .save").click(function(){
		LV.enterpriseSave();
		yinHangKHRelationSave();
		alert("保存成功");
	});
});
function yinHangKHRelationSave(){
	$(".edit_box .box3").each(function(index, ele){
		var roleType = $(ele).find("ul li a[data-roleType]").attr("data-roleType");
		var roleIdArray = new Array();
		// 扩展信息
		var roleDataExt = {};
		
		var roleIds = $(ele).find("ul li input[data-roleIdArray]").attr("data-roleIdArray");
		if(roleIds != undefined && roleIds != ""){
			var roleIdList = roleIds.split(";");
			for(var i=0;i<roleIdList.length;i++){
				if(roleIdList[i] != undefined && roleIdList[i] != ""){
					roleIdArray.push(roleIdList[i]);
				}
			}
			
			$(ele).find("input[data-info='partnerInfo:ext']").each(function(idx, ele){
				roleDataExt[$(ele).attr("id")] =  $(ele).val();
			});						
		}
		var param = "&enterpriseId=" + enterpriseId + "&roleType=" + roleType + "&roleIdArray=" 
					+ JSON.stringify(roleIdArray) + "&roleDataExt=" + JSON.stringify(roleDataExt);
		$.ajax({
			url:"/enterprise/business/roleRelationSave",
			type:'post',    
		    data:param,
		    dataType:'json',
		    async:false,
			complete:function(data){
				
			}
		});
	});
}