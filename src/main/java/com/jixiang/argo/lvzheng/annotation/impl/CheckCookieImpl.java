/**
 * 
 */

package com.jixiang.argo.lvzheng.annotation.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.utils.ActionResultUtils;
import com.jixiang.union.user.tools.LoginUtil;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.interceptor.PreInterceptor;



/**
 * simple introduction
 *
 * <p>detailed comment</p>
 * @author chuxuebao 2015年11月11日
 * @see
 * @since 1.0
 */

public class CheckCookieImpl implements PreInterceptor{

	@Override
	public ActionResult preExecute(BeatContext beat) {
		// 员工
//		String empId = LoginUtil.getEmpIdFromCookie(beat.getRequest());		
//		if(StringUtils.isNotBlank(empId)){
//			return null;
//		}
//		// 登录失败
//		try {
//			beat.getResponse().sendRedirect("http://test.union.lvzheng.com/emp/login");
//			return ActionResultUtils.renderJson("");
//		} catch (IOException e) {
//			e.printStackTrace();
//			return ActionResultUtils.renderJson("");
//		}
		return null;
	}

}
