package me.vgv.common.database.transaction;

import me.vgv.common.database.CommonDatabaseException;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class NoCurrentTransactionException extends CommonDatabaseException {

	public NoCurrentTransactionException(String message) {
		super(message);
	}
}
