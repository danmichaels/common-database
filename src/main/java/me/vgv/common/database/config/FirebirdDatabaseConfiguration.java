package me.vgv.common.database.config;

import me.vgv.common.database.config.DatabaseConfiguration;
import org.firebirdsql.jdbc.FBDriver;


/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class FirebirdDatabaseConfiguration implements DatabaseConfiguration {

	private final String host;
	private final String file;
	private final String user;
	private final String password;
	private final String encoding;

	private FirebirdDatabaseConfiguration(String host, String file, String user, String password, String encoding, String remoteProcessName, Integer remoteProcessId) {
		this.host = host;
		this.file = file;
		this.user = user;
		this.password = password;
		this.encoding = encoding;

		if (remoteProcessName != null) {
			System.setProperty("org.firebirdsql.jdbc.processName", remoteProcessName);
		}
		if (remoteProcessId != null) {
			System.setProperty("org.firebirdsql.jdbc.pid", String.valueOf(remoteProcessId));
		}
	}

	@Override
	public String getDriverClassName() {
		return FBDriver.class.getName();
	}

	@Override
	public String getJdbcUrl() {
		return "jdbc:firebirdsql:" + host + ":" + file + "?lc_ctype=" + encoding;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public static class Builder {
		private final String host;
		private final String file;
		private final String user;
		private final String password;
		private final String encoding;
		private String remoteProcessName;
		private Integer remoteProcessId;

		public Builder(String host, String file, String user, String password, String encoding) {
			this.host = host;
			this.file = file;
			this.user = user;
			this.password = password;
			this.encoding = encoding;
		}

		public Builder remoteProcessName(String remoteProcessName) {
			this.remoteProcessName = remoteProcessName;
			return this;
		}

		public Builder remoteProcessId(Integer remoteProcessId) {
			this.remoteProcessId = remoteProcessId;
			return this;
		}

		public DatabaseConfiguration build() {
			return new FirebirdDatabaseConfiguration(host, file, user, password, encoding, remoteProcessName, remoteProcessId);
		}
	}
}
