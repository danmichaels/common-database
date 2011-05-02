package me.vgv.common.database.config;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class DatabasePoolConfigurationImplTest {

	@Test(groups = "unit")
	public void testDatabasePoolConfiguration() throws Exception {
		DatabasePoolConfigurationImpl.Builder builder = new DatabasePoolConfigurationImpl.Builder();
		builder.initialPoolSize(1);
		builder.maxActivePoolSize(2);
		builder.maxIdlePoolSize(3);
		builder.minIdlePoolSize(4);
		builder.idleEvictorRunInterval(5);
		builder.idleConnectionLifetime(6);

		DatabasePoolConfiguration databasePoolConfiguration = builder.build();

		Assert.assertEquals(1, databasePoolConfiguration.getInitialPoolSize());
		Assert.assertEquals(2, databasePoolConfiguration.getMaxActivePoolSize());
		Assert.assertEquals(3, databasePoolConfiguration.getMaxIdlePoolSize());
		Assert.assertEquals(4, databasePoolConfiguration.getMinIdlePoolSize());
		Assert.assertEquals(5, databasePoolConfiguration.getIdleEvictorRunInterval());
		Assert.assertEquals(6, databasePoolConfiguration.getIdleConnectionLifetime());
	}


}
