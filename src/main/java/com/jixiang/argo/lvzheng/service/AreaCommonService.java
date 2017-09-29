package com.jixiang.argo.lvzheng.service;

import java.util.ArrayList;
import java.util.List;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;

/**
 * 区域操作共同类
 * @author wuyin
 *
 */
public class AreaCommonService {
	private static AreaCommonService instance = null;

	public static AreaCommonService getInstance() {
		if (instance == null) {
			instance = new AreaCommonService();
		}
		return instance;
	}
	/**
	 * 获得一级目录（现有的业务）
	 * @param condition
	 * @return
	 */
	public List<AreasEntity> getAraeEntityComm(String condition){
		List<AreasEntity> lareas = null;
		try {
			lareas = RSBLL.getstance().getAreasService()
					.getAeasEntity(condition, 1, 80, "areaid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lareas;
	}
	/**
	 * 获得业务支持的区域(现有的)
	 * @return
	 */
	public List<ProductPropertiesEntity> getBusinessArea(){
		List<ProductPropertiesEntity> propro = null;
		List<String> pronames = null;
		List<ProductPropertiesEntity> proproes = null;
		try {
			propro = RSBLL.getstance()
					.getProductPropertiesService().
					getProductPropertiesEntitys("type = 'addressprice' and localid is not null", 1, 50, "proid");
			if(propro.size() > 0){
				//排除重复的
				pronames = new ArrayList<String>();
				proproes = new ArrayList<ProductPropertiesEntity>();
				for(ProductPropertiesEntity ppe: propro){
					String prona = ppe.getProname();
					if(!pronames.contains(prona)){
						proproes.add(ppe);
					}
					pronames.add(prona);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proproes;
	}
	/**
	 * 判断选择区域是否匹配类别
	 * @param cateId
	 * @return
	 */
 public boolean isBussinessarea(long cateId){
	 boolean flag = false;
	 List<ProductPropertiesEntity> propro = null;
		try {
			propro = RSBLL.getstance()
					.getProductPropertiesService().
					getProductPropertiesEntitys("type = 'addressprice' and localid is not null and cateid ="+cateId,
							1, 50, "proid");
			if(propro != null && propro.size() >0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	 return flag; 
 }
 
}
