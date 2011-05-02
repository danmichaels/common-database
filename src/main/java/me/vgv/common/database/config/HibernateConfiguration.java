package me.vgv.common.database.config;

import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface HibernateConfiguration {

	String getDialect();

	String getCacheProviderClass();

	boolean getUseQueryCache();

	boolean getUseSecondLevelCache();

	boolean isFormatSql();

	boolean isShowSql();

	HibernateDatabaseUpdateMode getUpdateDatabaseMode();

	List<Class<?>> getAnnotatedClasses();

}
