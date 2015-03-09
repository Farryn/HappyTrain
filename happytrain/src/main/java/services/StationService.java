package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import util.HibernateUtil;
import valueobjects.StationVO;
import dao.StationDAO;
import dao.StationDAOImpl;
import entities.Station;

public class StationService {
	
	private static final Logger LOG = Logger.getLogger(StationService.class);
	
	private StationDAO dao = new StationDAOImpl();
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(StationDAO dao) {
		this.dao = dao;
	}

	public List<Station> getAllStations() throws NullPointerException, IllegalStateException {
		List<Station> stationList = new ArrayList<Station>();
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Searching for all stations");
			stationList = dao.findAll();
			if (stationList.isEmpty()) {
				throw new IllegalStateException();
			}
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (NullPointerException | HibernateException | IllegalStateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		
		return stationList;
	}
	
	public List<StationVO> getAllStationVO() throws NullPointerException, IllegalStateException {
		List<Station> stationList = getAllStations();
		List<StationVO> stationVOList = new ArrayList<StationVO>();
		
		for (Station station: stationList) {
			stationVOList.add(new StationVO(station));
		}
		return stationVOList;
	}
	
	
	
	public void addStation(String stationName) {
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Creating Station and adding it into DB");
			Station station = new Station(stationName);
			dao.persist(station);
			
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
