package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.HibernateUtil;
import valueobjects.StationVO;
import dao.StationDAOImpl;
import entities.Station;

public class StationService {
	
	private static Logger log = Logger.getLogger(StationService.class);
	
	public List<Station> getAllStations() throws Exception {
		List<Station> stationList = new ArrayList<Station>();
		StationDAOImpl dao = new StationDAOImpl();
		
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			log.info("Searching for all stations");
			stationList = dao.findAll();
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
		
		return stationList;
	}
	
	public List<StationVO> getAllStationVO() throws Exception {
		StationService ss = new StationService();
		List<Station> stationList = ss.getAllStations();
		List<StationVO> stationVOList = new ArrayList<StationVO>();
		
		for (Station station: stationList) {
			stationVOList.add(new StationVO(station));
		}
		return stationVOList;
	}
	
	
	/*
	public Station getStationByName(String str){
		StationDAOImpl dao = new StationDAOImpl();
		Station station = null;
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			station = dao.findByName(str);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		return station;
	}
	
	public StationVO getStationVOByName(String str) {
		StationService ss = new StationService();
		Station station = ss.getStationByName(str);
		StationVO stationVO = new StationVO(station); 
		return stationVO;
	}*/
	
	public void addStation(String stationName) throws Exception{
		StationDAOImpl dao = new StationDAOImpl();
		
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			log.info("Creating Station and adding it into DB");
			Station station = new Station(stationName);
			dao.persist(station);
			
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
	}

	

	
	
}
