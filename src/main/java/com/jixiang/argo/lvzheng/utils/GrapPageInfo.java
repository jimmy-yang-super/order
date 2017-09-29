package com.jixiang.argo.lvzheng.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jixiang.argo.lvzheng.vo.ApproveGshmSearchVo;
import com.jixiang.argo.lvzheng.vo.BusinessCategoryVo;
import com.jixiang.argo.lvzheng.vo.IndustryDetail;
import com.jixiang.argo.lvzheng.vo.IndustrylVo;
import com.jixiang.argo.lvzheng.vo.RemoteUserVo;
import com.jixiang.argo.lvzheng.vo.SaveNameInfoVo;
import com.jx.service.newcore.entity.BusinessLibaryEntity;

/**
 * 抓取工商网站主营业务
 * 
 * @author wuyin
 *
 */
public class GrapPageInfo {

	private final static String remoteUrl =RemoteConstant.GSWZ_REMOTEURL;
	
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
        // String str ="postData=%7B%22data%22%3A%5B%7B%22name%22%3A%22ent_panel%22%2C%22vtype%22%3A%22formpanel%22%2C%22data%22%3A%7B%22nameId%22%3A%22f6f8b2c0cff740d186d06110b7a56d98%22%2C%22entType%22%3A%221100%22%2C%22preRegOrg%22%3A%22110000000%22%7D%7D%2C%7B%22name%22%3A%22ent_detail_panel%22%2C%22vtype%22%3A%22formpanel%22%2C%22data%22%3A%7B%22invType%22%3A%221%22%2C%22specialIndustry%22%3A%222%22%2C%22specialRegion%22%3A%22%22%2C%22sgzwFlag%22%3A%22%22%2C%22sswwFlag%22%3A%22%22%2C%22icSuboffice%22%3A%22%22%7D%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22invStr%22%2C%22data%22%3A%22%5B%7B%5C%22name_id%5C%22%3A%5C%22f6f8b2c0cff740d186d06110b7a56d98%5C%22%2C%5C%22inv_type%5C%22%3A%5C%2220%5C%22%2C%5C%22inv_name%5C%22%3A%5C%22%E5%BC%A0%E7%A3%8A%5C%22%2C%5C%22cer_no%5C%22%3A%5C%22%5C%22%2C%5C%22blic_no%5C%22%3A%5C%22%5C%22%7D%2C%7B%5C%22name_id%5C%22%3A%5C%22f6f8b2c0cff740d186d06110b7a56d98%5C%22%2C%5C%22inv_type%5C%22%3A%5C%2220%5C%22%2C%5C%22inv_name%5C%22%3A%5C%22%E5%BC%A0%E6%98%8E%5C%22%2C%5C%22cer_no%5C%22%3A%5C%22%5C%22%2C%5C%22blic_no%5C%22%3A%5C%22%5C%22%7D%5D%22%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22nameRemark%22%2C%22data%22%3A%22%22%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22busiType%22%2C%22data%22%3A%2210%22%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22nameType%22%2C%22data%22%3A%221%22%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22isNaturalAccName%22%2C%22data%22%3Afalse%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22naturalAcc%22%2C%22data%22%3A%22%22%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22withoutInd%22%2C%22data%22%3A%22%22%7D%5D%7D";
        String str = "postData=%7B%22data%22%3A%5B%7B%22name%22%3A%22ent_panel%22%2C%22vtype%22%3A%22formpanel%22%2C%22data%22%3A%7B%22nameId%22%3A%22c1f98c4483b94b549cd0a9e0771c1d14%22%2C%22entType%22%3A%221100%22%2C%22preRegOrg%22%3A%22110101000%22%7D%7D%2C%7B%22name%22%3A%22ent_detail_panel%22%2C%22vtype%22%3A%22formpanel%22%2C%22data%22%3A%7B%22invType%22%3A%221%22%2C%22specialIndustry%22%3A%220%22%2C%22specialRegion%22%3A%220%22%2C%22sgzwFlag%22%3A%222%22%2C%22sswwFlag%22%3A%22%22%2C%22icSuboffice%22%3A%22110101000%22%7D%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22invStr%22%2C%22data%22%3A%22%5B%7B%5C%22name_id%5C%22%3A%5C%22c1f98c4483b94b549cd0a9e0771c1d14%5C%22%2C%5C%22inv_type%5C%22%3A%5C%2290%5C%22%2C%5C%22inv_name%5C%22%3A%5C%22si%5C%22%2C%5C%22cer_no%5C%22%3A%5C%22%5C%22%2C%5C%22blic_no%5C%22%3A%5C%22%5C%22%7D%2C%7B%5C%22name_id%5C%22%3A%5C%22c1f98c4483b94b549cd0a9e0771c1d14%5C%22%2C%5C%22inv_type%5C%22%3A%5C%2290%5C%22%2C%5C%22inv_name%5C%22%3A%5C%22%E5%90%B4%E7%A3%8A%E5%8A%A8%5C%22%2C%5C%22cer_no%5C%22%3A%5C%22%5C%22%2C%5C%22blic_no%5C%22%3A%5C%22%5C%22%7D%5D%22%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22nameRemark%22%2C%22data%22%3A%22%22%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22busiType%22%2C%22data%22%3A%2210%22%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22nameType%22%2C%22data%22%3A%221%22%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22isNaturalAccName%22%2C%22data%22%3Afalse%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22naturalAcc%22%2C%22data%22%3A%22%22%7D%2C%7B%22vtype%22%3A%22attr%22%2C%22name%22%3A%22withoutInd%22%2C%22data%22%3A%22%22%7D%5D%7D";
    // String str ="names=[{\"fullname\":\"北京旺旺商贸有限公司\",\"entTra\":\"旺旺\",\"nameInd\":\"商贸\",\"orgForm\":\"有限公司\",\"industryCo\":\"5219\",\"uniteCo\":\"51\",\"order\":0}]&accredits=[]";
         String mt = URLDecoder.decode(str);
	    System.out.println(mt);
	    //http://wsdj.baic.gov.cn:8088/myTransaction/getList.do
	    //返回值 
	    //tijaio:/name/nameCheck/foundCheck.do
	    //post:zihao=%E6%97%97%E8%88%B0&hangye=%E5%95%86%E8%B4%B8&quanming=%E5%8C%97%E4%BA%AC%E6%97%97%E8%88%B0%E5%95%86%E8%B4%B8%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&hydm=5219&zihaohangye=%E6%97%97%E8%88%B0%E5%95%86%E8%B4%B8&shijian=2015-09-29+14%3A52%3A03&hbdm=51&superNameId=
	    // 返回 ：可以  {"startTime":1443508907326,"errorNum":0,"entName":"北京你早商贸有限公司","jyz":[],"cc":[]}
	    //返回不可以的 {"startTime":1443508969280,"errorNum":3,"entName":"北京旗舰商贸有限公司","jyz":[{"rule":"驰名商标_汉字查重列表(查重)","msg":"您的名称与现有驰名商标(旗舰)冲突"}],"cc":[{"entName":"北京旗舰食品集团有限公司","rule":"同行业_查重列表(查重)"},{"entName":"北京旗舰集团","rule":"同行业_查重列表(查重)"}]}
	   // String json ="{\"exception\":\"OptimusException\",\"exceptionMes\":\"您提交过相同的名称,请勿重复提交!\",\"exceptionName\":\"OptimusException\"}";
	    
	    String mm ="76e99e1597594c1c85508a94cc485dac";
	    
        // url /name/nameRegist/submit.do?	

	    //URL‎   /system/username.do	
	    //响应 
/*	    String jsstr ="{\"idCard\":\"220319197102110614\",\"sex\":\"1\",\"userPwd\":\"6684D40D03F2777A08E818E59E98619B\""
	    		+ ",\"roleIdList\":[\"default_role\"],\"userId\":\"4e2a7ef209844ea5803bac7981f99885\"}";
      
	    JSONObject reobj = JSONObject.parseObject(jsstr);
	    
	    RemoteUserVo user = EntityUtils.transferEntity(reobj, RemoteUserVo.class);
	    System.out.println(user.getUserId());
	    */
		
	}
	/**
	 * 获得登录用户下的公司核名记录（及处理进展）
	 * @param sessionId
	 */
	public List<ApproveGshmSearchVo> getUserGshmTransactList(String sessionId){
		//拼接参数
		String postData ="{\"data\":[{\"vtype\":\"pagination\",\"name\":\"pagerows\",\"data\":25}]}";
		postData = "postData="+urlEncodeStr(postData,"utf-8");
		GrapPageInfo gpi = new GrapPageInfo();
		String backStr = gpi.getHttpContent(RemoteConstant.GSWZ_USERTRANSACLIST,postData,sessionId);
		ApproveGshmSearchVo vo = null;
		JSONObject obj = null;
		JSONArray newarr = null;
		JSONObject newobj = null;
		List<ApproveGshmSearchVo> appvos = null;
		if(StringUtil.isEmpty(backStr)){
			vo = new ApproveGshmSearchVo();
			appvos = new ArrayList<ApproveGshmSearchVo>();
			JSONObject jsobj = JSONObject.parseObject(backStr);
			JSONArray arr = jsobj.getJSONArray("data");
			for (int i = 0; i < arr.size(); i++) {
				jsobj = arr.getJSONObject(i);
				obj = jsobj.getJSONObject("data");
				String jsstr = obj.get("rows").toString();
				newarr = JSONArray.parseArray(jsstr);
				for (int j = 0; j < newarr.size(); j++) {
					newobj = newarr.getJSONObject(j);
					vo = EntityUtils.transferEntity(newobj,ApproveGshmSearchVo.class);
					appvos.add(vo);
				}
			}
		}
	   return appvos;
	}
	/**
	 * 公司名称检查
	 * @param sessionId
	 * @return
	 */
	public String gsnameCheck(String sessionId,BusinessLibaryEntity ble){
		String valif = "";
		String currTime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", new Date().getTime());
		String posParam ="zihao="+urlEncodeStr("诗选","utf-8")+"&hangye="+urlEncodeStr("商贸","utf-8")+
				         "&quanming="+urlEncodeStr("北京诗选商贸有限公司","utf-8")+"&hydm="+urlEncodeStr("5219","utf-8")+
				         "&zihaohangye="+urlEncodeStr("诗选商贸","utf-8")+"&shijian="+urlEncodeStr(currTime,"utf-8")+"&hbdm="+urlEncodeStr("51","utf-8")+"&superNameId="+urlEncodeStr("","utf-8")+"";
		
		GrapPageInfo gpi = new GrapPageInfo();
		String backStr = gpi.getHttpContent(RemoteConstant.GSWZ_NAMECHECK,posParam,sessionId);
		if(StringUtil.isEmpty(backStr)){
			 JSONObject reobj = JSONObject.parseObject(backStr);
			 String errorNum = reobj.getString("errorNum");
			 if(StringUtil.isEmpty(errorNum)){
				 if(!"0".equals(errorNum)){
					 valif = backStr;
				 } 
			 }
		}
		return valif;
	}
	/**
	 * 对字符串进行URLEncode 进行加密
	 * @param str
	 * @param charse 字符编码
	 * @return
	 */
	public String urlEncodeStr(String str,String charset){
		String encodeStr ="";
		if(StringUtil.isEmpty(str)){
			try {
				encodeStr = URLEncoder.encode(str, charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return encodeStr;
	}
	
	/**
	 * 拼接公司核名
	 * @return
	 */
	public String getGszcParamer(String sessionId){
		//获得保存符合要求的公司名称ID
		String nameId = GrapPageLogin.getSaveNameRegistId(sessionId, "北京先锋商贸有限公司", "先锋", "商贸", "有限公司", "5219", "51");
		String postData ="{\"data\":[{\"name\":\"ent_panel\",\"vtype\":\"formpanel\",\"data\":"
				+ "{\"nameId\":\""+nameId+"\","
				+ "\"entType\":\"1100\","//1151
				+ "\"preRegOrg\":\"110101000\"}}"//注册机关
				+ ",{\"name\":\"ent_detail_panel\",\"vtype\":\"formpanel\","
				 + "\"data\":{\"invType\":\"1\","//公司类型：1 内资公司   2：外资公司
				+ "\"specialIndustry\":\"0\","//经营范围是否包含以下特殊行业（0：不是特殊行业  2登记注册代理机构 4期货经纪机构 5投资基金(不含基金管理公司) 7母婴护理企业 等）
				+ "\"specialRegion\":\"0\","//注册地址是否存在特殊地区（当经营范围是否包含以下特殊行业：为0不是特殊行业 才显示该输入框） （0不在特殊区域 1西客站地区 2天安门地区） 
				+ "\"sgzwFlag\":\"2\","//是否是市国资委投资的公司      当注册地址是否存在特殊地区为0（不在特殊区域）时（含1 是  2 否）
				+ "\"sswwFlag\":\"\","
				+ "\"icSuboffice\":\"110101000\"}},"//名称核准机关 当是否是市国资委投资的公司为1时显示默认：北京市工商行政管理局。当选择2否时显示下拉框：
				                                      //110101000 北京市工商行政管理局东城分局;110106000 北京市工商行政管理局丰台分局;110102000  北京市工商行政管理局西城分局  
				                                     //110105000  北京市工商行政管理局朝阳分局;110107000 北京市工商行政管理局石景山分局 等
				+ "{\"vtype\":\"attr\",\"name\":\"invStr\","
				//投资人信息
				//一人投资
				  + "\"data\":\"[{\\\"name_id\\\":\\\""+nameId+"\\\","//登录用户ID
				+ "\\\"inv_type\\\":\\\"20\\\"," //投资人类别    20 自然人股东（内）；10 单位股东（内）；90 其他投资者
				+ "\\\"inv_name\\\":\\\"于凯\\\","//投资人姓名
				+ "\\\"cer_no\\\":\\\"37052319861205001X\\\","//投资人证件号
				+ "\\\"cer_type\\\":\\\"1\\\"}]\"},"///证件类别  1 中华人民共和国居民身份证，5 军人离(退)休证，9其他有效身份证件，2 中华人民共和国军官证 3 中华人民共和国警官证
			// 两个或两个以上
				/*+"\"data\":\"[{\\\"name_id\\\":\\\""+nameId+"\\\",\\\"inv_type\\\":\\\"20\\\",\\\"inv_name\\\":\\\"于丹\\\",\\\"cer_no\\\":\\\"\\\",\\\"blic_no\\\":\\\"\\\"},"
                +"{\\\"name_id\\\":\\\""+nameId+"\\\",\\\"inv_type\\\":\\\"20\\\",\\\"inv_name\\\":\\\"于雷\\\",\\\"cer_no\\\":\\\"\\\",\\\"blic_no\\\":\\\"\\\"}]\"},"
            */
				+ "{\"vtype\":\"attr\",\"name\":\"nameRemark\",\"data\":\"注册公司\"},"//备注
				+ "{\"vtype\":\"attr\",\"name\":\"busiType\",\"data\":\"10\"},{\"vtype\":\"attr\","
				+ "\"name\":\"nameType\",\"data\":\"1\"},"
				+ "{\"vtype\":\"attr\",\"name\":\"isNaturalAccName\",\"data\":false},"
				+ "{\"vtype\":\"attr\",\"name\":\"naturalAcc\",\"data\":\"\"},"
				+ "{\"vtype\":\"attr\",\"name\":\"withoutInd\",\"data\":\"\"}]}";
		postData = "postData="+urlEncodeStr(postData, "utf-8");
		return postData;
	}
	
	/**
	 * 提交公司核名返回信息处理
	 * @param jsonstr
	 * @return
	 */
	public String dealMingchzJson(String jsonstr){
		if(StringUtil.isEmpty(jsonstr)){
			JSONObject obj = JSONObject.parseObject(jsonstr);
			String st = obj.getString("exception");
			if(!StringUtil.isEmpty(st)){
				String datastr = obj.getString("data");
				JSONArray arry = JSONArray.parseArray(datastr);
				JSONObject nextObj = null;
				String pass =""; 
				for (int i = 0; i < arry.size(); i++) {
					nextObj = arry.getJSONObject(i);
					nextObj = nextObj.getJSONObject("data");
					pass = nextObj.getString("pass");
					if("true".equals(pass)){
						return "提交成功！！！";
					}
				}
			}else{
				//提交失败返回失败信息
				String failinfo = obj.getString("exceptionMes");
				return failinfo;
			}
		}
	    return "提交失败!!";
	}
	
	/**
	 * 返回类别下的子类
	 * @param jsonstr
	 * @return 
	 */
	public IndustrylVo getIndustrylVoByjsonstr(String jsonstr) {
		IndustrylVo vo = null;
		JSONObject obj = null;
		JSONObject newobj = null;
		JSONArray newarr = null;
		List<IndustryDetail> details= null;
		if(StringUtil.isEmpty(jsonstr)){
			JSONObject jsobj = JSONObject.parseObject(jsonstr);
			JSONArray arr = jsobj.getJSONArray("data");
			details = new ArrayList<IndustryDetail>();
			IndustryDetail indetail = null;
			for (int i = 0; i < arr.size(); i++) {
				jsobj = arr.getJSONObject(i);
				obj = jsobj.getJSONObject("data");
				vo = EntityUtils.transferEntity(obj, IndustrylVo.class);
				String jsstr = obj.get("rows").toString();
				newarr = JSONArray.parseArray(jsstr);
				for (int j = 0; j < newarr.size(); j++) {
					newobj = newarr.getJSONObject(j);
					indetail = EntityUtils.transferEntity(newobj,
							IndustryDetail.class);
					if (indetail != null) {
						details.add(indetail);
					}
				}
				vo.setRows(details);
			}
		}
		return vo;
	}
	
	/**
	 * 拼接
	 * @param unite_code
	 * @param pagerows
	 * @param page
	 * @param totalrows
	 * @return
	 */
	public String appendPostParam(String unite_code,String pagerows,String page,String totalrows){
		String appendparam ="postData={\"data\":[{\"name\":\"querypanel\",\"vtype\":\"formpanel\",\"data\":"
				+ "{\"industry_code\":\"\",\"keyword\":\"\",\"unite_code\":\""+unite_code+"\"}},{\"vtype\":\"pagination\",\"name\":"
				+ "\"pagerows\",\"data\":"+pagerows+"},{\"vtype\":\"pagination\",\"name\":\"page\",\"data\":"+page+"},{\"vtype\":\"pagination\","
				+ "\"name\":\"totalrows\",\"data\":"+totalrows+"},{\"vtype\":\"pagination\",\"name\":\"sortName\",\"data\":\"\"},"
				+ "{\"vtype\":\"pagination\",\"name\":\"sortFlag\",\"data\":\"\"}]}";
		return appendparam;
	}
	
	
	
	/**
	 * map对象装换成Vo类
	 */
	
	@SuppressWarnings("static-access")
	public List<BusinessCategoryVo> getBusinessCategoryVos(String jsonstr){
		JSONObject obj = null;
		Map<String,String> tempmap = null;
		BusinessCategoryVo vo = null;
		List<BusinessCategoryVo> lcategory = null;
		if(StringUtil.isEmpty(jsonstr)){
			tempmap = new HashMap<String, String>();
			lcategory = new ArrayList<BusinessCategoryVo>();
			JSONArray arry = new JSONArray().parseArray(jsonstr);
			for(int i = 0;i<arry.size();i++){
				obj = arry.getJSONObject(i);
				vo = EntityUtils.transferEntity(obj, BusinessCategoryVo.class);
				lcategory.add(vo);
			}
		}
		return lcategory;
	}

	/**
	 * 获得主营类别
	 * @param url‎‎
	 */
	public  String getHttpContent(String url,String postData,String sessionId) {
        return  getBusinessCategory(url, "UTF-8",postData,sessionId);
    }
	
	/**
	 * 通过Http请求获取返回信息
	 * @param action
	 * @param charSet
	 * @param postData
	 * @return
	 */
	public  String getBusinessCategory(String action,String charSet,String postData,String sessionId){
		String backStr ="";
		String urlStr = remoteUrl + action;
		 HttpURLConnection urlConn = null;
		 
		 URL address_url = null;
		try {
			//连接远程地址
			address_url = new URL(urlStr);
			urlConn = (HttpURLConnection) address_url.openConnection();
			//设置失效时间
			urlConn.setConnectTimeout(30000);  
			
			urlConn.setReadTimeout(30000);  
			
		    urlConn.setDoOutput(true);  
			  
		    // 设置是否从httpUrlConnection读入，默认情况下是true;  
		    urlConn.setDoInput(true);  
		  
		    // Post 请求不能使用缓存  
		    urlConn.setUseCaches(false); 
		    
		    buildHeader(urlConn,sessionId,postData);
		    // 设定请求的方法为"POST"，默认是GET  
		    urlConn.setRequestMethod("POST");  
		    //Post传参的方法 
		   // if(StringUtil.isEmpty(postData)){
		        OutputStream os = urlConn.getOutputStream();  
		        os.write(postData.getBytes());  
		        os.flush();  
		        // 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,  
		        // 再调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器  
		        os.close();  
		   // }
		    
		    //得到访问页面的返回值
            int response_code = urlConn.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK) {
                InputStream in = urlConn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                	backStr+=line;
                }
                if(in != null){
                	in.close();
                }
                return backStr;
           }
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            if(urlConn !=null){
            	urlConn.disconnect();
            }
        }
		return backStr;
		
	}
	private static void buildHeader(HttpURLConnection urlConn, String sessionId,String postData) {
		 urlConn.setRequestProperty("Referer", "http://wsdj.baic.gov.cn/");
		 urlConn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		 urlConn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		 urlConn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		 urlConn.setRequestProperty("Connection", "Keep-Alive");
		 urlConn.setRequestProperty("Cache-Control", "max-age=0");
		 urlConn.setRequestProperty("Host", "wsdj.baic.gov.cn:8088");
		 urlConn.setRequestProperty("Content-Length", "" + postData.length()); 
		 urlConn.setRequestProperty("Cookie", sessionId);
		 urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		 urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
	}
}
