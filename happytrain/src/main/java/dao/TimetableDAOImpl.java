package dao;

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
		String hql="SELECT t.runId FROM Timetable t WHERE (t.depTime BETWEEN :from and :to) and t.routeId IN (:routes)";
		List<Run> runList=HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("from", from)
				.setParameter("to", to)
				.setParameterList("routes", routes)
				.list();
		return runList;
	}
	
	
	public Date findDepTimeFromStation(Station station, Run run) {
		String hql = "SELECT t.depTime FROM Timetable t "
					+ "WHERE t.runId=:run "
					+ "and t.routeId.stationId=:station "
					+ "and t.routeId.trainId=t.runId.trainId";
		Date date = (Date) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("run", run)
				.setParameter("station", station)
				.uniqueResult();
		return date;
	}
	
	
	public Date findArrTimeToStation(Station station, Run run) {
		String hql = "SELECT t.arrTime FROM Timetable t "
				   + "WHERE t.runId=:run "
				   + "and t.routeId.stationId=:station "
				   + "and t.routeId.trainId=t.runId.trainId";
		Date date = (Date) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("run", run)
				.setParameter("station", station)
				.uniqueResult();
		return date;
	}

	
	public int findAvailableSeatsCount(Station station, Run run) {
		String hql = "SELECT t.availableSeats FROM Timetable t "
				    + "WHERE t.runId=:run "
			      	+ "and t.routeId.stationId=:station "
					+ "and t.routeId.trainId=t.runId.trainId";
		int count = (Integer) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("run", run)
				.setParameter("station", station)
				.uniqueResult();
		return count;
	}


	public List<Run> getTimetableFromStation(Station station, Date from, Date to) {
		String hql = "SELECT t.runId FROM Timetable t "
			    + "WHERE t.routeId.stationId=:station "
		      	+ "and (t.arrTime BETWEEN  :from and :to) ";
		List<Run> list =  HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("station", station)
				.setParameter("from", from)
				.setParameter("to", to)
				.list();
		return list;
	}

}
