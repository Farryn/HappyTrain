package dao;

import java.util.ArrayList;
import java.util.List;

import util.HibernateUtil;
import entities.Run;

public class RunDAOImpl extends GenericDAOImpl<Integer, Run> implements RunDAO {

	@SuppressWarnings("unchecked")
	public List<Run> findByTrainId(int trainId) {
		String hql = "SELECT r FROM Run r "
				+ "WHERE r.trainId.id = :trainId ";
		List<Run> runList = (List<Run>) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("trainId", trainId)
				.list();
		return runList;
	}

	

}
