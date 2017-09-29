package com.jixiang.argo.lvzheng.controllers;


import java.util.HashMap;
import java.util.Map;

import com.jixiang.argo.lvzheng.service.OrderService;
import com.jixiang.argo.lvzheng.service.impl.OrderServiceImpl;
import com.jixiang.union.interceptor.AutherCheck;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
/**
 * 客服订单控制类
 * @author 国明
 */
@Path("/order")
public class CustomerOrderController extends AbstractController {
	OrderService orderService = new OrderServiceImpl();
	
	/****
	 * 客服的线上订单查询入口 Start
	 * @return
	 */
	@Path("/hezuo58")
	@AutherCheck
	public ActionResult upline58() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "hezuo58",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/hezuo58");
		return view("/order/hezuo58");
	}
	
	@Path("/hezuo58/{pageno:\\d+}")
	@AutherCheck
	public ActionResult uplinefy58(Integer pageno) {
		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
		Map<String, String> queryOption = new HashMap<String, String>();
		if(paramOneValue!=null && (paramOneType !="" && paramOneType!="null")){
			queryOption.put("paramOneValue", paramOneValue);
			queryOption.put("paramOneType", paramOneType);
			queryOption.put("paramTwoValue", paramTwoValue);
		}
		orderService.getOrderList(beat(), "hezuo58",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/hezuo58");
		return view("/order/hezuo58");
	}
	/**** 客服的线上订单查询入口 END ***/
	
	
	/****
	 * 客服的线上订单查询入口 Start
	 * @return
	 */
	@Path("/upline")
	@AutherCheck
	public ActionResult upline() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "upline",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/upline");
		return view("/order/upline");
	}
	
	@Path("/upline/{pageno:\\d+}")
	@AutherCheck
	public ActionResult uplinefy(Integer pageno) {
		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
		Map<String, String> queryOption = new HashMap<String, String>();
		if(paramOneValue!=null && (paramOneType !="" && paramOneType!="null")){
			queryOption.put("paramOneValue", paramOneValue);
			queryOption.put("paramOneType", paramOneType);
			queryOption.put("paramTwoValue", paramTwoValue);
		}
		orderService.getOrderList(beat(), "upline",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/upline");
		return view("/order/upline");
	}
	/**** 客服的线上订单查询入口 END ***/
	
	
	
	/****
	 * 
	 * 客服的线下订单入口  Start
	 * @return
	 */
	@Path("/downline")
	@AutherCheck
	public ActionResult downline() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "downline",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/downline");
		return view("/order/downline");
	}
	
	@Path("/downline/{pageno:\\d+}")
	@AutherCheck
	public ActionResult qzddfy(Integer pageno) {
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
		orderService.getOrderList(beat(), "downline",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/downline");
		return view("/order/downline");
	}
	
	/****  客服的线下订单入口  END*******************/
	
	
	
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
	
	/*****
	 * 客服已派订单入口 Start
	 * @return
	 */
	@Path("/haveBeenSent")
	@AutherCheck
	public ActionResult haveBeenSent() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "haveBeenSent",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/haveBeenSent");
		return view("/order/haveBeenSent");
	}
	
	@Path("/haveBeenSent/{pageno:\\d+}")
	@AutherCheck
	public ActionResult haveBeenSentfy(Integer pageno) {
//		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
//		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
//		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
//		Map<String, String> queryOption = new HashMap<String, String>();
//		if(paramOneValue!=null && (paramOneType !="" && paramOneType!="null")){
//			queryOption.put("paramOneValue", paramOneValue);
//			queryOption.put("paramOneType", paramOneType);
//			queryOption.put("paramTwoValue", paramTwoValue);
//		}
		Map<String, String> queryOption = getParamsMap(beat());
//		System.out.println("参数1类型："+paramOneType+"参数1值："+paramOneValue+"参数2值:"+paramTwoValue+"========");
		orderService.getOrderList(beat(), "haveBeenSent",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/haveBeenSent");
		return view("/order/haveBeenSent");
	}
	/***** 客服已派订单入口 end  *******************/
	
	
	
	@Path("/kfcancelDD")
	@AutherCheck
	public ActionResult kfcancelDD() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "kfcancelDD",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/kfcancelDD");
		return view("/order/kfcancelDD");
	}
	
	@Path("/kfcancelDD/{pageno:\\d+}")
	@AutherCheck
	public ActionResult kfcancelDD(Integer pageno) {
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
		orderService.getOrderList(beat(), "kfcancelDD",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/kfcancelDD");
		return view("/order/kfcancelDD");
	}
	
	@Path("/twoorder")
	@AutherCheck
	public ActionResult twoorder() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "twoorder",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/twoorder");
		return view("/order/twoorder");
	}
	
	@Path("/twoorder/{pageno:\\d+}")
	@AutherCheck
	public ActionResult twoorder(Integer pageno) {
		Map<String, String> queryOption = getParamsMap(beat());
//		System.out.println("参数1类型："+paramOneType+"参数1值："+paramOneValue+"参数2值:"+paramTwoValue+"========");
		orderService.getOrderList(beat(), "twoorder",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/twoorder");
		return view("/order/twoorder");
	}
	
}
