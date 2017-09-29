package com.jixiang.argo.lvzheng.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.actionresult.JSONActionResult;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.service.OrderService;
import com.jixiang.argo.lvzheng.service.impl.OrderServiceImpl;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.Timers;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jx.argo.ActionResult;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.SuggestEntity;
import com.jx.service.newcore.entity.SuggestFollowEntity;
import com.jx.service.newcore.entity.VisitEntity;
/**
 * 投诉建议类
 * @author bruce 
 *  */
@Path("/complain")
public class ComplainController extends AbstractController {
	OrderService orderService = new OrderServiceImpl();
	
	/****
	 * 投诉建议列表
	 * @return
	 * @throws Exception 
	 */
	@Path("/list")
	// @AutherCheck
	public ActionResult list() throws Exception {
		Map<String, String> queryOption = new HashMap<String, String>();
		
		setParam(0, queryOption);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/complain/list");
		model().add("seltype", KVMap.ts_types) ;
		model().add("qudao", KVMap.qudao) ;
		model().add("T_status", KVMap.ts_status) ;
		return view("/order/complain");
	}


	
	
	@Path("/list/{pageno:\\d+}")
	// @AutherCheck
	public ActionResult list(Integer pageno) {
		Map<String, String> queryOption = new HashMap<String, String>();
		
		String phone = (String) beat().getRequest().getSession().getAttribute("phone");
		String type = (String)beat().getRequest().getSession().getAttribute("type");
		String startTime = (String)beat().getRequest().getSession().getAttribute("startTime");
		String endTime = (String)beat().getRequest().getSession().getAttribute("endTime");
		String qudao_param = (String)beat().getRequest().getSession().getAttribute("qudao_param");
		String status_param = (String)beat().getRequest().getSession().getAttribute("status_param");

		queryOption.put("phone",     phone);
		queryOption.put("type",      type );
		queryOption.put("startTime", startTime);
		queryOption.put("endTime",   endTime );
		
		queryOption.put("qudao_param",   qudao_param );
		queryOption.put("status_param",   status_param );
		
		setParam(pageno, queryOption);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/complain/list");
		model().add("seltype", KVMap.ts_types) ;
		model().add("qudao", KVMap.qudao) ;
		model().add("T_status", KVMap.ts_status) ;
		return view("/order/complain");
	}
	
	
	
	/***
	 * 跟进详情信息
	 * @return
	 */
	@Path("/view")
//	@AutherCheck
	public ActionResult view(){
		String suggestid = beat().getRequest().getParameter("suggestid");
		try {
			
			if(StringUtils.isNotEmpty(suggestid)){
				//查询投诉内容
				SuggestEntity model = RSBLL.getstance().getSuggestService().getSuggestEntityById(Long.valueOf(suggestid));
				
				//查询处理列表
				List<SuggestFollowEntity> list = RSBLL.getstance().getSuggestFollowService().getSuggestFollowEntity(" suggestid="+suggestid, 1, 10, "handletime desc");

				if(list!=null){
					if(list.size()>0){
						for (int i = 0; i < list.size(); i++) {
							Long userid = list.get(i).getUserid();
							//取得登陆用户姓名set
							String username = getUserName(userid);
							//暂时把显示div的信息设置到handletype里
							list.get(i).setHandletype(username);
						}
					}
				}
				beat().getModel().add("model", model);
				beat().getModel().add("list", list);
				
			    
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return view("/order/complainDiv");
	}
	
	
	/***
	 * 编辑本条信息
	 * @return
	 */
	@Path("/edit")
//	@AutherCheck
	public ActionResult edit(){
		String suggestid = beat().getRequest().getParameter("suggestid");
		try {
			
			if(StringUtils.isNotEmpty(suggestid)){
				//查询投诉内容
				SuggestEntity model = RSBLL.getstance().getSuggestService().getSuggestEntityById(Long.valueOf(suggestid));
				
				beat().getModel().add("model", model);
				beat().getModel().add("seltype", KVMap.ts_types) ;
				beat().getModel().add("qudao", KVMap.qudao) ;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return view("/order/complainEditDiv");
	}
	
	
	
	/***
	 * 保存编辑的信息
	 * @return
	 */
	@Path("/saveEdit")
//	@AutherCheck
	public ActionResult saveEdit(){
		String qudao   = beat().getRequest().getParameter("qudao");
		String suggestid   = beat().getRequest().getParameter("suggestid");
		String type   = beat().getRequest().getParameter("type");
		String resperson   = beat().getRequest().getParameter("resperson");
		String result = "success";
		try {
			
			if(StringUtils.isNotEmpty(suggestid)){
				SuggestEntity model = RSBLL.getstance().getSuggestService().getSuggestEntityById(Long.valueOf(suggestid));
				if(model!=null){
					model.setType(type);
					model.setResperson(resperson);
					model.setQudao(qudao);
					RSBLL.getstance().getSuggestService().updateSuggestEntity(model);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = "error";
			e.printStackTrace();
		}
		
		return new JSONActionResult(result);
	}
	
	
	/***
	 * 保存新增的的信息
	 * @return
	 */
	@Path("/saveNew")
//	@AutherCheck
	public ActionResult saveNew(){
		String qudao   = beat().getRequest().getParameter("J_sel_qudao_new");
		String phone   = beat().getRequest().getParameter("J_phone");
		String type   = beat().getRequest().getParameter("J_sel_type_new");
		String resperson   = beat().getRequest().getParameter("J_fzr");
		String content   =beat().getRequest().getParameter("J_content");
		String result = "success";
		try {
			
				SuggestEntity model = new SuggestEntity();
					model.setType(type);
					model.setResperson(resperson);
					model.setQudao(qudao);
					model.setContent("以跟进备注为准.");
					model.setMobile(phone);
					model.setStatus(KVMap.zhengzaichuli);
					model.setSugtime(Timers.nowTime());
					long suggestid = RSBLL.getstance().getSuggestService().addSuggestEntity(model);
			
					
					
					
					long userid = UtilsHelper.getLoginId(beat());
					
					SuggestFollowEntity sf = new SuggestFollowEntity();
					sf.setHandlemark(content);
					String now = Timers.nowTime();
					sf.setHandletime(now);
					sf.setSuggestid(suggestid);
					//TODO...
					if(userid>0){
						sf.setUserid(userid);
					}	
					
					RSBLL.getstance().getSuggestFollowService().addSuggestFollowEntity(sf);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = "error";
			e.printStackTrace();
		}
		
		return new JSONActionResult(result);
	}
	
	
	/***
	 * 回访详情信息
	 * @return
	 */
	@Path("/addFollow")
//	@AutherCheck
	public ActionResult addFollow(){
		String content   = beat().getRequest().getParameter("content");
		String suggestid   = beat().getRequest().getParameter("suggestid");
		String over   = beat().getRequest().getParameter("over");
		String result = "success";
		try {
			
			long userid = UtilsHelper.getLoginId(beat());
			
			SuggestFollowEntity model = new SuggestFollowEntity();
			model.setHandlemark(content);
			String now = Timers.nowTime();
			model.setHandletime(now);
			if(StringUtils.isNotEmpty(suggestid)){
				model.setSuggestid(Long.valueOf(suggestid));
			}
			//TODO...
			if(userid>0){
				model.setUserid(userid);
			}	
//         	model.setUserid(Long.valueOf("123456"));
			
			RSBLL.getstance().getSuggestFollowService().addSuggestFollowEntity(model);
			
			//完成时把suggest表的状态设置为已完成}要不就是处理中
			if(StringUtils.isNotEmpty(suggestid)){
				SuggestEntity sug = RSBLL.getstance().getSuggestService().getSuggestEntityById(Long.valueOf(suggestid));
				sug.setHandletime(now);
				if("yes".equals(over)){
					sug.setStatus(KVMap.yichuli);
				}else{
					sug.setStatus(KVMap.zhengzaichuli);
				}
				RSBLL.getstance().getSuggestService().updateSuggestEntity(sug);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = "error";
			e.printStackTrace();
		}
		
		return new JSONActionResult(result);
	}
	
	//------------------------------------------fuzhu------------------------------------------------------
	
	private List<SuggestEntity> setParam(int pageno,Map<String, String> queryOption){
		List<SuggestEntity> list = null;
		try{
			
		String phone = beat().getRequest().getParameter("phone")==null?"":beat().getRequest().getParameter("phone");
		String type = beat().getRequest().getParameter("type")==null?"":beat().getRequest().getParameter("type");
		String qudao_param = beat().getRequest().getParameter("J_qudao_id")==null?"":beat().getRequest().getParameter("J_qudao_id");
		String status_param = beat().getRequest().getParameter("J_status_id")==null?"":beat().getRequest().getParameter("J_status_id");
		String startTime = beat().getRequest().getParameter("startTime")==null?"":beat().getRequest().getParameter("startTime");
		String endTime = beat().getRequest().getParameter("endTime")==null?"":beat().getRequest().getParameter("endTime");
		
		//根据pageno判定是否为点击查询还是点击下一页触发的事件
		//如果点击查询则把查询条件放到session中，否则则去session中取出上一次的查询条件
		if(pageno==0){
			//把请求数据放到session中供翻页使用
			beat().getRequest().getSession().setAttribute("phone", phone);
			beat().getRequest().getSession().setAttribute("type", type);
			beat().getRequest().getSession().setAttribute("qudao_param", qudao_param);
			beat().getRequest().getSession().setAttribute("status_param", status_param);
			beat().getRequest().getSession().setAttribute("startTime", startTime);
			beat().getRequest().getSession().setAttribute("endTime", endTime);
		
		}else{
			phone = queryOption.get("phone")==null?"":queryOption.get("phone");
			type = queryOption.get("type")==null?"":queryOption.get("type");
			qudao_param = queryOption.get("qudao_param")==null?"":queryOption.get("qudao_param");
			status_param = queryOption.get("status_param")==null?"":queryOption.get("status_param");
			startTime = queryOption.get("startTime")==null?"":queryOption.get("startTime");
			endTime = queryOption.get("endTime")==null?"":queryOption.get("endTime");
		
		}
		
 
		String temp_pageindex = "1";
		if(pageno!=0){
			temp_pageindex = pageno+"";	
		}
		
		//-------拼装母单参数块
		String condition = getQueryCondition(phone, type,qudao_param,status_param, startTime, endTime);
		
//		System.out.println("==========查询条件为:"+condition+"==========");
		//-------拼装母单参数块 
		
		
		 // beat().getRequest().getParameter("pageindex");
		if(temp_pageindex==null||temp_pageindex.equals("")) temp_pageindex="1";
		
		//分页属性
		Integer pageindex = Integer.valueOf(temp_pageindex);
		Integer pagesize = Constants.houtai_page_size;
		String order = "sugtime desc"; //默认根据最后更新时间排序
		
		int ordercount =RSBLL.getstance().getSuggestService().getSuggestEntity(condition, 1, 100000, order).size(); //查询母单的总条数
		int pagecount = ordercount%pagesize == 0?ordercount/pagesize:ordercount/pagesize+1;
		
		
		
		//获得母订单列表信息
		list = RSBLL.getstance().getSuggestService().getSuggestEntity(condition, pageindex, pagesize, order);
		if(list!=null){
			if(list.size()>0){
				
				for (int i = 0; i < list.size(); i++) {
					SuggestEntity model = list.get(i);
					Long sugid = model.getSuggestid();
					String typeid = model.getType();
					if(typeid!="0"){
						
						model.setType(KVMap.ts_types.get(typeid));
					}
					//设置统计次数
					int fcount = RSBLL.getstance().getSuggestFollowService().getSuggestFollowEntity(" suggestid="+sugid, 1, 100, "followid").size();
					model.setFcount(fcount+"");
					//设置最后解决投诉的时间
					SuggestFollowEntity m = getLastOne(sugid);
					
					if(m!=null){
						model.setLastTime(m.getHandletime());
					}
					
				}
			}
		}
		
		//前台页面使用的参数START---------------
		beat().getModel().add("pagecount", pagecount);
		beat().getModel().add("pageIndex", pageindex);
		beat().getModel().add("phone", phone);  
		beat().getModel().add("type", type);
		beat().getModel().add("qudao_param", qudao_param);
		beat().getModel().add("status_param", status_param);
		beat().getModel().add("startTime", startTime);  
		beat().getModel().add("endTime", endTime);
		beat().getModel().add("list", list);
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
		return list;
		
		//前台页面使用的参数END-----------------
	}


	/**取得查询条件
	 * @param phone
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private String getQueryCondition(String phone, String type,String qudao_param,String status_param,String startTime, String endTime) {
		StringBuilder sb = new StringBuilder();
		sb.append(" 1=1");
		if(StringUtils.isNotEmpty(phone)){
			sb.append(" and mobile like '%"+phone+"%'");
		}
		if(StringUtils.isNotEmpty(type)&&!"0".equals(type)){
			sb.append(" and type ='"+type+"'");
		}
		if(StringUtils.isNotEmpty(qudao_param)&&!"0".equals(qudao_param)){
			sb.append(" and qudao ='"+qudao_param+"'");
		}
		if(StringUtils.isNotEmpty(status_param)&&!"0".equals(status_param)){
			sb.append(" and status ='"+status_param+"'");
		}
		if(StringUtils.isNotEmpty(startTime)){
			sb.append(" and  sugtime >='"+startTime+"'");
		}
		if(StringUtils.isNotEmpty(endTime)){
			sb.append(" and sugtime <='"+endTime+"'");
		}
		String condition = sb.toString();
		return condition;
	}
	
	
	/**取得最后一次的投诉跟进的实体
	 * @param sugid
	 * @throws Exception
	 */
	private SuggestFollowEntity getLastOne(Long sugid) throws Exception {
		SuggestFollowEntity m = null;
		List<SuggestFollowEntity> list1 =  RSBLL.getstance().getSuggestFollowService().getSuggestFollowEntity(" suggestid="+sugid, 1, 1, "handletime desc");
		if(list1!=null){
			if(list1.size()>0){
				m = list1.get(0);
			}
		}
		
		return m;
	}

	
	/**
	 * @param ve
	 * @throws Exception
	 */
	private String getUserName(Long userid) throws Exception {
		//取得登陆用户姓名set
		 String username  = "";  
		 EmployersEntity model = RSBLL.getstance().getEmployersService().getEmployersEntityById(userid);

			
		  if(model!=null){
			  username = model.getRealname();

		  }
		  return username;
	}
}
