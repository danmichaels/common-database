package me.vgv.common.database.connection;

import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class ConnectionWrapperTest {

	@Test(groups = "unit")
	public void testGetUnderlyingConnection() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(connection, connectionWrapper.getUnderlyingConnection());
	}

	@Test(groups = "unit")
	public void testRollback() throws Exception {
		Connection connection = Mockito.mock(Connection.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.rollback();

		Mockito.verify(connection, VerificationModeFactory.times(1)).rollback();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testRollbackToSavepoint() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Savepoint savepoint = Mockito.mock(Savepoint.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.rollback(savepoint);

		Mockito.verify(connection, VerificationModeFactory.times(1)).rollback(savepoint);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testSetAutoCommit() throws Exception {
		Connection connection = Mockito.mock(Connection.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.setAutoCommit(true);

		Mockito.verify(connection, VerificationModeFactory.times(1)).setAutoCommit(true);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testGetAutoCommit() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Mockito.when(connection.getAutoCommit()).thenReturn(true);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertTrue(connectionWrapper.getAutoCommit());

		Mockito.verify(connection, VerificationModeFactory.times(1)).getAutoCommit();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testCommit() throws Exception {
		Connection connection = Mockito.mock(Connection.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.commit();

		Mockito.verify(connection, VerificationModeFactory.times(1)).commit();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testClose() throws Exception {
		Connection connection = Mockito.mock(Connection.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.close();

		Mockito.verify(connection, VerificationModeFactory.times(1)).close();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testIsClosed() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Mockito.when(connection.isClosed()).thenReturn(true);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertTrue(connectionWrapper.isClosed());

		Mockito.verify(connection, VerificationModeFactory.times(1)).isClosed();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testSetReadOnly() throws Exception {
		Connection connection = Mockito.mock(Connection.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.setReadOnly(true);

		Mockito.verify(connection, VerificationModeFactory.times(1)).setReadOnly(true);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testIsReadOnly() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Mockito.when(connection.isReadOnly()).thenReturn(true);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertTrue(connectionWrapper.isReadOnly());

		Mockito.verify(connection, VerificationModeFactory.times(1)).isReadOnly();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testNativeSQL() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		String in = "in";
		String out = "out";
		Mockito.when(connection.nativeSQL(in)).thenReturn(out);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertEquals(out, connectionWrapper.nativeSQL(in));

		Mockito.verify(connection, VerificationModeFactory.times(1)).nativeSQL(in);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testGetMetaData() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		DatabaseMetaData databaseMetaData = Mockito.mock(DatabaseMetaData.class);
		Mockito.when(connection.getMetaData()).thenReturn(databaseMetaData);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertEquals(databaseMetaData, connectionWrapper.getMetaData());

		Mockito.verify(connection, VerificationModeFactory.times(1)).getMetaData();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testSetCatalog() throws Exception {
		Connection connection = Mockito.mock(Connection.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.setCatalog("catalog");

		Mockito.verify(connection, VerificationModeFactory.times(1)).setCatalog("catalog");
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testGetCatalog() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Mockito.when(connection.getCatalog()).thenReturn("catalog");

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertEquals("catalog", connectionWrapper.getCatalog());

		Mockito.verify(connection, VerificationModeFactory.times(1)).getCatalog();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testSetTransactionIsolation() throws Exception {
		Connection connection = Mockito.mock(Connection.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.setTransactionIsolation(1);

		Mockito.verify(connection, VerificationModeFactory.times(1)).setTransactionIsolation(1);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testGetTransactionIsolation() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Mockito.when(connection.getTransactionIsolation()).thenReturn(1);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertEquals(1, connectionWrapper.getTransactionIsolation());

		Mockito.verify(connection, VerificationModeFactory.times(1)).getTransactionIsolation();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testSetHoldability() throws Exception {
		Connection connection = Mockito.mock(Connection.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.setHoldability(3);

		Mockito.verify(connection, VerificationModeFactory.times(1)).setHoldability(3);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testGetHoldability() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Mockito.when(connection.getHoldability()).thenReturn(3);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertEquals(3, connectionWrapper.getHoldability());

		Mockito.verify(connection, VerificationModeFactory.times(1)).getHoldability();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testSetSavepoint() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Savepoint savepoint = Mockito.mock(Savepoint.class);
		Mockito.when(connection.setSavepoint()).thenReturn(savepoint);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(savepoint, connectionWrapper.setSavepoint());

		Mockito.verify(connection, VerificationModeFactory.times(1)).setSavepoint();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testSetSavepointWithName() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Savepoint savepoint = Mockito.mock(Savepoint.class);
		Mockito.when(connection.setSavepoint("savepoint")).thenReturn(savepoint);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(savepoint, connectionWrapper.setSavepoint("savepoint"));

		Mockito.verify(connection, VerificationModeFactory.times(1)).setSavepoint("savepoint");
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testReleaseSavepoint() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Savepoint savepoint = Mockito.mock(Savepoint.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.releaseSavepoint(savepoint);

		Mockito.verify(connection, VerificationModeFactory.times(1)).releaseSavepoint(savepoint);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testGetWarnings() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		SQLWarning sqlWarning = Mockito.mock(SQLWarning.class);
		Mockito.when(connection.getWarnings()).thenReturn(sqlWarning);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(sqlWarning, connectionWrapper.getWarnings());

		Mockito.verify(connection, VerificationModeFactory.times(1)).getWarnings();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testClearWarnings() throws Exception {
		Connection connection = Mockito.mock(Connection.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.clearWarnings();

		Mockito.verify(connection, VerificationModeFactory.times(1)).clearWarnings();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testGetTypeMap() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Map<String, Class<?>> map = new HashMap<String, Class<?>>();
		Mockito.when(connection.getTypeMap()).thenReturn(map);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(map, connectionWrapper.getTypeMap());

		Mockito.verify(connection, VerificationModeFactory.times(1)).getTypeMap();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testSetTypeMap() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Map<String, Class<?>> map = new HashMap<String, Class<?>>();

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.setTypeMap(map);

		Mockito.verify(connection, VerificationModeFactory.times(1)).setTypeMap(map);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testCreateArrayOf() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		String typeName = "typeName";
		Object[] objects = new Object[0];
		Array array = Mockito.mock(Array.class);
		Mockito.when(connection.createArrayOf(typeName, objects)).thenReturn(array);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(array, connectionWrapper.createArrayOf(typeName, objects));

		Mockito.verify(connection, VerificationModeFactory.times(1)).createArrayOf(typeName, objects);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testCreateStruct() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		String typeName = "typeName";
		Object[] objects = new Object[0];
		Struct struct = Mockito.mock(Struct.class);
		Mockito.when(connection.createStruct(typeName, objects)).thenReturn(struct);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(struct, connectionWrapper.createStruct(typeName, objects));

		Mockito.verify(connection, VerificationModeFactory.times(1)).createStruct(typeName, objects);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testUnwrap() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Object object = new Object();
		Mockito.when(connection.unwrap(Object.class)).thenReturn(object);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(object, connectionWrapper.unwrap(Object.class));

		Mockito.verify(connection, VerificationModeFactory.times(1)).unwrap(Object.class);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testIsWrapperFor() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Mockito.when(connection.isWrapperFor(Object.class)).thenReturn(true);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertTrue(connectionWrapper.isWrapperFor(Object.class));

		Mockito.verify(connection, VerificationModeFactory.times(1)).isWrapperFor(Object.class);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testCreateSQLXML() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		SQLXML sqlxml = Mockito.mock(SQLXML.class);
		Mockito.when(connection.createSQLXML()).thenReturn(sqlxml);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(sqlxml, connectionWrapper.createSQLXML());

		Mockito.verify(connection, VerificationModeFactory.times(1)).createSQLXML();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testIsValid() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Mockito.when(connection.isValid(5)).thenReturn(true);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertTrue(connection.isValid(5));

		Mockito.verify(connection, VerificationModeFactory.times(1)).isValid(5);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testCreateClob() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Clob clob = Mockito.mock(Clob.class);
		Mockito.when(connection.createClob()).thenReturn(clob);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(clob, connectionWrapper.createClob());

		Mockito.verify(connection, VerificationModeFactory.times(1)).createClob();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testCreateBlob() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Blob blob = Mockito.mock(Blob.class);
		Mockito.when(connection.createBlob()).thenReturn(blob);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(blob, connectionWrapper.createBlob());

		Mockito.verify(connection, VerificationModeFactory.times(1)).createBlob();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testCreateNClob() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		NClob nClob = Mockito.mock(NClob.class);
		Mockito.when(connection.createNClob()).thenReturn(nClob);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(nClob, connectionWrapper.createNClob());

		Mockito.verify(connection, VerificationModeFactory.times(1)).createNClob();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testSetClientInfoViaProperties() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Properties properties = Mockito.mock(Properties.class);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.setClientInfo(properties);

		Mockito.verify(connection, VerificationModeFactory.times(1)).setClientInfo(properties);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testSetClientInfo() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		String name = "name";
		String value = "value";

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		connectionWrapper.setClientInfo(name, value);

		Mockito.verify(connection, VerificationModeFactory.times(1)).setClientInfo(name, value);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testGetClientInfoProperties() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Properties properties = Mockito.mock(Properties.class);
		Mockito.when(connection.getClientInfo()).thenReturn(properties);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(properties, connectionWrapper.getClientInfo());

		Mockito.verify(connection, VerificationModeFactory.times(1)).getClientInfo();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testGetClientInfo() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		String name = "name";
		String value = "value";
		Mockito.when(connection.getClientInfo(name)).thenReturn(value);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertEquals(value, connectionWrapper.getClientInfo(name));

		Mockito.verify(connection, VerificationModeFactory.times(1)).getClientInfo(name);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testCreateStatement() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(connection.createStatement()).thenReturn(statement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(statement, connectionWrapper.createStatement());

		Mockito.verify(connection, VerificationModeFactory.times(1)).createStatement();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testCreateStatement2Params() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(connection.createStatement(1, 1)).thenReturn(statement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(statement, connectionWrapper.createStatement(1, 1));

		Mockito.verify(connection, VerificationModeFactory.times(1)).createStatement(1, 1);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testCreateStatement3Params() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Statement statement = Mockito.mock(Statement.class);
		Mockito.when(connection.createStatement(1, 1, 1)).thenReturn(statement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(statement, connectionWrapper.createStatement(1, 1, 1));

		Mockito.verify(connection, VerificationModeFactory.times(1)).createStatement(1, 1, 1);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testPrepareStatement() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
		Mockito.when(connection.prepareStatement("sql")).thenReturn(preparedStatement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(preparedStatement, connectionWrapper.prepareStatement("sql"));

		Mockito.verify(connection, VerificationModeFactory.times(1)).prepareStatement("sql");
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testPrepareStatement3Params() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
		Mockito.when(connection.prepareStatement("sql", 1, 1)).thenReturn(preparedStatement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(preparedStatement, connectionWrapper.prepareStatement("sql", 1, 1));

		Mockito.verify(connection, VerificationModeFactory.times(1)).prepareStatement("sql", 1, 1);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testPrepareStatement4Params() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
		Mockito.when(connection.prepareStatement("sql", 1, 1, 1)).thenReturn(preparedStatement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(preparedStatement, connectionWrapper.prepareStatement("sql", 1, 1, 1));

		Mockito.verify(connection, VerificationModeFactory.times(1)).prepareStatement("sql", 1, 1, 1);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testPrepareStatement2Params() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
		Mockito.when(connection.prepareStatement("sql", 1)).thenReturn(preparedStatement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(preparedStatement, connectionWrapper.prepareStatement("sql", 1));

		Mockito.verify(connection, VerificationModeFactory.times(1)).prepareStatement("sql", 1);
		Mockito.verifyNoMoreInteractions(connection);

	}

	@Test(groups = "unit")
	public void testPrepareStatementWithColumnIndexes() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
		int[] columnIndexes = new int[0];
		Mockito.when(connection.prepareStatement("sql", columnIndexes)).thenReturn(preparedStatement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(preparedStatement, connectionWrapper.prepareStatement("sql", columnIndexes));

		Mockito.verify(connection, VerificationModeFactory.times(1)).prepareStatement("sql", columnIndexes);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testPrepareStatementWithColumnNames() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
		String[] columnNames = new String[0];
		Mockito.when(connection.prepareStatement("sql", columnNames)).thenReturn(preparedStatement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(preparedStatement, connectionWrapper.prepareStatement("sql", columnNames));

		Mockito.verify(connection, VerificationModeFactory.times(1)).prepareStatement("sql", columnNames);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testPrepareCall() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		CallableStatement callableStatement = Mockito.mock(CallableStatement.class);
		String sql = "sql";
		Mockito.when(connection.prepareCall(sql)).thenReturn(callableStatement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(callableStatement, connectionWrapper.prepareCall(sql));

		Mockito.verify(connection, VerificationModeFactory.times(1)).prepareCall(sql);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testPrepareCall3Params() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		CallableStatement callableStatement = Mockito.mock(CallableStatement.class);
		Mockito.when(connection.prepareCall("sql", 1, 1)).thenReturn(callableStatement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(callableStatement, connectionWrapper.prepareCall("sql", 1, 1));

		Mockito.verify(connection, VerificationModeFactory.times(1)).prepareCall("sql", 1, 1);
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test(groups = "unit")
	public void testPrepareCall4Params() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		CallableStatement callableStatement = Mockito.mock(CallableStatement.class);
		Mockito.when(connection.prepareCall("sql", 1, 1, 1)).thenReturn(callableStatement);

		ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
		Assert.assertSame(callableStatement, connectionWrapper.prepareCall("sql", 1, 1, 1));

		Mockito.verify(connection, VerificationModeFactory.times(1)).prepareCall("sql", 1, 1, 1);
		Mockito.verifyNoMoreInteractions(connection);
	}
}
