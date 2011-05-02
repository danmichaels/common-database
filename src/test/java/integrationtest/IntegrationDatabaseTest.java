package integrationtest;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import me.vgv.common.database.*;
import me.vgv.common.database.config.*;
import me.vgv.common.database.connection.*;
import me.vgv.common.database.hibernate.BaseSessionFactory;
import me.vgv.common.database.transaction.TransactionExecutorImpl;
import me.vgv.common.database.transaction.TransactionInterceptor;
import me.vgv.common.database.transaction.TransactionManagerImpl;
import me.vgv.common.database.utils.TestDatabase;
import org.hibernate.SessionFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class IntegrationDatabaseTest {

	@Test(groups = "integration", invocationCount = 3)
	public void testDatabaseWork() throws Exception {
		// пересоздадим базу
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		testDatabase.recreateTestDatabase();

		// конфигурация dataSource
		final DatabaseConfiguration databaseConfiguration = testDatabase.getDatabaseConfiguration();

		// конфигурация hibernate
		List<Class<?>> annotatedClasses = new ArrayList<Class<?>>();
		annotatedClasses.add(HiberTest.class);
		final HibernateConfiguration hibernateConfiguration = new HibernateConfigurationImpl.Builder(CommonFirebirdDialect.class.getName(), annotatedClasses).updateDatabaseMode(HibernateDatabaseUpdateMode.VALIDATE).build();

		// создадим injector
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(DatabaseConfiguration.class).toInstance(databaseConfiguration);
				bind(HibernateConfiguration.class).toInstance(hibernateConfiguration);
				bind(DatabasePoolConfiguration.class).toInstance(new DatabasePoolConfigurationImpl.Builder().initialPoolSize(1).minIdlePoolSize(1).maxIdlePoolSize(2).maxActivePoolSize(2).build());

				// database classes
				bind(DataSource.class).to(BaseDataSource.class).in(Singleton.class);
				bind(ConnectionProvider.class).to(DataSourceConnectionProviderImpl.class).in(Singleton.class);
				bind(ConnectionFactory.class).to(ConnectionFactoryImpl.class).in(Singleton.class);
				bind(TransactionInitPolicy.class).to(FirebirdTransactionInitPolicy.class).in(Singleton.class);
				bind(TransactionManager.class).to(TransactionManagerImpl.class).in(Singleton.class);
				bind(TransactionExecutor.class).to(TransactionExecutorImpl.class).in(Singleton.class);
				bind(SqlManager.class).to(SqlManagerImpl.class).in(Singleton.class);

				bind(SessionFactory.class).to(BaseSessionFactory.class).in(Singleton.class);
				bind(IntegrationDatabaseService.class).to(IntegrationDatabaseServiceImpl.class).in(Singleton.class);

				// interceptors
				TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
				requestInjection(transactionInterceptor);
				bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class), transactionInterceptor);
			}
		});

		BaseDataSource baseDataSource = (BaseDataSource) injector.getInstance(DataSource.class);
		IntegrationDatabaseService databaseService = injector.getInstance(IntegrationDatabaseService.class);

		// посмотрим что в базе сейчас
		List<HiberTest> hiberTests = databaseService.get();
		Assert.assertEquals(databaseService.getCount(), hiberTests.size());
		Assert.assertEquals(1, hiberTests.get(0).getId());
		Assert.assertEquals("Один", hiberTests.get(0).getName());
		Assert.assertEquals(2, hiberTests.get(1).getId());
		Assert.assertEquals("Два", hiberTests.get(1).getName());

		// протестируем сохранение через hibernate
		HiberTest hiberTest1 = new HiberTest();
		hiberTest1.setId(0);
		hiberTest1.setName("Ноль");
		databaseService.saveViaHibernate(hiberTest1);

		// протестируем сохранение через jdbc
		HiberTest hiberTest2 = new HiberTest();
		hiberTest2.setId(3);
		hiberTest2.setName("Три");
		databaseService.saveViaJDBC(hiberTest2);

		// посмотрим что в базе после сохранения
		hiberTests = databaseService.get();
		Assert.assertEquals(databaseService.getCount(), hiberTests.size());
		Assert.assertEquals(0, hiberTests.get(0).getId());
		Assert.assertEquals("Ноль", hiberTests.get(0).getName());
		Assert.assertEquals(1, hiberTests.get(1).getId());
		Assert.assertEquals("Один", hiberTests.get(1).getName());
		Assert.assertEquals(2, hiberTests.get(2).getId());
		Assert.assertEquals("Два", hiberTests.get(2).getName());
		Assert.assertEquals(3, hiberTests.get(3).getId());
		Assert.assertEquals("Три", hiberTests.get(3).getName());

		baseDataSource.close();
	}

}
