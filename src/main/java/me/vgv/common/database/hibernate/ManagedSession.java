package me.vgv.common.database.hibernate;

import me.vgv.common.database.CommonDatabaseException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ManagedSession extends SessionWrapper {

	private static final Logger log = LoggerFactory.getLogger(ManagedSession.class);

	public ManagedSession(Session peer) {
		super(peer);
	}

	@Override
	public Connection close() throws HibernateException {
		throw new CommonDatabaseException("Calling close() on ManagedSession");
	}

	@Override
	public Transaction getTransaction() {
		throw new CommonDatabaseException("Calling getTransaction() on ManagedSession");
	}

	@Override
	public Transaction beginTransaction() throws HibernateException {
		throw new CommonDatabaseException("Calling beginTransaction() on ManagedSession");
	}
}
