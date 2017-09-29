package com.jixiang.argo.lvzheng.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.jixiang.argo.lvzheng.service.IndustrylEnterService;
import com.jixiang.argo.lvzheng.vo.BusinessCategoryVo;
import com.jixiang.argo.lvzheng.vo.IndustryDetail;
import com.jixiang.argo.lvzheng.vo.IndustrylVo;
import com.jixiang.argo.lvzheng.vo.RemoteUserVo;
import com.jixiang.argo.lvzheng.vo.SaveNameInfoVo;

public class GrapPageLogin {
	

	public static void main(String[] args) throws Exception {
		String sessionId = BusinessApplyUtils.getSessionId();
		InputStream verifyCodeInput = getVerifyCode(sessionId);
		File file = new File("D://test.jpg");
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		IOUtils.copy(verifyCodeInput, fileOutputStream);
		fileOutputStream.close();
		Scanner cin = new Scanner(System.in);
		System.out.println("请输入验证码：");
		String verifyCode = cin.nextLine();
		GrapPageLogin gra = new GrapPageLogin();
		//gra.postLogin(sessionId, verifyCode);
		//gra.postGetList(sessionId);  //获得用户列表
		
		getLoginUserNameId(sessionId);
	}
	
	public static InputStream getVerifyCode(String sessionId) throws ClientProtocolException, IOException{
		String url = RemoteConstant.GSWZ_VERIFYCODE;
		HttpPost httppost = new HttpPost(url);
		buildHeader(httppost, sessionId);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		HttpResponse execute = httpClient.execute(httppost);
		InputStream content = execute.getEntity().getContent();
		return content;
	}
	
	public  void postGetList(String sessionId) throws ClientProtocolException, IOException{
		String url = "http://wsdj.baic.gov.cn:8088/myTransaction/getList.do";
		HttpPost httppost = new HttpPost(url);
		postAction(httppost, sessionId);
	}
	
	/**
	 * 获得登录用户名ID
	 */
	public static String getLoginUserNameId(String sessionId){
		String name_Id = "";
		GrapPageInfo gpi = new GrapPageInfo();
		String backStr = gpi.getHttpContent(RemoteConstant.GSWZ_LOGINUSERINFOURL,"",sessionId);
		if(StringUtil.isEmpty(backStr)){
		    JSONObject reobj = JSONObject.parseObject(backStr);
		    RemoteUserVo user = EntityUtils.transferEntity(reobj, RemoteUserVo.class);
		    if(user != null){
		    	name_Id = user.getUserId();
		    }
		}
		return name_Id;
	}

	public static String getSaveNameRegistId(String sessionId,String fullname,String entTra,String nameInd,String orgForm,String industryCo,
			                          String uniteCo){
		String nameId = "";
		GrapPageInfo gpi = new GrapPageInfo();
		//	[{"fullname":"北京石轩商贸有限公司","entTra":"石轩","nameInd":"商贸","orgForm":"有限公司","industryCo":"5219","uniteCo":"51","order":0}
		//(备选名称),{"fullname":"北京颍河商贸有限公司","entTra":"颍河","nameInd":"商贸","orgForm":"有限公司","industryCo":"5219","uniteCo":"51","order":1}]
				
		String paramPost = "[{\"fullname\":\""+fullname+"\","//公司全名
				+ "\"entTra\":\""+entTra+"\","//字号
				+ "\"nameInd\":\""+nameInd+"\","//行业特点
				+ "\"orgForm\":\""+orgForm+"\","//组织形式
				+ "\"industryCo\":\""+industryCo+"\","//行业code
				+ "\"uniteCo\":\""+uniteCo+"\","//行业uniteCo
				+ "\"order\":0}]";
		try {
			paramPost = "names="+URLEncoder.encode(paramPost,"utf-8")+"&accredits="+URLEncoder.encode("[]","utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String backStr = gpi.getHttpContent(RemoteConstant.GSWZ_SAVEGSMCURL,paramPost,sessionId);
		if(StringUtil.isEmpty(backStr)){
			  JSONObject reobj = JSONObject.parseObject(backStr);
			  SaveNameInfoVo nameRegisr = EntityUtils.transferEntity(reobj, SaveNameInfoVo.class);
			    if(nameRegisr != null){
			    	nameId = nameRegisr.getNameId();
			    }
		}
		return nameId;
	}
	
	
	
	/**
	 * 模拟登录
	 * @param sessionId
	 * @param gongsxtcode
	 * @param password
	 * @param verifyCode
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String postLogin(String sessionId,String gongsxtcode,String password,String verifyCode) throws IOException, ClientProtocolException {
		// 创建Get方法实例
		String url = RemoteConstant.GSWZ_LOGINURL;
		HttpPost httppost = new HttpPost(url);
		
		List<BasicNameValuePair> parameters= new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("login_name", gongsxtcode));
		parameters.add(new BasicNameValuePair("user_pwd",password ));
		parameters.add(new BasicNameValuePair("verify_code", verifyCode));
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameters);
		httppost.setEntity(urlEncodedFormEntity);
		String str = postAction(httppost, sessionId);
		String backInfo = "";
		if(StringUtil.isEmpty(str)){
			JSONObject obj = JSONObject.parseObject(str);
			String result = obj.getString("result");
			if("5".equals(result)){
				backInfo ="您注册的身份证号码未通过校验！";
			}
			if("2".equals(result)){
				backInfo = "用户名或密码错误!";
			}
		}
		return backInfo;
	}

	/**
	 * @param httpClient
	 * @param httppost
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private static String postAction(HttpPost httppost,String sessionId) throws IOException,
			ClientProtocolException {
		buildHeader(httppost, sessionId);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(httppost);
		HttpEntity entity = response.getEntity();
		String str ="";
		if (entity != null) {
			InputStream instreams = entity.getContent();
			str = convertStreamToString(instreams);
			System.out.println(str);
			httppost.abort();
		}
		return str;
	}

	/**
	 * @param httppost
	 */
	public static void buildHeader(HttpPost httppost, String sessionId) {
		httppost.addHeader("Referer", "http://wsdj.baic.gov.cn/");
		httppost.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httppost.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		httppost.addHeader("Connection", "Keep-Alive");
		httppost.addHeader("Cache-Control", "max-age=0");
		httppost.addHeader("Host", "wsdj.baic.gov.cn:8088");
		httppost.addHeader("Cookie", sessionId);
		httppost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
