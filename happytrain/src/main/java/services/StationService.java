package services;

import java.util.ArrayList;
import java.util.List;

import util.HibernateUtil;
import valueobjects.StationVO;
import dao.StationDAOImpl;
import entities.Station;
import entities.Train;

public class StationService {
	
	public List<Station> getAllStations() {
		List<Station> stationList = new ArrayList<Station>();
		StationDAOImpl dao = new StationDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		stationList = dao.findAll();
		HibernateUtil.closeCurrentSessionwithTransaction();
		
		return stationList;
	}
	
	public List<StationVO> getAllStationVO() {
		StationService ss = new StationService();
		List<Station> stationList = ss.getAllStations();
		List<StationVO> stationVOList = new ArrayList<StationVO>();
		
		for (Station station: stationList) {
			stationVOList.add(new StationVO(station));
		}
		return stationVOList;
	}
	
	
	
	public Station getStationByName(String str){
		StationDAOImpl dao = new StationDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		Station station = dao.findByName(str);
		HibernateUtil.closeCurrentSessionwithTransaction();
		return station;
	}
	
	public StationVO getStationVOByName(String str) {
		StationService ss = new StationService();
		Station station = ss.getStationByName(str);
		StationVO stationVO = new StationVO(station); 
		return stationVO;
	}
	
	public void addStation(String stationName){
		StationDAOImpl dao = new StationDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		Station station = new Station(stationName);
		dao.persist(station);
		HibernateUtil.closeCurrentSessionwithTransaction();
	}

	

	
	
}
