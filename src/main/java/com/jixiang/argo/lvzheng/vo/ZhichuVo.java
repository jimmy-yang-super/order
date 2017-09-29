package com.jixiang.argo.lvzheng.vo;

public class ZhichuVo {

	private long orderid;
	private long empid;
	private float zhichu;
	private int feetype;
	private String content;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getOrderid() {
		return orderid;
	}
	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}
	public long getEmpid() {
		return empid;
	}
	public void setEmpid(long empid) {
		this.empid = empid;
	}
	public float getZhichu() {
		return zhichu;
	}
	public void setZhichu(float zhichu) {
		this.zhichu = zhichu;
	}
	public int getFeetype() {
		return feetype;
	}
	public void setFeetype(int feetype) {
		this.feetype = feetype;
	}
	
	
}
