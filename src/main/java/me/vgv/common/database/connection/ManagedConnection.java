package me.vgv.common.database.connection;

import me.vgv.common.database.CommonDatabaseException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ManagedConnection extends ConnectionWrapper {

	public ManagedConnection(Connection peer) {
		super(peer);
	}

	@Override
	public void close() throws SQLException {
		throw new CommonDatabaseException("Calling close() on ManagedConnection");
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		throw new CommonDatabaseException("Calling setAutoCommit() on ManagedConnection");
	}

	@Override
	public void commit() throws SQLException {
		throw new CommonDatabaseException("Calling commit() on ManagedConnection");
	}

	@Override
	public void rollback() throws SQLException {
		throw new CommonDatabaseException("Calling rollback() on ManagedConnection");
	}
}
