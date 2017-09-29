package com.jixiang.argo.lvzheng.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.actionresult.JSONActionResult;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.frame.service.OrderAssembleService;
import com.jixiang.argo.lvzheng.service.CouponsService;
import com.jixiang.argo.lvzheng.service.LvTaskService;
import com.jixiang.argo.lvzheng.service.OrderService;
import com.jixiang.argo.lvzheng.service.impl.LvTaskServiceImpl;
import com.jixiang.argo.lvzheng.service.impl.OrderServiceImpl;
import com.jixiang.argo.lvzheng.utils.ActionResultUtils;
import com.jixiang.argo.lvzheng.utils.EnterpriseUtils;
import com.jixiang.argo.lvzheng.utils.JavaDemoHttp;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.vo.FollowUpVO;
import com.jixiang.argo.lvzheng.vo.OrderChildInfoVO;
import com.jixiang.argo.lvzheng.vo.ServiceprocessVO;
import com.jixiang.argo.lvzheng.vo.UserCouponsVO;
import com.jixiang.union.interceptor.AutherCheck;
import com.jixiang.union.user.tools.LoginTool;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.enterprise.entity.LvEnterpriseEntity;
import com.jx.service.newcore.entity.ActionsEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.FollowupEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.OrderprocessEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;
import com.jx.service.newcore.entity.ServiceprocessEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;
/**
 * 订单管理控制类
 * @author 国明
 */
@Path("/order")
public class OrderController extends AbstractController {

	OrderService orderService = new OrderServiceImpl();
	 
	
	/**
	 * 返回followList
	 * @return
	 */
	@Path("/queryFollow.html")
	public ActionResult queryFollowList(){
		return new ActionResult() {
			@Override
			public void render(BeatContext beatContext) {
				response().setContentType("text/html");
				response().setCharacterEncoding("UTF-8");
				try {
					String orderid = beat().getRequest().getParameter("orderid");
//				System.out.println("+++++++++订单ID+++++++"+orderid);
					String condition = "orderid='"+orderid+"'";
					PrintWriter out =beat().getResponse().getWriter();
					String jsonStr = orderService.getFolowList(condition);
					out.print(jsonStr); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	@Path("/getFollowByOrderid")
	public  ActionResult getFollowByOrderid(){
		String queryString = beat().getRequest().getQueryString();
		String orderid = beat().getRequest().getParameter("orderid");
		String callback = getValue(queryString, "jsoncallback");
		String jsonStr = null;
		try {
			if(StringUtils.isNotBlank(orderid)){
				String condition = "orderid='"+orderid+"'";
				jsonStr = orderService.getFolowList(condition);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONActionResult(callback==null?jsonStr:callback+"("+jsonStr+")");
	}
	/**
	 * 动态添加followup数据
	 * @return
	 */
	@Path("/addFollow.html")
	public ActionResult addFollow(){
		return new ActionResult() {
			@Override
			public void render(BeatContext beatContext) {
				response().setContentType("text/html");
				response().setCharacterEncoding("UTF-8");
				//获取参数信息
				String content = beat().getRequest().getParameter("content");
				String intentcode = beat().getRequest().getParameter("intentcode");
				String orderid = beat().getRequest().getParameter("orderid");
				String title = beat().getRequest().getParameter("title");
				long addtime = new Date().getTime();
				
//				System.out.println(UtilsHelper.getLoginId(beat())+"==========获得登录的人员====================");

				long empId = UtilsHelper.getLoginId(beat());
				if(empId != 0){
					//拼装插入的数据块
					FollowupEntity fentity =new FollowupEntity();
					fentity.setAddtime(addtime);
					fentity.setContent(content);
					fentity.setEmpid(UtilsHelper.getLoginId(beat()));
					fentity.setIntentcode(Integer.valueOf(intentcode));
					fentity.setOrderid(Long.valueOf(orderid));
					fentity.setTitle(title);
					long fenityid =0l;
					try {
						fenityid = OrderAssembleService.getInstance().AddFollowUp(fentity); //插入数据
						SorderEntity se = OrderAssembleService.getInstance().getOrderEntityById(Long.valueOf(orderid));
						se.setIntentcode(Integer.valueOf(intentcode));  //设置母单的意向度 duxf 2015-07-7
						OrderAssembleService.getInstance().updateOrder(se);
					} catch (Exception e) {
						e.printStackTrace();
					}
					fentity.setFollowid(fenityid);
					
					FollowUpVO followVO = new FollowUpVO();
					followVO.setAddtime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", fentity.getAddtime())); //得到时间
					followVO.setContent(content);
					followVO.setEmpname(LoginTool.getLoginUserInfo(beat().getRequest()).getRealname());  //获得登录姓名
					followVO.setIntentcode(KVMap.followmap.get(Integer.valueOf(intentcode)).toString());
					followVO.setTitle(title);
					
					JSONObject jsObject = new JSONObject();
					try {
						PrintWriter out = beat().getResponse().getWriter();
//				System.out.println("=========返回的单个对象========"+jsObject.fromObject(followVO).toString());
						out.print(jsObject.fromObject(followVO).toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					try {
						PrintWriter out = beat().getResponse().getWriter();
						out.print("");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		};
	}
	@Path("/querypay/{orderid:\\d+}")
	public ActionResult querypay(final long orderid){
		return new ActionResult(){
			@Override
			public void render(BeatContext beatContext) {
				// TODO Auto-generated method stub
				JSONObject jo = new JSONObject();
				jo.put("ret", "fail");
				if(orderid > 0){
					SorderEntity se = null;
					try {
						se = RSBLL.getstance().getSorderService().getSorderEntityByid(orderid);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(se != null && se.getPaystate() == 2){
						jo.put("ret", "ok");
					}
				}
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				PrintWriter out = null;
				try {
					out = beat().getResponse().getWriter();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					beat().getResponse().getWriter().print(jo.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(out != null){
						out.close();
					}
				}
		}
		};
	}
	/****
	 * 获得财务列表中的日志信息
	 * @return
	 */
	@Path("/getPayprocess.html")
	public ActionResult getPayProcessList(){
		return new ActionResult() {
			@Override
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				try {
					String orderid = beat().getRequest().getParameter("orderid");
//System.out.println("+++++++++财务中订单ID+++++++"+orderid);
//					String condition = "orderid='"+orderid+"'";
					PrintWriter out =beat().getResponse().getWriter();
					String jsonStr = orderService.getPayProcessList(orderid);
//System.out.println("json格式为:========"+jsonStr);
					out.print(jsonStr); 
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		};
	} 
	
	/**
	 * 获得子订单的详情信息
	 * @return
	 */
	/*@Path("/getOrderTask.html")
	public ActionResult getChildOrderTask(){
		return new ActionResult() {
			@Override
			public void render(BeatContext beatContext) {
				response().setContentType("text/html");
				response().setCharacterEncoding("UTF-8");
				String orderid = beat().getRequest().getParameter("orderid");

				//获取标准化的节点信息Start---------------------------------------------------
//				String condition = "orderid = '"+orderid+"'";
//				String jsonAction="";
//				try {
//					Long proid = 0l;
//					List<SorderChildrenEntity> orderChild = RSBLL.getstance().getSorderChildrenService().getSorderChildrenEntityListBypage(condition, 1, 99, "");
//					for(SorderChildrenEntity orderChildE : orderChild){
//						ProductCategoryEntity categoryE = RSBLL.getstance().getProductCategoryService().getProductCategoryEntityById(orderChildE.getProdcateid());
//						if("公司注册".equals(categoryE.getCname()) || "代理记帐".equals(categoryE.getCname())){
//							proid = categoryE.getCid();
//						}
//					}
//					jsonAction = orderService.getChildOrderActions(proid);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
		        //获取标准化的节点信息end =====================================================
				
				
				
				String jsonArray = orderService.getChildOrderList(orderid);
 
System.out.println("=======获得的子订单的信息===================="+jsonArray);
				try {
					PrintWriter out = beat().getResponse().getWriter();
					out.print(jsonArray.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
	}*/
	
	
	//根据子单ID得到子单的具体流程信息
	public  List<ServiceprocessVO> getServiceProcessArray(String childOrderid){
		List<ServiceprocessVO>  tempArray = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessEntity> serviceprocessElist = OrderAssembleService.getInstance().getServiceProcessEntityList(childOrderid);
		for(ServiceprocessEntity serviceproceE : serviceprocessElist){
			ServiceprocessVO vo =new ServiceprocessVO();
			vo.setActionid(serviceproceE.getActionid());
			vo.setOptime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", serviceproceE.getOptime()));
			String optionName ="";
			EmployersEntity employer = OrderAssembleService.getInstance().getEmployersEntity(serviceproceE.getOpid());
			if(null != employer){
				optionName = employer.getRealname();
			}
			vo.setOptionName(optionName);
			vo.setOrderid(serviceproceE.getOrderid());
			vo.setOrderstate(serviceproceE.getOrderstate());
			vo.setPsid(serviceproceE.getPsid());
			tempArray.add(vo);
		}
		return tempArray;
	}
	
	public void setListStep(List tempStepList,StringBuffer tempSB,String name){
		
		if(tempStepList.size()>0){
			tempSB.append("["); 
			int count = tempStepList.size();
			for(int i=1;i<=count;i++){
				tempSB.append("{}");
				if(i!=count){
					tempSB.append(",");
				}
			}
			tempSB.append("]");
			
			beat().getModel().add("step_"+name, tempSB.toString());
		}
	}
	
	
	public void bookeep(String propertid,String childOrderid){
		if(!"".equals(propertid)){
			try {
				//通过获得的propertid转换成JSON对象
		    	com.bj58.sfft.json.orgjson.JSONArray jsonarray =new com.bj58.sfft.json.orgjson.JSONArray(propertid);
				for(int i=0;i<jsonarray.length();i++){
					com.bj58.sfft.json.orgjson.JSONObject jsonObject =jsonarray.getJSONObject(i); 
					String id =jsonObject.getString("propertyid");
					String val =jsonObject.getString("propertyval");
					if("bookOrder".equals(val)){
						ProductPropertiesEntity propertiesE =  RSBLL.getstance().getProductPropertiesService().getProductPropertiesEntityById(Long.valueOf(id));
						propertid = propertiesE.getProvalue();  //得到具体是几个月的代理记帐如果3个月 12个月或15个月
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			//记录服务表中一共有多少条记录
			Integer serviceProcessCount =  OrderAssembleService.getInstance().getServiceProcessEntityList(childOrderid).size();
			Integer overM = Integer.parseInt(propertid) ;	   //剩余几期的服务
			Integer dangqinQ = 1;  //当前期默认为第一期

			List<ServiceprocessEntity> overMList = new ArrayList<ServiceprocessEntity>();
			List<ServiceprocessVO> overMVOList = new LinkedList<ServiceprocessVO>();
			Integer currentQ =0;  //计算显示当前期

			//获得开始服务的记录
			ServiceprocessVO startVO = new ServiceprocessVO();
			try {
				if(serviceProcessCount>0){
					overM = Integer.parseInt(propertid) - (serviceProcessCount - 1);
					dangqinQ = (serviceProcessCount - 1);
					ServiceprocessEntity StartSE = OrderAssembleService.getInstance().getServiceProcessEntityList2(childOrderid).get(0);
					startVO.setActionid(StartSE.getActionid());
					startVO.setOptime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", StartSE.getOptime()));
					String Name ="";
					EmployersEntity employerE = OrderAssembleService.getInstance().getEmployersEntity(StartSE.getOpid());
					if(null != employerE){
						Name = employerE.getRealname();
					}
					startVO.setOptionName(Name);
					startVO.setOrderid(StartSE.getOrderid());
					startVO.setOrderstate(StartSE.getOrderstate());
					startVO.setPsid(StartSE.getPsid());
					
					overMList = RSBLL.getstance().getServiceprocessService().getServiceprocessEntity("childorderid='"+childOrderid+"'", 1, 4, "optime desc");
					for(ServiceprocessEntity se : overMList){ 
						if(se.getPsid() != StartSE.getPsid()){
							currentQ = currentQ + 1;
							ServiceprocessVO overMvo =new ServiceprocessVO();
							overMvo.setActionid(se.getActionid());
							overMvo.setOptime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", se.getOptime()));
							String optionName ="";
							EmployersEntity employer = OrderAssembleService.getInstance().getEmployersEntity(se.getOpid());
							if(null != employer){
								optionName = employer.getRealname();
							}
							overMvo.setOptionName(optionName);
							overMvo.setOrderid(se.getOrderid());
							overMvo.setOrderstate(se.getOrderstate());
							overMvo.setPsid(se.getPsid()); 
							overMVOList.add(overMvo);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			beat().getModel().add("overM", overM);  //剩余接个月的服务
			beat().getModel().add("propertid", propertid);  //需要办理多少个月代理记帐
			beat().getModel().add("startVO", startVO);  //服务的对象
			beat().getModel().add("currentQ", currentQ);  
			beat().getModel().add("dangqinQ", dangqinQ);  //当前第几期
			beat().getModel().add("serviceProcessCount", serviceProcessCount);
			beat().getModel().add("overMVOList", overMVOList);
		}
	}
	
//	//得到应该拥有的子单ID
	public void getChildOrderIds(Long childOrderid,String name){
		//定义子单ID
	    Map<String,String> childOrderIdMap = new HashMap<String, String>();
	    if("企业名称核准".equals(name)){
			beat().getModel().add("unclearname_coid", childOrderid);
	    }
	    if("公司注册".equals(name)){
			beat().getModel().add("regcompany_coid", childOrderid);	
	    }
	    if("代理地址".equals(name)){
			beat().getModel().add("address_coid", childOrderid);	
	    }
	    if("地税报到".equals(name)){
			beat().getModel().add("localtax_coid", childOrderid);	
	    }
	    if("银行开户".equals(name)){
			beat().getModel().add("bankaccount_coid", childOrderid);	
	    }
	    if("国税报到".equals(name)){
			beat().getModel().add("statetax_coid", childOrderid);	
	    }
	    if("社保开户".equals(name)){
			beat().getModel().add("socialopen_coid", childOrderid);		
	    }
	    if("公积金开户".equals(name)){
			beat().getModel().add("fundopen_coid", childOrderid);	
	    }
	    if("代理记账".equals(name)){
			beat().getModel().add("bookkepp_coid", childOrderid);	
	    }
	    if("商标注册".equals(name)){
			beat().getModel().add("trademark_coid", childOrderid);	
	    }
	    if("更换新版营业执照".equals(name)){
			beat().getModel().add("companyChange1_coid", childOrderid);	
	    }
	    if("经营范围变更".equals(name)){
			beat().getModel().add("companyChange2_coid", childOrderid);	
	    }
	    if("董监高信息变更".equals(name)){
			beat().getModel().add("companyChange3_coid", childOrderid);	
	    }
	    if("法定代表人/营业期限等信息变更".equals(name)){
			beat().getModel().add("companyChange4_coid", childOrderid);	
	    }
	    if("公司名称变更".equals(name)){
			beat().getModel().add("companyChange5_coid", childOrderid);	
	    }
	    if("注册资本变更".equals(name)){
			beat().getModel().add("companyChange6_coid", childOrderid);	
	    }
	    if("注册地址变更".equals(name)){
			beat().getModel().add("companyChange7_coid", childOrderid);	
	    }
	    if("企业股权转让变更".equals(name)){
			beat().getModel().add("companyChange8_coid", childOrderid);	
	    }
	}
	
	
	/***
	 * 获得母单的所有的详情信息
	 * @return
	 */
	@Path("/orderDetil/{orderid:\\d+}")
	@AutherCheck
	public ActionResult getOrderDetils(String orderid){
		System.out.println("============接到的订单ID=========="+orderid);
		SorderEntity s_orderE = OrderAssembleService.getInstance().getOrderEntityById(Long.valueOf(orderid));
		
		//通过母单ID查询旗下的子单
		List<SorderChildrenEntity> sorderchildList = OrderAssembleService.getInstance().getOrderChildrEntityList("orderid="+orderid);
		List<Map<String,String>> serverNameListtemp = new LinkedList<Map<String,String>>();
		for(SorderChildrenEntity sorderchildE : sorderchildList){
			Map<String,String> tempMap = new HashMap<String, String>();
			String cname = OrderAssembleService.getInstance().getCategoryEntity(sorderchildE.getProdcateid()).getCname();
			tempMap.put("servername", cname);
			
			tempMap.put("serverstate", KVMap.orderServerstatemap.get(String.valueOf(sorderchildE.getCostate())).toString());
			tempMap.put("realname","");
			if(!"0".equals(String.valueOf(s_orderE.getEmpid()))){
				tempMap.put("realname", OrderAssembleService.getInstance().getEmployersEntity(s_orderE.getEmpid()).getRealname()); //取母单的法务ID	
			}
			tempMap.put("time","");
			if(!"".equals(String.valueOf(sorderchildE.getUpdatetime())) && !"0".equals(String.valueOf(sorderchildE.getUpdatetime()))){
				tempMap.put("time", UtilsHelper.formatLongDate("yyyy/MM/dd", sorderchildE.getUpdatetime()));
			}
			
			tempMap.put("classTH", "class='green'");
			if("1".equals(sorderchildE.getCostate())){
				tempMap.put("classTR", "class='red'");
			}
			serverNameListtemp.add(tempMap);
			
			getChildOrderIds(sorderchildE.getCoid(),cname);
		}
		//流程名称集合
		List<Map<String,String>> serverName1List = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName2List  = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName3List     = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName4List    = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName5List = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName6List    = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName7List  = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName8List    = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName9List    = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName10List   = new ArrayList<Map<String,String>>();
		
		//公司变更11-18集合
		List<Map<String,String>> serverName11List   = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName12List   = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName13List   = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName14List   = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName15List   = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName16List   = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName17List   = new ArrayList<Map<String,String>>();
		List<Map<String,String>> serverName18List   = new ArrayList<Map<String,String>>();
		for(Map<String,String> tempM : serverNameListtemp ){
			if("企业名称核准".equals(tempM.get("servername"))){
				serverName1List.add(tempM);
				beat().getModel().add("serverName1List", serverName1List);
			}else if("公司注册".equals(tempM.get("servername"))){
				serverName2List.add(tempM);
				beat().getModel().add("serverName2List", serverName2List);
			}else if("代理地址".equals(tempM.get("servername"))){
				serverName3List.add(tempM);	
				beat().getModel().add("serverName3List", serverName3List);
			}else if("地税报到".equals(tempM.get("servername"))){
				serverName4List.add(tempM);	
				beat().getModel().add("serverName4List", serverName4List);
			}else if("银行开户".equals(tempM.get("servername"))){
				serverName5List.add(tempM);	
				beat().getModel().add("serverName5List", serverName5List);
			}else if("国税报到".equals(tempM.get("servername"))){
				serverName6List.add(tempM);	
				beat().getModel().add("serverName6List", serverName6List);
			}else if("社保开户".equals(tempM.get("servername"))){
				serverName7List.add(tempM);	
				beat().getModel().add("serverName7List", serverName7List);
			}else if("公积金开户".equals(tempM.get("servername"))){
				serverName8List.add(tempM);	
				beat().getModel().add("serverName8List", serverName8List);
			}else if("代理记账".equals(tempM.get("servername"))){
				serverName9List.add(tempM);	
				beat().getModel().add("serverName9List", serverName9List);
			}else if("商标注册".equals(tempM.get("servername"))){
				serverName10List.add(tempM);	
				beat().getModel().add("serverName10List", serverName10List);
			}else if("更换新版营业执照".equals(tempM.get("servername"))){
				serverName11List.add(tempM);	
				beat().getModel().add("serverName11List", serverName11List);
			}else if("经营范围变更".equals(tempM.get("servername"))){
				serverName12List.add(tempM);	
				beat().getModel().add("serverName12List", serverName12List);
			}else if("董监高信息变更".equals(tempM.get("servername"))){
				serverName13List.add(tempM);	
				beat().getModel().add("serverName13List", serverName13List);
			}else if("法定代表人/营业期限等信息变更".equals(tempM.get("servername"))){
				serverName14List.add(tempM);	
				beat().getModel().add("serverName14List", serverName14List);
			}else if("公司名称变更".equals(tempM.get("servername"))){
				serverName15List.add(tempM);	
				beat().getModel().add("serverName15List", serverName15List);
			}else if("注册资本变更".equals(tempM.get("servername"))){
				serverName16List.add(tempM);	
				beat().getModel().add("serverName16List", serverName16List);
			}else if("注册地址变更".equals(tempM.get("servername"))){
				serverName17List.add(tempM);	
				beat().getModel().add("serverName17List", serverName17List);
			}else if("企业股权转让变更".equals(tempM.get("servername"))){
				serverName18List.add(tempM);	
				beat().getModel().add("serverName18List", serverName18List);
			}
		}

		
		//流程节点集合
		List<ActionsEntity> unclearnameList = new ArrayList<ActionsEntity>();
		List<ActionsEntity> regcompanyList  = new ArrayList<ActionsEntity>();
		List<ActionsEntity> addressList     = new ArrayList<ActionsEntity>();
		List<ActionsEntity> localtaxList    = new ArrayList<ActionsEntity>();
		List<ActionsEntity> bankaccountList = new ArrayList<ActionsEntity>();
		List<ActionsEntity> statetaxList    = new ArrayList<ActionsEntity>();
		List<ActionsEntity> socialopenList  = new ArrayList<ActionsEntity>();
		List<ActionsEntity> fundopenList    = new ArrayList<ActionsEntity>();
		List<ActionsEntity> bookkeppList    = new ArrayList<ActionsEntity>();
		List<ActionsEntity> trademarkList   = new ArrayList<ActionsEntity>();
		

		List<ActionsEntity> companyChange1List   = new ArrayList<ActionsEntity>();
		List<ActionsEntity> companyChange2List   = new ArrayList<ActionsEntity>();
		List<ActionsEntity> companyChange3List   = new ArrayList<ActionsEntity>();
		List<ActionsEntity> companyChange4List   = new ArrayList<ActionsEntity>();
		List<ActionsEntity> companyChange5List   = new ArrayList<ActionsEntity>();
		List<ActionsEntity> companyChange6List   = new ArrayList<ActionsEntity>();
		List<ActionsEntity> companyChange7List   = new ArrayList<ActionsEntity>();
		List<ActionsEntity> companyChange8List   = new ArrayList<ActionsEntity>();
		
		//各个流程具体节点信息集合
		List<ServiceprocessVO> unclearnameArray = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> regcompanyArray  = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> addressArray     = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> localtaxArray    = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> bankaccountArray = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> statetaxArray    = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> socialopenArray  = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> fundopenArray    = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> bookkeppArray    = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> trademarkArray   = new ArrayList<ServiceprocessVO>();
		

		List<ServiceprocessVO> companyChange1Array   = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> companyChange2Array   = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> companyChange3Array   = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> companyChange4Array   = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> companyChange5Array   = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> companyChange6Array   = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> companyChange7Array   = new ArrayList<ServiceprocessVO>();
		List<ServiceprocessVO> companyChange8Array   = new ArrayList<ServiceprocessVO>();
		
		
		String propertid = "";
		for(SorderChildrenEntity sorderchildE : sorderchildList){ 
			ProductCategoryEntity categoryE =  OrderAssembleService.getInstance().getCategoryEntity(sorderchildE.getProdcateid());
			if("企业名称核准".equals(categoryE.getCname())){
				unclearnameList = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				unclearnameArray = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("公司注册".equals(categoryE.getCname())){
				regcompanyList = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				regcompanyArray = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("代理地址".equals(categoryE.getCname())){
				addressList = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				addressArray = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("地税报到".equals(categoryE.getCname())){
				localtaxList = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				localtaxArray = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("银行开户".equals(categoryE.getCname())){
				bankaccountList = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				bankaccountArray = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("国税报到".equals(categoryE.getCname())){
				statetaxList = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				statetaxArray = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("社保开户".equals(categoryE.getCname())){
				socialopenList = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				socialopenArray = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("公积金开户".equals(categoryE.getCname())){
				fundopenList = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				fundopenArray = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("代理记账".equals(categoryE.getCname())){
				bookkeppList = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				//bookkeppArray = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
				propertid =String.valueOf(sorderchildE.getPropertid()); //获取这步的目的是为了得到是几个月的代理记帐 
				bookeep(propertid,String.valueOf(sorderchildE.getCoid()));
			}else if("商标注册".equals(categoryE.getCname())){
				trademarkList = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				trademarkArray = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("更换新版营业执照".equals(categoryE.getCname())){
				companyChange1List = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				companyChange1Array = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("经营范围变更".equals(categoryE.getCname())){
				companyChange2List = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				companyChange2Array = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("董监高信息变更".equals(categoryE.getCname())){
				companyChange3List = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				companyChange3Array = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("法定代表人/营业期限等信息变更".equals(categoryE.getCname())){
				companyChange4List = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				companyChange4Array = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("公司名称变更".equals(categoryE.getCname())){
				companyChange5List = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				companyChange5Array = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("注册资本变更".equals(categoryE.getCname())){
				companyChange6List = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				companyChange6Array = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("注册地址变更".equals(categoryE.getCname())){
				companyChange7List = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				companyChange7Array = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}else if("企业股权转让变更".equals(categoryE.getCname())){
				companyChange8List = OrderAssembleService.getInstance().getActionsEntityList(categoryE.getCid());
				companyChange8Array = getServiceProcessArray(String.valueOf(sorderchildE.getCoid()));
			}
		}

		
		setListStep(unclearnameList,new StringBuffer(),"unclearname");
		setListStep(regcompanyList,new StringBuffer(),"regcompany");
		setListStep(addressList,new StringBuffer(),"address");
		setListStep(localtaxList,new StringBuffer(),"localtax");
		setListStep(bankaccountList,new StringBuffer(),"bankaccount");
		setListStep(statetaxList,new StringBuffer(),"statetax");
		setListStep(socialopenList,new StringBuffer(),"socialopen");
		setListStep(fundopenList,new StringBuffer(),"fundopen");
		setListStep(trademarkList,new StringBuffer(),"trademark");
		

		setListStep(companyChange1List,new StringBuffer(),"companyChange1");
		setListStep(companyChange2List,new StringBuffer(),"companyChange2");
		setListStep(companyChange3List,new StringBuffer(),"companyChange3");
		setListStep(companyChange4List,new StringBuffer(),"companyChange4");
		setListStep(companyChange5List,new StringBuffer(),"companyChange5");
		setListStep(companyChange6List,new StringBuffer(),"companyChange6");
		setListStep(companyChange7List,new StringBuffer(),"companyChange7");
		setListStep(companyChange8List,new StringBuffer(),"companyChange8");
		

		beat().getModel().add("unclearnameList", unclearnameList);   
		beat().getModel().add("regcompanyList", regcompanyList);   
		beat().getModel().add("addressList", addressList);   
		beat().getModel().add("localtaxList", localtaxList);   
		beat().getModel().add("bankaccountList", bankaccountList);   
		beat().getModel().add("statetaxList", statetaxList);   
		beat().getModel().add("socialopenList", socialopenList);   
		beat().getModel().add("fundopenList", fundopenList);   
		beat().getModel().add("bookkeppList", bookkeppList);   
		beat().getModel().add("trademarkList", trademarkList); 
		 
		beat().getModel().add("companyChange1List", companyChange1List);  
		beat().getModel().add("companyChange2List", companyChange2List);  
		beat().getModel().add("companyChange3List", companyChange3List);  
		beat().getModel().add("companyChange4List", companyChange4List);  
		beat().getModel().add("companyChange5List", companyChange5List);  
		beat().getModel().add("companyChange6List", companyChange6List);  
		beat().getModel().add("companyChange7List", companyChange7List);  
		beat().getModel().add("companyChange8List", companyChange8List); 
		
		
		
		beat().getModel().add("unclearnameArray", unclearnameArray);   
		beat().getModel().add("regcompanyArray", regcompanyArray);   
		beat().getModel().add("addressArray", addressArray);   
		beat().getModel().add("localtaxArray", localtaxArray);   
		beat().getModel().add("bankaccountArray", bankaccountArray);   
		beat().getModel().add("statetaxArray", statetaxArray);
		beat().getModel().add("socialopenArray", socialopenArray);   
		beat().getModel().add("fundopenArray", fundopenArray);   
		beat().getModel().add("bookkeppArray", bookkeppArray);   
		beat().getModel().add("trademarkArray", trademarkArray);    
		
		

		beat().getModel().add("companyChange1Array", companyChange1Array); 
		beat().getModel().add("companyChange2Array", companyChange2Array); 
		beat().getModel().add("companyChange3Array", companyChange3Array); 
		beat().getModel().add("companyChange4Array", companyChange4Array); 
		beat().getModel().add("companyChange5Array", companyChange5Array); 
		beat().getModel().add("companyChange6Array", companyChange6Array); 
		beat().getModel().add("companyChange7Array", companyChange7Array); 
		beat().getModel().add("companyChange8Array", companyChange8Array); 
		//======================获得是否购买服务end==================
	    

        
		//==========================获得流程节点start==========================
		SorderEntity orderE = OrderAssembleService.getInstance().getOrderEntityById(Long.valueOf(orderid));
		//===============================获得流程节点end=================================
		
		
		
		//=========获得订单详情TABLE中的数据statt=============
		List<OrderChildInfoVO> childOrderList = orderService.getOrderChildList(orderid);
		//=========获得订单详情TABLE中的数据end================
		
		
		SorderEntity sorderE = OrderAssembleService.getInstance().getOrderEntityById(Long.valueOf(orderid));
	    LoginEntity userE = OrderAssembleService.getInstance().getUserEntity(sorderE.getUserid()) ;
		String coupon_condition=" userid='"+userE.getUserid()+"' and orderid='"+orderid+"' ";
	    List<UserCouponsVO> usercouponsList =  CouponsService.getInstance().getUserCoupons(coupon_condition, "");
	    if(usercouponsList.size()>0){
		    beat().getModel().add("userCoupon", usercouponsList.get(0));
	    }
	
//		beat().getModel().add("actionsList", actionsList);   //非周期性节点
//		beat().getModel().add("cycleActionsList", cycleActionsList);   //周期性节点
		beat().getModel().add("order", orderE);
		beat().getModel().add("childOrderList", childOrderList);
		
//		if(cycleActionsList.size()>0){
//			return view("/order/orderDetil_1");
//		}else{
			return view("/order/orderDetil");
//		}
	}
	

	@Path("/editCustomerInfo/{customerId:\\d+}")
	public ActionResult editCustomerInfo(String customerId){
		JSONObject jo = new JSONObject();
		jo.put("msg", "success");
		String email = beat().getRequest().getParameter("email");
		String otherPhone = beat().getRequest().getParameter("otherPhone");
		String address = beat().getRequest().getParameter("address");
		String companyName = beat().getRequest().getParameter("companyName");
		String customerName = beat().getRequest().getParameter("customerName");
		String orderid = beat().getRequest().getParameter("orderid");

		try {
			LoginEntity loginEntity =   RSBLL.getstance().getLoginService().getLoginEntityById(Long.valueOf(customerId));
			loginEntity.setEmail(email);
			loginEntity.setLandlinenumber(otherPhone);
			loginEntity.setAddress(address);
//			loginEntity.setCompanyname(companyName);
			loginEntity.setUsername(customerName);
			//更新用户对象
			RSBLL.getstance().getLoginService().updateLoginEntity(loginEntity);
			//更新母单中的客户公司名称
			SorderEntity se = RSBLL.getstance().getSorderService().getSorderEntityByid(Long.valueOf(orderid));
			se.setCompanyname(companyName);
			RSBLL.getstance().getSorderService().updateSorderEntity(se);
			if(se != null){
				long buid = se.getBusid();
				/*BusinessLibaryEntity bl = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(buid);
				if(bl != null){
					bl.setCompanymc(companyName);
					RSBLL.getstance().getBusinessLibaryService().updateBusinessLibaryEntity(bl);
				}*/
				
				LvEnterpriseEntity enterprise = RSBLL.getstance().getEnterpriseService().getEnterpriseById(buid);
				if(enterprise != null){
					enterprise.setName(companyName);
					RSBLL .getstance().getEnterpriseService().updateEnterpriseEntity(enterprise, EnterpriseUtils.getLoginInfo(request()));
				}
				
				
			}
		} catch (Exception e) {
			jo.put("msg", "error");
			e.printStackTrace();
		}
		
		return new JSONActionResult(jo.toString());
	}
	
	/***
	 * 未接通电话时发送给短信方法 duxf
	 * @return
	 */
	@Path("/uncallsendmessage/{orderid:\\d+}")
	public ActionResult uncallsendmessage(String orderid){
		JSONObject jo = new JSONObject();
		jo.put("msg", "error");
		String message = request().getParameter("message");
		if(null != message){
			try {
				SorderEntity se = RSBLL.getstance().getSorderService().getSorderEntityByid(Long.valueOf(orderid));
				LoginEntity userEntity = RSBLL.getstance().getLoginService().getLoginEntityById(se.getUserid());
				EmployersEntity eEntity = RSBLL.getstance().getEmployersService().getEmployersEntityById(se.getEmpid());
				String phone = userEntity.getUserphone();
				if(null !=eEntity && null != userEntity){
					if(!"".equals(phone) && null!=phone){
						String content = "";
						if("uncall".equals(message)){
							content = "您好，我是小微的咨询顾问"+eEntity.getRealname()+"("+eEntity.getPhonenumber()+"),刚才回电未接通，很期待能用我的专业知识帮到您, 期待您方便时回电咨询！";	
						}else if("address".equals(message)){
							content = "小微温馨提示：应工商局要求，地址提供方镕辉佳特有限公司将于近日向公司法人致电并核对公司信息。请公司法人保持手机畅通并如实反馈公司信息，若法人未接电话或未如实反馈公司信息将导致注册地址无法使用且公司无法注册。";
						}else if("bankopen".equals(message)){
							content = "小微已为您提交银行开户申请，招商银行将于近日向公司法人致电进行公司信息核实，请保持手机畅通并如实反馈公司信息。若法人未接电话或未如实反馈公司信息，银行将不予开户。预计银行将于15个工作日完成资料审查。";
						}
						
						int temp = JavaDemoHttp.sendmsg(phone, content);
					System.out.println(temp+"发送短信返回的编码===========================");
						if(temp == 0){
							jo.put("msg", "success");
						}
					}	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new JSONActionResult(jo.toString());
	}
	
	/**
	 * 恢复订单
	 * @param orderid
	 * @return
	 */
	@Path("/recoveOrder/{orderid:\\d+}")
	public ActionResult recoveOrder(String orderid){
		JSONObject jo = new JSONObject();
		jo.put("msg", "error");
		
		String condition = "orderid='"+orderid+"'";
		try {
			SorderEntity sorder = RSBLL.getstance().getSorderService().getSorderEntityByid(Long.valueOf(orderid));
			List<OrderprocessEntity> orderProcessList =  RSBLL.getstance().getOrderprocessService().getOrderprocessEntity(condition, 1, 99, "optime desc");
			if(null != orderProcessList && orderProcessList.size()>0){
				//客服手动取消订单后进行恢复
				if(orderProcessList.size() == 1 && orderProcessList.get(0).getOrderstate() == 2){
					sorder.setOrderstate(7);
					sorder.setUpdatetime(new Date().getTime());
				}else{
					for(OrderprocessEntity orderProcess : orderProcessList){
						if(3==orderProcess.getOrderstate() || 7==orderProcess.getOrderstate()){
							Long orderstate  = orderProcess.getOrderstate();
							sorder.setOrderstate(Integer.valueOf(orderstate.toString()));
							if(orderstate == 3){
								sorder.setOrderstate(3);
								sorder.setUpdatetime(new Date().getTime());
								break;
							}
							if(orderstate == 7){
								sorder.setOrderstate(7);
								sorder.setUpdatetime(new Date().getTime());
				      			sorder.setPiesingletime(new Date().getTime());    //派单时间
								break;
							}
						}
					}
				}
				//更新订单状态
				RSBLL.getstance().getSorderService().updateSorderEntity(sorder);
				//添加操作记录
				OrderprocessEntity orderProcessE = new OrderprocessEntity();
				orderProcessE.setOpid(LoginTool.getLoginUserInfo(beat().getRequest()).getEmpid());
				orderProcessE.setOptcode(1139);
				orderProcessE.setOptime(new Date().getTime());
				orderProcessE.setOption("恢复订单");
				orderProcessE.setOrderid(Long.valueOf(orderid));
				orderProcessE.setOrderstate(sorder.getOrderstate());
				RSBLL.getstance().getOrderprocessService().addOrderprocessEntity(orderProcessE);
				jo.put("msg", "success");
			}else{
				//客户手动取消的默认置为潜在订单
				if(sorder.getOrderstate() == 2){
					sorder.setOrderstate(7);
					sorder.setUpdatetime(new Date().getTime());
				}
				//更新订单状态
				RSBLL.getstance().getSorderService().updateSorderEntity(sorder);
				//添加操作记录
				OrderprocessEntity orderProcessE = new OrderprocessEntity();
				orderProcessE.setOpid(LoginTool.getLoginUserInfo(beat().getRequest()).getEmpid());
				orderProcessE.setOptcode(1139);
				orderProcessE.setOptime(new Date().getTime());
				orderProcessE.setOption("恢复订单");
				orderProcessE.setOrderid(Long.valueOf(orderid));
				orderProcessE.setOrderstate(sorder.getOrderstate());
				RSBLL.getstance().getOrderprocessService().addOrderprocessEntity(orderProcessE);
				jo.put("msg", "success");
			}
			//调用新的服务系统恢复任务
//			RSBLL.getstance().getProcessService().activateProcessInstanceByOrderId(String.valueOf(sorder.getOrderid()));
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return new JSONActionResult(jo.toString());
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
	
	//根据母单ID判断订单是否包含公司注册如果包含则开启新流程
	@Path("/JudgeOrderIsContainCompanReg/{orderid:[\\w-]+}")
	public ActionResult JudgeOrderIsContainCompanReg(String orderid){
		List<SorderChildrenEntity> sorderChild = OrderAssembleService.getInstance().getOrderChildrEntityList("orderid='"+orderid+"'");
		boolean isNewProcess = false;
		if(null != sorderChild && sorderChild.size() > 0){
			 for(int i=0;i<sorderChild.size();i++){
				 SorderChildrenEntity sorderCE = sorderChild.get(i);
				 System.out.println("****************子单的具体商品ID=========="+sorderCE.getProdcateid()+"母单ID:"+sorderCE.getOrderid());
				 if("1001".equals(String.valueOf(sorderCE.getProdcateid()))){
					 isNewProcess = true;
					 break;
				 }
			 }
		}
		System.out.println("************不存在公司注册的产品因此不发起任务"+isNewProcess+"母单ID："+orderid);
		if(isNewProcess){
			try {
				LvTaskService lvtaskservice = new LvTaskServiceImpl();
				Long empid = UtilsHelper.getLoginId(beat());
				//发起任务
				lvtaskservice.StartTask(String.valueOf(empid), orderid, "");
			} catch (Exception e) {
				e.printStackTrace();
				return ActionResultUtils.renderJson("");
			}
			return ActionResultUtils.renderJson("{\"success\":true,\"reflag\":true}");
		}else{
			return ActionResultUtils.renderJson("{\"success\":true,\"reflag\":false}");
		}
	}
}
