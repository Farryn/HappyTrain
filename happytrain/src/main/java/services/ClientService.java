package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import util.HibernateUtil;
import util.MyException;
import valueobjects.StationVO;
import valueobjects.TimetableVO;
import valueobjects.UserVO;
import dao.RouteDAOImpl;
import dao.RunDAOImpl;
import dao.StationDAOImpl;
import dao.TicketDAOImpl;
import dao.TimetableDAOImpl;
import dao.UserDAOImpl;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Ticket;
import entities.Timetable;
import entities.User;

public class ClientService {
	
	private static Logger log = Logger.getLogger(ClientService.class);

	
	public List<TimetableVO> searchTrain(String stationA, String stationB, String fromTime, String toTime) throws Exception {
		
		Date from = getDateFromString(fromTime);
		Date to = getDateFromString(toTime);
		log.info("Checking input parameters");
		if (stationA == null || stationB == null || from == null || to == null) {
			log.warn("Input parameter is null");
			throw new IllegalArgumentException();
		}
		
		List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		List<Run> runList = new ArrayList<Run>();
		List<Route> routeList = new ArrayList<Route>();
		
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		RouteDAOImpl routeDao = new RouteDAOImpl();
		
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			log.info("Getting Route between " + stationA + " and " + stationB);
			routeList = routeDao.findRouteFromAtoB(stationA, stationB);
			if (routeList.isEmpty()) {
				throw new IllegalStateException();
			} 
			
			log.info("Getting Runs between period of time from" + from + " to " + to);
			runList = tdao.findTrainWithDepTimeBetweenPeriodOfTime(routeList, from, to);
			if (runList.isEmpty()) {
				throw new IllegalStateException();
			}
			
			log.info("Creating TimetableVO on every found Run ");
			for (Run run:runList) {
				Date departureTime = tdao.findDepTimeFromStation(stationA, run);
				Date arrivalTime = tdao.findArrTimeToStation(stationB, run);
				int count = tdao.findAvailableSeatsCount(stationA, String.valueOf(run.getId()));
				if (departureTime == null || arrivalTime == null) {
					throw new NullPointerException();
				}
				TimetableVO timetable = new TimetableVO(run.getTrainId().getId() ,run.getTrainId().getNumber(), run.getId(), departureTime, arrivalTime, count);
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
			HibernateUtil.closeCurrentSession();
		}
		
		
		return timetableVOList;
	}
	
	
	 private Date getDateFromString(String str) throws Exception {
		 if (str == null) {
	    		throw new IllegalArgumentException();
	    	}
	    	Date date = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy hh:mm");
			date = sdf.parse(str);
			
	    	return date;
	    }
	 
	public void buyTicket(UserVO userVO,  String stationFrom, String stationTo, String depTime, String runId) throws Exception {
		
		Date date = getDateFromString(depTime);
		log.info("Checking for free seats in Run " + runId);
		boolean haveFreeSeats = checkForFreeSeats(stationFrom, runId);
		if (!haveFreeSeats) {
			log.info("Fail. There is no free seats");
			throw new MyException("Нет свободных мест");
		}
		
		log.info("Checking for User is already in passenger list on Run " + runId);
		boolean isAlreadyInTicketList = checkForRegistrationOnRun(runId, userVO);
		if (isAlreadyInTicketList) {
			log.info("Fail. User is already registered on that Run");
			throw new MyException("Вы уже зарегистрированы на этот поезд");
		}
		
		log.info("Checking for time left until departure");
		boolean haveEnoughTimeUntilDeparture = checkForEnoughTimeUntilDeparture(date);
		if (!haveEnoughTimeUntilDeparture) {
			log.info("Fail. There is no time left");
			throw new MyException("До отправления поезда осталось менее 10 минут");
		}
		
		if (haveFreeSeats && !isAlreadyInTicketList && haveEnoughTimeUntilDeparture) {
			UserDAOImpl userDao = new UserDAOImpl();
			StationDAOImpl stationDao = new StationDAOImpl();
			RunDAOImpl runDao = new RunDAOImpl();
			TicketDAOImpl ticketDao = new TicketDAOImpl();
			RouteDAOImpl routeDao = new RouteDAOImpl();
			TimetableDAOImpl timetableDao = new TimetableDAOImpl();
			
			log.info("Opening Hibernate Session with transaction");
			HibernateUtil.openCurrentSession();
			HibernateUtil.beginTransaction();
			try {
				User user = userDao.findById(userVO.getId());
				Station stationA = stationDao.findByName(stationFrom);
				Station stationB = stationDao.findByName(stationTo);
				Run run = runDao.findById(Integer.parseInt(runId));
				
				log.info("Searching ordinal numbers of first and last Station in order");
				int stationFromOrdinalNumber = routeDao.getOrdinalNumber(stationFrom, run.getTrainId());
				int stationToOrdinalNumber = routeDao.getOrdinalNumber(stationTo, run.getTrainId());
				
				log.info("Creating new Ticket and adding it to DB");
				Ticket ticket = new Ticket(user, run, stationA, stationB, date);
				ticketDao.persist(ticket);
				
				log.info("Searching for all Stations between first and last Station in order");
				List<Station> stationList = routeDao.findStationsBetweenFromAndTo(run, stationFromOrdinalNumber, stationToOrdinalNumber);
				if (stationList.isEmpty()) {
					log.warn("Empty stationList returned");
					throw new IllegalStateException();
				}
				for (Station station: stationList){
					log.info("Searching for Timetable");
					Timetable timetable = timetableDao.findTimetableByRunAndStation(station, run);
					if (timetable == null) {
						log.warn("Could not get Timetable with Station name " + station.getName() + " and Run.Id " + run.getId());
						throw new NullPointerException();
					}
					
					log.info("Updating timetable available seats count");
					timetable.setAvailableSeats(timetable.getAvailableSeats()-1);
					timetableDao.update(timetable);
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

	

	private boolean checkForEnoughTimeUntilDeparture(Date date) {
		log.info("Getting difference between current time and depTime of Train");
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
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		boolean isRegistered = true;
		try {
			log.info("Searching Ticket in DB");
			Ticket ticket = tdao.findTicketByRunAndUserIds(runId, userId);
			if (ticket == null) {
				isRegistered = false;
			}
			log.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			log.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
		} finally {
			log.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		return isRegistered;
	}

	private boolean checkForFreeSeats(String stationFrom, String runId) {
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		
		int availableSeats = 0;
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			availableSeats = tdao.findAvailableSeatsCount(stationFrom, runId);
			log.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			log.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
		} finally {
			log.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		if (availableSeats > 0) {
			return true;
		}
		return false;
	}


	public List<TimetableVO> getTimesFromStationList(int runId, List<StationVO> stationList) throws Exception {
		log.info("Checking input parameters");
		if (stationList.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		List<TimetableVO> timetableVOList = new ArrayList<TimetableVO>();
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		
		log.info("Getting Run by id " + runId);
		RunService runService = new RunService();
		Run run = runService.getRunById(runId);
		
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			log.info("Creating TimetableVO on every Station");
			for (StationVO station: stationList) {
				Date departureTime = tdao.findDepTimeFromStation(station.getName(), run);
				Date arrivalTime = tdao.findArrTimeToStation(station.getName(), run);
				if (departureTime == null || arrivalTime == null) {
					throw new NullPointerException();
				}
				TimetableVO timetable = new TimetableVO(departureTime, arrivalTime);
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
			HibernateUtil.closeCurrentSession();
		}
		
		
		return timetableVOList;
	}
}
