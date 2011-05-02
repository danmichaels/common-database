package me.vgv.common.database;

/**
 * Если запрос, который должен вернуть 1 строку вернул 0 - бросаем это исключение (например,
 * запрос значения генератора)
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class NoRowsInSingletonSelectException extends CommonDatabaseException {

	public NoRowsInSingletonSelectException() {
	}
}
