function citychangge(){
	var city = $("#city").find("option:selected").val(); 
	if(city =="2"){
		$("#dyncdldz").hide();
		if(!$('#dydlinfo').is(':hidden')){
			$('#dydlinfo').hide();
			$('#dyziyinfo').show();
			$("#a1").attr("checked",true);
		}
	}else{
		$("#dyncdldz").show();
	}
	jQuery.ajax({
		url : '/order/dyncmicarea',
		type : "post",
		data : {
			"parentId" : city
		},
		dataType : "json",
		success : function(data, textStatus) {
			if(data != ""){
				var htmlstr ="";
				for(var i =0;i<data.length;i++){
					if(data[i].AreasEntity.areaid =='101'||data[i].AreasEntity.areaid =='201'){
						htmlstr = htmlstr+ "<option selected=\"selected\" value=\""+data[i].AreasEntity.areaid+"\">"+data[i].AreasEntity.name+"</option>";
					}else{
						htmlstr = htmlstr+ "<option value=\""+data[i].AreasEntity.areaid+"\">"+data[i].AreasEntity.name+"</option>";
					}
				}
			}
			$("#area").html(htmlstr);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

function saveDldz(dzhzs,dzlx,fangjh,busid){
    
    var pcode = "100021";
    var dldz = "北京朝阳区西大望路甲12号2号楼国家广告产业园区孵化器"+fangjh;
    var dlsuosjx = "南磨房街道大望路甲12号2号楼";
    var dlchaqzt = "单位房";
    var dlfangwgh = "办公 ";
    var dlmj = "5";
    var dlchanqr2dw = "镕辉佳特创业孵化器(北京)有限公司 ";
    var dlzhussynx = "1";
    var dlzhuchqfs = "租赁";
    var dlchuzcode = "08883453";
	jQuery.ajax({
		url : '/order/addRemoteBusinessinfo',
		type : "post",
		data : {
			"dizhzs":dzhzs,
			"fangjh":fangjh,
			/*"dzlx":dzlx,
			
			"ziydz":dldz,
			"suosjx":dlsuosjx,
			"chanpzt":dlchaqzt,
			
			"chanqr2dw":dlchanqr2dw,
			
			"fangwghyt":dlfangwgh,
			
			"zhushqfs":dlzhuchqfs,
			"zulnf":dlzhussynx,
			"zulmj":dlmj,
			"chuzjscode":dlchuzcode,
			"postcode":pcode,*/
			"busid":busid
		},
		dataType : "text",
		success : function(data, textStatus) {
			if(data !="" && data !="0"){
				 alert("保存成功!!");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

function modifybusigsmc(divide){
	var type =$("#modifygsmc").val();
	var busid =$("#busid").val();
	var comptype =$("input[name='comptype']:checked").val();
	var gsmc = $("#gsmc").val();
    var pattern=/^[\u4e00-\u9fa5]+$/; 
	if(gsmc == null || gsmc ==""){
		alert("修改的公司名称不能为空！！");
		return false;
	}/*else{
		//验证必须为汉字
		 if (!pattern.test(gsmc))
		 { 
		    alert("输入公司名称必须为汉字。"); 
		    return false; 
		 }
	}*/
	var beixmc ="";
	for(var i =1 ;i<=beixcount;i++){
		if($("#beixmc"+i).val() != null && $("#beixmc"+i).val() !=""){
			/*if (!pattern.test($("#beixmc"+i).val()))
			 { 
			    alert("输入公司备选名称必须为汉字。"); 
			    return false; 
			 }*/
			beixmc = beixmc +$("#beixmc"+i).val()+"&";
		}
	}
	if (beixmc != "") {
		beixmc = beixmc.substring(0, beixmc.lastIndexOf("&"));
	}
	var zuczb = $("#zuczb").val();
	if(zuczb == null || zuczb ==""){
		alert("修改的公司注册资本不能为空！！");
		return false;
	}else{
		var type1="^\\d+$" ; 
	    var re   =   new   RegExp(type1); 
		if(zuczb.trim().match(re)==null){
			alert("输入的公司注册资本必须为整数！！");
			return false;
		}
	}
	jQuery.ajax({
		url : '/order/modifyBusinessinfo',
		type : "post",
		async:false,
		data : {
			"type" : type,
			"comptype" : comptype,
			"gongsmc" : gsmc,
			"beixmc" : beixmc,
			"zuczb" : zuczb,
			"busId":busid
		},
		dataType : "text",
		success : function(data, textStatus) {
			if(divide == '0'){
				alert("修改成功！！");
				$("#disgsdzapper").show();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	return true;
}

function saveaddress(divide){
	var orderid = $("#orderid").val();
	var busid = $("#busid").val();
	var ishaveadd =$("input[name='ishaveaddress']:checked").val();
	if(ishaveadd == null || ishaveadd ==""){
		alert("请选择注册地址！！");
		return false;
	}
	if(ishaveadd =="0"){
		//自有地址
		if(ziydz(orderid,busid,divide)){
			return true;
		}
	}
	if(ishaveadd =="1"){
		//代理地址
		if(dldz(orderid,busid,divide)){
			return true;
		}
	}
}
function ziydz(orderid,busid,divide){
	var city = $("#city").find("option:selected").val();
    var area = $("#area").find("option:selected").val();
    
    var ishaveaddress ="0";
    var ziydz = $("#ziydz").val();
    if(ziydz == null || ziydz ==""){
    	alert("请输入自有地址！！");
    	return false;
    }else{
    	var len = ziydz.length;
    	if(len >50){
    		alert("输入的自有地址最多为50个数字或字符串！");
    		return false;
    	}
    }
    var suosjx = $("#suosjx").val();
    var  chanqr2dw = $("#chanqr2dw").val();
    var  chanpzt = $("#chanpzt").find("option:selected").val();
    if(chanpzt == null || chanpzt == ""){
    	alert("请输入产权主体！！");
    	return false;
    }
    var fangwghyt = $("#fangwghyt").find("option:selected").val();
    var zhushqfs = $("#zhushqfs").find("option:selected").val();
    var zulnf =  $("#zulnf").val();
    var type="^\\d+$" ; 
    if(zulnf != null && zulnf !=""){
	    var re   =   new   RegExp(type); 
		if(zulnf.trim().match(re)==null){
			alert("输入的租赁/使用年限必须为整数！！");
			return false;
		}
    }
    var zulmj =  $("#zulmj").val();
    if(zulmj != null && zulmj !=""){
	    var re   =   new   RegExp(type); 
		if(zulmj.trim().match(re)==null){
			alert("输入的租赁/使用面积必须为整数！！");
			return false;
		}
    }
    var chuzjscode =$("#chuzjscode").val();
    var re= /^[1-9][0-9]{5}$/;
    var postcode = $("#postcode").val();
    if(postcode != null && postcode !=""){
        if(!re.test(postcode.trim())){
	       	 alert("请输入正确合法的邮编！！");
	       	 return false;
        }
   }
    var type = $("#type").val();
    
    var shijdz = $("#shijdz").val();
    var kuaitddz = $("#kuaitddz").val();
   /* if(kuaitddz == null || kuaitddz ==""){
    	alert("请输入快递地址！！");
    	return false;
    }*/
    var shoujr = $("#kuaitddz").val();
    var phone = $("#phone").val();
    var shoujr = $("#shoujr").val();
   /* if(shoujr != null && shoujr !=""){
    	var pattern=/^[\u4e00-\u9fa5]+$/; 
    	 if (!pattern.test(shoujr.trim()))
		 { 
		    alert("输入的收件人必须为汉字。"); 
		    return false; 
		 }
    }*/
	if(phone.length > 0){
		if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(phone))){
			alert("输入的联系电话有误！！");
			return false; 
		}else{
			if(phone.length != 11){
				alert("请输入联系电话有误！！");
				return false;
			}
		} 
	}
    var ptcode = $("#ptcode").val();
    if(ptcode != null && ptcode !=""){
        if(!re.test(ptcode.trim())){
       	 alert("请输入正确合法的邮编！！");
       	 return false;
        }
   }
    var regaddressid = $("#regaddressid").val();
	jQuery.ajax({
		url : '/order/addBusinessinfo',
		type : "post",
		async:false,
		data : {
			"type" : type,
			"city" : city,
			"area" : area,
			"ishaveaddress":ishaveaddress,
			"ziydz":ziydz,
			"suosjx":suosjx,
			"chanpzt":chanpzt,
			"chanqr2dw":chanqr2dw,
			"fangwghyt":fangwghyt,
			"zhushqfs":zhushqfs,
			"zulnf":zulnf,
			"zulmj":zulmj,
			"chuzjscode":chuzjscode,
			"postcode":postcode,
			"orderid":orderid,
			"busid":busid,
			"shijdz":shijdz,
			"kuaitddz":kuaitddz,
			"shoujr":shoujr,
			"ptcode":ptcode,
			"phone":phone,
			"regaddressid":regaddressid
		},
		dataType : "text",
		success : function(data, textStatus) {
			if(data !="" && data !="0"){
				if(divide == '0'){
					$("#busid").val(data);
					$("#jingyfwdis").show();
					alert("保存成功!!");
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	return true;
}
function dldz(orderid,busid,divide){
	var city = $("#city").find("option:selected").val();
    var area = $("#area").find("option:selected").val();
    
    var ishaveaddress ="1";
    var dizhzs = $("#dzhzs").find("option:selected").val();
//    var dzlx = $("#dzlx").find("option:selected").val();
    
/*    var pcode = $("#pcode").val();
    var dldz = $("#dldz").val()+$("#fangjh").val();
    var dlsuosjx = $("#dlsuosjx").val();
    var dlchaqzt = $("#dlchaqzt").val();
    var dlfangwgh = $("#dlfangwgh").val();
    var dlmj = $("#dlmj").val();
    var dlchanqr2dw = $("#dlchanqr2dw").val();
    var dlzhussynx = $("#dlzhussynx").val();
    var dlzhuchqfs = $("#dlzhuchqfs").val();
    var dlchuzcode = $("#dlchuzcode").val();*/
    
    var type = $("#type").val();
    
    var shijdz = $("#shijdz").val();
   /* if(shijdz == null || shijdz ==""){
    	alert("请输入实际办公地址！！");
    	return false;
    }*/
    var kuaitddz = $("#kuaitddz").val();
   /* if(kuaitddz == null || kuaitddz ==""){
    	alert("请输入快递地址！！");
    	return false;
    }*/
    var shoujr = $("#shoujr").val();
  /*  if(shoujr != null && shoujr !=""){
    	var pattern=/^[\u4e00-\u9fa5]+$/; 
    	 if (!pattern.test(shoujr.trim()))
		 { 
		    alert("输入的收件人必须为汉字。"); 
		    return false; 
		 }
    }*/
    var phone = $("#phone").val();
	if(phone.length > 0){
		if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(phone))){
			alert("输入的联系电话有误！！");
			return false; 
		}else{
			if(phone.length != 11){
				alert("请输入联系电话有误！！");
				return false;
			}
		} 
	}
    var ptcode = $("#ptcode").val();
    var re= /^[1-9][0-9]{5}$/;
    if(ptcode != null && ptcode !=""){
        if(!re.test(ptcode.trim())){
       	 alert("请输入正确合法的邮编！！");
       	 return false;
        }
   }
    var regaddressid = $("#regaddressid").val();
	jQuery.ajax({
		url : '/order/addBusinessinfo',
		type : "post",
		async:false,
		data : {
			"type" : type,
			"city" : city,
			"area" : area,
			"ishaveaddress":ishaveaddress,
			
			"dizhzs":dizhzs,
			"fangjh":$("#fangjh").val(),
			/*"dzlx":dzlx,
			
			"ziydz":dldz,
			"suosjx":dlsuosjx,
			"chanpzt":dlchaqzt,
			
			"chanqr2dw":dlchanqr2dw,
			
			"fangwghyt":dlfangwgh,
			
			"zhushqfs":dlzhuchqfs,
			"zulnf":dlzhussynx,
			"zulmj":dlmj,
			"chuzjscode":dlchuzcode,
			"postcode":pcode,*/
			
			"orderid":orderid,
			"busid":busid,
			"shijdz":shijdz,
			"kuaitddz":kuaitddz,
			"shoujr":shoujr,
			"ptcode":ptcode,
			"phone":phone,
			"regaddressid":regaddressid
		},
		dataType : "text",
		success : function(data, textStatus) {
			if(data !="" && data !="0"){
				if(divide == '0'){
					$("#busid").val(data);
					$("#jingyfwdis").show();
					alert("保存成功!!");
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	return true;
}

function saveHangy(divide){
	var orderid = $("#orderid").val();
	var busid = $("#busid").val();
	
	var hangylx = $("#hangylx").val();
	var zhuyyw = $("input:checkbox[name='zhuyyw']:checked").val();
	var qityw ="";
	//$("input:checkbox[name='qityw']:checked").each(function() {
	//	qityw = qityw + $(this).val()+"&"; 
	//});
	$(".bgBlue").each(function() {
		qityw = qityw + $(this).attr("bindValue")+"&"; 
	});
	if (qityw != "") {
		qityw = qityw.substring(0, qityw.lastIndexOf("&"));
	}
	var orderid = $("#orderid").val();
	var busid = $("#busid").val();
	
	var type = $("#addjingyfw").val();
	
	var zdjyfw = $("#zdjyfw").val();
	//补充没填时，上述必填
	if(zdjyfw == null || zdjyfw ==""){
		if(zhuyyw =="" || zhuyyw == null){
			alert("请选择业务方向!!");
			return false;
		}
		if(qityw =="" || qityw == null){
			alert("请选择经营范围!!");
			return false;
		}
	}
	
	//保存
	jQuery.ajax({
		url : '/order/addBusinessother',
		type : "post",
		async:false,
		data : {
			"type" : type,
			"orderid":orderid,
			"busid":busid,
			"hangylx":hangylx,
			"zhuyyw":zhuyyw,
			"qityw":qityw,
			"zdjyfw":zdjyfw
		},
		dataType : "text",
		success : function(data, textStatus) {
			if(data !="" && data !="0"){
				$("#busid").val(data);
				if(divide == '0'){
					alert("保存成功!!");
					$("#zirgud").show();
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	return true;
}

function saveotherInfo(divide){
    var type= $("#addotherinfo").val();
    var issource = $("#issource").val();
    if(issource == "2"){
    	var shenqfs =$("input[name='shenqfs']:checked").val();
    	if(shenqfs == null || shenqfs ==""){
    		alert("请选择申请方式！！！");
    		return false;
    	}
    }
    var mingchzwh = $("#mingchzwh").val();
    var hemgszh = $("#hemgszh").val();
    //核名工商账号
    /*if(hemgszh == null || hemgszh ==""){
    	alert("请输入核名工商账号！！！");
    	return false;
    }*/
    var hempassword = $("#hempassword").val();
  /*  if(hempassword == null || hempassword ==""){
    	alert("请输入核名工商账号密码！！！");
    	return false;
    }*/
    //网登工商账号
    var wangdgszh = $("#wangdgszh").val();
   /* if(wangdgszh == null || wangdgszh ==""){
    	alert("请输入网登工商账号！！！");
    	return false;
    }*/
    var wangdmm = $("#wangdmm").val();
   /* if(wangdmm == null || wangdmm ==""){
    	alert("请输入网登工商账号密码！！！");
    	return false;
    }*/
    
    var orderid = $("#orderid").val();
	var busid = $("#busid").val();
  	jQuery.ajax({
		url : '/order/addBusinessother',
		type : "post",
		async:false,
		data : {
			"type" : type,
			"orderid":orderid,
			"busid":busid,
			//"gsxtcount":gsxtcount,
			//"password":password,
			"mingchzwh":mingchzwh,
			"hemgszh":hemgszh,
			"hempassword":hempassword,
			"wangdgszh":wangdgszh,
			"wangdmm":wangdmm
		},
		dataType : "text",
		success : function(data, textStatus) {
			if(data !="" && data !="0"){
				$("#busid").val(data);
				if(divide == '0'){
					 alert("保存成功!!");
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
  	if(divide == '0'){
  		var issource = $("#issource").val();
  		if(issource =="2"){
  			var taskData = commonDeal();
  			window.opener.location.WF.saveTask("111", taskData, "1");
  		}
  	}
  	return true;
}
/*判断输入是否为合法的电话号码，匹配固定电话或小灵通*/

function isphone(inpurStr)
{
     var partten = /^0(([1,2]\d)|([3-9]\d{2}))\d{7,8}$/;
     if(!partten.test(inpurStr))
     {
          return true;
     }
}

function isok(str)
{    
	if(""==str)
	{    
	     return false;    
	}    
	if(str.length!=15&&str.length!=18)
	{//身份证长度不正确    
	    return false;    
	}    
	if(str.length==15)
	{    
		if(!isNumber(str))
		{    
		  return false;    
		}    
	}
	else
	{
		var str1 = str.substring(0,17);    
		var str2 = str.substring(17,18);    
		var alpha = "X0123456789";    
		if(!isNumber(str1)||alpha.indexOf(str2)==-1)
		{    
		    return false;    
		}    
	}    
	return true;    
} 
function isNumber( s ){
	var regu = "^[0-9]+$"; 
	var re = new RegExp(regu); 
	if (s.search(re) != -1) {
		return true;
       } else { 
    	return false; 
    	}
 } 
function savegsyginfo(divide){
    var type =$("#addgsyginfo").val();
    var qiylxr = $("#qiylxr").val();
    if(qiylxr == null || qiylxr ==""){
    	alert("请输入企业联系人！");
    	return false;
    }
     var qiylxrphone = $("#qiylxrphone").val();
     if(qiylxrphone.length > 0){
  		if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(qiylxrphone.trim()))){
  			alert("输入的企业联系人的手机号有误！！");
  			return false; 
  		}else{
  			if(qiylxrphone.length != 11){
  				alert("请输入企业联系人的手机号有误！！");
  				return false;
  			}
  		} 
  	}else{
  		alert("请输入的企业联系人的手机号！！");
	    return false; 
  	}
     var guddh = $("#guddh").val();
     if(guddh != null && guddh != ""){
    	 if(isphone(guddh)){
    		 alert("输入的固定电话格式有误！！");
    		 return false;
    	 }
     }
     var email = $("#email").val();
     if(email != null && email !=""){
    	 var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;  
         if (!pattern.test(email)) {  
            alert("请输入正确的邮箱地址。");  
             return false;  
        } 
     }
     var postcode = $("#qiypostcode").val();
     var re= /^[1-9][0-9]{5}$/;
     var postcode = $("#qiypostcode").val();
     if(postcode != null && postcode !=""){
         if(!re.test(postcode.trim())){
        	 alert("请输入正确合法的邮编！！");
        	 return false;
         }
    }
     var zhigcount = $("#zhigcount").val();
     var bendrs = $("#bendrs").val();
     var waidrs = $("#waidrs").val();
     var nvxrw = $("#nvxrw").val();
     var type1="^\\d+$" ; 
	   var re   =   new   RegExp(type1); 
	   var sumzhig = 0;
	   var bcon = 0;
	   var wcon = 0;
	   var nvrw = 0;
	   if(zhigcount != null && zhigcount !=""){
		   zhigcount = zhigcount.trim();
	    	if(zhigcount.match(re)==null){
				alert("输入的职工总人数必须为整数！！");
				return false;
			}
	    	sumzhig = parseInt(zhigcount);
	    }else{
	    	alert("请输入的职工总人数！！");
			return false;
	    }
	   
	   if(bendrs != null && bendrs !=""){
		   bendrs = bendrs.trim();
	    	if(bendrs.match(re)==null){
				alert("输入的本地人数必须为整数！！");
				return false;
			}
	    	bcon = parseInt(bendrs);
	    }else{
	    	alert("请输入的本地人数！！");
			return false;
	    }
	   if(waidrs != null && waidrs !=""){
		   waidrs = waidrs.trim();
	    	if(waidrs.match(re)==null){
				alert("输入的外地人数必须为整数！！");
				return false;
			}
	    	wcon = parseInt(waidrs);
	    }else{
	    	alert("请输入的外地人数！！");
			return false;
	    }
	   if(nvxrw != null && nvxrw !=""){
		   nvxrw = nvxrw.trim();
	    	if(nvxrw.match(re)==null){
				alert("输入的女性人数必须为整数！！");
				return false;
			}
	    	nvrw = parseInt(nvxrw);
	    }else{
	    	alert("请输入的女性人数！！");
			return false;
	    }
	  var mt = bcon + wcon;
	  if(mt != sumzhig){
		  alert("输入的职工数不等于本地职工数加外地职工数总和！！");
		  return false;
	  }
	  if(nvrw > sumzhig){
		  alert("输入的女性人数大于职工总人数！！");
		  return false;
	  }
      var Ukey1 = $("#Ukey1").val();
      var Ukey1phone = $("#Ukey1phone").val();
    /*  if(Ukey1 == null || Ukey1 ==""){
      	alert("请输入Ukey管理员1！");
      	return false;
      }*/
      if(Ukey1phone.length > 0){
   		if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(Ukey1phone))){
   			alert("输入的Ukey管理员1的手机号有误！！");
   			return false; 
   		}else{
   			if(Ukey1phone.length != 11){
   				alert("请输入Ukey管理员1的手机号有误！！");
   				return false;
   			}
   		} 
   	}
      var Ukey2 = $("#Ukey2").val();
      var Ukey2phone = $("#Ukey2phone").val();
     /* if(Ukey2 == null || Ukey2 ==""){
        	alert("请输入Ukey管理员2！");
        	return false;
      }*/
      if(Ukey2phone.length > 0){
     		if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(Ukey2phone))){
     			alert("输入的Ukey管理员2的手机号有误！！");
     			return false; 
     		}else{
     			if(Ukey2phone.length != 11){
     				alert("请输入Ukey管理员2的手机号有误！！");
     				return false;
     			}
     		} 
     	}
      var orderid = $("#orderid").val();
  	  var busid = $("#busid").val();
  	jQuery.ajax({
		url : '/order/addBusinessother',
		type : "post",
		async:false,
		data : {
			"type" : type,
			"orderid":orderid,
			"busid":busid,
			
			"qiylxr":qiylxr,
			"qiylxrphone":qiylxrphone,
			"guddh":guddh,
			"email":email,
			"postcode":postcode,
			"zhigcount":zhigcount,
			"bendrs":bendrs,
			"waidrs":waidrs,
			"nvxrw":nvxrw,
			"Ukey1":Ukey1,
			"Ukey1phone":Ukey1phone,
			"Ukey2":Ukey2,
			"Ukey2phone":Ukey2phone
		},
		dataType : "text",
		success : function(data, textStatus) {
			if(data !="" && data !="0"){
				 $("#busid").val(data);
				 if(divide == '0'){
					 alert("保存成功!!");
					 $("#otherinfodis").show();
				 }
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
  	return true;
}
function savefardb2ds(divide){
     
     var farzx = $("#farzx").val();
     if(farzx == null || farzx ==""){
        alert("请输入法定代表人！");
        return false;
     }
     
     var phone = $("#farphone").val();
     if(phone.length > 0){
 		if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(phone))){
 			alert("输入法定代表人手机有误！！");
 			return false; 
 		}else{
 			if(phone.length != 11){
 				alert("请输入法定代表人手机有误！！");
 				return false;
 			}
 		} 
 	}else{
 		alert("输入法定代表人手机号！！");
	    return false; 
 	}
     var farcard = $("#farcard").val();
     if(farcard != null && farcard !=""){
     	if(!isok(farcard)){
     		alert("请输入合法的法定代表人身份证!!");
     		return false;
     	}
     }
     var ishavedongsh = $("input[name='ishavedongsh']:checked").val();
     if(ishavedongsh == '0'){
         var zhixgongsh = $("#zhixgongsh").val();
         if(zhixgongsh == null || zhixgongsh ==""){
             alert("请输入执行董事！");
             return false;
          }
    	 var zhixgongshphone =$("#zhixgongshphone").val();
    	 if(zhixgongshphone == null || zhixgongshphone ==""){
    	        alert("请输入执行董事手机号！");
    	        return false;
    	 }else{
    		 if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(zhixgongshphone))){
    	  			alert("输入执行董事手机有误！！");
    	  			return false; 
    	  		}else{
    	  			if(zhixgongshphone.length != 11){
    	  				alert("输入执行董事手机有误！！");
    	  				return false;
    	  			}
    	  		}  
    	 }
     }else{
    	 var dongshzx1 = $("#dongshzx1").val();
         if(dongshzx1 == null || dongshzx1 ==""){
             alert("请输入董事会主席！");
             return false;
          }
    	 var dongshzxphone1 = $("#dongshzxphone1").val();
    	 if(dongshzxphone1 == null || dongshzxphone1 ==""){
 	        alert("请输入董事会主席手机号！");
 	        return false;
	 	 }else{
	 		 if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(dongshzxphone1))){
	 	  			alert("输入董事会主席手机有误！！");
	 	  			return false; 
	 	  		}else{
	 	  			if(dongshzxphone1.length != 11){
	 	  				alert("输入董事会主席手机有误！！");
	 	  				return false;
	 	  			}
	 	  		}  
	 	 }
     }
/*     var ishavejiansh = $("input[name='ishavejiansh']:checked").val();
     if(ishavejiansh == '0'){
    	 var jians = $("#jians").val();
    	 if(jians == null || jians ==""){
             alert("请输入监事！");
             return false;
          }
    	 var jiansphone =$("#jiansphone").val();
    	 if(jiansphone == null || jiansphone ==""){
 	        alert("请输入监事手机号！");
 	        return false;
	 	 }else
    	 if(jiansphone != null && jiansphone !=""){
	 		 if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(jiansphone))){
	 	  			alert("输入监事手机有误！！");
	 	  			return false; 
	 	  		}else{
	 	  			if(jiansphone.length != 11){
	 	  				alert("输入监事手机有误！！");
	 	  				return false;
	 	  			}
	 	  		}  
	 	 }
     }else{
    	 var zhixjianszx1 =  $("#zhixjianszx1").val();
    	 if(zhixjianszx1 == null || zhixjianszx1 ==""){
             alert("请输入监事会主席！");
             return false;
          }
    	 var zhixjianszxphone1 = $("#zhixjianszxphone1").val();
    	 if(zhixjianszxphone1 == null || zhixjianszxphone1 ==""){
  	        alert("请输入监事会主席手机号！");
  	        return false;
 	 	 }else
    	 if(zhixjianszxphone1 != null && zhixjianszxphone1 !=""){
 	 		 if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(zhixjianszxphone1))){
 	 	  			alert("输入监事会主席有误！！");
 	 	  			return false; 
 	 	  		}else{
 	 	  			if(zhixjianszxphone1.length != 11){
 	 	  				alert("输入监事会主席手机有误！！");
 	 	  				return false;
 	 	  			}
 	 	  		}  
 	 	 }
     }*/
     var jingl = $("#jingl").val();
      if(jingl == null || jingl ==""){
        alert("请输入经理姓名！");
        return false;
     }
      var jinglcard = $("#jinglcard").val();
      if(jinglcard != null && jinglcard !=""){
      	if(!isok(jinglcard)){
      		alert("输入合法的经理身份证!!");
      		return false;
      	}
      }
      var ty =$("#jinglphone").val();
      if(ty.length > 0){
  		if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(ty))){
  			alert("输入经理手机有误！！");
  			return false; 
  		}else{
  			if(ty.length != 11){
  				alert("输入经理手机有误！！");
  				return false;
  			}
  		} 
  	}else{
  		alert("请输入经理手机号！！");
	    return false; 
  	}
     var caiwfzr = $("#caiwfzr").val();
      if(caiwfzr == null || caiwfzr ==""){
        alert("请输入财务负责人！");
        return false;
     }
      var caiwsfz = $("#caiwsfz").val();
      if(caiwsfz != null && caiwsfz !=""){
       	if(!isok(caiwsfz)){
       		alert("请输入合法的财务负责人身份证!!");
       		return false;
       	}
       }
      var cyp = $("#caiwphone").val();
      if(cyp.length > 0){
   		if(!(/^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/.test(cyp))){
   			alert("输入财务负责人手机有误！！");
   			return false; 
   		}else{
   			if(cyp.length != 11){
   				alert("请输入财务负责人手机有误！！");
   				return false;
   			}
   		} 
   	}else{
   		alert("输入财务负责人手机号！！");
		return false; 
   	}
   //提交
     $("#adddscount").val(addds);
     $("#addjscount").val(addjs);
	jQuery.ajax({
		url : '/order/saveKehinfo',
		type : "post",
		async:false,
		data : $('#myForm').serialize(),
		dataType : "text",
		success : function(data, textStatus) {
			if(data !="" && data !="0"){
				if(divide == '0'){
					alert("保存成功!!");
//					$("#employinfo").show();
					window.location.reload();
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	return true;
 }
 function modify(divide){
	 $("#zirrgudcount").val(gudjs);
     $("#fargudcount").val(farjs);
     if(gudjs == 0 && farjs==0){
     	alert("对不起，目前没有要保存的信息！！");
     	return false;
     }
    /* if(gudjs > 0){
    	 var gd =$("#gudong"+gudjs).val();
    	 if(gd == null || gd ==""){
    		 alert("请输入股东姓名！！");
    		 return false;
    	 }
    	 var chuzf =$("#chuzf"+gudjs).val();
    	 if(chuzf == null || chuzf ==""){
    		 alert("请输入股东出资额！！");
    		 return false;
    	 }
     }*/
    /* if(farjs >0){
    	 var fd =$("#fargud"+farjs).val();
    	 if(fd == null || fd ==""){
    		 alert("请输入法人股东姓名！！");
    		 return false;
    	 }
    	 var farchuze1 = $("#farchuze"+farjs).val();
    	 if(farchuze1 == null || farchuze1 ==""){
    		 alert("请输入法人股东出资额！！");
    		 return false;
    	 }
     }*/
  	jQuery.ajax({
		url : '/order/modifykeh',
		type : "post",
		async:false,
		data : $('#savegudform').serialize(),
		dataType : "text",
		success : function(data, textStatus) {
			if(data !="" && data !="0"){
				if(divide == '0'){
					alert("保存成功!!");
//					$("#fadingdb").show();
					window.location.reload();
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
  	return true;
}
 
 function changeDizhzs(){
	   var dldzcode = $("#dzhzs").find("option:selected").val();
	   var fjha = $("#fjh").val();
	  	jQuery.ajax({
			url : '/order/changeDizhzs',
			type : "post",
			data :{
				"dldzcode":dldzcode
			},
			dataType : "json",
			success : function(data, textStatus) {
				if(data !=""){
					 var mt = data.dizlx.split(",");
					 var dzlx = "";
					 for(var i =0;i<mt.length;i++){
						 dzlx = dzlx +" <option value=\""+mt[i]+"\" selected=\"selected\">"+mt[i]+"</option>";
					 }
					 $("#dzlx").html(dzlx);
					 if(typeof(data.postcode) == "undefined"){
						 $("#dzpcode").text("");
					 }else{
						 $("#dzpcode").text(data.postcode);
					 }
					 if(data.ziydz.indexOf("***")>-1){
						 $("#dzhzszydz").html(data.ziydz.replace("***","<input type=\"text\"  placeholder=\"房间号\" name=\"fangjh\" value=\""+fjha+"\" id=\"fangjh\">"));
					 }else if(data.ziydz.indexOf("xxx")>-1){
						 $("#dzhzszydz").html(data.ziydz.replace("xxx","<input type=\"text\"  placeholder=\"房间号\" name=\"fangjh\" value=\""+fjha+"\" id=\"fangjh\">"));
					 }else{
						 $("#dzhzszydz").html(data.ziydz+"<input type=\"text\"  placeholder=\"房间号\" name=\"fangjh\" id=\"fangjh\" value=\""+fjha+"\">");
					 }
					 if(typeof(data.suosjx) == "undefined"){
						 $("#dzhzsssjx").text("");
					 }else{
						 $("#dzhzsssjx").text(data.suosjx);
					 }
					 
					 if(typeof(data.chanqzt) == "undefined"){
						 $("#hzschanqzt").text("");
					 }else{
						 $("#hzschanqzt").text(data.chanqzt);
					 }
					 
					 if(typeof(data.chanqr) == "undefined"){
						 $("#hzschanqr").text("");
					 }else{
						 $("#hzschanqr").text(data.chanqr);
					 }
					 if(typeof(data.fangwzlyt) == "undefined"){
						 $("#hzsfangwyt").text("");
					 }else{
						 $("#hzsfangwyt").text(data.fangwzlyt);
					 }
					 if(typeof(data.fangwhqfs) == "undefined"){
						 $("#hzszshqfs").text("");
					 }else{
						 $("#hzszshqfs").text(data.fangwhqfs);
					 }
					 if(typeof(data.zulsymj) == "undefined"){
						 $("#hzsmj").text("");
					 }else{
						 $("#hzsmj").text(data.zulsymj+"平米");
					 }
					 if(typeof(data.zulsynx) == "undefined"){
						 $("#hzsnx").text("");
					 }else{
						 $("#hzsnx").text(data.zulsynx+"年");
					 }
					 if(typeof(data.chuzfcard) == "undefined"){
						 $("#hzscode").text("");
					 }else{
						 $("#hzscode").text(data.chuzfcard);
					 }
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
 }