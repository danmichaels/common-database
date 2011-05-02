package me.vgv.common.database.hibernate;

import com.google.common.base.Preconditions;
import org.hibernate.*;
import org.hibernate.jdbc.Work;
import org.hibernate.stat.SessionStatistics;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Простой wrapper, который транслирует абсолютно все вызовы низлежащей сессии
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
@SuppressWarnings({"deprecation"})
public class SessionWrapper implements org.hibernate.classic.Session {

	private final org.hibernate.classic.Session peer;

	public SessionWrapper(org.hibernate.classic.Session peer) {
		Preconditions.checkNotNull(peer, "peer is null - error");

		this.peer = peer;
	}

	public org.hibernate.classic.Session getUnderlyingSession() {
		return peer;
	}

	@Override
	public Object saveOrUpdateCopy(Object object) throws HibernateException {
		return peer.saveOrUpdateCopy(object);
	}

	@Override
	public Object saveOrUpdateCopy(Object object, Serializable id) throws HibernateException {
		return peer.saveOrUpdateCopy(object, id);
	}

	@Override
	public Object saveOrUpdateCopy(String entityName, Object object) throws HibernateException {
		return peer.saveOrUpdateCopy(entityName, object);
	}

	@Override
	public Object saveOrUpdateCopy(String entityName, Object object, Serializable id) throws HibernateException {
		return peer.saveOrUpdateCopy(entityName, object, id);
	}

	@Override
	public List find(String query) throws HibernateException {
		return peer.find(query);
	}

	@Override
	public List find(String query, Object value, Type type) throws HibernateException {
		return peer.find(query, value, type);
	}

	@Override
	public List find(String query, Object[] values, Type[] types) throws HibernateException {
		return peer.find(query, values, types);
	}

	@Override
	public Iterator iterate(String query) throws HibernateException {
		return peer.iterate(query);
	}

	@Override
	public Iterator iterate(String query, Object value, Type type) throws HibernateException {
		return peer.iterate(query, value, type);
	}

	@Override
	public Iterator iterate(String query, Object[] values, Type[] types) throws HibernateException {
		return peer.iterate(query, values, types);
	}

	@Override
	public Collection filter(Object collection, String filter) throws HibernateException {
		return peer.filter(collection, filter);
	}

	@Override
	public Collection filter(Object collection, String filter, Object value, Type type) throws HibernateException {
		return peer.filter(collection, filter, value, type);
	}

	@Override
	public Collection filter(Object collection, String filter, Object[] values, Type[] types) throws HibernateException {
		return peer.filter(collection, filter, values, types);
	}

	@Override
	public int delete(String query) throws HibernateException {
		return peer.delete(query);
	}

	@Override
	public int delete(String query, Object value, Type type) throws HibernateException {
		return peer.delete(query, value, type);
	}

	@Override
	public int delete(String query, Object[] values, Type[] types) throws HibernateException {
		return peer.delete(query, values, types);
	}

	@Override
	@Deprecated
	public Query createSQLQuery(String sql, String returnAlias, Class returnClass) {
		return peer.createSQLQuery(sql, returnAlias, returnClass);
	}

	@Override
	@Deprecated
	public Query createSQLQuery(String sql, String[] returnAliases, Class[] returnClasses) {
		return peer.createSQLQuery(sql, returnAliases, returnClasses);
	}

	@Override
	public void save(Object object, Serializable id) throws HibernateException {
		peer.save(object, id);
	}

	@Override
	public void save(String entityName, Object object, Serializable id) throws HibernateException {
		peer.save(entityName, object, id);
	}

	@Override
	public void update(Object object, Serializable id) throws HibernateException {
		peer.update(object, id);
	}

	@Override
	public void update(String entityName, Object object, Serializable id) throws HibernateException {
		peer.update(entityName, object, id);
	}

	@Override
	public EntityMode getEntityMode() {
		return peer.getEntityMode();
	}

	@Override
	public Session getSession(EntityMode entityMode) {
		return peer.getSession(entityMode);
	}

	@Override
	public void flush() throws HibernateException {
		peer.flush();
	}

	@Override
	public void setFlushMode(FlushMode flushMode) {
		peer.setFlushMode(flushMode);
	}

	@Override
	public FlushMode getFlushMode() {
		return peer.getFlushMode();
	}

	@Override
	public void setCacheMode(CacheMode cacheMode) {
		peer.setCacheMode(cacheMode);
	}

	@Override
	public CacheMode getCacheMode() {
		return peer.getCacheMode();
	}

	@Override
	public SessionFactory getSessionFactory() {
		return peer.getSessionFactory();
	}

	@Override
	public Connection connection() throws HibernateException {
		return peer.connection();
	}

	@Override
	public Connection close() throws HibernateException {
		return peer.close();
	}

	@Override
	public void cancelQuery() throws HibernateException {
		peer.cancelQuery();
	}

	@Override
	public boolean isOpen() {
		return peer.isOpen();
	}

	@Override
	public boolean isConnected() {
		return peer.isConnected();
	}

	@Override
	public boolean isDirty() throws HibernateException {
		return peer.isDirty();
	}

	@Override
	public boolean isDefaultReadOnly() {
		return peer.isDefaultReadOnly();
	}

	@Override
	public void setDefaultReadOnly(boolean readOnly) {
		peer.setDefaultReadOnly(readOnly);
	}

	@Override
	public Serializable getIdentifier(Object object) throws HibernateException {
		return peer.getIdentifier(object);
	}

	@Override
	public boolean contains(Object object) {
		return peer.contains(object);
	}

	@Override
	public void evict(Object object) throws HibernateException {
		peer.evict(object);
	}

	@Override
	public Object load(Class theClass, Serializable id, LockMode lockMode) throws HibernateException {
		return peer.load(theClass, id, lockMode);
	}

	@Override
	public Object load(Class theClass, Serializable id, LockOptions lockOptions) throws HibernateException {
		return peer.load(theClass, id, lockOptions);
	}

	@Override
	public Object load(String entityName, Serializable id, LockMode lockMode) throws HibernateException {
		return peer.load(entityName, id, lockMode);
	}

	@Override
	public Object load(String entityName, Serializable id, LockOptions lockOptions) throws HibernateException {
		return peer.load(entityName, id, lockOptions);
	}

	@Override
	public Object load(Class theClass, Serializable id) throws HibernateException {
		return peer.load(theClass, id);
	}

	@Override
	public Object load(String entityName, Serializable id) throws HibernateException {
		return peer.load(entityName, id);
	}

	@Override
	public void load(Object object, Serializable id) throws HibernateException {
		peer.load(object, id);
	}

	@Override
	public void replicate(Object object, ReplicationMode replicationMode) throws HibernateException {
		peer.replicate(object, replicationMode);
	}

	@Override
	public void replicate(String entityName, Object object, ReplicationMode replicationMode) throws HibernateException {
		peer.replicate(entityName, object, replicationMode);
	}

	@Override
	public Serializable save(Object object) throws HibernateException {
		return peer.save(object);
	}

	@Override
	public Serializable save(String entityName, Object object) throws HibernateException {
		return peer.save(entityName, object);
	}

	@Override
	public void saveOrUpdate(Object object) throws HibernateException {
		peer.saveOrUpdate(object);
	}

	@Override
	public void saveOrUpdate(String entityName, Object object) throws HibernateException {
		peer.saveOrUpdate(entityName, object);
	}

	@Override
	public void update(Object object) throws HibernateException {
		peer.update(object);
	}

	@Override
	public void update(String entityName, Object object) throws HibernateException {
		peer.update(entityName, object);
	}

	@Override
	public Object merge(Object object) throws HibernateException {
		return peer.merge(object);
	}

	@Override
	public Object merge(String entityName, Object object) throws HibernateException {
		return peer.merge(entityName, object);
	}

	@Override
	public void persist(Object object) throws HibernateException {
		peer.persist(object);
	}

	@Override
	public void persist(String entityName, Object object) throws HibernateException {
		peer.persist(entityName, object);
	}

	@Override
	public void delete(Object object) throws HibernateException {
		peer.delete(object);
	}

	@Override
	public void delete(String entityName, Object object) throws HibernateException {
		peer.delete(entityName, object);
	}

	@Override
	public void lock(Object object, LockMode lockMode) throws HibernateException {
		peer.lock(object, lockMode);
	}

	@Override
	public void lock(String entityName, Object object, LockMode lockMode) throws HibernateException {
		peer.lock(entityName, object, lockMode);
	}

	@Override
	public LockRequest buildLockRequest(LockOptions lockOptions) {
		return peer.buildLockRequest(lockOptions);
	}

	@Override
	public void refresh(Object object) throws HibernateException {
		peer.refresh(object);
	}

	@Override
	public void refresh(Object object, LockMode lockMode) throws HibernateException {
		peer.refresh(object, lockMode);
	}

	@Override
	public void refresh(Object object, LockOptions lockOptions) throws HibernateException {
		peer.refresh(object, lockOptions);
	}

	@Override
	public LockMode getCurrentLockMode(Object object) throws HibernateException {
		return peer.getCurrentLockMode(object);
	}

	@Override
	public Transaction beginTransaction() throws HibernateException {
		return peer.beginTransaction();
	}

	@Override
	public Transaction getTransaction() {
		return peer.getTransaction();
	}

	@Override
	public Criteria createCriteria(Class persistentClass) {
		return peer.createCriteria(persistentClass);
	}

	@Override
	public Criteria createCriteria(Class persistentClass, String alias) {
		return peer.createCriteria(persistentClass, alias);
	}

	@Override
	public Criteria createCriteria(String entityName) {
		return peer.createCriteria(entityName);
	}

	@Override
	public Criteria createCriteria(String entityName, String alias) {
		return peer.createCriteria(entityName, alias);
	}

	@Override
	public Query createQuery(String queryString) throws HibernateException {
		return peer.createQuery(queryString);
	}

	@Override
	public SQLQuery createSQLQuery(String queryString) throws HibernateException {
		return peer.createSQLQuery(queryString);
	}

	@Override
	public Query createFilter(Object collection, String queryString) throws HibernateException {
		return peer.createFilter(collection, queryString);
	}

	@Override
	public Query getNamedQuery(String queryName) throws HibernateException {
		return peer.getNamedQuery(queryName);
	}

	@Override
	public void clear() {
		peer.clear();
	}

	@Override
	public Object get(Class clazz, Serializable id) throws HibernateException {
		return peer.get(clazz, id);
	}

	@Override
	public Object get(Class clazz, Serializable id, LockMode lockMode) throws HibernateException {
		return peer.get(clazz, id, lockMode);
	}

	@Override
	public Object get(Class clazz, Serializable id, LockOptions lockOptions) throws HibernateException {
		return peer.get(clazz, id, lockOptions);
	}

	@Override
	public Object get(String entityName, Serializable id) throws HibernateException {
		return peer.get(entityName, id);
	}

	@Override
	public Object get(String entityName, Serializable id, LockMode lockMode) throws HibernateException {
		return peer.get(entityName, id, lockMode);
	}

	@Override
	public Object get(String entityName, Serializable id, LockOptions lockOptions) throws HibernateException {
		return peer.get(entityName, id, lockOptions);
	}

	@Override
	public String getEntityName(Object object) throws HibernateException {
		return peer.getEntityName(object);
	}

	@Override
	public Filter enableFilter(String filterName) {
		return peer.enableFilter(filterName);
	}

	@Override
	public Filter getEnabledFilter(String filterName) {
		return peer.getEnabledFilter(filterName);
	}

	@Override
	public void disableFilter(String filterName) {
		peer.disableFilter(filterName);
	}

	@Override
	public SessionStatistics getStatistics() {
		return peer.getStatistics();
	}

	@Override
	public boolean isReadOnly(Object entityOrProxy) {
		return peer.isReadOnly(entityOrProxy);
	}

	@Override
	public void setReadOnly(Object entityOrProxy, boolean readOnly) {
		peer.setReadOnly(entityOrProxy, readOnly);
	}

	@Override
	public void doWork(Work work) throws HibernateException {
		peer.doWork(work);
	}

	@Override
	public Connection disconnect() throws HibernateException {
		return peer.disconnect();
	}

	@Override
	public void reconnect() throws HibernateException {
		peer.reconnect();
	}

	@Override
	public void reconnect(Connection connection) throws HibernateException {
		peer.reconnect(connection);
	}

	@Override
	public boolean isFetchProfileEnabled(String name) throws UnknownProfileException {
		return peer.isFetchProfileEnabled(name);
	}

	@Override
	public void enableFetchProfile(String name) throws UnknownProfileException {
		peer.enableFetchProfile(name);
	}

	@Override
	public void disableFetchProfile(String name) throws UnknownProfileException {
		peer.disableFetchProfile(name);
	}

	@Override
	public TypeHelper getTypeHelper() {
		return peer.getTypeHelper();
	}

	@Override
	public LobHelper getLobHelper() {
		return peer.getLobHelper();
	}
}
