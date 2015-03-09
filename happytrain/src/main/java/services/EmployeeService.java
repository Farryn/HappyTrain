package services;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import entities.Train;
import util.HibernateUtil;

public class EmployeeService {
	
	private static final Logger LOG = Logger.getLogger(EmployeeService.class);
	
	public void addTrain(String name, int seatsCount, String[] stationArray) throws NullPointerException {
		TrainService ts = new TrainService();
		RouteService rs = new RouteService();
		LOG.info("Creating new Train with name " + name);
		Train train = new Train(name, seatsCount);
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Adding Train to DB");
			ts.addTrain(train);
			
			LOG.info("Adding Route to DB");
			int count = 0;
			for (String stationName: stationArray) {
				count++;
				rs.addRoute(train, stationName, count);
			}
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (HibernateException | NullPointerException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
	}
}
