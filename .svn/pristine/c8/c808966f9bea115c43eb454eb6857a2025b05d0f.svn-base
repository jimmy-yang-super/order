package com.jixiang.argo.lvzheng.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.bj58.sfft.json.orgjson.JSONException;
import com.jixiang.argo.lvzheng.buz.ProcessBuz;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.frame.service.OrderAssembleService;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.vo.SaveOrderVo;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.FollowupEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.PricesEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.ProductEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.newcore.entity.SorderExtEntity;
import com.jx.service.newcore.entity.SorderSuperEntity;

/**
 * 添加订单涉及到的业务处理
 * @author wuyin
 *
 */
public class AddOrderRealService {
	
	private static AddOrderRealService instance = null;
	
	public static AddOrderRealService getInstance(){
		if(instance == null){
			instance = new AddOrderRealService();
		}
		return instance;
	}

	/**
	 * 根据手机号码获得是否存在历史记录
	 */
	public List<SorderEntity> getHistoryOrderByPhone(String phoneNum){
		List<SorderEntity> lis = null;
		if(null != phoneNum && !"".equals(phoneNum.trim())){
			String userId = RealBusinessService.getInstance().getUserIdByphoneNum(phoneNum);
			if(userId != ""){
				//获取该用户的下单记录
				lis =RealBusinessService.getInstance().getSorderEntitys(userId);
			}
		}
		return lis;
	}
	/**
	 * 添加订单判断用户(判断用户是否存在,新添用户)
	 * return 用户ID
	 */
	public String  beforeAddOrderVali(LoginEntity user){
		String userId ="";
		if(user != null){
			String phoneNum = user.getUserphone();
			if(null != phoneNum && !"".equals(phoneNum.trim())){
				userId = RealBusinessService.getInstance().getUserIdByphoneNum(phoneNum);
				if(!StringUtil.isEmpty(userId)){
					user.setAddtime(new Date().getTime());
					userId = RealBusinessService.getInstance().addUserEntity(user);
				}else{
					//表示存在 更新
					long luserId = Long.valueOf(userId);
					LoginEntity em = getLoginEntityByUserId(luserId);
					em.setAddress(user.getAddress());
					em.setEmail(user.getEmail());
					em.setChannel(user.getChannel());
					//em.setCompanyname(user.getCompanyname());
					em.setLandlinenumber(user.getLandlinenumber());
					em.setUserphone(user.getUserphone());
					em.setUsername(user.getUsername());
					RealBusinessService.getInstance().updateLoginEntity(em);
				}
			}
		}
		return userId;
	}
	/**
	 * 获得区域一级目录
	 * @return
	 */
	public List<AreasEntity> getAreasEntity(){
		//获得cityId 集合
		List<AreasEntity>  areaEntitys = null;
		/*List citys = RealBusinessService.getInstance().getCitities();
		if(null != citys && citys.size() >0){
			String condition ="";
			for (int i = 0; i < citys.size(); i++) {
				condition = condition+citys.get(i)+",";
			}
			if(condition != ""){
				condition = condition.substring(0,condition.lastIndexOf(","));
				condition ="areaid in ("+condition+")";
				areaEntitys = AreaCommonService.getInstance().getAraeEntityComm(condition);
			}
		}*/
		areaEntitys = AreaCommonService.getInstance().getAraeEntityComm("parentid = 0");
		return areaEntitys;
	}
	
	public AreasEntity getAreasEntity(String cityId){
		AreasEntity areaEntitys = null;
		if(StringUtil.isEmpty(cityId)){
			long lcity = Integer.parseInt(cityId);
			try {
				areaEntitys = RSBLL.getstance().getAreasService().getAeasEntityById(lcity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return areaEntitys;
	}
	
	public List<AreasEntity> getAreasEntitychange(String cityId){
		//获得cityId 集合
		List<AreasEntity>  areaEntitys = null;
		areaEntitys = AreaCommonService.getInstance().getAraeEntityComm("parentid = "+cityId);
		return areaEntitys;
	}
	
	public List<ProductPropertiesEntity> getBusinessArea(){
		List<ProductPropertiesEntity> businePro = null;
		businePro = AreaCommonService.getInstance().getBusinessArea();
		return businePro;
	}
	
	/**
	 * 获得添加订单中地区信息展示(所有的)
	 * 例如：市|地区(初始页面默认所有商品对应)
	 *   (key:对应城市(集合) value：对应区集合)
	 */
	@SuppressWarnings("rawtypes")
	public Map<AreasEntity,List<ProductPropertiesEntity>> getAreasEntitys(){
		Map<AreasEntity,List<ProductPropertiesEntity>> map = 
				new HashMap<AreasEntity, List<ProductPropertiesEntity>>();
		//获得cityId 集合
		List citys = RealBusinessService.getInstance().getCitities();
		if(null != citys && citys.size() >0){
			String condition ="";
			for (int i = 0; i < citys.size(); i++) {
				condition = condition+citys.get(i)+",";
			}
			if(condition != ""){
				condition = condition.substring(0,condition.lastIndexOf(","));
				condition ="areaid in ("+condition+")";
				map = RealBusinessService.getInstance().getAreasEntityS(condition);
			}
		}
		return map;
	}
	/**
	 * 修改页面信息展示
	 * @return
	 */
	public List<AreasEntity> getAllAreasEntityByparent(){
		List<AreasEntity> le = null;
		//获得cityId 集合
		//List citys = RealBusinessService.getInstance().getCitities();
		List<AreasEntity> citys = this.getAreasEntity();
		if(StringUtil.isListNull(citys)){
			String condition ="";
			for (AreasEntity en : citys) {
				condition = condition+en.getAreaid()+",";
			}
			if(condition != ""){
				condition = condition.substring(0,condition.lastIndexOf(","));
				condition ="parentid in ("+condition+")";
				try {
					le = RSBLL.getstance().getAreasService()
							.getAeasEntity(condition, 1, 50, " status desc ,areaid");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return le;
	}
	
	/**
	 * 初始化页面中商品信息
	 * return map (key: 商品对象 value：商品类别集合)
	 */
	public LinkedHashMap<ProductEntity,List<ProductCategoryEntity>> initAddorderpage(String condition){
		LinkedHashMap<ProductEntity,List<ProductCategoryEntity>> map = null;
		//获得所有商品
		List<ProductEntity> lipe = RealBusinessService.getInstance()
				.getProductEntityList(condition);
		if(null != lipe && lipe.size() >0){
			map = new LinkedHashMap<ProductEntity, List<ProductCategoryEntity>>();
			for(ProductEntity pe : lipe){
				long proid = pe.getPid();
				//获得类别集合
				List<ProductCategoryEntity> llcpe = RealBusinessService.
						getInstance().getProductCategoryEntitysByproId(proid);
				if(null !=llcpe && llcpe.size() >0){
					map.put(pe, llcpe);
				}
			}
		}
		return map;
	}
	
	public List<ProductPropertiesEntity> getAddresslist(String condition){
		List<ProductPropertiesEntity> lep = null;
		try {
			lep = RSBLL.getstance().getProductPropertiesService()
					.getProductPropertiesEntitys("type ='"+condition+"'", 1, 25, "proid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lep;
	}
	/**
	 * 获得注册公司类别
	 */
	public List<ProductPropertiesEntity> getCompanyType(String condition){
		List<ProductPropertiesEntity> lep = null;
		try {
			lep = RSBLL.getstance().getProductPropertiesService()
					.getProductPropertiesEntitys("type ='"+condition+"'", 1, 25, "proid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lep;
	}
	/**
	 *  保存订单
	 * @param lentity 添加用户信息
	 * @param producepropertys 注册区域属性Id集合（格式 []多个','分割）
	 * @param intentcode  意向度
	 * @param product2CategroyIds 选择的商品和下属的服务
	 * //格式 :商品id_类别多个以','分割(注：整体多个以‘|’分割)   例： 1_1|2_18,19,20|3_10|4_5|
	 * 	String producepropertys =  companytype+","+citity+","+citityarea;
		//选择商品信息
		String product2CategroyIds = beat().getRequest().getParameter("selectProduct");
	 */
	public void saveOrder(String userId,LoginEntity lentity, String producepropertys,
			String product2CategroyIds,String beiz ,int intentcode,long login,int citityint,String gongsmc,long busId,String citityarea){
		//手机用户的验证（获得用户Id）
		//String userId = beforeAddOrderVali(lentity);
		SorderSuperEntity superorder = new SorderSuperEntity();
		float totalmoney =0l;//订单金额（根据选择条件组合几个。或是从页面上提交上）
		long user_id = Long.valueOf(userId);
		long nowtime = new Date().getTime();
		superorder.setUser_id(user_id);
		superorder.setPhone(lentity.getUserphone());
		superorder.setDescription(beiz);
		superorder.setOrderstate(7);
		superorder.setPaystate(0);
		superorder.setPostime(nowtime);
		superorder.setUpdatetime(nowtime);
		superorder.setTotalmoney(totalmoney);
		//保存t_sorder_super
		Long superId = RealBusinessService.getInstance().saveSorderSuperEntity(superorder);
		String cateids = "";
		if(null !=product2CategroyIds && !"".equals(product2CategroyIds)){
			//格式 :商品id_类别多个以','分割(注：整体多个以‘|’分割)   例： 1_1|2_18,19,20|3_10|4_5|
			if(product2CategroyIds.indexOf(";") >0){
				//表示选择多个服务
				product2CategroyIds = product2CategroyIds
						.substring(0,product2CategroyIds.lastIndexOf(";"));
			}
			String[] tempre = product2CategroyIds.split(";");
			SorderEntity sorderEntity = null;
			FollowupEntity followupEntity = null;
			String priceproperty = this.getProperties4json(producepropertys,"");
			String lolcity ="";
			for(int i =0;i<tempre.length;i++){
				String rro2cate = tempre[i];
				if(null != rro2cate && !"".equals(rro2cate.trim())){
					String [] temp = rro2cate.split("_");
					String productId =temp[0];
					if("2202".equals(productId) || "1105".equals(productId)){
						priceproperty = this.getProperties4json(producepropertys,productId);
					}
					//保存子订单
					sorderEntity = new SorderEntity();
					sorderEntity.setSuperid(superId);
					sorderEntity.setUserid(user_id);
				    sorderEntity.setKefuid(login);
				    sorderEntity.setDescription(beiz);

				    sorderEntity.setOrderstate(7);
				    sorderEntity.setServerstate(0);
				    sorderEntity.setPaystate(0);
				    //sorderEntity.setCurrentempid(login);//当前负责人默认客服
				    sorderEntity.setCompanyname(gongsmc);
				    sorderEntity.setOrderform(3);
				    sorderEntity.setPostime(nowtime);
				    sorderEntity.setUpdatetime(nowtime);
				    sorderEntity.setOrdercity(citityint);
					if(null !=productId && !"".equals(productId.trim())){
						long product_id = Long.valueOf(productId);
						sorderEntity.setProductid(product_id);
					}
					
					 //订单金额 （对应选择商品和下属服务的价格）
				    float orderPrice = 0f;
					sorderEntity.setTotalmoney(orderPrice);
					sorderEntity.setPaymoneycount(orderPrice);
					sorderEntity.setBusid(busId);
					
					sorderEntity.setIntentcode(intentcode);  //设置母单的意向度 duxf 2015-07-07
					//保存订单
					Long sorderId = RealBusinessService.getInstance().saveSorederEntity(sorderEntity);
					String categoryIds = temp[1];
					cateids = cateids+categoryIds+",";
					//当类别多个处理
					String childIIds ="";
					SorderChildrenEntity sorderChildrenEntity =null;
					if(null !=categoryIds && !"".equals(categoryIds.trim())){
						if(categoryIds.indexOf(",")>0){
							String[] tempcate = categoryIds.split(",");
							String dlproducepropertys ="";
							for (int j = 0; j < tempcate.length; j++) {
								sorderChildrenEntity = new SorderChildrenEntity();
								String categroyId = tempcate[j];
								lolcity =getLocalcity(producepropertys,categroyId);
								//保存t_sorder_children
								if(StringUtil.isEmpty(categroyId)){
									int lcateid = Integer.parseInt(categroyId);
									sorderChildrenEntity.setProdcateid(lcateid);
								}
								sorderChildrenEntity.setCostate(0);
								float price = 0;
								//增加 公司变更价格计算    adder  wy  timer 20150813   begin
								if("1102".equals(productId)){//公司变更－－－add by liuft
									if("2009".equals(categroyId)|| "2010".equals(categroyId)){
										//增加默认输入
										String type ="";
										if("2009".equals(categroyId)){
											priceproperty ="66";
											type ="bgqyxs";
										}else{
											priceproperty ="68";
											type ="bgqyxs";
										}
										price = getPriceBycondition(productId,categroyId,citityarea,priceproperty);
										if(price == 0){
											price = getPriceBycondition(productId,categroyId,citityint+"",priceproperty);
										}
										dlproducepropertys = this.backdefalustJson(producepropertys,priceproperty,type);
										sorderChildrenEntity.setPropertid(dlproducepropertys);
									}else{
										//根据城市获得价格
										price = getPriceBycondition(productId,categroyId,citityarea,priceproperty);
										if(price == 0){
											price = getPriceBycondition(productId,categroyId,citityint+"",priceproperty);
										}
										sorderChildrenEntity.setPropertid(producepropertys);
									}
								}else{
									if(categroyId.equals("4001")|| categroyId.equals("4002")){
										//代理记账新增默认12个月记账
										dlproducepropertys = this.backdefalustJson(producepropertys,"","");
										if(StringUtil.isEmpty(priceproperty)){
											if("1105".equals(productId)){
												lolcity = "1";
											}else{
												lolcity = priceproperty;
											}
											priceproperty = "30,33";
										}
										sorderChildrenEntity.setPropertid(dlproducepropertys);
									}else{
										if(categroyId.equals("1002")){
											//如果是代理地址默认选镕辉佳特
											JSONArray ja = JSONArray.fromObject(producepropertys);
											JSONObject jo = new JSONObject();
											jo.put("propertyid","73");
											jo.put("propertyval","addresstype");
											ja.add(jo);
											sorderChildrenEntity.setPropertid(ja.toString());
										}else{
											sorderChildrenEntity.setPropertid(producepropertys);
										}
										//sorderChildrenEntity.setPropertid(producepropertys);
									}
									if(categroyId.equals("1003")){
										//深圳公司注册
										priceproperty = this.getGssx4json(producepropertys);
										if(priceproperty.equals("44")){
											lolcity = citityarea;
											priceproperty = priceproperty+",30";
										}
									}
									//根据选择信息定义价格
									price = getPriceBycondition(productId,categroyId,lolcity,priceproperty);
								}
								
								// end
								orderPrice = orderPrice+price;
								sorderChildrenEntity.setPrice(price);
								sorderChildrenEntity.setMinprice(price);
								sorderChildrenEntity.setOrderid(sorderId);
								Long lchildId = RealBusinessService.getInstance().
										saveSorderhildEntity(sorderChildrenEntity);
								childIIds =childIIds +lchildId+",";
							}
							System.out.println("cateids is "+cateids);
							//同时购买代理地址，国税报道，地税报到优惠400
							if(cateids.indexOf("1002,") >= 0 && cateids.indexOf("4004,") >= 0 && cateids.indexOf("10014,") >= 0){
								orderPrice = orderPrice - 400;
							}
							//公司变更大于等于4项按最高收取并加300块钱
							if("1102".equals(productId) && tempcate.length >= 4){
								orderPrice = orderPrice + 300;		
							}
						}else{
							//表示一个
							sorderChildrenEntity = new SorderChildrenEntity();
							lolcity =getLocalcity(producepropertys,categoryIds);
							int lcateid = Integer.parseInt(categoryIds);
							sorderChildrenEntity.setProdcateid(lcateid);
							sorderChildrenEntity.setCostate(0);
							float price = 0;
							//增加 公司变更价格计算    adder  wy  timer 20150813   begin
							if("1102".equals(productId)){
								if("2009".equals(categoryIds)|| "2010".equals(categoryIds)){
									//增加默认输入
									String type ="";
									if("2009".equals(categoryIds)){
										priceproperty ="66";
										type ="bgqyxs";
									}else{
										priceproperty ="68";
										type ="bgqyxs";
									}
									String dlproducepropertys = this.backdefalustJson(producepropertys,priceproperty,type);
									sorderChildrenEntity.setPropertid(dlproducepropertys);
								}else{
									//根据城市获得价格
									price = getPriceBycondition(productId,categoryIds,citityarea,priceproperty);
									if(price == 0){
										price = getPriceBycondition(productId,categoryIds,citityint+"",priceproperty);
									}
									sorderChildrenEntity.setPropertid(producepropertys);
								}
							}else{
								if(categoryIds.equals("4001")|| categoryIds.equals("4002")){
									//代理记账新增默认12个月记账
									producepropertys = this.backdefalustJson(producepropertys,"","");
									if("1105".equals(productId)){
										lolcity = "1";
									}else{
										lolcity = priceproperty;
									}
									priceproperty = "30,33";
								}
								if(categoryIds.equals("1002")){
									//如果是代理地址默认选镕辉佳特
									JSONArray ja = JSONArray.fromObject(producepropertys);
									JSONObject jo = new JSONObject();
									jo.put("propertyid","73");
									jo.put("propertyval","addresstype");
									ja.add(jo);
									sorderChildrenEntity.setPropertid(ja.toString());
								}else{
									sorderChildrenEntity.setPropertid(producepropertys);
								}
//								//如果是代理地址默认选镕辉佳特
//								JSONArray ja = JSONArray.fromObject(producepropertys);
//								JSONObject jo = new JSONObject();
//								jo.put("propertyid","73");
//								jo.put("propertyval","addresstype");
//								ja.add(jo);
//								sorderChildrenEntity.setPropertid(ja.toString());
								//根据选择信息定义价格
								price = getPriceBycondition(productId,categoryIds,lolcity,priceproperty);
							}
							orderPrice = orderPrice+price;
							sorderChildrenEntity.setPrice(price);
							sorderChildrenEntity.setMinprice(price);
							sorderChildrenEntity.setOrderid(sorderId);
							Long lchildId = RealBusinessService.getInstance().
									saveSorderhildEntity(sorderChildrenEntity);
							childIIds = lchildId+"";
						}
					}
					
					//更新价格
					SorderEntity sorder = this.getSorderEntityById(sorderId);
					if("1102".equals(productId)){
						//公司变更重新计算价格
						sorder.setTotalmoney(orderPrice);
						//计算实付公司
						float ft = initChangeprice(sorderId);
						
						sorder.setPaymoneycount(ft);
					}else{
						sorder.setTotalmoney(orderPrice);
						sorder.setPaymoneycount(orderPrice);
					}
					updateSorderEntity(sorder);
					//发送信息
					try {
						ProcessBuz.pb.addNeworder(sorder, intentcode, login);
					} catch (Exception e) {
						e.printStackTrace();
					}
					totalmoney = totalmoney + orderPrice;
					//添加t_followup
					followupEntity = new FollowupEntity();
					followupEntity.setOrderid(sorderId);
					followupEntity.setAddtime(nowtime);
					followupEntity.setIntentcode(intentcode);
					followupEntity.setContent(beiz);
					followupEntity.setEmpid(login);
					RealBusinessService.getInstance().saveFollowupEntity(followupEntity);
				}
			}
		}
		//更新t_sorder_super
		SorderSuperEntity sscuper = getSorderSuperEntityById(superId);
		sscuper.setTotalmoney(totalmoney);
		updateSorderSuperEntity(sscuper);
		//增加公司注册 代理地址 国税 地税报道 同时购买优惠400
		UpdateGszcyhje(superId);
	}
	
	public void saveOrder(SaveOrderVo saveOrderVo){
		
		String userId = saveOrderVo.getUserId();
		LoginEntity lentity = saveOrderVo.getLentity(); 
		String producepropertys = saveOrderVo.getProducepropertys();
		String product2CategroyIds = saveOrderVo.getProduct2CategroyIds();
		String beiz = saveOrderVo.getBeiz();
		int intentcode = saveOrderVo.getIntentcode();
		long login = saveOrderVo.getLogin();
		int citityint = saveOrderVo.getCitityint();
		String gongsmc = saveOrderVo.getGongsmc();
		long busId = saveOrderVo.getBusId();
		String citityarea = saveOrderVo.getCitityarea();
		String loginroleids = saveOrderVo.getLoginroleids();
		
		String channel = saveOrderVo.getChannel();
		String terraceOrderId = saveOrderVo.getTerraceOrderId();
		
		//手机用户的验证（获得用户Id）
		//String userId = beforeAddOrderVali(lentity);
		SorderSuperEntity superorder = new SorderSuperEntity();
		float totalmoney =0l;//订单金额（根据选择条件组合几个。或是从页面上提交上）
		long user_id = Long.valueOf(userId);
		long nowtime = new Date().getTime();
		superorder.setUser_id(user_id);
		superorder.setPhone(lentity.getUserphone());
		superorder.setDescription(beiz);
		superorder.setOrderstate(7);
		superorder.setPaystate(0);
		superorder.setPostime(nowtime);
		superorder.setUpdatetime(nowtime);
		superorder.setTotalmoney(totalmoney);
		//保存t_sorder_super
		Long superId = RealBusinessService.getInstance().saveSorderSuperEntity(superorder);
		String cateids = "";
		if(null !=product2CategroyIds && !"".equals(product2CategroyIds)){
			//格式 :商品id_类别多个以','分割(注：整体多个以‘|’分割)   例： 1_1|2_18,19,20|3_10|4_5|
			if(product2CategroyIds.indexOf(";") >0){
				//表示选择多个服务
				product2CategroyIds = product2CategroyIds
						.substring(0,product2CategroyIds.lastIndexOf(";"));
			}
			String[] tempre = product2CategroyIds.split(";");
			SorderEntity sorderEntity = null;
			FollowupEntity followupEntity = null;
			String priceproperty = this.getProperties4json(producepropertys,"");
			String lolcity ="";
			for(int i =0;i<tempre.length;i++){
				String rro2cate = tempre[i];
				if(null != rro2cate && !"".equals(rro2cate.trim())){
					String [] temp = rro2cate.split("_");
					String productId =temp[0];
					if("2202".equals(productId) || "1105".equals(productId)){
						priceproperty = this.getProperties4json(producepropertys,productId);
					}
					//保存子订单
					sorderEntity = new SorderEntity();
					sorderEntity.setSuperid(superId);
					sorderEntity.setUserid(user_id);
					
					if(StringUtils.isNotEmpty(loginroleids)){
						String[] roleid = loginroleids.split(",");
						boolean flag = false;
						for(int j=0;j<roleid.length;j++){
							if(roleid[j].equals("1")){
								flag = true;
							}
						}
						if(flag){
							sorderEntity.setKefuid(login);
						}else{
							sorderEntity.setKefuid(login);
						}
					}
				    sorderEntity.setDescription(beiz);

				    sorderEntity.setOrderstate(7);
				    sorderEntity.setServerstate(0);
				    sorderEntity.setPaystate(0);
				    //sorderEntity.setCurrentempid(login);//当前负责人默认客服
				    sorderEntity.setCompanyname(gongsmc);
				    if(channel.equals("25")){
				    	sorderEntity.setOrderform(6);
					}else{
						sorderEntity.setOrderform(3);
					}
				    sorderEntity.setPostime(nowtime);
				    sorderEntity.setUpdatetime(nowtime);
				    sorderEntity.setOrdercity(citityint);
					if(null !=productId && !"".equals(productId.trim())){
						long product_id = Long.valueOf(productId);
						sorderEntity.setProductid(product_id);
					}
					
					 //订单金额 （对应选择商品和下属服务的价格）
				    float orderPrice = 0f;
					sorderEntity.setTotalmoney(orderPrice);
					sorderEntity.setPaymoneycount(orderPrice);
					sorderEntity.setBusid(busId);
					if(channel.equals("25")){
						sorderEntity.setPaystate(3);
					}
					sorderEntity.setIntentcode(intentcode);  //设置母单的意向度 duxf 2015-07-07
					//保存订单
					Long sorderId = RealBusinessService.getInstance().saveSorederEntity(sorderEntity);
					
					//保存orderExt
					if(channel.equals("25")){
						if(StringUtils.isNotBlank(channel)){
							SorderExtEntity sorderExtEntity = new SorderExtEntity();
							sorderExtEntity.setOrderId(sorderId);
							sorderExtEntity.setDataKey("channel");
							sorderExtEntity.setDataValue(channel);
							try {
								OrderAssembleService.getInstance().addOrderExt(sorderExtEntity);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(StringUtils.isNotBlank(terraceOrderId)){
							SorderExtEntity sorderExtEntity = new SorderExtEntity();
							sorderExtEntity.setOrderId(sorderId);
							sorderExtEntity.setDataKey("terraceOrderId");
							sorderExtEntity.setDataValue(terraceOrderId);
							try {
								OrderAssembleService.getInstance().addOrderExt(sorderExtEntity);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					String categoryIds = temp[1];
					cateids = cateids+categoryIds+",";
					//当类别多个处理
					String childIIds ="";
					SorderChildrenEntity sorderChildrenEntity =null;
					if(null !=categoryIds && !"".equals(categoryIds.trim())){
						if(categoryIds.indexOf(",")>0){
							String[] tempcate = categoryIds.split(",");
							String dlproducepropertys ="";
							for (int j = 0; j < tempcate.length; j++) {
								sorderChildrenEntity = new SorderChildrenEntity();
								String categroyId = tempcate[j];
								lolcity =getLocalcity(producepropertys,categroyId);
								//保存t_sorder_children
								if(StringUtil.isEmpty(categroyId)){
									int lcateid = Integer.parseInt(categroyId);
									sorderChildrenEntity.setProdcateid(lcateid);
								}
								sorderChildrenEntity.setCostate(0);
								float price = 0;
								//增加 公司变更价格计算    adder  wy  timer 20150813   begin
								if("1102".equals(productId)){//公司变更－－－add by liuft
									if("2009".equals(categroyId)|| "2010".equals(categroyId)){
										//增加默认输入
										String type ="";
										if("2009".equals(categroyId)){
											priceproperty ="66";
											type ="bgqyxs";
										}else{
											priceproperty ="68";
											type ="bgqyxs";
										}
										price = getPriceBycondition(productId,categroyId,citityarea,priceproperty);
										if(price == 0){
											price = getPriceBycondition(productId,categroyId,citityint+"",priceproperty);
										}
										dlproducepropertys = this.backdefalustJson(producepropertys,priceproperty,type);
										sorderChildrenEntity.setPropertid(dlproducepropertys);
									}else{
										//根据城市获得价格
										price = getPriceBycondition(productId,categroyId,citityarea,priceproperty);
										if(price == 0){
											price = getPriceBycondition(productId,categroyId,citityint+"",priceproperty);
										}
										sorderChildrenEntity.setPropertid(producepropertys);
									}
								}else{
									if(categroyId.equals("4001")|| categroyId.equals("4002")){
										//代理记账新增默认12个月记账
										dlproducepropertys = this.backdefalustJson(producepropertys,"","");
										if(StringUtil.isEmpty(priceproperty)){
											if("1105".equals(productId)){
												lolcity = "1";
											}else{
												lolcity = priceproperty;
											}
											priceproperty = "30,33";
										}
										sorderChildrenEntity.setPropertid(dlproducepropertys);
									}else{
										if(categroyId.equals("1002")){
											//如果是代理地址默认选镕辉佳特
											JSONArray ja = JSONArray.fromObject(producepropertys);
											JSONObject jo = new JSONObject();
											jo.put("propertyid","73");
											jo.put("propertyval","addresstype");
											ja.add(jo);
											sorderChildrenEntity.setPropertid(ja.toString());
										}else{
											sorderChildrenEntity.setPropertid(producepropertys);
										}
										//sorderChildrenEntity.setPropertid(producepropertys);
									}
									if(categroyId.equals("1003")){
										//深圳公司注册
										priceproperty = this.getGssx4json(producepropertys);
										if(priceproperty.equals("44")){
											lolcity = citityarea;
											priceproperty = priceproperty+",30";
										}
									}
									//根据选择信息定义价格
									price = getPriceBycondition(productId,categroyId,lolcity,priceproperty);
								}
								
								// end
								orderPrice = orderPrice+price;
								sorderChildrenEntity.setPrice(price);
								sorderChildrenEntity.setMinprice(price);
								sorderChildrenEntity.setOrderid(sorderId);
								Long lchildId = RealBusinessService.getInstance().
										saveSorderhildEntity(sorderChildrenEntity);
								childIIds =childIIds +lchildId+",";
							}
							System.out.println("cateids is "+cateids);
							//同时购买代理地址，国税报道，地税报到优惠400
//							if(cateids.indexOf("1002,") >= 0 && cateids.indexOf("4004,") >= 0 && cateids.indexOf("10014,") >= 0){
//								orderPrice = orderPrice - 400;
//							}
							//公司变更大于等于4项按最高收取并加300块钱
							if("1102".equals(productId) && tempcate.length >= 4){
								orderPrice = orderPrice + 300;		
							}
						}else{
							//表示一个
							sorderChildrenEntity = new SorderChildrenEntity();
							lolcity =getLocalcity(producepropertys,categoryIds);
							int lcateid = Integer.parseInt(categoryIds);
							sorderChildrenEntity.setProdcateid(lcateid);
							sorderChildrenEntity.setCostate(0);
							float price = 0;
							//增加 公司变更价格计算    adder  wy  timer 20150813   begin
							if("1102".equals(productId)){
								if("2009".equals(categoryIds)|| "2010".equals(categoryIds)){
									//增加默认输入
									String type ="";
									if("2009".equals(categoryIds)){
										priceproperty ="66";
										type ="bgqyxs";
									}else{
										priceproperty ="68";
										type ="bgqyxs";
									}
									String dlproducepropertys = this.backdefalustJson(producepropertys,priceproperty,type);
									sorderChildrenEntity.setPropertid(dlproducepropertys);
								}else{
									//根据城市获得价格
									price = getPriceBycondition(productId,categoryIds,citityarea,priceproperty);
									if(price == 0){
										price = getPriceBycondition(productId,categoryIds,citityint+"",priceproperty);
									}
									sorderChildrenEntity.setPropertid(producepropertys);
								}
							}else{
								if(categoryIds.equals("4001")|| categoryIds.equals("4002")){
									//代理记账新增默认12个月记账
									producepropertys = this.backdefalustJson(producepropertys,"","");
									if("1105".equals(productId)){
										lolcity = "1";
									}else{
										lolcity = priceproperty;
									}
									priceproperty = "30,33";
								}
								if(categoryIds.equals("1002")){
									//如果是代理地址默认选镕辉佳特
									JSONArray ja = JSONArray.fromObject(producepropertys);
									JSONObject jo = new JSONObject();
									jo.put("propertyid","73");
									jo.put("propertyval","addresstype");
									ja.add(jo);
									sorderChildrenEntity.setPropertid(ja.toString());
								}else{
									sorderChildrenEntity.setPropertid(producepropertys);
								}
//								//如果是代理地址默认选镕辉佳特
//								JSONArray ja = JSONArray.fromObject(producepropertys);
//								JSONObject jo = new JSONObject();
//								jo.put("propertyid","73");
//								jo.put("propertyval","addresstype");
//								ja.add(jo);
//								sorderChildrenEntity.setPropertid(ja.toString());
								//根据选择信息定义价格
								price = getPriceBycondition(productId,categoryIds,lolcity,priceproperty);
							}
							orderPrice = orderPrice+price;
							sorderChildrenEntity.setPrice(price);
							sorderChildrenEntity.setMinprice(price);
							sorderChildrenEntity.setOrderid(sorderId);
							Long lchildId = RealBusinessService.getInstance().
									saveSorderhildEntity(sorderChildrenEntity);
							childIIds = lchildId+"";
						}
					}
					
					//更新价格
					SorderEntity sorder = this.getSorderEntityById(sorderId);
					if("1102".equals(productId)){
						//公司变更重新计算价格
						sorder.setTotalmoney(orderPrice);
						//计算实付公司
						float ft = initChangeprice(sorderId);
						
						sorder.setPaymoneycount(ft);
					}else{
						sorder.setTotalmoney(orderPrice);
						sorder.setPaymoneycount(orderPrice);
					}
					updateSorderEntity(sorder);
					//发送信息
					try {
						ProcessBuz.pb.addNeworder(sorder, intentcode, login);
					} catch (Exception e) {
						e.printStackTrace();
					}
					totalmoney = totalmoney + orderPrice;
					//添加t_followup
					followupEntity = new FollowupEntity();
					followupEntity.setOrderid(sorderId);
					followupEntity.setAddtime(nowtime);
					followupEntity.setIntentcode(intentcode);
					followupEntity.setContent(beiz);
					followupEntity.setEmpid(login);
					RealBusinessService.getInstance().saveFollowupEntity(followupEntity);
				}
			}
		}
		//更新t_sorder_super
		SorderSuperEntity sscuper = getSorderSuperEntityById(superId);
		sscuper.setTotalmoney(totalmoney);
		updateSorderSuperEntity(sscuper);
		//增加公司注册 代理地址 国税 地税报道 同时购买优惠400
		
	}
	
	
	/**
	 * 同时购买公司注册 代理地址 国税 地税报道 优惠400
	 * @param superId
	 */
	public void UpdateGszcyhje(long superId){
		try {
			List<SorderEntity> lsorders = RSBLL.getstance().getSorderService()
					.getSorderEntityListBypage("superid = "+superId+" and  productid = 1101", 1, 1, "orderid");
			if(StringUtil.isListNull(lsorders)){
				SorderEntity se = lsorders.get(0);
				if(se != null){
					long sid = se.getOrderid();
					List<SorderChildrenEntity> schildren = this.getSorderChildBysorder(sid);
					int count = 0;//记录公司注册 代理地址 国税 地税报道 是否同时购买
					int cateid =0;
					for(SorderChildrenEntity sc : schildren){
						cateid = sc.getProdcateid();
						if(cateid == 1001 || cateid == 1002 || cateid == 1011|| cateid == 1012){
							count ++;
						}
					}
					//表示公司注册 代理地址 国税 地税报道 同时购买
					if(count == 4){
						float newprice = se.getPaymoneycount()-400;
						se.setPaymoneycount(newprice);
						this.updateSorderEntity(se);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重新初始化变更业务
	 * @param sorderid
	 */
	public float initChangeprice(long sorderid){
		float sumt = 0;
		try {
			List<SorderChildrenEntity> lscc = RSBLL.getstance().getSorderChildrenService()
					.getSorderChildrenEntityListBypage("orderid ="+sorderid, 1, 50, "coid");
			if(StringUtil.isListNull(lscc)){
				float temp =0;
				float other =0;
				float yuacol =0;
				for(SorderChildrenEntity ss : lscc){
					long caId = ss.getProdcateid();
					yuacol = ss.getMinprice();
					if(caId == 2013){
						//税务核定
						other = other + yuacol;
					}else{
						if(temp < yuacol){
							temp = yuacol;  
						}
					}
				}
				sumt = other + temp;
			}
			if(lscc != null && lscc.size() >= 4){
				sumt = sumt + 300;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sumt;
	}
	/**
	 * 更新SorderSuperEntity
	 * @param supersorder
	 */
	public void updateSorderSuperEntity(SorderSuperEntity supersorder){
		try {
			RSBLL.getstance().getSorderSuperService().updateSorderSuperEntity(supersorder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获得SorderSuperEntity对象通过Id
	 * @param superid
	 * @return
	 */
	public SorderSuperEntity getSorderSuperEntityById(long superid){
		SorderSuperEntity supers = null;
		try {
			supers = RSBLL.getstance().getSorderSuperService().getSorderSuperEntityByid(superid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supers;
	}
	/**
	 * 显示修改页面信息
	 */
	//客户信息
	public LoginEntity getLoginEntityBysorder(long sorderId){
		LoginEntity logentity = null;
		try {
			SorderEntity sorderen = RSBLL.getstance().
					getSorderService().getSorderEntityByid(sorderId);
			if(null != sorderen){
				long user_Id = sorderen.getUserid();
				logentity = RSBLL.getstance().getLoginService()
						.getLoginEntityById(user_Id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logentity;
	}
	
	//显示订单信息
	public Map<ProductEntity,List<ProductCategoryEntity>> modifySorderDisplay(long sorderid){
		Map<ProductEntity,List<ProductCategoryEntity>> map = null;
		List<ProductCategoryEntity> lpce =null;
		try {
			SorderEntity sorder = RSBLL.getstance().getSorderService().getSorderEntityByid(sorderid);
			if(sorder != null){
				map = new HashMap<ProductEntity, List<ProductCategoryEntity>>();
				long productId = sorder.getProductid();
				ProductEntity proEntity = RSBLL.getstance().getProductNewService().getProductEntityById(productId);
				//获取子订单中的categroy
				List<SorderChildrenEntity> secoilds = RSBLL.getstance().getSorderChildrenService()
						.getSorderChildrenEntityListBypage("orderid ="+sorderid, 1, 10, "coid");
				if(secoilds != null && secoilds.size() >0){
					lpce = new ArrayList<ProductCategoryEntity>();
					ProductCategoryEntity pcee = null;
					for(SorderChildrenEntity sce : secoilds){
						int prodcateid = sce.getProdcateid();
						pcee = RSBLL.getstance().getProductCategoryService()
								.getProductCategoryEntityById(prodcateid);
						lpce.add(pcee);
					}
				}
				map.put(proEntity, lpce);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public Map<ProductEntity,List<ProductCategoryEntity>> changeCityinfo(String priductId){
		long lpid = Long.valueOf(priductId);
		Map<ProductEntity,List<ProductCategoryEntity>> map = null;
		try {
			ProductEntity productEntity = RSBLL.getstance().getProductNewService().getProductEntityById(lpid);
			List<ProductCategoryEntity> lpces = RSBLL.getstance().getProductCategoryService()
					.getProductCategoryEntitys("productid = "+lpid, 1, 25, "cid");
			if(productEntity != null){
				map = new HashMap<ProductEntity, List<ProductCategoryEntity>>();
				map.put(productEntity, lpces);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public List<ProductCategoryEntity> changeCityinfoList(String priductId){
		long lpid = Long.valueOf(priductId);
		List<ProductCategoryEntity> lpces = null;
		try {
			lpces = RSBLL.getstance().getProductCategoryService()
					.getProductCategoryEntitys("productid = "+lpid, 1, 25, "cid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lpces;
	}
	
	//获得订单信息
	public SorderEntity getSorderEntityById(long orderId){
		SorderEntity sorderEntity = null;
		try {
			sorderEntity = RSBLL.getstance()
					.getSorderService().getSorderEntityByid(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sorderEntity;
	}
	
	//获得最近一次的意向度
	public int getIntercode(long orderId){
		int intecode = 1;
		try {
			List<FollowupEntity> follow = RSBLL.getstance().getFollowupService()
			.getFollowupEntity("orderid = "+orderId, 1, 5, "addtime desc");
			if(follow != null && follow.size() > 0){
				intecode = follow.get(0).getIntentcode();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return intecode;
	}
	/**
	 * 获得最近一次对象
	 * @param orderId
	 * @return
	 */
	public FollowupEntity getFollowIntercode(long orderId){
		FollowupEntity intecode = null;
		try {
			List<FollowupEntity> follow = RSBLL.getstance().getFollowupService()
			.getFollowupEntity("orderid = "+orderId, 1, 5, "addtime desc");
			if(follow != null && follow.size() > 0){
				intecode = follow.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return intecode;
	}
	
	//更新订单信息
	/**
	 * 更新t_sorder_children
	 * @param child
	 */
	public void updateSorderChildrenEntity(SorderChildrenEntity child){
		try {
			RSBLL.getstance().getSorderChildrenService().updateSorderChildrenEntity(child);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 更新用户信息
	 * @param loginentity
	 */
	public void updateLoginEntity(LoginEntity loginentity){
		//更新客户
		try {
			RSBLL.getstance().getLoginService().updateLoginEntity(loginentity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新Sordert
	 * @param sorder
	 */
	public void updateSorderEntity(SorderEntity sorder){
		try {
			RSBLL.getstance().getSorderService().updateSorderEntity(sorder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新followupEntity
	 * @param follow
	 */
	public void updateFollowEntity(FollowupEntity follow){
		try {
			RSBLL.getstance().getFollowupService().updateFollowupEntity(follow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getHistoryStr(List<SorderEntity> sorders){
		String text ="";
		ProductEntity proEntity = null;
		EmployersEntity employ = null;
		for(SorderEntity sr : sorders){
			Long orderId = sr.getOrderid();//订单ID
			String xiadsj = com.jixiang.argo.lvzheng.utils.UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss",sr.getPostime());
		    long proId = sr.getProductid();
		    if(proId > 0){
		    	try {
					proEntity = RSBLL.getstance().getProductNewService().getProductEntityById(proId);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }else{
		    	break;
		    }
			
			//商品类别
			String categroyStr = categroyStr(this.getProductCategoryBysorder(orderId));
			String proName = proEntity.getPname();//商品名称
			float total = sr.getTotalmoney();//订单金额
			String empname ="";
			long emp = sr.getEmpid();
			employ = this.getEmployEntity(emp);
			if(employ != null){
				empname = employ.getRealname();
			}
			String orderstatus = (String) KVMap.orderstatemap.get(sr.getOrderstate()+"");//订单状态
			text = text + orderId+"|"+xiadsj+"|"+categroyStr+"|"+proName+"|"+total+"|"+empname+"|"+orderstatus+";";
		}
		return text;
	}
	
	public String categroyStr(List<ProductCategoryEntity> category){
		String text ="";
		if(category != null && category.size() >0){
			for(ProductCategoryEntity  pe : category){
				if(pe != null && StringUtils.isNotBlank(pe.getCname())){
					text = text + pe.getCname()+"</br>";
				}
			}
		}
		return text;
	}
	/**
	 * 获得子订单中的商品类别
	 * @param sorderId
	 * @return
	 */
	public List<ProductCategoryEntity> getProductCategoryBysorder(long sorderId){
		List<ProductCategoryEntity> lpces = null;
		try {
			//获取子订单中的categroy
			List<SorderChildrenEntity> secoilds = RSBLL.getstance().getSorderChildrenService()
					.getSorderChildrenEntityListBypage("orderid ="+sorderId, 1, 10, "prodcateid");
			if(secoilds != null && secoilds.size() >0){
				lpces = new ArrayList<ProductCategoryEntity>();
				ProductCategoryEntity pcee = null;
				for(SorderChildrenEntity sce : secoilds){
					int prodcateid = sce.getProdcateid();
					pcee = RSBLL.getstance().getProductCategoryService()
							.getProductCategoryEntityById(prodcateid);
					if(pcee != null){
						lpces.add(pcee);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lpces;
	}
	
	 /**
	  * 获得业务顾问
	  * @param empid
	  * @return
	  */
	 public EmployersEntity getEmployEntity(long empid){
		 EmployersEntity employersEntity = null;
		 try {
			employersEntity = RSBLL.getstance().getEmployersService().getEmployersEntityById(empid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return employersEntity;
	 }
	 /**
	  * 根据用户ID获得用户对象
	  * @param userId
	  * @return
	  */
	 public LoginEntity getLoginEntityByUserId(long userId){
		 LoginEntity loginEntity = null;
		 try {
			loginEntity = RSBLL.getstance().getLoginService().getLoginEntityById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return loginEntity;
	 }
	 public List<SorderChildrenEntity> getSorderChildBysorder(long sorderId)throws Exception{
		 return RSBLL.getstance().getSorderChildrenService()
					.getSorderChildrenEntityListBypage("orderid ="+sorderId, 1, 10, "coid");
	 }
	 /**
	  * 获得母单子单中的类别
	  * 格式：子单Id_类别Id_属性Id 多个使用“;”
	  * @param sorderId
	  */
	 public String getSorderChildBySorderId(long sorderId){
		//获取子订单中的categroy
		 String  text ="";
			try {
				List<SorderChildrenEntity> secoilds = RSBLL.getstance().getSorderChildrenService()
						.getSorderChildrenEntityListBypage("orderid ="+sorderId, 1, 99, "coid");
				if(secoilds != null && secoilds.size() >0){
					for(SorderChildrenEntity sce : secoilds){
						long orderchildId = sce.getCoid();//子订单Id
						int prodcateid = sce.getProdcateid();//类别Id
						String property = sce.getPropertid();
						com.bj58.sfft.json.orgjson.JSONArray arrey = new com.bj58.sfft.json.orgjson.JSONArray(property);
						String mt ="";
						ProductCategoryEntity cate = this.getProductCategoryEntityById(prodcateid);
						String cname = cate.getCname();
						String qitmc ="";
						String qitcontent ="";
						String bigCount ="";
						String smalCount ="";
						for (int i = 0; i < arrey.length(); i++) {
							com.bj58.sfft.json.orgjson.JSONObject obj = arrey.getJSONObject(i); 
							String propertyid = (String) obj.get("propertyid");
							if(propertyid.equals("city") || propertyid.equals("area")){
								propertyid = (String) obj.get("propertyval");
							}
							if(cname.equals("其他")){
								 if(propertyid.equals("41")){
									 qitmc = (String) obj.get("propertyval");
								 }
								 if(propertyid.equals("42")){
									 qitcontent = (String) obj.get("propertyval");
								 }
							}else{
								mt = mt + propertyid+",";
							}
							if(propertyid.equals("39")){
								bigCount = (String) obj.get("propertyval");
							}
							if(propertyid.equals("40")){
								smalCount = (String) obj.get("propertyval");
							}
						}
						if(mt.indexOf(",")>-1){
							mt = mt.substring(0,mt.lastIndexOf(","));
						}
						if(mt.indexOf(",")>-1){
							mt = "["+mt+"]";
						}
						long procId = cate.getProductid();
						if(cname != null && cname.equals("其他")){
							text = text +orderchildId +"_"+prodcateid+"_"+"qit"+"_"+
						           qitmc+"_"+qitcontent+"_"+sce.getMinprice()+";";
						}else{
							if(procId == 1104 || procId == 2203){
								text = text +orderchildId +"_"+prodcateid+"_"+mt+"_"+bigCount+"_"+smalCount+";";
							}else{
								text = text +orderchildId +"_"+prodcateid+"_"+mt+";";
							}
							
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(text != ""){
				if(text.indexOf(";")>0){
					text = text.substring(0,text.lastIndexOf(";"));
				}
			}
			return text;
	 }
	 
	 public ProductCategoryEntity getProductCategoryEntityById(long id){
		 ProductCategoryEntity pe = null;
		 try {
			pe = RSBLL.getstance().getProductCategoryService().getProductCategoryEntityById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return pe;
	 }
	 /**
	  * 根据Id获得FollowUpEntity对象
	  * @param followId
	  * @return
	  */
	public  FollowupEntity getFollowEntityById(long followId){
		FollowupEntity entity = null;
		try {
			entity = RSBLL.getstance().getFollowupService().getFollowupEntityById(followId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	/**
	 * 根据Id获得SorderChildrenEntity对象
	 * @param childId
	 * @return
	 */
	public SorderChildrenEntity getSorderChildrenEntityById(long childId){
		SorderChildrenEntity sorderChildrenEntity = null;
		try {
			sorderChildrenEntity = RSBLL.getstance().getSorderChildrenService().getSorderChildrenEntityByid(childId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sorderChildrenEntity;
	}
	
	public long saveOrderChildren(SorderChildrenEntity children){
		long childId = 0l;
		try {
			childId = RSBLL.getstance().getSorderChildrenService().addSorderChildrenEntity(children);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return childId;
	}
	/**
	 * 更新followUpentity
	 * @param followEntityId
	 * @param intentcode
	 */
	public void modifyFollowEntity(String followEntityId,String intentcode,String beiz,long loginuser){
		long followId = Long.valueOf(followEntityId);
		FollowupEntity flowEntityt = AddOrderRealService.getInstance().getFollowEntityById(followId);
		int code = 1;
		if(null != intentcode && !"".equals(intentcode)){
			code = Integer.parseInt(intentcode);
		}
		if(StringUtil.isEmpty(beiz)){
			FollowupEntity followupEntity = new FollowupEntity();
			followupEntity.setOrderid(flowEntityt.getOrderid());
			followupEntity.setAddtime(new Date().getTime());
			followupEntity.setIntentcode(code);
			followupEntity.setContent(beiz);
			followupEntity.setEmpid(loginuser);
			RealBusinessService.getInstance().saveFollowupEntity(followupEntity);
		}else{
			if(StringUtil.isEmpty(intentcode)){
				code = Integer.parseInt(intentcode);
				flowEntityt.setIntentcode(code);
				updateFollowEntity(flowEntityt);
			}
		}
	}
	/**
	 * 更新sorder
	 * @param orderEnt
	 * @param beiz
	 */
	public void modifySorderEntity(SorderEntity orderEnt,String beiz,String constansChild,float totalMoney
			,Date now,float deletemoney,String gongsmc,long busId,float shifmoney){
		if(orderEnt != null){
			//orderEnt.setDescription(beiz);
			if(constansChild != ""){
				constansChild = constansChild.substring(0,constansChild.lastIndexOf(","));
				//String cons= orderEnt.getContents();
				//String temp ="";
				//if(cons != null && !"".equals(cons)){
						/*if(cons.indexOf(",") > -1){
						temp = cons.replace("]", ",")+constansChild+"]";
					}else{
						temp = "["+cons+","+constansChild+"]";
					}*/
				//	temp = cons.replace("]", ",")+constansChild+"]";
				//}
				//orderEnt.setContents(temp);
			}
			orderEnt.setUpdatetime(now.getTime());
			//orderEnt.setCompanyname(gongsmc);
			totalMoney = totalMoney - deletemoney;
			orderEnt.setTotalmoney(totalMoney);
			orderEnt.setPaymoneycount(shifmoney);
			orderEnt.setBusid(busId);
			//更新sorder
			updateSorderEntity(orderEnt);
			SorderSuperEntity sup;
			try {
				sup = RSBLL.getstance().getSorderSuperService().getSorderSuperEntityByid(orderEnt.getSuperid());
				List<SorderEntity> list = RSBLL.getstance().getSorderService()
					       .getSorderEntityListBypage("superid = "+ sup.getSuperid(), 1, 20, "orderid");
					float money = 0f;
					if(StringUtil.isListNull(list)){
						for(SorderEntity se : list) {
							money = money + se.getTotalmoney();
						}
					}
					money = money - deletemoney;
				if(sup != null){
					sup.setTotalmoney(money);
				}
				updateSorderSuperEntity(sup);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getCateIdOtherBycondition(String productId){
		String cateIds ="";
		try {
			List<ProductCategoryEntity> pre = RSBLL.getstance().
					getProductCategoryService().getProductCategoryEntitys("productid ="+productId+" ", 1, 1, "cid desc");
			if(pre !=null && pre.size() >0){
				cateIds = pre.get(0).getCid()+"";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cateIds;
	}
	
	
	public void deleteSorderEntity(long schildId){
		try {
			RSBLL.getstance().getSorderChildrenService().deleteSorderChildrenEntity(schildId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleEntitySorder(long sid){
		try {
			RSBLL.getstance().getSorderService().deleteSorderEntity(sid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据parentId获得数据
	 * @param parentid
	 * @return
	 */
	public List<AreasEntity> getAreaEntityByparentId(String parentid){
		List<AreasEntity>  arear  = null;
		try {
			arear  = RSBLL.getstance().getAreasService()
					.getAeasEntity("parentid ='"+parentid+"'", 1, 80, "areaid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arear;
	}
	
	public ProductPropertiesEntity getProductPropertiesEntityByid(long bid){
		ProductPropertiesEntity pre = null;
		try {
			pre = RSBLL.getstance().getProductPropertiesService().getProductPropertiesEntityById(bid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pre;
	}
	
	public net.sf.json.JSONObject getJsonObject(String str){
		net.sf.json.JSONObject obj = null;
        if(StringUtil.isEmpty(str)){
        	obj = new net.sf.json.JSONObject();
        	obj.put("propertyid", str);
			long lid = Long.valueOf(str);
			ProductPropertiesEntity entity = AddOrderRealService.getInstance().getProductPropertiesEntityByid(lid);
		    if(entity != null){
		    	obj.put("propertyval", entity.getType());
		    }
        }
        return obj;
	}
	public net.sf.json.JSONObject getJsonAreaObject(String str,String divide){
		net.sf.json.JSONObject obj = null;
        if(StringUtil.isEmpty(str)){
        	obj = new net.sf.json.JSONObject();
        	if(divide.equals("city")){
        		obj.put("propertyid", "city");
        	}else{
        		obj.put("propertyid", "area");
        	}
        	obj.put("propertyval", str);
        }
        return obj;
	}
	
	public String getQitprice2Json(String jsonstr){
		String price ="0";
		try {
			com.bj58.sfft.json.orgjson.JSONArray arry = new com.bj58.sfft.json.orgjson.JSONArray(jsonstr);
		    for (int i = 0; i < arry.length(); i++) {
				com.bj58.sfft.json.orgjson.JSONObject obj = (com.bj58.sfft.json.orgjson.JSONObject) arry.get(i);
			     String key = (String)obj.get("propertyid");
			     if(key.equals("qtprice")){
			    	 price = (String)obj.get("propertyval");
			    	 break;
			     }
		    }
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return price;
	}
	
	public ProductPropertiesEntity getProductPropertiesEntityBymc(String mc){
		ProductPropertiesEntity pr = null;
		try {
			List<ProductPropertiesEntity> ptr = (List<ProductPropertiesEntity>) RSBLL.getstance().getProductPropertiesService()
					.getProductPropertiesEntitys("proname ='"+mc+"'", 1, 1, "proid");
			if(StringUtil.isListNull(ptr)){
				pr = ptr.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pr;
	}
	
	public AreasEntity getAreasEntityBymc(String mc){
		AreasEntity  area = null;
		try {
			List<AreasEntity> lis = RSBLL.getstance().getAreasService()
					.getAeasEntity("name = '"+mc+"'", 1, 1, "areaid");
			if(StringUtil.isListNull(lis)){
				area = lis.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return area;
	}
	public String getCityAreaBymc(String zccity_){
		if(!StringUtil.isNum(zccity_)){
			  AreasEntity ar = getAreasEntityBymc(zccity_);
			  if(ar != null){
				zccity_ = ar.getAreaid()+"";
			  }
		  }
		return zccity_;
	}
	public String getPropertyBymc(String zccity_){
		if(!StringUtil.isNum(zccity_)){
			  ProductPropertiesEntity ar = getProductPropertiesEntityBymc(zccity_);
			  if(ar != null){
				zccity_ = ar.getProid()+"";
			  }
		  }
		return zccity_;
	}
	/**
	 * 获取价格
	 * @param productId
	 * @param cateId
	 * @param locactid
	 * @param properties
	 */
	public float getPriceBycondition(String productId,String cateId,String locactid,String properties){
		List<PricesEntity> pe = null;
		StringBuffer append = null;
		float price = 0;
		try {
			append = new StringBuffer("1=1");
			if(StringUtil.isEmpty(productId)){
				append.append(" and productid ="+productId);
			}
			if(StringUtil.isEmpty(cateId)){
				append.append(" and procateid ="+cateId);
			}
			if(StringUtil.isEmpty(locactid)){
				append.append(" and localid ="+locactid);
			}
			if(StringUtil.isEmpty(properties)){
				if(productId.equals("1105") || productId.equals("2202")){
					if(cateId.equals("4001") || cateId.equals("4002")){
						append.append(" and properties ='"+properties+"'");
					}
				}else{
					if(productId.equals("1104") || productId.equals("2203") ){
						append.append(" and properties ='"+properties+"'");
					}else{
						//append.append(" or properties ='"+properties+"'");
					}
				}
				if("2009".equals(cateId) || "2010".equals(cateId)){
					append.append(" and properties ='"+properties+"'");
				}
				if("1003".equals(cateId) && properties.contains("44")){//外资公司
					append.append(" and properties ='"+properties+"'");
				}
			}
			String cot = append.toString();
			
			pe = RSBLL.getstance().getPricesService().getPricelistBypage(cot, 1, 99, "priceid");
			if(StringUtil.isListNull(pe)){
				
				if(StringUtil.isEmpty(properties)){
					for(PricesEntity p : pe){
						if(p.getProperties() != null && p.getProperties().equals(properties)){
							price = p.getPrice();
							break;
						}else{
							PricesEntity entity = pe.get(0);
							price = entity.getPrice();
						}
					}
				}else{
					PricesEntity entity = pe.get(0);
					price = entity.getPrice();
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return price;
	}
	
	/**
	 * 获得属性Id集合
	 * @param properties
	 */
	public String getProperties4json(String properties,String productId){
		String proter ="";
		try {
			com.bj58.sfft.json.orgjson.JSONArray arry = new com.bj58.sfft.json.orgjson.JSONArray(properties);
			boolean flag = StringUtil.isEmpty(productId);
			for(int i =0;i< arry.length();i++){
		    	com.bj58.sfft.json.orgjson.JSONObject obj = arry.getJSONObject(i);
		    	String key = (String)obj.get("propertyid");
		    	String val = (String)obj.get("propertyval");
		    	//排除区域
		    	if(flag){
		    		if(key.equals("area")){
		    			proter = proter + val +",";
		    		}
		    	}else{
		    		if(!(key.equals("area")||key.equals("city"))){
		    			if(!val.equals("companytype")&& !val.equals("seller")){
		    				proter = proter + key +",";
		    			}
		    		}
		    	}
		    }
			if(proter != ""){
				proter = proter.substring(0, proter.lastIndexOf(","));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return proter;
	}
	
	public String getGssx4json(String properties){
		String proter ="";
		try {
			com.bj58.sfft.json.orgjson.JSONArray arry = new com.bj58.sfft.json.orgjson.JSONArray(properties);
			for(int i =0;i< arry.length();i++){
		    	com.bj58.sfft.json.orgjson.JSONObject obj = arry.getJSONObject(i);
		    	String key = (String)obj.get("propertyid");
		    	String val = (String)obj.get("propertyval");
		    	//排除区域
		    	if(val.equals("companytype")){
    				proter = proter + key +",";
    			}
		    }
			if(proter != ""){
				proter = proter.substring(0, proter.lastIndexOf(","));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return proter;
	}
	/**
	 * 返回格式：价格_新的属性格式
	 * @param properties
	 * @return
	 */
	public String getPriceforproer(String properties){
		String price ="";
		try {
			com.bj58.sfft.json.orgjson.JSONArray arry = new com.bj58.sfft.json.orgjson.JSONArray(properties);
			JSONArray jarry = new JSONArray();
			JSONObject mobj = null;
			for(int i =0;i< arry.length();i++){
				mobj = new JSONObject();
		    	com.bj58.sfft.json.orgjson.JSONObject obj = arry.getJSONObject(i);
		    	String key = (String)obj.get("propertyid");
		    	String val = (String)obj.get("propertyval");
		    	//排除区域
		    	if(key.equals("singleprice")){
		    		price = val;
		    	}else{
		    		mobj.put("propertyid", key);
		    		mobj.put("propertyval", val);
		    		jarry.add(mobj);
		    	}
		    }
		    String newproperty = jarry.toString();
		    price = price+"_"+newproperty;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return price;
	}
	/**
	 * 获得区域Id
	 * @param properties
	 * @return
	 */
	public String getLocalcity(String properties,String cateId){
		String locity ="";
		try {
			com.bj58.sfft.json.orgjson.JSONArray arry = new com.bj58.sfft.json.orgjson.JSONArray(properties);
			for(int i =0;i< arry.length();i++){
		    	com.bj58.sfft.json.orgjson.JSONObject obj = arry.getJSONObject(i);
		    	String key = (String)obj.get("propertyid");
		    	if(cateId.equals("1002") || cateId.equals("1007")||cateId.equals("1001")){
		    		if(key.equals("area")){
			    		locity = (String)obj.get("propertyval");
			    		break;
			    	}
		    	}else{
		    		if(key.equals("city")){
			    		locity = (String)obj.get("propertyval");
			    		break;
			    	}
		    	}
		    }
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return locity;
	}
	
	public String backdefalustJson(String producepropertys,String priceproperty,String type){
		String back ="";
		try {
			com.bj58.sfft.json.orgjson.JSONArray arry = new com.bj58.sfft.json.orgjson.JSONArray(producepropertys);
			JSONArray backarry = new JSONArray();
			JSONObject bckobj = null;
			String key = "";
			String val ="";
			for(int i =0;i< arry.length();i++){
		    	com.bj58.sfft.json.orgjson.JSONObject obj = arry.getJSONObject(i);
		    	bckobj = new JSONObject();
		        key = (String)obj.get("propertyid");
		    	val  = (String)obj.get("propertyval");
		    	bckobj.put("propertyid", key);
		    	bckobj.put("propertyval", val);
		    	backarry.add(bckobj);
		    }
			//新增12个与代理记账
			if(StringUtil.isEmpty(priceproperty)){
				bckobj = new JSONObject();
				bckobj.put("propertyid",priceproperty);
		    	bckobj.put("propertyval", type);
		    	backarry.add(bckobj);
			}else{
				bckobj = new JSONObject();
				bckobj.put("propertyid", "33");
		    	bckobj.put("propertyval", "bookOrder");
		    	backarry.add(bckobj);
			}
	    	back = backarry.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return back;
	}

	public String getQitPerperties(long sorderId){
		List<SorderChildrenEntity> sce = null;;
		String infos = "";
		try {
			sce = RSBLL.getstance().getSorderChildrenService()
					.getSorderChildrenEntityListBypage("orderid = "+ sorderId, 1, 1, "coid");
			if(StringUtil.isListNull(sce)){
				SorderChildrenEntity se = sce.get(0);
				if(se != null){
					infos = se.getPropertid();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
 /**
  * 通过订单ID获得代理记账历史数据归属问题
  * @param orderId
  * @return
  */
	public Map<Long,String> getCateIdByOrderId(long orderId){
		 Map<Long,String> catemap = null;
		List<SorderChildrenEntity> lschilds = null;
		try {
			 lschilds = this.getSorderChildBysorder(orderId);
			 if(StringUtil.isListNull(lschilds)){
				 catemap = new HashMap<Long, String>();
				 long caid =0;
				 for(SorderChildrenEntity se :lschilds ){
					 caid = se.getProdcateid();
					 if(caid == 4003 || caid == 4004 ||caid == 4005 || caid == 10014 || caid == 10015){
						 catemap.put(caid, "true");
					 }
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return catemap;
	}
}
