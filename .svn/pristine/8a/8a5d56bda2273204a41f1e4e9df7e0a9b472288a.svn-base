package com.jixiang.argo.lvzheng.utils;

import java.util.Date;

import com.jixiang.argo.lvzheng.frame.RSBLL;

public class WorkDayUtil {
	/**
	 * 计算两个时间相隔的工作日
	 * 
	 * @param from
	 * @param until
	 * @return
	 * @throws Exception
	 */
	public static int getWorkDayNumBetween(Date from, Date until) throws Exception {
		if(from == null || until == null){
			return 0;
		}
		/**
		 * 原有逻辑已经都放到workflow的gaea服务里了
		 */
		return RSBLL.getstance().getWorkDayService().getWorkDayNumBetween(from.getTime(), until.getTime());
	}
	/**
	 * 计算两个时间相隔的工作日
	 * 
	 * @param from
	 * @param until
	 * @return
	 * @throws Exception
	 */
	public static int getWorkDayNumBetween(String from, String until) throws Exception {
		if(from == null || until == null){
			return 0;
		}
		/**
		 * 原有逻辑已经都放到workflow的gaea服务里了
		 */
		return RSBLL.getstance().getWorkDayService().getWorkDayNumBetween(from, until);
	}
}
