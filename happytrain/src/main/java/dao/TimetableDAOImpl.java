package dao;

import java.util.Date;
import java.util.List;

import util.HibernateUtil;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Timetable;

/**
 * Implementation of TimetableDAO.
 *
 */
public class TimetableDAOImpl extends GenericDAOImpl<Integer, Timetable> implements TimetableDAO {

	
	/**
	 * @see dao.TimetableDAO#findTrainWithDepTimeBetweenPeriodOfTime(java.util.List, java.util.Date, java.util.Date)
	 */
	@Override
	public List<Run> findTrainWithDepTimeBetweenPeriodOfTime(List<Route> routes, Date from, Date to) {
		String hql="SELECT t.runId FROM Timetable t "
				+ "WHERE (t.depTime BETWEEN :from and :to) "
				+ "AND t.routeId IN (:routes)";
		List<Run> runList = HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("from", from)
				.setParameter("to", to)
				.setParameterList("routes", routes)
				.list();
		return runList;
	}
	
	
	/**
	 * @see dao.TimetableDAO#findDepTimeFromStation(java.lang.String, entities.Run)
	 */
	@Override
	public Date findDepTimeFromStation(String stationA, Run run) {
		String hql = "SELECT t.depTime FROM Timetable t "
					+ "WHERE t.runId=:run "
					+ "and t.routeId.stationId.name=:station "
					+ "and t.routeId.trainId=t.runId.trainId";
		Date date = (Date) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("run", run)
				.setParameter("station", stationA)
				.uniqueResult();
		return date;
	}
	
	
	/**
	 * @see dao.TimetableDAO#findArrTimeToStation(java.lang.String, entities.Run)
	 */
	@Override
	public Date findArrTimeToStation(String stationB, Run run) {
		String hql = "SELECT t.arrTime FROM Timetable t "
				   + "WHERE t.runId=:run "
				   + "and t.routeId.stationId.name=:station "
				   + "and t.routeId.trainId=t.runId.trainId";
		Date date = (Date) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("run", run)
				.setParameter("station", stationB)
				.uniqueResult();
		return date;
	}

	
	/**
	 * @see dao.TimetableDAO#findAvailableSeatsCount(java.lang.String, java.lang.String)
	 */
	@Override
	public int findAvailableSeatsCount(String stationA, String runId) {
		String hql = "SELECT t.availableSeats FROM Timetable t "
				    + "WHERE t.runId.id=:run "
			      	+ "and t.routeId.stationId.name=:station "
					+ "and t.routeId.trainId=t.runId.trainId";
		int count = (Integer) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("run", Integer.parseInt(runId))
				.setParameter("station", stationA)
				.uniqueResult();
		return count;
	}


	/**
	 * @see dao.TimetableDAO#getRunFromTimetableByStation(java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public List<Run> getRunFromTimetableByStation(String station, Date from, Date to) {
		String hql = "SELECT t.runId FROM Timetable t "
			    + "WHERE t.routeId.stationId.name=:station "
		      	+ "and (t.arrTime BETWEEN  :from and :to) ";
		List<Run> runList =  HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("station", station)
				.setParameter("from", from)
				.setParameter("to", to)
				.list();
		return runList;
	}


	/**
	 * @see dao.TimetableDAO#findTimetableByRunAndStation(entities.Station, entities.Run)
	 */
	@Override
	public Timetable findTimetableByRunAndStation(Station station, Run run) {
		String hql = "SELECT t FROM Timetable t "
				    + "WHERE t.runId=:run "
			      	+ "and t.routeId.stationId = :station "
					+ "and t.routeId.trainId=t.runId.trainId";
		Timetable timetable = (Timetable) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("run", run)
				.setParameter("station", station)
				.uniqueResult();
		return timetable;
	}


	


	

}
