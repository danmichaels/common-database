package me.vgv.common.database.transaction;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class TransactionDefinitionTest {

	@Test(groups = "unit")
	public void testDefaultTransactionDefinition() throws Exception {
		Assert.assertFalse(TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION.isReadOnly());
		Assert.assertFalse(TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION.isSnapshot());
		Assert.assertEquals(TransactionPropagation.REQUIRED, TransactionDefinition.DEFAULT_TRANSACTION_DEFINITION.getTransactionPropagation());
	}

	@Test(groups = "unit")
	public void testConstructor() throws Exception {
		TransactionDefinition transactionDefinition = new TransactionDefinition(true, false, TransactionPropagation.REQUIRES_NEW);
		Assert.assertTrue(transactionDefinition.isReadOnly());
		Assert.assertFalse(transactionDefinition.isSnapshot());
		Assert.assertEquals(TransactionPropagation.REQUIRES_NEW, transactionDefinition.getTransactionPropagation());
	}

}
