package me.vgv.common.database.transaction;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class TransactionTest {

	@Test(groups = "unit")
	public void testUniqueIdentifiers() throws Exception {
		Set<Long> set = new HashSet<Long>();

		for (int i = 0; i < 100000; i++) {
			Transaction transaction = new Transaction(TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION);
			set.add(transaction.getUniqueTransactionId());
		}

		Assert.assertEquals(100000, set.size());
	}

	@Test(groups = "unit")
	public void testStartTime() throws Exception {
		for (int i = 0; i < 100; i++) {
			Transaction first = new Transaction(TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION);
			Thread.sleep(2);
			Transaction second = new Transaction(TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION);

			Assert.assertTrue(first.getStartTime() < second.getStartTime());
		}
	}

}
