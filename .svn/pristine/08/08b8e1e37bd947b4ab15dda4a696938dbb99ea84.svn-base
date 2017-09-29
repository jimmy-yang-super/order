package com.jixiang.argo.lvzheng.controllers;


import java.util.HashMap;
import java.util.Map;

import com.jixiang.argo.lvzheng.service.OnlineCsService;
import com.jixiang.argo.lvzheng.service.impl.OnlineCsServiceImpl;
import com.jixiang.union.interceptor.AutherCheck;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
/**
 * 客服订单控制类
 * @author 国明
 */
@Path("/bl")
public class BlackFaceController extends AbstractController {
	OnlineCsService orderService = new OnlineCsServiceImpl();
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
	
	@Path("/onlinecs")
	@AutherCheck
	public ActionResult onlinecs() {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getContactList(beat(),queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/blackface/onlinecs");
		return view("/blackface/onlinecs");
	}
	
	@Path("/onlinecs/{pageno:\\d+}")
	@AutherCheck
	public ActionResult onlinecs(Integer pageno) {
		Map<String, String> queryOption = getParamsMap(beat());
//		System.out.println("参数1类型："+paramOneType+"参数1值："+paramOneValue+"参数2值:"+paramTwoValue+"========");
		orderService.getContactList(beat(),queryOption,pageno);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/blackface/onlinecs");
		return view("/blackface/onlinecs");
	}
	
}
