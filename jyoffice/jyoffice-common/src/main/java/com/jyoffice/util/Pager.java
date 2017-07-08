package com.jyoffice.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pager<T> {

	private Map<String, String> param = new HashMap<String, String>();
	
	private int pageSize = 10;
	
	private long total;
	
	private long countPage = 0;
	
	private List<T> resultList;
	
	private String html;
	
	private int currentPage = 1;

	private int after = 3;// 当前页后面显示分页按钮数
	
	private int before = 4; // 当前页前面显示分页按钮数
	
	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public String getHtml() {
		/*
		 * <div class="page clearfix"> <div class="p-wrap"> <span class="p-num">
		 * <a href="javascript:void(0)" class="pn-prev disabled">上一页</a> <a
		 * href="javascript:void(0)" class="grayr">1</a> <a
		 * href="javascript:void(0)" class="curr">2</a> <a
		 * href="javascript:void(0)" class="grayr">3</a> <b
		 * class="pn-break">...</b> <a href="javascript:void(0)"
		 * class="grayr">12</a> <a href="javascript:void(0)" class="">下一页</a>
		 * </span> </div> </div>
		 */

		String func = "doPager";

		StringBuffer bf = new StringBuffer(
				"<div class=\"page clearfix\"><div class=\"p-wrap\"><span class=\"p-num\">");

		if (total > pageSize) {
			// 进行分页
			if (countPage >= 1) {
				bf.append("<a href='javascript:");
				if (countPage == 1)
					bf.append("void(0);");
				else
					bf.append(func).append("(").append(countPage - 1).append(")");
				bf.append("' class='pn-prev'> 上一页 </a>");
			}

			List<Integer> list = getPage(currentPage, total, countPage);
			for (int i = 0; i < list.size(); i++) {
				bf.append("<a href='javascript:");
				if (list.get(i) == currentPage) {
					bf.append("void(0)' class='curr'>");
				} else {
					bf.append(func).append("(").append(list.get(i).toString()).append(")'").append(" class='grayr'>");
				}
				bf.append(list.get(i).toString());
				bf.append("</a>");
			}

			if (currentPage <= countPage) {
				bf.append("<a href='javascript:");
				if (currentPage == countPage)
					bf.append("void(0);");
				else
					bf.append(func).append("(").append(currentPage + 1).append(")");
				bf.append("' class='grayr'> 下一页 </a>");
			}
		}
		bf.append("</span></div></div>");
		html = bf.toString();
		return html;
	}

	private List<Integer> getPage(int p, long t, long count) {

		List<Integer> list = new ArrayList<Integer>();

		if (count > after + before) {

			int first = 1;
			long end = 1;
			if (p - before < 1) {
				first = 1;
				end = after + before + 1;
			} else if (p + after <= count) {
				first = p - before;
				end = p + after;
			} else {
				first = (int) (count - after - before);
				end = count;
			}
			for (int i = first; i <= end; i++) {
				list.add(i);
			}

		} else {
			for (int i = 1; i <= count; i++) {
				list.add(i);
			}

		}

		return list;
	}
}
