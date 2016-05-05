package com.project.framework.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import com.project.framework.controller.Page;
import com.project.framework.dao.BaseDao;

/**  
 * 核心业务实现类
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月3日 下午4:03:44
 */
public class BaseServiceImpl <T, ID extends Serializable> extends BaseService<T,ID>{
	
	protected BaseDao<T,ID> baseDao;
	
	@Override
	public T find(ID id) {
		return baseDao.findById(id);
	}
	@Override
	public List<T> findAll() {
		return baseDao.findAll();
	}
	@Override
	public void save(T entity) {
		baseDao.save(entity);
	}
	@Override
	public void removeById(ID id) {
		baseDao.deleteById(id);
	}
	@Override
	public boolean isPropertyUnique(String propertyName, Object newValue, Object oldValue) {
		return baseDao.isPropertyUnique(propertyName, newValue, oldValue);
	}
	@Override
	@Deprecated
	protected int executeUpdate(String queryString, Object... values) {
		return baseDao.executeUpdate(queryString, values);
	}
	@Override
	protected int executeUpdate(String queryString, Map<String, Object> values) {
		return baseDao.executeUpdate(queryString, values);
	}
	@Override
	protected List<T> query(String hql, Map<String, Object> args) {
		return baseDao.executeQuery(hql, args);
	}
	@Override
	@Deprecated
	protected List<T> query(String hql, Object... args) {
		return baseDao.executeQuery(hql, args);
	}
	@Override
	protected List<T> query(Criterion...criterions){
		return baseDao.executeQuery(criterions);
	}
	@Override
	protected Page<T> pageQuery(Page<T> page, String hql, Map<String, Object> args) {
		return baseDao.findPage(page, hql, args);
	}
	@Override
	@Deprecated
	protected Page<T> pageQuery(Page<T> page, String hql, Object... args) {
		return baseDao.findPage(page, hql, args);
	}
	@Override
	protected Page<T> pageQuery(Page<T> page, Criterion... criterions) {
		return baseDao.findPage(page, criterions);
	}
	@Override
	protected Page<T> pageQuery(Page<T> page, Criterion[] criterions, Order... orders) {
		return baseDao.findPage(page, criterions, orders);
	}
	@Override
	@Deprecated
	protected long countResult(String queryString, Object... values) {
		return baseDao.countResult(queryString, values);
	}
	@Override
	protected long countResult(String queryString, Map<String, ?> values) {
		return baseDao.countResult(queryString, values);
	}
	@Override
	protected int countResult(Criteria c) {
		return baseDao.countResult(c);
	}

}
