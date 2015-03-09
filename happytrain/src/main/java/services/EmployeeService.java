package services;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import entities.Train;
import util.HibernateUtil;

public class EmployeeService {
	
	private static final Logger LOG = Logger.getLogger(EmployeeService.class);
	private TrainService trainService = new TrainService();
	private RouteService routeService = new RouteService();
	
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

	public void addTrain(String name, int seatsCount, String[] stationArray) throws NullPointerException, IllegalStateException {
		
		LOG.info("Creating new Train with name " + name);
		Train train = new Train(name, seatsCount);
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Adding Train to DB");
			trainService.addTrain(train);
			
			LOG.info("Adding Route to DB");
			int count = 0;
			if (stationArray == null) {
				throw new NullPointerException();
			}
			if (stationArray.length < 1) {
				throw new IllegalStateException();
			}
			for (String stationName: stationArray) {
				count++;
				routeService.addRoute(train, stationName, count);
			}
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (HibernateException | NullPointerException | IllegalStateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
	}
}
