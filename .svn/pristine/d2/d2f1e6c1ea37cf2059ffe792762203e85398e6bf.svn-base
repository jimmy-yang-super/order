package com.jixiang.argo.lvzheng.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.jixiang.argo.lvzheng.actionresult.JSONActionResult;
import com.jixiang.argo.lvzheng.buz.ProcessBuz;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.frame.service.OrderAssembleService;
import com.jixiang.argo.lvzheng.service.CouponsService;
import com.jixiang.argo.lvzheng.service.LvTaskService;
import com.jixiang.argo.lvzheng.service.OrderService;
import com.jixiang.argo.lvzheng.service.RealBusinessService;
import com.jixiang.argo.lvzheng.service.impl.LvTaskServiceImpl;
import com.jixiang.argo.lvzheng.service.impl.OrderServiceImpl;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.vo.CustomerVO;
import com.jixiang.argo.lvzheng.vo.OrderChildInfoVO;
import com.jixiang.argo.lvzheng.vo.UserCouponsVO;
import com.jixiang.union.interceptor.AutherCheck;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.FollowupEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.OrderprocessEntity;
import com.jx.service.newcore.entity.PayProcessEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.newcore.entity.UserCouponsEntity;

@Path("/action")
public class StartServiceController extends AbstractController{
	OrderService orderservice = new OrderServiceImpl();
	/***
	 * 签单contracle
	 * @return
	 */
	@Path("/startservice")
	@AutherCheck
	 public ActionResult startService(){
		
		String orderid = beat().getRequest().getParameter("orderid");
		String paramType = beat().getRequest().getParameter("ParamType")==null?"":beat().getRequest().getParameter("ParamType");  //判断是从那里点的签单从而记录签单后跳转的路径
		model().add("paramType", paramType);
//		System.out.println("=============="+orderid);
		//得到母单实体
		SorderEntity sorderE = OrderAssembleService.getInstance().getOrderEntityById(Long.valueOf(orderid));
		//添加所属公司
		if(sorderE != null){
			long busid = sorderE.getBusid();
			String gsmc = RealBusinessService.getInstance().getBusinessGongsmc(busid)==null?"":RealBusinessService.getInstance().getBusinessGongsmc(busid);
			if(StringUtil.isEmpty(gsmc)){
				beat().getModel().add("suosgs", gsmc);
			}
		}
	    LoginEntity userE = OrderAssembleService.getInstance().getUserEntity(sorderE.getUserid()) ;
	    List<OrderChildInfoVO> childOrderList = orderservice.getChildOrdersList(orderid);
	    
	    
	    String serviceName = "";
	    for(OrderChildInfoVO vo : childOrderList){
	    	serviceName = vo.getServerName();
	    	if(!"".equals(serviceName))
	    	break;
	    }
	    beat().getModel().add("serviceName", serviceName);
	    serviceName = "";  //清空
	    
	    beat().getModel().add("childOrderList", childOrderList);
	    CustomerVO uservo =new CustomerVO();
	    uservo.setUsername(userE.getUsername());
	    uservo.setUserphone(userE.getUserphone());
	    uservo.setChannel(KVMap.channelMap.get(String.valueOf(userE.getChannel())));
	    uservo.setCompanyname(sorderE.getCompanyname());;
	    uservo.setEmail(userE.getEmail());
	    uservo.setAddress(userE.getAddress());
	    uservo.setLandlinenumber(userE.getLandlinenumber());
	    
	    String condition=" userid='"+userE.getUserid()+"' and orderid='"+orderid+"' ";
	    List<UserCouponsVO> usercouponsList =  CouponsService.getInstance().getUserCoupons(condition, "");
	    if(null != usercouponsList && usercouponsList.size()>0){
		    beat().getModel().add("userCoupon", usercouponsList.get(0));
	    }else{
	    	
	    	condition = " userid='"+userE.getUserid()+"' and dqtime>='"+new Date().getTime()+"' and isuse='0' ";
	    	String orderby = "dqtime,couponsid";
	    	usercouponsList = CouponsService.getInstance().getUserCoupons(condition, orderby);
	    	List<UserCouponsVO> tempUserCouponsList = new ArrayList<UserCouponsVO>();
	    	if(usercouponsList.size()>0){
	    		try {
	    			List<SorderChildrenEntity> listChildE = RSBLL.getstance().getSorderChildrenService().getSorderChildrenEntityListBypage("orderid='"+orderid+"'", 1, 99, "");
		    		for(UserCouponsVO usercouponsvo : usercouponsList){
		    			boolean tempFlag = false;
		    			String range = usercouponsvo.getRange();
		    			String [] range_sub =  null;
		    			if(null != range && !range.equals("")){
		    				range_sub = range.split(",");
		    			}
		    			if(null != range_sub && range_sub.length>0){
		    				for(SorderChildrenEntity childE : listChildE){
		    					for(String str : range_sub){
		    						if(str.equals(String.valueOf(childE.getProdcateid()))){
		    							tempFlag = true;
		    							break;
		    						}
		    					}
		    					if(tempFlag){
		    						break;
		    					}
		    				}
		    			}
		    			if(tempFlag){
		    				tempUserCouponsList.add(usercouponsvo);
		    			}
		    		}
		    		
				} catch (Exception e) {
					e.printStackTrace();
				}
	    		if(tempUserCouponsList.size()>0){
	    			beat().getModel().add("userCouponList", tempUserCouponsList);
	    		}
	    	}
	    	
	    }
	    
	    beat().getModel().add("sorderE", sorderE);
	    beat().getModel().add("userE", uservo);
	    beat().getModel().add("orderid", orderid);
	    //获得登录人员
	    Long currentLoginUser =  UtilsHelper.getLoginId(beat());
	    beat().getModel().add("qianDanCompanyAE", currentLoginUser);
		
		return view("/order/apply");
	 }
	
	/***
	 * 签单申请 点击确定
	 * @return
	 */
	@Path("/writeSingle")
	public ActionResult writeSingle(){

		JSONObject jo = new JSONObject();
//		CustomerVO uservo = (CustomerVO)beat().getModel().get("userE");
		
		String orderid = beat().getRequest().getParameter("orderid");
		String totalmoney = beat().getRequest().getParameter("totalmoney");   //订单金额 
		String paymoney = beat().getRequest().getParameter("paymoney");   //应付金额
		String contract = beat().getRequest().getParameter("contract");
		String contents = beat().getRequest().getParameter("contents");
//		String poundage = beat().getRequest().getParameter("poundage");
		String userCouponid = beat().getRequest().getParameter("userCouponid")==null?"":beat().getRequest().getParameter("userCouponid");  //用户的优惠券id
		String qianDanCompanyAE = beat().getRequest().getParameter("qianDanCompanyAE")==null?"":beat().getRequest().getParameter("qianDanCompanyAE");
		
		
		SorderEntity orderE =  OrderAssembleService.getInstance().getOrderEntityById(Long.valueOf(orderid));
		if(null != userCouponid && !userCouponid.equals("")){
			try {
				UserCouponsEntity usercouponE = RSBLL.getstance().getUserCouponsService().getUserCouponsById(Long.valueOf(userCouponid));
				usercouponE.setOrderid(Long.valueOf(orderid));
				usercouponE.setSuperid(orderE.getSuperid());
				usercouponE.setIsuse(1);
				RSBLL.getstance().getUserCouponsService().updateUserCoupons(usercouponE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*//根据母单ID查询子单的集合
		String condtion = "orderid = '"+orderid+"'";
		List<SorderChildrenEntity> orderChildEList = OrderAssembleService.getInstance().getOrderChildrEntityList(condtion);*/
		
		orderE.setTotalmoney(Float.valueOf(totalmoney));     //订单金额
		orderE.setPaymoneycount(Float.valueOf(paymoney));    //应付金额
//		orderE.setPrepaidamount(Float.valueOf(prepaidamount));   //预付金额    
//		orderE.setCurrentempid(UtilsHelper.getLoginId(beat())); //设置当前服务者人 （为当前登录人员）
		orderE.setContract(contract);
		orderE.setUpdatetime(new Date().getTime());
		orderE.setSigntime(new Date().getTime());              //设置签单时间duxf 2015-7-7
		//签单后订单的签单状态改为1已签单的状态
		orderE.setSignstate(1);
		
		if(Float.valueOf(paymoney) == 0){
			orderE.setPaystate(2);  //如果应支付为0则把母单的支付状态改为完款
		}
		
/*		//如果预付金额等于订单金额则支付状态为完款，为空则支付状态为未付 否则为预付状态
		//签单后为订单状态为正式订单
//		orderE.setOrderstate(1);
//		//修改订单服务状态
//		orderE.setServerstate(1);
*/		
		//添加跟进信息
		FollowupEntity followE =new FollowupEntity();
		
		followE.setEmpid(UtilsHelper.getLoginId(beat()));
		followE.setIntentcode(3);
		followE.setOrderid(Long.valueOf(orderid));
		followE.setAddtime(new Date().getTime());
		followE.setContent(contents);
		followE.setTitle("签单申请");
		
		//添加订单信息
		OrderprocessEntity orderprocessE = new OrderprocessEntity();
		orderprocessE.setOrderid(Long.valueOf(orderid));
		orderprocessE.setOrderstate(3);
		orderprocessE.setOpid(UtilsHelper.getLoginId(beat()));
		orderprocessE.setOptime(new Date().getTime()); 
		orderprocessE.setOption("签单");
		orderprocessE.setOptcode(1143);
		
		
		
		try {
			/*for(SorderChildrenEntity childE : orderChildEList){
				childE.setCostate(1); //设置子单状态为服务中
				childE.setUpdatetime(new Date().getTime());
				childE.setEmpid(UtilsHelper.getLoginId(beat())); //设置子单的当前负责人
				OrderAssembleService.getInstance().updateOrderChild(childE);
			}*/
			//更新母订单
			OrderAssembleService.getInstance().updateOrder(orderE);
			//添加跟进记录
			OrderAssembleService.getInstance().AddFollowUp(followE);
			//添加操作订单记录
			OrderAssembleService.getInstance().addOrderProcess(orderprocessE);
			

			try {
				ProcessBuz.pb.sendmessage(orderE, Constants.ORDER_SQ_ACTION_TYPE);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			jo.put("res", "ok");
			jo.put("msg", "开始服务操作成功!");
		} catch (Exception e) {
			jo.put("res", "fail");
			jo.put("msg", "开始服务失败!");
			e.printStackTrace();
		}finally{
			if(jo.get("res").equals("ok")){  //发起任务
				LvTaskService lvTaskService = new LvTaskServiceImpl();
				//获取当前登陆人
				Long empid = UtilsHelper.getLoginId(beat());
				try {
					//发起任务
					lvTaskService.StartTask(empid.toString(),String.valueOf(orderid),qianDanCompanyAE);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		
		return new JSONActionResult(jo.toString());
	}
	@Path("/isblanbusiness")
	public ActionResult isblanbusiness(){
		return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				String orderid = beat().getRequest().getParameter("orderid");
				String text ="false";
				if(StringUtil.isEmpty(orderid)){
					long lor = Long.valueOf(orderid);
					text = RealBusinessService.getInstance().orderIsbandBusiness(lor);
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
