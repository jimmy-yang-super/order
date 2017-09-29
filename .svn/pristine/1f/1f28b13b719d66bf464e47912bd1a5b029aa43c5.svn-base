
function initattach(){
	   var busid = $("#busid").val();
	  	jQuery.ajax({
			url : '/order/fileattachmentinit',
			type : "post",
			data : {
				"busid":busid
			},
			dataType : "text",
			success : function(data, textStatus) {
				if(data !=""){
					$("#initfile").html(data);
					 inituploa();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}); 
   }
   

	function disuploadFile(kehId,disfield){
		var kehname= $("#kehname_"+flag).val();
		var tempfield = disfield;
		if(disfield.indexOf("_")>-1){
			tempfield = disfield.split("_")[0];
		}
		 jQuery.ajax({
				url : '/order/displayUpload',
				type : "post",
				data : {
					"kehId" : kehId,
					"field" : tempfield
				},
				dataType : "text",
				success : function(data, textStatus) {
					if(data !="" && data != null){
						 var files = "";
						 var htmlstr ="";
						 var mmt = data.split(";");
						 if(disfield == 'gudsfzfyj'){
							 htmlstr ="";
							 htmlstr = 
								 " <div class=\"box2\">	<table> <tr><td class=\"name\">股东姓名："+kehname+"</td>";
							 htmlstr = htmlstr  + "<td class=\"pic\" align=\"left\">";
							 for(var i=0;i<mmt.length;i++){
								 files = files + mmt[i]+",";
								 htmlstr = htmlstr  +
								 " <div>  <p class=\"pic_box\"><img src=\"/file/download/"+mmt[i]+"\" width=\"150\" height=\"100\"><a class=\"del_btn\" onclick=\"deleFiless('"+mmt[i]+"','"+disfield+"','"+flag+"');\"><img src=\"/images/icon07.png\"></a></p></div>";
							 }
							 htmlstr = htmlstr  +"</td>";
							 if(files.indexOf(",")>0){
								 files = files.substring(0,files.lastIndexOf(","))
							 }
							 htmlstr = htmlstr + //"<td class=\"rang\"></td>"+
							 "<td class=\"btn\"><span class=\"add_btn\" id=\"xiaz_"+i+"\" onclick =\"download('"+files+"');\">下载</span></td>"+
							 //"<td class=\"del\"></td>"+
							 "</tr></table></div>";
							 $("#gudfile_"+flag).html(htmlstr);
						 }
						 if(disfield == 'faddbrshzfyj'){
							 htmlstr ="";
							 htmlstr = 
								 " <div class=\"box2\">	<table> <tr><td class=\"name\">法人代表姓名："+kehname+"</td>";
							 htmlstr = htmlstr  + "<td class=\"pic\" align=\"left\">";
							 for(var i=0;i<mmt.length;i++){
								 files = files + mmt[i]+",";
								 htmlstr = htmlstr  + 
								 " <div>  <p class=\"pic_box\"><img src=\"/file/download/"+mmt[i]+"\" width=\"150\" height=\"100\"><a class=\"del_btn\" onclick=\"deleFiless('"+mmt[i]+"','"+disfield+"','"+flag+"');\"><img src=\"/images/icon07.png\"></a></p></div> ";
							 }
							 htmlstr = htmlstr  + "</td>"
							 if(files.indexOf(",")>0){
								 files = files.substring(0,files.lastIndexOf(","))
							 }
							 htmlstr = htmlstr + //"<td class=\"rang\"></td>"+
							 "<td class=\"btn\"><span class=\"add_btn\" id=\"xiaz_"+i+"\" onclick =\"download('"+files+"');\">下载</span></td>"+
							// "<td class=\"del\"></td>"+
							 "</tr></table></div>";
							 $("#farfile_"+flag).html(htmlstr);
						 }
						 //增加其他角色客户信息   begin   add  wy
						 if(disfield.indexOf("faddbrshzfyj_") > -1){
							 var field_mc = disfield.split("_");
							 var mc = field_mc[1];
							 htmlstr ="";
							 htmlstr = 
								 " <div class=\"box2\">	<table> <tr><td class=\"name\">"+mc+"姓名："+kehname+"</td>";
							 htmlstr = htmlstr  + "<td class=\"pic\" align=\"left\">";
							 for(var i=0;i<mmt.length;i++){
								 files = files + mmt[i]+",";
								 htmlstr = htmlstr  + 
								 " <div>  <p class=\"pic_box\"><img src=\"/file/download/"+mmt[i]+"\" width=\"150\" height=\"100\"><a class=\"del_btn\" onclick=\"deleFiless('"+mmt[i]+"','"+disfield+"','"+flag+"');\"><img src=\"/images/icon07.png\"></a></p></div> ";
							 }
							 htmlstr = htmlstr  + "</td>"
							 if(files.indexOf(",")>0){
								 files = files.substring(0,files.lastIndexOf(","))
							 }
							 htmlstr = htmlstr + //"<td class=\"rang\"></td>"+
							 "<td class=\"btn\"><span class=\"add_btn\" id=\"xiaz_"+i+"\" onclick =\"download('"+files+"');\">下载</span></td>"+
							// "<td class=\"del\"></td>"+
							 "</tr></table></div>";
							 $("#farfile_"+flag).html(htmlstr);
						 }
			             // 增加其他角色客户信息   end   add  wy
						 if(disfield == 'yingyzzzb'){
							 htmlstr ="";
							 htmlstr = " <div class=\"box2\"><table><tr>"+
	                            "<td class=\"name\">营业执照正本</td>";
							 for(var i=0;i<data.length;i++){
								 files = files + data[i].value+",";
								 htmlstr = htmlstr  + "<td class=\"pic\">"+
								 " <div>  <p class=\"pic_box\"><img src=\"/file/download/"+data[i].value+"\" width=\"150\" height=\"100\"><a class=\"del_btn\" onclick=\"deleFiless('"+data[i].value+"','"+disfield+"',,'"+flag+"');\"><img src=\"/images/icon07.png\"></a></p></div> </td> ";
							 }
							 if(files.indexOf(",")>0){
								 files = files.substring(0,files.lastIndexOf(","))
							 }
							   htmlstr = htmlstr + "<td class=\"rang\"></td>"+
	                            "<td class=\"btn\"><span class=\"add_btn\" onclick =\"download('"+files+"');\">下载</span></td>"+
	                            "<td class=\"del\"></td>"+
	                            "</tr></table> </div>";
							   $("#disyyzzb_"+flag).html(htmlstr);
						 }
					}else{
						 if(disfield == 'gudsfzfyj'){
							 $("#gudfile_"+flag).html("");
						 }
                         if(disfield == 'faddbrshzfyj'){
                        	 $("#farfile_"+flag).html("");
						 }
                         if(disfield.indexOf("faddbrshzfyj_") > -1){
                        	 $("#farfile_"+flag).html("");
                         }
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			}); 
	}

	function deleFile(fileId,delfield){
		var kehId= $("#kehId_"+flag).val(); 
		var temlf = delfield;
		if(delfield.indexOf("_") > -1){
			temlf = delfield.split("_")[0];
		}
		 jQuery.ajax({
			url : '/order/deleFile',
			type : "post",
			data : {
				"kehId" : kehId,
				"field" : temlf,
				"fileId":fileId
			},
			dataType : "text",
			success : function(data, textStatus) {
				//迭代显示
	        	disuploadFile(kehId,delfield);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}); 
	}
	
	function deleFiless(fileId,delfield,mt){
		flag = mt;
		deleFile(fileId,delfield);
	}

	function download(files){
		if(files != ""){
			if(files.indexOf(",")>-1){
				var tmp = files.split(",");
				for(var i =0;i<tmp.length;i++){
				    window.location.href="/file/download/"+tmp[i];
				}
			}else{
				window.location.href="/file/download/"+files;
			}
		}
	}

	function saveFileattach(kehId,fileId){
		 var busid = $("#busid").val();
		 var subfield = field;
		 if(subfield.indexOf("_") >-1){
			 subfield = subfield.split("_")[0];
		 }
		 jQuery.ajax({
				url : '/order/addFileattach',
				type : "post",
				data : {
					"kehId" : kehId,
					"field" : subfield,
					"busid":busid,
					"fileId":fileId
				},
				dataType : "text",
				success : function(data, textStatus) {
					//迭代显示
		        	disuploadFile(kehId,field);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			}); 
	}
	
 	function initOtherfile(mapt){
		mapt.put("dwgdyyzz","单位股东营业执照：");
		mapt.put("yingyzzzb","营业执照正本:");
		mapt.put("yingyzzfb","营业执照副本:");
			
		mapt.put("hemjt","核名截图：");
		mapt.put("gongszc","公司章程（多页):");
		mapt.put("shuiwdjzb","税务登记证正本:");
			
		mapt.put("shuiwdjfb","税务登记证副本：");
		mapt.put("zuzjgdmzb","组织机构代码证正本:");
		mapt.put("zuzjgdmfb","组织机构代码证副本:");
			
		mapt.put("tongjzzb","统计证正本：");
		mapt.put("tongjzfb","统计证副本:");
		mapt.put("fangwcqzm","房屋产权证明（多页）:");
			 
		mapt.put("zhuszm","住所证明：");
		mapt.put("qiylxrdjb","企业联系人登记表:");
		mapt.put("dongjjbxxb","登记基本信息表:");
		
		mapt.put("shijbgdzlyzp","实际办公地址楼宇照片：");
		mapt.put("shijbgdzmpzp","实际办公地址楼宇门牌照片:");
		mapt.put("shijbgdzlsnzp","实际办公地址楼宇室内照片:");
		
		mapt.put("jizbgrzsysqwtsfj", "集中办公区入驻事宜授权委托书:");
	}
 	
	function saveOtherFile(fileId){
		var filebusid = $("#filebusid").val();
		 jQuery.ajax({
				url : '/order/addotherFileattach',
				type : "post",
				data : {
					"filebusid" : filebusid,
					"field" : field,
					"fileId":fileId
				},
				dataType : "text",
				success : function(data, textStatus) {
					//迭代显示
		        	disOtheruploadFile(data,field);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			}); 
	}
	
	function disOtheruploadFile(filaattid,disfield){
		var mingc= mrmap.get(disfield);
		 jQuery.ajax({
				url : '/order/displayOtherUpload',
				type : "post",
				data : {
					"filaattid" : filaattid,
					"field" : disfield
				},
				dataType : "json",
				success : function(data, textStatus) {
					if(data !="" && data != null){
						 var files = "";
						 var htmlstr ="";
						 htmlstr = " <div class=\"box2\"><table><tr>"+
                            "<td class=\"name\">"+mingc+"</td>";
						 htmlstr = htmlstr  + "<td class=\"pic\" align=\"left\">";
						 for(var i=0;i<data.length;i++){
							 files = files + data[i].value+",";
							 
							 htmlstr = htmlstr+" <div>  <p class=\"pic_box\"><img src=\"/file/download/"+data[i].value+"\" width=\"150\" height=\"100\"><a class=\"del_btn\" onclick=\"deleFileotherss('"+data[i].value+"','"+disfield+"','"+filaattid+"');\"><img src=\"/images/icon07.png\"></a></p></div> ";
						 }
						 htmlstr = htmlstr+"</td>"
						 if(files.indexOf(",")>0){
							 files = files.substring(0,files.lastIndexOf(","))
						 }
						   htmlstr = htmlstr + //"<td class=\"rang\"></td>"+
                            "<td class=\"btn\"><span class=\"add_btn\" onclick =\"download('"+files+"');\">下载</span></td>"+
                           // "<td class=\"del\"></td>"+
                            "</tr></table> </div>";
						   $("#"+disfield).html(htmlstr);
					}else{
						  $("#"+disfield).html("");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			}); 
	}
	
	function deleFileotherss(fileId,disfield,fileattid){
		 jQuery.ajax({
				url : '/order/deleOtherFile',
				type : "post",
				data : {
					"fileattid" : fileattid,
					"field" : disfield,
					"fileId":fileId
				},
				dataType : "text",
				success : function(data, textStatus) {
					//迭代显示
					disOtheruploadFile(fileattid,disfield);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			}); 
	}
