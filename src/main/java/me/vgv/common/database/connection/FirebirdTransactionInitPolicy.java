package me.vgv.common.database.connection;

import me.vgv.common.database.CommonDatabaseException;
import me.vgv.common.database.TransactionInitPolicy;
import me.vgv.common.database.transaction.TransactionDefinition;
import org.apache.commons.dbcp.DelegatingConnection;
import org.firebirdsql.gds.TransactionParameterBuffer;
import org.firebirdsql.jdbc.FBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * TransactionInitPolicy переводящая абстрактные типы транзакций в Firebird-специфичные
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class FirebirdTransactionInitPolicy implements TransactionInitPolicy {

	private static final Logger log = LoggerFactory.getLogger(FirebirdTransactionInitPolicy.class);

	private static final int[] NORMAL_TRANSACTION_PARAMS = new int[]{
			TransactionParameterBuffer.READ_COMMITTED,
			TransactionParameterBuffer.WRITE,
			TransactionParameterBuffer.REC_VERSION,
			TransactionParameterBuffer.NOWAIT};
	private static final int[] NORMAL_READONLY_TRANSACTION_PARAMS = new int[]{
			TransactionParameterBuffer.READ_COMMITTED,
			TransactionParameterBuffer.READ,
			TransactionParameterBuffer.REC_VERSION,
			TransactionParameterBuffer.NOWAIT};
	private static final int[] SNAPSHOT_TRANSACTION_PARAMS = new int[]{
			TransactionParameterBuffer.CONCURRENCY,
			TransactionParameterBuffer.WRITE,
			TransactionParameterBuffer.NOWAIT};
	private static final int[] SNAPSHOT_READONLY_TRANSACTION_PARAMS = new int[]{
			TransactionParameterBuffer.CONCURRENCY,
			TransactionParameterBuffer.READ,
			TransactionParameterBuffer.NOWAIT};

	/**
	 * Вытаскивает самый "нижний" vendor-connection
	 *
	 * @param connection connection
	 * @return FBConnection
	 */
	FBConnection extractVendorConnection(Connection connection) {
		if (connection instanceof FBConnection) {
			// в случае, если этот connection получен через DriverManager
			return (FBConnection) connection;
		} else if (connection instanceof DelegatingConnection) {
			// в случае, если этот connection получен от DBCP пула
			return extractVendorConnection(((DelegatingConnection) connection).getInnermostDelegate());
		} else {
			// какой-то третий случай, зарапортуем о нем
			log.error("Can't extract FBConnection from " + connection.getClass().getName());
			throw new CommonDatabaseException("can't extract FBConnection from " + connection.getClass().getName());
		}
	}

	int[] convertTransactionTypeToVendorParameters(TransactionDefinition transactionDefinition) {
		if (transactionDefinition.isSnapshot()) {
			if (transactionDefinition.isReadOnly()) {
				return SNAPSHOT_READONLY_TRANSACTION_PARAMS;
			} else {
				return SNAPSHOT_TRANSACTION_PARAMS;
			}
		} else {
			if (transactionDefinition.isReadOnly()) {
				return NORMAL_READONLY_TRANSACTION_PARAMS;
			} else {
				return NORMAL_TRANSACTION_PARAMS;
			}
		}
	}

	@Override
	public Connection initTransaction(Connection connection, TransactionDefinition transactionDefinition) throws SQLException {
		// получим vendor-connection
		FBConnection baseConnection = extractVendorConnection(connection);

		if (baseConnection.getAutoCommit()) {
			baseConnection.setAutoCommit(false);
			log.warn("connection is in autocommit=true state, switch to autocommit=false.");
		}

		TransactionParameterBuffer transactionParameterBuffer = baseConnection.createTransactionParameterBuffer();
		for (Integer transactionParameter : convertTransactionTypeToVendorParameters(transactionDefinition)) {
			transactionParameterBuffer.addArgument(transactionParameter);
		}
		baseConnection.setTransactionParameters(transactionParameterBuffer);

		return connection;
	}
}
