package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.HibernateUtil;
import valueobjects.RunVO;
import valueobjects.StationVO;
import dao.RouteDAOImpl;
import dao.StationDAOImpl;
import dao.TimetableDAOImpl;
import entities.Route;
import entities.Run;
import entities.Station;

public class ClientService {
	
	public List<RunVO> searchTrain(String stationA, String stationB, Date from, Date to) {
		StationService ss = new StationService();
		Station beginStation = ss.getStationByName(stationA);
		Station endStation = ss.getStationByName(stationB);
		List<RunVO> runVOList = new ArrayList<RunVO>();
		List<Run> runList = new ArrayList<Run>();
		List<Route> routeList = new ArrayList<Route>();
		RouteDAOImpl routeDao = new RouteDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		routeList = routeDao.findRouteFromAtoB(beginStation, endStation);
		HibernateUtil.closeCurrentSessionwithTransaction();
		
		if (!routeList.isEmpty()) {
			TimetableDAOImpl tdao = new TimetableDAOImpl();
			HibernateUtil.openCurrentSessionwithTransaction();
			runList = tdao.findTrainWithDepTimeBetweenPeriodOfTime(routeList, from, to);
			HibernateUtil.closeCurrentSessionwithTransaction();
		}
		for (Run run: runList) {
			runVOList.add(new RunVO(run));
		}
		return runVOList;
	}
	
	public Date getStationDepTime(StationVO stationVO, RunVO runVO){
		StationService ss = new StationService();
		RunService rs = new RunService();
		Station station = ss.getStationByName(stationVO.getName());
		Run run = rs.getRunById(runVO.getId());
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		Date depTime = tdao.findDepTimeFromStation(station, run);
		HibernateUtil.closeCurrentSessionwithTransaction();
		return depTime;
	}
	
	public Date getStationArrTime(StationVO stationVO, RunVO runVO){
		StationService ss = new StationService();
		RunService rs = new RunService();
		Station station = ss.getStationByName(stationVO.getName());
		Run run = rs.getRunById(runVO.getId());
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		Date depTime = tdao.findArrTimeToStation(station, run);
		HibernateUtil.closeCurrentSessionwithTransaction();
		return depTime;
	}
	
	public int getStationAvailableSeats(StationVO stationVO, RunVO runVO){
		StationService ss = new StationService();
		RunService rs = new RunService();
		Station station = ss.getStationByName(stationVO.getName());
		Run run = rs.getRunById(runVO.getId());
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		int count = tdao.findAvailableSeatsCount(station, run);
		HibernateUtil.closeCurrentSessionwithTransaction();
		return count;
	}
}
