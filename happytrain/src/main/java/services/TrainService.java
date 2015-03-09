package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import util.HibernateUtil;
import valueobjects.TrainVO;
import dao.TrainDAO;
import dao.TrainDAOImpl;
import entities.Train;

/**
 * @author Damir Tuktamyshev
 * Service for Train.
 */
public class TrainService {

	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(TrainService.class);

	/**
	 * DAO for Train.
	 */
	private TrainDAO dao = new TrainDAOImpl();
	

	/**
	 * @param dao the dao to set
	 */
	public void setDao(TrainDAO dao) {
		this.dao = dao;
	}


	/**Get all Trains.
	 * @return Train list
	 * @throws IllegalStateException
	 * @throws NullPointerException
	 */
	public List<TrainVO> getAllTrains() throws IllegalStateException, NullPointerException{
		List<Train> trainList = new ArrayList<Train>();
		List<TrainVO> trainVOList = new ArrayList<TrainVO>();
		
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Searching for all trains");
			trainList = dao.findAll();
			if (trainList.isEmpty()) {
				throw new IllegalStateException();
			}
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (HibernateException | NullPointerException |IllegalStateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		
		for (Train train: trainList) {
			trainVOList.add(new TrainVO(train));
		}
		return trainVOList;
		
	}
	
	
	/**Add Train onto DB.
	 * @param train Train
	 */
	public void addTrain(Train train){
		dao.persist(train);
	}
}
