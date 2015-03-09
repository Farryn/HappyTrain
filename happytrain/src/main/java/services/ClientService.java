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

public class ClientService {
	
	private static final Logger LOG = Logger.getLogger(ClientService.class);

	
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
		List<Run> runList = new ArrayList<Run>();
		List<Route> routeList = new ArrayList<Route>();
		
		TimetableDAO tdao = new TimetableDAOImpl();
		RouteDAO routeDao = new RouteDAOImpl();
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Getting Route between " + stationA + " and " + stationB);
			routeList = routeDao.findRouteFromAtoB(stationA, stationB);
			if (routeList.isEmpty()) {
				throw new IllegalStateException();
			} 
			
			LOG.info("Getting Runs between period of time from" + from + " to " + to);
			runList = tdao.findTrainWithDepTimeBetweenPeriodOfTime(routeList, from, to);
			if (runList.isEmpty()) {
				throw new IllegalStateException();
			}
			
			LOG.info("Creating TimetableVO on every found Run ");
			for (Run run:runList) {
				Date departureTimeBeforeFormat = tdao.findDepTimeFromStation(stationA, run);
				Date arrivalTimeBeforeFormat = tdao.findArrTimeToStation(stationB, run);
				int count = tdao.findAvailableSeatsCount(stationA, String.valueOf(run.getId()));
				if (departureTimeBeforeFormat == null || arrivalTimeBeforeFormat == null) {
					throw new NullPointerException();
				}
				SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm");
				String departureTime = dt.format(departureTimeBeforeFormat);
				String arrivalTime = dt.format(arrivalTimeBeforeFormat);
				TimetableVO timetable = new TimetableVO(run.getTrainId().getId() ,run.getTrainId().getNumber(), run.getId(), departureTime, arrivalTime, count);
				timetableVOList.add(timetable);
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
		
		
		return timetableVOList;
	}
	
	
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
	 *  
	 * @param userVO
	 * @param stationFrom
	 * @param stationTo
	 * @param depTime
	 * @param runId
	 * @throws MyException 
	 * @throws ParseException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	public void buyTicket(UserVO userVO,  String stationFrom, String stationTo, String depTime, String runId) 
			throws NullPointerException, IllegalStateException, IllegalArgumentException, ParseException, MyException  {
		
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
			UserDAO userDao = new UserDAOImpl();
			StationDAO stationDao = new StationDAOImpl();
			RunDAO runDao = new RunDAOImpl();
			TicketDAO ticketDao = new TicketDAOImpl();
			RouteDAO routeDao = new RouteDAOImpl();
			TimetableDAO timetableDao = new TimetableDAOImpl();
			
			LOG.info("Opening Hibernate Session with transaction");
			HibernateUtil.openCurrentSession();
			HibernateUtil.beginTransaction();
			try {
				User user = userDao.findById(userVO.getId());
				Station stationA = stationDao.findByName(stationFrom);
				Station stationB = stationDao.findByName(stationTo);
				Run run = runDao.findById(Integer.parseInt(runId));
				
				LOG.info("Searching ordinal numbers of first and last Station in order");
				int stationFromOrdinalNumber = routeDao.getOrdinalNumber(stationFrom, run.getTrainId());
				int stationToOrdinalNumber = routeDao.getOrdinalNumber(stationTo, run.getTrainId());
				
				LOG.info("Creating new Ticket and adding it to DB");
				Ticket ticket = new Ticket(user, run, stationA, stationB, date);
				ticketDao.persist(ticket);
				
				LOG.info("Searching for all Stations between first and last Station in order");
				List<Station> stationList = routeDao.findStationsBetweenFromAndTo(run, stationFromOrdinalNumber, stationToOrdinalNumber);
				if (stationList.isEmpty()) {
					LOG.warn("Empty stationList returned");
					throw new IllegalStateException();
				}
				for (Station station: stationList){
					LOG.info("Searching for Timetable");
					Timetable timetable = timetableDao.findTimetableByRunAndStation(station, run);
					if (timetable == null) {
						LOG.warn("Could not get Timetable with Station name " + station.getName() + " and Run.Id " + run.getId());
						throw new NullPointerException();
					}
					
					LOG.info("Updating timetable available seats count");
					timetable.setAvailableSeats(timetable.getAvailableSeats()-1);
					timetableDao.update(timetable);
				}
				
				LOG.info("Commiting transaction");
				HibernateUtil.commitTransaction();
			} catch (NullPointerException | IllegalStateException | HibernateException  e) {
				LOG.warn("Transaction was rollbacked");
				HibernateUtil.rollbackTransaction();
				throw e;
			} finally {
				LOG.info("Closing Hibernate Session");
				HibernateUtil.closeCurrentSession();
			}
			
		}
		
	}

	

	private boolean checkForEnoughTimeUntilDeparture(Date date) {
		LOG.info("Getting difference between current time and depTime of Train");
		long duration = date.getTime() - new Date().getTime();
		long difference = TimeUnit.MILLISECONDS.toMinutes(duration);
		if (difference > 10) {
			return true;
		}
		return false;
	}

	private boolean checkForRegistrationOnRun(String run, UserVO userVO) {
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
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		return isRegistered;
	}

	private boolean checkForFreeSeats(String stationFrom, String runId) {
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
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		if (availableSeats > 0) {
			return true;
		}
		return false;
	}


	public List<TimetableVO> getTimesFromStationList(int runId, List<StationVO> stationList) 
			throws NullPointerException, IllegalArgumentException {
		LOG.info("Checking input parameters");
		if (stationList.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		
		LOG.info("Getting Run by id " + runId);
		RunService runService = new RunService();
		Run run = runService.getRunById(runId);
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Creating TimetableVO on every Station");
			for (StationVO station: stationList) {
				Date departureTimeBeforeFormat = tdao.findDepTimeFromStation(station.getName(), run);
				Date arrivalTimeBeforeFormat = tdao.findArrTimeToStation(station.getName(), run);
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
