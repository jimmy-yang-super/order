package com.jixiang.argo.lvzheng.vo;

/**
 * 主营业务类别VO
 * 
 * @author wuyin
 *
 */
public class BusinessCategoryVo {
	
	private String fdm;// 父代码
	private String dm;// 当前代码
	private String wb;// 文本描述
	
	public String getFdm() {
		return fdm;
	}
	public void setFdm(String fdm) {
		this.fdm = fdm;
	}
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public String getWb() {
		return wb;
	}
	public void setWb(String wb) {
		this.wb = wb;
	}
}
