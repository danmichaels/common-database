package me.vgv.common.database.config;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class HibernateConfigurationImpl implements HibernateConfiguration {

	private final String dialect;
	private final String cacheProviderClass;
	private final boolean useQueryCache;
	private final boolean useSecondLevelCache;
	private final boolean formatSql;
	private final boolean showSql;
	private final HibernateDatabaseUpdateMode updateDatabaseMode;
	private final List<Class<?>> annotatedClasses;

	private HibernateConfigurationImpl(String dialect, String cacheProviderClass, boolean useQueryCache, boolean useSecondLevelCache, boolean formatSql, boolean showSql, HibernateDatabaseUpdateMode updateDatabaseMode, List<Class<?>> annotatedClasses) {
		this.dialect = dialect;
		this.cacheProviderClass = cacheProviderClass;
		this.useQueryCache = useQueryCache;
		this.useSecondLevelCache = useSecondLevelCache;
		this.formatSql = formatSql;
		this.showSql = showSql;
		this.updateDatabaseMode = updateDatabaseMode;
		if (annotatedClasses == null) {
			this.annotatedClasses = ImmutableList.of();
		} else {
			this.annotatedClasses = ImmutableList.copyOf(annotatedClasses);
		}
	}

	public static class Builder {
		private final String dialect;
		private String cacheProviderClass = "";
		private boolean useQueryCache = false;
		private boolean useSecondLevelCache = false;
		private boolean formatSql = true;
		private boolean showSql = false;
		private HibernateDatabaseUpdateMode updateDatabaseMode = HibernateDatabaseUpdateMode.VALIDATE;
		private final List<Class<?>> annotatedClasses;

		public Builder(String dialect, List<Class<?>> annotatedClasses) {
			this.dialect = dialect;
			this.annotatedClasses = annotatedClasses;
		}

		public Builder cacheProviderClass(String cacheProviderClass) {
			this.cacheProviderClass = cacheProviderClass;
			return this;
		}

		public Builder useQueryCache(boolean useQueryCache) {
			this.useQueryCache = useQueryCache;
			return this;
		}

		public Builder useSecondLevelCache(boolean useSecondLevelCache) {
			this.useSecondLevelCache = useSecondLevelCache;
			return this;
		}

		public Builder formatSql(boolean formatSql) {
			this.formatSql = formatSql;
			return this;
		}

		public Builder showSql(boolean showSql) {
			this.showSql = showSql;
			return this;
		}

		public Builder updateDatabaseMode(HibernateDatabaseUpdateMode updateDatabaseMode) {
			this.updateDatabaseMode = updateDatabaseMode;
			return this;
		}

		public HibernateConfiguration build() {
			return new HibernateConfigurationImpl(dialect, cacheProviderClass, useQueryCache, useSecondLevelCache, formatSql, showSql, updateDatabaseMode, annotatedClasses);
		}
	}

	@Override
	public String getDialect() {
		return dialect;
	}

	@Override
	public String getCacheProviderClass() {
		return cacheProviderClass;
	}

	@Override
	public boolean getUseQueryCache() {
		return useQueryCache;
	}

	@Override
	public boolean getUseSecondLevelCache() {
		return useSecondLevelCache;
	}

	@Override
	public boolean isFormatSql() {
		return formatSql;
	}

	@Override
	public boolean isShowSql() {
		return showSql;
	}

	@Override
	public HibernateDatabaseUpdateMode getUpdateDatabaseMode() {
		return updateDatabaseMode;
	}

	@Override
	public List<Class<?>> getAnnotatedClasses() {
		return annotatedClasses;
	}
}
