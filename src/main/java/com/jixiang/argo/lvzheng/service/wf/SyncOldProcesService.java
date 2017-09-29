package com.jixiang.argo.lvzheng.service.wf;

import java.util.Date;
import java.util.List;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.frame.service.OrderAssembleService;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jx.service.newcore.entity.SorderChildrenEntity;

/**
 * 同步老流程数据类
 * @author lvzheng-duxf
 */
public class SyncOldProcesService {
	/**
	 * 根据子单ID改变子单的状态为服务完成[新流程点击完成时调用]
	 * @param optid
	 * @param ChildOrderId
	 */
	public static void SynOrderChildState(Long optid,Long orderid,String businessKey){
		try {
			Long childOrderid = SynGetOrderChild(orderid, businessKey);
			if(childOrderid != 0){
				SorderChildrenEntity sce = RSBLL.getstance().getSorderChildrenService().getSorderChildrenEntityByid(childOrderid);
				if(null != sce){
					sce.setCostate(Constants.ORDER_STATE_CHILD_STATE);
					sce.setUpdatetime(new Date().getTime());
					sce.setEmpid(optid);
					RSBLL.getstance().getSorderChildrenService().updateSorderChildrenEntity(sce);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Long SynGetOrderChild(Long orderid,String businessKey){
		Long orderChildId = 0L;
		if(orderid !=0){
			orderChildId = getChildID(orderid,businessKey);
		}
		return orderChildId;
	}
	
	
	
	
	public static Long getChildID(Long orderid,String orderType){
		Long orderChildId = 0L;
		try {
			List<SorderChildrenEntity> sorderchildEList = OrderAssembleService.getInstance().getOrderChildrEntityList("orderid='"+orderid+"'");
			if(null != sorderchildEList && sorderchildEList.size() > 0){
				for(SorderChildrenEntity schildE : sorderchildEList){
					if(orderType.equals(String.valueOf(schildE.getProdcateid()))){
						orderChildId = schildE.getCoid();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderChildId;
	}
}
