package com.jixiang.argo.lvzheng.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.bj58.sfft.json.orgjson.JSONException;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.frame.service.OrderAssembleService;
import com.jixiang.argo.lvzheng.service.AddOrderRealService;
import com.jixiang.argo.lvzheng.service.CouponsService;
import com.jixiang.argo.lvzheng.service.RealBusinessService;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.vo.UserCouponsVO;
import com.jixiang.union.interceptor.AutherCheck;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.FollowupEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.ProductEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.newcore.entity.SorderExtEntity;
@Path("/order")
public class ModifyOrderController extends AbstractController{

	@Path("/modifyOrder/{orderId:\\S+}/{page:\\S+}.html")
	@AutherCheck
	public ActionResult initModify(String orderId,String page){
		if(null != orderId && !"".equals(orderId)){
			//获得用户信息
			long orderId_id = Long.valueOf(orderId);
			
			
			List<SorderExtEntity> orderExtList= OrderAssembleService.getInstance().getOrderExtEntityList(orderId);
			if(orderExtList != null && orderExtList.size() > 0){
				for(SorderExtEntity s:orderExtList){
					if(s.getDataKey().equals("channel")){
						if(s.getDataValue().equals("25") || s.getDataValue().equals("26")){
							model().add("channelKey", s.getDataValue());
							model().add("channelValue", KVMap.channelMap.get(s.getDataValue()+""));
						}else{
							model().add("channelKey", "");
						}
					}else if(s.getDataKey().equals("terraceOrderId")){
						model().add("terraceOrderId", s.getDataValue());
					}
				}
			}
			
			LoginEntity loginEntity = AddOrderRealService.getInstance().getLoginEntityBysorder(orderId_id);
			model().add("loginEntity", loginEntity);
		    String userInfo = RealBusinessService.getInstance().getLoginEntityNoUserinfoStr(RealBusinessService.getInstance().getLoginEntityByphoneNum(loginEntity.getUserphone()));
		    model().add("userInfo", userInfo);
		    if(loginEntity.getChannel() != 0){
		    	model().add("channel", KVMap.channelMap.get(loginEntity.getChannel()+""));
		    }
			List<AreasEntity> changecity = AddOrderRealService.getInstance().getAreasEntity();
			model().add("changecity", changecity);
			//获得订单信息
			SorderEntity sorderEntity = AddOrderRealService.getInstance().getSorderEntityById(orderId_id);
			model().add("sorderEntity", sorderEntity);
			
			String gsmc = RealBusinessService.getInstance().getBusinessGongsmc(sorderEntity.getBusid());
			if(StringUtils.isNotBlank(gsmc)){
				model().add("gsmc", gsmc);
			}
			//获得一级区域
			List<AreasEntity> firstArea = AddOrderRealService.getInstance().getAreasEntity();
			model().add("firstArea", firstArea);
			//获得二级菜单
			//List<ProductPropertiesEntity> secondArea = AddOrderRealService.getInstance().getBusinessArea();
			List<AreasEntity> secondArea =  AddOrderRealService.getInstance().getAllAreasEntityByparent();
			model().add("secondArea", secondArea);
			
			//展示商品信息
			Map<ProductEntity,List<ProductCategoryEntity>> map = AddOrderRealService.getInstance().initAddorderpage("");
			model().add("productInfos", map);
			
			//公司类别secondArea 
			List<ProductPropertiesEntity> company = AddOrderRealService.getInstance()
					.getCompanyType("companytype");
			model().add("companytype", company);
			//公司规模
			List<ProductPropertiesEntity> gongsgm =  AddOrderRealService.getInstance()
					.getCompanyType("gongsgm");
			model().add("gongsgm", gongsgm);
			//增加年报公司类型
			List<ProductPropertiesEntity> gsnb =  AddOrderRealService.getInstance()
					.getCompanyType("regnianbao");
			model().add("gsnb", gsnb);
			// 增加年报公司类型
			List<ProductPropertiesEntity> gsmchz = AddOrderRealService.getInstance().getCompanyType("reggshm");
			model().add("gsmchz", gsmchz);
			//是否销售
			List<ProductPropertiesEntity> isseller =  AddOrderRealService.getInstance()
					.getCompanyType("seller");
			model().add("isseller", isseller);
			//服务周期
			List<ProductPropertiesEntity> bookOrder =  AddOrderRealService.getInstance()
					.getCompanyType("bookOrder");
			model().add("serviceCircle", bookOrder);
			//开户银行
			List<ProductPropertiesEntity> kahbank =  AddOrderRealService.getInstance()
					.getCompanyType("kahbank");
			model().add("kahbank", kahbank);
			//商标
			List<ProductPropertiesEntity> tradeOrder =  AddOrderRealService.getInstance()
					.getCompanyType("tradeOrder");
			model().add("tradeOrder", tradeOrder);
			//商标属性
			List<ProductPropertiesEntity> tradeCount =  AddOrderRealService.getInstance()
			.getCompanyType("tradeCount");
	        model().add("tradeCount", tradeCount);
			String productId ="1101";
			String coderCity ="1";
			//存储反面信息
			if(sorderEntity != null){
				productId = sorderEntity.getProductid()+"";
				coderCity = sorderEntity.getOrdercity()+"";
				if("1".equals(KVMap.ischanggecity.get(productId))){
					AreasEntity area = AddOrderRealService.getInstance().getAreasEntity(coderCity);
					model().add("firstArea", area);
					//获得二级菜单
					secondArea =  AddOrderRealService.getInstance().getAreasEntitychange(coderCity);
					model().add("secondArea", secondArea);
					if(StringUtil.isListNull(secondArea)){
						model().add("dissecondArea", secondArea.get(0));
					}
					model().add("iscahngge", "true");
				}
			}
			String xiadsj ="";
			if(sorderEntity != null){
				xiadsj = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss",sorderEntity.getPostime());
			}
			model().add("xiadsj", xiadsj);
			//意向度
			FollowupEntity followEntity = AddOrderRealService.getInstance().getFollowIntercode(orderId_id);
			if(followEntity != null){
				model().add("followEntity", followEntity);
			}
			String orderChildinfo = AddOrderRealService.getInstance().getSorderChildBySorderId(orderId_id);
			List<SorderChildrenEntity> childlist = null;
			try {
				childlist = AddOrderRealService.getInstance().getSorderChildBysorder(orderId_id);
				if(null != childlist && childlist.size() > 0){
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//许可特殊处理
			if(sorderEntity.getProductid() == 1106 && childlist != null && childlist.size() > 0){
				beat().getModel().add("suborders", childlist);
				for(SorderChildrenEntity sc : childlist){
					if(sc.getProdcateid() == 5099){//其他
						beat().getModel().add("subprice", sc.getMinprice());
						JSONArray projson = JSONArray.fromObject(sc.getPropertid());
						beat().getModel().add("xuke", projson);
					}
				}
			}
			model().add("orderChildinfo", orderChildinfo);
			//获得商品信息
			Map<ProductEntity,List<ProductCategoryEntity>> pmap = AddOrderRealService.getInstance().modifySorderDisplay(orderId_id);
			model().add("product4Categroy", pmap);
			model().add("backpage", "/order/"+page);
			
			//duxf 添加优惠券的信息
			String condition=" userid='"+loginEntity.getUserid()+"' and orderid='"+orderId+"' ";
		    List<UserCouponsVO> usercouponsList =  CouponsService.getInstance().getUserCoupons(condition, "");
		    if(usercouponsList.size()>0){
			    beat().getModel().add("userCoupon", usercouponsList.get(0));
		    }
		    //添加公司变更 企业形式  bgqyxs
			List<ProductPropertiesEntity> bgqyxs =  AddOrderRealService.getInstance()
					.getCompanyType("bgqyxs");
			List<ProductPropertiesEntity> addresslist = AddOrderRealService.getInstance()
					.getCompanyType("addresstype");
			beat().getModel().add("addresslist", addresslist);
			model().add("bgqyxs", bgqyxs);
			//增加历史代理记账商品(原类别修改展示情况)
			Map<Long,String> dlhistory =  AddOrderRealService.getInstance().getCateIdByOrderId(orderId_id);
			if(dlhistory != null){
				model().add("dlhistory", dlhistory);
			}
		}
		return view("/order/edit");
	}
	@Path("/modifysubmit.html")
	@AutherCheck
	public ActionResult modifyOrder(){
		//获得用户信息 更新用户
        // updateLoginEntity();
		//获得原子单信息  格式：子单Id_类别Id 多个使用“;”
		String orderChildinfo = beat().getRequest().getParameter("orderChildinfo");
        String productId = beat().getRequest().getParameter("productId");
    	String gongsmc = beat().getRequest().getParameter("gongsmc");
        //获得选择的类别（多个以‘,’号分割）
       String cateIds = beat().getRequest().getParameter("cateIds");
       String sumMoney = beat().getRequest().getParameter("yingfjesub");
       String shifjine = beat().getRequest().getParameter("yingfpaycon");
       
       String channel = beat().getRequest().getParameter("channel");
       String terraceOrderId = beat().getRequest().getParameter("terraceOrderId");
       
	   // ---end
       float totalMoney = 0;
       float shifmoney =0;
	    if(StringUtil.isEmpty(sumMoney)){
	    	totalMoney = Float.valueOf(sumMoney);
	    }
	    if(StringUtil.isEmpty(shifjine)){
	    	shifmoney = Float.valueOf(shifjine);
	    }
       //获得原ｃｉｔｙ
       String oldcity = beat().getRequest().getParameter("city_id");
       String changgecity = beat().getRequest().getParameter("selectcity");
       
        List<String> selectList = null;
        if(null != cateIds && !"".equals(cateIds.trim())){
        	selectList = new ArrayList<String>();
        	if(cateIds.indexOf(",")>-1){
        		String[] tmcat = cateIds.split(",");
        		for (int i = 0; i < tmcat.length; i++) {
        			selectList.add(tmcat[i]);
				}
        	}else{
        		selectList.add(cateIds);
        	}
        }
        float deletemoney =0;
 	   String sorderId = beat().getRequest().getParameter("sorderId");
 	   long sorder_id = 0l;
 		if(sorderId != null && !"".equals(sorderId)){
 			 sorder_id = Long.valueOf(sorderId);
 		}
 	    //获得备注
 	    String beiz = beat().getRequest().getParameter("beiz");
 	    String backpage = beat().getRequest().getParameter("backpage");
 	    String intentcode = beat().getRequest().getParameter("yixd");
	    long loginuser = UtilsHelper.getLoginId(beat());
	    //增加所属公司  -- begin
	    String userId = beat().getRequest().getParameter("userId");
	    long luserId = 0;
	    if(StringUtil.isEmpty(userId)){
	    	luserId = Long.valueOf(userId);
	    }
	    long busId = RealBusinessService.getInstance().getBusinessLibraryId(beat(), gongsmc,luserId, "", "");
	    if(StringUtil.isEmpty(changgecity)){
	    	 if(!StringUtil.isNum(changgecity)){
	     		  AreasEntity are = AddOrderRealService.getInstance().getAreasEntityBymc(changgecity) ;
	     		  if(are != null){
	     			  changgecity = are.getAreaid()+"";
	     		  }
	     	   }
	    }
        //公司注册
       if(productId.equals("1101") || productId.equals("2201")){
           if(StringUtil.isEmpty(changgecity)){
         	   if(!changgecity.equals(oldcity)){
         		   //进行切换城市操作
         		  Map<String,String> map = divideGszc(selectList,sorder_id,"");
         		  int citityint = Integer.parseInt(changgecity);
         		  int intentcodeIn = 3;
         		  if(StringUtil.isEmpty(intentcode)){
         			 intentcodeIn = Integer.parseInt(intentcode);
         		  }
         		 RealBusinessService.getInstance().changgeCity(selectList,productId,sorder_id,totalMoney,
         				beiz,citityint,loginuser,gongsmc,map, intentcodeIn,shifmoney);
         		  if(StringUtil.isEmpty(backpage)){
         		    	return redirect(backpage);
         		    }
         	   }
            }
    	   comapnyReg(selectList,productId,orderChildinfo,deletemoney);
       }
       //财会服务
       if(productId.equals("1105") || productId.equals("2202")){
    	   if(StringUtil.isEmpty(changgecity)){
         	   if(!changgecity.equals(oldcity)){ 
         		   //进行切换城市操作
         		  Map<String,String> map = divideDljz(selectList,sorder_id,"");
         		  int citityint = Integer.parseInt(changgecity);
         		  int intentcodeIn = 3;
         		  if(StringUtil.isEmpty(intentcode)){
         			 intentcodeIn = Integer.parseInt(intentcode);
         		  }
         		 RealBusinessService.getInstance().changgeCity(selectList,productId,sorder_id,totalMoney,
         				beiz,citityint,loginuser,gongsmc,map, intentcodeIn,shifmoney);
         		  if(StringUtil.isEmpty(backpage)){
         		    	return redirect(backpage);
         		    }
         	   }
            }
    	   dalJizBusiness(selectList,productId,orderChildinfo,deletemoney);
       }
       //公司变更
       if(productId.equals("1102")){
    	   companyChange(selectList,productId,orderChildinfo,deletemoney);
       }
       //商标注册
       if(productId.equals("1104") || productId.equals("2203")){
    	   tradeReg(selectList,productId,orderChildinfo,deletemoney);
       }
       //许可
       if(productId.equals("1106")){
    	   String qtprice = beat().getRequest().getParameter("qitprice");
    	  String qitmc = beat().getRequest().getParameter("qitmc");
    	  JSONArray array = new JSONArray();
     	   if(StringUtil.isEmpty(qitmc)){
     		   ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("qitmc").get(0);
     	       if(qt != null){
     	    	   JSONObject qitobj = new JSONObject();
     	    	   qitobj.put("propertyid", qt.getProid()+"");
     	    	   qitobj.put("propertyval",qitmc);
     	    	   array.add(qitobj);
     	       }
     	   }
     	 String qitcontent = beat().getRequest().getParameter("qitcontent");
            if(StringUtil.isEmpty(qitcontent)){
     		   ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("qitcontent").get(0);
     		   if(qtc != null){
     	    	   JSONObject qitobj = new JSONObject();
     	    	   qitobj.put("propertyid", qtc.getProid()+"");
     	    	   qitobj.put("propertyval",qitcontent);
     	    	   array.add(qitobj);
     	       }
     	   }
           
    	   xukereg(orderChildinfo,qtprice,array.toString());
       }
	    //更新sorder
	    SorderEntity orderEnt = AddOrderRealService.getInstance().getSorderEntityById(sorder_id);
	    orderEnt.setIntentcode(Integer.valueOf(intentcode));   //修改订单记录意向度  duxf 2015-07-23
	    AddOrderRealService.getInstance().modifySorderEntity( orderEnt, beiz, "", totalMoney, new Date(),deletemoney,gongsmc,busId,shifmoney);
	    
	    List<SorderExtEntity> sorderExtList = OrderAssembleService.getInstance().getOrderExtEntityList(String.valueOf(sorder_id));
	    if(sorderExtList != null && sorderExtList.size() > 0){
	    	try{
		    	for(SorderExtEntity s:sorderExtList){
		    		if(s.getDataKey().equals("channel")){
						s.setDataValue(channel);
						OrderAssembleService.getInstance().updateOrderExt(s);
					}else if(s.getDataKey().equals("terraceOrderId")){
						s.setDataValue(terraceOrderId);
						OrderAssembleService.getInstance().updateOrderExt(s);
					}
		    	}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	    //意向度
	    String followEntityId = beat().getRequest().getParameter("followEntityId");
	    AddOrderRealService.getInstance().modifyFollowEntity(followEntityId,intentcode,beiz,loginuser);
	    int orderfrom = orderEnt.getOrderform();
	    if(StringUtil.isEmpty(backpage)){
	    	return redirect(backpage);
	    }
	    if(orderfrom == 3){
	    	//表示后台
	    	return redirect("/order/downline");
	    }else{
	    	//表示线上
	    	return redirect("/order/upline");
	    }
	}
	private void xukereg(String orderChildinfo,String qtprice,String properties) {
		// TODO Auto-generated method stub
		if(null == orderChildinfo || "".equals(orderChildinfo))
			return;
		String[] soarray = orderChildinfo.split(";");
		for(String sostr : soarray){
			if(sostr == null || "".equals(sostr)){
				String[] orderinfo = sostr.split("_");
				long coid = Long.parseLong(orderinfo[0]);
				SorderChildrenEntity sc = null;
				try {
					sc = RSBLL.getstance().getSorderChildrenService().getSorderChildrenEntityByid(coid);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(sc != null){
					sc.setPropertid(properties);
					sc.setMinprice(Float.parseFloat(qtprice));
					try {
						RSBLL.getstance().getSorderChildrenService().updateSorderChildrenEntity(sc);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
				
		}
	}
	/**
	 *商标注册
	 * @param selectList
	 * @param productId
	 * @param orderChildinfo
	 * @param totalMoney
	 */
	private void tradeReg(List<String> selectList, String productId,String orderChildinfo, float totalMoney) {
		String tradeqitmc = "";
		String tradeqitcontent = "";
		String qitprice = "";
		
		String sorderId = beat().getRequest().getParameter("sorderId");
		long sorder_id = 0l;
		if (sorderId != null && !"".equals(sorderId)) {
			sorder_id = Long.valueOf(sorderId);
		}
		//获得任意一个属性中的property值
		String perInfo = AddOrderRealService.getInstance().getQitPerperties(sorder_id);
		JSONArray array = null;
		String tradeOrderzc = "";
		String rradecity = "";
		String tradecitityarea = "";
		String bigsl = "";
		String smallsl = "";
		
		Map<String, String> map = null;// key:类别 value：属性集
		if (StringUtil.isListNull(selectList)) {
			map = new HashMap<String, String>();
			for (String str : selectList) {
				array = new JSONArray();
				String[] tmp = str.split("_");
				String fla = tmp[1];
				if(fla.equals("0")){
					fla ="";  
				}
				if (fla.equals("20")) {
					if(perInfo !=""){
						try {
							com.bj58.sfft.json.orgjson.JSONArray marr = new com.bj58.sfft.json.orgjson.JSONArray(perInfo);
							for (int i = 0; i < marr.length(); i++) {
								com.bj58.sfft.json.orgjson.JSONObject ob = marr.getJSONObject(i);
								String propertyid = (String) ob.get("propertyid");
								String val =(String) ob.get("propertyval");
								if(propertyid.equals("city") || propertyid.equals("area")){
									JSONObject qitmt = new JSONObject();
									qitmt.put("propertyid", propertyid);
									qitmt.put("propertyval", val);
									array.add(qitmt);
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					// 其他
					tradeqitmc = beat().getRequest().getParameter("tradeqitmc");
					if (StringUtil.isEmpty(tradeqitmc)) {
						ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("qitmc").get(0);
						if (qt != null) {
							JSONObject qitobj = new JSONObject();
							qitobj.put("propertyid", qt.getProid() + "");
							qitobj.put("propertyval", tradeqitmc);
							array.add(qitobj);
						}
					}
					tradeqitcontent = beat().getRequest().getParameter("tradeqitcontent");
					if (StringUtil.isEmpty(tradeqitcontent)) {
						ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("qitcontent").get(0);
						if (qtc != null) {
							JSONObject qitobj = new JSONObject();
							qitobj.put("propertyid", qtc.getProid() + "");
							qitobj.put("propertyval", tradeqitcontent);
							array.add(qitobj);
						}
					}
					qitprice = beat().getRequest().getParameter("tradeqitprice");
					if (StringUtil.isEmpty(qitprice)) {
						JSONObject qitobj = new JSONObject();
						qitobj.put("propertyid", "qtprice");
						qitobj.put("propertyval", qitprice);
						array.add(qitobj);
					}
				} else {
					//类型
					tradeOrderzc = beat().getRequest().getParameter("tradeOrderzc"+fla);
					if(!StringUtil.isEmpty(tradeOrderzc)){
						tradeOrderzc ="文字商标";
					}
					if(StringUtil.isEmpty(tradeOrderzc)){
						if(!StringUtil.isNum(tradeOrderzc)){
							tradeOrderzc = AddOrderRealService.getInstance().getPropertyBymc(tradeOrderzc);
						}
						JSONObject tradeobj = AddOrderRealService.getInstance().getJsonObject(tradeOrderzc);
						if (tradeobj != null) {
							array.add(tradeobj);
						}
					}
					// 区域
					rradecity = beat().getRequest().getParameter("rradecity" + fla);
					if (StringUtil.isEmpty(rradecity)) {
						if (!StringUtil.isNum(rradecity)) {
							rradecity = AddOrderRealService.getInstance().getCityAreaBymc(rradecity);
						}
						JSONObject ctyobj = AddOrderRealService.getInstance().getJsonAreaObject(rradecity, "city");
						if (ctyobj != null) {
							array.add(ctyobj);
						}
					}
					
					tradecitityarea = beat().getRequest().getParameter("tradecitityarea" + fla);
					if (StringUtil.isEmpty(tradecitityarea)) {
						if (!StringUtil.isNum(tradecitityarea)) {
							tradecitityarea = AddOrderRealService.getInstance().getCityAreaBymc(tradecitityarea);
						}
						JSONObject areaobj = AddOrderRealService.getInstance().getJsonAreaObject(tradecitityarea, "area");
						if (areaobj != null) {
							array.add(areaobj);
						}
					}
					//大数量
					bigsl = beat().getRequest().getParameter("bigsl" + fla);
					if (StringUtil.isEmpty(bigsl)) {
						ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("bigtradeCount").get(0);
						if (qtc != null) {
							JSONObject qitobj = new JSONObject();
							qitobj.put("propertyid", qtc.getProid() + "");
							qitobj.put("propertyval", bigsl);
							array.add(qitobj);
						}
					}
					smallsl = beat().getRequest().getParameter("smallsl" + fla);
					if (StringUtil.isEmpty(smallsl)) {
						ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("smalltradeCount").get(0);
						if (qtc != null) {
							JSONObject qitobj = new JSONObject();
							qitobj.put("propertyid", qtc.getProid() + "");
							qitobj.put("propertyval", smallsl);
							array.add(qitobj);
						}
					}
					//价格
					String gstrandezc = beat().getRequest().getParameter("gstrandezc"+fla);
					 if(StringUtil.isEmpty(gstrandezc)){
		 				   JSONObject priObj = new JSONObject();
		 				   priObj.put("propertyid", "singleprice");
		 				   priObj.put("propertyval", gstrandezc);
		 				  array.add(priObj);
		 			 }
				}
				map.put(str, array.toString());
			}
		}
		//执行业务处理
		commonDeal(map, orderChildinfo, selectList, totalMoney, qitprice,sorder_id);
	}
	/**
	 * 公司变更
	 * @param selectList
	 * @param productId
	 * @param orderChildinfo
	 * @param totalMoney
	 */
	private void companyChange(List<String> selectList, String productId,
			String orderChildinfo, float totalMoney) {
		// 公司变更
		String biangqitmc = "";
		String biangqitcontent = "";
		String qitprice = "";

		String sorderId = beat().getRequest().getParameter("sorderId");
		long sorder_id = 0l;
		if (sorderId != null && !"".equals(sorderId)) {
			sorder_id = Long.valueOf(sorderId);
		}
		//获得任意一个属性中的property值
		String perInfo = AddOrderRealService.getInstance().getQitPerperties(sorder_id);
		JSONArray array = null;
		String gongsadecity_ = "";
		String biangcitityarea_ = "";
		Map<String, String> map = null;// key:类别 value：属性集
		// 选择集合
		String gsxs ="";
		String gsgm ="";
		String qybgxs = "";
		if (StringUtil.isListNull(selectList)) {
			map = new HashMap<String, String>();
			for (String str : selectList) {
				array = new JSONArray();
				String[] tmp = str.split("_");
				String cateId = tmp[0];
				String fla = tmp[1];
				if (fla.equals("15")) {
					if(perInfo !=""){
						try {
							com.bj58.sfft.json.orgjson.JSONArray marr = new com.bj58.sfft.json.orgjson.JSONArray(perInfo);
							for (int i = 0; i < marr.length(); i++) {
								com.bj58.sfft.json.orgjson.JSONObject ob = marr.getJSONObject(i);
								String propertyid = (String) ob.get("propertyid");
								String val =(String) ob.get("propertyval");
								if(propertyid.equals("city") || propertyid.equals("area")){
									JSONObject qitmt = new JSONObject();
									qitmt.put("propertyid", propertyid);
									qitmt.put("propertyval", val);
									array.add(qitmt);
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					// 其他
					biangqitmc = beat().getRequest().getParameter("biangqitmc");
					if (StringUtil.isEmpty(biangqitmc)) {
						ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("qitmc").get(0);
						if (qt != null) {
							JSONObject qitobj = new JSONObject();
							qitobj.put("propertyid", qt.getProid() + "");
							qitobj.put("propertyval", biangqitmc);
							array.add(qitobj);
						}
					}
					biangqitcontent = beat().getRequest().getParameter("biangqitcontent");
					if (StringUtil.isEmpty(biangqitcontent)) {
						ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("qitcontent").get(0);
						if (qtc != null) {
							JSONObject qitobj = new JSONObject();
							qitobj.put("propertyid", qtc.getProid() + "");
							qitobj.put("propertyval", biangqitcontent);
							array.add(qitobj);
						}
					}
					qitprice = beat().getRequest().getParameter("biangqitprice");
					if (StringUtil.isEmpty(qitprice)) {
						JSONObject qitobj = new JSONObject();
						qitobj.put("propertyid", "qtprice");
						qitobj.put("propertyval", qitprice);
						array.add(qitobj);
					}
				} else {
					// 区域
					gongsadecity_ = beat().getRequest().getParameter("gongsadecity_" + fla);
					if (StringUtil.isEmpty(gongsadecity_)) {
						if (!StringUtil.isNum(gongsadecity_)) {
							gongsadecity_ = AddOrderRealService.getInstance().getCityAreaBymc(gongsadecity_);
						}
						JSONObject ctyobj = AddOrderRealService.getInstance().getJsonAreaObject(gongsadecity_, "city");
						if (ctyobj != null) {
							array.add(ctyobj);
						}
					}
					biangcitityarea_ = beat().getRequest().getParameter("biangcitityarea_" + fla);
					if (StringUtil.isEmpty(biangcitityarea_)) {
						if (!StringUtil.isNum(biangcitityarea_)) {
							biangcitityarea_ = AddOrderRealService.getInstance().getCityAreaBymc(biangcitityarea_);
						}
						JSONObject areaobj = AddOrderRealService.getInstance().getJsonAreaObject(biangcitityarea_, "area");
						if (areaobj != null) {
							array.add(areaobj);
						}
					}
					//增加公司变更属性   adder  wuyin     addtime  2015-08-11
					//公司形式
					gsxs = beat().getRequest().getParameter("bgqyxs_"+fla);
					if(StringUtil.isEmpty(gsxs)){
						 if(!StringUtil.isNum(gsxs)){
							 gsxs = AddOrderRealService.getInstance().getPropertyBymc(gsxs);
						   }
						  JSONObject serverObj = AddOrderRealService.getInstance().getJsonObject(gsxs);
	 	 				  array.add(serverObj);
					}
					//公司规模
					gsgm = beat().getRequest().getParameter("biangswgm_"+fla);
					if(StringUtil.isEmpty(gsgm)){
						 if(!StringUtil.isNum(gsgm)){
							 gsgm = AddOrderRealService.getInstance().getPropertyBymc(gsgm);
						  }
						  JSONObject serverObj = AddOrderRealService.getInstance().getJsonObject(gsgm);
	 	 				  array.add(serverObj);
					}
					//企业变更形式
					if("2009".equals(cateId) || "2010".equals(cateId)|| "2011".equals(cateId)){
						qybgxs = beat().getRequest().getParameter("biangqyxs_"+fla);
						if(StringUtil.isEmpty(qybgxs)){
							 if(!StringUtil.isNum(qybgxs)){
								 qybgxs = AddOrderRealService.getInstance().getPropertyBymc(qybgxs);
							  }
							  JSONObject serverObj = AddOrderRealService.getInstance().getJsonObject(qybgxs);
		 	 				  array.add(serverObj);
						}
					}
					//价格
					String biangmt_ = beat().getRequest().getParameter("biangmt_"+fla);
					 if(StringUtil.isEmpty(biangmt_)){
		 				   JSONObject priObj = new JSONObject();
		 				   priObj.put("propertyid", "singleprice");
		 				   priObj.put("propertyval", biangmt_);
		 				  array.add(priObj);
		 			 }
				}
				map.put(cateId, array.toString());
			}
		}
		// 执行业务
		 commonDeal(map, orderChildinfo, selectList, totalMoney, qitprice,sorder_id);
	}
	/**
	 * 代理记账处理
	 */
	private void dalJizBusiness (List<String> selectList,String productId,String orderChildinfo,float totalMoney){
		  //获得原子单信息  格式：子单Id_类别Id 多个使用“;”
        String sorderId = beat().getRequest().getParameter("sorderId");
		long sorder_id = 0l;
		if(sorderId != null && !"".equals(sorderId)){
			 sorder_id = Long.valueOf(sorderId);
		}
		 String qitprice ="";
		Map<String,String> map = divideDljz(selectList,sorder_id,qitprice);
 	   //执行
 	  commonDeal(map, orderChildinfo, selectList, totalMoney, qitprice,sorder_id);
	}
	
	public Map<String,String> divideDljz(List<String> selectList,long sorder_id,String qitprice){
		//代理记账
		String dlqitmc ="";
        String dlqitcontent ="";
		//获得任意一个属性中的property值
	   String perInfo = AddOrderRealService.getInstance().getQitPerperties(sorder_id);
       JSONArray array = null;
 	   String dljzcity_ ="";
 	   String dljzcitityarea_ ="";
 	   String serviceCircle_ ="";
 	   String dljzkahbank_ ="";
 	   String dlcompanytype = beat().getRequest().getParameter("dljzcompanytype");
	   String dljzgongsgm_ ="";
 	   Map<String,String> map = null;//key:类别 value：属性集
 	   //选择集合
 	   if(StringUtil.isListNull(selectList)){
 		  map = new HashMap<String, String>();
 		   for(String str : selectList){
 			  array = new JSONArray();
 			   String[] tmp = str.split("_");
 			   String fla =tmp[1];
 			   String cateId = tmp[0];
                if(fla.equals("10")){
                	if(perInfo !=""){
						try {
							com.bj58.sfft.json.orgjson.JSONArray marr = new com.bj58.sfft.json.orgjson.JSONArray(perInfo);
							for (int i = 0; i < marr.length(); i++) {
								com.bj58.sfft.json.orgjson.JSONObject ob = marr.getJSONObject(i);
								String propertyid = (String) ob.get("propertyid");
								String val =(String) ob.get("propertyval");
								if(propertyid.equals("city") || propertyid.equals("area")){
									JSONObject qitmt = new JSONObject();
									qitmt.put("propertyid", propertyid);
									qitmt.put("propertyval", val);
									array.add(qitmt);
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
 				   //其他
             	   dlqitmc = beat().getRequest().getParameter("dlqitmc");
             	   if(StringUtil.isEmpty(dlqitmc)){
             		   ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("qitmc").get(0);
             	       if(qt != null){
             	    	   JSONObject qitobj = new JSONObject();
             	    	   qitobj.put("propertyid", qt.getProid()+"");
             	    	   qitobj.put("propertyval",dlqitmc);
             	    	   array.add(qitobj);
             	       }
             	   }
             	  dlqitcontent = beat().getRequest().getParameter("dlqitcontent");
                    if(StringUtil.isEmpty(dlqitcontent)){
             		   ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("qitcontent").get(0);
             		   if(qtc != null){
             	    	   JSONObject qitobj = new JSONObject();
             	    	   qitobj.put("propertyid", qtc.getProid()+"");
             	    	   qitobj.put("propertyval",dlqitcontent);
             	    	   array.add(qitobj);
             	       }
             	   }
                    qitprice = beat().getRequest().getParameter("dlqitprice");
                	   if(StringUtil.isEmpty(qitprice)){
                		   JSONObject qitobj = new JSONObject();
             	    	   qitobj.put("propertyid", "qtprice");
             	    	   qitobj.put("propertyval",qitprice);
             	    	   array.add(qitobj);
                	   }
 			   }else{
 				  //区域 
 				  dljzcity_ = beat().getRequest().getParameter("dljzcity_"+fla);
	 			   if(StringUtil.isEmpty(dljzcity_)){
	 				   if(!StringUtil.isNum(dljzcity_)){
	 					   dljzcity_ = AddOrderRealService.getInstance().getCityAreaBymc(dljzcity_);
	 				   }
	 				   JSONObject ctyobj = AddOrderRealService.getInstance().getJsonAreaObject(dljzcity_, "city");
	 				   if(ctyobj != null){
	 					   array.add(ctyobj);
	 				   }
	 			   }
	 			  dljzcitityarea_ = beat().getRequest().getParameter("dljzcitityarea_"+fla);
	 			   if(StringUtil.isEmpty(dljzcitityarea_)){
	 				  if(!StringUtil.isNum(dljzcitityarea_)){
	 					 dljzcitityarea_ = AddOrderRealService.getInstance().getCityAreaBymc(dljzcitityarea_);
	 				   }
	 				 JSONObject areaobj = AddOrderRealService.getInstance().getJsonAreaObject(dljzcitityarea_, "area");
					   if(areaobj != null){
						   array.add(areaobj);
					   }
	 			   }
 				   
 				   if(fla.equals("0") || fla.equals("1")){
 					  if(!StringUtil.isNum(dlcompanytype)){
 						 dlcompanytype = AddOrderRealService.getInstance().getPropertyBymc(dlcompanytype);
	  			       }
 					   JSONObject companyobj = AddOrderRealService.getInstance().getJsonObject(dlcompanytype);
 					   if(companyobj != null){
 						   array.add(companyobj);
 					   }
 					   //服务周期
 					   if(fla.equals("0")){
 						  serviceCircle_ = beat().getRequest().getParameter("serviceCircle_"+fla);
 						  if(StringUtil.isEmpty(serviceCircle_)){
 							  if(!StringUtil.isNum(serviceCircle_)){
 								 serviceCircle_ = AddOrderRealService.getInstance().getPropertyBymc(serviceCircle_);
 							  }
 							  JSONObject serverObj = AddOrderRealService.getInstance().getJsonObject(serviceCircle_);
 		 	 				  array.add(serverObj);
 						  }
 						  dljzgongsgm_ = beat().getRequest().getParameter("dljzgongsgm_"+fla);
 		 	 			   if(StringUtil.isEmpty(dljzgongsgm_)){
 		 	 				   if(!StringUtil.isNum(str)){
 		 	 					   dljzgongsgm_ = AddOrderRealService.getInstance().getPropertyBymc(dljzgongsgm_);
 		 	 				   }
 		 	 	 			   JSONObject gongsObj = AddOrderRealService.getInstance().getJsonObject(dljzgongsgm_);
 		 	 				   array.add(gongsObj);
 		 	 			   }
 					   }
 					   //招商银行
 					  if(fla.equals("1")){
 						 dljzkahbank_ =beat().getRequest().getParameter("dljzkahbank_"+fla);
 						 if(StringUtil.isEmpty(dljzkahbank_)){
 							if(!StringUtil.isNum(dljzkahbank_)){
 								dljzkahbank_ = AddOrderRealService.getInstance().getPropertyBymc(dljzkahbank_);
							  }
							  JSONObject delbankObj = AddOrderRealService.getInstance().getJsonObject(dljzkahbank_);
		 	 				  array.add(delbankObj);
						  }
					   }
 				   }
 				   //价格
 				  String dljzprice_ = beat().getRequest().getParameter("dljzprice_"+fla);
				  if(StringUtil.isEmpty(dljzprice_)){
	 				   JSONObject priObj = new JSONObject();
	 				   priObj.put("propertyid", "singleprice");
	 				   priObj.put("propertyval", dljzprice_);
	 				  array.add(priObj);
	 			   }
 			   }
               map.put(cateId, array.toString());
 		   }
 	   }
 	   return map;
	}
	
	
	private void comapnyReg(List<String> selectList,String productId,String orderChildinfo,float totalMoney){
 	  
      //获得原子单信息  格式：子单Id_类别Id 多个使用“;”
        String sorderId = beat().getRequest().getParameter("sorderId");
		long sorder_id = 0l;
		if(sorderId != null && !"".equals(sorderId)){
			 sorder_id = Long.valueOf(sorderId);
		}
	   String qtprice ="";
       Map<String,String> map = divideGszc(selectList,sorder_id,qtprice);  
 	   //执行业务处理
 	  commonDeal(map, orderChildinfo, selectList, totalMoney, qtprice,sorder_id);
	}
	
	private Map<String,String> divideGszc(List<String> selectList,long sorder_id,String qtprice){
		 //公司注册
	   String qitmc ="";
       String qitcontent ="";
       String perInfo = AddOrderRealService.getInstance().getQitPerperties(sorder_id);
       JSONArray array = null;
  	   String zccity_ ="";
  	   String zccitityarea_ ="";
  	   String zccompanytype =""; 
 	   String zcgongsgm_ ="";
 	   String isseller ="";
  	   Map<String,String> map = null;//key:类别 value：属性集
		   //选择集合
	 	   if(StringUtil.isListNull(selectList)){
	 		  map = new HashMap<String, String>();
	 		   for(String str : selectList){
	 			  array = new JSONArray();
	 			   String[] tmp = str.split("_");
	 			   String cateId = tmp[0];
	 			   String fla =tmp[1];
	                if(fla.equals("25")){
	                  	if(perInfo !=""){
							try {
								com.bj58.sfft.json.orgjson.JSONArray marr = new com.bj58.sfft.json.orgjson.JSONArray(perInfo);
								for (int i = 0; i < marr.length(); i++) {
									com.bj58.sfft.json.orgjson.JSONObject ob = marr.getJSONObject(i);
									String propertyid = (String) ob.get("propertyid");
									String val =(String) ob.get("propertyval");
									if(propertyid.equals("city") || propertyid.equals("area")){
										JSONObject qitmt = new JSONObject();
										qitmt.put("propertyid", propertyid);
										qitmt.put("propertyval", val);
										array.add(qitmt);
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
	 				   //其他
	             	   qitmc = beat().getRequest().getParameter("qitmc");
	             	   if(StringUtil.isEmpty(qitmc)){
	             		   ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("qitmc").get(0);
	             	       if(qt != null){
	             	    	   JSONObject qitobj = new JSONObject();
	             	    	   qitobj.put("propertyid", qt.getProid()+"");
	             	    	   qitobj.put("propertyval",qitmc);
	             	    	   array.add(qitobj);
	             	       }
	             	   }
	             	  qitcontent = beat().getRequest().getParameter("qitcontent");
	                    if(StringUtil.isEmpty(qitcontent)){
	             		   ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("qitcontent").get(0);
	             		   if(qtc != null){
	             	    	   JSONObject qitobj = new JSONObject();
	             	    	   qitobj.put("propertyid", qtc.getProid()+"");
	             	    	   qitobj.put("propertyval",qitcontent);
	             	    	   array.add(qitobj);
	             	       }
	             	   }
	                	   qtprice = beat().getRequest().getParameter("qitprice");
	                	   if(StringUtil.isEmpty(qtprice)){
	                		   JSONObject qitobj = new JSONObject();
	             	    	   qitobj.put("propertyid", "qtprice");
	             	    	   qitobj.put("propertyval",qtprice);
	             	    	   array.add(qitobj);
	                	   }
	 			   }else{
	 				  zccompanytype = beat().getRequest().getParameter("zccompanytype_"+fla);
	 				  zccity_ = beat().getRequest().getParameter("zccity_"+fla);
	 	 			   if(StringUtil.isEmpty(zccity_)){
	 	 				   if(!StringUtil.isNum(zccity_)){
	 	 					   zccity_ = AddOrderRealService.getInstance().getCityAreaBymc(zccity_);
	 	 				   }
	 	 				   JSONObject ctyobj = AddOrderRealService.getInstance().getJsonAreaObject(zccity_, "city");
	 	 				   if(ctyobj != null){
	 	 					   array.add(ctyobj);
	 	 				   }
	 	 			   }
	 	 			   zccitityarea_ = beat().getRequest().getParameter("zccitityarea_"+fla);
	 	 			   if(StringUtil.isEmpty(zccitityarea_)){
	 	 				 if(!StringUtil.isNum(zccitityarea_)){
	 	 					zccitityarea_ = AddOrderRealService.getInstance().getCityAreaBymc(zccitityarea_);
		 				   }
	 	 				 JSONObject areaobj = AddOrderRealService.getInstance().getJsonAreaObject(zccitityarea_, "area");
						   if(areaobj != null){
							   array.add(areaobj);
						   }
	 	 			   }
	 	 			   
	 	 			   if(cateId.equals("1007")|| cateId.equals("1008") ){
	 	 				 //年度企业
	  			         String gsnb =  beat().getRequest().getParameter("zccompanytypett_"+fla);
	  			         if(!StringUtil.isNum(gsnb)){
	  			        	gsnb = AddOrderRealService.getInstance().getPropertyBymc(gsnb);
	  			         }
	  			         if(StringUtil.isEmpty(gsnb)){
	  			        	  JSONObject priObj = new JSONObject();
	  			        	  long lpro = Long.valueOf(gsnb);
	  			        	  ProductPropertiesEntity me = AddOrderRealService.getInstance().getProductPropertiesEntityByid(lpro);
	  			        	  if(me != null){
	  			        	     priObj.put("propertyid", gsnb);
	   	 	 				     priObj.put("propertyval", me.getType()) ;
	   	 	 				     array.add(priObj);
	  			        	  }
	  			         }
	 	 				   
	 	 			   }else if("1010".equals(cateId)){
	 	 				   String dljzkahbank = beat().getRequest().getParameter("dljzkahbank_"+fla);
	 	 				   if(!StringUtil.isNum(dljzkahbank)){
	 	 					 dljzkahbank = AddOrderRealService.getInstance().getPropertyBymc(dljzkahbank);
		  			       }
	 	 			      if(StringUtil.isEmpty(dljzkahbank)){
	  			        	  JSONObject priObj = new JSONObject();
	  			        	  long lpro = Long.valueOf(dljzkahbank);
	  			        	  ProductPropertiesEntity me = AddOrderRealService.getInstance().getProductPropertiesEntityByid(lpro);
	  			        	  if(me != null){
	  			        	     priObj.put("propertyid", dljzkahbank);
	   	 	 				     priObj.put("propertyval", me.getType())	 ;
	   	 	 				     array.add(priObj);
	  			        	  }
	  			         }
	 	 			   }else if(!"1011".equals(cateId) && !"1012".equals(cateId) && !"1013".equals(cateId) && !"1014".equals(cateId)){
	 	 				    if(!StringUtil.isNum(zccompanytype)){
	 	 				    	zccompanytype = AddOrderRealService.getInstance().getPropertyBymc(zccompanytype);
		  			         }
		 	 				 JSONObject companyobj = AddOrderRealService.getInstance().getJsonObject(zccompanytype);
		 			        if(companyobj != null){
		 			        	array.add(companyobj);
		 			        }
		  	 			   zcgongsgm_ = beat().getRequest().getParameter("zcgongsgm_"+fla);
		  	 			   if(StringUtil.isEmpty(zcgongsgm_)){
		  	 				 if(!StringUtil.isNum(zcgongsgm_)){
		  	 					 zcgongsgm_ = AddOrderRealService.getInstance().getPropertyBymc(zcgongsgm_);
		 	 				   }
		  	 	 			   JSONObject gongsObj = AddOrderRealService.getInstance().getJsonObject(zcgongsgm_);
		  	 				   array.add(gongsObj);
		  	 			   }
	 	 			   }
	 	 			   
	 	 			   if(fla.equals("1")){
	 						    String adt = beat().getRequest().getParameter("addresstypevalue");
	 						    if(adt != null && !"".equals(adt)){
	 						    	JSONObject adtobj = AddOrderRealService.getInstance().getJsonObject(adt);
	 						    	if(adtobj != null){
	 						    		array.add(adtobj);
	 						    	}
	 						    }
	 	 			   }
	 	 			   //获得价格
	 	 			   String price = beat().getRequest().getParameter("zcprice_"+fla);
	 	 			   if(StringUtil.isEmpty(price)){
	 	 				   JSONObject priObj = new JSONObject();
	 	 				   priObj.put("propertyid", "singleprice");
	 	 				   priObj.put("propertyval", price)	 ;
	 	 				  array.add(priObj);
	 	 			   }
	 			   }
	               map.put(cateId, array.toString());
	 		   }
	 	   }
	 	   return map;
	}
	
	public void commonDeal(Map<String,String> map,String orderChildinfo,List<String> selectList,float totalMoney,String qtprice,long sorder_id){
		 String productId = beat().getRequest().getParameter("productId");
	 	  if(StringUtil.isEmpty(orderChildinfo)){
	 	      	if(orderChildinfo.indexOf(";")>-1){
	 	      		String [] tmpOrder = orderChildinfo.split(";");
	 	      		for(int i =0;i< tmpOrder.length;i++){
	 	      			String ordertmp = tmpOrder[i];
	 	      			String[] tmp = ordertmp.split("_");
	 	      			String orChild = tmp[0];
	 	      			String cateid  = tmp[1];
	 	      			if(productId.equals("1104")|| productId.equals("2203")){
	 	      				if(cateid.equals("30017")){
		 	      				cateid = tmp[1]+"_20";
		 	      			}else{ 
		 	      				cateid = tmp[1]+"_"+i;
		 	      			}
	 	      			}else{
	 	      				cateid  = tmp[1];
	 	      			}
	 	      			if(map != null){
	 	      				if(map.containsKey(cateid)){
	 	      					if(StringUtil.isEmpty(orChild)){
	 	      						long childId = Long.valueOf(orChild);
	 	      						SorderChildrenEntity sentity = AddOrderRealService.getInstance()
	 	      								.getSorderChildrenEntityById(childId);
	 	      						
	 	      						String pros = map.get(cateid);
	 	      						String strpro = AddOrderRealService.getInstance().getPriceforproer(pros);
	 	      						String [] temp = strpro.split("_");
	 	      						String pri = temp[0];
	 	      						float totalPrice = 0;//获得价格
	 	      						if(StringUtil.isEmpty(pri)){
	 	      							totalPrice = Float.valueOf(pri);
	 	      							sentity.setPrice(totalPrice);
	 	      							sentity.setMinprice(totalPrice);
	 	      						}
	 	      						pros = temp[1];
	 	      						if(!StringUtil.isEmpty(pros)){
	 	      							pros = map.get(cateid);
	 	      						}
	 	      						sentity.setPropertid(pros);
	 	      						String cid ="";
	 	      						if(cateid.indexOf("_") > -1){
	 	      							cid = cateid.split("_")[0];
	 	      						}else{
	 	      							cid = cateid;
	 	      						}
	 	      						long lcate = Long.valueOf(cid);
	 	      						ProductCategoryEntity pro = AddOrderRealService.getInstance().getProductCategoryEntityById(lcate);
	 	      						String proname =  pro.getCname();
	 	      						if(proname.equals("其他")){
	 	      							qtprice = AddOrderRealService.getInstance().getQitprice2Json(pros);
	 	      							float qt = Float.valueOf(qtprice);
	 	      							sentity.setMinprice(qt);
	 	      							sentity.setPrice(qt);
	 	      						}
	 	      						
	 	      						AddOrderRealService.getInstance().updateSorderChildrenEntity(sentity);
	 	      						map.remove(cateid);//更新后删除原有的
	 	      					}
	 	      				}else{
	 	      				//删除
	 	 	      				long st = Long.valueOf(orChild);
	 	 	      				SorderChildrenEntity er = AddOrderRealService.getInstance().getSorderChildrenEntityById(st);
	 	 	      				float pric = er.getMinprice();
	 	 	      				totalMoney = totalMoney - pric;
	 	 	      				AddOrderRealService.getInstance().deleteSorderEntity(st);
	 	      				}
	 	      			}
	 	      		}
	 	      	}else{
	 	      		String[] ortm = orderChildinfo.split("_");
	 	      		String orChild = ortm[0];
	 	      		String cateid = "";//+"_0";
	 	      		String mt = selectList.get(0).split("_")[1];
	 	      		if(productId.equals("1104") || productId.equals("2203")){
	 	      			cateid = ortm[1];
	 	      			if(cateid.equals("30017")){
	 	      				cateid = ortm[1]+"_20";
	 	      			}else{ 
	 	      				cateid = ortm[1]+"_"+mt;
	 	      			}
	 	      		}else{
	 	      			cateid =  ortm[1];
	 	      		}
	 	      		if(map != null){
	 	      			if(map.containsKey(cateid)){
	 	      				if(orChild != null && !"".equals(orChild.trim())){
	 	      					long childId = Long.valueOf(orChild);
	 	      					SorderChildrenEntity sentity = AddOrderRealService.getInstance()
	 	      							.getSorderChildrenEntityById(childId);
	 	      					String pros = map.get(cateid);
	 	      					
 	      						String strpro = AddOrderRealService.getInstance().getPriceforproer(pros);
 	      						String [] temp = strpro.split("_");
 	      						String pri = temp[0];
 	      						float totalPrice = 0;//获得价格
 	      						if(StringUtil.isEmpty(pri)){
 	      							totalPrice = Float.valueOf(pri);
 	      							sentity.setPrice(totalPrice);
 	      							sentity.setMinprice(totalPrice);
 	      						}
 	      						pros = temp[1];
 	      						if(!StringUtil.isEmpty(pros)){
 	      							pros = map.get(cateid);
 	      						}
 	      						sentity.setPropertid(pros);
 	      						String cid ="";
 	      						if(cateid.indexOf("_") > -1){
 	      							cid = cateid.split("_")[0];
 	      						}else{
 	      							cid = cateid;
 	      						}
 	      						long lcate = Long.valueOf(cid);
 	      						ProductCategoryEntity pro = AddOrderRealService.getInstance().getProductCategoryEntityById(lcate);
 	      						String proname =  pro.getCname();
 	      						if(proname.equals("其他")){
 	      							qtprice = AddOrderRealService.getInstance().getQitprice2Json(pros);
 	      							float qt = Float.valueOf(qtprice);
 	      							sentity.setMinprice(qt);
 	      							sentity.setPrice(qt);
 	      						}
	 	      					AddOrderRealService.getInstance().updateSorderChildrenEntity(sentity);
	 	      					map.remove(cateid);//更新后删除原有的
	 	      				}
	 	      			}else{
	 	      				//删除
	 	      				long st = Long.valueOf(orChild);
 	 	      				SorderChildrenEntity er = AddOrderRealService.getInstance().getSorderChildrenEntityById(st);
 	 	      				float pric = er.getMinprice();
 	 	      				totalMoney = totalMoney - pric;
 	 	      				AddOrderRealService.getInstance().deleteSorderEntity(st);
	 	      			}
	 	      		}
	 	      	}
	 	      }
	 	 	  //表示新增
	 	      if(map != null && map.size() > 0){
	 	      	SorderChildrenEntity schildren = new SorderChildrenEntity();
	 	      	for(Entry<String,String> entity : map.entrySet()){
	 	      		String cate = (String) entity.getKey();//类别
	 	      		String cateId = cate.split("_")[0];
	 	      		String pros = (String) entity.getValue();
					String strpro = AddOrderRealService.getInstance().getPriceforproer(pros);
					String [] temp = strpro.split("_");
					String pri = temp[0];
					float totalPrice = 0;//获得价格
					if(StringUtil.isEmpty(pri)){
						totalPrice = Float.valueOf(pri);
						schildren.setPrice(totalPrice);
						schildren.setMinprice(totalPrice);
					}
					pros = temp[1];
					if(!StringUtil.isEmpty(pros)){
						pros = (String) entity.getValue();
					}
					schildren.setPropertid(pros);
	 	      		schildren.setCostate(0);
	 	      		schildren.setOrderid(sorder_id);
	 	      		int cateIdint = Integer.parseInt(cateId);
	 	      		schildren.setProdcateid(cateIdint);
	 	      		long lcate = Long.valueOf(cateId);
					ProductCategoryEntity pro = AddOrderRealService.getInstance().getProductCategoryEntityById(lcate);
					String proname =  pro.getCname();
					if(proname.equals("其他")){
						qtprice = AddOrderRealService.getInstance().getQitprice2Json(pros);
						float qt = Float.valueOf(qtprice);
						schildren.setMinprice(qt);
						schildren.setPrice(qt);
					}
	 	      		//保存t_order_children
	 	      		AddOrderRealService.getInstance().saveOrderChildren(schildren);
	 	      	}
	 	      }
	}
	/**
	 * 更新用户
	 */
	private void updateLoginEntity() {
		String userId = beat().getRequest().getParameter("userId");
		String phoneNum = beat().getRequest().getParameter("phoneNum");
		String kehname = beat().getRequest().getParameter("kehname");
		String tongydz = beat().getRequest().getParameter("tongydz");
		String kehchannl = beat().getRequest().getParameter("kehchannl");
		String kehzj = beat().getRequest().getParameter("kehzj");
		String email = beat().getRequest().getParameter("email");
		//String gongsmc = beat().getRequest().getParameter("gongsmc");
		long luserId = Long.valueOf(userId);
		LoginEntity loginEntity = AddOrderRealService.getInstance().getLoginEntityByUserId(luserId);
		if(loginEntity != null){
			loginEntity.setUserphone(phoneNum);
			loginEntity.setUsername(kehname);
			loginEntity.setAddress(tongydz);
			loginEntity.setEmail(email);
			loginEntity.setLandlinenumber(kehzj);
			int channel = Integer.valueOf(kehchannl);
			loginEntity.setChannel(channel);
			//loginEntity.setCompanyname(gongsmc);
			//更新用户对象
			AddOrderRealService.getInstance().updateLoginEntity(loginEntity);
		}
	}
	@Path("/dynamicPrice")
	public ActionResult dynamicPrice(){
		return new ActionResult() {
			@Override
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				//获得商品Id 类别Id 改变的属性
				String price ="0";
				String productId = beat().getRequest().getParameter("productId");
				String localcity = beat().getRequest().getParameter("localcity");
				if(StringUtil.isEmpty(localcity)){
					if(!StringUtil.isNum(localcity)){
						localcity = AddOrderRealService.getInstance().getCityAreaBymc(localcity);
					}
				}
				String perties = beat().getRequest().getParameter("perties");
				String periIds ="";
				if(StringUtil.isEmpty(perties)){
					String [] temp = perties.split(",");
					for (int i = 0; i < temp.length; i++) {
						String stm = temp[i];
						if(!StringUtil.isNum(stm)){
							String pro = AddOrderRealService.getInstance().getPropertyBymc(stm);
							periIds = periIds + pro +",";
						}else{
							periIds = periIds + stm +",";
						}
					}
				}
				
				if(periIds !=""){
					periIds = periIds.substring(0, periIds.lastIndexOf(","));
				}
				String tradePerbig = "";
				String tradePersmall = "";
				String cateId = beat().getRequest().getParameter("cateId");
				float tradePrice =0f;
				if(productId.equals("1104") || productId.equals("2203")){
					String bigsl = beat().getRequest().getParameter("bigsl");
					if(!StringUtil.isEmpty(bigsl)){
						bigsl ="0";
					}
					if(StringUtil.isEmpty(bigsl)){
						ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("bigtradeCount").get(0);
						if (qtc != null) {
							tradePerbig = periIds +","+qtc.getProid();
						}
					}
					String smallsl = beat().getRequest().getParameter("smallsl");
                    if(StringUtil.isEmpty(smallsl)){
                    	ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("smalltradeCount").get(0);
						if (qtc != null) {
							tradePersmall = periIds +","+qtc.getProid();
						}
					}
                    //大数量
                    float bprice = AddOrderRealService.getInstance().getPriceBycondition(productId, cateId, localcity, tradePerbig);
                    if(StringUtil.isEmpty(bigsl)){
                    	int count = Integer.parseInt(bigsl);
                    	float ttp = count * bprice;
                    	tradePrice = tradePrice + ttp ; 
                    	/*if(count >= 4){
                    		tradePrice = tradePrice -200;
                    	}*/
                    }
                    //小数量
                    float smaprice = AddOrderRealService.getInstance().getPriceBycondition(productId, cateId, localcity, tradePersmall);
                    if(StringUtil.isEmpty(smallsl)){
                    	int count = Integer.parseInt(smallsl);
                    	float ttp = count * smaprice;
                    	tradePrice = tradePrice + ttp ; 
                    }
                    price = tradePrice+"";
				}else{
					price = AddOrderRealService.getInstance().getPriceBycondition(productId, cateId, localcity, periIds)+"";
				}
				try {
					beat().getResponse().getWriter().print(price);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	@Path("/dyncprice")
	public  ActionResult getPriceByOlderchildId(){
		return new ActionResult() {
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				
				String orderChildId = beat().getRequest().getParameter("orderChildId");
				String price ="0";
				if(StringUtil.isEmpty(orderChildId)){
					long lid = Long.valueOf(orderChildId);
					SorderChildrenEntity se = AddOrderRealService.getInstance().getSorderChildrenEntityById(lid);
					if(se != null){
						price = se.getMinprice()+"";//成交价
					}
				}
				try {
					beat().getResponse().getWriter().print(price);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
