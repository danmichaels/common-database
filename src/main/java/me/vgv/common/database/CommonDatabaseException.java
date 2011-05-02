package me.vgv.common.database;

/**
 * Самое общее исключение, которое может возникать при работе с базой данных.
 * Может означать ЛЮБУЮ проблему с базой.
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class CommonDatabaseException extends RuntimeException {

	public CommonDatabaseException() {
	}

	public CommonDatabaseException(String message) {
		super(message);
	}

	public CommonDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonDatabaseException(Throwable cause) {
		super(cause);
	}
}
