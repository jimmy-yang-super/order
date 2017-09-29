package com.jixiang.argo.lvzheng.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jx.argo.BeatContext;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.KehinfosEntity;
import com.jx.service.newcore.entity.ReginAddressEntity;

/**
 * 
 * @author wuyin
 *
 */
public class BusinessOtherService {

	private static BusinessOtherService instance =null;
	
	private BusinessOtherService(){} 
	
	public static BusinessOtherService getInstance(){
		if(instance == null){
			instance = new BusinessOtherService();
		}
		return instance;
	}
	
	public void saveFar(List<String> farr,long lbusid,String fargudcount,KehinfosEntity keh,BeatContext beat,long nowtime,long loginuser){
		if(StringUtil.isEmpty(fargudcount)){
			int icount = Integer.parseInt(fargudcount);
			String fargud ="";
            String faryyzzh ="";
            String farchuze ="";
            String farId ="";
            BusinessLibaryEntity bl = BusinessLibraryService.getInstance().getBusinessLibaryEntity(lbusid);
        	for(int i =1;i<=icount;i++){
        		farId = beat.getRequest().getParameter("fargudId"+i);
				fargud = beat.getRequest().getParameter("fargud"+i);
				faryyzzh = beat.getRequest().getParameter("faryyzzh"+i);
				farchuze = beat.getRequest().getParameter("farchuze"+i);
				if(farr.contains(farId)){
					long lkehid = Long.valueOf(farId);
					keh = BusinessLibraryService.getInstance().getKehinfosEntityByid(lkehid);
					farr.remove(farId);
					keh.setUsername(fargud);
					keh.setYingyzz(faryyzzh);
					if(StringUtil.isEmpty(farchuze)){
						float fco = Float.valueOf(farchuze);
						keh.setChuze(fco);
					}
					keh.setBusidl(lbusid);
					keh.setRoletype(1);
					//增加log4j日志记录
			        if(bl.getBusstatus() == 1){
			        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
			        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
			        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,1);
			        }
					BusinessLibraryService.getInstance().updateKehInfos(keh);
				}else{
					if(StringUtil.isEmpty(fargud)){
						keh = new KehinfosEntity();
						keh.setUsername(fargud);
						keh.setYingyzz(faryyzzh);
						if(StringUtil.isEmpty(farchuze)){
							float fco = Float.valueOf(farchuze);
							keh.setChuze(fco);
						}
						keh.setBusidl(lbusid);
						keh.setRoletype(1);
						BusinessLibraryService.getInstance().saveKehInfos(keh);
					}
				}
			}
		}
	}
	
	public void deleteKehinfoEntity(List<String> zirr){
		if(StringUtil.isListNull(zirr)){
			long lkid =0;
			for(String kid : zirr){
				lkid = Long.valueOf(kid);
				BusinessLibraryService.getInstance().deleteKehinfo(lkid);
			}
		}
	}
	
	public void deleteKehinfolog(List<String> delstr,String nowtime,String opername,String mingc){
		if(StringUtil.isListNull(delstr)){
			String st ="";
			for(String mt : delstr){
				st = st + mt+",";
			}
			if(st.indexOf(",")>-1){
				st = st.substring(0, st.lastIndexOf(","));
			}
			if(st != ""){
				Logger.getLogger(BusinessOtherService.getInstance().getClass())
				.debug("删除"+mingc+ "    "+ nowtime + "     " + opername +"     "+"删除的ID集合：["+st+"]");
			}
		}
	}
	
	public void saveZirren(List<String> zirr,long lbusid,String zirrgudcount,BeatContext beat,KehinfosEntity keh
			               ,long nowtime,long loginuser){
		if(StringUtil.isEmpty(zirrgudcount)){
			int icount = Integer.parseInt(zirrgudcount);
			String gudong =""; 
            String chuzf ="";
            String gudIdcard ="";
            String gudIdcardaddre ="";
            String gudsex =""; 
            String gudphone ="";
            String kehId ="";
            BusinessLibaryEntity bl = BusinessLibraryService.getInstance().getBusinessLibaryEntity(lbusid);
			for(int i =1;i<=icount;i++){
				kehId = beat.getRequest().getParameter("zirgudId"+i);
				gudong = beat.getRequest().getParameter("gudong"+i);
				chuzf = beat.getRequest().getParameter("chuzf"+i);
				gudIdcard = beat.getRequest().getParameter("gudIdcard"+i);
				gudIdcardaddre = beat.getRequest().getParameter("gudIdcardaddre"+i);
				gudsex = beat.getRequest().getParameter("gudsex"+i);
				gudphone = beat.getRequest().getParameter("gudphone"+i);
				if(zirr.contains(kehId)){
					long lkehid = Long.valueOf(kehId);
					keh = BusinessLibraryService.getInstance().getKehinfosEntityByid(lkehid);
					zirr.remove(kehId);
					keh.setUsername(gudong);
					keh.setAttribute1(chuzf);
					keh.setIdcard(gudIdcard);
					keh.setIdcardaddress(gudIdcardaddre);
					keh.setPhone(gudphone);
					keh.setBusidl(lbusid);
					keh.setSex(gudsex);
					keh.setRoletype(0);
					//增加log4j日志记录
			        if(bl.getBusstatus() == 1){
			        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
			        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
			        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,0);
			        }
					BusinessLibraryService.getInstance().updateKehInfos(keh);
				}else{
					if(StringUtil.isEmpty(gudong)){
						keh = new KehinfosEntity();
						keh.setUsername(gudong);
						keh.setAttribute1(chuzf);
						keh.setIdcard(gudIdcard);
						keh.setIdcardaddress(gudIdcardaddre);
						keh.setPhone(gudphone);
						keh.setBusidl(lbusid);
						keh.setSex(gudsex);
						keh.setRoletype(0);
						BusinessLibraryService.getInstance().saveKehInfos(keh);
					}
				}
			}
		}
	}
	/**
	 * 替换地址模板中的特殊字符串
	 * @return
	 */
	public String replaceAddreTempleztr(ReginAddressEntity rea,String fangjh){
		//增加替换 地址模板中的特殊字符为房间号：*** 或 xxx    begin
		String ziydz = rea.getZiydz();
		if(StringUtil.isEmpty(ziydz)){
			if(StringUtil.isEmpty(fangjh)){
				if(StringUtils.indexOf(ziydz,"***")>-1){
					ziydz = StringUtils.replace(ziydz, "***", fangjh);
				}else if(StringUtils.indexOf(ziydz, "xxx") >-1){
					ziydz = StringUtils.replace(ziydz, "xxx", fangjh);
				}else{
					ziydz = ziydz + fangjh;
				}
			}
		}
	   return ziydz;
		//增加替换 地址模板中的特殊字符为房间号：*** 或 xxx    end
	}
}
