/**
 * Copyright 2008 - 2010 Simcore.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.project.framework.controller;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * zpr
 */
public class Page<T> implements Serializable {
	
	private static final long serialVersionUID = 3412800354094709366L;
	public static final int DEFAULT_PAGE_SIZE=20;//默认每页数量
	public static final String ORDER_ASC = "asc";
	public static final String ORDER_DESC = "desc";
	/**
	 * 字段排序映射，key:字段名,value:排序方向
	 */
	private Map<String, String> orderMap = null;
	
	/** 当前页第一条数据的位置,从0开始 */
	private long page = 1;

	/** 每页的记录数 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 是否自动请求总记录数
	 */
	protected boolean autoCount = true;

	/**
	 * 总纪录数，当autoCount为true时有效。
	 * <p>
	 * 默认值为-1
	 */
	protected long total = -1;

	/**
	 * 页内记录
	 */
	protected List<T> rows;

	// -- 构造函数 --//
	public Page() {
		rows = Collections.emptyList();
	}
	public Page(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}
	/**
	 * 获得当前页的起始记录序号,序号从0开始，默认为0
	 */
	public long getStartIndex() {
		return page < 1 ? 0 : (page - 1) * pageSize;
	}

	/**
	 * 获得每页的记录数量,默认为20.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 获得字段排序映射容器。
	 * 
	 * @return the orderMap
	 */
	public Map<String, String> getOrderMap() {
		return orderMap;
	}

	/**
	 * 添加一个字段的排序映射
	 * 
	 * @param propertyName
	 * @param OrderType
	 */
	public void addOrder(String propertyName, String orderType) {
		if (orderMap == null)
			orderMap = new LinkedHashMap<String, String>();
		orderMap.put(propertyName, orderType);
	}
	/**
	 * 添加一个字段的排序映射
	 * 
	 * @param propertyName
	 * @param OrderType
	 */
	public void addAscOrder(String propertyName) {
		if (orderMap== null)
			orderMap = new LinkedHashMap<String, String>();
		orderMap.put(propertyName,ORDER_ASC);
	}
	/**
	 * 添加一个字段的排序映射
	 * 
	 * @param propertyName
	 * @param OrderType
	 */
	public void addDescOrder(String propertyName) {
		if (orderMap== null)
			orderMap = new LinkedHashMap<String, String>();
		orderMap.put(propertyName,ORDER_DESC);
	}

	/**
	 * 是否进行排序
	 * 
	 * @return
	 */
	public boolean isOrder() {
		return orderMap != null && !orderMap.isEmpty();
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	/**
	 * 通过是否进行总纪录数查询得到默认分页
	 * 
	 * @param autoCount
	 * @return
	 */
	public Page<T> autoCount(final boolean autoCount) {
		setAutoCount(autoCount);
		return this;
	}

	// -- 访问查询结果方法 --//
	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setRows(final List<T> rows) {
		this.rows = rows;
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotal(final long total) {
		this.total = total;
	}
	/**
	 * 根据页容量(pageSize)与总记录数(totalCount)计算总页数(totalPage), 默认值为-1.
	 */
	public long getTotalPage() {
		if (total < 0) {
			return -1;
		}
		long count = total / getPageSize();
		if (total % getPageSize() > 0) {
			count++;
		}
		return count;
	}
	// --页操作方法 --//
	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return page <= this.getTotalPage() - 1;
	}
	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (page - 1 >= 1);
	}
	/**
	 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 */
	public long getNextPage() {
		if (isHasNext()) {
			return page + 1;
		} else {
			return page;
		}
	}
	/**
	 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 */
	public long getPrePage() {
		if (isHasPre()) {
			return page - 1;
		} else {
			return page;
		}
	}
}
