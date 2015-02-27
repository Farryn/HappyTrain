package dao;

import java.util.ArrayList;
import java.util.List;






import util.HibernateUtil;
import entities.Route;
import entities.Station;
import entities.Train;

public class RouteDAOImpl extends GenericDAOImpl<Integer, Route> implements RouteDAO {

	public List<Route> findRouteFromAtoB(Station A, Station B) {
		List<Route> routeList = new ArrayList<Route>();
		String hql = "SELECT r FROM Route r,Route r2 "
					+ "WHERE r.trainId=r2.trainId "
					+ "and r.stationId=:stationA "
					+ "and r2.stationId=:stationB "
					+ "and r.stationOrdinalNumber<r2.stationOrdinalNumber";
		routeList = HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("stationA", A)
				.setParameter("stationB", B)
				.list();
		return routeList;
	}

	public List<Station> findStationsByTrain(Train train) {
		List<Station> stationList = new ArrayList<Station>();
		String hql = "SELECT r.stationId FROM Route r "
					+ "WHERE r.trainId=:train "
					+ "ORDER BY r.stationOrdinalNumber";
		stationList = HibernateUtil.getCurrentSession().createQuery(hql)
			.setParameter("train", train)
			.list();
		return stationList;
	}
	
	
	
}
