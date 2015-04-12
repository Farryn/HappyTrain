package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.EmptyResultException;
import valueobjects.StationVO;
import dao.StationDAO;
import entities.Station;

/**
 * @author Damir Tuktamyshev
 * Service for Station.
 */
@Service("stationService")
public class StationServiceImpl implements StationService{
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(StationServiceImpl.class);
	
	/**
	 * DAO for Station.
	 */
	@Autowired
	@Qualifier(value = "stationDao")
	private StationDAO stationDao;
	
	/**
	 * @param dao the dao to set
	 */
	public void setStationDao(StationDAO stationDao) {
		this.stationDao = stationDao;
	}

	/**Get all Stations.
	 * @return List of Stations
	 */
	@Transactional
	public List<Station> getAllStations(){
		List<Station> stationList = new ArrayList<Station>();
		LOG.info("Searching for all stations");
		stationList = stationDao.findAll();
		if (stationList.isEmpty()) {
			LOG.warn("Received empty Station List from DAO");
		}
		
		return stationList;
	}
	
	/**Transform Station list into StationVO list.  
	 * @return StationVO list
	 */
	@Transactional
	public List<StationVO> getAllStationVO(){
		List<Station> stationList = getAllStations();
		List<StationVO> stationVOList = new ArrayList<StationVO>();
		
		for (Station station: stationList) {
			stationVOList.add(new StationVO(station));
		}
		return stationVOList;
	}
	
	
	
	/**Add station into DB.
	 * @param stationName
	 * @throws EmptyResultException 
	 */
	@Transactional
	public void addStation(String stationName) throws EmptyResultException {
		LOG.info("Creating Station and adding it into DB");
		Station station = new Station(stationName);
		List<Station> check = stationDao.findByName(stationName);
		if (!check.isEmpty()) throw new EmptyResultException("Station already exists");
		stationDao.persist(station);
			
	}

	

	
	
}
