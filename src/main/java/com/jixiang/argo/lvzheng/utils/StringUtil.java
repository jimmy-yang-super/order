package com.jixiang.argo.lvzheng.utils;

import java.util.List;

/**
 * 字符串操作Util
 * @author wuyin
 *
 */
public class StringUtil {
	
/**
 * 判断非空字符串
 * @param str
 * @return
 */
	public static boolean isEmpty(String str){
		boolean flag= false;
		if(null != str && !"".equals(str.trim())){
			flag = true;
		}
		return flag;
	}
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	/**
	 * 判断集合
	 * @param lis
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isListNull(List lis){
		boolean  flag= false;
		if(null != lis && lis.size() >0){
			flag = true;
		}
		return flag;
	}
}
