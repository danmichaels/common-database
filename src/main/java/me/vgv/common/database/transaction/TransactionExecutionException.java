package me.vgv.common.database.transaction;

import me.vgv.common.database.CommonDatabaseException;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class TransactionExecutionException extends CommonDatabaseException {

	public TransactionExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransactionExecutionException(Throwable cause) {
		super(cause);
	}
}
