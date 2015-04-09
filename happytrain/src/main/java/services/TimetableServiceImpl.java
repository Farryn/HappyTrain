package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.DateFormatUtil;
import util.EmptyResultException;
import valueobjects.TimetableVO;
import dao.RouteDAO;
import dao.RunDAO;
import dao.TimetableDAO;
import dao.TrainDAO;
import entities.Route;
import entities.Run;
import entities.Timetable;
import entities.Train;

/**
 * @author Damir Tuktamyshev
 * Service for Timetable.
 */
@Service("timetableService")
public class TimetableServiceImpl implements TimetableService{
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(TimetableServiceImpl.class);
	
	@Autowired
	private DateFormatUtil dateFormatUtil;
	
	/**
	 * DAO for Timetable.
	 */
	@Autowired
	private TimetableDAO timetableDao;
	/**
	 * DAO for Train.
	 */
	@Autowired
	private TrainDAO trainDao;
	/**
	 * DAO for Run.
	 */
	@Autowired
	private RunDAO runDao;
	/**
	 * DAO for Route.
	 */
	@Autowired
	private RouteDAO routeDao;
	
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
	 */
	@Transactional
	public List<TimetableVO> getTimetableByStation(String station, String fromTime, String toTime) {
		
		Date from;
		Date to;
		try {
			from = dateFormatUtil.getFullDateFromString(fromTime);
			to = dateFormatUtil.getFullDateFromString(toTime);
		} catch (ParseException e) {
			LOG.warn("Can't convert Date from String");
			return new ArrayList<TimetableVO>();
		}
		
		List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		LOG.info("Getting Runs by Station between period of time");
		List<Run> runList = timetableDao.getRunFromTimetableByStation(station, from, to);
		if (runList.isEmpty()) {
			LOG.warn("Received empty Station List from DAO");
			return new ArrayList<TimetableVO>();
		}
		LOG.info("Creating TimetableVO for every Run");
		for (Run run: runList){
			LOG.info("Getting arrival and departure times by every Run in list and by Station");
			Date departureTimeBeforeFormat = timetableDao.findDepTimeFromStation(station, run);
			Date arrivalTimeBeforeFormat = timetableDao.findArrTimeToStation(station, run);
			if (departureTimeBeforeFormat == null || arrivalTimeBeforeFormat == null) {
				LOG.warn("Received no datetime from given Station from DAO");
				return new ArrayList<TimetableVO>();
			}
			SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			String departureTime = dt.format(departureTimeBeforeFormat);
			String arrivalTime = dt.format(arrivalTimeBeforeFormat);
			TimetableVO timetable = new TimetableVO(run.getTrainId().getNumber(), departureTime, arrivalTime);
			timetableVOList.add(timetable);
		}
		return timetableVOList;
		
	}

	
	
	
	/**Add Run into DB. 
	 * @param trainId Train id
	 * @param stationArray Array of Stations
	 * @param arrivalTime Arrival time
	 * @param departureTime Departure time
	 * @throws EmptyResultException 
	 */
	@Transactional
	public void addRun(int trainId, String[] stationArray, String[] arrivalTime, String[] departureTime)
								throws EmptyResultException {
		if (arrivalTime.length !=  stationArray.length || departureTime.length != stationArray.length) {
			LOG.warn("Arrays length do not match");
			throw new EmptyResultException("Arrays length do not match");
		}
		LOG.info("Searching for Train by Train.Id " + trainId);
		Train train = trainDao.findById(trainId); 
		if (train == null) {
			LOG.warn("Received no Train with given ID from DAO");
			throw new EmptyResultException("Received no Train with given ID from DAO");
		}
		
		Date depFromStart;
		Date arrToFinish;
		try {
			LOG.info("Getting departure time from first station");
			depFromStart = dateFormatUtil.getFullDateFromString(departureTime[0]);
			
			LOG.info("Getting arrival time to last station");
			arrToFinish = dateFormatUtil.getFullDateFromString(arrivalTime[arrivalTime.length-1]);
		} catch (ParseException e) {
			LOG.warn("Can't convert Date from String");
			throw new EmptyResultException("Can't convert Date from String");
		}
			
		LOG.info("Creating new Run and adding it to DB");
		Run run = new Run(train, depFromStart, arrToFinish);
		runDao.persist(run);
			
		int seatsCount = train.getSeatsCount();
		for (int i = 0; i < arrivalTime.length; i++) {
			LOG.info("Creating new Timetable and adding it to DB");
			Timetable timetable = createTimetable(stationArray[i], trainId, run, arrivalTime[i], departureTime[i], seatsCount);
			timetableDao.persist(timetable);
					
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
	 * @throws EmptyResultException 
	 */
	private Timetable createTimetable(String station, int trainId, Run run, String arrivalTime, String departureTime, int seatsCount) throws EmptyResultException {
		
		LOG.info("Getting Route by Station name: " + station + " and TrainId: " + trainId);
		List<Route> routeList = routeDao.findRouteByStationStringAndTrainId(station, trainId);
		if (routeList.isEmpty()) {
			LOG.warn("Received empty Route List from DAO");
			throw new EmptyResultException("Received empty Route List from DAO");
		}
		Date arrDate;
		Date depDate;
		try {
			arrDate = dateFormatUtil.getFullDateFromString(arrivalTime);
			depDate = dateFormatUtil.getFullDateFromString(departureTime);
		} catch (ParseException e) {
			LOG.warn("Can't convert Date from String");
			throw new EmptyResultException("Can't convert Date from String");
		}
		Timetable timetable = new Timetable(routeList.get(0), run, arrDate, depDate, seatsCount);
		return timetable;
	}
}
