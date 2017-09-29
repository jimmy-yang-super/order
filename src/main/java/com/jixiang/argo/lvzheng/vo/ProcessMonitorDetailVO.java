/**
 * 
 */

package com.jixiang.argo.lvzheng.vo;

import java.util.List;

import com.jx.service.workflow.entity.LvTaskEntity;



/**
 * simple introduction
 *
 * <p>detailed comment</p>
 * @author chuxuebao 2015年8月25日
 * @see
 * @since 1.0
 */

public class ProcessMonitorDetailVO {

	// 流程名称
	private String processDefinitionName;
	
	// 流程实例ID
	private String processID;
	
	private String processSuspensionState;

	// 当前任务
	private List<LvTaskEntity> currentTaskList;
	
	// 历史任务
	private List<LvTaskEntity> hisTaskList;

	/**
	 * @return the processDefinitionName
	 */
	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	/**
	 * @param processDefinitionName the processDefinitionName to set
	 */
	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	/**
	 * @return the currentTaskList
	 */
	public List<LvTaskEntity> getCurrentTaskList() {
		return currentTaskList;
	}

	/**
	 * @param currentTaskList the currentTaskList to set
	 */
	public void setCurrentTaskList(List<LvTaskEntity> currentTaskList) {
		this.currentTaskList = currentTaskList;
	}

	/**
	 * @return the hisTaskList
	 */
	public List<LvTaskEntity> getHisTaskList() {
		return hisTaskList;
	}

	/**
	 * @param hisTaskList the hisTaskList to set
	 */
	public void setHisTaskList(List<LvTaskEntity> hisTaskList) {
		this.hisTaskList = hisTaskList;
	}
	/**
	 * @return the processID
	 */
	public String getProcessID() {
		return processID;
	}

	/**
	 * @param processID the processID to set
	 */
	public void setProcessID(String processID) {
		this.processID = processID;
	}

	/**
	 * @return the processSuspensionState
	 */
	public String getProcessSuspensionState() {
		return processSuspensionState;
	}

	/**
	 * @param processSuspensionState the processSuspensionState to set
	 */
	public void setProcessSuspensionState(String processSuspensionState) {
		this.processSuspensionState = processSuspensionState;
	}
}
