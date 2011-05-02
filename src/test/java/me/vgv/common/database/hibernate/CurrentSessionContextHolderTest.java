package me.vgv.common.database.hibernate;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class CurrentSessionContextHolderTest {

	@Test(groups = "unit", timeOut = 1000)
	public void testContextHolderAndSpeed() throws Exception {
		for (int i = 0; i < 50000; i++) {
			Assert.assertFalse(CurrentSessionContextHolder.isInCurrentSessionContext());
			CurrentSessionContextHolder.enterCurrentSessionContext();
			Assert.assertTrue(CurrentSessionContextHolder.isInCurrentSessionContext());
			CurrentSessionContextHolder.leaveCurrentSessionContext();
			Assert.assertFalse(CurrentSessionContextHolder.isInCurrentSessionContext());
		}
	}

}
