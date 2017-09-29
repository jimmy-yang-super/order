package com.jixiang.argo.lvzheng.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class KVMap {
	
	/**
	 *  1、未指派
		2、正在处理
		3、已通过
		4、未通过
	 */
	public static final String task_weizhipai= "1";
	public static final String task_chulizhong= "2";
	public static final String task_yitongguo= "3";
	public static final String task_weitongguo= "4";


	/**
	 *  1、未处理
		2、正在处理
		3、已处理
	 */
	public static final String weichuli= "1";
	public static final String zhengzaichuli= "2";
	public static final String yichuli= "3";

	
	/**
	 * 投诉状态初始化
	 */
	public static Map<String,String> ts_status = new LinkedHashMap<String,String>();
	
	static{
			
		ts_status.put("1", "未处理");
		ts_status.put("2", "正在处理");
		ts_status.put("3", "已处理");
		
	}
	
	/**
	 * 投诉渠道初始化
	 */
	public static Map<String,String> qudao = new LinkedHashMap<String,String>();
	
	static{
			
			qudao.put("1", "400投诉电话");
			qudao.put("2", "微信公众号");
			qudao.put("3", "客服反馈");
			qudao.put("4", "回访反馈");
			qudao.put("5", "其他");
			qudao.put("999", "微信菜单");
		
	}
	
	/**
	 * 外勤服务类型初始化
	 */
	public static Map<String,String> servicetype = new LinkedHashMap<String,String>();
	
	static{
			
		servicetype.put("1", "公司注册");
		servicetype.put("2", "公司变更");
		servicetype.put("3", "商标注册");
		servicetype.put("4", "代理记账");
		
	}
	
	
	/**
	 * 外勤地址初始化
	 */
	public static Map<String,String> wq_address = new LinkedHashMap<String,String>();
	
	static{
		
		
		wq_address.put("工商局","工商局"     );
		wq_address.put("国税所","国税所"     );
		wq_address.put("地税所","地税所"     );
		wq_address.put("社保中心","社保中心"   );
		wq_address.put("公积金中心","公积金中心" ); 
		wq_address.put("招商银行","招商银行"   );
		wq_address.put("民生银行","民生银行"   );
		wq_address.put("商标局","商标局"     );
		wq_address.put("版权局","版权局"     );
		wq_address.put("其他","其他"       );
		
	}
	
	
	
	/**
	 * 投诉类型初始化
	 */
	public static Map<String,String> ts_types = new LinkedHashMap<String,String>();
	
	static{
			
			ts_types.put("1", "法律顾问回电不及时");
			ts_types.put("2", "服务周期过长");
			ts_types.put("3", "法律顾问服务态度/专业度");
			ts_types.put("4", "材料的整理、寄送和沟通问题");
			ts_types.put("5", "在线下单流程不清晰、不好用");
			ts_types.put("6", "其他");
		
	}
	public static Map careemap = new HashMap();
	//1，客服。2，法律顾问 3，劳务，4，管理员，5、财务，6、运营，7、法律助理
	//管理员／客服／法律顾问／法律助理／运营／财务
	static{
		careemap.put("1", "客服");
		careemap.put("2", "法律顾问");
		careemap.put("3", "劳务");
		careemap.put("4", "管理员");
		careemap.put("5", "财物");
		careemap.put("6", "运营");
		careemap.put("7", "法律助理");
		
	}
	
	public static Map<Integer,String> followmap = new HashMap<Integer,String>();
	static{
		followmap.put(1, "低");
		followmap.put(2, "中");
		followmap.put(3, "高");
	}
	public static Map orderType = new HashMap();
	static{
		orderType.put("qzdd", "qzdd"); //潜在订单
//		orderType.put("", "");
//		orderType.put("", "");
//		orderType.put("", "");
	}
	
	public static Map orderstatemap = new HashMap();
	static{
		orderstatemap.put("1", "服务中");
		orderstatemap.put("2", "订单取消");
		orderstatemap.put("3", "派单成功");
		orderstatemap.put("4", "服务完成");
		orderstatemap.put("7", "预约成功");
		orderstatemap.put("10", "订单完结");
		orderstatemap.put("11", "等待二次派单");
	}
	public static Map orderPaystatemap =new HashMap();
	static{
		orderPaystatemap.put("0", "未付款");
		orderPaystatemap.put("1", "预付款");
		orderPaystatemap.put("2", "完款");
	}
	public static Map orderServerstatemap =new HashMap();
	static{
		orderServerstatemap.put("0", "未服务");
		orderServerstatemap.put("1", "服务中");
		orderServerstatemap.put("2", "服务完成");
	}
	public static Map orderSignstatemap = new HashMap();
	static{
		orderSignstatemap.put("0", "未签");
		orderSignstatemap.put("1", "已签");
	}
	
	//子单状态
	public static Map orderChildstatemap = new HashMap();
	static{
		orderChildstatemap.put("0", "未服务");
		orderChildstatemap.put("1", "服务中");
		orderChildstatemap.put("2", "服务完成");
	}
	public static Map<String,String> channelMap = new HashMap<String, String>();
	static{
		channelMap.put("1","微信");
		channelMap.put("2","58同城");
		channelMap.put("3","客户推荐");
		channelMap.put("4","赶集网");
		channelMap.put("5","公司官网");
		channelMap.put("6","同事推荐");
		channelMap.put("7","复购客户");
		channelMap.put("8","搜索引擎");
		channelMap.put("9","大客户渠道");
		channelMap.put("10","地面推广");
		channelMap.put("11","线下广告");
		channelMap.put("12","线下活动");
     	channelMap.put("14","营销邮件");
		channelMap.put("15","营销短信"); 
		channelMap.put("16","360搜索");
		channelMap.put("17","58合作客户");
		channelMap.put("13","其他");
		channelMap.put("18","孵化器");
		channelMap.put("19","诚信通");
		channelMap.put("20","渠道合作-优办");
		channelMap.put("21","渠道合作-点点租");
		channelMap.put("22","渠道合作-梦想加");
		channelMap.put("23","渠道合作-其他");
		channelMap.put("24","渠道合作-汉美");
		channelMap.put("25","平台成交");
		channelMap.put("26","平台线索");
	}
 
	public static Map<String,String> companyMap = new HashMap<String, String>();
	static{
		companyMap.put("小规模纳税人", "30");
		companyMap.put("一般纳税人", "31");
	}
	public static Map orderForm =new HashMap();
	static{
		orderForm.put("1", "微信");
		orderForm.put("2", "官网");
		orderForm.put("3", "后台");
		orderForm.put("4", "58合作订单");
		orderForm.put("5", "汉美");
		orderForm.put("6", "平台成交");
	}
	
	//订单支付状态
	public static Map orderPayState = new HashMap();
	static{
		orderPayState.put("0", "未支付");
		orderPayState.put("1", "预付");
		orderPayState.put("2", "完款");
		orderPayState.put("3", "平台支付");
		orderPayState.put("4", "平台完款");
	}
	public static Map payProcess = new HashMap();
	static{
		payProcess.put("1", "收款");
		payProcess.put("2", "催款");
	}
	public static Map payType =new HashMap();
	static{
		payType.put("0", "0元订单");
		payType.put("1", "现金");
		payType.put("2", "转账");
		payType.put("3", "刷卡");
		payType.put("4", "微信");
		payType.put("5", "支付宝");
		payType.put("7", "平台转账");
		//付款方式（1，现金，2，转账，3，刷卡，4，微信 5,支付宝）
	}
	
	public static Map jzstr = new HashMap();
	static{
		jzstr.put(1, "代理记账3个月");
		jzstr.put(2, "代理记账6个月");
		jzstr.put(3, "代理记账1年");
	}
	
	public static Map hymap = new HashMap();
	static{
		hymap.put(1, "互联网-软件");
		hymap.put(2, "信息传媒");
		hymap.put(3, "金融");
		hymap.put(4, "服务业");
		hymap.put(5, "教育");
		hymap.put(6, "医疗服务");
		hymap.put(7, "艺术娱乐");
		hymap.put(8, "制造加工");
		hymap.put(9, "地产建筑");
		hymap.put(10, "贸易零售");
		hymap.put(11, "公共服务");
		hymap.put(12, "开采冶金");
		hymap.put(13, "交通仓储");
		hymap.put(14, "农林牧渔");
		hymap.put(99, "其他");
	}
	
	public static Map serviemap = new HashMap();
	static{
		serviemap.put(1001, "工商注册");
		serviemap.put(1002, "代理地址");
		serviemap.put(1003, "银行开户");
		serviemap.put(1004, "税务报道");
		serviemap.put(1005, "社会工基金开户");
		serviemap.put(1006, "章程制定");
		serviemap.put(1007, "股权协议");
		serviemap.put(1008, "合同审核");
		serviemap.put(1009, "股权出质");
		serviemap.put(1010, "一般纳税人");
		serviemap.put(2001, "公司名称变更");
		serviemap.put(2002, "住所、法代变更");
		serviemap.put(2003, "注册资本、经营范围营业期限变更");
		serviemap.put(2004, "股东（名称）变更、公司登记备案");
		serviemap.put(3001, "一类商标");
		serviemap.put(3002, "二类商标");
		serviemap.put(3003, "三类商标");
		serviemap.put(3004, "四类商标");
		serviemap.put(4001, "代理记账");
	}
	
	public static Map servietopmap = new HashMap();
	static{
		serviemap.put(1001, "工商注册");
		serviemap.put(1002, "代理地址");
		serviemap.put(1003, "银行开户");
		serviemap.put(1004, "税务报道");
		serviemap.put(1005, "社会工基金开户");
		serviemap.put(1006, "章程制定");
		serviemap.put(1007, "股权协议");
		serviemap.put(1008, "合同审核");
		serviemap.put(1009, "股权出质");
		serviemap.put(1010, "一般纳税人");
		serviemap.put(2001, "公司名称变更");
		serviemap.put(2002, "住所、法代变更");
		serviemap.put(2003, "注册资本、经营范围营业期限变更");
		serviemap.put(2004, "股东（名称）变更、公司登记备案");
		serviemap.put(3001, "一类商标");
		serviemap.put(3002, "");
		serviemap.put(4001, "代理记账");
	}
	
	public static Map<String,String> operation = new HashMap<String, String>();
	static{
		operation.put("1101", "1001,1002,1006,1007,1008");
	}
	//是否显示切换城市
	public static Map<String,String> ischanggecity = new HashMap<String, String>();
	static{
		ischanggecity.put("1101", "1");
		ischanggecity.put("1105", "1");
		ischanggecity.put("2201", "1");
		ischanggecity.put("2202", "1");
	}
	public static Map<String,String> showpage = new HashMap<String, String>();
	static{
		showpage.put("1", "首页");
		showpage.put("2", "公司注册");
		showpage.put("3", "代理记账");
		showpage.put("4", "商标注册");
		showpage.put("5", "公司注册预约");
		showpage.put("6", "代理记账预约");
		showpage.put("7", "商标注册预约");
		showpage.put("8", "小微说首页");
		showpage.put("9", "小微说栏目页");
		//新加
		showpage.put("11", "新官网首页");
		showpage.put("12", "产品列表页");
		showpage.put("21", "资讯站首页");
		showpage.put("22", "文章一级分类");
		showpage.put("23", "文章二级分类");
		showpage.put("24", "问答首页");
		showpage.put("25", "问答一级分类");
		
	}
	public static Map<String,String> fileattmach = new HashMap<String, String>();
	static{
		fileattmach.put("dwgdyyzz","单位股东营业执照：");
		fileattmach.put("yingyzzzb","营业执照正本:");
		fileattmach.put("yingyzzfb","营业执照副本:");
			
		fileattmach.put("hemjt","核名截图：");
		fileattmach.put("gongszc","公司章程（多页):");
		fileattmach.put("shuiwdjzb","税务登记证正本:");
			
		fileattmach.put("shuiwdjfb","税务登记证副本：");
		fileattmach.put("zuzjgdmzb","组织机构代码证正本:");
		fileattmach.put("zuzjgdmfb","组织机构代码证副本:");
			
		fileattmach.put("tongjzzb","统计证正本：");
		fileattmach.put("tongjzfb","统计证副本:");
		fileattmach.put("fangwcqzm","房屋产权证明（多页）:");
			 
		fileattmach.put("zhuszm","住所证明：");
		fileattmach.put("qiylxrdjb","企业联系人登记表:");
		fileattmach.put("dongjjbxxb","登记基本信息表:");
		
		fileattmach.put("shijbgdzlyzp","实际办公地址楼宇照片:");
		fileattmach.put("shijbgdzmpzp","实际办公地址楼宇门牌照片:");
		fileattmach.put("shijbgdzlsnzp","实际办公地址楼宇室内照片:");
		
		fileattmach.put("jizbgrzsysqwtsfj", "集中办公区入驻事宜授权委托书:");
	}
}
