package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import servlets.ShowFoundTrainsServlet;
import util.HibernateUtil;
import valueobjects.RunVO;
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

	
	public List<TimetableVO> searchTrain(String stationA, String stationB, Date from, Date to) throws Exception {
		
		log.info("Checking input parameters");
		if (stationA == null || stationB == null || from == null || to == null) {
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
				int count = tdao.findAvailableSeatsCount(stationA, run);
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
	 
	public void buyTicket(UserVO userVO, String trainNumber, String stationFrom, String stationTo, String depTime, String runId) {
		Date date = getDateFromString(depTime);
		boolean haveFreeSeats = checkForFreeSeats(stationFrom, runId);
		boolean isAlreadyInTicketList = checkForRegistrationOnRun(runId, userVO);
		boolean haveEnoughTimeUntilDeparture = checkForEnoughTimeUntilDeparture(date);
		
		if (haveFreeSeats && !isAlreadyInTicketList && haveEnoughTimeUntilDeparture) {
			UserDAOImpl userDao = new UserDAOImpl();
			StationDAOImpl stationDao = new StationDAOImpl();
			RunDAOImpl runDao = new RunDAOImpl();
			TicketDAOImpl ticketDao = new TicketDAOImpl();
			RouteDAOImpl routeDao = new RouteDAOImpl();
			TimetableDAOImpl timetableDao = new TimetableDAOImpl();
			
			HibernateUtil.openCurrentSession();
			HibernateUtil.beginTransaction();
			try {
				User user = userDao.findById(userVO.getId());
				Station stationA = stationDao.findByName(stationFrom);
				Station stationB = stationDao.findByName(stationTo);
				Run run = runDao.findById(Integer.parseInt(runId));
				
				int stationFromOrdinalNumber = routeDao.getOrdinalNumber(stationA);
				int stationToOrdinalNumber = routeDao.getOrdinalNumber(stationA);
				
				Ticket ticket = new Ticket(user, run, stationA, stationB, date);
				ticketDao.persist(ticket);
				List<Station> stationList = routeDao.findStationsBetweenFromAndTo(run, stationFromOrdinalNumber, stationToOrdinalNumber);
				for (Station station: stationList){
					Timetable timetable = timetableDao.findTimetableByRunAndStation(stationList, run);
					timetable.setAvailableSeats(timetable.getAvailableSeats()-1);
					timetableDao.update(timetable);
				}
				
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				HibernateUtil.rollbackTransaction();
			} finally {
				HibernateUtil.closeCurrentSession();
			}
			
		}
		
	}

	

	private boolean checkForEnoughTimeUntilDeparture(Date date) {
		// TODO Auto-generated method stub
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
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		boolean isRegistered = true;
		try {
			Ticket ticket = tdao.findTicketByRunAndUserIds(runId, userId);
			if (ticket == null) {
				isRegistered = false;
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		return isRegistered;
	}

	private boolean checkForFreeSeats(String stationFrom, String runId) {
		StationDAOImpl sdao = new StationDAOImpl();
		RunDAOImpl rdao = new RunDAOImpl();
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		int availableSeats = 0;
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			Station station = sdao.findByName(stationFrom);
			Run run = rdao.findById(Integer.parseInt(runId));
			availableSeats = tdao.findAvailableSeatsCount(stationFrom, run);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
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
