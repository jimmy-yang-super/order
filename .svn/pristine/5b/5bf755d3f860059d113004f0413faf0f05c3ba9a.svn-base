package com.jixiang.argo.lvzheng.controllers;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.actionresult.JSONActionResult;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.union.user.model.Employee;
import com.jixiang.union.user.tools.LoginTool;
import com.jx.argo.ActionResult;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.FollowupEntity;
import com.jx.service.newcore.entity.SorderEntity;

@Path("/interface")
public class InterfaceController extends AbstractController{
	@Path("/order/noProcessing")
	public  ActionResult noProcessingOrder(){
		JSONObject jo = new JSONObject();
		String queryString = beat().getRequest().getQueryString();
		String callback = getValue(queryString, "jsoncallback");
		boolean isHave = false;
		try {
			Employee empVo = LoginTool.getLoginUserInfo(beat().getRequest());
			if(empVo != null){
				String condition = "orderstate=3 AND empid="+empVo.getEmpid();
				List<SorderEntity> oList = RSBLL.getstance().getSorderService().getSorderEntityListBypage(condition, 1, 10, "");
				if(CollectionUtils.isNotEmpty(oList)){
					for(SorderEntity order : oList){
						long orderId = order.getOrderid();
						String query = "orderid="+orderId+" AND empid="+empVo.getEmpid();
						List<FollowupEntity> fList = RSBLL.getstance().getFollowupService().getFollowupEntity(query, 1, 1, "");
						if(CollectionUtils.isEmpty(fList)){
							isHave = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jo.put("result", isHave);
		return new JSONActionResult(callback==null?jo.toString():callback+"("+jo.toString()+")");
	}
	
	public String getValue(String queryString,String name){
		if(StringUtils.isNotBlank(queryString) && StringUtils.isNotBlank(name)){
			String[] keyValueArr = queryString.split("&");
			for(int i=0;i<keyValueArr.length;i++){
				String key = keyValueArr[i].split("=")[0];
				if(name.equals(key)){
					return keyValueArr[i].split("=")[1];
				}
			}
		}
		return null;
	}
}
