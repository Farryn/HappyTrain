package dao;

import util.HibernateUtil;
import entities.Station;

/**
 * Implementation of RunDAO.
 *
 */
public class StationDAOImpl extends GenericDAOImpl<Integer, Station> implements StationDAO {

	
	/**
	 * @see dao.StationDAO#findByName(java.lang.String)
	 */
	@Override
	public Station findByName(final String str) {
		String hql = "SELECT s FROM Station s WHERE s.name=:name";
		Station station = (Station) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("name", str)
				.uniqueResult();
		return station;
	}

	

	

	
}
