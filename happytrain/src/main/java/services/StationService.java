package services;

import java.util.ArrayList;
import java.util.List;

import dao.StationDAOImpl;
import entities.Station;
import entities.Train;

public class StationService {
	
	public List<Station> getAllStations() {
		List<Station> stationList = new ArrayList<Station>();
		StationDAOImpl dao = new StationDAOImpl();
		dao.openCurrentSessionwithTransaction();
		stationList = dao.findAll();
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
	
	public void addStation(Station station){
		StationDAOImpl dao = new StationDAOImpl();
		dao.openCurrentSessionwithTransaction();
		dao.persist(station);
		dao.closeCurrentSessionwithTransaction();
	}

	
	
}
