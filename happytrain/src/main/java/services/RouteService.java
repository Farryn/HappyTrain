package services;

import java.util.List;

import dao.RouteDAOImpl;
import entities.Route;
import entities.Station;
import entities.Train;

public class RouteService {
	
	public List<Station> getStationsByTrain(Train train){
		RouteDAOImpl routeDao = new RouteDAOImpl();
		routeDao.openCurrentSessionwithTransaction();
		List<Station> stationList = routeDao.findStationsByTrain(train);
		routeDao.closeCurrentSessionwithTransaction();
		return stationList;
	}
}
