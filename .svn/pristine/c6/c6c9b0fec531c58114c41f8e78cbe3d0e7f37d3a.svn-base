package com.jixiang.argo.lvzheng.controllers;

import java.util.HashMap;
import java.util.Map;

import com.jixiang.argo.lvzheng.annotation.CheckCookie;
import com.jixiang.argo.lvzheng.service.OrderService;
import com.jixiang.argo.lvzheng.service.impl.OrderServiceImpl;
import com.jixiang.argo.lvzheng.service.wf.WfTaskListService;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;

/**
 * 任务列表访问类
 * @author lvzheng-duxf
 */
@Path("/wf")
@CheckCookie
public class WfTaskListController extends AbstractController{
	WfTaskListService wftaskServer =new  WfTaskListService();
	/***法助查询待办start****/
	@Path("/task/fz_tasklist/{pageno:\\d+}")
//	@AutherCheck
	public ActionResult fz_tasklist(Integer pageno) {
		if(pageno == 1){
			wftaskServer.getTaskingList(beat(), 1, "todolist");
		}else{
			wftaskServer.getTaskingList(beat(), pageno, "todolist");
		}
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/wf/task/fz_tasklist");
		return view("/wf/list/task-todo-list");
	}
	/**** 法助查询待办任务列表 END ***/
	
	/***法助查询已完成任务start****/
	@Path("/task/fz_taskendlist/{pageno:\\d+}")
//	@AutherCheck
	public ActionResult fz_taskendlist(Integer pageno) {
		if(pageno == 1){
			wftaskServer.getTaskingList(beat(), 1, "completelist");
		}else{
			wftaskServer.getTaskingList(beat(), pageno, "completelist");
		}
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/wf/task/fz_taskendlist");
		return view("/wf/list/task-complete-list");
	}
	/**** 法助查询已完成任务 END ***/
	
	
	/***服务顾问监控服务任务列表start****/
	@Path("/task/monitorTask/{pageno:\\d+}")
//	@AutherCheck
	public ActionResult monitorTask(Integer pageno) {
		OrderService orderService = new OrderServiceImpl();
		if(pageno == 0){
			Map<String, String> queryOption = new HashMap<String, String>();
			orderService.getOrderList(beat(), "monitorTask",queryOption,0);
		}else{
			Map<String, String> queryOption = getParamsMap(beat());
			orderService.getOrderList(beat(), "monitorTask",queryOption,pageno);
		}
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/wf/task/monitorTask");
		return view("/wf/list/task-monitor-list");
	}
	
	
	/***系统管理员转发任务列表start****/
	@Path("/task/forwardTasklist/{pageno:\\d+}")
	//@AutherCheck
	public ActionResult forwardTasklist(Integer pageno) {
		if(pageno == 1){
			wftaskServer.getForwordTaskList(beat(), 1, "forwardTasklist");
		}else{
			wftaskServer.getForwordTaskList(beat(), pageno, "forwardTasklist");
		}
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/wf/task/forwardTasklist");
		return view("/wf/list/task-forward-list");
	}
	/**** 系统管理员转发任务 END ***/
	
	
	/***咨询监控服务任务列表start****/
//	@Path("/task/zx_monitorTask/{pageno:\\d+}")
//	//@AutherCheck
//	public ActionResult zx_monitorTask(Integer pageno) {
//		OrderService orderService = new OrderServiceImpl();
//		if(pageno == 0){
//			Map<String, String> queryOption = new HashMap<String, String>();
//			orderService.getOrderList(beat(), "zx_monitorTask",queryOption,0);
//		}else{
//			Map<String, String> queryOption = getParamsMap(beat());
//			orderService.getOrderList(beat(), "zx_monitorTask",queryOption,pageno);
//		}
//		//分页需要跳转的页面
//		beat().getModel().add("currenturl", "/wf/task/zx_monitorTask");
//		return view("/wf/list/task-monitor-list");
//	}
	
	/**
	 * 拼参数(此处写的比较乱,笔者也看不下去了.各位看官将就一下,不胜感谢)
	 * @param beat
	 * @return
	 */
	public Map<String,String> getParamsMap(BeatContext beat){
		Map<String,String> queryOption = new HashMap<String, String>();
		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
		String paramTwoValue2 =(String) beat().getRequest().getSession().getAttribute("paramTwoValue2");
		String paramThreeValue =(String) beat().getRequest().getSession().getAttribute("paramThreeValue");
		String paramStartTimeValue =(String) beat().getRequest().getSession().getAttribute("paramStartTimeValue");
		String paramEndTimeValue =(String) beat().getRequest().getSession().getAttribute("paramEndTimeValue");
		String paramfourValue =(String) beat().getRequest().getSession().getAttribute("paramfourValue");
		String paramfiveValue =(String) beat().getRequest().getSession().getAttribute("paramfiveValue");
		String paramsexValue =(String) beat().getRequest().getSession().getAttribute("paramsexValue");
		
		if(paramOneValue!= null || paramStartTimeValue != null || paramEndTimeValue != null){
			queryOption.put("paramOneValue", paramOneValue);
			queryOption.put("paramOneType", paramOneType);
			queryOption.put("paramTwoValue", paramTwoValue);
			queryOption.put("paramTwoValue2", paramTwoValue2);
			queryOption.put("paramThreeValue", paramThreeValue);
			queryOption.put("paramStartTimeValue", paramStartTimeValue);
			queryOption.put("paramEndTimeValue", paramEndTimeValue);
			queryOption.put("paramfourValue", paramfourValue);
			queryOption.put("paramfiveValue", paramfiveValue);
			queryOption.put("paramsexValue", paramsexValue);
		}
		return queryOption;
	}
	/**** 监控服务任务列表 END ***/
}
