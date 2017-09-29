package com.jixiang.argo.lvzheng.buz;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.service.RealBusinessService;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.vo.PaymentVo;
import com.jx.service.messagecenter.contract.IActionService;
import com.jx.service.messagecenter.contract.IWeixinActionService;
import com.jx.service.messagecenter.entity.OrderMessageEntity;
import com.jx.service.newcore.contract.IActionsService;
import com.jx.service.newcore.contract.IEmployersService;
import com.jx.service.newcore.contract.IFollowupService;
import com.jx.service.newcore.contract.ILoginService;
import com.jx.service.newcore.contract.IOrderprocessService;
import com.jx.service.newcore.contract.IProductCategoryService;
import com.jx.service.newcore.contract.IProductPropertiesService;
import com.jx.service.newcore.contract.IServiceprocessService;
import com.jx.service.newcore.contract.ISorderChildrenService;
import com.jx.service.newcore.entity.ActionsEntity;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.FollowupEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.OrderprocessEntity;
import com.jx.service.newcore.entity.PayProcessEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;
import com.jx.service.newcore.entity.ServiceprocessEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;

public class ProcessBuz {

	public  static ProcessBuz pb = new ProcessBuz();
	private static IFollowupService ifs = RSBLL.getstance().getFollowupService();
	private static ILoginService ils = RSBLL.getstance().getLoginService();
	private static IOrderprocessService ops = RSBLL.getstance().getOrderprocessService();
	private static IWeixinActionService was = RSBLL.getstance().getWeixinActionService();
	private static IEmployersService ies = RSBLL.getstance().getEmployersService();
	private static IServiceprocessService iss = RSBLL.getstance().getServiceprocessService();
	private static ISorderChildrenService ics = RSBLL.getstance().getSorderChildrenService();
	private static IProductCategoryService ipc = RSBLL.getstance().getProductCategoryService();
	private static IActionsService as = RSBLL.getstance().getActionService();
	private static IProductPropertiesService ips = RSBLL.getstance().getProductPropertiesService();
	public void childover(long coid)throws Exception{
		SorderChildrenEntity sc = ics.getSorderChildrenEntityByid(coid);
		long cateid = sc.getProdcateid();
		long orderid = sc.getOrderid();
		SorderEntity se = RSBLL.getstance().getSorderService().getSorderEntityByid(orderid);
		String servstr = ipc.getProductCategoryEntityById(cateid).getCname();
		OrderMessageEntity ome = new OrderMessageEntity();
		
		long uid = se.getUserid();
		LoginEntity le = ils.getLoginEntityById(uid);
		
		long empid = se.getEmpid();
		if(empid > 0){
			EmployersEntity ee = ies.getEmployersEntityById(empid);
			
			ome.setWX_FW_NAME(ee.getRealname());
			ome.setEmopenid(ee.getOpenid());
			ome.setWX_FW_PHONE(ee.getPhonenumber());
			ome.setWX_EMPID(ee.getEmpid());
		}
		ome.setWX_SERVER_STR(servstr);
		ome.setWX_PAY_TOTALMONEY(se.getTotalmoney());
		ome.setOrderstatestr((String) KVMap.orderstatemap.get(se.getOrderstate()+""));;
		ome.setWX_ORDERID(se.getOrderid());
		ome.setActioncode(1126);
		ome.setWX_USER_NAME(le.getUsername() == null ? le.getUserphone():le.getUsername());
		ome.setOpenid(le.getOpenid());
		ome.setWX_FANS_PHONE(le.getUserphone());
		was.sendModuleByome(ome);
	}
	public void childorderstep(SorderEntity se,long childorderid, long optid,int actioncode)throws Exception{
		ServiceprocessEntity spe = new ServiceprocessEntity();
		spe.setActionid(actioncode);
		spe.setOpid(optid);
		spe.setOptime(new Date().getTime());
		spe.setOrderid(se.getOrderid());
		spe.setOrderstate(se.getOrderstate());
		spe.setChildorderid(childorderid);
		iss.addServiceprocessEntity(spe);
		
		sendmessage(se,actioncode);
	}
	public String getServicestrByorder(long orderid)throws Exception{
		String ret = "";
		String condition = "orderid = "+orderid;
		StringBuffer sb = new StringBuffer();
		List<SorderChildrenEntity> list = ics.getSorderChildrenEntityListBypage(condition, 1, 99, "coid");
		
		System.out.println("========size is ==="+list.size());
		if(null != list && list.size() > 0){
			for(SorderChildrenEntity so : list){
				ProductCategoryEntity pcate = ipc.getProductCategoryEntityById(so.getProdcateid());
				System.out.println("==========="+pcate.getCname());
				sb.append(pcate.getCname()+"/");
			}
		}
		if(!sb.equals("") && sb.length() > 1){
			ret = sb.substring(0, sb.length() - 1);
		}
		System.out.println("this "+orderid+" server str is "+ret);
		return ret;
	}
	public void sendmessagejz(SorderEntity se,long actioncode,long procateid)throws Exception{
		System.out.println("come into jizhang message process");
		String condition = "orderid="+se.getOrderid()+" and prodcateid="+procateid;
		OrderMessageEntity ome = new OrderMessageEntity();
		List<SorderChildrenEntity> list = ics.getSorderChildrenEntityListByCustoms(condition, "coid");
		String zq = "0";
		if(list != null && list.size() > 0){
			SorderChildrenEntity childorder = list.get(0);
			String prostr = childorder.getPropertid();
			JSONArray ja = JSONArray.fromObject(prostr);
			if(ja != null && ja.size() > 0){
				for(int i=0,c=ja.size();i<c;i++){
					JSONObject jo = ja.getJSONObject(i);
					if(jo.getString("propertyval").equals("bookOrder")){
						long prpid = Long.parseLong(jo.getString("propertyid"));
						ProductPropertiesEntity ppe = ips.getProductPropertiesEntityById(prpid);
						zq = ppe.getProvalue();
						break;
					}
				}
			}
			if(actioncode == 1132){
				ome.setWX_JZ_ZHOUQI(zq);
			}else if(actioncode == 1136){
				
			}else{
				String addmsg = "";
				String con = "actionid="+actioncode+" and orderid = "+se.getOrderid();
				int vv = Integer.parseInt(zq);
				int curr = iss.getcountBycondition(con);
				zq = curr+"/"+zq;
				int ncx = curr + 1;
				ome.setWX_JZ_ZHOUQI(zq);
				ome.setWX_CURR_CONTENT("当前进度：第"+curr+"期");
				ome.setWX_NEXT_CONTENT("下一进度：第"+ncx+"期");
				if(curr + 1 == vv){
					ome.setWX_ADD_MESSAGE("您的代理记账服务周期仅剩一个月，为了避免不必要的损失，请您尽早续费。");
				}else if(curr == vv){
					ome.setWX_NEXT_CONTENT("");
					ome.setWX_ADD_MESSAGE("您的代理记账服务已结束，如还需要继续记账服务，只需在线下单购买即可。");
				}
				ome.setWX_ADD_MESSAGE(addmsg);
			}
			ome.setWX_ORDERID(se.getOrderid());
			
			was.sendModuleByome(ome);
		}
	}
	public void sendmessage(SorderEntity se,long actioncode)throws Exception{
		ActionsEntity ae = as.getActionEntityByid(actioncode);
		OrderMessageEntity ome = new OrderMessageEntity();
		if(ae.getProid() == 4001 || ae.getProid() == 4002){//代理记账特殊操作
			sendmessagejz(se,actioncode,ae.getProid());
			
		}else{
			
			long uid = se.getUserid();
			LoginEntity le = ils.getLoginEntityById(uid);
			
			long empid = se.getEmpid();
			if(empid > 0){
				EmployersEntity ee = ies.getEmployersEntityById(empid);
				
				ome.setWX_FW_NAME(ee.getRealname());
				ome.setEmopenid(ee.getOpenid());
				ome.setWX_FW_PHONE(ee.getPhonenumber());
				ome.setWX_EMPID(ee.getEmpid());
			}else if(empid == 0 && se.getSignstate() == 1){
				empid = se.getKefuid();
				EmployersEntity ee = ies.getEmployersEntityById(empid);
				
				ome.setWX_FW_NAME(ee.getRealname());
				ome.setEmopenid(ee.getOpenid());
				ome.setWX_FW_PHONE(ee.getPhonenumber());
				ome.setWX_EMPID(ee.getEmpid());
			}
			ome.setWX_SERVER_STR(getServicestrByorder(se.getOrderid()));
			ome.setWX_PAY_BOOKMONEY(se.getPrepaidamount());
			ome.setWX_PAY_TOTALMONEY(se.getPaymoneycount());
			ome.setWX_PAY_NEEDMONEY(se.getPaymoneycount() - se.getPrepaidamount());
			ome.setOrderstatestr((String) KVMap.orderstatemap.get(se.getOrderstate()+""));;
			ome.setWX_ORDERID(se.getOrderid());
			ome.setActioncode(actioncode);
			ome.setWX_USER_NAME(le.getUsername() == null ? le.getUserphone():le.getUsername());
			ome.setOpenid(le.getOpenid());
			ome.setWX_FANS_PHONE(le.getUserphone());
			
			was.sendModuleByome(ome);
		}
		
		
	}
	public void lzorder(SorderEntity se,long opid)throws Exception{
		Date d = new Date();
		OrderprocessEntity ope = new OrderprocessEntity();
		ope.setOptime(d.getTime());
		ope.setOpid(opid);
		ope.setOption("订单流转");
		ope.setOrderid(se.getOrderid());
		ope.setOrderstate(se.getOrderstate());
		ope.setEmpid(se.getCurrentempid());
		ope.setOptcode(1131);
		ops.addOrderprocessEntity(ope);
		
		sendmessage(se,Constants.ORDER_LZ_ACTION_CODE);
	}
	public void cancelOrder(SorderEntity se,long opid)throws Exception{
		Date d = new Date();
		OrderprocessEntity ope = new OrderprocessEntity();
		ope.setOptime(d.getTime());
		ope.setOption("取消订单");
		ope.setOrderid(se.getOrderid());
		ope.setOrderstate(se.getOrderstate());
		ope.setOptcode(1105); //duxf 添加取消订单的code
		ope.setOpid(opid);  //duxf 2015/8/17 添加取消订单的人
		ops.addOrderprocessEntity(ope);
//		sendmessage(se,Constants.ORDER_CANCEL_ACTION_CODE);
	}
	public void ckorder(SorderEntity se,long opid)throws Exception{
		PayProcessEntity pe = new PayProcessEntity();
		pe.setOpempid(opid);
		pe.setOptime(new Date().getTime());
		pe.setOptype(Constants.ORDER_CK_ACTION_TYPE);
		pe.setOrderid(se.getOrderid());
		pe.setOrderstate(se.getOrderstate());
		pe.setPaystate(se.getPaystate());
		RSBLL.getstance().getPayprocessService().addNewPayprocess(pe);
		sendmessage(se,Constants.ORDER_CK_ACTION_CODE);
	}
	public void skorder(SorderEntity se,long opid,PaymentVo pv)throws Exception{
		PayProcessEntity pe = new PayProcessEntity();
		pe.setContents(pv.getContents());
		pe.setOpempid(opid);
		pe.setOptime(new Date().getTime());
		pe.setOptype(Constants.ORDER_SK_ACTION_TYPE);
		pe.setOrderid(pv.getOrderid());
		pe.setOrderstate(se.getOrderstate());
		pe.setPaychannel(pv.getPaychannel());
		pe.setPayfee(pv.getPayfee());
		pe.setPaynumber(pv.getPaynumber());
		pe.setPaystate(se.getPaystate());
		pe.setPaytotal(pv.getPaytotal());
		RSBLL.getstance().getPayprocessService().addNewPayprocess(pe);
		sendmessage(se,Constants.ORDER_SK_ACTION_CODE);
	}
	public void addNeworder(SorderEntity se,int intentcode,long opid)throws Exception{
		Date d = new Date();
		
		OrderprocessEntity ope = new OrderprocessEntity();
		ope.setOptime(d.getTime());
		ope.setOption("预约订单");
		ope.setOrderid(se.getOrderid());
		ope.setOrderstate(se.getOrderstate());
		ops.addOrderprocessEntity(ope);
		sendmessage(se,Constants.ORDER_SUBMIT_ACTION_CODE);
		
	}
	public void yjOrderProcess(SorderEntity se,long opid)throws Exception{
		Date d = new Date();
		OrderprocessEntity ope = new OrderprocessEntity();
		ope.setOptime(d.getTime());
		
		ope.setOption("移交订单");
		ope.setOrderid(se.getOrderid());
		ope.setOrderstate(se.getOrderstate());
		ope.setOpid(opid);
		ope.setOptcode(1137);
		ope.setEmpid(se.getFwgwempid());
		ops.addOrderprocessEntity(ope);
		//sendmessage(se,Constants.ORDER_YI_ACTION_CODE);
		
		OrderMessageEntity ome = new OrderMessageEntity();
		ome.setActioncode(Constants.ORDER_YI_ACTION_CODE);
		ome.setWX_ORDERID(se.getOrderid());
		LoginEntity le = ils.getLoginEntityById(se.getUserid());
		ome.setOpenid(le.getOpenid());
		long empid = se.getFwgwempid();
		if(empid > 0){
			EmployersEntity ee = ies.getEmployersEntityById(empid);
			
			ome.setWX_FW_NAME(ee.getRealname());
			ome.setEmopenid(ee.getOpenid());
			ome.setWX_FW_PHONE(ee.getPhonenumber());
			ome.setWX_EMPID(ee.getEmpid());
		}
		long zxempid = se.getEmpid();
		if(zxempid > 0){
			EmployersEntity ee = ies.getEmployersEntityById(zxempid);
			ome.setWX_ADD_MESSAGE(ee.getRealname());
			
		}
		ome.setWX_FANS_PHONE(le.getUserphone());
		ome.setWX_USER_NAME(le.getUsername());
		was.sendModuleByome(ome);
	}
	/**
	 * 改派发送短信 duxf
	 * @param se
	 * @param opid
	 * @throws Exception
	 */
	public void gpAEOrderProcess(SorderEntity se,long opid) throws Exception{
		Date d = new Date();
		OrderprocessEntity ope = new OrderprocessEntity();
		ope.setOptime(d.getTime());
		
		ope.setOption("改派AE");
		ope.setOrderid(se.getOrderid());
		ope.setOrderstate(se.getOrderstate());
		ope.setOpid(opid);
		ope.setOptcode(1138);
		ope.setEmpid(se.getFwgwempid());
		ops.addOrderprocessEntity(ope);
		//sendmessage(se,Constants.ORDER_YI_ACTION_CODE);
		
		
		OrderMessageEntity ome = new OrderMessageEntity();
		
		ome.setActioncode(1138);
		
		ome.setWX_ORDERID(se.getOrderid());
		
		LoginEntity le = ils.getLoginEntityById(se.getUserid());
		ome.setOpenid(le.getOpenid());
		
		long fwid = se.getFwgwempid();
		if(fwid > 0){
			EmployersEntity ee = ies.getEmployersEntityById(fwid);
			ome.setWX_FW_NAME(ee.getRealname());
			ome.setWX_FW_PHONE(ee.getPhonenumber());
		}
		
		ome.setWX_FANS_PHONE(le.getUserphone());
		ome.setWX_USER_NAME(le.getUsername());
		was.sendModuleByome(ome);
	}
	
	/**
	 * 客服指派AE发送短信 duxf 2015/8/10
	 * @param se
	 * @param opid
	 * @throws Exception
	 */
	public void kfzpAEOrderProcess(SorderEntity se,long opid) throws Exception{
		Date d = new Date();
		OrderprocessEntity ope = new OrderprocessEntity();
		ope.setOrderid(se.getOrderid());
		ope.setOrderstate(se.getOrderstate());
		ope.setOpid(opid);
		ope.setOptime(d.getTime());
		ope.setOption("客服指派AE");
		ope.setOptcode(1142);
		ope.setEmpid(se.getFwgwempid());
		ops.addOrderprocessEntity(ope);
		OrderMessageEntity ome = new OrderMessageEntity();
		ome.setActioncode(8801);
		ome.setWX_ORDERID(se.getOrderid());
		LoginEntity le = ils.getLoginEntityById(se.getUserid());
		ome.setOpenid(le.getOpenid());
		
		long fwid = se.getFwgwempid();
		if(fwid > 0){
			EmployersEntity ee = ies.getEmployersEntityById(fwid);
			ome.setWX_FW_NAME(ee.getRealname());
			ome.setWX_FW_PHONE(ee.getPhonenumber());
			ome.setEmopenid(ee.getOpenid());
		}
		ome.setWX_SERVER_STR(getServicestrByorder(se.getOrderid()));
		ome.setWX_FANS_PHONE(le.getUserphone());
		ome.setWX_USER_NAME(le.getUsername());
		was.sendModuleByome(ome);
	}
	
	
	public void zpOrderProcess(SorderEntity se,long opid)throws Exception{
		Date d = new Date();
		
		OrderprocessEntity ope = new OrderprocessEntity();
		ope.setOptime(d.getTime());
		ope.setOption("指派订单");
		ope.setEmpid(se.getEmpid());
		ope.setOptcode(1102);
		ope.setOrderid(se.getOrderid());
		ope.setOrderstate(se.getOrderstate());
		ope.setOpid(opid);
		ops.addOrderprocessEntity(ope);
		sendmessage(se,Constants.ORDER_ZP_ACTION_CODE);
	}
	public void backToKFOrderProcess(SorderEntity se,long opid)throws Exception{
		Date d = new Date();
		
		OrderprocessEntity ope = new OrderprocessEntity();
		ope.setOptime(d.getTime());
		ope.setOption("超期返回客服");
		ope.setEmpid(se.getEmpid());
		ope.setOptcode(1141);
		ope.setOrderid(se.getOrderid());
		ope.setOrderstate(se.getOrderstate());
		ope.setOpid(opid);
		ops.addOrderprocessEntity(ope);
	}
	public void twoZpOrderProcess(SorderEntity se,long opid, boolean isSamefwid)throws Exception{
		Date d = new Date();
		
		OrderprocessEntity ope = new OrderprocessEntity();
		ope.setOptime(d.getTime());
		ope.setOption("二次指派订单");
		ope.setEmpid(se.getEmpid());
		ope.setOptcode(1140);
		ope.setOrderid(se.getOrderid());
		ope.setOrderstate(se.getOrderstate());
		ope.setOpid(opid);
		ops.addOrderprocessEntity(ope);
		if(!isSamefwid)
		sendmessage(se,Constants.ORDER_TWOZP_ACTION_CODE);
	}
}
