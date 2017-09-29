package com.jixiang.argo.lvzheng.buz;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jx.service.newcore.contract.IPricesService;
import com.jx.service.newcore.contract.IProductCategoryService;
import com.jx.service.newcore.entity.ProductCategoryEntity;
import com.jx.service.newcore.entity.SorderEntity;

public class PricesBuz {
	public static PricesBuz pb = new PricesBuz();
	private static IPricesService sps = RSBLL.getstance().getPricesService();
	private static IProductCategoryService ipc = RSBLL.getstance().getProductCategoryService();
	public float countByorder(SorderEntity se)throws Exception{
		
		return 0;
	}
	public float countByorder(String productstr,String product)throws Exception{
		float fee = 0;
		JSONArray projo = JSONArray.fromObject(product);
		
		for(int i=0,c=projo.size();i<c;i++){
			
		}
		String[] pro = productstr.split("_");
		long productid = Long.parseLong(pro[0]);
		long procateid = Long.parseLong(pro[1]);
		String condition = "procateid="+procateid;
		ProductCategoryEntity pce = ipc.getProductCategoryEntityById(procateid);
		if(pce != null ){
			int pricetype = pce.getPricetype();
			switch(pricetype){
				case 0:
					break;
				case 1:
					
					break;
				case 3:
					break;
				case 9:
					break;
			}
		}
		sps.getPricelistBypage(condition, 1, 1, "priceid");
		return fee;
	}
}
