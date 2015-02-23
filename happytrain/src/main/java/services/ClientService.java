package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.RouteDAOImpl;
import dao.StationDAOImpl;
import dao.TimetableDAOImpl;
import entities.Route;
import entities.Run;
import entities.Station;

public class ClientService {
	
	public List<Run> searchTrain(Station beginStation, Station endStation, Date from, Date to) {
		List<Run> runList = null;
		RouteDAOImpl routeDao = new RouteDAOImpl();
		routeDao.openCurrentSessionwithTransaction();
		List<Route> routeList = routeDao.findRouteFromAtoB(beginStation, endStation);
		routeDao.closeCurrentSessionwithTransaction();
		
		if (!routeList.isEmpty()) {
			TimetableDAOImpl tdao = new TimetableDAOImpl();
			tdao.openCurrentSessionwithTransaction();
			runList = tdao.findTrainWithDepTimeBetweenPeriodOfTime(routeList, from, to);
			tdao.closeCurrentSessionwithTransaction();
		}
		
		return runList;
	}
	
	public Date getStationDepTime(Station station, Run run){
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		tdao.openCurrentSessionwithTransaction();
		Date depTime = tdao.findDepTimeFromStation(station, run);
		tdao.closeCurrentSessionwithTransaction();
		return depTime;
	}
	
	public Date getStationArrTime(Station station, Run run){
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		tdao.openCurrentSessionwithTransaction();
		Date depTime = tdao.findArrTimeToStation(station, run);
		tdao.closeCurrentSessionwithTransaction();
		return depTime;
	}
	
	public int getStationAvailableSeats(Station station, Run run){
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		tdao.openCurrentSessionwithTransaction();
		int count = tdao.findAvailableSeatsCount(station, run);
		tdao.closeCurrentSessionwithTransaction();
		return count;
	}
}
