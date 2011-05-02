package me.vgv.common.database.hibernate;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
final class CurrentSessionContextHolder {

	private static ThreadLocal<Boolean> CURRENT_SESSION_CONTEXT_HOLDER = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	public static void enterCurrentSessionContext() {
		CURRENT_SESSION_CONTEXT_HOLDER.set(Boolean.TRUE);
	}

	public static void leaveCurrentSessionContext() {
		CURRENT_SESSION_CONTEXT_HOLDER.set(Boolean.FALSE);
	}

	public static boolean isInCurrentSessionContext() {
		return CURRENT_SESSION_CONTEXT_HOLDER.get() == Boolean.TRUE;
	}
}
