package dao;

import java.util.ArrayList;
import java.util.List;












import util.HibernateUtil;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Train;

public class RouteDAOImpl extends GenericDAOImpl<Integer, Route> implements RouteDAO {

	public List<Route> findRouteFromAtoB(String stationA, String stationB) {
		String hql = "SELECT r FROM Route r,Route r2 "
					+ "WHERE r.trainId=r2.trainId "
					+ "and r.stationId.name=:stationA "
					+ "and r2.stationId.name=:stationB "
					+ "and r.stationOrdinalNumber<r2.stationOrdinalNumber";
		List<Route> routeList = HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("stationA", stationA)
				.setParameter("stationB", stationB)
				.list();
		return routeList;
	}

	public List<Station> findStationsByTrain(int id) {
		String hql = "SELECT r.stationId FROM Route r "
					+ "WHERE r.trainId.id=:train "
					+ "ORDER BY r.stationOrdinalNumber";
		List<Station> stationList = HibernateUtil.getCurrentSession().createQuery(hql)
			.setParameter("train", id)
			.list();
		return stationList;
	}

	public int getOrdinalNumber(String stationA, Train train) {
		String hql = "SELECT r.stationOrdinalNumber FROM Route r "
					+ "WHERE r.stationId.name=:stationA "
					+ "AND r.trainId =:train ";
		int number = (Integer) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("stationA", stationA)
				.setParameter("train", train)
				.uniqueResult();
		return number;
	}

	public List<Station> findStationsBetweenFromAndTo(Run run, int stationFromOrdinalNumber, int stationToOrdinalNumber) {
		String hql = "SELECT r.stationId FROM Route r "
					+ "WHERE r.trainId=:train "
					+ "AND (r.stationOrdinalNumber BETWEEN :stationFromOrdinalNumber AND :stationToOrdinalNumber) ";
		List<Station> stationList = HibernateUtil.getCurrentSession().createQuery(hql)
			.setParameter("train", run.getTrainId())
			.setParameter("stationFromOrdinalNumber", stationFromOrdinalNumber)
			.setParameter("stationToOrdinalNumber", stationToOrdinalNumber)
			.list();
		return stationList;
	}

	public Route findRouteByStationStringAndTrainId(String station, int trainId) {
		String hql = "SELECT r FROM Route r "
					+ "WHERE r.stationId.name=:station "
					+ "AND r.trainId.id=:trainId";
		Route route = (Route) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("station", station)
				.setParameter("trainId", trainId)
				.uniqueResult();
		return route;
	}
	
	
	
}
