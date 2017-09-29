package com.jixiang.argo.lvzheng.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.utils.EnterpriseUtils;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jx.argo.BeatContext;
import com.jx.service.enterprise.contract.ILvEnterpriseRoleRelationService;
import com.jx.service.enterprise.entity.LvEnterpriseEntity;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.FollowupEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.ProductEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.newcore.entity.SorderSuperEntity;

public class RealBusinessService {
	private static RealBusinessService instance = null;

	public static RealBusinessService getInstance() {
		if (instance == null) {
			instance = new RealBusinessService();
		}
		return instance;
	}

	/**
	 * 根据手机号获得用户Id
	 * 
	 * @param phoneNum
	 * @return
	 */
	public  String getUserIdByphoneNum(String phoneNum) {
		String userId = "";
		try {
			List<LoginEntity> le = RSBLL
					.getstance()
					.getLoginService()
					.getLoginEntity("userphone = '" + phoneNum + "'", 1, 1,
							"userid");
			if (le.size() > 0) {
				userId = le.get(0).getUserid() + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
	/**
	 * 获得用户对象
	 * @param phoneNum
	 * @return
	 */
	public  LoginEntity getLoginEntityByphoneNum(String phoneNum) {
		LoginEntity userId = null;
		try {
			List<LoginEntity> le = RSBLL
					.getstance()
					.getLoginService()
					.getLoginEntity("userphone = '" + phoneNum + "'", 1, 1,
							"userid");
			if (le.size() > 0) {
				userId = le.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
	public String getLoginEntityStr(LoginEntity le){
		String text ="";
		try {
			if(le != null){
				text = le.getUsername()+";"+le.getAddress()+";"+KVMap.channelMap.get(le.getChannel()+"")+";"+le.getLandlinenumber()
						+";"+le.getEmail()+";"+le.getCompanyname()+";"+le.getChannel()+";"+le.getAddress();
				long userId = le.getUserid();
				String secondgsmc = "";
				String usname = le.getUsername();
				String phone = le.getUserphone();
				String mt ="";
				if(StringUtil.isEmpty(usname)){
					secondgsmc = "new" +","+usname+"的公司";
					mt = usname+"的公司";
				}else{
					secondgsmc = "new" +","+phone+"的公司";
					mt = phone+"的公司";
				}
				String havegsmc = getBusenessLibraryinfoByuserId(userId,mt);
				String thiridgsmc = ""+","+"新建公司";
				String suosgs = "";
				if(StringUtil.isEmpty(havegsmc)){
					suosgs = suosgs +havegsmc+"&" +thiridgsmc;
				}else{
					suosgs = thiridgsmc;
				}
				//suosgs= suosgs+secondgsmc+"&"+thiridgsmc;
				text = text +";"+suosgs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public void updateOrderBusid(long orderid,long busid){
		try {
			SorderEntity se = RSBLL.getstance().getSorderService().getSorderEntityByid(orderid);
			if(se != null){
				se.setBusid(busid);
				RSBLL.getstance().getSorderService().updateSorderEntity(se);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public long getBusinessLibraryId(BeatContext beat,String gongsmc,long loginuser,String cityId, String cityAreaId){
/*		String hiddissuosgs =  beat.getRequest().getParameter("hiddissuosgs");
		long busid =0;
		BusinessLibaryEntity le = null;
		long ltime = new Date().getTime();
		if(StringUtil.isEmpty(hiddissuosgs)){
			try {
				busid = Long.valueOf(hiddissuosgs);
				le = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(busid);
				if(le != null){
					if(StringUtil.isEmpty(gongsmc)){
						le.setCompanymc(gongsmc);
					}
					le.setUpdatetime(ltime);
					BusinessLibraryService.getInstance().updateBusinessLibrary(le);
				}
			} catch (Exception e) {
				le = new BusinessLibaryEntity();
				if(StringUtil.isEmpty(gongsmc)){
					le.setCompanymc(gongsmc);
				}
				le.setAddperson(loginuser);
				le.setAddtime(ltime);
				le.setUpdatetime(ltime);
				busid = BusinessLibraryService.getInstance().saveBusinessLibraryEntity(le);
				if(sorder_id != 0){
					updateOrderBusid(sorder_id,busid);
				}
			}
			
		}else{
			le = new BusinessLibaryEntity();
			if(StringUtil.isEmpty(gongsmc)){
				le.setCompanymc(gongsmc);
			}
			le.setAddperson(loginuser);
			le.setAddtime(ltime);
			le.setUpdatetime(ltime);
			busid = BusinessLibraryService.getInstance().saveBusinessLibraryEntity(le);
			if(sorder_id != 0){
				updateOrderBusid(sorder_id,busid);
			}
		}
		return busid;
		*/
		String enterpriseId = beat.getRequest().getParameter("hiddissuosgs");
		// 需要新增企业，但是后台根据名字,ID自动判断是否需要更新
		Map<String,String> enterpriseMap = new HashMap<String, String>();
		enterpriseMap.put("name", gongsmc);
		enterpriseMap.put("enterpriseId", enterpriseId);
		enterpriseMap.put("cityId", cityId);
		enterpriseMap.put("areaId", cityAreaId);
		Long id = 0L;
		Map<String, String> loginInfo = null;
		try {
			loginInfo = EnterpriseUtils.getLoginInfo(beat.getRequest());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			id = RSBLL.getstance().getEnterpriseService().saveEnterpriseAllEntity(enterpriseMap, loginInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(id > 0L){
			// 保存企业关联人员
			try {
				RSBLL.getstance().getEnterpriseRoleRelationService().saveRoleRelationEntity(id, ILvEnterpriseRoleRelationService.ROLETYPE_ORDERPERSON, loginuser, null, loginInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		updateOrderBusid(sorder_id, id);
		return id;
	}
	//判断订单是否绑定企业
	public String orderIsbandBusiness(long orderid){
		String text ="false";
		try {
			SorderEntity se = RSBLL.getstance().getSorderService().getSorderEntityByid(orderid);
			if(se != null){
				long busid = se.getBusid();
//				BusinessLibaryEntity bel = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(busid);
				LvEnterpriseEntity enterpriseById = RSBLL.getstance().getEnterpriseService().getEnterpriseById(busid);
				
				if(enterpriseById != null){
					text = "true";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
	//获得企业库中的公司名称
	public String getBusinessGongsmc(long busid){
/*		String gsmc = "";
		try {
			BusinessLibaryEntity ble = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(busid);
			if(ble != null){
				gsmc = ble.getCompanymc();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gsmc;*/

		try {
			return RSBLL.getstance().getEnterpriseService().getValueByEnterpriseIdAndKey(busid + "", "name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String getLoginEntityNoUserinfoStr(LoginEntity le){
		String text ="";
		try {
			if(le != null){
				long userId = le.getUserid();
				String secondgsmc = "";
				String usname = le.getUsername();
				String phone = le.getUserphone();
				String mt ="";
				if(StringUtil.isEmpty(usname)){
					secondgsmc = "new" +","+usname+"的公司";
					mt = usname+"的公司";
				}else{
					secondgsmc = "new" +","+phone+"的公司";
					mt = phone+"的公司";
				}
				String havegsmc = getBusenessLibraryinfoByuserId(userId,mt);
				String thiridgsmc = ""+","+"新建公司";
				String suosgs = "";
				if(StringUtil.isEmpty(havegsmc)){
					suosgs = suosgs +havegsmc+"&" +thiridgsmc;
				}else{
					suosgs= suosgs+thiridgsmc;
				}
				//suosgs= suosgs+secondgsmc+"&"+thiridgsmc;
				text = suosgs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
	/**
	 * 获得用户企业中的公司名称
	 * @param userId
	 * @return
	 */
	public String  getBusenessLibraryinfoByuserId(long userId,String mtmc){
//		List<BusinessLibaryEntity> lbls = null;
		String text ="";
		try {
/*			lbls = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityListBypage("addperson ="+userId+" and status != -1", 1, 50, "busId");
			if(StringUtil.isListNull(lbls)){
				for(BusinessLibaryEntity ble : lbls){
					text = text + ble.getBusId()+","+ ((ble.getCompanymc()==null||"".equals(ble.getCompanymc()))?mtmc:ble.getCompanymc())+"&";
				}
				if(text.indexOf("&")> -1){
					text = text.substring(0,text.lastIndexOf("&"));
				}
			}*/
			
			
			// 获取下单人关联的企业
			List<LvEnterpriseEntity> enterpriseList = RSBLL.getstance().getEnterpriseService().getEnterpriseListByRoleTypeAndRoleIdWork(ILvEnterpriseRoleRelationService.ROLETYPE_ORDERPERSON, userId + "");
			if(enterpriseList != null && !enterpriseList.isEmpty()){
				for(LvEnterpriseEntity enterprise : enterpriseList){
					text = text + enterprise.getEnterpriseId()+","+ ((enterprise.getName()==null||"".equals(enterprise.getName()))?mtmc:enterprise.getName())+"&";
				}
				if(text.indexOf("&")> -1){
					text = text.substring(0,text.lastIndexOf("&"));
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * 根据用户Id获得订单记录
	 */
	public List<SorderEntity> getSorderEntitys(String userId){
		List<SorderEntity> lse = null;
		if(null != userId && !"".equals(userId.trim())){
			String condition ="userid = "+userId;
			try {
				lse = RSBLL.getstance().getSorderService()
						.getSorderEntityListBypage(condition, 1, 10, "postime desc");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lse;
	}
	/**
	 * 添加用户
	 */
	public String addUserEntity(LoginEntity lentity){
		String userId ="";
		try {
			userId = RSBLL.getstance().getLoginService().addLoginEntity(lentity)+"";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
	/**
	 * 获得对应的城市集合
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getCitities(){
		List ciyies = null;
		try {
			List<ProductCategoryEntity> pces = RSBLL.getstance().getProductCategoryService()
			.getProductCategoryEntitys("", 1, 50, "");
			if(pces.size() >0){
				ciyies = new ArrayList();
				for(ProductCategoryEntity pce : pces){
					int cityId = pce.getCityid();
					if(!(ciyies.contains(cityId))){
						ciyies.add(cityId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ciyies;
	}
	
	/** 
	 * 获得地区  (key:对应城市(集合) value：对应区集合)
	 * @return
	 */
	public Map<AreasEntity,List<ProductPropertiesEntity>> getAreasEntityS(String condition){
		List<AreasEntity> lareas = null;
		Map<AreasEntity,List<ProductPropertiesEntity>> map = 
				new HashMap<AreasEntity, List<ProductPropertiesEntity>>();
		try {
			lareas = RSBLL.getstance().getAreasService()
			.getAeasEntity(condition, 1, 80, "areaid");
			List<ProductPropertiesEntity> propro = null;//获得城市下所有的区
			List<ProductPropertiesEntity> proproes = null;
			List<String> pronames =null;
			for(AreasEntity ae : lareas){
				long parentid = ae.getAreaid();
				//获取下属子节点
				List<AreasEntity> larchil = RSBLL.getstance().getAreasService()
						.getAeasEntity("parentid ='"+parentid+"'", 1, 80, "areaid");
				String condi ="";
				if(larchil.size() >0){
					for(AreasEntity are : larchil){
						condi = condi+are.getAreaid()+",";
					}
				}
				if(condi != ""){
					condi = condi.substring(0,condi.lastIndexOf(","));
					condi = condi.replace("(", "('").replace(",", "','").replace(")", "')");
					condi ="localid in ('"+condi+"') and type='addressprice'";
					propro = RSBLL.getstance()
							.getProductPropertiesService().
							getProductPropertiesEntitys(condi, 1, 50, "proid");
					if(propro.size() > 0){
						//排除重复的
						pronames = new ArrayList<String>();
						proproes = new ArrayList<ProductPropertiesEntity>();
						for(ProductPropertiesEntity ppe: propro){
							String prona = ppe.getProname();
							if(!pronames.contains(prona)){
								proproes.add(ppe);
							}
							pronames.add(prona);
						}
						if(proproes.size() >0){
							map.put(ae, proproes);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 获得商品集合
	 */
	public List<ProductEntity> getProductEntityList(String condition){
		List<ProductEntity> lpe = null;
		try {
			lpe = RSBLL.getstance().getProductNewService()
			.getProductEntitys(condition, 1, 25, "pid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lpe;
	}
	/**
	 * 获得商品下的分类
	 * @param proid
	 * @return
	 */
	public List<ProductCategoryEntity> getProductCategoryEntitysByproId(long proid){
		List<ProductCategoryEntity> pce = null;
		String condition = "productid = "+ proid;
		try {
			pce = RSBLL.getstance().getProductCategoryService()
			.getProductCategoryEntitys(condition, 1, 25, "cid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pce;
	}
	/**
	 * 保存t_sorder_super对象
	 * @param sentity
	 * @return
	 */
	public Long saveSorderSuperEntity(SorderSuperEntity sentity){
		Long superId = 0l;
		try {
			superId = RSBLL.getstance().getSorderSuperService().addSorderSuperEntity(sentity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return superId;
	}
	/**
	 * 保存t_sorder
	 * @param soe
	 * @return
	 */
	public Long saveSorederEntity(SorderEntity soe){
		Long sorderid = 0l;
		try {
			sorderid = RSBLL.getstance().getSorderService().addSorderEntity(soe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sorderid;
	}
	/**
	 * 保存t_sorder_children
	 * @param sce
	 * @return
	 */
	public Long saveSorderhildEntity(SorderChildrenEntity sce){
		Long childid = 0l;
		try {
			childid = RSBLL.getstance().getSorderChildrenService().addSorderChildrenEntity(sce);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return childid;
	}
	/**
	 * 更新t_sorder 通过sorderId
	 */
	public void updateSorderEntity4contents(long sorderId,String contents){
		try {
			SorderEntity se = RSBLL.getstance().getSorderService().getSorderEntityByid(sorderId);
			if(se != null){
				RSBLL.getstance().getSorderService().updateSorderEntity(se);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保存follow记录
	 * @param follow
	 */
	public void saveFollowupEntity(FollowupEntity follow){
		try {
			RSBLL.getstance().getFollowupService().addFollowupEntity(follow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新
	 * @param entity
	 */
	public void updateLoginEntity(LoginEntity entity){
		try {
			RSBLL.getstance().getLoginService().updateLoginEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void  changgeCity(List<String> selectList,String productId,long sorder_id,float totalMoney,
			String beiz,int citityint,long login,String gongsmc,Map<String,String> map,int intentcode,float shifmoney){
		//更新t_sorder_super
		SorderEntity se = null;
		try {
			se = RSBLL.getstance().getSorderService().getSorderEntityByid(sorder_id);
			long superId = 0;
			long time = new Date().getTime();
			if(se != null){
				superId = se.getSuperid();
				SorderSuperEntity sup = RSBLL.getstance().getSorderSuperService().getSorderSuperEntityByid(superId);
				
				if(sup != null){
					sup.setTotalmoney(totalMoney);
					if(StringUtil.isEmpty(beiz)){
						sup.setDescription(beiz);
					}
					sup.setUpdatetime(time);
					RSBLL.getstance().getSorderSuperService().updateSorderSuperEntity(sup);
				}
			}
			//删除原单和对应的子单
			List<SorderChildrenEntity> lces = RSBLL.getstance().getSorderChildrenService()
					.getSorderChildrenEntityListBypage("orderid = "+sorder_id, 1, 25, "coid");
			if(lces != null){
				for(SorderChildrenEntity wc : lces){
					AddOrderRealService.getInstance().deleteSorderEntity(wc.getCoid());
				}
			}
			//更新母单
			SorderEntity newsorder = AddOrderRealService.getInstance().getSorderEntityById(sorder_id);
			Long sorderId = sorder_id;
			if(newsorder != null){
				if(StringUtil.isEmpty(beiz)){
					newsorder.setDescription(beiz);
				}
				if(StringUtil.isEmpty(gongsmc)){
					//newsorder.setCompanyname(gongsmc);
				}
				newsorder.setOrdercity(citityint);
				if(null !=productId && !"".equals(productId.trim())){
					long product_id = Long.valueOf(productId);
					newsorder.setProductid(product_id);
				}
				 //订单金额 （对应选择商品和下属服务的价格）
			    newsorder.setTotalmoney(totalMoney);
			    newsorder.setPaymoneycount(shifmoney);//实付金额
				//保存订单
				 AddOrderRealService.getInstance().updateSorderEntity(newsorder);
			}
			//保存子单
   			if(map != null){
   				//迭代map  key:cateId  value : arry tostring
   				SorderChildrenEntity sorderChildrenEntity = null;
   				for(Entry<String, String> entity : map.entrySet()){
   					sorderChildrenEntity = new SorderChildrenEntity();
   					String key =  entity.getKey();
   					String val = entity.getValue();
   					String strpro = AddOrderRealService.getInstance().getPriceforproer(val);
						String [] temp = strpro.split("_");
						String pri = temp[0];
						float totalPrice = 0;//获得价格
						if(StringUtil.isEmpty(pri)){
							totalPrice = Float.valueOf(pri);
							sorderChildrenEntity.setPrice(totalPrice);
							sorderChildrenEntity.setMinprice(totalPrice);
						}
						String pros = temp[1];
						if(!StringUtil.isEmpty(val)){
							pros = val;
						}
						sorderChildrenEntity.setPropertid(pros);
						if(StringUtil.isEmpty(key)){
							long lcate = Long.valueOf(key);
							int lcateid = Integer.parseInt(key);
							sorderChildrenEntity.setProdcateid(lcateid);
	   						ProductCategoryEntity pro = AddOrderRealService.getInstance().getProductCategoryEntityById(lcate);
	   						String proname =  pro.getCname();
	   						if(proname.equals("其他")){
	   							String qtprice = AddOrderRealService.getInstance().getQitprice2Json(pros);
	   							float qt = Float.valueOf(qtprice);
	   							sorderChildrenEntity.setMinprice(qt);
	   							sorderChildrenEntity.setPrice(qt);
	   						}
						}
   						sorderChildrenEntity.setOrderid(sorderId);
   						sorderChildrenEntity.setCostate(0);
   						sorderChildrenEntity.setUpdatetime(time);
   						this.saveSorderhildEntity(sorderChildrenEntity);
   				}
   				//保存跟进
   				FollowupEntity followupEntity = new FollowupEntity();
				followupEntity.setOrderid(sorderId);
				followupEntity.setAddtime(time);
				followupEntity.setIntentcode(intentcode);
				followupEntity.setContent(beiz);
				followupEntity.setEmpid(login);
				this.saveFollowupEntity(followupEntity);
   			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
