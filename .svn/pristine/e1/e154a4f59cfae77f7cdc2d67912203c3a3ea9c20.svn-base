package com.jixiang.argo.lvzheng.buz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.vo.EmploysVo;
import com.jixiang.union.user.tools.MemcachedUtil;
import com.jx.service.newcore.contract.IEmployersService;
import com.jx.service.newcore.contract.ISorderService;
import com.jx.service.newcore.entity.EmployersEntity;
import com.jx.service.newcore.entity.SorderEntity;



public class EmployBuz {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private IEmployersService es = RSBLL.getstance().getEmployersService();
	private ISorderService iss = RSBLL.getstance().getSorderService();
	private static EmployBuz eb = null;
	public static EmployBuz getstance(){
		if(eb == null)
			eb = new EmployBuz();
		return eb;
	}
	public EmploysVo getEmpvoByid(long empid)throws Exception{
		EmployersEntity ee =  es.getEmployersEntityById(empid);
		EmploysVo vo = tranceEmpEntityTovo(ee);
		return vo;
	}
	public String skOrder(long orderid,long opid,float fee)throws Exception{
		JSONObject jo = new JSONObject();
		SorderEntity se = iss.getSorderEntityByid(opid);
		if(se != null){
			float bookfee = se.getPrepaidamount() + fee;
			float totalfee = se.getTotalmoney();
			float debtfee  = totalfee - bookfee;
			se.setPrepaidamount(bookfee);
			iss.updateSorderEntity(se);
			
			if(debtfee > 0){
				
			}
		}
		return jo.toString();
	}
	public List<EmploysVo> fwvoList()throws Exception{
		String cacheKey = "employBuz.fwvoList:12345";
		Object cacheObj = MemcachedUtil.get(cacheKey);
		//MemcachedUtil.delete(cacheKey);
		if(cacheObj != null){
			String condition = "emptype = 2 ";
			List<EmployersEntity> enlist = es.getEmployersEntity(condition, 1, 99, "realname");
			JSONArray arrObj = JSONArray.fromObject(cacheObj.toString());
			List<EmploysVo> fwList = JSONArray.toList(arrObj, EmploysVo.class);
			for(EmploysVo empVo:fwList){
				for(EmployersEntity e : enlist){
					if(empVo.getEmpid() == e.getEmpid()){
						empVo.setJdstate(e.getJiedanstatus());
						break;
					}
				}
			}
			return fwList;
		}
		List<EmploysVo> vlist = new ArrayList<EmploysVo>();
		String condition = "emptype = 2 ";
		List<EmployersEntity> enlist = es.getEmployersEntity(condition, 1, 99, "realname");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String nowday = sdf.format(new Date());
		for(EmployersEntity ee : enlist){
			EmploysVo vv = tranceEmpEntityTovo(ee);
			//服务中订单
			int servicecount = iss.getSorderEntityCount("empid="+vv.getEmpid()+" AND orderstate=3");
			vv.setServiceCount(servicecount);
			//已签和已派订单
			String c = "empid="+vv.getEmpid()+" AND postime>"+calendar.getTimeInMillis();
			int orderSize = iss.getSorderEntityCount(c);
			
			c = "empid="+vv.getEmpid()+" AND signstate=1 AND postime>"+calendar.getTimeInMillis();
			
			if(orderSize != 0){
				int signCount = iss.getSorderEntityCount(c);
				vv.setSigningRate(signCount*100/orderSize);
			}
			c = "empid="+vv.getEmpid()+" AND postime>"+sdf.parse(nowday).getTime();
			int paiDanTodayCount = iss.getSorderEntityCount(c);
			vv.setPaiDanTodayCount(paiDanTodayCount);
			vlist.add(vv);
		}
		Date expireDate = new Date();
		expireDate = DateUtils.addSeconds(expireDate, 40);
		String cacheString = JSONArray.fromObject(vlist).toString();
		MemcachedUtil.set(cacheKey,cacheString,expireDate);
		return vlist;
	}
	private EmploysVo tranceEmpEntityTovo(EmployersEntity ee) {
		// TODO Auto-generated method stub
		EmploysVo vo = new EmploysVo();
		if(ee != null){
			vo.setActiontimes(ee.getActiontime());
			vo.setAddip(ee.getRegip());
			vo.setCaree(KVMap.careemap.get(ee.getEmptype())+"");
			vo.setAddress(ee.getAddress());
			vo.setAddtime(UtilsHelper.formatDateTostring("yyyy/MM/dd", ee.getCreatetime()));
			vo.setAge(new Date().getYear()+1900-ee.getEmpbornyear());
			vo.setBornyear(ee.getEmpbornyear());
			vo.setCareeid(ee.getEmptype());
			vo.setCityid(Integer.parseInt(ee.getServicecity()+""));
			vo.setServiceids(ee.getServicelocal());
			vo.setEmail(ee.getEmail());
			vo.setActiontimes(ee.getActiontime());
			vo.setEmpid(ee.getEmpid());
			vo.setEmpname(ee.getEmpname());
			vo.setGender(ee.getGender());
			vo.setPhonenumber(ee.getPhonenumber());
			vo.setRealname(ee.getRealname());
			vo.setOpenid(ee.getOpenid());
			vo.setJdstate(ee.getJiedanstatus());
			vo.setOrderslimit(ee.getOrderslimit());
			vo.setIsgoodat(ee.getIsgoodat());
			if(StringUtils.isNotBlank(vo.getIsgoodat())){
				String isgoodAtStr = vo.getIsgoodat().substring(0,vo.getIsgoodat().length()>7?7:vo.getIsgoodat().length());
				String[] isgoodAtArr = isgoodAtStr.split("\\|");
				vo.setIsgoodatList(Arrays.asList(isgoodAtArr));
			}
		}
		return vo;
	}
	//职位(1，客服。2，法律顾问 3，劳务，4，管理员,5,财物，6，运营，7，法律助理）
	public List<EmploysVo> fzvoList() throws Exception{
		// TODO Auto-generated method stub
		List<EmploysVo> vlist = new ArrayList<EmploysVo>();
		String condition = "emptype = "+Constants.EMP_TYPE_ROLER;
		List<EmployersEntity> enlist = new ArrayList<EmployersEntity>();
		for(int i=0;i<9;i++){
			List<EmployersEntity> list	= es.getEmployersEntity(condition, i+1, 10, "empid");
			if(list!=null&&list.size()>0){
				enlist.addAll(list);
			}else{
				break;
			}
		}
	
		for(EmployersEntity ee : enlist){
			EmploysVo vv = tranceEmpEntityTovo(ee);
			vlist.add(vv);
		}
		return vlist;
	}
	public List<EmploysVo> fuwulist() throws Exception{
		// TODO Auto-generated method stub
		List<EmploysVo> vlist = new ArrayList<EmploysVo>();
		String condition = "emptype = "+Constants.EMP_TYPE_FUWU_TYPE;
		List<EmployersEntity> enlist = es.getEmployersEntity(condition, 1, 99, "empid");
		for(EmployersEntity ee : enlist){
			EmploysVo vv = tranceEmpEntityTovo(ee);
			vlist.add(vv);
		}
		return vlist;
	}
}
