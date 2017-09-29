package com.jixiang.argo.lvzheng.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.actionresult.JSONActionResult;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.service.BusinessLibraryService;
import com.jixiang.argo.lvzheng.service.OrderService;
import com.jixiang.argo.lvzheng.service.impl.OrderServiceImpl;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.Timers;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jx.argo.ActionResult;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.newcore.entity.SuggestEntity;
import com.jx.service.newcore.entity.SuggestFollowEntity;
import com.jx.service.newcore.entity.TaskEntity;
/**
 * 外勤任务类
 * @author bruce 
 *  */
@Path("/task")
public class TaskController extends AbstractController {
	OrderService orderService = new OrderServiceImpl();
	
	
	/***
	 * 新建任务
	 * @return
	 */
	@Path("/addTask")
//	@AutherCheck
	public ActionResult addTask(){
		
		
		//上面的信息列表
		//服务类型
		beat().getModel().add("stype", KVMap.servicetype);
		//区域信息

		List<AreasEntity> bjArea =BusinessLibraryService.getInstance().getAreasEntity("1");
		
		model().add("bjArea", bjArea);
		
		List<AreasEntity> szArea =BusinessLibraryService.getInstance().getAreasEntity("2");
		
		model().add("szArea", szArea);
		//外勤地址
		beat().getModel().add("wq_address", KVMap.wq_address);
		
		//拉取下面的服务中订单列表
		Map<String, String> queryOption = new HashMap<String, String>();
		orderService.getOrderList(beat(), "sysinservice",queryOption,0);
		//分页需要跳转的页面
		beat().getModel().add("currenturl", "/order/sysinservice");
		
		return view("/order/addTask");
	}
	
	/***
	 * 新建任务从订单拉取信息
	 * @return
	 */
	@Path("/task_add")
//	@AutherCheck
	public ActionResult task_add(){
		
		
		String orderid = beat().getRequest().getParameter("orderid");
		if(StringUtils.isNotEmpty(orderid)){
			
			try {
		          SorderEntity model =RSBLL.getstance().getSorderService().getSorderEntityByid(Long.valueOf(orderid));
		           model().add("order", model) ;
		          
		          
		        //查询客户的信息表
					LoginEntity loginEntity = RSBLL.getstance().getLoginService().getLoginEntityById(model.getUserid());
					if(null != loginEntity){
						model().add("userinfo", loginEntity);
					}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//上面的信息列表
		//服务类型
		beat().getModel().add("stype", KVMap.servicetype);
		//区域信息

		List<AreasEntity> bjArea =BusinessLibraryService.getInstance().getAreasEntity("1");
		
		model().add("bjArea", bjArea);
		
		List<AreasEntity> szArea =BusinessLibraryService.getInstance().getAreasEntity("2");
		
		model().add("szArea", szArea);
		//外勤地址
		beat().getModel().add("wq_address", KVMap.wq_address);
		
		
		return view("/order/addTask_detail");
	}
	
	
	/***
	 * 保存新增的的
	 * @return
	 */
	@Path("/saveTask")
//	@AutherCheck
	public ActionResult saveTask(){
		
		String orderid = (String) beat().getRequest().getParameter("orderid");
		String company_name = (String)beat().getRequest().getParameter("company_name");
		String partner_name = (String)beat().getRequest().getParameter("partner_name");
		String userphone = (String)beat().getRequest().getParameter("userphone");
		String servicetype = (String)beat().getRequest().getParameter("servicetype");
		String task_name = (String)beat().getRequest().getParameter("task_name");
		String cityid      = (String)beat().getRequest().getParameter("cityid");
		String areaid      = (String)beat().getRequest().getParameter("areaid");
		String address     = (String)beat().getRequest().getParameter("address");
		String handletime  = (String)beat().getRequest().getParameter("handletime");
		String timeflag    = (String)beat().getRequest().getParameter("timeflag");
		String remark      = (String)beat().getRequest().getParameter("remark");
		
		String result = "success";
		try {
			
			TaskEntity model  = new TaskEntity();
			if(StringUtils.isNotEmpty(orderid)){
				model.setOrderid(Long.valueOf(orderid));
			}		
			model.setCompany_name(company_name);
			model.setPartner_name(partner_name);
			model.setUserphone(userphone);
			model.setServicetype(servicetype);
			model.setTask_name(task_name);
			model.setCityid(cityid);
			model.setAreaid(areaid);
			model.setAddress(address);
			model.setHandletime(handletime);
			model.setTimeflag(timeflag);
			model.setRemark(remark);
			model.setTask_status(KVMap.task_weizhipai);
			model.setAddtime(Timers.nowTime());
			long userid = UtilsHelper.getLoginId(beat());
			
			//TODO...
			if(userid>0){
				model.setTaskbyid(userid);
			}	
			RSBLL.getstance().getFieldTaskService().addTaskEntity(model);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = "error";
			e.printStackTrace();
		}
		
		return new JSONActionResult(result);
	}
	
	
	
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
