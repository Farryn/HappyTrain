package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.HibernateUtil;
import entities.Role;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Timetable;
import entities.Train;

public class TimetableDAOImpl extends GenericDAOImpl<Integer, Timetable> implements TimetableDAO {

	
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
