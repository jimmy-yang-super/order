package com.jixiang.argo.lvzheng.utils;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.service.LvTaskService;
import com.jixiang.argo.lvzheng.service.impl.LvTaskServiceImpl;

public class deplyMyProcess {
	public static void main(String[] args) throws Exception{
		InputStream inputStream = ClassLoader.getSystemResourceAsStream("META-INF/bj-all-localtax_report.bpmn20.xml");
		byte[] byteArray = IOUtils.toByteArray(inputStream);
		System.out.println(byteArray.length);
//		//GaeaUtils.getDeployService().deployByInputStream("bj-all-company_name_check.bpmn20.xml", byteArray);
		RSBLL.getstance().getLvDeployService().deployByInputStream("bj-all-localtax_report.bpmn20.xml", byteArray);;
//		RSBLL.getstance().getProcessService().deleteProcessInstanceByOrderId("34205226687745", "测试");

		//LvTaskService lvTaskService = new LvTaskServiceImpl();
		//lvTaskService.StartTask("10008", "34203905203457", "");
	}
}
