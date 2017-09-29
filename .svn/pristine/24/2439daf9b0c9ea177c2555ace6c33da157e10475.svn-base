package com.jixiang.argo.lvzheng.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.frame.service.OrderAssembleService;
import com.jixiang.argo.lvzheng.service.LvTaskService;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.OrderprocessEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;

public class LvTaskServiceImpl implements LvTaskService{
	
	public final static String newProcess ="1001,1008";

	public static void main(String[] args) {
		LvTaskServiceImpl serviceImpl = new LvTaskServiceImpl();
		try {
			//34034726799361
	//{"busid":33304735323905,"checkNameStatus":"0","taskOrderId":34027116736513,"taskInitiator":"31926878381057","city":1,"prodcateid":"_1008_","companymc":"北京百维华龙科技有限公司","coid":"_34027116738817_","superid":34027116735489,"qianDanCompanyAE":"33231561669121"}
			serviceImpl.StartTask("31970620021249", "38650945359105", "");
			//RSBLL.getstance().getTaskService().setAssignee("510408939533783041", "33231561669121");activateProcessInstanceById("34034726799361");
			//RSBLL.getstance().getProcessService().deleteProcessInstanceByOrderId("34038106777857", "测试废弃");
			
//			Map<String,String> map = RSBLL.getstance().getSorderService().getAllProductCategoryAndOrderIdByBusId("33303327419649");
//			while(map.entrySet().iterator().hasNext()){
//				System.out.println(map.entrySet().iterator().next().getKey()+"==="+map.entrySet().iterator().next().getValue());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发起任务 empid:当前登陆人 orderid:当前母单ID
	 */
	@Override
	public void StartTask(String empid,String orderid,String qianDanCompanyAE) throws Exception {
		//定义发起指定任务的条件
		Map<String,Object> variables = new HashMap<String, Object>();
		RSBLL rsbll = RSBLL.getstance();
		if(!"".equals(orderid) && orderid != null){
			 SorderEntity sorderE = rsbll.getSorderService().getSorderEntityByid(Long.valueOf(orderid));
			 if(null != sorderE){
				 //variables.put("productid", sorderE.getProductid());  //存放对应服务的ID
				 variables.put("city", sorderE.getOrdercity());       //存放城市ID
				 variables.put("taskOrderId", sorderE.getOrderid());      //存放母单ID
				 variables.put("superid", sorderE.getSuperid());	  //存放超级母单ID
				 variables.put("busid", sorderE.getBusid()); 		  //存放企业ID
				 if(sorderE.getBusid()!=0){
					 BusinessLibaryEntity busindE = rsbll.getBusinessLibaryService().getBusinessLibaryEntityByid(sorderE.getBusid());
					 if(null != busindE){
						 variables.put("companymc", busindE.getCompanymc());  //公司名称
					 }
				 }
				 //根据条件查找到母单下的具体购买的服务信息(子单的信息)
				 String condition = " orderid='"+orderid+"' ";
				 List<SorderChildrenEntity> sorderchildEList = OrderAssembleService.getInstance().getOrderChildrEntityList(condition);
				 StringBuffer sb_childProdcateid = new StringBuffer("_");
				 StringBuffer sb_childCoid = new StringBuffer("_");
//				 boolean isNewProcess = false;
				 if(null != sorderchildEList && sorderchildEList.size()>0){
					 for(int i=0;i<sorderchildEList.size();i++){
						 SorderChildrenEntity sorderCE = sorderchildEList.get(i);
//						 if(newProcess.contains(String.valueOf(sorderCE.getProdcateid()))){
//							 isNewProcess = true;
//						 }
						 sb_childCoid.append(sorderCE.getCoid()).append("_");
						 sb_childProdcateid.append(sorderCE.getProdcateid()).append("_");
					 }
				 }
				 variables.put("prodcateid", sb_childProdcateid.toString());
				 variables.put("coid", sb_childCoid.toString());
				 variables.put("taskInitiator", empid); //当前登陆人
				 variables.put("qianDanCompanyAE", qianDanCompanyAE); //签单特殊角色小微制定人员
				 //查看企业是否已核名
				 BusinessLibaryEntity ble = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(sorderE.getBusid());
/*				 if(ble != null){
					 variables.put("checkNameStatus", String.valueOf(ble.getQiymczt()));   
				 }else{
					 variables.put("checkNameStatus", "0"); 
				 }*/
//				 RSBLL.getstance().getTaskService().startTask(variables);
				 Map<String,String> processMap = RSBLL.getstance().getTaskService().startTask(variables);
				 if(null != processMap && !processMap.isEmpty()){
					 sorderE.setIsnewprocess(1);
					 sorderE.setOrderstate(1);
					 sorderE.setServerstate(1);
				     sorderE.setPiesingletime(new Date().getTime());
					 sorderE.setUpdatetime(new Date().getTime());
					 Long optid = 0l;
					 if(sorderE.getBusid() != 0){
						 String _optid = "";
						//只买企业名称核准[1008]的时候把监控列表控制的人给咨询顾问
						 if("1008".equals(sb_childProdcateid.toString())){  
							 optid = Long.valueOf(empid); //登陆人也是签单人
						 }else{
							 _optid = rsbll.getLvCompanyService().getBusValueByCompanyIdAndBusKey(String.valueOf(sorderE.getBusid()), "AE");
							 if(StringUtils.isNotBlank(_optid)){
								 optid = Long.valueOf(_optid);
							 }
						 }
						 sorderE.setFwgwempid(optid);
					 }
					//更新企业库信息  
					 if(ble != null){
						ble.setStatus(1);
						RSBLL.getstance().getBusinessLibaryService().updateBusinessLibaryEntity(ble);
					 }
					 OrderAssembleService.getInstance().updateOrder(sorderE);
					 
					 //添加一条订单移交记录
					 OrderprocessEntity ope = new OrderprocessEntity();
					 ope.setOptime(new Date().getTime());
					 ope.setOption("移交订单");
					 ope.setOrderid(sorderE.getOrderid());
					 ope.setOrderstate(sorderE.getOrderstate());
					 ope.setOpid(Long.valueOf(optid));
					 ope.setOptcode(1137);
					 ope.setEmpid(sorderE.getFwgwempid());
					 rsbll.getOrderprocessService().addOrderprocessEntity(ope);
				 }
//				 //当任务不为空时,系统自动移交老流程
//				 if(null != processMap && !processMap.isEmpty()){
//					 while(processMap.entrySet().iterator().hasNext()){
//						 String procesKey = processMap.entrySet().iterator().next().getKey();
//						 if("bj-all-company_reg".equals(procesKey) || "bj-all-company_name_check".equals(procesKey)){
//							 List<LvTaskEntity> taskList = RSBLL.getstance().getHistoryService().getPageHisTaskListByProcessInstanceId(procesKey, 0, 10);
//							 if(null != taskList && taskList.size()>0){
//								 for(LvTaskEntity taskEntity : taskList){
//									 if("CheckNameInfo".equals(taskEntity.getTaskDefinitionKey())){
//										 sorderE.setFwgwempid(Long.valueOf(taskEntity.getAssignee()));
//										 sorderE.setUpdatetime(new Date().getTime());
//									     sorderE.setOrderstate(Constants.YJ_ORDER_STATE);
//									     sorderE.setPiesingletime(new Date().getTime());
//									     //更新企业库信息  
//										 if(ble != null){
//											ble.setStatus(1);
//											RSBLL.getstance().getBusinessLibaryService().updateBusinessLibaryEntity(ble);
//										 }
//										 OrderAssembleService.getInstance().updateOrder(sorderE);
//									 }
//									
//								 }
//							 }
//						 }
//					 }
//				 }
			 }
		}
	}
}
