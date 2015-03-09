package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import util.HibernateUtil;
import valueobjects.TimetableVO;
import dao.RouteDAO;
import dao.RouteDAOImpl;
import dao.RunDAO;
import dao.RunDAOImpl;
import dao.TimetableDAO;
import dao.TimetableDAOImpl;
import dao.TrainDAO;
import dao.TrainDAOImpl;
import entities.Route;
import entities.Run;
import entities.Timetable;
import entities.Train;

/**
 * @author Damir Tuktamyshev
 * Service for Timetable.
 */
public class TimetableService {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(TimetableService.class);
	
	/**
	 * DAO for Timetable.
	 */
	private TimetableDAO timetableDao = new TimetableDAOImpl();
	/**
	 * DAO for Train.
	 */
	private TrainDAO trainDao = new TrainDAOImpl();
	/**
	 * DAO for Run.
	 */
	private RunDAO runDao = new RunDAOImpl();
	/**
	 * DAO for Route.
	 */
	private RouteDAO routeDao = new RouteDAOImpl();
	
	/**
	 * @param timetableDao the timetableDao to set
	 */
	public void setTimetableDao(TimetableDAO timetableDao) {
		this.timetableDao = timetableDao;
	}


	/**
	 * @param trainDao the trainDao to set
	 */
	public void setTrainDao(TrainDAO trainDao) {
		this.trainDao = trainDao;
	}


	/**
	 * @param runDao the runDao to set
	 */
	public void setRunDao(RunDAO runDao) {
		this.runDao = runDao;
	}


	/**
	 * @param routeDao the routeDao to set
	 */
	public void setRouteDao(RouteDAO routeDao) {
		this.routeDao = routeDao;
	}


	/**Get list of Timetable by given Station and period of time
	 * @param station Station name
	 * @param fromTime Beginning of period
	 * @param toTime End of period
	 * @return TimetableVO list
	 * @throws NullPointerException
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	public List<TimetableVO> getTimetableByStation(String station, String fromTime, String toTime) 
			throws NullPointerException, IllegalStateException, IllegalArgumentException, ParseException {
		
		Date from = getDateFromString(fromTime);
		Date to = getDateFromString(toTime);
		List<Run> runList = new ArrayList<Run>();
		List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Getting Runs by Station between period of time");
			runList = timetableDao.getRunFromTimetableByStation(station, from, to);
			if (runList.isEmpty()) {
				throw new IllegalStateException();
			}
			LOG.info("Creating TimetableVO for every Run");
			for (Run run: runList){
				LOG.info("Getting arrival and departure times by every Run in list and by Station");
				Date departureTimeBeforeFormat = timetableDao.findDepTimeFromStation(station, run);
				Date arrivalTimeBeforeFormat = timetableDao.findArrTimeToStation(station, run);
				if (departureTimeBeforeFormat == null || arrivalTimeBeforeFormat == null) {
					throw new NullPointerException();
				}
				SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm");
				String departureTime = dt.format(departureTimeBeforeFormat);
				String arrivalTime = dt.format(arrivalTimeBeforeFormat);
				TimetableVO timetable = new TimetableVO(run.getTrainId().getNumber(), departureTime, arrivalTime);
				timetableVOList.add(timetable);
			}
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (NullPointerException | IllegalStateException | HibernateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
		}
		
		
		return timetableVOList;
		
	}

	
	/**Generates Date object from String.
	 * @param str String representing date
	 * @return Date
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	private Date getDateFromString(String str) throws ParseException, IllegalArgumentException  {
		if (str == null) {
    		throw new IllegalArgumentException();
    	}
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy HH:mm");
		date = sdf.parse(str);
		
    	return date;
    }
	
	/**Add Run into DB. 
	 * @param trainId Train id
	 * @param stationArray Array of Stations
	 * @param arrivalTime Arrival time
	 * @param departureTime Departure time
	 * @throws NullPointerException
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	public void addRun(int trainId, String[] stationArray, String[] arrivalTime, String[] departureTime) 
			throws NullPointerException, IllegalStateException, IllegalArgumentException, ParseException {
			LOG.info("Opening Hibernate Session with transaction");
			HibernateUtil.openCurrentSession();
			HibernateUtil.beginTransaction();
			try {
				if (arrivalTime.length !=  stationArray.length || departureTime.length != stationArray.length) {
					throw new IllegalArgumentException();
				}
				LOG.info("Searching for Train by Train.Id " + trainId);
				Train train = trainDao.findById(trainId); 
				if (train == null) {
					throw new NullPointerException();
				}
				LOG.info("Getting departure time from first station");
				Date depFromStart = getDateFromString(departureTime[0]);
				
				LOG.info("Getting arrival time to last station");
				Date arrToFinish = getDateFromString(arrivalTime[arrivalTime.length-1]);
				
				LOG.info("Creating new Run and adding it to DB");
				Run run = new Run(train, depFromStart, arrToFinish);
				runDao.persist(run);
				
				int seatsCount = train.getSeatsCount();
				for (int i = 0; i < arrivalTime.length; i++) {
					LOG.info("Creating new Timetable and adding it to DB");
					
					Timetable timetable = createTimetable(stationArray[i], trainId, run, arrivalTime[i], departureTime[i], seatsCount);
					timetableDao.persist(timetable);
					
				}
				LOG.info("Commiting transaction");
				HibernateUtil.commitTransaction();
			} catch (NullPointerException | IllegalStateException | HibernateException | IllegalArgumentException e) {
				LOG.warn("Transaction was rollbacked");
				HibernateUtil.rollbackTransaction();
				throw e;
			} finally {
				LOG.info("Closing Hibernate Session");
				HibernateUtil.closeCurrentSession();
			}
			
		
		
	}


	/**Creates Timetable by given parameters.
	 * @param station Station name
	 * @param trainId Train id
	 * @param run Run
	 * @param arrivalTime Arrival time
	 * @param departureTime Departure time
	 * @param seatsCount Count of seats
	 * @return Timetable
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 * @throws NullPointerException
	 */
	private Timetable createTimetable(String station, int trainId, Run run,
			String arrivalTime, String departureTime, int seatsCount) 
					throws IllegalArgumentException, ParseException, NullPointerException {
		
		Route route = routeDao.findRouteByStationStringAndTrainId(station, trainId);
		if (route == null) {
			throw new NullPointerException();
		}
		Date arrDate = getDateFromString(arrivalTime);
		Date depDate = getDateFromString(departureTime);
		Timetable timetable = new Timetable(route, run, arrDate, depDate, seatsCount);
		return timetable;
	}
}
