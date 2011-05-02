package me.vgv.common.database;

/**
 * Если запрос, который должен вернуть 1 строку вернул больше - бросаем это исключение (например,
 * запрос значения генератора)
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class MultipleRowsInSingletonSelectException extends CommonDatabaseException {

	public MultipleRowsInSingletonSelectException() {
	}
}
