/**
 * 
 */
package services;

import java.util.List;

import dao.RouteDAO;
import dao.RouteDAOImpl;
import dao.StationDAO;
import dao.StationDAOImpl;
import entities.Train;
import util.EmptyResultException;
import valueobjects.StationVO;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface RouteService {
	public List<StationVO> getStationsByTrain(int id);
	public void addRoute(Train train, String stationName, int count) throws EmptyResultException;
	public void setRouteDao(RouteDAO routeDAO);
	public void setStationDao(StationDAO stationDAO);
}
