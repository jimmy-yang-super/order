package com.jixiang.argo.lvzheng.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.actionresult.JSONActionResult;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.service.AddOrderRealService;
import com.jixiang.argo.lvzheng.service.RealBusinessService;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.vo.SaveOrderVo;
import com.jixiang.union.interceptor.AutherCheck;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.ProductEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;
import com.jx.service.newcore.entity.SorderEntity;
@Path("/order")
public class AddOrderController extends AbstractController{
	@Path("/addorder/{page:\\S+}")
	@AutherCheck
	public ActionResult list(String page) {
		//公司类别
		List<ProductPropertiesEntity> company = AddOrderRealService.getInstance()
				.getCompanyType("companytype");
		model().add("companytype", company);
		//获得一级区域
		List<AreasEntity> firstArea = AddOrderRealService.getInstance().getAreasEntity();
		model().add("firstArea", firstArea);
		//获得二级菜单
		List<ProductPropertiesEntity> secondArea = AddOrderRealService.getInstance().getBusinessArea();
		model().add("secondArea", secondArea);
		//展示商品信息
		Map<ProductEntity,List<ProductCategoryEntity>> map = AddOrderRealService.getInstance().initAddorderpage("");
		model().add("productInfos", map);
		model().add("backpage", "/order/"+page);
		String fromPage = beat().getRequest().getParameter("fromPage");
		if("customercenter".equals(fromPage)){
			return view("order/customercenter/add");
		}
		return view("order/add");
	}
	@Path("/historyByphone")
	public ActionResult historyRecord(){
		return new ActionResult() {
			@Override
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String phoneNum = beat().getRequest().getParameter("phoneNum");
			    String userInfo = RealBusinessService.getInstance().getLoginEntityStr(RealBusinessService.getInstance().getLoginEntityByphoneNum(phoneNum));
			    //根据电话记录获得记录
				String text = "";
				List<SorderEntity> sorders = AddOrderRealService.getInstance().getHistoryOrderByPhone(phoneNum);
				if(sorders != null && sorders.size() >0){
					text = AddOrderRealService.getInstance().getHistoryStr(sorders);
					if(text.indexOf(";")>0){
						text = text.substring(0,text.lastIndexOf(";"));
					}
				}
				
				text = text + "#" + userInfo;
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	@Path("/getHistoryOrderByphone")
	public  ActionResult getHistoryOrderByphone(){
		JSONObject jo = new JSONObject();
		String queryString = beat().getRequest().getQueryString();
		String phoneNum = beat().getRequest().getParameter("phoneNum");
		String callback = getValue(queryString, "jsoncallback");
		boolean isHave = true;
		String text = "";
		try {
			//根据电话记录获得记录
			List<SorderEntity> sorders = AddOrderRealService.getInstance().getHistoryOrderByPhone(phoneNum);
			if(sorders != null && sorders.size() >0){
				text = AddOrderRealService.getInstance().getHistoryStr(sorders);
				if(text.indexOf(";")>0){
					text = text.substring(0,text.lastIndexOf(";"));
				}
				jo.put("text", text);
			}else{
				isHave = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jo.put("result", isHave);
		return new JSONActionResult(callback==null?jo.toString():callback+"("+jo.toString()+")");
	}
	
	@Path("/getBusinessLibaryListByUserId")
	public  ActionResult getBusinessLibaryListByUserId(){
		JSONArray jo = new JSONArray();
		String queryString = beat().getRequest().getQueryString();
		String userId = beat().getRequest().getParameter("userId");
		String callback = getValue(queryString, "jsoncallback");
		try {
			List<BusinessLibaryEntity> lbls = RSBLL.getstance().getBusinessLibaryService()
					.getBusinessLibaryEntityListBypage("addperson ="+userId+" and status != -1", 1, 50, "busId");
			if(lbls!=null){
				jo = JSONArray.fromObject(lbls);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONActionResult(callback==null?jo.toString():callback+"("+jo.toString()+")");
	}
	
	@Path("/addOrder.html")
	@AutherCheck
	public ActionResult saveOrder(){
		//获得客户信息并保存
		String phoneNum = beat().getRequest().getParameter("phoneNum");
		String kehchannel = beat().getRequest().getParameter("kehchannel");
		String kehzj = beat().getRequest().getParameter("kehzj");
		String kehname = beat().getRequest().getParameter("kehname");
		String gongsmc = beat().getRequest().getParameter("gongsmc");
		String email = beat().getRequest().getParameter("email");
		String tongxdz = beat().getRequest().getParameter("tongxdz");
		String terraceOrderId = beat().getRequest().getParameter("terraceOrderId");
		// ---end
		Date now = new Date();
		LoginEntity loginEntity = new LoginEntity();
		loginEntity.setEmail(email);
		int channel = 1;
		if(StringUtil.isEmpty(kehchannel)){
			if(StringUtil.isNum(kehchannel)){
				channel = Integer.parseInt(kehchannel);
			}
		}
		loginEntity.setChannel(channel);
		//loginEntity.setCompanyname(gongsmc);
		loginEntity.setUserphone(phoneNum);
		loginEntity.setUsername(kehname);
		loginEntity.setAddress(tongxdz);
		loginEntity.setLandlinenumber(kehzj);
		//获得注册区域
		String companytype = beat().getRequest().getParameter("companytype");
		String citity = beat().getRequest().getParameter("citity");
		if(!StringUtil.isEmpty(citity)){
			citity="1";//默认北京
		}
		String citityarea = beat().getRequest().getParameter("citityarea");
		if(!StringUtil.isEmpty(citityarea)){
			citityarea="101";//默认朝阳区
		}
		JSONObject obj = null;
		JSONArray arrey = new JSONArray();
		if(StringUtil.isEmpty(companytype)){
			obj = new JSONObject();
			obj.put("propertyid", companytype);
			long lid = Long.valueOf(companytype);
			ProductPropertiesEntity entity = AddOrderRealService.getInstance().getProductPropertiesEntityByid(lid);
		    if(entity != null){
		    	obj.put("propertyval", entity.getType());
		    }
		    arrey.add(obj);
		}
		if(StringUtil.isEmpty(citity)){
			obj = new JSONObject();
			obj.put("propertyid", "city");
			obj.put("propertyval", citity);
			arrey.add(obj);
		}
        if(StringUtil.isEmpty(citityarea)){
        	obj.put("propertyid", "area");
			obj.put("propertyval", citityarea);
			arrey.add(obj);
		}
		String producepropertys =arrey.toString();
		//选择商品信息
		//格式 :商品id_类别多个以','分割(注：整体多个以‘|’分割)   例： 1_1|2_18,19,20|3_10|4_5|
		String product2CategroyIds = beat().getRequest().getParameter("selectProduct");
		String beiz = beat().getRequest().getParameter("beiz");
		//意向度
		String intentcode = beat().getRequest().getParameter("yixd");
		if(!StringUtil.isEmpty(intentcode)){
			intentcode = "1";
		}
		long loginuser = UtilsHelper.getLoginId(beat());
		String loginroleids = UtilsHelper.getLoginRoleids(beat());
		//增加所属公司  -- begin
		String userId = AddOrderRealService.getInstance().beforeAddOrderVali(loginEntity);
		long luid = Long.valueOf(userId);
        long busId = RealBusinessService.getInstance().getBusinessLibraryId(beat(), gongsmc,luid, citity, citityarea);
		int citityint = Integer.parseInt(citity);
		//AddOrderRealService.getInstance().saveOrder(userId,loginEntity, producepropertys, product2CategroyIds, beiz, Integer.parseInt(intentcode),loginuser,citityint,gongsmc,busId,citityarea);
		
		SaveOrderVo saveOrderVo = new SaveOrderVo();
		saveOrderVo.setUserId(userId);
		saveOrderVo.setLentity(loginEntity);
		saveOrderVo.setProducepropertys(producepropertys);
		saveOrderVo.setProduct2CategroyIds(product2CategroyIds);
		saveOrderVo.setBeiz(beiz);
		saveOrderVo.setIntentcode(Integer.parseInt(intentcode));
		saveOrderVo.setLogin(loginuser);
		saveOrderVo.setCitityint(citityint);
		saveOrderVo.setGongsmc(gongsmc);
		saveOrderVo.setBusId(busId);
		saveOrderVo.setCitityarea(citityarea);
		saveOrderVo.setTerraceOrderId(terraceOrderId);
		saveOrderVo.setChannel(String.valueOf(channel));
		saveOrderVo.setLoginroleids(loginroleids);
		
		AddOrderRealService.getInstance().saveOrder(saveOrderVo);
		
		String fromPage = beat().getRequest().getParameter("fromPage");
		if("customercenter".equals(fromPage)){
			return view("/order/customercenter/sucess");
		}
		return redirect("/order/downline");		
	}
	
	@Path("/dyncmicarea")
	public ActionResult dyncmicarea(){
		return new ActionResult() {
			@Override
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String parentId = beat().getRequest().getParameter("parentId");
				String text = "";
			    List<AreasEntity> le = AddOrderRealService.getInstance().getAreaEntityByparentId(parentId);
			    if(null != le && le.size() >0){
			    	JSONArray json = new JSONArray();
			    	JSONObject obj = null;
			    	for(AreasEntity entity : le){
			    		obj = new JSONObject();
			    		obj.put("AreasEntity", entity);
			    		json.add(obj);
			    	}
			    	text = json.toString();
			    }
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	public String getValue(String queryString,String name){
		if(StringUtils.isNotBlank(queryString) && StringUtils.isNotBlank(name)){
			String[] keyValueArr = queryString.split("&");
			for(int i=0;i<keyValueArr.length;i++){
				String key = keyValueArr[i].split("=")[0];
				if(name.equals(key)){
					return keyValueArr[i].split("=")[1];
				}
			}
		}
		return null;
	}
	
	@Path("/dyChangeprice")
	public ActionResult dyChangeprice(){
		return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String productId = beat().getRequest().getParameter("productId");
				String areaId = beat().getRequest().getParameter("localcity");
				String cateId = beat().getRequest().getParameter("cateId");
				String cityId = beat().getRequest().getParameter("cityId");
				String perties = beat().getRequest().getParameter("perties");
				if(!StringUtil.isNum(perties)){
					String pro = AddOrderRealService.getInstance().getPropertyBymc(perties);
					perties = pro;
				}
				String text ="";
				float price = AddOrderRealService.getInstance().getPriceBycondition(productId,cateId,areaId,perties);
				if(price == 0){
					price =  AddOrderRealService.getInstance().getPriceBycondition(productId,cateId,cityId,perties);
				}
				text = price+"";
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
