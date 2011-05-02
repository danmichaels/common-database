package me.vgv.common.database.transaction;

import com.google.common.base.Preconditions;

/**
 * Настройки транзакции
 * <p/>
 * Класс является immutable
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class TransactionDefinition {

	public static final TransactionDefinition DEFAULT_TRANSACTION_DEFINITION = new TransactionDefinition(false, false, TransactionPropagation.REQUIRED);

	private final boolean readOnly;
	private final boolean snapshot;
	private final TransactionPropagation transactionPropagation;

	public TransactionDefinition(boolean readOnly, boolean snapshot, TransactionPropagation transactionPropagation) {
		Preconditions.checkNotNull(transactionPropagation, "transactionPropagation is null - error");

		this.readOnly = readOnly;
		this.snapshot = snapshot;
		this.transactionPropagation = transactionPropagation;
	}

	/**
	 * @return Транзакция только для чтения
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @return Транзакция типа snapshot
	 */
	public boolean isSnapshot() {
		return snapshot;
	}

	/**
	 * @return тип взаимодействия с другими транзакциями
	 */
	public TransactionPropagation getTransactionPropagation() {
		return transactionPropagation;
	}
}
