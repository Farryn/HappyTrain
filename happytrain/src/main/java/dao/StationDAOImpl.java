package dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import entities.Station;

/**
 * Implementation of StationDAO.
 *
 */
@Repository("stationDao")
public class StationDAOImpl extends GenericDAOImpl<Integer, Station> implements StationDAO {

	
	/**
	 * @see dao.StationDAO#findByName(java.lang.String)
	 */
	@Override
	public List<Station> findByName(final String str) {
		String hql = "SELECT s "
					+ "FROM Station s "
					+ "WHERE s.name=:name";
		@SuppressWarnings("unchecked")
		List<Station> stationList =  getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("name", str)
				.list();
		if (stationList.isEmpty() || stationList == null){
			stationList = new ArrayList<Station>();
		}
		return stationList;
	}

	

	

	
}
