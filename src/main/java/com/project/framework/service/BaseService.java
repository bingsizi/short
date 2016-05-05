package com.project.framework.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;
import com.project.framework.controller.Page;


/**  
 * 业务接口
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月3日 下午3:30:15
 */
public abstract class BaseService<T, ID extends Serializable> {

	public abstract T find(ID id);
	public abstract List<T> findAll();
	@Transactional(rollbackFor=RuntimeException.class)
	public abstract void save(T entity);
	@Transactional(rollbackFor=RuntimeException.class)
	public abstract void removeById(ID id);
	@Transactional(rollbackFor=RuntimeException.class)
	protected abstract int executeUpdate(final String queryString, final Object... values);
	@Transactional(rollbackFor=RuntimeException.class)
	protected abstract int executeUpdate(final String queryString, final Map<String,Object> values);
	protected abstract List<T> query(String hql, Map<String, Object> args);
	protected abstract List<T> query(String hql,Object...args);
	protected abstract List<T> query(Criterion...criterions);
	protected abstract Page<T> pageQuery(Page<T> page, String hql, Map<String, Object> args);
	protected abstract Page<T> pageQuery(Page<T> page, String hql, Object...args);
	protected abstract Page<T> pageQuery(final Page<T> page,final Criterion... criterions);
	protected abstract Page<T> pageQuery(final Page<T> page,final Criterion[] criterions, final Order... orders);
	protected abstract long countResult(final String queryString, final Object... values);
	protected abstract long countResult(final String queryString, final Map<String, ?> values);
	protected abstract  int countResult(final Criteria c);
    public abstract boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue);
}
