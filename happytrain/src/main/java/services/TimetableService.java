/**
 * 
 */
package services;

import java.util.List;

import util.EmptyResultException;
import valueobjects.TimetableVO;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface TimetableService {
	public List<TimetableVO> getTimetableByStation(String station, String fromTime, String toTime);
	public void addRun(int trainId, String[] stationArray, String[] arrivalTime, String[] departureTime) throws EmptyResultException;
}
