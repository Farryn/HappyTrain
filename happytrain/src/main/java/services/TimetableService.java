package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import util.HibernateUtil;
import valueobjects.RunVO;
import valueobjects.StationVO;
import valueobjects.TimetableVO;
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
	
	private static Logger log = Logger.getLogger(TimetableService.class);
	
	
	
	public List<TimetableVO> getTimetableByStation(String station, Date from, Date to) throws Exception{
		
		List<Run> runList = new ArrayList<Run>();
		List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		
		TimetableDAOImpl dao = new TimetableDAOImpl();
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			log.info("Getting Runs by Station between period of time");
			runList = dao.getRunFromTimetableByStation(station, from, to);
			if (runList.isEmpty()) {
				throw new IllegalStateException();
			}
			log.info("Creating TimetableVO for every Run");
			for (Run run: runList){
				log.info("Getting arrival and departure times by every Run in list and by Station");
				Date departureTime = dao.findDepTimeFromStation(station, run);
				Date arrivalTime = dao.findArrTimeToStation(station, run);
				if (departureTime == null || arrivalTime == null) {
					throw new NullPointerException();
				}
				TimetableVO timetable = new TimetableVO(run.getTrainId().getNumber(), departureTime, arrivalTime);
				timetableVOList.add(timetable);
			}
			log.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			log.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			log.info("Closing Hibernate Session");
		}
		
		
		return timetableVOList;
		
	}

	
	private Date getDateFromString(String str) throws ParseException, IllegalArgumentException  {
		if (str == null) {
    		throw new IllegalArgumentException();
    	}
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		date = sdf.parse(str);
		
    	return date;
    }
	
	public void addRun(int trainId, String[] stationArray, String[] arrivalTime, String[] departureTime) throws Exception {
		
			TrainDAOImpl trainDao = new TrainDAOImpl();
			RunDAOImpl runDao = new RunDAOImpl();
			RouteDAOImpl routeDao = new RouteDAOImpl();
			TimetableDAOImpl timetableDao = new TimetableDAOImpl();
			
			log.info("Opening Hibernate Session with transaction");
			HibernateUtil.openCurrentSession();
			HibernateUtil.beginTransaction();
			try {
				if (arrivalTime.length !=  stationArray.length || departureTime.length != stationArray.length) {
					throw new IllegalArgumentException();
				}
				log.info("Searching for Train by Train.Id " + trainId);
				Train train = trainDao.findById(trainId); 
				
				log.info("Getting departure time from first station");
				Date depFromStart = getDateFromString(departureTime[0]);
				
				log.info("Getting arrival time to last station");
				Date arrToFinish = getDateFromString(arrivalTime[arrivalTime.length-1]);
				
				log.info("Creating new Run and adding it to DB");
				Run run = new Run(train, depFromStart, arrToFinish);
				runDao.persist(run);
				
				int seatsCount = train.getSeatsCount();
				for (int i = 0; i < arrivalTime.length; i++) {
					log.info("Creating new Timetable and adding it to DB");
					Route route = routeDao.findRouteByStationStringAndTrainId(stationArray[i], trainId);
					if (route == null) {
						throw new NullPointerException();
					}
					Date arrDate = getDateFromString(arrivalTime[i]);
					Date depDate = getDateFromString(departureTime[i]);
					Timetable timetable = new Timetable(route, run, arrDate, depDate, seatsCount);
					timetableDao.persist(timetable);
					
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
			
		
		
	}
}
