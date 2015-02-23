package dao;

import java.util.List;

import entities.Role;
import entities.Route;
import entities.Station;
import entities.Train;

public interface RouteDAO extends GenericDAO<Integer, Route> {
	List<Route> findRouteFromAtoB(Station A, Station B);
	List<Station> findStationsByTrain(Train train);
}
