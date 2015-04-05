/**
 * 
 */
package services;

import java.util.List;

import util.EmptyResultException;
import valueobjects.StationVO;
import valueobjects.TimetableVO;
import dao.TimetableDAO;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface ClientService {

	public void setTimetableDao(TimetableDAO timetableDao);
	public List<TimetableVO> searchTrain(String stationA, String stationB, String fromTime, String toTime);
	public void buyTicket(String login,  String stationFrom, String stationTo, String depTime, String runId) throws EmptyResultException;
	public List<TimetableVO> getTimesFromStationList(int runId, List<StationVO> stationList);
}
