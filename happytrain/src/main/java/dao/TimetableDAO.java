package dao;

import java.util.Date;
import java.util.List;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Timetable;

/**
 * DAO for Timetable Entity.
 *
 */
public interface TimetableDAO extends GenericDAO<Integer, Timetable> {
	/**Get all Runs of given Routes which will depart in given period of time. 
	 * @param routes Routes list
	 * @param from Beginning of period
	 * @param to End of period
	 * @return Runs list
	 */
	List<Run> findTrainWithDepTimeBetweenPeriodOfTime(List<Route> routes, Date from, Date to);
	
	/**Get departure time from given Station(by name) on given Run.
	 * @param stationA Station	name
	 * @param run Run
	 * @return Departure date
	 */
	Date findDepTimeFromStation(String stationA, Run run);
	
	/**Get arrival time to given Station(by name) on given Run.
	 * @param stationB Station name
	 * @param run Run
	 * @return Arrival date
	 */
	Date findArrTimeToStation(String stationB, Run run);
	
	/**Get available seats count on given Run(by Id) on given Station(by name).
	 * @param stationA Station name
	 * @param runId Run
	 * @return available seats count
	 */
	int findAvailableSeatsCount(String stationA, String runId);
	
	/**Get all Runs which pass given Station(by name) in given period of time. 
	 * @param station Station name
	 * @param from Beginning of period
	 * @param to End of period
	 * @return Runs list
	 */
	List<Run> getRunFromTimetableByStation(String station, Date from, Date to);
	
	/**Get Timetable Entity with given Station(by name) and given Run.
	 * @param station Station name
	 * @param run Run
	 * @return Timetable Entity
	 */
	Timetable findTimetableByRunAndStation(Station station, Run run);
}
