/**
 * 
 */
package services;

import java.util.List;

import util.EmptyResultException;
import valueobjects.RunVO;
import entities.Run;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface RunService {
	public Run getRunById(int id) throws EmptyResultException;
	public List<RunVO> getRunByTrainId(int trainId);
}
