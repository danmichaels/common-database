package me.vgv.common.database;

import me.vgv.common.database.transaction.TransactionDefinition;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Общий интерфейс для тонкой настройки типа используемой новой транзакции
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface TransactionInitPolicy {

	public Connection initTransaction(Connection connection, TransactionDefinition transactionDefinition) throws SQLException;

}
