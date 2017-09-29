package com.jixiang.argo.lvzheng.service;

import java.util.List;

import com.jx.service.newcore.entity.AreasEntity;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;

/**
 * 切换城市业务处理
 * @author wuyin
 *
 */
public class ChangeCityService {

	private static ChangeCityService instance = null;
	
	public static ChangeCityService getInstance(){
		if(instance == null){
			instance = new ChangeCityService();
		}
		return instance;
	}
	/**
	 * @param lpses
	 * @return
	 */
	public String getAppendDltype(int i,ProductCategoryEntity pe){
		String text =" <div class=\"nice-select2 w_select_s1\" name=\"nice-select2\">"+
              	"<input name=\"addresstype\" value=\"$!{}\" type=\"text\" readonly id=\"addresstype\" />"+
              	"<input name=\"addresstypevalue\" value=\"$!{}\" type=\"hidden\" readonly id=\"addresstypevalue\" />";
		text = text +"<ul>";
		List<ProductPropertiesEntity> gsmchz = AddOrderRealService.getInstance().getCompanyType("addresstype");
        for(ProductPropertiesEntity pro : gsmchz){
        	text = text +" <li name=\"addresstypeli\" value=\""+pro.getProid()+"\" data-value=\""+pe.getCid()+"_"+pro.getProid()+"\" tm=\""+i+"\">"+pro.getProname()+"</li>";
        }
	   text = text +"</ul></div>";
	   return text;
	}
	
	
	public String backGszcinfo(List<ProductCategoryEntity> lpses,String cityId){
		StringBuffer app = new StringBuffer("");
		int i = 0;
		String areaatr ="";
		String apper1007 ="";
		String apper1008 ="";
		String companyStr ="";
		String appendYhkh ="";
		String dldztype ="";
		String dldz ="";
		for(ProductCategoryEntity pce : lpses){
			if(!"其他".equals(pce.getCname())){
				app.append(" <li class=\"li_box\"><div class=\"check_box\">"+
		                "<input onclick=\"clickcheckbox('"+pce.getProductid()+"_"+pce.getCid()+"_"+i+"');\" type=\"checkbox\" value=\""+pce.getProductid()+"_"+pce.getCid()+"_"+i+"\" name=\"selectProduct\" id=\"selectProduct\"/><!-- 公司注册  -->"+pce.getCname()+""+
		                "</div><div class=\"select_box\">");
					areaatr =areaInfo(i,pce,cityId);
					app.append(areaatr);
					//拼接代理地址（）增加中为加特
					if("1002".equals(pce.getCid()+"")){
						dldztype = getAppendDltype(i,pce);
						app.append(dldztype);
					}
					if("1007".equals(pce.getCid()+"")){
						apper1007 = append1007(i,pce);
						app.append(apper1007);
					}else if("1008".equals(pce.getCid()+"")){
						apper1008 = append1008(i,pce);
						app.append(apper1008);
					}else if("1010".equals(pce.getCid()+"")){
						appendYhkh = "<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\">"+
                                     "<input type=\"text\" value=\"招商银行\" readonly/>"+
                                     "<input type=\"hidden\" name=\"dljzkahbank_"+i+"\" id=\"dljzkahbank_"+i+"\" value=\"38\"/></div>";
						app.append(appendYhkh);
					}else if(!"1011".equals(pce.getCid()+"") && !"1012".equals(pce.getCid()+"") && !"1013".equals(pce.getCid()+"")&& !"1014".equals(pce.getCid()+"")){
						companyStr = companyStr(i,pce,cityId);
						app.append(companyStr);
					}
					//去除代理地址 非销售
					/*if("代理地址".equals(pce.getCname())){
						dldz = dldzStr(i,pce);
						app.append(dldz);
					}*/
					app.append("  </div><div class=\"pri_box red\">￥<span id=\"price_"+i+"\">0</span></div><input type=\"hidden\" name=\"zcprice_"+i+"\" id=\"zcprice_"+i+"\"  value=\"\"/></li>");
					app.append("<div class=\"clear\"></div>");
			}else{
				String qit = appendOther(i,pce);
				app.append(qit);
			}
			i++;
		}
		return app.toString();
	}
	public String appendOther(int i ,ProductCategoryEntity pe){
		String text ="<li class=\"li_box\"><div class=\"check_box\">"+
              "<input onclick=\"clickcheckbox('"+pe.getProductid()+"_"+pe.getCid()+"_25');\" type=\"checkbox\" value=\""+pe.getProductid()+"_"+pe.getCid()+"_25\" name=\"selectProduct\" id=\"selectProduct\"/> 其他"+
              "</div><div class=\"select_box in_text\">"+
               "<input type=\"text\" class=\"w_text_m\" placeholder=\"自定义内容 最多8个字\" name=\"qitmc\" id =\"qitmc\"/>"+
               "<input type=\"text\" class=\"w_text_l\" placeholder=\"自定义内容 最多20个字\" name=\"qitcontent\" id =\"qitcontent\"/>"+
               "<input  type=\"text\" class=\"w_text_s\" name=\"qitprice\" id =\"qitprice\" onblur=\"dyqitprice('qitprice','qitprice_25');\"/>"+
               "元</div><div class=\"pri_box gray\">￥<span id=\"qitprice_25\">0</span></div></li>";
		return text;
	}
	public String dldzStr(int i,ProductCategoryEntity pe){
		String text = "<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\"><input type=\"text\" value=\"非销售\" readonly />";
		//是否销售
		List<ProductPropertiesEntity> isseller =  AddOrderRealService.getInstance().getCompanyType("seller");
		for(ProductPropertiesEntity pro : isseller){
			text = text +" <input type=\"hidden\" name=\"isseller\" id =\"isseller\" value=\""+pro.getProid()+"\"/>";
		}
		text = text +"</div>";
		return text;
	}
	/**
	 * @param i
	 * @param pe
	 * @param cityId
	 * @return
	 */
	public String companyStr(int i,ProductCategoryEntity pe,String cityId){
		String text ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\"><input type=\"text\" value=\"内资公司\" readonly>"+
	                  " <input type=\"hidden\" name=\"zccompanytype_"+i+"\" id =\"zccompanytype_"+i+"\" value=\"35\"/><ul>";
		//公司类别secondArea 
		List<ProductPropertiesEntity> company = AddOrderRealService.getInstance().getCompanyType("companytype");
		String comStr ="";
		for(ProductPropertiesEntity pro : company){
			//comStr = comStr+"<input type=\"hidden\" name=\"zccompanytype\" id =\"zccompanytype\" value=\""+pro.getProid()+"\"/>";
			if("2".equals(cityId)){
				comStr = comStr+" <li name=\"zccompanytypeszz_"+i+"\" data-value=\""+pe.getCid()+"_"+pro.getProid()+"\">"+pro.getProname()+"</li>";
			}else{
				if(pro.getProid() == 35){
					comStr = comStr+" <li name=\"zccompanytypeszz_"+i+"\" data-value=\""+pe.getCid()+"_"+pro.getProid()+"\">"+pro.getProname()+"</li>";
				}
			}
		}
		comStr = comStr+" </div>";
		//公司规模
		List<ProductPropertiesEntity> gongsgm =  AddOrderRealService.getInstance().getCompanyType("gongsgm");
		String gsgn ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\"><input type=\"text\" value=\"小规模纳税人\" readonly>"+
		              "<input type=\"hidden\" name=\"zcgongsgm_"+i+"\" id=\"zcgongsgm_"+i+"\" value=\"30\"/>";
		if("1".equals(cityId)){
			gsgn = gsgn +"<ul>";
			for(ProductPropertiesEntity per : gongsgm){
				gsgn = gsgn+"<li name=\"zcgongsgm_"+i+"\" data-value=\""+pe.getCid()+"_"+per.getProid()+"\">"+per.getProname()+"</li>";
			}
			gsgn = gsgn +"</ul>";
		}
		gsgn = gsgn+"</div>";
		return text+comStr+gsgn;
	}
	
	public String append1008(int i,ProductCategoryEntity pe){
		String text ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\"><input type=\"text\" value=\"一人有限公司\" readonly>"+
	                 "<input type=\"hidden\" name=\"zccompanytypett_"+i+"\" id =\"zccompanytypett_"+i+"\" value=\"64\"/><ul>";
		List<ProductPropertiesEntity> gsmchz = AddOrderRealService.getInstance().getCompanyType("reggshm");
        for(ProductPropertiesEntity pro : gsmchz){
        	text = text +" <li name=\"zccompanytype_"+i+"\" data-value=\""+pe.getCid()+"_"+pro.getProid()+"\">"+pro.getProname()+"</li>";
        }
        text = text +"</ul></div><div class=\"nice-select2 w_select_s1\" name=\"nice-select2\">"+
               " <input type=\"text\" class=\"w_text_m\" placeholder=\"企业名称核准\"  name=\"zcgongsgmgsmchz\" readonly=\"true\" id=\"zcgongsgmgsmchz\" value=\"\"/></div>";
		return text;
	}
	public String append1007(int i,ProductCategoryEntity pe){
		String text ="";
		text ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\"><input type=\"text\" value=\"有限责任公司\" readonly>"+
		       "<input type=\"hidden\" name=\"zccompanytypett_"+i+"\" id =\"zccompanytypett_"+i+"\" value=\"62\"/><ul>";
		List<ProductPropertiesEntity> gsnb =  AddOrderRealService.getInstance().getCompanyType("regnianbao");
		for(ProductPropertiesEntity pro : gsnb){
			text = text +" <li name=\"zccompanytype_"+i+"\" data-value=\""+pe.getCid()+"_"+pro.getProid()+"\">"+pro.getProname()+"</li>";
		}
		text = text +"</ul></div><div class=\"nice-select2 w_select_s1\" name=\"nice-select2\">"+
		       " <input type=\"text\" class=\"w_text_m\" placeholder=\"公司名称\"  name=\"zcgongsgmnb\" readonly=\"true\" id=\"zcgongsgmnb\" value=\"\"/></div>";
		
		return text;
	}
	
	public String areaInfo(int i,ProductCategoryEntity pe,String cityId){
		//获得一级区域
		//List<AreasEntity> firstArea = AddOrderRealService.getInstance().getAreasEntity();
		AreasEntity ae =  AddOrderRealService.getInstance().getAreasEntity(cityId);
		String cityStr ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\">";
		/*for(AreasEntity ae : firstArea){
			cityStr = cityStr+"<li  name=\"zccity_"+i+"\" data-value=\""+pe.getCid()+"_"+ae.getAreaid()+"\">"+ae.getName()+"</li>";
		}*/
		if(ae != null){
			cityStr = cityStr + "<input type=\"text\" value=\""+ae.getName()+"\" readonly/>"+
	                "<input type=\"hidden\" name=\"zccity_"+i+"\" id =\"zccity_"+i+"\" value =\""+ae.getAreaid()+"\"/>";
	               // "<ul id =\"area\">";
			//cityStr = cityStr+"<li  name=\"zccity_"+i+"\" data-value=\""+pe.getCid()+"_"+ae.getAreaid()+"\">"+ae.getName()+"</li></ul>";
		}
		cityStr = cityStr +"</div>";
		//获得二级菜单
		List<AreasEntity> secondArea =  AddOrderRealService.getInstance().getAreasEntitychange(cityId);
		String areaStr ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\">";
		int j = 0;
		for(AreasEntity aes : secondArea){
			if(j == 0){
				areaStr = areaStr + "<input type=\"text\" value=\""+aes.getName()+"\" readonly id=\"initzcarea_"+i+"\" onclick=\"areaulclick('gszc','"+i+"');\"/>"+
			                "<input type=\"hidden\" name=\"zccitityarea_"+i+"\" id =\"zccitityarea_"+i+"\" value=\""+aes.getAreaid()+"\"/>"+
			                "<ul id=\"cityArea_"+i+"\">";
			}
			areaStr = areaStr +"<li name=\"zcarea_"+i+"\" parent=\""+aes.getParentid()+"\" data-value=\""+pe.getCid()+"_"+aes.getAreaid()+"\">"+aes.getName()+"</li>";
		    j++;
		}
		areaStr = areaStr+"</ul></div>";
		return cityStr+areaStr;
	}
	
	public String dlareaInfo(int i,ProductCategoryEntity pe,String cityId){
		//获得一级区域
		//List<AreasEntity> firstArea = AddOrderRealService.getInstance().getAreasEntity();
		AreasEntity ae =  AddOrderRealService.getInstance().getAreasEntity(cityId);
		String cityStr ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\">";
		/*for(AreasEntity ae : firstArea){
			cityStr = cityStr+"<li  name=\"dlcity_"+i+"\" data-value=\""+pe.getCid()+"_"+ae.getAreaid()+"\">"+ae.getName()+"</li>";
		}*/
		if(ae != null){
			cityStr = cityStr+"<input type=\"text\" value=\""+ae.getName()+"\" readonly/>"+
	                "<input type=\"hidden\" name=\"dljzcity_"+i+"\" id =\"dljzcity_"+i+"\" value =\""+ae.getAreaid()+"\"/>";
	               // "<ul id =\"area\">";
			//cityStr = cityStr+"<li  name=\"dlcity_"+i+"\" data-value=\""+pe.getCid()+"_"+ae.getAreaid()+"\">"+ae.getName()+"</li></ul>";
		}
		cityStr = cityStr +"</div>";
		//获得二级菜单
		List<AreasEntity> secondArea =  AddOrderRealService.getInstance().getAreasEntitychange(cityId);
		String areaStr ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\">";
                
		int j = 0;
		for(AreasEntity aes : secondArea){
			if(j == 0){
				areaStr = areaStr +"<input type=\"text\" value=\""+aes.getName()+"\" readonly id=\"dljiz_"+i+"\" onclick=\"areaulclick('dljz','"+i+"');\"/>"+
		                "<input type=\"hidden\" name=\"dljzcitityarea_"+i+"\" id =\"dljzcitityarea_"+i+"\" value=\""+aes.getAreaid()+"\"/>"+
		                "<ul id=\"dlmz_"+i+"\">";
			}
			areaStr = areaStr +"<li name=\"dlarea_"+i+"\" parent=\""+aes.getParentid()+"\" data-value=\""+pe.getCid()+"_"+aes.getAreaid()+"\">"+aes.getName()+"</li>";
		   j++;
		}
		areaStr = areaStr+"</ul></div>";
		return cityStr+areaStr;
	}
	
	public String appendDljz(int i,ProductCategoryEntity pe){
		String text ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\">"+
               "<input type=\"text\" value=\"12个月\" readonly/>"+
                "<input type=\"hidden\" name=\"serviceCircle_"+i+"\" id=\"serviceCircle_"+i+"\" value=\"33\"/><ul>";
		List<ProductPropertiesEntity> bookOrder =  AddOrderRealService.getInstance().getCompanyType("bookOrder");
		for(ProductPropertiesEntity pce : bookOrder){
			text = text +"<li name=\"servicecircle_"+i+"\" data-value=\""+pe.getCid()+"_"+pce.getProid()+"\">"+pce.getProname()+"</li>";
		}
		text = text +"</ul></div>";
		return text;
	}
	public String appendZhaosyhkh(int i,ProductCategoryEntity pe){
		String text ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\">"+
                  "<input type=\"text\" value=\"招商银行\" readonly/>"+
                  "<input type=\"hidden\" name=\"dljzkahbank_"+i+"\" id=\"dljzkahbank_"+i+"\" value=\"38\"/></div>";
		return text;
	}
	
	public String dljzCompanyType(int i,ProductCategoryEntity pe){
		String text ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\"><input type=\"text\" value=\"内资公司\" readonly>";
		//公司类别secondArea 
		List<ProductPropertiesEntity> company = AddOrderRealService.getInstance().getCompanyType("companytype");
		String comStr ="";
		for(ProductPropertiesEntity pro : company){
			comStr = comStr+"<input type=\"hidden\" name=\"dljzcompanytype\" id =\"dljzcompanytype\" value=\""+pro.getProid()+"\"/>";
		}
		comStr = comStr+" </div>";
		return text+comStr;
	}
	public String gongsGmAppen(int i, ProductCategoryEntity pe,String cityId){
		//公司规模
		List<ProductPropertiesEntity> gongsgm =  AddOrderRealService.getInstance().getCompanyType("gongsgm");
		String gsgn ="<div class=\"nice-select2 w_select_s1\" name=\"nice-select2\"><input type=\"text\" value=\"小规模纳税人\" readonly>"+
		              "<input type=\"hidden\" name=\"dljzgongsgm_"+i+"\" id=\"dljzgongsgm_"+i+"\" value=\"30\"/>";
		if("1".equals(cityId)){
			gsgn = gsgn +"<ul>";
			for(ProductPropertiesEntity per : gongsgm){
				gsgn = gsgn+"<li name=\"dlgongsgm_"+i+"\" data-value=\""+pe.getCid()+"_"+per.getProid()+"\">"+per.getProname()+"</li>";
			}
			gsgn = gsgn +"</ul>";
		}
		gsgn = gsgn+"</div>";
		return gsgn;
	}
	
	public String appenddljzOther(int i ,ProductCategoryEntity pe){
		String text ="<li class=\"li_box\"><div class=\"check_box\">"+
	              "<input onclick=\"clickcheckbox('"+pe.getProductid()+"_"+pe.getCid()+"_10');\" type=\"checkbox\" value=\""+pe.getProductid()+"_"+pe.getCid()+"_10\" name=\"selectProduct\" id=\"selectProduct\"/> 其他"+
	              "</div><div class=\"select_box in_text\">"+
	               "<input type=\"text\" class=\"w_text_m\" placeholder=\"自定义内容 最多8个字\" name=\"dlqitmc\" id =\"dlqitmc\"/>"+
	               "<input type=\"text\" class=\"w_text_l\" placeholder=\"自定义内容 最多20个字\" name=\"dlqitcontent\" id =\"dlqitcontent\"/>"+
	               "<input  type=\"text\" class=\"w_text_s\" name=\"dlqitprice\" id =\"dlqitprice\" onblur=\"dyqitprice('dlqitprice','dlprice_10');\"/>"+
	               "元</div><div class=\"pri_box gray\">￥<span id=\"dlprice_10\">0</span></div></li>";
			return text;
		}
	/**
	 * 拼接代理记账
	 * @return
	 */
	public String backDljzinfo(List<ProductCategoryEntity> lpses,String cityId){
		StringBuffer app = new StringBuffer("");
		int i = 0;
		String areaatr ="";
		String appdljz ="";
		String zhaosyhkh ="";
		String companyType ="";
		String gsgm = "";
		for(ProductCategoryEntity pce : lpses){
			if(!"其他".equals(pce.getCname())){
				if(pce.getCid() == 4001 || pce.getCid() == 4002){
					app.append(" <li class=\"li_box\"><div class=\"check_box\">"+
							"<input onclick=\"clickcheckbox('"+pce.getProductid()+"_"+pce.getCid()+"_"+i+"');\" type=\"checkbox\" value=\""+pce.getProductid()+"_"+pce.getCid()+"_"+i+"\" name=\"selectProduct\" id=\"selectProduct\"/><!-- 公司注册  -->"+pce.getCname()+""+
							"</div><div class=\"select_box\">");
					areaatr =dlareaInfo(i,pce,cityId);
					app.append(areaatr);
					if("代理记账".equals(pce.getCname())){
						appdljz = appendDljz(i,pce);
						app.append(appdljz);
					}else if("'银行开户".equals(pce.getCname())){
						zhaosyhkh = appendZhaosyhkh(i,pce);
						app.append(zhaosyhkh);
					}
					if("代理记账".equals(pce.getCname())||"银行开户".equals(pce.getCname())){
						companyType = dljzCompanyType(i,pce);
						app.append(companyType);
					}
					if("代理记账".equals(pce.getCname())){
						gsgm = gongsGmAppen(i,pce,cityId);
						app.append(gsgm);
					}
					app.append(" </div><div class=\"pri_box red\">￥<span id=\"dlprice_"+i+"\">0</span></div><input type=\"hidden\" name=\"dljzprice_"+i+"\" id=\"dljzprice_"+i+"\"  value=\"\"/></li>");
					app.append("<div class=\"clear\"></div>");
				}
			}else{
				String qit = appenddljzOther(i,pce);
				app.append(qit);
			}
			i++;
		}
		return app.toString();
	}
	
}
