package me.vgv.common.database.hibernate;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.vgv.common.database.config.HibernateConfiguration;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class BaseSessionFactory implements SessionFactory {

	private final SessionFactory sessionFactory;

	@Inject
	public BaseSessionFactory(HibernateConfiguration hibernateConfiguration, Injector injector) {
		Configuration configuration = new Configuration();

		// Настройки Hibernate
		configuration.setProperty(Environment.DIALECT, hibernateConfiguration.getDialect());
		configuration.setProperty(Environment.CACHE_PROVIDER, hibernateConfiguration.getCacheProviderClass());
		configuration.setProperty(Environment.USE_QUERY_CACHE, String.valueOf(hibernateConfiguration.getUseQueryCache()));
		configuration.setProperty(Environment.USE_SECOND_LEVEL_CACHE, String.valueOf(hibernateConfiguration.getUseSecondLevelCache()));
		configuration.setProperty(Environment.FORMAT_SQL, String.valueOf(hibernateConfiguration.isFormatSql()));
		configuration.setProperty(Environment.SHOW_SQL, String.valueOf(hibernateConfiguration.isShowSql()));
		configuration.setProperty(Environment.HBM2DDL_AUTO, hibernateConfiguration.getUpdateDatabaseMode().getHibernateParameter());
		configuration.setProperty(Environment.AUTO_CLOSE_SESSION, "false");
		configuration.setProperty(Environment.FLUSH_BEFORE_COMPLETION, "true");
		configuration.setProperty(Environment.CONNECTION_PROVIDER, HibernateConnectionProvider.class.getName());
		configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, HibernateCurrentSessionContext.class.getName());

		// добавим entity-классы
		for (Class<?> annotatedClass : hibernateConfiguration.getAnnotatedClasses()) {
			configuration.addAnnotatedClass(annotatedClass);
		}

		try {
			// это трюк, объяснение в HibernateConnectionProvider
			HibernateConnectionProvider.injector = injector;
			HibernateCurrentSessionContext.injector = injector;

			this.sessionFactory = configuration.buildSessionFactory();
		} finally {
			HibernateCurrentSessionContext.injector = null;
			HibernateConnectionProvider.injector = null;
		}
	}

	@Override
	public Session openSession() throws HibernateException {
		return sessionFactory.openSession();
	}

	@Override
	public Session openSession(Interceptor interceptor) throws HibernateException {
		return sessionFactory.openSession(interceptor);
	}

	@Override
	public Session openSession(Connection connection) {
		return sessionFactory.openSession(connection);
	}

	@Override
	public Session openSession(Connection connection, Interceptor interceptor) {
		return sessionFactory.openSession(connection, interceptor);
	}

	@Override
	public Session getCurrentSession() throws HibernateException {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public StatelessSession openStatelessSession() {
		return sessionFactory.openStatelessSession();
	}

	@Override
	public StatelessSession openStatelessSession(Connection connection) {
		return sessionFactory.openStatelessSession(connection);
	}

	@Override
	public ClassMetadata getClassMetadata(Class entityClass) {
		return sessionFactory.getClassMetadata(entityClass);
	}

	@Override
	public ClassMetadata getClassMetadata(String entityName) {
		return sessionFactory.getClassMetadata(entityName);
	}

	@Override
	public CollectionMetadata getCollectionMetadata(String roleName) {
		return sessionFactory.getCollectionMetadata(roleName);
	}

	@Override
	public Map<String, ClassMetadata> getAllClassMetadata() {
		return sessionFactory.getAllClassMetadata();
	}

	@Override
	public Map getAllCollectionMetadata() {
		return sessionFactory.getAllCollectionMetadata();
	}

	@Override
	public Statistics getStatistics() {
		return sessionFactory.getStatistics();
	}

	@Override
	public void close() throws HibernateException {
		sessionFactory.close();
	}

	@Override
	public boolean isClosed() {
		return sessionFactory.isClosed();
	}

	@Override
	public Cache getCache() {
		return sessionFactory.getCache();
	}

	@Override
	public void evict(Class persistentClass) throws HibernateException {
		sessionFactory.evict(persistentClass);
	}

	@Override
	public void evict(Class persistentClass, Serializable id) throws HibernateException {
		sessionFactory.evict(persistentClass, id);
	}

	@Override
	public void evictEntity(String entityName) throws HibernateException {
		sessionFactory.evictEntity(entityName);
	}

	@Override
	public void evictEntity(String entityName, Serializable id) throws HibernateException {
		sessionFactory.evictEntity(entityName, id);
	}

	@Override
	public void evictCollection(String roleName) throws HibernateException {
		sessionFactory.evictCollection(roleName);
	}

	@Override
	public void evictCollection(String roleName, Serializable id) throws HibernateException {
		sessionFactory.evictCollection(roleName, id);
	}

	@Override
	public void evictQueries(String cacheRegion) throws HibernateException {
		sessionFactory.evictQueries(cacheRegion);
	}

	@Override
	public void evictQueries() throws HibernateException {
		sessionFactory.evictQueries();
	}

	@Override
	public Set getDefinedFilterNames() {
		return sessionFactory.getDefinedFilterNames();
	}

	@Override
	public FilterDefinition getFilterDefinition(String filterName) throws HibernateException {
		return sessionFactory.getFilterDefinition(filterName);
	}

	@Override
	public boolean containsFetchProfileDefinition(String name) {
		return sessionFactory.containsFetchProfileDefinition(name);
	}

	@Override
	public TypeHelper getTypeHelper() {
		return sessionFactory.getTypeHelper();
	}

	@Override
	public Reference getReference() throws NamingException {
		return sessionFactory.getReference();
	}
}
