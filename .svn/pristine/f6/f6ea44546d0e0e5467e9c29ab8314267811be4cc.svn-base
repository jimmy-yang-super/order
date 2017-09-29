package com.jixiang.argo.lvzheng.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.utils.Timers;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.vo.CustomerVO;
import com.jixiang.argo.lvzheng.vo.UserCouponsVO;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.CouponsEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.UserCouponsEntity;

/***
 * 优惠券操作类
 * @author lvzheng-duxf
 */
public class CouponsService {
	private static CouponsService couponservice = null;
	
	public static CouponsService getInstance(){
		if(null == couponservice){
			couponservice = new CouponsService();
		}
		return couponservice;
	}
	
	
	/****
	 * 得到用户的优惠券
	 * @param condition 查询条件
	 * @param orderby 排序条件
	 * @return
	 */
	public List<UserCouponsVO> getUserCoupons(String condition,String orderby){
		List<UserCouponsVO> usercouponsvoList = new ArrayList<UserCouponsVO>();
		try {
			List<UserCouponsEntity> usercouponsList = RSBLL.getstance().getUserCouponsService().getUserCoupons(condition, 1, 99, orderby);	
			if(null!=usercouponsList && usercouponsList.size()>0){
				for(UserCouponsEntity ucE : usercouponsList){
					UserCouponsVO userCouponVo = new UserCouponsVO();
					CouponsEntity couponsE = null;
					List<CouponsEntity> cE = RSBLL.getstance().getCouponsService().getCoupons(" couponsid='"+ucE.getCouponsid()+"' ", 1, 1, "");
					if(null != cE && cE.size()>0){
						couponsE = cE.get(0);
					}
					
					//设置优惠券的属性
					if(null != couponsE){
						userCouponVo.setCouponsid(couponsE.getCouponsid());
						userCouponVo.setPrice(couponsE.getMoney());
						userCouponVo.setCoupontypeid(couponsE.getCoupontypeid());
						userCouponVo.setCouponsname(couponsE.getCouponsname());
						userCouponVo.setOvertime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", couponsE.getOvertime()));
						userCouponVo.setUnrange(couponsE.getUnrange());
						userCouponVo.setRange(couponsE.getRange());
						userCouponVo.setContent(couponsE.getContent());
						userCouponVo.setCityid(couponsE.getCityid());
						AreasEntity areaE = RSBLL.getstance().getAreasService().getAeasEntityById(couponsE.getCityid());
						if(null != areaE){
							userCouponVo.setCity(areaE.getName());	
						}
					}
					userCouponVo.setTucid(ucE.getTucid());
					userCouponVo.setUserid(ucE.getUserid());
					userCouponVo.setEcode(String.valueOf(ucE.getEcode()));
					userCouponVo.setGettime(UtilsHelper.formatLongDate("yyyy-MM-dd HH:mm:ss", ucE.getGettime()));
					userCouponVo.setIsuse(ucE.getIsuse() == 0?"未使用":"使用");
					String date1 = UtilsHelper.formatLongDate("yyyy-MM-dd", new Date().getTime());
					String date2 = UtilsHelper.formatLongDate("yyyy-MM-dd", ucE.getDqtime());
					//得到优惠券的到期时间目前为顺延3个月
					userCouponVo.setDqtime(UtilsHelper.formatLongDate("yyyy-MM-dd",ucE.getDqtime()));
					String duetime = "";
					if(Timers.getBetweenDay(date2,date1) <= 7){
						duetime = Timers.getBetweenDay(date1, date2).toString();
					}
					userCouponVo.setDuetime(duetime);  //得到优惠券与现在时间的差
					usercouponsvoList.add(userCouponVo);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usercouponsvoList;
	}
	
	/**
	 * 通过用户优惠券ID得到此优惠券
	 * @param tucid
	 * @return
	 */
	public UserCouponsEntity getUserCouponsById(Long tucid){
		UserCouponsEntity ucE = null;
		try {
			ucE  = RSBLL.getstance().getUserCouponsService().getUserCouponsById(tucid);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ucE;
	}
	
	/***
	 * 修改用户的优惠券
	 * @param ucE
	 */
	public void updateUserCoupons(UserCouponsEntity ucE){
		try {
			RSBLL.getstance().getUserCouponsService().updateUserCoupons(ucE);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/****
	 * 判断用户是否存在优惠券
	 * @return
	 */
	public boolean getUserHaveCoupons(CustomerVO uservo){
		boolean flag = false;
		String condition = " userid = '"+uservo.getUserid()+"' ";
		try {
			List<UserCouponsEntity> usercouponsEList = RSBLL.getstance().getUserCouponsService().getUserCoupons(condition, 1, 1, "");	
			if(usercouponsEList.size()>0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * 给用户添加一个固定的优惠券[此处添加的为198的优惠券0001]
	 * @param userE
	 */
	public void addUserCoupons(CustomerVO user){
		if(null != user){
			UserCouponsEntity usercoupon = new UserCouponsEntity();
			usercoupon.setUserid(user.getUserid());   								//用户id
			usercoupon.setCouponsid(100001);   			    						//优惠券ID
			usercoupon.setEcode(Integer.parseInt(getCode()));   					//兑换码[目前为随机获得6位的兑换码]
			usercoupon.setGettime(new Date().getTime());   							//得到优惠券的时间
			usercoupon.setDqtime(Timers.getAddaferDate(new Date(new Date().getTime()), "month", 3).getTime()); //得到优惠券到期时间(顺延3个月时间)
			try {
				RSBLL.getstance().getUserCouponsService().addUserCoupons(usercoupon);	
				Minus_CouponsCount(100001);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * 给用户添加一个固定的优惠券[此处添加的为100的优惠券0002]
	 * @param userE
	 */
	public void addUserCoupons2(CustomerVO user){
		if(null != user){
			UserCouponsEntity usercoupon = new UserCouponsEntity();
			usercoupon.setUserid(user.getUserid());   								//用户id
			usercoupon.setCouponsid(100002);   			    						//优惠券ID
			usercoupon.setEcode(Integer.parseInt(getCode()));   					//兑换码[目前为随机获得6位的兑换码]
			usercoupon.setGettime(new Date().getTime());   							//得到优惠券的时间
			try {
				RSBLL.getstance().getUserCouponsService().addUserCoupons(usercoupon);	
				Minus_CouponsCount(100002);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	/***
	 * 得到活动的时间
	 * @return
	 */
	public CouponsEntity getCouponEntity(){
		CouponsEntity coupons = new CouponsEntity();
		try {
			String condtion = " coupontypeid='0001' ";
			List<CouponsEntity> list =  RSBLL.getstance().getCouponsService().getCoupons(condtion, 1, 1, "");
			if(list.size()>0){
				coupons = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return coupons;
	}
	
	
	/***
	 * 添加用户和用户的优惠券
	 * @param uservo
	 */
	public void addUserAndUserCoupons(CustomerVO uservo){
		LoginEntity userE = new LoginEntity();
		if(null != uservo){
			userE.setOpenid(uservo.getOpenid());
		}
		userE.setUserphone(uservo.getUserphone());
		userE.setAddtime(new Date().getTime());   //用户注册时间
		userE.setChannel(1);                      //默认为微信
		try {
			long userid = RSBLL.getstance().getLoginService().addLoginEntity(userE);
			if(userid != 0){
				UserCouponsEntity usercoupon = new UserCouponsEntity();
				usercoupon.setUserid(userid);   								        //用户id
				usercoupon.setCouponsid(100001);   			    						//优惠券ID
				usercoupon.setEcode(Integer.parseInt(getCode()));   					//兑换码[目前为随机获得6位的兑换码]
				usercoupon.setGettime(new Date().getTime());   							//得到优惠券的时间
				RSBLL.getstance().getUserCouponsService().addUserCoupons(usercoupon);	
				Minus_CouponsCount(100001);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//修改优惠券的数量 每发一次减去一
	public static void Minus_CouponsCount(long couponsid){
		if(couponsid != 0){
			try {
				CouponsEntity couponsE = RSBLL.getstance().getCouponsService().getCouponsById(couponsid);
				Integer M_count = couponsE.getCount();
				if(M_count > 0){
					M_count =  M_count - 1;
				}else{
					M_count = 0;
				}
				couponsE.setCount(M_count);
				RSBLL.getstance().getCouponsService().updateCoupons(couponsE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 随机生成兑换码
	 * @return
	 */
	public static String getCode(){
    	String code ="";
    	for(int i=0 ; i<6; i++){
        	double random = Math.random()*9;
    		code += (int)random;
    	}
    	return code;
    }
	
	/****
	 * 判断用户是否已分享优惠券
	 * @return
	 */
	public boolean getUserShareCoupons(CustomerVO uservo){
		boolean flag = false;
		String condition = " userid = '"+uservo.getUserid()+"' and couponsid='100002' ";
		try {
			List<UserCouponsEntity> usercouponsEList = RSBLL.getstance().getUserCouponsService().getUserCoupons(condition, 1, 1, "");	
			if(usercouponsEList.size()>0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public static void main(String[] args) {
		System.out.println(CouponsService.getInstance().getCode());
	}
}
