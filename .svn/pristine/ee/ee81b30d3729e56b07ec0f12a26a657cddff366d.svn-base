package com.jixiang.argo.lvzheng.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.annotation.CheckCookie;
import com.jixiang.argo.lvzheng.service.BusinessLibraryService;
import com.jixiang.argo.lvzheng.service.BusinessOtherService;
import com.jixiang.argo.lvzheng.service.LogDealService;
import com.jixiang.argo.lvzheng.service.RegaddreTempleteService;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.KehinfosEntity;
import com.jx.service.newcore.entity.ReginAddressEntity;
import com.jx.service.union.entity.BusesspaterRemarks;
@Path("/order")
@CheckCookie
public class BusinessOtherController  extends AbstractController{
    @Path("/addBusinessother")
	public ActionResult businessother(){
		return new ActionResult() {
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				//获得保存类型
				String type =beat().getRequest().getParameter("type");
				String text ="";
				String orderid = beat().getRequest().getParameter("orderid");
				long nowtime = new Date().getTime();
				long loginuser = UtilsHelper.getLoginId(beat());
				String busid = beat().getRequest().getParameter("busid");
				BusinessLibaryEntity ble = null;
				if("addjingyfw".equals(type)){
					String hangylx = beat().getRequest().getParameter("hangylx");
					String zhuyyw = beat().getRequest().getParameter("zhuyyw");
					String qityw = beat().getRequest().getParameter("qityw");
					String zdjyfw = beat().getRequest().getParameter("zdjyfw");
					if(!StringUtil.isEmpty(busid)){
						ble = new BusinessLibaryEntity();
						ble.setUpdatetime(nowtime);
					    ble.setHangytd(hangylx);
					    ble.setZhuyyw(zhuyyw);
					    ble.setQitjyyw(qityw);
					    ble.setAddtime(nowtime);
					    ble.setJingyfw(zdjyfw);
					    if(StringUtil.isEmpty(orderid)){
				        	long lorderid = Long.valueOf(orderid);
				        	ble.setOrderid(lorderid);
				        }
					    ble.setAddperson(loginuser);
					    long bleId = BusinessLibraryService.getInstance().saveBusinessLibraryEntity(ble);
					    text = bleId+"";
					}else{
						//更新
						long lbusid = Long.valueOf(busid);
					    ble = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(lbusid);
				        ble.setUpdatetime(nowtime);
				        ble.setHangytd(hangylx);
				        ble.setZhuyyw(zhuyyw);
				        ble.setQitjyyw(qityw);
				        ble.setJingyfw(zdjyfw);
				        BusinessLibraryService.getInstance().updateBusinessLibrary(ble);
				        //增加log4j日志记录
				        if(ble.getBusstatus() == 1){
				        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
				        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
				        	LogDealService.getInstance().writeBusinessLog(lbusid, ble, optime, opername,"jingyfw");
				        }
				        text = busid;
					}
				}
				if("addgsyginfo".equals(type)){
					String qiylxr = beat().getRequest().getParameter("qiylxr");
					String qiylxrphone = beat().getRequest().getParameter("qiylxrphone");
					String guddh = beat().getRequest().getParameter("guddh");
					String email = beat().getRequest().getParameter("email");
					String postcode = beat().getRequest().getParameter("postcode");
					String zhigcount = beat().getRequest().getParameter("zhigcount");
					String bendrs = beat().getRequest().getParameter("bendrs");
					
					String waidrs = beat().getRequest().getParameter("waidrs");
					String nvxrw = beat().getRequest().getParameter("nvxrw");
					String Ukey1 = beat().getRequest().getParameter("Ukey1");
					String Ukey1phone = beat().getRequest().getParameter("Ukey1phone");
					String Ukey2 = beat().getRequest().getParameter("Ukey2");
					String Ukey2phone = beat().getRequest().getParameter("Ukey2phone");
					KehinfosEntity keh = null;
					BusinessLibaryEntity bl = null;
					if(!StringUtil.isEmpty(busid)){
						//添加
						bl = new BusinessLibaryEntity();
						bl.setAddperson(loginuser);
						bl.setAddtime(nowtime);
						bl.setUpdatetime(nowtime);
						if(StringUtil.isEmpty(zhigcount)){
							int inzhig = Integer.parseInt(zhigcount);
							bl.setZhigrs(inzhig);
						}
						if(StringUtil.isEmpty(bendrs)){
							int inbend = Integer.parseInt(bendrs);
							bl.setBendrs(inbend);
						}
						if(StringUtil.isEmpty(waidrs)){
							int inwaid = Integer.parseInt(waidrs);
							bl.setWaidrs(inwaid);
						}
						if(StringUtil.isEmpty(nvxrw)){
							int innvxrw = Integer.parseInt(nvxrw);
							bl.setNvxrs(innvxrw);
						}
						if(StringUtil.isEmpty(orderid)){
					        long lorderid = Long.valueOf(orderid);
					        bl.setOrderid(lorderid);
					    }
						long lbid = BusinessLibraryService.getInstance().saveBusinessLibraryEntity(bl);
						text = lbid+"";
						keh = new KehinfosEntity();
						keh.setUsername(qiylxr);
						keh.setPhone(qiylxrphone);
						keh.setGudphone(guddh);
						keh.setEmail(email);
						keh.setAttribute2(postcode);
						keh.setRoletype(6);//企业联系人
						keh.setBusidl(lbid);
						BusinessLibraryService.getInstance().saveKehInfos(keh);
						if(StringUtil.isEmpty(Ukey1)){
							keh = new KehinfosEntity();
							keh.setUsername(Ukey1);
							keh.setPhone(Ukey1phone);
							keh.setRoletype(7);//Ukey1
							keh.setBusidl(lbid);
							BusinessLibraryService.getInstance().saveKehInfos(keh);
						}
						if(StringUtil.isEmpty(Ukey2)){
							keh = new KehinfosEntity();
							keh.setUsername(Ukey2);
							keh.setPhone(Ukey2phone);
							keh.setRoletype(8);//Ukey2
							keh.setBusidl(lbid);
							BusinessLibraryService.getInstance().saveKehInfos(keh);
						}
					}else{
						//更新
						long lbusid = Long.valueOf(busid);
						bl = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(lbusid);
						bl.setUpdatetime(nowtime);
						if(StringUtil.isEmpty(zhigcount)){
							int inzhig = Integer.parseInt(zhigcount);
							bl.setZhigrs(inzhig);
						}
						if(StringUtil.isEmpty(bendrs)){
							int inbend = Integer.parseInt(bendrs);
							bl.setBendrs(inbend);
						}
						if(StringUtil.isEmpty(waidrs)){
							int inwaid = Integer.parseInt(waidrs);
							bl.setWaidrs(inwaid);
						}
						if(StringUtil.isEmpty(nvxrw)){
							int innvxrw = Integer.parseInt(nvxrw);
							bl.setNvxrs(innvxrw);
						}
						//增加log4j日志记录
				        if(bl.getBusstatus() == 1){
				        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
				        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
				        	LogDealService.getInstance().writeBusinessLog(lbusid, bl, optime, opername,"employ");
				        }
						BusinessLibraryService.getInstance().updateBusinessLibrary(bl);
						//企业联系人
					    keh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(lbusid, 6);
					    if(keh != null){
					    	keh.setUsername(qiylxr);
							keh.setPhone(qiylxrphone);
							keh.setGudphone(guddh);
							keh.setEmail(email);
							keh.setAttribute2(postcode);
							keh.setRoletype(6);//企业联系人
							keh.setBusidl(lbusid);
							//增加log4j日志记录
					        if(bl.getBusstatus() == 1){
					        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
					        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
					        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,6);
					        }
							BusinessLibraryService.getInstance().updateKehInfos(keh);
					    }else{
					    	//添加
					    	keh = new KehinfosEntity();
							keh.setUsername(qiylxr);
							keh.setPhone(qiylxrphone);
							keh.setGudphone(guddh);
							keh.setEmail(email);
							keh.setAttribute2(postcode);
							keh.setRoletype(6);//企业联系人
							keh.setBusidl(lbusid);
							BusinessLibraryService.getInstance().saveKehInfos(keh);
					    }
					    keh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(lbusid, 7);
                        if(keh != null){
                        	keh.setUsername(Ukey1);
							keh.setPhone(Ukey1phone);
							keh.setRoletype(7);//Ukey1
							keh.setBusidl(lbusid);
							//增加log4j日志记录
					        if(bl.getBusstatus() == 1){
					        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
					        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
					        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,7);
					        }
							BusinessLibraryService.getInstance().updateKehInfos(keh);
					    }else{
					    	//添加
					    	keh = new KehinfosEntity();
							keh.setUsername(Ukey1);
							keh.setPhone(Ukey1phone);
							keh.setRoletype(7);//Ukey1
							keh.setBusidl(lbusid);
							BusinessLibraryService.getInstance().saveKehInfos(keh);
					    }
					    keh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(lbusid, 8);
                        if(keh != null){
                        	keh.setUsername(Ukey2);
							keh.setPhone(Ukey2phone);
							keh.setRoletype(8);//Ukey2
							keh.setBusidl(lbusid);
							//增加log4j日志记录
					        if(bl.getBusstatus() == 1){
					        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
					        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
					        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,8);
					        }
							BusinessLibraryService.getInstance().updateKehInfos(keh);
					    }else{
					    	//添加
					    	keh = new KehinfosEntity();
							keh.setUsername(Ukey2);
							keh.setPhone(Ukey2phone);
							keh.setRoletype(8);//Ukey2
							keh.setBusidl(lbusid);
							BusinessLibraryService.getInstance().saveKehInfos(keh);
					    }
                        text = busid;
					}
				}
                if("addotherinfo".equals(type)){
                	String gsxtcount = beat().getRequest().getParameter("gsxtcount");
                	String password = beat().getRequest().getParameter("password");
                	String mingchzwh = beat().getRequest().getParameter("mingchzwh");
                	String  hemgszh = beat().getRequest().getParameter("hemgszh");
                	String  hempassword = beat().getRequest().getParameter("hempassword");
                	String  wangdgszh = beat().getRequest().getParameter("wangdgszh");
                	String  wangdmm = beat().getRequest().getParameter("wangdmm");
                	if(!StringUtil.isEmpty(busid)){
                		//添加
                		ble = new BusinessLibaryEntity();
                		ble.setAddperson(loginuser);
                		ble.setAddtime(nowtime);
                		ble.setUpdatetime(nowtime);
                		ble.setGongsxtcode(gsxtcount);
                		ble.setPassword(password);
                		ble.setMingcyxhz(mingchzwh);
                		ble.setHemgszh(hemgszh);
                		ble.setHempassword(hempassword);
                		ble.setWangdgszh(wangdgszh);
                		ble.setWangdpassword(wangdmm);
                		if(StringUtil.isEmpty(orderid)){
					        long lorderid = Long.valueOf(orderid);
					        ble.setOrderid(lorderid);
					    }
                		long lt = BusinessLibraryService.getInstance().saveBusinessLibraryEntity(ble);
                		text =lt+"";
                	}else{
                		//更新
                		long lbusid = Long.valueOf(busid);
						ble = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(lbusid);
						ble.setUpdatetime(nowtime);
						ble.setGongsxtcode(gsxtcount);
                		ble.setPassword(password);
                		ble.setMingcyxhz(mingchzwh);
                		ble.setHemgszh(hemgszh);
                		ble.setHempassword(hempassword);
                		ble.setWangdgszh(wangdgszh);
                		ble.setWangdpassword(wangdmm);
                		//增加log4j日志记录
				        if(ble.getBusstatus() == 1){
				        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
				        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
				        	LogDealService.getInstance().writeBusinessLog(lbusid, ble, optime, opername,"other");
				        }
                		BusinessLibraryService.getInstance().updateBusinessLibrary(ble);
                		text = busid;
                	}
                }
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		};
	}
    
    @Path("/modifykeh")
    public ActionResult modifykeh(){
    	return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String orderid = beat().getRequest().getParameter("addorderid");
				long nowtime = new Date().getTime();
				long loginuser = UtilsHelper.getLoginId(beat());
				String busid = beat().getRequest().getParameter("addbusid");
				
				String zirrgudcount = beat().getRequest().getParameter("zirrgudcount");
				String fargudcount = beat().getRequest().getParameter("fargudcount");
				String display = beat().getRequest().getParameter("display");
				KehinfosEntity keh = null;
				BusinessLibaryEntity bl = null;
				String text ="";
				if(StringUtil.isEmpty(busid)){
					text = busid;
					//更新
					long lbusid = Long.valueOf(busid);
					bl = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(lbusid);
					bl.setUpdatetime(nowtime);
					List<String> zirr = BusinessLibraryService.getInstance().getKehByRoletype(lbusid, 0);
					
					BusinessLibraryService.getInstance().updateBusinessLibrary(bl);
					//自然人0
					BusinessOtherService.getInstance().saveZirren(zirr,lbusid, zirrgudcount, beat(), keh,nowtime,loginuser);
					//删除
					BusinessOtherService.getInstance().deleteKehinfoEntity(zirr);
					 if(bl.getBusstatus() == 1){
				        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
				        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
				        	BusinessOtherService.getInstance().deleteKehinfolog(zirr, optime, opername, "自然人股东");
				     }
					//法人1
					List<String> farr = BusinessLibraryService.getInstance().getKehByRoletype(lbusid, 1);
					
					BusinessOtherService.getInstance().saveFar(farr, lbusid, fargudcount, keh, beat(),nowtime,loginuser);
					//删除
					BusinessOtherService.getInstance().deleteKehinfoEntity(farr);
					 if(bl.getBusstatus() == 1){
				        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
				        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
				        	BusinessOtherService.getInstance().deleteKehinfolog(farr, optime, opername, "法人股东");
				     }
				}
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
    }
    
    @Path("/addRemoteBusinessinfo")
    public ActionResult addRemoteBusinessinfo(){
    	return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				
				String dizhzs = beat().getRequest().getParameter("dizhzs");
				String fangjh =  beat().getRequest().getParameter("fangjh");
				String busid = beat().getRequest().getParameter("busid");
				if(StringUtil.isEmpty(busid)){
					ReginAddressEntity rea = null;
					//判断地址是否存在
					rea = RegaddreTempleteService.getInstance().getReginAddressEntityBybusId(Long.parseLong(busid));
					String ziydz ="";
					if(rea != null){
						rea.setFangjh(fangjh);
						//增加替换 地址模板中的特殊字符为房间号：*** 或 xxx    begin
						BusinessLibaryEntity bel = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(Long.parseLong(busid));
						if(bel != null){
							dizhzs = bel.getAttribute2();
						}
						ReginAddressEntity rea_update = RegaddreTempleteService.getInstance().converReginAddressTempleteEntity(RegaddreTempleteService.getInstance().getReginAddressTempleteEntityBycode(dizhzs),rea);
						ziydz = BusinessOtherService.getInstance().replaceAddreTempleztr(rea_update, fangjh);
						rea.setZiydz(ziydz);
						RegaddreTempleteService.getInstance().updateReginAddressEntity(rea);
					}else{
						rea = new ReginAddressEntity();
						dizhzs = beat().getRequest().getParameter("dizhzs");
						rea = RegaddreTempleteService.getInstance().converReginAddressTempleteEntity(RegaddreTempleteService.getInstance().getReginAddressTempleteEntityBycode(dizhzs),rea);
						rea.setFangjh(fangjh);
						//增加替换 地址模板中的特殊字符为房间号：*** 或 xxx    begin
						ziydz = BusinessOtherService.getInstance().replaceAddreTempleztr(rea, fangjh);
						rea.setZiydz(ziydz);
						//增加替换 地址模板中的特殊字符为房间号：*** 或 xxx    end
						rea.setAddtime(new Date().getTime());
						//保存地址
						long reaid = BusinessLibraryService.getInstance().saveReginAddress(rea);
						long lbusid = Long.valueOf(busid);
						BusinessLibaryEntity bl = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(lbusid);
						if(bl != null){
							bl.setReginaddressId(reaid);
							if(StringUtil.isEmpty(dizhzs)){
								bl.setIszhucdz(1);
							}else{
								bl.setIszhucdz(0);
							}
							bl.setUpdatetime(new Date().getTime());
							bl.setAttribute2(dizhzs);
							BusinessLibraryService.getInstance().updateBusinessLibrary(bl);
						}
					}
				}
			}
		};
    }
}
