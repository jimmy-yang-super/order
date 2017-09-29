package com.jixiang.argo.lvzheng.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.actionresult.JSONActionResult;
import com.jixiang.argo.lvzheng.annotation.CheckCookie;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.service.OrderService;
import com.jixiang.argo.lvzheng.service.impl.OrderServiceImpl;
import com.jixiang.argo.lvzheng.utils.JavaDemoHttp;
import com.jixiang.argo.lvzheng.utils.Timers;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.vo.OrderInfoVO;
import com.jx.argo.ActionResult;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.CommentEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.newcore.entity.VisitEntity;
/**
 * 回访类
 * @author bruce 
 *  */
@Path("/visit")
@CheckCookie
public class VisitController extends AbstractController {
	OrderService orderService = new OrderServiceImpl();
	
	/****
	 * 回访订单的列表
	 * @return
	 * @throws Exception 
	 */
	@Path("/list")
	// @AutherCheck
	public ActionResult list() throws Exception {
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "visit",queryOption,0);
		
		String paramTwoValue = beat().getRequest().getParameter("paramTwoValue")==null?"":beat().getRequest().getParameter("paramTwoValue");
		
		
		//查询是包含回访状态de查询
		if("000".equals(paramTwoValue)){
			List<OrderInfoVO> volist_ = new ArrayList<OrderInfoVO>();
			List<OrderInfoVO> volist  = (List<OrderInfoVO>) beat().getModel().get("orderList");
			for (int i = 0; i < volist.size(); i++) {
				String flag = volist.get(i).getHuifangFlag();
				if("未回访".equals(flag)){
					volist_.add(volist.get(i));
				}
			}
			beat().getModel().add("orderList", volist_);
			
		}else if("111".equals(paramTwoValue)){
			List<OrderInfoVO> volist_ = new ArrayList<OrderInfoVO>();
			List<OrderInfoVO> volist  = (List<OrderInfoVO>) beat().getModel().get("orderList");
			for (int i = 0; i < volist.size(); i++) {
				String flag = volist.get(i).getHuifangFlag();
				if("已回访".equals(flag)){
					volist_.add(volist.get(i));
				}
			}
			beat().getModel().add("orderList", volist_);
		}
		//查询是包含回访状态de查询
		
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/visit/list");
		return view("/order/visit");
	}

	
	@Path("/list/{pageno:\\d+}")
	// @AutherCheck
	public ActionResult list(Integer pageno) {
		String paramOneValue =(String) beat().getRequest().getSession().getAttribute("paramOneValue");
		String paramOneType =(String) beat().getRequest().getSession().getAttribute("paramOneType");
		String paramTwoValue =(String) beat().getRequest().getSession().getAttribute("paramTwoValue");
		Map<String, String> queryOption = new HashMap<String, String>();
		if(paramOneValue!=null && (paramOneType !="" && paramOneType!="null")){
			queryOption.put("paramOneValue", paramOneValue);
			queryOption.put("paramOneType", paramOneType);
			queryOption.put("paramTwoValue", paramTwoValue);
		}
		orderService.getOrderList(beat(), "visit",queryOption,pageno);
		
		
		
		      //查询是包含回访状态de查询
				if("000".equals(paramTwoValue)){
					List<OrderInfoVO> volist_ = new ArrayList<OrderInfoVO>();
					List<OrderInfoVO> volist  = (List<OrderInfoVO>) beat().getModel().get("orderList");
					for (int i = 0; i < volist.size(); i++) {
						String flag = volist.get(i).getHuifangFlag();
						if("未回访".equals(flag)){
							volist_.add(volist.get(i));
						}
					}
					beat().getModel().add("orderList", volist_);
				}else if("111".equals(paramTwoValue)){
					List<OrderInfoVO> volist_ = new ArrayList<OrderInfoVO>();
					List<OrderInfoVO> volist  = (List<OrderInfoVO>) beat().getModel().get("orderList");
					for (int i = 0; i < volist.size(); i++) {
						String flag = volist.get(i).getHuifangFlag();
						if("已回访".equals(flag)){
							volist_.add(volist.get(i));
						}
					}
					beat().getModel().add("orderList", volist_);
				}
				//查询是包含回访状态de查询
				//分页需要跳转的页面
				beat().getModel().add("currenturl", "/visit/list");
		return view("/order/visit");
	}
	
	
	
	/***
	 * 回访详情信息
	 * @return
	 */
	@Path("/view")
//	@AutherCheck
	public ActionResult view(){
		String orderid = beat().getRequest().getParameter("orderid");
		String visitid = beat().getRequest().getParameter("visitid");
		String empid   = beat().getRequest().getParameter("empid");
		System.out.println(visitid+"<------------visitid");
		try {
			//查询评论
			if(StringUtils.isNotEmpty(orderid)){
			    CommentEntity cmt = getCommentModelByOrderid(orderid);
			    if(cmt!=null){
			    	model().add("cmt", cmt);
			    	model().add("star", cmt.getStar());
			    	System.out.println(cmt.getStar()+"<------------star");
			    }else{
			    	model().add("cmtFlag", "no");
			    }
			    
			    model().add("orderid", orderid);
			}
			//查询回访
			if(StringUtils.isNotEmpty(visitid)&&!"0".equals(visitid)){
				VisitEntity ve = RSBLL.getstance().getVisitService().getVisitEntityById(Long.valueOf(visitid));
				if(ve!=null){
			      model().add("ve", ve);
			    //取得登陆用户姓名set
			      getUserName(ve);
				}
				
			}else{
				model().add("veFlag", "no"); 
			}
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return view("/order/visitDiv");
	}


	
	
	
	/***
	 * 回访详情信息
	 * @return
	 */
	@Path("/addVisit")
//	@AutherCheck
	public ActionResult addVisit(){
		String orderid = beat().getRequest().getParameter("orderid");
		String level = beat().getRequest().getParameter("level");
		String content   = beat().getRequest().getParameter("content");
		String result = "success";
		try {
			
			long userid = UtilsHelper.getLoginId(beat());
			
			VisitEntity model = new VisitEntity();
			model.setLevel(level);
			model.setContent(content);
			if(StringUtils.isNotEmpty(orderid)){
				model.setOrderid( Long.valueOf(orderid) );
			}
			//TODO...
			if(userid>0){
				model.setUserid(userid);
			}	
//         	model.setUserid(Long.valueOf("123456"));
			model.setAddtime(Timers.nowTime());
			
			RSBLL.getstance().getVisitService().addVisitEntity(model);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = "error";
			e.printStackTrace();
		}
		
		return new JSONActionResult(result);
	}
	
	/**
	 * 根据orderid获取评价实体
	 * @param orderid
	 * @throws Exception
	 */
	private CommentEntity getCommentModelByOrderid(String orderid) throws Exception {
		CommentEntity ce = null;
		List<CommentEntity> list = RSBLL.getstance().getCommentService().getCommentEntity(" orderid="+orderid, 1, 1, "commentid");
		if(list!=null){
			if(list.size()>0){
			    ce = list.get(0);
			}
		}
		
		return ce;
	}
	/**
	 * @param ve
	 * @throws Exception
	 */
	private void getUserName(VisitEntity ve) throws Exception {
		//取得登陆用户姓名set
		  EmployersEntity model = RSBLL.getstance().getEmployersService().getEmployersEntityById(ve.getUserid());

				  
				  
		  if(model!=null){
			  String username =model.getRealname();
			  if(StringUtils.isNotEmpty(username)){
				  model().add("username", username);
			  }
		  }
	}
//	@Path("/visitSendMessage/{orderid:\\d+}")
//	private ActionResult visitSendMessage(String orderid){
//		int temp = -1;
//		try {
//			SorderEntity se = RSBLL.getstance().getSorderService().getSorderEntityByid(Long.valueOf(orderid));
//			LoginEntity userEntity = RSBLL.getstance().getLoginService().getLoginEntityById(se.getUserid());
//			String phone = userEntity.getUserphone();
//			String content = "Hey,Boss.您的忠实企业管家来报：开公司/办财税/护知产/变工商/拓海外，小微样样行。详情访问m.lvzheng.com。电话13366021349。";
//			temp = JavaDemoHttp.sendmsg(phone, content);
//			System.out.println(temp+"发送短信返回的编码===========================");
//			if(temp == 2){
//				return new JSONActionResult("{\"success\":true}");
//			}else{
//				return new JSONActionResult("{\"success\":false}");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new JSONActionResult("");
//		}
//	}
}
