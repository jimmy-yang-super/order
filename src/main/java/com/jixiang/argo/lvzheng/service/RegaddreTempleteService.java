package com.jixiang.argo.lvzheng.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.util.Hash;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.ReginAddressEntity;
import com.jx.service.newcore.entity.ReginAddressTempleteEntity;

/**
 * 代理地址模板业务类
 * @author wuyin
 *
 */
public class RegaddreTempleteService {

	private static RegaddreTempleteService instance = null;

	public static RegaddreTempleteService getInstance() {
		if (instance == null) {
			instance = new RegaddreTempleteService();
		}
		return instance;
	}
	
	public ReginAddressTempleteEntity getReginAddressTempleteEntityBycode(String code){
		ReginAddressTempleteEntity templ = null;
		try {
			List<ReginAddressTempleteEntity> templs = RSBLL.getstance().getReginAddressTempleteService()
					.getReginAddressTempleteEntityListBypage("regadtcode = '"+code+"'", 1, 5, "reginid");
			if(StringUtil.isListNull(templs)){
				templ = templs.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return templ;
	}
	
	public List<ReginAddressTempleteEntity> getReginAddressTempAll(){
		List<ReginAddressTempleteEntity> templs = null;
		try {
			templs = RSBLL.getstance().getReginAddressTempleteService().getReginAddressTempleteEntityListBypage("", 1, 99, "reginid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return templs;
	}
	
	public ReginAddressEntity converReginAddressTempleteEntity(ReginAddressTempleteEntity templ,ReginAddressEntity re){
		if(templ != null){
			 if(re != null){
				 re.setZiydz(templ.getZiydz());
				 re.setSuosjx(templ.getSuosjx());
				 re.setAttribute1(templ.getDihzs());
				 re.setAttribute2(templ.getDizlx());
				 re.setChanqzt(templ.getChanqzt());
				 re.setChanqr(templ.getChanqr());
				 re.setChuzfcard(templ.getChuzfcard());
				 re.setPostcode(templ.getPostcode());
				 re.setZulsymj(templ.getZulsymj());
				 re.setZulsynx(templ.getZulsynx());
				 re.setFangwhqfs(templ.getFangwhqfs());
				 re.setFangwzlyt(templ.getFangwzlyt());
			 }
		}
		return re;
	}
	/**
	 * 判断企业下是否存地址
	 * @param busid
	 * @return
	 */
	public ReginAddressEntity getReginAddressEntityBybusId(long busid){
		ReginAddressEntity rae = null;
		try {
			BusinessLibaryEntity bea = RSBLL.getstance().getBusinessLibaryService().getBusinessLibaryEntityByid(busid);
			if(bea != null){
				long regId = bea.getReginaddressId();
				rae = RSBLL.getstance().getReginAddressService().getReginAddressEntityByid(regId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rae;
	}
	/**
	 * 更新地址
	 * @param rea
	 */
	public void updateReginAddressEntity(ReginAddressEntity rea){
		try {
			RSBLL.getstance().getReginAddressService().updateReginAddressEntity(rea);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
