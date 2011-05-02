package me.vgv.common.database.transaction;

import com.google.inject.Inject;
import me.vgv.common.database.DatabaseCall;
import me.vgv.common.database.CommonDatabaseException;
import me.vgv.common.database.TransactionExecutor;
import me.vgv.common.database.Transactional;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class TransactionInterceptor implements MethodInterceptor {

	private TransactionExecutor transactionExecutor;

	@Override
	public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
		Transactional transactional = methodInvocation.getMethod().getAnnotation(Transactional.class);
		if (transactional == null) {
			throw new CommonDatabaseException("method " + methodInvocation.getMethod() + " not marked with @" + Transactional.class);
		}

		// метод помечен как @Transactional
		boolean isReadOnly = transactional.readonly();
		boolean isSnapshot = transactional.snapshot();
		TransactionPropagation transactionPropagation = transactional.propagation();

		try {
			TransactionDefinition transactionDefinition = new TransactionDefinition(isReadOnly, isSnapshot, transactionPropagation);

			DatabaseCall<Object> databaseCall = new DatabaseCall<Object>() {
				@Override
				public Object execute() throws Throwable {
					return methodInvocation.proceed();
				}
			};

			return transactionExecutor.execute(transactionDefinition, databaseCall);
		} catch (TransactionExecutionException e) {
			throw e.getCause();
		}
	}

	@Inject
	public void setTransactionExecutor(TransactionExecutor transactionExecutor) {
		this.transactionExecutor = transactionExecutor;
	}
}
