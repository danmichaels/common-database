package me.vgv.common.database;

import me.vgv.common.database.transaction.TransactionPropagation;

import java.lang.annotation.*;

/**
 * Этой аннотацией необходимо помечать все методы, выполнение которых
 * должно происходить в контексте транзакции
 *
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transactional {

	boolean readonly() default false;

	boolean snapshot() default false;

	/**
	 * @return отношения вложенных транзакций "между собой"
	 */
	TransactionPropagation propagation() default TransactionPropagation.REQUIRED;
}
