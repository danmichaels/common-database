package me.vgv.common.database.connection;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import me.vgv.common.database.TransactionInitPolicy;
import me.vgv.common.database.config.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectionManager, вытаскивающий подключение не из пула, а напрямую из DriverManager'а
 * У Connection'а свойство autoCommit устанавливается в false
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DriverManagerConnectionProviderImpl extends AbstractConnectionProviderImpl {

	private final DatabaseConfiguration databaseConfiguration;

	@Inject
	public DriverManagerConnectionProviderImpl(DatabaseConfiguration databaseConfiguration, TransactionInitPolicy transactionInitPolicy) {
		super(transactionInitPolicy);

		Preconditions.checkNotNull(databaseConfiguration, "databaseConfiguration is null");
		this.databaseConfiguration = databaseConfiguration;
	}

	@Override
	protected Connection getBaseConnection() throws SQLException {
		String url = databaseConfiguration.getJdbcUrl();
		String user = databaseConfiguration.getUser();
		String password = databaseConfiguration.getPassword();

		Connection connection = DriverManager.getConnection(url, user, password);
		connection.setAutoCommit(false);

		return connection;
	}
}
