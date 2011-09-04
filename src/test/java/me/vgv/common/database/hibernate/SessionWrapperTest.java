package me.vgv.common.database.hibernate;

import org.hibernate.classic.Session;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.Serializable;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class SessionWrapperTest {

	@Test(groups = "unit")
	public void testGetUnderlyingSession() throws Exception {
		Session session = Mockito.mock(Session.class);
		SessionWrapper sessionWrapper = new SessionWrapper(session);
		Assert.assertSame(session, sessionWrapper.getUnderlyingSession());
	}

	@Test
	public void testFlush() throws Exception {
		Session session = Mockito.mock(Session.class);

		SessionWrapper sessionWrapper = new SessionWrapper(session);
		sessionWrapper.flush();

		Mockito.verify(session, VerificationModeFactory.times(1)).flush();
		Mockito.verifyNoMoreInteractions(session);
	}


	@Test(groups = "unit")
	public void testSaveOrUpdateCopy_1() throws Exception {
		Session session = Mockito.mock(Session.class);
		Object returnObject = new Object();
		Mockito.when(session.saveOrUpdateCopy(Mockito.<Object>anyObject())).thenReturn(returnObject);

		Object object = new Object();
		SessionWrapper sessionWrapper = new SessionWrapper(session);
		Object returnedObject = sessionWrapper.saveOrUpdateCopy(object);

		Assert.assertSame(returnObject, returnedObject);
		Mockito.verify(session, VerificationModeFactory.times(1)).saveOrUpdateCopy(object);
		Mockito.verifyNoMoreInteractions(session);
	}

	@Test(groups = "unit")
	public void testSaveOrUpdateCopy_2() throws Exception {
		Session session = Mockito.mock(Session.class);
		Object returnObject = new Object();
		Mockito.when(session.saveOrUpdateCopy(Mockito.<Object>anyObject(), Mockito.<Serializable>any())).thenReturn(returnObject);

		Object object = new Object();
		Integer id = 870;
		SessionWrapper sessionWrapper = new SessionWrapper(session);
		Object returnedObject = sessionWrapper.saveOrUpdateCopy(object, id);

		Assert.assertSame(returnObject, returnedObject);
		Mockito.verify(session, VerificationModeFactory.times(1)).saveOrUpdateCopy(object, id);
		Mockito.verifyNoMoreInteractions(session);
	}

	@Test(groups = "unit")
	public void testSaveOrUpdateCopy_3() throws Exception {
		Session session = Mockito.mock(Session.class);
		Object returnObject = new Object();
		Mockito.when(session.saveOrUpdateCopy(Mockito.anyString(), Mockito.<Object>anyObject())).thenReturn(returnObject);

		String entityName = "entity";
		Object object = new Object();
		SessionWrapper sessionWrapper = new SessionWrapper(session);
		Object returnedObject = sessionWrapper.saveOrUpdateCopy(entityName, object);

		Assert.assertSame(returnObject, returnedObject);
		Mockito.verify(session, VerificationModeFactory.times(1)).saveOrUpdateCopy(entityName, object);
		Mockito.verifyNoMoreInteractions(session);
	}

	@Test(groups = "unit")
	public void testSaveOrUpdateCopy_4() throws Exception {
		Session session = Mockito.mock(Session.class);
		Object returnObject = new Object();
		Mockito.when(session.saveOrUpdateCopy(Mockito.anyString(), Mockito.<Object>anyObject(), Mockito.<Serializable>any())).thenReturn(returnObject);

		String entityName = "entity";
		Object object = new Object();
		Integer id = 78956;

		SessionWrapper sessionWrapper = new SessionWrapper(session);
		Object returnedObject = sessionWrapper.saveOrUpdateCopy(entityName, object, id);

		Assert.assertSame(returnObject, returnedObject);
		Mockito.verify(session, VerificationModeFactory.times(1)).saveOrUpdateCopy(entityName, object, id);
		Mockito.verifyNoMoreInteractions(session);
	}

	/*
	@Test(groups = "unit")
	public void testFind() throws Exception {
	}

	@Test(groups = "unit")
	public void testFind() throws Exception {
	}

	@Test(groups = "unit")
	public void testFind() throws Exception {
	}

	@Test(groups = "unit")
	public void testIterate() throws Exception {
	}

	@Test
	public void testIterate() throws Exception {
	}

	@Test
	public void testIterate() throws Exception {
	}

	@Test
	public void testFilter() throws Exception {
	}

	@Test
	public void testFilter() throws Exception {
	}

	@Test
	public void testFilter() throws Exception {
	}

	@Test
	public void testDelete() throws Exception {
	}

	@Test
	public void testDelete() throws Exception {
	}

	@Test
	public void testDelete() throws Exception {
	}

	@Test
	public void testCreateSQLQuery() throws Exception {
	}

	@Test
	public void testCreateSQLQuery() throws Exception {
	}

	@Test
	public void testSave() throws Exception {
	}

	@Test
	public void testSave() throws Exception {
	}

	@Test
	public void testUpdate() throws Exception {
	}

	@Test
	public void testUpdate() throws Exception {
	}

	@Test
	public void testGetEntityMode() throws Exception {
	}

	@Test
	public void testGetSession() throws Exception {
	}



	@Test
	public void testSetFlushMode() throws Exception {
	}

	@Test
	public void testGetFlushMode() throws Exception {
	}

	@Test
	public void testSetCacheMode() throws Exception {
	}

	@Test
	public void testGetCacheMode() throws Exception {
	}

	@Test
	public void testGetSessionFactory() throws Exception {
	}

	@Test
	public void testConnection() throws Exception {
	}

	@Test
	public void testClose() throws Exception {
	}

	@Test
	public void testCancelQuery() throws Exception {
	}

	@Test
	public void testIsOpen() throws Exception {
	}

	@Test
	public void testIsConnected() throws Exception {
	}

	@Test
	public void testIsDirty() throws Exception {
	}

	@Test
	public void testIsDefaultReadOnly() throws Exception {
	}

	@Test
	public void testSetDefaultReadOnly() throws Exception {
	}

	@Test
	public void testGetIdentifier() throws Exception {
	}

	@Test
	public void testContains() throws Exception {
	}

	@Test
	public void testEvict() throws Exception {
	}

	@Test
	public void testLoad() throws Exception {
	}

	@Test
	public void testLoad() throws Exception {
	}

	@Test
	public void testLoad() throws Exception {
	}

	@Test
	public void testLoad() throws Exception {
	}

	@Test
	public void testLoad() throws Exception {
	}

	@Test
	public void testLoad() throws Exception {
	}

	@Test
	public void testLoad() throws Exception {
	}

	@Test
	public void testReplicate() throws Exception {
	}

	@Test
	public void testReplicate() throws Exception {
	}

	@Test
	public void testSave() throws Exception {
	}

	@Test
	public void testSave() throws Exception {
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
	}

	@Test
	public void testUpdate() throws Exception {
	}

	@Test
	public void testUpdate() throws Exception {
	}

	@Test
	public void testMerge() throws Exception {
	}

	@Test
	public void testMerge() throws Exception {
	}

	@Test
	public void testPersist() throws Exception {
	}

	@Test
	public void testPersist() throws Exception {
	}

	@Test
	public void testDelete() throws Exception {
	}

	@Test
	public void testDelete() throws Exception {
	}

	@Test
	public void testLock() throws Exception {
	}

	@Test
	public void testLock() throws Exception {
	}

	@Test
	public void testBuildLockRequest() throws Exception {
	}

	@Test
	public void testRefresh() throws Exception {
	}

	@Test
	public void testRefresh() throws Exception {
	}

	@Test
	public void testRefresh() throws Exception {
	}

	@Test
	public void testGetCurrentLockMode() throws Exception {
	}

	@Test
	public void testBeginTransaction() throws Exception {
	}

	@Test
	public void testGetTransaction() throws Exception {
	}

	@Test
	public void testCreateCriteria() throws Exception {
	}

	@Test
	public void testCreateCriteria() throws Exception {
	}

	@Test
	public void testCreateCriteria() throws Exception {
	}

	@Test
	public void testCreateCriteria() throws Exception {
	}

	@Test
	public void testCreateQuery() throws Exception {
	}

	@Test
	public void testCreateSQLQuery() throws Exception {
	}

	@Test
	public void testCreateFilter() throws Exception {
	}

	@Test
	public void testGetNamedQuery() throws Exception {
	}

	@Test
	public void testClear() throws Exception {
	}

	@Test
	public void testGet() throws Exception {
	}

	@Test
	public void testGet() throws Exception {
	}

	@Test
	public void testGet() throws Exception {
	}

	@Test
	public void testGet() throws Exception {
	}

	@Test
	public void testGet() throws Exception {
	}

	@Test
	public void testGet() throws Exception {
	}

	@Test
	public void testGetEntityName() throws Exception {
	}

	@Test
	public void testEnableFilter() throws Exception {
	}

	@Test
	public void testGetEnabledFilter() throws Exception {
	}

	@Test
	public void testDisableFilter() throws Exception {
	}

	@Test
	public void testGetStatistics() throws Exception {
	}

	@Test
	public void testIsReadOnly() throws Exception {
	}

	@Test
	public void testSetReadOnly() throws Exception {
	}

	@Test
	public void testDoWork() throws Exception {
	}

	@Test
	public void testDisconnect() throws Exception {
	}

	@Test
	public void testReconnect() throws Exception {
	}

	@Test
	public void testReconnect() throws Exception {
	}

	@Test
	public void testIsFetchProfileEnabled() throws Exception {
	}

	@Test
	public void testEnableFetchProfile() throws Exception {
	}

	@Test
	public void testDisableFetchProfile() throws Exception {
	}

	@Test
	public void testGetTypeHelper() throws Exception {
	}

	@Test
	public void testGetLobHelper() throws Exception {
	}
	*/
}
