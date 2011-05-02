package me.vgv.common.database;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface DatabaseCall<V> {

	V execute() throws Throwable;

}
