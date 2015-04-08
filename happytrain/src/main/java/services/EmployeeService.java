/**
 * 
 */
package services;

import util.EmptyResultException;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface EmployeeService {
	public void addTrain(String name, int seatsCount, String[] stationArray) throws EmptyResultException;

	public void setRouteService(RouteService routeService);

	public void setTrainService(TrainService trainService);
}
