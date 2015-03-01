package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import util.HibernateUtil;
import valueobjects.RunVO;
import valueobjects.StationVO;
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
	
	/*public List<RunVO> searchTrain(String stationA, String stationB, Date from, Date to) {
		StationService ss = new StationService();
		Station beginStation = ss.getStationByName(stationA);
		Station endStation = ss.getStationByName(stationB);
		List<RunVO> runVOList = new ArrayList<RunVO>();
		List<Run> runList = new ArrayList<Run>();
		List<Route> routeList = new ArrayList<Route>();
		RouteDAOImpl routeDao = new RouteDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			routeList = routeDao.findRouteFromAtoB(beginStation, endStation);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		}
		HibernateUtil.closeCurrentSession();
		
		if (!routeList.isEmpty()) {
			TimetableDAOImpl tdao = new TimetableDAOImpl();
			HibernateUtil.openCurrentSession();
			HibernateUtil.beginTransaction();
			try {
				runList = tdao.findTrainWithDepTimeBetweenPeriodOfTime(routeList, from, to);
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				HibernateUtil.rollbackTransaction();
			}
			HibernateUtil.closeCurrentSession();
		}
		for (Run run: runList) {
			runVOList.add(new RunVO(run));
		}
		return runVOList;
	}*/
	
	public List<RunVO> searchTrain(String stationA, String stationB, Date from, Date to) {
		StationService ss = new StationService();
		Station beginStation = ss.getStationByName(stationA);
		Station endStation = ss.getStationByName(stationB);
		
		List<RunVO> runVOList = new ArrayList<RunVO>();
		List<Run> runList = new ArrayList<Run>();
		List<Route> routeList = new ArrayList<Route>();
		
		RouteDAOImpl routeDao = new RouteDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			routeList = routeDao.findRouteFromAtoB(beginStation, endStation);
			if (!routeList.isEmpty()) {
				TimetableDAOImpl tdao = new TimetableDAOImpl();
				runList = tdao.findTrainWithDepTimeBetweenPeriodOfTime(routeList, from, to);
			}
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
	
	public Date getStationDepTime(StationVO stationVO, RunVO runVO){
		StationService ss = new StationService();
		RunService rs = new RunService();
		
		Station station = ss.getStationByName(stationVO.getName());
		Run run = rs.getRunById(runVO.getId());
		
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		Date depTime = new Date();
		try {
			depTime = tdao.findDepTimeFromStation(station, run);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		
		return depTime;
	}
	
	public Date getStationArrTime(StationVO stationVO, RunVO runVO){
		StationService ss = new StationService();
		RunService rs = new RunService();
		
		Station station = ss.getStationByName(stationVO.getName());
		Run run = rs.getRunById(runVO.getId());
		
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		Date arrTime = new Date();
		try {
			arrTime = tdao.findArrTimeToStation(station, run);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		return arrTime;
	}
	
	public int getStationAvailableSeats(StationVO stationVO, RunVO runVO){
		StationService ss = new StationService();
		RunService rs = new RunService();
		
		Station station = ss.getStationByName(stationVO.getName());
		Run run = rs.getRunById(runVO.getId());
		
		TimetableDAOImpl tdao = new TimetableDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		int count = 0;
		try {
			count = tdao.findAvailableSeatsCount(station, run);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		return count;
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
			availableSeats = tdao.findAvailableSeatsCount(station, run);
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
}
