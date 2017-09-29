package com.jixiang.argo.lvzheng.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bj58.sfft.json.orgjson.JSONException;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.FileattachmentEntity;
import com.jx.service.newcore.entity.KehinfosEntity;

/**
 * 附件业务处理
 * @author wuyin
 *
 */
public class FileattachmentService {

	private static FileattachmentService instance = null;
	
	public static FileattachmentService getInstance(){
		if(instance == null){
			instance = new FileattachmentService();
		}
		return instance;
	}
	public Map getFrgdstr(long lbuid,int i,int roletype,String mingc){
		Map map = new HashMap();
		String text ="";
		List<KehinfosEntity> far = BusinessLibraryService.getInstance().getKehByRoletypeObj(lbuid, roletype);
		if(StringUtil.isListNull(far)){
			int m = 1;
			for(KehinfosEntity kh : far){
				text = text +"<div class=\"box1\"><p class=\"tit\">"+mingc+"身份证复印件：</p>"+
			           "<input mindex=\""+i+"\" name=\"kehId_"+i+"\" id=\"kehId_"+i+"\" value=\""+kh.getKehid()+"\" type=\"hidden\" />"+
			           "<input mindex=\""+i+"\" name=\"kehname_"+i+"\" id=\"kehname_"+i+"\" value=\""+kh.getUsername()+"\" type=\"hidden\" />"+
			           "<input id=\"fileupload\" style=\"display:none\" type=\"file\" fileId=\"CheckNameInfo_shareholder4IDFile\" name=\"fileupload\" multiple />"+
                       "<p class=\"add_box\"><span class=\"name\">"+mingc+"姓名"+m+"："+kh.getUsername()+"</span> <span class=\"add_btn\" onclick=\"uploadfile('fileupload','"+i+"','faddbrshzfyj_"+mingc+"')\">上传证件</span> </p> </div>"+
                       "<div id=\"farfile_"+i+"\" > ";
				if(kh != null){
					String jsonstr = kh.getFaddbrshzfyj();
					if(StringUtil.isEmpty(jsonstr)){
						text = text + " <div class=\"box2\">	<table> <tr><td class=\"name\">"+mingc+"姓名"+m+"："+kh.getUsername()+"</td> <div id=\"farfile_"+i+"\"></div>";
						String files ="";
						text = text + "<td class=\"pic\" align=\"left\">";
						String tmp[] = jsonstr.split(";");
						for (int t = 0; t < tmp.length; t++) {
							files = files +tmp[t]+",";
							text = text + //"<td class=\"pic\">"+
		                      " <div> <p class=\"pic_box\"><img src=\"/file/download/"+tmp[t]+"\" width=\"150\" height=\"100\"><a class=\"del_btn\" onclick=\"deleFiless('"+tmp[t]+"','faddbrshzfyj_"+mingc+"','"+i+"');\"><img src=\"/images/icon07.png\"></a></p> </div> ";
						}
						text = text + " </td>";
						
						if(files.indexOf(",")>-1){
							files = files.substring(0, files.lastIndexOf(","));
						}
						text = text + //"<td class=\"rang\"></td>"+
				                  "<td class=\"btn\"><span class=\"add_btn\" id=\"xiaz_"+i+"\" onclick =\"download('"+files+"');\">下载</span></td>"+
				                 // "<td class=\"del\"></td>"+
				                  "</tr></table></div>";
					}
				}
				text = text + "</div>";
				i++;
				m++;
			}
		}
		map.put("text", text);
		map.put("i", i);
		return map;
	}
	public String initFileattachmeetdis(String busid){
		String text ="";
		if(StringUtil.isEmpty(busid)){
			long lbuid = Long.valueOf(busid);
			//获得股东
			List<KehinfosEntity> zirr = BusinessLibraryService.getInstance().getKehByRoletypeObj(lbuid, 0);
			int i =1;
			if(StringUtil.isListNull(zirr)){
				for(KehinfosEntity kh : zirr){
					text = text +"<div class=\"box1\"><p class=\"tit\">股东身份证复印件：</p>"+
				           "<input mindex=\""+i+"\" name=\"kehId_"+i+"\" id=\"kehId_"+i+"\" value=\""+kh.getKehid()+"\" type=\"hidden\" />"+
				           "<input mindex=\""+i+"\" name=\"kehname_"+i+"\" id=\"kehname_"+i+"\" value=\""+kh.getUsername()+"\" type=\"hidden\" />"+
                           "<p class=\"add_box\"><span class=\"name\">股东姓名："+kh.getUsername()+"</span> <span class=\"add_btn\" onclick=\"uploadfile('fileupload','"+i+"','gudsfzfyj')\">上传证件</span> </p> </div>"+
                           "<div id=\"gudfile_"+i+"\" > ";
					if(kh != null){
						String jsonstr = kh.getGudsfzfyj();
						if(StringUtil.isEmpty(jsonstr)){
							text = text + " <div class=\"box2\">	<table> <tr><td class=\"name\">股东姓名："+kh.getUsername()+"</td> <div id=\"gudfile_"+i+"\"></div>";
							String files ="";
							text = text +"<td class=\"pic\"  align=\"left\">";
							String[] tmp = jsonstr.split(";");
							for (int t = 0; t < tmp.length; t++) {
								files = files +tmp[t]+",";
								text = text + //"<td class=\"pic\"  align=\"left\">"+
			                      " <div> <p class=\"pic_box\"><img src=\"/file/download/"+tmp[t]+"\" width=\"150\" height=\"100\"><a class=\"del_btn\" onclick=\"deleFiless('"+tmp[t]+"','gudsfzfyj','"+i+"');\"><img src=\"/images/icon07.png\"></a></p></div> ";
							}
							text = text +" </td>";
							if(files.indexOf(",")>-1){
								files = files.substring(0, files.lastIndexOf(","));
							}
							text = text + //"<td class=\"rang\"></td>"+
					                  "<td class=\"btn\"><span class=\"add_btn\" id=\"xiaz_"+i+"\" onclick =\"download('"+files+"');\">下载</span></td>"+
					                  //"<td class=\"del\"></td>"+
					                  "</tr></table></div>";
						}
					}
					text = text + "</div>";
					i++;
				}
			}
			//法人股东
			Map frgdstr = getFrgdstr(lbuid,i,1,"自然人股东");
			String fargd = frgdstr.get("text").toString();
			i = Integer.valueOf(frgdstr.get("i").toString());
			//获得法人代表
			Map frgdstr2 = getFrgdstr(lbuid,i,2,"法定代表人");
			String farstr = frgdstr2.get("text").toString();
			i = Integer.valueOf(frgdstr2.get("i").toString());
			//法定代表人及董监高
			String djs = getAppendDjg(lbuid,i);
			text = text +fargd+ farstr + djs;
			//拼接其他附件
			text = text + getOtherattach(lbuid);
			
		}
		return text;
	}
	public String getappendStrByroletype(long buid,int roletype,int i,String mingc){
		 KehinfosEntity kh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(buid, roletype);
		 String text ="";
		 if(kh != null){
			 text = text +"<div class=\"box1\"><p class=\"tit\">"+mingc+"身份证复印件：</p>"+
			           "<input mindex=\""+i+"\" name=\"kehId_"+i+"\" id=\"kehId_"+i+"\" value=\""+kh.getKehid()+"\" type=\"hidden\" />"+
			           "<input mindex=\""+i+"\" name=\"kehname_"+i+"\" id=\"kehname_"+i+"\" value=\""+kh.getUsername()+"\" type=\"hidden\" />"+
			           "<input id=\"fileupload\" style=\"display:none\" type=\"file\" fileId=\"CheckNameInfo_shareholder4IDFile\" name=\"fileupload\" multiple />"+
                     "<p class=\"add_box\"><span class=\"name\">"+mingc+"姓名："+kh.getUsername()+"</span> <span class=\"add_btn\" onclick=\"uploadfile('fileupload','"+i+"','faddbrshzfyj_"+mingc+"')\">上传证件</span> </p> </div>"+
                     "<div id=\"farfile_"+i+"\" > ";
				String jsonstr = kh.getFaddbrshzfyj();
				if(StringUtil.isEmpty(jsonstr)){
					text = text + " <div class=\"box2\">	<table> <tr><td class=\"name\">"+mingc+"姓名："+kh.getUsername()+"</td> <div id=\"farfile_"+i+"\"></div>";
					String files ="";
					text = text + "<td class=\"pic\" align=\"left\">";
					String tmp[] = jsonstr.split(";");
					for (int t = 0; t < tmp.length; t++) {
						files = files +tmp[t]+",";
						text = text + //"<td class=\"pic\">"+
	                      " <div> <p class=\"pic_box\"><img src=\"/file/download/"+tmp[t]+"\" width=\"150\" height=\"100\"><a class=\"del_btn\" onclick=\"deleFiless('"+tmp[t]+"','faddbrshzfyj_"+mingc+"','"+i+"');\"><img src=\"/images/icon07.png\"></a></p> </div> ";
					}
					text = text + " </td>";
					
					if(files.indexOf(",")>-1){
						files = files.substring(0, files.lastIndexOf(","));
					}
					text = text + //"<td class=\"rang\"></td>"+
			                  "<td class=\"btn\"><span class=\"add_btn\" id=\"xiaz_"+i+"\" onclick =\"download('"+files+"');\">下载</span></td>"+
			                 // "<td class=\"del\"></td>"+
			                  "</tr></table></div>";
				}
				text = text + "</div>";
				//i++;
		 }
		 return text;
	}
	
	
	
	public String getAppendDjg(long lbuid,int i){
		String text ="";
		String dsmingc = "";
	    BusinessLibaryEntity bl = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(lbuid);
	    String dsstr ="";
	    String jsmingc = "";
	    String jiansstr = "";
	    if(bl != null){
	    	int ds = bl.getIsdengsh();
	    	if(ds == 1){
	    		dsmingc = "董事会主席";
	    	}else{
	    		dsmingc = "执行董事";
	    	}
	    	i++;
	    	dsstr = getappendStrByroletype(lbuid,10,i,dsmingc);
	    	//董事
	    	i++;
	    	Map frgdstr3 = getFrgdstr(lbuid,i,3,"董事");
	    	String fdsstr = frgdstr3.get("text").toString();
	    	List<KehinfosEntity> kfs = BusinessLibraryService.getInstance().getKehByRoletypeObj(lbuid, 3);
	    	if(StringUtil.isListNull(kfs)){
	    		i = i+ kfs.size();
	    	}
	    	dsstr = dsstr + fdsstr;
	    	int js = bl.getIsjiash();
	    	if(js ==1){
	    		jsmingc = "监事会主席";
	    	}else{
	    		jsmingc = "监事";
	    	}
	    	i++;
	    	jiansstr = getappendStrByroletype(lbuid,11,i,jsmingc);
	    	//监事
	    	i++;
	    	Map frgdstr4 = getFrgdstr(lbuid,i,9,"监事");
	    	String fjsstr = frgdstr4.get("text").toString();
	    	kfs = BusinessLibraryService.getInstance().getKehByRoletypeObj(lbuid, 9);
	    	if(StringUtil.isListNull(kfs)){
	    		i = i+ kfs.size();
	    	}
	    	jiansstr = jiansstr + fjsstr;
	    }
		//经理 4
	    i++;
		String jlstr = getappendStrByroletype(lbuid,4,i,"经理");
	   //财务负责人 5
		i++;
		String cwstr =  getappendStrByroletype(lbuid,5,i,"财务负责人");
		//企业联系人 6
		i++;
		String qystr =  getappendStrByroletype(lbuid,6,i,"企业联系人");
		//Ukey管理员1 7
		i++;
		String ukeys1 =  getappendStrByroletype(lbuid,7,i,"Ukey管理员1");
		//Ukey管理员2  8
		i++;
		String ukeys2 =  getappendStrByroletype(lbuid,8,i,"Ukey管理员2");
		text = text + dsstr + jiansstr + jlstr + cwstr + qystr + ukeys1 +ukeys2;
		return text;
	}
	
	public String getAppendStr(long attid,String jsonstr,String field,String mc){
		String text ="";
		if(StringUtil.isEmpty(jsonstr)){
			text = text +" <div class=\"box2\"><table><tr><td class=\"name\">"+mc+":</td>";
			com.bj58.sfft.json.orgjson.JSONArray marr;
			String files ="";
			try {
				marr = new com.bj58.sfft.json.orgjson.JSONArray(jsonstr);
				text = text + "<td class=\"pic\" align=\"left\">";
				for (int t = 0; t < marr.length(); t++) {
					com.bj58.sfft.json.orgjson.JSONObject ob = marr.getJSONObject(t);
					String val =(String) ob.get("value");
					files = files +val+",";
					text = text +// "<td class=\"pic\">"+
							" <div>  <p class=\"pic_box\"><img src=\"/file/download/"+val+"\" width=\"150\" height=\"100\"><a class=\"del_btn\" onclick=\"deleFileotherss('"+val+"','"+field+"','"+attid+"');\"><img src=\"/images/icon07.png\"></a></p></div> ";
				}
				text = text +"</td>";
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if(files.indexOf(",")>-1){
				files = files.substring(0, files.lastIndexOf(","));
			}
			text = text +//"<td class=\"rang\"></td>"+
					"<td class=\"btn\"><span class=\"add_btn\" onclick =\"download('"+files+"');\">下载</span></td>"+
					//"<td class=\"del\"></td>"+
					"</tr></table> </div>";
		}
		return text;
	}
	public String saveOtherFile(String busid,String field,String fielsId){
		String text ="";
		if(StringUtil.isEmpty(busid)){
			long bid = Long.valueOf(busid);
			BusinessLibaryEntity ble = this.getBusinessLibaryEntityById(bid);
			if(ble != null){
				long attid = ble.getAttachid();
				JSONArray array = null;
				FileattachmentEntity fe = getFFileattachmentEntityByid(attid);
				if(fe != null){
					//更新
					array = getCompleOthertjsons(fe,field);
					getOtherfilejson(array,fe,field,fielsId);
					this.updateFileAttachEntity(fe);
					text = attid+"";
				}else{
					//新建
					fe = new FileattachmentEntity();
					getOtherfilejson(array,fe,field,fielsId);
					long atid = saveFileattachmentEntity(fe);
					ble.setAttachid(atid);
					updateBusinessLibaryEntity(ble);
					text = atid +"";
				}
			}
		}
		return text;
	}
	public void updateBusinessLibaryEntity(BusinessLibaryEntity be){
		try {
			RSBLL.getstance().getBusinessLibaryService().updateBusinessLibaryEntity(be);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public long saveFileattachmentEntity(FileattachmentEntity fe){
		long fid =0;
		try {
			fid = RSBLL.getstance().getFileattachmentService().addFileattachmentEntity(fe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fid;
	}
	
	public JSONObject initField(String field,String fileId){
		JSONObject qitobj =  new JSONObject();
		qitobj.put("key", field);
		qitobj.put("value", fileId);
		return  qitobj;
	}
	
	public JSONArray getCompleOthertjsons(FileattachmentEntity fe,String field){
		JSONArray array = null;
		String jsonstr = getJsonstr(fe,field);
		array = getFieldJson(jsonstr);
		return array;
	}
	
	public String getDisOtherJson(String fattid,String field){
		String jsonstr = "";
		if(StringUtil.isEmpty(fattid)){
			long lattid = Long.valueOf(fattid);
			FileattachmentEntity fe = getFFileattachmentEntityByid(lattid);
			if(fe != null){
				jsonstr = getJsonstr(fe,field);
			}
		}
		return jsonstr;
	}
	
	public String getJsonstr(FileattachmentEntity fe,String field){
		String jsonstr = "";
		if("dwgdyyzz".equals(field)){
			jsonstr = fe.getDwgdyyzz();
		}
		if("yingyzzzb".equals(field)){
			jsonstr = fe.getYingyzzzb();
	    }
		if("yingyzzfb".equals(field)){
			jsonstr = fe.getYingyzzfb();
		}
		if("hemjt".equals(field)){
			jsonstr = fe.getHemjt();
		}
		if("gongszc".equals(field)){
			jsonstr = fe.getGongszc();
		}
		if("shuiwdjzb".equals(field)){
			jsonstr = fe.getShuiwdjzb();
		}
		if("shuiwdjfb".equals(field)){
			jsonstr = fe.getShuiwdjfb();
		}
		
		if("zuzjgdmzb".equals(field)){
			jsonstr = fe.getZuzjgdmzb();
		}
		if("zuzjgdmfb".equals(field)){
			jsonstr = fe.getZuzjgdmfb();
		}
		if("tongjzzb".equals(field)){
			jsonstr = fe.getTongjzzb();
		}
		if("tongjzfb".equals(field)){
			jsonstr = fe.getTongjzfb();
		}
		
       if("fangwcqzm".equals(field)){
    	   jsonstr = fe.getFangwcqzm();
		}
		if("zhuszm".equals(field)){
			jsonstr = fe.getZhuszm();
		}
		if("qiylxrdjb".equals(field)){
			jsonstr = fe.getQiylxrdjb();
		}
		if("dongjjbxxb".equals(field)){
			jsonstr = fe.getDongjjbxxb();
		}
		
		if("shijbgdzlyzp".equals(field)){
			jsonstr = fe.getShijbgdzlyzp();
		}
		if("shijbgdzmpzp".equals(field)){
			jsonstr = fe.getShijbgdzmpzp();
		}
		if("shijbgdzlsnzp".equals(field)){
			jsonstr = fe.getShijbgdzlsnzp();
		}
		/*if("jizbgrzsysqwtsfj".equals(field)){
			jsonstr = fe.getJizbgrzsysqwtsfj();
		}*/
		
		return jsonstr;
	}
	
	public void getOtherfilejson(JSONArray array, FileattachmentEntity fe ,String field,String fileId){
		if(array == null){
			array = new JSONArray();
		}
		JSONObject initField = initField(field,fileId);
		if("dwgdyyzz".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setDwgdyyzz(array.toString());
		}
		if("yingyzzzb".equals(field) && !array.contains(initField)){
			array.add(initField);	
			fe.setYingyzzzb(array.toString());
	    }
		if("yingyzzfb".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setYingyzzfb(array.toString());
		}
		if("hemjt".equals(field) && !array.contains(initField)){
			System.out.println("=================:"+11111111);
			array.add(initField);
			fe.setHemjt(array.toString());
		}
		if("hemjt".equals(field) && array.size() == 0){
			System.out.println("=================:"+222222);
			array.add(initField);
			fe.setHemjt(array.toString());
		}
		System.out.println("=================:"+field+"=======:"+array);
		
		if("gongszc".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setGongszc(array.toString());
		}
		if("shuiwdjzb".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setShuiwdjzb(array.toString());
		}
		if("shuiwdjfb".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setShuiwdjfb(array.toString());
		}
		
		if("zuzjgdmzb".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setZuzjgdmzb(array.toString());
		}
		if("zuzjgdmfb".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setZuzjgdmfb(array.toString());
		}
		if("tongjzzb".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setTongjzzb(array.toString());
		}
		if("tongjzfb".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setTongjzfb(array.toString());
		}
		
       if("fangwcqzm".equals(field) && !array.contains(initField)){
    	   array.add(initField);
    	   fe.setFangwcqzm(array.toString());
		}
		if("zhuszm".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setZhuszm(array.toString());
		}
		if("qiylxrdjb".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setQiylxrdjb(array.toString());
		}
		if("dongjjbxxb".equals(field) && !array.contains(initField)){
			array.add(initField);
			fe.setDongjjbxxb(array.toString());
		}
		
		if("shijbgdzlyzp".equals(field)){
			array.add(initField(field,fileId));
			fe.setShijbgdzlyzp(array.toString());
		}
		if("shijbgdzmpzp".equals(field)){
			array.add(initField(field,fileId));
			fe.setShijbgdzmpzp(array.toString());
		}
		if("shijbgdzlsnzp".equals(field)){
			array.add(initField(field,fileId));
			fe.setShijbgdzlsnzp(array.toString());
		}
		/*if("jizbgrzsysqwtsfj".equals(field)){
			array.add(initField(field,fileId));
			fe.setJizbgrzsysqwtsfj(array.toString());
		}*/
	}
	
 	public String getOtherattach(long busid){
		String text ="";
		BusinessLibaryEntity ble = getBusinessLibaryEntityById(busid);
		if(ble != null){
			long attid = ble.getAttachid();
			FileattachmentEntity fa = getFFileattachmentEntityByid(attid);
			if(fa != null){
				String dwyyzz =getAppendStr(fa.getAttachid(),fa.getDwgdyyzz(),"dwgdyyzz","单位股东营业执照");
				text = text + "       <p class=\"tit1\"><span class=\"gd_name\">单位股东营业执照：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','dwgdyyzz')\">上传证件</span></p>"
	                        +" <div id=\"dwgdyyzz\">"+dwyyzz+"</div>";
				String yyzzzb = getAppendStr(fa.getAttachid(),fa.getYingyzzzb(),"yingyzzzb","营业执照正本");
				text = text + "       <p class=\"tit1\"><span class=\"gd_name\">营业执照正本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','yingyzzzb')\">上传证件</span></p>"
	                        +" <div id=\"yingyzzzb\">"+yyzzzb+"</div>";
				String yyzzfb = getAppendStr(fa.getAttachid(),fa.getYingyzzfb(),"yingyzzfb","营业执照副本");
				text = text + "       <p class=\"tit1\"><span class=\"gd_name\">营业执照副本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','yingyzzfb')\">上传证件</span></p>"
	                        +" <div id=\"yingyzzfb\">"+yyzzfb+"</div>";
				String hmjt = getAppendStr(fa.getAttachid(),fa.getHemjt(),"hemjt","核名截图");
				text = text + "       <p class=\"tit1\"><span class=\"gd_name\">核名截图：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','hemjt')\">上传证件</span></p>"
                         +" <div id=\"hemjt\">"+hmjt+"</div>";
				String gszc = getAppendStr(fa.getAttachid(),fa.getGongszc(),"gongszc","公司章程（多页)");
				text = text + "       <p class=\"tit1\"><span class=\"gd_name\">公司章程（多页)：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','gongszc')\">上传证件</span></p>"
                         +" <div id=\"gongszc\">"+gszc+"</div>";
				String swdjzzb = getAppendStr(fa.getAttachid(),fa.getShuiwdjzb(),"shuiwdjzb","税务登记证正本");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">税务登记证正本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','shuiwdjzb')\">上传证件</span></p>"
                        +" <div id=\"shuiwdjzb\">"+swdjzzb+"</div>";
				 String swdjzfb = getAppendStr(fa.getAttachid(),fa.getShuiwdjfb(),"shuiwdjfb","税务登记证副本");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">税务登记证副本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','shuiwdjfb')\">上传证件</span></p>"
                        +" <div id=\"shuiwdjfb\">"+swdjzfb+"</div>";
				//组织机构代码证正本
				 String zzjgdmzb  = getAppendStr(fa.getAttachid(),fa.getZuzjgdmzb(),"zuzjgdmzb","组织机构代码证正本");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">组织机构代码证正本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','zuzjgdmzb')\">上传证件</span></p>"
                        +" <div id=\"zuzjgdmzb\">"+zzjgdmzb+"</div>";
				 //组织机构代码证副本
				 String zzjgdmfb  = getAppendStr(fa.getAttachid(),fa.getZuzjgdmfb(),"zuzjgdmfb","组织机构代码证副本");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">组织机构代码证副本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','zuzjgdmfb')\">上传证件</span></p>"
                        +" <div id=\"zuzjgdmfb\">"+zzjgdmfb+"</div>";
				 //统计证正本
				 String tjzzb  = getAppendStr(fa.getAttachid(),fa.getTongjzzb(),"tongjzzb","统计证正本");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">统计证正本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','tongjzzb')\">上传证件</span></p>"
                        +" <div id=\"tongjzzb\">"+tjzzb+"</div>";
				 //统计证副本
				 String tjzfb  = getAppendStr(fa.getAttachid(),fa.getTongjzfb(),"tongjzfb","统计证副本");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">统计证副本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','tongjzfb')\">上传证件</span></p>"
                        +" <div id=\"tongjzfb\">"+tjzfb+"</div>";
				 //房屋产权证明（多页）
				 String fwcqzs =  getAppendStr(fa.getAttachid(),fa.getFangwcqzm(),"fangwcqzm","房屋产权证明（多页）");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">房屋产权证明（多页）：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','fangwcqzm')\">上传证件</span></p>"
                        +" <div id=\"fangwcqzm\">"+fwcqzs+"</div>";
				 //住所证明
				 String zszm =  getAppendStr(fa.getAttachid(),fa.getZhuszm(),"zhuszm","住所证明");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">住所证明：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','zhuszm')\">上传证件</span></p>"
                        +" <div id=\"zhuszm\">"+zszm+"</div>";
				 //企业联系人登记表
				 String qylxrdjb =  getAppendStr(fa.getAttachid(),fa.getQiylxrdjb(),"qiylxrdjb","企业联系人登记表");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">企业联系人登记表：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','qiylxrdjb')\">上传证件</span></p>"
                        +" <div id=\"qiylxrdjb\">"+qylxrdjb+"</div>";
				 //登记基本信息表
				 String jbdjb =  getAppendStr(fa.getAttachid(),fa.getDongjjbxxb(),"dongjjbxxb","登记基本信息表");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">登记基本信息表：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','dongjjbxxb')\">上传证件</span></p>"
                        +" <div id=\"dongjjbxxb\">"+jbdjb+"</div>";
			
				// 实际办公地址楼宇照片 
				 String shijbgdzlyzp =  getAppendStr(fa.getAttachid(),fa.getShijbgdzlyzp(),"shijbgdzlyzp","实际办公地址楼宇照片");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">实际办公地址楼宇照片：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','shijbgdzlyzp')\">上传证件</span></p>"
                        +" <div id=\"shijbgdzlyzp\">"+shijbgdzlyzp+"</div>";
				//实际办公地址楼宇照片门牌照片 
				 String shijbgdzmpzp =  getAppendStr(fa.getAttachid(),fa.getShijbgdzmpzp(),"shijbgdzmpzp","实际办公地址楼宇门牌照片");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">实际办公地址楼宇门牌照片：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','shijbgdzmpzp')\">上传证件</span></p>"
                        +" <div id=\"shijbgdzmpzp\">"+shijbgdzmpzp+"</div>";
				 //实际办公地址楼宇照片室内照片 
				 String shijbgdzlsnzp =  getAppendStr(fa.getAttachid(),fa.getShijbgdzmpzp(),"shijbgdzlsnzp","实际办公地址楼宇室内照片");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">实际办公地址楼宇室内照片：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','shijbgdzlsnzp')\">上传证件</span></p>"
                        +" <div id=\"shijbgdzlsnzp\">"+shijbgdzlsnzp+"</div>";
			     //集中办公区入驻事宜授权委托书 
				/* String jizbgrzsysqwtsfj =  getAppendStr(fa.getAttachid(),fa.getJizbgrzsysqwtsfj(),"shijbgdzlsnzp","集中办公区入驻事宜授权委托书 ");
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">集中办公区入驻事宜授权委托书 ：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','jizbgrzsysqwtsfj')\">上传证件</span></p>"
                        +" <div id=\"jizbgrzsysqwtsfj\">"+jizbgrzsysqwtsfj+"</div>";*/
			}else{
				 //单位股东营业执照
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">单位股东营业执照：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','dwgdyyzz')\">上传证件</span></p>"
                        +" <div id=\"dwgdyyzz\"></div>";
				 //营业执照正本
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">营业执照正本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','yingyzzzb')\">上传证件</span></p>"
                        +" <div id=\"yingyzzzb\"></div>";
				//营业执照副本
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">营业执照副本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','yingyzzfb')\">上传证件</span></p>"
                        +" <div id=\"yingyzzfb\"></div>";
				//核名截图
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">核名截图：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','hemjt')\">上传证件</span></p>"
                         +" <div id=\"hemjt\"></div>";
				 //公司章程（多页)
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">公司章程（多页)：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','gongszc')\">上传证件</span></p>"
                         +" <div id=\"gongszc\"></div>";
				 //税务登记证正本
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">税务登记证正本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','shuiwdjzb')\">上传证件</span></p>"
                         +" <div id=\"shuiwdjzb\"></div>";
				 //税务登记证副本
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">税务登记证副本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','shuiwdjfb')\">上传证件</span></p>"
                         +" <div id=\"shuiwdjfb\"></div>";
				//组织机构代码证正本
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">组织机构代码证正本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','zuzjgdmzb')\">上传证件</span></p>"
                         +" <div id=\"zuzjgdmzb\"></div>";
				 //组织机构代码证副本
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">组织机构代码证副本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','zuzjgdmfb')\">上传证件</span></p>"
                         +" <div id=\"zuzjgdmfb\"></div>";
				 //统计证正本
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">统计证正本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','tongjzzb')\">上传证件</span></p>"
                         +" <div id=\"tongjzzb\"></div>";
				 //统计证副本
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">统计证副本：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','tongjzfb')\">上传证件</span></p>"
                         +" <div id=\"tongjzfb\"></div>";
				 //房屋产权证明（多页）
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">房屋产权证明（多页）：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','fangwcqzm')\">上传证件</span></p>"
                         +" <div id=\"fangwcqzm\"></div>";
				 //住所证明
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">住所证明：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','zhuszm')\">上传证件</span></p>"
                         +" <div id=\"zhuszm\"></div>";
				 //企业联系人登记表
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">企业联系人登记表：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','qiylxrdjb')\">上传证件</span></p>"
                         +" <div id=\"qiylxrdjb\"></div>";
				 //登记基本信息表
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">登记基本信息表：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','dongjjbxxb')\">上传证件</span></p>"
                         +" <div id=\"dongjjbxxb\"></div>";
				 // 实际办公地址楼宇照片
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">实际办公地址楼宇照片：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','shijbgdzlyzp')\">上传证件</span></p>"
                         +" <div id=\"shijbgdzlyzp\"></div>";
				 //实际办公地址楼宇照片门牌照片 
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">实际办公地址楼宇门牌照片：</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','shijbgdzmpzp')\">上传证件</span></p>"
                         +" <div id=\"shijbgdzmpzp\"></div>";
				 //实际办公地址楼宇照片室内照片 
				 text = text + "       <p class=\"tit1\"><span class=\"gd_name\">实际办公地址楼宇室内照片 :</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','shijbgdzlsnzp')\">上传证件</span></p>"
                         +" <div id=\"shijbgdzlsnzp\"></div>";
				 //集中办公区入驻事宜授权委托书 
				/* text = text + "       <p class=\"tit1\"><span class=\"gd_name\">集中办公区入驻事宜授权委托书 :</span><span class=\"add_id\" onclick=\"uploadfile('fileupload','0','jizbgrzsysqwtsfj')\">上传证件</span></p>"
                         +" <div id=\"jizbgrzsysqwtsfj\"></div>";*/
			}
		}
		return text;
	}
	
	public BusinessLibaryEntity getBusinessLibaryEntityById(long bid){
		BusinessLibaryEntity be = null;
		try {
			be = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(bid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return be;
	}
	
	public String getCompletjsons(KehinfosEntity fa,String field){
		String jsonstr ="";
		if("gudsfzfyj".equals(field)){
			jsonstr = fa.getGudsfzfyj();
		}
		
		if("faddbrshzfyj".equals(field)){
			jsonstr = fa.getFaddbrshzfyj();
		}
		return jsonstr;
	}
	
	public JSONArray getFieldJson(String jsonstr){
		JSONArray array =null;
		JSONObject qitobj =null;
		if(StringUtil.isEmpty(jsonstr)){
			array = new JSONArray();
			com.bj58.sfft.json.orgjson.JSONArray marr;
			try {
				marr = new com.bj58.sfft.json.orgjson.JSONArray(jsonstr);
				for (int i = 0; i < marr.length(); i++) {
					com.bj58.sfft.json.orgjson.JSONObject ob = marr.getJSONObject(i);
					String key = (String) ob.get("key");
					String val =(String) ob.get("value");
					qitobj = new JSONObject();
					qitobj.put("key", key);
					qitobj.put("value", val);
					array.add(qitobj);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return array;
	}
	
	public FileattachmentEntity getFFileattachmentEntityByid(long lattid){
		FileattachmentEntity fe = null;
		try {
			fe = RSBLL.getstance().getFileattachmentService().getFileattachmentEntityById(lattid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fe;
	}
	
	public String  saveFileattach(String buid,String kehid,String field,String fileId){
		String text ="";
		if(StringUtil.isEmpty(kehid)){
			long lkhid = Long.valueOf(kehid);
			try {
				KehinfosEntity ke = RSBLL.getstance().getKehinfosService().getKehinfosEntityByid(lkhid);
				if(ke != null){
					String tmpstr = getCompletjsons(ke,field);
					if("gudsfzfyj".equals(field)){
						if(StringUtil.isEmpty(tmpstr)){
							tmpstr = tmpstr +";"+fileId;
						}else{
							tmpstr = fileId;
						}
						ke.setGudsfzfyj(tmpstr);
					}
					if("faddbrshzfyj".equals(field)){
						if(StringUtil.isEmpty(tmpstr)){
							tmpstr = tmpstr +";"+fileId;
						}else{
							tmpstr = fileId;
						}
						ke.setFaddbrshzfyj(tmpstr);
					}
					this.updateKehinfoEntity(ke);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return text;
	}
	
	public void updateKehinfoEntity(KehinfosEntity ke){
		try {
			RSBLL.getstance().getKehinfosService().updateKehinfosEntity(ke);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getInituploadinfo(String kehId,String field){
		String text ="";
		if(StringUtil.isEmpty(kehId)){
			long lkh = Long.valueOf(kehId);
			KehinfosEntity kh = getKehinfosEntity(lkh);
			if(kh != null){
				if("gudsfzfyj".equals(field)){
					text = kh.getGudsfzfyj();
				}
				if("faddbrshzfyj".equals(field)){
					text = kh.getFaddbrshzfyj();
				}
			}
		}
		return text;
	}
	
	public KehinfosEntity getKehinfosEntity(long kehid){
		KehinfosEntity kh = null;
		try {
			kh = RSBLL.getstance().getKehinfosService().getKehinfosEntityByid(kehid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kh;
	}
	
	public String delOtherFile(String fattid,String field,String fileId){
		String text = "";
		if(StringUtil.isEmpty(fattid)){
			long lattid = Long.valueOf(fattid);
			FileattachmentEntity fe = getFFileattachmentEntityByid(lattid);
			if(fe != null){
				JSONArray array = delOtherFieldJson(fe,field,fileId);
				if("dwgdyyzz".equals(field)){
					fe.setDwgdyyzz(array.size()>0?array.toString():"");
				}
				if("yingyzzzb".equals(field)){
					fe.setYingyzzzb(array.size()>0?array.toString():"");
			    }
				if("yingyzzfb".equals(field)){
					fe.setYingyzzfb(array.size()>0?array.toString():"");
				}
				if("hemjt".equals(field)){
					fe.setHemjt(array.size()>0?array.toString():"");
				}
				if("gongszc".equals(field)){
					fe.setGongszc(array.size()>0?array.toString():"");
				}
				if("shuiwdjzb".equals(field)){
					fe.setShuiwdjzb(array.size()>0?array.toString():"");
				}
				if("shuiwdjfb".equals(field)){
					fe.setShuiwdjfb(array.size()>0?array.toString():"");
				}
				
				if("zuzjgdmzb".equals(field)){
					fe.setZuzjgdmzb(array.size()>0?array.toString():"");
				}
				if("zuzjgdmfb".equals(field)){
					fe.setZuzjgdmfb(array.size()>0?array.toString():"");
				}
				if("tongjzzb".equals(field)){
					fe.setTongjzzb(array.size()>0?array.toString():"");
				}
				if("tongjzfb".equals(field)){
					fe.setTongjzfb(array.size()>0?array.toString():"");
				}
				
		       if("fangwcqzm".equals(field)){
		    	   fe.setFangwcqzm(array.size()>0?array.toString():"");
				}
				if("zhuszm".equals(field)){
					fe.setZhuszm(array.size()>0?array.toString():"");
				}
				if("qiylxrdjb".equals(field)){
					fe.setQiylxrdjb(array.size()>0?array.toString():"");
				}
				if("dongjjbxxb".equals(field)){
					fe.setDongjjbxxb(array.size()>0?array.toString():"");
				}
				
				if("shijbgdzlyzp".equals(field)){
					fe.setShijbgdzlyzp(array.size()>0?array.toString():"");
				}
				if("shijbgdzmpzp".equals(field)){
					fe.setShijbgdzmpzp(array.size()>0?array.toString():"");
				}
				if("shijbgdzlsnzp".equals(field)){
					fe.setShijbgdzlsnzp(array.size()>0?array.toString():"");
				}
				/*if("jizbgrzsysqwtsfj".equals(field)){
					fe.setJizbgrzsysqwtsfj(array.size()>0?array.toString():"");
				}*/
				//更新
				this.updateFileAttachEntity(fe);
			}
		}
		return text;
	}
	
	public String delUploadFile(String kehId,String field,String fileId){
		String text ="";
		if(StringUtil.isEmpty(kehId)){
			long lkehid = Long.valueOf(kehId);
			KehinfosEntity ke = this.getKehinfosEntity(lkehid);
			if(ke != null){
				String  attstr = delFieldJson(ke,field,fileId);
				if("gudsfzfyj".equals(field)){
					ke.setGudsfzfyj(attstr);
				}
				if("faddbrshzfyj".equals(field)){
					ke.setFaddbrshzfyj(attstr);
				}
				//更新
				updateKehinfoEntity(ke);
			}
		}
		return text;
	}
	
	public void updateFileAttachEntity(FileattachmentEntity fe){
		try {
			RSBLL.getstance().getFileattachmentService().updateFileattachmentEntity(fe);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JSONArray delOtherFieldJson(FileattachmentEntity fe,String field,String fileId){
		JSONArray array =null;
		JSONObject qitobj =null;
		String jsonstr = this.getJsonstr(fe, field);
		if(StringUtil.isEmpty(jsonstr)){
			array = new JSONArray();
			com.bj58.sfft.json.orgjson.JSONArray marr;
			try {
				marr = new com.bj58.sfft.json.orgjson.JSONArray(jsonstr);
				for (int i = 0; i < marr.length(); i++) {
					com.bj58.sfft.json.orgjson.JSONObject ob = marr.getJSONObject(i);
					String key = (String) ob.get("key");
					String val =(String) ob.get("value");
					if(!val.equals(fileId)){
						qitobj = new JSONObject();
						qitobj.put("key", key);
						qitobj.put("value", val);
						array.add(qitobj);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return array;
	}
	
	public String delFieldJson(KehinfosEntity fe,String field,String fileId){
		String jsonstr = null;
		if("gudsfzfyj".equals(field)){
			jsonstr = fe.getGudsfzfyj();
		}
		if("faddbrshzfyj".equals(field)){
			jsonstr = fe.getFaddbrshzfyj();
		}
		String text ="";
		if(StringUtil.isEmpty(jsonstr)){
			if(jsonstr.contains(fileId)){
				String tmp[]  = jsonstr.split(";");
				for(String str : tmp){
					if(!str.equals(fileId)){
						text = text +str +";";
					}
				}
				if(text != ""){
					if(text.indexOf(";")>-1){
						text = text.substring(0,text.lastIndexOf(";"));
					}
				}
			}
		}
		return text;
	}
	
	public String submitBusenessinfo(String buid){
		String text ="false";
		if(StringUtil.isEmpty(buid)){
			long lbuid = Long.valueOf(buid);
			BusinessLibaryEntity be = this.getBusinessLibaryEntityById(lbuid);
			if(be != null){
				be.setBusstatus(1);
				this.updateBusinessLibaryEntity(be);
				text ="true";
			}
		}
		return text;
	}
	
	public String validateFileattach(String buid){
		String text ="";
		if(StringUtil.isEmpty(buid)){
			long lbuid = Long.valueOf(buid);
			BusinessLibaryEntity be = this.getBusinessLibaryEntityById(lbuid);
			if(be != null){
				//法人代表身份证
			/*	List<KehinfosEntity> fr = getKehinfoByroletype(lbuid,2);
				if(StringUtil.isListNull(fr)){
					String sfz ="";
					for(KehinfosEntity kh : fr){
						sfz = kh.getFaddbrshzfyj();
						if(!StringUtil.isEmpty(sfz)){
							return "请上传法人代表身份证复印件！！";
						}
					}
				}*/
				//股东
				/*List<KehinfosEntity> zrr = getKehinfoByroletype(lbuid,0);
				if(StringUtil.isListNull(zrr)){
					String sfz ="";
					for(KehinfosEntity kh : zrr){
						sfz = kh.getGudsfzfyj();
						if(!StringUtil.isEmpty(sfz)){
							return "请上传股东身份证复印件！！";
						}
					}
				}*/
				//监事会 （提交前必填）
			/*	List<KehinfosEntity> jish = getKehinfoByroletype(lbuid,11);
				int mt = be.getIsjiash();
				if(StringUtil.isListNull(jish)){
					String sfz ="";
					for(KehinfosEntity kh : jish){
						sfz = kh.getFaddbrshzfyj();
						if(!StringUtil.isEmpty(sfz)){
							if(mt == 1){
								return "请上传监事会主席身份证复印件！！";
							}else{
								return "请上传监事身份证复印件！！";
							}
						}
					}
				}
				//监事
				List<KehinfosEntity> jis = getKehinfoByroletype(lbuid,9);
				if(StringUtil.isListNull(jis)){
					String sfz ="";
					for(KehinfosEntity kh : jis){
						sfz = kh.getFaddbrshzfyj();
						if(!StringUtil.isEmpty(sfz)){
							return "请上传监事身份证复印件！！";
						}
					}
				}*/
				long attid = be.getAttachid();
				FileattachmentEntity fa = this.getFFileattachmentEntityByid(attid);
				if(fa != null){
					String hmjt = fa.getHemjt();
					if(!StringUtil.isEmpty(hmjt)){
						return "请上传核名截图附件！！";
					}
					/*String fwcqz = fa.getFangwcqzm();
                    if(!StringUtil.isEmpty(fwcqz)){
                    	return  "请上传房屋产权证附件！！";
					}*/
				}
			}
		}
		return text;
	}
	
	public List<KehinfosEntity> getKehinfoByroletype(long busid,int roletype){
		List<KehinfosEntity> zirr = BusinessLibraryService.getInstance().getKehByRoletypeObj(busid, roletype);
		return zirr;
	}
	
	/**
	 * 更新企业库附件 TODO
	 * @param busId
	 * @param attachObj
	 * @throws Exception
	 */
	public void updateBusLibAttach(long busId, com.alibaba.fastjson.JSONObject attachObj) throws Exception{
		Set<String> keySet = attachObj.keySet();
		JSONArray array = null;
		for(String key:keySet){
			com.alibaba.fastjson.JSONArray jsonArray = attachObj.getJSONArray(key);
			array =  new JSONArray();
			if(jsonArray != null && jsonArray.size() > 0){
				String fileid = "0";
				JSONObject initField = null;
				for(int i=0;i<jsonArray.size();i++){
					fileid = (String)jsonArray.get(i);
					System.out.println("key:"+key+":"+fileid);
					initField = initField(key,fileid);
					array.add(initField);
					//saveOtherFile(busId+"",key,fileid);
				}
				saveOtherFileRemote(busId+"",key,array);
			}
		}
	}
	
	public void saveOtherFileRemote(String busid,String field, JSONArray array){
		if(StringUtil.isEmpty(busid)){
			long bid = Long.valueOf(busid);
			BusinessLibaryEntity ble = this.getBusinessLibaryEntityById(bid);
			if(ble != null){
				long attid = ble.getAttachid();
				FileattachmentEntity fe = getFFileattachmentEntityByid(attid);
				if(fe != null){
					if("hemjt".equals(field)){
						fe.setHemjt(array.toString());
					}
					this.updateFileAttachEntity(fe);
				}else{
					//新建
					fe = new FileattachmentEntity();
					if("hemjt".equals(field)){
						fe.setHemjt(array.toString());
					}
					long atid = saveFileattachmentEntity(fe);
					ble.setAttachid(atid);
					updateBusinessLibaryEntity(ble);
				}
			}
		}
	}
	
	
	public void getJsonStr(String jsonstr,com.alibaba.fastjson.JSONObject obj){
		com.alibaba.fastjson.JSONArray arry = null;
		if(StringUtil.isEmpty(jsonstr)){
			arry = new com.alibaba.fastjson.JSONArray();
			com.bj58.sfft.json.orgjson.JSONArray marr = null;
			try {
				marr = new com.bj58.sfft.json.orgjson.JSONArray(jsonstr);
				String key = "";
				String val = "";
				for (int i = 0; i < marr.length(); i++) {
					com.bj58.sfft.json.orgjson.JSONObject ob = marr.getJSONObject(i);
					key = (String) ob.get("key");
					val =(String) ob.get("value");
					arry.add(val);
				}
				if(key != null){
					obj.put(key, arry);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取企业库附件 TODO
	 * @param busId
	 * @return
	 * @throws Exception
	 */
	public com.alibaba.fastjson.JSONObject getBusLibAttach(long busId) throws Exception{
		com.alibaba.fastjson.JSONObject attachObj = new com.alibaba.fastjson.JSONObject();
		BusinessLibaryEntity ble = getBusinessLibaryEntityById(busId);
		if(ble != null){
			long attid = ble.getAttachid();
			FileattachmentEntity fe = getFFileattachmentEntityByid(attid);
			if(fe != null){
				getJsonStr(fe.getDwgdyyzz(),attachObj);
				getJsonStr(fe.getYingyzzzb(),attachObj);
				getJsonStr(fe.getYingyzzfb(),attachObj);
				getJsonStr(fe.getHemjt(),attachObj);
				getJsonStr(fe.getGongszc(),attachObj);
				getJsonStr(fe.getShuiwdjzb(),attachObj);
				getJsonStr(fe.getShuiwdjfb(),attachObj);
				getJsonStr(fe.getZuzjgdmzb(),attachObj);
				getJsonStr(fe.getZuzjgdmfb(),attachObj);
				getJsonStr(fe.getTongjzzb(),attachObj);
				getJsonStr(fe.getTongjzfb(),attachObj);
				getJsonStr(fe.getFangwcqzm(),attachObj);
				getJsonStr(fe.getZhuszm(),attachObj);
			    getJsonStr(fe.getQiylxrdjb(),attachObj);
				getJsonStr(fe.getDongjjbxxb(),attachObj);
				
				getJsonStr(fe.getShijbgdzlyzp(),attachObj);
			    getJsonStr(fe.getShijbgdzmpzp(),attachObj);
				getJsonStr(fe.getShijbgdzlsnzp(),attachObj);
				
				//getJsonStr(fe.getJizbgrzsysqwtsfj(),attachObj);
			}
		}
		return attachObj;
	}
}
