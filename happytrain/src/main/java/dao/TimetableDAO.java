package dao;

import java.util.Date;
import java.util.List;

import entities.Route;
import entities.Run;
import entities.Station;
import entities.Timetable;

public interface TimetableDAO extends GenericDAO<Integer, Timetable> {
	List<Run> findTrainWithDepTimeBetweenPeriodOfTime(List<Route> routes, Date from, Date to);
	Date findDepTimeFromStation(String stationA, Run run);
	Date findArrTimeToStation(String stationB, Run run);
	int findAvailableSeatsCount(String stationA, String runId);
}
