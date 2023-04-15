package org.example.dao.impl;

import org.example.dao.BaseDao;
import org.example.pageModel.Page;
import org.example.pageModel.Pageable;
import org.example.pageModel.OrderEntity;
import org.example.pageModel.Order;
import org.example.pageModel.Filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import javax.persistence.*;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Dao - 基类
 * 
 */
public abstract class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {

	/** 实体类类型 */
	private Class<T> entityClass;

	/** 别名数 */
	private static volatile long aliasCount = 0;

	@PersistenceContext
	protected EntityManager entityManager;

	public EntityManager getEntityManager(){
		return this.entityManager;
	}

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type type = getClass().getGenericSuperclass();
		Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class<T>) parameterizedType[0];
	}

	public T find(ID id) {
		if (id != null) {
			return entityManager.find(entityClass, id);
		}
		return null;
	}

	public T find(ID id, LockModeType lockModeType) {
		if (id != null) {
			if (lockModeType != null) {
				return entityManager.find(entityClass, id, lockModeType);
			} else {
				return entityManager.find(entityClass, id);
			}
		}
		return null;
	}

	public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return findList(criteriaQuery, first, count, filters, orders);
	}

	public Page<T> findPage(Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return findPage(criteriaQuery, pageable);
	}

	/**
	 *  szy add on 20160407
	 */
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findBysql(String jpql){
		return entityManager.createNativeQuery(jpql)
				.setFlushMode(FlushModeType.COMMIT).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<List<Object[]>> findPageBysql(String jpql, Pageable pageable) {
		Query q= entityManager.createNativeQuery(jpql).setFlushMode(FlushModeType.COMMIT);
		long total = q.getResultList().size();
		List<Object[]> lst= q.setFlushMode(FlushModeType.COMMIT)
				.setFirstResult((pageable.getPage() - 1) * pageable.getRows())
				.setMaxResults(pageable.getRows()).getResultList();
		return new Page<List<Object[]>>(lst, total, pageable, 0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findBysql(String jpql,Map<String, Object> params){
		Query q= entityManager.createNativeQuery(jpql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFlushMode(FlushModeType.COMMIT).getResultList();
	}
	
	public Page<T> findPage(Pageable pageable,T t) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		// modify by szy at 20160615
		List<Filter> filters = pageable.getFilters();
		filters.addAll(getFilters(t));
		pageable.setFilters(filters);
		// end of modify by szy at 20160615
		return findPage(criteriaQuery, pageable);
	}

	/**
	 *  获取对象的所有属性，包括父对象 2023.3.14添加
	 * @param class1
	 * @return
	 */
	private static List<Field> getAllField(Class<?> class1){
		List<Field> list= new ArrayList();
		while (class1!= Object.class){
			list.addAll(Arrays.stream(class1.getDeclaredFields()).collect(Collectors.toList()));
			//获取父类
			class1=class1.getSuperclass();
		}
		return list;
	}

	private List<Filter> getFilters(T t){
		List<Filter> filters = new ArrayList<Filter>();
		// Field[] fields = t.getClass().getDeclaredFields(); // 获取对象的所有属性对象：type，name，value

		List<Field> fields = getAllField (t.getClass());

		for (Field fd : fields) {
			fd.setAccessible(true);
			String name = fd.getName();
			if (name.equals("serialVersionUID"))
				continue;
			if (fd.getType().toString().equalsIgnoreCase("interface java.util.Set") ||
					fd.getType().toString().equalsIgnoreCase("interface java.util.List"))
				continue;
			Object value = getFieldValueByName(name, t);
			if (value != null ) {
				Filter fl= new Filter();
				fl.setProperty(name);
				fl.setValue(value);
				if (fd.getType().toString().equalsIgnoreCase("class java.lang.string")){
					if (((String)value).trim().equals("")) 
						continue;
					fl.setOperator(Filter.Operator.like);
				}
				else
					fl.setOperator(Filter.Operator.eq);
				filters.add(fl);
			}
		}
		return filters;
	}
	
	private Object getFieldValueByName(String fieldName, Object o) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = o.getClass().getMethod(getter, new Class[] {});    
	           Object value = method.invoke(o, new Object[] {});    
	           return value;    
	       } catch (Exception e) {    
	        //   log.error(e.getMessage(),e);    
	           return null;    
	       }    
	   }   
	//-- end of szy add
	
	public long count(Filter... filters) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return count(criteriaQuery, filters != null ? Arrays.asList(filters) : null);
	}

	public void persist(T entity) {
		Assert.notNull(entity);
		entityManager.persist(entity);
	}

	public T merge(T entity) {
		Assert.notNull(entity);
		return entityManager.merge(entity);
	}

	public void remove(T entity) {
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	public void refresh(T entity) {
		if (entity != null) {
			entityManager.refresh(entity);
		}
	}

	public void refresh(T entity, LockModeType lockModeType) {
		if (entity != null) {
			if (lockModeType != null) {
				entityManager.refresh(entity, lockModeType);
			} else {
				entityManager.refresh(entity);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ID getIdentifier(T entity) {
		Assert.notNull(entity);
		return (ID) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
	}

	public boolean isManaged(T entity) {
		return entityManager.contains(entity);
	}

	public void detach(T entity) {
		entityManager.detach(entity);
	}

	public void lock(T entity, LockModeType lockModeType) {
		if (entity != null && lockModeType != null) {
			entityManager.lock(entity, lockModeType);
		}
	}

	public void clear() {
		entityManager.clear();
	}

	public void flush() {
		entityManager.flush();
	}

	protected List<T> findList(CriteriaQuery<T> criteriaQuery, Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Root<T> root = getRoot(criteriaQuery);
		addRestrictions(criteriaQuery, filters);
		addOrders(criteriaQuery, orders);
		if (criteriaQuery.getOrderList().isEmpty()) {
			if (OrderEntity.class.isAssignableFrom(entityClass)) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
			} else {
				//criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderEntity.CREATE_DATE_PROPERTY_NAME)));
			}
		}
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		if (first != null) {
			query.setFirstResult(first);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	protected Page<T> findPage(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());

		if (pageable == null) {
			pageable = new Pageable();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Root<T> root = getRoot(criteriaQuery);
		addRestrictions(criteriaQuery, pageable);
		addOrders(criteriaQuery, pageable);
		if (criteriaQuery.getOrderList().isEmpty()) {
			if (OrderEntity.class.isAssignableFrom(entityClass)) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
			} else {
				//criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderEntity.CREATE_DATE_PROPERTY_NAME)));
			}
		}
		long total = count(criteriaQuery, null);
		//int totalPages = (int) Math.ceil((double) total / (double) pageable.getRows());
		//if (totalPages < pageable.getPage()) {
		//	pageable.setPage(totalPages);
		//}
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		query.setFirstResult((pageable.getPage() - 1) * pageable.getRows());
		query.setMaxResults(pageable.getRows());
		return new Page<T>(query.getResultList(), total, pageable);
	}

	protected Long count(CriteriaQuery<T> criteriaQuery, List<Filter> filters) {
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		addRestrictions(criteriaQuery, filters);

		CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
		for (Root<?> root : criteriaQuery.getRoots()) {
			Root<?> dest = countCriteriaQuery.from(root.getJavaType());
			dest.alias(getAlias(root));
			copyJoins(root, dest);
		}

		Root<?> countRoot = getRoot(countCriteriaQuery, criteriaQuery.getResultType());
		countCriteriaQuery.select(criteriaBuilder.count(countRoot));

		if (criteriaQuery.getGroupList() != null) {
			countCriteriaQuery.groupBy(criteriaQuery.getGroupList());
		}
		if (criteriaQuery.getGroupRestriction() != null) {
			countCriteriaQuery.having(criteriaQuery.getGroupRestriction());
		}
		if (criteriaQuery.getRestriction() != null) {
			countCriteriaQuery.where(criteriaQuery.getRestriction());
		}
		return entityManager.createQuery(countCriteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
	}

	private synchronized String getAlias(Selection<?> selection) {
		if (selection != null) {
			String alias = selection.getAlias();
			if (alias == null) {
				if (aliasCount >= 1000) {
					aliasCount = 0;
				}
				alias = "szyGeneratedAlias" + aliasCount++;
				selection.alias(alias);
			}
			return alias;
		}
		return null;
	}

	private Root<T> getRoot(CriteriaQuery<T> criteriaQuery) {
		if (criteriaQuery != null) {
			return getRoot(criteriaQuery, criteriaQuery.getResultType());
		}
		return null;
	}

	private Root<T> getRoot(CriteriaQuery<?> criteriaQuery, Class<T> clazz) {
		if (criteriaQuery != null && criteriaQuery.getRoots() != null && clazz != null) {
			for (Root<?> root : criteriaQuery.getRoots()) {
				if (clazz.equals(root.getJavaType())) {
					return (Root<T>) root.as(clazz);
				}
			}
		}
		return null;
	}

	private void copyJoins(From<?, ?> from, From<?, ?> to) {
		for (Join<?, ?> join : from.getJoins()) {
			Join<?, ?> toJoin = to.join(join.getAttribute().getName(), join.getJoinType());
			toJoin.alias(getAlias(join));
			copyJoins(join, toJoin);
		}
		for (Fetch<?, ?> fetch : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
			copyFetches(fetch, toFetch);
		}
	}

	private void copyFetches(Fetch<?, ?> from, Fetch<?, ?> to) {
		for (Fetch<?, ?> fetch : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
			copyFetches(fetch, toFetch);
		}
	}

	private void addRestrictions(CriteriaQuery<T> criteriaQuery, List<Filter> filters) {
		if (criteriaQuery == null || filters == null || filters.isEmpty()) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		for (Filter filter : filters) {
			if (filter == null || StringUtils.isEmpty(filter.getProperty())) {
				continue;
			}
			// mark
			String aa="";
			if (filter.getValue()!=null)
				aa=filter.getValue().getClass().getSimpleName();
			if (filter.getOperator() == Filter.Operator.eq && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(filter.getProperty()), filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.ne && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get(filter.getProperty()), filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.gt && filter.getValue() != null) {
//				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				//mark
				if (aa.equalsIgnoreCase("string"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThan(root.<String> get(filter.getProperty()), (String) filter.getValue()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.lt && filter.getValue() != null) {
//				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				//mark
				if (aa.equalsIgnoreCase("string"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThan(root.<String> get(filter.getProperty()), (String) filter.getValue()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.ge && filter.getValue() != null) {
//				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				//mark
				if (aa.equalsIgnoreCase("string"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<String> get(filter.getProperty()), (String) filter.getValue()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.le && filter.getValue() != null) {
//				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				//mark
				if (aa.equalsIgnoreCase("string"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<String> get(filter.getProperty()), (String) filter.getValue()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.like && filter.getValue() != null && filter.getValue() instanceof String) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String> get(filter.getProperty()), "%%"+filter.getValue()+"%%"));
			} else if (filter.getOperator() == Filter.Operator.in && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in(filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.isNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNull());
			} else if (filter.getOperator() == Filter.Operator.isNotNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNotNull());
			}
		}
		criteriaQuery.where(restrictions);
	}

	private void addRestrictions(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		if (criteriaQuery == null || pageable == null) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		if (StringUtils.isNotEmpty(pageable.getSearchProperty()) && StringUtils.isNotEmpty(pageable.getSearchValue())) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String> get(pageable.getSearchProperty()), "%" + pageable.getSearchValue() + "%"));
		}
		if (pageable.getFilters() != null) {
			List<Filter> lstFilters = pageable.getFilters();
			restrictions = getRestrictions(root, criteriaBuilder, restrictions, lstFilters);
		}
		criteriaQuery.where(restrictions);
	}

	// szy add 2022-7-4
	public List<T> findByEntity(T entity){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);// 创建查询， 传入调用的实体类
		criteriaQuery.select(criteriaQuery.from(entityClass));  // 等价于 sql 中的 from实体类表；  entityClass 表的实体类。
		addRestrictions(criteriaQuery,entity);

		TypedQuery<T> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);

		return  query.getResultList();
	}

	private void addRestrictions(CriteriaQuery<T> criteriaQuery,T t){
		if (criteriaQuery == null ) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();

		List<Filter> filters = new ArrayList<>() ;
		filters.addAll(getFilters(t));

		//getRestrictions(root,criteriaBuilder,restrictions,filters);
		criteriaQuery.where(getRestrictions(root,criteriaBuilder,restrictions,filters));

	}
	// end of szy add 2022-7-4

	private Predicate getRestrictions(Root<T> root, CriteriaBuilder criteriaBuilder, Predicate restrictions, List<Filter> lstFilters) {
		for (Filter filter : lstFilters) {
			if (filter == null || StringUtils.isEmpty(filter.getProperty())) {
				continue;
			}
			String aa="";
			if (filter.getValue()!=null)
				aa=filter.getValue().getClass().getSimpleName();
			if (filter.getOperator() == Filter.Operator.eq && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(filter.getProperty()), filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.ne && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get(filter.getProperty()), filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.gt && filter.getValue() != null) {
				if (aa.equalsIgnoreCase("string"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThan(root.<String> get(filter.getProperty()), (String) filter.getValue()));
				else if (aa.equalsIgnoreCase("date"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThan(root.<Date> get(filter.getProperty()), (Date) filter.getValue()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.lt && filter.getValue() != null) {
				if (aa.equalsIgnoreCase("string"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThan(root.<String> get(filter.getProperty()), (String) filter.getValue()));
				else if (aa.equalsIgnoreCase("date"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThan(root.<Date> get(filter.getProperty()), (Date) filter.getValue()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.ge && filter.getValue() != null) {
				if (aa.equalsIgnoreCase("string"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<String> get(filter.getProperty()), (String) filter.getValue()));
				else if (aa.equalsIgnoreCase("date"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get(filter.getProperty()), (Date) filter.getValue()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.le && filter.getValue() != null) {
				if (aa.equalsIgnoreCase("string"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<String> get(filter.getProperty()), (String) filter.getValue()));
				else if (aa.equalsIgnoreCase("date"))
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get(filter.getProperty()), (Date) filter.getValue()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.like && filter.getValue() != null && filter.getValue() instanceof String) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
						.like(root.<String> get(filter.getProperty()), (String) "%" + filter.getValue() + "%"));
			} else if (filter.getOperator() == Filter.Operator.in && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in(filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.isNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNull());
			} else if (filter.getOperator() == Filter.Operator.isNotNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNotNull());
			}
		}
		return restrictions;
	}

	private void addOrders(CriteriaQuery<T> criteriaQuery, List<Order> orders) {
		if (criteriaQuery == null || orders == null || orders.isEmpty()) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (!criteriaQuery.getOrderList().isEmpty()) {
			orderList.addAll(criteriaQuery.getOrderList());
		}
		for (Order order : orders) {
			if (order.getDirection() == Order.Direction.asc) {
				orderList.add(criteriaBuilder.asc(root.get(order.getProperty())));
			} else if (order.getDirection() == Order.Direction.desc) {
				orderList.add(criteriaBuilder.desc(root.get(order.getProperty())));
			}
		}
		criteriaQuery.orderBy(orderList);
	}

	private void addOrders(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		if (criteriaQuery == null || pageable == null) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (!criteriaQuery.getOrderList().isEmpty()) {
			orderList.addAll(criteriaQuery.getOrderList());
		}
		if (StringUtils.isNotEmpty(pageable.getOrderProperty()) && pageable.getOrderDirection() != null) {
			if (pageable.getOrderDirection() == Order.Direction.asc) {
				orderList.add(criteriaBuilder.asc(root.get(pageable.getOrderProperty())));
			} else if (pageable.getOrderDirection() == Order.Direction.desc) {
				orderList.add(criteriaBuilder.desc(root.get(pageable.getOrderProperty())));
			}
		}
		if (pageable.getOrders() != null) {
			for (Order order : pageable.getOrders()) {
				if (order.getDirection() == Order.Direction.asc) {
					orderList.add(criteriaBuilder.asc(root.get(order.getProperty())));
				} else if (order.getDirection() == Order.Direction.desc) {
					orderList.add(criteriaBuilder.desc(root.get(order.getProperty())));
				}
			}
		}
		// add by szy 20160415
		if (pageable.getSort()!=null){
			if (pageable.getOrder()!=null && pageable.getOrder().equalsIgnoreCase("asc"))
				orderList.add(criteriaBuilder.asc(root.get(pageable.getSort())));
			else
				orderList.add(criteriaBuilder.desc(root.get(pageable.getSort())));
		}
		// end add by szy 20160415
		criteriaQuery.orderBy(orderList);
	}

	public int executeSql(String sql){
		int k=0;
		k = entityManager.createQuery(sql).executeUpdate();
		return  k;
	}

	public List<T> find(String hql, Map<String, Object> params) {
		Query q = entityManager.createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.getResultList();
	}
}