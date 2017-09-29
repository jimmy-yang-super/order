/**
 * 
 */

package com.jixiang.argo.lvzheng.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jx.service.enterprise.entity.LvEnterpriseDicDataEntity;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.union.entity.EmployEntity;
import com.jx.service.workflow.entity.LvDicDataEntity;



/**
 * simple introduction
 *
 * <p>detailed comment</p>
 * @author chuxuebao 2015年8月21日
 * @see
 * @since 1.0
 */

public class DicUtils {
	
	public static String getDicDataValue(String dicTypeKey, String dicDataKey, String defaultValue){
		String dicDataValue = null;
		try {
			dicDataValue = RSBLL.getstance().getDicService().getDicDataValueByDicTypeKeyAndDicDataKey(dicTypeKey, dicDataKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(dicDataValue) && StringUtils.isNotBlank(dicDataKey)){
			// 人员名称
			Pattern pattern = Pattern.compile("[0-9]*");
			if(pattern.matcher(dicDataKey).matches() && StringUtils.length(dicDataKey) < 18){
				EmployEntity employEntity = null;
				try {
					employEntity = RSBLL.getstance().getUserService().getUserInfoByUserId(dicDataKey);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(employEntity != null){
					dicDataValue = employEntity.getRealname();
				}
			}
		}
		if(StringUtils.isBlank(dicDataValue)){
			return defaultValue;
		}
		return dicDataValue;
	}

	public static String getDicDataValue(String dicTypeKey, String dicDataKey){
		return getDicDataValue(dicTypeKey, dicDataKey, "");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> transferDicData(Map map){
		if(map == null || map.isEmpty()){
			return Collections.EMPTY_MAP;
		}
		Map<String, Object> reMap = new HashMap<String, Object>();
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, Object> next = iterator.next();
			String key = next.getKey();
			Object obj = next.getValue();
			if(obj != null && StringUtils.isNotBlank(obj.toString())){
				String value = obj.toString();
				String dicDataValue = DicUtils.getDicDataValue(key, value);
				if(StringUtils.isNotBlank(dicDataValue)){
					reMap.put(key, dicDataValue);
					reMap.put(key + "Key", value);
					continue;
				}
				
				// 人员名称
				Pattern pattern = Pattern.compile("[0-9]*");
				if(StringUtils.isNotBlank(value) && pattern.matcher(value).matches() && StringUtils.length(value) < 18){
					EmployEntity employEntity = null;
					try {
						employEntity = RSBLL.getstance().getUserService().getUserInfoByUserId(value);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(employEntity != null){
						reMap.put(key, employEntity.getRealname());
						reMap.put(key + "Key", value);
						continue;
					}
				}
				
				// 企业库字典表
				String enterpriseDicValue = DicUtils.getEnterpriseDicValue(key, value);
				if(StringUtils.isNotBlank(enterpriseDicValue)){
					reMap.put(key, enterpriseDicValue);
					reMap.put(key + "Key", value);
					continue;
				}
				
				reMap.put(key, value);
			}else{
				map.put(key, "");
			}
		}
		return reMap;
	}
	
	public static List<LvDicDataEntity> getDicDataList(String dicTypeKey){
		List<LvDicDataEntity> lvDicDataList = null;
		try {
			lvDicDataList = RSBLL.getstance().getDicService().getListDicDataByDicTypeKey(dicTypeKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if((lvDicDataList == null || lvDicDataList.isEmpty()) && StringUtils.startsWith(dicTypeKey, "R") ){
			lvDicDataList = new ArrayList<LvDicDataEntity>();
			// 根据角色ID获取人员列表
			String roleId = StringUtils.removeStart(dicTypeKey, "R");
			List<EmployEntity> userEntityList = null;
			try {
				userEntityList = RSBLL.getstance().getUserService().getUserListByRoleId(roleId);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(userEntityList != null && !userEntityList.isEmpty()){
				for(EmployEntity userEntity:userEntityList){
					LvDicDataEntity lvDicDataEntity = new LvDicDataEntity();
					lvDicDataEntity.setDataKey(userEntity.getEmpid() + "");
					lvDicDataEntity.setDataValue(userEntity.getRealname());
					lvDicDataList.add(lvDicDataEntity);
				}
			}
		}
		

		return lvDicDataList;
	}
	
	
	public static List<AreasEntity> getAreaDataList(String parentId){
		String condition = " parentid = " + parentId;
		List<AreasEntity> aeasEntity = new ArrayList<AreasEntity>();
		try {
			aeasEntity = RSBLL.getstance().getAreasService().getAeasEntity(condition, 1, 100, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aeasEntity;
	}
	
	public static String getAreaDataById(String dicDataId){
		if(StringUtils.isBlank(dicDataId)){
			return "";
		}
		AreasEntity aeasEntity = null;
		try {
			aeasEntity = RSBLL.getstance().getAreasService().getAeasEntityById(Long.parseLong(dicDataId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(aeasEntity != null){
			return aeasEntity.getName();
		}
		return "";
	}
	
	public static String getEnterpriseDicValue(String dicTypeKey, String dicDataKey){
		String dicDataValue = null;
		try {
			dicDataValue = RSBLL.getstance().getEnterpriseDicDataService().getDicDataValueByTypeKeyAndDataKey(dicTypeKey, dicDataKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(dicDataValue)){
			return dicDataValue;
		}
		return "";
	}
	
	public static List<LvEnterpriseDicDataEntity> getEnterpriseDicDataList(String dicTypeKey){
		List<LvEnterpriseDicDataEntity> dicDataList = new ArrayList<LvEnterpriseDicDataEntity>();
		try {
			dicDataList = RSBLL.getstance().getEnterpriseDicDataService().getDicDataListByType(dicTypeKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dicDataList;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> getAddressPartner(){
		Map<String, String> addressPartnerDicData = null;
		try {
			addressPartnerDicData = RSBLL.getstance().getEnterpriseAddressTemplateService().getAddressPartnerDicData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(addressPartnerDicData == null){
			return Collections.EMPTY_MAP;
		}
		return addressPartnerDicData;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> getAddressAreaDicData(String addressPartnerCode){
		Map<String, String> addressAreaDicData = null;
		try {
			addressAreaDicData = RSBLL.getstance().getEnterpriseAddressTemplateService().getAddressAreaDicDataByAddressPartnerCode(addressPartnerCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(addressAreaDicData == null){
			return Collections.EMPTY_MAP;
		}
		return addressAreaDicData;
	}
	
	public static String getEnterpriseDicKeyByDicTypeAndDicValue(String dicType, String dataValue){
		LvEnterpriseDicDataEntity dicDataEntity = null;
		try {
			dicDataEntity = RSBLL.getstance().getEnterpriseDicDataService().getDicDataEntityByTypeKeyAndDataValue(dicType, dataValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dicDataEntity != null){
			return dicDataEntity.getDataKey();
		}
		return null;
	}
	
	
}
