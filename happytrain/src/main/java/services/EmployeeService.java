package services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.EmptyResultException;
import entities.Train;

/**
 * @author Damir Tuktamyshev
 * Service for Employee.
 */
@Service("employeeService")
public class EmployeeService {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(EmployeeService.class);
	/**
	 * Service for Train.
	 */
	@Autowired
	private TrainService trainService;
	/**
	 * Service for Route.
	 */
	@Autowired
	private RouteService routeService;
	
	/**
	 * @param trainService the trainService to set
	 */
	public void setTrainService(TrainService trainService) {
		this.trainService = trainService;
	}

	/**
	 * @param routeService the routeService to set
	 */
	public void setRouteService(RouteService routeService) {
		this.routeService = routeService;
	}

	/**Method adds Train into DB.
	 * @param name Train number
	 * @param seatsCount Count of seats
	 * @param stationArray Station array
	 * @throws EmptyResultException 
	 */
	@Transactional
	public void addTrain(String name, int seatsCount, String[] stationArray) throws EmptyResultException {
		
		LOG.info("Creating new Train with name " + name);
		Train train = new Train(name, seatsCount);
		
		LOG.info("Adding Train to DB");
		trainService.addTrain(train);
		
		LOG.info("Adding Route to DB");
		int count = 0;
		
		for (String stationName: stationArray) {
			count++;
			routeService.addRoute(train, stationName, count);
		}
	}
}
