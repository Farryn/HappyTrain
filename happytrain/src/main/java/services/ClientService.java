package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import util.HibernateUtil;
import util.MyException;
import valueobjects.StationVO;
import valueobjects.TimetableVO;
import valueobjects.UserVO;
import dao.RouteDAO;
import dao.RouteDAOImpl;
import dao.RunDAO;
import dao.RunDAOImpl;
import dao.StationDAO;
import dao.StationDAOImpl;
import dao.TicketDAO;
import dao.TicketDAOImpl;
import dao.TimetableDAO;
import dao.TimetableDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Ticket;
import entities.Timetable;
import entities.User;

/**
 * @author 
 * Service for searching Trains and buying Tickets. 
 */
public class ClientService {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(ClientService.class);

	
	/**
	 * DAO for User.
	 */
	private UserDAO userDao = new UserDAOImpl();
	/**
	 * DAO for Station.
	 */
	private StationDAO  stationDao = new StationDAOImpl();
	/**
	 * DAO for Run.
	 */
	private RunDAO runDao = new RunDAOImpl();
	/**
	 * DAO for Tickets.
	 */
	private TicketDAO ticketDao = new TicketDAOImpl();
	/**
	 * DAO for Route.
	 */
	private RouteDAO routeDao = new RouteDAOImpl();
	/**
	 * DAO for Timetable.
	 */
	private TimetableDAO timetableDao = new TimetableDAOImpl();
	/**
	 * Service for Run.
	 */
	private RunService runService = new RunService();
	
	/**
	 * @param runService the runService to set
	 */
	public void setRunService(RunService runService) {
		this.runService = runService;
	}


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
	 * @throws NullPointerException 
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	public List<TimetableVO> searchTrain(String stationA, String stationB, String fromTime, String toTime) 
			throws NullPointerException ,IllegalStateException, IllegalArgumentException, ParseException {
		
		Date from = getDateFromString(fromTime);
		Date to = getDateFromString(toTime);
		LOG.info("Checking input parameters");
		if (stationA == null || stationB == null || from == null || to == null) {
			LOG.warn("Input parameter is null");
			throw new IllegalArgumentException();
		}
		
		List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			timetableVOList = getSearchedTrains(stationA, stationB, from, to);
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
		return timetableVOList;
	}
	
	
	 /**Inner method of searchTrain method providing main logic.
	 * @param stationA First Station
	 * @param stationB Second Station
	 * @param from Beginning of period
	 * @param to End of period
	 * @return TimetableVO list
	 * @throws IllegalStateException
	 */
	private List<TimetableVO> getSearchedTrains(String stationA,
			String stationB, Date from, Date to) throws IllegalStateException{
		 
		 	List<Run> runList = new ArrayList<Run>();
		 	List<Route> routeList = new ArrayList<Route>();
		 	List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		 
		    LOG.info("Getting Route between " + stationA + " and " + stationB);
		 	routeList = routeDao.findRouteFromAtoB(stationA, stationB);
			if (routeList.isEmpty()) {
				throw new IllegalStateException();
			} 
			
			LOG.info("Getting Runs between period of time from" + from + " to " + to);
			runList = timetableDao.findTrainWithDepTimeBetweenPeriodOfTime(routeList, from, to);
			if (runList.isEmpty()) {
				throw new IllegalStateException();
			}
			
			LOG.info("Getting TimetableVO list");
			timetableVOList = getTimetableByRunList(runList, stationA, stationB);
			if (timetableVOList.isEmpty()) {
				throw new IllegalStateException();
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
	private List<TimetableVO> getTimetableByRunList(List<Run> runList, String stationA, String stationB)
			 throws NullPointerException{
		
		List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		LOG.info("Creating TimetableVO on every found Run");
			
		for (Run run:runList) {
			Date departureTimeBeforeFormat = timetableDao.findDepTimeFromStation(stationA, run);
			Date arrivalTimeBeforeFormat = timetableDao.findArrTimeToStation(stationB, run);
			int count = timetableDao.findAvailableSeatsCount(stationA, String.valueOf(run.getId()));
				
			if (departureTimeBeforeFormat == null || arrivalTimeBeforeFormat == null) {
				throw new NullPointerException();
			}
			SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			String departureTime = dt.format(departureTimeBeforeFormat);
			String arrivalTime = dt.format(arrivalTimeBeforeFormat);
			TimetableVO timetable = new TimetableVO(run.getTrainId().getId(), run.getTrainId().getNumber(), run.getId(), departureTime, arrivalTime, count);
			timetableVOList.add(timetable);
		}
			
		return timetableVOList;
	}


	/**Generates Date object from String.
	 * @param str String representing date
	 * @return Date
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	private Date getDateFromString(String str) throws IllegalArgumentException, ParseException {
		 if (str == null) {
	    		throw new IllegalArgumentException();
	    	}
	    	Date date = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy HH:mm");
			date = sdf.parse(str);
			
	    	return date;
	    }
	
	/**
	 * Method for buying Ticket.
	 * @param userVO User value object
	 * @param stationFrom Departure Station
	 * @param stationTo Arrival Station
	 * @param depTime Departure time
	 * @param runId Run id
	 * @throws MyException if checks failed
	 * @throws ParseException 
	 * @throws IllegalArgumentException 
	 */
	public void buyTicket(UserVO userVO,  String stationFrom, String stationTo, String depTime, String runId) 
			throws NullPointerException, IllegalStateException, IllegalArgumentException, ParseException, MyException  {
		
		if (checkForBuying(userVO, stationFrom, depTime, runId)) {
			LOG.info("Opening Hibernate Session with transaction");
			HibernateUtil.openCurrentSession();
			HibernateUtil.beginTransaction();
			try {
				LOG.info("Creating new Ticket and adding it to DB");
				Run run = runDao.findById(Integer.parseInt(runId));
				if (run == null) {
					throw new NullPointerException();
				}
				addTicket(userVO, stationFrom, stationTo, run, depTime);
				updateTimetable(stationFrom, stationTo, run);
				
				LOG.info("Commiting transaction");
				HibernateUtil.commitTransaction();
			} catch (NullPointerException | IllegalStateException | HibernateException | IllegalArgumentException | ParseException e) {
				LOG.warn("Transaction was rollbacked");
				HibernateUtil.rollbackTransaction();
				throw e;
			} finally {
				LOG.info("Closing Hibernate Session");
				HibernateUtil.closeCurrentSession();
			}
			
		}
		
	}

	/**Method updates available seats in Timetable.
	 * @param stationFrom Departure Station
	 * @param stationTo Arrival Station
	 * @param run Run
	 * @throws IllegalStateException
	 * @throws NullPointerException
	 */
	private void updateTimetable(String stationFrom, String stationTo, Run run) throws IllegalStateException, NullPointerException{
		LOG.info("Searching ordinal numbers of first and last Station in order");
		int stationFromOrdinalNumber = routeDao.getOrdinalNumber(stationFrom, run.getTrainId());
		int stationToOrdinalNumber = routeDao.getOrdinalNumber(stationTo, run.getTrainId());
		
		LOG.info("Searching for all Stations between first and last Station in order");
		List<Station> stationList = routeDao.findStationsBetweenFromAndTo(run, stationFromOrdinalNumber, stationToOrdinalNumber);
		if (stationList.isEmpty()) {
			LOG.warn("Empty stationList returned");
			throw new IllegalStateException();
		}
		for (Station station: stationList) {
			LOG.info("Searching for Timetable");
			Timetable timetable = timetableDao.findTimetableByRunAndStation(station, run);
			if (timetable == null) {
				LOG.warn("Could not get Timetable with Station name " + station.getName() + " and Run.Id " + run.getId());
				throw new NullPointerException();
			}
			
			LOG.info("Updating timetable available seats count");
			timetable.setAvailableSeats(timetable.getAvailableSeats() - 1);
			timetableDao.update(timetable);
		}
		
	}


	/**Method adds Ticket into DB.
	 * @param userVO User value object
	 * @param stationFrom Departure Station
	 * @param stationTo Arrival Station
	 * @param run Run
	 * @param depTime Departure time
	 * @throws ParseException
	 * @throws NullPointerException
	 */
	private void addTicket(UserVO userVO, String stationFrom, String stationTo,
			Run run, String depTime) throws ParseException, NullPointerException {
		
		Date date = getDateFromString(depTime);
		User user = userDao.findById(userVO.getId());
		Station stationA = stationDao.findByName(stationFrom);
		Station stationB = stationDao.findByName(stationTo);
		
		if (user == null || stationA == null || stationB == null) {
			throw new NullPointerException();
		}
		
		
		Ticket ticket = new Ticket(user, run, stationA, stationB, date);
		ticketDao.persist(ticket);
		
	}


	/**Method does all checks for buying Ticket.
	 * @param userVO User value object
	 * @param stationFrom Departure Station
	 * @param depTime Departure time
	 * @param runId Run id
	 * @return true if all checks were passed
	 * @throws ParseException
	 * @throws MyException
	 */
	private boolean checkForBuying(UserVO userVO,  String stationFrom, String depTime, String runId) throws ParseException, MyException{
		
		Date date = getDateFromString(depTime);
		LOG.info("Checking for free seats in Run " + runId);
		boolean haveFreeSeats = checkForFreeSeats(stationFrom, runId);
		if (!haveFreeSeats) {
			LOG.info("Fail. There is no free seats");
			throw new MyException("Нет свободных мест");
		}
		
		LOG.info("Checking for User is already in passenger list on Run " + runId);
		boolean isAlreadyInTicketList = checkForRegistrationOnRun(runId, userVO);
		if (isAlreadyInTicketList) {
			LOG.info("Fail. User is already registered on that Run");
			throw new MyException("Вы уже зарегистрированы на этот поезд");
		}
		
		LOG.info("Checking for time left until departure");
		boolean haveEnoughTimeUntilDeparture = checkForEnoughTimeUntilDeparture(date);
		if (!haveEnoughTimeUntilDeparture) {
			LOG.info("Fail. There is no time left");
			throw new MyException("До отправления поезда осталось менее 10 минут");
		}
		
		if (haveFreeSeats && !isAlreadyInTicketList && haveEnoughTimeUntilDeparture) {
			return true;
		}
		return false;
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
	 * @throws NullPointerException
	 */
	private boolean checkForRegistrationOnRun(String run, UserVO userVO) throws NullPointerException{
		TicketDAOImpl tdao = new TicketDAOImpl();
		int userId = userVO.getId();
		int runId = Integer.parseInt(run);
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		boolean isRegistered = true;
		try {
			LOG.info("Searching Ticket in DB");
			Ticket ticket = tdao.findTicketByRunAndUserIds(runId, userId);
			if (ticket == null) {
				isRegistered = false;
			}
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (NullPointerException | HibernateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		return isRegistered;
	}

	/**Method checks for amount of free seats on given Run.
	 * @param stationFrom Departure Station
	 * @param runId Run id
	 * @return true if there are free seats
	 * @throws NullPointerException
	 */
	private boolean checkForFreeSeats(String stationFrom, String runId) throws NullPointerException {
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		
		int availableSeats = 0;
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			availableSeats = tdao.findAvailableSeatsCount(stationFrom, runId);
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (NullPointerException | HibernateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		if (availableSeats > 0) {
			return true;
		}
		return false;
	}


	/**Method returns TimetableVO list by given Run id and list of Stations.
	 * @param runId Run id
	 * @param stationList List of Stations
	 * @return TimetableVO list
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public List<TimetableVO> getTimesFromStationList(int runId, List<StationVO> stationList) 
			throws NullPointerException, IllegalArgumentException {
		LOG.info("Checking input parameters");
		if (stationList.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		
		LOG.info("Getting Run by id " + runId);
		Run run =  runService.getRunById(runId);
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Creating TimetableVO on every Station");
			for (StationVO station: stationList) {
				Date departureTimeBeforeFormat = timetableDao.findDepTimeFromStation(station.getName(), run);
				Date arrivalTimeBeforeFormat = timetableDao.findArrTimeToStation(station.getName(), run);
				if (departureTimeBeforeFormat == null || arrivalTimeBeforeFormat == null) {
					throw new NullPointerException();
				}
				SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm");
				String departureTime = dt.format(departureTimeBeforeFormat);
				String arrivalTime = dt.format(arrivalTimeBeforeFormat);
				TimetableVO timetable = new TimetableVO(departureTime, arrivalTime);
				timetableVOList.add(timetable);
			}
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (NullPointerException | HibernateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		
		
		return timetableVOList;
	}
}
