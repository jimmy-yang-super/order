package com.jixiang.argo.lvzheng.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.utils.GrapPageInfo;
import com.jixiang.argo.lvzheng.utils.GrapPageRegist;
import com.jixiang.argo.lvzheng.utils.RemoteConstant;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.vo.ApproveGshmSearchVo;
import com.jixiang.argo.lvzheng.vo.BusinessCategoryVo;
import com.jixiang.argo.lvzheng.vo.IndustryDetail;
import com.jixiang.argo.lvzheng.vo.IndustrylVo;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.IndustrylEntity;
import com.jx.service.newcore.entity.KehinfosEntity;

/**
 * 业务范围处理类
 * @author wuyin
 *
 */
public class IndustrylEnterService {

private static IndustrylEnterService instance = null;
	
	public static IndustrylEnterService getInstance(){
		if(instance == null){
			instance = new IndustrylEnterService();
		}
		return instance;
	}
	/**
	 * 保存业务对象
	 * @param industry
	 * @return
	 */
	public long saveIndustryEntity(IndustrylEntity industry){
		long induId = 0;
		try {
			induId= RSBLL.getstance().getIndustrylServiceService().addIndustrylEntity(industry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return induId;
	}
	/**
	 * 保存业务主类别
	 * @param cate
	 */
	public void saveIndustryCategory(BusinessCategoryVo bcategory,long nowtime){
		//父类节点
		IndustrylEntity industry = new IndustrylEntity();
		industry.setFdm(bcategory.getFdm());
		industry.setDm(bcategory.getDm());
		industry.setWb(bcategory.getWb());
		
		industry.setAddtime(nowtime);
		industry.setUpdatetime(nowtime);
		industry.setStatus(1);//表示启用状态
		industry.setRownum(1);
		saveIndustryEntity(industry);//保存
	}
	/**
	 * 保存根节点
	 * @param industry
	 * @param nowtime
	 */
	public void saveIndustryEnterprice(IndustryDetail detail,long nowtime){
		IndustrylEntity industry = new IndustrylEntity();
		industry.setFdm(detail.getFdm());
		industry.setDm(detail.getDm());
		industry.setWb(detail.getWb());
		industry.setRownum(detail.getRownum());
		industry.setUniteCode(detail.getUniteCode());
		
		industry.setAddtime(nowtime);
		industry.setUpdatetime(nowtime);
		industry.setStatus(1);//表示启用状态
		
		saveIndustryEntity(industry);//保存
	}
	/**
	 * 设置验证码生成的图片位置
	 * @return
	 */
	public String getValidateCodePostion(){
		 File file = new File(System.getProperty("user.dir"));
 	     String path = file.getAbsolutePath().replace('\\', '/');
 	     path = path+RemoteConstant.GSWZ_VALIDATECODEPATH;
 	     return path;
	}
	/**
	 * 导入新数据前需删除库中原数据
	 */
	public void beforeAddremoteDate(){
		try {
			int count = RSBLL.getstance().getIndustrylServiceService()
					.getIndustrylEntityCount("");//获取库内所有业务数据
			List<IndustrylEntity> industrys = RSBLL.getstance().getIndustrylServiceService()
					.getIndustrylEntityListBypage("", 1, count, "indusid");//status = 1
			long induId = 0;
			for(IndustrylEntity indu : industrys){
				induId = indu.getIndusid();
				RSBLL.getstance().getIndustrylServiceService().deleteIndustrylEntity(induId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 提权远程业务范围数据
	 */
	public String dealRemoteBusiness(String sessionId){
		String backstr= "";
		//调用访问
		GrapPageInfo gpi = new GrapPageInfo();
		//获得主营类别
		String backStr = gpi.getHttpContent(RemoteConstant.GSWZ_INDUSTRYCATEGORY,"",sessionId);
		List<BusinessCategoryVo> businessCategory = gpi.getBusinessCategoryVos(backStr);
		//根据code 获得分类
		if(StringUtil.isListNull(businessCategory)){
			String fdm ="";
			String paosParam ="";
			long nowtime = new Date().getTime();
			
			//清除库里数据
			beforeAddremoteDate();
			
			for(BusinessCategoryVo bcategory : businessCategory){
				fdm = bcategory.getDm();
				//排除分类根节点
				if(StringUtil.isEmpty(fdm)){
						if(!StringUtil.isNum(fdm)){
							System.out.println("父代码："+fdm +" 描述：" + bcategory.getWb());
							//保存根节点
							IndustrylEnterService.getInstance().saveIndustryCategory(bcategory, nowtime);
							continue;
						}
						IndustrylEnterService.getInstance().saveIndustryCategory(bcategory, nowtime);
						paosParam = gpi.appendPostParam(fdm, "50", "1", "0");
						System.out.println("请求："+paosParam);
						backStr = gpi.getHttpContent(RemoteConstant.GSWZ_INDUSTRYDETAIL,paosParam,sessionId);
						
						System.out.println(backStr);
						
						IndustrylVo industry = gpi.getIndustrylVoByjsonstr(backStr);
						
						if(industry != null){
							System.out.println("page:" + industry.getPage()+" Pagerows: "+ industry.getPagerows());
							
							for(IndustryDetail detail : industry.getRows()){
								//保存子节点信息
								IndustrylEnterService.getInstance().saveIndustryEnterprice(detail, nowtime);
								
								System.out.println("dm:"+detail.getDm()+"  UniteCode: "+detail.getUniteCode()+"   wb : "+detail.getWb());
							}
							
						}

				}
			}
			backstr ="true";
		}
		return backstr;
	}
	
	public String submitGszcDate(String sessionId){
		String text = "";
		GrapPageInfo gpi = new GrapPageInfo();
		//获得post 请求参数
		String posParam = gpi.getGszcParamer(sessionId);
		String backStr = gpi.getHttpContent(RemoteConstant.GSWZ_GSMCREGISTSUBMIT,posParam,sessionId);
		System.out.println("返回信息："+backStr);
		text = gpi.dealMingchzJson(backStr);
		return text;
	}
	/**
	 * 检查核准的名称是否符合要求
	 * @param sessionId
	 * @return
	 */
	public String checkGsmingc(String sessionId,BusinessLibaryEntity ble){
		GrapPageInfo gpi = new GrapPageInfo();
		return gpi.gsnameCheck(sessionId,ble);
	}
	/**
	 * 获得用户下公司核名的处理进度
	 * @param sessionId
	 */
	public void getUserTransaction(String sessionId){
		GrapPageInfo gpi = new GrapPageInfo();
		List<ApproveGshmSearchVo> appvos = gpi.getUserGshmTransactList(sessionId);
		if(StringUtil.isListNull(appvos)){
			for(ApproveGshmSearchVo pvo : appvos){
				System.out.println("公司名称："+pvo.getEntName()+"   审核状态："+pvo.getState());
			}
		}
	}
	/**
	 * 保存注册用户
	 * @param sessionId
	 */
	public String saveRegistUser(String sessionId,KehinfosEntity keh){
		//检查用户名是否存在
		GrapPageRegist regist = GrapPageRegist.getInstance();
		String text ="";
		try {
			String isexit = regist.checkRegistName(sessionId, keh.getPhone());
			if("true".equals(isexit)){
				text ="注册的用户名已存在！！";
			}else{
				//进行信息保存
				text = regist.saveRegistuser(sessionId, keh);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
}
