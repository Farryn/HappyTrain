/**
 * 
 */
package services;

import java.util.List;

import dao.RouteDAO;
import dao.RouteDAOImpl;
import dao.RunDAO;
import dao.RunDAOImpl;
import dao.TimetableDAO;
import dao.TimetableDAOImpl;
import dao.TrainDAO;
import dao.TrainDAOImpl;
import util.EmptyResultException;
import valueobjects.TimetableVO;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface TimetableService {
	public List<TimetableVO> getTimetableByStation(String station, String fromTime, String toTime);
	public void addRun(int trainId, String[] stationArray, String[] arrivalTime, String[] departureTime) throws EmptyResultException;
	public void setTimetableDao(TimetableDAO timetableDAO);
	public void setRouteDao(RouteDAO routeDAO);
	public void setRunDao(RunDAO runDAO);
	public void setTrainDao(TrainDAO trainDAO);
}
