package com.jixiang.argo.lvzheng.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.jixiang.argo.lvzheng.frame.RSBLL;

public class SelfStartServiceUtils {
	
	public static void main(String[] args) {
		//定义发起指定任务的条件
		Map<String,Object> variables = new HashMap<String, Object>();
		variables.put("superProcessInstanceId", "37412119207681");
		variables.put("superActivityId", "37414928003585");
		variables.put("sellid", "36666185121537");
		variables.put("busid", "37412119157249");
		variables.put("webUserId", "32284470234625");
//		variables.put("productid", "");
		variables.put("productCode", "GS-00106-446907");
		variables.put("city", "1");
		try {
			Map<String,String> processMap = RSBLL.getstance().getTaskService().startTask(variables);
			if(null != processMap && !processMap.isEmpty()){
				System.out.println("发起成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
