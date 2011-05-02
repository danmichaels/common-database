package me.vgv.common.database.connection;

import me.vgv.common.database.ConnectionProvider;
import me.vgv.common.database.config.DatabaseConfiguration;
import me.vgv.common.database.config.DatabasePoolConfiguration;
import me.vgv.common.database.config.DatabasePoolConfigurationImpl;
import me.vgv.common.database.transaction.TransactionDefinition;
import me.vgv.common.database.transaction.TransactionPropagation;
import me.vgv.common.database.utils.TestDatabase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class BaseDataSourceTest {

	/**
	 * Это ОЧЕНЬ ВАЖНЫЙ ТЕСТ. Он обязательно должен выполняться без переделок и прочего.
	 * <p/>
	 * Суть - мы можем получать новое подключение к базе как минимум двумя способами:
	 * 1) Через ConnectionProvider
	 * 2) Напрямую через BaseDataSource
	 * <p/>
	 * Если подключение получаем через ConnectionProvider то провайдер настраивает подключение
	 * для выбранного типа транзакции. Если подключение получаем через BaseDataSource то настройки
	 * подключения не должно происходить, необходимо вернуть дефолтное подключение. Теперь представим
	 * следующую ситуацию:
	 * 1) ConnectionProvider получает подключение из DataSource с настроенным connection pool'ом
	 * 2) Этот же DataSource доступен отдельно, т.е. можно получать подключения и с его помощью
	 * 3) Получаем _READONLY_ подключение через ConnectionProvider, работаем с ним
	 * 4) Закрываем это подключение, оно возвращается в connection pool (!)
	 * 5) Получаем в этом же потоке подключение через DataSource
	 * 6) Это подключение осталось настроено как READONLY (!) потому что когда пул вернул его в
	 * список "доступных" подключений настройки типа транзакции не изменились и остались прежними.
	 * <p/>
	 * Таким образом возникает херовая зависимость настроек нового подключения в зависимости
	 * от того, какие настройки были сделаны раньше.
	 * <p/>
	 * Решение существует - BaseDataSource прежде чем вернуть новое подключение клиенту
	 * всегда сбрасывает его транзакционные настройки
	 */
	@Test(groups = "integration")
	public void testNewConnectionIsInDefaultState() throws Exception {
		TestDatabase testDatabase = new TestDatabase("testdatabasesql");
		DatabaseConfiguration databaseConfiguration = testDatabase.getDatabaseConfiguration();
		DatabasePoolConfiguration databasePoolConfiguration = new DatabasePoolConfigurationImpl.Builder().initialPoolSize(1).minIdlePoolSize(1).maxIdlePoolSize(1).maxActivePoolSize(1).build();
		testDatabase.recreateTestDatabase();

		FirebirdTransactionInitPolicy transactionInitPolicy = new FirebirdTransactionInitPolicy();
		BaseDataSource baseDataSource = new BaseDataSource(databaseConfiguration, databasePoolConfiguration, transactionInitPolicy);
		ConnectionProvider connectionProvider = new DataSourceConnectionProviderImpl(baseDataSource, transactionInitPolicy);

		// получим readonly (!) подключение через ConnectionProvider
		TransactionDefinition transactionDefinition = new TransactionDefinition(true, false, TransactionPropagation.REQUIRED);
		Assert.assertTrue(transactionDefinition.isReadOnly()); // проверим что readonly явно
		Connection connection = connectionProvider.getConnection(transactionDefinition);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from test");
		resultSet.next();
		resultSet.close();
		statement.close();
		connection.commit();
		connection.close();

		// теперь получим обычное подключение через BaseDataSource и попробуем сделать в нем update-запрос
		// это докажет, что подключение не является readonly
		connection = baseDataSource.getConnection();
		statement = connection.createStatement();
		statement.executeUpdate("insert into test(int_value,bigint_value,smallint_value,varchar_value) values(100, 100, 100,'Hundred')");
		statement.close();
		connection.commit();
		connection.close();
	}
}