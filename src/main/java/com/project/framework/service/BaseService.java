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
public interface BaseService<T, ID extends Serializable> {
	T find(ID id);
	List<T> findAll();
	@Transactional(rollbackFor=RuntimeException.class)
	void save(T entity);
	@Transactional(rollbackFor=RuntimeException.class)
	void removeById(ID id);
	@Transactional(rollbackFor=RuntimeException.class)
    int executeUpdate(final String queryString, final Object... values);
	@Transactional(rollbackFor=RuntimeException.class)
	int executeUpdate(final String queryString, final Map<String,Object> values);
	List<T> query(String hql, Map<String, Object> args);
	List<T> query(String hql,Object...args);
	List<T> query(Criterion...criterions);
    Page<T> pageQuery(Page<T> page, String hql, Map<String, Object> args);
    Page<T> pageQuery(Page<T> page, String hql, Object...args);
    Page<T> pageQuery(final Page<T> page,final Criterion... criterions);
    Page<T> pageQuery(final Page<T> page,final Criterion[] criterions, final Order... orders);
    long countResult(final String queryString, final Object... values);
    long countResult(final String queryString, final Map<String, ?> values);
    int countResult(final Criteria c);
    boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue);
}
