package com.jixiang.argo.lvzheng.vo;

/**
 * 客户对象
 * @author lvzheng-duxf
 */
public class CustomerVO {
	private long userid;
	private String username; // 用户名称
	private String password; // 密码
	private String openid; // 微信公众号
	private String userphone; // 注册电话
	private String email; //
	private String sidnumbers; //
	private int cityid; //
	private String address; //
	private long regtime; // 注册时间
	private String regip; //
	private int userstate; // 状态(0，取消关注。1，关注）
	private long lastuptime; // 最后更新时间
	private String citystr; // 城市名称',
	private long addtime; // 注册时间，long类型',
	private String channel; // 用户来源(1：微信。2：58同城。3：线下推广)',
	private String companyname; // 公司名称',
	private String landlinenumber; // 座机号码',
	private int authenflag;        //认证标识0未认证 1已认证
	
	
	private String cityname;
	private String channelName;
	private String addtimerValue;
	
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUserphone() {
		return userphone;
	}
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSidnumbers() {
		return sidnumbers;
	}
	public void setSidnumbers(String sidnumbers) {
		this.sidnumbers = sidnumbers;
	}
	public int getCityid() {
		return cityid;
	}
	public void setCityid(int cityid) {
		this.cityid = cityid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getRegtime() {
		return regtime;
	}
	public void setRegtime(long regtime) {
		this.regtime = regtime;
	}
	public String getRegip() {
		return regip;
	}
	public void setRegip(String regip) {
		this.regip = regip;
	}
	public int getUserstate() {
		return userstate;
	}
	public void setUserstate(int userstate) {
		this.userstate = userstate;
	}
	public long getLastuptime() {
		return lastuptime;
	}
	public void setLastuptime(long lastuptime) {
		this.lastuptime = lastuptime;
	}
	public String getCitystr() {
		return citystr;
	}
	public void setCitystr(String citystr) {
		this.citystr = citystr;
	}
	public long getAddtime() {
		return addtime;
	}
	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getLandlinenumber() {
		return landlinenumber;
	}
	public void setLandlinenumber(String landlinenumber) {
		this.landlinenumber = landlinenumber;
	}
	public int getAuthenflag() {
		return authenflag;
	}
	public void setAuthenflag(int authenflag) {
		this.authenflag = authenflag;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getAddtimerValue() {
		return addtimerValue;
	}
	public void setAddtimerValue(String addtimerValue) {
		this.addtimerValue = addtimerValue;
	}
	
	
	
	
	
}
