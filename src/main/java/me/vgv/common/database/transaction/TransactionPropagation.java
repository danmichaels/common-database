package me.vgv.common.database.transaction;

/**
 * Различные виды Transaction Propagation. В целом - повторяют типы из
 * J2EE, но пока поддерживаются только 2 вида. Как только придумаю как мне
 * использовать остальные типы (MANDATORY, NEVER etc) - так сразу и добавлю
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public enum TransactionPropagation {

	/**
	 * Метод всегда будет выполнен в транзакции. Если на момент вызова метода
	 * транзакция существует - будет использована она, если транзакции еще нет - будет
	 * создана новая
	 */
	REQUIRED,

	/**
	 * Метод всегда выполняется в новой транзакции
	 */
	REQUIRES_NEW

}
