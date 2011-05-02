package me.vgv.common.database;

import me.vgv.common.database.transaction.Transaction;
import org.hibernate.Session;

import java.sql.Connection;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface TransactionManager {

	public void startTransaction(Transaction transaction);

	public void commitTransaction(Transaction transaction);

	public void rollbackTransaction(Transaction transaction);

	public boolean hasCurrentTransaction();

	public Transaction getCurrentTransaction();

	public Connection getConnection(Transaction transaction);

	public void associateConnection(Transaction transaction, Connection connection);

	public Session getSession(Transaction transaction);

	public void associateSession(Transaction transaction, Session session);

}
