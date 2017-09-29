package com.jixiang.argo.lvzheng.vo;

public class PaymentVo {

	private long orderid;
	private float paynumber;
	private float payfee;
	private float paytotal;
	private int paychannel;
	private String contents;
	
	public long getOrderid() {
		return orderid;
	}
	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}
	
	public float getPaynumber() {
		return paynumber;
	}
	public void setPaynumber(float paynumber) {
		this.paynumber = paynumber;
	}
	public float getPayfee() {
		return payfee;
	}
	public void setPayfee(float payfee) {
		this.payfee = payfee;
	}
	public float getPaytotal() {
		return paytotal;
	}
	public void setPaytotal(float paytotal) {
		this.paytotal = paytotal;
	}
	
	public int getPaychannel() {
		return paychannel;
	}
	public void setPaychannel(int paychannel) {
		this.paychannel = paychannel;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
}
