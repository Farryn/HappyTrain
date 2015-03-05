package dao;

import java.util.List;
import util.HibernateUtil;
import entities.Run;

/**
 * Implementation of RunDAO.
 *
 */
public class RunDAOImpl extends GenericDAOImpl<Integer, Run> implements RunDAO {

	/**
	 * @see dao.RunDAO#findByTrainId(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Run> findByTrainId(final int trainId) {
		String hql = "SELECT r FROM Run r "
				+ "WHERE r.trainId.id = :trainId ";
		List<Run> runList = (List<Run>) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("trainId", trainId)
				.list();
		return runList;
	}

	

}
