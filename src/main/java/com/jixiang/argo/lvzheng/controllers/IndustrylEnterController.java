package com.jixiang.argo.lvzheng.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;

import com.jixiang.argo.lvzheng.service.IndustrylEnterService;
import com.jixiang.argo.lvzheng.utils.BusinessApplyUtils;
import com.jixiang.argo.lvzheng.utils.GrapPageLogin;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
@Path("/order")
public class IndustrylEnterController extends AbstractController{
	IndustrylEnterService induService = IndustrylEnterService.getInstance();
	@Path("/inputValidateCode")
	public ActionResult inputValidateCode(){
		String sessionId;
		try {
			String isSource =  beat().getRequest().getParameter("isSource");
			if(StringUtil.isEmpty(isSource)){
				model().add("isSource", isSource);
			}
			model().add("valipath", "/images/validate/code.jpg");
			sessionId = BusinessApplyUtils.getSessionId();
			if(sessionId == ""){
				model().add("errorMsg", "因访问频繁，服务器重置了访问地址，请过一段时间再次访问，谢谢合作！！！");
				return view("order/validatecode");
			}
			GrapPageLogin grappage = new GrapPageLogin();
			InputStream verifyCodeInput = grappage.getVerifyCode(sessionId);
			String path = induService.getValidateCodePostion();
			path = path +"/code.jpg";
			File file = new File(path);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			IOUtils.copy(verifyCodeInput, fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			model().add("sessionId", sessionId);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view("order/validatecode");
	}
	@Path("/grapRemoateDate")
	public ActionResult grapRemoateDate(){
		return new ActionResult() {
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String sessionId = beat().getRequest().getParameter("sessionid");
				String verifyCode = beat().getRequest().getParameter("validatecode");
				String isSource = beat().getRequest().getParameter("isSource");//表示处理不同业务的标记
				GrapPageLogin grappage = new GrapPageLogin();
				String text ="";
				try {
					text = grappage.postLogin(sessionId,"gszc12","wy123456", verifyCode.trim());
					// 1 表示提取远程业务范围数据
					if("1".equals(isSource)){
						//text = induService.dealRemoteBusiness(sessionId);
					}
					if("2".equals(isSource)){
						//公司名称check
						/*String flag = induService.checkGsmingc(sessionId);
					    if(StringUtil.isEmpty(flag)){
					    	text = flag;//表示验证失败
					    }*/
						//获得用户公司核名的进度
						//induService.getUserTransaction(sessionId);
						//提交公司审核
						text = induService.submitGszcDate(sessionId);
						//保存注册用户
						//text = induService.saveRegistUser(sessionId, "zhaoqingsheng");
					}
					try {
						beat().getResponse().getWriter().print(text);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
}
