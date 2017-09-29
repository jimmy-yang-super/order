package com.jixiang.argo.lvzheng.service;


import java.util.List;
import java.util.Map;

import com.jixiang.argo.lvzheng.vo.OrderChildInfoVO;
import com.jx.argo.BeatContext;

public interface OrderService {
	//订单列表查询使用的方法
	public void getOrderList(BeatContext beat,String orderType,Map<String, String> queryOption,Integer pageno);
	
	//根据条件查询订单的跟进列表
	public String getFolowList(String condition);
	
	//查询子订单的详情信息
/*	public String getChildOrderList(String orderid);*/
	
	
	public List<OrderChildInfoVO> getOrderChildList(String orderid);
	
/*	public String getChildOrderActions(long proid);*/
	
	//查询子订单的详情信息 供签单申请使用
	public List<OrderChildInfoVO> getChildOrdersList(String orderid);
	
	//财务查询订单的日志列表(支付支付日志)
	public String getPayProcessList(String orderid);
	
	//财务订单列表查询
	public void getOrderForFinanceList(BeatContext beat,String orderType,Map<String, String> queryOption,Integer pageno);
}
