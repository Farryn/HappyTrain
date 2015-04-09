package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.DateFormatUtil;
import util.EmptyResultException;
import valueobjects.StationVO;
import valueobjects.TimetableVO;
import valueobjects.UserVO;
import dao.RouteDAO;
import dao.RunDAO;
import dao.StationDAO;
import dao.TicketDAO;
import dao.TimetableDAO;
import dao.UserDAO;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Ticket;
import entities.Timetable;
import entities.User;

/**
 * @author Damir Tuktamyshev
 * Service for searching Trains and buying Tickets. 
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService{
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(ClientServiceImpl.class);

	
	/**
	 * DAO for User.
	 */
	@Autowired
	private UserDAO userDao;
	/**
	 * DAO for Station.
	 */
	@Autowired
	private StationDAO  stationDao;
	/**
	 * DAO for Run.
	 */
	@Autowired
	private RunDAO runDao;
	/**
	 * DAO for Tickets.
	 */
	@Autowired
	private TicketDAO ticketDao;
	/**
	 * DAO for Route.
	 */
	@Autowired
	private RouteDAO routeDao;
	/**
	 * DAO for Timetable.
	 */
	@Autowired
	private TimetableDAO timetableDao;
	
	@Autowired
	private DateFormatUtil dateFormatUtil;
	
	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}


	/**
	 * @param stationDao the stationDao to set
	 */
	public void setStationDao(StationDAO stationDao) {
		this.stationDao = stationDao;
	}


	/**
	 * @param runDao the runDao to set
	 */
	public void setRunDao(RunDAO runDao) {
		this.runDao = runDao;
	}


	/**
	 * @param ticketDao the ticketDao to set
	 */
	public void setTicketDao(TicketDAO ticketDao) {
		this.ticketDao = ticketDao;
	}


	/**
	 * @param routeDao the routeDao to set
	 */
	public void setRouteDao(RouteDAO routeDao) {
		this.routeDao = routeDao;
	}


	/**
	 * @param timetableDao the timetableDao to set
	 */
	public void setTimetableDao(TimetableDAO timetableDao) {
		this.timetableDao = timetableDao;
	}


	/**Search Train passing two given Stations.The departure time from first Station must be between given period of time.
	 * @param stationA First Station
	 * @param stationB Second Station
	 * @param fromTime Beginning of period
	 * @param toTime End of period
	 * @return TimetableVO list
	 */
	@Transactional
	public List<TimetableVO> searchTrain(String stationA, String stationB, String fromTime, String toTime) {
		
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
		timetableVOList = getSearchedTrains(stationA, stationB, from, to);
		if (timetableVOList.isEmpty()) {
			LOG.warn("Received empty TimetableVO List from DAO");
		}
		return timetableVOList;
	}
	
	
	 /**Inner method of searchTrain method providing main logic.
	 * @param stationA First Station
	 * @param stationB Second Station
	 * @param from Beginning of period
	 * @param to End of period
	 * @return TimetableVO list
	 */
	private List<TimetableVO> getSearchedTrains(String stationA, String stationB, Date from, Date to) {
		 
		 	List<Run> runList = new ArrayList<Run>();
		 	List<Route> routeList = new ArrayList<Route>();
		 	List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		 
		    LOG.info("Getting Route between " + stationA + " and " + stationB);
		 	routeList = routeDao.findRouteFromAtoB(stationA, stationB);
			if (routeList.isEmpty()) {
				return new ArrayList<TimetableVO>();
			} 
			
			LOG.info("Getting Runs between period of time from" + from + " to " + to);
			runList = timetableDao.findTrainWithDepTimeBetweenPeriodOfTime(routeList, from, to);
			if (runList.isEmpty()) {
				return new ArrayList<TimetableVO>();
			}
			
			LOG.info("Getting TimetableVO list");
			timetableVOList = getTimetableByRunList(runList, stationA, stationB);
			if (timetableVOList.isEmpty()) {
				return new ArrayList<TimetableVO>();
			}
		return timetableVOList;
	}


	/**Generates TimetableVO list using list of Runs.
	 * @param runList Run list
	 * @param stationA First Station
	 * @param stationB Second Station
	 * @return TimetableVO list
	 * @throws NullPointerException
	 */
	private List<TimetableVO> getTimetableByRunList(List<Run> runList, String stationA, String stationB) {
		
		List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		LOG.info("Creating TimetableVO on every found Run");
			
		for (Run run:runList) {
			Date departureTimeBeforeFormat = timetableDao.findDepTimeFromStation(stationA, run);
			Date arrivalTimeBeforeFormat = timetableDao.findArrTimeToStation(stationB, run);
			int count = timetableDao.findAvailableSeatsCount(stationA, String.valueOf(run.getId()));
				
			if (departureTimeBeforeFormat == null || arrivalTimeBeforeFormat == null) {
				LOG.warn("Received no datetime from given Station from DAO");
				return new ArrayList<TimetableVO>();
			}
			SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			String departureTime = dt.format(departureTimeBeforeFormat);
			String arrivalTime = dt.format(arrivalTimeBeforeFormat);
			TimetableVO timetable = new TimetableVO(run.getTrainId().getId(), run.getTrainId().getNumber(), run.getId(), departureTime, arrivalTime, count);
			timetableVOList.add(timetable);
		}
			
		return timetableVOList;
	}


	
	
	/**
	 * Method for buying Ticket.
	 * @param userVO User value object
	 * @param stationFrom Departure Station
	 * @param stationTo Arrival Station
	 * @param depTime Departure time
	 * @param runId Run id
	 * @throws EmptyResultException 
	 */
	@Transactional
	public void buyTicket(String login,  String stationFrom, String stationTo, String depTime, String runId) throws EmptyResultException {
		
		if (checkForBuying(login, stationFrom, depTime, runId).equals("OK")) {
			LOG.info("Creating new Ticket and adding it to DB");
			Run run = runDao.findById(Integer.parseInt(runId));
			if (run == null) {
				LOG.warn("Received no Run with given ID from DAO");
				throw new EmptyResultException("Received no Run with given ID from DAO");
			}
			addTicket(login, stationFrom, stationTo, run, depTime);
			updateTimetable(stationFrom, stationTo, run);
		}
		
	}

	/**Method updates available seats in Timetable.
	 * @param stationFrom Departure Station
	 * @param stationTo Arrival Station
	 * @param run Run
	 * @throws EmptyResultException 
	 */
	private void updateTimetable(String stationFrom, String stationTo, Run run) throws EmptyResultException {
		LOG.info("Searching ordinal numbers of first and last Station in order");
		int stationFromOrdinalNumber = routeDao.getOrdinalNumber(stationFrom, run.getTrainId());
		int stationToOrdinalNumber = routeDao.getOrdinalNumber(stationTo, run.getTrainId());
		
		LOG.info("Searching for all Stations between first and last Station in order");
		List<Station> stationList = routeDao.findStationsBetweenFromAndTo(run, stationFromOrdinalNumber, stationToOrdinalNumber);
		if (stationList.isEmpty()) {
			LOG.warn("Received empty Station List from DAO");
			throw new EmptyResultException("Received empty Station List from DAO");
		}
		for (Station station: stationList) {
			LOG.info("Searching for Timetable");
			List<Timetable> timetableList = timetableDao.findTimetableByRunAndStation(station, run);
			if (timetableList.isEmpty()) {
				LOG.warn("Could not get Timetable with Station name " + station.getName() + " and Run.Id " + run.getId());
				throw new EmptyResultException("Could not get Timetable with Station name " + station.getName() + " and Run.Id " + run.getId());
			}
			
			LOG.info("Updating timetable available seats count");
			timetableList.get(0).setAvailableSeats(timetableList.get(0).getAvailableSeats() - 1);
			timetableDao.update(timetableList.get(0));
		}
		
	}


	/**Method adds Ticket into DB.
	 * @param userVO User value object
	 * @param stationFrom Departure Station
	 * @param stationTo Arrival Station
	 * @param run Run
	 * @param depTime Departure time
	 * @throws EmptyResultException 
	 */
	private void addTicket(String login, String stationFrom, String stationTo,	Run run, String depTime) throws EmptyResultException {
		
		Date date;
		try {
			date = dateFormatUtil.getFullDateFromString(depTime);
		} catch (ParseException e) {
			LOG.warn("Can't convert Date from String");
			throw new EmptyResultException("Can't convert Date from String");
		}
		List<User> userList = userDao.findByUserName(login);
		List<Station> stationA = stationDao.findByName(stationFrom);
		List<Station> stationB = stationDao.findByName(stationTo);
		
		if (userList.isEmpty() || stationA.isEmpty() || stationB.isEmpty()) {
			LOG.warn("Received empty List from DAO");
			throw new EmptyResultException("Received empty List from DAO");
		}
		User user = userList.get(0);
		
		Ticket ticket = new Ticket(user, run, stationA.get(0), stationB.get(0), date);
		ticketDao.persist(ticket);
		
	}


	/**Method does all checks for buying Ticket.
	 * @param userVO User value object
	 * @param stationFrom Departure Station
	 * @param depTime Departure time
	 * @param runId Run id
	 * @return true if all checks were passed
	 */
	private String checkForBuying(String login,  String stationFrom, String depTime, String runId) {
		
		Date date;
		try {
			date = dateFormatUtil.getFullDateFromString(depTime);
		} catch (ParseException e) {
			LOG.warn("Can't convert Date from String");
			return "Can't convert Date from String";
		}
		LOG.info("Checking for free seats in Run " + runId);
		boolean haveFreeSeats = checkForFreeSeats(stationFrom, runId);
		if (!haveFreeSeats) {
			LOG.info("Fail. There is no free seats");
			return "Нет свободных мест";
		}
		
		LOG.info("Checking for User is already in passenger list on Run " + runId);
		boolean isAlreadyInTicketList = checkForRegistrationOnRun(runId, login);
		if (isAlreadyInTicketList) {
			LOG.info("Fail. User is already registered on that Run");
			return "Вы уже зарегистрированы на этот поезд";
		}
		
		LOG.info("Checking for time left until departure");
		boolean haveEnoughTimeUntilDeparture = checkForEnoughTimeUntilDeparture(date);
		if (!haveEnoughTimeUntilDeparture) {
			LOG.info("Fail. There is no time left");
			return "До отправления поезда осталось менее 10 минут";
		}
		
		return "OK";
	}

	/**Method checks for amount of time left until departure.
	 * @param date Departure date
	 * @return true if there is enough time
	 */
	private boolean checkForEnoughTimeUntilDeparture(Date date) {
		LOG.info("Getting difference between current time and depTime of Train");
		long duration = date.getTime() - new Date().getTime();
		long difference = TimeUnit.MILLISECONDS.toMinutes(duration);
		if (difference > 10) {
			return true;
		}
		return false;
	}

	/**Method checks whether User has already registered on given Run.
	 * @param run Run
	 * @param userVO User value object
	 * @return true if User has already registered
	 */
	@Transactional
	private boolean checkForRegistrationOnRun(String run, String login){
		int runId = Integer.parseInt(run);
		boolean isRegistered = true;
		LOG.info("Searching Ticket in DB");
		List<Ticket> ticketList = ticketDao.findTicketByRunAndLogin(runId, login);
		if (ticketList.isEmpty()) {
			isRegistered = false;
		}
		return isRegistered;
	}

	/**Method checks for amount of free seats on given Run.
	 * @param stationFrom Departure Station
	 * @param runId Run id
	 * @return true if there are free seats
	 */
	@Transactional
	private boolean checkForFreeSeats(String stationFrom, String runId) {
		int availableSeats = 0;
		availableSeats = timetableDao.findAvailableSeatsCount(stationFrom, runId);
		if (availableSeats > 0) {
			return true;
		}
		return false;
	}


	/**Method returns TimetableVO list by given Run id and list of Stations.
	 * @param runId Run id
	 * @param stationList List of Stations
	 * @return TimetableVO list
	 */
	@Transactional
	public List<TimetableVO> getTimesFromStationList(int runId, List<StationVO> stationList){
		LOG.info("Checking input parameters");
		if (stationList.isEmpty()) {
			LOG.warn("Empty Station List input ");
			return new ArrayList<TimetableVO>();
		}
		
		List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		
		LOG.info("Getting Run by id " + runId);
		Run run = runDao.findById(runId);
		if (run == null) {
			LOG.warn("Received no Run with given ID from DAO");
			return new ArrayList<TimetableVO>();
		}
		
		LOG.info("Creating TimetableVO on every Station");
		for (StationVO station: stationList) {
			Date departureTimeBeforeFormat = timetableDao.findDepTimeFromStation(station.getName(), run);
			Date arrivalTimeBeforeFormat = timetableDao.findArrTimeToStation(station.getName(), run);
			if (departureTimeBeforeFormat == null || arrivalTimeBeforeFormat == null) {
				LOG.warn("Received no date from DAO");
				return new ArrayList<TimetableVO>();
			}
			SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			String departureTime = dt.format(departureTimeBeforeFormat);
			String arrivalTime = dt.format(arrivalTimeBeforeFormat);
			TimetableVO timetable = new TimetableVO(departureTime, arrivalTime);
			timetableVOList.add(timetable);
		}
		return timetableVOList;
	}
}
