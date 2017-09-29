package com.jixiang.argo.lvzheng.vo;

public class TaskListVO {
	
	private String processDefinitionName;  //服务流程名称
	private String taskId;	 //任务ID
	private String taskName; //任务名称 
	private String busid; // 档案编号
	private String companyName;  //公司名称
	private String taskFalg;   //任务状态
	private String accepTime;		//接受时间
	private String completeTime;		//应完成时间
	private String priority;		//优先级
	private String candidateGroup;  //角色ID
	private String assignee;        //当前操作人ID
	private String assigneeName;    //当前操作人名称
	private String processSuspensionState ;  //任务处理状态
	private Long   orderId;             //母单ID
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getBusid() {
		return busid;
	}
	public void setBusid(String busid) {
		this.busid = busid;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTaskFalg() {
		return taskFalg;
	}
	public void setTaskFalg(String taskFalg) {
		this.taskFalg = taskFalg;
	}
	public String getAccepTime() {
		return accepTime;
	}
	public void setAccepTime(String accepTime) {
		this.accepTime = accepTime;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getProcessDefinitionName() {
		return processDefinitionName;
	}
	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}
	public String getCandidateGroup() {
		return candidateGroup;
	}
	public void setCandidateGroup(String candidateGroup) {
		this.candidateGroup = candidateGroup;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getProcessSuspensionState() {
		return processSuspensionState;
	}
	public void setProcessSuspensionState(String processSuspensionState) {
		this.processSuspensionState = processSuspensionState;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	
	
	
}
