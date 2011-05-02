package me.vgv.common.database.connection;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import me.vgv.common.database.TransactionInitPolicy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * ConnectionProvider, вытаскивающий подключение из предоставленного DataSource
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DataSourceConnectionProviderImpl extends AbstractConnectionProviderImpl {

	private final DataSource dataSource;

	@Inject
	public DataSourceConnectionProviderImpl(DataSource dataSource, TransactionInitPolicy transactionInitPolicy) {
		super(transactionInitPolicy);
		Preconditions.checkNotNull(dataSource, "dataSource is null");
		this.dataSource = dataSource;
	}

	@Override
	protected Connection getBaseConnection() throws SQLException {
		return dataSource.getConnection();
	}

}
