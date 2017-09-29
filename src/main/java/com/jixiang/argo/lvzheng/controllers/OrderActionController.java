package com.jixiang.argo.lvzheng.controllers;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jixiang.argo.lvzheng.actionresult.JSONActionResult;
import com.jixiang.argo.lvzheng.buz.EmployBuz;
import com.jixiang.argo.lvzheng.buz.OrderBuz;
import com.jixiang.argo.lvzheng.buz.ProcessBuz;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.service.LvTaskService;
import com.jixiang.argo.lvzheng.service.impl.LvTaskServiceImpl;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.vo.EmploysVo;
import com.jixiang.argo.lvzheng.vo.PaymentVo;
import com.jixiang.argo.lvzheng.vo.ZhichuVo;
import com.jixiang.union.interceptor.AutherCheck;
import com.jixiang.union.user.model.Employee;
import com.jixiang.union.user.tools.LoginTool;
import com.jx.argo.ActionResult;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.contract.ISorderChildrenService;
import com.jx.service.newcore.contract.ISorderService;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.FollowupEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.PayProcessEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.newcore.entity.SpendEntity;
import com.jx.service.newcore.entity.UserCouponsEntity;

@Path("/action")
public class OrderActionController extends AbstractController{

	ISorderService iss = RSBLL.getstance().getSorderService();
	@Path("/ckorder/{oid:\\d+}")
	public ActionResult cuikuan(long oid){
		JSONObject jo = new JSONObject();
		long opid = UtilsHelper.getLoginId(beat());
		SorderEntity se = null;
		try {
			se = iss.getSorderEntityByid(oid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			jo.put("res", "fail");
			jo.put("msg", "催款失败！未能找到相关订单消息，或查询该订单出现异常！");
			e.printStackTrace();
			
		}
		if(se != null){
			try {
				ProcessBuz.pb.ckorder(se, opid);
				jo.put("res", "ok");
				jo.put("msg", "催款成功！已把相关款项信息发信息给客户！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jo.put("res", "fail");
				jo.put("msg", "催款失败！在发送催款消息的过程中出现了异常，请稍后再试！");
			}
		}
		
		return new JSONActionResult(jo.toString());
	}
	@Path("/zcorder")
	public ActionResult zhichu(){
		JSONObject jo = new JSONObject();
		ZhichuVo zv = new ZhichuVo();
		long opid = UtilsHelper.getLoginId(beat());
		zv = (ZhichuVo) tranceParmtersToObject(zv.getClass());
		long spendid = 0;
		if(zv != null){
			SorderEntity order = null;
			try {
				order = RSBLL.getstance().getSorderService().getSorderEntityByid(zv.getOrderid());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SpendEntity se = new SpendEntity();
			se.setContent(zv.getContent());
			se.setOrderid(zv.getOrderid());
			se.setOpempid(opid);
			se.setOpttime(new Date().getTime());
			se.setSpendmoney(zv.getZhichu());
			se.setSpendtype(zv.getFeetype());
			se.setEmpid(zv.getEmpid());
			try {
				spendid = RSBLL.getstance().getSpendRecordService().addspendRecord(se);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(spendid > 0){
				PayProcessEntity pe = new PayProcessEntity();
				pe.setContents(spendid+"");
				pe.setOpempid(opid);
				pe.setOptime(new Date().getTime());
				pe.setOptype(Constants.ORDER_ZC_ACTION_TYPE);
				pe.setOrderid(zv.getOrderid());
				pe.setOrderstate(order.getOrderstate());
				pe.setPaychannel(1);
				
				pe.setPaystate(order.getPaystate());
				pe.setPaytotal(zv.getZhichu());
				long payprocessid = 0;
				try {
					payprocessid = RSBLL.getstance().getPayprocessService().addNewPayprocess(pe);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jo.put("res", "ok");
				jo.put("msg", "支出操作成功！");
			}
		}
		return new JSONActionResult(jo.toString());
	}
	@Path("/skorder")
	public ActionResult jieKuan(){
		PaymentVo pv = new PaymentVo();
		pv = (PaymentVo) tranceParmtersToObject(pv.getClass());
		JSONObject jo = new JSONObject();
		long opid = UtilsHelper.getLoginId(beat());
		jo.put("res", "fail");
		jo.put("msg", "收款操作失败！原因不详！");
		if(pv != null){
			long orderid = pv.getOrderid();
			SorderEntity se =null;
			try {
				se = iss.getSorderEntityByid(orderid);
				float prepay = se.getPrepaidamount();
				prepay = prepay + pv.getPaytotal();
				se.setPrepaidamount(prepay);
				if(prepay > 0 && prepay < se.getPaymoneycount()){
					se.setPaystate(Constants.ORDER_PAY_STATE_YF);
				}else if(prepay >= se.getPaymoneycount()){
					se.setPaystate(Constants.ORDER_PAY_STATE_QK);
				}else if(prepay == 0){
					se.setPaystate(Constants.ORDER_PAY_STATE_WF);
				}
				iss.updateSorderEntity(se);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				jo.put("msg", "更新收款操作失败！未能找到相关订单，或者更新订单状态过程中异常！请稍后再试！");
				e.printStackTrace();
			}
			if(se != null){
				try {
					ProcessBuz.pb.skorder(se, opid,pv);
					jo.put("res", "ok");
					jo.put("msg", "收款操作成功！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					jo.put("msg", "更新收款操作成功，但是再给用户发送收款消息过程中出现异常！");
					e.printStackTrace();
				}
			}
		}
		return new JSONActionResult(jo.toString());
	}
	
	
	@Path("/skorderCop")
	public ActionResult jieKuanCop(){
		PaymentVo pv = new PaymentVo();
		pv = (PaymentVo) tranceParmtersToObject(pv.getClass());
		JSONObject jo = new JSONObject();
		long opid = UtilsHelper.getLoginId(beat());
		jo.put("res", "fail");
		jo.put("msg", "收款操作失败！原因不详！");
		if(pv != null){
			long orderid = pv.getOrderid();
			SorderEntity se =null;
			try {
				se = iss.getSorderEntityByid(orderid);
				float prepay = se.getPrepaidamount();
				prepay = prepay + pv.getPaytotal();
				se.setPrepaidamount(prepay);
				if(prepay > 0 && prepay < se.getPaymoneycount()){
					se.setPaystate(Constants.ORDER_PAY_STATE_WF_TERRACE);
				}else if(prepay >= se.getPaymoneycount()){
					se.setPaystate(Constants.ORDER_PAY_STATE_QK_TERRACE);
				}else if(prepay == 0){
					se.setPaystate(Constants.ORDER_PAY_STATE_WF_TERRACE);
				}
				iss.updateSorderEntity(se);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				jo.put("msg", "更新收款操作失败！未能找到相关订单，或者更新订单状态过程中异常！请稍后再试！");
				e.printStackTrace();
			}
			if(se != null){
				try {
					pv.setPaychannel(7);
					ProcessBuz.pb.skorder(se, opid,pv);
					jo.put("res", "ok");
					jo.put("msg", "收款操作成功！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					jo.put("msg", "更新收款操作成功，但是再给用户发送收款消息过程中出现异常！");
					e.printStackTrace();
				}
			}
		}
		return new JSONActionResult(jo.toString());
	}
	
	
	/**
	 * 修改金额 duxf
	 * @return
	 */
	@Path("/editmoney")
	public ActionResult editmoney(){
		JSONObject jo = new JSONObject();
		long opid = UtilsHelper.getLoginId(beat());
		jo.put("res", "fail");
		jo.put("msg", "修改金额失败！原因不详！");
		String orderid= beat().getRequest().getParameter("orderid");
		String yjprice= beat().getRequest().getParameter("yjprice");  //原价
		yjprice = yjprice.substring(1);
		String xjprice= beat().getRequest().getParameter("xjprice");  //现价
		String contents= beat().getRequest().getParameter("contents"); //原因
		
		try {
			SorderEntity  se = iss.getSorderEntityByid(Long.valueOf(orderid));
			se.setPaymoneycount(Float.parseFloat(xjprice));  //修改后的金额
			if(se.getPrepaidamount()!=0){
				Float weikuan = se.getPaymoneycount()-se.getPrepaidamount();
				if(weikuan==0){
					se.setPaystate(2);  //设置为完款
				}
			}
			iss.updateSorderEntity(se);
			
			PayProcessEntity pe = new PayProcessEntity();
			pe.setPaystate(se.getPaystate());
			pe.setOpempid(opid);
			pe.setOptime(new Date().getTime());
			pe.setOptype(3);   //修改金额
			pe.setOrderstate(se.getOrderstate());
			pe.setPaychannel(1);
			pe.setContents(contents);
			pe.setPaynumber(Float.valueOf(yjprice));  //把原价存到其中
			pe.setPaytotal(Float.valueOf(xjprice));  //把现价存到其中
			pe.setOrderid(se.getOrderid());
			RSBLL.getstance().getPayprocessService().addNewPayprocess(pe);
			jo.put("res", "ok");
			jo.put("msg", "修改操作成功！");
		} catch (Exception e) {
			jo.put("res", "fail");
			jo.put("msg", "修改操作失败！");
			e.printStackTrace();
		}
		return new JSONActionResult(jo.toString());
	}
	
	@Path("/fqorder/{orderid:\\d+}")
	public ActionResult fzlist(long orderid){
		String reason = beat().getRequest().getParameter("reason");
		JSONObject jo = new JSONObject();
		long opid = UtilsHelper.getLoginId(beat());
		SorderEntity se = null;
		jo.put("res", "fail");
		jo.put("msg", "废弃失败！");
		try {
			se = iss.getSorderEntityByid(orderid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jo.put("msg", "查询订单过程出现异常！请稍后再试！");
		}
		boolean f = false;
		if(se != null){
			se.setOrderstate(Constants.fq_order_state);
			se.setGvreason(reason);
			se.setUpdatetime(new Date().getTime());
			try {
				iss.updateSorderEntity(se);
				FollowupEntity fe = new FollowupEntity();
				fe.setAddtime(new Date().getTime());
				fe.setContent(reason);
				fe.setEmpid(opid);
				fe.setIntentcode(Constants.ORDER_FOLLOW_UP_CODE_LOW);
				fe.setOrderid(orderid);
				fe.setTitle("");
				RSBLL.getstance().getFollowupService().addFollowupEntity(fe);
				
				//如果订单未支付且使用了优惠券就退回优惠券
				if(se.getPaystate() == 0){
					String coupons_condition = " userid='"+se.getUserid()+"' and orderid='"+orderid+"' ";
			    	List<UserCouponsEntity> usercouponlist =  RSBLL.getstance().getUserCouponsService().getUserCoupons(coupons_condition, 1, 1, "");
			    	if(null != usercouponlist && usercouponlist.size()>0){
			    		UserCouponsEntity usercoupon = usercouponlist.get(0);
			    		usercoupon.setOrderid(0);
			    		usercoupon.setSuperid(0);
			    		usercoupon.setIsuse(0);
			    		RSBLL.getstance().getUserCouponsService().updateUserCoupons(usercoupon);
			    	}	
				}
				try {
					//调用服务系统暂停服务
					RSBLL.getstance().getProcessService().deleteProcessInstanceByOrderId(String.valueOf(se.getOrderid()), reason);
				} catch (Exception e) {
					System.out.println("*******************************服务系统暂停失败*******orderid:"+se.getOrderid());
				}
				f = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jo.put("msg", "更新订单订单状态失败！");
			}
			
		}
		if(f){
			try {
				ProcessBuz.pb.cancelOrder(se,opid);
				jo.put("res", "ok");
				jo.put("msg", "订单状态更新成功！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jo.put("msg", "订单状态更新成功，给客户发送相关信息过程中出现异常！");
			}
		}
		return new JSONActionResult(jo.toString());
	}
	@Path("/fzlist")
	public ActionResult fzlist(){
		List<EmploysVo> vlist = null;
		try {
			vlist = EmployBuz.getstance().fzvoList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		beat().getModel().add("fzlist", vlist);
		return view("common/fzlist");
	}
	@Path("/lzorder/{fzid:\\d+}/{orderid:\\d+}")
	public ActionResult liuzhuan(long fzid,long orderid){
		JSONObject jo = new JSONObject();
		long opid = UtilsHelper.getLoginId(beat());
		ISorderService iss = RSBLL.getstance().getSorderService();
		
		try {
			SorderEntity se = iss.getSorderEntityByid(orderid);
			se.setCurrentempid(fzid);
			se.setUpdatetime(new Date().getTime());
			List<SorderChildrenEntity> colist = OrderBuz.getstance().getChildorderlistBypoid(orderid);
			if(colist != null && colist.size() > 0){
				for(SorderChildrenEntity sc : colist){
					sc.setEmpid(fzid);
					RSBLL.getstance().getSorderChildrenService().updateSorderChildrenEntity(sc);
				}
			}
			iss.updateSorderEntity(se);
			ProcessBuz.pb.lzorder(se, opid);
			//iss.lzorder(fzid, orderid, opid);//(fwid, orderid, opid);
			jo.put("res", "ok");
			jo.put("msg", "流转成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jo.put("res", "fail");
			jo.put("msg", "发生异常！");
		}
		return new JSONActionResult(jo.toString());
	}
	@Path("/fuwulist")
	public ActionResult fuwulist(){
		List<EmploysVo> vlist = null;
		try {
			vlist = EmployBuz.getstance().fuwulist();
			//接收改派AE的参数
			String gpAE = beat().getRequest().getParameter("gpAE")==null?"":beat().getRequest().getParameter("gpAE");
			//客服指派AE的参数  duxf 2015/8/10
			String kfzpAE = beat().getRequest().getParameter("kfzpAE")==null?"":beat().getRequest().getParameter("kfzpAE");
			if(!"".equals(gpAE)){
				beat().getModel().add("gpAE", gpAE);
			}
			if(!"".equals(kfzpAE)){
				beat().getModel().add("kfzpAE", kfzpAE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		beat().getModel().add("fwlist", vlist);
		return view("common/fuwulist");
	}
	@Path("/notZpfuwulist")
	public ActionResult notZpfuwulist(){
		List<EmploysVo> vlist = null;
		try {
			vlist = EmployBuz.getstance().fuwulist();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		beat().getModel().add("fwlist", vlist);
		return view("common/notZpfuwulist");
	}
	@Path("/fwlist")
	public ActionResult fwlist(){
		String city= beat().getRequest().getParameter("city");
		List<EmploysVo> vlist = null;
		try {
			vlist = EmployBuz.getstance().fwvoList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(city==null||"".equals(city.trim())){//自动选择城市
			city="1";
		}
		beat().getModel().add("city", city);
		beat().getModel().add("fwlist", vlist);
		return view("common/fwlist");
	}
	@Path("/servstate/{orderid:\\d+}/{chiorder:\\d+}")
	public ActionResult orderchildOver(long orderid,long chiorder){
		JSONObject jo = new JSONObject();
		jo.put("res", "fail");
		jo.put("msg", "订单状态更新失败");
		long opid = UtilsHelper.getLoginId(beat());
		ISorderChildrenService isc = RSBLL.getstance().getSorderChildrenService();
		SorderChildrenEntity sce = null;
		EmployersEntity ee = null;
		try {
			sce = isc.getSorderChildrenEntityByid(chiorder);
			ee = RSBLL.getstance().getEmployersService().getEmployersEntityById(opid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jo.put("msg", "查询子单过程失败！");
		}
		if(sce != null){
			sce.setCostate(Constants.ORDER_STATE_CHILD_STATE);
			sce.setUpdatetime(new Date().getTime());
			sce.setEmpid(ee.getEmpid());
			boolean f= false;
			try {
				isc.updateSorderChildrenEntity(sce);
				f = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jo.put("msg", "更新订单状态过程中发生异常，请稍后再试！");
			}
			if(f){
				try {
					ProcessBuz.pb.childover(chiorder);
					jo.put("res", "ok");
					String rs = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss",new Date().getTime());
					jo.put("msg", rs+" "+ee.getRealname());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					jo.put("msg", "订单状态更新成功，但是发送给客户相关信息过程当中异常！");
				}
				
			}
			
		}
		return new JSONActionResult(jo.toString());
	}
	@Path("/servicestep/{orderid:\\d+}/{childorderid:\\d+}/{actioncode:\\d+}")
	public ActionResult serviceStep(long orderid,long childorderid,int actioncode){
		JSONObject jo = new JSONObject();
		ISorderService ss = RSBLL.getstance().getSorderService();
		long opid = UtilsHelper.getLoginId(beat());
		if(opid != 0){
			Employee emp = LoginTool.getLoginUserInfo(beat().getRequest());
			SorderEntity se = null;
			jo.put("res", "fail");
			try {
				se = ss.getSorderEntityByid(orderid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				jo.put("msg", "查询订单过程当初发生异常！请稍后再试！");
				e.printStackTrace();
				
			}
			if(se != null){
				try {
					ProcessBuz.pb.childorderstep(se, childorderid, opid, actioncode);
					jo.put("res", "ok");
					jo.put("infoUser", emp.getRealname());
					jo.put("infoTime", UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", new Date().getTime()));
					
					jo.put("msg", "发送成功");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					jo.put("msg", "发送客户信息过程中出现异常！");
				}
			}
		}else{
			jo.put("msg", "登录超时!请重新登录!");
		}
		return new JSONActionResult(jo.toString());
	}
	@Path("/yjorder/{fwempid:\\d+}/{orderid:\\d+}")
	public ActionResult yjorder(long fwempid,long orderid){
		JSONObject jo = new JSONObject();
		long opid = UtilsHelper.getLoginId(beat());
		String gpAE = beat().getRequest().getParameter("gpAE")==null?"":beat().getRequest().getParameter("gpAE");
		String kfzpAE = beat().getRequest().getParameter("kfzpAE")==null?"":beat().getRequest().getParameter("kfzpAE"); //客服指派AE参数 duxf 2015/8/10
		ISorderService iss = RSBLL.getstance().getSorderService();
		try {
			SorderEntity se = iss.getSorderEntityByid(orderid);
			se.setFwgwempid(fwempid);
			se.setUpdatetime(new Date().getTime());
			if(se.getOrderstate() == Constants.zq_order_state){
				se.setOrderstate(Constants.YJ_ORDER_STATE);
			}
			//更新企业库信息   adder  wy    timer :  2015-08-17   begin
			if(se != null){
				long busid = se.getBusid();
				BusinessLibaryEntity ble = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(busid);
				if(ble != null){
					ble.setStatus(1);
					RSBLL.getstance().getBusinessLibaryService().updateBusinessLibaryEntity(ble);
				}
			}
			// end
			
			//如果为客服指派AE则【1.订单状态改为1(正式订单) 2.签单状态改为已签单 3.签单时间为当前时间】
			if("kfzpAE".equals(kfzpAE)){
				se.setOrderstate(1); 
				//se.setSignstate(1);
				se.setPiesingletime(new Date().getTime());
				//se.setSigntime(new Date().getTime());
				se.setUpdatetime(new Date().getTime());
				ISorderChildrenService childSorder = RSBLL.getstance().getSorderChildrenService();
				List<SorderChildrenEntity> childsorderList = childSorder.getSorderChildrenEntityListBypage("orderid='"+orderid+"'", 1, 99, "");
				if(null != childsorderList && childsorderList.size()>0){
					for(SorderChildrenEntity childE : childsorderList){
						childE.setCostate(1); //设置子单状态为服务中
						childE.setUpdatetime(new Date().getTime());
						childE.setEmpid(fwempid); //设置子单的当前负责人
						childSorder.updateSorderChildrenEntity(childE);
					}
				}
			}
			iss.updateSorderEntity(se);
			
			
			if("gpAE".equals(gpAE)){
				ProcessBuz.pb.gpAEOrderProcess(se,opid);
			}else if("kfzpAE".equals(kfzpAE)){
				ProcessBuz.pb.kfzpAEOrderProcess(se,opid);
			}else{
				ProcessBuz.pb.yjOrderProcess(se,opid);
			}
			
			jo.put("res", "ok");
			jo.put("msg", "指派成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jo.put("res", "fail");
			jo.put("msg", "发生异常！");
		}
		return new JSONActionResult(jo.toString());
	}
	//指派
	@Path("/zporder/{fwid:\\d+}/{orderid:\\d+}")
	public ActionResult zporder(long fwid,long orderid){
		JSONObject jo = new JSONObject();
		long opid = UtilsHelper.getLoginId(beat());
		ISorderService iss = RSBLL.getstance().getSorderService();
		try {
			boolean isTwoZp = false;
			boolean isSamefwid = true;
			SorderEntity se = iss.getSorderEntityByid(orderid);
			if(se.getEmpid()!=fwid){
				isSamefwid = false;
			}
			se.setEmpid(fwid);
			se.setUpdatetime(new Date().getTime());
			se.setPiesingletime(new Date().getTime());  //设置派单时间 duxf 2015-7-7
			
			if(se.getOrderstate() == Constants.two_ORDER_STATE){
				isTwoZp = true;
			}

			if(se.getOrderstate() == Constants.YY_ORDER_STATE||isTwoZp){
				se.setOrderstate(Constants.zq_order_state);
			}
			iss.updateSorderEntity(se);
			if(isTwoZp){
				ProcessBuz.pb.twoZpOrderProcess(se,opid,isSamefwid);
			}else{
				ProcessBuz.pb.zpOrderProcess(se,opid);
			}
			
		
			
			jo.put("res", "ok");
			jo.put("msg", "指派成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jo.put("res", "fail");
			jo.put("msg", "发生异常！");
		}
		return new JSONActionResult(jo.toString());
	}
}
