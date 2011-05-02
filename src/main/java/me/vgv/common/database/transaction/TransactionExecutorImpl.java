package me.vgv.common.database.transaction;

import com.google.inject.Inject;
import me.vgv.common.database.DatabaseCall;
import me.vgv.common.database.TransactionExecutor;
import me.vgv.common.database.TransactionManager;


/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class TransactionExecutorImpl implements TransactionExecutor {

	private final TransactionManager transactionManager;

	@Inject
	public TransactionExecutorImpl(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Override
	public <V> V execute(TransactionDefinition transactionDefinition, DatabaseCall<V> databaseCall) {
	    V result = null;
		Throwable error = null;
		boolean success = false;

		// если нужна транзакция - стартанем ее
		Transaction transaction = null;
		if (!transactionManager.hasCurrentTransaction() || transactionDefinition.getTransactionPropagation() == TransactionPropagation.REQUIRES_NEW) {
			// метод должен выполняться в новой транзакции
			transaction = new Transaction(transactionDefinition);
			transactionManager.startTransaction(transaction);
		}

		// выполним вызов
		try {			
			try {
				result = databaseCall.execute();
				success = true;
			} catch (Throwable th) {
				error = th;
				success = false;
			}
		} finally {
			if (transaction != null) {
				if (success) {
					transactionManager.commitTransaction(transaction);
				} else {
					transactionManager.rollbackTransaction(transaction);
				}
			}
		}

		// вернем результат
		if (success) {
			return result;
		} else {
			throw new TransactionExecutionException(error);
		}
	}
}
