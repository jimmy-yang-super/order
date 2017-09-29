/**
 * 
 */

package com.jixiang.argo.lvzheng.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.union.user.tools.LoginUtil;
import com.jx.service.enterprise.contract.ILvEnterpriseAddressService;
import com.jx.service.enterprise.contract.ILvEnterpriseRoleRelationService;
import com.jx.service.enterprise.contract.ILvEnterpriseService;
import com.jx.service.enterprise.entity.LvEnterpriseAddressEntity;
import com.jx.service.enterprise.entity.LvEnterpriseAddressTemplateEntity;
import com.jx.service.enterprise.entity.LvEnterprisePersonEntity;
import com.jx.service.enterprise.entity.LvEnterpriseRoleDataEntity;
import com.jx.service.enterprise.entity.LvEnterpriseRoleRelationEntity;



/**
 * simple introduction
 *
 * <p>detailed comment</p>
 * @author chuxuebao 2015年10月28日
 * @see
 * @since 1.0
 */

public class EnterpriseUtils {

	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getRoleListByEnterpriseAndRoleType(String enterpriseId, String roleType){
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		List<LvEnterpriseRoleRelationEntity> roleRelationList = new ArrayList<LvEnterpriseRoleRelationEntity>();
		try {
			roleRelationList = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleRelationListByEnterpriseIdAndRoleType(Long.parseLong(enterpriseId), roleType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(roleRelationList == null || roleRelationList.isEmpty()){
			return reList;
		}
		for(LvEnterpriseRoleRelationEntity enterpriseRoleRelationEntity:roleRelationList){
			Map<String, String> roleDataMap = new HashMap<String, String>();
			if(StringUtils.equalsIgnoreCase(enterpriseRoleRelationEntity.getRoleType(), ILvEnterpriseRoleRelationService.ROLETYPE_LEGALPARTNER)){
				// 获取企业信息
				try {
					roleDataMap = RSBLL.getstance().getEnterpriseService().getAllValueByEnterpriseId(enterpriseRoleRelationEntity.getRoleId() + "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				// 获取人员信息
				LvEnterprisePersonEntity enterprisePersonEntity = null;
				try {
					enterprisePersonEntity = RSBLL.getstance().getEnterprisePersonService().getEnterprisePersonById(enterpriseRoleRelationEntity.getRoleId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(enterprisePersonEntity != null){
					roleDataMap = JSONObject.parseObject(JSONObject.toJSONString(enterprisePersonEntity), Map.class);
				}
			}
			List<LvEnterpriseRoleDataEntity> roleDataList = null;
			try {
				roleDataList = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleDataListByRoleRelationId(enterpriseRoleRelationEntity.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(roleDataList != null && !roleDataList.isEmpty()){
				for(LvEnterpriseRoleDataEntity enterpriseRoleDataEntity:roleDataList){
					roleDataMap.put(enterpriseRoleDataEntity.getDataKey(), enterpriseRoleDataEntity.getDataValue());
				}
			}
			roleDataMap.put("roleRelationId", enterpriseRoleRelationEntity.getId() + "");
			reList.add(roleDataMap);
		}
		return reList;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, Object>> getRoleNameStrByByEnterprise(String enterpriseId){
		Map<String, Map<String, Object>> reMap = new HashMap<String, Map<String, Object>>();
		List<LvEnterpriseRoleRelationEntity> roleRelationList = new ArrayList<LvEnterpriseRoleRelationEntity>();
		try {
			roleRelationList = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleRelationListByEnterpriseId(Long.parseLong(enterpriseId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(roleRelationList == null || roleRelationList.isEmpty()){
			return reMap;
		}
		
		for(LvEnterpriseRoleRelationEntity enterpriseRoleRelationEntity:roleRelationList){
			String roleType = enterpriseRoleRelationEntity.getRoleType();
			if(!StringUtils.equalsIgnoreCase(roleType, ILvEnterpriseRoleRelationService.ROLETYPE_LEGALPARTNER)){
				Map<String, Object> roleTypeData = reMap.get(roleType);
				if(roleTypeData == null || roleTypeData.isEmpty()){
					roleTypeData = new HashMap<String, Object>();
					Set<String> roleNameSet = new HashSet<String>();
					Set<Long> roleIdSet = new HashSet<Long>();
					roleTypeData.put("roleNameSet", roleNameSet);
					roleTypeData.put("roleIdSet", roleIdSet);
					reMap.put(roleType, roleTypeData);
				}
				// 获取人员信息
				LvEnterprisePersonEntity enterprisePersonEntity = null;
				try {
					enterprisePersonEntity = RSBLL.getstance().getEnterprisePersonService().getEnterprisePersonById(enterpriseRoleRelationEntity.getRoleId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(enterprisePersonEntity != null){
					// 名称
					((Set<String>)roleTypeData.get("roleNameSet")).add(enterprisePersonEntity.getName());
					// ID
					((Set<Long>)roleTypeData.get("roleIdSet")).add(enterprisePersonEntity.getId());
					try {
						roleTypeData.putAll(BeanUtils.describe(enterprisePersonEntity));
					} catch (Exception e) {

						e.printStackTrace();
					} 
				}
			}
		}
		
		if(reMap != null && !reMap.isEmpty()){
			Set<String> keySet = reMap.keySet();
			for(String roleType:keySet){
				Map<String, Object> map = reMap.get(roleType);
				if(map == null || map.isEmpty()){
					continue;
				}
				Object roleNameSet = map.get("roleNameSet");
				if(roleNameSet != null){
					map.put("roleNameList", StringUtils.join((Set<String>)roleNameSet, "；"));
				}
				Object roleIdSet = map.get("roleIdSet");
				if(roleIdSet != null){
					map.put("roleIdList", StringUtils.join((Set<String>)roleIdSet, ";"));
				}
			}
			
			
		}
		return reMap;
	}
	/**
	 * 针对一种角色只有一个人
	 * @param enterpriseId
	 * @param roleType
	 * @return
	 */
	public static Map<String, String> getRoleDataByEnterpriseIdAndRoleType(String enterpriseId, String roleType){
		Map<String, String> reMap = new HashMap<String, String>();
		if(StringUtils.isBlank(roleType) || StringUtils.isBlank(enterpriseId)){
			return reMap;
		}
		List<LvEnterpriseRoleDataEntity> roleDataList = null;
		try {
			roleDataList = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleDataListByEnterpriseIdAndRoleType(Long.parseLong(enterpriseId), roleType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(roleDataList != null && !roleDataList.isEmpty()){
			for(LvEnterpriseRoleDataEntity enterpriseRoleDataEntity:roleDataList){
				reMap.put(enterpriseRoleDataEntity.getDataKey(), enterpriseRoleDataEntity.getDataValue());
			}
		}
		return reMap;
	}
	/**
	 * 针对一种角色有多个人
	 * @param enterpriseId
	 * @param roleType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getRoleInfoListByEnterpriseIdAndRoleType(String enterpriseId, String roleType){
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		if(StringUtils.isBlank(roleType) || StringUtils.isBlank(enterpriseId)){
			return reList;
		}
		// 角色主要信息
		List<LvEnterpriseRoleRelationEntity> roleRelationList = new ArrayList<LvEnterpriseRoleRelationEntity>();
		try {
			roleRelationList = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleRelationListByEnterpriseIdAndRoleType(Long.parseLong(enterpriseId), roleType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(roleRelationList == null || roleRelationList.isEmpty()){
			return reList;
		}
		for(LvEnterpriseRoleRelationEntity enterpriseRoleRelationEntity:roleRelationList){
			Map<String, String> roleDataMap = new HashMap<String, String>();
			
			// 关联信息
			roleDataMap.putAll(JSONObject.parseObject(JSONObject.toJSONString(enterpriseRoleRelationEntity), Map.class));
			roleDataMap.put("roleRelationId", enterpriseRoleRelationEntity.getId() + "");
			
			if(StringUtils.equalsIgnoreCase(enterpriseRoleRelationEntity.getRoleType(), ILvEnterpriseRoleRelationService.ROLETYPE_LEGALPARTNER)){
				// 获取企业信息
				try {
					roleDataMap.putAll(RSBLL.getstance().getEnterpriseService().getAllValueByEnterpriseId(enterpriseRoleRelationEntity.getRoleId() + ""));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				// 获取人员信息
				LvEnterprisePersonEntity enterprisePersonEntity = null;
				try {
					enterprisePersonEntity = RSBLL.getstance().getEnterprisePersonService().getEnterprisePersonById(enterpriseRoleRelationEntity.getRoleId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(enterprisePersonEntity != null){
					roleDataMap.putAll(JSONObject.parseObject(JSONObject.toJSONString(enterprisePersonEntity), Map.class));;
				}
			}
			// 角色扩展信息
			List<LvEnterpriseRoleDataEntity> roleDataList = null;
			try {
				roleDataList = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleDataListByRoleRelationId(enterpriseRoleRelationEntity.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(roleDataList != null && !roleDataList.isEmpty()){
				for(LvEnterpriseRoleDataEntity enterpriseRoleDataEntity:roleDataList){
					roleDataMap.put(enterpriseRoleDataEntity.getDataKey(), enterpriseRoleDataEntity.getDataValue());
				}
			}
			reList.add(roleDataMap);
		}
		return reList;
	}
	
	/**
	 * 判断是否为企业法人
	 * @param enterpriseId 企业ID
	 * @param roleId 人员ID
	 * @return
	 */
	public static boolean isLegalPerson(String enterpriseId, String roleType){
		if(StringUtils.isBlank(enterpriseId) || StringUtils.isBlank(roleType)){
			return false;
		}
		List<LvEnterpriseRoleRelationEntity> roleRelationList = null;
		try {
			roleRelationList = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleRelationListByEnterpriseIdAndRoleType(Long.parseLong(enterpriseId), ILvEnterpriseRoleRelationService.ROLETYPE_LEGALPERSON);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		if(roleRelationList == null || roleRelationList.isEmpty()){
			return false;
		}
		for(LvEnterpriseRoleRelationEntity enterpriseRoleRelationEntity:roleRelationList){
			String roleDataValue = null;
			try {
				roleDataValue = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleDataValueByRoleRelationIdAndDataKey(enterpriseRoleRelationEntity.getId(), "legalPersonMappingRoleType");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(StringUtils.equals(roleType, roleDataValue)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取企业地址信息
	 * @param enterpriseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getAddressDataByEnterpriseId(String enterpriseId){
		Map<String, Object> reMap = new HashMap<String, Object>();
		List<LvEnterpriseAddressEntity> enterpriseAddressList = null;
		try {
			enterpriseAddressList = RSBLL.getstance().getEnterpriseAddressService().getEnterpriseAddressListByEnterpriseId(enterpriseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(enterpriseAddressList == null || enterpriseAddressList.isEmpty()){
			return reMap;
		}
		String addressType = null;
		try {
			addressType = RSBLL.getstance().getEnterpriseService().getValueByEnterpriseIdAndKey(enterpriseId, ILvEnterpriseService.EXT_KEY_ADDRESSTYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.equals(addressType, ILvEnterpriseService.MAIN_KEY_ADDRESSTYPE_ONE)){
			// 孵化器地址
			String addressArea = null;
			try {
				addressArea = RSBLL.getstance().getEnterpriseAddressService().getEnterpriseAddressDataMapByEnterpriseIdAndDataKey(Long.parseLong(enterpriseId), ILvEnterpriseAddressService.COLUMN_ADDRESS_TEMPLATE_ID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(StringUtils.isNotBlank(addressArea)){
				LvEnterpriseAddressTemplateEntity enterpriseAddressTemplate = null;
				try {
					enterpriseAddressTemplate = RSBLL.getstance().getEnterpriseAddressTemplateService().getEnterpriseAddressTemplateById(Long.parseLong(addressArea));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(enterpriseAddressTemplate != null){
					try {
						reMap.putAll(BeanUtils.describe(enterpriseAddressTemplate));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			// 自有地址
			for(LvEnterpriseAddressEntity enterpriseAddressEntity:enterpriseAddressList){
				reMap.put(enterpriseAddressEntity.getDataKey(), enterpriseAddressEntity.getDataValue());
			}
		}
		
		return DicUtils.transferDicData(reMap);
	}
	
	
	/**
	 * 获取登录信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getLoginInfo(HttpServletRequest request) throws Exception{
		String ipAddr = LoginUtil.getIpAddr(request);
		String empId = LoginUtil.getEmpIdFromCookie(request);
		Map<String, String> loginInfo = new HashMap<String, String>();
		loginInfo.put("empId", empId);
		loginInfo.put("IP", ipAddr);
		return loginInfo;
	}
	
	/**
	 * 取企业主要人员并去重
	 * @param enterpriseId
	 * @param roleType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getRoleInfoListByEnterpriseIdAndRoleType(String enterpriseId){
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		if( StringUtils.isBlank(enterpriseId)){
			return reList;
		}
		// 角色主要信息
		List<LvEnterpriseRoleRelationEntity> roleRelationList = new ArrayList<LvEnterpriseRoleRelationEntity>();
		try {
			roleRelationList = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleRelationListByEnterpriseIdAndRoleType(Long.parseLong(enterpriseId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(roleRelationList == null || roleRelationList.isEmpty()){
			return reList;
		}
		
		List<LvEnterpriseRoleRelationEntity> recomeRoleRelationList = new ArrayList<LvEnterpriseRoleRelationEntity>();
		StringBuffer roleRelationIdStr = new StringBuffer();
		//for(LvEnterpriseRoleRelationEntity enterpriseRoleRelationEntity:roleRelationList){
		for(int i=0;i<roleRelationList.size();i++){
			if(roleRelationList.get(i).getRoleType().equals("orderPerson")){
				continue;
			}
			if(roleRelationIdStr.toString().contains(roleRelationList.get(i).getRoleId()+"")){
				continue;
			}
			roleRelationIdStr.append(roleRelationList.get(i).getRoleId()+"");
			for(int j=i+1;j<roleRelationList.size();j++){
				if(roleRelationList.get(i).getRoleId() == roleRelationList.get(j).getRoleId()){
					StringBuffer roleTypeStr = new StringBuffer();
					roleTypeStr.append(roleRelationList.get(i).getRoleType()).append(",").append(roleRelationList.get(j).getRoleType());
					roleRelationList.get(i).setRoleType(roleTypeStr.toString());
				}
			}
			recomeRoleRelationList.add(roleRelationList.get(i));
			if(StringUtils.isNotBlank(roleRelationIdStr.toString())){
				roleRelationIdStr.append(",").append(roleRelationList.get(i).getRoleId());
			}else{
				roleRelationIdStr.append(roleRelationList.get(i).getRoleId());
			}
			
		}
		
		if(recomeRoleRelationList == null || recomeRoleRelationList.isEmpty()){
			return reList;
		}
		
		for(LvEnterpriseRoleRelationEntity enterpriseRoleRelationEntity:recomeRoleRelationList){
			Map<String, String> roleDataMap = new HashMap<String, String>();
			
			// 关联信息
			roleDataMap.putAll(JSONObject.parseObject(JSONObject.toJSONString(enterpriseRoleRelationEntity), Map.class));
			roleDataMap.put("roleRelationId", enterpriseRoleRelationEntity.getId() + "");
			
			if(StringUtils.equalsIgnoreCase(enterpriseRoleRelationEntity.getRoleType(), ILvEnterpriseRoleRelationService.ROLETYPE_LEGALPARTNER)){
				// 获取企业信息
				try {
					roleDataMap.putAll(RSBLL.getstance().getEnterpriseService().getAllValueByEnterpriseId(enterpriseRoleRelationEntity.getRoleId() + ""));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				// 获取人员信息
				LvEnterprisePersonEntity enterprisePersonEntity = null;
				try {
					enterprisePersonEntity = RSBLL.getstance().getEnterprisePersonService().getEnterprisePersonById(enterpriseRoleRelationEntity.getRoleId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(enterprisePersonEntity != null){
					roleDataMap.putAll(JSONObject.parseObject(JSONObject.toJSONString(enterprisePersonEntity), Map.class));;
				}
			}
			// 角色扩展信息
			List<LvEnterpriseRoleDataEntity> roleDataList = null;
			try {
				roleDataList = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleDataListByRoleRelationId(enterpriseRoleRelationEntity.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(roleDataList != null && !roleDataList.isEmpty()){
				for(LvEnterpriseRoleDataEntity enterpriseRoleDataEntity:roleDataList){
					roleDataMap.put(enterpriseRoleDataEntity.getDataKey(), enterpriseRoleDataEntity.getDataValue());
				}
			}
			reList.add(roleDataMap);
		}
		return reList;
	}
	
}
