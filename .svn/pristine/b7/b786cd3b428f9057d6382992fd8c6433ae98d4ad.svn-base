package com.jixiang.argo.lvzheng.service.wf;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.DicUtils;
import com.jixiang.argo.lvzheng.utils.EntityUtils;
import com.jixiang.argo.lvzheng.utils.Timers;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.utils.WorkDayUtil;
import com.jixiang.argo.lvzheng.vo.TaskListVO;
import com.jx.argo.BeatContext;
import com.jx.service.enterprise.entity.LvEnterpriseEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.workflow.entity.LvTaskEntity;
import com.jx.tools.waq.WAQ;

/**
 * 获取任务列表的数据
 * @author lvzheng-duxf
 */
public class WfTaskListService {
	public static final String todolist = "todolist";
	public static final String completelist = "completelist";
	public static final String forwardTasklist = "forwardTasklist";
	
	//返回代办服务
	public void getTaskingList(BeatContext beat,Integer pageno,String tasktype){
		String paramOneValue = beat.getRequest().getParameter("paramOneValue")==null?"":beat.getRequest().getParameter("paramOneValue");
		String paramOneType = beat.getRequest().getParameter("paramOneType")==null?"":beat.getRequest().getParameter("paramOneType");
		String paramTwoValue = beat.getRequest().getParameter("paramTwoValue")==null?"":beat.getRequest().getParameter("paramTwoValue");
		String paramTwoType = beat.getRequest().getParameter("paramTwoType")==null?"":beat.getRequest().getParameter("paramTwoType");
		
		paramOneValue = WAQ.forXSS().HTMLEncode(paramOneValue);
		paramOneType = WAQ.forXSS().HTMLEncode(paramOneType);
		paramTwoValue = WAQ.forXSS().HTMLEncode(paramTwoValue);
		paramTwoType = WAQ.forXSS().HTMLEncode(paramTwoType);
		
		beat.getModel().add("paramOneValue", paramOneValue);
		beat.getModel().add("paramOneType", paramOneType);
		beat.getModel().add("paramTwoValue", paramTwoValue);
		beat.getModel().add("paramTwoType", paramTwoType);
		
		String queryUrl = "";
		try {
			queryUrl = "paramOneValue=" + URLEncoder.encode(paramOneValue, "utf-8") + "&paramOneType=" + paramOneType + "&paramTwoValue=" + URLEncoder.encode(paramTwoValue, "utf-8") + "&paramTwoType=" + paramTwoType;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		beat.getModel().add("paramValue", queryUrl);

		
		
		Map<String,Object> variable = new HashMap<String, Object>();
		String taskName = null;

		if(!"".equals(paramOneValue)){
			if(paramOneType.equals("1")){
				if(paramOneValue.matches("[+-]?[1-9]+[0-9]*(\\.[0-9]+)?")){
					variable.put("busid", Long.valueOf(paramOneValue));
				}
				beat.getRequest().getSession().setAttribute("paramOneType", "1");
			}else if(paramOneType.equals("2")){
				List<String> enterpriseIdList = null;
				try {
					enterpriseIdList = RSBLL.getstance().getEnterpriseService().getMainValueListByLikeName(paramOneValue, "enterpriseId");
				} catch (Exception e) {
					e.printStackTrace();
				}
				List<Long> enterpriseIdLongList = new ArrayList<Long>();
				if(enterpriseIdList !=null && enterpriseIdList.size() < 100){
					for(String enterpriseId:enterpriseIdList){
						enterpriseIdLongList.add(Long.parseLong(enterpriseId));
					}
					variable.put("busid", enterpriseIdLongList);
					beat.getRequest().getSession().setAttribute("paramOneType", "2");
				}
			}
		}
		
		if(!"".equals(paramTwoValue)){
			if(paramTwoType.equals("1")){
				taskName = paramTwoValue;
				beat.getRequest().getSession().setAttribute("paramTwoType", "1");
			}
		}

		List<TaskListVO> tasklist = new ArrayList<TaskListVO>();
		String temp_pageindex = "1";
		if(pageno!=1){
			temp_pageindex = pageno.toString();	
		}
		//分页属性
		Integer pageindex = Integer.valueOf(temp_pageindex);
		Integer pagesize = Constants.houtai_page_size;
		
		try {
			Long empid = UtilsHelper.getLoginId(beat);
			System.out.println("当前登录人员:"+empid);
//			Long empid = 33523970515969L;
			Long ordercount = 0l;
			List<LvTaskEntity> lvtaskElist = null;
			
			taskName = WAQ.forSQL().escapeSql(taskName);
			
			
			if(todolist.equals(tasktype)){ //待办的
				ordercount = RSBLL.getstance().getTaskQueryService().getPageListTaskByAssigneeAndLikeTaskNameAndVariableCount(empid.toString(), taskName, variable); //查询母单的总条数
				lvtaskElist = RSBLL.getstance().getTaskQueryService().getPageListTaskByAssigneeAndLikeTaskNameAndVariable(empid.toString(), taskName, variable, pageno-1, pagesize);
			}else if(completelist.equals(tasktype)){  //完成的
				ordercount = RSBLL.getstance().getHistoryService().getPageListHisTaskByAssigneeAndLikeTaskNameAndVariableCount(empid.toString(), taskName, variable); //查询母单的总条数
				lvtaskElist = RSBLL.getstance().getHistoryService().getPageListHisTaskByAssigneeeAndLikeTaskNameAndVariable(empid.toString(), taskName, variable, pageno-1, pagesize);
			}
			Long pagecount = ordercount%pagesize == 0?ordercount/pagesize:ordercount/pagesize+1;
			if(null != lvtaskElist && lvtaskElist.size()>0){
				Date until = new Date();
				for(LvTaskEntity lvtaskE : lvtaskElist){
					TaskListVO taskVO = new TaskListVO();
					String busid = RSBLL.getstance().getHistoryService().getVariableByProcessInstanceIdAndName(lvtaskE.getProcessInstanceId(), "busid");
					if(StringUtils.isNotBlank(busid)){
						LvEnterpriseEntity enterpriseEntity = RSBLL.getstance().getEnterpriseService().getEnterpriseById(Long.valueOf(busid));
						if(enterpriseEntity != null){
							taskVO.setBusid(enterpriseEntity.getEnterpriseId() + "");
							taskVO.setCompanyName(enterpriseEntity.getName());
						}
					}
					taskVO.setProcessDefinitionName(lvtaskE.getProcessDefinitionName());
					taskVO.setTaskId(lvtaskE.getId());
					taskVO.setTaskName(lvtaskE.getName());
					taskVO.setAccepTime(Timers.longToStr(lvtaskE.getCreateTime().getTime()));
					if(null != lvtaskE.getDueDate()){
						taskVO.setCompleteTime(Timers.longToStr(lvtaskE.getDueDate().getTime()));
					}
					taskVO.setTaskFalg("服务中");
					if(todolist.equals(tasktype)){
						if(null != lvtaskE.getDueDate()){
//							Long l = new Date().getTime() - lvtaskE.getDueDate().getTime();
							Integer yqDay = WorkDayUtil.getWorkDayNumBetween(UtilsHelper.formatLongDate("yyyy-MM-dd", lvtaskE.getDueDate().getTime()),UtilsHelper.formatLongDate("yyyy-MM-dd", until.getTime()));
							System.out.println(yqDay);
//							Long tempI = l / (1000 * 60 * 60 * 24);
							if(yqDay>0){
								taskVO.setTaskFalg("延期"+yqDay+"天"); 
							}
						}
					}else if(completelist.equals(tasktype)){
						taskVO.setTaskFalg("服务完成");
					}
					
					if("0".equals(lvtaskE.getPriority())){
						taskVO.setPriority(DicUtils.getDicDataValue("taskpriority", "0")); //优先级 一般
					}else{
						taskVO.setPriority("紧急"); //优先级 紧急
					}
					tasklist.add(taskVO);
				}
				beat.getModel().add("lvtasklist", tasklist);
			}
			//前台页面使用的参数START---------------
			beat.getModel().add("pagecount", pagecount);
			beat.getModel().add("pageIndex", pageindex);
/*			beat.getModel().add("paramOneValue", paramOneValue);
			beat.getModel().add("paramOneType", paramOneType);*/
//			beat.getModel().add("taskflag", taskflag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//得到转发任务集合
	public void getForwordTaskList(BeatContext beat,Integer pageno,String tasktype){
		String paramOneValue = beat.getRequest().getParameter("paramOneValue")==null?"":beat.getRequest().getParameter("paramOneValue");
		String paramOneType = beat.getRequest().getParameter("paramOneType")==null?"":beat.getRequest().getParameter("paramOneType");
		String paramTwoValue = beat.getRequest().getParameter("paramTwoValue")==null?"":beat.getRequest().getParameter("paramTwoValue");
		String paramTwoType = beat.getRequest().getParameter("paramTwoType")==null?"":beat.getRequest().getParameter("paramTwoType");
		
		beat.getModel().add("paramOneValue", paramOneValue);
		beat.getModel().add("paramOneType", paramOneType);
		beat.getModel().add("paramTwoValue", paramTwoValue);
		beat.getModel().add("paramTwoType", paramTwoType);
		
		paramOneValue = WAQ.forXSS().HTMLEncode(paramOneValue);
		paramOneType = WAQ.forXSS().HTMLEncode(paramOneType);
		paramTwoValue = WAQ.forXSS().HTMLEncode(paramTwoValue);
		paramTwoType = WAQ.forXSS().HTMLEncode(paramTwoType);
		
		String queryUrl = "paramOneValue=" + paramOneValue + "&paramOneType=" + paramOneType + "&paramTwoValue=" + paramTwoValue + "&paramTwoType=" + paramTwoType;
		beat.getModel().add("paramValue", queryUrl);
		
		Map<String,Object> variable = new HashMap<String, Object>();
		String taskName = "";
		if(!"".equals(paramOneValue)){
			if(paramOneType.equals("1")){
				if(paramOneValue.matches("[+-]?[1-9]+[0-9]*(\\.[0-9]+)?")){
					variable.put("busid", Long.valueOf(paramOneValue));
				}
			}else if(paramOneType.equals("2")){
				List<String> enterpriseIdList = null;
				try {
					enterpriseIdList = RSBLL.getstance().getEnterpriseService().getMainValueListByLikeName(paramOneValue, "enterpriseId");
				} catch (Exception e) {
					e.printStackTrace();
				}
				List<Long> enterpriseIdLongList = new ArrayList<Long>();
				if(enterpriseIdList !=null && enterpriseIdList.size() < 100){
					for(String enterpriseId:enterpriseIdList){
						enterpriseIdLongList.add(Long.parseLong(enterpriseId));
					}
					variable.put("busid", enterpriseIdLongList);
				}
			}
		}
		
		if(StringUtils.isNotBlank(paramTwoValue) && paramTwoType.equals("1")){
			taskName = paramTwoValue;
		}

		String temp_pageindex = "1";
		if(pageno!=1){
			temp_pageindex = pageno.toString();	
		}
		//分页属性
		Integer pageindex = Integer.valueOf(temp_pageindex);
		Integer pagesize = Constants.houtai_page_size;
		
		try {
			Long ordercount = WfTaskListService.getLvTaskEntityListCount(beat, tasktype,taskName, variable);
			List<LvTaskEntity> lvtaskElist = WfTaskListService.getLvTaskEntityList(beat, tasktype, taskName,  variable, pageno, pagesize);
		
			List<TaskListVO> tasklist = WfTaskListService.getTaskVoList(lvtaskElist);
			Long pagecount = ordercount%pagesize == 0?ordercount/pagesize:ordercount/pagesize+1;
			beat.getModel().add("pagecount", pagecount);
			beat.getModel().add("pageIndex", pageindex);
			beat.getModel().add("lvtasklist", tasklist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//通过类别得到任务集合
	private static List<LvTaskEntity> getLvTaskEntityList(BeatContext beat,String tasktype , String taskName, Map<String,Object> variable,Integer pageno,Integer pagesize) throws Exception{
		List<LvTaskEntity> lvtaskElist = null;
		if(forwardTasklist.equals(tasktype)){ 
			List<String> groupIdList =new ArrayList<String>();
			lvtaskElist = RSBLL.getstance().getTaskQueryService().getPageListTaskByTaskNameAndCandidateGroupListAndVariable(taskName, groupIdList, variable, pageno-1, pagesize);
		}
		return lvtaskElist;
	}
	
	//通过类别得到任务集合总数
	private static Long getLvTaskEntityListCount(BeatContext beat,String tasktype, String taskName ,Map<String,Object> variable) throws Exception{
		Long ordercount = 0l;
		if(forwardTasklist.equals(tasktype)){ 
			List<String> groupIdList =new ArrayList<String>();
			ordercount = RSBLL.getstance().getTaskQueryService().getPageListTaskByTaskNameAndCandidateGroupListAndVariableCount(taskName, groupIdList, variable);
		}
		return ordercount;
	}
	
//	//得到当前登录的所有角色集合
//	private static List<String> getUserRorsIdList(BeatContext beat){
//		Employee emp = LoginTool.getLoginUserInfo(beat.getRequest());
//		List<String> userRorsIdList = new ArrayList<String>();
////		暂时不按照角色去查询
////		if(null != emp){
////			String userRoleids = emp.getRoleids();
////			if(StringUtils.isNotEmpty(userRoleids)){
////				String [] userRoleidsArray = userRoleids.split(",");
////				for(String str : userRoleidsArray){
////					userRorsIdList.add(str);
////				}
////			}
////		}
//		return userRorsIdList;
//	}
	
	//转换list
	private static List<TaskListVO> getTaskVoList(List<LvTaskEntity> lvList) throws Exception{
		List<TaskListVO> taskvoList = new ArrayList<TaskListVO>();
		if(null != lvList && lvList.size()>0){
			for(LvTaskEntity lvtaskE : lvList){
				//转换vo对象
				TaskListVO taskVO = EntityUtils.transferEntity(lvtaskE, TaskListVO.class);
				String busid = RSBLL.getstance().getHistoryService().getVariableByProcessInstanceIdAndName(lvtaskE.getProcessInstanceId(), "busid");

				LvEnterpriseEntity enterpriseEntity = RSBLL.getstance().getEnterpriseService().getEnterpriseById(Long.valueOf(busid));
				if(enterpriseEntity != null){
					taskVO.setBusid(enterpriseEntity.getEnterpriseId() + "");
					taskVO.setCompanyName(enterpriseEntity.getName());
				}
//				BusinessLibaryEntity businessLibaryE = WfTaskListService.getBusinessLibaryEntity(lvtaskE);
//				if(StringUtils.isNotBlank(taskOrderId)){
//					SorderEntity sorderEntityByid = RSBLL.getstance().getSorderService().getSorderEntityByid(Long.parseLong(taskOrderId));
//					if(sorderEntityByid != null){
//					
//					}
//				}
				SorderEntity sorderEntity = WfTaskListService.getSorderEntity(lvtaskE);
				if(null != sorderEntity){
					taskVO.setOrderId(sorderEntity.getOrderid());
				}
				taskVO.setTaskId(lvtaskE.getId());
				taskVO.setTaskName(lvtaskE.getName());
				taskVO.setAccepTime(Timers.longToStr(lvtaskE.getCreateTime().getTime()));
				taskVO.setProcessDefinitionName(lvtaskE.getProcessDefinitionName());
				if(null != lvtaskE.getDueDate()){
					taskVO.setCompleteTime(Timers.longToStr(lvtaskE.getDueDate().getTime()));
				}
				if(StringUtils.isNotEmpty(taskVO.getAssignee())){
					EmployersEntity empEntity = RSBLL.getstance().getEmployersService().getEmployersEntityById(Long.valueOf(taskVO.getAssignee()));
					taskVO.setAssigneeName(empEntity.getRealname());
				}
				taskVO.setProcessSuspensionState(DicUtils.getDicDataValue("taskHandleState",taskVO.getProcessSuspensionState(),"正常"));
				taskvoList.add(taskVO);
			}
		}
		return taskvoList;
	}
	
	//得到企业库的实体对象
/*	private static BusinessLibaryEntity getBusinessLibaryEntity(LvTaskEntity lvtaskE) throws Exception{
		BusinessLibaryEntity businessLibaryEntity = null;
		if(null !=lvtaskE && !"".equals(lvtaskE.getProcessInstanceId())){
			List<String> varablename = new ArrayList<String>();
			varablename.add("busid");
			Map<String,Object> varableMap = RSBLL.getstance().getHistoryService().getVariablesByProcessInstanceId(lvtaskE.getProcessInstanceId(), varablename);
			if(null != varableMap && !varableMap.isEmpty()){
				Object object = varableMap.get("busid");
				if(object != null && StringUtils.isNotBlank(object.toString())){
					Long busid = Long.parseLong(object.toString());
					businessLibaryEntity = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(busid);
				}
			}
		}
		return businessLibaryEntity;
	}*/
	
	private static SorderEntity getSorderEntity(LvTaskEntity lvtaskE) throws Exception{
		SorderEntity sorderEntity = null;
		Long orderid = getEntityPrimaryKey(lvtaskE, "taskOrderId");
		if(null != orderid && orderid !=0){
			sorderEntity = RSBLL.getstance().getSorderService().getSorderEntityByid(orderid);
		}
		return sorderEntity;
	}
	
	//通过流程ID和类型参数获得实体对象的主键
	private static Long getEntityPrimaryKey(LvTaskEntity lvtaskE,String paramType) throws Exception{
		Long primaryKey = 0l;
		if(null !=lvtaskE && !"".equals(lvtaskE.getProcessInstanceId())){
			if(StringUtils.isNotEmpty(paramType)){
				List<String> varablename = new ArrayList<String>();
				varablename.add(paramType);
				Map<String,Object> varableMap = RSBLL.getstance().getHistoryService().getVariablesByProcessInstanceId(lvtaskE.getProcessInstanceId(), varablename);
				if(null != varableMap && !varableMap.isEmpty()){
					Object object = varableMap.get(paramType);
					if(object != null && StringUtils.isNotBlank(object.toString())){
						primaryKey = Long.parseLong(object.toString());
					}
				}
			}
		}
		return primaryKey;
	}
	
	public static void main(String[] args) throws Exception {
/*		List<LvTaskEntity> pageListTaskByAssigneeAndVariable = RSBLL.getstance().getTaskQueryService().getPageListTaskByAssigneeAndVariable("33523970515969", null , 0, 10);
		for(LvTaskEntity LvTaskEntity:pageListTaskByAssigneeAndVariable){
			System.out.println(LvTaskEntity.getName());
		}*/
		List<LvTaskEntity> pageListTaskByCandidateGroup = RSBLL.getstance().getTaskQueryService().getPageListTaskByCandidateGroup("9", 1, 10);

		System.out.println(pageListTaskByCandidateGroup.size());
	}
}
