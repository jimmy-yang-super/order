package com.jixiang.argo.lvzheng.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jx.blackface.tools.webblack.Constant;
import com.jx.blackface.tools.webblack.auth.AuthHelper;
import com.jx.blackface.tools.webblack.exception.WebblackException;
import com.jx.blackface.tools.webblack.page.entity.QueryPageEntity;
import com.jx.blackface.tools.webblack.query.checkname.CheckNameQuery;
import com.jx.blackface.tools.webblack.query.checkname.entity.NameCheckInfoQueryEntity;
import com.jx.blackface.tools.webblack.query.checkname.entity.NameCheckInfoQueryEntity.ApplyInfo;
import com.jx.blackface.tools.webblack.query.checkname.entity.NameCheckInfoQueryEntity.BaseInfo;
import com.jx.blackface.tools.webblack.query.checkname.entity.NameCheckInfoQueryEntity.CheckInfo;
import com.jx.blackface.tools.webblack.query.checkname.entity.NameCheckInfoQueryEntity.EntInfo;
import com.jx.blackface.tools.webblack.query.checkname.entity.NameCheckInfoQueryEntity.InvEntity;
import com.jx.blackface.tools.webblack.query.checkname.entity.NameQueryEntity;
import com.jx.blackface.tools.webblack.query.setup.SetupQuery;
import com.jx.blackface.tools.webblack.query.setup.entity.ApproveMsgEntity;
import com.jx.blackface.tools.webblack.query.setup.entity.BoardSupedEntity;
import com.jx.blackface.tools.webblack.query.setup.entity.DownloadDetailEntity;
import com.jx.blackface.tools.webblack.query.setup.entity.EntMemberEntity;
import com.jx.blackface.tools.webblack.query.setup.entity.InvDetailEntity;
import com.jx.blackface.tools.webblack.query.setup.entity.InvestorQueryEntity;
import com.jx.blackface.tools.webblack.query.setup.entity.ReqDetailEntity;
import com.jx.blackface.tools.webblack.setup.SetupHelper;
import com.jx.blackface.tools.webblack.setup.entity.ContactEntity;
import com.jx.blackface.tools.webblack.setup.entity.EntBaseInfoEntity;
import com.jx.blackface.tools.webblack.setup.entity.MemberEntity;
import com.jx.blackface.tools.webblack.setup.entity.OtherInfoEntity;
import com.jx.blackface.tools.webblack.setup.entity.SetupInitEntity;
import com.jx.blackface.tools.webblack.setup.entity.SetupInitResponseEntity;
import com.jx.blackface.tools.webblack.setup.entity.StagespayEntity;
import com.jx.blackface.tools.webblack.user.RegistHelper;
import com.jx.blackface.tools.webblack.user.entity.UserGovEntity;
import com.jx.blackface.tools.webblack.utils.WebLogicUtils;
import com.jx.service.enterprise.entity.LvEnterpriseBusinessDataEntity;
import com.jx.service.enterprise.entity.LvEnterpriseEntity;
import com.jx.service.enterprise.entity.LvEnterprisePersonEntity;
import com.jx.service.workflow.entity.LvFileEntity;

public class WebBlackUtils {
	
	public static final String alreadySubmit = "已提交(待审核)";
	public static final String alreadyOk     = "已核准";
	public static final String dismissed     = "不予受理";
	public static final String unsubmit      = "未提交";
	
	public static final String checkNameGovStatus_two   = "2";  //未获取工商局信息
	public static final String checkNameGovStatus_twelve   = "12";  //获取工商局信息失败
	public static final String checkNameGovStatus_Twenty   = "20";  //工商信息已获取
	public static final String setupGovStatus_two = "2";  //未获取填写工商信息
	public static final String setupGovStatus_four = "4";  //未获取补充信息
	public static final String setupGovStatus_twelve = "12";  //获取工商信息失败
	public static final String setupGovStatus_fourteen = "14";  //获取补充信息失败
	public static final String setupGovStatus_Twenty = "20";  //工商信息已获取
	
	public static final String setupReason = "2014"; //设立审核通知(标示)
	
	public static final String position_1 = "1";  //董事
	public static final String position_2 = "2";  //经理
	public static final String position_3 = "3";  //监事
	
	public static Map<String,String> positionType = new HashMap<String, String>();
	static{
		positionType.put("1", "directorManager-S"); //董事
		positionType.put("2", "manager-S");     //经理
		positionType.put("3", "supervisor-S"); //监事
	}
	
//	//股东类型
//	public static Map<String,String> gudongType = new HashMap<String, String>();
//	static{
//		gudongType.put("自然人股东（内）", "20");
//		gudongType.put("单位股东（内）", "10");
//	}
	//证件类型
	public static Map<String,String> zhengjianType = new HashMap<String, String>();
	static{
		zhengjianType.put("中华人民共和国居民身份证", "1");
		zhengjianType.put("军人离(退)休证", "5");
		zhengjianType.put("其他有效身份证件", "9");
		zhengjianType.put("中华人民共和国军官证", "2");
		zhengjianType.put("中华人民共和国警官证", "3");
	}
	
	/***
	 * 获取工商系统核名信息并且存入企业库
	 * 1核准通过
	 * 2 未获取到企业库id 3 未获取到企业库信息 4 账号密码错误 5 公司名称错误 6 获取校验信息失败 7 获取baseinfo失败
	 * 8 获取申请信息失败 9 获取核准信息失败 10 提示未提交工商信息 11 提示工商信息待审核 12 不予批准 (驳回)
	 * @return
	 */
	public static String getCheckInfo(String enterpriseId,String userName,String passWord,String companyName){
		try {
			if(StringUtils.isBlank(enterpriseId)){
				return "未获取到企业库ID";
			}
			LvEnterpriseEntity enterpriseById = RSBLL.getstance().getEnterpriseService().getEnterpriseById(Long.valueOf(enterpriseId));
			if(null == enterpriseById){
				return "未获取到企业库信息";
			}
			
			String sessionId = AuthHelper.postLogin(userName, passWord);
			if(StringUtils.isBlank(sessionId)){
				return "账号密码错误";
			}
			
			UserGovEntity userInfo = RegistHelper.getUserInfo(sessionId);
			//保存使用此账号核名的登录人信息
			if(null != userInfo){
				Map<String,String> userMap = new HashMap<String, String>();
				userMap.put("idNum", userInfo.getIdCard()); //证件号码
				userMap.put("idType", userInfo.getCerType()); //证件类型
				userMap.put("idAddress", userInfo.getAddress()); //联系地址
				userMap.put("email", userInfo.getEmail()); //邮箱
				userMap.put("phoneNum", userInfo.getMobile()); //移动电话
				userMap.put("zipCode", userInfo.getZipCode()); //邮政编码
				userMap.put("sex", userInfo.getSex()); //
				userMap.put("name", userInfo.getUserName()); //
				userMap.put("contry", userInfo.getCountryCity()); //国籍
				Long roleId = RSBLL.getstance().getEnterprisePersonService().saveEnterprisePersonEntity(enterpriseId, userMap, null);
				RSBLL.getstance().getEnterpriseRoleRelationService().saveRoleRelationEntity(Long.parseLong(enterpriseId), "checkNamePerson", roleId, null, null);
			}
			
			
			
			QueryPageEntity<NameQueryEntity> queryNameList = CheckNameQuery.queryNwList(sessionId, companyName);
			List<NameQueryEntity> rows = queryNameList.getRows();
			if(null == queryNameList || (rows == null || rows.size() <= 0)){
				return "公司名称输入不正确";
			}
			String transactId  = "";
			for(NameQueryEntity queryE : rows){
				if(StringUtils.equals(queryE.getNameStateCo(), "2")){
					transactId = queryE.getTransactId();
					break;
				}
			}
			if(StringUtils.isBlank(transactId)){
				return "公司名称已驳回!";
			}
			
			Map<String,String> mapAll = new HashMap<String, String>();
			NameCheckInfoQueryEntity checkInfo = CheckNameQuery.queryCheckInfo(sessionId, transactId);
			if(null == checkInfo){
				mapAll.put("checkNameGovStatus", checkNameGovStatus_twelve); //获取工商信息失败
				RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, "2003", mapAll, null);
				return "获取工商局校验信息失败";
			}
			BaseInfo baseInfo = checkInfo.getBaseInfo();
			if(null == baseInfo){
				mapAll.put("checkNameGovStatus", checkNameGovStatus_twelve); //获取工商信息失败
				RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, "2003", mapAll, null);
				return "获取工商局baseinfo失败";
			}
			ApplyInfo applyInfo = checkInfo.getApplyInfo();
			if(null == applyInfo){
				mapAll.put("checkNameGovStatus", checkNameGovStatus_twelve); //获取工商信息失败
				RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, "2003", mapAll, null);
				return "获取工商局申请信息失败";
			}
			
			mapAll.put("checkNameGovStatus", checkNameGovStatus_two); //先设置未为获取信息
			RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, "2003", mapAll, null);
			mapAll.clear();
			
			
			List<InvEntity> invList = checkInfo.getInvList(); //获得人员信息
			if(null != invList && invList.size() > 0){
				Map<String,String> persionMap = null; 
				for(InvEntity invEntity : invList){
					persionMap = new HashMap<String, String>();
					if(StringUtils.equals(invEntity.getInvType(), "自然人股东（内）")){
						persionMap.put("name", invEntity.getInv());
						if(StringUtils.isNotBlank(invEntity.getCerType())){
							persionMap.put("idType", zhengjianType.get(invEntity.getCerType()));
						}
						if(StringUtils.isNotBlank(invEntity.getCerNo())){
							persionMap.put("idNum", invEntity.getCerNo());
						}
						Long roleId = RSBLL.getstance().getEnterprisePersonService().saveEnterprisePersonEntity(enterpriseId, persionMap, null);
						RSBLL.getstance().getEnterpriseRoleRelationService().saveRoleRelationEntity(Long.parseLong(enterpriseId), "naturalPartner", roleId, null, null);
					}else{
						//单位名称
						persionMap.put("name", invEntity.getInv());
						//单位证照号码
						if(StringUtils.isNotBlank(invEntity.getCerNo())){ 
							persionMap.put("idNum", invEntity.getCerNo());
						}
						Long roleId = RSBLL.getstance().getEnterprisePersonService().saveEnterprisePersonEntity(enterpriseId, persionMap, null);
						RSBLL.getstance().getEnterpriseRoleRelationService().saveRoleRelationEntity(Long.parseLong(enterpriseId), "legalPartner", roleId, null, null);
					}
				}
			}
			
			mapAll.put("checkNameAccount", userName);
			mapAll.put("checkNamePassword", passWord); 
			//申请基本信息
			mapAll.put("name", applyInfo.getEntName()); //公司名称
			mapAll.put("mainBusinessCode", applyInfo.getIndustryCo()); //主营义务编号
			mapAll.put("mainBusiness", applyInfo.getWb()); //主营业务
			mapAll.put("industryCharacteristics", applyInfo.getNameInd()); //行业特点
			mapAll.put("shopName", applyInfo.getEntTra()); //字号
			mapAll.put("organizationType", applyInfo.getOrgForm()); //组织形式
			mapAll.put("gov-nameDistrict", applyInfo.getNameDistrict()); //核名行政区域 json中的 北京
			mapAll.put("gov-nameId", applyInfo.getNameId());
			mapAll.put("gov-transactId", applyInfo.getSerialNo());
			mapAll.put("gov-savePerTo", applyInfo.getSavePerTo()); //有效期至
			//获取企业基本信息
			EntInfo entInfo = checkInfo.getEntInfo();
			if(null == entInfo){
				return "获取企业信息失败!";
			}

			String entTypeCode = DicUtils.getEnterpriseDicKeyByDicTypeAndDicValue("enterpriseType", entInfo.getEntType());
			mapAll.put("enterpriseType", entTypeCode); //企业类型
			
			//未提交
			if(StringUtils.equals(baseInfo.getNameStateCo(), unsubmit)){
				mapAll.put("checkNameGovStatus", "1"); //未提交工商信息
				RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, "2003", mapAll, null);
				return "未提交工商信息,请到工商局网站进行提交";
			}else if(StringUtils.equals(baseInfo.getNameStateCo(), alreadySubmit)){ //已提交待审核
//				mapAll.put("checkNameGovStatus", checkNameGovStatus_sex);  //已获取
//				RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, "2003", mapAll, null);
				return "工商信息待审核";
			}else if(StringUtils.equals(baseInfo.getNameStateCo(), alreadyOk)){  //已核准
				CheckInfo check = checkInfo.getCheckInfo();
				if(null == check){
					mapAll.put("checkNameGovStatus", checkNameGovStatus_twelve); //获取工商信息失败
					RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, "2003", mapAll, null);
					return "未获取到核准信息";
				}
				mapAll.put("checkNameGovStatus", checkNameGovStatus_Twenty); //工商信息以获取
				//审核结果
				mapAll.put("gov-checkResult", check.getCheckResult()); //核名结果
				mapAll.put("gov-checkOrg", check.getCheckOrg()); //核名审核部门
				mapAll.put("gov-checkDate", check.getCheckDate()); //核名时间
				
				mapAll.put("checkedNameCode", baseInfo.getNotNo()); //核准文号
				mapAll.put("gov-nameStateCo", baseInfo.getNameStateCo()); //业务状态
				mapAll.put("checkNameStatus", "1"); //核名状态 已核名通过
				RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, "2003", mapAll, null);
			}else if(StringUtils.equals(baseInfo.getNameStateCo(), dismissed)){ //不予受理 
				CheckInfo check = checkInfo.getCheckInfo();
				if(null == check){
					mapAll.put("checkNameGovStatus", checkNameGovStatus_twelve); //获取工商信息失败
					RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, "2003", mapAll, null);
					return "未获取到核准信息";
				}
				mapAll.put("checkNameGovStatus", checkNameGovStatus_Twenty);
				
				//审核结果
				mapAll.put("gov-checkResult", check.getCheckResult()); //核名结果
				mapAll.put("gov-checkOrg", check.getCheckOrg()); //核名审核部门
				mapAll.put("gov-checkDate", check.getCheckDate()); //核名时间
				
				mapAll.put("checkNameStatus", "4"); //核名状态 驳回
				RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, "2003", mapAll, null);
				return "提交的核名信息，工商局不予受理";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("************查询工商局信息出错,企业库id:"+enterpriseId+"账号"+userName+"密码"+passWord+"公司名称"+companyName);
			return "未知错误,请刷新重试!";
		}
		return "核准通过";
	}
	
	/**
	 * 获取工商设立信息
	 * @param enterpriseId
	 * @param userName
	 * @param passWord
	 * @param companyName
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getGSSheLiInfo(String enterpriseId,String userName,String passWord,String companyName){
//		String returnStr = "获取工商局设立信息失败!请刷新后重试!";
		if(StringUtils.isBlank(enterpriseId)){
			return "未获取到企业库ID";
		}
		LvEnterpriseEntity enterpriseById = null;
		try {
			enterpriseById = RSBLL.getstance().getEnterpriseService().getEnterpriseById(Long.valueOf(enterpriseId));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("未获取到企业库信息异常,企业库id:"+enterpriseId+"企业名称:"+companyName+"用户名:"+userName+"密码:"+passWord);
		}
		if(null == enterpriseById){
			return "未获取到企业库信息";
		}
		String sessionId = "";
		try {
			sessionId = AuthHelper.postLogin(userName, passWord);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("根据账号密码获取sessionid失败!用户名:"+userName+"密码:"+passWord);
		}
		if(StringUtils.isBlank(sessionId)){
			return "账号密码错误";
		}
		
		Map<String,String> mapAll = new HashMap<String, String>();
		
		mapAll.put("setupGovStatus", setupGovStatus_four); //先设置为未获取填写补充信息
		try {
			RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("设置未获取填写补充信息状态失败!企业库id:"+enterpriseId);
			return "初始化设立状态失败";
		}
		mapAll.clear();
		
		ReqDetailEntity cpReqDetail = null;	
		try {
			QueryPageEntity<ReqDetailEntity> queryReqList = SetupQuery.queryReqList(sessionId, companyName, Constant.OP_TYPE_SETUP, "", 1); //20标示设立
			if(null != queryReqList && !queryReqList.getRows().isEmpty()){
				cpReqDetail = queryReqList.getRows().get(0);
				//获取判断设立是否提交或在审核状态［获取公司详细信息］
//				cpReqDetail = SetupQuery.getCpReqDetail(sessionId, setupInit.getReqId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(cpReqDetail == null){
			return "获取公司详细信息失败!";
		}
		
		if(StringUtils.equals(cpReqDetail.getOperationTypeZh(), "设立") && StringUtils.equals(cpReqDetail.getStateZh(), "未提交")){
			mapAll.put("setupStatus", "2"); //设立状态未提交
			try {
				RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("更新设立申请未提交状态失败,企业库id:"+enterpriseId);
			}
			return "设立申请未在工商局进行提交,请先提交再结束任务";
		}else if(StringUtils.equals(cpReqDetail.getOperationTypeZh(), "设立") && StringUtils.equals(cpReqDetail.getStateZh(), "等待内容审查")){
			mapAll.put("setupStatus", "3"); //设立状态审核中
			try {
				RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("更新设立申请审核中状态失败,企业库id:"+enterpriseId);
			}
			return "设立申请状态为等待内容审查,请等待";
		}else if(StringUtils.equals(cpReqDetail.getOperationTypeZh(), "设立") && StringUtils.equals(cpReqDetail.getStateZh(), "内容审查未通过")){
			mapAll.put("setupStatus", "4"); //设立状态驳回
			mapAll.put("setupGovStatus", setupGovStatus_Twenty); 
			try {
				RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("更新设立申请驳回状态失败,企业库id:"+enterpriseId);
			}
			List<ApproveMsgEntity> queryApproveMsgList = null;
			try {
				queryApproveMsgList = SetupQuery.queryApproveMsgList(sessionId, cpReqDetail.getReqId());
			} catch (Exception e1) {
				e1.printStackTrace();
				return "获取设立审查意见失败!";
			}
			if(null != queryApproveMsgList && queryApproveMsgList.size() > 0){
				for(ApproveMsgEntity appmsg : queryApproveMsgList){
					if(!StringUtils.equals(appmsg.getBusiState(), "内容审查未通过")){
						continue;
					}
					ApproveMsgEntity approveMsgEntity = appmsg;
					mapAll.put("gov-regOrg", approveMsgEntity.getRegOrg());  //审批部门
					mapAll.put("gov-busiState", approveMsgEntity.getBusiState()); //审批状态
					mapAll.put("gov-context", approveMsgEntity.getContext());  //审批意见
					mapAll.put("gov-noticeDate", approveMsgEntity.getNoticeDate()); //审批时间
					mapAll.put("gov-nameStateCo", cpReqDetail.getState()); //业务状态
					try {
						RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("保存设立审查意见失败!企业库id"+enterpriseId);
						return "保存设立审查意见失败!";
					}
				}
			}
			return "设立申请被驳回,请重新提交!";
		}else{
			List<ApproveMsgEntity> queryApproveMsgList = null;
			try {
				queryApproveMsgList = SetupQuery.queryApproveMsgList(sessionId, cpReqDetail.getReqId());
			} catch (Exception e1) {
				e1.printStackTrace();
				return "获取设立审查意见失败!";
			}
			if(null != queryApproveMsgList && queryApproveMsgList.size() > 0){
				for(ApproveMsgEntity appmsg : queryApproveMsgList){
					if(!StringUtils.equals(appmsg.getBusiState(), "内容审查通过")){
						continue;
					}
					ApproveMsgEntity approveMsgEntity = appmsg;
					mapAll.put("gov-regOrg", approveMsgEntity.getRegOrg());//审批部门
					mapAll.put("gov-regOrgCode", cpReqDetail.getRegOrg()); //审批部门id
					mapAll.put("gov-busiState", approveMsgEntity.getBusiState());//审批状态
					mapAll.put("gov-context", approveMsgEntity.getContext());//审批意见
					mapAll.put("gov-noticeDate", approveMsgEntity.getNoticeDate());//审批时间
					mapAll.put("gov-busiState", cpReqDetail.getState()); //业务状态
					mapAll.put("gov-entId", cpReqDetail.getEntId()); //设立通过后预约需要用到的id
					mapAll.put("gov-reqId", cpReqDetail.getReqId()); //设立通过后预约需要用到的id
					try {
						RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("保存设立审查意见失败!企业库id"+enterpriseId);
						return "保存设立审查意见失败!";
					}
				}
			}
		}
		
		mapAll.put("setupGovStatus", setupGovStatus_fourteen); //预先设置获取工商补充信息失败
		try {
			RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		EntBaseInfoEntity entBaseInfo = null;
		try {
			//获取设立基本信息
			entBaseInfo = SetupQuery.getEntBaseInfo(sessionId, cpReqDetail.getEntId());
		} catch (WebblackException e) {
			e.printStackTrace();
		}
		if(null == entBaseInfo){
			return "保存设立基本信息状态失败!";
		}
		
		
		mapAll.clear();
		//网登账户名
		mapAll.put("checkNameAccount", userName);
		mapAll.put("checkNamePassword", passWord); 
		//获取填写基本信息
		mapAll.put("regCapital", entBaseInfo.getRegCap()); //注册资本
		mapAll.put("doBusinessDuration", entBaseInfo.getTradeTerm()); //营业期限
		mapAll.put("operatingRange", entBaseInfo.getPtBusScope()); //经营范围
		mapAll.put("residence", entBaseInfo.getDomDistrict()); //住所(经营场所)
		mapAll.put("copyNo", entBaseInfo.getCopyNo()); //营业执照副本数
		
		try {
			RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("保存基本信息失败,企业库id"+enterpriseId);
			return "保存基本信息失败";
		}
		mapAll.clear();
		
		//［住所产权人，住所产权类型{有房产证}，住所提供方式{租赁}，营业面积{100平方米}，住所使用期限{5年}］
		Map<String,String> addressMap = new HashMap<String, String>();
		addressMap.put("useArea", entBaseInfo.getDomAcreage()); //使用面积
		addressMap.put("theOwnerName", entBaseInfo.getDomOwner()); //产权人
		addressMap.put("getWay", entBaseInfo.getDomProRight()); //提供方式
		addressMap.put("domOwnType", entBaseInfo.getDomOwnType()); //住所产权类型
		addressMap.put("domDistrict", entBaseInfo.getDomDistrict()); //住所(经营场所)
		addressMap.put("domDetail", entBaseInfo.getDomDetail()); //住所经营场所(详细地址)
		addressMap.put("domProvince", entBaseInfo.getDomProRight()); //住所提供方式
		addressMap.put("useAge", entBaseInfo.getDomTerm()); //使用期限
		try {
			RSBLL.getstance().getEnterpriseAddressService().saveEnterpriseAddressData(enterpriseId, addressMap, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("保存地址信息失败,企业库id:"+enterpriseId);
			return "保存地址信息失败!";
		}
		
		
		QueryPageEntity<InvestorQueryEntity> investorForEntId = null;
		try {
			//获取股东信息
			investorForEntId = SetupQuery.getInvestorForEntId(sessionId, entBaseInfo.getEntId(), 30);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取股东信息失败,企业库id:"+enterpriseId+"sessionid:"+sessionId+"entid:"+entBaseInfo.getEntId());
		}
		if(null == investorForEntId){
			return "获取股东信息失败!";
		}
		boolean gudongInfo = true;
		for(int i = 0 ; i < investorForEntId.getTotalrows() ; i++){
			gudongInfo = true;
			InvestorQueryEntity investorE = investorForEntId.getRows().get(i);
			if(null == investorE){
				break;
			}
			if(StringUtils.isBlank(investorE.getInv())){
				break;
			}
			
			InvDetailEntity ivtE = null;
			try {
				ivtE = SetupQuery.getIvtById(sessionId, investorE.getIvtId()); //查询股东的详细信息
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(null == ivtE){
				break;
			}
			Long roleId = 0l;
			String dataRoleType = "";
			if(StringUtils.equals(investorE.getInvTypeName(), "自然人股东")){
				dataRoleType = "naturalPartner";
				LvEnterprisePersonEntity enterprisePersonE = null;
				try {
					//查询是否有此人信息
					enterprisePersonE = RSBLL.getstance().getEnterprisePersonService().getEnterprisePersonByNameAndEnterpriseId(investorE.getInv(),Long.valueOf(enterpriseId));
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				if(null == enterprisePersonE){
					break;
				}
				Map<String,String> personMap = new HashMap<String, String>();
				personMap.put("id", String.valueOf(enterprisePersonE.getId())); //得到自然股东人的id
				personMap.put("name", investorE.getInv());  //姓名
				personMap.put("idNum", ivtE.getCerNo()); //证件号码
				personMap.put("idAddress", ivtE.getDom());   //住所 
				personMap.put("residenceProv", ivtE.getDomProv());   //省 
				personMap.put("residenceCity", ivtE.getCity());   //市
				personMap.put("residenceAddress", ivtE.getDomOther());   //户籍登记 - 详细地址 
				try {
					roleId = RSBLL.getstance().getEnterprisePersonService().saveEnterprisePersonEntity(enterpriseId, personMap, null); //保存自然人股东
					gudongInfo = false;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("保存自然人股东失败,企业库id:"+enterpriseId+"人员姓名:"+investorE.getInv());
					break;
				}
			}else{
				dataRoleType = "legalPartner";
				Map<String,String> unitMap = new HashMap<String, String>();
				unitMap.put("enterpriseId", String.valueOf(enterpriseId)); //得到单位股东的id
				unitMap.put("name", investorE.getInv());  //单位名称
				unitMap.put("addressFormat", ivtE.getDom());   //公司注册地址
				unitMap.put("businessLicenseNum", ivtE.getCerNo());  //单位证照号码
				unitMap.put("residenceProv", ivtE.getDomProv());   //省 
				unitMap.put("residenceCity", ivtE.getCity());   //市
				unitMap.put("residenceAddress", ivtE.getDomOther());   //住所 - 详细地址 
				try {
					roleId = RSBLL.getstance().getEnterpriseService().saveEnterpriseAllEntity(unitMap, null);  //保存单位股东
					gudongInfo = false;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("保存单位股东失败,企业库id:"+enterpriseId+"单位名称:"+investorE.getInv());
					break;
				}
			}
			//保存出资信息
			Map<String,String> comPartnerExt = new HashMap<String, String>();
			comPartnerExt.put("capitalSize", ivtE.getSubConAm()); //出资额(总额,为明细和)
			try {
				RSBLL.getstance().getEnterpriseRoleRelationService().saveRoleRelationEntity(Long.parseLong(enterpriseId), dataRoleType, roleId, comPartnerExt, null);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			
			//出资信息明细
			List<StagespayEntity> stagespayList = ivtE.getStagespayList();
			if(null == stagespayList || stagespayList.isEmpty()){
				break;
			}
			List<Map> payInfoList = new ArrayList<Map>();
			for(StagespayEntity stagespayEntity : stagespayList){
				Map payInfo = new HashMap();
				payInfo.put("capitalDate", stagespayEntity.getConDate());  //出资时间
				payInfo.put("paySize", stagespayEntity.getCurActConAm()); //出资额
				payInfo.put("capitalMethod", stagespayEntity.getConForm()); //出资方式 
				payInfo.put("techStockFlag", stagespayEntity.getIsTechStock()); //是否实施股权激励
				payInfo.put("stagespayId", stagespayEntity.getStagespayId()); //stagespayId 唯一标示
				payInfoList.add(payInfo);
			}
			try {
				RSBLL.getstance().getEnterprisePartnerPayService().savePartnerPayList(roleId, dataRoleType, payInfoList);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			
		}
		if(gudongInfo){
			return "获取股东信息失败";
		}
		
		//主要人员信息列表
		//保存董事会和监事会标示
		BoardSupedEntity boardSupedSetting = null;
		try {
			boardSupedSetting = SetupQuery.getBoardSupedSetting(sessionId, cpReqDetail.getEntId());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		if(null == boardSupedSetting){
			return "获取董事会监事会标示失败!";
		}
		if(StringUtils.equals(boardSupedSetting.getIsBoard(), "1")){
			mapAll.put("isDongShiMeeting", "1"); //有董事会
		}else if(StringUtils.equals(boardSupedSetting.getIsBoard(), "0")){
			mapAll.put("isDongShiMeeting", "2"); //无董事会
		}

		if(StringUtils.equals(boardSupedSetting.getIsSuped(), "1")){
			mapAll.put("isJianShiMeeting", "1"); //有监事会
		}else if(StringUtils.equals(boardSupedSetting.getIsSuped(), "0")){
			mapAll.put("isJianShiMeeting", "2"); //无监事会
		}
		try {
			RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
		} catch (Exception e2) {
			e2.printStackTrace();
			return "保存董事会监事会状态失败!";
		}
		mapAll.clear();
		
		//主要人员信息
		Map<String,String> mainPersonMap = null;
		Map<String,String> mainPersonExtMap = null;
		QueryPageEntity<EntMemberEntity> entMemberList = null;
		try {
			for(int position = 1; position < 4; position++){
				entMemberList = null;
				//查询董事、监事、经理
				entMemberList = SetupQuery.getEntMemberList(sessionId, String.valueOf(position), cpReqDetail.getEntId(), 20);
				if(null == entMemberList || entMemberList.getRows().isEmpty()){
					break;
				}
				for(EntMemberEntity entmemberE : entMemberList.getRows()){
					System.out.println(JSON.toJSONString(entmemberE)+"==============");
					MemberEntity entMember = SetupQuery.getEntMember(sessionId, entmemberE.getEntmemberId(), entmemberE.getPosition());
					mainPersonMap = new HashMap<String, String>();
					mainPersonMap.put("name", entMember.getName());  //姓名
					mainPersonMap.put("idNum", entMember.getCerNo()); //证件号码
					mainPersonMap.put("idAddress", entMember.getHouseAddOther());   //住所 
					Long roleId = RSBLL.getstance().getEnterprisePersonService().saveEnterprisePersonEntity(enterpriseId, mainPersonMap, null);   //保存主要人员
					//如果是法人
					if(StringUtils.equals(entmemberE.getLeRepSign(), "1")){
						mainPersonExtMap = new HashMap<String, String>();
						mainPersonExtMap.put("legalPersonMappingRoleType", positionType.get(String.valueOf(position)));
						RSBLL.getstance().getEnterpriseRoleRelationService().saveRoleRelationEntity(Long.parseLong(enterpriseId), "legalPerson", roleId, mainPersonExtMap, null); //保存法人
					}
					RSBLL.getstance().getEnterpriseRoleRelationService().saveRoleRelationEntity(Long.parseLong(enterpriseId), positionType.get(String.valueOf(position)), roleId, null, null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "保存主要人员信息出错!";
		}
		if(entMemberList == null || entMemberList.getRows().isEmpty()){
			return "获取主要人员信息失败!";
		}
		
		
		//企业补充信息
		//--联系信息 企业联系人
		try {
			ContactEntity cpContact = SetupQuery.getCpContact(sessionId, cpReqDetail.getEntId(),"0");
			mapAll.put("addresseeEmail", cpContact.getEmail()); 		   //email
			mapAll.put("fixedPhone", cpContact.getContactTel());  //固定电话
			mapAll.put("zipCode", cpContact.getContactPostalCode());				  //邮政编码
			RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
			Map<String,String> lxrPersionMap  = new HashMap<String, String>();
			lxrPersionMap.put("name", cpContact.getContactName());  //姓名
			lxrPersionMap.put("idNum", cpContact.getContactCerNo()); //证件号码
			lxrPersionMap.put("idType", cpContact.getContactCerType()); //证件类型;
			lxrPersionMap.put("phoneNum", cpContact.getContactMobile()); //电话
			Long roleId = RSBLL.getstance().getEnterprisePersonService().saveEnterprisePersonEntity(enterpriseId, lxrPersionMap, null);   //保存主要人员
			RSBLL.getstance().getEnterpriseRoleRelationService().saveRoleRelationEntity(Long.parseLong(enterpriseId), "contacts", roleId, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return "保存企业联系人失败";
		}
		//--联系信息 财务联系人
		try {
			ContactEntity cpContact = SetupQuery.getCpContact(sessionId, cpReqDetail.getEntId(),"2");
			Map<String,String> cwPersionMap  = new HashMap<String, String>();
			cwPersionMap.put("name", cpContact.getContactName());  //姓名
			cwPersionMap.put("idNum", cpContact.getContactCerNo()); //证件号码
			cwPersionMap.put("idType", cpContact.getContactCerType()); //证件类型;
			cwPersionMap.put("phoneNum", cpContact.getContactMobile()); //电话
			Long roleId = RSBLL.getstance().getEnterprisePersonService().saveEnterprisePersonEntity(enterpriseId, cwPersionMap, null);   //保存主要人员
			RSBLL.getstance().getEnterpriseRoleRelationService().saveRoleRelationEntity(Long.parseLong(enterpriseId), "finance", roleId, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return "保存财务联系人失败";
		}

		//---其它信息
		OtherInfoEntity cpOtherInfo = null;
		try {
			cpOtherInfo = SetupQuery.getCpOtherInfo(sessionId, cpReqDetail.getEntId());
		} catch (WebblackException e1) {
			e1.printStackTrace();
		}
		if(null == cpOtherInfo){
			return "获取补充信息-其它信息失败!";
		}
		mapAll.put("staffTotalNum", cpOtherInfo.getMembers()); //总人数
		mapAll.put("staffFemaleNum", cpOtherInfo.getFemaleNumber()); //女性人数
		mapAll.put("staffLocalNum", cpOtherInfo.getIncityNumber()); //本地人数
		mapAll.put("staffOutNum", cpOtherInfo.getOthercityNumber());  //外地人数
		mapAll.put("artiOfCorpCnt", cpOtherInfo.getArtiOfCorpCnt());  //公司章程
		try {
			RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
		} catch (Exception e) {
			e.printStackTrace();
			return "保存设立补充信息-其它信息失败";
		}
		mapAll.clear();
		
		//企业补充信息
		
		List<DownloadDetailEntity> queryReqDownloadList = null;
		try {
			//文件信息
			queryReqDownloadList = SetupQuery.queryReqDownloadList(sessionId, cpReqDetail.getReqId(), cpReqDetail.getCatId(), cpReqDetail.getEntId(), "1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null == queryReqDownloadList || queryReqDownloadList.isEmpty()){
			return "获取下载网登材料失败";
		}
		
		Map<String,String> queryMap = new HashMap<String, String>();
		try {
			for(DownloadDetailEntity downloadDetailEntity:queryReqDownloadList){
				String reqdocId = downloadDetailEntity.getReqdocId();
				String regFileKey = WebLogicUtils.getRegFileKeyByRegDocId(reqdocId);
				byte[] downloadFile = SetupQuery.queryRegDownloadFile(sessionId, cpReqDetail.getReqId(), cpReqDetail.getCatId(), cpReqDetail.getEntId(), reqdocId, downloadDetailEntity.getDocName());
				LvFileEntity lvFileEntity = new LvFileEntity();
				lvFileEntity.setFileName(downloadDetailEntity.getDocName() + ".PDF");
				lvFileEntity.setCompanyId(enterpriseId);
				String objectKey = AliOSSUtils.pubObject(downloadFile);
				lvFileEntity.setAliyunBucket(AliOSSUtils.bucketName);
				lvFileEntity.setAliyunFileKey(objectKey);
				long fileId = RSBLL.getstance().getFileService().insert(lvFileEntity);
				queryMap.put(regFileKey, fileId + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "下载文件失败!";
		}
		try {
			RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, queryMap, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("保存下载网登材料失败,企业库id:"+enterpriseId);
			return "保存设立文件信息失败";
		}
		
		// 更新状态
		mapAll.put("setupGovStatus", setupGovStatus_Twenty);
		mapAll.put("setupStatus", "1"); //设立状态审核通过
		try {
			RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, setupReason, mapAll, null);
		} catch (Exception e) {
			e.printStackTrace();
			return "更新设立审核通过状态失败!";
		}
		
		return "设立通过";
	}
	
//	//添加企业库数据
//	public void updateEnterprice(String  enterpriseId, Map map) throws Exception{
//		RSBLL.getstance().getEnterpriseBusinessDataService().saveEnterpriseBusinessData(enterpriseId, "2003", map, null);
//		RSBLL.getstance().getEnterprisePersonService().saveEnterprisePersonEntity(arg0, arg1, arg2)
//	}
}
