package com.jixiang.argo.lvzheng.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import com.jx.argo.ArgoTool;

/***
 * 获取properties文件的工具类默认的工程是lvzheng下
 * @author lvzheng-duxf
 *
 */
public class PropertiesUtils {
	public static Properties getProperties(String fileName){
		Properties pro = new Properties();    
		InputStream ins = null;
		try {
			String path = ArgoTool.getConfigFolder() + ArgoTool.getNamespace()+"/"+fileName;
			ins = new BufferedInputStream(new FileInputStream(path));
			pro.load(ins);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pro;
	}
}
