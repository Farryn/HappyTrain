package dao;

import java.util.Date;
import java.util.List;

import entities.Route;
import entities.Run;
import entities.Station;
import entities.Timetable;

public interface TimetableDAO extends GenericDAO<Integer, Timetable> {
	List<Run> findTrainWithDepTimeBetweenPeriodOfTime(List<Route> routes, Date from, Date to);
	Date findDepTimeFromStation(Station station, Run run);
	Date findArrTimeToStation(Station station, Run run);
	int findAvailableSeatsCount(Station station, Run run);
}
