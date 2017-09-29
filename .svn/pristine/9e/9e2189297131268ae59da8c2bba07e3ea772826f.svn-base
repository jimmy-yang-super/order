package com.jixiang.argo.lvzheng.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jixiang.argo.lvzheng.utils.StringUtil;
import com.jx.service.newcore.entity.BusinessLibaryEntity;
import com.jx.service.newcore.entity.KehinfosEntity;
import com.jx.service.newcore.entity.ReginAddressEntity;

public class LogDealService {

	private static LogDealService instance = null;
	
	public static LogDealService getInstance(){
		if(instance == null){
			instance = new LogDealService();
		}
		return instance;
	}

	  private static  Logger logger = null;
	  public LogDealService(){
		  PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("META-INF/log4j.properties"));
		  if(logger == null){
			  logger = LoggerFactory.getLogger(LogDealService.class);
		  }
	  }
	
	public void writerOperatLo(String desc,String nowtime,String operuser,String modifyfield,String oldcont,String newcontent){
	    String appendstr = desc + "   "+ nowtime+"  " + operuser +"    "+ modifyfield+"   " + oldcont +"   "+newcontent;
		logger.info(appendstr);
	}
	/**
	 * 
	 * @param buid
	 * @param newbr
	 * @param nowtime
	 * @param operuser
	 * 
	 */
	public void writeBusinessLog(long buid,BusinessLibaryEntity newbr,String nowtime,String operuser,String type){
		//获得原来的
		BusinessLibaryEntity oldbr = BusinessLibraryService.getInstance().getBusinessLibaryEntity(buid);
		if(oldbr != null){
			if("gsmc".equals(type)){
				//公司类型
				if(!StringUtils.equals(oldbr.getCompanytype()+"", newbr.getCompanytype()+"")){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"公司类型",oldbr.getCompanytype()+"",newbr.getCompanytype()+"");
				}
				//公司名称
				if(!StringUtils.equals(oldbr.getCompanymc(),newbr.getCompanymc())){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"公司名称",oldbr.getCompanymc(),newbr.getCompanymc());
				}
				//注资资本
				if(!StringUtils.equals(oldbr.getZuczb()+"", newbr.getZuczb()+"")){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"注册资本",oldbr.getZuczb()+"",newbr.getZuczb()+"");
				}
				//备选名称
				if(!StringUtils.equals(oldbr.getBeixmc(),newbr.getBeixmc())){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"公司备选名称",oldbr.getBeixmc(),newbr.getBeixmc());
				}
			}
			if("dldz".equals(type)){
				//注册城市
				if(!StringUtils.equals(oldbr.getCityId()+"",newbr.getCityId()+"")){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"注册城市",oldbr.getCityId()+"",newbr.getCityId()+"");
				}
				//注册区域
				if(!StringUtils.equals(oldbr.getAreaid()+"", newbr.getAreaid()+"")){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"注册区域",oldbr.getAreaid()+"",newbr.getAreaid()+"");
				}
				//注册地址
				if(!StringUtils.equals(oldbr.getIszhucdz()+"", newbr.getIszhucdz()+"")){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"注册地址",oldbr.getIszhucdz()+"",newbr.getIszhucdz()+"");
				}
			}
			if("jingyfw".equals(type)){
				//主营业务
				if(!StringUtils.equals(oldbr.getJingyfw(),newbr.getJingyfw())){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"主营业务",oldbr.getJingyfw(),newbr.getJingyfw());
				}
				//业务方向
				if(!StringUtils.equals(oldbr.getZhuyyw(),newbr.getZhuyyw())){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"业务方向",oldbr.getZhuyyw(),newbr.getZhuyyw());
				}
				//经营范围
				if(!StringUtils.equals(oldbr.getQitjyyw(),newbr.getQitjyyw())){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"经营范围",oldbr.getQitjyyw(),newbr.getQitjyyw());
				}
				//制定经营范围
				if(!StringUtils.equals(oldbr.getJingyfw(),newbr.getJingyfw())){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"制定经营范围",oldbr.getJingyfw(),newbr.getJingyfw());
				}
			}
			if("employ".equals(type)){
				//职工总人数
				if(!StringUtils.equals(oldbr.getZhigrs()+"",newbr.getZhigrs()+"")){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"职工总人数",oldbr.getZhigrs()+"",newbr.getZhigrs()+"");
				}
				//本地人数
				if(!StringUtils.equals(oldbr.getBendrs()+"", newbr.getBendrs()+"")){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"本地人数",oldbr.getBendrs()+"",newbr.getBendrs()+"");
				}
				//外地人数
				if(!StringUtils.equals(oldbr.getWaidrs()+"", newbr.getWaidrs()+"")){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"外地人数",oldbr.getWaidrs()+"",newbr.getWaidrs()+"");
				}
				//女性人数
				if(!StringUtils.equals(oldbr.getNvxrs()+"", newbr.getNvxrs()+"")){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"女性人数",oldbr.getNvxrs()+"",newbr.getNvxrs()+"");
				}
			}
			if("other".equals(type)){
				//名称预先核准
				if(!StringUtils.equals(oldbr.getMingcyxhz(),newbr.getMingcyxhz())){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"名称预先核准",oldbr.getMingcyxhz(),newbr.getMingcyxhz());
				}
				//核名账号
				if(!StringUtils.equals(oldbr.getHemgszh(),newbr.getHemgszh())){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"核名账号",oldbr.getHemgszh(),newbr.getHemgszh());
				}
				//核名密码
				if(!StringUtils.equals(oldbr.getHempassword(),newbr.getHempassword())){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"核名密码",oldbr.getHempassword(),newbr.getHempassword());
				}
				//网登公司账户
				if(!StringUtils.equals(oldbr.getWangdgszh(),newbr.getWangdgszh())){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"网登公司账户",oldbr.getWangdgszh(),newbr.getWangdgszh());
				}
				//网登密码
				if(!StringUtils.equals(oldbr.getWangdpassword(),newbr.getWangdpassword())){
					writerOperatLo("企业ID:"+buid,nowtime,operuser,"网登密码",oldbr.getWangdpassword(),newbr.getWangdpassword());
				}
			}
			//是否董事会
			if(!StringUtils.equals(oldbr.getIsdengsh()+"",newbr.getIsdengsh()+"")){
				writerOperatLo("企业ID:"+buid,nowtime,operuser,"是否董事会",oldbr.getIsdengsh()+"",newbr.getIsdengsh()+"");
			}
			//是否监事会
			if(!StringUtils.equals(oldbr.getIsjiash()+"",newbr.getIsjiash()+"")){
				writerOperatLo("企业ID:"+buid,nowtime,operuser,"是否监事会址",oldbr.getIsjiash()+"",newbr.getIsjiash()+"");
			}
			
		}
	}
	/**
	 * @param buid
	 * @param newreg
	 * @param nowtime
	 * @param operuser
	 */
	public void writeRegaddressLog(long buid,ReginAddressEntity newreg,String nowtime,String operuser){
		BusinessLibaryEntity oldbr = BusinessLibraryService.getInstance().getBusinessLibaryEntity(buid);
		if(oldbr != null){
			long regid = oldbr.getReginaddressId();
			ReginAddressEntity oldreg = BusinessLibraryService.getInstance().getReginAddressEntityById(regid);
			if(oldreg != null){
				//自有地址
				if(!StringUtils.equals(oldreg.getZiydz(),newreg.getZiydz())){
					writerOperatLo("注册地址ID:"+oldreg.getReginId(),nowtime,operuser,"自有地址",oldreg.getZiydz(),newreg.getZiydz());
				}
				//所属街乡
				if(!StringUtils.equals(oldreg.getSuosjx(),newreg.getSuosjx())){
					writerOperatLo("注册地址ID:"+oldreg.getReginId(),nowtime,operuser,"所属街乡",oldreg.getSuosjx(),newreg.getSuosjx());
				}
				//产权主体
				if(!StringUtils.equals(oldreg.getChanqzt(),newreg.getChanqzt())){
					writerOperatLo("注册地址ID:"+oldreg.getReginId(),nowtime,operuser,"产权主体",oldreg.getChanqzt(),newreg.getChanqzt());
				}
				//产权人/产权单位
				if(!StringUtils.equals(oldreg.getChanqr(),newreg.getChanqr())){
					writerOperatLo("注册地址ID:"+oldreg.getReginId(),nowtime,operuser,"产权主体",oldreg.getChanqr(),newreg.getChanqr());
				}
				//实际办公地址
				if(!StringUtils.equals(oldreg.getShijbgdz(),newreg.getShijbgdz())){
					writerOperatLo("注册地址ID:"+oldreg.getReginId(),nowtime,operuser,"实际办公地址",oldreg.getShijbgdz(),newreg.getShijbgdz());
				}
				//快递地址
				if(!StringUtils.equals(oldreg.getKuaiddz(),newreg.getKuaiddz())){
					writerOperatLo("注册地址ID:"+oldreg.getReginId(),nowtime,operuser,"快递地址",oldreg.getKuaiddz(),newreg.getKuaiddz());
				}
				//收件人
				if(!StringUtils.equals(oldreg.getShoujr(),newreg.getShoujr())){
					writerOperatLo("注册地址ID:"+oldreg.getReginId(),nowtime,operuser,"收件人",oldreg.getShoujr(),newreg.getShoujr());
				}
				//联系电话
				if(!StringUtils.equals(oldreg.getLianxdh(),newreg.getLianxdh())){
					writerOperatLo("注册地址ID:"+oldreg.getReginId(),nowtime,operuser,"联系电话",oldreg.getLianxdh(),newreg.getLianxdh());
				}
				//地址合作商
				if(!StringUtils.equals(oldreg.getAttribute1(),newreg.getAttribute1())){
					writerOperatLo("注册地址ID:"+oldreg.getReginId(),nowtime,operuser,"地址合作商",oldreg.getAttribute1(),newreg.getAttribute1());
				}
				//地址类型
				if(!StringUtils.equals(oldreg.getAttribute2(),newreg.getAttribute2())){
					writerOperatLo("注册地址ID:"+oldreg.getReginId(),nowtime,operuser,"地址类型",oldreg.getAttribute2(),newreg.getAttribute2());
				}
				//房间号
				if(!StringUtils.equals(oldreg.getFangjh(),newreg.getFangjh())){
					writerOperatLo("注册地址ID:"+oldreg.getReginId(),nowtime,operuser,"地址类型",oldreg.getFangjh(),newreg.getFangjh());
				}
			}
		}
	}
	/**
	 * @param buid
	 * @param newkeh
	 * @param nowtime
	 * @param operuser
	 */
	public void writrKehinfoLog(long buid,KehinfosEntity newkeh,String nowtime,String operuser,int rotetype){
		//法定代表人
		if(rotetype == 2){
			writeKehLogByRoletype(buid,newkeh,2,nowtime,operuser,"法定代表人");
		}
		//经理
		if(rotetype == 4){
			writeKehLogByRoletype(buid,newkeh,4,nowtime,operuser,"经理");
		}
		//财务负责人
		if(rotetype == 5){
			writeKehLogByRoletype(buid,newkeh,5,nowtime,operuser,"财务负责人");
		}
		//企业联系人
		if(rotetype == 6){
			writeKehLogByRoletype(buid,newkeh,6,nowtime,operuser,"企业联系人");
		}
		//Ukey管理员1
		if(rotetype == 7){
			writeKehLogByRoletype(buid,newkeh,7,nowtime,operuser,"Ukey管理员1");
		}
		//Ukey管理员2
		if(rotetype == 8){
			writeKehLogByRoletype(buid,newkeh,8,nowtime,operuser,"Ukey管理员2");
		}
		//董事会主席 10
		if(rotetype == 10){
			writeKehLogByRoletype(buid,newkeh,10,nowtime,operuser,"董事会主席");
		}
		//监事主席 11
		if(rotetype == 11){
			writeKehLogByRoletype(buid,newkeh,11,nowtime,operuser,"监事主席");
		}
		List<KehinfosEntity> khlis = null;
		//自然人股东0
		if(rotetype == 0){
			khlis =  BusinessLibraryService.getInstance().getKehByRoletypeObj(buid, 0);
			if(StringUtil.isListNull(khlis)){
				writeKehinfoByList(khlis,newkeh,nowtime,operuser,"自然人股东");
			}
		}
		//法人股东1
		if(rotetype == 1){
			khlis =  BusinessLibraryService.getInstance().getKehByRoletypeObj(buid, 1);
			if(StringUtil.isListNull(khlis)){
				writeKehinfoByList(khlis,newkeh,nowtime,operuser,"法人股东");
			}
		}
        //董事 3
        if(rotetype == 3){
        	khlis =  BusinessLibraryService.getInstance().getKehByRoletypeObj(buid, 3);
        	if(StringUtil.isListNull(khlis)){
        		writeKehinfoByList(khlis,newkeh,nowtime,operuser,"董事");
        	}
        }
        //监事 9
        if(rotetype == 9){
        	khlis =  BusinessLibraryService.getInstance().getKehByRoletypeObj(buid, 9);
        	if(StringUtil.isListNull(khlis)){
        		writeKehinfoByList(khlis,newkeh,nowtime,operuser,"监事");
        	}
        }
	}
	
	public void writeKehinfoByList(List<KehinfosEntity> khlis,KehinfosEntity newkeh,String nowtime,String operuser,String mingc){
		for(KehinfosEntity olkeh : khlis){
			if(StringUtils.equals(olkeh.getKehid()+"",newkeh.getKehid()+"")){
				writeKeyLogByObject(olkeh,newkeh,nowtime,operuser,mingc);
			}
		}
	}
	
	public void writeKeyLogByObject(KehinfosEntity oldkeh,KehinfosEntity newkeh,String nowtime,String operuser,String mingc){
		 //用户名
		 if(!StringUtils.equals(oldkeh.getUsername(),newkeh.getUsername())){
			 writerOperatLo("客户ID："+oldkeh.getKehid(),nowtime,operuser,mingc+"姓名",oldkeh.getUsername(),newkeh.getUsername());
		 }
		 //手机号
		 if(!StringUtils.equals(oldkeh.getPhone(),newkeh.getPhone())){
			 writerOperatLo("客户ID："+oldkeh.getKehid(),nowtime,operuser,mingc+"手机号",oldkeh.getPhone(),newkeh.getPhone());
		 }
		 if(oldkeh.getChuze() != newkeh.getChuze()){
		     writerOperatLo("客户ID："+oldkeh.getKehid(),nowtime,operuser,mingc+"出资额",oldkeh.getChuze()+"",newkeh.getChuze()+"");
	     }
		 //身份证号
		 if(!StringUtils.equals(oldkeh.getIdcard(),newkeh.getIdcard())){
			 writerOperatLo("客户ID："+oldkeh.getKehid(),nowtime,operuser,mingc+"身份证号",oldkeh.getIdcard(),newkeh.getIdcard());
		 }
		 //附件
		 if(!StringUtils.equals(oldkeh.getGudsfzfyj(),newkeh.getGudsfzfyj())){
			 writerOperatLo("客户ID："+oldkeh.getKehid(),nowtime,operuser,mingc+"身份证复印件附件",oldkeh.getGudsfzfyj(),newkeh.getGudsfzfyj());
		 }
		 if(!StringUtils.equals(oldkeh.getFaddbrshzfyj(),newkeh.getFaddbrshzfyj())){
			 writerOperatLo("客户ID："+oldkeh.getKehid(),nowtime,operuser,mingc+"身份证复印件附件",oldkeh.getFaddbrshzfyj(),newkeh.getFaddbrshzfyj());
		 }
	}
	
	public void writeKehLogByRoletype(long buid,KehinfosEntity newkeh,int roletype,String nowtime,String operuser,String mingc){
		 KehinfosEntity oldkeh = BusinessLibraryService.getInstance().getKehinfosEntity2Busid2role(buid, roletype);
		 if(oldkeh != null){
			 //用户名
			 if(!StringUtils.equals(oldkeh.getUsername(), newkeh.getUsername())){
				 writerOperatLo("客户ID："+oldkeh.getKehid(),nowtime,operuser,mingc+"姓名",oldkeh.getUsername(),newkeh.getUsername());
			 }
			 //手机号
			 if(!StringUtils.equals(oldkeh.getPhone(),newkeh.getPhone())){
				 writerOperatLo("客户ID："+oldkeh.getKehid(),nowtime,operuser,mingc+"手机号",oldkeh.getPhone(),newkeh.getPhone());
			 }
			 //身份证号
			 if(!StringUtils.equals(oldkeh.getIdcard(),newkeh.getIdcard())){
				 writerOperatLo("客户ID："+oldkeh.getKehid(),nowtime,operuser,mingc+"身份证号",oldkeh.getIdcard(),newkeh.getIdcard());
			 }
			 //附件
			 if(!StringUtils.equals(oldkeh.getGudsfzfyj(),newkeh.getGudsfzfyj())){
				 writerOperatLo("客户ID："+oldkeh.getKehid(),nowtime,operuser,mingc+"身份证复印件附件",oldkeh.getGudsfzfyj(),newkeh.getGudsfzfyj());
			 }
			 if(!StringUtils.equals(oldkeh.getFaddbrshzfyj(),newkeh.getFaddbrshzfyj())){
				 writerOperatLo("客户ID："+oldkeh.getKehid(),nowtime,operuser,mingc+"身份证复印件附件",oldkeh.getFaddbrshzfyj(),newkeh.getFaddbrshzfyj());
			 }
		 }
	}
}
