package com.jixiang.argo.lvzheng.buz;

import java.util.List;
import java.util.Random;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jx.service.indexing.contract.TestService;
import com.jx.service.indexing.entity.TestModel;


public class IndexTestBuz {
	
	public static void main(String[] args) throws Exception {
//		EmployBuz employBuz = EmployBuz.getstance();
//		EmploysVo vo = employBuz.getEmpvoByid(10002l);
//		System.out.println(vo.getRealname());
		TestModel testModel = new TestModel();
		testModel.setId(String.valueOf(new Random().nextDouble()));
		String[] title = {"zgm","lvzheng"};
		testModel.setTitle(title);
		
		TestService testService = RSBLL.getstance().getTestService();
		testService.add(testModel);
		testService.commit();
		List<TestModel> list = testService.query("id:0.8344576215495599", 0, 1,"id asc");
		if(list!=null && list.size()>0){
			System.out.println(list.get(0).getTitle()[1]);
		}
	}
}
