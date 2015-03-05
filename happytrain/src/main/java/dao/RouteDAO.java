package dao;

import java.util.List;

import entities.Route;
import entities.Run;
import entities.Station;
import entities.Train;

/**
 * DAO for Route Entity.
 *
 */
public interface RouteDAO extends GenericDAO<Integer, Route> {
	/**Get Routes which connect Station A and Station B.
	 * @param stationA first Station
	 * @param stationB second Station
	 * @return Route list
	 */
	List<Route> findRouteFromAtoB(String stationA, String stationB);
	
	/**Get all stations given train passes.
	 * @param id Train Id
	 * @return Stations list
	 */
	List<Station> findStationsByTrain(int id);
	
	/**Get ordinal number of given station in Route of given Train. 
	 * @param stationA Station
	 * @param train Train
	 * @return ordinal number
	 */
	int getOrdinalNumber(String stationA, Train train);
	
	/**Get all Stations between 2 given Stations(by their ordinal numbers) on given Run.
	 * @param run Run
	 * @param stationFromOrdinalNumber First Station ordinal number
	 * @param stationToOrdinalNumber Second Station ordinal number
	 * @return Stations list
	 */
	List<Station> findStationsBetweenFromAndTo(Run run, int stationFromOrdinalNumber, int stationToOrdinalNumber);
	
	/**Get Route Entity which has given Station name and given Train Id.
	 * @param station Station name
	 * @param trainId Train Id
	 * @return Route Entity
	 */
	Route findRouteByStationStringAndTrainId(String station, int trainId);
}
