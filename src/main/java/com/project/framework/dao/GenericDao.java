/**
 * Copyright 2008 - 2011 Simcore.org.
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
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import com.project.framework.util.ReflectionUtils;

/**
 * 封装Hibernate原生API的DAO泛型基类.
 * 
 * 可在Service层直接使用,也可以扩展泛型DAO子类使用.
 * 参考Spring2.5自带的Petlinc例子,取消了HibernateTemplate,直接使用Hibernate原生API.
 * 
 * @param <T> DAO操作的对象类型
 * @param <ID> 主键类型
 * <p>
 * @author ray
 */
@SuppressWarnings("unchecked")
class GenericDao<T, ID extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	/**
	 * 用于Dao层子类使用的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.<p>
	 * eg.<p>
	 * <code>public class UserDao extends GenericDao&lt;User, Long&gt;{...}</code>
	 */
	public GenericDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 用于省略Dao层, 在Service层直接使用通用GenericDao的构造函数.
	 * 在构造函数中定义对象类型Class.<p>
	 * eg.<p>
	 * <code>GenericDao<User, Long> userDao = new GenericDao&lt;User, Long&gt;(sessionFactory, User.class){...}</code>
	 *
	 * @param sessionFactory Hibernate Session工厂
	 * @param entityClass 泛型类型
	 */
	public GenericDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	/**
	 * 取得sessionFactory.
	 *
	 * @return SessionFactory Hibernate Session工厂
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候Override本函数.
	 * 
	 * @param sessionFactory Hibernate Session工厂
	 */
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 取得当前Session.
	 *
	 * @return Session
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存新增或修改的对象.
	 *
	 * @param entity 要保存的实体对象
	 */
	public void save(final T entity) {
		Assert.notNull(entity, "entity Can not NULL");
		
		getSession().saveOrUpdate(entity);
		logger.debug("Save {}: {}", entityClass.getSimpleName(), entity);
	}
	/**
	 * 保存新增或修改的对象.
	 *
	 * @param entity 要保存的实体对象
	 */
	public void update(final T entity) {
		Assert.notNull(entity, "entity Can not NULL");
		
		getSession().update(entity);
		logger.debug("Save {}: {}", entityClass.getSimpleName(), entity);
	}
	
	/**
	 * 批量新增或修改对象
	 * 
	 * @param entitys 要保存的实体对象列表
	 * @param flushSize 在ORM缓存中缓存数量，该数量影响内存的使用，当实体对象占用内存数值大时，应相应减少该缓存数值。
	 */
	public void save(final List<T> entitys, int flushSize) {
		Assert.notNull(entitys, "entitys Can not NULL");
		Assert.notEmpty(entitys, "entitys Can not EMPTY");
		
		Session session = getSession();
		int count = 1;
		for(T entity:entitys){
			session.saveOrUpdate(entity);
			
			if (count++ % flushSize == 0) {
				session.flush(); 
				session.clear();
			}
		}
		logger.debug("batch save {}, number of rows affected: {}", entityClass.getSimpleName(), count-1);
	}

	/**
	 * 删除对象.
	 * 
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 */
	public void delete(final T entity) {
		Assert.notNull(entity, "entity Can not NULL");
		
		getSession().delete(entity);
		logger.debug("delete {}: {}",entityClass.getSimpleName(), entity);
	}
	
	/**
	 * 批量删除对象
	 * 
	 * @param entitys 删除对象列表
	 * @param flushSize 在ORM缓存中缓存数量，该数量影响内存的使用，当实体对象占用内存数值大时，应相应减少该缓存数值。
	 */
	public void delete(final List<T> entitys, int flushSize) {
		Assert.notNull(entitys, "entity Can not NULL");
		Assert.notEmpty(entitys, "entitys Can not EMPTY");
		
		Session session = getSession();
		int count = 1;
		for(T entity:entitys){
			session.delete(entity);
			
			if (count++ % flushSize == 0) {
				session.flush(); 
				session.clear();
			}
		}
		
		logger.debug("batch delete {}, number of rows affected: {}", entityClass.getSimpleName(), count-1);
	}

	/**
	 * 按id删除对象.
	 *
	 * @param id 删除对象的id
	 */
	public void deleteById(final ID id) {
		Assert.notNull(id, "entity Can not NULL");
		
		getSession().delete(findById(id));
		logger.debug("delete {},id is {}", entityClass.getSimpleName(), id);
	}
	
	/**
	 * 按id列表批量删除对象
	 * 
	 * @param ids 删除对象的id列表
	 */
	public void deleteById(final List<ID> ids) {
		Assert.notNull(ids,"ids Can not NULL");
		
		final String queryString  = "delete from " + entityClass.getSimpleName() + " as model where model."+ getIdName() +" in(:ids)";
		
		Query query = createQuery(queryString);
		query.setParameterList("ids", ids);
		
		int result = query.executeUpdate();
		
		logger.debug("batch delete {}, number of rows affected: {}", entityClass.getSimpleName(), result);
	}
	
	/**
	 * 按id获取对象.
	 *
	 * @param id 获取对象的id
	 * @return T 该id的对象,如果该id的对象不存在则返回null.
	 */
	public T findById(final ID id) {
		Assert.notNull(id, "id Can not NULL");
		
		T t = (T) getSession().load(entityClass, id);
		try {
			initEntity(t);
		} catch (ObjectNotFoundException e) {
			return null;
		}
		
		return t;
	}
	/**
	 * 根据ID获得对象
	 * @param id
	 * @return
	 */
	public T getEntity(final ID id){
		try{
			T t=(T)getSession().get(entityClass,id);
			return t;
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * 获取全部对象.
	 *
	 * @return 对象列表
	 */
	public List<T> findAll() {
		return executeQuery();
	}

	/**
	 * 获取全部对象,支持排序.
	 *
	 * @param orderBy 排序的字段
	 * @param isAsc 是否正序排序
	 * @return 排序的对象列表
	 */
	public List<T> findAll(final String orderBy, boolean isAsc) {
		Criteria criteria = createCriteria();
		if (isAsc) {
			criteria.addOrder(Order.asc(orderBy));
		} else {
			criteria.addOrder(Order.desc(orderBy));
		}
		return criteria.list();
	}

	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 *
	 * @param propertyName 对象字段名
	 * @param value 匹配的值
	 * @return 匹配对象列表
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName Can not NULL");
		
		Criterion criterion = Restrictions.eq(propertyName, value);
		return executeQuery(criterion);
	}
	
	/**
	 * 按属性查找对象列表,匹配方式为相等,支持排序.
	 * 
	 * @param propertyName 对象字段名
	 * @param value 匹配的值
	 * @param orderBy 排序的字段
	 * @param isAsc 是否正序排序
	 * @return 排序匹配对象列表
	 */
	public List<T> findBy(final String propertyName, final Object value, final String orderBy, boolean isAsc) {
		Assert.hasText(propertyName, "propertyName Can not NULL");
		
		Criterion criterion = Restrictions.eq(propertyName, value);
		
		Criteria criteria = createCriteria(criterion);
		if (isAsc) {
			criteria.addOrder(Order.asc(orderBy));
		} else {
			criteria.addOrder(Order.desc(orderBy));
		}
		
		return criteria.list();
	}

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 *
	 * @param propertyName 对象字段名
	 * @param value 匹配的值
	 * @return 唯一匹配的对象
	 */
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName Can not NULL");
		
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按查询语句查询对象列表.
	 * 
	 * @param <X> 查询语句查询的类型
	 * @param queryString 查询语句
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 查询返回的对象列表
	 */
	public <X> List<X> executeQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString Can not NULL");
		
		return createQuery(queryString, values).list();
	}

	/**
	 * 按查询语句查询对象列表.
	 * 
	 * @param <X> 查询语句查询的类型
	 * @param queryString 查询语句
	 * @param values 命名参数,按名称绑定.
	 * @return 查询返回的对象列表
	 */
	public <X> List<X> executeQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString Can not NULL");
		
		return createQuery(queryString, values).list();
	}
	
	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions 数量可变的Criterion.
	 * @return 查询返回的对象列表
	 */
	public List<T> executeQuery(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}
	/**
	 * 按Criteria查询对象列表,带排序
	 * @param orderBy 排序字段
	 * @param isAsc 是否升序
	 * @param criterions 数量可变的Criterion.
	 * @return 查询返回的对象列表
	 */
	public List<T> executeQueryWithOrder(final String orderBy, boolean isAsc,final Criterion... criterions) {
		Criteria criteria = createCriteria(criterions);
		if (isAsc) {
			criteria.addOrder(Order.asc(orderBy));
		} else {
			criteria.addOrder(Order.desc(orderBy));
		}
		return criteria.list();
	}

	/**
	 * 按查询语句查询唯一对象.
	 * 
	 * @param <X> 查询语句查询的类型
	 * @param queryString 查询语句
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 查询返回的唯一对象
	 */
	public <X> X executeUniqueQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString Can not NULL");
		
		return (X) createQuery(queryString, values).uniqueResult();
	}

	/**
	 * 按查询语句查询唯一对象.
	 * 
	 * @param <X> 查询语句查询的类型
	 * @param queryString 查询语句
	 * @param values 命名参数,按名称绑定.
	 * @return 查询返回的唯一对象
	 */
	public <X> X executeUniqueQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString Can not NULL");
		
		return (X) createQuery(queryString, values).uniqueResult();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions 数量可变的Criterion.
	 * @return 查询返回的唯一对象
	 */
	public T executeUniqueQuery(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}
	
	/**
	 * 执行查询语句进行修改/删除操作.
	 *
	 * @param queryString 批量执行语句
	 * @param values 匹配参数
	 * @return 更新记录数
	 */
	public int executeUpdate(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString Can not NULL");
		
		return createQuery(queryString, values).executeUpdate();
	}

	/**
	 * 执行查询语句进行修改/删除操作.
	 * 
	 * @param queryString 批量执行语句
	 * @param values 匹配参数
	 * @return 更新记录数.
	 */
	public int executeUpdate(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString Can not NULL");
		
		return createQuery(queryString, values).executeUpdate();
	}
	
	/**
	 * 执行原生查询语句进行修改/删除操作.
	 * 
	 * @param nativeQueryString 批量执行原生语句
	 * @param values 匹配参数
	 * @return 更新记录数
	 */
	public int executeNativeUpdate(final String nativeQueryString, final Object... values){
		Assert.hasText(nativeQueryString, "nativeQueryString Can not NULL");
		
		return createNativeQuery(nativeQueryString,values).executeUpdate();
	}
	
	/**
	 * 执行原生查询语句进行批量修改/删除操作.
	 * 
	 * @param nativeQueryString 批量执行原生语句
	 * @param values 匹配参数
	 * @return 更新记录数
	 */
	public int executeNativeUpdate(final String nativeQueryString, final Map<String, ?> values) {
		Assert.hasText(nativeQueryString, "nativeQueryString Can not NULL");
		
		return createNativeQuery(nativeQueryString, values).executeUpdate();
	}

	/**
	 * 根据查询查询语句与参数列表创建Query对象.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		
		Assert.hasText(queryString, "queryString Can not NULL");
		
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/**
	 * 根据原生SQL与查询语句与参数列表创建SQLQuery对象
	 * 
	 * @param nativeQueryString 原生SQL语句
	 * @param values 数量可变得参数,按顺序绑定
	 */
	public SQLQuery createNativeQuery(final String nativeQueryString,final Object...values){
		
		Assert.hasText(nativeQueryString, "nativeQueryString Can not NULL");
		SQLQuery query = getSession().createSQLQuery(nativeQueryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询查询语句与参数列表创建Query对象.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {

		Assert.hasText(queryString, "queryString Can not NULL");
		
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	/**
	 * 根据原生SQL与查询语句与参数列表创建SQLQuery对象
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public SQLQuery createNativeQuery(final String nativeQueryString, final Map<String,?> values){
		
		Assert.hasText(nativeQueryString, "nativeQueryString Can not NULL");
		
		SQLQuery query = getSession().createSQLQuery(nativeQueryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	/**
	 * 根据Criterion条件创建Criteria.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 初始化对象.
	 * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
	 * 只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
	 * 如需初始化关联属性,可实现新的函数,执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initEntity(T entity) {
		Hibernate.initialize(entity);
	}

	/**
	 * @see #initEntity(Object)
	 */
	public void initEntity(List<T> entityList) {
		for (T entity : entityList) {
			Hibernate.initialize(entity);
		}
	}

	/**
	 * Flush当前Session.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * 为Query添加distinct transformer.
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 为Criteria添加distinct transformer.
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}
	
}