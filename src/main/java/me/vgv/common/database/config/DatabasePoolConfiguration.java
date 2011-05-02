package me.vgv.common.database.config;

/**
 * Конфигурация пула подключений
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface DatabasePoolConfiguration {

	/**
	 * @return Сколько подключений к базе создавать при первом к ней обращении
	 */
	int getInitialPoolSize();

	/**
	 * @return Сколько активных подключений может существовать одновременно
	 */
	int getMaxActivePoolSize();

	/**
	 * @return Сколько неактивных подключений к базе может существовать одновременно
	 */
	int getMaxIdlePoolSize();

	/**
	 * @return Сколько неактивных подключений к базе должно быть "в запасе"
	 */
	int getMinIdlePoolSize();

	/**
	 * @return Как часто мы чистим неактивные подключения, живущие дольше установленного срока
	 */
	long getIdleEvictorRunInterval();

	/**
	 * @return Как долго живут неактивные подключения
	 */
	long getIdleConnectionLifetime();
}
