package me.vgv.common.database.transaction;

import com.google.common.base.Preconditions;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Транзакция в системе.
 * <p/>
 * Это абстракция, которая позволяет управлять транзакциями внутри. Она не
 * связана напрямую ни с JDBC-транзакциями, ни с Hibernate-транзакциями, указанные
 * транзакции ассоциируются с объектами этого класса и текущим потоком во время
 * выполнения, см {@link me.vgv.common.database.TransactionManager}
 * <p/>
 * Класс является immutable
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class Transaction {

	/**
	 * Генератор последовательных идентификаторов для всех транзакций.
	 */
	private static final AtomicLong TRANSACTION_ID_GENERATOR = new AtomicLong(0);

	private final long uniqueTransactionId = TRANSACTION_ID_GENERATOR.incrementAndGet();
	private final long startTime = System.currentTimeMillis();
	private final TransactionDefinition transactionDefinition;

	public Transaction(TransactionDefinition transactionDefinition) {
		Preconditions.checkNotNull(transactionDefinition, "transactionDefinition is null - error");
		this.transactionDefinition = transactionDefinition;
	}

	/**
	 * @return уникальный идентификатор транзакции
	 */
	public long getUniqueTransactionId() {
		return uniqueTransactionId;
	}

	/**
	 * @return время старта этой транзакции
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @return тип транзакции
	 */
	public TransactionDefinition getTransactionDefinition() {
		return transactionDefinition;
	}

	public static long getCurrentTransactionIDGeneratorValue() {
		return TRANSACTION_ID_GENERATOR.get();
	}
}
