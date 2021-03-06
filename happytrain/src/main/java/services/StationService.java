/**
 * 
 */
package services;

import java.util.List;

import dao.StationDAO;
import dao.StationDAOImpl;
import util.EmptyResultException;
import valueobjects.StationVO;
import entities.Station;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface StationService {
	public List<Station> getAllStations();
	public List<StationVO> getAllStationVO();
	public void addStation(String stationName) throws EmptyResultException;
	public void setStationDao(StationDAO stationDAO); 
}
