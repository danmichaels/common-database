package integrationtest;

import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface IntegrationDatabaseService {

	public int getCount();

	public void saveViaJDBC(HiberTest hiberTest);

	public void saveViaHibernate(HiberTest hiberTest);

	public List<HiberTest> get();

}
