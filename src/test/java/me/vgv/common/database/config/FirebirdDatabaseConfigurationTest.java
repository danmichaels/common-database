package me.vgv.common.database.config;

import org.firebirdsql.jdbc.FBDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class FirebirdDatabaseConfigurationTest {

	@Test(groups = "unit")
	public void testFirebirdConfiguration() throws Exception {
		FirebirdDatabaseConfiguration.Builder builder = new FirebirdDatabaseConfiguration.Builder("host", "file", "user", "password", "encoding");
		builder.remoteProcessName("remote_process_name").remoteProcessId(123);

		DatabaseConfiguration databaseConfiguration = builder.build();

		Assert.assertEquals(databaseConfiguration.getUser(), "user");
		Assert.assertEquals(databaseConfiguration.getPassword(), "password");
		Assert.assertEquals(databaseConfiguration.getDriverClassName(), FBDriver.class.getName());
		Assert.assertEquals(databaseConfiguration.getJdbcUrl(), "jdbc:firebirdsql:host:file?lc_ctype=encoding");
		Assert.assertEquals(System.getProperty("org.firebirdsql.jdbc.processName"), "remote_process_name");
		Assert.assertEquals(System.getProperty("org.firebirdsql.jdbc.pid"), "123");
	}

}
