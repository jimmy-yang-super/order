package com.jixiang.argo.lvzheng.controllers;


import java.util.HashMap;
import java.util.Map;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.service.OrderService;
import com.jixiang.argo.lvzheng.service.impl.OrderServiceImpl;
import com.jixiang.union.interceptor.AutherCheck;
import com.jixiang.union.user.model.Employee;
import com.jixiang.union.user.tools.LoginTool;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.EmployersEntity;
/**
 * 财务 订单查询入口
 * @author 国明
 */
@Path("/order")
public class FinanceOrderController extends AbstractController {

	OrderService orderService = new OrderServiceImpl();
	@Path("/forPayment")
	@AutherCheck
	public ActionResult forPayment() {
		Employee empVo = LoginTool.getLoginUserInfo(beat().getRequest());
		long empid = empVo.getEmpid();
		EmployersEntity ee = null;
		try {
			ee = RSBLL.getstance().getEmployersService().getEmployersEntityById(empid);
			beat().getModel().add("empvo", ee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if(empVo != null){
//			System.out.println(empVo.getEmpid());
//		}
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderForFinanceList(beat(), "forPayment",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/forPayment");
		return view("/order/forPayment");
	}
	
	@Path("/forPayment/{pageno:\\d+}")
	@AutherCheck
	public ActionResult forPayment(Integer pageno) {
//		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
//		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
//		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
//		Map<String, String> queryOption = new HashMap<String, String>();
//		if(paramOneValue!=null && (paramOneType !="" && paramOneType!="null")){
//			queryOption.put("paramOneValue", paramOneValue);
//			queryOption.put("paramOneType", paramOneType);
//			queryOption.put("paramTwoValue", paramTwoValue);
//		}
		Employee empVo = LoginTool.getLoginUserInfo(beat().getRequest());
		long empid = empVo.getEmpid();
		EmployersEntity ee = null;
		try {
			ee = RSBLL.getstance().getEmployersService().getEmployersEntityById(empid);
			beat().getModel().add("empvo", ee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> queryOption = getParamsMap(beat());
//		System.out.println("参数1类型："+paramOneType+"参数1值："+paramOneValue+"参数2值:"+paramTwoValue+"========");
		orderService.getOrderForFinanceList(beat(), "forPayment",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/forPayment");
		return view("/order/forPayment");
	}
	
	@Path("/overMoney")
	@AutherCheck
	public ActionResult overMoney() {
		Employee empVo = LoginTool.getLoginUserInfo(beat().getRequest());
		long empid = empVo.getEmpid();
		EmployersEntity ee = null;
		try {
			ee = RSBLL.getstance().getEmployersService().getEmployersEntityById(empid);
			beat().getModel().add("empvo", ee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderForFinanceList(beat(), "overMoney",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/overMoney");
		return view("/order/overMoney");
	}
	
	@Path("/overMoney/{pageno:\\d+}")
	@AutherCheck
	public ActionResult overMoney(Integer pageno) {
		Employee empVo = LoginTool.getLoginUserInfo(beat().getRequest());
		long empid = empVo.getEmpid();
		EmployersEntity ee = null;
		try {
			ee = RSBLL.getstance().getEmployersService().getEmployersEntityById(empid);
			beat().getModel().add("empvo", ee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
		Map<String, String> queryOption = new HashMap<String, String>();
		if(paramOneValue!=null && (paramOneType !="" && paramOneType!="null")){
			queryOption.put("paramOneValue", paramOneValue);
			queryOption.put("paramOneType", paramOneType);
			queryOption.put("paramTwoValue", paramTwoValue);
		}
//		System.out.println("参数1类型："+paramOneType+"参数1值："+paramOneValue+"参数2值:"+paramTwoValue+"========");
		orderService.getOrderForFinanceList(beat(), "overMoney",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/overMoney");
		return view("/order/overMoney");
	}
	
	@Path("/forPaymentForTerrace")
	@AutherCheck
	public ActionResult forPaymentForTerrace() {
		Employee empVo = LoginTool.getLoginUserInfo(beat().getRequest());
		long empid = empVo.getEmpid();
		//long empid = 10007L;
		EmployersEntity ee = null;
		try {
			ee = RSBLL.getstance().getEmployersService().getEmployersEntityById(empid);
			beat().getModel().add("empvo", ee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if(empVo != null){
//			System.out.println(empVo.getEmpid());
//		}
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderForFinanceList(beat(), "forPaymentForTerrace",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/forPaymentForTerrace");
		return view("/order/forPaymentForTerrace");
	}
	
	@Path("/forPaymentForTerrace/{pageno:\\d+}")
	@AutherCheck
	public ActionResult forPaymentForTerrace(Integer pageno) {
//		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
//		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
//		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
//		Map<String, String> queryOption = new HashMap<String, String>();
//		if(paramOneValue!=null && (paramOneType !="" && paramOneType!="null")){
//			queryOption.put("paramOneValue", paramOneValue);
//			queryOption.put("paramOneType", paramOneType);
//			queryOption.put("paramTwoValue", paramTwoValue);
//		}
		Employee empVo = LoginTool.getLoginUserInfo(beat().getRequest());
		long empid = empVo.getEmpid();
		EmployersEntity ee = null;
		try {
			ee = RSBLL.getstance().getEmployersService().getEmployersEntityById(empid);
			beat().getModel().add("empvo", ee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> queryOption = getParamsMap(beat());
//		System.out.println("参数1类型："+paramOneType+"参数1值："+paramOneValue+"参数2值:"+paramTwoValue+"========");
		orderService.getOrderForFinanceList(beat(), "forPaymentForTerrace",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/forPaymentForTerrace");
		return view("/order/forPaymentForTerrace");
	}
	
	
	
	/**
	 * 拼参数(此处写的比较乱,笔者也看不下去了.各位看官将就一下,不胜感谢)
	 * @param beat
	 * @return
	 */
	public Map<String,String> getParamsMap(BeatContext beat){
		Map<String,String> queryOption = new HashMap<String, String>();
		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
		String paramTwoValue2 =(String) beat().getRequest().getSession().getAttribute("paramTwoValue2");
		String paramThreeValue =(String) beat().getRequest().getSession().getAttribute("paramThreeValue");
		String paramStartTimeValue =(String) beat().getRequest().getSession().getAttribute("paramStartTimeValue");
		String paramEndTimeValue =(String) beat().getRequest().getSession().getAttribute("paramEndTimeValue");
		String paramfourValue =(String) beat().getRequest().getSession().getAttribute("paramfourValue");
		String paramfiveValue =(String) beat().getRequest().getSession().getAttribute("paramfiveValue");
		String paramsexValue =(String) beat().getRequest().getSession().getAttribute("paramsexValue");
		
		if(paramOneValue!= null || paramStartTimeValue != null || paramEndTimeValue != null){
			queryOption.put("paramOneValue", paramOneValue);
			queryOption.put("paramOneType", paramOneType);
			queryOption.put("paramTwoValue", paramTwoValue);
			queryOption.put("paramTwoValue2", paramTwoValue2);
			queryOption.put("paramThreeValue", paramThreeValue);
			queryOption.put("paramStartTimeValue", paramStartTimeValue);
			queryOption.put("paramEndTimeValue", paramEndTimeValue);
			queryOption.put("paramfourValue", paramfourValue);
			queryOption.put("paramfiveValue", paramfiveValue);
			queryOption.put("paramsexValue", paramsexValue);
		}
		return queryOption;
	}
	
	
	
}
