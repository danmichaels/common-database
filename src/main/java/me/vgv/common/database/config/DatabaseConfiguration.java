package me.vgv.common.database.config;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface DatabaseConfiguration {

	/**
	 * @return Имя класса JDBC-драйвера
	 */
	String getDriverClassName();

	/**
	 * @return Строка соединения
	 */
	String getJdbcUrl();

	/**
	 * @return Имя пользователя базы данных
	 */
	String getUser();

	/**
	 * @return Пароль
	 */
	String getPassword();

}
