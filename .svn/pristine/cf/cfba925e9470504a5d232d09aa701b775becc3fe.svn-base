package com.jixiang.argo.lvzheng.controllers;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.jixiang.argo.lvzheng.service.RegaddreTempleteService;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.ReginAddressTempleteEntity;
/**
 * 代理地址模板控制类
 * @author wuyin
 *
 */
@Path("/order")
public class RegaddreTempleteController extends AbstractController{
    @Path("/changeDizhzs")
	public ActionResult changeDizhzs(){
		return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				
				String dldzcode = beat().getRequest().getParameter("dldzcode");
				String text = "";
				if(StringUtil.isEmpty(dldzcode)){
					ReginAddressTempleteEntity tem = RegaddreTempleteService.getInstance().getReginAddressTempleteEntityBycode(dldzcode);
					if(tem != null){
						JSONObject obj = new JSONObject();
						text = (String)obj.toJSONString(tem);
					}
				}
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
