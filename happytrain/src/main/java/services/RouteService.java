package services;

import java.util.ArrayList;
import java.util.List;

import util.HibernateUtil;
import valueobjects.RouteVO;
import valueobjects.StationVO;
import valueobjects.TrainVO;
import dao.RouteDAOImpl;
import dao.StationDAOImpl;
import entities.Route;
import entities.Station;
import entities.Train;

public class RouteService {
	
	public List<StationVO> getStationsByTrain(TrainVO trainVO){
		List<Station> stationList = new ArrayList<Station>();
		List<StationVO> stationVOList = new ArrayList<StationVO>();
		
		TrainService ts = new TrainService();
		Train train = ts.getTrainById(trainVO.getId());
		
		RouteDAOImpl routeDao = new RouteDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			stationList = routeDao.findStationsByTrain(train);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		
		for (Station station: stationList) {
			stationVOList.add(new StationVO(station));
		}
		return stationVOList;
	}
	
/*	public void addRoute(RouteVO routeVO){
		Route route = new Route(routeVO); 
		RouteDAOImpl routeDao = new RouteDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			routeDao.persist(route);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		
	}*/

	public void addRoute(Train train, Station station, int count) {
		
		Route route = new Route(train, station, count);
		RouteDAOImpl routeDao = new RouteDAOImpl();
		routeDao.persist(route);
		
	}
	
	
}
