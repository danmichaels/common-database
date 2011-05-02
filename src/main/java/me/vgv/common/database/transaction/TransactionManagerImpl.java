package me.vgv.common.database.transaction;

import com.google.common.base.Preconditions;
import me.vgv.common.database.CommonDatabaseException;
import me.vgv.common.database.TransactionManager;
import me.vgv.common.database.connection.ManagedConnection;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class TransactionManagerImpl implements TransactionManager {

	// блокировки
	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
	private final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

	// структуры данных
	// хранилище транзакций, ассоциированных с потоком (может быть больше 1, например, при вложенных транзакциях)
	private final ThreadLocal<List<Transaction>> transactions = new ThreadLocal<List<Transaction>>() {
		@Override
		protected List<Transaction> initialValue() {
			return new ArrayList<Transaction>();
		}
	};
	// ассоциация подключения с транзакцией
	private final Map<Transaction, Connection> connections = new HashMap<Transaction, Connection>();
	// ассоциация сессии с транзакцией
	private final Map<Transaction, Session> sessions = new HashMap<Transaction, Session>();

	@Override
	public void startTransaction(Transaction transaction) {
		Preconditions.checkNotNull(transaction, "transaction is null - error");

		writeLock.lock();
		try {
			List<Transaction> transactionList = transactions.get();
			transactionList.add(transaction);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void commitTransaction(Transaction transaction) {
		Preconditions.checkNotNull(transaction, "transaction is null - error");

		writeLock.lock();
		try {
			List<Transaction> transactionList = transactions.get();
			Transaction lastTransaction = transactionList.isEmpty() ? null : transactionList.get(transactionList.size() - 1);

			// мы пробуем остановить не текущую а какую-то другую транзакцию
			if (!transaction.equals(lastTransaction)) {
				throw new CommonDatabaseException("you are trying to stop non-active transaction");
			}


			boolean connectionAlreadyClosed = false;

			// мы останавливаем правильную транзакцию
			Session session = sessions.remove(transaction);
			if (session != null) {
				// надо правильно закрыть сессию
				session.getTransaction().commit();
				session.close();
				connectionAlreadyClosed = true;
			}

			//
			Connection connection = connections.remove(transaction);
			if (connection != null && !connectionAlreadyClosed) {
				try {
					Connection baseConnection = ((ManagedConnection) connection).getUnderlyingConnection();
					baseConnection.commit();
					baseConnection.close();
				} catch (SQLException e) {
					throw new CommonDatabaseException("can not commit transaction (on connection)", e);
				}
			}

			// ну и удалим транзакцию
			transactionList.remove(transactionList.size() - 1);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void rollbackTransaction(Transaction transaction) {
		Preconditions.checkNotNull(transaction, "transaction is null - error");

		writeLock.lock();
		try {
			List<Transaction> transactionList = transactions.get();
			Transaction lastTransaction = transactionList.isEmpty() ? null : transactionList.get(transactionList.size() - 1);

			// мы пробуем остановить не текущую а какую-то другую транзакцию
			if (!transaction.equals(lastTransaction)) {
				throw new CommonDatabaseException("you are trying to stop non-active transaction");
			}

			boolean connectionAlreadyClosed = false;

			// мы останавливаем правильную транзакцию
			Session session = sessions.remove(transaction);
			if (session != null) {
				// надо правильно закрыть сессию
				session.getTransaction().rollback();
				session.close();
				connectionAlreadyClosed = true;
			}

			//
			Connection connection = connections.remove(transaction);
			if (connection != null && !connectionAlreadyClosed) {
				try {
					Connection baseConnection = ((ManagedConnection) connection).getUnderlyingConnection();
					baseConnection.rollback();
					baseConnection.close();
				} catch (SQLException e) {
					throw new CommonDatabaseException("can not rollback transaction (on connection)", e);
				}
			}

			// ну и удалим транзакцию
			transactionList.remove(transactionList.size() - 1);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean hasCurrentTransaction() {
		boolean result = false;

		readLock.lock();
		try {
			List<Transaction> transactionList = transactions.get();
			result = !transactionList.isEmpty();
		} finally {
			readLock.unlock();
		}

		return result;
	}

	@Override
	public Transaction getCurrentTransaction() {
		Transaction transaction = null;

		readLock.lock();
		try {
			List<Transaction> transactionList = transactions.get();
			if (transactionList.isEmpty()) {
				throw new NoCurrentTransactionException("no transaction associated with thread");
			} else {
				transaction = transactionList.get(transactionList.size() - 1);
			}
		} finally {
			readLock.unlock();
		}

		return transaction;
	}

	@Override
	public Connection getConnection(Transaction transaction) {
		Preconditions.checkNotNull(transaction, "transaction is null - error");

		Connection connection = null;

		readLock.lock();
		try {
			connection = connections.get(transaction);
		} finally {
			readLock.unlock();
		}

		return connection;
	}


	@Override
	public void associateConnection(Transaction transaction, Connection connection) {
		Preconditions.checkNotNull(transaction, "transaction is null - error");
		Preconditions.checkNotNull(connection, "connection is null - error");

		writeLock.lock();
		try {
			if (connections.containsKey(transaction)) {
				throw new CommonDatabaseException("transaction already associated with connection");
			} else {
				connections.put(transaction, connection);
			}
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public Session getSession(Transaction transaction) {
		Preconditions.checkNotNull(transaction, "transaction is null - error");

		Session session = null;
		readLock.lock();
		try {
			session = sessions.get(transaction);
		} finally {
			readLock.unlock();
		}
		return session;
	}

	@Override
	public void associateSession(Transaction transaction, Session session) {
		Preconditions.checkNotNull(transaction, "transaction is null - error");
		Preconditions.checkNotNull(session, "session is null - error");

		writeLock.lock();
		try {
			if (sessions.containsKey(transaction)) {
				throw new CommonDatabaseException("transaction already associated with session");
			} else {
				sessions.put(transaction, session);
			}
		} finally {
			writeLock.unlock();
		}
	}
}
