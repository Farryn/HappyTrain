package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import util.HibernateUtil;
import valueobjects.TrainVO;
import dao.StationDAO;
import dao.TrainDAO;
import dao.TrainDAOImpl;
import entities.Train;

/**
 * @author Damir Tuktamyshev
 * Service for Train.
 */
@Service("trainService")
public class TrainService {

	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(TrainService.class);

	/**
	 * DAO for Train.
	 */
	@Autowired
	private TrainDAO trainDao;
	

	/**
	 * @param dao the dao to set
	 */
	public void setTrainDao(TrainDAO trainDao) {
		this.trainDao = trainDao;
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
