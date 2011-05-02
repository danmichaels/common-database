package me.vgv.common.database;

import me.vgv.common.database.transaction.TransactionDefinition;

import java.sql.Connection;

/**
 * Управляет получением подключения к базе данных с учетом транзакционного контекста.
 * По-сути - это некий аналог Hibernate SessionFactory, но только для не для сессий, а
 * для подключений к базе.
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface ConnectionFactory {

	/**
	 * Возвращает подключение, связанное с текущей транзакцией.
	 * <p/>
	 * Если подключение еще не создано - создает его. Если метод вызван вне
	 * контекста транзакции - бросает {@link me.vgv.common.database.transaction.NoCurrentTransactionException}
	 *
	 * @return подключение к базе ассоциированное с текущей транзакцией
	 * @throws me.vgv.common.database.transaction.NoCurrentTransactionException
	 *          если метод вызван вне коннекста транзакции
	 * @throws me.vgv.common.database.transaction.TransactionExecutionException
	 *          если в процессе выполнения DatabaseCall произошла какая-либо ошибка. Базовый exception можно получить как TransactionExecutionException.getCause()
	 */
	public Connection getCurrentConnection();

	/**
	 * Получает новое соединение с параметрами транзакции по-умолчанию
	 * <p/>
	 * Метод работает в любом окружении и никак не связан с транзакционным контекстом
	 *
	 * @return подключение к базе данных
	 * @throws me.vgv.common.database.transaction.TransactionExecutionException
	 *          если в процессе получения подключения произошла какая-либо ошибка. Базовый exception можно получить как TransactionExecutionException.getCause()
	 */
	public Connection openConnection();

	/**
	 * Получает новое соединение с указанными параметрами транзакции
	 * <p/>
	 * Метод работает в любом окружении и никак не связан с транзакционным контекстом
	 *
	 * @param transactionDefinition параметры транзакции
	 * @return подключение к базе данных
	 * @throws me.vgv.common.database.transaction.TransactionExecutionException
	 *          если в процессе получения подключения произошла какая-либо ошибка. Базовый exception можно получить как TransactionExecutionException.getCause()
	 */
	public Connection openConnection(TransactionDefinition transactionDefinition);

}
