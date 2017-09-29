package com.jixiang.argo.lvzheng.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;

public class ReadTxtPhoneSendDX {
	/**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static void readTxtFile(String filePath){
    	String txtContent = "因工商政策变化，原营业执照等三证合为一照，银行及税务等机构已做调整。为您办理业务不受影响，请找小微更换新营业执照：13366021349。";
        try {
                String encoding="UTF-8";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    int readyNum = 0;
                    int sendErroy = 0;
                    StringBuffer sb = new StringBuffer();
                    while((lineTxt = bufferedReader.readLine()) != null){
                        int msg = JavaDemoHttp.sendmsg(lineTxt,txtContent);
                        if(msg==0){
                        	readyNum++;
                        }else{
                        	sendErroy++;
                        	sb.append(lineTxt).append(",");
                        }
                    }
                    read.close();
                    System.out.println("*******发送完成!*******成功数:"+readyNum+"******失败数："+sendErroy);
                    System.out.println("****失败的手机号为:"+sb.toString());
		        }else{
		            System.out.println("找不到指定的文件");
		        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }
     
    public static void main(String argv[]){
        String filePath = "D:\\1111.txt";
        readTxtFile(filePath);
//    	try {
//    		int msg = JavaDemoHttp.sendmsg("18811144804","您的验证码是：890890(15分钟有效)。");
//    		System.out.println(msg);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
    }
}
