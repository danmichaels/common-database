package integrationtest;

import com.google.inject.Inject;
import me.vgv.common.database.ConnectionFactory;
import me.vgv.common.database.SqlManager;
import me.vgv.common.database.Transactional;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.testng.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class IntegrationDatabaseServiceImpl implements IntegrationDatabaseService {

	private final SqlManager sqlManager;
	private final ConnectionFactory connectionFactory;
	private final SessionFactory sessionFactory;

	@Inject
	public IntegrationDatabaseServiceImpl(SqlManager sqlManager, ConnectionFactory connectionFactory, SessionFactory sessionFactory) {
		this.sqlManager = sqlManager;
		this.connectionFactory = connectionFactory;
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional(readonly = true)
	public int getCount() {
		return sqlManager.readInt("select count(*) from hiber_test");
	}

	@Override
	@Transactional
	public void saveViaJDBC(HiberTest hiberTest) {
		Connection connection = connectionFactory.getCurrentConnection();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into hiber_test(id,name) values(?,?)");
			preparedStatement.setInt(1, hiberTest.getId());
			preparedStatement.setString(2, hiberTest.getName());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			Assert.fail("WTF?", e);
		}
	}

	@Override
	@Transactional
	public void saveViaHibernate(HiberTest hiberTest) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(hiberTest);
	}

	@Override
	@Transactional(readonly = true)
	public List<HiberTest> get() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HiberTest.class);
		criteria.addOrder(Order.asc("id"));
		@SuppressWarnings({"unchecked"})
		List<HiberTest> result = criteria.list();
		return result;
	}
}
