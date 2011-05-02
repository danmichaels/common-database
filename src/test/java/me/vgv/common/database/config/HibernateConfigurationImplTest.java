package me.vgv.common.database.config;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class HibernateConfigurationImplTest {

	@Test(groups = "unit")
	public void testHibernateConfig() throws Exception {
		List<Class<?>> annotatedClasses = new ArrayList<Class<?>>();
		annotatedClasses.add(Object.class);
		annotatedClasses.add(Integer.class);

		HibernateConfigurationImpl.Builder builder = new HibernateConfigurationImpl.Builder("dialect", annotatedClasses);
		builder.formatSql(true).showSql(true).cacheProviderClass("cache_provider_class").updateDatabaseMode(HibernateDatabaseUpdateMode.CREATE_DROP).useQueryCache(true).useSecondLevelCache(true);

		HibernateConfiguration hibernateConfiguration = builder.build();

		Assert.assertEquals(hibernateConfiguration.getDialect(), "dialect");
		Assert.assertEquals(hibernateConfiguration.getAnnotatedClasses(), annotatedClasses);
		Assert.assertNotSame(hibernateConfiguration.getAnnotatedClasses(), annotatedClasses);
		Assert.assertTrue(hibernateConfiguration.isFormatSql());
		Assert.assertTrue(hibernateConfiguration.isShowSql());
		Assert.assertEquals(hibernateConfiguration.getCacheProviderClass(), "cache_provider_class");
		Assert.assertEquals(hibernateConfiguration.getUpdateDatabaseMode(), HibernateDatabaseUpdateMode.CREATE_DROP);
		Assert.assertTrue(hibernateConfiguration.getUseQueryCache());
		Assert.assertTrue(hibernateConfiguration.getUseSecondLevelCache());
	}

}
