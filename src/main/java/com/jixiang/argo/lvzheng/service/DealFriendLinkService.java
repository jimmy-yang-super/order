package com.jixiang.argo.lvzheng.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jixiang.argo.lvzheng.frame.RSBLL;
import com.jixiang.argo.lvzheng.utils.Constants;
import com.jixiang.argo.lvzheng.utils.KVMap;
import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jixiang.argo.lvzheng.utils.UtilsHelper;
import com.jixiang.argo.lvzheng.vo.FriendLinkVo;
import com.jixiang.argo.lvzheng.vo.OrderInfoVO;
import com.jx.argo.BeatContext;
import com.jx.service.newcore.entity.FriendLinkEntity;

public class DealFriendLinkService {

	private static DealFriendLinkService instance;
	
	private DealFriendLinkService(){}
	
	public static DealFriendLinkService getInstance(){
		if(instance == null){
			instance = new DealFriendLinkService();
		}
		return instance;
	}
	/**
	 * 添加友情链接
	 * @param entity
	 * @return
	 */
	public long addFriendLinkEntity(FriendLinkEntity entity){
		long lid = 0;
		try {
			lid = RSBLL.getstance().getFriendLinkService().addFriendLinkEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lid;
	}
	public FriendLinkEntity getFriendLinkEntityById(long lid){
		FriendLinkEntity  entity = null;
		try {
			entity = RSBLL.getstance().getFriendLinkService().getFriendLinkEntityById(lid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	/**
	 * 删除友情链接
	 * @param lid
	 */
	public void deleteFriendLinkEntityById(long lid){
		try {
			RSBLL.getstance().getFriendLinkService().deleteFriendLinkEntity(lid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新友情链接
	 * @param entity
	 */
	public void updateFriendLinkEntity(FriendLinkEntity entity){
		try {
			RSBLL.getstance().getFriendLinkService().updateFriendLinkEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询进行相关的分页操作
	 * @param condition
	 * @param pageindex
	 * @param pagesize
	 * @param orderby
	 * @return
	 */
	public List<FriendLinkEntity> getListFriendLinkEntityBypage(String condition,int pageindex,int pagesize,String orderby){
		List<FriendLinkEntity> lfes = null;
		try {
			lfes = RSBLL.getstance().getFriendLinkService()
					.getFriendLinkEntity(condition, pageindex, pagesize, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lfes;
	}
	/**
	 * 友情查询不带分页查询
	 * @return
	 */
	public List<FriendLinkEntity> getListFriendLinkEntitys(){
		List<FriendLinkEntity> lefs = null;
		try {
			lefs = RSBLL.getstance().getFriendLinkService().getFriendLinkEntity("", 1, 50, "lid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lefs;
	}
	
	public int getFriendLinkCount(String condition){
		int count = 0;
		try {
			count = RSBLL.getstance().getFriendLinkService().getFriendLinkEntityCount(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public void getFriendLinkList(BeatContext beat,Map<String, String> queryOption,Integer pageno) {
		String paramOneType =  beat.getRequest().getParameter("paramOneType")==null?"":beat.getRequest().getParameter("paramOneType");
		
		if(pageno==0){
			//把请求数据放到session中供翻页使用
			beat.getRequest().getSession().setAttribute("paramOneType", paramOneType);
		}else{
			paramOneType = queryOption.get("paramOneType")==null?"":queryOption.get("paramOneType");
		}
		
 
		String temp_pageindex = "1";
		if(pageno!=0){
			temp_pageindex = pageno.toString();	
		}
		
		if(temp_pageindex==null||temp_pageindex.equals("")) temp_pageindex="1";
		
		//分页属性
		Integer pageindex = Integer.valueOf(temp_pageindex);
		Integer pagesize = Constants.houtai_page_size;
		String orderby = "updatetime desc"; //默认根据最后更新时间排序
		String condition ="";
		if(StringUtil.isEmpty(paramOneType)){
			condition = "inclupage = "+paramOneType;
			beat.getModel().add("showsearch", KVMap.showpage.get(paramOneType));
			beat.getModel().add("showsearchval", paramOneType);
		}else{
			beat.getModel().add("showsearch", KVMap.showpage.get("1"));
			beat.getModel().add("showsearchval", "1");
		}
     	int ordercount = getFriendLinkCount(condition);
    	int pagecount = ordercount%pagesize == 0?ordercount/pagesize:ordercount/pagesize+1;
		
		//获得母订单列表信息
		List<FriendLinkEntity> friendLinkEntity = this.getListFriendLinkEntityBypage(condition, pageindex, pagesize, orderby);
		List<FriendLinkVo> riendLinkVos = converObject(friendLinkEntity);
		//前台页面使用的参数START---------------
		beat.getModel().add("pagecount", pagecount);
		beat.getModel().add("pageIndex", pageindex);
		beat.getModel().add("paramOneType", paramOneType);
		beat.getModel().add("friendLinkEntity", riendLinkVos);
	}

	private List<FriendLinkVo> converObject(List<FriendLinkEntity> friendLinkEntity) {
		List<FriendLinkVo> friendLinkVos = new ArrayList<FriendLinkVo>();
		FriendLinkVo friendLinkVo = null;
		if(StringUtil.isListNull(friendLinkEntity)){
			for(FriendLinkEntity entity : friendLinkEntity){
				friendLinkVo = new FriendLinkVo();
				friendLinkVo.setLid(entity.getLid());
				friendLinkVo.setKeyword(entity.getKeyword());
				friendLinkVo.setLurl(entity.getLurl());
				long addtime = entity.getAddtime();
				friendLinkVo.setAddtime(UtilsHelper.formatDateTostring("yyyy/MM/dd", addtime));
				friendLinkVos.add(friendLinkVo);
			}
		}
		
		return friendLinkVos;
	}
	
	
}
