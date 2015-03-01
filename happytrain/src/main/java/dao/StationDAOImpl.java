package dao;

import java.io.Serializable;
import java.util.List;







import util.HibernateUtil;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Train;

public class StationDAOImpl extends GenericDAOImpl<Integer, Station> implements StationDAO {

	
	public Station findByName(String str) {
		
		String hql = "SELECT s FROM Station s WHERE s.name=:name";
		Station station = (Station) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("name", str)
				.uniqueResult();
		return station;
	}

	

	

	
}
