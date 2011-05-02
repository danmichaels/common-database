package me.vgv.common.database.connection;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.vgv.common.database.*;
import me.vgv.common.database.utils.TestDatabase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class SqlManagerImplTest {

	@Test(groups = "integration")
	public void testReadLong() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		Assert.assertEquals(2L, sqlManager.readLong("select bigint_value from test where int_value = 2").longValue());
		Assert.assertNull(sqlManager.readLong("select null from test where int_value = 2"));
	}

	@Test(groups = "integration", expectedExceptions = NoRowsInSingletonSelectException.class)
	public void testReadLongNoRowsInSingletonException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readLong("select bigint_value from test where int_value = 223");
	}

	@Test(groups = "integration", expectedExceptions = MultipleRowsInSingletonSelectException.class)
	public void testReadLongMultipleRowsInSingletonException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readLong("select bigint_value from test");
	}

	@Test(groups = "integration", expectedExceptions = ExecuteSqlException.class)
	public void testReadLongExecuteSqlException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readLong("invalid sql statement");
	}

	@Test(groups = "integration")
	public void testReadInt() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		Assert.assertEquals(2, sqlManager.readInt("select int_value from test where int_value = 2").intValue());
		Assert.assertNull(sqlManager.readInt("select null from test where int_value = 2"));
	}

	@Test(groups = "integration", expectedExceptions = NoRowsInSingletonSelectException.class)
	public void testReadIntNoRowsInSingletonException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readInt("select int_value from test where int_value = 223");
	}

	@Test(groups = "integration", expectedExceptions = MultipleRowsInSingletonSelectException.class)
	public void testReadIntMultipleRowsInSingletonException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readInt("select int_value from test");
	}

	@Test(groups = "integration", expectedExceptions = ExecuteSqlException.class)
	public void testReadIntExecuteSqlException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readInt("invalid sql statement");
	}

	@Test(groups = "integration")
	public void testReadString() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		Assert.assertEquals("Two", sqlManager.readString("select varchar_value from test where int_value = 2"));
		Assert.assertNull(sqlManager.readString("select null from test where int_value = 2"));
	}

	@Test(groups = "integration", expectedExceptions = NoRowsInSingletonSelectException.class)
	public void testReadStringNoRowsInSingletonException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readString("select varchar_value from test where int_value = 223");
	}

	@Test(groups = "integration", expectedExceptions = MultipleRowsInSingletonSelectException.class)
	public void testReadStringMultipleRowsInSingletonException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readString("select varchar_value from test");
	}

	@Test(groups = "integration", expectedExceptions = ExecuteSqlException.class)
	public void testReadStringExecuteSqlException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readString("invalid sql statement");
	}

	@Test(groups = "integration")
	public void testReadBoolean() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		Assert.assertFalse(sqlManager.readBoolean("select int_value from test where int_value = 0"));
		Assert.assertTrue(sqlManager.readBoolean("select int_value from test where int_value = 1"));
		Assert.assertNull(sqlManager.readBoolean("select null from test where int_value = 2"));
	}

	@Test(groups = "integration", expectedExceptions = NoRowsInSingletonSelectException.class)
	public void testReadBooleanNoRowsInSingletonException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readBoolean("select int_value from test where int_value = 223");
	}

	@Test(groups = "integration", expectedExceptions = MultipleRowsInSingletonSelectException.class)
	public void testReadBooleanMultipleRowsInSingletonException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readBoolean("select int_value from test");
	}

	@Test(groups = "integration", expectedExceptions = CommonDatabaseException.class)
	public void testReadBooleanInvalidBooleanValueException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readBoolean("select bigint_value from test where int_value = 2");
	}

	@Test(groups = "integration", expectedExceptions = ExecuteSqlException.class)
	public void testReadBooleanExecuteSqlException() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		Injector injector = Guice.createInjector(testDatabase.getTestDatabaseModule());
		SqlManager sqlManager = injector.getInstance(SqlManager.class);

		sqlManager.readBoolean("invalid sql statement");
	}
}
