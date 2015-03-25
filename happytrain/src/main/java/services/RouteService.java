package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.EmptyResultException;
import valueobjects.StationVO;
import dao.RouteDAO;
import dao.StationDAO;
import entities.Route;
import entities.Station;
import entities.Train;

/**
 * @author Damir Tuktamyshev
 * Service for Route.
 */
@Service("routeService")
public class RouteService {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(RouteService.class);   
	
	/**
	 * DAO for Route.
	 */
	@Autowired
	private RouteDAO routeDao;
	/**
	 * DAO for Station.
	 */
	@Autowired
	private StationDAO stationDao;
	
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
	 */
	@Transactional
	public List<StationVO> getStationsByTrain(int id){
		List<Station> stationList = new ArrayList<Station>();
		List<StationVO> stationVOList = new ArrayList<StationVO>();
		LOG.info("Getting Stations with Train.id " + id);
		stationList = routeDao.findStationsByTrain(id);
		if (stationList.isEmpty()) {
			LOG.warn("Received empty Station List from DAO");
			return new ArrayList<StationVO>();
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
	public void addRoute(Train train, String stationName, int count) throws EmptyResultException {
		
		List<Station> stationList = stationDao.findByName(stationName);
		if (stationList.isEmpty()) {
			LOG.warn("Received no Station with given name from DAO");
			throw new EmptyResultException("Ошибка при добавлении");
		}
		Route route = new Route(train, stationList.get(0), count);
		routeDao.persist(route);
		
	}
	
	
}
