package dao;

import java.util.List;
import entities.Run;

/**
 *DAO for Run Entity.
 *
 */
public interface RunDAO extends GenericDAO<Integer, Run> {
	/**Get Runs by given Train Id.
	 * @param trainId Train Id
	 * @return Runs list
	 */
	List<Run> findByTrainId(final int trainId);
}
