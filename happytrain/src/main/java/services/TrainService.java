/**
 * 
 */
package services;

import java.util.List;

import dao.TrainDAO;
import entities.Train;
import valueobjects.TrainVO;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface TrainService {
	public List<TrainVO> getAllTrains();
	public void addTrain(Train train);
	public void setTrainDao(TrainDAO trainDAO);
}
