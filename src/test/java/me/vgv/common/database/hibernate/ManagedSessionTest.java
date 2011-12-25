package me.vgv.common.database.hibernate;

import me.vgv.common.database.CommonDatabaseException;
import org.hibernate.Session;
import org.mockito.Mockito;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class ManagedSessionTest {

	@Test(expectedExceptions = CommonDatabaseException.class, groups = "unit")
	public void testClose() throws Exception {
		ManagedSession managedSession = new ManagedSession(Mockito.mock(Session.class));
		managedSession.close();
	}

	@Test(expectedExceptions = CommonDatabaseException.class, groups = "unit")
	public void testGetTransaction() throws Exception {
		ManagedSession managedSession = new ManagedSession(Mockito.mock(Session.class));
		managedSession.getTransaction();
	}

	@Test(expectedExceptions = CommonDatabaseException.class, groups = "unit")
	public void testBeginTransaction() throws Exception {
		ManagedSession managedSession = new ManagedSession(Mockito.mock(Session.class));
		managedSession.beginTransaction();
	}
}
