package me.vgv.common.database.connection;

import me.vgv.common.database.ConnectionProvider;
import me.vgv.common.database.config.DatabaseConfiguration;
import me.vgv.common.database.transaction.TransactionDefinition;
import me.vgv.common.database.transaction.TransactionPropagation;
import me.vgv.common.database.utils.TestDatabase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class DriverManagerConnectionProviderImplTest {

	@Test(groups = {"integration"})
	public void testGetConnectionWithParams() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		DatabaseConfiguration databaseConfiguration = testDatabase.getDatabaseConfiguration();
		testDatabase.recreateTestDatabase();

		// insert data
		ConnectionProvider connectionProvider = new DriverManagerConnectionProviderImpl(databaseConfiguration, new FirebirdTransactionInitPolicy());
		Connection connection = connectionProvider.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate("insert into test(int_value, bigint_value, smallint_value, varchar_value) values(3, 3, 3, 'Three')");
		statement.close();
		connection.commit();
		connection.close();

		// read only connection
		connection = connectionProvider.getConnection(new TransactionDefinition(true, false, TransactionPropagation.REQUIRED));
		statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select int_value, bigint_value, smallint_value, varchar_value from test order by int_value");
		if (resultSet.next()) {
			// это значение вставляется в таблицу скриптами при подготовке тестовой базы
			Assert.assertEquals(0, resultSet.getInt(1));
			Assert.assertEquals(0, resultSet.getLong(2));
			Assert.assertEquals(0, resultSet.getShort(3));
			Assert.assertEquals("Zero", resultSet.getString(4));
		}
		if (resultSet.next()) {
			// это значение вставляется в таблицу скриптами при подготовке тестовой базы
			Assert.assertEquals(1, resultSet.getInt(1));
			Assert.assertEquals(1, resultSet.getLong(2));
			Assert.assertEquals(1, resultSet.getShort(3));
			Assert.assertEquals("One", resultSet.getString(4));
		}
		if (resultSet.next()) {
			// это значение вставляется в таблицу скриптами при подготовке тестовой базы
			Assert.assertEquals(2, resultSet.getInt(1));
			Assert.assertEquals(2, resultSet.getLong(2));
			Assert.assertEquals(2, resultSet.getShort(3));
			Assert.assertEquals("Two", resultSet.getString(4));
		}
		if (resultSet.next()) {
			Assert.assertEquals(3, resultSet.getInt(1));
			Assert.assertEquals(3, resultSet.getLong(2));
			Assert.assertEquals(3, resultSet.getShort(3));
			Assert.assertEquals("Three", resultSet.getString(4));
		}
		// проверим что больше ничего нет в таблице
		Assert.assertFalse(resultSet.next(), "Other records in table? Hmm...");

		resultSet.close();
		statement.close();
		connection.commit();
		connection.close();
	}

}
