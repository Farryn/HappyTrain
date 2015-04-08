/**
 * 
 */
package services;

import java.util.List;

import dao.RunDAO;
import dao.RunDAOImpl;
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
	public void setRunDao(RunDAO runDAO);
}
