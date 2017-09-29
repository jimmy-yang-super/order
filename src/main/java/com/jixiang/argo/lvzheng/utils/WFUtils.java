/**
 * 
 */

package com.jixiang.argo.lvzheng.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jx.service.union.entity.EmployEntity;
import com.jx.service.workflow.entity.LvTaskEntity;



/**
 * simple introduction
 *
 * <p>detailed comment</p>
 * @author chuxuebao 2015年9月8日
 * @see
 * @since 1.0
 */

public class WFUtils {

	/**
	 * @param processInstanceId
	 * @param taskDefKey
	 * @return
	 */
	public static String getAssgineeByProcessInstanceIdAndTaskDefId(String processInstanceId, String taskDefKey){
		LvTaskEntity taskInfo = null;
		try {
			taskInfo = RSBLL.getstance().getTaskQueryService()
					.loadTaskByProcessInstanceIdAndTaskDefKey(processInstanceId, taskDefKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(taskInfo != null){
			EmployEntity employEntity = null;
			try {
				employEntity = RSBLL.getstance().getUserService().getUserInfoByUserId(taskInfo.getAssignee());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(employEntity != null){
				return employEntity.getRealname();
			}
		}
		return "";
	}
	
	public static String getVariableByTaskIdAndName(String taskId, String variableName){
		String name = "";
		try{
			name = RSBLL.getstance().getHistoryService().getVariableByTaskIdAndName(taskId, variableName);
		}catch(Exception e){
			e.printStackTrace();
		}
		return StringUtils.isNotBlank(name)?name:"";
	}
	
	public static String getVariableByProcInstIdAndName(String procInstId, String variableName){
		Map<String, Object> variables = null;
		try{
			List<String> list = new ArrayList<String>();
			list.add(variableName);
			variables = RSBLL.getstance().getHistoryService().getVariablesByProcessInstanceId(procInstId, list);
		}catch(Exception e){
			e.printStackTrace();
		}
		String name = "";
		if(variables != null && !variables.isEmpty()){
			Object object = variables.get(variableName);
			if(object != null){
				name = object.toString();
			}
		}
		return StringUtils.isNotBlank(name)?name:"";
	}
}
