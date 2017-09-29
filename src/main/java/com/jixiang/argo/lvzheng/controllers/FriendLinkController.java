package com.jixiang.argo.lvzheng.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jixiang.argo.lvzheng.annotation.CheckCookie;
import com.jixiang.argo.lvzheng.service.DealFriendLinkService;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.FriendLinkEntity;
/**
 * 友情烂链接Controler层
 * @author wuyin
 *
 */
@Path("/order")
@CheckCookie
public class FriendLinkController extends AbstractController{

	@Path("/friendLinklist")
	public ActionResult friendLinklist(){
		Map<String, String> queryOption = new HashMap<String, String>();
		DealFriendLinkService.getInstance().getFriendLinkList(beat(), queryOption, 0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/friendLinklist");
		return view("order/friendlink");
	}
	
	@Path("/friendLinklist/{pageno:\\d+}")
	public ActionResult friendLinklistBypage(Integer pageno) {
		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
		Map<String, String> queryOption = new HashMap<String, String>();
		if(StringUtil.isEmpty(paramOneType)){
			queryOption.put("paramOneType", paramOneType);
		}
		DealFriendLinkService.getInstance().getFriendLinkList(beat(), queryOption, pageno);
		// 分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/friendLinklist");
		return view("order/friendlink");
	}
	
	@Path("/addFriendLink")
	public ActionResult addFriendLink(){
		//获得提交值
		String includepage = beat().getRequest().getParameter("includepage");
		String keyword = beat().getRequest().getParameter("keyword");
		String lur = beat().getRequest().getParameter("lur");
		long nowtime = new Date().getTime();
		long loginuser = UtilsHelper.getLoginId(beat());
		
		String friendLinkid = beat().getRequest().getParameter("friendLinkid");
		FriendLinkEntity entity = null;
		if(StringUtil.isEmpty(friendLinkid)){
			//更新
			long lid = Long.valueOf(friendLinkid);
			entity = DealFriendLinkService.getInstance().getFriendLinkEntityById(lid);
			if(entity != null){
				entity.setKeyword(keyword);
				entity.setEmpid(loginuser);
				int includepageIn = 1;
				if(StringUtil.isEmpty(includepage)){
					includepageIn = Integer.parseInt(includepage);
				}
				entity.setInclupage(includepageIn);
				entity.setUpdatetime(nowtime);
				entity.setLurl(lur);
				DealFriendLinkService.getInstance().updateFriendLinkEntity(entity);
			}
		}else{
			//添加
			entity = new FriendLinkEntity();
			entity.setKeyword(keyword);
			entity.setEmpid(loginuser);
			int includepageIn = 1;
			if(StringUtil.isEmpty(includepage)){
				includepageIn = Integer.parseInt(includepage);
			}
			entity.setInclupage(includepageIn);
			entity.setAddtime(nowtime);
			entity.setUpdatetime(nowtime);
			entity.setLurl(lur);
			entity.setStatus(1);
			DealFriendLinkService.getInstance().addFriendLinkEntity(entity);
		}
		
		return redirect("/order/friendLinklist");
	}
	@Path("/friendLinkinfo")
    public ActionResult friendLinkinfo(){
    	return new ActionResult() {
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String lid = beat().getRequest().getParameter("lid");
				
				String text ="";
				if(StringUtil.isEmpty(lid)){
					long llid = Long.valueOf(lid);
					FriendLinkEntity entity = DealFriendLinkService.getInstance().getFriendLinkEntityById(llid);
					if(entity != null){
						text = entity.getInclupage()+"_"+entity.getKeyword()+"_"+entity.getLurl()+"_"+KVMap.showpage.get(entity.getInclupage()+"");
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
	@Path("/deletefriendLink/{lid:\\S+}")
	public ActionResult deletefriendLink(String lid ){
		if(StringUtil.isEmpty(lid)){
			long llid = Long.valueOf(lid);
			DealFriendLinkService.getInstance().deleteFriendLinkEntityById(llid);
		}
		return redirect("/order/friendLinklist");
	}
}
