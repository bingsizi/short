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
package com.project.framework.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.OrderEntry;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.project.framework.controller.Page;
import com.project.framework.util.ReflectionUtils;


/**
 * 扩展功能的Hibernat DAO泛型基类.
 * 
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 * 可在Service层直接使用,也可以扩展泛型DAO子类使用,见两个构造函数的注释.
 * 
 * @param <T> DAO操作的对象类型
 * @param <ID> 主键类型
 * <p>
 * @author ray
 */
@SuppressWarnings("unchecked")
@Repository
public class BaseDao<T, ID extends Serializable> extends GenericDao<T, ID> {
	/**
	 * 用于Dao层子类使用的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends HibernateDao<User, Long>{
	 * }
	 */
	public BaseDao() {
		super();
	}

	/**
	 * 用于省略Dao层, Service层直接使用通用HibernateDao的构造函数.
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * HibernateDao<User, Long> userDao = new HibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public BaseDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页对象.不支持其中的orderBy参数.
	 * @param queryString 查询语句.
	 * @param values 数量可变的查询参数,按顺序绑定.
	 * 
	 * @return Page 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	public Page<T> findPage(final Page<T> page,final String queryString, final Object... values) {
		Assert.notNull(page, "page can not null");

		Query q = createQuery(queryString, values);

		if (page.isAutoCount()) {
			long total = countResult(queryString, values);
			page.setTotal(total);
		}

		setPageParameter(q, page);
		List<T> result = q.list();
		page.setRows(result);
		return page;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页对象.
	 * @param queryString 查询语句.
	 * @param values 命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	public Page<T> findPage(final Page<T> page,final String queryString, final Map<String, ?> values) {
		Assert.notNull(page, "page can not null");

		Query q = createQuery(queryString, values);

		if (page.isAutoCount()) {
			long total = countResult(queryString, values);
			page.setTotal(total);
		}

		setPageParameter(q, page);

		List<T> result = q.list();
		page.setRows(result);
		return page;
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page 分页对象.
	 * @param criterions 数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询时的参数.
	 */
	public Page<T> findPage(final Page<T> page,final Criterion... criterions) {

		Assert.notNull(page, "page can not null");

		Criteria c = createCriteria(criterions);

		if (page.isAutoCount()) {
			int total = countResult(c);
			page.setTotal(total);
		}

		setPageParameter(c, page);
		List<T> result = c.list();
		page.setRows(result);
		return page;
	}

	public Page<T> findPage(final Page<T> page,final Criterion[] criterions, final Order... orders) {
		Assert.notNull(page, "page can not null");

		Criteria c = createCriteria(criterions);

		if (page.isAutoCount()) {
			int total = countResult(c);
			page.setTotal(total);
		}

		if (orders != null) {
			for (Order order : orders) {
				c = c.addOrder(order);
			}
		}
		setPageParameter(c, page);
		List<T> result = c.list();
		page.setRows(result);
		return page;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameter(final Query q, final Page<T> page) {
		if (page.getStartIndex() > Integer.MAX_VALUE)
			throw new ClassCastException("Hibernate can not support startIndex Greater than Integer.Max");
		
		//设置分页范围，hibernate的firstResult的序号从0开始
		q.setFirstResult((int) page.getStartIndex());
		q.setMaxResults(page.getPageSize());
		
		
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameter(final Criteria c, final Page<T> page) {
		if (page.getStartIndex() > Integer.MAX_VALUE)
			throw new ClassCastException("Hibernate can not support startIndex Greater than Integer.Max");
		
		//设置分页范围，hibernate的firstResult的序号从0开始
		c.setFirstResult((int) page.getStartIndex());
		c.setMaxResults(page.getPageSize());
		
		//设置order by 条件
		if (page.isOrder()) {
			Map<String, String> orderMap = page.getOrderMap();
			Iterator<String> iterator = orderMap.keySet().iterator();
			while(iterator.hasNext()) {
				
				String propertyName = iterator.next();
				if (orderMap.get(propertyName).equals(Page.ORDER_ASC)) {
					c.addOrder(Order.asc(propertyName));
				} else {
					c.addOrder(Order.desc(propertyName));
				}
			}
		}
		return c;
	}

	/**
	 * 执行count查询获得本次查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的查询语句,复杂的查询请另行编写count语句查询.
	 */
	public long countResult(final String queryString, final Object... values) {
		String fromHql = queryString;
		//select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = executeUniqueQuery(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("queryString can't be auto count, queryString is:" + countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的查询语句,复杂的查询请另行编写count语句查询.
	 */
	public long countResult(final String queryString, final Map<String, ?> values) {
		String fromHql = queryString;
		//select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = executeUniqueQuery(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("queryString can't be auto count, queryString is:" + countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	public int countResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List<OrderEntry>) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList<Object>());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		int totalCount = Integer.parseInt(c.setProjection(Projections.rowCount()).uniqueResult().toString());

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}
