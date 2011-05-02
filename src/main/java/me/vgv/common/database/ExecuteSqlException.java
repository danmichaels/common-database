package me.vgv.common.database;

/**
 * Если при выполнении sql-запроса возникла ошибка и нет возможности
 * узнать причину точнее - бросаем это исключение
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ExecuteSqlException extends CommonDatabaseException {

	public ExecuteSqlException(String message) {
		super(message);
	}

	public ExecuteSqlException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExecuteSqlException(Throwable cause) {
		super(cause);
	}
}
