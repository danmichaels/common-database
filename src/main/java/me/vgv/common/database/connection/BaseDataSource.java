package me.vgv.common.database.connection;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import me.vgv.common.database.TransactionInitPolicy;
import me.vgv.common.database.config.DatabaseConfiguration;
import me.vgv.common.database.config.DatabasePoolConfiguration;
import me.vgv.common.database.transaction.TransactionDefinition;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Реализация DataSource который основан на DBCP BasicDataSource и все вызовы транслирует ему.
 * Преимущество этого класса над "просто использовать BasicDataSource" - этот класс настраивается
 * нашими конфигами DatabaseConfiguration и DatabaseConfigurationPool
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class BaseDataSource implements DataSource {

	private final TransactionInitPolicy transactionInitPolicy;
	private final BasicDataSource dataSource;

	@Inject
	public BaseDataSource(DatabaseConfiguration databaseConfiguration, DatabasePoolConfiguration databasePoolConfiguration, TransactionInitPolicy transactionInitPolicy) {
		Preconditions.checkNotNull(transactionInitPolicy, "transactionInitPolicy is null - error");
		this.transactionInitPolicy = transactionInitPolicy;

		// создадим dataSource
		this.dataSource = new BasicDataSource();
		this.dataSource.setDriverClassName(databaseConfiguration.getDriverClassName());
		this.dataSource.setUrl(databaseConfiguration.getJdbcUrl());
		this.dataSource.setUsername(databaseConfiguration.getUser());
		this.dataSource.setPassword(databaseConfiguration.getPassword());
		this.dataSource.setAccessToUnderlyingConnectionAllowed(true);
		this.dataSource.setDefaultAutoCommit(false);

		// настроим connection pool
		this.dataSource.setInitialSize(databasePoolConfiguration.getInitialPoolSize());
		this.dataSource.setMaxActive(databasePoolConfiguration.getMaxActivePoolSize());
		this.dataSource.setMinIdle(databasePoolConfiguration.getMinIdlePoolSize());
		this.dataSource.setMaxIdle(databasePoolConfiguration.getMaxIdlePoolSize());
		this.dataSource.setTimeBetweenEvictionRunsMillis(databasePoolConfiguration.getIdleEvictorRunInterval());
		this.dataSource.setMinEvictableIdleTimeMillis(databasePoolConfiguration.getIdleConnectionLifetime());
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection connection = dataSource.getConnection();
		return transactionInitPolicy.initTransaction(connection, TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION);
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		Connection connection = dataSource.getConnection(username, password);
		return transactionInitPolicy.initTransaction(connection, TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return dataSource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return dataSource.isWrapperFor(iface);
	}

	public void close() throws SQLException {
		dataSource.close();
	}

	public boolean isClosed() {
		return dataSource.isClosed();
	}
}
