package com.jixiang.argo.lvzheng.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.jixiang.argo.lvzheng.service.BusinessLibraryService;
import com.jixiang.argo.lvzheng.service.LogDealService;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.argo.controller.AbstractController;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.KehinfosEntity;

/**
 * 企业库中角色处理
 * @author wuyin
 *
 */
@Path("/order")
public class BusinessKehController extends AbstractController{
    @Path("/saveKehinfo")
    public ActionResult saveKehinfo(){
    	return new ActionResult() {
			
			public void render(BeatContext beatContext) {
				beat().getResponse().setContentType("text/html");
				beat().getResponse().setCharacterEncoding("UTF-8");
				
				String orderid = beat().getRequest().getParameter("orderid");
				long nowtime = new Date().getTime();
				long loginuser = UtilsHelper.getLoginId(beat());
				String busid = beat().getRequest().getParameter("busid");
				//法定代表人 2
				String farzx = beat().getRequest().getParameter("farzx");
				String farphone = beat().getRequest().getParameter("farphone");
				String farsex = beat().getRequest().getParameter("farsex");
				String farcard = beat().getRequest().getParameter("farcard");
				String farsfdz = beat().getRequest().getParameter("farsfdz");
				String display = beat().getRequest().getParameter("display");
				beat().getModel().add("display", display);
				//经理  4
				String jingl = beat().getRequest().getParameter("jingl");
				String jinglphone = beat().getRequest().getParameter("jinglphone");
				String jinglsex = beat().getRequest().getParameter("jinglsex");
				String jinglcard = beat().getRequest().getParameter("jinglcard");
				String jinglsfdz = beat().getRequest().getParameter("jinglsfdz");
				
				//财务负责人  5
				String caiwfzr = beat().getRequest().getParameter("caiwfzr");
				String caiwphone = beat().getRequest().getParameter("caiwphone");
				String caiwsfz = beat().getRequest().getParameter("caiwsfz");
				KehinfosEntity keh = null;
				BusinessLibaryEntity bl = null;
			    String adddsc = beat().getRequest().getParameter("adddscount");
			    String addjscount = beat().getRequest().getParameter("addjscount");
			    String ishavedongsh = beat().getRequest().getParameter("ishavedongsh");
			    String ishavejiansh = beat().getRequest().getParameter("ishavejiansh");
			    String text ="";
				if(!StringUtil.isEmpty(busid)){
					//添加
					bl = new BusinessLibaryEntity();
					bl.setAddperson(loginuser);
					bl.setAddtime(nowtime);
					bl.setUpdatetime(nowtime);
					if(StringUtil.isEmpty(orderid)){
				        long lorderid = Long.valueOf(orderid);
				        bl.setOrderid(lorderid);
				    }
					int intis = Integer.parseInt(ishavedongsh);
					bl.setIsdengsh(intis);
					int intisjs = Integer.parseInt(ishavejiansh);
					bl.setIsjiash(intisjs);
					long lbid = BusinessLibraryService.getInstance().saveBusinessLibraryEntity(bl);
					text = lbid +"";
					if(StringUtil.isEmpty(farzx)){
						keh = new KehinfosEntity();
						keh.setUsername(farzx);
						keh.setPhone(farphone);
						keh.setSex(farsex);
						keh.setIdcard(farcard);
						keh.setIdcardaddress(farsfdz);
						keh.setBusidl(lbid);
						keh.setRoletype(2);//法人代表
						BusinessLibraryService.getInstance().saveKehInfos(keh);
					}
		            if(StringUtil.isEmpty(jingl)){
		            	keh = new KehinfosEntity();
						keh.setUsername(jingl);
						keh.setPhone(jinglphone);
						keh.setSex(jinglsex);
						keh.setIdcard(jinglcard);
						keh.setIdcardaddress(jinglsfdz);
						keh.setBusidl(lbid);
						keh.setRoletype(4);//经理
						BusinessLibraryService.getInstance().saveKehInfos(keh);
					}
		            if(StringUtil.isEmpty(caiwfzr)){
		            	keh = new KehinfosEntity();
						keh.setUsername(caiwfzr);
						keh.setPhone(caiwphone);
						keh.setIdcard(caiwsfz);
						keh.setBusidl(lbid);
						keh.setRoletype(5);//财务负责人
						BusinessLibraryService.getInstance().saveKehInfos(keh);
					}
		    	    //9.监事，11 监事主席
		    	    if("1".equals(ishavejiansh)){
		    	    	 String zhixjianszx1 = beat().getRequest().getParameter("zhixjianszx1");
		                 String zhixjianszxphone1 = beat().getRequest().getParameter("zhixjianszxphone1");
		                 String zhixjianszxsex1 = beat().getRequest().getParameter("zhixjianszxsex1");
		                 String zhixjianszxcard1 = beat().getRequest().getParameter("zhixjianszxcard1");
		                 String zhixjianszxcarddz1 = beat().getRequest().getParameter("zhixjianszxcarddz1");
		                 if(StringUtil.isEmpty(zhixjianszx1)){
		                	keh = new KehinfosEntity();
		         			keh.setUsername(zhixjianszx1);
		     				keh.setPhone(zhixjianszxphone1);
		     				keh.setSex(zhixjianszxsex1);
		     				keh.setIdcard(zhixjianszxcard1);
		     				keh.setIdcardaddress(zhixjianszxcarddz1);
		     				keh.setBusidl(lbid);
		     				keh.setRoletype(11);// 监事主席
		     				BusinessLibraryService.getInstance().saveKehInfos(keh);
		                 }
		                 if(StringUtil.isEmpty(addjscount)){
		                	 int ic = Integer.parseInt(addjscount);
		                	 String jians = "";
		                     String jiansphone  = "";
		                     String jianssex = "";
		                     String jianscard = "";
		                     String jianscarddz = "";
		                	 for(int i =1;i<= ic;i++){
		                		 jians = beat().getRequest().getParameter("jians"+i);
		                		 jiansphone = beat().getRequest().getParameter("jiansphone"+i);
		                		 jianssex = beat().getRequest().getParameter("jianssex"+i);
		                		 jianscard = beat().getRequest().getParameter("jianscard"+i);
		                		 jianscarddz = beat().getRequest().getParameter("jianscarddz"+i);
		                		 if(StringUtil.isEmpty(jians)){
		                			keh = new KehinfosEntity();
		                  			keh.setUsername(jians);
		              				keh.setPhone(jiansphone);
		              				keh.setSex(jianssex);
		              				keh.setIdcard(jianscard);
		              				keh.setIdcardaddress(jianscarddz);
		              				keh.setBusidl(lbid);
		              				keh.setRoletype(9);// 监事主席
		              				BusinessLibraryService.getInstance().saveKehInfos(keh);
		                		 }
		                	 }
		                 }
		    	    }else{
		    	    	//无
		    	    	 String jians = beat().getRequest().getParameter("jians");
		                 String jiansphone = beat().getRequest().getParameter("jiansphone");
		                 String jianssex =beat().getRequest().getParameter("jianssex");
		                 String jianscard = beat().getRequest().getParameter("jianscard");
		                 String jianscarddz =beat().getRequest().getParameter("jianscarddz");
		                 if(StringUtil.isEmpty(jians)){
		                	 keh = new KehinfosEntity();
		           			keh.setUsername(jians);
		       				keh.setPhone(jiansphone);
		       				keh.setSex(jianssex);
		       				keh.setIdcard(jianscard);
		       				keh.setIdcardaddress(jianscarddz);
		       				keh.setBusidl(lbid);
		       				keh.setRoletype(11);// 监事
		       				BusinessLibraryService.getInstance().saveKehInfos(keh);
		                 }
		    	    }
		    	    //有
		    	    if("1".equals(ishavedongsh)){
		    	    	//董事会主席 10
		    	    	String dongshzx1 = beat().getRequest().getParameter("dongshzx1");
		    	        String dongshzxphone1 = beat().getRequest().getParameter("dongshzxphone1");
		    	        String dongshzxsex1 = beat().getRequest().getParameter("dongshzxsex1");
		    	        String dongshzxcard1 = beat().getRequest().getParameter("dongshzxcard1");
		    	        String dongshzxcarddz1= beat().getRequest().getParameter("dongshzxcarddz1");
		    	        if(StringUtil.isEmpty(dongshzx1)){
		    	        	keh = new KehinfosEntity();
		        			keh.setUsername(dongshzx1);
		    				keh.setPhone(dongshzxphone1);
		    				keh.setSex(dongshzxsex1);
		    				keh.setIdcard(dongshzxcard1);
		    				keh.setIdcardaddress(dongshzxcarddz1);
		    				keh.setBusidl(lbid);
		    				keh.setRoletype(10);//董事主席
		    				BusinessLibraryService.getInstance().saveKehInfos(keh);
		    	        }
		    	        //董事 3
		    	        if(StringUtil.isEmpty(adddsc)){
		    	        	int ic = Integer.parseInt(adddsc);
		    	        	 String dongsjis ="";
		                     String dongsjisphone ="";
		                     String dongsjissex ="";
		                     String dongsjiscard ="";
		                     String dongsjiscarddz ="";
		    	        	for(int i =1;i<=ic;i++){
		    	        		dongsjis =  beat().getRequest().getParameter("dongsjis"+i);
		    	        		dongsjisphone =  beat().getRequest().getParameter("dongsjisphone"+i);
		    	        		dongsjissex =  beat().getRequest().getParameter("dongsjissex"+i);
		    	        		dongsjiscard =  beat().getRequest().getParameter("dongsjiscard"+i);
		    	        		dongsjiscarddz =  beat().getRequest().getParameter("dongsjiscarddz"+i);
		    	        		if(StringUtil.isEmpty(dongsjis)){
		    	        			keh = new KehinfosEntity();
		    	        			keh.setUsername(dongsjis);
		    	    				keh.setPhone(dongsjisphone);
		    	    				keh.setSex(dongsjissex);
		    	    				keh.setIdcard(dongsjiscard);
		    	    				keh.setIdcardaddress(dongsjiscarddz);
		    	    				keh.setBusidl(lbid);
		    	    				keh.setRoletype(3);//董事
		    	    				BusinessLibraryService.getInstance().saveKehInfos(keh);
		    	        		}
		    	        	}
		    	        }
		    	    }else{
		    	    	//无
		    	    	String zhixgongsh = beat().getRequest().getParameter("zhixgongsh");
		    	        String zhixgongshphone = beat().getRequest().getParameter("zhixgongshphone");
		    	        String zhixgongshsex = beat().getRequest().getParameter("zhixgongshsex");
		    	        String zhixgongshcard = beat().getRequest().getParameter("zhixgongshcard");
		    	        String zhixgongshcarddz= beat().getRequest().getParameter("zhixgongshcarddz");
		    	        if(StringUtil.isEmpty(zhixgongsh)){
		    	        	keh = new KehinfosEntity();
		        			keh.setUsername(zhixgongsh);
		    				keh.setPhone(zhixgongshphone);
		    				keh.setSex(zhixgongshsex);
		    				keh.setIdcard(zhixgongshcard);
		    				keh.setIdcardaddress(zhixgongshcarddz);
		    				keh.setBusidl(lbid);
		    				keh.setRoletype(10);//董事主席
		    				BusinessLibraryService.getInstance().saveKehInfos(keh);
		    	        }
		    	    }
				}else{
					text = busid;
					//更新
					long lbusid = Long.valueOf(busid);
					bl = BusinessLibraryService.getInstance().getBusinessLibaryEntityById(lbusid);
					bl.setUpdatetime(nowtime);
					int intis = Integer.parseInt(ishavedongsh);
					bl.setIsdengsh(intis);
					int intisjs = Integer.parseInt(ishavejiansh);
					bl.setIsjiash(intisjs);
					BusinessLibraryService.getInstance().updateBusinessLibrary(bl);
					//法定代办人
				    keh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(lbusid, 2);
				    if(keh != null){
				    	keh.setUsername(farzx);
						keh.setPhone(farphone);
						keh.setSex(farsex);
						keh.setIdcard(farcard);
						keh.setIdcardaddress(farsfdz);
						keh.setBusidl(lbusid);
						keh.setRoletype(2);//法人代表
						//增加log4j日志记录
				        if(bl.getBusstatus() == 1){
				        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
				        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
				        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,2);
				        }
						BusinessLibraryService.getInstance().updateKehInfos(keh);
				    }else{
				    	keh = new KehinfosEntity();
						keh.setUsername(farzx);
						keh.setPhone(farphone);
						keh.setSex(farsex);
						keh.setIdcard(farcard);
						keh.setIdcardaddress(farsfdz);
						keh.setBusidl(lbusid);
						keh.setRoletype(2);//法人代表
						BusinessLibraryService.getInstance().saveKehInfos(keh);
				    }
				  //经理
				   keh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(lbusid, 4);
				   if(keh != null){
						keh.setUsername(jingl);
						keh.setPhone(jinglphone);
						keh.setSex(jinglsex);
						keh.setIdcard(jinglcard);
						keh.setIdcardaddress(jinglsfdz);
						keh.setBusidl(lbusid);
						keh.setRoletype(4);//经理
						//增加log4j日志记录
				        if(bl.getBusstatus() == 1){
				        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
				        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
				        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,4);
				        }
						BusinessLibraryService.getInstance().updateKehInfos(keh);
				   }else{
						keh = new KehinfosEntity();
						keh.setUsername(jingl);
						keh.setPhone(jinglphone);
						keh.setSex(jinglsex);
						keh.setIdcard(jinglcard);
						keh.setIdcardaddress(jinglsfdz);
						keh.setBusidl(lbusid);
						keh.setRoletype(4);//经理
						BusinessLibraryService.getInstance().saveKehInfos(keh);
				   }
				   //财务负责人
				   keh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(lbusid, 5);
				   if(keh != null){
					    keh.setUsername(caiwfzr);
						keh.setPhone(caiwphone);
						keh.setIdcard(caiwsfz);
						keh.setBusidl(lbusid);
						keh.setRoletype(5);//财务负责人
						//增加log4j日志记录
				        if(bl.getBusstatus() == 1){
				        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
				        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
				        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,5);
				        }
						BusinessLibraryService.getInstance().updateKehInfos(keh);
				   }else{
						keh = new KehinfosEntity();
						keh.setUsername(caiwfzr);
						keh.setPhone(caiwphone);
						keh.setIdcard(caiwsfz);
						keh.setBusidl(lbusid);
						keh.setRoletype(5);//财务负责人
						BusinessLibraryService.getInstance().saveKehInfos(keh);
				   }
				   //更新董事
				   if("1".equals(ishavedongsh)){
		   	    	//董事会主席 10
		   	    	String dongshzx1 = beat().getRequest().getParameter("dongshzx1");
		   	        String dongshzxphone1 = beat().getRequest().getParameter("dongshzxphone1");
		   	        String dongshzxsex1 = beat().getRequest().getParameter("dongshzxsex1");
		   	        String dongshzxcard1 = beat().getRequest().getParameter("dongshzxcard1");
		   	        String dongshzxcarddz1= beat().getRequest().getParameter("dongshzxcarddz1");
		   	        keh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(lbusid, 10);
		   	        if(keh == null){
		   	        	keh = new KehinfosEntity();
		       			keh.setUsername(dongshzx1);
		   				keh.setPhone(dongshzxphone1);
		   				keh.setSex(dongshzxsex1);
		   				keh.setIdcard(dongshzxcard1);
		   				keh.setIdcardaddress(dongshzxcarddz1);
		   				keh.setBusidl(lbusid);
		   				keh.setRoletype(10);//董事主席
		   				BusinessLibraryService.getInstance().saveKehInfos(keh);
		   	        }else{
		   	        	keh.setUsername(dongshzx1);
		   				keh.setPhone(dongshzxphone1);
		   				keh.setSex(dongshzxsex1);
		   				keh.setIdcard(dongshzxcard1);
		   				keh.setIdcardaddress(dongshzxcarddz1);
		   				keh.setBusidl(lbusid);
		   				keh.setRoletype(10);//董事主席
		   			    //增加log4j日志记录
				        if(bl.getBusstatus() == 1){
				        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
				        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
				        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,10);
				        }
		   				BusinessLibraryService.getInstance().updateKehInfos(keh);
		   	        }
		   	        //董事 3
		   	        if(StringUtil.isEmpty(adddsc)){
		   	        	    int ic = Integer.parseInt(adddsc);
		   	        	    String dongsjis ="";
		                    String dongsjisphone ="";
		                    String dongsjissex ="";
		                    String dongsjiscard ="";
		                    String dongsjiscarddz ="";
		                    String dongsId = "";
		                List<String> gsds = BusinessLibraryService.getInstance().getKehByRoletype(lbusid, 3);
		   	        	for(int i =1;i<=ic;i++){
		   	        		dongsId = beat().getRequest().getParameter("gongsId"+i);
		   	        		dongsjis =  beat().getRequest().getParameter("dongsjis"+i);
		   	        		dongsjisphone =  beat().getRequest().getParameter("dongsjisphone"+i);
		   	        		dongsjissex =  beat().getRequest().getParameter("dongsjissex"+i);
		   	        		dongsjiscard =  beat().getRequest().getParameter("dongsjiscard"+i);
		   	        		dongsjiscarddz =  beat().getRequest().getParameter("dongsjiscarddz"+i);
		   	        		if(gsds.contains(dongsId)){
		   	        			//更新
		   	        			long lkid = Long.valueOf(dongsId);
		   	        			keh = BusinessLibraryService.getInstance().getKehinfosEntityByid(lkid);
		   	        			gsds.remove(dongsId);
		   	        			keh.setUsername(dongsjis);
		   	    				keh.setPhone(dongsjisphone);
		   	    				keh.setSex(dongsjissex);
		   	    				keh.setIdcard(dongsjiscard);
		   	    				keh.setIdcardaddress(dongsjiscarddz);
		   	    				keh.setBusidl(lbusid);
		   	    				keh.setRoletype(3);//董事
		   	    			    //增加log4j日志记录
						        if(bl.getBusstatus() == 1){
						        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
						        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
						        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,3);
						        }
		   	    				BusinessLibraryService.getInstance().updateKehInfos(keh);
		   	        		}else{
		   	        			//新增
		   	        			if(StringUtil.isEmpty(dongsjis)){
		   	   	        			keh = new KehinfosEntity();
		   	   	        			keh.setUsername(dongsjis);
		   	   	    				keh.setPhone(dongsjisphone);
		   	   	    				keh.setSex(dongsjissex);
		   	   	    				keh.setIdcard(dongsjiscard);
		   	   	    				keh.setIdcardaddress(dongsjiscarddz);
		   	   	    				keh.setBusidl(lbusid);
		   	   	    				keh.setRoletype(3);//董事
		   	   	    				BusinessLibraryService.getInstance().saveKehInfos(keh);
		   	   	        		}
		   	        		}
		   	        	}
		   	        	if(StringUtil.isListNull(gsds)){
		   	        		for(String str : gsds){
		   	        			long lh = Long.valueOf(str);
		   	        			BusinessLibraryService.getInstance().deleteKehinfo(lh);
		   	        		}
		   	        	}
		   	        }
				   }else{
						//无
		   	    	String zhixgongsh = beat().getRequest().getParameter("zhixgongsh");
		   	        String zhixgongshphone = beat().getRequest().getParameter("zhixgongshphone");
		   	        String zhixgongshsex = beat().getRequest().getParameter("zhixgongshsex");
		   	        String zhixgongshcard = beat().getRequest().getParameter("zhixgongshcard");
		   	        String zhixgongshcarddz= beat().getRequest().getParameter("zhixgongshcarddz");
		   	       keh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(lbusid, 10);
		   	       if(keh == null){
		   	    	   if(StringUtil.isEmpty(zhixgongsh)){
		   	    		   keh = new KehinfosEntity();
		   	    		   keh.setUsername(zhixgongsh);
		   	    		   keh.setPhone(zhixgongshphone);
		   	    		   keh.setSex(zhixgongshsex);
		   	    		   keh.setIdcard(zhixgongshcard);
		   	    		   keh.setIdcardaddress(zhixgongshcarddz);
		   	    		   keh.setBusidl(lbusid);
		   	    		   keh.setRoletype(10);//董事主席
		   	    		   BusinessLibraryService.getInstance().saveKehInfos(keh);
		   	    	   }
		   	       }else{
		   	    	  keh.setUsername(zhixgongsh);
			    		   keh.setPhone(zhixgongshphone);
			    		   keh.setSex(zhixgongshsex);
			    		   keh.setIdcard(zhixgongshcard);
			    		   keh.setIdcardaddress(zhixgongshcarddz);
			    		   keh.setBusidl(lbusid);
			    		   keh.setRoletype(10);//董事主席
			    		   //增加log4j日志记录
					        if(bl.getBusstatus() == 1){
					        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
					        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
					        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,10);
					        }
			    		   BusinessLibraryService.getInstance().updateKehInfos(keh);
		   	       }
		   	        
				   }
				   //保存监事
				   //9.监事，11 监事主席
		   	    if("1".equals(ishavejiansh)){
		   	    	 String zhixjianszx1 = beat().getRequest().getParameter("zhixjianszx1");
		                String zhixjianszxphone1 = beat().getRequest().getParameter("zhixjianszxphone1");
		                String zhixjianszxsex1 = beat().getRequest().getParameter("zhixjianszxsex1");
		                String zhixjianszxcard1 = beat().getRequest().getParameter("zhixjianszxcard1");
		                String zhixjianszxcarddz1 = beat().getRequest().getParameter("zhixjianszxcarddz1");
		                keh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(lbusid, 11);
		                if(keh == null){
		                	if(StringUtil.isEmpty(zhixjianszx1)){
		                		keh = new KehinfosEntity();
		                		keh.setUsername(zhixjianszx1);
		                		keh.setPhone(zhixjianszxphone1);
		                		keh.setSex(zhixjianszxsex1);
		                		keh.setIdcard(zhixjianszxcard1);
		                		keh.setIdcardaddress(zhixjianszxcarddz1);
		                		keh.setBusidl(lbusid);
		                		keh.setRoletype(11);// 监事主席
		                		BusinessLibraryService.getInstance().saveKehInfos(keh);
		                	}
		                }else{
		                	keh.setUsername(zhixjianszx1);
		            		keh.setPhone(zhixjianszxphone1);
		            		keh.setSex(zhixjianszxsex1);
		            		keh.setIdcard(zhixjianszxcard1);
		            		keh.setIdcardaddress(zhixjianszxcarddz1);
		            		keh.setBusidl(lbusid);
		            		keh.setRoletype(11);// 监事主席
		            		//增加log4j日志记录
					        if(bl.getBusstatus() == 1){
					        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
					        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
					        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,11);
					        }
		            		BusinessLibraryService.getInstance().updateKehInfos(keh);
		                }
		                if(StringUtil.isEmpty(addjscount)){
		               	 int ic = Integer.parseInt(addjscount);
		               	 String jians = "";
		                    String jiansphone  = "";
		                    String jianssex = "";
		                    String jianscard = "";
		                    String jianscarddz = "";
		                    String jiansId ="";
		                    List<String> jsds = BusinessLibraryService.getInstance().getKehByRoletype(lbusid, 9);
		               	 for(int i =1;i<= ic;i++){
		               		jiansId = beat().getRequest().getParameter("jiansId"+i);
		               		 jians = beat().getRequest().getParameter("jians"+i);
		               		 jiansphone = beat().getRequest().getParameter("jiansphone"+i);
		               		 jianssex = beat().getRequest().getParameter("jianssex"+i);
		               		 jianscard = beat().getRequest().getParameter("jianscard"+i);
		               		 jianscarddz = beat().getRequest().getParameter("jianscarddz"+i);
		               		if(jsds.contains(jiansId)){
		               			//更新
		               			long lkid = Long.valueOf(jiansId);
		   	        			keh = BusinessLibraryService.getInstance().getKehinfosEntityByid(lkid);
		   	        			jsds.remove(jiansId);
		   	        			keh.setUsername(jians);
		           				keh.setPhone(jiansphone);
		           				keh.setSex(jianssex);
		           				keh.setIdcard(jianscard);
		           				keh.setIdcardaddress(jianscarddz);
		           				keh.setBusidl(lbusid);
		           				keh.setRoletype(9);// 监事
		           			  //增加log4j日志记录
						        if(bl.getBusstatus() == 1){
						        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
						        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
						        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,9);
						        }
		           				BusinessLibraryService.getInstance().updateKehInfos(keh);
		               		}else{
		               			if(StringUtil.isEmpty(jians)){
		               				keh = new KehinfosEntity();
		               				keh.setUsername(jians);
		               				keh.setPhone(jiansphone);
		               				keh.setSex(jianssex);
		               				keh.setIdcard(jianscard);
		               				keh.setIdcardaddress(jianscarddz);
		               				keh.setBusidl(lbusid);
		               				keh.setRoletype(9);// 监事
		               				BusinessLibraryService.getInstance().saveKehInfos(keh);
		               			}
		               		}
		               	 }
		               	 if(StringUtil.isListNull(jsds)){
		               		 for(String str : jsds){
		               			long lc = Long.valueOf(str);
		               			BusinessLibraryService.getInstance().deleteKehinfo(lc);
		               		 }
		               	 }
		                }
		   	    }else{
		   	    	//无
		   	    	 String jians = beat().getRequest().getParameter("jians");
		                String jiansphone = beat().getRequest().getParameter("jiansphone");
		                String jianssex =beat().getRequest().getParameter("jianssex");
		                String jianscard = beat().getRequest().getParameter("jianscard");
		                String jianscarddz =beat().getRequest().getParameter("jianscarddz");
		                keh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(lbusid, 11);
		                if(keh == null){
		                	if(StringUtil.isEmpty(jians)){
		                		keh = new KehinfosEntity();
		                		keh.setUsername(jians);
		                		keh.setPhone(jiansphone);
		                		keh.setSex(jianssex);
		                		keh.setIdcard(jianscard);
		                		keh.setIdcardaddress(jianscarddz);
		                		keh.setBusidl(lbusid);
		                		keh.setRoletype(11);// 监事
		                		BusinessLibraryService.getInstance().saveKehInfos(keh);
		                	}
		                }else{
		                	keh.setUsername(jians);
		            		keh.setPhone(jiansphone);
		            		keh.setSex(jianssex);
		            		keh.setIdcard(jianscard);
		            		keh.setIdcardaddress(jianscarddz);
		            		keh.setBusidl(lbusid);
		            		keh.setRoletype(11);// 监事
		            		//增加log4j日志记录
					        if(bl.getBusstatus() == 1){
					        	String opername = BusinessLibraryService.getInstance().getLoginUsername(loginuser);
					        	String optime = UtilsHelper.formatDateTostring("yyyy-MM-dd hh:mm:ss", nowtime);
					        	LogDealService.getInstance().writrKehinfoLog(lbusid, keh, optime, opername,11);
					        }
		            		BusinessLibraryService.getInstance().updateKehInfos(keh);
		                }
		   	    }
		   	    
				}
				try {
					beat().getResponse().getWriter().print(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
    }
    @Path("/savegud")
    public ActionResult savegud(){
    	String orderid = beat().getRequest().getParameter("addorderid");
		long nowtime = new Date().getTime();
		long loginuser = UtilsHelper.getLoginId(beat());
		String busid = beat().getRequest().getParameter("addbusid");
		
		String zirrgudcount = beat().getRequest().getParameter("zirrgudcount");
		String fargudcount = beat().getRequest().getParameter("fargudcount");
		KehinfosEntity keh = null;
		BusinessLibaryEntity bl = null;
		String display = beat().getRequest().getParameter("display");
		beat().getModel().add("display", display);
		if(!StringUtil.isEmpty(busid)){
			bl = new BusinessLibaryEntity();
			bl.setAddperson(loginuser);
			bl.setAddtime(nowtime);
			bl.setUpdatetime(nowtime);
			if(StringUtil.isEmpty(orderid)){
		        long lorderid = Long.valueOf(orderid);
		        bl.setOrderid(lorderid);
		    }
			long lbid = BusinessLibraryService.getInstance().saveBusinessLibraryEntity(bl);
			//自然人股东 0
			if(StringUtil.isEmpty(zirrgudcount)){
				int icount = Integer.parseInt(zirrgudcount);
				String gudong =""; 
	            String chuzf ="";
	            String gudIdcard ="";
	            String gudIdcardaddre ="";
	            String gudsex =""; 
	            String gudphone ="";
				for(int i =1;i<=icount;i++){
					gudong = beat().getRequest().getParameter("gudong"+i);
					chuzf = beat().getRequest().getParameter("chuzf"+i);
					gudIdcard = beat().getRequest().getParameter("gudIdcard"+i);
					gudIdcardaddre = beat().getRequest().getParameter("gudIdcardaddre"+i);
					gudsex = beat().getRequest().getParameter("gudsex"+i);
					gudphone = beat().getRequest().getParameter("gudphone"+i);
					if(StringUtil.isEmpty(gudong)){
						keh = new KehinfosEntity();
						keh.setUsername(gudong);
						keh.setAttribute1(chuzf);
						keh.setIdcard(gudIdcard);
						keh.setIdcardaddress(gudIdcardaddre);
						keh.setPhone(gudphone);
						keh.setBusidl(lbid);
						keh.setSex(gudsex);
						keh.setRoletype(0);
						BusinessLibraryService.getInstance().saveKehInfos(keh);
					}
				}
			}
			//法人股东 1
			if(StringUtil.isEmpty(fargudcount)){
				int icount = Integer.parseInt(fargudcount);
				String fargud ="";
	            String faryyzzh ="";
	            String farchuze ="";
				for(int i =1;i<=icount;i++){
					fargud = beat().getRequest().getParameter("fargud"+i);
					faryyzzh = beat().getRequest().getParameter("faryyzzh"+i);
					farchuze = beat().getRequest().getParameter("farchuze"+i);
					if(StringUtil.isEmpty(fargud)){
						keh = new KehinfosEntity();
						keh.setUsername(fargud);
						keh.setYingyzz(faryyzzh);
						if(StringUtil.isEmpty(farchuze)){
							float fco = Float.valueOf(farchuze);
							keh.setChuze(fco);
						}
						keh.setBusidl(lbid);
						keh.setRoletype(1);
						BusinessLibraryService.getInstance().saveKehInfos(keh);
					}
				}
			}
		} 
    	return redirect("/order/business/"+busid+"_"+display);
    }
}
