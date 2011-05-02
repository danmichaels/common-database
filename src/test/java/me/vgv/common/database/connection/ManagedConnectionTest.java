package me.vgv.common.database.connection;

import me.vgv.common.database.CommonDatabaseException;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.sql.Connection;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class ManagedConnectionTest {

	@Test(expectedExceptions = CommonDatabaseException.class, groups = "unit")
	public void testClose() throws Exception {
		ManagedConnection managedConnection = new ManagedConnection(Mockito.mock(Connection.class));
		managedConnection.close();
	}

	@Test(expectedExceptions = CommonDatabaseException.class, groups = "unit")
	public void testSetAutoCommit() throws Exception {
		ManagedConnection managedConnection = new ManagedConnection(Mockito.mock(Connection.class));
		managedConnection.setAutoCommit(true);
	}

	@Test(expectedExceptions = CommonDatabaseException.class, groups = "unit")
	public void testCommit() throws Exception {
		ManagedConnection managedConnection = new ManagedConnection(Mockito.mock(Connection.class));
		managedConnection.commit();
	}

	@Test(expectedExceptions = CommonDatabaseException.class, groups = "unit")
	public void testRollback() throws Exception {
		ManagedConnection managedConnection = new ManagedConnection(Mockito.mock(Connection.class));
		managedConnection.rollback();
	}
}
