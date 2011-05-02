package me.vgv.common.database.connection;

import com.google.common.base.Preconditions;
import me.vgv.common.database.ConnectionProvider;
import me.vgv.common.database.TransactionInitPolicy;
import me.vgv.common.database.transaction.TransactionDefinition;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Это абстрактный родитель для всех ConnectionProvider'ов. Он все еще не знает откуда берется
 * реальное подключение к базе данных, но уже умеет настраивать полученное подключение
 * выбранными параметрами транзакции.
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public abstract class AbstractConnectionProviderImpl implements ConnectionProvider {

	private final TransactionInitPolicy transactionInitPolicy;

	protected AbstractConnectionProviderImpl(TransactionInitPolicy transactionInitPolicy) {
		Preconditions.checkNotNull(transactionInitPolicy, "transactionInitPolicy is null");

		this.transactionInitPolicy = transactionInitPolicy;
	}

	/**
	 * Этот метод необходимо переопределить в наследниках.
	 * Он отвечает за получение реального подключения к базе данных
	 *
	 * @return подключение к базе
	 * @throws java.sql.SQLException если что-то пошло не так
	 */
	protected abstract Connection getBaseConnection() throws SQLException;

	@Override
	public Connection getConnection() throws SQLException {
		// получим реальное подключение
		Connection connection = getBaseConnection();
		// применим дефолтные параметры транзакции
		return transactionInitPolicy.initTransaction(connection, TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION);
	}

	@Override
	public Connection getConnection(TransactionDefinition transactionDefinition) throws SQLException {
		// получим реальное подключение
		Connection connection = getBaseConnection();
		// применим нужные параметры транзакции
		return transactionInitPolicy.initTransaction(connection, transactionDefinition);
	}
}
