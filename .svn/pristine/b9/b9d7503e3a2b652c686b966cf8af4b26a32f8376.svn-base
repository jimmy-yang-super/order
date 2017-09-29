package com.jixiang.argo.lvzheng.buz;

import java.util.List;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jx.service.newcore.contract.ISorderChildrenService;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;

public class OrderBuz {

	private static OrderBuz stance = new OrderBuz();
	private static ISorderChildrenService ics = RSBLL.getstance().getSorderChildrenService();
	public static OrderBuz getstance(){
		return stance;
	}
	public List<Object> getXWSlistByPage(String condition,int pageindex,int pagesize,String orderby){
		List<Object> list = null;
		return list;
	}
	public List<SorderChildrenEntity> getChildorderlistBypoid(long poid)throws Exception{
		String condition = "orderid = "+poid;
		return ics.getSorderChildrenEntityListBypage(condition, 1, 99, "coid");
	}
	public static void main(String[] args){
		String condition = "prodcateid=1007";
		try {
			List<SorderChildrenEntity> clist = ics.getSorderChildrenEntityListBypage(condition, 1, 99, "orderid");
			for(SorderChildrenEntity sc : clist){
				long oid = sc.getOrderid();
				if(oid > 0){
					SorderEntity se = RSBLL.getstance().getSorderService().getSorderEntityByid(oid);
					//订单状态("1", "正式订单","2", "订单取消","3", "已派单",“7”，“潜在订单”，"10", "订单完结"）
					System.out.println("order id is "+se.getOrderid()+" and state is "+se.getOrderstate());
						String un = RSBLL.getstance().getLoginService().getLoginEntityById(se.getUserid()).getUserphone();
						System.out.println("userid is "+se.getUserid()+" phone is "+un);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//(condition, orderby)
	}
}
