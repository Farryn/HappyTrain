package dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import entities.Route;
import entities.Run;
import entities.Station;
import entities.Train;

/**
 * Implementation of RouteDAO.
 *
 */
@Repository("routeDao")
public class RouteDAOImpl extends GenericDAOImpl<Integer, Route> implements RouteDAO {

	/**
	 * @see dao.RouteDAO#findRouteFromAtoB(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Route> findRouteFromAtoB(String stationA, String stationB) {
		String hql = "SELECT r FROM Route r,Route r2 "
					+ "WHERE r.trainId=r2.trainId "
					+ "and r.stationId.name=:stationA "
					+ "and r2.stationId.name=:stationB "
					+ "and r.stationOrdinalNumber<r2.stationOrdinalNumber";
		@SuppressWarnings("unchecked")
		List<Route> routeList = getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("stationA", stationA)
				.setParameter("stationB", stationB)
				.list();
		if (routeList.isEmpty() || routeList == null){
			routeList = new ArrayList<Route>();
		}
		return routeList;
	}

	/**
	 * @see dao.RouteDAO#findStationsByTrain(int)
	 */
	@Override
	public List<Station> findStationsByTrain(int id) {
		String hql = "SELECT r.stationId FROM Route r "
					+ "WHERE r.trainId.id=:train "
					+ "ORDER BY r.stationOrdinalNumber";
		@SuppressWarnings("unchecked")
		List<Station> stationList = getSessionFactory().getCurrentSession().createQuery(hql)
			.setParameter("train", id)
			.list();
		if (stationList.isEmpty() || stationList == null){
			stationList = new ArrayList<Station>();
		}
		return stationList;
	}

	/**
	 * @see dao.RouteDAO#getOrdinalNumber(java.lang.String, entities.Train)
	 */
	@Override
	public int getOrdinalNumber(String stationA, Train train) {
		String hql = "SELECT r.stationOrdinalNumber FROM Route r "
					+ "WHERE r.stationId.name=:stationA "
					+ "AND r.trainId =:train ";
		int number = (Integer) getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("stationA", stationA)
				.setParameter("train", train)
				.uniqueResult();
		return number;
	}

	/**
	 * @see dao.RouteDAO#findStationsBetweenFromAndTo(entities.Run, int, int)
	 */
	@Override
	public List<Station> findStationsBetweenFromAndTo(Run run, int stationFromOrdinalNumber, int stationToOrdinalNumber) {
		String hql = "SELECT r.stationId FROM Route r "
					+ "WHERE r.trainId=:train "
					+ "AND (r.stationOrdinalNumber BETWEEN :stationFromOrdinalNumber AND :stationToOrdinalNumber) ";
		@SuppressWarnings("unchecked")
		List<Station> stationList = getSessionFactory().getCurrentSession().createQuery(hql)
			.setParameter("train", run.getTrainId())
			.setParameter("stationFromOrdinalNumber", stationFromOrdinalNumber)
			.setParameter("stationToOrdinalNumber", stationToOrdinalNumber)
			.list();
		if (stationList.isEmpty() || stationList == null){
			stationList = new ArrayList<Station>();
		}
		return stationList;
	}

	/**
	 * @see dao.RouteDAO#findRouteByStationStringAndTrainId(java.lang.String, int)
	 */
	@Override
	public List<Route> findRouteByStationStringAndTrainId(String station, int trainId) {
		String hql = "SELECT r FROM Route r "
					+ "WHERE r.stationId.name=:station "
					+ "AND r.trainId.id=:trainId";
		@SuppressWarnings("unchecked")
		List<Route> routeList = getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("station", station)
				.setParameter("trainId", trainId)
				.list();
		if (routeList.isEmpty() || routeList == null){
			routeList = new ArrayList<Route>();
		}
		return routeList;
	}
	
	
	
}
