package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import servlets.ShowRouteServlet;
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
	
	private static Logger log = Logger.getLogger(RouteService.class);   
	
	public List<StationVO> getStationsByTrain(int id) throws Exception{
		List<Station> stationList = new ArrayList<Station>();
		List<StationVO> stationVOList = new ArrayList<StationVO>();
		
		RouteDAOImpl routeDao = new RouteDAOImpl();
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			log.info("Getting Stations with Train.id " + id);
			stationList = routeDao.findStationsByTrain(id);
			if (stationList.isEmpty()) {
				throw new IllegalStateException();
			}
			log.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			log.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			log.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		
		for (Station station: stationList) {
			stationVOList.add(new StationVO(station));
		}
		return stationVOList;
	}
	


	public void addRoute(Train train, String stationName, int count) throws Exception {
		StationDAOImpl stationDao = new StationDAOImpl();
		Station station = stationDao.findByName(stationName);
		if (station == null) {
			throw new NullPointerException();
		}
		Route route = new Route(train, station, count);
		RouteDAOImpl routeDao = new RouteDAOImpl();
		routeDao.persist(route);
		
	}
	
	
}
