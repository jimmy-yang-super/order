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
 * 法务订单查询入口
 * @author   to_be_signed
 */
@Path("/order")
public class JusticeOrderController extends AbstractController {

	OrderService orderService = new OrderServiceImpl();
	
	/**** * 法务待签订单入口 strat  */
	/*@Path("/fwtobesigned")
	@AutherCheck
	public ActionResult fwtobesigned() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "fwtobesigned",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fwtobesigned");
		return view("/order/fwtobesigned");
	}
	
	@Path("/fwtobesigned/{pageno:\\d+}")
	@AutherCheck
	public ActionResult fwtobesigned(Integer pageno) {
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
		orderService.getOrderList(beat(), "fwtobesigned",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fwtobesigned");
		return view("/order/fwtobesigned");
	}*/
	/**** * 法务签单订单入口 end  */
	
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
	
	/**** * 咨询顾问待签订单入口 strat  */
	@Path("/zxgwtobesigned")
	@AutherCheck
	public ActionResult fwtobesigned() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "zxgwtobesigned",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/zxgwtobesigned");
		return view("/order/zxgwpages/zxgwtobesigned");
	}
	
	@Path("/zxgwtobesigned/{pageno:\\d+}")
	@AutherCheck
	public ActionResult fwtobesigned(Integer pageno) {
//		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
//		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
//		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
		Map<String, String> queryOption = getParamsMap(beat());
//		if(paramOneValue!=null && (paramOneType !="" && paramOneType!="null")){  
//			queryOption.put("paramOneValue", paramOneValue);
//			queryOption.put("paramOneType", paramOneType);
//			queryOption.put("paramTwoValue", paramTwoValue);
//		}
//		System.out.println("参数1类型："+paramOneType+"参数1值："+paramOneValue+"参数2值:"+paramTwoValue+"========");
		orderService.getOrderList(beat(), "zxgwtobesigned",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/zxgwtobesigned");
		return view("/order/zxgwpages/zxgwtobesigned");
	}
	/**** * 咨询顾问签单订单入口 end  */
	
	/**** * 咨询顾问完款订单入口 strat  */
	@Path("/zxgwovermoney")
	@AutherCheck
	public ActionResult zxgwovermoney() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderForFinanceList(beat(), "zxgwovermoney",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/zxgwovermoney");
		return view("/order/zxgwpages/zxgwoverMoney");
	}
	
	@Path("/zxgwovermoney/{pageno:\\d+}")
	@AutherCheck
	public ActionResult zxgwovermoney(Integer pageno) {
		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
		String paramTwoValue2 =(String) beat().getRequest().getSession().getAttribute("paramTwoValue2");
		Map<String, String> queryOption = new HashMap<String, String>();
		if(paramOneValue!=null && (paramOneType !="" && paramOneType!="null")){
			queryOption.put("paramOneValue", paramOneValue);
			queryOption.put("paramOneType", paramOneType);
			queryOption.put("paramTwoValue", paramTwoValue);
			queryOption.put("paramTwoValue2", paramTwoValue2);
		}
//		System.out.println("参数1类型："+paramOneType+"参数1值："+paramOneValue+"参数2值:"+paramTwoValue+"========");
		orderService.getOrderForFinanceList(beat(), "zxgwovermoney",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/zxgwovermoney");
		return view("/order/zxgwpages/zxgwoverMoney");
	}
	/**** * 咨询顾问完款订单入口 end  */
	
	
	/**** * 咨询顾问待付款订单入口 strat  */
	@Path("/zxgwforPayment")
	@AutherCheck
	public ActionResult zxgwforPayment() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderForFinanceList(beat(), "zxgwforPayment",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/zxgwforPayment");
		return view("/order/zxgwpages/zxgwforPayment");
	}
	
	@Path("/zxgwforPayment/{pageno:\\d+}")
	@AutherCheck
	public ActionResult zxgwforPayment(Integer pageno) {
//		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
//		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
//		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
//		String paramTwoValue2 =(String) beat().getRequest().getSession().getAttribute("paramTwoValue2");
//		Map<String, String> queryOption = new HashMap<String, String>();
//		if(paramOneValue!=null && (paramOneType !="" && paramOneType!="null")){
//			queryOption.put("paramOneValue", paramOneValue);
//			queryOption.put("paramOneType", paramOneType);
//			queryOption.put("paramTwoValue", paramTwoValue);
//			queryOption.put("paramTwoValue2", paramTwoValue2);
//		}
		Map<String, String> queryOption = getParamsMap(beat());
//		System.out.println("参数1类型："+paramOneType+"参数1值："+paramOneValue+"参数2值:"+paramTwoValue+"========");
		orderService.getOrderForFinanceList(beat(), "zxgwforPayment",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/zxgwforPayment");
		return view("/order/zxgwpages/zxgwforPayment");
	}
	/**** * 咨询顾问待付款订单入口 end  */
	
	/**** * 咨询顾问待付款订单入口 strat  */
	@Path("/zxgwforPaymentForTerrace")
	@AutherCheck
	public ActionResult zxgwforPaymentForTerrace() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderForFinanceList(beat(), "zxgwforPaymentForTerrace",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/zxgwforPaymentForTerrace");
		return view("/order/zxgwpages/zxgwforPaymentForTerrace");
	}
	
	@Path("/zxgwforPaymentForTerrace/{pageno:\\d+}")
	@AutherCheck
	public ActionResult zxgwforPaymentForTerrace(Integer pageno) {
//		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
//		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
//		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
//		String paramTwoValue2 =(String) beat().getRequest().getSession().getAttribute("paramTwoValue2");
//		Map<String, String> queryOption = new HashMap<String, String>();
//		if(paramOneValue!=null && (paramOneType !="" && paramOneType!="null")){
//			queryOption.put("paramOneValue", paramOneValue);
//			queryOption.put("paramOneType", paramOneType);
//			queryOption.put("paramTwoValue", paramTwoValue);
//			queryOption.put("paramTwoValue2", paramTwoValue2);
//		}
		Map<String, String> queryOption = getParamsMap(beat());
//		System.out.println("参数1类型："+paramOneType+"参数1值："+paramOneValue+"参数2值:"+paramTwoValue+"========");
		orderService.getOrderForFinanceList(beat(), "zxgwforPaymentForTerrace",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/zxgwforPaymentForTerrace");
		return view("/order/zxgwpages/zxgwforPaymentForTerrace");
	}
	/**** * 咨询顾问待付款订单入口 end  */
	
	
	
	
	/**** * 法务服务中订单入口 start  */
	@Path("/fwinservice")
	@AutherCheck
	public ActionResult fwinservice() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "fwinservice",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fwinservice");
		return view("/order/fwinservice");
	}
	
	@Path("/fwinservice/{pageno:\\d+}")
	@AutherCheck
	public ActionResult fwinservice(Integer pageno) {
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
		orderService.getOrderList(beat(), "fwinservice",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fwinservice");
		return view("/order/fwinservice");
	}
	/**** * 法务服务中订单入口 end  */
	
	/**** * 法务服务完成订单入口 start  */
	@Path("/fwcompleteservice")
	@AutherCheck
	public ActionResult fwcompleteservice() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "fwcompleteservice",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fwcompleteservice");
		return view("/order/fwcompleteservice");
	}
	
	@Path("/fwcompleteservice/{pageno:\\d+}")
	@AutherCheck
	public ActionResult fwcompleteservice(Integer pageno) {
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
		orderService.getOrderList(beat(), "fwcompleteservice",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fwcompleteservice");
		return view("/order/fwcompleteservice");
	}
	/**** * 法务服务完成订单入口 end  */
	
	
	/**** * 法务完结订单入口 start  */
	@Path("/fwend")
	@AutherCheck
	public ActionResult fwend() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "fwend",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fwend");
		return view("/order/fwend");
	}
	
	@Path("/fwend/{pageno:\\d+}")
	@AutherCheck
	public ActionResult fwend(Integer pageno) {
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
		orderService.getOrderList(beat(), "fwend",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fwend");
		return view("/order/fwend");
	}
	/**** * 法务完结订单入口 end  */
	
	
	/**** * 法务取消订单入口 start  */
	@Path("/fwcancelDD")
	@AutherCheck
	public ActionResult fwcancelDD() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "fwcancelDD",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fwcancelDD");
		return view("/order/fwcancelDD");
	}
	
	@Path("/fwcancelDD/{pageno:\\d+}")
	@AutherCheck
	public ActionResult fwcancelDD(Integer pageno) {
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
		orderService.getOrderList(beat(), "fwcancelDD",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fwcancelDD");
		return view("/order/fwcancelDD");
	}
	/**** 法务取消订单入口 end  *************/
	
	
	/*****
	 * 法务助手的服务中订单Start
	 * @return
	 */
	@Path("/fzinservice")
	@AutherCheck
	public ActionResult fzinservice() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "fzinservice",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fzinservice");
		return view("/order/fzinservice");
	}
	
	@Path("/fzinservice/{pageno:\\d+}")
	@AutherCheck
	public ActionResult fzinservice(Integer pageno) {
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
		orderService.getOrderList(beat(), "fzinservice",queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/fzinservice");
		return view("/order/fzinservice");
	}
	/*****法务助手的服务中订单end******************************/
	
	
	
}
