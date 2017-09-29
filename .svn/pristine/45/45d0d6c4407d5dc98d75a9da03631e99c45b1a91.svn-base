package com.jixiang.argo.lvzheng.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jx.service.newcore.entity.KehinfosEntity;

/**
 * http模拟登录
 * @author wuyin
 *
 */
public class GrapPageRegist {

private static GrapPageRegist instance = null;
	
	public static GrapPageRegist getInstance(){
		if(instance == null){
			instance = new GrapPageRegist();
		}
		return instance;
	}
	
	/**
	 * 注册用户名check
	 */
	public  String  checkRegistName(String sessionId,String regiName) throws ClientProtocolException, IOException{
		String url = RemoteConstant.GSWZ_CHECKREGISTLOBINNAME;
		String param = "login_name="+regiName;
		HttpMethod get = new GetMethod(url+"?"+param);
        get.releaseConnection();
		HttpClient httpClient = new HttpClient();
		int  code = httpClient.executeMethod(get);
		String str="";
		if(code == HttpURLConnection.HTTP_OK){
			str = get.getResponseBodyAsString();
		}
	    return str;
	}
	/**
	 * 保存注册用户
	 * @param sessionId
	 */
	public String saveRegistuser(String sessionId,KehinfosEntity keh){
		//提交参数
		String postData ="{\"data\":[{\"name\":\"user_info\",\"vtype\":\"formpanel\",\"data\":{\"loginName\":\""+keh.getPhone()+"\",\"userPwd\":\"xiaowei\""
                         +",\"rePwd\":\"xiaowei\",\"pwdQuestion\":\"\",\"pwdAnswer\":\"\",\"userName\":\""+keh.getUsername()+"\",\"sex\":\"1\",\"cerType\":\"1\",\"cerNo\""
                         +":\""+keh.getIdcard()+"\",\"countryCity\":\"156\",\"telphone\":\"\",\"mobile\":\""+keh.getPhone()+"\",\"email\":\""+keh.getEmail()+"\""
		                  + ",\"address\":\"北京朝阳区\",\"zipCode\":\"\"}}]}";
		try {
			postData = "postData=" + URLEncoder.encode(postData, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		GrapPageInfo gpi = new GrapPageInfo();
		String backStr = gpi.getHttpContent(RemoteConstant.GSWZ_USERREGIST,postData,sessionId);
		JSONObject obj = JSONObject.parseObject(backStr);
		JSONArray jsarry = obj.getJSONArray("data");
		JSONObject newObj = null;
		String backInfo ="";
		String text =""; 
		for (int i = 0; i < jsarry.size(); i++) {
			newObj = jsarry.getJSONObject(i);
			backInfo = newObj.getString("data");
			if(!"1".equals(backInfo)){
				text ="注册失败！失败原因："+backInfo;
			}
		}
		return text;
	}
}
