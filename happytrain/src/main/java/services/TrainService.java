package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import valueobjects.TrainVO;
import dao.TrainDAO;
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
	 */
	@Transactional
	public List<TrainVO> getAllTrains(){
		List<Train> trainList = new ArrayList<Train>();
		List<TrainVO> trainVOList = new ArrayList<TrainVO>();
		LOG.info("Searching for all trains");
		trainList = trainDao.findAll();
		if (trainList.isEmpty()) {
			LOG.warn("Received empty Train List from DAO");
			return new ArrayList<TrainVO>();
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
		trainDao.persist(train);
	}
}
