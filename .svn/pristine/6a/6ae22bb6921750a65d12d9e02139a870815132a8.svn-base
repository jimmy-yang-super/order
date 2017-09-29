package com.jixiang.argo.lvzheng.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.utils.BusinessApplyUtils;
import com.jixiang.argo.lvzheng.utils.GrapPageLogin;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.KehinfosEntity;

/**
 * 公司核名（远程提交工商系统）
 * @author wuyin
 *
 */
public class EnterpriseMchzRemoteService {

	private static EnterpriseMchzRemoteService instance = null;
	
	private EnterpriseMchzRemoteService(){}
	
	public EnterpriseMchzRemoteService getInstance(){
		if(instance == null){
			instance = new EnterpriseMchzRemoteService();
		}
		return instance;
	}
	
	private IndustrylEnterService industryEnterService = IndustrylEnterService.getInstance();
	/**
	 * 远程进行公司名称核准
	 * @param busId
	 * @return
	 */
	public String remoteEnterpriseMchz(String busId){
		String backInfo ="";
		
		if(StringUtil.isEmpty(busId)){
			long lbusId = Long.valueOf(busId);
			//获得企业库信息
			BusinessLibaryEntity ble = getBusinessLibaryEntityById(lbusId);
			if(ble != null){
				//判断企业用户是否已有 ：工商系统 登录用户
				String gongsxtcode = ble.getGongsxtcode();//4-16个英文数字
				String password = ble.getPassword();
                //获得工商网站JSESSIONID
				String sessionId ="";
				try {
					sessionId = BusinessApplyUtils.getSessionId();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(sessionId == ""){
					 return "因访问频繁，服务器重置了访问地址，请过一段时间再次访问，谢谢合作！！！";
				}
				
				if(StringUtil.isEmpty(gongsxtcode)){
					//判断登录用户名是否合法
					if(!checkGsxtCode(gongsxtcode)){
						return "工商系统账户格式有误，请输入4-16个英文或数字！";
					}
				}else{
					//无工商系统登录用户
					//（使用法人手机号作为登录账户；密码：xiaowei（默认））  
					KehinfosEntity keh = getKehinfosEntity2Busid2role(lbusId,2);//2 法定代表人
					gongsxtcode = keh.getPhone();
					password ="xiaowei";
					//注册用户
					backInfo = industryEnterService.saveRegistUser(sessionId, keh);
					if(StringUtil.isEmpty(backInfo)){
						return backInfo;
					}
					//更新（企业库中的 工商系统账户和密码）
					ble.setGongsxtcode(gongsxtcode);
					ble.setPassword(password);
					updateBusinessLibaryEntity(ble);
				}
				//登录工商网站
				GrapPageLogin gra = new GrapPageLogin();
				InputStream verifyCodeInput = null;
				try {
					//获取验证码
					verifyCodeInput = gra.getVerifyCode(sessionId);
					File file = new File("D://test.jpg");
					FileOutputStream fileOutputStream = new FileOutputStream(file);
					IOUtils.copy(verifyCodeInput, fileOutputStream);
					fileOutputStream.close();
					Scanner cin = new Scanner(System.in);
					System.out.println("请输入验证码：");
					String verifyCode = cin.nextLine();
					backInfo = gra.postLogin(sessionId,gongsxtcode,password, verifyCode);
					if(StringUtil.isEmpty(backInfo)){
						return backInfo;
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				//公司名称检查
				backInfo = industryEnterService.checkGsmingc(sessionId,ble);
				if(StringUtil.isEmpty(backInfo)){
					return backInfo;
				}
			}
		}
		
		return backInfo;
	}
	/**
	 * 更新企业库信息
	 * @param ble
	 */
	public void updateBusinessLibaryEntity(BusinessLibaryEntity ble){
		try {
			RSBLL.getstance().getBusinessLibaryService().updateBusinessLibaryEntity(ble);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 通过角色获得用户信息
	 * @param busid
	 * @param role
	 * @return
	 */
	public KehinfosEntity getKehinfosEntity2Busid2role(long busid,int role){
		KehinfosEntity kehinfo = null;
		try {
			List<KehinfosEntity> lkes = RSBLL.getstance().getKehinfosService()
					.getKehinfosEntityListBypage("busidl ="+busid +" and roletype ="+role, 1, 1, "kehid");
			if(StringUtil.isListNull(lkes)){
				kehinfo = lkes.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return kehinfo;
	}
	/**
	 * 获得企业库对象
	 * @param busid
	 * @return
	 */
	private static BusinessLibaryEntity getBusinessLibaryEntityById(long busid){
		BusinessLibaryEntity ble = null;
		try {
			ble = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(busid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ble;
	}
	/**
	 * 检查登录工商系统登录用户格式(4-16个英文数字)
	 * @param gsxtcode
	 * @return
	 */
	public boolean checkGsxtCode(String gsxtcode) {
		String s = "\\d+.\\d+|\\w+";
		Pattern pattern = Pattern.compile(s);
		Matcher ma = pattern.matcher(gsxtcode);
		boolean mt = ma.matches();
		if(mt){
			int length = gsxtcode.length();
			if(length <4 || length > 16){
				mt = false;
			}
		}
		return mt;
	}
}
