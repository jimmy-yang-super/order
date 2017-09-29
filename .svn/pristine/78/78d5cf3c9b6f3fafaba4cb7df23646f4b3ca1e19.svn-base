package com.jixiang.argo.lvzheng.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.service.AddOrderRealService;
import com.jixiang.argo.lvzheng.service.RealBusinessService;
import com.jx.service.newcore.entity.OrderInfoEntity;
import com.jx.service.newcore.entity.ProductPropertiesEntity;
import com.jx.service.newcore.entity.SorderChildrenEntity;
import com.jx.service.newcore.entity.SorderEntity;
import com.jx.service.newcore.entity.SorderSuperEntity;

/**
 * 老系统（t_order）迁移到老系统
 * @author wuyin
 *
 */
public class Oldsys2newsysdbUtil {

	public static void main(String[] args) {
		Oldsys2newsysdbUtil uti = new Oldsys2newsysdbUtil();
		uti.olddb2newdbOrder();
	}
	
	public void olddb2newdbOrder(){
		//获得老系统数据
		SorderSuperEntity superorder = null;
		SorderEntity sorder = null;
		SorderChildrenEntity schild = null;
		try {
			List<OrderInfoEntity> lis = RSBLL.getstance().getOrderService().getOrderListBypage("", 1, 5, "orderid");
			if(StringUtil.isListNull(lis)){
				JSONObject obj = null;
				JSONArray arrey = null;
				for(OrderInfoEntity orderinfo : lis){
					arrey = new JSONArray();
					//保存t_super_sorder
					superorder = new SorderSuperEntity();
					superorder.setUser_id(orderinfo.getUserid());
					superorder.setPhone(orderinfo.getOrderphone());
					superorder.setDescription(orderinfo.getOrdercontent());
					superorder.setOrderstate(orderinfo.getOrderstate());
					superorder.setContract_no(orderinfo.getContract());
					int orderstatus = orderinfo.getOrderstate();
				
					superorder.setPostime(orderinfo.getCreatetime());
					superorder.setUpdatetime(orderinfo.getUpdatetime());
					superorder.setTotalmoney(orderinfo.getOrderprice());
					//保存t_sorder_super
					Long superId = RealBusinessService.getInstance().saveSorderSuperEntity(superorder);
					sorder = new SorderEntity();
					sorder.setSuperid(superId);
					int productId = orderinfo.getOrdertype();
					
					String companytype = orderinfo.getCompanytype()+"";
					
					if(StringUtil.isEmpty(companytype)){
						obj = new JSONObject();
						String type ="";
						if(companytype.equals("1")){
							//内资公司
							type="companytype";
						}
						if(companytype.equals("2")){
							//外资公司
							type="waicompanytype";
						}
						ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType(type).get(0);
					    if(qt != null){
					    	obj = new JSONObject();
					    	obj.put("propertyid", qt.getProid()+"");
							obj.put("propertyval", qt.getType());
					    	arrey.add(obj);
					    }
					}
					String citity = orderinfo.getOrdercity()+"";
					if(StringUtil.isEmpty(citity)){
						obj = new JSONObject();
						obj.put("propertyid", "city");
						obj.put("propertyval", citity);
						arrey.add(obj);
					}
					String citityarea = orderinfo.getOrderlocal()+"";
			        if(StringUtil.isEmpty(citityarea)){
			        	obj = new JSONObject();
			        	obj.put("propertyid", "area");
						obj.put("propertyval", citityarea);
						arrey.add(obj);
					}
			    	//印章服务价格
					float yinzhangcost = orderinfo.getYinzhangcost();
					if(yinzhangcost != 0){
						ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("yinzhangcost").get(0);
					    if(qt != null){
					    	obj = new JSONObject();
					    	obj.put("propertyid", qt.getProid()+"");
							obj.put("propertyval", yinzhangcost+"");
					    	arrey.add(obj);
					    }
					}
					//是否印章服务
					int isyinzhang = orderinfo.getIsyinzhang();
					if(isyinzhang != 0){
						ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("isyinzhang").get(0);
					    if(qt != null){
					    	obj = new JSONObject();
					    	obj.put("propertyid", qt.getProid()+"");
							obj.put("propertyval", isyinzhang+"");
					    	arrey.add(obj);
					    }
					}
					//上门服务地址
					String serivceaddress = orderinfo.getSerivceaddress();
					if(StringUtil.isEmpty(serivceaddress)){
						ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("serivceaddress").get(0);
					    if(qt != null){
					    	obj = new JSONObject();
					    	obj.put("propertyid", qt.getProid()+"");
							obj.put("propertyval", serivceaddress);
					    	arrey.add(obj);
					    }
					}
					//公司行业
					int companyhy = orderinfo.getCompanyhy();
					if(companyhy != 0){
					    ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("companyhy").get(0);
					    if(qt != null){
					    	obj = new JSONObject();
					    	obj.put("propertyid", qt.getProid()+"");
							obj.put("propertyval", companyhy+"");
					    	arrey.add(obj);
					    }
					}
					//是否核实地址(1,是。2否)
					int isaddresscheck = orderinfo.getIsaddresscheck();
					if(isaddresscheck != 0){
						 ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("isaddresscheck").get(0);
						    if(qt != null){
						    	obj = new JSONObject();
						    	obj.put("propertyid", qt.getProid()+"");
								obj.put("propertyval", isaddresscheck+"");
						    	arrey.add(obj);
						    }
					}
					//是否核实公司名称(1是。2否)'
					int isnamecheck = orderinfo.getIsnamecheck();
					if(isnamecheck != 0){
						 ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("isnamecheck").get(0);
						    if(qt != null){
						    	obj = new JSONObject();
						    	obj.put("propertyid", qt.getProid()+"");
								obj.put("propertyval", isnamecheck+"");
						    	arrey.add(obj);
						    }
					}
					//执照号
					String zhizhaocode = orderinfo.getZhizhaocode();
					if(StringUtil.isEmpty(zhizhaocode)){
						ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("zhizhaocode").get(0);
					    if(qt != null){
					    	obj = new JSONObject();
					    	obj.put("propertyid", qt.getProid()+"");
							obj.put("propertyval", zhizhaocode);
					    	arrey.add(obj);
					    }
					}
					//变更类型
					int biangengtype = orderinfo.getBiangengtype();
					if(biangengtype != 0){
						ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("biangengtype").get(0);
					    if(qt != null){
					    	obj = new JSONObject();
					    	obj.put("propertyid", qt.getProid()+"");
							obj.put("propertyval", biangengtype+"");
					    	arrey.add(obj);
					    }
					}
					//上门服务时间
					String servicetime = orderinfo.getServicetime();
					if(StringUtil.isEmpty(servicetime)){
						ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("servicetime").get(0);
					    if(qt != null){
					    	obj = new JSONObject();
					    	obj.put("propertyid", qt.getProid()+"");
							obj.put("propertyval", servicetime);
					    	arrey.add(obj);
					    }
					}
					//快递单号
					String postcode = orderinfo.getPostcode();
					if(StringUtil.isEmpty(postcode)){
						ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("postcode").get(0);
					    if(qt != null){
					    	obj = new JSONObject();
					    	obj.put("propertyid", qt.getProid()+"");
							obj.put("propertyval", postcode);
					    	arrey.add(obj);
					    }
					}
					//快递公司
					String postname = orderinfo.getPostname();
					if(StringUtil.isEmpty(postname)){
						ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("postname").get(0);
					    if(qt != null){
					    	obj = new JSONObject();
					    	obj.put("propertyid", qt.getProid()+"");
							obj.put("propertyval", postname);
					    	arrey.add(obj);
					    }
					}
					if(orderstatus == 4 || orderstatus == 5){
						//表示 4 待付款  5 已付款
						superorder.setPaystate(orderstatus);
					}
					if(productId == 1101){
						//默认赠送3个月代理记账
						giveService(orderinfo,superId,arrey);
					}
				
					//是否记账',(1是。2否)
					int isjizhang = orderinfo.getIsjizhang();
					if(isjizhang != 0){
						 ProductPropertiesEntity qt = AddOrderRealService.getInstance().getCompanyType("isjizhang").get(0);
						    if(qt != null){
						    	obj = new JSONObject();
						    	obj.put("propertyid", qt.getProid()+"");
								obj.put("propertyval", isjizhang+"");
						    	arrey.add(obj);
						    }
					}
				
					//记账类别
					String properties ="";
					int jizhangtype = orderinfo.getJizhangtype();
					if(jizhangtype !=0){
						String proId ="";
						 if(jizhangtype == 1){
							 proId = 32+"";
						 }else if(jizhangtype == 2){
							 proId = 57+"";
						 }else if (jizhangtype == 3){
							 proId = 33+"";
						 }else{
							 proId = 32+"";
						 }
						 if(proId != ""){
							obj = new JSONObject();
					    	obj.put("propertyid", proId);
							obj.put("propertyval", "bookOrder");
					    	arrey.add(obj);
						 }
						 properties = proId;
					}
					sorder.setProductid(productId);
					sorder.setUserid(orderinfo.getUserid());
					if(orderstatus == 4 || orderstatus == 5){
						//表示 4 待付款  5 已付款
						sorder.setPaystate(orderstatus);
					}
					if(orderstatus == 10){
						sorder.setServerstate(orderstatus);
					}
					sorder.setDescription(orderinfo.getOrdercontent());
					sorder.setPrepaidamount(orderinfo.getBookfee());
					sorder.setTotalmoney(orderinfo.getOrderprice());
					sorder.setContract(orderinfo.getContract());
					sorder.setCompanyname(orderinfo.getCompanyname());
					sorder.setPostime(orderinfo.getCreatetime());
					sorder.setUpdatetime(orderinfo.getUpdatetime());
					sorder.setOrderform(orderinfo.getOrderchanel());
					sorder.setGvreason(orderinfo.getGiveupreson());
					sorder.setCurrentempid(orderinfo.getCurrentempid());
					sorder.setKefuid(orderinfo.getKefuid());
					sorder.setEmpid(orderinfo.getFawuid());
					sorder.setOrdercity(orderinfo.getOrdercity());
					int status = orderinfo.getOrderstate();
					String service = orderinfo.getBuyservicestr();
					String newservice ="";
					if(productId == 1101){
						if(StringUtil.isEmpty(service)){
							//判断是否跨单
							//该下属的类型  3
							String childinfo = KVMap.operation.get(1101+"");
							String[] strt = childinfo.split(",");
							List<String> listmp = new ArrayList<String>();
							for(String st :strt ){
								listmp.add(st);
							}
							String[] tmp = service.split(",");
							for(String tp : tmp){
								if(listmp.contains(tp)){
									newservice = newservice+tp +",";
								}else{
									status = 3;
								}
							}
						}else{
							newservice ="1001";
						}
					}
					if(productId == 1102){
						//公司表更
						if(!StringUtil.isEmpty(service)){
							newservice ="2001";
						}
					}
					if(productId == 1104){
						//商标注册
						if(!StringUtil.isEmpty(service)){
							newservice ="3001";
						}
					}
					if(productId == 1105){
						//代理记账
						if(!StringUtil.isEmpty(service)){
							newservice ="4001";
						}
					}
					sorder.setOrderstate(status);
					//保存订单
					Long sorderId = RealBusinessService.getInstance().saveSorederEntity(sorder);
					//保存子订单
					if(newservice !=""){
						if(newservice.indexOf(",")> -1){
							newservice = newservice.substring(0, newservice.lastIndexOf(","));
						}
						service = newservice;
					}
					if(StringUtil.isEmpty(service)){
						String [] sersp = service.split(",");
						for(int i =0;i < sersp.length;i++){
							int code = Integer.parseInt(sersp[i]);
							schild = new SorderChildrenEntity();
							//类别： code   商品Id productId
							float singleprice = 0;
							
							String city = orderinfo.getOrdercity()+"";
							String area = orderinfo.getOrderlocal()+"";
							String cateId = code +"";
							
							if(productId == 1101){
								//公司注册
								if(code == 1001 ){
									code = 1001;
									singleprice = 598;   
								}else if(code == 1002){
									code = 1002;
									singleprice = AddOrderRealService.getInstance().getPriceBycondition(productId+"", cateId, area, "");
								}else{
									  code = 10013;//表示其他
									  ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("qitmc").get(0);
				             		   if(qtc != null){
				             	    	   JSONObject qitobj = new JSONObject();
				             	    	   qitobj.put("propertyid", qtc.getProid()+"");
				             	    	   qitobj.put("propertyval",KVMap.serviemap.get(code+""));
				             	    	   arrey.add(qitobj);
				             	       }
				             		  singleprice = 598;    
								}
							}
							
							if(productId == 1102){
								//公司变更
								code = code;
								singleprice = AddOrderRealService.getInstance().getPriceBycondition(productId+"", cateId, city, "");
							}
							//商标注册
							if(productId == 1104){
								if(code == 3001 || code == 3002 || code == 3003 || code == 3004){
									code = 3001;
									singleprice = 3800;
									ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("bigtradeCount").get(0);
									if (qtc != null) {
										JSONObject qitobj = new JSONObject();
										qitobj.put("propertyid", qtc.getProid() + "");
										qitobj.put("propertyval", "4");
										arrey.add(qitobj);
									}
								}
							}
							//代理记账
							if(productId == 1105){
								if(code == 1003){
									code = 4003;
									singleprice = AddOrderRealService.getInstance().getPriceBycondition(productId+"", cateId, city, properties);
								}
								if(code == 4001){
									code = 4001;
									singleprice = AddOrderRealService.getInstance().getPriceBycondition(productId+"", cateId, city, properties);
								}
								if(code == 1004){
									//保存两条记录
									String codestr = "4004,10014";
									saverealService(orderinfo,codestr,sorderId,status,arrey);
									continue;
								}
								if(code == 1005){
									//保存两条记录
									String codestr = "4005,10015";
									saverealService(orderinfo,codestr,sorderId,status,arrey);
									continue;
								}
								if(code == 1009 || code == 1010){
									 code = 10016;
									 ProductPropertiesEntity qtc = AddOrderRealService.getInstance().getCompanyType("qitmc").get(0);
				             		   if(qtc != null){
				             	    	   JSONObject qitobj = new JSONObject();
				             	    	   qitobj.put("propertyid", qtc.getProid()+"");
				             	    	   qitobj.put("propertyval",KVMap.serviemap.get(code+""));
				             	    	   arrey.add(qitobj);
				             	       }
				             		  singleprice = 900;
								}
								
							}
							schild.setPropertid(arrey.toString());
							schild.setMinprice(singleprice);
							schild.setPrice(singleprice);
							schild.setOrderid(sorderId);
							schild.setCostate(status);
							schild.setEmpid(orderinfo.getFawuid());
							schild.setProdcateid(code);//类别
							schild.setUpdatetime(orderinfo.getUpdatetime());
							
							RealBusinessService.getInstance().saveSorderhildEntity(schild);
						}
					}
				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存相关服务
	 * @param orderinfo
	 * @param codestr
	 * @param sorderId
	 */
	private void saverealService(OrderInfoEntity orderinfo, String codestr,Long sorderId,int status,JSONArray arrey) {
		SorderChildrenEntity schild = null;
		String[] str = codestr.split(",");
		for(String tmp : str){
			int code = Integer.parseInt(tmp);
			schild = new SorderChildrenEntity();
			float singleprice = 0;
			String city = orderinfo.getOrdercity()+"";
			String productId = orderinfo.getOrdertype()+"";
			String cateId = code +"";
			singleprice = AddOrderRealService.getInstance().getPriceBycondition(productId, cateId, city, "");
			schild.setMinprice(singleprice);
			schild.setPrice(singleprice);
			schild.setPropertid(arrey.toString());
			schild.setOrderid(sorderId);
			schild.setCostate(status);
			schild.setEmpid(orderinfo.getFawuid());
			schild.setProdcateid(code);//类别
			schild.setUpdatetime(orderinfo.getUpdatetime());
			
			RealBusinessService.getInstance().saveSorderhildEntity(schild);
		}
	}

	/**
	 * 添加三个月代理记账
	 * @param orderinfo
	 */
	public void giveService(OrderInfoEntity orderinfo,long superid,JSONArray arry){
		SorderEntity sorder = new SorderEntity();
		//代理记账
		sorder.setProductid(1105);
		sorder.setUserid(orderinfo.getUserid());
		int orderstatus = orderinfo.getOrderstate();
		sorder.setOrderstate(orderstatus);
		if(orderstatus == 4 || orderstatus == 5){
			//表示 4 待付款  5 已付款
			sorder.setPaystate(orderstatus);
		}
		if(orderstatus == 10){
			sorder.setServerstate(orderstatus);
		}
		sorder.setSuperid(superid);
		sorder.setDescription(orderinfo.getOrdercontent());
		sorder.setPrepaidamount(orderinfo.getBookfee());
		sorder.setTotalmoney(orderinfo.getOrderprice());
		sorder.setContract(orderinfo.getContract());
		sorder.setCompanyname(orderinfo.getCompanyname());
		sorder.setPostime(orderinfo.getCreatetime());
		sorder.setUpdatetime(orderinfo.getUpdatetime());
		sorder.setOrderform(orderinfo.getOrderchanel());
		sorder.setGvreason(orderinfo.getGiveupreson());
		sorder.setCurrentempid(orderinfo.getCurrentempid());
		sorder.setKefuid(orderinfo.getKefuid());
		sorder.setEmpid(orderinfo.getFawuid());
		sorder.setOrdercity(orderinfo.getOrdercity());
		//保存订单
		Long sorderId = RealBusinessService.getInstance().saveSorederEntity(sorder);
		//保存子单
		SorderChildrenEntity schild = new SorderChildrenEntity();
		float singleprice = 0;
		schild.setMinprice(singleprice);
		schild.setPrice(singleprice);
		//添加三个月代理记账
		JSONObject obj = new JSONObject();
		obj.put("propertyid", "32");
		obj.put("propertyval", "bookOrder");
		arry.add(obj);
		schild.setPropertid(arry.toString());
		schild.setOrderid(sorderId);
		schild.setCostate(orderinfo.getOrderstate());
		schild.setEmpid(orderinfo.getFawuid());
		schild.setProdcateid(4001);//类别 代理记账
		schild.setUpdatetime(orderinfo.getUpdatetime());
		RealBusinessService.getInstance().saveSorderhildEntity(schild);
	}

}
