package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import entities.Station;
import entities.Train;
import util.HibernateUtil;
import valueobjects.StationVO;
import valueobjects.TrainVO;

public class EmployeeService {
	
	private static Logger log = Logger.getLogger(EmployeeService.class);
	
	public void addTrain(String name, int seatsCount, String[] stationArray) throws Exception {
		TrainService ts = new TrainService();
		RouteService rs = new RouteService();
		StationService ss = new StationService();
		
		log.info("Creating new Train with name " + name);
		Train train = new Train(name, seatsCount);
		
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			log.info("Adding Train to DB");
			ts.addTrain(train);
			
			log.info("Adding Route to DB");
			int count = 0;
			for (String stationName: stationArray) {
				count++;
				rs.addRoute(train, stationName, count);
			}
			log.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			log.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			log.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
	}
}
