package com.jixiang.argo.lvzheng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.frame.service.OrderAssembleService;
import com.jixiang.argo.lvzheng.service.OrderService;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.utils.Timers;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.vo.OrderChildInfoVO;
import com.jixiang.argo.lvzheng.vo.OrderInfoVO;
import com.jixiang.union.user.tools.LoginTool;
import com.jx.argo.BeatContext;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.CommentEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.FollowupEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.OrderprocessEntity;
import com.jx.service.newcore.entity.PayProcessEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.ProductEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.newcore.entity.SorderExtEntity;
import com.jx.service.newcore.entity.SpendEntity;
import com.jx.service.newcore.entity.VisitEntity;

/**
 * 针对响应的controlleers调用
 * @author lvzheng-duxf
 * 对外操作订单类
 */
public class OrderServiceImpl implements OrderService{
	OrderAssembleService orderAssemble =new OrderAssembleService();
	/***
	 * 参数：beta对应上下文信息  orderType 对应订单的类型【比如潜在订单列表、潜在法务订单层、放弃订单等根据类型去筛选对应的列表】
	 */
	@Override
	public void getOrderList(BeatContext beat,String orderType,Map<String, String> queryOption,Integer pageno) {
		List<OrderInfoVO> orderList =new ArrayList<OrderInfoVO>();
		
		
  

		String paramOneValue = beat.getRequest().getParameter("paramOneValue")==null?"":beat.getRequest().getParameter("paramOneValue");
		String paramOneType =  beat.getRequest().getParameter("paramOneType")==null?"":beat.getRequest().getParameter("paramOneType");
		String paramTwoValue = beat.getRequest().getParameter("paramTwoValue")==null?"":beat.getRequest().getParameter("paramTwoValue");
		String paramTwoValue2 = beat.getRequest().getParameter("paramTwoValue2")==null?"":beat.getRequest().getParameter("paramTwoValue2");
		String paramThreeValue =  beat.getRequest().getParameter("paramThreeValue")==null?"":beat.getRequest().getParameter("paramThreeValue");
		String paramStartTimeValue = beat.getRequest().getParameter("paramStartTimeValue")==null?"":beat.getRequest().getParameter("paramStartTimeValue");
		String paramEndTimeValue = beat.getRequest().getParameter("paramEndTimeValue")==null?"":beat.getRequest().getParameter("paramEndTimeValue");
		String paramfourValue = beat.getRequest().getParameter("paramfourValue")==null?"":beat.getRequest().getParameter("paramfourValue");
		String paramfiveValue = beat.getRequest().getParameter("paramfiveValue")==null?"":beat.getRequest().getParameter("paramfiveValue");
		String paramsexValue = beat.getRequest().getParameter("paramsexValue")==null?"":beat.getRequest().getParameter("paramsexValue");
		
		//根据pageno判定是否为点击查询还是点击下一页触发的事件
		//如果点击查询则把查询条件放到session中，否则则去session中取出上一次的查询条件
		if(pageno==0){
			//把请求数据放到session中供翻页使用
			beat.getRequest().getSession().setAttribute("paramOneValue", paramOneValue);
			beat.getRequest().getSession().setAttribute("paramOneType", paramOneType);
			beat.getRequest().getSession().setAttribute("paramTwoValue", paramTwoValue);
			beat.getRequest().getSession().setAttribute("paramTwoValue2", paramTwoValue2);
			beat.getRequest().getSession().setAttribute("paramThreeValue", paramThreeValue);
			beat.getRequest().getSession().setAttribute("paramStartTimeValue", paramStartTimeValue);
			beat.getRequest().getSession().setAttribute("paramEndTimeValue", paramEndTimeValue);
			beat.getRequest().getSession().setAttribute("paramfourValue", paramfourValue);
			beat.getRequest().getSession().setAttribute("paramfiveValue", paramfiveValue);
			beat.getRequest().getSession().setAttribute("paramsexValue", paramsexValue);
		}else{
			paramOneValue = queryOption.get("paramOneValue")==null?"":queryOption.get("paramOneValue");
			paramOneType = queryOption.get("paramOneType")==null?"":queryOption.get("paramOneType");
			paramTwoValue = queryOption.get("paramTwoValue")==null?"":queryOption.get("paramTwoValue");
			paramTwoValue2 = queryOption.get("paramTwoValue2")==null?"":queryOption.get("paramTwoValue2");
			paramThreeValue =  queryOption.get("paramThreeValue")==null?"":queryOption.put("paramThreeValue", paramThreeValue);
			paramStartTimeValue = queryOption.get("paramStartTimeValue")==null?"":queryOption.put("paramStartTimeValue", paramStartTimeValue);
			paramEndTimeValue = queryOption.get("paramEndTimeValue")==null?"":queryOption.put("paramEndTimeValue", paramEndTimeValue);
			paramfourValue = queryOption.get("paramfourValue")==null?"":queryOption.put("paramfourValue", paramfourValue);
			paramfiveValue = queryOption.get("paramfiveValue")==null?"":queryOption.put("paramfiveValue", paramfiveValue);
			paramsexValue = queryOption.get("paramsexValue")==null?"":queryOption.put("paramsexValue", paramsexValue);
		}
		
 
		String temp_pageindex = "1";
		if(pageno!=0){
			temp_pageindex = pageno.toString();	
		}
		
		//-------拼装母单参数块
		String condition = getCondition(orderAssemble,beat,paramOneType,paramOneValue,paramTwoValue,paramTwoValue2,paramThreeValue,paramStartTimeValue,paramEndTimeValue,paramfourValue,paramfiveValue,paramsexValue,orderType);
		
//		System.out.println("==========查询条件为:"+condition+"==========");
		//-------拼装母单参数块 
		
		
		 // beat.getRequest().getParameter("pageindex");
		if(temp_pageindex==null||temp_pageindex.equals("")) temp_pageindex="1";
		
		
		//分页属性
		Integer pageindex = Integer.valueOf(temp_pageindex);
		Integer pagesize = Constants.houtai_page_size;
		
		if("zxgwtobesigned".equals(orderType) ){
			pagesize = Constants.zixungenjin_page_size; //咨询顾问15条
		}
		
		
		String order = "updatetime desc"; //默认根据最后更新时间排序
		
		int ordercount = orderAssemble.getOrderEntityCount(condition); //查询母单的总条数
		int pagecount = ordercount%pagesize == 0?ordercount/pagesize:ordercount/pagesize+1;
		
		if("zxgwtobesigned".equals(orderType) || "haveBeenSent".equals(orderType)|| "twoorder".equals(orderType)){
			order = " piesingletime desc "; //派单时间
		}else if("zxgwovermoney".equals(orderType) ||  "fwinservice".equals(orderType) || "fwcompleteservice".equals(orderType) || "sysinservice".equals(orderType) || "syscompleteservice".equals(orderType) || "visit".equals(orderType)){
			order = " signtime desc "; //签单时间
		}
		//获得母订单列表信息
		List<SorderEntity> sorderEntity = orderAssemble.getOrderEntityList(condition, pageindex, pagesize, order);
		orderList = this.getOrderInfoList(sorderEntity);
		//登陆用户的角色
		String roleidFlag = "0";
		String roleids = UtilsHelper.getLoginRoleids(beat);
		if(StringUtils.isNotBlank(roleids)){
			String[] roleid = roleids.split(",");
			for(int i=0;i<roleid.length;i++){
				if(roleid[i].equals("1")){
					roleidFlag = "1";
					break;
				}
			}
		}
		
		//前台页面使用的参数START---------------
		beat.getModel().add("roleidFlag",roleidFlag);
		beat.getModel().add("pagecount", pagecount);
		beat.getModel().add("pageIndex", pageindex);
		beat.getModel().add("paramOneValue", paramOneValue);  
		beat.getModel().add("paramOneType", paramOneType);
		beat.getModel().add("paramTwoValue", paramTwoValue);
		beat.getModel().add("paramTwoValue2", paramTwoValue2);
		
		beat.getModel().add("paramStartTimeValue", paramStartTimeValue);
		beat.getModel().add("paramEndTimeValue", paramEndTimeValue);
		
		beat.getModel().add("paramthreeValue", paramThreeValue);
		beat.getModel().add("paramfourValue", paramfourValue);
		beat.getModel().add("paramfiveValue", paramfiveValue);
		beat.getModel().add("paramsexValue", paramsexValue);
		beat.getModel().add("orderList", orderList);
		
		
		
		
		
		//前台页面使用的参数END-----------------
		
	}
	
	/***
	 * 拼装orderVO对象的list
	 * @param list
	 * @return
	 */
	public List<OrderInfoVO> getOrderInfoList(List<SorderEntity> list){
		List<OrderInfoVO> orderInfoList =new ArrayList<OrderInfoVO>();
		if(null == list || list.size()==0) return orderInfoList;
		for(SorderEntity entity : list){
			OrderInfoVO orderInfoVO =new OrderInfoVO();
			orderInfoVO.setOrderId(entity.getOrderid());
			orderInfoVO.setSuperid(entity.getSuperid());
			orderInfoVO.setContract(entity.getContract());  //设置合同编号
			orderInfoVO.setOrderForm(KVMap.orderForm.get(String.valueOf(entity.getOrderform())).toString());  //设置订单来源
			orderInfoVO.setBusId(entity.getBusid());
			//ADDED..........添加服务开始和服务结束时间
			try{
				List<OrderprocessEntity> startlist = RSBLL.getstance().getOrderprocessService().getOrderprocessEntity(" orderid="+entity.getOrderid()+" and optcode='1102'", 1, 1, "poid");
				if(startlist!=null){
					if(startlist.size()>0){
						 String serviceStartTime = Timers.longToStr(startlist.get(0).getOptime());
						 orderInfoVO.setServiceStartTime(serviceStartTime);
					}
				}
				
				//完成时间
				List<OrderprocessEntity> endlist = RSBLL.getstance().getOrderprocessService().getOrderprocessEntity(" orderid="+entity.getOrderid()+" and optcode='1104'", 1, 1, "poid");
				if(endlist!=null){
					if(endlist.size()>0){
						 String serviceEndTime = Timers.longToStr(endlist.get(0).getOptime());
						 orderInfoVO.setServiceEndTime(serviceEndTime);
					}
				}
				
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//ADDED。。。。。。。查询回访状态
			orderInfoVO.setHuifangFlag("未回访");
			try {
				List<VisitEntity> vlist = RSBLL.getstance().getVisitService().getVisitEntity(" orderid="+entity.getOrderid(), 1, 1, "visitid");
			    if(vlist!=null){
				   if(vlist.size()>0){
					  long visitid =  vlist.get(0).getVisitid();
					  orderInfoVO.setHuifangFlag("已回访");
					  orderInfoVO.setVisitid(visitid);
				   }
			    }
			
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//ADDED。。。。。。。查询评价状态
			orderInfoVO.setPlFlag("未评价");
			try {
				List<CommentEntity> pllist = RSBLL.getstance().getCommentService().getCommentEntity(" orderid="+entity.getOrderid(), 1, 1, "commentid");
				if(pllist!=null){
					if(pllist.size()>0){
						orderInfoVO.setPlFlag("已评价");
					}
				}
			
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			//查询客户的信息表
			LoginEntity loginEntity = orderAssemble.getUserEntity(entity.getUserid());
			if(null != loginEntity){
				orderInfoVO.setCustomerId(loginEntity.getUserid());      //客户的ID
				orderInfoVO.setCustomerName(loginEntity.getUsername());  //设置客户姓名
				orderInfoVO.setCustomerPhone(loginEntity.getUserphone());  //设置客户手机号
				orderInfoVO.setAddress(loginEntity.getAddress());     //公司的地址
				orderInfoVO.setEmail(loginEntity.getEmail());         //邮箱
				orderInfoVO.setLandlinenumber(loginEntity.getLandlinenumber());  //其他电话 (座机)
				orderInfoVO.setCustomerForm(KVMap.channelMap.get(String.valueOf(loginEntity.getChannel())));  //设置客户来源
			}
			String mc = getGsmc4Business(entity.getBusid());
			orderInfoVO.setCompanyname(mc);
			//orderInfoVO.setCompanyname(entity.getCompanyname());  //设置公司名称
			orderInfoVO.setTotalmoney(entity.getTotalmoney());         //设置订单金额
			if(entity.getPrepaidamount() != 0){
				orderInfoVO.setPrepaidamount(entity.getPrepaidamount());;     //设置订单预付金额	
			}
			orderInfoVO.setPaymoney(entity.getPaymoneycount());   //设置应付金额
			
			//查询跟进次数
			String contition = "orderid = '"+orderInfoVO.getOrderId()+"'";
			List<FollowupEntity> followList  = orderAssemble.getFollowupList(contition);
			//添加跟进次数
			if(null !=followList && followList.size()>0){
				orderInfoVO.setFlowupSize(followList.size());	
				orderInfoVO.setIntent(KVMap.followmap.get(followList.get(0).getIntentcode())); //设置客户的意向度
				orderInfoVO.setBeizhuContent(followList.get(0).getContent());
				orderInfoVO.setBeizhuQuestion(followList.get(0).getTitle());
			}else{
				orderInfoVO.setFlowupSize(0);
			}
			
			
			//取出子订单的列表
			List<SorderChildrenEntity> sorderChildEntity = orderAssemble.getOrderChildrEntityList(contition);
			
			Map tempList =new LinkedMap();
			List<Map> clist =new ArrayList<Map>();
			for(SorderChildrenEntity centity : sorderChildEntity){
				Map<String,String> tempMap = new HashMap<String, String>();
				Map<String,String> cmap =new HashMap<String, String>();
				//得到商品类别id
				long cateid = centity.getProdcateid();
				ProductCategoryEntity category = orderAssemble.getCategoryEntity(cateid); // 得到商品类别表 t_product_category
			    if(null != category){
					cmap.put("categoryType", category.getCname());  //设置商品类别名称
					clist.add(cmap);	
			    }
				
				//获取子单的所有属性
				String propertid = centity.getPropertid();
			    try {
			    	com.bj58.sfft.json.orgjson.JSONArray jsonarray =new com.bj58.sfft.json.orgjson.JSONArray(propertid);
					for(int i=0;i<jsonarray.length();i++){
						com.bj58.sfft.json.orgjson.JSONObject jsonObject =jsonarray.getJSONObject(i);
						if(jsonObject.length()>0){
							String aa =jsonObject.getString("propertyid");
							String bb =jsonObject.getString("propertyval");
							if(aa.equals("city") || aa.equals("area")){
								AreasEntity areasE = orderAssemble.getAreasEntity(Long.valueOf(bb));
								if(areasE != null){
									tempMap.put(aa, areasE.getName()); 
								}
							}else if("companytype".equals(bb)){ 
								ProductPropertiesEntity propertiesE = orderAssemble.getPropertiesEntity(Long.valueOf(aa));
								tempMap.put(bb, propertiesE.getProname());
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				tempList.put(entity.getOrderid(), tempMap);
			}
			String c="";
			if(clist != null){
				for(Map m: clist){
					if(m.get("categoryType").toString().equals("其他")){
						String condition = "orderid='"+entity.getOrderid()+"'";
						List<SorderChildrenEntity> orderChildList = orderAssemble.getOrderChildrEntityList(condition);
						for(SorderChildrenEntity childE : orderChildList){
							Integer categoryId = childE.getProdcateid();  //得到商品类别ID
							ProductCategoryEntity pcategoryE = orderAssemble.getCategoryEntity(Long.valueOf(categoryId.toString()));
							String childorderName = pcategoryE.getCname();   //获得子单名称 如公司注册或代理地址
							String childorderPropertid = childE.getPropertid();   //获得子单属性 如 地区 内资外资等
					    	
							try {
								com.bj58.sfft.json.orgjson.JSONArray jsonarray =new com.bj58.sfft.json.orgjson.JSONArray(childorderPropertid);
								if(childorderName.equals("其他")){ 
									for(int i=0;i<jsonarray.length();i++){
										com.bj58.sfft.json.orgjson.JSONObject jsonO =jsonarray.getJSONObject(i); 
										String propertyid =jsonO.getString("propertyid");
										String propertyval =jsonO.getString("propertyval");
									  
										if(!propertyid.equals("qtprice")){
											if(isNum(propertyid)){
												ProductPropertiesEntity propertiesE = orderAssemble.getPropertiesEntity(Long.valueOf(propertyid));
												if("qitmc".equals(propertiesE.getType())){
													childorderName = propertyval +"(其他)";
												}else{
													childorderName = "其他";	
												}
											}
										}
									} 
								}
								m.put("categoryType", childorderName);   //商品类型
							} catch (Exception e) {
								System.err.println(e);
								e.printStackTrace();
							}
						}
					}
					c += m.get("categoryType").toString()+"<br/>";
				}
			}
			
			
			if(StringUtil.isEmpty(c)){
				orderInfoVO.setCommodiType(c.substring(0,c.lastIndexOf("<")));   //商品类型
			}
			
			if(tempList.size()>0){
				Map m =(Map)tempList.get(entity.getOrderid());
				
				if(m.containsKey("city")){
					orderInfoVO.setCityName(m.get("city").toString());  //注册城市	
				}
				if(m.containsKey("area")){
					orderInfoVO.setAreaName(m.get("area").toString());  //注册区域 如海淀 朝阳	
				}
				orderInfoVO.setCityId(String.valueOf(entity.getOrdercity()));
				//判断是否拥有内外资
				if(m.containsKey("companytype")){
					orderInfoVO.setCompanytype(m.get("companytype").toString()); //内资外资 默认是内资企业
				}
			}
			
			//订单签单且客服不为空 表示客服签单
			if(entity.getSignstate()==1 && entity.getKefuid()!=0 && entity.getEmpid()==0){
				EmployersEntity fwEntity = orderAssemble.getEmployersEntity(entity.getKefuid());
				orderInfoVO.setKefuname(fwEntity.getRealname());
			}else if(entity.getSignstate()==1 && entity.getKefuid()==0 && entity.getEmpid()==0){
				try {
					List<OrderprocessEntity> orderproceList = RSBLL.getstance().getOrderprocessService().getOrderprocessEntity(" orderid='"+entity.getOrderid()+"' and optcode='1143' ", 1, 1, "");
					if(null != orderproceList && orderproceList.size()>0){
						EmployersEntity fwEntity = orderAssemble.getEmployersEntity(orderproceList.get(0).getOpid());
						if(null != fwEntity){
							orderInfoVO.setKefuname(fwEntity.getRealname());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//客服
			if(entity.getKefuid() != 0){
//				orderInfoVO.setFawuId(String.valueOf(entity.getEmpid()));
				EmployersEntity fwEntity = orderAssemble.getEmployersEntity(entity.getKefuid());
				if(null != fwEntity)
					orderInfoVO.setKefuname(fwEntity.getRealname());  
			}
			//咨询顾问
			if(entity.getEmpid() != 0){
//				orderInfoVO.setFawuId(String.valueOf(entity.getEmpid()));
				EmployersEntity fwEntity = orderAssemble.getEmployersEntity(entity.getEmpid());
				if(null != fwEntity)
					orderInfoVO.setFawuName(fwEntity.getRealname());  
			}
			//得到服务顾问信息对象
			if(entity.getFwgwempid() != 0){
//				orderInfoVO.setFawuId(String.valueOf(entity.getEmpid()));
				EmployersEntity fwgwEntity = orderAssemble.getEmployersEntity(entity.getFwgwempid());
				if(null != fwgwEntity)
					orderInfoVO.setConsultantName("<br>"+fwgwEntity.getRealname());   //设置服务顾问
			}
			if(entity.getCurrentempid() !=0 && entity.getCurrentempid()!=entity.getEmpid()){
				EmployersEntity fzEntity = orderAssemble.getEmployersEntity(entity.getCurrentempid());
				if(null != fzEntity)
					orderInfoVO.setFzName("<br>"+fzEntity.getRealname());   //设置法律助理 
			}
			
			orderInfoVO.setDownOrderTime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", entity.getPostime()));    //下单时间h
			if(entity.getPiesingletime()!=0){
				orderInfoVO.setPiesingletime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", entity.getPiesingletime())); //派单时间
			}
			if(entity.getSigntime()!=0){
				orderInfoVO.setSigntime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", entity.getSigntime()));           //签单时间
			}
			orderInfoVO.setOrderState(KVMap.orderstatemap.get(String.valueOf(entity.getOrderstate())).toString());   //设置订单状态
			orderInfoVO.setSignstate(KVMap.orderSignstatemap.get(String.valueOf(entity.getSignstate())).toString());  //设置签单状态
			orderInfoVO.setPaystate(KVMap.orderPayState.get(String.valueOf(entity.getPaystate())).toString());   //设置支付状态
			orderInfoVO.setIsNewProcess(entity.getIsnewprocess());     //是否是新流程 duxf 
			
			//平台订单id和订单来源
			List<SorderExtEntity> sorderExtList = orderAssemble.getOrderExtEntityList(String.valueOf(entity.getOrderid()));
			if(sorderExtList != null){
				for(SorderExtEntity s:sorderExtList){
					if(s.getDataKey().equals("channel")){
						orderInfoVO.setChannel(KVMap.channelMap.get(String.valueOf(s.getDataValue())).toString());
					}else if(s.getDataKey().equals("terraceOrderId")){
						orderInfoVO.setTerraceOrderId(s.getDataValue());
					}
				}
			}
			
			orderInfoList.add(orderInfoVO);
		}
		
		
		return orderInfoList;
	}
	
	public String getGsmc4Business(long buid){
/*		String gsmc ="";
		try {
			BusinessLibaryEntity ble = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(buid);
			if(ble != null){
				String mc = ble.getCompanymc();
				if(StringUtil.isEmpty(mc)){
					gsmc = mc;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gsmc;
		*/
		try {
			return RSBLL.getstance().getEnterpriseService().getValueByEnterpriseIdAndKey(buid + "", "name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
	/***
	 * 得到订单的意向度list 
	 * @return
	 */
	public String getFolowList(String condition){
		List<FollowupEntity> list = orderAssemble.getFollowupList(condition);
		JSONArray  jsArray = new JSONArray();
		for(FollowupEntity fentity : list){
			JSONObject jsonObject = new JSONObject();
			Map<String,String> map = new HashMap<String, String>();
			long empid = fentity.getEmpid();
			EmployersEntity emp = orderAssemble.getEmployersEntity(empid);
			String empName = "系统";
			if(null!=emp){
				empName = emp.getRealname();  //得到记录人	
			}
			String intentvalue  = KVMap.followmap.get(fentity.getIntentcode()).toString() ; //得到意向度
			String addtime = UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", fentity.getAddtime()) ; //得到时间
			String content = fentity.getContent(); //得到内容
			String title = fentity.getTitle();  //得到问题
			map.put("empName", empName);
			map.put("intentvalue", intentvalue);
			map.put("addtime", addtime);
			map.put("content", content);
			map.put("title", title);
			jsArray.add(jsonObject.fromObject(map));
		}
//		System.out.println("=========返回的json========="+jsArray.toString());
		return jsArray.toString();
	}
	
	/**
	 * 获得子订单的详情信息 
	 */
	/*@Override
	public String getChildOrderList(String orderid) {
		String condition = "orderid='"+orderid+"'";
		List<SorderChildrenEntity> orderChildList = orderAssemble.getOrderChildrEntityList(condition);
		
		JSONArray jsonArray =new JSONArray();
		
		for(SorderChildrenEntity childE : orderChildList){
			JSONObject jsonObject = new JSONObject();
			OrderChildInfoVO orderChildVO = new OrderChildInfoVO();
			Integer categoryId = childE.getProdcateid();  //得到商品类别ID
			ProductCategoryEntity pcategoryE = orderAssemble.getCategoryEntity(Long.valueOf(categoryId.toString()));
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
							AreasEntity areasE = orderAssemble.getAreasEntity(Long.valueOf(propertyval));
							sbProperName.append(areasE.getName()).append(" ");
						}else if(!propertyid.equals("qitcontent") && !propertyid.equals("qtprice")){
							ProductPropertiesEntity propertiesE = orderAssemble.getPropertiesEntity(Long.valueOf(propertyid));
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
							AreasEntity areasE = orderAssemble.getAreasEntity(Long.valueOf(propertyval));
							sbProperName.append(areasE.getName()).append(" ");
						}else if(!propertyid.equals("qtprice")){
							ProductPropertiesEntity propertiesE = orderAssemble.getPropertiesEntity(Long.valueOf(propertyid));
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
			if("3".equals(childE.getCostate())){
				//得到操作人与时间
				EmployersEntity empE = orderAssemble.getEmployersEntity(childE.getEmpid());
				String optionName = empE.getRealname();
				String updatetime = UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", childE.getUpdatetime());
				 logInfo = optionName +" " +updatetime;	
			}
			
			orderChildVO.setCoid(childE.getCoid());
			orderChildVO.setOrderId(Long.valueOf(orderid));
			orderChildVO.setServerName(childorderName);
			orderChildVO.setServerContent(sbProperName.toString());
			orderChildVO.setState(KVMap.orderChildstatemap.get(String.valueOf(childE.getCostate())).toString());
			orderChildVO.setPrice(childPrice);
			orderChildVO.setLogInfo(logInfo);
			jsonArray.add(jsonObject.fromObject(orderChildVO));
		}
		return jsonArray.toString();
	}*/
	
	
	/****
	 * 根据条件拼接母单的查询参数
	 * @param orderAssemble
	 * @param beat 上下文
	 * @param paramOneType 参数1类型 
	 * @param paramOneValue 参数1值
	 * @param paramTwoValue 参数2值
	 * @param orderType 订单类型  一般就是说从哪个入口过来
	 * @return
	 */
	public static String getCondition(OrderAssembleService orderAssemble,BeatContext beat,String paramOneType,String paramOneValue,String paramTwoValue,
			String paramTwoValue2,String paramThreeValue,String paramStartTimeValue,String paramEndTimeValue,String paramfourValue, String paramfiveValue,String paramsexValue,String orderType){
		
		
		String condition ="";
		/***
		 * 第一个IF表示如果为公司名称的拼装
		 * 第二个IF表示如果为客户名称或为客户电话的拼装
		 * 第三个IF表示如果为合同编号的拼装
		 */
		if(null !=paramOneType && !"".equals(paramOneValue) && "1".equals(paramOneType)){
			String enterpriseIdList = "";
			if(null !=paramOneType && !"".equals(paramOneValue) && "1".equals(paramOneType)){
				List<String> mainValueList = null;
				try {
					mainValueList = RSBLL.getstance().getEnterpriseService().getMainValueListByLikeName(paramOneValue, "enterpriseId");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(mainValueList != null && !mainValueList.isEmpty() && mainValueList.size() < 200){
					enterpriseIdList = StringUtils.join(mainValueList, "','");
					condition =" busid in ('" + enterpriseIdList + "')";
				}
			}

		}else if(null !=paramOneType && !"".equals(paramOneValue) && ("2".equals(paramOneType) || "3".equals(paramOneType))){
			String userCondition="";
			if("2".equals(paramOneType)){
				userCondition = "userphone like '%"+paramOneValue+"%'";
			}else if("3".equals(paramOneType)){
				userCondition = "username like '%"+paramOneValue+"%'";
			}
			List<LoginEntity> userList = orderAssemble.getUserEntity(userCondition);
			String userid = "";
			if(userList.size()>0){
				for(LoginEntity e : userList){
					userid += e.getUserid()+",";
				}
				userid = userid.substring(0, userid.lastIndexOf(","));	
			}
			condition = " userid in ("+userid+")" ;
		}else if(null !=paramOneType && !"".equals(paramOneValue)  && "4".equals(paramOneType)){
			condition =" contract like '%"+paramOneValue+"%' ";
		}else if(null !=paramOneType && !"".equals(paramOneValue)  && ("5".equals(paramOneType) || "6".equals(paramOneType) || "7".equals(paramOneType))){ //按照咨询顾问/服务顾问/服务专员查询 duxf
			String empCondition = " realname like '%"+paramOneValue+"%' ";
			List<EmployersEntity> empList = orderAssemble.getEmployersList(empCondition);
			String empid = "";
			if(empList.size()>0){
				for(EmployersEntity e : empList){
					empid += e.getEmpid()+",";
				}
				empid = empid.substring(0, empid.lastIndexOf(","));	
			}
			//5咨询顾问 -- 6服务顾问   7服务专员
			if("5".equals(paramOneType)){
				condition = " empid in ("+empid+")" ;	
			}else if("6".equals(paramOneType)){
				condition = " fwgwempid in ("+empid+")" ;	
			}else if("7".equals(paramOneType)){
				condition = " currentempid in ("+empid+")" ;	
			}
		}else if(null !=paramOneType && !"".equals(paramOneValue)  && ("8".equals(paramOneType)||"9".equals(paramOneType))){
			if("8".equals(paramOneType)){
				condition = " orderid="+Long.parseLong(paramOneValue);	
			}else if("9".equals(paramOneType)){
				condition = " busid="+Long.parseLong(paramOneValue);	
			}
		}
		
		//订单来源1微信 2官网 3后台   
		if("".equals(paramTwoValue) || "0".equals(paramTwoValue)){
			if(condition.equals("")){
				if("upline".equals(orderType)){
					condition += " (orderform=1 or orderform=2)";
				}else if("downline".equals(orderType)){
					condition += " (orderform=3 or orderform=6)";
				}else if("hezuo58".equals(orderType)){
					condition += " orderform=4";
				}
			}else{
				if("upline".equals(orderType)){
					condition += " and (orderform=1 or orderform=2)";
				}else if("downline".equals(orderType)){
					condition += " and (orderform=3 or orderform=6)";
				}else if("hezuo58".equals(orderType)){
					condition += " and orderform=4";
				}	
			}
		}else if("1".equals(paramTwoValue)){
			if(condition.equals("")){
				condition += " orderform='"+paramTwoValue+"'";
			}else{
				condition += " and orderform='"+paramTwoValue+"'";	
			}
		}else if("2".equals(paramTwoValue)){
			if("".equals(condition)){
				condition += "  orderform='"+paramTwoValue+"'";
			}else{
				condition += " and orderform='"+paramTwoValue+"'";	
			}
		}else if("3".equals(paramTwoValue)){
			if("".equals(condition)){
				condition += " orderform='"+paramTwoValue+"'";
			}else{
				condition += " and orderform='"+paramTwoValue+"'";	
			}
		}else if("4".equals(paramTwoValue)){
			if("".equals(condition)){
				condition += " orderform='"+paramTwoValue+"'";
			}else{
				condition += " and orderform='"+paramTwoValue+"'";	
			}
		}
		
		
		//0未付 1 预付 2 完款
		if("".equals(paramTwoValue2)){
			//如果是财务访问则应该拼接订单状态
			if("forPayment".equals(orderType) || "zxgwforPayment".equals(orderType)){
				if(condition.equals("")){
					condition += " (paystate=0 or paystate =1)";
				}else{
					condition += " and (paystate=0 or paystate =1)";	
				}
			}else if("overMoney".equals(orderType) || "zxgwovermoney".equals(orderType)){
				if(condition.equals("")){
					condition += " paystate=2 ";
				}else{
					condition += " and paystate=2 ";	
				}
			}else if("forPaymentForTerrace".equals(orderType) || "zxgwforPaymentForTerrace".equals(orderType)){
				if(condition.equals("")){
					condition += " (paystate=3 or paystate =4)";
				}else{
					condition += " and (paystate=3 or paystate =4) ";	
				}
			}
		}else if("0".equals(paramTwoValue2)){
			//如果是财务访问则应该拼接订单状态
			//if("forPayment".equals(orderType) || "overMoney".equals(orderType) || "zxgwforPayment".equals(orderType) || "zxgwovermoney".equals(orderType)){
			if(condition.equals("")){
				condition += " paystate='"+paramTwoValue2+"'";
			}else{
				condition += " and paystate='"+paramTwoValue2+"'";	
			}
			//}
		}else if("1".equals(paramTwoValue2)){
			if("".equals(condition)){
				condition += "  paystate='"+paramTwoValue2+"'";
			}else{
				condition += " and paystate='"+paramTwoValue2+"'";	
			}
		}else if("2".equals(paramTwoValue2)){
			if("".equals(condition)){
				condition += " paystate='"+paramTwoValue2+"'";
			}else{
				condition += " and paystate='"+paramTwoValue2+"'";	
			}
		}else if("3".equals(paramTwoValue2)){
			if("".equals(condition)){
				condition += " paystate='"+paramTwoValue2+"'";
			}else{
				condition += " and paystate='"+paramTwoValue2+"'";	
			}
		}else if("4".equals(paramTwoValue2)){
			if("".equals(condition)){
				condition += " paystate='"+paramTwoValue2+"'";
			}else{
				condition += " and paystate='"+paramTwoValue2+"'";	
			}
		}
		
		
		/*else if("000".equals(paramTwoValue)){//未回访
			if("forPayment".equals(orderType) || "overMoney".equals(orderType) || "zxgwforPayment".equals(orderType) || "zxgwovermoney".equals(orderType)){
				if("".equals(condition)){
					condition += " paystate='"+paramTwoValue+"'";
				}else{
					condition += " and paystate='"+paramTwoValue+"'";	
				}
			}else{
				if("".equals(condition)){
					condition += " orderform='"+paramTwoValue+"'";
				}else{
					condition += " and orderform='"+paramTwoValue+"'";	
				}	
			}
		}else if("111".equals(paramTwoValue)){//已回访
			if("forPayment".equals(orderType) || "overMoney".equals(orderType) || "zxgwforPayment".equals(orderType) || "zxgwovermoney".equals(orderType)){
				if("".equals(condition)){
					condition += " paystate='"+paramTwoValue+"'";
				}else{
					condition += " and paystate='"+paramTwoValue+"'";	
				}
			}else{
				if("".equals(condition)){
					condition += " orderform='"+paramTwoValue+"'";
				}else{
					condition += " and orderform='"+paramTwoValue+"'";	
				}	
			}
		} */
		
		//为1时为派单时间 2 为签单
		if("1".equals(paramThreeValue)){
			if(!"".equals(paramStartTimeValue) && !"".equals(paramEndTimeValue)){
				long stime = Timers.getLongTimeForString("yyyy-MM-dd HH:mm:ss", paramStartTimeValue+" 00:00:00");
				long etime = Timers.getLongTimeForString("yyyy-MM-dd HH:mm:ss", paramEndTimeValue+" 24:00:00");
				if("".equals(condition)){
					condition += " piesingletime>='"+stime+"' and piesingletime<='"+etime+"' " ;
				}else{
					condition += " and piesingletime>='"+stime+"' and piesingletime<='"+etime+"' ";
				}
			}
		}else if("2".equals(paramThreeValue)){
			if(!"".equals(paramStartTimeValue) && !"".equals(paramEndTimeValue)){
				long stime = Timers.getLongTimeForString("yyyy-MM-dd HH:mm:ss", paramStartTimeValue+" 00:00:00");
				long etime = Timers.getLongTimeForString("yyyy-MM-dd HH:mm:ss", paramEndTimeValue+" 24:00:00");
				if("".equals(condition)){
					condition += " signtime>='"+stime+"' and signtime<='"+etime+"' " ;
				}else{
					condition += " and signtime>='"+stime+"' and signtime<='"+etime+"' " ;
				}
			}
		}
		
		//意向度
		if(!"".equals(paramfourValue)){
			if("".equals(condition)){
				condition += " intentcode ='"+paramfourValue+"' ";
			}else{
				condition += " and intentcode ='"+paramfourValue+"' ";	
			}	
		}
		
		
		
		//根据不同的列表传进来的订单类别区分查询订单的状态
		if("upline".equals(orderType)){
			if("".equals(condition)){
				condition += " orderstate =7 ";
			}else{
				condition += " and orderstate =7 ";	
			}	
		}else if("downline".equals(orderType)){
			if("".equals(condition)){
				condition += " orderstate =7 ";
			}else{
				condition += " and orderstate =7 ";	
			}	
			String roleids = UtilsHelper.getLoginRoleids(beat);
			if(StringUtils.isNotEmpty(roleids)){
				String[] roleid = roleids.split(",");
				boolean flag = true;
				for(int i=0;i<roleid.length;i++){
					if(roleid[i].equals("1")){
						flag = false;
					}
				}
				if(flag){
					condition += "and kefuid='"+UtilsHelper.getLoginId(beat)+"' ";
				}
			}
			
		}else if("hezuo58".equals(orderType)){
			if("".equals(condition)){
				condition += " orderstate =7 ";
			}else{
				condition += " and orderstate =7 ";	
			}	
		}else if("haveBeenSent".equals(orderType)){
			if("".equals(condition)){
				if("".equals(paramfiveValue)){
					condition += " ( orderstate ='3' or orderstate ='1' or orderstate ='4' or orderstate ='10' ) ";
				}else{
					condition += " orderstate='"+paramfiveValue+"' ";
				}
			}else{
				if("".equals(paramfiveValue)){
					condition += " and ( orderstate ='3' or orderstate ='1' or orderstate ='4' or orderstate ='10' ) ";
				}else{
					condition += " and orderstate='"+paramfiveValue+"' ";
				}
			}	
		}else if("kfcancelDD".equals(orderType)){
            //客服自己的id作为条件只查询自己的数据 订单取消状态
			if("".equals(condition)){
				condition += " orderstate =2    ";
			}else{
				condition += " and orderstate =2 ";
			}	
		}else if("zxgwtobesigned".equals(orderType)){
            //法务自己的id作为条件只查询自己的数据 待签单也就是已派单的状态
			if("".equals(condition)){
				if("".equals(paramfiveValue)){
					condition += " (orderstate ='3' or orderstate ='1' or orderstate ='4') and signstate='0'  and empid='"+UtilsHelper.getLoginId(beat)+"' ";	
				}else{
					condition += " orderstate='"+paramfiveValue+"' and signstate='0'  and empid='"+UtilsHelper.getLoginId(beat)+"' ";
				}
			}else{
				if("".equals(paramfiveValue)){
					condition += " and (orderstate ='3' or orderstate ='1' or orderstate ='4') and signstate='0'  and empid='"+UtilsHelper.getLoginId(beat)+"' ";
				}else{
					condition += " and orderstate='"+paramfiveValue+"' and signstate='0'  and empid='"+UtilsHelper.getLoginId(beat)+"' ";
				}
			}	
		}else if("fwinservice".equals(orderType)){
            //法务自己的id作为条件只查询自己的数据  [目前服务中订单
			if("".equals(condition)){
				if("".equals(paramsexValue)){
					condition += " orderstate =1   and fwgwempid='"+UtilsHelper.getLoginId(beat)+"' ";
				}else{
					condition += " signstate='"+paramsexValue+"' and orderstate =1   and fwgwempid='"+UtilsHelper.getLoginId(beat)+"' ";
				}
			}else{
				if("".equals(paramsexValue)){
					condition += " and orderstate =1  and fwgwempid='"+UtilsHelper.getLoginId(beat)+"' ";
				}else{
					condition += " and signstate='"+paramsexValue+"' and orderstate =1  and fwgwempid='"+UtilsHelper.getLoginId(beat)+"' ";
				}
			}	
		}else if("fzinservice".equals(orderType)){
            //法务助理自己的id作为条件只查询自己的数据[通过母单的当前负责人字段]   [目前服务中订单  
			if("".equals(condition)){
				condition += " orderstate =1   and currentempid='"+UtilsHelper.getLoginId(beat)+"' ";
			}else{
				condition += " and orderstate =1   and currentempid='"+UtilsHelper.getLoginId(beat)+"' ";
			}	
		}else if("fwcompleteservice".equals(orderType)){
			//服务完成状态
			if("".equals(condition)){
				if("".equals(paramsexValue)){
					condition += " (orderstate = 4 or orderstate = 10)   and fwgwempid='"+UtilsHelper.getLoginId(beat)+"' ";
				}else{
					condition += " signstate='"+paramsexValue+"' and (orderstate = 4 or orderstate = 10)   and fwgwempid='"+UtilsHelper.getLoginId(beat)+"' ";
				}
			}else{
				if("".equals(paramsexValue)){
					condition += " and (orderstate = 4 or orderstate = 10)  and fwgwempid='"+UtilsHelper.getLoginId(beat)+"' ";
				}else{
					condition += " and signstate='"+paramsexValue+"' and (orderstate = 4 or orderstate = 10)  and fwgwempid='"+UtilsHelper.getLoginId(beat)+"' ";
				}
			}
		}else if("twoorder".equals(orderType)){
			if("".equals(condition)){
					condition += " (orderstate =11) ";
			}else{
				condition += " and (orderstate =11) ";
			}	
		}
//		else if("fwend".equals(orderType)){
//			//法务服务完结
//			if("".equals(condition)){
//				condition += " orderstate =10   and empid='"+UtilsHelper.getLoginId(beat)+"' ";
//			}else{
//				condition += " and orderstate =10  and empid='"+UtilsHelper.getLoginId(beat)+"' ";
//			}
//		}
		else if("fwcancelDD".equals(orderType)){
			//法务取消订单
			if("".equals(condition)){
				condition += " orderstate =2   and empid='"+UtilsHelper.getLoginId(beat)+"' ";
			}else{
				condition += " and orderstate =2  and empid='"+UtilsHelper.getLoginId(beat)+"' ";
			}
		}else if("forPayment".equals(orderType)){
			//财务带催款订单 按是否签单查询
			if("".equals(condition)){
				condition += " signstate='1' and orderstate <> '2'  and ordercity = '"+LoginTool.getLoginUserInfo(beat.getRequest()).getCityid()+"' ";
				//condition += " signstate='1' and orderstate <> '2'  and ordercity = '1' ";
			}else{
				condition += " and signstate='1' and orderstate <> '2'  and ordercity = '"+LoginTool.getLoginUserInfo(beat.getRequest()).getCityid()+"' ";
				//condition += " and signstate='1' and orderstate <> '2'  and ordercity = '1' ";
			}
		}else if("forPaymentForTerrace".equals(orderType)){
			//财务带催款订单 按是否签单查询
			if("".equals(condition)){
				condition += " signstate='1' and orderstate <> '2'  and ordercity = '"+LoginTool.getLoginUserInfo(beat.getRequest()).getCityid()+"' ";
				//condition += " signstate='1' and orderstate <> '2'  and ordercity = '1' ";
			}else{
				condition += " and signstate='1' and orderstate <> '2'  and ordercity = '"+LoginTool.getLoginUserInfo(beat.getRequest()).getCityid()+"' ";
				//condition += " and signstate='1' and orderstate <> '2'  and ordercity = '1' ";
			}
		}else if("zxgwforPaymentForTerrace".equals(orderType)){
			//财务带催款订单 0未付 1预付 两个都为带催款状态 订单状态不能为取消的
			if("".equals(condition)){
				if("".equals(paramfiveValue)){
					condition += " signstate='1' and orderstate <> '2' and empid='"+UtilsHelper.getLoginId(beat)+"'   ";	
					//condition += " signstate='1' and orderstate <> '2'  ";	
				}else{
					condition += " signstate='1' and orderstate='"+paramfiveValue+"' and empid='"+UtilsHelper.getLoginId(beat)+"'  ";
					//condition += " signstate='1' and orderstate='"+paramfiveValue+"'  ";
				}
			}else{
				if("".equals(paramfiveValue)){
					condition += " and signstate='1' and orderstate <> '2' and empid='"+UtilsHelper.getLoginId(beat)+"'  ";
					//condition += " and signstate='1' and orderstate <> '2'  ";
				}else{
					condition += " and signstate='1' and orderstate='"+paramfiveValue+"' and empid='"+UtilsHelper.getLoginId(beat)+"'  ";
					//condition += " and signstate='1' and orderstate='"+paramfiveValue+"'  ";
				}
			}
		}else if("zxgwforPayment".equals(orderType)){
			//财务带催款订单 0未付 1预付 两个都为带催款状态 订单状态不能为取消的
			if("".equals(condition)){
				if("".equals(paramfiveValue)){
					condition += " signstate='1' and orderstate <> '2' and empid='"+UtilsHelper.getLoginId(beat)+"'   ";	
				}else{
					condition += " signstate='1' and orderstate='"+paramfiveValue+"' and empid='"+UtilsHelper.getLoginId(beat)+"'  ";
				}
			}else{
				if("".equals(paramfiveValue)){
					condition += " and signstate='1' and orderstate <> '2' and empid='"+UtilsHelper.getLoginId(beat)+"'  ";	
				}else{
					condition += " and signstate='1' and orderstate='"+paramfiveValue+"' and empid='"+UtilsHelper.getLoginId(beat)+"'  ";
				}
			}
		}else if("overMoney".equals(orderType)){
			if("".equals(condition)){
				condition += " signstate='1' and orderstate <> '2'  and ordercity = '"+LoginTool.getLoginUserInfo(beat.getRequest()).getCityid()+"' ";
			}else{
				//condition += " and signstate='1' and orderstate <> '2'  and ordercity = '"+LoginTool.getLoginUserInfo(beat.getRequest()).getCityid()+"' ";
				condition += " and signstate='1' and orderstate <> '2'  and ordercity = 1 ";
			}
		}else if("zxgwovermoney".equals(orderType)){
			if("".equals(condition)){
				condition += " signstate='1' and orderstate <> '2' and empid='"+UtilsHelper.getLoginId(beat)+"'   ";
			}else{
				condition += " and signstate='1' and orderstate <> '2' and empid='"+UtilsHelper.getLoginId(beat)+"'  ";
			}
		}else if("sysinservice".equals(orderType)){
            //管理员自己的id作为条件只查询自己的数据  [目前服务中订单
			if("".equals(condition)){
				if("".equals(paramsexValue)){
					condition += " orderstate =1    ";	
				}else{
					condition += " signstate='"+paramsexValue+"' and orderstate =1    ";
				}
			}else{
				if("".equals(paramsexValue)){
					condition += " and orderstate =1   ";
				}else{
					condition += " and signstate='"+paramsexValue+"' and orderstate =1   ";
				}
			}	
		}else if("syscompleteservice".equals(orderType)){
			//服务完成状态
			if("".equals(condition)){
				condition += " (orderstate = 4 or orderstate = 10)    ";
			}else{
				condition += " and (orderstate = 4 or orderstate = 10)  ";
			}
		}else if("visit".equals(orderType)){//added.........这是回访列表的list
			if("".equals(condition)){
				condition += " orderstate =10 ";
			}else{
				condition += " and orderstate =10 ";	
			}	
		}
		else if("monitorTask".equals(orderType)){     //added.........监控的查询条件
			if("".equals(condition)){
				condition += " signstate='1' and isnewprocess='1' and orderstate<>'2' and fwgwempid='"+UtilsHelper.getLoginId(beat)+"'";
			}else{
				condition += " and signstate='1' and isnewprocess='1' and orderstate<>'2' and fwgwempid='"+UtilsHelper.getLoginId(beat)+"'";	
			}	
		}
		
		return condition;
	}
	
	
	/*****
	 * 财务订单列表查询 [只有财务使用]
	 */
	@Override
	public void getOrderForFinanceList(BeatContext beat, String orderType,Map<String, String> queryOption, Integer pageno) {
		List<OrderInfoVO> orderList =new ArrayList<OrderInfoVO>();

		String paramOneValue = beat.getRequest().getParameter("paramOneValue")==null?"":beat.getRequest().getParameter("paramOneValue");
		String paramOneType =  beat.getRequest().getParameter("paramOneType")==null?"":beat.getRequest().getParameter("paramOneType");
		String paramTwoValue = beat.getRequest().getParameter("paramTwoValue")==null?"":beat.getRequest().getParameter("paramTwoValue");
		String paramTwoValue2 = beat.getRequest().getParameter("paramTwoValue2")==null?"":beat.getRequest().getParameter("paramTwoValue2");
		String paramThreeValue =  beat.getRequest().getParameter("paramThreeValue")==null?"":beat.getRequest().getParameter("paramThreeValue");
		String paramStartTimeValue = beat.getRequest().getParameter("paramStartTimeValue")==null?"":beat.getRequest().getParameter("paramStartTimeValue");
		String paramEndTimeValue = beat.getRequest().getParameter("paramEndTimeValue")==null?"":beat.getRequest().getParameter("paramEndTimeValue");
		String paramfourValue = beat.getRequest().getParameter("paramfourValue")==null?"":beat.getRequest().getParameter("paramfourValue");
		String paramfiveValue = beat.getRequest().getParameter("paramfiveValue")==null?"":beat.getRequest().getParameter("paramfiveValue");
		String paramsexValue = beat.getRequest().getParameter("paramsexValue")==null?"":beat.getRequest().getParameter("paramsexValue");
		
		//根据pageno判定是否为点击查询还是点击下一页触发的事件
		//如果点击查询则把查询条件放到session中，否则则去session中取出上一次的查询条件
		if(pageno==0){
			//把请求数据放到session中供翻页使用
			beat.getRequest().getSession().setAttribute("paramOneValue", paramOneValue);
			beat.getRequest().getSession().setAttribute("paramOneType", paramOneType);
			beat.getRequest().getSession().setAttribute("paramTwoValue", paramTwoValue);
			beat.getRequest().getSession().setAttribute("paramTwoValue2", paramTwoValue2);
			beat.getRequest().getSession().setAttribute("paramThreeValue", paramThreeValue);
			beat.getRequest().getSession().setAttribute("paramStartTimeValue", paramStartTimeValue);
			beat.getRequest().getSession().setAttribute("paramEndTimeValue", paramEndTimeValue);
			beat.getRequest().getSession().setAttribute("paramfourValue", paramfourValue);
			beat.getRequest().getSession().setAttribute("paramfiveValue", paramfiveValue);
			beat.getRequest().getSession().setAttribute("paramsexValue", paramsexValue);
		}else{
			paramOneValue = queryOption.get("paramOneValue")==null?"":queryOption.get("paramOneValue");
			paramOneType = queryOption.get("paramOneType")==null?"":queryOption.get("paramOneType");
			paramTwoValue = queryOption.get("paramTwoValue")==null?"":queryOption.get("paramTwoValue");
			paramTwoValue2 = queryOption.get("paramTwoValue2")==null?"":queryOption.get("paramTwoValue2");
			paramThreeValue =  queryOption.get("paramThreeValue")==null?"":queryOption.put("paramThreeValue", paramThreeValue);
			paramStartTimeValue = queryOption.get("paramStartTimeValue")==null?"":queryOption.put("paramStartTimeValue", paramStartTimeValue);
			paramEndTimeValue = queryOption.get("paramEndTimeValue")==null?"":queryOption.put("paramEndTimeValue", paramEndTimeValue);
			paramfourValue = queryOption.get("paramfourValue")==null?"":queryOption.put("paramfourValue", paramfourValue);
			paramfiveValue = queryOption.get("paramfiveValue")==null?"":queryOption.put("paramfiveValue", paramfiveValue);
			paramsexValue = queryOption.get("paramsexValue")==null?"":queryOption.put("paramsexValue", paramsexValue);
		}
		
 
		String temp_pageindex = "1";
		if(pageno!=0){
			temp_pageindex = pageno.toString();	
		}
		

		
		//-------拼装母单参数块
		String condition = getCondition(orderAssemble,beat,paramOneType,paramOneValue,paramTwoValue,paramTwoValue2,paramThreeValue,paramStartTimeValue,paramEndTimeValue,paramfourValue,paramfiveValue,paramsexValue,orderType);
		//-------拼装母单参数块 
		
		
		 // beat.getRequest().getParameter("pageindex");
		if(temp_pageindex==null||temp_pageindex.equals("")) temp_pageindex="1";
		
		//分页属性
		Integer pageindex = Integer.valueOf(temp_pageindex);
		Integer pagesize =Constants.houtai_page_size;//10 
		String order = "updatetime desc"; //默认根据最后更新时间排序
		
		int ordercount = orderAssemble.getOrderEntityCount(condition); //查询母单的总条数
		int pagecount = ordercount%pagesize == 0?ordercount/pagesize:ordercount/pagesize+1;
		
		if("zxgwforPayment".equals(orderType) || "forPayment".equals(orderType) || "forPaymentForTerrace".equals(orderType) || "zxgwforPaymentForTerrace".equals(orderType)){
			order = " signtime desc ";
		}
		//获得母订单列表信息
		List<SorderEntity> sorderEntity = orderAssemble.getOrderEntityList(condition, pageindex, pagesize, order);
		
		//财务获取订单列表
		orderList = this.getOrderForFinance(sorderEntity);
		
		//前台页面使用的参数START---------------
		beat.getModel().add("pagecount", pagecount);
		beat.getModel().add("pageIndex", pageindex);
		beat.getModel().add("paramOneValue", paramOneValue);  
		beat.getModel().add("paramOneType", paramOneType);
		beat.getModel().add("paramTwoValue", paramTwoValue);
		beat.getModel().add("paramTwoValue2", paramTwoValue2);
		
		beat.getModel().add("paramStartTimeValue", paramStartTimeValue);
		beat.getModel().add("paramEndTimeValue", paramEndTimeValue);
		
		beat.getModel().add("paramthreeValue", paramThreeValue);
		beat.getModel().add("paramfourValue", paramfourValue);
		beat.getModel().add("paramfiveValue", paramfiveValue);
		beat.getModel().add("orderList", orderList);
		
	}
	
	/*****
	 * 财务查询子订单订单列表 [直供财务使用]
	 * @param sorderEntity
	 * @return
	 */
	public List<OrderInfoVO> getOrderForFinance(List<SorderEntity> list){
		List<OrderInfoVO> orderInfoList =new ArrayList<OrderInfoVO>();
		if(null == list || list.size()==0) return orderInfoList;
		for(SorderEntity entity : list){
			
			OrderInfoVO orderInfoVO =new OrderInfoVO();
			orderInfoVO.setOrderId(entity.getOrderid());
			orderInfoVO.setSuperid(entity.getSuperid());
			orderInfoVO.setContract(entity.getContract());  //设置合同编号
			orderInfoVO.setBusId(entity.getBusid());
			orderInfoVO.setOrderForm(KVMap.orderForm.get(String.valueOf(entity.getOrderform())).toString());  //设置订单来源
			//查询客户的信息表
			LoginEntity loginEntity = orderAssemble.getUserEntity(entity.getUserid());
			if(null != loginEntity){
				orderInfoVO.setCustomerId(loginEntity.getUserid());      //客户的ID
				orderInfoVO.setCustomerName(loginEntity.getUsername());  //设置客户姓名
				orderInfoVO.setCustomerPhone(loginEntity.getUserphone());  //设置客户手机号
				orderInfoVO.setAddress(loginEntity.getAddress());     //公司的地址
				orderInfoVO.setEmail(loginEntity.getEmail());         //邮箱
				orderInfoVO.setLandlinenumber(loginEntity.getLandlinenumber());  //其他电话 (座机)
				orderInfoVO.setCustomerForm(KVMap.channelMap.get(String.valueOf(loginEntity.getChannel())));  //设置客户来源
			}
			String mc = getGsmc4Business(entity.getBusid());
			orderInfoVO.setCompanyname(mc);
			//orderInfoVO.setCompanyname(entity.getCompanyname());  //设置公司名称
			orderInfoVO.setTotalmoney(entity.getTotalmoney());         //设置订单金额
			orderInfoVO.setPaymoney(entity.getPaymoneycount());         //设置应付款金额
			if(entity.getPrepaidamount() != 0){
				orderInfoVO.setPrepaidamount(entity.getPrepaidamount());;     //设置订单预付金额	
			}
			
			orderInfoVO.setPaystate(KVMap.orderPayState.get(String.valueOf(entity.getPaystate())).toString());   //设置支付状态
			
			//获取支付方式 按时间倒叙查询
			List<PayProcessEntity> payprocesList = orderAssemble.getPayprocess("orderid='"+entity.getOrderid()+"'");
			if(null != payprocesList && payprocesList.size()>0){
				float totalspend = 0;
				for(PayProcessEntity payproceE : payprocesList){
					if(payproceE.getOptype() == Constants.ORDER_ZC_ACTION_TYPE){
						totalspend = totalspend + payproceE.getPaytotal();
					}
					if(payproceE.getOptype() == 1 && KVMap.payType.get(String.valueOf(payproceE.getPaychannel())) != null){
						orderInfoVO.setPaychannel(KVMap.payType.get(String.valueOf(payproceE.getPaychannel())).toString());
					}
					
				}
				orderInfoVO.setTotalzc(totalspend);
				
			}
			ProductEntity productE = orderAssemble.getProductEntity(entity.getProductid());
//			orderInfoVO.setServerName(productE.getPname()); //得到母单大类的名称
			
			String contition = "orderid = '"+orderInfoVO.getOrderId()+"'";
  
			//取出子订单的列表
			List<SorderChildrenEntity> sorderChildEntity = orderAssemble.getOrderChildrEntityList(contition);
			
			Map tempList =new LinkedMap();
			List<Map> clist =new ArrayList<Map>();
			for(SorderChildrenEntity centity : sorderChildEntity){
				Map<String,String> tempMap = new HashMap<String, String>();
				Map<String,String> cmap =new HashMap<String, String>();
				//得到商品类别id
				long cateid = centity.getProdcateid();
				ProductCategoryEntity category = orderAssemble.getCategoryEntity(cateid); // 得到商品类别表 t_product_category
				if(category != null){
					cmap.put("categoryType", category.getCname());  //设置商品类别名称
				}else{
					System.out.println("contition = " + contition);
					System.out.println("cateid = " + cateid);
				}
				clist.add(cmap);
 
				
				tempList.put(entity.getOrderid(), tempMap);
			}
			String c="";
			for(Map m: clist){
				
				c += (m.containsKey("categoryType") && m.get("categoryType") != null? m.get("categoryType").toString():"")+",";
			}
			
			if(StringUtil.isEmpty(c)){
				orderInfoVO.setCommodiType(c.substring(0,c.lastIndexOf(",")));   //商品类型	
			}
			
			//订单签单且客服不为空 表示客服签单
			if(entity.getSignstate()==1 && entity.getKefuid()!=0 && entity.getEmpid()==0){
				EmployersEntity fwEntity = orderAssemble.getEmployersEntity(entity.getKefuid());
				orderInfoVO.setKefuname(fwEntity.getRealname());
			}else if(entity.getSignstate()==1 && entity.getKefuid()==0 && entity.getEmpid()==0){
				try {
					List<OrderprocessEntity> orderproceList = RSBLL.getstance().getOrderprocessService().getOrderprocessEntity(" orderid='"+entity.getOrderid()+"' and optcode='1143' ", 1, 1, "");
					if(null != orderproceList && orderproceList.size()>0){
						EmployersEntity fwEntity = orderAssemble.getEmployersEntity(orderproceList.get(0).getOpid());
						orderInfoVO.setKefuname(fwEntity.getRealname());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//咨询顾问
			if(entity.getEmpid() != 0){
//				orderInfoVO.setFawuId(String.valueOf(entity.getEmpid()));
				EmployersEntity fwEntity = orderAssemble.getEmployersEntity(entity.getEmpid());
				if(null != fwEntity)
					orderInfoVO.setFawuName(fwEntity.getRealname());  
			}
			//得到服务顾问信息对象
			if(entity.getFwgwempid() != 0){
//				orderInfoVO.setFawuId(String.valueOf(entity.getEmpid()));
				EmployersEntity fwgwEntity = orderAssemble.getEmployersEntity(entity.getFwgwempid());
				if(null != fwgwEntity)
					orderInfoVO.setConsultantName("<br>"+fwgwEntity.getRealname());   //设置服务顾问
			}
			if(entity.getCurrentempid() !=0 && entity.getCurrentempid()!=entity.getEmpid()){
				EmployersEntity fzEntity = orderAssemble.getEmployersEntity(entity.getCurrentempid());
				if(null != fzEntity)
					orderInfoVO.setFzName("<br>"+fzEntity.getRealname());   //设置法律助理 
			}
			
			orderInfoVO.setDownOrderTime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", entity.getPostime()));    //下单时间h
			if(entity.getPiesingletime()!=0){
				orderInfoVO.setPiesingletime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", entity.getPiesingletime())); //派单时间
			}
			if(entity.getSigntime()!=0){
				orderInfoVO.setSigntime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", entity.getSigntime()));           //签单时间
			}
			orderInfoVO.setOrderState(KVMap.orderstatemap.get(String.valueOf(entity.getOrderstate())).toString());   //设置订单状态
			orderInfoVO.setSignstate(KVMap.orderSignstatemap.get(String.valueOf(entity.getSignstate())).toString());  //设置签单状态
			orderInfoVO.setIsNewProcess(entity.getIsnewprocess());     //是否是新流程 duxf
			//平台订单id和订单来源
			List<SorderExtEntity> sorderExtList = orderAssemble.getOrderExtEntityList(String.valueOf(entity.getOrderid()));
			if(sorderExtList != null){
				for(SorderExtEntity s:sorderExtList){
					if(s.getDataKey().equals("channel")){
						orderInfoVO.setChannel(KVMap.channelMap.get(String.valueOf(s.getDataValue())).toString());
					}else if(s.getDataKey().equals("terraceOrderId")){
						orderInfoVO.setTerraceOrderId(s.getDataValue());
					}
				}
			}
			orderInfoList.add(orderInfoVO);
		}
		
		
		return orderInfoList;
	} 
	


	/****
	 * 查询财务的日志列表
	 */
	@Override
	public String getPayProcessList(String orderid) {
		
		String condition = "orderid='"+orderid+"'";
		List<PayProcessEntity> payprocessList = orderAssemble.getPayprocess(condition);
		
		JSONArray jsonArray =new JSONArray();
		
		for(PayProcessEntity payprocessE : payprocessList){
			JSONObject jsonObject = new JSONObject();
			Map<String, String> map = new HashMap<String, String>();
			if(payprocessE.getOptype() == 2){
				map.put("optime", UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", payprocessE.getOptime()));  //日志事件
				EmployersEntity emp =orderAssemble.getEmployersEntity(payprocessE.getOpempid());
				String title =emp.getRealname()+" 发起催款一次!";
			    map.put("title", title);
			    map.put("contents", "");  //备注
			}else if(payprocessE.getOptype() == 3){   //修改金额显示的内容
				map.put("optime", UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", payprocessE.getOptime()));  //日志事件
				EmployersEntity emp =orderAssemble.getEmployersEntity(payprocessE.getOpempid());
				String title =emp.getRealname()+" 将应付金额原价:"+payprocessE.getPaynumber()+"改为"+payprocessE.getPaytotal()+"原因:";
			    map.put("title", title);
			    map.put("contents", payprocessE.getContents());  //备注
			} else if(payprocessE.getOptype() == 4){//支出
				map.put("optime", UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", payprocessE.getOptime()));
				EmployersEntity emp =orderAssemble.getEmployersEntity(payprocessE.getOpempid());
				long zc = 0L;					
				String zcid = payprocessE.getContents();
				Pattern pattern = Pattern.compile("[0-9]*");  
			    if(pattern.matcher(zcid).matches()){
			    	zc = Long.parseLong(zcid);
			    }
				SpendEntity sse = null;
				if(zc > 0){
					try {
						sse = RSBLL.getstance().getSpendRecordService().loadSpendEntityByid(zc);
						if(null != sse){
							int st = (int) sse.getSpendtype();
							String spstr = "";
							switch(st){
								case 1:
									spstr = "国税官费";
									break;
								case 2:
									spstr = "地税官费";
									break;
								case 3:
									spstr = "社保官费";
									break;
								case 4:
									spstr = "公积金官费";
									break;
								case 5:
									spstr = "印花税";
									break;
								case 6:
									spstr = "地址费";
									break;
								case 7:
									spstr = "刻章";
									break;
								case 8:
									spstr = "罚款";
									break;
								case 9:
									spstr = "个人所得税";
									break;
								case 10:
									spstr = "知识产权";
									break;
								case 11:
									spstr = "退款";
									break;
								case 13:
									spstr = "国税CA";
									break;
								default:
									spstr = "其他";
								}
								String title = "";
								if(emp == null){
									title = " 支出"+spstr+sse.getSpendmoney()+"元";
								}else{
									title = emp.getRealname()+" 支出"+spstr+sse.getSpendmoney()+"元";
								}
								 
								map.put("title", title);
								map.put("contents", sse.getContent()==null?"":sse.getContent());
							
							}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
						String spstr = "地址费";
						
						String title = "";
						if(emp == null){
							title = " 支出"+spstr+payprocessE.getPaytotal()+"元";
						}else{
							title = emp.getRealname()+" 支出"+spstr+payprocessE.getPaytotal()+"元";
						}
						 
						map.put("title", title);
						map.put("contents", payprocessE.getContents()==null?"":payprocessE.getContents());
				}
			}else{
				//支付记录展示需改进
				if(payprocessE.getPaychannel() == 4){
					map.put("optime", UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", payprocessE.getOptime()));
					String title = " 客户通过微信支付了  "+payprocessE.getPaytotal()+"元";
					map.put("title", title);
					map.put("contents", "");
				}else if(payprocessE.getPaychannel() == 5){
					map.put("optime", UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", payprocessE.getOptime()));
					String title = " 客户通过支付宝支付了  "+payprocessE.getPaytotal()+"元";
					map.put("title", title);
					map.put("contents", "");
				//确认支出地址费用
				}else if(payprocessE.getPaychannel() == 6){
					map.put("optime", UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", payprocessE.getOptime()));
					EmployersEntity emp =orderAssemble.getEmployersEntity(payprocessE.getOpempid());
					if(emp == null){
						emp = new EmployersEntity();
					}
					String title = " "+emp.getRealname()+" 确认支出地址费用 :"+payprocessE.getPaytotal()+"元, 地址明细：";
					map.put("title", title);
					map.put("contents", payprocessE.getContents()==null?"":payprocessE.getContents());
				}else if(payprocessE.getPaychannel() == 7){
					map.put("optime", UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", payprocessE.getOptime()));  //日志事件
					String paychannel="现金";
					if(payprocessE.getPaychannel()!=0){
						paychannel = KVMap.payType.get(String.valueOf(payprocessE.getPaychannel())).toString();	
					} 
					EmployersEntity emp =orderAssemble.getEmployersEntity(payprocessE.getOpempid());
					String title =emp.getRealname()+"  确认结算："+payprocessE.getPaytotal()+"元,结算方式："+paychannel;
				    map.put("title", title);
				    map.put("contents", payprocessE.getContents()==null?"无":payprocessE.getContents());  //备注
				}else{
					map.put("optime", UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", payprocessE.getOptime()));  //日志事件
					String paychannel="现金";
					if(payprocessE.getPaychannel()!=0){
						paychannel = KVMap.payType.get(String.valueOf(payprocessE.getPaychannel())).toString();	
					} 
					EmployersEntity emp =orderAssemble.getEmployersEntity(payprocessE.getOpempid());
					String title =emp.getRealname()+"  确认收款："+payprocessE.getPaytotal()+"元, 其中实收："+payprocessE.getPaynumber()+"元, 手续费："+payprocessE.getPayfee()+"元,收款方式："+paychannel;
				    map.put("title", title);
				    map.put("contents", payprocessE.getContents()==null?"":payprocessE.getContents());  //备注
				}
			}
			
			jsonArray.add(jsonObject.fromObject(map));
		}
		return jsonArray.toString();
	}

	/****
	 * 通过母单ID得到子单的信息 供签单页面使用
	 */
	@Override
	public List<OrderChildInfoVO> getChildOrdersList(String orderid) {
		List<OrderChildInfoVO> orderChildlistvo= new ArrayList<OrderChildInfoVO>();
		
		String condition = "orderid='"+orderid+"'";
		List<SorderChildrenEntity> orderChildList = orderAssemble.getOrderChildrEntityList(condition);
		
		
		for(SorderChildrenEntity childE : orderChildList){
			OrderChildInfoVO orderChildVO = new OrderChildInfoVO();
			Integer categoryId = childE.getProdcateid();  //得到商品类别ID
			ProductCategoryEntity pcategoryE = orderAssemble.getCategoryEntity(Long.valueOf(categoryId.toString()));
			long productId = pcategoryE.getProductid();
			ProductEntity productE = orderAssemble.getProductEntity(productId);
			String bigClass = productE.getPname();   //获得大类的名称 如公司注册或财会服务
			String twoClass = pcategoryE.getCname();   //获得大类下小类的服务如 公司注册下公司注册或代理地址
			StringBuffer sb =new StringBuffer();
		    try {
		    	String propertid = childE.getPropertid();
		    	com.bj58.sfft.json.orgjson.JSONArray jsonarray =new com.bj58.sfft.json.orgjson.JSONArray(propertid);
		    	if(!twoClass.equals("其他")){
					for(int i=0;i<jsonarray.length();i++){
						com.bj58.sfft.json.orgjson.JSONObject jsonO =jsonarray.getJSONObject(i); 
						String propertyid =jsonO.getString("propertyid");
						String propertyval =jsonO.getString("propertyval");
						//如果为城市区域则去areas表取，如果是其他则直接拿出来值否则需要排除不是qitcontent和qtprice两个作为ID的去查询productproperties表
						if(propertyid.equals("city") || propertyid.equals("area")){
							AreasEntity areasE = orderAssemble.getAreasEntity(Long.valueOf(propertyval));
							sb.append(areasE.getName()).append(" ");
						}else if(!propertyid.equals("qtprice")){
							ProductPropertiesEntity propertiesE = orderAssemble.getPropertiesEntity(Long.valueOf(propertyid));
							sb.append(propertiesE.getProname()).append(" ");
						}
					}
				}else{
					for(int i=0;i<jsonarray.length();i++){
						com.bj58.sfft.json.orgjson.JSONObject jsonO =jsonarray.getJSONObject(i); 
						String propertyid =jsonO.getString("propertyid");
						String propertyval =jsonO.getString("propertyval");
						//如果为城市区域则去areas表取，如果是其他则直接拿出来值否则需要排除不是qitcontent和qtprice两个作为ID的去查询productproperties表
						if(propertyid.equals("city") || propertyid.equals("area")){
							AreasEntity areasE = orderAssemble.getAreasEntity(Long.valueOf(propertyval));
							sb.append(areasE.getName()).append(" ");
						}else if(!propertyid.equals("qtprice")){
							ProductPropertiesEntity propertiesE = orderAssemble.getPropertiesEntity(Long.valueOf(propertyid));
							if("qitmc".equals(propertiesE.getType())){
								twoClass = propertyval;
							}else if("qitcontent".equals(propertiesE.getType())){
								sb.append(propertyval).append(" ");
							}
//							else{
//								twoClass = propertiesE.getProname();
//							}
						}
					}
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
			float childPrice = childE.getMinprice();       //取得子单的成交价格
			orderChildVO.setCoid(childE.getCoid());
			orderChildVO.setOrderId(Long.valueOf(orderid));
			orderChildVO.setServerName(bigClass);
			orderChildVO.setServerContent(twoClass);
			orderChildVO.setState(KVMap.orderChildstatemap.get(String.valueOf(childE.getCostate())).toString());
			orderChildVO.setPrice(childPrice);
			orderChildVO.setProperties(sb.toString());
			orderChildlistvo.add(orderChildVO);
		}
		
		return orderChildlistvo;
	}


	/****
	 * 获得需要展示的标准化的节点内容 目前只有公司注册与代理记帐
	 */
	/*@Override
	public String getChildOrderActions(long proid) {
		List<ActionsEntity> actionElist = orderAssemble.getActionsEntityList(proid);
		JSONArray jsonarray = new JSONArray();
		for(ActionsEntity actionE : actionElist){
			JSONObject jsonObject =new JSONObject();
			Map m = new HashMap();
			m.put("actionid", actionE.getActionid());
			m.put("proid", actionE.getProid());
			m.put("actionname", actionE.getActionname());
			m.put("actiondesc", actionE.getActiondesc());
			
			jsonObject.fromObject(m);
			jsonarray.add(jsonObject);
		}
		
		return jsonarray.toString();
		
	}*/


	public static void main(String[] args) {
		OrderServiceImpl aa =new OrderServiceImpl();
		
//		for(OrderChildInfoVO vo : aa.getChildOrdersList("31211752518401")){
//			System.out.println("==============="+vo.getServerName());
//			System.out.println("==============="+vo.getServerContent());
//			System.out.println("==============="+vo.getProperties());
//			System.out.println("==============="+vo.getPrice());
//		}
	}

	/*****
	 * 返回详情信息中子单的集合信息(详情通用)
	 */
	@Override
	public List<OrderChildInfoVO> getOrderChildList(String orderid) {
		List<OrderChildInfoVO> orderVoList =new ArrayList<OrderChildInfoVO>();
		String condition = "orderid='"+orderid+"'";
		List<SorderChildrenEntity> orderChildList = orderAssemble.getOrderChildrEntityList(condition);
		for(SorderChildrenEntity childE : orderChildList){
			OrderChildInfoVO orderChildVO = new OrderChildInfoVO();
			Integer categoryId = childE.getProdcateid();  //得到商品类别ID
			ProductCategoryEntity pcategoryE = orderAssemble.getCategoryEntity(Long.valueOf(categoryId.toString()));
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
							AreasEntity areasE = orderAssemble.getAreasEntity(Long.valueOf(propertyval));
							sbProperName.append(areasE.getName()).append(" ");
						}else if(!propertyid.equals("qitcontent") && !propertyid.equals("qtprice")){
							ProductPropertiesEntity propertiesE = orderAssemble.getPropertiesEntity(Long.valueOf(propertyid));
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
							AreasEntity areasE = orderAssemble.getAreasEntity(Long.valueOf(propertyval));
							sbProperName.append(areasE.getName()).append(" ");
						}else if(!propertyid.equals("qtprice")){
							ProductPropertiesEntity propertiesE = orderAssemble.getPropertiesEntity(Long.valueOf(propertyid));
							if("qitmc".equals(propertiesE.getType())){
								childorderName = propertyval +"(其他)";
							}else if("qitcontent".equals(propertiesE.getType())){
								sbProperName.append(propertyval).append(" ");
							}else{
								childorderName = "其他";	
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
				EmployersEntity empE = orderAssemble.getEmployersEntity(childE.getEmpid());
				String optionName = "";
				if(null != empE){
					optionName = empE.getRealname();
				}
				String updatetime = UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", childE.getUpdatetime());
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
	
	 private static boolean isNum(String num){
        String reg = "^[0-9]*$";
        return Pattern.compile(reg).matcher(num).find();
     }
}
 