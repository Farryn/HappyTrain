package dao;

import java.util.List;

import entities.Role;
import entities.Route;
import entities.Station;
import entities.Train;

public interface RouteDAO extends GenericDAO<Integer, Route> {
	List<Route> findRouteFromAtoB(String stationA, String stationB);
	List<Station> findStationsByTrain(int id);
}
