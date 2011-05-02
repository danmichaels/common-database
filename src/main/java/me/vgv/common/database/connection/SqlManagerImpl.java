package me.vgv.common.database.connection;

import com.google.inject.Inject;
import me.vgv.common.database.*;
import me.vgv.common.utils.CloseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class SqlManagerImpl implements SqlManager {

	private static final Logger log = LoggerFactory.getLogger(SqlManagerImpl.class);

	private final ConnectionFactory connectionFactory;

	@Inject
	public SqlManagerImpl(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	@Transactional(readonly = true)
	public Long readLong(String sql) {
		Connection connection = connectionFactory.getCurrentConnection();

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				Long result = resultSet.getLong(1);
				if (resultSet.wasNull()) {
					result = null;
				}

				if (resultSet.next()) {
					throw new MultipleRowsInSingletonSelectException();
				}

				return result;
			} else {
				throw new NoRowsInSingletonSelectException();
			}
		} catch (SQLException e) {
			throw new ExecuteSqlException("can't execute sql", e);
		} finally {
			CloseUtils.close(preparedStatement);
		}
	}

	@Override
	@Transactional(readonly = true)
	public Integer readInt(String sql) {
		Connection connection = connectionFactory.getCurrentConnection();

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				Integer result = resultSet.getInt(1);
				if (resultSet.wasNull()) {
					result = null;
				}

				if (resultSet.next()) {
					throw new MultipleRowsInSingletonSelectException();
				}

				return result;
			} else {
				throw new NoRowsInSingletonSelectException();
			}
		} catch (SQLException e) {
			throw new ExecuteSqlException("can't execute sql", e);
		} finally {
			CloseUtils.close(preparedStatement);
		}
	}

	@Override
	@Transactional(readonly = true)
	public String readString(String sql) {
		Connection connection = connectionFactory.getCurrentConnection();

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				String result = resultSet.getString(1);
				if (resultSet.wasNull()) {
					result = null;
				}

				if (resultSet.next()) {
					throw new MultipleRowsInSingletonSelectException();
				}

				return result;
			} else {
				throw new NoRowsInSingletonSelectException();
			}
		} catch (SQLException e) {
			throw new ExecuteSqlException("can't execute sql", e);
		} finally {
			CloseUtils.close(preparedStatement);
		}

	}

	@Override
	@Transactional(readonly = true)
	public Boolean readBoolean(String sql) {
		Connection connection = connectionFactory.getCurrentConnection();

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				Integer result = resultSet.getInt(1);
				if (resultSet.wasNull()) {
					result = null;
				}

				if (resultSet.next()) {
					throw new MultipleRowsInSingletonSelectException();
				}

				if (result == null) {
					return null;
				} else if (result == 0) {
					return Boolean.FALSE;
				} else if (result == 1) {
					return Boolean.TRUE;
				} else {
					throw new CommonDatabaseException("unexpected boolean value " + result);
				}
			} else {
				throw new NoRowsInSingletonSelectException();
			}
		} catch (SQLException e) {
			throw new ExecuteSqlException("can't execute sql", e);
		} finally {
			CloseUtils.close(preparedStatement);
		}
	}
}
