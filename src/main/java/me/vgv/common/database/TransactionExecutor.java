package me.vgv.common.database;

import me.vgv.common.database.transaction.TransactionDefinition;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface TransactionExecutor {

	/**
	 * Метод выполняет DatabaseCall в транзакционном окружении. Каким будет это окружение
	 * определяется аргументом transactionDefinition
	 *
	 * @param transactionDefinition параметры транзакционного контекста
	 * @param databaseCall		  собственно, отложенный вызов
	 * @return результат выполнения DatabaseCall
	 * @throws TransactionExecutionException
	 *          если в процессе выполнения DatabaseCall произошла какая-либо ошибка. Базовый exception можно получить как TransactionExecutionException.getCause()
	 */
	public <V> V execute(TransactionDefinition transactionDefinition, DatabaseCall<V> databaseCall);

}
