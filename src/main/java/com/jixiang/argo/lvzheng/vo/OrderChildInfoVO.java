package com.jixiang.argo.lvzheng.vo;


/***
 * 子订单的vo
 * @author lvzheng-duxf
 */
public class OrderChildInfoVO {
	private long orderId;  //母订单id
	private long coid ; //子订单ID
	private String serverName; //服务名称
	private String serverContent; //服务内容
	private String state;         //状态子单服务状态 [0未服务 1服务中 2服务完成 ]
	private float price;        //价格
	private String logInfo;         //日志信息
	
	private String properties;   //子单的属性信息
	
	
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getCoid() {
		return coid;
	}
	public void setCoid(long coid) {
		this.coid = coid;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerContent() {
		return serverContent;
	}
	public void setServerContent(String serverContent) {
		this.serverContent = serverContent;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getLogInfo() {
		return logInfo;
	}
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	
	
	
    
}
