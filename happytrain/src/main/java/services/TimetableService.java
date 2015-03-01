package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.HibernateUtil;
import valueobjects.RunVO;
import valueobjects.StationVO;
import dao.RouteDAOImpl;
import dao.RunDAOImpl;
import dao.TimetableDAOImpl;
import dao.TrainDAOImpl;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Timetable;
import entities.Train;

public class TimetableService {
	
	private Date getDateFromString(String str) {
    	Date date = new Date();
    	try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			date = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return date;
    }
	
	public List<RunVO> getRunFromTimetableByStation(StationVO stationVO, Date from, Date to){
		StationService ss = new StationService();
		Station station = ss.getStationByName(stationVO.getName());
		
		List<Run> runList = new ArrayList<Run>();
		List<RunVO> runVOList = new ArrayList<RunVO>();
		
		TimetableDAOImpl dao = new TimetableDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			runList = dao.getRunFromTimetableByStation(station, from, to);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		
		for (Run run: runList) {
			runVOList.add(new RunVO(run));
		}
		return runVOList;
		
	}

	public void addRun(int trainId, String[] stationArray, String[] arrivalTime, String[] departureTime) {
		
		if (arrivalTime.length ==  stationArray.length && departureTime.length == stationArray.length) {
			TrainDAOImpl trainDao = new TrainDAOImpl();
			RunDAOImpl runDao = new RunDAOImpl();
			RouteDAOImpl routeDao = new RouteDAOImpl();
			TimetableDAOImpl timetableDao = new TimetableDAOImpl();
			HibernateUtil.openCurrentSession();
			HibernateUtil.beginTransaction();
			try {
				
				Train train = trainDao.findById(trainId); 
				Date depFromStart = getDateFromString(departureTime[0]);
				Date arrToFinish = getDateFromString(arrivalTime[arrivalTime.length-1]);
				Run run = new Run(train, depFromStart, arrToFinish);
				runDao.persist(run);
				
				int seatsCount = train.getSeatsCount();
				for (int i = 0; i < arrivalTime.length; i++) {
					Route route = routeDao.findRouteByStationStringAndTrainId(stationArray[i], trainId);
					Date arrDate = getDateFromString(arrivalTime[i]);
					Date depDate = getDateFromString(departureTime[i]);
					Timetable timetable = new Timetable(route, run, arrDate, depDate, seatsCount);
					timetableDao.persist(timetable);
					
				}
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				HibernateUtil.rollbackTransaction();
			} finally {
				HibernateUtil.closeCurrentSession();
			}
			
		} else {
			System.out.println("AddRunServletError");
		}
		
	}
}
