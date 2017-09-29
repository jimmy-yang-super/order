package com.jixiang.argo.lvzheng.frame.service;

import java.util.ArrayList;
import java.util.List;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jx.service.newcore.entity.ActionsEntity;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.FollowupEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.OrderprocessEntity;
import com.jx.service.newcore.entity.PayProcessEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.ProductEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;
import com.jx.service.newcore.entity.ServiceprocessEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.newcore.entity.SorderExtEntity;



/***
 * 内部订单业务操作类
 * @author lvzheng-duxf
 */
public class OrderAssembleService {
	
	public static OrderAssembleService orderAssembleService;
	public static OrderAssembleService getInstance(){
		 if(null == orderAssembleService){
			 orderAssembleService = new OrderAssembleService();
		 }
		 return orderAssembleService;
	}
	
	
	//根据条件查询母单表
	public List<SorderEntity> getOrderEntityList(String condition,Integer pageindx,Integer pagesize,String orderby){
		
		List<SorderEntity> SorderList = null;
		
		try {
			SorderList = RSBLL.getstance().getSorderService().getSorderEntityListBypage(condition, pageindx, pagesize, orderby);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SorderList;
	}
	
	//根据ID查询母单表
	public SorderEntity getOrderEntityById(long orderid){
		SorderEntity sorderE= new SorderEntity();
		try {
			sorderE = RSBLL.getstance().getSorderService().getSorderEntityByid(orderid);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sorderE;
	}
	
	//更新母订单
	public void updateOrder(SorderEntity sorderEntity) throws Exception{
		RSBLL.getstance().getSorderService().updateSorderEntity(sorderEntity);
	}
	
	//更新子订单
	public void updateOrderChild(SorderChildrenEntity sorderChildEntity) throws Exception{
		RSBLL.getstance().getSorderChildrenService().updateSorderChildrenEntity(sorderChildEntity);
	}
	
	
	//根据条件查询母单表总数
	public int getOrderEntityCount(String condition){
		int sorderCount = 0;
		try {
			sorderCount = RSBLL.getstance().getSorderService().getSorderEntityCount(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sorderCount;
	}
	//根据母订单ID查询子订单
//	public List<SorderChildrenEntity> getOrderChildrEntityList(long orderid){
//		List<SorderChildrenEntity> orderChildList =null;
//		try {
//			String condition  = "orderid='"+orderid+"'";
//			orderChildList = RSBLL.getstance().getSorderChildrenService().getSorderChildrenEntityListByCustoms(condition, "");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return orderChildList;
//	}
	//根据母订单ID查询子订单  分页查询默认显示总数为99
	public List<SorderChildrenEntity> getOrderChildrEntityList(String condition){
		List<SorderChildrenEntity> orderChildList =null;
		try {
//			String condition  = "orderid='"+orderid+"'";
			orderChildList = RSBLL.getstance().getSorderChildrenService().getSorderChildrenEntityListBypage(condition, 1, 99, "prodcateid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderChildList;
	}
	//根据客户ID查询客户的详细信息
	public LoginEntity getUserEntity(long userid){
		LoginEntity entity  = null;
		try {
			entity = RSBLL.getstance().getLoginService().getLoginEntityById(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	//根据条件查询客户的详细信息
	public List<LoginEntity> getUserEntity(String condition){
		List<LoginEntity> entityList  = null;
		try {
			entityList = RSBLL.getstance().getLoginService().getLoginEntity(condition, 1, 99, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityList;
	}
	//根据ID查询product表
	public ProductEntity getProductEntity(long pid){
		ProductEntity entity  = null;
		try {
			entity = RSBLL.getstance().getProductNewService().getProductEntityById(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	public ProductCategoryEntity getCategoryEntity(long cid){
		ProductCategoryEntity entity  = null;
		try {
			entity = RSBLL.getstance().getProductCategoryService().getProductCategoryEntityById(cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	public ProductPropertiesEntity getPropertiesEntity(long proid){
		ProductPropertiesEntity entity  = null;
		try {
			entity = RSBLL.getstance().getProductPropertiesService().getProductPropertiesEntityById(proid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	public AreasEntity getAreasEntity(long cityid){
		AreasEntity entity  = null;
		try {
			entity = RSBLL.getstance().getAreasService().getAeasEntityById(cityid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	public EmployersEntity getEmployersEntity(long empid){
		EmployersEntity entity  = null;
		try {
			entity = RSBLL.getstance().getEmployersService().getEmployersEntityById(empid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	public List<EmployersEntity> getEmployersList(String condition){
		List<EmployersEntity> empEList  = new ArrayList<EmployersEntity>();
		try {
			empEList = RSBLL.getstance().getEmployersService().getEmployersEntity(condition, 1, 99, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empEList;
	}
	
	
	/****
	 * 
	 * 以下为操作FOLLOWUP表数据方法 strat
	 * 
	 */
	public List<FollowupEntity> getFollowupList(String condition){
		List<FollowupEntity> entity = new ArrayList<FollowupEntity>();
		try {
			entity = RSBLL.getstance().getFollowupService().getFollowupEntity(condition, 1, 99, "addtime desc");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	//添加意向度
	public long AddFollowUp(FollowupEntity fentity) throws Exception{
		long followid=0l;
		followid = RSBLL.getstance().getFollowupService().addFollowupEntity(fentity);
		return followid;
	}
	/** 以下为操作FOLLOWUP表数据方法  end */
	
	//查询财务的的日志信息server
	public List<PayProcessEntity> getPayprocess(String condition){
		List<PayProcessEntity> payEntity = new ArrayList<PayProcessEntity>();
		try {
			payEntity = RSBLL.getstance().getFinanceService().getPayProcessBypage(condition, 1, 99, "optime desc");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payEntity;
	}
	//添加财务的支付信息server
	public Long addPayprocess(PayProcessEntity payprocess){
		long payprocessid=0;
		try {
			payprocessid = RSBLL.getstance().getFinanceService().addPayProcess(payprocess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payprocessid;
	}
	
	
	//获得action节点
	public List<ActionsEntity> getActionsEntityList(long prodid){
		List<ActionsEntity> actionElist= new ArrayList<ActionsEntity>();
		try {
			actionElist  = RSBLL.getstance().getActionService().getActionslistByproid(prodid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionElist;
	}
	
	//获得子单服务节点
	public List<ServiceprocessEntity> getServiceProcessEntityList(String orderid){
		List<ServiceprocessEntity> serviceProcessElist=new ArrayList<ServiceprocessEntity>();
		try {
			String condition = "childorderid='"+orderid+"'";
			serviceProcessElist = RSBLL.getstance().getServiceprocessService().getServiceprocessEntity(condition, 1, 99, "optime");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceProcessElist;
	}
	
	//获得子单服务节点
	public List<ServiceprocessEntity> getServiceProcessEntityList2(String orderid){
		List<ServiceprocessEntity> serviceProcessElist=new ArrayList<ServiceprocessEntity>();
		try {
			String condition = "childorderid='"+orderid+"'";
			serviceProcessElist = RSBLL.getstance().getServiceprocessService().getServiceprocessEntity(condition, 1, 1, "optime");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceProcessElist;
	}
	//获得子单服务节点
	public List<ServiceprocessEntity> getServiceProcessEntity(String orderid){
		List<ServiceprocessEntity> serviceProcessElist=new ArrayList<ServiceprocessEntity>();
		try {
			String condition = "childorderid='"+orderid+"'";
			serviceProcessElist = RSBLL.getstance().getServiceprocessService().getServiceprocessEntity(condition, 1, 1, "optime desc");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceProcessElist;
	}
	
	public long addOrderProcess(OrderprocessEntity orderprocessE)throws Exception{
		long orderprocessid = 0;
		orderprocessid = RSBLL.getstance().getOrderprocessService().addOrderprocessEntity(orderprocessE);
		return orderprocessid;
	}
	
	public List<SorderExtEntity> getOrderExtEntityList(String orderId){
		List<SorderExtEntity> SorderList = null;	
		try {
			SorderList = RSBLL.getstance().getSorderService().getSorderExtByOrderId(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SorderList;
	}
	
	public void updateOrderExt(SorderExtEntity sorderEntity) throws Exception{
		RSBLL.getstance().getSorderService().updateSorderExtEntity(sorderEntity);
	}
	public void addOrderExt(SorderExtEntity sorderEntity) throws Exception{
		RSBLL.getstance().getSorderService().addSorderExtEntity(sorderEntity);
	}
	
	
}
