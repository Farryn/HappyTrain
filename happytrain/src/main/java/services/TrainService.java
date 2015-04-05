/**
 * 
 */
package services;

import java.util.List;

import entities.Train;
import valueobjects.TrainVO;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface TrainService {
	public List<TrainVO> getAllTrains();
	public void addTrain(Train train);
}
