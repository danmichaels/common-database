package me.vgv.common.database.connection;

import me.vgv.common.database.CommonDatabaseException;
import me.vgv.common.database.transaction.TransactionDefinition;
import org.firebirdsql.gds.TransactionParameterBuffer;
import org.firebirdsql.jdbc.FBConnection;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class FirebirdTransactionInitPolicyTest {

	@Test(groups = "unit")
	public void testExtractVendorConnectionFromFirebirdConnection() throws Exception {
		FirebirdTransactionInitPolicy transactionPolicy = new FirebirdTransactionInitPolicy();

		FBConnection fbConnection = Mockito.mock(FBConnection.class);
		Assert.assertSame(fbConnection, transactionPolicy.extractVendorConnection(fbConnection));
	}

	@Test(expectedExceptions = CommonDatabaseException.class, groups = "unit")
	public void testExtractVendorConnectionFromUnknownConnection() throws Exception {
		FirebirdTransactionInitPolicy transactionPolicy = new FirebirdTransactionInitPolicy();

		Connection connection = Mockito.mock(Connection.class);
		Assert.assertSame(connection, transactionPolicy.extractVendorConnection(connection));
	}

	@Test(groups = "unit")
	public void testApply() throws Exception {
		TransactionParameterBuffer transactionParameterBuffer = Mockito.mock(TransactionParameterBuffer.class);

		FBConnection connection = Mockito.mock(FBConnection.class);
		Mockito.when(connection.getAutoCommit()).thenReturn(true);  // ниже мы проверим что будет вызов, переводящий autocommit в false 
		Mockito.when(connection.createTransactionParameterBuffer()).thenReturn(transactionParameterBuffer);

		FirebirdTransactionInitPolicy transactionPolicy = new FirebirdTransactionInitPolicy();

		Assert.assertSame(connection, transactionPolicy.initTransaction(connection, TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION));
		Mockito.verify(connection, VerificationModeFactory.times(1)).setAutoCommit(false);
		Mockito.verify(connection, VerificationModeFactory.times(1)).createTransactionParameterBuffer();
		Mockito.verify(connection, VerificationModeFactory.times(1)).setTransactionParameters(transactionParameterBuffer);
	}


}
