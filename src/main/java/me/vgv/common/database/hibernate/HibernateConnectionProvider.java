package me.vgv.common.database.hibernate;

import com.google.inject.Injector;
import me.vgv.common.database.ConnectionFactory;
import me.vgv.common.database.connection.ManagedConnection;
import me.vgv.common.database.transaction.NoCurrentTransactionException;
import org.hibernate.HibernateException;
import org.hibernate.connection.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class HibernateConnectionProvider implements ConnectionProvider {

	private static final Logger log = LoggerFactory.getLogger(HibernateConnectionProvider.class);

	/**
	 * Это хитрожопый трюк
	 * Этот объект создается Hibernat'ом (не Guice!), поэтому взять connectionManager и connectionContext негде
	 * Перед созданием SessionFactory это статическое поле получает свое
	 * значение, после создания - сразу заnullяется (потому что нефиг!)
	 */
	public static Injector injector;

	private final ConnectionFactory connectionFactory;

	public HibernateConnectionProvider() {
		connectionFactory = injector.getInstance(ConnectionFactory.class);
	}

	@Override
	public void configure(Properties properties) throws HibernateException {
	}

	@Override
	public Connection getConnection() throws SQLException {
		// этот метод может быть вызван кем угодно, в частности методами
		// 1) SessionFactory.openSession()
		// 2) SessionFactory.getCurrentSession()
		// очевидно, что метод (1) может быть вызван из любого контекста, как транзакционного так и нет
		// поэтому соединение должно быть предоставлено в любом случае. Метод (2) вызывается только
		// из транзакционного контекста

		if (CurrentSessionContextHolder.isInCurrentSessionContext()) {
			try {
				ManagedConnection managedConnection = (ManagedConnection) connectionFactory.getCurrentConnection();
				return managedConnection.getUnderlyingConnection();
			} catch (NoCurrentTransactionException e) {
				// поскольку тут нет транзакционного контекста, то вообще неизвестно какой тип транзакции
				// надо создать, поэтому создадим обычное подключение, т.е. дефолтное
				return connectionFactory.openConnection();
			}
		} else {
			return connectionFactory.openConnection();
		}
	}

	@Override
	public void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public void close() throws HibernateException {

	}

	@Override
	public boolean supportsAggressiveRelease() {
		return true;
	}
}
