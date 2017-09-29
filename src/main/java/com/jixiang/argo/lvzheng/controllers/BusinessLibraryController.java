package com.jixiang.argo.lvzheng.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.jixiang.argo.lvzheng.annotation.CheckCookie;
import com.jixiang.argo.lvzheng.service.BusinessLibraryService;
import com.jixiang.argo.lvzheng.service.BusinessOtherService;
import com.jixiang.argo.lvzheng.service.LogDealService;
import com.jixiang.argo.lvzheng.service.RegaddreTempleteService;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.utils.WFUtils;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.ReginAddressEntity;
import com.jx.service.newcore.entity.ReginAddressTempleteEntity;
@Path("/order")
@CheckCookie
public class BusinessLibraryController extends AbstractController{
	@Path("/business/{orderId:\\S+}")
	public ActionResult appearBusniessinfo(String orderId) {
		//增加登录判断
	/*	long loginuser = UtilsHelper.getLoginId(beat());
		if(loginuser == 0){
			//跳转登录页面
			return redirect("./emp/login");
		}*/
		//根据订单获得企业库信息
		boolean ismodify = false;
		String issource = beat().getRequest().getParameter("issource");
		if(StringUtil.isEmpty(issource)){
			beat().getModel().add("issource", issource);
		}
	    String taskId = beat().getRequest().getParameter("taskId");
	    
	    String applyMethod = WFUtils.getVariableByTaskIdAndName(taskId, "applyMethod");
	    
	    if(StringUtil.isEmpty(applyMethod)){
	    	beat().getModel().add("applyMethod", applyMethod);
	    }
		String gongShangInfoTuiHuiFlag = beat().getRequest().getParameter("gongShangInfoTuiHuiFlag");
		if(StringUtil.isEmpty(gongShangInfoTuiHuiFlag)){
			beat().getModel().add("gongShangInfoTuiHuiFlag", gongShangInfoTuiHuiFlag);
	    }
		String createArchiveStatus =WFUtils.getVariableByTaskIdAndName(taskId, "createArchiveStatus");
		if(StringUtil.isEmpty(createArchiveStatus)){
			beat().getModel().add("createArchiveStatus", createArchiveStatus);
	    }
		if(StringUtil.isEmpty(orderId)){
			String display ="";
			if(orderId.indexOf("_")> -1){
				String temp [] =orderId.split("_");
				orderId = temp[0];
				display = temp[1];
				beat().getModel().add("display", display);
			}
			long lsid = Long.valueOf(orderId);
			ismodify = BusinessLibraryService.getInstance().getBusinessLibraryByorderId(lsid, beat());
		
			BusinessLibaryEntity lbes = BusinessLibraryService.getInstance().getBusinessLibaryEntity(lsid);
			if(lbes != null){
				int bustatus = lbes.getBusstatus();
				if(bustatus == 1 && "1".equals(issource)){
					return redirect("/order/businessdetail/"+lsid+"?issource=1&isedit=1");		
				}
			}
			
		}
		List<AreasEntity> secondArea = BusinessLibraryService.getInstance().getEnterpriseAreasEntity("1");
		model().add("firstArea", secondArea);
		beat().getModel().add("orderid", orderId);
		//动态显示地址合作商
		List<ReginAddressTempleteEntity> mt = BusinessLibraryService.getInstance().getReginAddressTempleteEntitys();
		if(StringUtil.isListNull(mt)){
			beat().getModel().add("dlhzs", mt);
		}
		if(ismodify){
			return view("order/business");
		}else{
			return view("order/addbusiness");
		}
	}
	
	@Path("/businessdetail/{busid:\\S+}")
	public ActionResult businessdetail(String busid){
		 if(StringUtil.isEmpty(busid)){
			long lsid = Long.valueOf(busid);
			String issource = beat().getRequest().getParameter("issource");
			if(StringUtil.isEmpty(issource)){
				beat().getModel().add("issource", issource);
			}
			String isedit = beat().getRequest().getParameter("isedit");
			if(StringUtil.isEmpty(isedit)){
				beat().getModel().add("isedit", isedit);
			}
			BusinessLibraryService.getInstance().getBusinessLibraryByorderId(lsid, beat());
			//显示附件信息
			BusinessLibraryService.getInstance().disFileattachinfo(lsid,beat());
		}
		return view("order/businessdetail");
	}
	
	@Path("/addBusinessinfo")
	public ActionResult addBusinessinfo(){
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
				if("addgsmc".equals(type)){
					//保存公司信息
					String comptype = beat().getRequest().getParameter("comptype");
					if(!StringUtil.isEmpty(comptype)){
						comptype = "0";
					}
					int incomptype = Integer.parseInt(comptype);
					String gongsmc = beat().getRequest().getParameter("gongsmc");
					String beixmc = beat().getRequest().getParameter("beixmc");
					String zuczb = beat().getRequest().getParameter("zuczb");
					if(!StringUtil.isEmpty(zuczb)){
						zuczb ="0";
					}
					int inzuczb = Integer.parseInt(zuczb);
			        BusinessLibaryEntity ble = new BusinessLibaryEntity();
			        ble.setCompanytype(incomptype);
			        ble.setCompanymc(gongsmc);
			        ble.setBeixmc(beixmc);
			        ble.setZuczb(inzuczb);
			        ble.setAddtime(nowtime);
			        ble.setUpdatetime(nowtime);
			        ble.setAddperson(loginuser);
			        if(StringUtil.isEmpty(orderid)){
			        	long lorderid = Long.valueOf(orderid);
			        	ble.setOrderid(lorderid);
			        }
			        if(!StringUtil.isEmpty(busid)){
			        	 long bleId = BusinessLibraryService.getInstance().saveBusinessLibraryEntity(ble);
					     BusinessLibraryService.getInstance().addBusinesslogin(loginuser, "a", nowtime, bleId, "公司名称");
					     text = bleId+"";
			        }else{
			        	//更新
			        	long lbusid = Long.valueOf(busid);
					    ble = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(lbusid);
					    ble.setCompanytype(incomptype);
				        ble.setCompanymc(gongsmc);
				        ble.setBeixmc(beixmc);
				        ble.setZuczb(inzuczb);
				        ble.setUpdatetime(nowtime);
				        BusinessLibraryService.getInstance().updateBusinessLibrary(ble);
				        BusinessLibraryService.getInstance().addBusinesslogin(loginuser, "u", nowtime, lbusid, "公司名称");
				        text = busid;
			        }
				}
				if("addgsdz".equals(type)){
					//保存地址
					String city = beat().getRequest().getParameter("city");
					String area = beat().getRequest().getParameter("area");
					String ishaveaddress = beat().getRequest().getParameter("ishaveaddress");
					String dizhzs ="";
					String dzlx ="";
					String ziydz = beat().getRequest().getParameter("ziydz");
					String suosjx = beat().getRequest().getParameter("suosjx");
					String chanpzt = beat().getRequest().getParameter("chanpzt");
					String chanqr2dw = beat().getRequest().getParameter("chanqr2dw");
					String fangwghyt = beat().getRequest().getParameter("fangwghyt");
					String zhushqfs = beat().getRequest().getParameter("zhushqfs");
					
					String zulnf = beat().getRequest().getParameter("zulnf");
					String zulmj = beat().getRequest().getParameter("zulmj");
					String chuzjscode = beat().getRequest().getParameter("chuzjscode");
					String postcode = beat().getRequest().getParameter("postcode");
					
					String shijdz = beat().getRequest().getParameter("shijdz");
					String kuaitddz = beat().getRequest().getParameter("kuaitddz"); 
					String shoujr = beat().getRequest().getParameter("shoujr");
					String ptcode = beat().getRequest().getParameter("ptcode"); 
					String phone = beat().getRequest().getParameter("phone"); 
					
					String fangjh = beat().getRequest().getParameter("fangjh"); 
					
					BusinessLibaryEntity bl = null;
					ReginAddressEntity rea = null; 
					if(!StringUtil.isEmpty(busid)){
						//添加
						rea = new ReginAddressEntity();
						rea.setZiydz(ziydz);
						rea.setSuosjx(suosjx);
						rea.setChanqzt(chanpzt);
						rea.setChanqr(chanqr2dw);
						rea.setFangwzlyt(fangwghyt);
						rea.setFangwhqfs(zhushqfs);
						if(!StringUtil.isEmpty(zulnf)){
							zulnf ="0";
						}
						int inzulnf = Integer.parseInt(zulnf);
						rea.setZulsynx(inzulnf);
						if(!StringUtil.isEmpty(zulmj)){
							zulmj ="0";
						}
						int inzulmj = Integer.parseInt(zulmj);
						rea.setZulsymj(inzulmj);
						rea.setChuzfcard(chuzjscode);
						rea.setPostcode(postcode);
						if(dizhzs != ""){
							rea.setAttribute1(dizhzs);
						}
						if(dzlx != ""){
							rea.setAttribute2(dzlx);
						}
						rea.setAddtime(nowtime);
						
						rea.setShijbgdz(shijdz);
						rea.setShoujr(shoujr);
						rea.setKuaiddz(kuaitddz);
						rea.setLianxdh(phone);
						//保存地址
						long regid = BusinessLibraryService.getInstance().saveReginAddress(rea);
						
						bl = new  BusinessLibaryEntity();
						if(StringUtil.isEmpty(orderid)){
				        	long lorderid = Long.valueOf(orderid);
				        	bl.setOrderid(lorderid);
				        }
						bl.setReginaddressId(regid);
						bl.setAddtime(nowtime);
						bl.setAttribute1(ptcode);
						bl.setAddperson(loginuser);
						int izcdz = Integer.parseInt(ishaveaddress);
						bl.setIszhucdz(izcdz);
						if(StringUtil.isEmpty(city)){
							int cityId = Integer.parseInt(city);
							bl.setCityId(cityId);
						}
                        if(StringUtil.isEmpty(area)){
                        	int areaid = Integer.parseInt(area);
							bl.setAreaid(areaid);
						}
					    long bleId = BusinessLibraryService.getInstance().saveBusinessLibraryEntity(bl);
					    text = bleId+"";
					    
					}else{
						//更新
						long lbusid = Long.valueOf(busid);
						bl = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(lbusid);
						long reaid = bl.getReginaddressId();
						rea = BusinessLibraryService.getInstance().getReginAddressEntityById(reaid);
						if(rea != null){
							rea.setZiydz(ziydz);
							rea.setSuosjx(suosjx);
							rea.setChanqzt(chanpzt);
							rea.setChanqr(chanqr2dw);
							rea.setFangwzlyt(fangwghyt);
							rea.setFangwhqfs(zhushqfs);
							if(!StringUtil.isEmpty(zulnf)){
								zulnf ="0";
							}
							int inzulnf = Integer.parseInt(zulnf);
							rea.setZulsynx(inzulnf);
							if(!StringUtil.isEmpty(zulmj)){
								zulmj ="0";
							}
							int inzulmj = Integer.parseInt(zulmj.trim());
							rea.setZulsymj(inzulmj);
							rea.setChuzfcard(chuzjscode);
							rea.setPostcode(postcode);
							if(dizhzs != ""){
								rea.setAttribute1(dizhzs);
							}
							if(dzlx != ""){
								rea.setAttribute2(dzlx);
							}
							if("1".equals(ishaveaddress)){
								dizhzs = beat().getRequest().getParameter("dizhzs");
								rea = RegaddreTempleteService.getInstance().converReginAddressTempleteEntity(RegaddreTempleteService.getInstance().getReginAddressTempleteEntityBycode(dizhzs),rea);
								rea.setFangjh(fangjh);
								//增加替换 地址模板中的特殊字符为房间号：*** 或 xxx    begin
								ziydz = BusinessOtherService.getInstance().replaceAddreTempleztr(rea, fangjh);
								rea.setZiydz(ziydz);
								//增加替换 地址模板中的特殊字符为房间号：*** 或 xxx    end
							}else{
								rea.setAttribute1("");
								rea.setAttribute2("");
								rea.setFangjh("");
							}
							rea.setAddtime(nowtime);
							rea.setShijbgdz(shijdz);
							rea.setShoujr(shoujr);
							rea.setKuaiddz(kuaitddz);
							rea.setLianxdh(phone);
							bl.setAttribute1(ptcode);
							 //增加log4j日志记录
					        if(bl.getBusstatus() == 1){
					        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
					        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
					        	LogDealService.getInstance().writeRegaddressLog(lbusid, rea, optime, opername);
					        }
							BusinessLibraryService.getInstance().updateReginAddress(rea);
						}else{
							//添加
							rea = new ReginAddressEntity();
							rea.setZiydz(ziydz);
							rea.setSuosjx(suosjx);
							rea.setChanqzt(chanpzt);
							rea.setChanqr(chanqr2dw);
							rea.setFangwzlyt(fangwghyt);
							rea.setFangwhqfs(zhushqfs);
							if(!StringUtil.isEmpty(zulnf)){
								zulnf ="0";
							}
							int inzulnf = Integer.parseInt(zulnf);
							rea.setZulsynx(inzulnf);
							if(!StringUtil.isEmpty(zulmj)){
								zulmj ="0";
							}
							int inzulmj = Integer.parseInt(zulmj.trim());
							rea.setZulsymj(inzulmj);
							rea.setChuzfcard(chuzjscode);
							rea.setPostcode(postcode);
							if(dizhzs != ""){
								rea.setAttribute1(dizhzs);
							}
							if(dzlx != ""){
								rea.setAttribute2(dzlx);
							}
							if("1".equals(ishaveaddress)){
								dizhzs = beat().getRequest().getParameter("dizhzs");
								rea = RegaddreTempleteService.getInstance().converReginAddressTempleteEntity(RegaddreTempleteService.getInstance().getReginAddressTempleteEntityBycode(dizhzs),rea);
								rea.setFangjh(fangjh);
								//增加替换 地址模板中的特殊字符为房间号：*** 或 xxx    begin
								ziydz = BusinessOtherService.getInstance().replaceAddreTempleztr(rea, fangjh);
								rea.setZiydz(ziydz);
								//增加替换 地址模板中的特殊字符为房间号：*** 或 xxx    end
							}else{
								rea.setAttribute1("");
								rea.setAttribute2("");
								rea.setFangjh("");
							}
							rea.setAddtime(nowtime);
							
							rea.setShijbgdz(shijdz);
							rea.setShoujr(shoujr);
							rea.setKuaiddz(kuaitddz);
							rea.setLianxdh(phone);
							//保存地址
							reaid = BusinessLibraryService.getInstance().saveReginAddress(rea);
						}
						if(bl != null){
							int izcdz = Integer.parseInt(ishaveaddress);
							bl.setIszhucdz(izcdz);
							if(StringUtil.isEmpty(city)){
								int cityId = Integer.parseInt(city);
								bl.setCityId(cityId);
							}
	                        if(StringUtil.isEmpty(area)){
	                        	int areaid = Integer.parseInt(area);
								bl.setAreaid(areaid);
							}
	                        bl.setUpdatetime(nowtime);
	                        bl.setAttribute1(ptcode);
	                        bl.setReginaddressId(reaid);
	                        bl.setAttribute2(dizhzs);
	                        //增加log4j日志记录
					        if(bl.getBusstatus() == 1){
					        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
					        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
					        	LogDealService.getInstance().writeBusinessLog(lbusid, bl, optime, opername,"dldz");
					        }
	                        BusinessLibraryService.getInstance().updateBusinessLibrary(bl);

						}
						 text = lbusid+"";
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
	@Path("/modifyBusinessinfo")
	public ActionResult modifyBusinessinfo(){
		return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				//获得保存类型
				String type =beat().getRequest().getParameter("type");
				String text ="";
				String busId = beat().getRequest().getParameter("busId");
				long nowtime = new Date().getTime();
				long loginuser = UtilsHelper.getLoginId(beat());
				if("modifygsmc".equals(type)){
					String comptype = beat().getRequest().getParameter("comptype");
					int incomptype = Integer.parseInt(comptype);
					String gongsmc = beat().getRequest().getParameter("gongsmc");
					String beixmc = beat().getRequest().getParameter("beixmc");
					String zuczb = beat().getRequest().getParameter("zuczb");
					if(!StringUtil.isEmpty(zuczb)){
						zuczb ="0";
					}
					int inzuczb = Integer.parseInt(zuczb.trim());
					if(StringUtil.isEmpty(busId)){
						long lbusid = Long.valueOf(busId);
					    BusinessLibaryEntity ble = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(lbusid);
					    ble.setCompanytype(incomptype);
				        ble.setCompanymc(gongsmc);
				        ble.setBeixmc(beixmc);
				        ble.setZuczb(inzuczb);
				        ble.setUpdatetime(nowtime);
				        //增加log4j日志记录
				        if(ble.getBusstatus() == 1){
				        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
				        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
				        	LogDealService.getInstance().writeBusinessLog(lbusid, ble, optime, opername,"gsmc");
				        }
				        BusinessLibraryService.getInstance().updateBusinessLibrary(ble);
				       // BusinessLibraryService.getInstance().addBusinesslogin(loginuser, "u", nowtime, lbusid, "公司名称");
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
}
