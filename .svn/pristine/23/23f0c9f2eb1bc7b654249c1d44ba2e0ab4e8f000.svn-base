/**
 * 
 */

package com.jixiang.argo.lvzheng.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jixiang.argo.lvzheng.annotation.impl.CheckCookieImpl;
import com.jx.argo.interceptor.PreInterceptorAnnotation;



/**
 * simple introduction
 *
 * <p>detailed comment</p>
 * @author chuxuebao 2015年11月11日
 * @see
 * @since 1.0
 */
@PreInterceptorAnnotation(CheckCookieImpl.class)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CheckCookie {

}
