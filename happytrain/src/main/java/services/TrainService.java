package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import servlets.ShowAllTrainsServlet;
import util.HibernateUtil;
import valueobjects.TrainVO;
import dao.StationDAOImpl;
import dao.TrainDAOImpl;
import entities.Station;
import entities.Train;

public class TrainService {

	private static Logger log = Logger.getLogger(TrainService.class);

	
	/*public TrainVO getTrainVOById(int id){
		TrainService ts = new TrainService();
		Train train = ts.getTrainById(id);
		TrainVO trainVO = new TrainVO(train);
		return trainVO;
		
	}
	
	public Train getTrainById(int id){
		
		TrainDAOImpl dao = new TrainDAOImpl();
		Train train = null;
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			train = dao.findById(id);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		return train;
		
	}
*/
	public List<TrainVO> getAllTrains() throws Exception{
		List<Train> trainList = new ArrayList<Train>();
		List<TrainVO> trainVOList = new ArrayList<TrainVO>();
		
		TrainDAOImpl dao = new TrainDAOImpl();
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			log.info("Searching for all trains");
			trainList = dao.findAll();
			if (trainList.isEmpty()) {
				throw new IllegalStateException();
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
		
		for (Train train: trainList) {
			trainVOList.add(new TrainVO(train));
		}
		return trainVOList;
		
	}
	
	
	public void addTrain(Train train){
		TrainDAOImpl dao = new TrainDAOImpl();
		dao.persist(train);
	}
}
