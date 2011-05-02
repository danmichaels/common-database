package me.vgv.common.database.utils;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import me.vgv.common.database.*;
import me.vgv.common.database.config.DatabaseConfiguration;
import me.vgv.common.database.config.FirebirdDatabaseConfiguration;
import me.vgv.common.database.connection.ConnectionFactoryImpl;
import me.vgv.common.database.connection.DriverManagerConnectionProviderImpl;
import me.vgv.common.database.connection.FirebirdTransactionInitPolicy;
import me.vgv.common.database.connection.SqlManagerImpl;
import me.vgv.common.database.transaction.TransactionExecutorImpl;
import me.vgv.common.database.transaction.TransactionInterceptor;
import me.vgv.common.database.transaction.TransactionManagerImpl;
import me.vgv.common.utils.CloseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class TestDatabase {

	private static final Logger log = LoggerFactory.getLogger(TestDatabase.class);

	private final List<String> ddlStatements;
	private final List<String> dmlStatements;
	private final DatabaseConfiguration databaseConfiguration;

	public TestDatabase(String baseDir) {
		// read database properties
		String databaseHost;
		String databaseFile;
		String databaseUser;
		String databasePassword;

		try {
			// default file
			InputStream defaultDatabasePropertiesStream = ClassLoader.getSystemResourceAsStream(baseDir + "/test.database.properties.default");
			Properties defaultDatabaseProperties = new Properties();
			if (defaultDatabasePropertiesStream == null) {
				throw new Exception("can not find file " + baseDir + "/test.database.properites.default");
			}
			defaultDatabaseProperties.load(defaultDatabasePropertiesStream);
			defaultDatabasePropertiesStream.close();

			// override file
			InputStream overrideDatabasePropertiesStream = ClassLoader.getSystemResourceAsStream(baseDir + "/test.database.properties");
			Properties overrideDatabaseProperties = new Properties();
			if (overrideDatabasePropertiesStream != null) {
				overrideDatabaseProperties.load(overrideDatabasePropertiesStream);
				overrideDatabasePropertiesStream.close();
			}

			databaseHost = overrideDatabaseProperties.getProperty("database.host", defaultDatabaseProperties.getProperty("database.host"));
			databaseFile = overrideDatabaseProperties.getProperty("database.file", defaultDatabaseProperties.getProperty("database.file"));
			databaseUser = overrideDatabaseProperties.getProperty("database.user", defaultDatabaseProperties.getProperty("database.user"));
			databasePassword = overrideDatabaseProperties.getProperty("database.password", defaultDatabaseProperties.getProperty("database.password"));

		} catch (Exception e) {
			throw new Error("can't read database properties", e);
		}

		// read create sql statements
		try {
			InputStream createSqlInputStream = ClassLoader.getSystemResourceAsStream(baseDir + "/test.ddl.sql");
			List<String> statements = DatabaseUtils.extractSqlStatementsFromStream(createSqlInputStream);
			createSqlInputStream.close();

			this.ddlStatements = ImmutableList.copyOf(statements);
		} catch (Exception e) {
			throw new Error("can't read " + baseDir + "/test.ddl.sql properties", e);
		}

		// read data sql statements
		try {
			InputStream dataSqlInputStream = ClassLoader.getSystemResourceAsStream(baseDir + "/test.dml.sql");
			List<String> statements = DatabaseUtils.extractSqlStatementsFromStream(dataSqlInputStream);
			dataSqlInputStream.close();

			this.dmlStatements = ImmutableList.copyOf(statements);
		} catch (Exception e) {
			throw new Error("can't read " + baseDir + "/test.dml.sql properties", e);
		}

		// databaseConfiguration


		FirebirdDatabaseConfiguration.Builder databaseBuilder = new FirebirdDatabaseConfiguration.Builder(databaseHost, databaseFile, databaseUser, databasePassword, "UTF-8");
		databaseBuilder.remoteProcessName("database.test");
		databaseConfiguration = databaseBuilder.build();
	}

	private static void executeStatements(DatabaseConfiguration databaseConfiguration, List<String> sqlStatements) {
		// этот метод может вызываться откуда угодно и когда угодно, поэтому загрузим драйвер принудительно
		try {
			Class.forName(databaseConfiguration.getDriverClassName());
		} catch (ClassNotFoundException e) {
			log.error("can't load JDBC driver class " + databaseConfiguration.getDriverClassName(), e);
		}

		// создадим DriverManagerConnectionProviderImpl
		ConnectionProvider connectionProvider = new DriverManagerConnectionProviderImpl(databaseConfiguration, new FirebirdTransactionInitPolicy());

		// выполним все sql-операторы один за другим, каждый в отдельном подключении к базе
		for (String sqlStatement : sqlStatements) {
			Connection connection = null;
			try {
				connection = connectionProvider.getConnection();
				Statement statement = connection.createStatement();
				statement.execute(sqlStatement);
				statement.close();
				connection.commit();
			} catch (SQLException e) {
				DatabaseUtils.rollbackConnection(connection);
				log.error("can't execute sql script \"" + sqlStatement + "\"", e);
			} finally {
				CloseUtils.close(connection);
			}
		}
	}

	public Module getTestDatabaseModule() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				bind(DatabaseConfiguration.class).toInstance(databaseConfiguration);

				// database classes
				bind(ConnectionProvider.class).to(DriverManagerConnectionProviderImpl.class).in(Singleton.class);
				bind(ConnectionFactory.class).to(ConnectionFactoryImpl.class).in(Singleton.class);
				bind(TransactionInitPolicy.class).to(FirebirdTransactionInitPolicy.class).in(Singleton.class);
				bind(TransactionManager.class).to(TransactionManagerImpl.class).in(Singleton.class);
				bind(TransactionExecutor.class).to(TransactionExecutorImpl.class).in(Singleton.class);
				bind(SqlManager.class).to(SqlManagerImpl.class).in(Singleton.class);

				// interceptors
				TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
				requestInjection(transactionInterceptor);
				bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class), transactionInterceptor);
			}
		};
	}

	public DatabaseConfiguration getDatabaseConfiguration() {
		return databaseConfiguration;
	}

	public void recreateTestDatabase() {
		DatabaseConfiguration databaseConfiguration = getDatabaseConfiguration();
		executeStatements(databaseConfiguration, ddlStatements);
		executeStatements(databaseConfiguration, dmlStatements);
	}
}
