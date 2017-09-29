package com.jixiang.argo.lvzheng.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.jixiang.argo.lvzheng.vo.OrderInfoVO;

/**
时间工具类
@since 2014-05-23
@author zhangyang
*
*/
public class Timers {
	  private static final long ONE_MINUTE = 60000L;  
      private static final long ONE_HOUR = 3600000L;  
      private static final long ONE_DAY = 86400000L;  
      private static final long ONE_WEEK = 604800000L;  
      
      private static final String ONE_SECOND_AGO = "秒前";  
      private static final String ONE_MINUTE_AGO = "分钟前";  
      private static final String ONE_HOUR_AGO = "小时前";  
      private static final String ONE_DAY_AGO = "天前";  
      private static final String ONE_MONTH_AGO = "月前";  
      private static final String ONE_YEAR_AGO = "年前";  
	 /**@author bruce
	  * 取得当前时间 格式yyyy-MM-dd hh:mm:ss
	  */
		public static String nowTime() {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(new Date().getTime());
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return dateFormat.format(c.getTime());
		}
		
		/**@author bruce
		  * 取得当前日期
		  */
		public static String nowdate() {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(new Date().getTime());
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			return dateFormat.format(c.getTime());
		}
		
		/**@author bruce
		  * 取得N个月以后的日期
		  */
		public static String N_MonthDate(int n ) {
			Calendar c = Calendar.getInstance();
			c.add(c.MONTH, n);
			return ""+c.get(c.YEAR)+"-"+(c.get(c.MONTH)+1)+"-"+c.get(c.DATE);
		}
		
		/**@author bruce
		 * @function 取得标准时间 2014-05-23从格式yyyy-MM-dd hh:mm:ss
		 */
		
		public static String getHalfDate(String timeStr) {
			String t= timeStr;
			if(timeStr.contains("-")){
				 t = timeStr.substring(0, 10);
			}
			return t;
		}
		
		/**@author bruce
		 * @function 取得标准时间 2014从格式yyyy-MM-dd hh:mm:ss
		 */
		
		public static String getYear(String timeStr) {
			String t= timeStr;
			if(timeStr.contains("-")){
				 t = timeStr.substring(0, 4);
			}
			return t;
		}
		
//		public static void main(String[] args) {
//			System.out.println(N_MonthDate(5));
//		}
		
		//格式化时间串成为  几天前 几秒前 几小时前  几分钟前 几年前sth.....
		public static String formatToNear(String str){
			
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");  
			        Date date;
					date = format.parse(str);
					str = Timers.format(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			return str;
			
		}
		
		//格式化时间串成为  几天前 几秒前 几小时前  几分钟前 几年前sth.....
		public static String format(Date date) {  
	        long delta = new Date().getTime() - date.getTime();  
	        if (delta < 1L * ONE_MINUTE) {  
	            long seconds = toSeconds(delta);  
	            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;  
	        }  
	        if (delta < 45L * ONE_MINUTE) {  
	            long minutes = toMinutes(delta);  
	            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;  
	        }  
	        if (delta < 24L * ONE_HOUR) {  
	            long hours = toHours(delta);  
	            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;  
	        }  
	        if (delta < 48L * ONE_HOUR) {  
	            return "昨天";  
	        }  
	        if (delta < 30L * ONE_DAY) {  
	            long days = toDays(delta);  
	            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;  
	        }  
	        if (delta < 12L * 4L * ONE_WEEK) {  
	            long months = toMonths(delta);  
	            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;  
	        } else {  
	            long years = toYears(delta);  
	            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;  
	        }  
	    }  
	  
	    private static long toSeconds(long date) {  
	        return date / 1000L;  
	    }  
	  
	    private static long toMinutes(long date) {  
	        return toSeconds(date) / 60L;  
	    }  
	  
	    private static long toHours(long date) {  
	        return toMinutes(date) / 60L;  
	    }  
	  
	    private static long toDays(long date) {  
	        return toHours(date) / 24L;  
	    }  
	  
	    private static long toMonths(long date) {  
	        return toDays(date) / 30L;  
	    }  
	  
	    private static long toYears(long date) {  
	        return toMonths(date) / 365L;  
	    }  
	    /***
	     * /判断两个时间的天数差
	     * @param date1  yyyy-mm-dd格式的字符串
	     * @param date2  yyyy-mm-dd格式的字符串
	     * @return
	     */
	    public static Long getBetweenDay(String date1, String date2) {  
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = null;
	        Date d2 = null;
	        long diffDays = 0l;
	        try {
	            d1 = format.parse(date1);
	            d2 = format.parse(date2);	
		    	//毫秒ms
//	            long diffSeconds = diff / 1000 % 60;
//	            long diffMinutes = diff / (60 * 1000) % 60;
//	            long diffHours = diff / (60 * 60 * 1000) % 24;
	            long diff = d1.getTime() - d2.getTime();
	            diffDays = diff / (24 * 60 * 60 * 1000);
	           
			} catch (Exception e) {
				e.printStackTrace();
			}
	        return diffDays;
	    } 
	    
	    /***
	     * 返回相加后的日期
	     * @param date 目标日期
	     * @param type  加天或月或年
	     * @param m 数量
	     * @return
	     */
	    public static Date getAddaferDate(Date date,String type,int m){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			if(type.equals("year")){
				c.add(Calendar.YEAR, m);
			}else if(type.equals("month")){
				c.add(Calendar.MONTH, m);
			}
	    	return c.getTime();
	    }
		

	    
	    /**
		 * @author bruce
		 * @function 计算t1-t2
		 * @throws ParseException
		 */

		public static long jian(String t1, String t2) throws ParseException {//
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date now = df.parse(t1);
			java.util.Date over = df.parse(t2);
			long l = now.getTime() - over.getTime();
			
			return l;
		}
	
	/**
	 * 把String类型转换为long类型
	 * @param data
	 * @param pattern [yyyy-MM-dd HH:mm:ss/yyyy-MM-dd]
	 * @return
	 */
	public static Long getLongTimeForString(String pattern,String data){
		Long time = 0l;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			time = sdf.parse(data).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}
	 /**@author bruce
	  * 鍙栧緱long杞寲鎴愭牸寮弝yyy-MM-dd hh:mm:ss
	  */
	public static String longToStr(long date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(c.getTime());
	}
	
	@SuppressWarnings("unchecked")
	public static List<OrderInfoVO> sortByTimeDesc(List<OrderInfoVO> list){
		
		System.out.println("鎺掑簭鍓峫ist"+list.get(0).getServiceEndTime());
		
        Collections.sort(list, new Comparator<OrderInfoVO>() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH锛歮m锛歴s");

		private Date getTime(OrderInfoVO m) {
			if (m == null || m.getServiceEndTime() == null)
				return null;
			try {
				return sdf.parse(m.getServiceEndTime().substring(11));
			} catch (ParseException e) {
				return null;
			}
		}
        
        
        public int compare(OrderInfoVO o1, OrderInfoVO o2) {
             Date d1 = getTime(o1);
             Date d2 = getTime(o2);
             if (d1 == null && d2 == null)
             return 0;
             if (d1 == null)
             return -1;
             if (d2 == null)
             return 1;
             return d2.compareTo(d1);
        }
       });
        
        System.out.println("鎺掑簭鍚巐ist"+list.get(0).getServiceEndTime());
        return list;
	}
	
	/**
	 * 
	 * @param str [yyyy-MM-dd HH:mm:ss]
	 * @param date [long 类型时间]
	 * @return
	 */
	public static String formatLongDate(String str, long date){
		Date d = new Date(date);
		SimpleDateFormat sd = new SimpleDateFormat(str); 
		return sd.format(d);
	}
		
	
	public static void main(String[] args) {
		System.out.println(Timers.getLongTimeForString("yyyy-MM-dd HH:mm:ss", "2015-06-29 00:00:00"));
		System.out.println(Timers.getLongTimeForString("yyyy-MM-dd", "2015-06-29"));
	}


}
