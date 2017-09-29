package com.jixiang.argo.lvzheng.service;

/***
 * Order的任务接口类
 * @author lvzheng-duxf
 *
 */
public interface LvTaskService {
	//发起任务 param:当前登录人 ，母单ID
	public void StartTask(String empid,String Orderid,String qianDanCompanyAE) throws Exception;
}
