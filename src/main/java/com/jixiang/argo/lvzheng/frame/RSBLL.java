 package com.jixiang.argo.lvzheng.frame;


import com.jx.argo.ArgoTool;
import com.jx.blackface.gaea.sell.contract.ILvzProductCateService;
import com.jx.blackface.gaea.sell.contract.ILvzProductService;
import com.jx.blackface.gaea.sell.contract.ILvzSellProductService;
import com.jx.blackface.gaea.sell.contract.ISellCommonService;
import com.jx.blackface.messagecenter.core.contract.ICallService;
import com.jx.blackface.messagecenter.core.contract.ICustomerAgentService;
import com.jx.service.enterprise.contract.ILvEnterpriseAddressService;
import com.jx.service.enterprise.contract.ILvEnterpriseAddressTemplateService;
import com.jx.service.enterprise.contract.ILvEnterpriseBusinessDataService;
import com.jx.service.enterprise.contract.ILvEnterpriseBusinessService;
import com.jx.service.enterprise.contract.ILvEnterpriseDicDataService;
import com.jx.service.enterprise.contract.ILvEnterpriseOperatingRangeService;
import com.jx.service.enterprise.contract.ILvEnterprisePartnerPayService;
import com.jx.service.enterprise.contract.ILvEnterprisePersonService;
import com.jx.service.enterprise.contract.ILvEnterpriseRoleRelationService;
import com.jx.service.enterprise.contract.ILvEnterpriseService;
import com.jx.service.indexing.contract.TestService;
import com.jx.service.messagecenter.contract.IMoblieSmsService;
import com.jx.service.messagecenter.contract.IWeixinActionService;
//import com.jx.service.indexing.contract.TestService;
import com.jx.service.newcore.contract.IActionsService;
import com.jx.service.newcore.contract.IAreasService;
import com.jx.service.newcore.contract.IBusinessLibaryService;
import com.jx.service.newcore.contract.IBusinessLogsService;
import com.jx.service.newcore.contract.ICommentService;
import com.jx.service.newcore.contract.ICouponsService;
import com.jx.service.newcore.contract.IEmployersService;
import com.jx.service.newcore.contract.IFileattachmentService;
import com.jx.service.newcore.contract.IFinanceService;
import com.jx.service.newcore.contract.IFollowupService;
import com.jx.service.newcore.contract.IFriendLinkService;
import com.jx.service.newcore.contract.IIndustrylService;
import com.jx.service.newcore.contract.IKehinfosService;
import com.jx.service.newcore.contract.ILoginService;
import com.jx.service.newcore.contract.INewCoreService;
import com.jx.service.newcore.contract.IOrderService;
import com.jx.service.newcore.contract.IOrderprocessService;
import com.jx.service.newcore.contract.IPayProcessService;
import com.jx.service.newcore.contract.IPayRecordeService;
import com.jx.service.newcore.contract.IPricesService;
import com.jx.service.newcore.contract.IProductCategoryService;
import com.jx.service.newcore.contract.IProductNewService;
import com.jx.service.newcore.contract.IProductPropertiesService;
import com.jx.service.newcore.contract.IProductService;
import com.jx.service.newcore.contract.IReginAddressService;
import com.jx.service.newcore.contract.IReginAddressTempleteService;
import com.jx.service.newcore.contract.IServiceprocessService;
import com.jx.service.newcore.contract.ISorderChildrenService;
import com.jx.service.newcore.contract.ISorderService;
import com.jx.service.newcore.contract.ISorderSuperService;
import com.jx.service.newcore.contract.ISpendRecordService;
import com.jx.service.newcore.contract.ISuggestFollowService;
import com.jx.service.newcore.contract.ISuggestService;
import com.jx.service.newcore.contract.ITaskService;
import com.jx.service.newcore.contract.IUserCouponsService;
import com.jx.service.newcore.contract.IVisitService;
import com.jx.service.union.contract.UserService;
import com.jx.service.workflow.contract.ILvCompanyService;
import com.jx.service.workflow.contract.ILvDeployService;
import com.jx.service.workflow.contract.ILvDicService;
import com.jx.service.workflow.contract.ILvFileService;
import com.jx.service.workflow.contract.ILvHistoryService;
import com.jx.service.workflow.contract.ILvProcessService;
import com.jx.service.workflow.contract.ILvTaskQueryService;
import com.jx.service.workflow.contract.ILvTaskService;
import com.jx.service.workflow.contract.ILvWorkDayService;
import com.jx.spat.gaea.client.GaeaConst;
import com.jx.spat.gaea.client.GaeaInit;
import com.jx.spat.gaea.client.proxy.builder.ProxyFactory;

public class RSBLL {

	private static RSBLL newstance = null;
	
	private static Object lock = new Object();
	
	// activiti
	private final String SVname = "workflow"; 
	
	private final String SVname_enterprise = "enterprise"; 
	//商品库
	private final String SVname_sellcore = "sellcore";
	
	
	static{
		String url = ArgoTool.getConfigFolder()+ArgoTool.getNamespace()+"/gaea.config";
		GaeaInit.init(url);
		System.out.println("**********" + GaeaConst.CONFIG_PATH);
	}
	public Object getNewCoreService(){
		String url = "tcp://jxcore/OrderCoreService";
		INewCoreService ics = ProxyFactory.create(Object.class, url);
		return ics;
	}
	
	//返会任务的service
		public ITaskService getFieldTaskService(){
			String url = "tcp://jxcore/TaskService";
			ITaskService ics = ProxyFactory.create(ITaskService.class, url);
			return ics;
		}
	
	public IActionsService getActionService(){
		String url = "tcp://jxcore/ActionsService";
		return ProxyFactory.create(IActionsService.class, url);
	}
	public IPricesService getPricesService(){
		String url = "tcp://jxcore/PricesService";
		return ProxyFactory.create(IPricesService.class, url);
	}
	//返回订单表的service
	public ISorderService getSorderService(){
		String url = "tcp://jxcore/SorderService";
		ISorderService ics = ProxyFactory.create(ISorderService.class, url);
		return ics;
	}
	public ICallService getICallService() {
		return ProxyFactory.create(ICallService.class, "tcp://jxmessage/CallService");
	}
	public ICustomerAgentService getICustomerAgentService() {
		return ProxyFactory.create(ICustomerAgentService.class, "tcp://jxmessage/CustomerAgentService");
	}
	//返回子订单表的service
	public ISorderChildrenService getSorderChildrenService(){
		String url = "tcp://jxcore/SorderChildrenService";
		ISorderChildrenService ics = ProxyFactory.create(ISorderChildrenService.class, url);
		return ics;
	}
	//返回超级订单表的service
	public ISorderSuperService getSorderSuperService(){
		String url = "tcp://jxcore/SorderSuperService";
		ISorderSuperService ics = ProxyFactory.create(ISorderSuperService.class, url);
		return ics;
	}
	//返回用户表的service
	public ILoginService getLoginService(){
		String url = "tcp://jxcore/LoginService";
		ILoginService ics = ProxyFactory.create(ILoginService.class, url);
		return ics;
	}
	//订单操作日志
	public IOrderprocessService getOrderprocessService(){
		return ProxyFactory.create(IOrderprocessService.class, "tcp://jxcore/OrderprocessService");
	}
	//返回商品service
	public IProductService getProductService(){
		String url = "tcp://jxcore/ProductService";
		IProductService ics = ProxyFactory.create(IProductService.class, url);
		return ics;
	}
	//返回product_category表的service
	public IProductCategoryService getProductCategoryService(){
		String url = "tcp://jxcore/ProductCategoryService";
		IProductCategoryService ics = ProxyFactory.create(IProductCategoryService.class, url);
		return ics;
	}
	//返回属性表的service
	public IProductPropertiesService getProductPropertiesService(){
		String url = "tcp://jxcore/ProductPropertiesService";
		IProductPropertiesService ics = ProxyFactory.create(IProductPropertiesService.class, url);
		return ics;
	}
	//返回雇员表的service
	public IEmployersService getEmployersService(){
		String url = "tcp://jxcore/EmployersService";
		IEmployersService ics = ProxyFactory.create(IEmployersService.class, url);
		return ics;
	}
	//返回跟进表的service
	public IFollowupService getFollowupService(){
		String url = "tcp://jxcore/FollowupService";
		IFollowupService ics = ProxyFactory.create(IFollowupService.class, url);
		return ics;
	}
	
	//返回地区SERVICE
	public IAreasService getAreasService(){
		String url = "tcp://jxcore/AreasService";
		IAreasService ics = ProxyFactory.create(IAreasService.class, url);
		return ics;
	}
	

	//返回支付的properties
	public IFinanceService getFinanceService(){
		String url = "tcp://jxcore/FinanceService";
		IFinanceService ics = ProxyFactory.create(IFinanceService.class, url);
		return ics;
	}
	
	public IWeixinActionService getWeixinActionService(){
		return ProxyFactory.create(IWeixinActionService.class, "tcp://jxmessage/WeixinActionService");
	}
	
	//返回服务的借口
	public IServiceprocessService getServiceprocessService(){
		String url = "tcp://jxcore/ServiceprocessService";
		IServiceprocessService ics = ProxyFactory.create(IServiceprocessService.class, url);
		return ics;
	}
	public IPayProcessService getPayprocessService(){
		return ProxyFactory.create(IPayProcessService.class, "tcp://jxcore/PayProcessService");
	}
	

	/**
	 * 商品操作对象
	 * @return
	 */
	public IProductNewService getProductNewService(){
		String url ="tcp://jxcore/ProductNewService";
		IProductNewService ipe =  ProxyFactory.create(IProductNewService.class, url);
		return ipe;
	}
	
	public TestService getTestService(){
		String url ="tcp://indexservice/TestServiceImp";
		TestService testService =  ProxyFactory.create(TestService.class, url);
		return testService;
	}
	
	public IOrderService getOrderService(){
		String url ="tcp://jxcore/OrderService";
		IOrderService ioe =  ProxyFactory.create(IOrderService.class, url);
		return ioe;
	}
	
	public IVisitService getVisitService(){
		String url ="tcp://jxcore/VisitService";
		IVisitService ioe =  ProxyFactory.create(IVisitService.class, url);
		return ioe;
	}
	
	public ICommentService getCommentService(){
		String url = "tcp://jxcore/CommentService";
		return ProxyFactory.create(ICommentService.class, url);
	}
	//得到优惠券表接口
	public ICouponsService getCouponsService(){
		return ProxyFactory.create(ICouponsService.class, "tcp://jxcore/CouponsService");
	}
	//得到用户优惠券表接口
	public IUserCouponsService getUserCouponsService(){
		return ProxyFactory.create(IUserCouponsService.class, "tcp://jxcore/UserCouponsService");
	}
	//友情链接
	public IFriendLinkService getFriendLinkService(){
		String url = "tcp://jxcore/FriendLinkService";
		return ProxyFactory.create(IFriendLinkService.class, url);
	}
	//企业库主表
	public IBusinessLibaryService getBusinessLibaryService(){
		String url = "tcp://jxcore/BusinessLibaryService";
		return ProxyFactory.create(IBusinessLibaryService.class, url);
	}
	//企业库主表日志
	public IBusinessLogsService getBusinessLogsService(){
		String url = "tcp://jxcore/BusinessLogsService";
		return ProxyFactory.create(IBusinessLogsService.class, url);
	}
	//相关用户信息
	public IKehinfosService getKehinfosService(){
		String url = "tcp://jxcore/KehinfosService";
		return ProxyFactory.create(IKehinfosService.class, url);
	}
	//注册地址
	public IReginAddressService getReginAddressService(){
		String url = "tcp://jxcore/ReginAddressService";
		return ProxyFactory.create(IReginAddressService.class, url);
	}
	//附件上传类
	public IFileattachmentService getFileattachmentService(){
		return ProxyFactory.create(IFileattachmentService.class, "tcp://jxcore/FileattachmentService");
	}
	//代理地址模板
	public IReginAddressTempleteService getReginAddressTempleteService(){
		return ProxyFactory.create(IReginAddressTempleteService.class, "tcp://jxcore/ReginAddressTempleteService");
	}
	//企业业务
	public IIndustrylService getIndustrylServiceService(){
		return ProxyFactory.create(IIndustrylService.class, "tcp://jxcore/IndustrylService");
	}
	public static RSBLL getstance(){
		if(newstance == null){
			synchronized (lock) {
				if(newstance == null){
					newstance = new RSBLL();
				}
			}
		}
		return newstance;
	}
	
	public IWeixinActionService getIWeixinActionService(){
		return ProxyFactory.create(IWeixinActionService.class, "tcp://jxmessage/WeixinActionService");
	}
	public IPayRecordeService getPayRecordeService(){
		return ProxyFactory.create(IPayRecordeService.class, "tcp://jxcore/PayRecordeService");
	}
	//得到suggest服务
	public ISuggestService getSuggestService(){
		String url = "tcp://jxcore/SuggestService";
		return ProxyFactory.create(ISuggestService.class, url);
	}
	
	
	//得到suggestFOLLOW服务
	public ISuggestFollowService getSuggestFollowService(){
		String url = "tcp://jxcore/SuggestFollowService";
		return ProxyFactory.create(ISuggestFollowService.class, url);
	}
	public ISpendRecordService getSpendRecordService(){
		return ProxyFactory.create(ISpendRecordService.class, "tcp://jxcore/SpendRecordService");
	}

	// activiti-任务操作
	public ILvTaskService getTaskService(){
		final String url = "tcp://" + SVname + "/LvTaskService";
		ILvTaskService iLvTaskService = ProxyFactory.create(ILvTaskService.class, url);
		return iLvTaskService;
	}
	// activiti-任务查询
	public ILvTaskQueryService getTaskQueryService(){
		final String url = "tcp://" + SVname + "/LvTaskQueryService";
		ILvTaskQueryService iLvTaskQueryService = ProxyFactory.create(ILvTaskQueryService.class, url);
		return iLvTaskQueryService;
	}
	// activiti-任务历史查询
	public ILvHistoryService getHistoryService(){
		final String url = "tcp://" + SVname + "/LvHistoryService";
		ILvHistoryService iLvHistoryService = ProxyFactory.create(ILvHistoryService.class, url);
		return iLvHistoryService;
	}
	// activiti-服务流程
	public ILvProcessService getProcessService(){
		final String url = "tcp://" + SVname + "/LvProcessService";
		ILvProcessService iLvProcessService = ProxyFactory.create(ILvProcessService.class, url);
		return iLvProcessService;
	}
	
	// activiti-服务流程
	public ILvFileService getFileService(){
		final String url = "tcp://" + SVname + "/LvFileService";
		ILvFileService iLvFileService = ProxyFactory.create(ILvFileService.class, url);
		return iLvFileService;
	}
	
	// activiti-字典数据
	public ILvDicService getDicService(){
		final String url = "tcp://" + SVname + "/LvDicService";
		ILvDicService iLvDicService = ProxyFactory.create(ILvDicService.class, url);
		return iLvDicService;
	}
	
	//手机短信服务
	public IMoblieSmsService getMoblieSmsService(){
		return ProxyFactory.create(IMoblieSmsService.class, "tcp://jxmessage/MoblieSmsService");
	}
	
	// 权限 - 查询用户服务
	public UserService getUserService() {
		String url = "tcp://union/UserService";
		UserService ics = ProxyFactory.create(UserService.class, url);
		return ics;
	}
	
	// 流程 - 发布接口
	public ILvDeployService getLvDeployService() {
		String url = "tcp://" + SVname + "/LvDeployService";
		ILvDeployService ics = ProxyFactory.create(ILvDeployService.class, url);
		return ics;
	}
	//流程 -接口
	public ILvCompanyService getLvCompanyService(){
		String url = "tcp://" + SVname + "/LvCompanyService";
		ILvCompanyService ics = ProxyFactory.create(ILvCompanyService.class, url);
		return ics;
	}
	
	//返回订单表的service
	public ILvWorkDayService getWorkDayService(){
		String url = "tcp://" + SVname + "/LvWorkDayService";
		ILvWorkDayService ics = ProxyFactory.create(ILvWorkDayService.class, url);
		return ics;
	}
	//企业库开始
	public ILvEnterpriseBusinessService getEnterpriseBusinessService(){
		String url = "tcp://"+SVname_enterprise+"/LvEnterpriseBusinessService";
		return ProxyFactory.create(ILvEnterpriseBusinessService.class, url);
	}
	
	public ILvEnterpriseBusinessDataService getEnterpriseBusinessDataService(){
		String url = "tcp://"+SVname_enterprise+"/LvEnterpriseBusinessDataService";
		return ProxyFactory.create(ILvEnterpriseBusinessDataService.class, url);
	}
	
	public ILvEnterpriseDicDataService getEnterpriseDicDataService(){
		String url = "tcp://"+SVname_enterprise+"/LvEnterpriseDicDataService";
		return ProxyFactory.create(ILvEnterpriseDicDataService.class, url);
	}
	
	public ILvEnterpriseService getEnterpriseService(){
		String url = "tcp://"+SVname_enterprise+"/LvEnterpriseService";
		return ProxyFactory.create(ILvEnterpriseService.class, url);
	}
	
	public ILvEnterpriseRoleRelationService getEnterpriseRoleRelationService(){
		String url = "tcp://"+SVname_enterprise+"/LvEnterpriseRoleRelationService";
		return ProxyFactory.create(ILvEnterpriseRoleRelationService.class, url);
	}
	
	public ILvEnterprisePersonService getEnterprisePersonService(){
		String url = "tcp://"+SVname_enterprise+"/LvEnterprisePersonService";
		return ProxyFactory.create(ILvEnterprisePersonService.class, url);
	}
	
	public ILvEnterpriseOperatingRangeService getEnterpriseOperatingRangeService(){
		String url = "tcp://"+SVname_enterprise+"/LvEnterpriseOperatingRangeService";
		return ProxyFactory.create(ILvEnterpriseOperatingRangeService.class, url);
	}
	
	public ILvEnterpriseAddressTemplateService getEnterpriseAddressTemplateService(){
		String url = "tcp://"+SVname_enterprise+"/LvEnterpriseAddressTemplateService";
		return ProxyFactory.create(ILvEnterpriseAddressTemplateService.class, url);
	}
	
	public ILvEnterpriseAddressService getEnterpriseAddressService(){
		String url = "tcp://"+SVname_enterprise+"/LvEnterpriseAddressService";
		return ProxyFactory.create(ILvEnterpriseAddressService.class, url);
	}
	/**
	 * ep - enterprise pay
	 * @return
	 */
	public ILvEnterprisePartnerPayService getEnterprisePartnerPayService(){
		return ProxyFactory.create(ILvEnterprisePartnerPayService.class , "tcp://"+SVname_enterprise+"/LvEnterprisePartnerPayService");
	}
	//企业库结束
	
	//商品库服务开始
	public ILvzProductService getLvzProductService(){
		String url = "tcp://"+SVname_sellcore+"/LvzProductService";
		ILvzProductService iLvzProductService = ProxyFactory.create(ILvzProductService.class, url);
		return iLvzProductService;
	}
	public ILvzSellProductService getLvzSellProductService(){
		String url = "tcp://"+SVname_sellcore+"/LvzSellProductService";
		ILvzSellProductService lvzSellProductService = ProxyFactory.create(ILvzSellProductService.class, url);
		return lvzSellProductService;
	}
	
	public ILvzProductCateService getLvzProductCateService(){
		String url = "tcp://"+SVname_sellcore+"/LvzProductCateService";
		ILvzProductCateService iLvzProductCateService = ProxyFactory.create(ILvzProductCateService.class, url);
		return iLvzProductCateService;
	}
	public ISellCommonService getSellCommonService(){
		String url = "tcp://"+SVname_sellcore+"/SellCommonService";
		ISellCommonService isellcommonService = ProxyFactory.create(ISellCommonService.class, url);
		return isellcommonService;
	}
	//商品库服务结束
}
