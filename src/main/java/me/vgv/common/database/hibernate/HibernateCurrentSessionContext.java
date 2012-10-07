package me.vgv.common.database.hibernate;

import com.google.inject.Injector;
import me.vgv.common.database.TransactionManager;
import me.vgv.common.database.transaction.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.hibernate.context.CurrentSessionContext;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class HibernateCurrentSessionContext implements CurrentSessionContext {

	/**
	 * Это хитрожопый трюк
	 * Этот объект создается Hibernat'ом (не Guice!), поэтому взять connectionManager и connectionContext негде
	 * Перед созданием SessionFactory это статическое поле получает свое
	 * значение, после создания - сразу заnullяется (потому что нефиг!)
	 */
	public static Injector injector;

	private final SessionFactoryImplementor sessionFactory;
	private final TransactionManager transactionManager;

	public HibernateCurrentSessionContext(org.hibernate.engine.SessionFactoryImplementor sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.transactionManager = injector.getInstance(TransactionManager.class);
	}

	@Override
	public Session currentSession() throws HibernateException {
		Transaction transaction = transactionManager.getCurrentTransaction();
		org.hibernate.Session session = transactionManager.getSession(transaction);

		if (session == null) {
			try {
				CurrentSessionContextHolder.enterCurrentSessionContext();
				session = sessionFactory.openSession();
				session.beginTransaction();
				transactionManager.associateSession(transaction, session);
			} finally {
				CurrentSessionContextHolder.leaveCurrentSessionContext();
			}
		}

		return (Session) session;
	}
}
