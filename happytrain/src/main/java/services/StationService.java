/**
 * 
 */
package services;

import java.util.List;

import valueobjects.StationVO;
import entities.Station;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface StationService {
	public List<Station> getAllStations();
	public List<StationVO> getAllStationVO();
	public void addStation(String stationName); 
}
