package com.jixiang.argo.lvzheng.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jixiang.argo.lvzheng.annotation.CheckCookie;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.utils.ActionResultUtils;
import com.jixiang.argo.lvzheng.utils.DicUtils;
import com.jixiang.argo.lvzheng.utils.EnterpriseUtils;
import com.jixiang.argo.lvzheng.utils.JSONUtils;
import com.jx.argo.ActionResult;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.enterprise.contract.ILvEnterpriseRoleRelationService;
import com.jx.service.enterprise.contract.ILvEnterpriseService;
import com.jx.service.enterprise.entity.LvEnterpriseAddressTemplateEntity;
import com.jx.service.enterprise.entity.LvEnterpriseBusinessEntity;
import com.jx.service.enterprise.entity.LvEnterpriseOperatingRangeEntity;
import com.jx.service.enterprise.entity.LvEnterprisePersonEntity;
import com.jx.service.enterprise.entity.LvEnterpriseRoleRelationEntity;
import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.LoginEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.tools.waq.WAQ;

@Path("enterprise")
@CheckCookie
public class EnterpriseController extends AbstractController{
	
	private static final String mainUrl = "http://www.lvzheng.com";
	
	@Path("emp/edit")
	public ActionResult empEdit() {
		return empEdit("");
	}
	
	@Path("emp/edit/{businessKey:[\\w-]+}")
	public ActionResult empEdit(String businessKey) {
		String orderId = WAQ.forXSS().HTMLEncode(beat().getRequest().getParameter("orderId"));
		String sourceType = WAQ.forXSS().HTMLEncode(beat().getRequest().getParameter("sourceType"));
		String morePage = WAQ.forXSS().HTMLEncode(beat().getRequest().getParameter("morePage"));
		model().add("sourceType", getSourceType(sourceType));
		try{
			model().add("orderId", orderId);
			List<String> cateIds = RSBLL.getstance().getSorderService().getAllProductCategoryIdByOrderId(orderId);
			if(cateIds == null || cateIds.isEmpty()){
				return redirect(mainUrl);
			}

			SorderEntity sorderEntity = RSBLL.getstance().getSorderService().getSorderEntityByid(Long.parseLong(orderId));
			if(sorderEntity == null){
				return redirect(mainUrl);
			}
			
			long enterpriseId = sorderEntity.getBusid();
			model().add("enterpriseId", enterpriseId);
			
			// 业务
			List<LvEnterpriseBusinessEntity> businessList = RSBLL.getstance().getEnterpriseBusinessService().getBusinessKeyListByGoodsIdList(cateIds);
			if(businessList == null || businessList.isEmpty()){
				return redirect(mainUrl);
			}else{
				model().add("businessList", businessList);
			}
			
			if(StringUtils.endsWithIgnoreCase(morePage, "false")){
				// 编辑一个业务
				model().add("onePage", true);
			}
			
			if(StringUtils.isBlank(businessKey)){
				businessKey = RSBLL.getstance().getEnterpriseService().getExtValueByEnterpriseIdAndKey(String.valueOf(enterpriseId), ILvEnterpriseService.EXT_KEY_LOCKBUSINESSKEY);
			}
			if(StringUtils.isBlank(businessKey)){
				businessKey = businessList.get(0).getBusinessKey();
			}
			for(int i = 0; i < businessList.size(); i++){
				if(StringUtils.equalsIgnoreCase(businessKey, businessList.get(i).getBusinessKey()) && i < businessList.size() - 1 ){
					String nextBusinessKey = businessList.get(i+1).getBusinessKey();
					model().add("nextBusinessKey", nextBusinessKey);
					break;
				}
			}
			String businessKeyCop = businessKey;
			if(businessKey.indexOf("-") > -1){
				businessKeyCop = businessKey.split("-")[0];
			}
			
			model().add("businessKey", businessKeyCop);
			// 业务信息详情
			Map<String, String> businessDataMap = RSBLL.getstance().getEnterpriseBusinessDataService().getBusinessDataMapByEnterpriseIdAndBusinessKey(enterpriseId + "", businessKey);
			if(businessDataMap != null && !businessDataMap.isEmpty()){
				model().addAll(businessDataMap);
			}
			
			// 企业主信息
			Map<String, String> enterpriseMap = RSBLL.getstance().getEnterpriseService().getAllValueByEnterpriseId(String.valueOf(enterpriseId));
			model().addAll(DicUtils.transferDicData(enterpriseMap));
			
			// 下单用户名 userName
			LoginEntity loginEntity = RSBLL.getstance().getLoginService().getLoginEntityById(sorderEntity.getUserid());
			if(loginEntity != null){
				model().add("userName", loginEntity.getUserphone());
			}
			
			model().add("DicUtils", DicUtils.class);
			model().add("EnterpriseUtils", EnterpriseUtils.class);
			return view("enterprise/business/" + businessKey);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return redirect(mainUrl);
	}
	
	//单纯展示企业库详情方法 待完善 目前和编辑使用同一个页面，以后需要提出一个专属页面
	@Path("business/show/{enterpriseId:\\S+}")
	public ActionResult show(String enterpriseId) {
		model().add("sourceType", "AE");
		try{
			model().add("enterpriseId", enterpriseId);
			// 编辑一个业务
			model().add("onePage", true);
			model().add("businessKey", "show");
			// 业务信息详情
			Map<String, String> businessDataMap = RSBLL.getstance().getEnterpriseBusinessDataService().getBusinessDataMapByEnterpriseIdAndBusinessKey(enterpriseId + "", "show");
			if(businessDataMap != null && !businessDataMap.isEmpty()){
				model().addAll(businessDataMap);
			}
			// 企业主信息
			Map<String, String> enterpriseMap = RSBLL.getstance().getEnterpriseService().getAllValueByEnterpriseId(String.valueOf(enterpriseId));
			model().addAll(DicUtils.transferDicData(enterpriseMap));
			model().add("DicUtils", DicUtils.class);
			model().add("EnterpriseUtils", EnterpriseUtils.class);
			return view("enterprise/business/show");
		}catch(Exception e){
			e.printStackTrace();
		}
		return redirect(mainUrl);
	}
	
	@Path("/common/getdicdatalist")
	public ActionResult getDicDataList(){
		String type = WAQ.forXSS().HTMLEncode(request().getParameter("type"));
		String parentId = request().getParameter("parentId");
		
		JSONObject json = new JSONObject();
		if(StringUtils.equals(type, "city")){
			List<AreasEntity> areaDataList = DicUtils.getAreaDataList(parentId);
			if(areaDataList != null && !areaDataList.isEmpty()){
				for(AreasEntity areasEntity:areaDataList){
					json.put(areasEntity.getAreaid() + "", areasEntity.getName());
				}
			}
		}
		if(StringUtils.equals(type, "addressTemplateId")){
			Map<String, String> addressAreaDicData = DicUtils.getAddressAreaDicData(parentId);
			json.putAll(addressAreaDicData);
		}
		if(StringUtils.equals(type, "addressInfo")){
			LvEnterpriseAddressTemplateEntity enterpriseAddressTemplate = null;
			try {
				enterpriseAddressTemplate = RSBLL.getstance().getEnterpriseAddressTemplateService().getEnterpriseAddressTemplateById(Long.parseLong(parentId));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(enterpriseAddressTemplate != null){
				return ActionResultUtils.renderJson(JSONUtils.toJsonString(enterpriseAddressTemplate));
			}
		}
		
		return ActionResultUtils.renderJson(json);
	}
	
	@Path("/common/getdicdatabyid")
	public ActionResult getDicDataById(){
		String type = WAQ.forXSS().HTMLEncode(request().getParameter("type"));
		String dicDataId = WAQ.forXSS().HTMLEncode(request().getParameter("dicDataId"));
		
		if(StringUtils.equals(type, "city")){
			return ActionResultUtils.renderText(DicUtils.getAreaDataById(dicDataId));
		}
		return ActionResultUtils.renderText("");
	}
	
	@Path("/business/enterpriseSubmit")
	public ActionResult enterpriseSubmit(){
		String enterpriseId = WAQ.forXSS().HTMLEncode(request().getParameter("enterpriseId"));
		String businessKey = WAQ.forXSS().HTMLEncode(request().getParameter("nextBusinessKey"));
		try {
			RSBLL.getstance().getEnterpriseBusinessDataService().submitEnterpriseBusinessData(enterpriseId, businessKey, EnterpriseUtils.getLoginInfo(request()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultUtils.renderJson("");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Path("/business/enterpriseSave")
	public ActionResult enterpriseSave(){
		String data = request().getParameter("data");
		System.out.println(data);
		String enterpriseId = WAQ.forXSS().HTMLEncode(request().getParameter("enterpriseId"));
		
		if(StringUtils.isBlank(data)){
			return ActionResultUtils.renderText("NULL");
		}
		JSONObject dataJson = JSONObject.parseObject(data);
		
		// 业务信息保存
		List<LvEnterpriseBusinessEntity> businessKeyList = null;
		try {
			businessKeyList = RSBLL.getstance().getEnterpriseBusinessService().getBusinessKeyList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(businessKeyList != null && !businessKeyList.isEmpty()){
			System.out.println("businessKeyList is not null");
			for(LvEnterpriseBusinessEntity lvEnterpriseBusinessEntity:businessKeyList){
				String businessKey = lvEnterpriseBusinessEntity.getBusinessKey();
				System.out.println(businessKey);
				long businessId = lvEnterpriseBusinessEntity.getBusinessId();
				System.out.println(businessId);
				if(!dataJson.containsKey(businessKey)){
					continue;
				}
				Map businessMap = dataJson.getObject(businessKey, Map.class);
				try {
					RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, businessId + "", businessMap, EnterpriseUtils.getLoginInfo(request()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// 保存企业库地址信息
		if(dataJson.containsKey("addressData")){
			System.out.println("addressData is not null");
			Map addressDataMap = dataJson.getObject("addressData", Map.class);
			try {
				RSBLL.getstance().getEnterpriseAddressService().saveEnterpriseAddressData(enterpriseId, addressDataMap, EnterpriseUtils.getLoginInfo(request()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 相关角色信息
		JSONArray partnerInfoArray = dataJson.getJSONArray("partnerInfoArray");
		if(partnerInfoArray != null && !partnerInfoArray.isEmpty()){
			System.out.println("partnerInfoArray is not null");
			for(int i = 0; i < partnerInfoArray.size(); i++){
				JSONObject comPartnerJson = partnerInfoArray.getJSONObject(i);
				// 保存主体信息
				Map partnerInfoMain = comPartnerJson.getObject("partnerInfoMain", Map.class);
				if(partnerInfoMain == null || partnerInfoMain.isEmpty()){
					continue;
				}
				String dataRoleType = comPartnerJson.getString("dataRoleType");
				long roleId = 0;
				if(StringUtils.equalsIgnoreCase(dataRoleType, ILvEnterpriseRoleRelationService.ROLETYPE_LEGALPARTNER)){
					// 保存单位
					try {
						roleId = RSBLL.getstance().getEnterpriseService().saveEnterpriseAllEntity(partnerInfoMain, EnterpriseUtils.getLoginInfo(request()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					// 保存人员
					try {
						roleId = RSBLL.getstance().getEnterprisePersonService().saveEnterprisePersonEntity(enterpriseId, partnerInfoMain, EnterpriseUtils.getLoginInfo(request()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				partnerInfoMain.put("roleId", roleId);
				// 保存关系与扩展数据
				Map comPartnerExt = comPartnerJson.getObject("partnerInfoExt", Map.class);
				try {
					RSBLL.getstance().getEnterpriseRoleRelationService().saveRoleRelationEntity(Long.parseLong(enterpriseId), dataRoleType, roleId, comPartnerExt, EnterpriseUtils.getLoginInfo(request()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ActionResultUtils.renderJson(dataJson);
	}
	@Path("/business/roleRelationDel")
	public ActionResult roleRelationDel(){
		String roleRelationId = request().getParameter("relationId");
		if(StringUtils.isBlank(roleRelationId)){
			return ActionResultUtils.renderText("OK");
		}
		
		try {
			RSBLL.getstance().getEnterpriseRoleRelationService().delEnterpriseRoleRelationEntityById(Long.parseLong(roleRelationId), EnterpriseUtils.getLoginInfo(request()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultUtils.renderText("OK");
	}
	
	@Path("/business/getOperatingRange")
	public ActionResult getOperatingRange(){
		String addressType = request().getParameter("addressType");
		String searchKey = request().getParameter("searchKey");
		String parentId = request().getParameter("parentId");
		List<LvEnterpriseOperatingRangeEntity> operatingRangeList = null;
		if(StringUtils.isBlank(searchKey)){
			try {
				operatingRangeList = RSBLL.getstance().getEnterpriseOperatingRangeService()
						.getOperatingRangeListByAddressTypeAndParentId(addressType, parentId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				operatingRangeList = RSBLL.getstance().getEnterpriseOperatingRangeService()
						.getOperatingRangeListByAddressTypeAndSearchKey(addressType, searchKey);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(operatingRangeList != null && !operatingRangeList.isEmpty()){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("operatingRangeList", operatingRangeList);
			return ActionResultUtils.renderJson(jsonObject);
		}
		return ActionResultUtils.renderText("");
	}
	
	@Path("/business/getPersonRole")
	public ActionResult getPersonRole(){
		String enterpriseId = WAQ.forXSS().HTMLEncode(request().getParameter("enterpriseId"));
		String roleId = WAQ.forXSS().HTMLEncode(request().getParameter("roleId"));
		List<LvEnterprisePersonEntity> personList = new ArrayList<LvEnterprisePersonEntity>();
		
		if(StringUtils.isNotBlank(roleId)){
			LvEnterprisePersonEntity enterprisePerson = null;
			try {
				enterprisePerson = RSBLL.getstance().getEnterprisePersonService().getEnterprisePersonById(Long.parseLong(roleId));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(enterprisePerson != null){
				personList.add(enterprisePerson);
			}
		}else{
			try {
				personList = RSBLL.getstance().getEnterprisePersonService().getPersonListByEnterpriseId(enterpriseId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(personList != null && !personList.isEmpty()){
			JSONObject jsonObject = new JSONObject();
			
			JSONArray personRoleJsonArray = new JSONArray();
			for(LvEnterprisePersonEntity enterprisePersonEntity:personList){
				if(StringUtils.isBlank(enterprisePersonEntity.getIdNum())){
					continue;
				}
				JSONObject entityObject = new JSONObject();
				entityObject.put("id", enterprisePersonEntity.getId() + "");	
				entityObject.put("name", enterprisePersonEntity.getName());
				entityObject.put("idNum", enterprisePersonEntity.getIdNum());
				
				// 获取人员担任的角色
				List<LvEnterpriseRoleRelationEntity> roleRelationList = null;
				try {
					roleRelationList = RSBLL.getstance().getEnterpriseRoleRelationService().getRoleRelationListByEnterpriseIdAndRoleId(Long.parseLong(enterpriseId), enterprisePersonEntity.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				String roleType = "";
				if(roleRelationList != null && !roleRelationList.isEmpty()){
					for(LvEnterpriseRoleRelationEntity enterpriseRoleRelationEntity:roleRelationList){
						roleType += enterpriseRoleRelationEntity.getRoleType() + ";";
					}
				}
				entityObject.put("roleType", roleType);
				personRoleJsonArray.add(entityObject);
			}
			jsonObject.put("personRoleList", personRoleJsonArray);
			return ActionResultUtils.renderJson(jsonObject);
		}
		return ActionResultUtils.renderText("");
	}
	@SuppressWarnings("unchecked")
	@Path("/business/roleRelationSave")
	public ActionResult roleRelationSave(){
		String enterpriseId = request().getParameter("enterpriseId");
		String roleType = request().getParameter("roleType");
		String roleIdArray = request().getParameter("roleIdArray");
		String roleDataExt = request().getParameter("roleDataExt");
		
		Map<String, String> roleDataExtMap = new HashMap<String, String>();
		if(StringUtils.isNotBlank(roleDataExt)){
			roleDataExtMap = JSONObject.parseObject(roleDataExt, Map.class);
		}
		
		List<Long> roleIdList = new ArrayList<Long>();
		if(StringUtils.isNotBlank(roleIdArray)){
			roleIdList = JSONArray.parseArray(roleIdArray, Long.class);
		}
		
		try {
			RSBLL.getstance().getEnterpriseRoleRelationService().saveRoleRelationEntity(Long.parseLong(enterpriseId), roleType, roleIdList, roleDataExtMap, EnterpriseUtils.getLoginInfo(request()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultUtils.renderText("OK");
	}
	
	/**
	 * 企业库来源类型映射
	 * @param sourceType
	 * @return
	 */
	private String getSourceType(String sourceType){
		if(StringUtils.isBlank(sourceType)){
			return "";
		}
		switch (Integer.valueOf(sourceType)) {
		case 1:
			return "AE";
		case 2:
			return "WF";
		default:
			break;
		}
		return "";
	}

	
	
	
}
