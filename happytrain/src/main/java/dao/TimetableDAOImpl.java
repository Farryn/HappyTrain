package dao;

import java.util.Date;
import java.util.List;

import entities.Role;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Timetable;

public class TimetableDAOImpl extends GenericDAOImpl<Integer, Timetable> implements TimetableDAO {

	@Override
	public List<Run> findTrainWithDepTimeBetweenPeriodOfTime(List<Route> routes, Date from, Date to) {
		String hql="SELECT t.runId FROM Timetable t WHERE (t.depTime BETWEEN :from and :to) and t.routeId IN (:routes)";
		List<Run> runList=getCurrentSession().createQuery(hql)
				.setParameter("from", from)
				.setParameter("to", to)
				.setParameterList("routes", routes)
				.list();
		return runList;
	}
	
	@Override
	public Date findDepTimeFromStation(Station station, Run run) {
		String hql = "SELECT t.depTime FROM Timetable t "
					+ "WHERE t.runId=:run "
					+ "and t.routeId.stationId=:station "
					+ "and t.routeId.trainId=t.runId.trainId";
		Date date = (Date) getCurrentSession().createQuery(hql)
				.setParameter("run", run)
				.setParameter("station", station)
				.uniqueResult();
		return date;
	}
	
	@Override
	public Date findArrTimeToStation(Station station, Run run) {
		String hql = "SELECT t.arrTime FROM Timetable t "
				   + "WHERE t.runId=:run "
				   + "and t.routeId.stationId=:station "
				   + "and t.routeId.trainId=t.runId.trainId";
		Date date = (Date) getCurrentSession().createQuery(hql)
				.setParameter("run", run)
				.setParameter("station", station)
				.uniqueResult();
		return date;
	}

	@Override
	public int findAvailableSeatsCount(Station station, Run run) {
		String hql = "SELECT t.availableSeats FROM Timetable t "
				    + "WHERE t.runId=:run "
			      	+ "and t.routeId.stationId=:station "
					+ "and t.routeId.trainId=t.runId.trainId";
		int count = (int) getCurrentSession().createQuery(hql)
				.setParameter("run", run)
				.setParameter("station", station)
				.uniqueResult();
		return count;
	}

}
