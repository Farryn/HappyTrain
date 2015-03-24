package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.HibernateUtil;
import valueobjects.StationVO;
import dao.StationDAO;
import dao.StationDAOImpl;
import entities.Station;

/**
 * @author Damir Tuktamyshev
 * Service for Station.
 */
@Service("stationService")
public class StationService {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(StationService.class);
	
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
	 * @throws NullPointerException
	 * @throws IllegalStateException
	 */
	@Transactional
	public List<Station> getAllStations(){
		List<Station> stationList = new ArrayList<Station>();
		
		LOG.info("Opening Hibernate Session with transaction");
		//HibernateUtil.openCurrentSession();
		//HibernateUtil.beginTransaction();
		try {
			LOG.info("Searching for all stations");
			stationList = stationDao.findAll();
			if (stationList.isEmpty()) {
				throw new IllegalStateException();
			}
			LOG.info("Commiting transaction");
			//HibernateUtil.commitTransaction();
		} catch (NullPointerException | HibernateException | IllegalStateException e) {
			LOG.warn("Transaction was rollbacked");
			e.printStackTrace();
			//HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			//HibernateUtil.closeCurrentSession();
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
	 */
	public void addStation(String stationName) {
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Creating Station and adding it into DB");
			Station station = new Station(stationName);
			stationDao.persist(station);
			
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
	}

	

	
	
}
