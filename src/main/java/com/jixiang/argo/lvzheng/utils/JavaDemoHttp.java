package com.jixiang.argo.lvzheng.utils;


import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jx.service.messagecenter.entity.MobileSmsResult;



public class JavaDemoHttp {
	public static String sendMsg(String phone,String content)throws UnsupportedEncodingException{
		//����Ϊ����Ĳ������ʱ���޸�,��������תΪ16�����ٷ���
				String strReg = "101100-WEB-HUAX-622264";   //ע��ţ��ɻ�����ͨ�ṩ��
		        String strPwd = "PBJRMRBH";                 //���루�ɻ�����ͨ�ṩ��
		        String strSourceAdd = "";                   //��ͨ���ţ���Ϊ�գ�Ԥ������һ��Ϊ�գ�
		        String strPhone = phone;//�ֻ���룬����ֻ���ð�Ƕ��ŷֿ������1000��
		        String strContent = HttpSend.paraTo16(content);       //��������
		        
		        //���²���Ϊ������URL,�Լ������������Ĳ������޸�
		        String strSmsUrl = "http://www.stongnet.com/sdkhttp/sendsms.aspx";
		        String strSmsParam = "reg=" + strReg + "&pwd=" + strPwd + "&sourceadd=" + strSourceAdd + "&phone=" + strPhone + "&content=" + strContent;;
		        
		        String strRes  = new String();
		        
		        strRes = HttpSend.postSend(strSmsUrl, strSmsParam);
		        return strRes;
	}
	public static int sendmsg1(String phone,String content) throws UnsupportedEncodingException{
		//����Ϊ����Ĳ������ʱ���޸�,��������תΪ16�����ٷ���
		String strReg = "101100-WEB-HUAX-622264";   //ע��ţ��ɻ�����ͨ�ṩ��
        String strPwd = "PBJRMRBH";                 //���루�ɻ�����ͨ�ṩ��
        String strSourceAdd = "";                   //��ͨ���ţ���Ϊ�գ�Ԥ������һ��Ϊ�գ�
        String strPhone = phone;//�ֻ���룬����ֻ���ð�Ƕ��ŷֿ������1000��
        String strContent = HttpSend.paraTo16(content);       //��������
        
        //���²���Ϊ������URL,�Լ������������Ĳ������޸�
        String strSmsUrl = "http://www.stongnet.com/sdkhttp/sendsms.aspx";
        String strSmsParam = "reg=" + strReg + "&pwd=" + strPwd + "&sourceadd=" + strSourceAdd + "&phone=" + strPhone + "&content=" + strContent;;
        
        String strRes  = new String();
        
        strRes = HttpSend.postSend(strSmsUrl, strSmsParam);
        
        System.out.println(strRes);
        int r = -1;
        String[] arr = strRes.split("&");
        if(arr.length > 1 && arr[0].equals("result=0")){
        	strRes = "ok";
        	r = 0;
        }else{
        	strRes = "fail";
        }
        return r;
        
	}
	
	public static int sendmsg(String phone,String content) throws UnsupportedEncodingException{
		int r = -1;
		try {
			MobileSmsResult result = RSBLL.getstance().getMoblieSmsService().sendMsg(phone, content);
			System.out.println(result.getCode()+result.getMsg());
			if(2 == result.getCode()){
	        	r = 0;
	        }
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        return r;
        
	}
}
