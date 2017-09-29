package com.jixiang.argo.lvzheng.service.wd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.frame.service.OrderAssembleService;
import com.jixiang.argo.lvzheng.service.CouponsService;
import com.jixiang.argo.lvzheng.utils.EntityUtils;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.Timers;
import com.jixiang.argo.lvzheng.vo.BusinessLibaryVO;
import com.jixiang.argo.lvzheng.vo.CustomerVO;
import com.jixiang.argo.lvzheng.vo.EnterpriseVO;
import com.jixiang.argo.lvzheng.vo.OrderChildInfoVO;
import com.jixiang.argo.lvzheng.vo.SorderVO;
import com.jixiang.argo.lvzheng.vo.UserCouponsVO;
import com.jx.argo.BeatContext;
import com.jx.service.enterprise.contract.ILvEnterpriseRoleRelationService;
import com.jx.service.enterprise.contract.ILvEnterpriseService;
import com.jx.service.enterprise.entity.LvEnterpriseEntity;
import com.jx.service.enterprise.entity.LvEnterprisePersonEntity;
import com.jx.service.enterprise.entity.LvEnterpriseRoleRelationEntity;
import com.jx.service.newcore.contract.IAreasService;
import com.jx.service.newcore.contract.IEmployersService;
import com.jx.service.newcore.contract.ILoginService;
import com.jx.service.newcore.contract.ISorderService;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;

public class QueryDataService {
	
	public IEmployersService iemployersService = RSBLL.getstance().getEmployersService();
	public ILoginService iloginService = RSBLL.getstance().getLoginService();
	public ILvEnterpriseService ienterpriseService =  RSBLL.getstance().getEnterpriseService();
	public IAreasService  iareaService = RSBLL.getstance().getAreasService();
	public ISorderService isorderService = RSBLL.getstance().getSorderService();
	
	private static QueryDataService instance = null;
	
	public static QueryDataService getInstance(){
		if(instance == null){
			instance = new QueryDataService();
		}
		return instance;
	}
	
	
	
	public void queryData(BeatContext beat,String queryType,String query){
		try {
			JQqueryCustomerData(beat,queryType,query);
			JQqueryEmpData(beat,queryType,query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//查询客户数据[精确]
	public void JQqueryCustomerData(BeatContext beat,String queryType,String query) throws Exception{
		List<LoginEntity> loginEntitys = new ArrayList<LoginEntity>();
		if("1".equals(queryType)){
			loginEntitys = iloginService.getLoginEntity("userphone='"+query+"'", 1, 10, "addtime desc");
			System.out.println("****************************"+loginEntitys.size());
			JQCommonCustomerData(loginEntitys,beat);
		}else if("2".equals(queryType)){
			loginEntitys = iloginService.getLoginEntity("username='"+query+"'", 1, 10, "addtime desc");
			System.out.println("****************************"+loginEntitys.size());
			JQCommonCustomerData(loginEntitys,beat);
		}else if("3".equals(queryType)){
			queryEnterprise(beat,query);
		}
	}
	
	public void JQCommonCustomerData(List<LoginEntity> loginEntitys,BeatContext beat) throws Exception{
		List<SorderEntity> sorderEntitys = new ArrayList<SorderEntity>();
		List<LvEnterpriseEntity> enterpriseEntitys = new ArrayList<LvEnterpriseEntity>();
		if(null != loginEntitys && loginEntitys.size() > 0){
			CustomerVO customerVo = getCustomerVo(loginEntitys.get(0));
			beat.getModel().add("loginEntity", customerVo);
			sorderEntitys = isorderService.getSorderEntityListBypage("userid='"+loginEntitys.get(0).getUserid()+"'", 1, 10, "signtime desc");
			List<SorderVO> sorderVOs = getSorderVo(sorderEntitys);
			beat.getModel().add("sorderEntitys", sorderVOs);
			enterpriseEntitys = ienterpriseService.getEnterpriseListByRoleTypeAndRoleIdWork(ILvEnterpriseRoleRelationService.ROLETYPE_ORDERPERSON, String.valueOf(loginEntitys.get(0).getUserid()));
			List<EnterpriseVO> enterpriseVOs = getEnterpriseVo(enterpriseEntitys);
			beat.getModel().add("enterpriseEntitys", enterpriseVOs);
			beat.getModel().add("enterpriseFlag", "false");
		}
	}
	
	//查询用户数据[精确]
	public void JQqueryEmpData(BeatContext beat,String queryType,String query) throws Exception{
		List<EmployersEntity> employsEntitys = new ArrayList<EmployersEntity>();
		List<SorderEntity> sorderEntitys = new ArrayList<SorderEntity>();
		if("1".equals(queryType)){
			employsEntitys = iemployersService.getEmployersEntity("phonenumber='"+query+"'", 1, 10, null);
			System.out.println("****************************"+employsEntitys.size());
		}else if("2".equals(queryType)){
			employsEntitys = iemployersService.getEmployersEntity("realname='"+query+"'", 1, 10, null);
			System.out.println("****************************"+employsEntitys.size());
		}
		if(null != employsEntitys && employsEntitys.size() > 0){
			beat.getModel().add("employsEntitys", employsEntitys);
			Long empid = employsEntitys.get(0).getEmpid();
			sorderEntitys = isorderService.getSorderEntityListBypage("currentempid='"+empid+"' or empid='"+empid+"' or kefuid='"+empid+"' or fwgwempid='"+empid+"' ", 1, 10, "signtime desc");
			System.out.println("****************************"+employsEntitys.size());
			List<SorderVO> sorderVOs = getSorderVo(sorderEntitys);
			beat.getModel().add("empsorderEntitys", sorderVOs);
		}
		
	}
	
	public CustomerVO getCustomerVo(LoginEntity loginEntity) throws Exception{
		CustomerVO customerVo = EntityUtils.transferEntity(loginEntity, CustomerVO.class);
		if(customerVo == null){
			return new CustomerVO();
		}
		AreasEntity areasEntity = iareaService.getAeasEntityById(customerVo.getCityid());
		if(null != areasEntity){
			customerVo.setCityname(areasEntity.getName());
			customerVo.setChannelName(KVMap.channelMap.get(String.valueOf(customerVo.getChannel())));
			customerVo.setAddtimerValue(Timers.longToStr(customerVo.getAddtime()));
		}
		return customerVo;
	}
	
	
	
	public List<SorderVO> getSorderVo(List<SorderEntity> sorderEntitys) throws Exception{
		List<SorderVO> sorderVOs = new ArrayList<SorderVO>();
		if(null != sorderEntitys && sorderEntitys.size()>0){
			for(SorderEntity entity : sorderEntitys){
				SorderVO sorderVo = EntityUtils.transferEntity(entity, SorderVO.class);
				if(sorderVo == null) continue;
				EmployersEntity employEntity = iemployersService.getEmployersEntityById(sorderVo.getKefuid());
				if(null != employEntity){
					sorderVo.setKefuname(employEntity.getRealname());
				}
				employEntity = iemployersService.getEmployersEntityById(sorderVo.getEmpid());
				if(null != employEntity){
					sorderVo.setEmpname(employEntity.getRealname());
				}
				employEntity = iemployersService.getEmployersEntityById(sorderVo.getFwgwempid());
				if(null != employEntity){
					sorderVo.setFwgwempname(employEntity.getRealname());
				}
				employEntity = iemployersService.getEmployersEntityById(sorderVo.getCurrentempid());
				if(null != employEntity){
					sorderVo.setCurrentempname(employEntity.getRealname());
				}
				LoginEntity loginEntity = iloginService.getLoginEntityById(sorderVo.getUserid());
				if(null != loginEntity){
					sorderVo.setUsername(iloginService.getLoginEntityById(sorderVo.getUserid()).getUsername());
				}
				LvEnterpriseEntity enterpriseEntity = ienterpriseService.getEnterpriseById(sorderVo.getBusid());
				if(null != enterpriseEntity){
					sorderVo.setCompanyname(enterpriseEntity.getName());
				}
				sorderVo.setOrderstateValue(KVMap.orderstatemap.get(String.valueOf(sorderVo.getOrderstate()))==null?"":KVMap.orderstatemap.get(String.valueOf(sorderVo.getOrderstate())).toString());
				sorderVo.setSigntimeValue(Timers.longToStr(sorderVo.getSigntime()));
				sorderVo.setPaystateValue(KVMap.orderPayState.get(String.valueOf(sorderVo.getPaystate()))==null?"":KVMap.orderPayState.get(String.valueOf(sorderVo.getPaystate())).toString());
				sorderVOs.add(sorderVo);
			}
		}
		return sorderVOs;
	}
	public List<EnterpriseVO> getEnterpriseVo(List<LvEnterpriseEntity> enterpriseEntities) throws Exception{
		List<EnterpriseVO> enterpriseVOs = new ArrayList<EnterpriseVO>();
		if(null != enterpriseEntities && enterpriseEntities.size() > 0){
			for(LvEnterpriseEntity entity : enterpriseEntities){
				EnterpriseVO enterpriseVO = EntityUtils.transferEntity(entity, EnterpriseVO.class);
				if(enterpriseVO == null) continue;
				AreasEntity areasEntity = iareaService.getAeasEntityById(enterpriseVO.getAreaid());
				if(null != areasEntity){
					enterpriseVO.setAreaname(iareaService.getAeasEntityById(enterpriseVO.getAreaid()).getName());
				}
				areasEntity = iareaService.getAeasEntityById(enterpriseVO.getAreaid());
				if(null != areasEntity){
					enterpriseVO.setCityname(iareaService.getAeasEntityById(enterpriseVO.getCityid()).getName());
				}
				enterpriseVOs.add(enterpriseVO);
			}
		}
		return enterpriseVOs;
	}
	
	//查询企业库
	public void queryEnterprise(BeatContext beat,String query) throws Exception{
		List<String> busids = new ArrayList<String>();
		System.out.println(query+"*******");
		busids = ienterpriseService.getMainValueListByLikeName(query, "enterpriseId");
		System.out.println("***********"+busids);
		beat.getModel().add("enterpriseFlag", "true");
		if(null !=busids && busids.size()<10){
			List<EnterpriseVO> enterpriseEntitys = new ArrayList<EnterpriseVO>();
			EnterpriseVO enterpriseVo =null;
			for(String busid : busids){
				LvEnterpriseEntity enterpriseE = ienterpriseService.getEnterpriseById(Long.valueOf(busid));
				enterpriseVo = EntityUtils.transferEntity(enterpriseE, EnterpriseVO.class);
				if(null != enterpriseVo){
					String aeEmpID = RSBLL.getstance().getLvCompanyService().getBusValueByCompanyIdAndBusKey(busid, "AE");  //获得企业负责人id
					if(StringUtils.isNotBlank(aeEmpID)){
						EmployersEntity employersE = RSBLL.getstance().getEmployersService().getEmployersEntityById(Long.valueOf(aeEmpID));
						if(null != employersE){
							enterpriseVo.setAename(employersE.getRealname());
							enterpriseVo.setAephone(employersE.getPhonenumber());
							enterpriseVo.setAeID(aeEmpID);
						}
					}
					List<LvEnterpriseRoleRelationEntity> lvEnterpriseRoleRelationEntities = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleRelationListByEnterpriseIdAndRoleType(Long.valueOf(busid), ILvEnterpriseRoleRelationService.ROLETYPE_ORDERPERSON);
					if(null != lvEnterpriseRoleRelationEntities && lvEnterpriseRoleRelationEntities.size() > 0){
						long userid = lvEnterpriseRoleRelationEntities.get(0).getRoleId();
						LoginEntity loginEntity = RSBLL.getstance().getLoginService().getLoginEntityById(userid);
						if(null != loginEntity){
							enterpriseVo.setUsername(loginEntity.getUsername());
							enterpriseVo.setUserphone(loginEntity.getUserphone());
							enterpriseVo.setUserID(String.valueOf(loginEntity.getUserid()));
						}
					}
					enterpriseEntitys.add(enterpriseVo);
				}
			}
			beat.getModel().add("enterpriseEntitys", enterpriseEntitys);
			
			List<SorderEntity> sorderEntitys = isorderService.getSorderEntityListBypage(" companyname like '%"+query+"%'", 1, 10, "");
			List<SorderVO> sorderVos = getSorderVo(sorderEntitys);
			beat.getModel().add("OldSorderBusinessLibarys", sorderVos);
			
			List<BusinessLibaryEntity> businessLibaryEntitys = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityListBypage(" companymc like '%"+query+"%'", 1, 10, "");
			List<BusinessLibaryVO> businessLibaryVos = new ArrayList<BusinessLibaryVO>();
			if(null != businessLibaryEntitys && businessLibaryEntitys.size() > 0){
				for(BusinessLibaryEntity businessEntity : businessLibaryEntitys){
					BusinessLibaryVO businessLibarVO = EntityUtils.transferEntity(businessEntity, BusinessLibaryVO.class);
					LoginEntity loginEntity = RSBLL.getstance().getLoginService().getLoginEntityById(businessEntity.getAddperson());
					if(loginEntity != null){
						businessLibarVO.setUsername(loginEntity.getUsername());
						businessLibarVO.setUserphone(loginEntity.getUserphone());
					}
					businessLibaryVos.add(businessLibarVO);
				}
			}
			beat.getModel().add("OldBusinessLibars", businessLibaryVos);
		}else{
			System.out.println("请多输入查询条件!");
			beat.getModel().add("isSoLitter", "true");
		}
	}
	
	//查询企业库对应的所有人员信息
	public void queryEnterprisePeopleList(BeatContext beat,String enterpriseId) throws Exception{
		List<LvEnterprisePersonEntity> enterprisePersionList = new ArrayList<LvEnterprisePersonEntity>();
		enterprisePersionList = RSBLL.getstance().getEnterprisePersonService().getPersonListByEnterpriseId(enterpriseId);
		beat.getModel().add("enterprisePersionList", enterprisePersionList);
	}
	
	
	//查询订单详情
	public void queryOrderDetil(BeatContext beat,String orderid){
		System.out.println("============接到的订单ID=========="+orderid);
		SorderEntity s_orderE = OrderAssembleService.getInstance().getOrderEntityById(Long.valueOf(orderid));
		
		//通过母单ID查询旗下的子单
		List<SorderChildrenEntity> sorderchildList = OrderAssembleService.getInstance().getOrderChildrEntityList("orderid="+orderid);
		List<Map<String,String>> serverNameListtemp = new LinkedList<Map<String,String>>();
		for(SorderChildrenEntity sorderchildE : sorderchildList){
			Map<String,String> tempMap = new HashMap<String, String>();
			String cname = OrderAssembleService.getInstance().getCategoryEntity(sorderchildE.getProdcateid()).getCname();
			tempMap.put("servername", cname);
			
			tempMap.put("serverstate", KVMap.orderServerstatemap.get(String.valueOf(sorderchildE.getCostate())).toString());
			tempMap.put("realname","");
			if(!"0".equals(String.valueOf(s_orderE.getEmpid()))){
				tempMap.put("realname", OrderAssembleService.getInstance().getEmployersEntity(s_orderE.getEmpid()).getRealname()); //取母单的法务ID	
			}
			tempMap.put("time","");
			if(!"".equals(String.valueOf(sorderchildE.getUpdatetime())) && !"0".equals(String.valueOf(sorderchildE.getUpdatetime()))){
				tempMap.put("time", Timers.formatLongDate("yyyy/MM/dd", sorderchildE.getUpdatetime()));
			}
			
			tempMap.put("classTH", "class='green'");
			if("1".equals(sorderchildE.getCostate())){
				tempMap.put("classTR", "class='red'");
			}
		}
		
		//=========获得订单详情TABLE中的数据statt=============
		SorderEntity orderE = OrderAssembleService.getInstance().getOrderEntityById(Long.valueOf(orderid));
		List<OrderChildInfoVO> childOrderList = this.getOrderChildList(orderid);
		//=========获得订单详情TABLE中的数据end================
		
		
		SorderEntity sorderE = OrderAssembleService.getInstance().getOrderEntityById(Long.valueOf(orderid));
	    LoginEntity userE = OrderAssembleService.getInstance().getUserEntity(sorderE.getUserid()) ;
		String coupon_condition=" userid='"+userE.getUserid()+"' and orderid='"+orderid+"' ";
	    List<UserCouponsVO> usercouponsList =  CouponsService.getInstance().getUserCoupons(coupon_condition, "");
	    if(usercouponsList.size()>0){
		    beat.getModel().add("userCoupon", usercouponsList.get(0));
	    }
		beat.getModel().add("order", orderE);
		beat.getModel().add("childOrderList", childOrderList);
	}
	
	
	
	
	/*****
	 * 返回详情信息中子单的集合信息(详情通用)
	 */
	public List<OrderChildInfoVO> getOrderChildList(String orderid) {
		List<OrderChildInfoVO> orderVoList =new ArrayList<OrderChildInfoVO>();
		String condition = "orderid='"+orderid+"'";
		List<SorderChildrenEntity> orderChildList = OrderAssembleService.getInstance().getOrderChildrEntityList(condition);
		for(SorderChildrenEntity childE : orderChildList){
			OrderChildInfoVO orderChildVO = new OrderChildInfoVO();
			Integer categoryId = childE.getProdcateid();  //得到商品类别ID
			ProductCategoryEntity pcategoryE = OrderAssembleService.getInstance().getCategoryEntity(Long.valueOf(categoryId.toString()));
			String childorderName = pcategoryE.getCname();   //获得子单名称 如公司注册或代理地址
			String childorderPropertid = childE.getPropertid();   //获得子单属性 如 地区 内资外资等
	    	
			StringBuffer sbProperName = new StringBuffer();
			try {
				com.bj58.sfft.json.orgjson.JSONArray jsonarray =new com.bj58.sfft.json.orgjson.JSONArray(childorderPropertid);
				if(!childorderName.equals("其他")){ 
					for(int i=0;i<jsonarray.length();i++){
						com.bj58.sfft.json.orgjson.JSONObject jsonO =jsonarray.getJSONObject(i); 
						String propertyid =jsonO.getString("propertyid");
						String propertyval =jsonO.getString("propertyval");
					  
						//如果为城市区域则去areas表取，如果是其他则直接拿出来值否则需要排除不是qitcontent和qtprice两个作为ID的去查询productproperties表
						if(propertyid.equals("city") || propertyid.equals("area")){
							AreasEntity areasE = OrderAssembleService.getInstance().getAreasEntity(Long.valueOf(propertyval));
							sbProperName.append(areasE.getName()).append(" ");
						}else if(!propertyid.equals("qitcontent") && !propertyid.equals("qtprice")){
							ProductPropertiesEntity propertiesE = OrderAssembleService.getInstance().getPropertiesEntity(Long.valueOf(propertyid));
							sbProperName.append(propertiesE.getProname()).append(" ");
						}
					} 
				}else{ 
					for(int i=0;i<jsonarray.length();i++){
						com.bj58.sfft.json.orgjson.JSONObject jsonO =jsonarray.getJSONObject(i); 
						String propertyid =jsonO.getString("propertyid");
						String propertyval =jsonO.getString("propertyval");
					  
						//如果为城市区域则去areas表取，如果是其他则直接拿出来值否则需要排除不是qitcontent和qtprice两个作为ID的去查询productproperties表
						if(propertyid.equals("city") || propertyid.equals("area")){
							AreasEntity areasE = OrderAssembleService.getInstance().getAreasEntity(Long.valueOf(propertyval));
							sbProperName.append(areasE.getName()).append(" ");
						}else if(!propertyid.equals("qtprice")){
							ProductPropertiesEntity propertiesE = OrderAssembleService.getInstance().getPropertiesEntity(Long.valueOf(propertyid));
							if("qitmc".equals(propertiesE.getType())){
								childorderName = propertyval +"(其他)";
							}else if("qitcontent".equals(propertiesE.getType())){
								sbProperName.append(propertyval).append(" ");
							}else{
								childorderName = propertiesE.getProname();	
							}
						}
					} 
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			float childPrice = childE.getMinprice();       //取得子单的成交价格
			
			//只有服务完成时才取得日志
			String logInfo="";
			if(2 == childE.getCostate()){
				//得到操作人与时间
				EmployersEntity empE = OrderAssembleService.getInstance().getEmployersEntity(childE.getEmpid());
				String optionName = "";
				if(null != empE){
					optionName = empE.getRealname();
				}
				String updatetime = Timers.formatLongDate("yyyy-MM-dd HH:mm:ss", childE.getUpdatetime());
				logInfo = optionName +" " +updatetime;	
			}
			
			orderChildVO.setCoid(childE.getCoid());
			orderChildVO.setOrderId(Long.valueOf(orderid));
			orderChildVO.setServerName(childorderName);
			orderChildVO.setServerContent(sbProperName.toString());
			orderChildVO.setState(KVMap.orderChildstatemap.get(String.valueOf(childE.getCostate())).toString());
			orderChildVO.setPrice(childPrice);
			orderChildVO.setLogInfo(logInfo);
			orderVoList.add(orderChildVO);
		} 
		return orderVoList;
	}
	
}
