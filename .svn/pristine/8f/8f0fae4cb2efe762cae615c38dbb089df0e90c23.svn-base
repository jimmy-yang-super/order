package com.jixiang.argo.lvzheng.controllers;


import java.util.ArrayList;
import java.util.List;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.service.LvTaskService;
import com.jixiang.argo.lvzheng.service.impl.LvTaskServiceImpl;
import com.jixiang.argo.lvzheng.utils.ActionResultUtils;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.vo.EmploysVo;
import com.jx.argo.ActionResult;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.union.entity.EmployEntity;
import com.jx.service.workflow.entity.LvTaskEntity;

@Path("/common")
public class CommonController extends AbstractController{
	
	public static final String roleAEid = "9";
	
	@Path("/userWin/{roleid:[\\w-]+}")
	public ActionResult getUserListByRole(String roleid){
		List<EmployEntity> userList = null;
		try {
			userList = RSBLL.getstance().getUserService().getUserListByRoleId(roleid);
			if(null != userList && userList.size() > 0){
				model().add("userList", userList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view("common/smallUserListWin");
	}
	
	@Path("/aeroleWin")
	public ActionResult getAERoleUserList(){
		List<EmployEntity> userList = null;
		try {
			userList = RSBLL.getstance().getUserService().getUserListByRoleId(roleAEid);
			if(null != userList && userList.size() > 0){
				model().add("userList", userList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view("common/smallUserListWin");
	}
	
	
	
	
	@Path("/taskOptionCommonList")
	public ActionResult taskOptionCommonList(){
		return view("/wf/taskCommonOption");
	}
	
	@Path("/taskOptionCommon/{option:[\\w-]+}")
	public ActionResult taskOptionCommon(String option){
		String active_orderid = beat().getRequest().getParameter("active_orderid")==null?"":beat().getRequest().getParameter("active_orderid");
		String suspend_orderid = beat().getRequest().getParameter("suspend_orderid")==null?"":beat().getRequest().getParameter("suspend_orderid");
		String del_orderid = beat().getRequest().getParameter("del_orderid")==null?"":beat().getRequest().getParameter("del_orderid");
		String start_orderid = beat().getRequest().getParameter("start_orderid")==null?"":beat().getRequest().getParameter("start_orderid");
		String empid  = beat().getRequest().getParameter("empid");
		String taskId  = beat().getRequest().getParameter("taskId")==null?"":beat().getRequest().getParameter("taskId");
		String delReson = beat().getRequest().getParameter("delReson")==null?"":beat().getRequest().getParameter("delReson");
		String userId  = beat().getRequest().getParameter("userid")==null?"":beat().getRequest().getParameter("userid");
		try {
			if("start".equals(option)){
				if(!empid.equals("") && !start_orderid.equals("")){
					LvTaskService lvtaskService = new LvTaskServiceImpl();
					lvtaskService.StartTask(empid, start_orderid, "");
				}else{
					return ActionResultUtils.renderJson("");
				}
			}else if("setAssignee".equals(option)){
				if(!taskId.equals("")){
					StringBuffer sb = new StringBuffer();
					LvTaskEntity loadByTaskId = RSBLL.getstance().getTaskQueryService().loadByTaskId(taskId);
					if(null != loadByTaskId){
						sb.append("oldAssignee_|_").append(loadByTaskId.getAssignee()).append("_|_").append("newAssignee_|_").append(userId).append("optionid_|_").append(UtilsHelper.getLoginId(beat()));
					}
					RSBLL.getstance().getTaskService().addTaskComment(taskId, "replayAssignee", sb.toString());
					RSBLL.getstance().getTaskService().setAssignee(taskId, userId);
				}else{
					return ActionResultUtils.renderJson("");
				}
			}else if("del".equals(option)){
				if(del_orderid.equals("")){
					RSBLL.getstance().getProcessService().deleteProcessInstanceByOrderId(del_orderid, delReson);
				}else{
					return ActionResultUtils.renderJson("");
				}
			}else if("suspend".equals(option)){
				if(suspend_orderid.equals("")){
					RSBLL.getstance().getProcessService().suspendProcessInstanceByOrderId(suspend_orderid);   //暂停
				}else{
					return ActionResultUtils.renderJson("");
				}
			}else if("active".equals(option)){
				if(active_orderid.equals("")){
					RSBLL.getstance().getProcessService().activateProcessInstanceByOrderId(active_orderid); //激活任务
				}else{
					return ActionResultUtils.renderJson("");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ActionResultUtils.renderJson("");
		}
		return ActionResultUtils.renderJson("{\"success\":true}");
	}
}
