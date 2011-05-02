package me.vgv.common.database.connection;

import me.vgv.common.database.CommonDatabaseException;
import me.vgv.common.database.ConnectionProvider;
import me.vgv.common.database.TransactionManager;
import me.vgv.common.database.transaction.NoCurrentTransactionException;
import me.vgv.common.database.transaction.Transaction;
import me.vgv.common.database.transaction.TransactionDefinition;
import me.vgv.common.database.transaction.TransactionPropagation;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class ConnectionFactoryImplTest {

	@Test(groups = "unit", expectedExceptions = NoCurrentTransactionException.class)
	public void testGetCurrentConnectionIfNoTransactionContext() throws Exception {
		// настроим моки и тестовые объекты
		TransactionManager transactionManager = Mockito.mock(TransactionManager.class);
		ConnectionProvider connectionProvider = Mockito.mock(ConnectionProvider.class);
		Mockito.when(transactionManager.getCurrentTransaction()).thenThrow(new NoCurrentTransactionException("test"));

		ConnectionFactoryImpl connectionFactory = new ConnectionFactoryImpl(connectionProvider, transactionManager);
		connectionFactory.getCurrentConnection();
	}

	@Test(groups = "unit")
	public void testGetCurrentConnectionIfNoConnection() throws Exception {
		// настроим моки и тестовые объекты
		Transaction transaction = new Transaction(TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION);
		Connection baseConnection = Mockito.mock(Connection.class);
		TransactionManager transactionManager = Mockito.mock(TransactionManager.class);
		ConnectionProvider connectionProvider = Mockito.mock(ConnectionProvider.class);
		Mockito.when(transactionManager.getCurrentTransaction()).thenReturn(transaction);
		Mockito.when(connectionProvider.getConnection(TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION)).thenReturn(baseConnection);

		// сделаем вызовы
		ConnectionFactoryImpl connectionFactory = new ConnectionFactoryImpl(connectionProvider, transactionManager);
		Connection managedConnection = connectionFactory.getCurrentConnection();

		Assert.assertTrue(managedConnection instanceof ManagedConnection);
		Mockito.verify(connectionProvider, VerificationModeFactory.times(1)).getConnection(TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION);
		Mockito.verify(transactionManager, VerificationModeFactory.times(1)).associateConnection(transaction, managedConnection);
	}

	@Test(groups = "unit")
	public void testGetCurrentConnectionIfHasConnection() throws Exception {
		// настроим моки и тестовые объекты
		Transaction transaction = new Transaction(TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION);
		Connection baseConnection = Mockito.mock(Connection.class);
		TransactionManager transactionManager = Mockito.mock(TransactionManager.class);
		ConnectionProvider connectionProvider = Mockito.mock(ConnectionProvider.class);
		Mockito.when(transactionManager.getCurrentTransaction()).thenReturn(transaction);
		Mockito.when(transactionManager.getConnection(transaction)).thenReturn(baseConnection);

		// сделаем вызовы
		ConnectionFactoryImpl connectionFactory = new ConnectionFactoryImpl(connectionProvider, transactionManager);
		Connection connection = connectionFactory.getCurrentConnection();

		Assert.assertSame(connection, baseConnection);
		Mockito.verifyZeroInteractions(connectionProvider);
	}

	@Test(groups = "unit")
	public void testGetCurrentConnectionPropagateException() throws Exception {
		// настроим моки и тестовые объекты
		Transaction transaction = new Transaction(TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION);
		SQLException sqlException = new SQLException();
		TransactionManager transactionManager = Mockito.mock(TransactionManager.class);
		ConnectionProvider connectionProvider = Mockito.mock(ConnectionProvider.class);
		Mockito.when(transactionManager.getCurrentTransaction()).thenReturn(transaction);
		Mockito.when(connectionProvider.getConnection(TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION)).thenThrow(sqlException);

		// сделаем вызовы
		ConnectionFactoryImpl connectionFactory = new ConnectionFactoryImpl(connectionProvider, transactionManager);
		try {
			Connection managedConnection = connectionFactory.getCurrentConnection();
			Assert.fail("Where is the exception?");
		} catch (CommonDatabaseException e) {
			Assert.assertSame(e.getCause(), sqlException);
		}
	}

	@Test(groups = "unit")
	public void testOpenConnectionWithTransactionParams() throws Exception {
		// настроим моки
		ConnectionProvider connectionProvider = Mockito.mock(ConnectionProvider.class);
		Connection baseConnection = Mockito.mock(Connection.class);
		TransactionDefinition transactionDefinition = new TransactionDefinition(true, false, TransactionPropagation.REQUIRES_NEW);
		Mockito.when(connectionProvider.getConnection(transactionDefinition)).thenReturn(baseConnection);

		// сделаем вызовы
		ConnectionFactoryImpl connectionFactory = new ConnectionFactoryImpl(connectionProvider, Mockito.mock(TransactionManager.class));
		Connection connection = connectionFactory.openConnection(transactionDefinition);

		// проверим моки
		Assert.assertSame(connection, baseConnection);
		Mockito.verify(connectionProvider, VerificationModeFactory.times(1)).getConnection(transactionDefinition);
	}

	@Test(groups = "unit")
	public void testOpenConnection() throws Exception {
		// настроим моки
		ConnectionProvider connectionProvider = Mockito.mock(ConnectionProvider.class);
		Connection baseConnection = Mockito.mock(Connection.class);
		Mockito.when(connectionProvider.getConnection()).thenReturn(baseConnection);

		// сделаем вызовы
		ConnectionFactoryImpl connectionFactory = new ConnectionFactoryImpl(connectionProvider, Mockito.mock(TransactionManager.class));
		Connection connection = connectionFactory.openConnection();

		// проверим моки
		Assert.assertSame(connection, baseConnection);
		Mockito.verify(connectionProvider, VerificationModeFactory.times(1)).getConnection();
	}

}
