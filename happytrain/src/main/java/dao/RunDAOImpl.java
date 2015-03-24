package dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import util.HibernateUtil;
import entities.Route;
import entities.Run;

/**
 * Implementation of RunDAO.
 *
 */
@Repository("runDao")
public class RunDAOImpl extends GenericDAOImpl<Integer, Run> implements RunDAO {

	/**
	 * @see dao.RunDAO#findByTrainId(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Run> findByTrainId(final int trainId) {
		String hql = "SELECT r FROM Run r "
				+ "WHERE r.trainId.id = :trainId ";
		List<Run> runList =  getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("trainId", trainId)
				.list();
		if (runList.isEmpty() || runList == null){
			runList = new ArrayList<Run>();
		}
		return runList;
	}

	

}
