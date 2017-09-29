package com.jixiang.argo.lvzheng.service.impl;

import java.util.List;
import java.util.Map;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.service.OnlineCsService;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jx.argo.BeatContext;
import com.jx.blackface.messagecenter.core.entity.CallEntity;

public class OnlineCsServiceImpl implements OnlineCsService {

	@Override
	public void getContactList(BeatContext beat, Map<String, String> queryOption, Integer pageno) {
		// TODO Auto-generated method stub
		long empid = UtilsHelper.getLoginId(beat);
		if(empid==0) return;
		Integer pageindex =  1;
		if(pageno!=0){
			pageindex = pageno;	
		}
		Integer pagesize = Constants.houtai_page_size;
		
		try {
			List<CallEntity>  lce =RSBLL.getstance().getICallService().getCallListByEmp(empid, pageindex, pagesize);
			if(lce!=null&&lce.size()>0){
				beat.getModel().add("calllist", lce);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
