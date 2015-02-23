package services;

import java.util.List;

import dao.StationDAOImpl;
import entities.Station;

public class StationService {
	
	public List<Station> getAllStations() {
		StationDAOImpl dao = new StationDAOImpl();
		dao.openCurrentSessionwithTransaction();
		List<Station> stationList = dao.findAll();
		dao.closeCurrentSessionwithTransaction();
		return stationList;
	}
	
	public Station getStationByName(String str){
		StationDAOImpl dao = new StationDAOImpl();
		dao.openCurrentSessionwithTransaction();
		Station station = dao.findByName(str);
		dao.closeCurrentSessionwithTransaction();
		return station;
		
	}
	
}
