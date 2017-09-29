package com.jixiang.argo.lvzheng.vo;

import java.util.List;

/**
 * 分类下信息
 * @author wuyin
 *
 */
public class IndustrylVo {
	
	private Integer page;
	private Integer pagerows;
	private List<IndustryDetail> rows;
	private Integer totalrows;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPagerows() {
		return pagerows;
	}
	public void setPagerows(Integer pagerows) {
		this.pagerows = pagerows;
	}
	public List<IndustryDetail> getRows() {
		return rows;
	}
	public void setRows(List<IndustryDetail> rows) {
		this.rows = rows;
	}
	public Integer getTotalrows() {
		return totalrows;
	}
	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
	}

}
