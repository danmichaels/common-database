package me.vgv.common.database;

import me.vgv.common.database.transaction.TransactionDefinition;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Управляет получением реального подключения к базе данных. Откуда конкретно этот
 * класс достает подключение - не важно. Это может быть DriverManager, DataSource, JNDI и т.д.
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface ConnectionProvider {

	/**
	 * Возвращает подключение к базе данных с установленными стандартными
	 * параметрами транзакции
	 *
	 * @return подключение
	 * @throws java.sql.SQLException если получить подключение не удалось
	 */
	public Connection getConnection() throws SQLException;

	/**
	 * Возвращает активное подключение к базе данных с установленными параметрами транзакции
	 *
	 * @param transactionDefinition транзакция
	 * @return подключение
	 * @throws java.sql.SQLException если получить подключение не удалось
	 */
	public Connection getConnection(TransactionDefinition transactionDefinition) throws SQLException;

}
