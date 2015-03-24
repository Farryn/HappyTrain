package dao;

import org.springframework.stereotype.Repository;

import entities.Train;

/**
 * Implementation of TrainDAO.
 *
 */
@Repository("trainDao")
public class TrainDAOImpl extends GenericDAOImpl<Integer, Train> implements	TrainDAO {

	

}
