package com.jixiang.argo.lvzheng.vo;

import java.util.List;

/**
 * 附件Vo类
 * @author wuyin
 *
 */
public class FileAttachmentVo {

	private String  mingc;
	private List<String> imgurls;
	private String fileIds;
	public String getMingc() {
		return mingc;
	}
	public void setMingc(String mingc) {
		this.mingc = mingc;
	}
	public List<String> getImgurls() {
		return imgurls;
	}
	public void setImgurls(List<String> imgurls) {
		this.imgurls = imgurls;
	}
	public String getFileIds() {
		return fileIds;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	
}
