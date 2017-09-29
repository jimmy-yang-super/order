package com.jixiang.argo.lvzheng.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.frame.service.OrderAssembleService;
import com.jixiang.argo.lvzheng.service.wd.QueryDataService;
import com.jixiang.argo.lvzheng.utils.DicUtils;
import com.jixiang.argo.lvzheng.utils.EnterpriseUtils;
import com.jixiang.argo.lvzheng.utils.MapUtils;
import com.jixiang.argo.lvzheng.vo.ProcessMonitorDetailVO;
import com.jixiang.argo.lvzheng.vo.SorderVO;
import com.jx.argo.ActionResult;
import com.jx.argo.Model;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.enterprise.contract.ILvEnterpriseService;
import com.jx.service.enterprise.entity.LvEnterpriseBusinessEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.union.entity.EmployEntity;
import com.jx.service.workflow.entity.LvProcInstEntitiy;
import com.jx.service.workflow.entity.LvTaskEntity;

@Path("/webdata")
public class QueryDataCommonController extends AbstractController{
	@Path("/query")
	public ActionResult QueryData(){
		System.out.println("**********进入查询**********");
		String query  = beat().getRequest().getParameter("query")==null?"":beat().getRequest().getParameter("query");
		String queryType = beat().getRequest().getParameter("queryType")==null?"":beat().getRequest().getParameter("queryType");
		System.out.println(query+"==========="+queryType);
		if(StringUtils.isNotBlank(query) && StringUtils.isNotBlank(queryType)){
			QueryDataService.getInstance().queryData(beat(), queryType, query);
		}
		beat().getModel().add("query", query);
		beat().getModel().add("queryType", queryType);
		return view("/wd/dataMain");
	}
	
	@Path("/index")
	public ActionResult myIndex(){
		return view("/wd/dataMain");
	}
	
	
	@Path("/openEnterprisePeopleList/{enterpriseId:\\S+}")
	public ActionResult openEnterprisePeopleList(String enterpriseId){
		try {
			QueryDataService.getInstance().queryEnterprisePeopleList(beat(), enterpriseId);
		} catch (Exception e) {
			System.out.println("查询人员失败************企业id"+enterpriseId);
			e.printStackTrace();
		}
		return view("/wd/enterprisePeopleList");
	}
	
	private static final String mainUrl = "/wd/errory";
	@Path("/openEnterpriseDetail/{enterpriseId:\\S+}")
	public ActionResult openEnterpriseDetail(String enterpriseId){
		try {
			model().add("enterpriseId", enterpriseId);
			String businessKey = RSBLL.getstance().getEnterpriseService().getExtValueByEnterpriseIdAndKey(String.valueOf(enterpriseId), ILvEnterpriseService.EXT_KEY_LOCKBUSINESSKEY);
			
			List<String> cateIds = RSBLL.getstance().getSorderService().getAllProductCategoryIdByBusId(enterpriseId);
			if(cateIds == null || cateIds.isEmpty()){
				return view(mainUrl);
			}
			
			// 业务
			List<LvEnterpriseBusinessEntity> businessList = RSBLL.getstance().getEnterpriseBusinessService().getBusinessKeyListByGoodsIdList(cateIds);
			model().add("businessList", businessList);
			// 业务信息详情
			Map<String, String> businessDataMap = RSBLL.getstance().getEnterpriseBusinessDataService().getBusinessDataMapByEnterpriseIdAndBusinessKey(enterpriseId + "", businessKey);
			if(businessDataMap != null && !businessDataMap.isEmpty()){
				model().addAll(businessDataMap);
			}
			
			// 企业主信息
			Map<String, String> enterpriseMap = RSBLL.getstance().getEnterpriseService().getAllValueByEnterpriseId(String.valueOf(enterpriseId));
			model().addAll(DicUtils.transferDicData(enterpriseMap));
			
			model().add("DicUtils", DicUtils.class);
			model().add("EnterpriseUtils", EnterpriseUtils.class);
		} catch (Exception e) {
			System.out.println("查询人员失败************企业id"+enterpriseId);
			e.printStackTrace();
		}
		return view("/wd/enterpriseDetail");
	}
	
	@Path("/openOrderDetil/{orderid:\\S+}")
	public ActionResult openOrderDetil(String orderid){
		try {
			QueryDataService.getInstance().queryOrderDetil(beat(), orderid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view("/wd/orderDetil");
	}
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/***
	 * 查询企业库的详情信息
	 * @param orderId
	 * @return
	 */
	@Path("/monitor/task/detail/{orderId:[\\w-]+}")
	public ActionResult wfMonitorDetail(String orderId){
		List<LvProcInstEntitiy> lvProcInstList = null;
		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("taskOrderId", Long.parseLong(orderId));
		try {
			lvProcInstList = RSBLL.getstance().getHistoryService().getPageHisProcListByVariable(variable, 0, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ProcessMonitorDetailVO> rocessMonitorDetailVOList = new ArrayList<ProcessMonitorDetailVO>();
		Model model = model();
		if(lvProcInstList != null && !lvProcInstList.isEmpty()){
			for(LvProcInstEntitiy lvProcInstEntitiy:lvProcInstList){
				ProcessMonitorDetailVO processMonitorDetailVO = new ProcessMonitorDetailVO();
				rocessMonitorDetailVOList.add(processMonitorDetailVO);
				processMonitorDetailVO.setProcessDefinitionName(lvProcInstEntitiy.getName());
				processMonitorDetailVO.setProcessID(lvProcInstEntitiy.getId());
				processMonitorDetailVO.setProcessSuspensionState(lvProcInstEntitiy.getProcessSuspensionState());
				// 流程历史数据
				Map<String, Object> variables = null;
				try {
					variables = RSBLL.getstance().getHistoryService().getVariablesByProcessInstanceId(lvProcInstEntitiy.getId());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				// 企业库数据
				if(variables != null && !variables.isEmpty()){
					model.addAll(DicUtils.transferDicData(variables));
					//订单数据
					if(null != variables.get("taskOrderId") && variables.containsKey("taskOrderId")){
						Long orderid = Long.parseLong(variables.get("taskOrderId").toString());
						// 添加订单（母单与子单）信息
						buildOrderInfo(model, orderid);
					}
				}
				// 历史任务
				int pageSize = 100;
				List<LvTaskEntity> hisTaskList = null;
				try {
					hisTaskList = RSBLL.getstance().getHistoryService().getPageHisTaskListByProcessInstanceId(lvProcInstEntitiy.getId(), 0, pageSize);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(hisTaskList != null && !hisTaskList.isEmpty()){
					for(LvTaskEntity lvTaskEntity:hisTaskList){
						String userNameByUserId = getUserNameByUserId(lvTaskEntity.getAssignee());
						if(StringUtils.isNotBlank(userNameByUserId)){
							lvTaskEntity.setAssignee(userNameByUserId);
						}
					}
					processMonitorDetailVO.setHisTaskList(hisTaskList);
				}
				
				// 当前任务
				List<LvTaskEntity> currTaskList = null;
				try {
					currTaskList = RSBLL.getstance().getTaskQueryService().getPageListTaskByProcessInstanceId(lvProcInstEntitiy.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(currTaskList != null && !currTaskList.isEmpty()){
					for(LvTaskEntity lvTaskEntity:currTaskList){
						lvTaskEntity.setProcessSuspensionState(DicUtils.getDicDataValue("taskHandleState", lvTaskEntity.getProcessSuspensionState(), "正常"));
						lvTaskEntity.setAssignee(getUserNameByUserId(lvTaskEntity.getAssignee()));
					}
				}
				processMonitorDetailVO.setCurrentTaskList(currTaskList);
			
			}
		}
		if(rocessMonitorDetailVOList != null){
			model.add("rocessMonitorDetailVOList", rocessMonitorDetailVOList);
			model.add("simpleDateFormat", simpleDateFormat);
		}
		return view("/wd/task-monitor-detail");
	}
	
	/**
	 * 添加订单（母单与子单）信息
	 * @param model
	 * @param orderid
	 */
	@SuppressWarnings("unchecked")
	private void buildOrderInfo(Model model, Long orderid) {
		SorderEntity orderEntity = null;
		List<SorderChildrenEntity> childrenEList = null;
		try {
			orderEntity = RSBLL.getstance().getSorderService().getSorderEntityByid(orderid);
			if(null != orderEntity){
				RSBLL.getstance().getProductCategoryService().getProductCategoryEntitys("", 1, 1, "");
				childrenEList = OrderAssembleService.getInstance().getOrderChildrEntityList("orderid='"+orderEntity.getOrderid()+"'");
				if(null != childrenEList && childrenEList.size() > 0 ){
					StringBuffer sb = new StringBuffer();
					for(SorderChildrenEntity sorderChildE : childrenEList){
						ProductCategoryEntity prodcateE =  OrderAssembleService.getInstance().getCategoryEntity(sorderChildE.getProdcateid());
						if(null != prodcateE){
							sb.append(prodcateE.getCname()).append(",");
						}
					}
					model.add("serverName", sb.toString());
					if(sb.length()>10){
						model.add("serverNameShow", sb.toString().substring(0, 10)+"...");
					}else{
						model.add("serverNameShow", sb.toString());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(orderEntity != null){
			@SuppressWarnings("rawtypes")
			Map describe = new HashMap();
			try {
				describe = BeanUtils.describe(orderEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAll(MapUtils.getMap(describe));
			
			LoginEntity loginEntity = null;
			try {
				loginEntity = RSBLL.getstance().getLoginService().getLoginEntityById(orderEntity.getUserid());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(loginEntity != null){
				model.add("loginEntity", loginEntity);
			}
			
			/**
			 * 新企业库
			 */
			// 添加企业库信息
			buildEnterpriseInfo(model, orderEntity.getBusid() + "");
		}
		
	}
	
	private String getUserNameByUserId(String userId){
		if(StringUtils.isBlank(userId)){
			return null;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		if(pattern.matcher(userId).matches()){
			EmployEntity employEntity = null;
			try {
				employEntity = RSBLL.getstance().getUserService().getUserInfoByUserId(userId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(employEntity != null){
				return employEntity.getRealname();
			}
		}
		return userId;
	}
	private void buildEnterpriseInfo(Model model, String enterpriseId){
		// 注册地址
		Map<String, String> addressDataMap = null;
		try {
			addressDataMap = RSBLL.getstance().getEnterpriseAddressService().getEnterpriseAddressDataMapByEnterpriseId(enterpriseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(addressDataMap != null && !addressDataMap.isEmpty()){
			model.addAll(DicUtils.transferDicData(addressDataMap));
		}
		// 企业库详情
		Map<String, String> enterpriseBusinessDataMap = null;
		try {
			enterpriseBusinessDataMap = RSBLL.getstance().getEnterpriseBusinessDataService().getBusinessDataMapByEnterpriseId(enterpriseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(enterpriseBusinessDataMap != null && !enterpriseBusinessDataMap.isEmpty()){
			model.addAll(DicUtils.transferDicData(enterpriseBusinessDataMap));
		}
		Map<String, String> allValue = null;
		try {
			allValue = RSBLL.getstance().getEnterpriseService().getAllValueByEnterpriseId(enterpriseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(allValue != null){
			model.addAll(DicUtils.transferDicData(allValue));
		}
		model.add("EnterpriseUtils", EnterpriseUtils.class);
		
		model.add("enterpriseId", enterpriseId);

	}
	
	
	@Path("/orderlistbyenterpriseid/{enterpriseId:[\\w-]+}")
	public ActionResult orderlistbyenterpriseid(String enterpriseId){
		List<SorderVO>  sordervos = new ArrayList<SorderVO>();
		try {
			 List<SorderEntity> sorderentitys =  RSBLL.getstance().getSorderService().getSorderEntityListBypage(" busid='"+enterpriseId+"' ", 1, 99, "signtime desc");
			 if(null != sorderentitys && sorderentitys.size() > 0 ){
				 sordervos = QueryDataService.getInstance().getSorderVo(sorderentitys);
//				 for(SorderEntity sorderE : sorderentitys){
//					 sordervos.add(EntityUtils.transferEntity(sorderE, SorderVO.class));  
//				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("根据企业id查询订单失败!");
		}
		model().add("SorderList", sordervos);
		return view("/wd/common/orderlist");
	}
	

}
