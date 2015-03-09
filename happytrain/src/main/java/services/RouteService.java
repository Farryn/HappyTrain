package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import util.HibernateUtil;
import valueobjects.StationVO;
import dao.RouteDAO;
import dao.RouteDAOImpl;
import dao.StationDAO;
import dao.StationDAOImpl;
import entities.Route;
import entities.Station;
import entities.Train;

/**
 * @author Damir Tuktamyshev
 * Service for Route.
 */
public class RouteService {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(RouteService.class);   
	
	/**
	 * DAO for Route.
	 */
	private RouteDAO routeDao = new RouteDAOImpl();
	/**
	 * DAO for Station.
	 */
	private StationDAO stationDao = new StationDAOImpl();
	
	/**
	 * @param routeDao the routeDao to set
	 */
	public void setRouteDao(RouteDAO routeDao) {
		this.routeDao = routeDao;
	}

	/**
	 * @param stationDao the stationDao to set
	 */
	public void setStationDao(StationDAO stationDao) {
		this.stationDao = stationDao;
	}



	/**Get Station list by given train id.
	 * @param id Train id.
	 * @return StationVO list
	 * @throws IllegalStateException
	 * @throws NullPointerException
	 */
	public List<StationVO> getStationsByTrain(int id) throws IllegalStateException, NullPointerException{
		List<Station> stationList = new ArrayList<Station>();
		List<StationVO> stationVOList = new ArrayList<StationVO>();
		
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Getting Stations with Train.id " + id);
			stationList = routeDao.findStationsByTrain(id);
			if (stationList.isEmpty()) {
				throw new IllegalStateException();
			}
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (IllegalStateException | HibernateException | NullPointerException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		
		for (Station station: stationList) {
			stationVOList.add(new StationVO(station));
		}
		return stationVOList;
	}
	


	/**Method adds Route into DB.
	 * @param train Train
	 * @param stationName Station name
	 * @param count Count of seats
	 * @throws NullPointerException
	 */
	public void addRoute(Train train, String stationName, int count) throws NullPointerException {
		
		Station station = stationDao.findByName(stationName);
		if (station == null) {
			throw new NullPointerException();
		}
		Route route = new Route(train, station, count);
		routeDao.persist(route);
		
	}
	
	
}
