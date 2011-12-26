package me.vgv.common.database.hibernate;

import org.hibernate.Session;
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
}
