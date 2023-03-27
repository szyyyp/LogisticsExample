package org.example.pageModel;

import java.io.Serializable;
/**
 * datagrid控件发送请求参数的封装
 * @author zlw
 * @date 2015-4-12
 */
public class PageHelper implements Serializable {
	private static final long serialVersionUID = 7232798260610351343L;
	private int page; //当前页,名字必须为page
	private int rows ; //每页大小,名字必须为rows
	private String sort; //排序字段
	private String order; //排序规则
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + page;
		result = prime * result + rows;
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageHelper other = (PageHelper) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (page != other.page)
			return false;
		if (rows != other.rows)
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		return true;
	}
	
	

}
