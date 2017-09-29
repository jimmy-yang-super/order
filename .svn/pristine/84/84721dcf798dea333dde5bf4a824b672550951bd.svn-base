package com.jixiang.argo.lvzheng.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bj58.sfft.json.orgjson.JSONException;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.vo.FileAttachmentVo;
import com.jixiang.argo.lvzheng.vo.KehIdCardAttachVo;
import com.jx.argo.BeatContext;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.BusinessLogsEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.FileattachmentEntity;
import com.jx.service.newcore.entity.KehinfosEntity;
import com.jx.service.newcore.entity.ReginAddressEntity;
import com.jx.service.newcore.entity.ReginAddressTempleteEntity;
/**
 * 企业库信息业务类
 * @author wuyin
 *
 */
public class BusinessLibraryService {

	private static BusinessLibraryService instance =null;
	
	private BusinessLibraryService(){} 
	
	public static BusinessLibraryService getInstance(){
		if(instance == null){
			instance = new BusinessLibraryService();
		}
		return instance;
	}
	
	public void disFileattachinfo(long sid,BeatContext beat){
		//获得股东
		List<KehIdCardAttachVo> guinfo = getKehInfoByRoleType(sid,0);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("gudinfos", guinfo);
		}
		//增加自然人股东显示
		guinfo = getKehInfoByRoleType(sid,1);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("zirrgd", guinfo);
		}
		guinfo = getKehInfoByRoleType(sid,2);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("farinfo", guinfo);
		}
		//董事会主席
	    BusinessLibaryEntity bl = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(sid);
	    String dsmingc = "";
	    String jsmingc ="";
	    if(bl != null){
	    	int ds = bl.getIsdengsh();
	    	if(ds == 1){
	    		dsmingc = "董事会主席";
	    	}else{
	    		dsmingc = "执行董事";
	    	}
	    	int js = bl.getIsjiash();
	    	if(js ==1){
	    		jsmingc = "监事会主席";
	    	}else{
	    		jsmingc = "监事";
	    	}
	    }
		guinfo =  getKehInfoByRoleType(sid,10);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("dsmingc", dsmingc);
			beat.getModel().add("dsinfos", guinfo);
		}
		//董事
		guinfo =  getKehInfoByRoleType(sid,3);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("dongs", guinfo);
		}
		//监事会主席
		guinfo =  getKehInfoByRoleType(sid,11);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("jsmingc", jsmingc);
			beat.getModel().add("jisinfos", guinfo);
		}
		//监事
		guinfo =  getKehInfoByRoleType(sid,9);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("jsinf", guinfo);
		}
		//经理 4
		guinfo =  getKehInfoByRoleType(sid,4);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("jl", guinfo);
		}
		//财务负责人 5
		guinfo =  getKehInfoByRoleType(sid,5);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("cw", guinfo);
		}
		//企业联系人 6
		guinfo =  getKehInfoByRoleType(sid,6);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("qy", guinfo);
		}
		//Ukey管理员1 7
		guinfo =  getKehInfoByRoleType(sid,7);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("uk1", guinfo);
		}
		///Ukey管理员2 8
		guinfo =  getKehInfoByRoleType(sid,8);
		if(StringUtil.isListNull(guinfo)){
			beat.getModel().add("uk2", guinfo);
		}
		//其他信息
		List<FileAttachmentVo> fvos = getOtherFileAttmach(sid);
		if(StringUtil.isListNull(fvos)){
			if(StringUtil.isListNull(fvos)){
				beat.getModel().add("otherinfo", fvos);
			}
		}
	}
	
	public FileAttachmentVo getFileAttachmentVo(String jsonstr){
		FileAttachmentVo vo = null;
		if(StringUtil.isEmpty(jsonstr)){
			vo = new FileAttachmentVo();
			List<String> jsons = null;
			String field ="";
			String fileids = "";
			if(StringUtil.isEmpty(jsonstr)){
				jsons = new ArrayList<String>();
				com.bj58.sfft.json.orgjson.JSONArray marr;
				try {
					marr = new com.bj58.sfft.json.orgjson.JSONArray(jsonstr);
					String val = "";
					for (int i = 0; i < marr.length(); i++) {
						com.bj58.sfft.json.orgjson.JSONObject ob = marr.getJSONObject(i);
						field = (String) ob.get("key");
						val =(String) ob.get("value");
						fileids = fileids +val+",";
						jsons.add(val);
					}
					if(fileids != ""){
						if(fileids.indexOf(",")>-1){
							fileids = fileids.substring(0,fileids.lastIndexOf(","));
						}
					}
					if(field != ""){
						field = KVMap.fileattmach.get(field);
						vo.setMingc(field);
					}
					if(jsons != null){
						vo.setImgurls(jsons);
					}
					if(fileids != ""){
						vo.setFileIds(fileids);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return vo;
	}
	
	public List<FileAttachmentVo> getOtherFileAttmach(long busid){
		List<FileAttachmentVo> fvos = null;
		FileAttachmentVo vo =  null;
		BusinessLibaryEntity ble = getBusinessLibaryEntity(busid);
		if(ble != null){
			fvos = new ArrayList<FileAttachmentVo>();
			long lattid = ble.getAttachid();
			FileattachmentEntity fe = FileattachmentService.getInstance().getFFileattachmentEntityByid(lattid);
			if(fe != null){
				vo = getFileAttachmentVo(fe.getDwgdyyzz());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getYingyzzzb());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getYingyzzfb());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getHemjt());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getGongszc());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getShuiwdjzb());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getShuiwdjfb());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getZuzjgdmzb());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getZuzjgdmfb());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getTongjzzb());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getTongjzfb());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getFangwcqzm());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getZhuszm());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getQiylxrdjb());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getDongjjbxxb());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getShijbgdzlyzp());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getShijbgdzmpzp());
				if(vo != null){
					fvos.add(vo);
				}
				vo = getFileAttachmentVo(fe.getShijbgdzlsnzp());
				if(vo != null){
					fvos.add(vo);
				}
			}
		}
		return fvos;
	}
	
	public List<KehIdCardAttachVo> getKehInfoByRoleType(long busid,int rpletype){
		List<KehinfosEntity> zirr = BusinessLibraryService.getInstance().getKehByRoletypeObj(busid, rpletype);
		List<KehIdCardAttachVo> kehs = null;
		KehIdCardAttachVo icard= null;
		List<String> imags =null;
		if(StringUtil.isListNull(zirr)){
			kehs = new ArrayList<KehIdCardAttachVo>();
			String fileids = "";
			String mtemp ="";
			for(KehinfosEntity k : zirr){
				fileids = "";
				icard = new KehIdCardAttachVo();
				icard.setKehId(k.getKehid()+"");
				icard.setKehName(k.getUsername());
				if(rpletype ==0){
					mtemp = k.getGudsfzfyj();
				}else{
					mtemp = k.getFaddbrshzfyj();
				}
				if(StringUtil.isEmpty(mtemp)){
					String tm[] = mtemp.split(";");
					imags = new ArrayList<String>();
					for(String st : tm){
						imags.add(st);
					}
				}
				//imags = getImageIds(mtemp,fileids,"");
				if(imags != null){
					icard.setImageIds(imags);
				}
				if(fileids != ""){
					icard.setFileIds(fileids);
				}
				kehs.add(icard);
			}
		}
		return kehs;
	}
	
	public List<String> getImageIds(String jsonstr,String fileids,String field){
		List<String> jsons = null;
		if(StringUtil.isEmpty(jsonstr)){
			jsons = new ArrayList<String>();
			com.bj58.sfft.json.orgjson.JSONArray marr;
			try {
				marr = new com.bj58.sfft.json.orgjson.JSONArray(jsonstr);
				String val = "";
				for (int i = 0; i < marr.length(); i++) {
					com.bj58.sfft.json.orgjson.JSONObject ob = marr.getJSONObject(i);
					field = (String) ob.get("key");
					val =(String) ob.get("value");
					fileids = fileids +",";
					jsons.add(val);
				}
				if(fileids != ""){
					if(fileids.indexOf(",")>-1){
						fileids = fileids.substring(0,fileids.lastIndexOf(","));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsons;
	}
	
	//根据订单ID获得相关信息
	public boolean getBusinessLibraryByorderId(long sid,BeatContext beat){
		boolean flag = false;
		try {
			BusinessLibaryEntity lbes = getBusinessLibaryEntity(sid);
			if(lbes != null){
				BusinessLibaryEntity be = lbes;
				if(be != null){
					String beixmc =be.getBeixmc();
					if(StringUtil.isEmpty(beixmc)){
						if(beixmc.indexOf("&")> -1){
							String bxmc[] = beixmc.split("&");
							for(int i = 1;i<=bxmc.length;i++){
								beat.getModel().add("beixmc"+i, bxmc[i-1]);
							}
							beat.getModel().add("beixcon", bxmc.length);
							beat.getModel().add("beixmcs", beixmc);
							beat.getModel().add("beixmcsmt", beixmc.replace("&", "、"));
						}else{
							beat.getModel().add("beixmc1", beixmc);
							beat.getModel().add("beixcon", 1);
							beat.getModel().add("beixmcsmt", beixmc);
						}
					}else{
						beat.getModel().add("beixmc1", "");
						beat.getModel().add("beixcon", 1);
					}
					String hylx = be.getJingyfw();
					if(StringUtil.isEmpty(hylx)){
						beat.getModel().add("hylx", hylx.replace("&", "、"));
					}
					String qityw = be.getQitjyyw();
                     if(StringUtil.isEmpty(qityw)){
                    	 String mt = qityw.replace("&", "、");
                    	 beat.getModel().add("qitywt", mt);
					}
					beat.getModel().add("businessLibaryEntity", be);//企业库信息
					//公司注册地址
					long reginid = be.getReginaddressId();
					ReginAddressEntity rae = getReginAddressEntityById(reginid);
					if(rae != null){
						int ishave = be.getIszhucdz();
						if(1 == ishave ){
							beat.getModel().add("fjha", rae.getFangjh()==null?"":rae.getFangjh());
							beat.getModel().add("hezdz", lbes.getAttribute2() == null?"":lbes.getAttribute2());
							beat.getModel().add("dzlx", rae.getAttribute2()==null?"":rae.getAttribute2());
						}else{
							beat.getModel().add("zulyt", rae.getFangwzlyt() == null?"":rae.getFangwzlyt());
							beat.getModel().add("fuqfs", rae.getFangwhqfs()==null?"":rae.getFangwhqfs());
							beat.getModel().add("chanqur", rae.getChanqzt()==null?"":rae.getChanqzt());
						}
						if("zthd".equals(lbes.getAttribute2())|| "szrxcybl".equals(lbes.getAttribute2())){
							beat.getModel().add("zthddz", StringUtils.replace(rae.getZiydz(), "***", rae.getFangjh()));
						}
						if("bjyabxx".equals(lbes.getAttribute2())||"rhjtnansi".equals(lbes.getAttribute2()) || "fswygl".equals(lbes.getAttribute2())){
							beat.getModel().add("neadddiz", StringUtils.replace(rae.getZiydz(), "xxx", rae.getFangjh()));
						}
						beat.getModel().add("reginAddressEntity", rae);
					}
					beat.getModel().add("hylx", be.getHangytd()==null?"":be.getHangytd());
					beat.getModel().add("zhuyyw", be.getZhuyyw()==null?"":be.getZhuyyw());
					beat.getModel().add("qityw", be.getQitjyyw()==null?"":be.getQitjyyw());
					//获得不同角色的用户信息
					long busid = be.getBusId();
					//企业信息
					KehinfosEntity kehinfo = getKehinfosEntity2Busid2role(busid,6);
					if(kehinfo != null){
						beat.getModel().add("qiyeryinfo", kehinfo);
					}
					kehinfo = getKehinfosEntity2Busid2role(busid,7);
					if(kehinfo != null){
						beat.getModel().add("Ukey1", kehinfo);
					}
					kehinfo = getKehinfosEntity2Busid2role(busid,8);
					if(kehinfo != null){
						beat.getModel().add("Ukey2", kehinfo);
					}
					//法定代办人
					kehinfo = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(busid, 2);
					if(kehinfo != null){
						beat.getModel().add("faddbr", kehinfo);
					}
				    //经理
					kehinfo = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(busid, 4);
					if(kehinfo != null){
						beat.getModel().add("jingl", kehinfo);
					}
				   //财务负责人
					kehinfo = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(busid, 5);
					if(kehinfo != null){
						beat.getModel().add("caiwfzr", kehinfo);
					}
					//自然人股东 0
					List<KehinfosEntity> kehs = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2roles(busid, 0);
					beat.getModel().add("zirrgud", kehs);
					beat.getModel().add("isdisplayzr", "false");
					if(StringUtil.isListNull(kehs)){
						beat.getModel().add("zicount", kehs.size());
						beat.getModel().add("isdisplayzr", "true");
					}else{
						beat.getModel().add("zicount",1);
					}
					//法人股东1
					kehs = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2roles(busid, 1);
					beat.getModel().add("fargud", kehs);
					beat.getModel().add("isdisplayfr", "false");
					if(StringUtil.isListNull(kehs)){
						beat.getModel().add("isdisplayfr", "true");
						beat.getModel().add("fargudcount", kehs.size());
					}else{
						beat.getModel().add("fargudcount", 0);
					}
					//董事会主席 10 董事3
					int ishaveds = be.getIsdengsh();
					kehinfo = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(busid, 10);
					if(kehinfo != null){
						beat.getModel().add("dshzx", kehinfo);
					}
					if(ishaveds == 1){
						kehs = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2roles(busid, 3);
						beat.getModel().add("dongs_1", kehs);
						beat.getModel().add("isdisplayds", "false");
						if(StringUtil.isListNull(kehs)){
							beat.getModel().add("isdisplayds", "true");
							beat.getModel().add("dscount", kehs.size());
						}else{
							beat.getModel().add("dscount", 1);
						}
					}else{
						beat.getModel().add("dscount", 1);
					}
					//9.监事，11 监事主席
					int ishavejs = be.getIsjiash();
					kehinfo = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(busid, 11);
					if(kehinfo != null){
						beat.getModel().add("jianszx", kehinfo);
					}
					if(ishavejs == 1){
						kehs = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2roles(busid, 9);
						beat.getModel().add("jianss", kehs);
						beat.getModel().add("isdisplayjs", "false");
						if(StringUtil.isListNull(kehs)){
							beat.getModel().add("isdisplayjs", "true");
							beat.getModel().add("jianscount", kehs.size());
						}else{
							beat.getModel().add("jianscount", 1);
						}
					}else{
						beat.getModel().add("jianscount", 1);
					}
				}
				flag =true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public BusinessLibaryEntity getBusinessLibaryEntityById(long sid){
		BusinessLibaryEntity bl = null;
		try {
			bl = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(sid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bl;
	}
	//获得登录姓名
	public String getLoginUsername(long luid){
		String uname="";
		try {
			EmployersEntity emp = RSBLL.getstance().getEmployersService().getEmployersEntityById(luid);
			if(emp != null){
				uname = emp.getRealname();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uname;
	}
	//获得不同角色的用户信息
	public KehinfosEntity getKehinfosEntity2Busid2role(long busid,int role){
		KehinfosEntity kehinfo = null;
		try {
			List<KehinfosEntity> lkes = RSBLL.getstance().getKehinfosService()
					.getKehinfosEntityListBypage("busidl ="+busid +" and roletype ="+role, 1, 1, "kehid");
			if(StringUtil.isListNull(lkes)){
				kehinfo = lkes.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kehinfo;
	}
	public List<KehinfosEntity> getKehinfosEntity2Busid2roles(long busid,int role){
		List<KehinfosEntity> lkes = new ArrayList<KehinfosEntity>();
		try {
			lkes = RSBLL.getstance().getKehinfosService()
					.getKehinfosEntityListBypage("busidl ="+busid +" and roletype ="+role, 1, 99, "kehid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lkes;
	}
	
	public List<String> getKehByRoletype(long busid,int role){
		List<String> lis = new ArrayList<String>();
		try {
			List<KehinfosEntity> lkes = RSBLL.getstance().getKehinfosService()
					.getKehinfosEntityListBypage("busidl ="+busid +" and roletype ="+role, 1, 99, "kehid");
		    if(StringUtil.isListNull(lkes)){
		    	for(KehinfosEntity keh : lkes){
		    		lis.add(keh.getKehid()+"");
		    	}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lis;
	}
	
	public List<KehinfosEntity> getKehByRoletypeObj(long busid,int role){
		List<KehinfosEntity> lkes = null;
		try {
			lkes = RSBLL.getstance().getKehinfosService()
					.getKehinfosEntityListBypage("busidl ="+busid +" and roletype ="+role, 1, 99, "kehid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lkes;
	}
	//注册地址对象
	public ReginAddressEntity getReginAddressEntityById(long reid){
		ReginAddressEntity re = null;
		try {
			re = RSBLL.getstance().getReginAddressService().getReginAddressEntityByid(reid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
	//获得企业库对象
	public BusinessLibaryEntity getBusinessLibaryEntity(long sid){
		BusinessLibaryEntity lbes = null;
	/*	try {
			SorderEntity seo = RSBLL.getstance().getSorderService().getSorderEntityByid(sid);
			if(seo != null){
				long busid = seo.getBusid();
				lbes = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(busid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try {
		   lbes = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(sid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lbes;
	}
	
	//保存 企业库主表
	public long saveBusinessLibraryEntity(BusinessLibaryEntity ble){
		long bleid = 0;
		try {
			bleid = RSBLL.getstance().getBusinessLibaryService().addBusinessLibaryEntity(ble);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bleid;
	}
	//保存企业库日志表
	public long saveBusinessLibraryLog(BusinessLogsEntity ble){
		long blogid = 0;
 		try {
			blogid = RSBLL.getstance().getBusinessLogsService().addBusinessLogsEntity(ble);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blogid;
	}
	//保存注册地址
	public long saveReginAddress(ReginAddressEntity rae){
		long reaid = 0;
		try {
			reaid = RSBLL.getstance().getReginAddressService().addReginAddressEntity(rae);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reaid;
	}
	//保存客户信息
	public long saveKehInfos(KehinfosEntity keh){
		long kehId = 0;
		try {
			kehId = RSBLL.getstance().getKehinfosService().addKehinfosEntity(keh);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kehId;
	}
	//更新企业库信息
	public void updateBusinessLibrary(BusinessLibaryEntity bel){
		try {
			RSBLL.getstance().getBusinessLibaryService().updateBusinessLibaryEntity(bel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//更新注册地址
	public void updateReginAddress(ReginAddressEntity regin){
		try {
			RSBLL.getstance().getReginAddressService().updateReginAddressEntity(regin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//更新客户信息
	public void updateKehInfos(KehinfosEntity ke){
		try {
			RSBLL.getstance().getKehinfosService().updateKehinfosEntity(ke);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addBusinesslogin(long userid,String operobj,long nowtime,long busid,String beiz){
		BusinessLogsEntity log = new BusinessLogsEntity();
		log.setAddtime(nowtime);
		log.setBusid(busid);
		log.getUserid();
		log.setOpertype(operobj);
		log.setBeiz(beiz);
		saveBusinessLibraryLog(log);
	}
	
	public List<AreasEntity> getAreasEntity(String parentid){
		//获得cityId 集合
		List<AreasEntity>  areaEntitys = null;
		areaEntitys = AreaCommonService.getInstance().getAraeEntityComm("parentid = "+parentid);
		return areaEntitys;
	}
	public List<AreasEntity> getEnterpriseAreasEntity(String parentid){
		//获得cityId 集合
		List<AreasEntity>  areaEntitys = null;
		areaEntitys = AreaCommonService.getInstance().getAraeEntityComm("parentid = "+parentid+" and status = 1");
		return areaEntitys;
	}
	
	public KehinfosEntity getKehinfosEntityByid(long kehid){
		KehinfosEntity keh = null;
		try {
			keh = RSBLL.getstance().getKehinfosService().getKehinfosEntityByid(kehid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keh;
	}
	
	public void deleteKehinfo(long kehid){
		try {
			RSBLL.getstance().getKehinfosService().deleteKehinfosEntity(kehid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示企业信息中的代理地址的合作商
	 */
	public List<ReginAddressTempleteEntity> getReginAddressTempleteEntitys(){
		List<ReginAddressTempleteEntity> tetags = null;
		try {
			tetags = RSBLL.getstance().getReginAddressTempleteService()
					.getReginAddressTempleteEntityListBypage("", 1, 50, "reginid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tetags;
	}
}
