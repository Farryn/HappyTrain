package services;

import java.util.ArrayList;
import java.util.List;

import dao.RouteDAOImpl;
import dao.StationDAOImpl;
import entities.Route;
import entities.Station;
import entities.Train;

public class RouteService {
	
	public List<Station> getStationsByTrain(Train train){
		List<Station> stationList = new ArrayList<Station>();
		RouteDAOImpl routeDao = new RouteDAOImpl();
		routeDao.openCurrentSessionwithTransaction();
		stationList = routeDao.findStationsByTrain(train);
		routeDao.closeCurrentSessionwithTransaction();
		return stationList;
	}
	
	public void addRoute(Route route){
		RouteDAOImpl routeDao = new RouteDAOImpl();
		routeDao.openCurrentSessionwithTransaction();
		routeDao.persist(route);
		routeDao.closeCurrentSessionwithTransaction();
	}
	
	
}
