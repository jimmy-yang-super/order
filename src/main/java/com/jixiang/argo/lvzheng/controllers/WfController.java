/**
 * 
 */

package com.jixiang.argo.lvzheng.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.annotation.CheckCookie;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.frame.service.OrderAssembleService;
import com.jixiang.argo.lvzheng.service.wf.SyncOldProcesService;
import com.jixiang.argo.lvzheng.utils.ActionResultUtils;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.DicUtils;
import com.jixiang.argo.lvzheng.utils.EnterpriseUtils;
import com.jixiang.argo.lvzheng.utils.EntityUtils;
import com.jixiang.argo.lvzheng.utils.JSONUtils;
import com.jixiang.argo.lvzheng.utils.MapUtils;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.utils.WFUtils;
import com.jixiang.argo.lvzheng.utils.WebBlackUtils;
import com.jixiang.argo.lvzheng.vo.ProcessMonitorDetailVO;
import com.jixiang.union.user.tools.LoginUtil;
import com.jx.argo.ActionResult;
import com.jx.argo.Model;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.blackface.gaea.sell.entity.LvzProductEntity;
import com.jx.blackface.gaea.sell.entity.LvzSellProductEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.PayProcessEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.newcore.entity.SpendEntity;
import com.jx.service.union.entity.EmployEntity;
import com.jx.service.workflow.entity.LvProcInstEntitiy;
import com.jx.service.workflow.entity.LvTaskEntity;
import com.jx.tools.waq.WAQ;


/**
 * simple introduction
 *
 * <p>detailed comment</p>
 * @author chuxuebao 2015年8月17日
 * @see
 * @since 1.0
 */
@Path("wf")
@CheckCookie
public class WfController extends AbstractController {

	@Path("task/detail/{taskId:[\\w-]+}")
	public ActionResult wfTaskDetail(String taskId){
		Model model = model();
		model.add("DicUtils", DicUtils.class);
		model.add("WFUtils", WFUtils.class);
		LvTaskEntity taskInfo = null;
		try {
			taskInfo = RSBLL.getstance().getTaskQueryService().loadByTaskId(taskId);
			if(taskInfo != null){
				model.add("taskInfo", taskInfo);
				
				String processDefinitionId = taskInfo.getProcessDefinitionId();
				if(processDefinitionId != null && !processDefinitionId.equals("")){
					String[] idArray = processDefinitionId.split(":");
					if(idArray.length > 2)
						model.add("procDefVersion", idArray[1]);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			LvTaskEntity lastTaskInfo = RSBLL.getstance().getHistoryService().loadLastTaskInfoByTaskId(taskId);
			if(lastTaskInfo != null){
				model.add("lastTaskInfo", lastTaskInfo);
				EmployersEntity ee = RSBLL.getstance().getEmployersService().getEmployersEntityById(Long.parseLong(lastTaskInfo.getAssignee()));
				model.add("lastEmployersName", ee.getRealname());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 流程历史数据
		Map<String, Object> variables = null;
		try {
			if(taskInfo != null){
				variables = RSBLL.getstance().getHistoryService().getVariablesByProcessInstanceId(taskInfo.getProcessInstanceId()); // .getVariablesByTaskId(taskId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(variables != null && !variables.isEmpty()){
			model.addAll(DicUtils.transferDicData(variables));
			//订单数据
			if(null != variables.get("taskOrderId") && variables.containsKey("taskOrderId")){
				Long orderid = Long.parseLong(variables.get("taskOrderId").toString());
				// 添加订单（母单与子单）信息
				buildOrderInfo(model, orderid);
			}
			
			//客户数据(新服务系统使用)
			if(null != variables.get("webUserId") && variables.containsKey("webUserId")){
				String userid = variables.get("webUserId").toString();
				buildCustomerInfo(model,Long.valueOf(userid));
			}
			
			//商品库数据
			if(null != variables.get("sellid") && variables.containsKey("sellid")){
				String sellid = variables.get("sellid").toString();
				getSellProductInfo(model,sellid);
			}
			/**
			 * 新企业库
			 */
			// 添加企业库信息
			if(null != variables.get("busid") && variables.containsKey("busid")){
				String busid = variables.get("busid").toString();
				String addressFormat = "";
				if(variables.get("addressFormat") != null){
					addressFormat = variables.get("addressFormat").toString();
				}
				buildEnterpriseInfo(model, busid);
				if( StringUtils.isNotBlank(addressFormat)){
					model.add("addressFormat", addressFormat);
				}
			}
		}
		return view("wf/detail/task-detail");
	}
	
	/**
	 * 新服务系统构建客户信息
	 * @param model
	 * @param userid
	 */
	private void buildCustomerInfo(Model model,Long userid){
		LoginEntity loginEntity  = null;
		try {
			loginEntity = RSBLL.getstance().getLoginService().getLoginEntityById(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null != loginEntity){
			model.add("loginEntity", loginEntity);
		}
		
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
				//用户 start tzy
				Long userid = orderEntity.getUserid();
				buildCustomerInfo(model,Long.valueOf(userid));
				//用户 end tzy
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
			
//			/**
//			 * 新企业库
//			 */
//			// 添加企业库信息
//			buildEnterpriseInfo(model, orderEntity.getBusid() + "");
		}
		
	}
	
	/***
	 * 获取商品库信息
	 * @param model
	 * @param sellid
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getSellProductInfo(Model model,String sellid){
		if(StringUtils.isNotBlank(sellid)){
			try {
				LvzSellProductEntity lvzSellProductEntity = RSBLL.getstance().getLvzSellProductService().getSellProductEntityById(Long.valueOf(sellid));
				if(null != lvzSellProductEntity){
					Map lvzSellProductMap = new HashMap();
					try {
						lvzSellProductMap = BeanUtils.describe(lvzSellProductEntity);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(null != lvzSellProductMap && lvzSellProductMap.size() > 0){
						LvzProductEntity lvzProductEntity = RSBLL.getstance().getLvzProductService().getProductEntityById(lvzSellProductEntity.getProduct_id());
						if(null != lvzProductEntity){
							lvzSellProductMap.put("product_tips", lvzProductEntity.getProduct_tips());
							lvzSellProductMap.put("product_desc", lvzProductEntity.getProduct_desc());
							lvzSellProductMap.put("product_other_name", lvzProductEntity.getProduct_other_name());
						}
						model.addAll(MapUtils.getMap(lvzSellProductMap));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	/**
	 * 流程提交
	 * @param taskId
	 * @return
	 */
	@Path("task/submit/{taskId:[\\w-]+}")
	public ActionResult wfSubmitTask(String taskId){
		Map<String, Object> properties = new HashMap<String, Object>();
		Enumeration<String> parameterNames = request().getParameterNames();
		while(parameterNames.hasMoreElements()){
			String nextElement = WAQ.forXSS().HTMLEncode(parameterNames.nextElement());
			String parameter = WAQ.forXSS().HTMLEncode(request().getParameter(nextElement));
			if(StringUtils.isNotBlank(parameter)){
				properties.put(nextElement, parameter);
			}
		}
		if(properties != null && !properties.isEmpty() && properties.containsKey("taskActionName")){
			try {
				RSBLL.getstance().getTaskService().addTaskComment(taskId, "taskActionName", properties.get("taskActionName").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LvTaskEntity taskInfo = null;
		try {
			taskInfo = RSBLL.getstance().getHistoryService().loadTaskInfoByTaskId(taskId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(taskInfo != null && StringUtils.isBlank(taskInfo.getAssignee())){
			String empId = LoginUtil.getEmpIdFromCookie(request());
			if(StringUtils.isNotBlank(empId)){
				try {
					RSBLL.getstance().getTaskService().claimTask(taskId, empId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			RSBLL.getstance().getTaskService().submitTask(taskId, properties);
		} catch (Exception e) {
			e.printStackTrace();
			return ActionResultUtils.renderJson("{\"success\":false}");
		}
		
		// 调用后台接口，触发原有流程
		try {
			if(null != taskInfo){
				String processInstanceId =  taskInfo.getProcessInstanceId();
				LvProcInstEntitiy procInstE = RSBLL.getstance().getHistoryService().loadByProcessInstanceId(processInstanceId);
				if(null != procInstE){
					//流程结束后改变子单状态
					if(null != procInstE.getEndTime() && !"".equals(procInstE.getEndTime())){
						List<String> variableNames = new ArrayList<String>();
						variableNames.add("taskOrderId");
						Map<String,Object> _variableNames = RSBLL.getstance().getHistoryService().getVariablesByProcessInstanceId(processInstanceId, variableNames);
						Long orderid = 0l;
						Long optid   = 0l;
						if(null != _variableNames && !_variableNames.isEmpty()){
							if(_variableNames.containsKey("taskOrderId")){
								orderid = Long.valueOf(_variableNames.get("taskOrderId").toString());
							}
							if(_variableNames.containsKey("busid")){
								String _optid = RSBLL.getstance().getLvCompanyService().getBusValueByCompanyIdAndBusKey(_variableNames.get("busid").toString(), "AE");
								if(StringUtils.isNotBlank(_optid)) optid = Long.valueOf(_optid);
							}
						}
						SyncOldProcesService.SynOrderChildState(optid, orderid, procInstE.getBusinessKey());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ActionResultUtils.renderJson("{\"success\":false}");
		}

		return ActionResultUtils.renderJson("{\"success\":true}");
	}
	
	@Path("task/save/{taskId:[\\w-]+}")
	public ActionResult wfSaveTask(String taskId){
		Map<String, Object> properties = new HashMap<String, Object>();
		Enumeration<String> parameterNames = request().getParameterNames();
		while(parameterNames.hasMoreElements()){
			String nextElement = WAQ.forXSS().HTMLEncode(parameterNames.nextElement());
			String parameter = WAQ.forXSS().HTMLEncode(request().getParameter(nextElement));
			if(StringUtils.isNotBlank(parameter)){
				properties.put(nextElement, parameter);
			}
		}
		try {
			RSBLL.getstance().getTaskService().saveTask(taskId, properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultUtils.renderJson("{\"success\":true}");
	}
	//添加任务备注信息
	@Path("task/addComment/{taskId:[\\w-]+}/{type:[\\w-]+}")
	public ActionResult wfAddTaskComment(String taskId, String type){
		String message = JSONUtils.toJsonString(request().getParameterMap());
		message = WAQ.forXSS().HTMLEncode(message);
		try {
			RSBLL.getstance().getTaskService().addTaskComment(taskId, type, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultUtils.renderJson("{\"success\":true}");
	}
	
	//更新订单信息
	@Path("order/update/{orderId:[\\w-]+}")
	public ActionResult wfaddOrderInfo(String orderId){
		Map<String, Object> properties = new HashMap<String, Object>();
		Enumeration<String> parameterNames = request().getParameterNames();
		while(parameterNames.hasMoreElements()){
			String nextElement = parameterNames.nextElement();
			String parameter = request().getParameter(nextElement);
			if(StringUtils.isNotBlank(parameter)){
				properties.put(nextElement, parameter);
			}
		}
		SorderEntity sorderEntity = null;
		try {
			sorderEntity = RSBLL.getstance().getSorderService().getSorderEntityByid(Long.valueOf(orderId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 合并数据
			SorderEntity orderInfo = EntityUtils.combineEntity(sorderEntity, properties);
			RSBLL.getstance().getSorderService().updateSorderEntity(orderInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultUtils.renderJson("{\"success\":true}");
	}
	
	// 添加支出记录
	@Path("payout/process/add/{orderId:[\\w-]+}")
	public ActionResult wfAddPayOutProcessInfo(String orderId){
		Map<String, Object> properties = new HashMap<String, Object>();
		Enumeration<String> parameterNames = request().getParameterNames();
		while(parameterNames.hasMoreElements()){
			String nextElement = parameterNames.nextElement();
			String parameter = request().getParameter(nextElement);
			if(StringUtils.isNotBlank(parameter)){
				properties.put(nextElement, parameter);
			}
		}
		long spendid =0L;
		SpendEntity se = new SpendEntity();
		se.setOrderid(Long.valueOf(orderId));
		se.setOpempid(UtilsHelper.getLoginId(beat()));
		se.setOpttime(new Date().getTime());
		se.setSpendmoney(Float.valueOf(properties.get("paytotal").toString()));
		se.setSpendtype(13); //13为国税CA
		se.setEmpid(0);
		try {
			spendid = RSBLL.getstance().getSpendRecordService().addspendRecord(se);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(spendid >0){
			PayProcessEntity processEntity = new PayProcessEntity();
			processEntity.setOptime(new Date().getTime());
			processEntity.setOptype(Constants.ORDER_ZC_ACTION_TYPE);
			processEntity.setOrderid(Long.valueOf(orderId));
			processEntity.setOpempid(UtilsHelper.getLoginId(beat()));
			processEntity.setContents(spendid+"");
			SorderEntity sorderEntity  = null;
			try {
				sorderEntity = RSBLL.getstance().getSorderService().getSorderEntityByid(Long.valueOf(orderId));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(null != sorderEntity){
				processEntity.setOrderstate(sorderEntity.getOrderstate());
				processEntity.setPaystate(sorderEntity.getPaystate());
			}
			PayProcessEntity payprocessInfo = EntityUtils.combineEntity(processEntity, properties);
			payprocessInfo.setPaynumber(0);
			try {
				// 合并数据
				RSBLL.getstance().getPayprocessService().addNewPayprocess(payprocessInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			return ActionResultUtils.renderJson("");
		}
		
		
		return ActionResultUtils.renderJson("{\"success\":true}");
	}	
	
	//添加支付记录信息[同时更新order]
	@Path("payprocess/add/{orderId:[\\w-]+}")
	public ActionResult wfaddPayProcessInfo(String orderId){
		Map<String, Object> properties = new HashMap<String, Object>();
		Enumeration<String> parameterNames = request().getParameterNames();
		while(parameterNames.hasMoreElements()){
			String nextElement = parameterNames.nextElement();
			String parameter = request().getParameter(nextElement);
			if(StringUtils.isNotBlank(parameter)){
				properties.put(nextElement, parameter);
			}
		}
		PayProcessEntity processEntity = new PayProcessEntity();
		processEntity.setOptime(new Date().getTime());
		processEntity.setOptype(Constants.ORDER_SK_ACTION_TYPE);
		processEntity.setOrderid(Long.valueOf(orderId));
		processEntity.setOpempid(UtilsHelper.getLoginId(beat()));
		SorderEntity     sorderEntity  = null;
		try {
			sorderEntity = RSBLL.getstance().getSorderService().getSorderEntityByid(Long.valueOf(orderId));
			if(null != sorderEntity){
				processEntity.setOrderstate(sorderEntity.getOrderstate());
				if(properties.containsKey("paytotal")){
					float prepay = sorderEntity.getPrepaidamount();
					prepay = prepay + Float.valueOf(properties.get("paytotal").toString());
					sorderEntity.setPrepaidamount(prepay);
					if(sorderEntity.getPaystate() == 0 || sorderEntity.getPaystate() == 1 || sorderEntity.getPaystate() == 2){
						if(prepay > 0 && prepay < sorderEntity.getPaymoneycount()){
							sorderEntity.setPaystate(Constants.ORDER_PAY_STATE_YF);
						}else if(prepay >= sorderEntity.getPaymoneycount()){
							sorderEntity.setPaystate(Constants.ORDER_PAY_STATE_QK);
						}else if(prepay == 0){
							sorderEntity.setPaystate(Constants.ORDER_PAY_STATE_WF);
						}
					}else{
						if(prepay > 0 && prepay < sorderEntity.getPaymoneycount()){
							sorderEntity.setPaystate(Constants.ORDER_PAY_STATE_WF_TERRACE);
						}else if(prepay >= sorderEntity.getPaymoneycount()){
							sorderEntity.setPaystate(Constants.ORDER_PAY_STATE_QK_TERRACE);
						}else if(prepay == 0){
							sorderEntity.setPaystate(Constants.ORDER_PAY_STATE_WF_TERRACE);
						}
					}
					processEntity.setPaystate(sorderEntity.getPaystate());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 合并数据
			PayProcessEntity payprocessInfo = EntityUtils.combineEntity(processEntity, properties);
			RSBLL.getstance().getPayprocessService().addNewPayprocess(payprocessInfo);
			RSBLL.getstance().getSorderService().updateSorderEntity(sorderEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultUtils.renderJson("{\"success\":true}");
	}
	/**
	 * 暂停流程
	 * @param processInstanceId
	 * @return
	 */
	@Path("process/suspend/{processInstanceId:[\\w-]+}")
	public ActionResult wfSuspendProcess(String processInstanceId){
		try {
			RSBLL.getstance().getProcessService().suspendProcessInstanceById(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultUtils.renderJson("{\"success\":true}");
	}
	
	/**
	 * 激活流程
	 * @param processInstanceId
	 * @return
	 */
	@Path("process/activate/{processInstanceId:[\\w-]+}")
	public ActionResult wfActivateProcess(String processInstanceId){
		try {
			RSBLL.getstance().getProcessService().activateProcessInstanceById(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultUtils.renderJson("{\"success\":true}");
	}
	
	/**
	 * 流程图
	 * @param processInstanceId
	 * @return
	 */
	@Path("process/pic/{processInstanceId:[\\w-]+}")
	public ActionResult wfProcessPic(String processInstanceId){
		try {
			byte[] processPic = RSBLL.getstance().getHistoryService().getProcessPicByProcessInstanceId(processInstanceId);
			return ActionResultUtils.renderFile(processPic, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultUtils.renderJson("");
	}
	
	/**
	 * 保存企业数据
	 */
	@Path("company/update/{enterpriseId:\\d+}/{businessId:\\d+}")
	public ActionResult wfUpdateEnterpriseInfo(Long enterpriseId, Long businessId){
		Map<String, String> properties = new HashMap<String, String>();
		Enumeration<String> parameterNames = request().getParameterNames();
		while(parameterNames.hasMoreElements()){
			String nextElement = parameterNames.nextElement();
			String parameter = request().getParameter(nextElement);
			if(StringUtils.isNotBlank(parameter)){
				properties.put(nextElement, parameter);
			}
		}
		try {
			RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId + "", businessId + "", properties, EnterpriseUtils.getLoginInfo(request()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultUtils.renderJson("{\"success\":true}");
	}
	/**
	 * 更新企业库附件
	 * @param busId
	 * @return
	 */
	@Path("companyattach/update/{enterpriseId:\\d+}/{businessId:\\d+}")
	public ActionResult updateCompanyAttach(Long enterpriseId, Long businessId){
		Map<String, String> properties = new HashMap<String, String>();
		Enumeration<String> parameterNames = request().getParameterNames();
		while(parameterNames.hasMoreElements()){
			String nextElement = parameterNames.nextElement();
			String parameter = request().getParameter(nextElement);
			properties.put(nextElement, parameter);
		}
		if(properties != null && !properties.isEmpty()){
			try {
				// 更新图片
				RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId + "", businessId + "", properties, EnterpriseUtils.getLoginInfo(request()));
			} catch (Exception e) {
				e.printStackTrace();
				return ActionResultUtils.renderJson("{\"success\":false}");
			}
		}
		return ActionResultUtils.renderJson("{\"success\":true}");
	}
	
	@Path("task/getvariable/{procInstId:[\\w-]+}/{variableName:[\\w-]+}")
	public ActionResult getVariableByTaskIdAndName(String procInstId, String variableName){
		String variable = WFUtils.getVariableByProcInstIdAndName(procInstId, variableName);
		return ActionResultUtils.renderText(variable);
	}
	
	@Path("monitor/task/detail/{orderId:[\\w-]+}")
	public ActionResult wfMonitorDetail(String orderId){
		List<LvProcInstEntitiy> lvProcInstList = null;
		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("taskOrderId", Long.parseLong(orderId));
		try {
			lvProcInstList = RSBLL.getstance().getHistoryService().getPageHisProcListByVariable(variable, 0, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		buildOrderInfo(model(), Long.parseLong(orderId));
		if(model().contains("busid")){
			Object busidObj = model().get("busid");
			if(busidObj != null){
				buildEnterpriseInfo(model(), busidObj.toString());
			}
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
		return view("wf/detail/task-monitor-detail");
	}
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
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
	
	/***
	 * 更换人员
	 * @param userId
	 * @return
	 */
	@Path("/task/replayUser/{userId:[\\w-]+}/{taskId:[\\w-]+}")
	public ActionResult replayUser(String userId,String taskId){
		try {
			StringBuffer sb = new StringBuffer();
			LvTaskEntity loadByTaskId = RSBLL.getstance().getTaskQueryService().loadByTaskId(taskId);
			if(null != loadByTaskId){
				sb.append("oldAssignee_|_").append(loadByTaskId.getAssignee()).append("_|_").append("newAssignee_|_").append(userId).append("optionid_|_").append(UtilsHelper.getLoginId(beat()));
			}
			RSBLL.getstance().getTaskService().addTaskComment(taskId, "replayAssignee", sb.toString());
			RSBLL.getstance().getTaskService().setAssignee(taskId, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return ActionResultUtils.renderJson("{\"success\":false}");
		}
		return ActionResultUtils.renderJson("{\"success\":true}");
	}
	
	/****
	 * 更换企业负责人
	 * @param orderid
	 * @param busId
	 * @param userId
	 * @param taskId
	 * @return
	 */
	@Path("/task/replayBussinessUser/{userId:[\\w-]+}/{taskId:[\\w-]+}")
	public ActionResult replayBussinessUser(String userId,String taskId){
		
		String orderId = WFUtils.getVariableByTaskIdAndName(taskId, "taskOrderId");
		try {
			SorderEntity sorderE = RSBLL.getstance().getSorderService().getSorderEntityByid(Long.valueOf(orderId));
			if(null != sorderE){
				//改变母单中的服务人员
				sorderE.setFwgwempid(Long.valueOf(userId));
				RSBLL.getstance().getSorderService().updateSorderEntity(sorderE);
				
				//添加备注
				String busId = sorderE.getBusid() + "";
				StringBuffer sb = new StringBuffer();
				String oldBusValue = RSBLL.getstance().getLvCompanyService().getBusValueByCompanyIdAndBusKey(busId, "AE");
				sb.append("oldBusValue_|_").append(oldBusValue).append("_|_").append("newBusValue_|_").append(userId).append("_|_").append("optionid_|_").append(UtilsHelper.getLoginId(beat()));
				RSBLL.getstance().getTaskService().addTaskComment(taskId, "replayBusValue", sb.toString());
				
				//更新服务系统中的企业负责人
				RSBLL.getstance().getLvCompanyService().updateAeBusValue(busId, userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ActionResultUtils.renderJson("{\"success\":false}");
		}
		return ActionResultUtils.renderJson("{\"success\":true}");
	}
	
	/**
	 * 获取核名信息
	 * @return
	 */
	@Path("/getGSCheckNameInfo/{enterpriseId:\\S+}")
	public ActionResult getGSCheckNameInfo(String enterpriseId){
		String userName = beat().getRequest().getParameter("hemgszh")==null?"":beat().getRequest().getParameter("hemgszh");
		String passWord = beat().getRequest().getParameter("hempassword")==null?"":beat().getRequest().getParameter("hempassword");
		String companyName = beat().getRequest().getParameter("companymc")==null?"":beat().getRequest().getParameter("companymc");
		
		if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(passWord) && StringUtils.isNotBlank(companyName)){
			try {
				String returnFlag =  WebBlackUtils.getCheckInfo(enterpriseId, userName, passWord, companyName);//
				System.out.println(returnFlag+"********");
				return ActionResultUtils.renderJson("{\"returnFlag\":\""+returnFlag+"\"}");
//				return ActionResultUtils.renderJson("{\"error\":}");
			} catch (Exception e) {
				e.printStackTrace();
				return ActionResultUtils.renderJson("{\"error\":}");
			}
		}
		
		return ActionResultUtils.renderJson("{\"error\":}");
	}
	
	/**
	 * 获取设立信息
	 * @return
	 */
	@Path("/getGSSheLiInfo/{enterpriseId:\\S+}")
	public ActionResult getGSSheLiInfo(String enterpriseId){
		String userName = beat().getRequest().getParameter("hemgszh")==null?"":beat().getRequest().getParameter("hemgszh");
		String passWord = beat().getRequest().getParameter("hempassword")==null?"":beat().getRequest().getParameter("hempassword");
		String companyName = beat().getRequest().getParameter("companymc")==null?"":beat().getRequest().getParameter("companymc");
		
		if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(passWord) && StringUtils.isNotBlank(companyName)){
			String returnFlag = WebBlackUtils.getGSSheLiInfo(enterpriseId, userName, passWord, companyName);
			System.out.println("********返回的提示语句:"+returnFlag);
			return ActionResultUtils.renderJson("{\"returnFlag\":\""+returnFlag+"\"}");
		}
		System.out.println("*********************111");
		
		return ActionResultUtils.renderJson("{\"error\":}");
	}
	
	
	
	public static void main(String[] args) throws Exception {
		/*List<LvTaskEntity> pageListTaskByCandidateGroup = RSBLL.getstance().getTaskQueryService().getPageListTaskByCandidateGroup("16", 0, 10);
		for(LvTaskEntity lvTaskEntity :pageListTaskByCandidateGroup){
			System.out.println(JSON.toJSONString(lvTaskEntity));
		}*/
		String userId = "32059953512961";
		
		String condition = "isnewprocess = 1 and userid = 32345457830145 ";
		List<SorderEntity> sorderEntityListBypage = RSBLL.getstance().getSorderService().getSorderEntityListBypage(condition, 1, 10, "");
		for(SorderEntity sorderEntity:sorderEntityListBypage){
			System.out.println(sorderEntity.getOrderid());
			//改变母单中的服务人员
			sorderEntity.setFwgwempid(Long.valueOf(userId));
			RSBLL.getstance().getSorderService().updateSorderEntity(sorderEntity);
			
			//添加备注
			String busId = sorderEntity.getBusid() + "";
			StringBuffer sb = new StringBuffer();
			String oldBusValue = RSBLL.getstance().getLvCompanyService().getBusValueByCompanyIdAndBusKey(busId, "AE");
			sb.append("oldBusValue_|_").append(oldBusValue).append("_|_").append("newBusValue_|_").append(userId).append("_|_").append("optionid_|_");
//			RSBLL.getstance().getTaskService().addTaskComment(taskId, "replayBusValue", sb.toString());
			
			//更新服务系统中的企业负责人
			RSBLL.getstance().getLvCompanyService().updateAeBusValue(busId, userId);
		}
	}
}
