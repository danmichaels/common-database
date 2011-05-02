package me.vgv.common.database.connection;

import com.google.inject.Inject;
import me.vgv.common.database.CommonDatabaseException;
import me.vgv.common.database.ConnectionFactory;
import me.vgv.common.database.ConnectionProvider;
import me.vgv.common.database.TransactionManager;
import me.vgv.common.database.transaction.Transaction;
import me.vgv.common.database.transaction.TransactionDefinition;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ConnectionFactoryImpl implements ConnectionFactory {

	private final ConnectionProvider connectionProvider;
	private final TransactionManager transactionManager;

	@Inject
	public ConnectionFactoryImpl(ConnectionProvider connectionProvider, TransactionManager transactionManager) {
		this.connectionProvider = connectionProvider;
		this.transactionManager = transactionManager;
	}

	@Override
	public Connection getCurrentConnection() {
		Transaction transaction = transactionManager.getCurrentTransaction();
		Connection connection = transactionManager.getConnection(transaction);

		if (connection == null) {
			try {
				connection = new ManagedConnection(connectionProvider.getConnection(transaction.getTransactionDefinition()));
				transactionManager.associateConnection(transaction, connection);
			} catch (SQLException e) {
				throw new CommonDatabaseException("can not open new connection", e);
			}
		}

		return connection;
	}

	@Override
	public Connection openConnection() {
		try {
			return connectionProvider.getConnection();
		} catch (SQLException e) {
			throw new CommonDatabaseException("can not open new connection", e);
		}
	}

	@Override
	public Connection openConnection(TransactionDefinition transactionDefinition) {
		try {
			return connectionProvider.getConnection(transactionDefinition);
		} catch (SQLException e) {
			throw new CommonDatabaseException("can not open new connection", e);
		}
	}
}
